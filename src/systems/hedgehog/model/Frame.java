package systems.hedgehog.model;

import java.util.Optional;
import java.util.OptionalInt;

public class Frame {

    private Optional<BowlingThrow> firstThrow;
    private Optional<BowlingThrow> secondThrow;

    public Optional<BowlingThrow> getFirstThrow() {
        return firstThrow;
    }

    public void setFirstThrow(Optional<BowlingThrow> firstThrow) {
        this.firstThrow = firstThrow;
    }

    public Optional<BowlingThrow> getSecondThrow() {
        return secondThrow;
    }

    public void setSecondThrow(Optional<BowlingThrow> secondThrow) {
        this.secondThrow = secondThrow;
    }

    public OptionalInt getSum() {
        return OptionalInt.of(getFirstThrow().get().getScores().orElse(0) + getSecondThrow().get().getScores().orElse(0));
    }

    /**
     * @return <code>true</code>, if succeeded
     */
    public Boolean setNextThrow(Optional<BowlingThrow> nextThrow) {

        if(!firstThrow.get().getScores().isPresent()) {
            if(nextThrow.get().getScores().getAsInt() == BowlingThrow.MAX_SCORES) {
                nextThrow.get().setBonus(Optional.of(Bonus.STRIKE));
                secondThrow = generateExtraStrikeBowlingThrow();
            } else if(nextThrow.get().getScores().getAsInt() == 0) {
                nextThrow.get().setBonus(Optional.of(Bonus.MISS));
            }
            firstThrow = nextThrow;
            return true;
        } else if(!secondThrow.get().getScores().isPresent()) {
            if((firstThrow.get().getScores().getAsInt() + nextThrow.get().getScores().getAsInt()) == BowlingThrow.MAX_SCORES) {
                nextThrow.get().setBonus(Optional.of(Bonus.SPARE));
            } else if(nextThrow.get().getScores().getAsInt() == 0) {
                nextThrow.get().setBonus(Optional.of(Bonus.MISS));
            }
            secondThrow = nextThrow;
            return true;
        }
        return false;
    }

    private Optional<BowlingThrow> generateExtraStrikeBowlingThrow() {
        BowlingThrow extraBowlingThrow = new BowlingThrow();
        extraBowlingThrow.setScores(OptionalInt.empty());
        extraBowlingThrow.setBonus(Optional.of(Bonus.STRIKE));
        return Optional.of(extraBowlingThrow);
    }

    public Optional<BowlingThrow> getThrow(Integer throwNumber) {

        if(throwNumber.equals(1)) {
            return firstThrow;
        }
        return secondThrow;
    }

    public Boolean isFinished() {
        return firstThrow.get().getScores().isPresent() && secondThrow.get().getScores().isPresent();
    }
}
