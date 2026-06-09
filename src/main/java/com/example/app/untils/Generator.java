package com.example.app.untils;

import java.util.UUID;

public class Generator {
    public static String generateId() {
        return UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8);
    }
}
