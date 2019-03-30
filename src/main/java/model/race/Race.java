package model.race;

import model.unit.Unit;
import model.unit.UnitType;

/**
 * Описывает расу.
 * Каждая раса включает в себя лучника, мага и воина
 */
public class Race {

    /**
     * Наименование расы.
     */
    private String name;

    /**
     * Наименование расы в родительном падеже.
     */
    private String nameGenitive;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameGenitive() {
        return nameGenitive;
    }

    public void setNameGenitive(String nameGenitive) {
        this.nameGenitive = nameGenitive;
    }

    /**
     * Маг.
     */
    protected final Unit mage = new Unit(UnitType.MAGE);

    /**
     * Лучник.
     */
    protected final Unit shooter = new Unit(UnitType.SHOOTER);

    /**
     * Воин.
     */
    protected final Unit warrior = new Unit(UnitType.WARRIOR);

    public Unit getMage() {
        return mage;
    }

    public Unit getShooter() {
        return shooter;
    }

    public Unit getWarrior() {
        return warrior;
    }

    @Override
    public String toString() {
        return "Race{" + "name='" + name + '\'' + '}';
    }
}
