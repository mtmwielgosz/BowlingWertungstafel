package systems.hedgehog.model;

import java.util.Optional;

public class LastFrame extends Frame {

    private Optional<BowlingThrow> thirdThrow;

    public Optional<BowlingThrow> getThirdThrow() {
        return thirdThrow;
    }

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
}
