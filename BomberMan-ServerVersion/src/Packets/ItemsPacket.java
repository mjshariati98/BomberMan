package Packets;

import Models.Map;

import java.io.Serializable;

public class ItemsPacket implements Serializable {
    String[][] items;

    public ItemsPacket(Map map) {
        items = new String[map.getColumns()][map.getRows()];
        for (int i = 0; i < map.getColumns(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                if (map.getCells()[i][j].getItem() != null)
                    items[i][j] = map.getCells()[i][j].getItem().getType();
                else
                    items[i][j] = "null";
            }
        }
    }

    public String[][] getItems() {
        return items;
    }
}