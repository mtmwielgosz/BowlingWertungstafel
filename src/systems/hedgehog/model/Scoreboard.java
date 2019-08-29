package systems.hedgehog.model;

import java.util.List;

public class Scoreboard {

    public static final int All_FRAMES_NUMBER = 10;
    private List<Frame> frames;
    private LastFrame lastFrame;

    public Scoreboard(List<Frame> frames, LastFrame lastFrame) {
        this.frames = frames;
        this.lastFrame = lastFrame;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public LastFrame getLastFrame() {
        return lastFrame;
    }

    public Frame getFrame(Integer frameNumber) {
        if(frameNumber.equals(All_FRAMES_NUMBER - 1)) {
            return lastFrame;
        }
        return frames.get(frameNumber);
    }

}
