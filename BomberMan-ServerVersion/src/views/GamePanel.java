package views;

import Configs.GameConfiguration;
import Models.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class GamePanel extends JPanel {
    private Map map;
    private int cellWidth;
    private int cellHeight;
    private Cell[][] cells;
    private Image greenImage = null;
    private Image wallImage = null;
    private Image blockImage = null;
    private Image wallpaper = null;
    private List<Bomb> bombs;
    private List<Monster> monsters;
    private List<BomberMan> bomberMans;

    public GamePanel(Map map) {
        this.map = map;
        this.bomberMans = map.getBomberMans();
        this.monsters = map.getMonsters();
        map.setGamePanel(this);
        cellWidth = GameConfiguration.CELL_WIDTH;
        cellHeight = GameConfiguration.CELL_HEIGHT;
        cells = map.getCells();


        try {
            greenImage = ImageIO.read(new File("src/Images/green.png"))
                    .getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
            wallImage = ImageIO.read(new File("src/Images/wall.png"))
                    .getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
            blockImage = ImageIO.read(new File("src/Images/block.png"))
                    .getScaledInstance(cellWidth, cellHeight, Image.SCALE_SMOOTH);
            wallpaper = ImageIO.read(new File("src/Images/wallpaper.jpg"))
                    .getScaledInstance(map.getMainWidth(), map.getHeight(), Image.SCALE_SMOOTH);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
        this.monsters = map.getMonsters();
    }

    public List<Monster> getMonsters() {
        return monsters;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }

    @Override
    protected synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        cells = map.getCells();
        if (getMap().getRows() <= 30 && getMap().getColumns() <= 30) {
            g2.drawImage(wallpaper,
                    0, 0, new Color(0, 0, 0, 0), null);
        }

        for (int i = 0; i < map.getColumns(); i++) {
            for (int j = 0; j < map.getRows(); j++) {
                if (map.getRows() > 30 || map.getColumns() > 30) {
                    g2.drawImage(greenImage, i * cellWidth, j * cellHeight,
                            new Color(0, 0, 0, 0), null);
                }
                if (cells[i][j].getType() == Cell.CellTypes.BLOCK) {
                    g2.drawImage(blockImage, i * cellWidth, j * cellHeight,
                            new Color(0, 0, 0, 0), null);
                } else if (cells[i][j].getType() == Cell.CellTypes.WALL) {
                    g2.drawImage(wallImage, i * cellWidth, j * cellHeight,
                            new Color(0, 0, 0, 0), null);
                } else if ((cells[i][j].getType() == Cell.CellTypes.EMPTY ||
                        cells[i][j].getType() == Cell.CellTypes.BOMB) &&
                        cells[i][j].getItem() != null) {
                    cells[i][j].getItem().paint(g2);
                }
            }
        }

        for (BomberMan bomberMan : bomberMans) {
            bombs = bomberMan.getBobms();
            for (Bomb bomb : bombs) {
                if (!bomb.isExploded()) {
                    if (bomb.getPreviousCellType() == Cell.CellTypes.WALL){
                        g2.drawImage(wallImage,bomb.getCell().getX(),bomb.getCell().getY(),null);
                    }else if (bomb.getPreviousCellType() == Cell.CellTypes.BLOCK){
                        g2.drawImage(blockImage,bomb.getCell().getX(),bomb.getCell().getY(),null);
                    }
                    bomb.paint(g2);
                } else {
                    int bombRange = bomberMan.getBombRange();
                    bomb.paintSpark(g2, bomb.getCellsInRange(bombRange));
                }
            }
        }

        for (Monster monster : monsters) {
            monster.paint(g2);
        }
        Font font = new Font(Font.DIALOG, Font.BOLD, 13);
        FontMetrics fontMetrics = g.getFontMetrics(font);
        g2.setFont(font);
        for (BomberMan bomberMan : bomberMans) {
            if (!bomberMan.isFailed()) {
                bomberMan.paint(g2);
                int length = (fontMetrics.stringWidth(bomberMan.getName()) - bomberMan.getWidth()) / 2;
                g2.drawString(bomberMan.getName(), bomberMan.getX() - length, bomberMan.getY());
            }
        }
    }

}
