package com.mtech.sjmsjob.util;

import java.util.Base64;

import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;

//@Component
//public class JwtTokenUtil {
//
//    public String getUserNameFromJWT(String token) {
//        var tokenParts = token.split("\\.");
//        String tokenWithoutSignature = tokenParts[1];
//        Base64.Decoder decoder = Base64.getDecoder();
//        var tokenWithoutSignatureDecode = new String(decoder.decode(tokenWithoutSignature));
//
//        var jsonParser = JsonParserFactory.getJsonParser();
//        var map = jsonParser.parseMap(tokenWithoutSignatureDecode);
//        var username = (String) map.get("username");
//        return username;
//    }
//}
