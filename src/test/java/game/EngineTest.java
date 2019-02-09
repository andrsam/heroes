package game;

import model.Squad;
import model.actions.Attack;
import model.actions.Disease;
import model.race.races.Elves;
import model.race.races.Humans;
import model.race.races.Orcs;
import model.race.races.Undead;
import model.unit.Unit;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * Тесты игрового движка.
 */
public class EngineTest {
    private static final int WARRIOR_POSITION = 4;
    private static final int MAGE_POSITION = 0;

    private Engine engineMock = mock(Engine.class, withSettings().useConstructor().defaultAnswer(CALLS_REAL_METHODS));

    private Squad squadGood;
    private Squad squadEvil;

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

        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{mage, goodUnit});

        //выставляем действие-улучшение
        mage.setCurrentAction(mage.getActions().get(1));

        engineMock.makeTurn();

        assertTrue(goodUnit.isPrivileged());

        //проверяем, что первой должна ходить привелигированная группа
        assertTrue(squadGood.getRandomUnit().isPrivileged());

        //проверяем, что после атаки юнит вышел из прив. группцы
        Unit evilUnit = squadEvil.getRandomUnit();

        float health = evilUnit.getHealth();

        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{goodUnit, evilUnit});
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

        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{evilMage, goodUnit});

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

        Squad[] squads = {squadGood, squadEvil};
        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{mage, goodUnit});

        //Выставляем действие - улучшение
        mage.setCurrentAction(mage.getActions().get(1));

        engineMock.makeTurn();
        assertTrue(goodUnit.isPrivileged());


        Unit shaman = squadEvil.getUnits().get(0);
        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{shaman, goodUnit});

        //действие-проклятие
        shaman.setCurrentAction(shaman.getActions().get(1));

        engineMock.makeTurn();
        assertFalse(goodUnit.isPrivileged());

        //ходим хорошим юнитом
        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{goodUnit, shaman});
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

        when(engineMock.getActiveUnits()).thenReturn(new Unit[]{goodWarrior, evilUnit});

        engineMock.makeTurn();

        assertTrue(evilUnit.getHealth() == 0);
        assertEquals(7, squadEvil.getUnits().size());
    }
}