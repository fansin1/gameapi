package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooLargeFileException;
import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String mPathToBin;

    public MyClassLoader(String pathToBin, ClassLoader parent) {
        super(parent);
        this.mPathToBin = pathToBin;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            byte[] b = fetchClassFromFS(mPathToBin + className + ".class");
            return defineClass(className, b, 0, b.length);
        } catch (IOException ex) {
            return super.findClass(className);
        }
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
