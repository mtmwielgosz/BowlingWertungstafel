package systems.hedgehog.service;

import org.junit.*;
import systems.hedgehog.model.Bonus;
import systems.hedgehog.model.BowlingThrow;

import java.util.Optional;

public class ScoreboardServiceTest {

    private static ScoreboardService scoreboardService;

    @BeforeClass
    public static void init() {
        scoreboardService = new ScoreboardService();
    }

    @Before
    public void resetScoreboard() {
        scoreboardService.resetScoreboard();
    }

    @Test
    public void shouldGenerateEmptyScoreboardAfterReset() {

        // when
        scoreboardService.resetScoreboard();

        //then
        Assert.assertEquals(Integer.valueOf(0), scoreboardService.getSumOfScoreboard());
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnAndSetStrikeBowlingThrowWhenThrown10() {

        // given
        String numberOfPins = "10";

        // when
        Optional<BowlingThrow> currentBowlingThrow = scoreboardService.updateScoreboard(numberOfPins);

        //then
        Assert.assertEquals(Integer.valueOf(10), Integer.valueOf(currentBowlingThrow.get().getScore().orElse(0)));
        Assert.assertEquals(Bonus.STRIKE, currentBowlingThrow.get().getBonus().get());
        Assert.assertEquals(Bonus.STRIKE.getSign(), scoreboardService.getBowlingThrowResult(0,1));
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnAndSetSpareBowlingThrowWhenThrown10InOneFrame() {

        // given
        String firstNumberOfPins = "4";
        String secondNumberOfPins = "6";

        // when
        Optional<BowlingThrow> firstBowlingThrow = scoreboardService.updateScoreboard(firstNumberOfPins);
        Optional<BowlingThrow> secondBowlingThrow = scoreboardService.updateScoreboard(secondNumberOfPins);

        //then
        Assert.assertEquals(Integer.valueOf(4), Integer.valueOf(firstBowlingThrow.get().getScore().orElse(0)));
        Assert.assertFalse(firstBowlingThrow.get().getBonus().isPresent());
        Assert.assertEquals(Integer.valueOf(6), Integer.valueOf(secondBowlingThrow.get().getScore().orElse(0)));
        Assert.assertEquals(Bonus.SPARE, secondBowlingThrow.get().getBonus().get());
        Assert.assertEquals(Bonus.SPARE.getSign(), scoreboardService.getBowlingThrowResult(0,2));
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnAndSetMissBowlingThrowWhenThrown0() {

        // given
        String numberOfPins = "0";

        // when
        Optional<BowlingThrow> currentBowlingThrow = scoreboardService.updateScoreboard(numberOfPins);

        //then
        Assert.assertEquals(Integer.valueOf(0), Integer.valueOf(currentBowlingThrow.get().getScore().orElse(0)));
        Assert.assertEquals(Bonus.MISS, currentBowlingThrow.get().getBonus().get());
        Assert.assertEquals(Bonus.MISS.getSign(), scoreboardService.getBowlingThrowResult(0,1));
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumOfScoreboardWhenIsPartlySet() {

        // given
        for(Integer index = 0; index < 8; index++) {
          scoreboardService.updateScoreboard("4");
        }

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();

        // then
        Assert.assertEquals(Integer.valueOf(8 * 4), sumOfScoreboard);
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumWithStrikeOfScoreboardWhenIsPartlySet() {

        // given
        for(Integer index = 0; index < 4; index++) {
            scoreboardService.updateScoreboard("4");
        }
        scoreboardService.updateScoreboard("10");
        for(Integer index = 5; index < 7; index++) {
            scoreboardService.updateScoreboard("4");
        }

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();
        Integer sumOfStrikeFrame = scoreboardService.getSumOfFrame(2).getAsInt();

        // then
        Assert.assertEquals(Integer.valueOf(6 * 4 + 10 + 4 + 4), sumOfScoreboard); // 6 times scored 4 + Strike + Bonus for strike
        Assert.assertEquals(Integer.valueOf(10 + 4 + 4), sumOfStrikeFrame);
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumWithSpareOfScoreboardWhenIsPartlySet() {

        // given
        for(Integer index = 0; index < 4; index++) {
            scoreboardService.updateScoreboard("4");
        }
        scoreboardService.updateScoreboard("7");
        scoreboardService.updateScoreboard("3");
        for(Integer index = 5; index < 7; index++) {
            scoreboardService.updateScoreboard("4");
        }

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();
        Integer sumOfSpareFrame = scoreboardService.getSumOfFrame(2).getAsInt();

        // then
        Assert.assertEquals(Integer.valueOf(6 * 4 + 7 + 3 + 4), sumOfScoreboard); /// 6 times scored 4 + Spare + Bonus for spare
        Assert.assertEquals(Integer.valueOf(7 + 3 + 4), sumOfSpareFrame);
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumWithMissOfScoreboardWhenIsPartlySet() {

        // given
        for(Integer index = 0; index < 4; index++) {
            scoreboardService.updateScoreboard("4");
        }
        scoreboardService.updateScoreboard("0");
        for(Integer index = 5; index < 7; index++) {
            scoreboardService.updateScoreboard("4");
        }

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();

        // then
        Assert.assertEquals(Integer.valueOf(6 * 4 + 0), sumOfScoreboard); // 6 times scored 4 + Miss
        Assert.assertFalse(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumOfScoreboardWhenGameOver() {

        // given
        givenScoreboard();
        scoreboardService.updateScoreboard("2");
        scoreboardService.updateScoreboard("8");
        scoreboardService.updateScoreboard("2"); // 10th

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();
        Integer sumOfLastFrame = scoreboardService.getSumOfFrame(9).getAsInt();

        // then
        Assert.assertEquals(Integer.valueOf(givenScoreboardResult() + (2 + 8 + 2) ), sumOfScoreboard);
        Assert.assertEquals(Integer.valueOf(2 + 8 + 2), sumOfLastFrame);
        Assert.assertTrue(scoreboardService.isGameOver());
    }

    @Test
    public void shouldReturnCorrectSumOfScoreboardWhenGameOverAndLastThrowNotDeserved() {

        // given
        givenScoreboard();
        scoreboardService.updateScoreboard("2");
        scoreboardService.updateScoreboard("2"); // 10th

        // when
        Integer sumOfScoreboard = scoreboardService.getSumOfScoreboard();
        Integer sumOfLastFrame = scoreboardService.getSumOfFrame(9).getAsInt();

        // then
        Assert.assertEquals(Integer.valueOf(givenScoreboardResult() + (2 + 2) ), sumOfScoreboard);
        Assert.assertEquals(Integer.valueOf(2 + 2), sumOfLastFrame);
        Assert.assertTrue(scoreboardService.isGameOver());
    }

    private void givenScoreboard() {
        for (Integer index = 0; index < 4; index++) {
            scoreboardService.updateScoreboard("4"); // 1st, 2nd Frames
        }
        scoreboardService.updateScoreboard("10"); // 3rd Frame
        for (Integer index = 5; index < 9; index++) {
            scoreboardService.updateScoreboard("2"); // 4th, 5th Frames
        }
        scoreboardService.updateScoreboard("7");
        scoreboardService.updateScoreboard("3"); // 6th
        for (Integer index = 10; index < 14; index++) {
            scoreboardService.updateScoreboard("1"); // 7th, 8th Frames
        }
        scoreboardService.updateScoreboard("0");
        scoreboardService.updateScoreboard("5"); // 9th
    }

    private Integer givenScoreboardResult() {
        return 4 * 4 + (10 + 2 + 2) + 4 * 2 + (7 + 3 + 1) + 4 * 1 + (0 + 5);
    }

    @Test
    public void shouldReturnFalseWhenBowlingThrowCorrectValue() {

        // given
        String zeroNumberOfPins = "0";
        String fiveNumberOfPins = "5";
        String tenNumberOfPins = "10";

        // when
        Boolean isZeroNumberOfPins = scoreboardService.validateNumberOfPins(zeroNumberOfPins);
        Boolean isFiveNumberOfPins = scoreboardService.validateNumberOfPins(fiveNumberOfPins);
        Boolean isTenNumberOfPins = scoreboardService.validateNumberOfPins(tenNumberOfPins);

        //then
        Assert.assertTrue(isZeroNumberOfPins);
        Assert.assertTrue(isFiveNumberOfPins);
        Assert.assertTrue(isTenNumberOfPins);
    }

    @Test
    public void shouldReturnFalseWhenBowlingThrowWrongValue() {

        // given
        String tooBigNumberOfPins = "11";
        String minusNumberOfPins = "-123";
        String stringNumberOfPins = "aezakmi";

        // when
        Boolean isTooBigNumberOfPins = scoreboardService.validateNumberOfPins(tooBigNumberOfPins);
        Boolean isMinusNumberOfPins = scoreboardService.validateNumberOfPins(minusNumberOfPins);
        Boolean isStringNumberOfPins = scoreboardService.validateNumberOfPins(stringNumberOfPins);

        //then
        Assert.assertFalse(isTooBigNumberOfPins);
        Assert.assertFalse(isMinusNumberOfPins);
        Assert.assertFalse(isStringNumberOfPins);
    }


}
