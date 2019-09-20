package org.derek.processor.util;

import java.io.Closeable;
import java.io.IOException;

public final class IOUtil {
    public static void closeQuitly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void print(String content){
        System.out.println(content);
    }
}
