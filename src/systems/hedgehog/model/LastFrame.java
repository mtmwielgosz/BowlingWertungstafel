package systems.hedgehog.model;

import java.util.Optional;
import java.util.OptionalInt;

public class LastFrame extends Frame {

    private Optional<BowlingThrow> thirdThrow;

    public void setThirdThrow(Optional<BowlingThrow> thirdThrow) {
        this.thirdThrow = thirdThrow;
    }

    @Override
    public Optional<BowlingThrow> getThrow(Integer throwNumber) {

        if(throwNumber.equals(3)) {
            return thirdThrow;
        }
        return super.getThrow(throwNumber);
    }

    @Override
    public OptionalInt getSum() {
        return super.getSum();
    }

    @Override
    public Boolean isFinished() {
        return super.isFinished() && thirdThrow.get().getScores().isPresent();
    }

    @Override
    public Boolean setNextThrow(Optional<BowlingThrow> nextThrow) {

        Boolean inserted = super.setNextThrow(nextThrow);
        if(!inserted && !thirdThrow.get().getScores().isPresent() && (super.getFirstThrow().get().getScores().getAsInt() + super.getSecondThrow().get().getScores().getAsInt() >= BowlingThrow.MAX_SCORES)) {
            thirdThrow = nextThrow;
            return true;
        }
        if(inserted && super.getSecondThrow().get().getScores().isPresent() && (super.getFirstThrow().get().getScores().getAsInt() + super.getSecondThrow().get().getScores().getAsInt()) < BowlingThrow.MAX_SCORES) {
            thirdThrow = generateZeroBowlingThrow();
            return true;
        }
        return false;
    }

    private Optional<BowlingThrow> generateZeroBowlingThrow() {
        BowlingThrow bowlingThrow = new BowlingThrow();
        bowlingThrow.setScores(OptionalInt.of(0));
        bowlingThrow.setBonus(Optional.empty());
        return Optional.of(bowlingThrow);
    }

}
