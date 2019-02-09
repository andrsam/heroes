package game;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import model.Squad;
import model.actions.Improvement;
import model.race.Race;
import model.race.RaceType;
import model.race.races.*;
import model.unit.Unit;

import java.util.Random;

/**
 * Игровой движок.
 */
@Getter
@Setter
@Slf4j
public class Engine {
    private Squad squadGood;
    private Squad squadEvil;
    private Random random = new Random();
    private int turnNumber = 1;

    public Engine() {
        squadGood = new Squad(getRandomRace(RaceType.GOOD));
        squadEvil = new Squad(getRandomRace(RaceType.EVIL));
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
        log.info("Ход №" + turnNumber + "-----------------------------------------------------------------");
        Unit[] units = getActiveUnits();

        Unit dstUnit = units[1];
        Unit srcUnit = units[0];

        srcUnit.executeAction(dstUnit);

        removeDeathUnits();
        turnNumber++;
    }

    /**
     * Возвращает массив из атакующего юнита, и атакуемого юнита.
     *
     * @return массив [атакующий юнит, атакуемый юнит]
     */
    protected Unit[] getActiveUnits() {

        Squad[] squads = new Squad[]{squadGood, squadEvil};

        int src = random.nextInt(2);
        Unit srcUnit = squads[src].getActiveUnit();

        /**
         *  выбираем отряд назначения.
         *  это своя раса, если выбрано улучшение,
         *  иначе - раса противника
         */
        boolean isImprovement = srcUnit.getCurrentAction() instanceof Improvement;
        int dst = isImprovement ? src : src ^ 1;
        Unit dstUnit = squads[dst].getRandomUnit();

        if (isImprovement) {
            log.info("Раса {} применила улучшение", squads[src].getRaceName());
        } else {
            log.info("{} атакуют {}", squads[src].getRaceName(), squads[dst].getRaceNameGenitive());
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
