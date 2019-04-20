package model.action.actions;

import model.action.Action;
import model.unit.Unit;

/**
 * Недуг. Урон персонажа уменьшается на 50%.
 */
public class Disease implements Action {
    /**
     * Величина коэффициента (уменьшение силы урона персонажа противника на 50%)
     */
    private final float IMPROVEMENT_RATE = 0.5f;

    /**
     * Наименование действия
     */
    private String name;

    public Disease(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getImprovementRate() {
        return IMPROVEMENT_RATE;
    }

    @Override
    public boolean isActionChangesState() {
        return true;
    }

    @Override
    public void execute(Unit unit) {
        unit.setMagic(this);
    }
}
