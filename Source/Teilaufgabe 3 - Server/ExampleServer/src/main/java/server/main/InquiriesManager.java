package server.main;

import javax.servlet.http.HttpServletResponse;

import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromserver.GameState;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import messagesbase.ResponseEnvelope;
import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.PlayerRegistration;
import server.exceptions.BasicValidationFailedException;
import server.exceptions.GameActionValidationFailedException;

@RestController
@RequestMapping(value = "/games")
public class InquiriesManager {

	private Manager manager = new Manager();
	private Converter converter = new Converter();
	private CredentialsValidator validator = new CredentialsValidator();

	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody UniqueGameIdentifier newGame(
			@RequestParam(required = false, defaultValue = "false", value = "enableDebugMode") boolean enableDebugMode,
			@RequestParam(required = false, defaultValue = "false", value = "enableDummyCompetition") boolean enableDummyCompetition) {

		GameID gameID = manager.createGameID();
		UniqueGameIdentifier clientGameID = converter.convertGameID(gameID);
		validator.addGameID(clientGameID);
		return clientGameID;
	}

	@RequestMapping(value = "/{gameID}/players", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<UniquePlayerIdentifier> registerPlayer(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerRegistration playerRegistration) {

		validator.containsGameID(gameID);
		validator.isNewPlayerAllowed(gameID);

		PlayerID playerID = manager.registerPlayer(
				converter.convertUniqueGameIdentifier(gameID),
				converter.convertPlayerRegistration(playerRegistration)
		);

		UniquePlayerIdentifier newPlayerID = converter.convertPlayerID(playerID);
		validator.addPlayerID(gameID, newPlayerID);

		ResponseEnvelope<UniquePlayerIdentifier> playerIDMessage = new ResponseEnvelope<>(newPlayerID);
		return playerIDMessage;
	}


	@RequestMapping(value = "/{gameID}/halfmaps", method = RequestMethod.POST, consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<?> addMap(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @RequestBody PlayerHalfMap halfMap) {
		validator.containsGameID(gameID);
		validator.addNewMap(gameID, halfMap);
		validator.validateMap(gameID,halfMap);

		manager.saveHalfMap(
				converter.convertUniqueGameIdentifier(gameID),
				converter.convertUniquePlayerIdentifier(halfMap),
				converter.convertPlayerHalfMap(halfMap)
		);
		return new ResponseEnvelope<>();
	}

	@RequestMapping(value = "/{gameID}/states/{playerID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
	public @ResponseBody ResponseEnvelope<GameState> getGameState(
			@Validated @PathVariable UniqueGameIdentifier gameID,
			@Validated @PathVariable UniquePlayerIdentifier playerID) {
		validator.containsGameID(gameID);
		validator.isPlayerRegistered(gameID, playerID);

		Game currentGame = manager.getGame(converter.convertUniqueGameIdentifier(gameID), converter.convertUniquePlayerIdentifier(playerID));
		GameState convertedGame = converter.convertGame(currentGame, converter.convertUniquePlayerIdentifier(playerID));
		return new ResponseEnvelope<>(convertedGame);
	}

	@ExceptionHandler({ BasicValidationFailedException.class })
	public @ResponseBody ResponseEnvelope<?> handleException(BasicValidationFailedException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getErrorName(), ex.getMessage());

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}

	@ExceptionHandler({ GameActionValidationFailedException.class })
	public @ResponseBody ResponseEnvelope<?> handleGameActionException(GameActionValidationFailedException ex, HttpServletResponse response) {
		ResponseEnvelope<?> result = new ResponseEnvelope<>(ex.getActionDescription(), "Player " + ex.getExceptionCausePlayerID().getUniquePlayerID() + " has lost");

		manager.processInvalidGameAction(converter.convertUniqueGameIdentifier(ex.getGameWithExceptionID()), converter.convertUniquePlayerIdentifier(ex.getExceptionCausePlayerID()));

		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
}
