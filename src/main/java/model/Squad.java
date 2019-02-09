package model;

import lombok.Getter;
import lombok.Setter;
import model.race.Race;
import model.unit.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Описывает отряд.
 */
@Getter
@Setter
public class Squad {
    private List<Unit> units = new ArrayList<>();

    public final int MAGE_COUNT = 1;
    public final int SHOOTER_COUNT = 3;
    public final int WARRIOR_COUNT = 4;

    private Race race;

    public Squad(Race race) {
        this.race = race;

        IntStream.rangeClosed(1, MAGE_COUNT).forEach(i -> units.add(new Unit(race.getMage())));
        IntStream.rangeClosed(1, SHOOTER_COUNT).forEach(i -> units.add(new Unit(race.getShooter())));
        IntStream.rangeClosed(1, WARRIOR_COUNT).forEach(i ->  units.add(new Unit(race.getWarrior())));
    }

    /**
     * Случайным образом выбирает атакующий юнит
     * и устанавливает ему случайно выбранное действие.
     *
     * @return атакующий юнит
     */
    public Unit getActiveUnit() {
        Unit unit = getRandomUnit();
        unit.setCurrentAction();
        return unit;
    }

    /**
     * Удаляет из отряда юниты, чей показатель здоровья равен нулю.
     */
    public void removeDeathUnits() {
        units.removeIf(unit -> unit.getHealth() == 0);
    }

    /**
     * Случайным образом выбирает юнит из отряда.
     * Сначала выбирается юнит из привелигированной группы, иначе из обычной
     *
     * @return Случайно выбранный юнит
     */
    public Unit getRandomUnit() {
        List<Unit> privileged = units.stream().filter(Unit::isPrivileged).collect(Collectors.toList());
        boolean isPrivilegedEmpty = privileged.isEmpty();

        Random random = new Random();
        int i = isPrivilegedEmpty ? random.nextInt(units.size()) : random.nextInt(privileged.size());
        return isPrivilegedEmpty ? units.get(i) : privileged.get(i);
    }

    /**
     * Возвращает наименование расы, к которой принадлежит отряд.
     *
     * @return Наименование расы
     */
    public String getRaceName() {
        return this.race.getName();
    }

    /**
     * Возвращает наименование расы, к которой принадлежит отряд в родительном падеже.
     *
     * @return Наименование расы в родительном падеже
     */
    public String getRaceNameGenitive() {
        return this.getRace().getNameGenitive();
    }
}
