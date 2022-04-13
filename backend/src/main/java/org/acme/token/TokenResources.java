package org.acme.token;


import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * To handle call related to token, creation, and decoding the token
 * */
@Singleton
public class TokenResources {

    @Inject
    TokenCreate create;

    public String verificationToken(Long id){
        return create.verificationTkn(String.valueOf(id));
    }

    public String sessionToken(Long id){
        return create.sessionTkn(String.valueOf(id));
    }

    public Long extractId(@Context SecurityContext securityContext){
        return Long.parseLong(securityContext.getUserPrincipal().getName());
    }


// create token verification and session
// decode session token to id


}
