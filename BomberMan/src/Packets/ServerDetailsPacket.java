package Packets;

import java.io.Serializable;
import java.util.List;

public class ServerDetailsPacket implements Serializable {
    private List<GameServerPacket> gameServerPackets;

    public ServerDetailsPacket(List<GameServerPacket> gameServerPackets) {
        this.gameServerPackets = gameServerPackets;
    }

    public List<GameServerPacket> getGameServerPackets() {
        return gameServerPackets;
    }
}
