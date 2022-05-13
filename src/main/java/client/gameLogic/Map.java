package client.gameLogic;

import MessagesBase.MessagesFromClient.ETerrain;
import MessagesBase.MessagesFromClient.HalfMapNode;

import java.util.*;

import MessagesBase.*;

public class Map {

    private Set<Field> fields;
    private Player playerID;
    // private List<Entity> entities;
    private int size;
    private int height;
    private int width;

    public Map() {

    }

    public Map(Set<Field> fields, Player playerID, int size, int height, int width) {
        super();
        this.fields = fields;
        this.playerID = playerID;
        this.size = size;
        this.height = height;
        this.width = width;
    }

    public static Collection<HalfMapNode> generateHalfMap() {
        List<HalfMapNode> halfMapNodes = new ArrayList<HalfMapNode>();


        int X = 8, Y = 4;
//        at least 3 mountain fields, 15 meadow fields and 4 water fields.
        int MIN_MEADOW = 15;
        int MIN_MOUNTAIN = 3;
        int MIN_WATER = 4;
        HalfMapNode[][] nodes = new HalfMapNode[X][Y];
        boolean fortPresent = false;

        int fortPosition = (((int) (Math.random() * Math.random() * 10)) % MIN_MEADOW) + 1;
        for (int i = 0; i < MIN_MEADOW; i++) {
            if (fortPosition == i) {
                halfMapNodes.add(new HalfMapNode(0, 0, true, ETerrain.Grass));
            } else {
                halfMapNodes.add(new HalfMapNode(0, 0, fortPresent, ETerrain.Grass));
            }
        }
        for (int i = 0; i < MIN_MOUNTAIN; i++) {
            halfMapNodes.add(new HalfMapNode(0, 0, fortPresent, ETerrain.Mountain));
        }
        for (int i = 0; i < MIN_WATER; i++) {
            halfMapNodes.add(new HalfMapNode(0, 0, fortPresent, ETerrain.Water));
        }
        int size = halfMapNodes.size() - 1;
        //suffle
        for (int i = 0; i < 20; i++) {
            int from = (((int) (Math.random() * 175400)) % size) + 1;
            int to = (((int) (Math.random() * 15980)) % size) + 1;
            HalfMapNode halfMapNode = halfMapNodes.get(from);
            halfMapNodes.set(from, halfMapNodes.get(to));
            halfMapNodes.set(to, halfMapNode);
        }
        int position = 0;
        for (int i = 0; i < X; i++) {
            for (int j = 0; j < Y; j++) {

                if (position < halfMapNodes.size() ) {
                    HalfMapNode n = halfMapNodes.get(position);
                    halfMapNodes.set(position, new HalfMapNode(i, j, n.isFortPresent(), n.getTerrain()));
                }else{
                    halfMapNodes.add( new HalfMapNode(i, j, false, ETerrain.Grass));
                }
                position++;
            }


        }


        return halfMapNodes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Set<Field> getFields() {
        return fields;
    }

    public int getSize() {
        return size;
    }

    public static Map generateMap() {


        return new Map();
    }

}
