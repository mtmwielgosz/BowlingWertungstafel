package systems.hedgehog.service;

import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.model.Messages;

import javax.ejb.Stateful;
import java.util.Optional;

@Stateful
public class MessageService {

    private String messageHeader =  Messages.START_GAME_HEADER;
    private String message = Messages.START_GAME;

    public String getMessage() {
        return message;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public void updateMessage(Optional<BowlingThrow> addedBowlingThrow) {

        if(!addedBowlingThrow.isPresent()) {
            messageHeader = Messages.GAME_OVER_HEADER;
            message = Messages.GAME_OVER;
        } else if(addedBowlingThrow.get().getBonus().isPresent()) {
            messageHeader = addedBowlingThrow.get().getBonus().get().getMessageHeader();
            message = addedBowlingThrow.get().getBonus().get().getMessage();
        } else {
            messageHeader = Messages.EMPTY;
            message = String.format(Messages.REGULAR_THROW, String.valueOf(addedBowlingThrow.get().getScore().orElse(0)));
        }
    }

    public void updateNewGameMessage() {
        messageHeader = Messages.START_GAME_HEADER;
        message = Messages.START_GAME;
    }

    public void updateValidationErrorMessage() {
        messageHeader = Messages.VALUE_INVALID_HEADER;
        message = Messages.VALUE_INVALID;
    }

}