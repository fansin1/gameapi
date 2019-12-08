package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooLargeFileException;
import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String mPathToBin;

    public MyClassLoader(String pathToBin, ClassLoader parent) {
        super(parent);
        this.mPathToBin = pathToBin;
    }

    public Class<?> loadClassInDir(String dir, String className) {
        byte[] b = new byte[0];
        if (!dir.endsWith("/"))
            dir = dir + "/";
        try {
            b = fetchClassFromFS(mPathToBin + dir + className + ".class");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(className, b, 0, b.length);
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] b = new byte[0];
        try {
            b = fetchClassFromFS(mPathToBin + className + ".class");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(className, b, 0, b.length);
    }

    private byte[] fetchClassFromFS(String path) throws IOException {
        File file = new File(path);
        long length = file.length();

        byte[] bytes = new byte[(int)length];

        try (InputStream is = new FileInputStream(file)) {
            if (length > Integer.MAX_VALUE) {
                throw new TooLargeFileException();
            }

            int offset = 0;
            int numRead;
            while (offset < bytes.length
                    && (numRead=is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + path);
            }
        }

        return bytes;
    }
}
