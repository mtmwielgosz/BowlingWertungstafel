package systems.hedgehog.controller;

import systems.hedgehog.model.BowlingThrow;
import systems.hedgehog.service.MessageService;
import systems.hedgehog.service.ScoreboardService;
import systems.hedgehog.model.Messages;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Optional;

@ManagedBean
@SessionScoped
public class SessionBean {

    @EJB
    private ScoreboardService scoreboardService;

    @EJB
    private MessageService messageService;

    private String newBowlingThrow = Messages.EMPTY;

    public SessionBean() {
    }

    public ScoreboardService getScoreboardService() {
        return scoreboardService;
    }

    public void setScoreboardService(ScoreboardService scoreboardService) {
        this.scoreboardService = scoreboardService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String getNewBowlingThrow() {
        return newBowlingThrow;
    }

    public void setNewBowlingThrow(String numberOfPins) {

        if(!scoreboardService.isGameOver()) {
            if (scoreboardService.validateNumberOfPins(numberOfPins)) {
                Optional<BowlingThrow> addedBowlingThrow = scoreboardService.updateScoreboard(numberOfPins);
                messageService.updateMessage(addedBowlingThrow);
            } else {
                messageService.updateValidationErrorMessage();
            }
        }
    }

    public void setNewGame(String start) {
        if(Messages.START_GAME.equals(start)) {
            scoreboardService.resetScoreboard();
            messageService.updateNewGameMessage();
        }
    }

    public String getNewGame() {
        return Messages.START_GAME;
    }

}