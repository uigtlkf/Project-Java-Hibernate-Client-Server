package client.main.Map;

import client.main.Rules.*;

import java.util.*;

public class HalfMapCreation {

    private RuleChecker ruleChecker;
    private final int NUM_ROWS = 4;
    private final int NUM_COLS = 9;
    private final int MIN_GRASS_NUM = 24;
    private final int MIN_MOUNTAINS_NUM = 5;
    private final int MIN_WATER_NUM=7;
    private final int MAX_RIGHT_WATER_NUM=2;
    private final int MAX_LEFT_WATER_NUM=2;
    private final int MAX_UPPER_WATER_NUM=4;
    private final int MAX_LOWER_WATER_NUM=4;


    public HalfMapCreation() {
        ruleChecker = new RuleChecker();

        MapRule mapRuleCastle = new CheckCastle();
        MapRule mapRuleGrass = new CheckGrass(MIN_GRASS_NUM);
        MapRule mapRuleIslands = new CheckIslands();
        MapRule mapRuleMountains = new CheckMountains(MIN_MOUNTAINS_NUM);
        MapRule mapRuleWater = new CheckWater(MIN_WATER_NUM);
        MapRule mapRuleWaterBorders = new CheckWaterBorders(MAX_RIGHT_WATER_NUM,MAX_LEFT_WATER_NUM,MAX_UPPER_WATER_NUM,MAX_LOWER_WATER_NUM
                ,NUM_ROWS, NUM_COLS);

        ruleChecker.addMapRule(mapRuleCastle);
        ruleChecker.addMapRule(mapRuleGrass);
        ruleChecker.addMapRule(mapRuleIslands);
        ruleChecker.addMapRule(mapRuleMountains);
        ruleChecker.addMapRule(mapRuleWater);
        ruleChecker.addMapRule(mapRuleWaterBorders);

    }

    /**
     * Retrieves a random position from the provided set of positions.
     *
     * @param positions The set of positions from which a random position is to be selected.
     * @return A randomly selected position from the set.
     */
    private static Position getRandomPosition(Set<Position> positions) {
        Random random = new Random();
        List<Position> positionList = new ArrayList<>(positions);
        return positionList.get(random.nextInt(positionList.size()));
    }
    /**
     * Creates a castle on the half map at a randomly chosen grass position.
     *
     * @param currentHalfMap The current half map.
     * @return A new half map with the added castle.
     */
    private HalfMap createCastle(HalfMap currentHalfMap) {
        Map<Position, NodeType> currentHashMap = currentHalfMap.getMap();

        Set<Position> grassPositions = new HashSet<>();
        for (Map.Entry<Position, NodeType> entry : currentHashMap.entrySet()) {
            if (entry.getValue() == NodeType.Grass) {
                grassPositions.add(entry.getKey());
            }
        }
        Position castlePosition = getRandomPosition(grassPositions);
        Castle castle = new Castle(castlePosition);

        return new HalfMap(currentHashMap, castle);
    }


    /**
     * Generates a landscape for an empty half map, filling it with various node types.
     *
     * @param currentHalfMap The empty half map.
     * @return The half map filled with the generated landscape.
     */
    private HalfMap createLandscape(HalfMap currentHalfMap) {
        Map<Position, NodeType> map = new HashMap<>();
        Random random = new Random();
        Set<Position> grassPositions = generateRandomPositions(MIN_GRASS_NUM);
        for (int row = 0; row <= NUM_ROWS; row++) {
            for (int col = 0; col <= NUM_COLS; col++) {
                Position position = new Position(col, row);

                if (grassPositions.contains(position)) {
                    map.put(position, NodeType.Grass);
                } else {
                    NodeType[] nodeTypes = NodeType.values();
                    NodeType randomNodeType = nodeTypes[random.nextInt(nodeTypes.length)];
                    map.put(position, randomNodeType);
                }
            }
        }

        return new HalfMap(map, currentHalfMap.getCastle());
    }

    /**
     * Generates a set of random positions based on a specified count.
     *
     * @param count The number of random positions to be generated.
     * @return A set of randomly generated positions.
     */
    private Set<Position> generateRandomPositions(int count) {
        Set<Position> positions = new HashSet<>();
        Random random = new Random();

        while(positions.size() != count) {
            int randomX = random.nextInt(NUM_COLS + 1);
            int randomY = random.nextInt(NUM_ROWS + 1);
            Position newPosition = new Position(randomX, randomY);
            positions.add(newPosition);
        }

        return positions;
    }

    /**
     * Checks the current half map on the rules.
     *
     * @param currentHalfMap The half map to be checked.
     * @return True if the half map complies with rules, false otherwise.
     */
    private boolean checkRules(HalfMap currentHalfMap) {
        return ruleChecker.checkRules(currentHalfMap);
    }


    /**
     * Creates a new half map by generating a landscape and castle, ensuring it corresponds to specified rules.
     *
     * @return A generated half map that corresponds to the rules.
     */
    public HalfMap createHalfMap() {
        HalfMap currentHalfMap = new HalfMap(new Castle());
        do {
            currentHalfMap = createLandscape(currentHalfMap);
            currentHalfMap = createCastle(currentHalfMap);
        }  while (!checkRules(currentHalfMap));

        System.out.println(currentHalfMap);
        System.out.println(currentHalfMap.getCastle().getPosition());

        return currentHalfMap;
    }
}
