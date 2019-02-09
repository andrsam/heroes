package model.actions;

import lombok.Getter;
import model.unit.Unit;

/**
 * Недуг. Урон персонажа уменьшается на 50%.
 */
@Getter
public class Disease extends Action {
    private float improvementRate = 0.5f;

    public Disease(String name) {
        super(name);
    }

    @Override
    public void execute(Unit unit) {
        unit.setMagic(this);
    }
}
