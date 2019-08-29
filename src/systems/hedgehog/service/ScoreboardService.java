package systems.hedgehog.service;

import systems.hedgehog.factory.ScoreboardFactory;
import systems.hedgehog.model.Bonus;
import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.model.Scoreboard;

import javax.ejb.Stateless;
import java.util.Optional;
import java.util.OptionalInt;

@Stateless
public class ScoreboardService {

    private Scoreboard scoreboard;

    public ScoreboardService() {
        scoreboard = ScoreboardFactory.generateEmptyScoreboard();
    }

    public void updateScoreboard(String nextNumberOfPins) {

        if(!isGameOver()) {
            scoreboard.getFirstEmptyFrame().get().setNextThrow(Optional.of(generateNextBowlingThrow(nextNumberOfPins)));
        }
    }

    private BowlingThrow generateNextBowlingThrow(String nextNumberOfPins) {
        BowlingThrow nextThrow = new BowlingThrow();
        nextThrow.setScores(OptionalInt.of(Integer.parseInt(nextNumberOfPins)));
        return nextThrow;
    }


    public String getBowlingThrowResult(int whichFrame, int whichThrow) {

        Optional<Bonus> currentBonus = scoreboard.getFrame(whichFrame).getThrow(whichThrow).get().getBonus();
        OptionalInt currentScores = scoreboard.getFrame(whichFrame).getThrow(whichThrow).get().getScores();
        if(currentScores.isPresent() && currentBonus != null && currentBonus.isPresent()) {
            if(currentScores.getAsInt() == 0 && currentBonus.get().equals(Bonus.STRIKE)) {
                return " ";
            }
            return currentBonus.get().getSign();
        }

        if(currentScores.isPresent()) {
            return String.valueOf(currentScores.getAsInt());
        }
        return "";
    }

    public Integer getScoreboardSum() {
        return scoreboard.getSum();
    }

    public OptionalInt getFrameSum(int frameNumber) {
        return scoreboard.getFrame(frameNumber).getSum();
    }

    public Boolean isGameOver() {
        return scoreboard.getFrames().stream().allMatch(frame -> frame.isFinished());
    }

}
