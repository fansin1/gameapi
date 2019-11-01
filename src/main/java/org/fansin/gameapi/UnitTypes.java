package org.fansin.gameapi;

public enum UnitTypes implements UnitType {
    CROSSBOWMAN("Crossbowman"),
    SKELETON("Skeleton"),
    FURY("Fury"),
    HYDRA("Hydra"),
    ANGEL("Angel"),
    BONEDRAGON("Bonedragon"),
    DEVIL("Devil"),
    CYCLOPS("Cyclops"),
    GRIFFIN("Griffin"),
    SHAMAN("Shaman"),
    LICH("Lich");

    private String name;

    UnitTypes(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}