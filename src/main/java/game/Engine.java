package game;

import model.Squad;
import model.race.Race;
import model.race.RaceType;
import model.unit.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Игровой движок.
 */
public class Engine {
    private static final Logger LOG = LoggerFactory.getLogger(Engine.class);
    private final Random random = new Random();

    /**
     * Количество
     */
    public static final int SQUADS_NUMBER = 3;


    /**
     * Стек, определяющий порядок ходов в игре
     */
    private final Deque<Squad> stack = new ArrayDeque<>();

    /**
     * Выбранные атакующие отряды
     */
    private final Squad[] activeSquads = new Squad[2];


    public Engine() {
        defineSquads();
    }

    public boolean isAllUnitsDead() {
        return stack.isEmpty();
    }

    /**
     * Создаёт отряды-участники и инициализирует стек
     */
    private void defineSquads() {
        RaceType raceType;
        for (int i = 0; i < SQUADS_NUMBER; i++) {
            raceType = random.nextBoolean() ? RaceType.GOOD : RaceType.EVIL;
            Squad squad = new Squad(getRandomRace(raceType));
            stack.push(squad);
        }
    }

    /**
     * Получает из стека атакующий и атакуемый отряды
     */
    private void prepareSquads() {
        activeSquads[0] = stack.pop();
        activeSquads[1] = stack.pop();
        LOG.info("Играют {} против {}", activeSquads[0].getRaceName(), activeSquads[1].getRaceNameGenitive());
    }

    /**
     * Выводит отряды играющих рас
     */
    public void printSquadsRaces() {
        System.out.println("Играют отряды: " + stack.stream().map(Squad::getRaceName).collect(Collectors.joining(", ")));
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
        int turnNumber = 1;
        prepareSquads();
        LOG.info("Ход № {} -----------------------------------------------------------------", turnNumber);
        doAttack();
        postProcessSquads();
        turnNumber++;
    }

    /**
     * Инициализирует атакующий и атакуемый юнит, после чего выполняет атаку
     */
    protected void doAttack() {
        Unit comrade, enemy;

        int src = random.nextInt(2);
        comrade = activeSquads[src].getActiveUnit();

        boolean isImprovement = comrade.getCurrentAction().isActionChangesState();

        /*
         *  выбираем отряд назначения.
         *  это своя раса, если выбрано улучшение,
         *  иначе - раса противника
         */
        int dst = isImprovement ? src : src ^ 1;
        enemy = activeSquads[dst].getRandomUnit();

        if (isImprovement) {
            LOG.info("Раса {} применила улучшение", activeSquads[src].getRaceName());
        } else {
            LOG.info("{} атакуют {}", activeSquads[src].getRaceName(), activeSquads[dst].getRaceNameGenitive());
        }
        comrade.executeAction(enemy);
    }

    /**
     * Удаляет юниты из отрядов, чей показатель здоровья равен нулю.
     */
    private void postProcessSquads() {
        for (int i = 0; i < activeSquads.length; i++) {
            Squad activeSquad = activeSquads[i];
            activeSquad.removeDeathUnits();

            if (activeSquad.getUnits().size() > 0) {
                stack.add(activeSquad);
            }
        }


    }
}
