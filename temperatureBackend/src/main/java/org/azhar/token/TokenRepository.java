package org.azhar.token;

import io.smallrye.jwt.build.Jwt;

import javax.inject.Singleton;

@Singleton
public class TokenRepository {

    public String verificationTkn(String id) {

        return Jwt.issuer("azhar")
                .subject(id)
                .groups("activation")
                .expiresIn(900)
                .sign();
    }

    public String sessionTkn(String id) {

        return Jwt.issuer("azhar")
                .subject(id)
                .groups("user")
                .expiresIn(999999999)
                .sign();
    }
}
