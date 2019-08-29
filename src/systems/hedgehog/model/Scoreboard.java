package systems.hedgehog.model;

import java.util.List;
import java.util.Optional;

public class Scoreboard {

    public static final int All_FRAMES_NUMBER = 10;
    private List<Frame> frames;

    public Scoreboard(List<Frame> frames) {
        this.frames = frames;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public Frame getFrame(Integer frameNumber) {
        return frames.get(frameNumber);
    }

    public Integer getSum() {
        return frames.stream().mapToInt(frame -> frame.getSum().orElse(0)).sum();
    }

    public Optional<Frame> getFirstEmptyFrame() {
        return frames.stream().filter(frame -> !frame.isFinished()).findFirst();
    }

}
