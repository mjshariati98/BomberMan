package views;

import Network.GameServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class AddEnemyFrame extends JFrame {
    private GameServer gameServer;
    private JTabbedPane tabbedPane;
    private JPanel sampleClassPanel;
    private JPanel cellTypePanel;
    private JPanel directoinClassPanel;
    private JPanel bomberManDetailsPanel;
    private JButton loadBtn;

    public AddEnemyFrame(GameServer gameServer) {
        this.gameServer = gameServer;
        setSize(600, 500);
        setLayout(null);
        setResizable(false);

        tabbedPane = new JTabbedPane();
        sampleClassPanel = new JPanel();
        sampleClassPanel.setLayout(null);
        cellTypePanel = new JPanel();
        cellTypePanel.setLayout(null);
        directoinClassPanel = new JPanel();
        directoinClassPanel.setLayout(null);
        bomberManDetailsPanel = new JPanel();
        bomberManDetailsPanel.setLayout(null);
        tabbedPane.add("AbstractNewMonster Class", sampleClassPanel);
        tabbedPane.add(" CellType Enum", cellTypePanel);
        tabbedPane.add("BomberManDetails Class", bomberManDetailsPanel);
        tabbedPane.add("Direction Enum",directoinClassPanel);
        tabbedPane.setBounds(40,25, getWidth() - 80, 400);
        add(tabbedPane);


        // AbstractNewMonster
        String abstractNemMonsterExplanation = "package Models.Monsters;\n" +
                "\n" +
                "import utils.Direction;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "/**\n" +
                " * Your class must extends AbstractNewMonster.\n" +
                " * No package !\n" +
                " */\n" +
                "public abstract class AbstractNewMonster {\n" +
                "    private int rows;\n" +
                "    private int columns;\n" +
                "    private int width;\n" +
                "    private int height;\n" +
                "    private int startingLevel;\n" +
                "    private String imagePath;\n" +
                "    private int deathScore;\n" +
                "\n" +
                "    /**\n" +
                "     * Set startingLevel, imagePath and deathScore in Constructor with using theit setter.\n" +
                "     * StartingLevel begins from 2.\n" +
                "     * @param rows rows number of the map.\n" +
                "     * @param columns columns number of the map.\n" +
                "     */\n" +
                "    public AbstractNewMonster(int rows, int columns, int width, int height) {\n" +
                "        this.rows = rows;\n" +
                "        this.columns = columns;\n" +
                "        this.width = width;\n" +
                "        this.height = height;\n" +
                "    }\n" +
                "\n" +
                "    public int getRows() {\n" +
                "        return rows;\n" +
                "    }\n" +
                "\n" +
                "    public int getColumns() {\n" +
                "        return columns;\n" +
                "    }\n" +
                "\n" +
                "    public int getStartingLevel() {\n" +
                "        return startingLevel;\n" +
                "    }\n" +
                "\n" +
                "    public String getImagePath() {\n" +
                "        return imagePath;\n" +
                "    }\n" +
                "\n" +
                "    public int getDeathScore() {\n" +
                "        return deathScore;\n" +
                "    }\n" +
                "\n" +
                "    public void setStartingLevel(int startingLevel) {\n" +
                "        this.startingLevel = startingLevel;\n" +
                "    }\n" +
                "\n" +
                "    public void setImagePath(String imagePath) {\n" +
                "        this.imagePath = imagePath;\n" +
                "    }\n" +
                "\n" +
                "    public void setDeathScore(int deathScore) {\n" +
                "        this.deathScore = deathScore;\n" +
                "    }\n" +
                "\n" +
                "    /**\n" +
                "     * @return an int for speed of monster in every move.\n" +
                "     */\n" +
                "    public abstract int getSpeed();\n" +
                "\n" +
                "    /**\n" +
                "     *\n" +
                "     * @param cells Give you the Type of every Cell of the Map. ([column][row])\n" +
                "     * @param bomberMansDetails give you any required information about all bomberMans in the Map.\n" +
                "     * You can see Fields and Methods of BomberManDetails in BomberManDetailsClass tab.\n" +
                "     * @return a Direction to move or null for no move.\n" +
                "     * You can see Cell.CellTypes enum in CellTypesEnum tab.\n" +
                "     */\n" +
                "    public abstract Direction move(int x, int y, int row, int column, CellType[][] cells,int cellSize,\n" +
                "                                   List<BomberManDetails> bomberMansDetails);\n" +
                "}\n";

        JTextArea abstractNewMonsterTextArea= new JTextArea();
        JScrollPane scrollPane = new JScrollPane(abstractNewMonsterTextArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, getWidth() - 80, 400);
        sampleClassPanel.add(scrollPane);

        abstractNewMonsterTextArea.setEditable(false);
        abstractNewMonsterTextArea.setText(abstractNemMonsterExplanation);


        // CellType
        String cellTypeExplanation = "package Models.Monsters;\n" +
                "\n" +
                "public enum CellType {\n" +
                "    EMPTY, WALL, BLOCK, BOMB\n" +
                "}\n";

        JTextArea cellTypeTextArea = new JTextArea();
        JScrollPane scrollPane2 = new JScrollPane(cellTypeTextArea);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setBounds(0, 0, getWidth() - 80, 400);
        cellTypePanel.add(scrollPane2);

        cellTypeTextArea.setEditable(false);
        cellTypeTextArea.setText(cellTypeExplanation);


        // Direction Enum
        String directionExplanation = "package utils;\n" +
                "\n" +
                "public enum Direction {\n" +
                "    UP, RIGHT, DOWN, LEFT;\n" +
                "}";

        JTextArea directionTextArea = new JTextArea();
        JScrollPane scrollPane3 = new JScrollPane(directionTextArea);
        scrollPane3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane3.setBounds(0, 0, getWidth() - 80, 400);
        directoinClassPanel.add(scrollPane3);
        directionTextArea.setEditable(false);
        directionTextArea.setText(directionExplanation);


        // BomberManDetails Class
        String bomberManExplanation = "package Models.Monsters;\n" +
                "\n" +
                "public class BomberManDetails {\n" +
                "    private int x;\n" +
                "    private int y;\n" +
                "    private int score;\n" +
                "    private int bombLimit;\n" +
                "    private int bombRange;\n" +
                "    private boolean controlBomb;\n" +
                "    private int speed;\n" +
                "    private boolean failed;\n" +
                "\n" +
                "    public int getX() {\n" +
                "        return x;\n" +
                "    }\n" +
                "\n" +
                "    public int getY() {\n" +
                "        return y;\n" +
                "    }\n" +
                "\n" +
                "    public int getScore() {\n" +
                "        return score;\n" +
                "    }\n" +
                "\n" +
                "    public int getBombLimit() {\n" +
                "        return bombLimit;\n" +
                "    }\n" +
                "\n" +
                "    public int getBombRange() {\n" +
                "        return bombRange;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isControlBomb() {\n" +
                "        return controlBomb;\n" +
                "    }\n" +
                "\n" +
                "    public int getSpeed() {\n" +
                "        return speed;\n" +
                "    }\n" +
                "\n" +
                "    public boolean isFailed() {\n" +
                "        return failed;\n" +
                "    }\n" +
                "}\n \n \n";

        JTextArea bomberManTextArea = new JTextArea();
        JScrollPane scrollPane4 = new JScrollPane(bomberManTextArea);
        scrollPane4.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane4.setBounds(0, 0, getWidth() - 80, 400);
        bomberManDetailsPanel.add(scrollPane4);
        bomberManTextArea.setEditable(false);
        bomberManTextArea.setText(bomberManExplanation);


        loadBtn = new JButton("Load .class File");
        loadBtn.setBounds(370, 425, 170, 40);
        add(loadBtn);

        JFileChooser fileChooser = new JFileChooser();
        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.showOpenDialog(AddEnemyFrame.this);
                if (fileChooser.getSelectedFile() != null) {
                    try {
                        String path = fileChooser.getSelectedFile().getPath();
                        String name = fileChooser.getSelectedFile().getName();
                        String pathDirectory = path.substring(0,path.length() - name.length());
                        File file = new File(pathDirectory);
                        URL url = file.toURI().toURL();
                        URL[] urls = new URL[]{url};
                        ClassLoader cl = new URLClassLoader(urls);
                        Class monsterClass = cl.loadClass(name.substring(0,name.length()-6));
                        gameServer.getNewMonstersClasses().add(monsterClass);

                        setVisible(false);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }
        });

    }
}
