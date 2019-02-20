package views;

import Network.ClientThread;
import Packets.MessagePacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class ChatRoom extends JFrame {
    private int myID;
    private String myName;
    private ClientThread clientThread;
    private JPanel mainPanel;
    private JPanel scrollPanel;
    private JTextField messageTextField;
    private JButton sendBtn;
    private List<MessagePacket> messagesPacketList;
    private List<MessagePacket> sendingMessages;

    public ChatRoom(ClientThread clientThread, int topInset) {
        this.clientThread = clientThread;
        this.myID = clientThread.getID();
        this.myName = clientThread.getUsername();
        this.messagesPacketList = new ArrayList<>();
        this.sendingMessages = new ArrayList<>();
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
        messageTextField = new JTextField();
        sendBtn = new JButton("Send");


        scrollPane.setBounds(15, 15, getWidth() - 30, 330);
        mainPanel.add(scrollPane);

        messageTextField.setBounds(15, 350, getWidth() - 80, 40);
        sendBtn.setBounds(238, 350, 50, 40);
        mainPanel.add(messageTextField);
        mainPanel.add(sendBtn);

        messageTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendBtn.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!messageTextField.getText().equals("") && !messageTextField.getText().equals(" ")) {
                    MessagePacket messagePacket = new MessagePacket(myID, myName, messageTextField.getText());
                    sendingMessages.add(messagePacket);
                }
                update();

                messageTextField.setText("");
                messageTextField.requestFocus();
            }
        });


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
                if (messagePacket.getId() == myID) {
                    messageLbl.setBorder(BorderFactory.createTitledBorder(""));
                    int width = (int) messageLbl.getPreferredSize().getWidth() + 7;
                    messageLbl.setBounds(ChatRoom.this.getWidth() - width - 50, lastY + lastHeight + 15, width, 40);
                    lastY = messageLbl.getY();
                    lastHeight = messageLbl.getHeight();
                } else {
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
                }
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

                if (messagePacket.getId() == myID) {
                    messageLbl.setBorder(BorderFactory.createTitledBorder(""));
                    messageLbl.setBounds(50, lastY + lastHeight + 15, 200, (linesCount + 1) * 25);
                    lastY = messageLbl.getY();
                    lastHeight = messageLbl.getHeight();

                } else {
                    messageLbl.setBorder(BorderFactory.createTitledBorder(messagePacket.getName()));
                    messageLbl.setBounds(10, lastY + lastHeight + 15, 200, (linesCount + 1) * 25);
                    lastY = messageLbl.getY();
                    lastHeight = messageLbl.getHeight();
                }

            }
            scrollPanel.add(messageLbl);
        }
        scrollPanel.setPreferredSize(new Dimension(getWidth() - 30,
                lastY + lastHeight + 10));


        scrollPanel.updateUI();
    }

    public void setMessagesPacketList(List<MessagePacket> messagesPacketList) {
        this.messagesPacketList = messagesPacketList;
    }

    public List<MessagePacket> getSendingMessages() {
        return sendingMessages;
    }
}
