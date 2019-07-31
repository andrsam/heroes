package game;

import model.Squad;
import model.action.actions.Attack;
import model.action.actions.Disease;
import model.race.descriptions.Elves;
import model.race.descriptions.Orcs;
import model.race.descriptions.Undead;
import model.unit.Unit;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Тесты игрового движка.
 */
public class EngineTest {
    /**
     * Позиция в отряде, на которой находится воин
     */
    private static final int WARRIOR_POSITION = 4;

    /**
     * Позиция в отряде, на которой находится маг
     */
    private static final int MAGE_POSITION = 0;

    private Engine engineMock = mock(Engine.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));

    {
        doCallRealMethod().when(engineMock).makeTurn();
    }

    /**
     * Тест наложения улучшения.
     */
    @Test
    public void testImprovement() {
        Squad squadGood = new Squad(new Elves());
        Squad squadEvil = new Squad(new Undead());

        Unit mage = squadGood.getUnits().get(MAGE_POSITION);
        Unit goodUnit = squadGood.getUnits().get(WARRIOR_POSITION);

        //выставляем действие-улучшение
        mage.setCurrentAction(mage.getActions().get(1));

        mage.executeAction(goodUnit);

        assertTrue(goodUnit.isPrivileged());

        //проверяем, что первой должна ходить привелигированная группа
        assertTrue(squadGood.getRandomUnit().isPrivileged());

        //проверяем, что после атаки юнит вышел из прив. группцы
        Unit evilUnit = squadEvil.getRandomUnit();

        //получаем здоровье юнита до атаки
        float health = evilUnit.getHealth();

        goodUnit.executeAction(evilUnit);

        //проверяем, что воину противника нанесен урон в 1,5 раза больше обычного
        Attack attack = (Attack) goodUnit.getActions().get(0);
        assertEquals(health - attack.getDamage() * attack.getImprovementRate(), evilUnit.getHealth(), 0);

        assertFalse(goodUnit.isPrivileged());
    }

    /**
     * Тест наложения недуга.
     */
    @Test
    public void testDisease() {
        Squad squadGood = new Squad(new Elves());
        Squad squadEvil = new Squad(new Undead());

        Unit evilMage = squadEvil.getUnits().get(MAGE_POSITION);
        Unit goodUnit = squadGood.getUnits().get(WARRIOR_POSITION);

        //выставляем действие-проклятие
        evilMage.setCurrentAction(evilMage.getActions().get(0));

        evilMage.executeAction(goodUnit);

        //проверяем, что воин проклят
        assertEquals(Disease.class, goodUnit.getMagic().getClass());
    }

    /**
     * Тест наложения проклятия.
     */
    @Test
    public void testDamnation() {
        Squad squadGood = new Squad(new Elves());
        Squad squadEvil = new Squad(new Orcs());

        Unit mage = squadGood.getUnits().get(0);
        Unit goodUnit = squadGood.getRandomUnit();

        //Выставляем действие - улучшение
        mage.setCurrentAction(mage.getActions().get(1));
        mage.executeAction(goodUnit);

        assertTrue(goodUnit.isPrivileged());

        Unit shaman = squadEvil.getUnits().get(0);

        //выставляем действие-проклятие
        shaman.setCurrentAction(shaman.getActions().get(1));

        shaman.executeAction(goodUnit);
        assertFalse(goodUnit.isPrivileged());

        //проверяем, что на следующем ходу проклятие снято
        goodUnit.executeAction(shaman);

        //проверяем, что уровень привелигированный
        assertTrue(goodUnit.isPrivileged());
    }
}