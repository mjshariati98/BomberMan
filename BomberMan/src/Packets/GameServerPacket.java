package Packets;

import java.io.Serializable;

public class GameServerPacket implements Serializable {
    private int gameID;
    private String name;
    private int maxPlayers;
    private int players;
    private int rows;
    private int columns;
    private int level;

    public GameServerPacket(int gameID, String name, int maxPlayers, int players,
                            int rows, int columns, int level) {
        this.gameID = gameID;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.players = players;
        this.rows = rows;
        this.columns = columns;
        this.level = level;
    }

    public int getGameID() {
        return gameID;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayers() {
        return players;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getLevel() {
        return level;
    }
}
