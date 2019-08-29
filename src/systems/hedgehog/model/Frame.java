package systems.hedgehog.model;

import java.util.Optional;

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

    public Optional<BowlingThrow> getThrow(Integer throwNumber) {
        if(throwNumber.equals(1)) {
            return firstThrow;
        }
        return secondThrow;
    }
}
