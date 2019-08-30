package systems.hedgehog.model;

import systems.hedgehog.factory.ScoreboardFactory;

import java.util.Optional;
import java.util.OptionalInt;

public class Frame {

    private static final int FIRST_THROW = 1;
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

    public OptionalInt getRawSum() {
        return OptionalInt.of(getFirstThrow().get().getScore().orElse(0) + getSecondThrow().get().getScore().orElse(0));
    }

    /**
     * @return added bowling throw
     */
    public Optional<BowlingThrow> setNextThrow(Optional<BowlingThrow> nextThrow) {

        if(isMiss(nextThrow)) {
            nextThrow.get().setBonus(Optional.of(Bonus.MISS));
        }
        if(firstThrow.get().isToBeSet()) {
            return setAndReturnFirstThrow(nextThrow);
        }
        if(secondThrow.get().isToBeSet()) {
            return setAndReturnSecondThrow(nextThrow);
        }
        return Optional.empty();
    }

    private Optional<BowlingThrow> setAndReturnFirstThrow(Optional<BowlingThrow> nextThrow) {
        if(isStrike(nextThrow)) {
            nextThrow.get().setBonus(Optional.of(Bonus.STRIKE));
            secondThrow = Optional.of(ScoreboardFactory.generateBlankBowlingThrow());
        }
        firstThrow = nextThrow;
        return nextThrow;
    }

    private Optional<BowlingThrow> setAndReturnSecondThrow(Optional<BowlingThrow> nextThrow) {
        if(isOutOfRange(nextThrow)) {
            return Optional.of(ScoreboardFactory.generateErrorBowlingThrow());
        }
        if(isSpare(nextThrow)) {
            nextThrow.get().setBonus(Optional.of(Bonus.SPARE));
        }
        secondThrow = nextThrow;
        return nextThrow;
    }

    protected boolean isMiss(Optional<BowlingThrow> nextThrow) {
        return nextThrow.get().getScore().getAsInt() == 0;
    }

    protected boolean isStrike(Optional<BowlingThrow> nextThrow) {
        return nextThrow.get().getScore().getAsInt() == BowlingThrow.MAX_SCORES;
    }

    protected boolean isSpare(Optional<BowlingThrow> nextThrow) {
        return isSpare(firstThrow, nextThrow);
    }
    protected boolean isSpare(Optional<BowlingThrow> currentThrow, Optional<BowlingThrow> nextThrow) {
        return currentThrow.get().getScore().getAsInt() + nextThrow.get().getScore().getAsInt() == BowlingThrow.MAX_SCORES;
    }

    protected boolean isOutOfRange(Optional<BowlingThrow> nextThrow) {
        return firstThrow.get().getScore().getAsInt() + nextThrow.get().getScore().getAsInt() > BowlingThrow.MAX_SCORES;
    }

    public Optional<BowlingThrow> getThrow(Integer throwNumber) {
        return throwNumber.equals(FIRST_THROW) ? firstThrow : secondThrow;
    }

    public Boolean isFinished() {
        return firstThrow.get().getScore().isPresent() && secondThrow.get().getScore().isPresent();
    }

}
