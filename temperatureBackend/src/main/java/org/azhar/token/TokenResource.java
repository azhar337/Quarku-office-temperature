package org.azhar.token;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

@Singleton
public class TokenResource {

    @Inject
    TokenRepository create;

    public String verificationToken(Long id){
        return create.verificationTkn(String.valueOf(id));
    }

    public String sessionToken(Long id){
        return create.sessionTkn(String.valueOf(id));
    }

    public Long extractId(@Context SecurityContext securityContext){
        return Long.parseLong(securityContext.getUserPrincipal().getName());
    }
}
