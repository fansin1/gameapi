package org.fansin.gameapi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class BattleUnitStack extends UnitStack {

    private int mLastUnitHipPoints;
    private final int mStartCount;
    private boolean mRebuffedThisRound = false;
    private List<TemporaryModifier> mModifiers = new ArrayList<>();
    private Random mRandom = new Random();
    private Double mSmallRandom = smallRandom();
    private Double mNextSmallRandom = smallRandom();

    public BattleUnitStack(UnitStack unitStack) {
        super(unitStack.mUnit, unitStack.mCount);
        mStartCount = mCount;
        mLastUnitHipPoints = mUnit.getHitPoints();
    }

    private Double smallRandom() {
        return mRandom.nextDouble() / 100d;
    }

    private void damagedBy(int damage, int attack, BattleUnitStack enemy) {
        int defence = calculateDefence(enemy);

        if (attack > defence) {
            mLastUnitHipPoints -= damage * (1 + 0.05f * (attack - defence));
        } else if (attack < defence) {
            mLastUnitHipPoints -= damage / (1 + 0.05f * (defence - attack));
        } else {
            mLastUnitHipPoints -= damage;
        }

        while (mLastUnitHipPoints <= 0 && mCount > 0) {
            mCount--;
            mLastUnitHipPoints += mUnit.getHitPoints();
        }

        if (mCount == 0) mLastUnitHipPoints = 0;
    }

    private void rebuff(Battle battle, BattleUnitStack enemy) {
        if (canRebuff(battle, enemy)) {
            enemy.damagedBy(calculateDamage(enemy), calculateAttack(enemy), enemy);
            mRebuffedThisRound = true;
        }
    }

    public void healBy(int hitPoints) {
        mLastUnitHipPoints += hitPoints;

        while (mCount < getStartCount() && mLastUnitHipPoints > mUnit.getHitPoints()) {
            mLastUnitHipPoints -= mUnit.getHitPoints();
        }

        if (mLastUnitHipPoints > mUnit.getHitPoints())
            mLastUnitHipPoints = mUnit.getHitPoints();
    }

    public void useSkill(Battle battle, int skillIndex, BattleUnitStack... targets) {
        getSkills().get(skillIndex).cast(battle, this, targets);
    }

    public void attack(Battle battle, BattleUnitStack enemy) {
        for (BattleUnitStack busEnemy : calculateTargets(battle, enemy)) {
            busEnemy.damagedBy(calculateDamage(enemy), calculateAttack(enemy), enemy);
            busEnemy.rebuff(battle, this);
        }
    }

    public void defend() {
        addModifier(TemporaryModifiers.DEFEND);
    }

    public boolean canRebuff(Battle battle, BattleUnitStack enemy) {
        RebuffState rebuffState = RebuffState.STANDARD;

        if (battle.currentArmy().getStacks().contains(this))
            return false;

        for (UnitFeature feature : mUnit.getFeatures()) {
            rebuffState = feature.canRebuff(rebuffState, enemy.mUnit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        for (TemporaryModifier mod : mModifiers) {
            rebuffState = mod.canRebuff(rebuffState, mUnit, enemy.mUnit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }


        for (UnitFeature feature : enemy.mUnit.getFeatures()) {
            rebuffState = feature.enemyCanRebuff(rebuffState, mUnit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        for (TemporaryModifier mod : enemy.mModifiers) {
            rebuffState = mod.enemyCanRebuff(rebuffState, enemy.mUnit, mUnit);
            if (rebuffState == RebuffState.CANT) {
                return false;
            }
        }

        if (rebuffState == RebuffState.MUST) {
            return true;
        } else {
            return !mRebuffedThisRound;
        }

    }

    public double calculateInitiative() {
        double initiative = getInitiative();

        for (TemporaryModifier mod : mModifiers) {
            initiative = mod.initiativeChange(initiative);
        }

        for (UnitFeature feature : getFeatures()) {
            initiative = feature.initiativeChange(initiative);
        }

        return initiative + mSmallRandom;
    }

    public double calculateInitiativeOnNextRound() {
        double initiative = getInitiative();

        for (TemporaryModifier mod: mModifiers) {
            if (mod.getRounds() > 1) {
                initiative = mod.initiativeChange(initiative);
            }
        }

        for (UnitFeature feature : getFeatures()) {
            initiative = feature.initiativeChange(initiative);
        }

        return initiative + mNextSmallRandom;
    }

    public int calculateAttack(BattleUnitStack enemy) {
        int attack = getAttack();

        for (TemporaryModifier mod : mModifiers) {
            attack = mod.attackChange(attack, mUnit, enemy.mUnit);
        }

        for (UnitFeature feature : mUnit.getFeatures()) {
            attack = feature.attackChange(attack, enemy.mUnit);
        }


        for (TemporaryModifier mod : enemy.mModifiers) {
            attack = mod.enemyAttackChange(attack, enemy.mUnit, mUnit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            attack = feature.enemyAttackChange(attack, mUnit);
        }

        return attack;
    }

    public int calculateDamage(BattleUnitStack enemy) {
        int damage = getDamage().getRandom();

        for (TemporaryModifier mod : mModifiers) {
            damage = mod.damageChange(damage, mUnit, enemy.mUnit);
        }

        for (UnitFeature feature : mUnit.getFeatures()) {
            damage = feature.damageChange(damage, enemy.mUnit);
        }


        for (TemporaryModifier mod : enemy.mModifiers) {
            damage = mod.enemyDamageChange(damage, enemy.mUnit, mUnit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            damage = feature.enemyDamageChange(damage, mUnit);
        }

        return damage * mCount;
    }

    public int calculateDefence(BattleUnitStack enemy) {
        int defence = getDefence();

        for (TemporaryModifier mod : mModifiers) {
            defence = mod.defenceChange(defence, mUnit, enemy.mUnit);
        }

        for (UnitFeature feature : mUnit.getFeatures()) {
            defence = feature.defenceChange(defence, enemy.mUnit);
        }

        for (TemporaryModifier mod : enemy.mModifiers) {
            defence = mod.enemyDefenceChange(defence, enemy.mUnit, mUnit);
        }

        for (UnitFeature feature : enemy.getFeatures()) {
            defence = feature.enemyDefenceChange(defence, mUnit);
        }

        return defence;
    }

    public List<BattleUnitStack> calculateTargets(Battle battle, BattleUnitStack enemy) {
        List<BattleUnitStack> enemies = new ArrayList<>();
        enemies.add(enemy);

        for (UnitFeature feature : getFeatures()) {
            enemies = feature.changeTargets(battle, enemies);
        }

        for (TemporaryModifier mod : getModifiers()) {
            enemies = mod.changeTargets(battle, enemies);
        }

        return enemies;
    }

    public void endRound() {
        mRebuffedThisRound = false;

        Supplier<Stream<TemporaryModifier>> modSupplier = () -> mModifiers.stream();
        modSupplier.get().forEach(TemporaryModifier::endRound);
        mModifiers = modSupplier.get().filter(TemporaryModifier::isWorking).collect(Collectors.toList());
        mSmallRandom = mNextSmallRandom;
        mNextSmallRandom = smallRandom();
    }

    public void addModifier(TemporaryModifier modifier) {
        mModifiers.add(modifier);
    }

    public List<TemporaryModifier> getModifiers() {
        return Collections.unmodifiableList(mModifiers);
    }

    public List<Skill> getSkills() {
        return mUnit.getSkills();
    }

    public int getAttack() {
        return mUnit.getAttack();
    }

    public Damage getDamage() {
        return mUnit.getDamage();
    }

    public int getDefence() {
        return mUnit.getDefence();
    }

    public List<UnitFeature> getFeatures() {
        return mUnit.getFeatures();
    }

    public double getInitiative() {
        return mUnit.getInitiative();
    }

    public int getLastUnitHipPoints() {
        return mLastUnitHipPoints;
    }

    public int getStartCount() {
        return mStartCount;
    }

    public Unit getUnit() {
        return mUnit;
    }
}
