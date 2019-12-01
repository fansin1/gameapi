package org.fansin.gameapi;

import java.util.Collections;
import java.util.List;

public class Unit {

    private final String mName;
    private final int mHitPoints;
    private final int mAttack;
    private final Damage mDamage;
    private final int mDefence;
    private final double mInitiative;
    private final List<UnitFeature> mFeatures;
    private final List<Skill> mSkills;

    public Unit(String name,
                Damage damage,
                List<UnitFeature> features,
                List<Skill> skills,
                int attack,
                int hitPoints,
                int defence,
                double initiative) {
        this.mName = name;
        this.mHitPoints = hitPoints;
        this.mAttack = attack;
        this.mDamage = damage;
        this.mDefence = defence;
        this.mInitiative = initiative;
        this.mFeatures = features;
        this.mSkills = skills;
    }

    public String getName() {
        return mName;
    }

    public List<UnitFeature> getFeatures() {
        return Collections.unmodifiableList(mFeatures);
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(mSkills);
    }

    public int getHitPoints() {
        return mHitPoints;
    }

    public Damage getDamage() {
        return mDamage;
    }

    public int getDefence() {
        return mDefence;
    }

    public double getInitiative() {
        return mInitiative;
    }

    public int getAttack() {
        return mAttack;
    }

}
