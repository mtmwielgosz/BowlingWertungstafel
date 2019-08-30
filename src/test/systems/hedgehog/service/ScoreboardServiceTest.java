package systems.hedgehog.service;

import org.junit.BeforeClass;

public class ScoreboardServiceTest {

    private static ScoreboardService scoreboardService;

    @BeforeClass
    public static void init() {
        scoreboardService = new ScoreboardService();
    }
}
