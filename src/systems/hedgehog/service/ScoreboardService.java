package systems.hedgehog.service;

import systems.hedgehog.factory.MessageFactory;
import systems.hedgehog.factory.ScoreboardFactory;
import systems.hedgehog.model.Scoreboard;

import javax.ejb.Stateless;
import java.util.OptionalInt;

@Stateless
public class ScoreboardService {

    private Scoreboard scoreboard;

    public ScoreboardService() {
        scoreboard = ScoreboardFactory.generateEmptyScoreboard();
    }

    public void updateScoreboard(String numberOfPins) {

        int frameIndex = 0;
        int throwNumber = 1;

        while( !(throwNumber == 3 && frameIndex == (Scoreboard.All_FRAMES_NUMBER - 1)) || !getBowlingThrowResult(frameIndex, throwNumber).equals(MessageFactory.EMPTY) ) {
            throwNumber++;
            if(throwNumber == 3 && frameIndex < (Scoreboard.All_FRAMES_NUMBER - 1)) {
                frameIndex++;
                throwNumber = 1;
            }
        }

        if(!(throwNumber == 3 && frameIndex == (Scoreboard.All_FRAMES_NUMBER - 1))) {

            OptionalInt optionalNumberOfPins = OptionalInt.of(Integer.parseInt(numberOfPins));
            scoreboard.getFrame(frameIndex).getThrow(throwNumber).get().setScores(optionalNumberOfPins);


            if (frameIndex == 9) {
                if (throwNumber == 1) {
                    scoreboard.getLastFrame().getFirstThrow().get().setScores(OptionalInt.of(Integer.parseInt(numberOfPins)));
                } else if (throwNumber == 2) {
                    scoreboard.getLastFrame().getSecondThrow().get().setScores(OptionalInt.of(Integer.parseInt(numberOfPins)));
                } else {
                    scoreboard.getLastFrame().getThirdThrow().get().setScores(OptionalInt.of(Integer.parseInt(numberOfPins)));
                }
            } else {
                if (throwNumber == 1) {
                    scoreboard.getFrames().get(frameIndex).getFirstThrow().get().setScores(OptionalInt.of(Integer.parseInt(numberOfPins)));
                } else {
                    scoreboard.getFrames().get(frameIndex).getSecondThrow().get().setScores(OptionalInt.of(Integer.parseInt(numberOfPins)));
                }
            }
        }
    }



    public String getBowlingThrowResult(int frameNumber, int whichThrow) {

        if(frameNumber == 9) {
            if(whichThrow == 2 && scoreboard.getLastFrame().getSecondThrow().get().getScores().isPresent()) {
                return String.valueOf(scoreboard.getLastFrame().getSecondThrow().get().getScores().getAsInt());
            }
            if(whichThrow == 1 && scoreboard.getLastFrame().getFirstThrow().get().getScores().isPresent()) {
                return  String.valueOf(scoreboard.getLastFrame().getFirstThrow().get().getScores().getAsInt());
            }
            if(whichThrow == 3 && scoreboard.getLastFrame().getThirdThrow().get().getScores().isPresent()) {
                return String.valueOf(scoreboard.getLastFrame().getThirdThrow().get().getScores().getAsInt());
            }
        } else {

            if (whichThrow == 2 && scoreboard.getFrames().get(frameNumber).getSecondThrow().get().getScores().isPresent()) {
                return String.valueOf(scoreboard.getFrames().get(frameNumber).getSecondThrow().get().getScores().getAsInt());
            }
            if (whichThrow == 1 && scoreboard.getFrames().get(frameNumber).getFirstThrow().get().getScores().isPresent()) {
                return String.valueOf(scoreboard.getFrames().get(frameNumber).getFirstThrow().get().getScores().getAsInt());
            }
        }

        return "";
    }

    public Integer getSum() {
        return 123;
    }

    public Integer getSumForFrame(int frameNumber) {
        return null;
    }

    public Boolean isGameOver() {

        int frameIndex = 0;
        int throwNumber = 1;

        while( !(throwNumber == 3 && frameIndex == (Scoreboard.All_FRAMES_NUMBER - 1)) || !getBowlingThrowResult(frameIndex, throwNumber).equals(MessageFactory.EMPTY) ) {
            throwNumber++;
            if(throwNumber == 3 && frameIndex < (Scoreboard.All_FRAMES_NUMBER - 1)) {
                frameIndex++;
                throwNumber = 1;
            }
        }

        return (throwNumber == 3 && frameIndex == (Scoreboard.All_FRAMES_NUMBER - 1));
    }

}
