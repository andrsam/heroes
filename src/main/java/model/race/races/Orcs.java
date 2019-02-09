package model.race.races;

import model.actions.Attack;
import model.actions.Damnation;
import model.actions.Improvement;
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
