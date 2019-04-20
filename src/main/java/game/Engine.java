package game;

import model.Squad;
import model.race.Race;
import model.race.RaceType;
import model.unit.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Игровой движок.
 */
public class Engine {
    private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

    /**
     * Отряд положительеных героев - эльфов или людей
     */
    private final Squad squadGood;

    /**
     * Отряд отрицательных героев - орков или нежити
     */
    private final Squad squadEvil;

    private final Random random = new Random();

    /**
     * Атакующий юнит
     */
    private Unit comrade;

    /**
     * Атакуемый юнит
     */
    private Unit enemy;

    /**
     * Счетчик количества ходов
     */
    private int turnNumber = 1;

    public Engine() {
        squadGood = new Squad(getRandomRace(RaceType.GOOD));
        squadEvil = new Squad(getRandomRace(RaceType.EVIL));
    }

    public void setComrade(Unit comrade) {
        this.comrade = comrade;
    }

    public void setEnemy(Unit enemy) {
        this.enemy = enemy;
    }

    /**
     * Возвращает отряд положительных героев
     *
     * @return объект типа Squad
     */
    public Squad getSquadGood() {
        return squadGood;
    }

    /**
     * Возвращает отряд отрицательных героев
     *
     * @return объект типа Squad
     */
    public Squad getSquadEvil() {
        return squadEvil;
    }

    /**
     * Возвращает случайным образом выбранную расу.
     */
    private Race getRandomRace(RaceType raceType) {
        Race[] races = raceType.getRaces();
        return races[random.nextInt(2)];
    }

    /**
     * Выполняет игровой ход.
     */
    public void makeTurn() {
        LOG.info("Ход № {} -----------------------------------------------------------------", turnNumber);
        setActiveUnits();
        comrade.executeAction(enemy);
        removeDeathUnits();
        turnNumber++;
    }

    /**
     * Возвращает массив из атакующего юнита и атакуемого юнита
     * для выполнения хода
     *
     * @return массив [атакующий юнит, атакуемый юнит]
     */
    protected void setActiveUnits() {
        Squad[] squads = new Squad[]{squadGood, squadEvil};

        int src = random.nextInt(2);
        comrade = squads[src].getActiveUnit();

        boolean isImprovement = comrade.getCurrentAction().isActionChangesState();

        /*
         *  выбираем отряд назначения.
         *  это своя раса, если выбрано улучшение,
         *  иначе - раса противника
         */
        int dst = isImprovement ? src : src ^ 1;
        enemy = squads[dst].getRandomUnit();

        if (isImprovement) {
            LOG.info("Раса {} применила улучшение", squads[src].getRaceName());
        } else {
            LOG.info("{} атакуют {}", squads[src].getRaceName(), squads[dst].getRaceNameGenitive());
        }
    }

    /**
     * Удаляет юниты из отрядов, чей показатель здоровья равен нулю.
     */
    private void removeDeathUnits() {
        squadGood.removeDeathUnits();
        squadEvil.removeDeathUnits();
    }
}
