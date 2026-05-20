package com.example.enyakinrestorant;

import java.io.File;

public class FileHelper {
    public static File findFile(String relativePath) {
        File f1 = new File(relativePath);
        if (f1.exists()) {
            return f1;
        }
        File f2 = new File("EN_YAKIN_RESTORANT/EN_YAKIN_RESTORANT/" + relativePath);
        if (f2.exists()) {
            return f2;
        }
        File f3 = new File("EN_YAKIN_RESTORANT/" + relativePath);
        if (f3.exists()) {
            return f3;
        }
        
        // If not found, choose the path where the parent folder exists
        try {
            File parentOfRelative = new File(relativePath).getParentFile();
            String parentPathStr = parentOfRelative != null ? parentOfRelative.getPath() : "";
            
            File p2 = new File("EN_YAKIN_RESTORANT/EN_YAKIN_RESTORANT/" + parentPathStr);
            if (p2.exists()) {
                return f2;
            }
            File p3 = new File("EN_YAKIN_RESTORANT/" + parentPathStr);
            if (p3.exists()) {
                return f3;
            }
        } catch (Exception e) {
            // Ignore and fall back
        }
        return f1;
    }
}
