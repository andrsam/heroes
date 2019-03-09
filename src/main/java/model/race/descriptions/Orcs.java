package model.race.descriptions;

import model.action.actions.Attack;
import model.action.actions.Damnation;
import model.action.actions.Improvement;
import model.race.Race;

/**
 * Описывает расу орков.
 */
public class Orcs extends Race {
    public Orcs() {
        this.setName("Орки");
        this.setNameGenitive("Орков");

        mage.setName("Шаман");
        mage.setNameGenitive("шамана");
        mage.setActions(new Improvement("Улучшение"), new Damnation("Проклятие"));

        shooter.setName("Лучник");
        shooter.setNameGenitive("лучника");
        shooter.setActions(new Attack("выстрелил из лука в", 3), new Attack("нанёс удар клинком", 2));

        warrior.setName("Гоблин");
        warrior.setNameGenitive("гоблина");
        warrior.setActions(new Attack("ударил дубиной", 20));
    }
}
