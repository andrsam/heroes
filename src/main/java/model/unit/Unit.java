package model.unit;

import model.action.Action;
import model.action.ActionProxy;
import model.action.actions.Improvement;

import java.util.*;

/**
 * Класс-описание персонажа.
 */
public class Unit {
    /**
     * Тип персонажа (маг, лучник, воин)
     */
    private UnitType unitType;

    /**
     * Наименование.
     */
    private String name;

    /**
     * Наименование в родительном падеже.
     */
    private String nameGenitive;

    /**
     * Показатель здоровья.
     */
    private float health = 100;

    /**
     * Список допустимых действий.
     */
    private List<Action> actions = new ArrayList<>();

    /**
     * Текущее действие.
     */
    private Action currentAction;

    /**
     * Улучшение(проклятие), наложенные на персонаж.
     */
    private Action magic;

    /**
     * Улучшение, ранее наложенное на персонаж.
     */
    private Action prevImprovement;

    /**
     * Устанавливает улучшение, запоминая старое.
     *
     * @param action накладываемое улучшение
     */
    public void setImprovement(Action action) {
        if (this.getMagic() != null) {
            prevImprovement = this.getMagic();
        }
        this.setMagic(action);
    }

    /**
     * Восстанавливает ранее снятое улучшение.
     */
    public void restoreMagic() {
        this.setMagic(prevImprovement);
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameGenitive() {
        return nameGenitive;
    }

    public void setNameGenitive(String nameGenitive) {
        this.nameGenitive = nameGenitive;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Action getMagic() {
        return magic;
    }

    public void setMagic(Action magic) {
        this.magic = magic;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Unit(UnitType unitType) {
        this.unitType = unitType;
    }

    /**
     * Копирующий конструктор.
     * Используется при создании персонажа заданной расы.
     *
     * @param unit - юнит, который необходимо скопировать
     */
    public Unit(Unit unit) {
        this.unitType = unit.unitType;
        this.name = unit.name;
        this.nameGenitive = unit.nameGenitive;
        this.actions = unit.getActions();
    }

    /**
     * Заполняет список действий персонажа.
     *
     * @param actions - список действий
     */
    public void setActions(Action... actions) {
        Collections.addAll(this.actions, actions);
    }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public Action getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction() {
        this.currentAction = actions.get(new Random().nextInt(actions.size()));
    }

    /**
     * Запускает действие по отношению к заданному персонажу.
     *
     * @param unit
     */
    public void executeAction(Unit unit) {
        if (currentAction == null) {
            setCurrentAction();
        }
        new ActionProxy(currentAction, this, unit).execute();
    }

    /**
     * Прооверяет, находится ли юнит в привелегированной группе
     *
     * @return true, если юнит в привелегированной группе
     */
    public boolean isPrivileged() {
        return magic != null && magic.getClass() == Improvement.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return unitType == unit.unitType &&
                name.equals(unit.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unitType, name);
    }
}
