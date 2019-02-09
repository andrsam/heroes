package model.unit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.actions.Action;
import model.actions.ActionProxy;
import model.actions.Improvement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Класс-описание персонажа.
 */
@Getter
@Setter
@NoArgsConstructor
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
     * Магия.
     */
    private Action magic;

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

    public void setCurrentAction() {
        this.currentAction = actions.size() == 1 ? actions.get(0) : actions.get(new Random().nextInt(actions.size()));
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
     * Прооверяет, наложена ли магия на воина или нет.
     *
     * @return true, если на воина наложена магия
     */
    public boolean isPrivileged() {
        return magic != null && magic.getClass() == Improvement.class;
    }
}
