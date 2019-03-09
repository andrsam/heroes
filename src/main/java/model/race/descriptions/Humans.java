package model.race.descriptions;


import model.action.actions.Attack;
import model.action.actions.Improvement;
import model.race.Race;

/**
 * Описывает расу людей.
 */
public class Humans extends Race {
    public Humans() {
        this.setName("Люди");
        this.setNameGenitive("Людей");

        mage.setName("Маг");
        mage.setNameGenitive("мага");
        mage.setActions(new Attack("нанёс урон магией", 4), new Improvement("применил улучшение"));

        shooter.setName("Арбалетчик");
        shooter.setNameGenitive("арбалетчика");
        shooter.setActions(new Attack("выстрелил из арбалета в", 5),
                new Attack("атаковал ", 3));

        warrior.setName("Воин");
        warrior.setNameGenitive("воина");
        warrior.setActions(new Attack("ударил мечом", 18));
    }
}
