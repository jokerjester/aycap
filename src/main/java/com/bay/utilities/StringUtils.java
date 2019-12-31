package com.bay.utilities;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class StringUtils {

    public static String hash(String text) {
        return Hashing.sha256()
                .hashString(text, StandardCharsets.UTF_8)
                .toString();
    }
}
