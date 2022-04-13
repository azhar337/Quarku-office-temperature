package org.acme.token;
/**
 * To create verification token and session token
 *
 * */
import io.smallrye.jwt.build.Jwt;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
@Singleton
public class TokenCreate {

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
