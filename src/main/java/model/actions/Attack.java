package model.actions;

import lombok.Getter;
import lombok.Setter;
import model.unit.Unit;

/**
 * Действие-атака.
 */
@Setter
@Getter
public class Attack extends Action {
    /**
     *  Величина наносимого урона.
     */
    private int damage;
    private float improvementRate = 1;

    public Attack(String name, int damage) {
        super(name);
        this.damage = damage;
    }

    public void execute(Unit unit) {
             unit.setHealth(unit.getHealth() - (damage * improvementRate));
    }
}
