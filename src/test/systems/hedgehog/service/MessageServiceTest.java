package systems.hedgehog.service;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import systems.hedgehog.factory.BowlingThrowFactory;
import systems.hedgehog.model.Bonus;
import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.model.Messages;

import java.util.Optional;
import java.util.OptionalInt;

public class MessageServiceTest {

    private static MessageService messageService;

    @BeforeClass
    public static void init() {
        messageService = new MessageService();
    }

    @Test
    public void shouldShowEndGameMessageWhenNoBowlingThrowAdded() {
        // given
        Optional<BowlingThrow> emptyBowlingThrow = Optional.empty();

        // when
        messageService.updateMessage(emptyBowlingThrow);

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.GAME_OVER_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.GAME_OVER);
    }

    @Test
    public void shouldShowStrikeMessageWhenStrikeBowlingThrowAdded() {
        // given
        BowlingThrow strikeBowlingThrow = BowlingThrowFactory.generateBowlingThrow(OptionalInt.of(10), Optional.of(Bonus.STRIKE));

        // when
        messageService.updateMessage(Optional.of(strikeBowlingThrow));

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.STRIKE_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.STRIKE);
    }

    @Test
    public void shouldShowSpareMessageWhenSpareBowlingThrowAdded() {
        // given
        BowlingThrow strikeBowlingThrow = BowlingThrowFactory.generateBowlingThrow(OptionalInt.of(5), Optional.of(Bonus.SPARE));

        // when
        messageService.updateMessage(Optional.of(strikeBowlingThrow));

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.SPARE_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.SPARE);
    }

    @Test
    public void shouldShowMissMessageWhenMissBowlingThrowAdded() {
        // given
        BowlingThrow strikeBowlingThrow = BowlingThrowFactory.generateBowlingThrow(OptionalInt.of(0), Optional.of(Bonus.MISS));

        // when
        messageService.updateMessage(Optional.of(strikeBowlingThrow));

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.MISS_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.MISS);
    }

    @Test
    public void shouldShowRegularMessageWhenRegularBowlingThrowAdded() {
        // given
        BowlingThrow strikeBowlingThrow = BowlingThrowFactory.generateBowlingThrow(OptionalInt.of(2), Optional.empty());

        // when
        messageService.updateMessage(Optional.of(strikeBowlingThrow));

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.EMPTY);
        Assert.assertEquals(messageService.getMessage(), String.format(Messages.REGULAR_THROW, String.valueOf(2)));
    }

    @Test
    public void shouldShowNewGameMessage() {
        // when
        messageService.updateNewGameMessage();

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.START_GAME_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.START_GAME);
    }

    @Test
    public void shouldShowValidationErrorMessage() {
        // when
        messageService.updateValidationErrorMessage();

        // then
        Assert.assertEquals(messageService.getMessageHeader(), Messages.VALUE_INVALID_HEADER);
        Assert.assertEquals(messageService.getMessage(), Messages.VALUE_INVALID);
    }

}
