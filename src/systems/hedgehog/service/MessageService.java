package systems.hedgehog.service;

import systems.hedgehog.factory.MessageFactory;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@Stateful
public class MessageService {

    @EJB
    private ScoreboardService scoreboardService;

    private String messageHeader =  MessageFactory.START_GAME_HEADER;
    private String message = MessageFactory.START_GAME;

    public String getMessage() {
        return message;
    }

    public String getMessageHeader() {
        return messageHeader;
    }

    public void updateMessage() {


            messageHeader = MessageFactory.GAME_OVER_HEADER;
            messageHeader = MessageFactory.GAME_OVER;


    }

}