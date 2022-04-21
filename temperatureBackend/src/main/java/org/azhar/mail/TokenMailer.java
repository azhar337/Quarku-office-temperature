package org.azhar.mail;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TokenMailer {

    @Inject
    Mailer mailer;

    @ConfigProperty(name = "url.address")
    String urlAddress;

    public String mailToken(String toMail, String token) {

        String header = "Your email verification";
        String body = "<strong>Click <a href='"+ urlAddress +"?ver="+ token +"'>here to verify</a></strong>"; //TODO: Make the frontend get the token in this url then put it as header and send to backend
        Mail sendToken = Mail.withHtml(toMail, header, body);

        mailer.send(sendToken);

        return "token send";
    }
}
