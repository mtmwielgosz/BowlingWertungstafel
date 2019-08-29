package systems.hedgehog.model;

import java.util.Optional;
import java.util.OptionalInt;

public class BowlingThrow {

    public static final Integer MAX_SCORES = 10;

    private OptionalInt scores;
    private Optional<Bonus> bonus;

    public OptionalInt getScores() {
        return scores;
    }

    public void setScores(OptionalInt scores) {
        this.scores = scores;
    }

    public Optional<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(Optional<Bonus> bonus) {
        this.bonus = bonus;
    }
}
