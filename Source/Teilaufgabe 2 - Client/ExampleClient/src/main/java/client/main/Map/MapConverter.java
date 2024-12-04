package client.main.Map;
import client.main.Player.PlayerID;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.EFortState;
import messagesbase.messagesfromserver.ETreasureState;
import messagesbase.messagesfromserver.FullMap;
import messagesbase.messagesfromserver.FullMapNode;

import java.util.*;

public class MapConverter {

    public static PlayerHalfMap convertToPlayerHalfMap(PlayerID playerID, HalfMap halfMap) {
        Collection<PlayerHalfMapNode> playerHalfMapNodes = new ArrayList<>();
        Map<Position, NodeType> map = halfMap.getMap();
        Optional<Position> castlePositionOptional = halfMap.getCastle().getPosition();
        Position castlePosition = castlePositionOptional.orElseThrow(() -> new NoSuchElementException("Castle is not present"));

        for (int row = 0; row <= halfMap.getNUM_ROWS(); row++) {
            for (int col = 0; col <= halfMap.getNUM_COLS(); col++) {
                Position position = new Position(col, row);
                NodeType nodeType = map.get(position);
                boolean isCastle = position.equals(castlePosition);

                PlayerHalfMapNode playerHalfMapNode = new PlayerHalfMapNode(col, row, isCastle, NodeTypeToETerrain(nodeType));
                playerHalfMapNodes.add(playerHalfMapNode);
            }
        }

        return new PlayerHalfMap(String.valueOf(playerID), playerHalfMapNodes);
    }

    public static MergedMap convertToMergedMap(FullMap fullMap) {
        if(fullMap.getMapNodes().size() == 100) {
            return splitConvert(fullMap);
        } else {
            Map<Position, Node> map = new HashMap<>();

            for (FullMapNode mapNode : fullMap.getMapNodes()) {
                int x = mapNode.getX();
                int y = mapNode.getY();
                ETerrain terrain = mapNode.getTerrain();
                Castle myCastle= new Castle((mapNode.getFortState().equals(EFortState.MyFortPresent)?new Position(x, y): null));
                Castle opponentCastle= new Castle((mapNode.getFortState().equals(EFortState.EnemyFortPresent)?new Position(x, y): null));
                Treasure myTreasure=new Treasure(mapNode.getTreasureState().equals(ETreasureState.MyTreasureIsPresent)?new Position(x,y):null);

                NodeType nodeType = mapTerrainToNodeType(terrain);
                Node node=new Node(nodeType, myCastle, opponentCastle, myTreasure);
                Position position = new Position(x, y);

                map.put(position, node);
            }
            return new MergedMap(map);
        }
    }

    private static MergedMap splitConvert(FullMap fullMap) {
        Map<Position, Node> map = new HashMap<>();
        Castle myCastle = null;
        for (FullMapNode mapNode : fullMap.getMapNodes()) {
            int x = mapNode.getX();
            int y = mapNode.getY();
            ETerrain terrain = mapNode.getTerrain();
            Castle castle= new Castle((mapNode.getFortState().equals(EFortState.MyFortPresent)?new Position(x, y): null));
            Castle opponentCastle= new Castle((mapNode.getFortState().equals(EFortState.EnemyFortPresent)?new Position(x, y): null));
            Treasure myTreasure=new Treasure(mapNode.getTreasureState().equals(ETreasureState.MyTreasureIsPresent)?new Position(x,y):null);

            NodeType nodeType = mapTerrainToNodeType(terrain);
            Node node=new Node(nodeType, castle, opponentCastle, myTreasure);
            Position position = new Position(x, y);

            map.put(position, node);

            if(castle.getPosition().isPresent()) {
                myCastle = castle;
            }
        }
        MergedMap tmp = new MergedMap(map);
        // todo: create class for splitting maps and call it here (can be static), make test

        tmp.checkExtensionType();
        MergedMapSplitter splitter=new MergedMapSplitter();
        splitter.applyMapSplittingLogic(myCastle, tmp);
        return tmp;
    }

    private static NodeType mapTerrainToNodeType(ETerrain terrain) {
        switch (terrain) {
            case Water:
                return NodeType.Water;
            case Mountain:
                return NodeType.Mountain;
            case Grass:
                return NodeType.Grass;
            default:
                throw new IllegalArgumentException("Unknown terrain type: " + terrain);
        }
    }

    private static ETerrain NodeTypeToETerrain(NodeType nodeType) {
        switch (nodeType) {
            case Grass:
                return ETerrain.Grass;
            case Water:
                return ETerrain.Water;
            case Mountain:
                return ETerrain.Mountain;
            default:
                return ETerrain.Grass;
        }
    }
}

