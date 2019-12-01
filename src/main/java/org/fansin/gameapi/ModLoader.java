package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.WrongDirException;

import java.io.File;

public class ModLoader {

    private MyClassLoader mClassLoader;
    private String mPathToModsDir;

    public ModLoader(String pathToModsDir) {
        if (!pathToModsDir.endsWith("/")) {
            pathToModsDir = pathToModsDir + "/";
        }
        this.mPathToModsDir = pathToModsDir;
        this.mClassLoader = new MyClassLoader(pathToModsDir, ClassLoader.getSystemClassLoader());
    }

    public void registerUnits() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File modsDir = new File(mPathToModsDir);

        String[] files = modsDir.list();

        if (files == null)
            throw new WrongDirException();

        for (String modName : files) {
            Class clazz = mClassLoader.
                    findClass(modName.substring(0, modName.length() - 6));

            Object obj = clazz.newInstance();

            if (obj instanceof Unit) {
                Barracks.getInstance().registerUnit((Unit)obj);
            }
        }
    }

}
