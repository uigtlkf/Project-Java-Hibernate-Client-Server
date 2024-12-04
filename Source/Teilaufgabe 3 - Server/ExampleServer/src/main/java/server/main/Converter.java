package server.main;

import messagesbase.UniqueGameIdentifier;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromclient.PlayerRegistration;
import messagesbase.messagesfromserver.*;

import java.util.*;


public class Converter {
    public UniqueGameIdentifier convertGameID(GameID gameID) {
        return new UniqueGameIdentifier(gameID.getGameID());
    }

    public PlayerData convertPlayerRegistration(PlayerRegistration playerRegistration) {
        return new PlayerData(playerRegistration.getStudentFirstName(), playerRegistration.getStudentLastName(), playerRegistration.getStudentUAccount());
    }

    public UniquePlayerIdentifier convertPlayerID(PlayerID playerID) {
        return new UniquePlayerIdentifier(playerID.getPlayerID());
    }

    public GameID convertUniqueGameIdentifier(UniqueGameIdentifier gameID) {
        return new GameID(gameID.getUniqueGameID());
    }

    public ServerHalfMap convertPlayerHalfMap(PlayerHalfMap halfMap) {
        Map<Position, NodeType> playerHalfMap = new HashMap<>();
        Castle castle = null;

        for (PlayerHalfMapNode node : halfMap.getMapNodes()) {
            Position position = new Position(node.getX(), node.getY());
            NodeType nodeType = convertTerrainToNodeType(node.getTerrain());
            playerHalfMap.put(position, nodeType);

            if (node.isFortPresent()) {
                castle = new Castle(position);
            }
        }

        ServerHalfMap serverHalfMap = new ServerHalfMap();
        serverHalfMap.setPlayerHalfMap(playerHalfMap);
        serverHalfMap.setCastle(castle);

        return serverHalfMap;
    }

    private NodeType convertTerrainToNodeType(ETerrain terrain) {
        switch (terrain) {
            case Water:
                return NodeType.Water;
            case Mountain:
                return NodeType.Mountain;
            case Grass:
                return NodeType.Grass;
            default:
                throw new IllegalArgumentException("Unknown terrain type: " + terrain.toString());
        }
    }

    public PlayerID convertUniquePlayerIdentifier(UniquePlayerIdentifier uniquePlayerIdentifier) {
        return new PlayerID(uniquePlayerIdentifier.getUniquePlayerID());
    }

    public GameState convertGame(Game game, PlayerID playerID) {
        Set<PlayerState> playerStates = new HashSet<>();
        playerStates.add(convertPlayer(game.getFirstPlayer()));
        playerStates.add(convertPlayer(game.getSecondPlayer()));
        FullMap fullMap = convertGameMap(game, playerID);
        return new GameState(fullMap, playerStates, game.getGameStateIdentifier());
    }

    private FullMap convertGameMap(Game game, PlayerID playerID) {
        Set<FullMapNode> fullMapNodes = new HashSet<>();

        GameMap gameMap = game.getGameMap();
        for (Map.Entry<PlayerID, ServerHalfMap> entry : gameMap.getHalfMaps().entrySet()) {
            ServerHalfMap halfMap = entry.getValue();
            for (Map.Entry<Position, NodeType> positionEntry : halfMap.getPlayerHalfMap().entrySet()) {
                Position pos = positionEntry.getKey();
                NodeType nodeType = positionEntry.getValue();
                ETerrain terrain = convertNodeType(nodeType);

                EPlayerPositionState playerPosState = getPlayerPositionState(game, pos, playerID);
                ETreasureState treasureState = getTreasureState(game, pos, playerID);
                EFortState fortState = getFortState(game, pos, playerID);

                FullMapNode fullMapNode = new FullMapNode(
                        terrain, playerPosState, treasureState, fortState, pos.getX(), pos.getY()
                );

                fullMapNodes.add(fullMapNode);
            }
        }

        return new FullMap(fullMapNodes);
    }

    private EFortState getFortState(Game game, Position pos, PlayerID playerID) {
        if(game.getHalfMapById(playerID) != null) {
            if(game.getHalfMapById(playerID).getCastle().getCastlePosition() != null) {
                if(game.getHalfMapById(playerID).getCastle().getCastlePosition().equals(pos)) {
                    return EFortState.MyFortPresent;
                }
            }
        }
        PlayerID enemyPlayerID = game.getEnemyPlayerByID(playerID).getPlayerID();
        if(game.getHalfMapById(enemyPlayerID) != null) {
            if(game.getHalfMapById(enemyPlayerID).getCastle().getCastlePosition() != null) {
                if(game.getHalfMapById(enemyPlayerID).getCastle().getCastlePosition().equals(pos)) {
                    // return EFortState.EnemyFortPresent;
                    return EFortState.NoOrUnknownFortState;
                }
            }
        }
        return EFortState.NoOrUnknownFortState;
    }

    private ETreasureState getTreasureState(Game game, Position pos, PlayerID playerID) {
        if(game.getHalfMapById(playerID) != null) {
            if(game.getHalfMapById(playerID).getTreasure().getTreasurePosition() != null) {
                if(game.getHalfMapById(playerID).getTreasure().getTreasurePosition().equals(pos)) {
                    // return ETreasureState.MyTreasureIsPresent;
                    // TODO: replace
                    return ETreasureState.NoOrUnknownTreasureState;
                }
            }
        }
        return ETreasureState.NoOrUnknownTreasureState;
    }

    private EPlayerPositionState getPlayerPositionState(Game game, Position pos, PlayerID playerID) {
        if(game.getGameMap().getHalfMaps().containsKey(playerID)) {
            if(game.getPlayerByID(playerID).getPosition() != null && game.getPlayerByID(playerID).getPosition().equals(pos)) {
                if(game.getEnemyPlayerByID(playerID).getPosition() != null && game.getEnemyPlayerByID(playerID).getPosition().equals(pos)) {
                    return EPlayerPositionState.BothPlayerPosition;
                } else {
                    return EPlayerPositionState.MyPlayerPosition;
                }
            }
        }

        if(game.getGameMap().getHalfMaps().containsKey(game.getEnemyPlayerByID(playerID).getPlayerID())) {
            if(game.getEnemyPlayerByID(playerID).getPosition() != null && game.getEnemyPlayerByID(playerID).getPosition().equals(pos)) {
                return EPlayerPositionState.EnemyPlayerPosition;
            }
        }
        return EPlayerPositionState.NoPlayerPresent;
    }

    private ETerrain convertNodeType(NodeType nodeType) {
        switch (nodeType) {
            case Water:
                return ETerrain.Water;
            case Mountain:
                return ETerrain.Mountain;
            case Grass:
                return ETerrain.Grass;
            default:
                throw new IllegalArgumentException("Unknown NodeType: " + nodeType);
        }
    }

    private PlayerState convertPlayer(Player player) {
        EPlayerGameState playerStateEnum = convertPlayerGameState(player.getState());
        return new PlayerState(
                player.getPlayerData().getFirstName(),
                player.getPlayerData().getLastName(),
                player.getPlayerData().getUAccount(),
                playerStateEnum,
                new UniquePlayerIdentifier(player.getPlayerID().getPlayerID()),
                player.hasTreasure()
        );
    }

    private EPlayerGameState convertPlayerGameState(PlayerGameState playerGameState) {
        switch (playerGameState) {
            case Won:
                return EPlayerGameState.Won;
            case Lost:
                return EPlayerGameState.Lost;
            case MustAct:
                return EPlayerGameState.MustAct;
            case MustWait:
                return EPlayerGameState.MustWait;
            default:
                throw new IllegalArgumentException("Unknown PlayerGameState: " + playerGameState);
        }
    }

}
