package systems.hedgehog.factory;

import systems.hedgehog.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ScoreboardFactory {

    public static Scoreboard generateEmptyScoreboard() {

        List<Frame> frames = new ArrayList<>();
        for(int index = 0; index < Scoreboard.All_FRAMES_NUMBER - 1; index++) {
            frames.add(generateEmptyFrame(new Frame()));
        }
        LastFrame lastFrame = generateEmptyFrame(new LastFrame());
        frames.add(lastFrame);
        return new Scoreboard(frames);
    }

    public static <T extends Frame> T generateEmptyFrame(T frame) {
        frame.setFirstThrow(Optional.of(generateEmptyBowlingThrow()));
        frame.setSecondThrow(Optional.of(generateEmptyBowlingThrow()));
        if(frame instanceof LastFrame) {
            ((LastFrame) frame).setThirdThrow(Optional.of(generateEmptyBowlingThrow()));
        }
        return frame;
    }

    public static BowlingThrow generateEmptyBowlingThrow() {
        BowlingThrow bowlingThrow = new BowlingThrow();
        bowlingThrow.setScore(OptionalInt.empty());
        bowlingThrow.setBonus(Optional.empty());
        return bowlingThrow;
    }

    public static BowlingThrow generateBowlingThrow(Integer numberOfPins, Optional<Bonus> bonus) {
        BowlingThrow nextThrow = new BowlingThrow();
        nextThrow.setScore(OptionalInt.of(numberOfPins));
        nextThrow.setBonus(bonus);
        return nextThrow;
    }

    public static BowlingThrow generateMissBowlingThrow() {
        return generateBowlingThrow(0, Optional.of(Bonus.MISS));
    }

    public static BowlingThrow generateBlankBowlingThrow() {
        return generateBowlingThrow(0, Optional.of(Bonus.BLANK));
    }

    public static BowlingThrow generateErrorBowlingThrow() {
        return generateBowlingThrow(0, Optional.of(Bonus.ERROR));
    }
}
