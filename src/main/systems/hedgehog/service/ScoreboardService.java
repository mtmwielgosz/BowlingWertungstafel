package systems.hedgehog.service;

import systems.hedgehog.factory.BowlingThrowFactory;
import systems.hedgehog.factory.ScoreboardFactory;
import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.model.Frame;
import systems.hedgehog.model.Scoreboard;

import javax.ejb.Stateful;
import java.util.Optional;
import java.util.OptionalInt;

@Stateful
public class ScoreboardService {

    private static final String NUMBER_REGEX = "\\d+";

    private Scoreboard scoreboard;

    public ScoreboardService() {
        scoreboard = ScoreboardFactory.generateEmptyScoreboard();
    }

    public void resetScoreboard() {
        scoreboard = ScoreboardFactory.generateEmptyScoreboard();
    }

    public Optional<BowlingThrow> updateScoreboard(String nextNumberOfPins) {
        return setAndReturnNextBowlingThrow(Integer.parseInt(nextNumberOfPins));
    }

    private Optional<BowlingThrow> setAndReturnNextBowlingThrow(Integer nextNumberOfPins) {
        return scoreboard.getFirstEmptyFrame().get().setNextThrow(
                Optional.of(BowlingThrowFactory.generateBowlingThrow(OptionalInt.of(nextNumberOfPins), Optional.empty())));
    }

    public String getBowlingThrowResult(Integer frameNumber, Integer throwNumberInFrame) {
        return scoreboard.getFrame(frameNumber).getThrow(throwNumberInFrame).get().toString();
    }

    public Integer getSumOfScoreboard() {
        return scoreboard.getSumOfScoreboard();
    }

    public OptionalInt getSumOfFrame(int frameNumber) {
        return scoreboard.getSumOfFrame(frameNumber);
    }

    public Boolean isGameOver() {
        return scoreboard.getFrames().stream().allMatch(Frame::isFinished);
    }

    public Boolean validateNumberOfPins(String numberOfPins) {
        return numberOfPins != null && !numberOfPins.isEmpty() && numberOfPins.matches(NUMBER_REGEX)
                && Integer.parseInt(numberOfPins) >= 0 && Integer.parseInt(numberOfPins) <= BowlingThrow.MAX_SCORES;
    }

}
