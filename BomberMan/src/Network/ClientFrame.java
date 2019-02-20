package Network;

import Packets.GamePacket;
import Packets.GameServerPacket;
import Packets.RequestPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientFrame extends JFrame {
    private String username;
    private String password;
    private String serverIP;
    private int serverPort;
    private ClientThread clientThread;
    private RequestPacket requestPacket;

    private JPanel ipPanel;
    private JPanel clientPanel;
    private JPanel newGamePanel;
    private JPanel runningGamesPanel;
    private JPanel runningGames_scrollPanel;

    private Thread runningGamesThread;


    public ClientFrame() {
        this.requestPacket = new RequestPacket(RequestPacket.requestID.NONE);
        int width = 450;
        int height = 400;
        setLayout(null);
        setSize(width, height);

        /**** ipPanel ****/
        ipPanel = new JPanel();
        ipPanel.setSize(width, height);
        ipPanel.setLayout(null);
        ipPanel.setBounds(0, 0, width, height);
        add(ipPanel);
        ipPanel.setVisible(true);

        JLabel usernameLbl = new JLabel("Username :");
        JTextField userNameTextField = new JTextField();
        JLabel passwordLbl = new JLabel("Password :");
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setEchoChar('*');
        userNameTextField.setHorizontalAlignment(0);
        JRadioButton specialIp = new JRadioButton();
        JRadioButton rangeOfIp = new JRadioButton();
        ButtonGroup ipGroup = new ButtonGroup();
        ipGroup.add(specialIp);
        ipGroup.add(rangeOfIp);
        specialIp.setSelected(true);
        JLabel specialIpLbl = new JLabel("Special IP : ");
        JLabel ipLbl = new JLabel("IP : ");
        JLabel portLbl = new JLabel("Port : ");
        JTextField ipTextField = new JTextField("127.0.0.1");
        ipTextField.setHorizontalAlignment(0);
        JTextField portTextField = new JTextField("9090");
        portTextField.setHorizontalAlignment(0);
        JButton ip_connectBtn = new JButton("Connect");
        JLabel rangeOfIpLbl = new JLabel("Range of IP : ");
        JLabel fromLbl = new JLabel("From : ");
        fromLbl.setEnabled(false);
        JLabel toLbl = new JLabel("To : ");
        toLbl.setEnabled(false);
        JLabel portLbl2 = new JLabel("Port : ");
        portLbl2.setEnabled(false);
        JTextField fromTextField = new JTextField();
        fromTextField.setHorizontalAlignment(0);
        fromTextField.setEnabled(false);
        JTextField toTextField = new JTextField();
        toTextField.setHorizontalAlignment(0);
        toTextField.setEnabled(false);
        JTextField portTextField2 = new JTextField();
        portTextField2.setHorizontalAlignment(0);
        portTextField2.setEnabled(false);
        JButton ip_searchBtn = new JButton("Search");
        ip_searchBtn.setEnabled(false);
        JComboBox ipRangeComboBox = new JComboBox();
        ipRangeComboBox.setEnabled(false);
        JButton rangeIP_connectBtn = new JButton("Connect");
        rangeIP_connectBtn.setEnabled(false);

        usernameLbl.setBounds(35, 25, 80, 30);
        ipPanel.add(usernameLbl);
        userNameTextField.setBounds(120, 25, 90, 30);
        ipPanel.add(userNameTextField);
        passwordLbl.setBounds(240, 25, 80, 30);
        ipPanel.add(passwordLbl);
        passwordTextField.setBounds(320, 25, 90, 30);
        ipPanel.add(passwordTextField);

        specialIp.setBounds(10, 70, 40, 40);
        ipPanel.add(specialIp);
        specialIpLbl.setBounds(40, 73, 90, 30);
        ipPanel.add(specialIpLbl);

        ipLbl.setBounds(50, 110, 60, 30);
        ipPanel.add(ipLbl);
        ipTextField.setBounds(100, 110, 90, 30);
        ipPanel.add(ipTextField);
        portLbl.setBounds(220, 110, 40, 30);
        ipPanel.add(portLbl);
        portTextField.setBounds(270, 110, 60, 30);
        ipPanel.add(portTextField);
        ip_connectBtn.setBounds(360, 108, 70, 35);
        ipPanel.add(ip_connectBtn);

        rangeOfIp.setBounds(10, 155, 40, 40);
        ipPanel.add(rangeOfIp);
        rangeOfIpLbl.setBounds(40, 160, 120, 30); ///
        ipPanel.add(rangeOfIpLbl);

        fromLbl.setBounds(40, 205, 60, 30);
        ipPanel.add(fromLbl);
        fromTextField.setBounds(100, 205, 90, 30);
        ipPanel.add(fromTextField);
        toLbl.setBounds(40, 250, 60, 30);
        ipPanel.add(toLbl);
        toTextField.setBounds(100, 250, 90, 30);
        ipPanel.add(toTextField);

        portLbl2.setBounds(220, 230, 40, 30);
        ipPanel.add(portLbl2);
        portTextField2.setBounds(270, 230, 60, 30);
        ipPanel.add(portTextField2);
        ip_searchBtn.setBounds(360, 230, 70, 35);
        ipPanel.add(ip_searchBtn);

        ipRangeComboBox.setBounds(70, 295, 160, 60);
        ipPanel.add(ipRangeComboBox);
        rangeIP_connectBtn.setBounds(280, 305, 100, 40);
        ipPanel.add(rangeIP_connectBtn);


        specialIp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromLbl.setEnabled(rangeOfIp.isSelected());
                toLbl.setEnabled(rangeOfIp.isSelected());
                portLbl2.setEnabled(rangeOfIp.isSelected());
                fromTextField.setEnabled(rangeOfIp.isSelected());
                toTextField.setEnabled(rangeOfIp.isSelected());
                portTextField2.setEnabled(rangeOfIp.isSelected());
                ip_searchBtn.setEnabled(rangeOfIp.isSelected());
                rangeIP_connectBtn.setEnabled(rangeOfIp.isSelected());
                ipRangeComboBox.setEnabled(rangeOfIp.isSelected());

                ipLbl.setEnabled(specialIp.isSelected());
                ipTextField.setEnabled(specialIp.isSelected());
                portLbl.setEnabled(specialIp.isSelected());
                portTextField.setEnabled(specialIp.isSelected());
                ip_connectBtn.setEnabled(specialIp.isSelected());
            }
        });
        rangeOfIp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fromLbl.setEnabled(rangeOfIp.isSelected());
                toLbl.setEnabled(rangeOfIp.isSelected());
                portLbl2.setEnabled(rangeOfIp.isSelected());
                fromTextField.setEnabled(rangeOfIp.isSelected());
                toTextField.setEnabled(rangeOfIp.isSelected());
                portTextField2.setEnabled(rangeOfIp.isSelected());
                ip_searchBtn.setEnabled(rangeOfIp.isSelected());
                rangeIP_connectBtn.setEnabled(rangeOfIp.isSelected());
                ipRangeComboBox.setEnabled(rangeOfIp.isSelected());

                ipLbl.setEnabled(specialIp.isSelected());
                ipTextField.setEnabled(specialIp.isSelected());
                portLbl.setEnabled(specialIp.isSelected());
                portTextField.setEnabled(specialIp.isSelected());
                ip_connectBtn.setEnabled(specialIp.isSelected());
            }
        });


        /**** clientPanel ****/

        clientPanel = new JPanel();
        clientPanel.setSize(width, height);
        clientPanel.setLayout(null);
        clientPanel.setBounds(0, 0, width, height);
        add(clientPanel);
        clientPanel.setVisible(false);


        JLabel client_ipLbl = new JLabel("Server IP :");
        JTextField client_ipTextField = new JTextField("127.0.0.1");
        client_ipTextField.setHorizontalAlignment(0);
        client_ipTextField.setEnabled(false);
        JLabel client_portLbl = new JLabel("Server Port :");
        client_portLbl.setEnabled(false);
        JTextField client_portTextField = new JTextField();
        client_portTextField.setEnabled(false);
        client_portTextField.setHorizontalAlignment(0);
        JButton client_newGameBtn = new JButton("Create New Game");
        JButton client_runningGamesBtn = new JButton("Running Games");
        JButton client_backBtn = new JButton("Connect To Another Server");
        JButton client_exitBtn = new JButton("Exit");

        client_ipLbl.setBounds(40, 25, 80, 30);
        clientPanel.add(client_ipLbl);
        client_ipTextField.setBounds(120, 25, 90, 30);
        clientPanel.add(client_ipTextField);
        client_portLbl.setBounds(270, 25, 80, 30);
        clientPanel.add(client_portLbl);
        client_portTextField.setBounds(360, 25, 60, 30);
        clientPanel.add(client_portTextField);
        client_newGameBtn.setBounds(125, 90, 200, 40);
        clientPanel.add(client_newGameBtn);
        client_runningGamesBtn.setBounds(125, 160, 200, 40);
        clientPanel.add(client_runningGamesBtn);
        client_backBtn.setBounds(125, 230, 200, 40);
        clientPanel.add(client_backBtn);
        client_exitBtn.setBounds(125, 315, 200, 40);
        clientPanel.add(client_exitBtn);

        client_runningGamesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runningGamesPanel.setVisible(true);
                clientPanel.setVisible(false);

                runningGamesThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            updateRunningGames();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
                runningGamesThread.start();
            }
        });

        client_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clientPanel.setVisible(false);
                clientThread.interrupt();
                ipPanel.setVisible(true);
            }
        });

        client_exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        /**** newGamePanel ****/
        newGamePanel = new JPanel();
        newGamePanel.setSize(width, height);
        newGamePanel.setLayout(null);
        newGamePanel.setBounds(0, 0, width, height);
        add(newGamePanel);
        newGamePanel.setVisible(false);
        JLabel newGameLbl = new JLabel("New Game");
        newGameLbl.setHorizontalAlignment(0);
        newGameLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JLabel nameLbl = new JLabel("Name : ");
        JTextField nameTextField = new JTextField();
        nameTextField.setHorizontalAlignment(0);
        JLabel rowLbl = new JLabel("Rows : ");
        JLabel columnLbl = new JLabel("Columns : ");
        JTextField rowTextField = new JTextField();
        rowTextField.setHorizontalAlignment(0);
        JTextField columnTextField = new JTextField();
        columnTextField.setHorizontalAlignment(0);
        JLabel maxPlayerLbl = new JLabel("Max Players : ");
        JTextField maxPlayerTextField = new JTextField();
        maxPlayerTextField.setHorizontalAlignment(0);
        JLabel bombLimitLbl = new JLabel("Initial Bomb Limit : ");
        JLabel bombrangeLbl = new JLabel("Initial Bomb Range : ");
        JLabel controlBombLbl = new JLabel("Initial Control Bomb : ");
        JLabel speedLbl = new JLabel("Initial Speed : ");
        JLabel max = new JLabel("( max : 5 )");
        JLabel max2 = new JLabel("( max : 5 )");
        JLabel min = new JLabel("min");
        JLabel maxx = new JLabel("max");
        JTextField bombLimitText = new JTextField("1");
        bombLimitText.setHorizontalAlignment(0);
        JTextField bombRangeText = new JTextField("1");
        bombRangeText.setHorizontalAlignment(0);
        JCheckBox controlBombCheckBox = new JCheckBox();
        controlBombCheckBox.setSelected(false);
        JRadioButton minSpeed = new JRadioButton();
        JRadioButton maxSpeed = new JRadioButton();
        ButtonGroup speedGroup = new ButtonGroup();
        speedGroup.add(minSpeed);
        speedGroup.add(maxSpeed);
        minSpeed.setSelected(true);
        minSpeed.setActionCommand("min");
        JButton newGame_createGameBtn = new JButton("Create Game");
        JButton newGame_backBtn = new JButton("Back");

        newGameLbl.setBounds(150, 15, 150, 40);
        newGamePanel.add(newGameLbl);
        nameLbl.setBounds(25, 60, 50, 30);
        newGamePanel.add(nameLbl);
        nameTextField.setBounds(100, 60, 100, 30);
        newGamePanel.add(nameTextField);
        maxPlayerLbl.setBounds(260, 60, 90, 30);
        newGamePanel.add(maxPlayerLbl);
        maxPlayerTextField.setBounds(360, 60, 50, 30);
        newGamePanel.add(maxPlayerTextField);
        rowLbl.setBounds(25, 100, 50, 30);
        newGamePanel.add(rowLbl);
        rowTextField.setBounds(115, 100, 50, 30);
        newGamePanel.add(rowTextField);
        columnLbl.setBounds(260, 100, 80, 30);
        newGamePanel.add(columnLbl);
        columnTextField.setBounds(360, 100, 50, 30);
        newGamePanel.add(columnTextField);
        bombLimitLbl.setBounds(25, 140, 140, 30);
        newGamePanel.add(bombLimitLbl);
        bombLimitText.setBounds(165, 140, 50, 30);
        newGamePanel.add(bombLimitText);
        max.setBounds(225, 140, 70, 30);
        newGamePanel.add(max);
        bombrangeLbl.setBounds(25, 180, 140, 30);
        newGamePanel.add(bombrangeLbl);
        bombRangeText.setBounds(165, 180, 50, 30);
        newGamePanel.add(bombRangeText);
        max2.setBounds(225, 180, 70, 30);
        newGamePanel.add(max2);
        controlBombLbl.setBounds(25, 220, 140, 30);
        newGamePanel.add(controlBombLbl);
        controlBombCheckBox.setBounds(180, 217, 40, 40);
        newGamePanel.add(controlBombCheckBox);
        speedLbl.setBounds(25, 260, 140, 30);
        newGamePanel.add(speedLbl);
        min.setBounds(145, 260, 40, 30);
        newGamePanel.add(min);
        minSpeed.setBounds(175, 257, 40, 40);
        newGamePanel.add(minSpeed);
        maxx.setBounds(225, 260, 40, 30);
        newGamePanel.add(maxx);
        maxSpeed.setBounds(255, 257, 40, 40);
        newGamePanel.add(maxSpeed);
        newGame_createGameBtn.setBounds(290, 315, 150, 40);
        newGamePanel.add(newGame_createGameBtn);
        newGame_backBtn.setBounds(180, 315, 100, 40);
        newGamePanel.add(newGame_backBtn);

        newGame_createGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameTextField.getText();
                int maxPlayer = Integer.parseInt(maxPlayerTextField.getText());
                int row = Integer.parseInt(rowTextField.getText());
                int column = Integer.parseInt(columnTextField.getText());
                int bombLimit = Integer.parseInt(bombLimitText.getText());
                int bombRange = Integer.parseInt(bombRangeText.getText());
                boolean controlBomb = controlBombCheckBox.isSelected();
                int speed = 5;
                if (maxSpeed.isSelected())
                    speed = 10;
                int topInstet = getInsets().top;
                GamePacket gamePacket = new GamePacket(name, maxPlayer, row, column,
                        bombLimit, bombRange, controlBomb, speed, topInstet);
                RequestPacket requestPacket = new RequestPacket(RequestPacket.requestID.NEWGAME);
                requestPacket.setNewGamePacket(gamePacket);
                setRequestPacket(requestPacket);

                newGamePanel.setVisible(false);
                clientPanel.setVisible(true);
            }
        });

        newGame_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGamePanel.setVisible(false);
                clientPanel.setVisible(true);
            }
        });


        /**** RunningGamesPanel ****/
        runningGames_scrollPanel = new JPanel();
        runningGames_scrollPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(runningGames_scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        runningGamesPanel = new JPanel(null);
        runningGamesPanel.setPreferredSize(new Dimension(width, height));
        runningGamesPanel.setBounds(0, 0, width, height);
        scrollPane.setBounds(25, 85, width - 50, 230);
        runningGamesPanel.add(scrollPane);
        add(runningGamesPanel);
        runningGamesPanel.setVisible(false);

        JLabel runningGamesLbl = new JLabel("Running Games");
        runningGamesLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        runningGamesLbl.setHorizontalAlignment(0);
        JLabel runningGames_nameLbl = new JLabel("Name");
        JLabel runningGames_playersLbl = new JLabel("Players (Online) ");
        JLabel runningGames_seeLbl = new JLabel("See Game Live");
        JLabel runningGames_playLbl = new JLabel("Play");
        JButton runningGames_backBtn = new JButton("Back");

        runningGamesLbl.setBounds(145, 10, 150, 40);
        runningGamesPanel.add(runningGamesLbl);
        runningGames_nameLbl.setBounds(55, 45, 70, 40);
        runningGamesPanel.add(runningGames_nameLbl);
        runningGames_playersLbl.setBounds(150, 45, 120, 40);
        runningGamesPanel.add(runningGames_playersLbl);
        runningGames_seeLbl.setBounds(265, 45, 100, 40);
        runningGamesPanel.add(runningGames_seeLbl);
        runningGames_playLbl.setBounds(378, 45, 60, 40);
        runningGamesPanel.add(runningGames_playLbl);
        runningGames_backBtn.setBounds(25, 325, 90, 40);
        runningGamesPanel.add(runningGames_backBtn);

        runningGames_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runningGamesPanel.setVisible(false);
                clientPanel.setVisible(true);

                runningGamesThread.stop();
            }
        });


        /** Buttons Action **/
        ip_connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userNameTextField.getText().equals("") && !userNameTextField.getText().equals(" "))
                    username = userNameTextField.getText();
                else
                    username = "Unknown";
                ClientFrame.this.password = passwordTextField.getText();
                serverIP = ipTextField.getText();
                serverPort = Integer.parseInt(portTextField.getText());

                if (clientThread != null && clientThread.isAlive()) {
                    clientThread.interrupt();
                }
                try {
                    Socket clientSocket = new Socket();
                    clientSocket.connect(new InetSocketAddress(serverIP, serverPort), 100);
                    clientThread = new ClientThread(clientSocket, ClientFrame.this, username);
                    clientThread.start();

                    ipPanel.setVisible(false);
                    clientPanel.setVisible(true);
                    client_portTextField.setText(ClientFrame.this.serverPort + "");
                    client_newGameBtn.requestFocus();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        ip_searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipRangeComboBox.removeAllItems();
                String firstIp = fromTextField.getText();
                String lastIp = toTextField.getText();
                int port = Integer.parseInt(portTextField2.getText());
                List<String> connectedIPs = new ArrayList<>();
                for (String ip : getIpRange(firstIp, lastIp)) {
                    Socket clientSocket = new Socket();
                    try {
                        clientSocket.connect(new InetSocketAddress(ip, port), 10);
                        if (clientSocket.isConnected())
                            ipRangeComboBox.addItem(ip);
                    } catch (Exception e1) {
//                        e1.printStackTrace();
                    }
                }
            }
        });

        rangeIP_connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!userNameTextField.getText().equals("") && !userNameTextField.getText().equals(" "))
                    username = userNameTextField.getText();
                else
                    username = "Unknown";
                ClientFrame.this.password = passwordTextField.getText();
                serverIP = (String) ipRangeComboBox.getSelectedItem();
                serverPort = Integer.parseInt(portTextField2.getText());

                if (clientThread != null && clientThread.isAlive()) {
                    clientThread.interrupt();
                }
                try {
                    Socket clientSocket = new Socket();
                    clientSocket.connect(new InetSocketAddress(serverIP, serverPort), 100);
                    clientThread = new ClientThread(clientSocket, ClientFrame.this, username);
                    clientThread.start();

                    ipPanel.setVisible(false);
                    clientPanel.setVisible(true);
                    client_portTextField.setText(ClientFrame.this.serverPort + "");
                    client_newGameBtn.requestFocus();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        client_newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameTextField.setText("");
                maxPlayerTextField.setText("");
                rowTextField.setText("");
                columnTextField.setText("");
                bombLimitText.setText("1");
                bombRangeText.setText("1");
                controlBombCheckBox.setSelected(false);
                minSpeed.setSelected(true);

                clientPanel.setVisible(false);
                newGamePanel.setVisible(true);
                nameTextField.requestFocus();
            }
        });


        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        ipTextField.requestFocus();
        setVisible(true);
    }

    public RequestPacket getRequestPacket() {
        return requestPacket;
    }

    public void setRequestPacket(RequestPacket requestPacket) {
        this.requestPacket = requestPacket;
    }

    public void resetRequestPacket() {
        this.requestPacket = new RequestPacket(RequestPacket.requestID.NONE);
    }

    private void updateRunningGames() {
        runningGames_scrollPanel.removeAll();
        runningGames_scrollPanel.setPreferredSize(new Dimension(ClientFrame.this.getWidth() - 50,
                clientThread.getServerDetailsPacket().getGameServerPackets().size() * 30 + 15));
        int i = 1;
        for (GameServerPacket gsp : clientThread.getServerDetailsPacket().getGameServerPackets()) {
            JLabel num = new JLabel(i + ".");
            JLabel name = new JLabel(gsp.getName());
            JLabel players = new JLabel(gsp.getMaxPlayers() + " ( " + gsp.getPlayers() + " )");
            JButton view = new JButton("View");
            view.setHorizontalAlignment(0);
            JButton play = new JButton("Play");
            play.setHorizontalAlignment(0);

            num.setBounds(10, i * 30 - 15, 20, 30);
            name.setBounds(35, i * 30 - 15, 150, 30);
            players.setBounds(155, i * 30 - 15, 70, 30);
            view.setBounds(255, i * 30 - 15, 60, 30);
            play.setBounds(335, i * 30 - 15, 60, 30);

            runningGames_scrollPanel.add(num);
            runningGames_scrollPanel.add(name);
            runningGames_scrollPanel.add(players);
            runningGames_scrollPanel.add(view);
            runningGames_scrollPanel.add(play);
            final int number = i - 1;
            view.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RequestPacket requestPacket = new RequestPacket(RequestPacket.requestID.VIEW);
                    requestPacket.setViewGameID(number);
                    setRequestPacket(requestPacket);
                }
            });

            play.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RequestPacket requestPacket = new RequestPacket(RequestPacket.requestID.PLAY);
                    requestPacket.setPlayGameID(number);
                    setRequestPacket(requestPacket);
                }
            });

            i++;
        }
        runningGames_scrollPanel.updateUI();
    }

    private List<String> getIpRange(String firstIp, String lastIp) {
        List<String> ipRange = new ArrayList<>();
        String[] firstIpParts = firstIp.split("\\.");
        String[] lastIpParts = lastIp.split("\\.");
        for (int i = Integer.parseInt(firstIpParts[0]); i <= Integer.parseInt((lastIpParts[0])); i++) {
            for (int j = Integer.parseInt(firstIpParts[1]); j <= Integer.parseInt((lastIpParts[1])); j++) {
                for (int k = Integer.parseInt(firstIpParts[2]); k <= Integer.parseInt(lastIpParts[2]); k++) {
                    for (int l = Integer.parseInt(firstIpParts[3]); l <= Integer.parseInt(lastIpParts[3]); l++) {
                        ipRange.add(i + "." + j + "." + k + "." + l);
                    }
                }
            }
        }
        return ipRange;
    }

    public JPanel getIpPanel() {
        return ipPanel;
    }

    public JPanel getClientPanel() {
        return clientPanel;
    }

    public JPanel getNewGamePanel() {
        return newGamePanel;
    }

    public JPanel getRunningGamesPanel() {
        return runningGamesPanel;
    }


}
