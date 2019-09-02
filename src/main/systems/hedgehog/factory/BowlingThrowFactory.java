package systems.hedgehog.factory;

import systems.hedgehog.model.*;

import java.util.Optional;
import java.util.OptionalInt;

public class BowlingThrowFactory {

    public static BowlingThrow generateBowlingThrow(OptionalInt numberOfPins, Optional<Bonus> bonus) {
        BowlingThrow nextThrow = new BowlingThrow();
        nextThrow.setScore(numberOfPins);
        nextThrow.setBonus(bonus);
        return nextThrow;
    }

    public static BowlingThrow generateEmptyBowlingThrow() {
        return generateBowlingThrow(OptionalInt.empty(), Optional.empty());
    }

    public static BowlingThrow generateMissBowlingThrow() {
        return generateBowlingThrow(OptionalInt.of(0), Optional.of(Bonus.MISS));
    }

    public static BowlingThrow generateBlankBowlingThrow() {
        return generateBowlingThrow(OptionalInt.of(0), Optional.of(Bonus.BLANK));
    }

    public static BowlingThrow generateErrorBowlingThrow() {
        return generateBowlingThrow(OptionalInt.of(0), Optional.of(Bonus.ERROR));
    }
}
