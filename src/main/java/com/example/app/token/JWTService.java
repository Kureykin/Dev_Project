package com.example.app.token;


import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

import java.text.ParseException;

public class JWTService {

    public String compile(String user) {
        JWTClaimsSet jwt = new JWTClaimsSet.Builder().subject(user).build();

        PlainJWT plainJWT = new PlainJWT(jwt);

        return plainJWT.serialize();
    }

    public JWT parse(String token) throws ParseException {
        return  PlainJWT.parse(token);
    }
}
