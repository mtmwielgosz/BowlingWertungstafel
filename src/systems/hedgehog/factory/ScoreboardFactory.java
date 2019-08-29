package systems.hedgehog.factory;

import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.model.Frame;
import systems.hedgehog.model.LastFrame;
import systems.hedgehog.model.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ScoreboardFactory {

    public static Scoreboard generateEmptyScoreboard() {

        List<Frame> frames = new ArrayList<>();
        for(int index = 0; index < Scoreboard.All_FRAMES_NUMBER - 1; index++) {
            Frame e = new Frame();
            e.setFirstThrow(Optional.of(generateBowlingThrow()));
            e.setSecondThrow(Optional.of(generateBowlingThrow()));
            frames.add(e);
        }
        LastFrame lastFrame = new LastFrame();
        lastFrame.setThirdThrow(Optional.of(generateBowlingThrow()));
        lastFrame.setSecondThrow(Optional.of(generateBowlingThrow()));
        lastFrame.setFirstThrow(Optional.of(generateBowlingThrow()));
        return new Scoreboard(frames, lastFrame);
    }

    private static BowlingThrow generateBowlingThrow() {
        BowlingThrow bowlingThrow = new BowlingThrow();
        bowlingThrow.setScores(OptionalInt.empty());
        bowlingThrow.setBonus(Optional.empty());
        return bowlingThrow;
    }
}
