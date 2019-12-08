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

    private void loadInDir(File modsDir, String dirName) {
        File featuresDir = new File(modsDir, dirName);

        String[] files = featuresDir.list();

        if (files == null)
            throw new WrongDirException();

        for (String modName : files) {
            Object obj = mClassLoader.loadClassInDir(dirName, modName.substring(0, modName.length() - 6));
        }
    }

    private void loadModifiers(File modsDir) {
        loadInDir(modsDir, "modifiers");
    }

    private void loadFeatures(File modsDir) throws ClassNotFoundException {
        loadInDir(modsDir, "features");
    }

    public void registerUnits() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        File modsDir = new File(mPathToModsDir);
        loadFeatures(modsDir);
        loadModifiers(modsDir);

        File[] files = modsDir.listFiles();

        if (files == null)
            throw new WrongDirException();

        for (File modFile : files) {
            if (modFile.isDirectory())
                continue;

            String modName = modFile.getName();

            Object obj = mClassLoader.findClass(modName.substring(0, modName.length() - 6)).newInstance();

            if (obj instanceof Unit) {
                Barracks.getInstance().registerUnit((Unit)obj);
            }
        }
    }
}
