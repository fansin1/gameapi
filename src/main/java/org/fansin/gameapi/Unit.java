package org.fansin.gameapi;

import java.util.Collections;
import java.util.List;

public class Unit {

    private final UnitType type;
    private final String name;
    private final int hitPoints;
    private final int attack;
    private final Damage damage;
    private final int defence;
    private final double initiative;
    private final List<UnitFeature> features;
    private final List<Skill> skills;

    public Unit(UnitType type,
                String name,
                Damage damage,
                List<UnitFeature> features,
                List<Skill> skills,
                int attack,
                int hitPoints,
                int defence,
                double initiative) {
        this.type = type;
        this.name = name;
        this.hitPoints = hitPoints;
        this.attack = attack;
        this.damage = damage;
        this.defence = defence;
        this.initiative = initiative;
        this.features = features;
        this.skills = skills;
    }

    public UnitType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<UnitFeature> getFeatures() {
        return Collections.unmodifiableList(features);
    }

    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public Damage getDamage() {
        return damage;
    }

    public int getDefence() {
        return defence;
    }

    public double getInitiative() {
        return initiative;
    }

    public int getAttack() {
        return attack;
    }

}
