package client.gameLogic;

import java.util.Set;

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
