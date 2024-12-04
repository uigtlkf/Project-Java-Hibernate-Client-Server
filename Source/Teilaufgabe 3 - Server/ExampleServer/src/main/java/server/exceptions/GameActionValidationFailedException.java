package server.exceptions;

import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;

public class GameActionValidationFailedException extends RuntimeException {

    private UniqueGameIdentifier gameWithExceptionID;
    private UniquePlayerIdentifier exceptionCausePlayerID;
    private String actionDescription;

    public GameActionValidationFailedException(UniqueGameIdentifier gameWithExceptionID, UniquePlayerIdentifier exceptionCausePlayerID, String actionDescription) {
        this.gameWithExceptionID = gameWithExceptionID;
        this.exceptionCausePlayerID = exceptionCausePlayerID;
        this.actionDescription = actionDescription;
    }

    public UniquePlayerIdentifier getExceptionCausePlayerID() {
        return exceptionCausePlayerID;
    }

    public UniqueGameIdentifier getGameWithExceptionID() {
        return gameWithExceptionID;
    }

    public String getActionDescription() {
        return actionDescription;
    }
}
