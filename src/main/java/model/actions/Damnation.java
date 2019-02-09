package model.actions;

import lombok.Getter;
import model.unit.Unit;

/**
 * Проклятие. Снимает улучшение с персонажа.
 */
@Getter
public class Damnation extends Action {
    private float improvementRate = 1f;

    public Damnation(String name) {
        super(name);
    }

    @Override
    public void execute(Unit unit) {
        setImprovement(unit);
    }


}
