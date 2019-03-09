package model.action.actions;

import model.action.Action;
import model.unit.Unit;

/**
 * Проклятие. Снимает улучшение с персонажа.
 */
public class Damnation implements Action {
    /**
     * Величина коэффициента (сбрасывается до 1)
     */
    private final float IMPROVEMENT_RATE = 1f;

    /**
     * Наименование действия
     */
    private String name;

    @Override
    public float getImprovementRate() {
        return IMPROVEMENT_RATE;
    }

    @Override
    public String getName() {
        return name;
    }

    public Damnation(String name) {
        this.name = name;
    }

    @Override
    public void execute(Unit unit) {
        unit.setImprovement(this);
    }


}
