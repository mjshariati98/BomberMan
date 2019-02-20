package Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFrame extends JFrame {
    private Server server;
    private ListenThread listenThread;

    private JPanel serverPanel;
    private JPanel newGamePanel;
    private JPanel runningGamesPanel;
    private JPanel connectedClientsPanel;
    private JPanel runningGames_scrollPanel;
    private JPanel connectedClients_scrollPanel;

    private Thread runningGamesThread;
    private Thread connectedClientsThread;
    private int counter;


    public ServerFrame() {

        int width = 500;
        int height = 450;
        setLayout(null);
        setSize(width, height);


        /**** ServerPanel ****/
        serverPanel = new JPanel();
        serverPanel.setSize(width, height);
        serverPanel.setLayout(null);
        serverPanel.setBounds(0, 0, width, height);
        add(serverPanel);
        serverPanel.setVisible(true);

        JLabel ipLbl = new JLabel("IP :");
        JTextField ipTextField = new JTextField("127.0.0.1");
        ipTextField.setHorizontalAlignment(0);
        ipTextField.setEnabled(false);
        JLabel portLbl = new JLabel("Port :");
        JTextField portTextField = new JTextField();
        portTextField.setHorizontalAlignment(0);
        JButton runServerBtn = new JButton("Run Server");
        JLabel serverRunningLbl = new JLabel("Server is running .....");
        serverRunningLbl.setVisible(false);
        serverRunningLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        JButton server_newGameBtn = new JButton("Create New Game");
        server_newGameBtn.setEnabled(false);
        JButton server_runningGamesBtn = new JButton("Running Games");
        server_runningGamesBtn.setEnabled(false);
        JButton server_connectedClientsBtn = new JButton("Connected Clients");
        server_connectedClientsBtn.setEnabled(false);
        JButton server_closeServerBtn = new JButton("Close Server");

        ipLbl.setBounds(40, 25, 30, 30);
        serverPanel.add(ipLbl);
        ipTextField.setBounds(90, 25, 90, 30);
        serverPanel.add(ipTextField);
        portLbl.setBounds(230, 25, 40, 30);
        serverPanel.add(portLbl);
        portTextField.setBounds(280, 25, 60, 30);
        serverPanel.add(portTextField);
        runServerBtn.setBounds(380, 22, 100, 35);
        serverPanel.add(runServerBtn);
        serverRunningLbl.setBounds(160, 60, 180, 30);
        serverPanel.add(serverRunningLbl);
        server_newGameBtn.setBounds(150, 120, 200, 40);
        serverPanel.add(server_newGameBtn);
        server_runningGamesBtn.setBounds(150, 190, 200, 40);
        serverPanel.add(server_runningGamesBtn);
        server_connectedClientsBtn.setBounds(150, 260, 200, 40);
        serverPanel.add(server_connectedClientsBtn);
        server_closeServerBtn.setBounds(150, 350, 200, 40);
        serverPanel.add(server_closeServerBtn);

        runServerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipLbl.setEnabled(false);
                portLbl.setEnabled(false);
                portTextField.setEnabled(false);
                runServerBtn.setEnabled(false);

                server = new Server(ipTextField.getText(), Integer.parseInt(portTextField.getText()));
                if (listenThread != null && listenThread.isAlive()) {
                    listenThread.interrupt();
                }
                listenThread = new ListenThread(server);
                listenThread.start();
                server.setListenThread(listenThread);

                serverRunningLbl.setVisible(true);
                server_newGameBtn.setEnabled(true);
                server_runningGamesBtn.setEnabled(true);
                server_connectedClientsBtn.setEnabled(true);
                server_newGameBtn.requestFocus();

            }
        });

        counter = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    switch (counter % 6) {
                        case 0:
                            serverRunningLbl.setText("Server is running ");
                            break;
                        case 1:
                            serverRunningLbl.setText("Server is running .");
                            break;
                        case 2:
                            serverRunningLbl.setText("Server is running ..");
                            break;
                        case 3:
                            serverRunningLbl.setText("Server is running ...");
                            break;
                        case 4:
                            serverRunningLbl.setText("Server is running ....");
                            break;
                        case 5:
                            serverRunningLbl.setText("Server is running .....");
                            break;
                    }
                    counter++;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        /**** NewGamePanel ****/
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

        newGameLbl.setBounds(175, 20, 150, 40);
        newGamePanel.add(newGameLbl);
        nameLbl.setBounds(25, 70, 50, 30);
        newGamePanel.add(nameLbl);
        nameTextField.setBounds(100, 70, 100, 30);
        newGamePanel.add(nameTextField);
        maxPlayerLbl.setBounds(325, 70, 90, 30);
        newGamePanel.add(maxPlayerLbl);
        maxPlayerTextField.setBounds(425, 70, 50, 30);
        newGamePanel.add(maxPlayerTextField);
        rowLbl.setBounds(25, 120, 50, 30);
        newGamePanel.add(rowLbl);
        rowTextField.setBounds(125, 120, 50, 30);
        newGamePanel.add(rowTextField);
        columnLbl.setBounds(325, 120, 80, 30);
        newGamePanel.add(columnLbl);
        columnTextField.setBounds(425, 120, 50, 30);
        newGamePanel.add(columnTextField);
        bombLimitLbl.setBounds(25, 170, 140, 30);
        newGamePanel.add(bombLimitLbl);
        bombLimitText.setBounds(165, 170, 50, 30);
        newGamePanel.add(bombLimitText);
        max.setBounds(225, 170, 70, 30);
        newGamePanel.add(max);
        bombrangeLbl.setBounds(25, 220, 140, 30);
        newGamePanel.add(bombrangeLbl);
        bombRangeText.setBounds(165, 220, 50, 30);
        newGamePanel.add(bombRangeText);
        max2.setBounds(225, 220, 70, 30);
        newGamePanel.add(max2);
        controlBombLbl.setBounds(25, 270, 140, 30);
        newGamePanel.add(controlBombLbl);
        controlBombCheckBox.setBounds(180, 267, 40, 40);
        newGamePanel.add(controlBombCheckBox);
        speedLbl.setBounds(25, 320, 140, 30);
        newGamePanel.add(speedLbl);
        min.setBounds(145, 320, 40, 30);
        newGamePanel.add(min);
        minSpeed.setBounds(175, 317, 40, 40);
        newGamePanel.add(minSpeed);
        maxx.setBounds(225, 320, 40, 30);
        newGamePanel.add(maxx);
        maxSpeed.setBounds(255, 317, 40, 40);
        newGamePanel.add(maxSpeed);
        newGame_createGameBtn.setBounds(310, 370, 150, 40);
        newGamePanel.add(newGame_createGameBtn);
        newGame_backBtn.setBounds(200, 370, 100, 40);
        newGamePanel.add(newGame_backBtn);


        /**** RunningGamesPanel ****/
        runningGames_scrollPanel = new JPanel();
        runningGames_scrollPanel.setLayout(null);
        JScrollPane scrollPane = new JScrollPane(runningGames_scrollPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        runningGamesPanel = new JPanel(null);
        runningGamesPanel.setPreferredSize(new Dimension(width, height));
        runningGamesPanel.setBounds(0, 0, width, height);
        scrollPane.setBounds(25, 100, width - 50, 260);
        runningGamesPanel.add(scrollPane);
        add(runningGamesPanel);
        runningGamesPanel.setVisible(false);

        JLabel runningGamesLbl = new JLabel("Running Games");
        runningGamesLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        runningGamesLbl.setHorizontalAlignment(0);
        JLabel runningGames_nameLbl = new JLabel("Name");
        JLabel runningGames_playersLbl = new JLabel("Players(Online)");
        JLabel runningGames_pointsLbl = new JLabel("Points");
        JLabel runningGames_seeLbl = new JLabel("See Game Live");
        JLabel runningGames_removeLbl = new JLabel("Remove");
        JButton runningGames_backBtn = new JButton("Back");

        runningGamesLbl.setBounds(175, 20, 150, 40);
        runningGamesPanel.add(runningGamesLbl);
        runningGames_nameLbl.setBounds(60, 65, 70, 40);
        runningGamesPanel.add(runningGames_nameLbl);
        runningGames_playersLbl.setBounds(130, 65, 120, 40);
        runningGamesPanel.add(runningGames_playersLbl);
        runningGames_pointsLbl.setBounds(240, 65, 70, 40);
        runningGamesPanel.add(runningGames_pointsLbl);
        runningGames_seeLbl.setBounds(300, 65, 100, 40);
        runningGamesPanel.add(runningGames_seeLbl);
        runningGames_removeLbl.setBounds(410, 65, 60, 40);
        runningGamesPanel.add(runningGames_removeLbl);
        runningGames_backBtn.setBounds(30, 370, 90, 40);
        runningGamesPanel.add(runningGames_backBtn);


        /**** ConnectedClientsPanel ****/
        connectedClients_scrollPanel = new JPanel();
        connectedClients_scrollPanel.setLayout(null);
        JScrollPane scrollPane2 = new JScrollPane(connectedClients_scrollPanel);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        connectedClientsPanel = new JPanel(null);
        connectedClientsPanel.setPreferredSize(new Dimension(width, height));
        connectedClientsPanel.setBounds(0, 0, width, height);
        scrollPane2.setBounds(25, 100, width - 50, 260);
        connectedClientsPanel.add(scrollPane2);
        add(connectedClientsPanel);
        connectedClientsPanel.setVisible(false);

        JLabel connectedClientsLbl = new JLabel("Connected Clients");
        connectedClientsLbl.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        connectedClientsLbl.setHorizontalAlignment(0);
        JLabel connectedClients_nameLbl = new JLabel("Player Name");
        JLabel connectedClients_gameLbl = new JLabel("Current Game");
        JLabel connectedClients_disconnectLbl = new JLabel("Disconnect");
        JButton connectedClients_backBtn = new JButton("Back");

        connectedClientsLbl.setBounds(175, 20, 150, 40);
        connectedClientsPanel.add(connectedClientsLbl);
        connectedClients_nameLbl.setBounds(55, 65, 100, 40);
        connectedClientsPanel.add(connectedClients_nameLbl);
        connectedClients_gameLbl.setBounds(220, 65, 100, 40);
        connectedClientsPanel.add(connectedClients_gameLbl);
        connectedClients_disconnectLbl.setBounds(380, 65, 100, 40);
        connectedClientsPanel.add(connectedClients_disconnectLbl);
        connectedClients_backBtn.setBounds(30, 370, 90, 40);
        connectedClientsPanel.add(connectedClients_backBtn);


        /** Buttons Action **/

        server_newGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverPanel.setVisible(false);
                newGamePanel.setVisible(true);
                nameTextField.setText("");
                maxPlayerTextField.setText("");
                rowTextField.setText("");
                columnTextField.setText("");
                bombLimitText.setText("1");
                bombRangeText.setText("1");
                controlBombCheckBox.setSelected(false);
                minSpeed.setSelected(true);
                nameTextField.requestFocus();
            }
        });

        server_runningGamesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverPanel.setVisible(false);
                runningGamesPanel.setVisible(true);

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

        server_connectedClientsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                connectedClientsThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            updateConnectedClient();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
                connectedClientsThread.start();

                serverPanel.setVisible(false);
                connectedClientsPanel.setVisible(true);
            }
        });

        server_closeServerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (server != null) {
                    if (listenThread != null && listenThread.isAlive()) {
                        listenThread.interrupt();
                    }
                }
                System.exit(0);
            }
        });

        newGame_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGamePanel.setVisible(false);
                serverPanel.setVisible(true);
            }
        });

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
                GameServer game = new GameServer(server.getGames().size(), name, maxPlayer, row, column,
                        bombLimit, bombRange, controlBomb, speed, topInstet);
                server.addGame(game);
                newGamePanel.setVisible(false);
                serverPanel.setVisible(true);
            }
        });

        runningGames_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runningGamesThread.stop();

                runningGamesPanel.setVisible(false);
                serverPanel.setVisible(true);
            }
        });

        connectedClients_backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectedClientsThread.stop();

                connectedClientsPanel.setVisible(false);
                serverPanel.setVisible(true);
            }
        });


        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (windowSize.getWidth() - this.getWidth()) / 2;
        int y = (int) (windowSize.getHeight() - this.getHeight()) / 2;
        setLocation(x, y);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        portTextField.setVisible(true);
        setVisible(true);
    }

    private void updateRunningGames() {
        runningGames_scrollPanel.removeAll();
        runningGames_scrollPanel.setPreferredSize(
                new Dimension(ServerFrame.this.getWidth() - 50, server.getGames().size() * 30 + 15));
        int i = 1;
        for (GameServer game : server.getGames()) {
            JLabel num = new JLabel(i + ".");
            JLabel name = new JLabel(game.getName());
            JLabel players = new JLabel(game.getMaxPlayerNumber() + " ( " + game.getPlayers() + " )");
            JButton points = new JButton("Points");
            points.setHorizontalAlignment(0);
            JButton view = new JButton("View");
            view.setHorizontalAlignment(0);
            JButton remove = new JButton("Remove");
            remove.setHorizontalAlignment(0);

            num.setBounds(10, i * 30 - 15, 20, 30);
            name.setBounds(35, i * 30 - 15, 150, 30);
            players.setBounds(130, i * 30 - 15, 50, 30);
            points.setBounds(200, i * 30 - 15, 70, 30);
            view.setBounds(283, i * 30 - 15, 70, 30);
            remove.setBounds(362, i * 30 - 15, 83, 30);

            runningGames_scrollPanel.add(num);
            runningGames_scrollPanel.add(name);
            runningGames_scrollPanel.add(players);
            runningGames_scrollPanel.add(points);
            runningGames_scrollPanel.add(view);
            runningGames_scrollPanel.add(remove);

            points.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getPointsFrame().setVisible(true);
                }
            });

            view.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.setVisible(true);
                }
            });

            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.getGameThread().stop();
                    game.dispose();
                    server.getGames().remove(game);
                    for (SocketThread socketThread :game.getClientsSocketThreads()){
                        socketThread.getClient().setGame(null);
                        socketThread.setGaming(false);
                        socketThread.setViewing(false);
                    }
                }
            });
            i++;
        }
        runningGames_scrollPanel.updateUI();
    }

    private void updateConnectedClient() {
        connectedClients_scrollPanel.removeAll();
        connectedClients_scrollPanel.setPreferredSize(new Dimension(getWidth() - 50, 50)); ////////// +15
        for (int i = 1; i <= server.getClients().size(); i++) {
            Client client = server.getClients().get(i-1);
            JLabel num = new JLabel(i + ".");
            JLabel name = new JLabel(client.getUsername());
            JLabel gameLbl = new JLabel();
            gameLbl.setHorizontalAlignment(0);
            if (client.getGame() != null)
                gameLbl.setText(client.getGame().getName());
            else
                gameLbl.setText("-");

            JButton disconnect = new JButton("Disconnect");

            num.setBounds(10, i * 30 - 15, 20, 30);
            name.setBounds(30, i * 30 - 15, 120, 30);
            gameLbl.setBounds(180, i * 30 - 15, 120, 30);
            disconnect.setBounds(340, i * 30 - 15, 100, 30);

            disconnect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    client.getSocketThread().interrupt();
                }
            });


            connectedClients_scrollPanel.add(num);
            connectedClients_scrollPanel.add(name);
            connectedClients_scrollPanel.add(gameLbl);
            connectedClients_scrollPanel.add(disconnect);
        }
        connectedClients_scrollPanel.updateUI();
    }
}
