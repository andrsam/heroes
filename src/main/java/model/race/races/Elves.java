package model.race.races;

import model.actions.Attack;
import model.actions.Improvement;
import model.race.Race;

/**
 * Описывает расу эльфов.
 */
public class Elves extends Race {
    public Elves() {
        this.setName("Эльфы");
        this.setNameGenitive("Эльфов");

        mage.setName("Маг");
        mage.setNameGenitive("мага");

        mage.setActions(new Attack("нанес урон магией", 10), new Improvement("улучшение"));

        shooter.setName("Лучник");
        shooter.setNameGenitive("лучника");

        shooter.setActions(new Attack("выстрелил из лука в", 7),
                new Attack("атаковал", 3));

        warrior.setName("Воин");
        warrior.setNameGenitive("воина");

        warrior.setActions(new Attack("ударил мечом", 15));
    }
}
