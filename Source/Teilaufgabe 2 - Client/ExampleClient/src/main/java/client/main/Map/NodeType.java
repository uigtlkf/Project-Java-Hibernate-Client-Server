package client.main.Map;

public enum NodeType {
    Water("Water"),
    Mountain("Mountain"),
    Grass("Grass");

    private final String label;

    NodeType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
