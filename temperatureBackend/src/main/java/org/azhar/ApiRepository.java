package org.azhar;

import org.azhar.dbmanager.DataRepository;
import org.azhar.dbmanager.DataResource;
import org.azhar.prediction.PredictionResources;
import org.azhar.utils.TokenMailer;
import org.azhar.token.TokenResource;
import org.azhar.utils.FileManager;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.io.*;
import java.util.Objects;

@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
@Path("/api")
@ApplicationScoped
public class ApiRepository {

    @Inject
    DataResource dataResource;

    @Inject
    TokenResource token;

    @Inject
    TokenMailer tokenMailer;

    @Inject
    FileManager fileManager;


    //to store new registered user and send token verification
    @POST
    @Path("/register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNew(DataRepository userData){
        if(!dataResource.checkUser(userData.email)){
            String newToken = token.verificationToken(dataResource.getId(userData.email));
            return  dataResource.checkStatus(dataResource.getId(userData.email))?
                    Response.ok("Already registered").build()
                    :Response.ok(tokenMailer.mailToken(userData.email, newToken)).build();
        }
        if(dataResource.newUser(userData)){
            String _token = token.verificationToken(userData.id);
            return Response.ok(tokenMailer.mailToken(userData.email, _token)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    //activate account
    @POST
    @Path("/activate")
    @RolesAllowed("activation")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response activateAccount(@Context SecurityContext id){
        Long _id = Long.parseLong(id.getUserPrincipal().getName());
        dataResource.activateStatus(_id,true);
        return Response.ok(dataResource.checkStatus(_id)).build();
    }

    //user login verification and send session token
    @POST
    @Path("/login")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(DataRepository details){
        if(dataResource.checkUser(details.email)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Long id = dataResource.getId(details.email);

        if (!dataResource.checkStatus(id)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (Objects.equals(dataResource.getPassword(id), details.password)){
            String sessionToken = token.sessionToken(id);
            return Response.ok(sessionToken).build(); //TODO: session token here
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    //user upload csv file
    @POST
    @RolesAllowed("user")
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response userDir(@Context SecurityContext securityId, @MultipartForm MultipartBody data) throws IOException {
        String path = fileManager.storeFile(data);
        dataResource.uploadDir(token.extractId(securityId), path);
        return Response.ok().build();
    }

    @POST
    @RolesAllowed("user")
    @Path("/files")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiles(@Context SecurityContext securityId){
        return Response.ok(dataResource.getDir(token.extractId(securityId))).build();
    }

    @POST
    @RolesAllowed("user")
    @Path("/data/{id}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Transactional
    public File getData(@Context SecurityContext securityId,@PathParam("id") Long dataId){

       String dir = dataResource.getDir(token.extractId(securityId));
        return fileManager.grabFile(dir,dataId);
    }

    @POST
    @RolesAllowed("user")
    @Path("/prediction/{id}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prediction(
            @Context SecurityContext securityId, @PathParam("id") Long dataId,@PathParam("date") String date
    ) throws IOException {

        String dir = dataResource.getDir(token.extractId(securityId));
        String prediction = PredictionResources.makePrediction(dir,date);
        return Response.ok(prediction).build();
    }

}
