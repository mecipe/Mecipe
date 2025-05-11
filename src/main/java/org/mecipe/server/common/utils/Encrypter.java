package org.mecipe.server.common.utils;

import org.springframework.util.DigestUtils;

public class Encrypter {

    private static final String SALT = "mecipe-server-salt-36d9cd6d59f4d43a";

    public static String encryptPassword(String plainText) {
        return DigestUtils.md5DigestAsHex((SALT + plainText).getBytes());
    }

}
