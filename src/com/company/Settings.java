package com.company;

import com.sun.javafx.scene.traversal.Direction;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings extends JFrame {
    private Coord sizeWindow = new Coord(825, 600);

    private JPanel jPanel;
    private boolean isPressedsButtons[] = { false, false, false, false};

    public Settings(){
        InitFrame();
        InitPanel();
    }

    private void InitFrame(){
        pack();
        setTitle("Sea Battle - Settings");
        setLocation(550,100);
        setResizable(false);
        setVisible(true);
        setSize(sizeWindow.x, sizeWindow.y);
        setIconImage(Game.getImage("icon.png"));
    }

    private void InitPanel(){
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Game.getImage("background1.png"), 0, 0, this);
                Game.DrawWaterPolo(new Coord(1,3), g);

                Coord coords = new Coord(1, 1);

                int _x = coords.x*Game.heightCell;
                int _y = coords.y*Game.widthCell;

                JButton oneShip = new JButton();
                JButton twoShip = new JButton();
                JButton threeShip = new JButton();
                JButton fourShip = new JButton();

                oneShip.setBorderPainted(false);
                oneShip.setFocusPainted(false);
                oneShip.setContentAreaFilled(false);
                oneShip.setBounds(_x, _y, 1*Game.widthCell, 37);
                oneShip.addMouseListener(new MouseListener() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[0]) {
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(true);
                            oneShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(true, false, false, false);
                        }
                        else{
                            oneShip.setBorderPainted(false);
                            ((JButton)e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                            SetValueIsPressetBtn(false, false, false, false);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                jPanel.add(oneShip);
                Game.DrawOneShips(coords, g);
                Game.DrawSymbols(new Coord(coords.x+1,coords.y), 4, g);

                _x = (coords.x+4)*Game.heightCell;
                _y = coords.y*Game.widthCell;
                twoShip.setBorderPainted(false);
                twoShip.setFocusPainted(false);
                twoShip.setContentAreaFilled(false);
                twoShip.setBounds(_x, _y, 2*Game.widthCell, 37);
                twoShip.addMouseListener(new MouseListener() {
                    boolean isPressed = false;
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[1]) {
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(true);
                            oneShip.setBorderPainted(false);
                            twoShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, true, false, false);
                        }
                        else{
                            twoShip.setBorderPainted(false);
                            ((JButton)e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                            SetValueIsPressetBtn(false, false, false, false);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                jPanel.add(twoShip);
                Game.DrawTwoShips(Direction.RIGHT, coords, g);
                Game.DrawSymbols(new Coord(coords.x+6,coords.y), 3, g);

                _x = (coords.x+9)*Game.heightCell;
                _y = coords.y*Game.widthCell;
                threeShip.setBorderPainted(false);
                threeShip.setFocusPainted(false);
                threeShip.setContentAreaFilled(false);
                threeShip.setBounds(_x, _y, 3*Game.widthCell, 37);
                threeShip.addMouseListener(new MouseListener() {
                    boolean isPressed = false;
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[2]) {
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(true);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            threeShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, true, false);
                        }
                        else{
                            threeShip.setBorderPainted(false);
                            ((JButton)e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                            SetValueIsPressetBtn(false, false, false, false);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                jPanel.add(threeShip);
                Game.DrawThreeShips(Direction.RIGHT, coords, g);
                Game.DrawSymbols(new Coord(coords.x+12,coords.y), 2, g);


                _x = (coords.x+15)*Game.heightCell;
                _y = coords.y*Game.widthCell;
                fourShip.setBorderPainted(false);
                fourShip.setFocusPainted(false);
                fourShip.setContentAreaFilled(false);
                fourShip.setBounds(_x, _y, 4*Game.widthCell, 37);
                fourShip.addMouseListener(new MouseListener() {
                    boolean isPressed = false;
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[3]) {
                            fourShip.setBorderPainted(true);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            fourShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, false, true);
                        }
                        else{
                            fourShip.setBorderPainted(false);
                            ((JButton)e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                            SetValueIsPressetBtn(false, false, false, false);
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                });
                jPanel.add(fourShip);
                Game.DrawFourShips(Direction.RIGHT, coords, g);
                Game.DrawSymbols(new Coord(coords.x+19,coords.y), 1, g);
            }

        };
        jPanel.setLayout(null);
        jPanel.setSize(1200, 800);

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                JOptionPane.showMessageDialog(null,"X = " + x + ", \nY = " + y,"TITLE", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(jPanel);
    }
    private void SetValueIsPressetBtn(boolean v1, boolean v2, boolean v3, boolean v4){
        isPressedsButtons[0] = v1;
        isPressedsButtons[1] = v2;
        isPressedsButtons[2] = v3;
        isPressedsButtons[3] = v4;
    }
}
