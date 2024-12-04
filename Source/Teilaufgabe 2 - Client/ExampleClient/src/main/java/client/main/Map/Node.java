package client.main.Map;

public class Node {
    private Castle myCastle;
    private Castle opponentCastle;
    private Treasure myTreasure;
    private NodeType nodeType;

    public Node(NodeType nodeType, Castle myCastle, Castle opponentCastle, Treasure myTreasure) {
        this.nodeType=nodeType;
        if(myCastle!=null){this.myCastle=myCastle;}
        if(opponentCastle!=null){this.opponentCastle=opponentCastle;}
        if(myTreasure!=null){this.myTreasure=myTreasure;}
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public Castle getMyCastle() {
        return myCastle;
    }

    public Castle getOpponentCastle() {
        return opponentCastle;
    }

    public Treasure getMyTreasure() {
        return myTreasure;
    }

}
