package systems.hedgehog.factory;

import systems.hedgehog.model.Frame;
import systems.hedgehog.model.LastFrame;

import java.util.Optional;

public class FrameFactory {

    public static <T extends Frame> T generateEmptyFrame(T frame) {
        frame.setFirstThrow(Optional.of(BowlingThrowFactory.generateEmptyBowlingThrow()));
        frame.setSecondThrow(Optional.of(BowlingThrowFactory.generateEmptyBowlingThrow()));
        if(frame instanceof LastFrame) {
            ((LastFrame) frame).setThirdThrow(Optional.of(BowlingThrowFactory.generateEmptyBowlingThrow()));
        }
        return frame;
    }
}
