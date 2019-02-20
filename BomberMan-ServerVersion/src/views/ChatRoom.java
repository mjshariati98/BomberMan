package views;

import Network.GameServer;
import Packets.MessagePacket;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatRoom extends JFrame {
    private GameServer gameServer;
    private JPanel mainPanel;
    private JPanel scrollPanel;
    private List<MessagePacket> messagesPacketList;

    public ChatRoom(GameServer gameServer, int topInset) {

        this.gameServer = gameServer;
        this.messagesPacketList = gameServer.getMessagePackets();

        setSize(300, 400 + topInset);
        setLayout(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, getWidth(), getHeight());
        add(mainPanel);


        scrollPanel = new JPanel();
        scrollPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        scrollPane.setBounds(15, 15, getWidth() - 30, 375);
        mainPanel.add(scrollPane);

        setResizable(false);
    }

    public void update() {
        scrollPanel.removeAll();

        int lastY = 0;
        int lastHeight = 0;
        for (MessagePacket messagePacket : messagesPacketList) {
            JLabel messageLbl = new JLabel();
            if (messagePacket.getMessage().length() < 30) {
                messageLbl.setText(messagePacket.getMessage());
                messageLbl.setBorder(BorderFactory.createTitledBorder(messagePacket.getName()));
                int width;
                if (messagePacket.getMessage().length() > messagePacket.getName().length()) {
                    width = (int) messageLbl.getPreferredSize().getWidth() + 7;
                } else {
                    messageLbl.setText(messagePacket.getName());
                    width = (int)messageLbl.getPreferredSize().getWidth() + 7;
                    messageLbl.setText(messagePacket.getMessage());
                }
                messageLbl.setBounds(10, lastY + lastHeight + 15,
                        width, 45);
                lastY = messageLbl.getY();
                lastHeight = messageLbl.getHeight();
            } else {
                String[] messagePieces = messagePacket.getMessage().split(" ");
                int linesCount = 0;
                String lines[] = new String[messagePieces.length];

                for (int i = 0; i < messagePieces.length; i++) {
                    if (i == 0) {
                        lines[0] = messagePieces[0];
                    } else if (lines[linesCount].length() + messagePieces[i].length() < 30) {
                        lines[linesCount] += " " + messagePieces[i];
                    } else {
                        linesCount++;
                        lines[linesCount] = messagePieces[i];
                    }
                }

                String finalPm = "<html>";
                for (int i = 0; i < linesCount + 1; i++) {
                    finalPm += lines[i];
                    finalPm += "<br/>";
                }
                finalPm += "</html>";
                messageLbl.setText(finalPm);
                messageLbl.setBorder(BorderFactory.createTitledBorder(messagePacket.getName()));
                messageLbl.setBounds(10, lastY + lastHeight + 15, 200, (linesCount + 1) * 25);
                lastY = messageLbl.getY();
                lastHeight = messageLbl.getHeight();

            }
            scrollPanel.add(messageLbl);
        }
        scrollPanel.setPreferredSize(new Dimension(getWidth() - 30,
                lastY + lastHeight + 10));


        scrollPanel.updateUI();
    }

}

