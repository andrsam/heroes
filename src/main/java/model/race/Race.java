package model.race;

import lombok.Getter;
import lombok.Setter;
import model.unit.Unit;
import model.unit.UnitType;

@Setter
@Getter
public class Race {
    private String name;

    private String nameGenitive;

    protected Unit mage = new Unit(UnitType.MAGE);

    protected Unit shooter = new Unit(UnitType.SHOOTER);

    protected Unit warrior = new Unit(UnitType.WARRIOR);
}
