package systems.hedgehog.controller;

import systems.hedgehog.factory.MessageFactory;
import systems.hedgehog.service.MessageService;
import systems.hedgehog.service.ScoreboardService;

import javax.ejb.EJB;
import javax.faces.annotation.ManagedProperty;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class SessionBean {

    @EJB
    private ScoreboardService scoreboardService;

    @EJB
    private MessageService messageService;

    private String newBowlingThrow = MessageFactory.EMPTY;

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
        newBowlingThrow = numberOfPins;
        scoreboardService.updateScoreboard(numberOfPins);
        messageService.updateMessage();
    }

}