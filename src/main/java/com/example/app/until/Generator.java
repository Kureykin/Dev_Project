package com.example.app.until;

import java.util.UUID;

public class Generator {
    public static String generateId() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }
}
