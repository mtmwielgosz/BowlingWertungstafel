package systems.hedgehog.factory;

import systems.hedgehog.model.*;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardFactory {

    public static Scoreboard generateEmptyScoreboard() {

        List<Frame> frames = new ArrayList<>();
        for(int index = 0; index < Scoreboard.All_FRAMES_NUMBER - 1; index++) {
            frames.add(FrameFactory.generateEmptyFrame(new Frame()));
        }
        LastFrame lastFrame = FrameFactory.generateEmptyFrame(new LastFrame());
        frames.add(lastFrame);
        return new Scoreboard(frames);
    }

}
