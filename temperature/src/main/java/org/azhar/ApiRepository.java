package org.azhar;

import org.azhar.dbmanager.DataRepository;
import org.azhar.dbmanager.DataResource;
import org.azhar.mail.TokenMailer;
import org.azhar.token.TokenResource;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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




    //to store new registered user and send token verification
    @POST
    @Path("/register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNew(DataRepository userData){
        if(!dataResource.checkUser(userData.email)){
            String newToken = token.verificationToken(dataResource.getId(userData.email));
            return  dataResource.checkStatus(dataResource.getId(userData.email))?Response.ok("Already registered").build()
                    :Response.ok(tokenMailer.mailToken(userData.email, newToken)).build();
        }
        if(dataResource.newUser(userData)){
            String _token = token.verificationToken(userData.id);
            return Response.ok(tokenMailer.mailToken(userData.email, _token)).build(); //TODO: create verification token and send email
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
    public Response activateAccount(@Context SecurityContext id){   // TODO: take token of activation here and verify its id and email
        Long _id = Long.parseLong(id.getUserPrincipal().getName());
        return Response.ok(dataResource.activateStatus(_id,true)).build();
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
            return Response.status(Response.Status.CREATED).header("session_token",sessionToken).build(); //TODO: session token here
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    @POST
    @RolesAllowed("user")
    @Path("/directory")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userDir(@Context SecurityContext securityId){
        return Response.ok(dataResource.getDir(token.extractId(securityId))).build();
    }



    //to get all user data
    //TODO: remove this when done
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response dumpAll() {
        return Response.ok(dataResource.getUsers()).build();
    }

    //test function
    //TODO: remove this when done
    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response testFunction(@Context SecurityContext securityContext) {
        return Response.ok(token.extractId(securityContext)).build();
    }
}
