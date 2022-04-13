package org.acme;

import org.acme.database.temperature.TemperatureRepository;
import org.acme.database.temperature.TemperatureResources;
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

@SecurityScheme(
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT"
)
@Path("/")
@ApplicationScoped
public class DataResources {

    @Inject
    TemperatureResources temperatureResources;

    @Inject
    TokenResources token;

    @GET
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response main(@Context SecurityContext securityContext){
        return Response.ok(temperatureResources.getData(token.extractId(securityContext))).build();
    }

    @POST
    @RolesAllowed("user")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertData(@Context SecurityContext securityContext, TemperatureRepository directory){
        directory.userId = token.extractId(securityContext);
        return Response.ok(temperatureResources.newDir(directory)).build();
    }



//    @POST
//    @Path("/userDetail")
//    @Transactional
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response find(String email){
//        List<UserRepository> userDetails = UserDb.getDetail(email);
//        if (!userDetails.isEmpty()){
//            return Response.ok(userDetails).build();
//        }
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }


    // need a function to extract id from token
//     need a function to verify id from token
    // data send here check the token is it "user" than extract id

}
