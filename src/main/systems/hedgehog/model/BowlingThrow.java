package systems.hedgehog.model;

import java.util.Optional;
import java.util.OptionalInt;

public class BowlingThrow {

    public static final int MAX_SCORES = 10;

    private OptionalInt score;
    private Optional<Bonus> bonus;

    public OptionalInt getScore() {
        return score;
    }

    public void setScore(OptionalInt score) {
        this.score = score;
    }

    public Optional<Bonus> getBonus() {
        return bonus;
    }

    public void setBonus(Optional<Bonus> bonus) {
        this.bonus = bonus;
    }

    public boolean isToBeSet() {
        return !getScore().isPresent();
    }

    @Override
    public String toString() {
        if(score.isPresent()) {
            return bonus.isPresent() ? bonus.get().getSign() : String.valueOf(score.getAsInt());
        }
        return Messages.EMPTY;
    }
}
