package game;

import model.Squad;
import model.race.races.Elves;
import model.unit.Unit;
import model.unit.UnitType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Тест работы с отрядом
 */
public class SquadTest {
    /**
     * Тест создания отряда
     */
    @Test
    public void generateSquad() {
        Squad squad = new Squad(new Elves());
        List<Unit> elves = squad.getUnits();

        assertEquals(squad.MAGE_COUNT, getUnitTypeCount(elves, UnitType.MAGE));
        assertEquals(squad.SHOOTER_COUNT, getUnitTypeCount(elves, UnitType.SHOOTER));
        assertEquals(squad.WARRIOR_COUNT, getUnitTypeCount(elves, UnitType.WARRIOR));
    }

    /**
     * Получает количество юнитов заданного типа.
     *
     * @param units
     * @param unitType
     * @return
     */
    private long getUnitTypeCount(List<Unit> units, UnitType unitType) {
        return units.stream().filter(e -> e.getUnitType().equals(unitType)).count();
    }
}