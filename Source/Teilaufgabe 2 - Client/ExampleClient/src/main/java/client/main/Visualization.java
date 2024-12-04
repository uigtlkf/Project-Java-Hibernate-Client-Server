package client.main;

import java.beans.PropertyChangeListener;

import client.main.Exceptions.UncheckedModelException;
import client.main.Game.Game;
import client.main.Map.NodeType;
import client.main.Map.Position;

public class Visualization {
    public Visualization(Game game) {
        game.addPropertyChangeListener(modelChangedListener);
    }

    final PropertyChangeListener modelChangedListener = event -> {
        Object model = event.getSource();

        if(model instanceof Game) {
            Game game = (Game) model;
            System.out.println(printGame(game));
        } else {
            throw new UncheckedModelException("Model type is not supported: " + model.getClass().getName());
        }
    };

    /**
     * Generates the visual representation of the Game map and player positions.
     *
     * @param game The Game object containing the MergedMap, my Player and opponent Player.
     */

    public String printGame(Game game) {
        System.out.println("Game is updated");
        StringBuilder sb = new StringBuilder();
        if (game != null) {
            System.out.println("Player has collected treasure: " + (game.getMyPlayer() != null ? game.getMyPlayer().isCollectedTreasure() : false));
            System.out.println("Enemy has collected treasure: " + (game.getOpponentPlayer() != null ? game.getOpponentPlayer().isCollectedTreasure() : false));
            game.getMergedMap().checkExtensionType();
            int NUM_COLS=game.getMergedMap().getNUM_COLS();
            int NUM_ROWS=game.getMergedMap().getNUM_ROWS();

            Position opponentPlayerPosition = game.getOpponentPlayer() != null ? game.getOpponentPlayer().getPlayerPosition() : null;
            Position myPlayerPosition = game.getMyPlayer() != null ? game.getMyPlayer().getPlayerPosition() : null;
            if (game.getMergedMap()!= null) {
                for (int row = 0; row <= NUM_ROWS; row++) {
                    for (int col = 0; col <= NUM_COLS; col++) {
                        Position position = new Position(col, row);
                        Position myCastlePosition = (game.getMergedMap().getMap().get(position)!=null?game.getMergedMap().getMap().get(position).getMyCastle().getPosition().orElse(null):null);
                        Position opponentCastlePosition = (game.getMergedMap().getMap().get(position)!=null?game.getMergedMap().getMap().get(position).getOpponentCastle().getPosition().orElse(null):null);
                        Position myTreasurePosition = (game.getMergedMap().getMap().get(position)!=null?game.getMergedMap().getMap().get(position).getMyTreasure().getPosition().orElse(null):null);
                        NodeType nodeType = (game.getMergedMap().getMap().get(position)!=null?game.getMergedMap().getMap().get(position).getNodeType():null);
                        String backgroundColor = "\u001B[0m";
                        String content = "  ";
                        if (position.equals(myCastlePosition)) {
                            if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            } else {
                                backgroundColor = "\u001B[41m";
                            }
                            content = "MC";
                        }
                        else if (position.equals(opponentCastlePosition)) {
                            if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            } else {
                                backgroundColor = "\u001B[41m";
                            }
                            content = "OC";
                        }
                        else if (position.equals(myTreasurePosition)) {
                            if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            } else {
                                backgroundColor = "\u001B[41m";
                            }
                            content = "T ";
                        }
                        else if (position.equals(myPlayerPosition)) {
                            if (nodeType == NodeType.Water) {
                                backgroundColor = "\u001B[44m";
                            } else if (nodeType == NodeType.Mountain) {
                                backgroundColor = "\u001B[47m";
                            } else if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            }
                            content = "M ";
                        }
                        else if (position.equals(opponentPlayerPosition)) {
                            if (nodeType == NodeType.Water) {
                                backgroundColor = "\u001B[44m";
                            } else if (nodeType == NodeType.Mountain) {
                                backgroundColor = "\u001B[47m";
                            } else if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            }
                            content = "O ";

                        }
                        else if (nodeType != null) {
                            if (nodeType == NodeType.Water) {
                                backgroundColor = "\u001B[44m";
                            } else if (nodeType == NodeType.Mountain) {
                                backgroundColor = "\u001B[47m";
                            } else if (nodeType == NodeType.Grass) {
                                backgroundColor = "\u001B[42m";
                            }
                        }
                        sb.append(backgroundColor).append(content).append("\u001B[0m");
                    }
                    sb.append("\n");
                }
            }
        } else {
            sb.append("Game is null.");
        }
        return sb.toString();

    }
}
