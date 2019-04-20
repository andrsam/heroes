package game;

import model.Squad;
import model.action.actions.Attack;
import model.action.actions.Disease;
import model.race.descriptions.Elves;
import model.race.descriptions.Humans;
import model.race.descriptions.Orcs;
import model.race.descriptions.Undead;
import model.unit.Unit;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;
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
     * Тест создания отрядов.
     */
    @Test
    public void createSquads() {
        Engine engine = new Engine();
        assertThat(engine.getSquadGood().getRace().getClass(), isOneOf(Elves.class, Humans.class));
        assertThat(engine.getSquadEvil().getRace().getClass(), isOneOf(Orcs.class, Undead.class));
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

        doAnswer((i) -> {
            engineMock.setComrade(mage);
            engineMock.setEnemy(goodUnit);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();

        //выставляем действие-улучшение
        mage.setCurrentAction(mage.getActions().get(1));

        engineMock.makeTurn();

        assertTrue(goodUnit.isPrivileged());

        //проверяем, что первой должна ходить привелигированная группа
        assertTrue(squadGood.getRandomUnit().isPrivileged());

        //проверяем, что после атаки юнит вышел из прив. группцы
        Unit evilUnit = squadEvil.getRandomUnit();

        float health = evilUnit.getHealth();

        doAnswer((i) -> {
            engineMock.setComrade(goodUnit);
            engineMock.setEnemy(evilUnit);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();
        engineMock.makeTurn();

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


        doAnswer((i) -> {
            engineMock.setComrade(evilMage);
            engineMock.setEnemy(goodUnit);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();

        //выставляем действие-проклятие
        evilMage.setCurrentAction(evilMage.getActions().get(0));
        engineMock.makeTurn();

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

        doAnswer((i) -> {
            engineMock.setComrade(mage);
            engineMock.setEnemy(goodUnit);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();

        //Выставляем действие - улучшение
        mage.setCurrentAction(mage.getActions().get(1));

        engineMock.makeTurn();
        assertTrue(goodUnit.isPrivileged());


        Unit shaman = squadEvil.getUnits().get(0);

        doAnswer((i) -> {
            engineMock.setComrade(shaman);
            engineMock.setEnemy(goodUnit);
            return null;
        }).when(engineMock).setActiveUnits();
        engineMock.setActiveUnits();

        //действие-проклятие
        shaman.setCurrentAction(shaman.getActions().get(1));

        engineMock.makeTurn();
        assertFalse(goodUnit.isPrivileged());

        //ходим хорошим юнитом
        doAnswer((i) -> {
            engineMock.setComrade(goodUnit);
            engineMock.setEnemy(shaman);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();
        engineMock.makeTurn();

        //проверяем, что уровень привелигированный
        assertTrue(goodUnit.isPrivileged());
    }

    /**
     * Тест причинения смертельного урона.
     */
    @Test
    public void testDeath() {
        Squad squadGood = engineMock.getSquadGood();
        Squad squadEvil = engineMock.getSquadEvil();

        Unit goodWarrior = squadGood.getUnits().get(WARRIOR_POSITION);

        //выставляем уровень урона от атаки = 100
        Attack attack = (Attack) goodWarrior.getActions().get(0);
        attack.setDamage(100);

        Unit evilUnit = squadEvil.getRandomUnit();

        doAnswer((i) -> {
            engineMock.setComrade(goodWarrior);
            engineMock.setEnemy(evilUnit);
            return null;
        }).when(engineMock).setActiveUnits();

        engineMock.setActiveUnits();
        engineMock.makeTurn();

        assertTrue(evilUnit.getHealth() == 0);
        assertEquals(7, squadEvil.getUnits().size());
    }
}