package model.action.actions;

import model.action.Action;
import model.unit.Unit;

/**
 * Действие-атака.
 */
public class Attack implements Action {
    /**
     * Величина наносимого урона.
     */
    private int damage;

    /**
     * Величина коэффициента
     */
    private float improvementRate = 1;

    /**
     * Наименование действия
     */
    private final String name;

    public Attack(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getImprovementRate() {
        return improvementRate;
    }

    public void setImprovementRate(float improvementRate) {
        this.improvementRate = improvementRate;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public boolean isActionChangesState() {
        return false;
    }

    @Override
    public void execute(Unit unit) {
        unit.setHealth(unit.getHealth() - (damage * improvementRate));
    }
}
