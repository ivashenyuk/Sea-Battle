package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Settings extends JFrame {
    private Coord sizeWindow = new Coord(825, 600);
    public static String howSelect = "";

    private JPanel jPanel;
    private boolean isPressedsButtons[] = { false, false, false, false};
    private static boolean isMousePressed = false;
    private static Coord coordsWaterPolo = new Coord(1,3);
    private static int positionMouseX = 0;
    private static int positionMouseY = 0;

    int _positionMouseX = 0;
    int _positionMouseY = 0;

    private com.company.Direction direction = com.company.Direction.RIGHT;
    public static ButtonGroup group = null;
    private static boolean isPressedMouseLeftButton;

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
        UIManager.put("TabbedPane.contentOpaque", Boolean.FALSE);
        UIManager.put("TabbedPane.tabsOpaque", Boolean.FALSE);
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if((isPressedsButtons[0] ||
                        isPressedsButtons[1] ||
                        isPressedsButtons[2] ||
                        isPressedsButtons[3]) && isMousePressed) {
                    SetShip(g);
                    repaint();
                }
                g.drawImage(Game.getImage("background1.png"), 0, 0, this);
                Game.DrawWaterPolo(coordsWaterPolo, g);

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
                oneShip.setBounds(_x, _y, Game.widthCell, 37);
                oneShip.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[0]) {
                            isPressedMouseLeftButton = true;
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

                Game.DrawOneShips(new Coord(coords.x,coords.y), g);
                Game.DrawSymbols(new Coord(coords.x+1,coords.y), Game.playe1.countShip1, g);

                _x = (coords.x+4)*Game.heightCell;
                _y = coords.y*Game.widthCell;
                twoShip.setBorderPainted(false);
                twoShip.setFocusPainted(false);
                twoShip.setContentAreaFilled(false);
                twoShip.setBounds(_x, _y, 2*Game.widthCell, 37);
                twoShip.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!isPressedsButtons[1]) {
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(true);
                            oneShip.setBorderPainted(false);
                            twoShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, true, false, false);
                        }
                        else{
                            isPressedMouseLeftButton = false;
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

                Game.DrawTwoShips(Direction.RIGHT, new Coord(coords.x,coords.y), g);
                Game.DrawSymbols(new Coord(coords.x+6,coords.y), Game.playe1.countShip2, g);

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
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(false);
                            threeShip.setBorderPainted(true);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            threeShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, true, false);
                        }
                        else{
                            isPressedMouseLeftButton = false;
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

                Game.DrawThreeShips(Direction.RIGHT, new Coord(coords.x,coords.y), g);
                Game.DrawSymbols(new Coord(coords.x+12,coords.y), Game.playe1.countShip3, g);

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
                            isPressedMouseLeftButton = true;
                            fourShip.setBorderPainted(true);
                            threeShip.setBorderPainted(false);
                            twoShip.setBorderPainted(false);
                            oneShip.setBorderPainted(false);
                            fourShip.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                            SetValueIsPressetBtn(false, false, false, true);
                        }
                        else{
                            isPressedMouseLeftButton = false;
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

                Game.DrawFourShips(Direction.RIGHT, new Coord(coords.x,coords.y), g);
                Game.DrawSymbols(new Coord(coords.x+19,coords.y), Game.playe1.countShip4, g);


                Game.DrawShipsOnWaterPolo(new Coord(Game.placeOfBattleUser[0][0].coord.x, Game.placeOfBattleUser[0][0].coord.y), g, 1);

                Point point = MouseInfo.getPointerInfo().getLocation();
                SwingUtilities.convertPointFromScreen(point, this);
                _positionMouseX = point.x;
                _positionMouseY = point.y;

                if(isPressedMouseLeftButton){
                    if(CheckSizeShip() == 1 && (Game.playe1.countShip1 > 0)) {
                        Game.DrawOneShips(new Coord((_positionMouseX / Game.widthCell), (_positionMouseY / Game.heightCell)), g);
                    } else if(CheckSizeShip() == 2 && (Game.playe1.countShip2 > 0 && isInWaterPoloShip())) {
                        Game.DrawTwoShips(direction, new Coord((_positionMouseX / Game.widthCell-4), (_positionMouseY / Game.heightCell)), g);
                    } else if(CheckSizeShip() == 3 && (Game.playe1.countShip3 > 0 && isInWaterPoloShip())) {
                        Game.DrawThreeShips(direction, new Coord((_positionMouseX / Game.widthCell-9), (_positionMouseY / Game.heightCell)), g);
                    } else if(CheckSizeShip() == 4 && (Game.playe1.countShip4 > 0 && isInWaterPoloShip())) {
                        Game.DrawFourShips(direction, new Coord((_positionMouseX / Game.widthCell-15), (_positionMouseY / Game.heightCell)), g);
                    }
                }



               repaint();
            }
        };
        jPanel.setLayout(null);
        jPanel.setSize(1200, 800);
        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;
                    positionMouseX = e.getX();
                    positionMouseY = e.getY();
                    jPanel.repaint();
                }
                else{
                    isMousePressed = false;
                }
                if(e.getButton() == MouseEvent.BUTTON3){
                    GetNextDirection();
                }
            }

        });

        //----------------------- Name User -----------------------
        JTextField nameUser = new JTextField(16);

        nameUser.setBorder(BorderFactory.createLineBorder(new Color(41, 171, 226)));
        nameUser.setBackground(new Color(255,255,255, 10));
        nameUser.setForeground(Color.RED);
        //nameUser.setPlaceholder("Your name...");
        nameUser.setFont(new Font("Segoe Script", Font.BOLD, 18));
        nameUser.setBounds(0, 50 , 321, 35);
        nameUser.setMargin(new Insets(30,0,100,0));
        nameUser.setMaximumSize(new Dimension(321, 35));
        //Player.name = nameUser.getText();
        //jPanel.add(nameUser);
        //----------------------- IP for connection -----------------------
        JTextField ipForConnection = new JTextField(16);

        ipForConnection.setBorder(BorderFactory.createLineBorder(new Color(41, 171, 226)));
        ipForConnection.setBackground(new Color(255,255,255, 10));
        ipForConnection.setForeground(Color.RED);
        //ipForConnection.setPlaceholder("127.0.0.1:8080");
        ipForConnection.setFont(new Font("Segoe Script", Font.BOLD, 18));
        ipForConnection.setBounds(0, 215 , 321, 35);
        ipForConnection.setMaximumSize(new Dimension(321, 35));
        //Player.ipAddress = ipForConnection.getText();
        //jPanel.add(ipForConnection);

        JRadioButton serverRadioButton = new JRadioButton();
        JRadioButton clientRadioButton = new JRadioButton();
        serverRadioButton.setActionCommand("server");
        clientRadioButton.setActionCommand("client");

        group = new ButtonGroup();
        group.add(serverRadioButton);
        group.add(clientRadioButton);
        howSelect = "server";
        serverRadioButton.setSelected(true);
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                JRadioButton radioButton = (JRadioButton)e.getSource();
                if(radioButton == serverRadioButton && radioButton.isSelected()){
                    howSelect = "server";
                } else  if(radioButton == clientRadioButton && radioButton.isSelected()){
                    howSelect = "client";
                }
            }
        };
        if( howSelect == "client") {
            clientRadioButton.setSelected(true);
            serverRadioButton.setSelected(false);

        } else if(howSelect == "server") {
            serverRadioButton.setSelected(true);
            clientRadioButton.setSelected(false);

        }
        serverRadioButton.addChangeListener(changeListener);
        clientRadioButton.addChangeListener(changeListener);


        JLabel lableServer = new JLabel();
        lableServer.setText("Server ");
        lableServer.setFont(new Font("Segoe Script", Font.BOLD, 24));
        //lableServer.setCaretColor((Color.red));
        lableServer.setEnabled(true);
        lableServer.setBackground(new Color(255,255,255, 10));
        lableServer.setForeground(Color.RED);
        lableServer.setBorder(BorderFactory.createEmptyBorder());

        JLabel lableClient = new JLabel();
        lableClient.setText("Client ");
        lableClient.setFont(new Font("Segoe Script", Font.BOLD, 24));
        //lableServer.setCaretColor((Color.red));
        lableClient.setEnabled(true);
        lableClient.setBackground(new Color(255,255,255, 10));
        lableClient.setForeground(Color.RED);
        lableClient.setBorder(BorderFactory.createEmptyBorder());

        JLabel lableName = new JLabel();
        lableName.setText("Your Name ");
        lableName.setFont(new Font("Segoe Script", Font.BOLD, 22));
        //lableServer.setCaretColor((Color.red));
        lableName.setEnabled(true);
        lableName.setBackground(new Color(255,255,255, 10));
        lableName.setForeground(Color.RED);
        lableName.setBorder(BorderFactory.createEmptyBorder());

        JLabel lableIP = new JLabel();
        lableIP.setText("IP - 127.0.0.0:8080");
        lableIP.setFont(new Font("Segoe Script", Font.BOLD, 22));
        //lableServer.setCaretColor((Color.red));
        lableIP.setEnabled(true);
        lableIP.setBackground(new Color(255,255,255, 10));
        lableIP.setForeground(Color.RED);
        lableIP.setBorder(BorderFactory.createEmptyBorder());


        //створюємо вкладку
        JTabbedPane tabby = new JTabbedPane(JTabbedPane.TOP);

        //створюємо панелі для вкладок
        JPanel panel1= new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel panel2 = new JPanel();
        //panel1.setLayout(new GridLayout(3, 2));

        panel1.add(lableServer);
        panel1.add(serverRadioButton);

        panel1.add(lableClient);
        panel1.add(clientRadioButton);

        panel1.add(lableName);
        panel1.add(nameUser);
        panel1.add(lableIP);
        panel1.add(ipForConnection);

        panel1.setBackground(new Color(255, 255, 255, 10));
        panel1.setBorder(BorderFactory.createEmptyBorder());
        //на другу панель додаємо просто напис
        panel2.add(new JLabel("Друга вкладка"));
        panel2.setBackground(new Color(255, 255, 255, 10));
        panel2.setBorder(BorderFactory.createEmptyBorder());


        //додаємо панелі у JTabbedPane
        tabby.addTab("PVPLocal", panel1);
        tabby.addTab("Друга", panel2);
        // додаємо вкладки у фрейм

        jPanel.add(tabby);
        tabby.setBounds(463,143, 321, 250);

        add(jPanel);
    }
    private void SetValueIsPressetBtn(boolean v1, boolean v2, boolean v3, boolean v4){
        isPressedsButtons[0] = v1;
        isPressedsButtons[1] = v2;
        isPressedsButtons[2] = v3;
        isPressedsButtons[3] = v4;
    }
    private void SetShip(Graphics g) {
        Coord coordBegin = null;
        int _y = (positionMouseY / Game.heightCell);
        int _x = (positionMouseX / Game.widthCell);
        if (_x > coordsWaterPolo.x && _x <= coordsWaterPolo.x + 10 &&
            _y > coordsWaterPolo.y && _y <= coordsWaterPolo.y + 10) {
            //-------------- For One Ship
            if (isPressedsButtons[0] && Game.playe1.countShip1 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if(coordBegin == null)
                    coordBegin = new Coord(indexX, indexY);
                if(CheckPlaceForShip(indexX, indexY, 1))
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

                if(coordBegin == null)
                    coordBegin = new Coord(indexX, indexY);
                if(CheckPlaceForShip(indexX, indexY, 2))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip2--;
                    SetPositionShip(_x, _y, 2);
                }
                repaint();
            }
            //-------------- For Three Ship
            else if (isPressedsButtons[2]  && Game.playe1.countShip3 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if(coordBegin == null)
                    coordBegin = new Coord(indexX, indexY);
                if(CheckPlaceForShip(indexX, indexY, 3))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip3--;
                    SetPositionShip(_x, _y, 3);
                }
                repaint();
            }
            //-------------- For Four Ship
            else if (isPressedsButtons[3]  && Game.playe1.countShip4 > 0) {
                int indexX = _x - 2;
                int indexY = _y - 4;

                if(coordBegin == null)
                    coordBegin = new Coord(indexX, indexY);
                if(CheckPlaceForShip(indexX, indexY, 4))
                    return;

                if (!Game.placeOfBattleUser[_x - 2][_y - 4].isHere) {
                    Game.playe1.countShip4--;
                    SetPositionShip(_x, _y, 4);
                }
                repaint();
            }
        }
    }
    private int CheckSizeShip(){
        for(int i = 0; i < isPressedsButtons.length; i++){
            if(isPressedsButtons[i])
                return (++i);
        }
        return -1;
    }
    private boolean isInWaterPoloShip(){
        int _y = (_positionMouseY / Game.heightCell);
        int _x = (_positionMouseX / Game.widthCell);
        if (_x > coordsWaterPolo.x && _x <= coordsWaterPolo.x + 10 &&
                _y > coordsWaterPolo.y && _y <= coordsWaterPolo.y + 10) {
            if(CheckSizeShip() == 1) {
                return true;
            } else if(CheckSizeShip() == 2) {
               return CheckPlusSizeShip(_x, _y, 2);
            } else if(CheckSizeShip() == 3 && (Game.playe1.countShip3 > 0)) {
                return CheckPlusSizeShip(_x, _y, 3);
            } else if(CheckSizeShip() == 4 && (Game.playe1.countShip4 > 0)) {
                return CheckPlusSizeShip(_x, _y, 4);
            }
        }
        return  false;
    }
    private void SetPositionShip(int _x, int _y, int sizeShip) {
        if( direction == Direction.RIGHT) {
            for (int x = 0; x < sizeShip; x++) {
                if(_x - 2+x > 9) {
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
        }
        else if( direction == Direction.LEFT) {
            for (int x = 0; x < sizeShip; x++) {
                if(_x - 2-x < 0) {
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
        }
        else if( direction == Direction.DOWN) {
            for (int y = 0; y < sizeShip; y++) {
                if(_y - 2+y > 9) {
                    int indexX = _x - 2;
                    int indexY = _y - 4-y;

                    Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y - y + 2);
                    Game.placeOfBattleUser[indexX][indexY].isHere = true;
                } else {
                    int indexX = _x - 2;
                    int indexY = _y - 4 + y;

                    Game.placeOfBattleUser[indexX][indexY].coord = new Coord(_x, _y + y + 2);
                    Game.placeOfBattleUser[indexX][indexY].isHere = true;
                }
            }
        }
        else if( direction == Direction.UP) {
            for (int y = 0; y < sizeShip; y++) {
                if(_y - 4 - y < 0) {
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
    private boolean CheckPlusSizeShip(int _x, int _y, int sizeShip){
        if(_x+sizeShip > coordsWaterPolo.x+10 && direction == Direction.RIGHT)
            direction = Direction.LEFT;
        if(_x-sizeShip < coordsWaterPolo.x && direction == Direction.LEFT)
            direction = Direction.RIGHT;
        if(_y+sizeShip > coordsWaterPolo.y+10 && direction == Direction.DOWN)
            direction = Direction.UP;
        if(_y-sizeShip < coordsWaterPolo.y && direction == Direction.UP)
            direction = Direction.DOWN;
        return true;
    }
    private void GetNextDirection(){
        int index = direction.ordinal();
        int nextIndex = index + 1;
        com.company.Direction[] dir = com.company.Direction.values();
        nextIndex %= dir.length;
        direction = dir[nextIndex];
    }
    private boolean CheckPlaceForShip(int indexX, int indexY, int sizeShip){
        Coord coordBegin = new Coord(indexX, indexY);
        Coord coordEnd = null;

            if( direction == Direction.RIGHT) {
                coordEnd = new Coord(coordBegin.x+sizeShip, coordBegin.y);
            }
            else if( direction == Direction.LEFT) {
                coordEnd = coordBegin;
                coordBegin = new Coord(coordBegin.x-sizeShip, coordBegin.y);
            }
            else if( direction == Direction.DOWN) {
                coordEnd = new Coord(coordBegin.x, coordBegin.y+sizeShip);
            }
            else if( direction == Direction.UP) {
                coordEnd = coordBegin;
                coordBegin = new Coord(coordBegin.x, coordBegin.y-sizeShip);
            }

        boolean check = false;
        if(coordBegin != null  && coordEnd != null) {
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