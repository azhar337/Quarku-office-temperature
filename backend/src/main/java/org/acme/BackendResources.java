package org.acme;

import org.acme.database.DataRepository;
import org.acme.database.DataResources;
import org.acme.token.TokenResources;
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
@Path("/auth")
@ApplicationScoped
public class BackendResources {


    @Inject
    DataResources UserDb;

    @Inject
    TokenResources token;



    //to store new registered user and send token verification
    @POST
    @Path("/register")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNew(DataRepository userData){
        if(!UserDb.checkUser(userData.email)){
            return Response.ok("Already registered").build();
        }
        if(UserDb.newUser(userData)){
            String _token = token.verificationToken(userData.id);
            return Response.ok(_token).build(); //TODO: create verification token and send email
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
        return Response.ok(UserDb.activateStatus(_id,true)).build();
    }

    //user login verification and send session token
    @POST
    @Path("/login")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(DataRepository _details){
        if(UserDb.checkUser(_details.email)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Long id = UserDb.getId(_details.email);

        if (!UserDb.checkStatus(id)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (Objects.equals(UserDb.getPassword(id), _details.password)){
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
        return Response.ok(UserDb.getDir(token.extractId(securityId))).build();
    }



    //to get all user data
    //TODO: remove this when done
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response dumpAll() {
        return Response.ok(UserDb.getUsers()).build();
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
