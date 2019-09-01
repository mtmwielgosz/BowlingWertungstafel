package systems.hedgehog.model;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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

    public Integer getSumOfScoreboard() {
        return IntStream.range(0, All_FRAMES_NUMBER).map(index -> getSumOfFrame(index).orElse(0)).sum();
    }

    public OptionalInt getSumOfFrame(Integer frameNumber) {

        Frame currentFrame = getFrame(frameNumber);
        if(isLastFrame(frameNumber)) {
            return currentFrame.getRawSum();
        }
        return OptionalInt.of(currentFrame.getRawSum().orElse(0) + getBonus(frameNumber));
    }

    private boolean isLastFrame(Integer frameNumber) {
        return frameNumber == All_FRAMES_NUMBER - 1;
    }

    private Integer getBonus(Integer frameNumber) {

        Frame currentFrame = getFrame(frameNumber);
        if(isSpareInFrame(currentFrame)) {
            return getNextThrowScore(frameNumber);
        }
        if(isStrikeInFrame(currentFrame)) {
            return getNextSpikeBonus(frameNumber);
        }
        return 0;
    }

    private Integer getNextSpikeBonus(Integer frameNumber) {
        return getNextThrowScore(frameNumber) + getAfterNextThrowScore(frameNumber);
    }

    private int getAfterNextThrowScore(Integer frameNumber) {
        if(isStrikeInFrame(getFrame(frameNumber + 1))) {
            if(isLastFrame(frameNumber + 1)) {
                return getFrame(frameNumber + 1).getSecondThrow().get().getScore().orElse(0);
            } else {
                return getFrame(frameNumber + 2).getFirstThrow().get().getScore().orElse(0);
            }
        }
        return getFrame(frameNumber + 1).getSecondThrow().get().getScore().orElse(0);
    }

    private boolean isSpareInFrame(Frame currentFrame) {
        return currentFrame.getSecondThrow().get().getBonus().isPresent()
                && Bonus.SPARE.equals(currentFrame.getSecondThrow().get().getBonus().get());
    }

    private boolean isStrikeInFrame(Frame currentFrame) {
        return currentFrame.getFirstThrow().get().getBonus().isPresent()
                && Bonus.STRIKE.equals(currentFrame.getFirstThrow().get().getBonus().get());
    }

    private int getNextThrowScore(Integer frameNumber) {
        return getFrame(frameNumber + 1).getFirstThrow().get().getScore().orElse(0);
    }

    public Optional<Frame> getFirstEmptyFrame() {
        return frames.stream().filter(frame -> !frame.isFinished()).findFirst();
    }

}
