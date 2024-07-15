package cn.ksmcbrigade.btt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static void write(File file,String str) throws IOException {
        Path path = file.toPath();
        Files.writeString(path,new String(str.getBytes(),StandardCharsets.UTF_8));
    }

    public static String readAsciiFile(File file) throws IOException {
        Path path = file.toPath();
        return Files.readString(path,StandardCharsets.UTF_8);
    }
}
