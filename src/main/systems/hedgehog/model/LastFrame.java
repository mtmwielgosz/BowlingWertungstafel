package systems.hedgehog.model;

import systems.hedgehog.factory.ScoreboardFactory;

import java.util.Optional;
import java.util.OptionalInt;

public class LastFrame extends Frame {

    private static final int LAST_THROW = 3;
    private Optional<BowlingThrow> thirdThrow;

    public void setThirdThrow(Optional<BowlingThrow> thirdThrow) {
        this.thirdThrow = thirdThrow;
    }

    @Override
    public Optional<BowlingThrow> getThrow(Integer throwNumber) {
        return throwNumber.equals(LAST_THROW) ? thirdThrow : super.getThrow(throwNumber);
    }

    @Override
    public OptionalInt getRawSum() {
        return OptionalInt.of(super.getRawSum().orElse(0) + thirdThrow.get().getScore().orElse(0));
    }

    @Override
    public Boolean isFinished() {
        return super.isFinished() && thirdThrow.get().getScore().isPresent();
    }

    @Override
    public Optional<BowlingThrow> setNextThrow(Optional<BowlingThrow> nextThrow) {

        if (nextThrow.get().getScore().getAsInt() == 0) {
            nextThrow.get().setBonus(Optional.of(Bonus.MISS));
        }
        if (isStrike(nextThrow)) {
            nextThrow.get().setBonus(Optional.of(Bonus.STRIKE));
        }
        if (getFirstThrow().get().isToBeSet()) {
            return setAndReturnFirstThrow(nextThrow);
        }
        if (getSecondThrow().get().isToBeSet()) {
            return setAndReturnSecondThrow(nextThrow);
        }
        if (thirdThrow.get().isToBeSet()) {
            return setLastThrowAndEndGame(nextThrow);
        }
        return Optional.empty();
    }

    private Optional<BowlingThrow> setAndReturnFirstThrow(Optional<BowlingThrow> nextThrow) {

        setFirstThrow(nextThrow);
        return nextThrow;
    }

    private Optional<BowlingThrow> setAndReturnSecondThrow(Optional<BowlingThrow> nextThrow) {

        if(isOutOfRange(getFirstThrow(), nextThrow)) {
            return Optional.of(ScoreboardFactory.generateErrorBowlingThrow());
        }
        if (isSpare(nextThrow)) {
            nextThrow.get().setBonus(Optional.of(Bonus.SPARE));
        }
        setSecondThrow(nextThrow);
        if (lastThrowNotDeserved()) {
            return setMissLastThrowAndEndGame();
        }
        return nextThrow;
    }

    private Optional<BowlingThrow> setLastThrowAndEndGame(Optional<BowlingThrow> nextThrow) {

        if (!isSpare(getSecondThrow()) && !isStrike(getSecondThrow())) {
            if(isOutOfRange(getSecondThrow(), nextThrow)) {
                return Optional.of(ScoreboardFactory.generateErrorBowlingThrow());
            }
            if(isSpare(getSecondThrow(), nextThrow)) {
                nextThrow.get().setBonus(Optional.of(Bonus.SPARE));
            }
        }
        thirdThrow = nextThrow;
        return Optional.empty();
    }

    private boolean lastThrowNotDeserved() {
        return getFirstThrow().get().getScore().getAsInt() + getSecondThrow().get().getScore().getAsInt() < BowlingThrow.MAX_SCORES;
    }

    private Optional<BowlingThrow> setMissLastThrowAndEndGame() {
        thirdThrow = Optional.of(ScoreboardFactory.generateMissBowlingThrow());
        return Optional.empty();
    }

    private boolean isOutOfRange(Optional<BowlingThrow> currentThrow, Optional<BowlingThrow> nextThrow) {
        return !isStrike(currentThrow) &&
                (currentThrow.get().getScore().getAsInt() + nextThrow.get().getScore().getAsInt()) > BowlingThrow.MAX_SCORES;
    }

}

