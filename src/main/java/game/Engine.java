package game;

import model.Squad;
import model.action.actions.Improvement;
import model.race.Race;
import model.race.RaceType;
import model.race.descriptions.Elves;
import model.race.descriptions.Humans;
import model.race.descriptions.Orcs;
import model.race.descriptions.Undead;
import model.unit.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Игровой движок.
 */
public class Engine {
    private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

    private final Squad squadGood;
    private final Squad squadEvil;
    private final Random random = new Random();

    private int turnNumber = 1;

    public Engine() {
        squadGood = new Squad(getRandomRace(RaceType.GOOD));
        squadEvil = new Squad(getRandomRace(RaceType.EVIL));
    }

    public Squad getSquadGood() {
        return squadGood;
    }

    public Squad getSquadEvil() {
        return squadEvil;
    }

    /**
     * Возвращает случайным образом выбранную расу.
     */
    private Race getRandomRace(RaceType raceType) {
        Race[] races = raceType.equals(RaceType.GOOD) ? new Race[]{new Elves(), new Humans()} : new Race[]{new Orcs(), new Undead()};
        return races[random.nextInt(2)];
    }

    /**
     * Выполняет игровой ход.
     */
    public void makeTurn() {
        LOG.info("Ход №" + turnNumber + "-----------------------------------------------------------------");
        Unit[] units = getActiveUnits();

        Unit dstUnit = units[1];
        Unit srcUnit = units[0];

        srcUnit.executeAction(dstUnit);

        removeDeathUnits();
        turnNumber++;
    }

    /**
     * Возвращает массив из атакующего юнита и атакуемого юнита
     * для выполнения хода
     *
     * @return массив [атакующий юнит, атакуемый юнит]
     */
    protected Unit[] getActiveUnits() {

        Squad[] squads = new Squad[]{squadGood, squadEvil};

        int src = random.nextInt(2);
        Unit srcUnit = squads[src].getActiveUnit();

        /*
         *  выбираем отряд назначения.
         *  это своя раса, если выбрано улучшение,
         *  иначе - раса противника
         */
        boolean isImprovement = srcUnit.getCurrentAction() instanceof Improvement;

        int dst = isImprovement ? src : src ^ 1;
        Unit dstUnit = squads[dst].getRandomUnit();

        if (isImprovement) {
            LOG.info("Раса {} применила улучшение", squads[src].getRaceName());
        } else {
            LOG.info("{} атакуют {}", squads[src].getRaceName(), squads[dst].getRaceNameGenitive());
        }
        return new Unit[]{srcUnit, dstUnit};
    }

    /**
     * Удаляет юниты из отрядов, чей показатель здоровья равен нулю.
     */
    private void removeDeathUnits() {
        squadGood.removeDeathUnits();
        squadEvil.removeDeathUnits();
    }
}
