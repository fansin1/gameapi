package org.fansin.gameapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BattleUnitStack extends UnitStack {

    private int lastUnitHipPoints;
    private final int startCount;
    private boolean rebuffedThisRound = false;
    private List<TemporaryModifier> modifiers = new ArrayList<>();

    public BattleUnitStack(UnitStack unitStack) {
        super(unitStack.unit, unitStack.count);
        startCount = count;
        lastUnitHipPoints = unit.getHitPoints();
    }

    private int calculateAttack(BattleUnitStack enemy) {
        int attack = getAttack();

        for (TemporaryModifier mod : modifiers) {
            attack = mod.attackChange(attack, unit, enemy.unit);
        }

        for (UnitFeature feature : unit.getFeatures()) {
            attack = feature.attackChange(attack, enemy.unit);
        }


        for (TemporaryModifier mod : enemy.modifiers) {
            attack = mod.enemyAttackChange(attack, enemy.unit, unit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            attack = feature.enemyAttackChange(attack, unit);
        }

        return attack;
    }

    private int calculateDamage(BattleUnitStack enemy) {
        int damage = getDamage().getRandom();

        for (TemporaryModifier mod : modifiers) {
            damage = mod.damageChange(damage, unit, enemy.unit);
        }

        for (UnitFeature feature : unit.getFeatures()) {
            damage = feature.damageChange(damage, enemy.unit);
        }


        for (TemporaryModifier mod : enemy.modifiers) {
            damage = mod.enemyDamageChange(damage, enemy.unit, unit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            damage = feature.enemyDamageChange(damage, unit);
        }

        return damage * count;
    }

    private int calculateDefence(BattleUnitStack enemy) {
        int defence = getDefence();

        for (TemporaryModifier mod : modifiers) {
            defence = mod.defenceChange(defence, unit, enemy.unit);
        }

        for (UnitFeature feature : unit.getFeatures()) {
            defence = feature.defenceChange(defence, enemy.unit);
        }

        for (TemporaryModifier mod : enemy.modifiers) {
            defence = mod.enemyDefenceChange(defence, enemy.unit, unit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            defence = feature.enemyDefenceChange(defence, unit);
        }

        return defence;
    }

    private void damagedBy(int damage, int attack, BattleUnitStack enemy) {
        int defence = calculateDefence(enemy);

        if (attack > defence) {
            lastUnitHipPoints -= damage * (1 + 0.05f * (attack - defence));
        } else if (attack < defence) {
            lastUnitHipPoints -= damage / (1 + 0.05f * (defence - attack));
        } else {
            lastUnitHipPoints -= damage;
        }

        while (lastUnitHipPoints <= 0 && count > 0) {
            count--;
            lastUnitHipPoints += unit.getHitPoints();
        }

        if (count == 0) lastUnitHipPoints = 0;
    }

    private void rebuff(BattleUnitStack enemy) {
        if (canRebuff(enemy)) {
            enemy.damagedBy(calculateDamage(enemy), calculateAttack(enemy), enemy);
            rebuffedThisRound = true;
        }
    }

    public void healBy(int hitPoints) {
        lastUnitHipPoints += hitPoints;

        while (count < getStartCount() && lastUnitHipPoints > unit.getHitPoints()) {
            lastUnitHipPoints -= unit.getHitPoints();
        }

        if (lastUnitHipPoints > unit.getHitPoints())
            lastUnitHipPoints = unit.getHitPoints();
    }

    public void useSkill(Battle battle, int skillIndex, BattleUnitStack... targets) {
        getSkills().get(skillIndex).cast(battle, this, targets);
    }

    public void attack(BattleUnitStack enemy) {
        enemy.damagedBy(calculateDamage(enemy), calculateAttack(enemy), enemy);
        enemy.rebuff(this);
    }

    public void defend() {
        addModifier(TemporaryModifiers.DEFEND);
    }

    public boolean canRebuff(BattleUnitStack enemy) {
        RebuffState rebuffState = RebuffState.STANDARD;

        for (UnitFeature feature : unit.getFeatures()) {
            rebuffState = feature.canRebuff(rebuffState, enemy.unit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        for (TemporaryModifier mod : modifiers) {
            rebuffState = mod.canRebuff(rebuffState, unit, enemy.unit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }


        for (UnitFeature feature : enemy.unit.getFeatures()) {
            rebuffState = feature.enemyCanRebuff(rebuffState, unit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        for (TemporaryModifier mod : enemy.modifiers) {
            rebuffState = mod.enemyCanRebuff(rebuffState, enemy.unit, unit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        if (rebuffState == RebuffState.MUST) {
            return true;
        } else {
            return !rebuffedThisRound;
        }

    }

    public double calculateInitiative() {
        double initiative = getInitiative();

        for (TemporaryModifier mod : modifiers) {
            initiative = mod.initiativeChange(initiative);
        }

        for (UnitFeature feature : getFeatures()) {
            initiative = feature.initiativeChange(initiative);
        }

        return initiative;
    }

    public double calculateInitiativeOnNextRound() {
        double initiative = getInitiative();

        for (TemporaryModifier mod: modifiers) {
            if (mod.getRounds() > 1) {
                initiative = mod.initiativeChange(initiative);
            }
        }

        for (UnitFeature feature : getFeatures()) {
            initiative = feature.initiativeChange(initiative);
        }

        return initiative;
    }

    public void endRound() {
        rebuffedThisRound = false;

        Stream<TemporaryModifier> mods = modifiers.stream();
        mods.forEach(TemporaryModifier::endRound);
        modifiers = mods.filter(TemporaryModifier::isWorking).collect(Collectors.toList());
    }

    public void addModifier(TemporaryModifier modifier) {
        modifiers.add(modifier);
    }

    public List<TemporaryModifier> getModifiers() {
        return Collections.unmodifiableList(modifiers);
    }

    public List<Skill> getSkills() {
        return unit.getSkills();
    }

    public int getAttack() {
        return unit.getAttack();
    }

    public Damage getDamage() {
        return unit.getDamage();
    }

    public int getDefence() {
        return unit.getDefence();
    }

    public List<UnitFeature> getFeatures() {
        return unit.getFeatures();
    }

    public double getInitiative() {
        return unit.getInitiative();
    }

    public int getLastUnitHipPoints() {
        return lastUnitHipPoints;
    }

    public int getStartCount() {
        return startCount;
    }
}
