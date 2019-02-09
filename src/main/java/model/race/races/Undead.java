package model.race.races;

import model.actions.Attack;
import model.actions.Disease;
import model.race.Race;

/**
 * Описывает расу нежити.
 */
public class Undead extends Race {
    public Undead() {
        this.setName("Нежить");
        this.setNameGenitive("Нежити");

        mage.setName("Некромант");
        mage.setNameGenitive("некроманту");
        mage.setActions(new Disease("наслал недуг"), new Attack("атаковал", 5));

        shooter.setName("Охотник");
        shooter.setNameGenitive("охотника");
        shooter.setActions(new Attack("выстрелил из лука в", 4), new Attack("атаковал", 2));

        warrior.setName("Зомби");
        warrior.setNameGenitive("зомби");
        warrior.setActions(new Attack("ударил мечом", 18));
    }
}
