package client.main;

import client.main.Exceptions.CustomGameConversionException;
import client.main.Exceptions.ServerConnectionException;
import client.main.Game.Game;
import client.main.Game.GameConverter;
import client.main.Game.GameID;
import client.main.Map.HalfMap;
import client.main.Map.MapConverter;
import client.main.Movement.MoveAction;
import client.main.Movement.MoveConverter;
import client.main.Player.PlayerID;
import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ERequestState;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerMove;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.GameState;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class Communication {

    private GameID gameID;
    private PlayerID playerID;

    private WebClient baseWebClient;
    private static final Logger logger = Logger.getLogger(Manager.class.getName());

    public Communication(GameID gameID, String serverBaseUrl) {
        this.gameID = gameID;
        this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE) // the network protocol uses XML
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE).build();
    }

    /**
     * Registers a player in the server and returns his/her unique player identifier.
     *
     * @param firstname  first name of the player.
     * @param lastname  last name of the player.
     * @param username  username of the player.
     * @return unique player identifier.
     * @throws IllegalArgumentException in case of an error during the player registration.
     */
    public PlayerID sendPlayerRegistration(String firstname, String lastname, String username) throws ServerConnectionException {

        PlayerRegistration playerReg = new PlayerRegistration(firstname, lastname, username);
        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST).uri("/" + this.gameID.getGameID() + "/players")
                .body(BodyInserters.fromValue(playerReg)) // specify the data which is sent to the server
                .retrieve().bodyToMono(ResponseEnvelope.class); // specify the object returned by the server

        // WebClient support asynchronous message exchange. In SE1 we use a synchronous
        // one for the sake of simplicity. So calling block (which should normally be
        // avoided) is fine.
        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();

        // always check for errors, and if some are reported, at least print them to the
        // console (logging should always be preferred!)
        // so that you become aware of them during debugging! The provided server gives
        // you constructive error messages.
        if (resultReg.getState() == ERequestState.Error) {
            // typically happens if you forgot to create a new game before the client
            // execution or forgot to adapt the run configuration so that it supplies
            // the id of the new game to the client
            // open http://swe1.wst.univie.ac.at:18235/games in your browser to create a new
            // game and obtain its game id
            System.err.println("Client error, errormessage: " + resultReg.getExceptionMessage());
            logger.severe("Client error, errorMessage: " + resultReg.getExceptionMessage());
            throw new ServerConnectionException(resultReg.getExceptionMessage());
        } else {
            UniquePlayerIdentifier uniqueID = resultReg.getData().get();
            System.out.println("My Player ID: " + uniqueID.getUniquePlayerID());
            this.playerID = new PlayerID(uniqueID.getUniquePlayerID());
            return this.playerID;
        }
    }

    /**
     * Returns the latest game state based on the game and player's IDs from the server.
     *
     * @return The latest game state.
     * @throws IllegalArgumentException in case of an error during retrieving the game state.
     */
    public Game getLatestGame() throws ServerConnectionException, CustomGameConversionException {

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.GET)
                .uri("/" + gameID.getGameID() + "/states/" + playerID.getPlayerID()).retrieve().bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<GameState> requestResult = webAccess.block();

        if (requestResult.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + requestResult.getExceptionMessage());
            throw new ServerConnectionException(requestResult.getExceptionMessage());
        } else {
            GameState gameState = requestResult.getData().get();
            Game game = GameConverter.convertToGame(gameState, playerID);
            return game;
        }
    }

    /**
     * Sends the player's HalfMap to the server.
     *
     * @param halfMap the player's HalfMap.
     * @throws IllegalArgumentException in case of an error during sending the half map.
     */
    public void sendPlayerHalfMap(HalfMap halfMap) throws ServerConnectionException {
        PlayerHalfMap convertedHalfMap = MapConverter.convertToPlayerHalfMap(this.playerID, halfMap);

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST)
                .uri("/" + this.gameID.getGameID() + "/halfmaps")
                .body(BodyInserters.fromValue(convertedHalfMap))
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<PlayerHalfMap> resultHalfMap = webAccess.block();

        if (resultHalfMap.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + resultHalfMap.getExceptionMessage());
            throw new ServerConnectionException(resultHalfMap.getExceptionMessage());
        }
    }

    /**
     * Sends the player's move action to the server.
     *
     * @param nextAction the move action.
     * @throws IllegalArgumentException  in case of an error during sending the move action.
     */
    public void sendMoveAction(MoveAction nextAction) throws ServerConnectionException {
        PlayerMove playerMove = MoveConverter.convertMoveAction(nextAction, playerID);

        Mono<ResponseEnvelope> webAccess = baseWebClient.method(HttpMethod.POST)
                .uri("/" + this.gameID.getGameID() + "/moves")
                .body(BodyInserters.fromValue(playerMove))
                .retrieve()
                .bodyToMono(ResponseEnvelope.class);

        ResponseEnvelope<PlayerMove> resultHalfMap = webAccess.block();

        if (resultHalfMap.getState() == ERequestState.Error) {
            System.err.println("Client error, errormessage: " + resultHalfMap.getExceptionMessage());
            throw new ServerConnectionException(resultHalfMap.getExceptionMessage());
        }

    }
}
