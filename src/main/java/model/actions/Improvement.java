package model.actions;

import lombok.Getter;
import model.unit.Unit;

/**
 * Переводит персонаж в привелигированную группу.
 */
@Getter
public class Improvement extends Action {
    private float improvementRate = 1.5f;

    public Improvement(String name) {
        super(name);
    }

    @Override
    public void execute(Unit unit) {
        setImprovement(unit);
    }
}
