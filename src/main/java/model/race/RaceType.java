package model.race;

import model.race.descriptions.Elves;
import model.race.descriptions.Humans;
import model.race.descriptions.Orcs;
import model.race.descriptions.Undead;

/**
 * Тип расы.
 */
public enum RaceType {
    GOOD(new Race[]{new Elves(), new Humans()}),
    EVIL(new Race[]{new Orcs(), new Undead()});

    Race[] races;

    RaceType(Race[] races) {
        this.races = races;
    }

    public Race[] getRaces() {
        return races;
    }
}
