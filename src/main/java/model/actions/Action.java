package model.actions;

import model.unit.Unit;

/**
 * Описывает общее поведение всех действий.
 */
public abstract class Action {
    /**
     * Наименование действия
     */
    private String name;

    /**
     * Хранит улучшение, ранее наложенное на персонаж.
     */
    private Action prevImprovement;

    /**
     * Коэффициент, предназначенный для увеличения(уменьшения) наносимого урона.
     */
    private float improvementRate;

    public Action(String name) {
        this.name = name;
    }

    /**
     * Запускает действие.
     *
     * @param unit - Юнит, к которому следует  применить действие
     */
    abstract public void execute(Unit unit);

    /**
     * Восстанавливает ранее снятое улучшение.
     *
     * @param unit - юнит, к которому улучшение было применено
     */
    public void restoreMagic(Unit unit) {
        unit.setMagic(prevImprovement);
    }

    public String getName() {
        return name;
    }

    public float getImprovementRate() {
        return improvementRate;
    }

    public void setImprovementRate(float improvementRate) {
        this.improvementRate = improvementRate;
    }

    public void setImprovement(Unit unit) {
        if (unit.getMagic() != null) {
            prevImprovement = unit.getMagic();
        }
        unit.setMagic(this);
    }
}
