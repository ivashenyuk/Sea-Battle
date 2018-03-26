package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Settings extends JFrame {
    private Coord sizeWindow = new Coord(825, 600);
    public static String howSelect = "";
    public static int IndexCurrentTab = 2;
    private JPanel jPanel;
    private boolean isPressedsButtons[] = {false, false, false, false};
    private static boolean isMousePressed = false;
    private static Coord coordsWaterPolo = new Coord(1, 3);
    private static int positionMouseX = 0;
    private static int positionMouseY = 0;

    int _positionMouseX = 0;
    int _positionMouseY = 0;

    private com.company.Direction direction = com.company.Direction.RIGHT;
    public static ButtonGroup group = null;
    public static ButtonGroup groupComplexity = null;
    private static boolean isPressedMouseLeftButton = false;
    public static String Complexity;

    public Settings() {
        InitFrame();
        InitPanel();
    }

    private void InitFrame() {
        pack();
        setTitle("Sea Battle - Settings");
        setLocation(550, 100);
        setResizable(false);
        setVisible(true);
        setSize(sizeWindow.x, sizeWindow.y);
        setIconImage(Game.getImage("icon.png"));
    }

    private void InitPanel() {
        if (Game.isEndGame) {
            Game.SetShipNULL();
            isMousePressed = false;
            positionMouseX = 0;
            positionMouseY = 0;
            isPressedMouseLeftButton = false;
        }
        Image img = null;
        try {
            img = ImageIO.read(new File("img/buttons/button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
        //UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);
        //створюємо вкладку
        JTabbedPane tabby = new JTabbedPane(JTabbedPane.TOP);

        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if ((isPressedsButtons[0] ||
                        isPressedsButtons[1] ||
                        isPressedsButtons[2] ||
                        isPressedsButtons[3]) && isMousePressed) {
                    SetShip(g);
                    repaint();
                } else {
                    isMousePressed = false;
                }


                g.drawImage(Game.getImage("background1.png"), 0, 0, this);
                Game.DrawWaterPolo(coordsWaterPolo, g);

                Coord coords = new Coord(1, 1);

                int _x = coords.x * Game.heightCell;
                int _y = coords.y * Game.widthCell;

                JButton oneShip = new JButton();
                JButton twoShip = new JButton();
                JButton threeShip = new JButton();
                JButton fourShip = new JButton();

                oneShip.setBorderPainted(false);
                oneShip.setFocusPainted(false);
                oneShip.setContentAreaFilled(false);
                oneShip.setBounds(_x, _y, Game.widthCell, 37);
                oneShip.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!isPressedsButtons[0]) {
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(true);
                            oneShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(true, false, false, false);
                        } else {
                            oneShip.setBorderPainted(false);
                            ((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
                            SetValueIsPressetBtn(false, false, false, false);
                            isPressedMouseLeftButton = false;
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

                Game.DrawOneShips(new Coord(coords.x, coords.y), g);
                Game.DrawSymbols(new Coord(coords.x + 1, coords.y), Game.playe1.countShip1, g);

                _x = (coords.x + 4) * Game.heightCell;
                _y = coords.y * Game.widthCell;
                twoShip.setBorderPainted(false);
                twoShip.setFocusPainted(false);
                twoShip.setContentAreaFilled(false);
                twoShip.setBounds(_x, _y, 2 * Game.widthCell, 37);
                twoShip.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!isPressedsButtons[1]) {
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(true);
                            oneShip.setBorderPainted(false);
                            twoShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, true, false, false);
                        } else {
                            isPressedMouseLeftButton = false;
                            twoShip.setBorderPainted(false);
                            ((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
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

                Game.DrawTwoShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
                Game.DrawSymbols(new Coord(coords.x + 6, coords.y), Game.playe1.countShip2, g);

                _x = (coords.x + 9) * Game.heightCell;
                _y = coords.y * Game.widthCell;
                threeShip.setBorderPainted(false);
                threeShip.setFocusPainted(false);
                threeShip.setContentAreaFilled(false);
                threeShip.setBounds(_x, _y, 3 * Game.widthCell, 37);
                threeShip.addMouseListener(new MouseListener() {
                    boolean isPressed = false;

                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!isPressedsButtons[2]) {
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(true);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            threeShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, true, false);
                        } else {
                            isPressedMouseLeftButton = false;
                            threeShip.setBorderPainted(false);
                            ((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
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

                Game.DrawThreeShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
                Game.DrawSymbols(new Coord(coords.x + 12, coords.y), Game.playe1.countShip3, g);

                _x = (coords.x + 15) * Game.heightCell;
                _y = coords.y * Game.widthCell;
                fourShip.setBorderPainted(false);
                fourShip.setFocusPainted(false);
                fourShip.setContentAreaFilled(false);
                fourShip.setBounds(_x, _y, 4 * Game.widthCell, 37);
                fourShip.addMouseListener(new MouseListener() {
                    boolean isPressed = false;

                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (!isPressedsButtons[3]) {
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(true);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            fourShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, false, true);
                        } else {
                            isPressedMouseLeftButton = false;
                            fourShip.setBorderPainted(false);
                            ((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
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

                Game.DrawFourShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
                Game.DrawSymbols(new Coord(coords.x + 19, coords.y), Game.playe1.countShip4, g);


                Game.DrawShipsOnWaterPolo(new Coord(Game.placeOfBattleUser[0][0].coord.x, Game.placeOfBattleUser[0][0].coord.y), g, 1);
                //Game.DrawShipsOnWaterPolo(new Coord(Game.placeOfBattleEnemy[0][0].coord.x, Game.placeOfBattleEnemy[0][0].coord.y), g, 1);

                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, this);
                _positionMouseX = point.x;
                _positionMouseY = point.y;

                if (isPressedMouseLeftButton) {
                    if (CheckSizeShip() == 1 && (Game.playe1.countShip1 > 0 && isInWaterPoloShip())) {
                        Game.DrawOneShips(new Coord((_positionMouseX / Game.widthCell), (_positionMouseY / Game.heightCell)), g);
                    } else if (CheckSizeShip() == 2 && (Game.playe1.countShip2 > 0 && isInWaterPoloShip())) {
                        Game.DrawTwoShips(direction, new Coord((_positionMouseX / Game.widthCell - 4), (_positionMouseY / Game.heightCell)), g);
                    } else if (CheckSizeShip() == 3 && (Game.playe1.countShip3 > 0 && isInWaterPoloShip())) {
                        Game.DrawThreeShips(direction, new Coord((_positionMouseX / Game.widthCell - 9), (_positionMouseY / Game.heightCell)), g);
                    } else if (CheckSizeShip() == 4 && (Game.playe1.countShip4 > 0 && isInWaterPoloShip())) {
                        Game.DrawFourShips(direction, new Coord((_positionMouseX / Game.widthCell - 15), (_positionMouseY / Game.heightCell)), g);
                    }
                }
                //tabby.setSelectedIndex(IndexCurrentTab);
                IndexCurrentTab = tabby.getSelectedIndex();

                repaint();
            }
        };
        jPanel.setLayout(null);
        jPanel.setSize(1200, 800);
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;
                    positionMouseX = e.getX();
                    positionMouseY = e.getY();
                    jPanel.repaint();
                } else {
                    isMousePressed = false;
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    GetNextDirection();
                }
            }

        });


        JRadioButton serverRadioButton = new JRadioButton();
        JRadioButton clientRadioButton = new JRadioButton();
        serverRadioButton.setBackground(new Color(0, 0, 0, 0));
        clientRadioButton.setBackground(new Color(0, 0, 0, 0));
        serverRadioButton.setActionCommand("server");
        clientRadioButton.setActionCommand("client");

        group = new ButtonGroup();
        group.add(serverRadioButton);
        group.add(clientRadioButton);
        group.setSelected(serverRadioButton.getModel(), true);
        howSelect = "server";
        ChangeListener changeListener = e -> {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            if (radioButton == serverRadioButton && radioButton.isSelected()) {
                howSelect = "server";
            } else if (radioButton == clientRadioButton && radioButton.isSelected()) {
                howSelect = "client";
            }
        };
        if (howSelect == "client") {
            group.setSelected(clientRadioButton.getModel(), true);

        } else if (howSelect == "server") {
            group.setSelected(serverRadioButton.getModel(), true);

        }
        serverRadioButton.addChangeListener(changeListener);
        clientRadioButton.addChangeListener(changeListener);


//FabryComponents - фабрика для створення компонентів
        JLabel lableServer = FabryComponents.CreateJLableName("Server ", 24);
        JLabel lableClient = FabryComponents.CreateJLableName("Client ", 24);


        //створюємо панелі для вкладок
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //panel1.setLayout(new GridLayout(3, 2));

        panel1.add(lableServer);
        panel1.add(serverRadioButton);

        panel1.add(lableClient);
        panel1.add(clientRadioButton);


        //Begin Tab1
        JTextField nameUser = FabryComponents.CreateLTextField(Game.playe1.name);
        JTextField ipForConnection = FabryComponents.CreateLTextField(Game.playe1.ipAddress);

        panel1.add(FabryComponents.CreateJLableName("Your Name ", 22));
        panel1.add(nameUser);
        panel1.add(FabryComponents.CreateJLableName("Default IP - 127.0.0.1", 22));
        panel1.add(ipForConnection);
        //End Tab1

        //Begin Tab2
        JTextField nameUser1 = FabryComponents.CreateLTextField(Game.playe1.name);
        JTextField ipForConnection1 = FabryComponents.CreateLTextField(Game.playe1.ipAddress);

        panel2.add(FabryComponents.CreateJLableName("Your Name ", 22));
        panel2.add(nameUser1);
        panel2.add(FabryComponents.CreateJLableName("Default IP - 127.0.0.1", 22));
        panel2.add(ipForConnection1);
        //End Tab2

        //Begin Tab3
        JRadioButton easyRadioButton = new JRadioButton();
        JRadioButton hardRadioButton = new JRadioButton();
        JRadioButton impossibleRadioButton = new JRadioButton();
        easyRadioButton.setBackground(new Color(0, 0, 0, 0));
        hardRadioButton.setBackground(new Color(0, 0, 0, 0));
        impossibleRadioButton.setBackground(new Color(0, 0, 0, 0));

        easyRadioButton.setActionCommand("easy");
        hardRadioButton.setActionCommand("hard");
        impossibleRadioButton.setActionCommand("impossible");

        groupComplexity = new ButtonGroup();
        groupComplexity.add(easyRadioButton);
        groupComplexity.add(hardRadioButton);
        groupComplexity.add(impossibleRadioButton);

        groupComplexity.setSelected(easyRadioButton.getModel(), true);

        ChangeListener changeListenerForComplexity = e -> {
            JRadioButton radioButton = (JRadioButton) e.getSource();
            if (radioButton == easyRadioButton && radioButton.isSelected()) {
                Complexity = "easy";
            } else if (radioButton == hardRadioButton && radioButton.isSelected()) {
                Complexity = "hard";
            } else if (radioButton == impossibleRadioButton && radioButton.isSelected()) {
                Complexity = "impossible";
            }
        };
        easyRadioButton.addChangeListener(changeListenerForComplexity);
        hardRadioButton.addChangeListener(changeListenerForComplexity);
        impossibleRadioButton.addChangeListener(changeListenerForComplexity);

        JTextField nameUser2 = FabryComponents.CreateLTextField(Game.playe1.name);
        JTextField ipForConnection2 = FabryComponents.CreateLTextField(Game.playe1.ipAddress);
        JLabel easy = FabryComponents.CreateJLableName("Easy ", 18);
        JLabel hard = FabryComponents.CreateJLableName("Hard ", 18);
        JLabel impossible = FabryComponents.CreateJLableName("Impossible ", 18);

        panel3.add(easy);
        panel3.add(easyRadioButton);
        panel3.add(hard);
        panel3.add(hardRadioButton);
        panel3.add(impossible);
        panel3.add(impossibleRadioButton);
        panel3.add(FabryComponents.CreateJLableName("Your Name ", 22));
        panel3.add(nameUser2);
        panel3.add(FabryComponents.CreateJLableName("Default IP - 127.0.0.1", 22));
        panel3.add(ipForConnection2);
        //End Tab3

        //Прозорий фон в табів
        panel1.setBackground(new Color(255, 255, 255, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder());
        panel2.setBackground(new Color(255, 255, 255, 10));
        panel2.setBorder(BorderFactory.createEmptyBorder());
        panel3.setBackground(new Color(255, 255, 255, 10));
        panel3.setBorder(BorderFactory.createEmptyBorder());


        //додаємо панелі у JTabbedPane
        tabby.addTab("PVPLocal", panel1);
        tabby.addTab("PVP", panel2);
        tabby.addTab("PVS", panel3);
        // додаємо вкладки у фрейм
        tabby.setSelectedIndex(IndexCurrentTab);
        int _x = (16) * Game.widthCell;
        int _y = (13) * Game.heightCell;
        JButton jSave = new JButton("Save", new ImageIcon(img));
        jSave.setBorderPainted(false);
        jSave.setFocusPainted(false);
        jSave.setContentAreaFilled(false);
        jSave.setBounds(_x - 5, _y - 5, 215, 37);
        jSave.setFont(new Font("Segoe Script", Font.BOLD, 32));
        jSave.setHorizontalTextPosition(SwingConstants.CENTER);

        jSave.setEnabled(true);
        jSave.setBackground(new Color(255, 255, 255, 10));
        jSave.setForeground(Color.RED);
        jSave.setBorder(BorderFactory.createEmptyBorder());
        jSave.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (Game.btnPvpLocal != null)
                    Game.btnPvpLocal = null;
                if (Game.btnPvP != null)
                    Game.btnPvP = null;
                if (Game.btnPvS != null)
                    Game.btnPvS = null;
                if (Game.btnSettings != null)
                    Game.btnSettings = null;
                if (IndexCurrentTab == 0) {
                    Game.playe1.name = nameUser.getText();
                    Game.playe1.ipAddress = ipForConnection.getText();
                } else if (IndexCurrentTab == 1) {
                    Game.playe1.name = nameUser1.getText();
                    Game.playe1.ipAddress = ipForConnection1.getText();
                } else if (IndexCurrentTab == 2) {
                    Game.playe1.name = nameUser2.getText();
                    Game.playe1.ipAddress = ipForConnection2.getText();
                }
                Game.jPanel.removeAll();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (jSave.isEnabled()) {
                    jSave.setLocation(_x, _y);
                    jSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    jPanel.repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (jSave.isEnabled()) {
                    jSave.setLocation(_x - 5, _y - 5);
                    jPanel.repaint();
                }
            }
        });

        jPanel.add(jSave);

        jPanel.add(tabby);
        tabby.setBounds(463, 143, 321, 250);
        add(jPanel);
    }


    private void SetValueIsPressetBtn(boolean v1, boolean v2, boolean v3, boolean v4) {
        isPressedsButtons[0] = v1;
        isPressedsButtons[1] = v2;
        isPressedsButtons[2] = v3;
        isPressedsButtons[3] = v4;
    }

    private void SetShip(Graphics g) {
        int _y = (positionMouseY / Game.heightCell);
        int _x = (positionMouseX / Game.widthCell);
        if (_x > coordsWaterPolo.x && _x <= coordsWaterPolo.x + 10 &&
                _y > coordsWaterPolo.y && _y <= coordsWaterPolo.y + 10) {
            //-------------- For One Ship
            if (isPressedsButtons[0] && Game.playe1.countShip1 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if (CheckPlaceForShip(indexX, indexY, 1))
                    return;
                if (!Game.placeOfBattleUser[indexX][indexY].isHere) {
                    Game.playe1.countShip1--;
                    SetPositionShip(_x, _y, 1);
                }
                repaint();
            }
            //-------------- For Two Ship
            else if (isPressedsButtons[1] && Game.playe1.countShip2 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if (CheckPlaceForShip(indexX, indexY, 2))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip2--;
                    SetPositionShip(_x, _y, 2);
                }
                repaint();
            }
            //-------------- For Three Ship
            else if (isPressedsButtons[2] && Game.playe1.countShip3 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if (CheckPlaceForShip(indexX, indexY, 3))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip3--;
                    SetPositionShip(_x, _y, 3);
                }
                repaint();
            }
            //-------------- For Four Ship
            else if (isPressedsButtons[3] && Game.playe1.countShip4 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if (CheckPlaceForShip(indexX, indexY, 4))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip4--;
                    SetPositionShip(_x, _y, 4);
                }
                repaint();
            }
        }
    }

    private int CheckSizeShip() {
        for (int i = 0; i < isPressedsButtons.length; i++) {
            if (isPressedsButtons[i])
                return (++i);
        }
        return -1;
    }

    private boolean isInWaterPoloShip() {
        int _y = (_positionMouseY / Game.heightCell);
        int _x = (_positionMouseX / Game.widthCell);
        if (_x > coordsWaterPolo.x && _x <= coordsWaterPolo.x + 10 &&
                _y > coordsWaterPolo.y && _y <= coordsWaterPolo.y + 10) {
            if (CheckSizeShip() == 1) {
                return true;
            } else if (CheckSizeShip() == 2) {
                return CheckPlusSizeShip(_x, _y, 2);
            } else if (CheckSizeShip() == 3 && (Game.playe1.countShip3 > 0)) {
                return CheckPlusSizeShip(_x, _y, 3);
            } else if (CheckSizeShip() == 4 && (Game.playe1.countShip4 > 0)) {
                return CheckPlusSizeShip(_x, _y, 4);
            }
        }
        return false;
    }

    private void SetPositionShip(int _x, int _y, int sizeShip) {
        if (isMousePressed) {
            if (direction == Direction.RIGHT) {
                for (int x = 0; x < sizeShip; x++) {
                    if (_x - 2 + x > 9) {
                        int indexX = _x - 2 - x;
                        int indexY = _y - 4;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x - x, _y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    } else {
                        int indexX = _x - 2 + x;
                        int indexY = _y - 4;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x + x, _y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    }
                }
            } else if (direction == Direction.LEFT) {
                for (int x = 0; x < sizeShip; x++) {
                    if (_x - 2 - x < 0) {
                        int indexX = _x - 2 + x;
                        int indexY = _y - 4;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x + x, _y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    } else {
                        int indexX = _x - 2 - x;
                        int indexY = _y - 4;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x - x, _y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    }
                }
            } else if (direction == Direction.DOWN) {
                for (int y = 0; y < sizeShip; y++) {
                    if (_y - 2 + y > 9) {
                        int indexX = _x - 2;
                        int indexY = _y - 4 - y;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y - y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    } else {
                        int indexX = _x - 2;
                        int indexY = _y - 4 + y;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y + y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    }
                }
            } else if (direction == Direction.UP) {
                for (int y = 0; y < sizeShip; y++) {
                    if (_y - 4 - y < 0) {
                        int indexX = _x - 2;
                        int indexY = _y - 4 + y;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y + y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;
                    } else {
                        int indexX = _x - 2;
                        int indexY = _y - 4 - y;

                        Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y - y + 2);
                        Game.placeOfBattleUser[indexX][indexY].isHere = true;

                    }
                }
            }
        }
    }


    private boolean CheckPlusSizeShip(int _x, int _y, int sizeShip) {
        if (_x + sizeShip > coordsWaterPolo.x + 10 && direction == Direction.RIGHT)
            direction = Direction.LEFT;
        if (_x - sizeShip < coordsWaterPolo.x && direction == Direction.LEFT)
            direction = Direction.RIGHT;
        if (_y + sizeShip > coordsWaterPolo.y + 10 && direction == Direction.DOWN)
            direction = Direction.UP;
        if (_y - sizeShip < coordsWaterPolo.y && direction == Direction.UP)
            direction = Direction.DOWN;
        return true;
    }

    private void GetNextDirection() {
        int index = direction.ordinal();
        int nextIndex = index + 1;
        com.company.Direction[] dir = com.company.Direction.values();
        nextIndex %= dir.length;
        direction = dir[nextIndex];
    }

    private boolean CheckPlaceForShip(int indexX, int indexY, int sizeShip) {
        Coord coordBegin = new Coord(indexX, indexY);
        Coord coordEnd = null;

        if (direction == Direction.RIGHT) {
            coordEnd = new Coord(coordBegin.x + sizeShip, coordBegin.y);
        } else if (direction == Direction.LEFT) {
            coordEnd = coordBegin;
            coordBegin = new Coord(coordBegin.x - sizeShip, coordBegin.y);
        } else if (direction == Direction.DOWN) {
            coordEnd = new Coord(coordBegin.x, coordBegin.y + sizeShip);
        } else if (direction == Direction.UP) {
            coordEnd = coordBegin;
            coordBegin = new Coord(coordBegin.x, coordBegin.y - sizeShip);
        }

        boolean check = false;
        if (coordBegin != null && coordEnd != null) {
            for (int x = coordBegin.x - 1; x <= coordEnd.x + 1; x++) {
                for (int y = coordBegin.y - 1; y <= coordEnd.y + 1; y++) {
                    if (x >= 0 && y >= 0 && x < 10 && y < 10) {
                        if (Game.placeOfBattleUser[x][y].isHere) {
                            check = true;
                        }
                    }
                }
            }
        }
        return check;
    }
}