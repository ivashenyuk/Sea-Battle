package com.company;

import com.company.Direction;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame implements TCPConnectionListener {
    public static Ship  placeOfBattleUser[][] =
            {
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
            };
    public static Ship  placeOfBattleEnemy[][] =
            {
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
                    {new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship(), new Ship()},
            };

    public static final int widthCell = 36;
    public static final int heightCell = 36;
    private JPanel jPanel = null;

    private JButton btnPvpLocal = null;
    private JButton btnPvP = null;
    private JButton btnPvS = null;
    private JButton btnSettings = null;

    private MyEvents events = null;

    public static Player playe1 = new Player();
    private Coord coord;
    private Coord sizeWindow = new Coord(1060, 800);

    public static Coord coordWaterPolo1 = new Coord(1, 5);
    public static Coord coordWaterPolo2 = new Coord(coordWaterPolo1.x+16, coordWaterPolo1.y);
    private final Coord coordVS = new Coord( ((coordWaterPolo1.x+12)*widthCell-7), ((coordWaterPolo1.y+4)*heightCell) );

    private static boolean isServer = false;
    /*==========================Begin block checked============================*/
    private static boolean isSetShips = false;
    /*===========================End block checked=============================*/

    public Game(){
        this.initPanel();
        this.initFrame();
    }

    private void initFrame(){
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");
        setLocation(550,100);
        setResizable(false);
        setVisible(true);
        setSize(sizeWindow.x, sizeWindow.y);
        setIconImage(getImage("icon.png"));
    }

    private void initPanel(){
        jPanel = null;
        btnPvpLocal = null;
        btnPvP = null;
        btnPvS = null;
        btnSettings = null;
        events = null;
        events = new MyEvents();
        jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(getImage("background1.png"), 0, 0, this);

                DrawWaterPolo(coordWaterPolo1, g);
                DrawShipsOnWaterPolo(new Coord(coordWaterPolo1.x, coordWaterPolo1.y), g);
                //DrawBeaten(coordWaterPolo1, g);
                DrawWaterPolo(coordWaterPolo2, g);
                //DrawBeaten(coordWaterPolo2, g);

                g.drawImage(getImage("VS.png"), coordVS.x, coordVS.y, 108, 72,this);

                DrawMainButtons(new Coord(1,1));
                DrawShips(new Coord(4, 3), g);
                repaint();
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

    public static Image getImage(String name){
        String filename = "E://img/"+name;
        Image gras = Toolkit.getDefaultToolkit().getImage(filename);
        return gras;
    }
    public static void DrawShipsOnWaterPolo(Coord coords, Graphics g){
        for (int x = coords.x, i = 0; x < coords.x+10; x++, i++){
            for (int y = coords.y, j = 0; y < coords.y+10; y++, j++) {
                if(placeOfBattleUser[i][j].isHere){
                    if(placeOfBattleUser[i][j].coord != null)
                        DrawOneShips(placeOfBattleUser[i][j].coord, g);
                        //g.drawImage(getImage("slip.png"), x * 36, y*36, null);
                }
            }
        }
    }
    public static void DrawShipsOnWaterPolo(Coord coords, Graphics g, int tmp){
        for (int x = coords.x+1, i = 0; x < coords.x+11; x++, i++){
            for (int y = coords.y+1, j = 0; y < coords.y+11; y++, j++) {
                if(placeOfBattleUser[i][j].isHere)
                    g.drawImage(getImage("ship.png"), (placeOfBattleUser[i][j].coord.x) * 36, (placeOfBattleUser[i][j].coord.y-2)*36, null);
            }
        }
    }
    private void DrawBeaten(Coord coords, Graphics g){
        for (int x = coords.x+1; x < coords.x+11; x++){
            for (int y = coords.y+1; y < coords.y+11; y++) {
                if(x*y%2==1)
                    g.drawImage(getImage("slip.png"), x * 36, y*36, this);
                if(x*y%3==2)
                    g.drawImage(getImage("ship.png"), x * 36, y*36, this);
                if(x*y%4==0)
                    g.drawImage(getImage("beaten_ship.png"), x * 36, y*36, this);
            }
        }
    }
    public static void DrawWaterPolo(Coord coords, Graphics g){
        g.drawImage(getImage("waterpolo.png"), coords.x*widthCell, coords.y*heightCell, 500, 500,null);
    }
    private void DrawMainButtons(Coord coords){

        for(int x = coords.x, i=1; x <= 28; x +=7, i++) {
            if(btnPvpLocal == null || btnPvP == null || btnPvS == null || btnSettings == null) {

                Image img = null;
                try {
                    img = ImageIO.read(new File("E://img/buttons/button"+i+".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JButton button = new JButton(new ImageIcon(img));

                int _x = (x) * widthCell;
                int _y = (coords.y) * heightCell;
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                if (i != 4)
                    button.setEnabled(false);
                if (i == 1 && Player.name != "" && Player.ipAddress != "")
                    button.setEnabled(true);
                button.setBounds(_x, _y, 215, 37);

                switch (i) {
                    case 1:
                        if (btnPvpLocal == null)
                            btnPvpLocal = button;
                        break;
                    case 2:
                        if (btnPvP == null)
                            btnPvP = button;
                        break;
                    case 3:
                        if (btnPvS == null)
                            btnPvS = button;
                        break;
                    case 4:
                        if (btnSettings == null)
                            btnSettings = button;
                        break;

                    default:
                        btnSettings = null;
                        btnPvS = null;
                        btnPvP = null;
                        btnPvpLocal = null;
                        break;
                }

                button.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if (System.identityHashCode(btnSettings) == System.identityHashCode(button)) {
                            if (btnSettings.isEnabled()) {
                                btnSettingsEvent();
                            }
                        } else if (System.identityHashCode(btnPvS) == System.identityHashCode(button)) {
                            if (btnPvS.isEnabled()) {
                                btnPvSEvent();
                            }
                        } else if (System.identityHashCode(btnPvP) == System.identityHashCode(button)) {
                            if (btnPvP.isEnabled()) {
                                btnPvPEvent();
                            }
                        } else if (System.identityHashCode(btnPvpLocal) == System.identityHashCode(button)) {
                            if (btnPvpLocal.isEnabled()) {
                                btnPvpLocalEvent();
                            }
                        }

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (button.isEnabled()) {
                            button.setLocation(_x + 10, _y + 5);
                            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                            jPanel.repaint();
                        }

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (button.isEnabled()) {
                            button.setLocation(_x, _y);
                            jPanel.repaint();
                        }
                    }
                });
                jPanel.add(button);
                jPanel.repaint();
            }
        }
    }
    /*------------------ Draw Ships -------------------------*/
    public static void DrawShips(Coord coords, Graphics g){
        if(!isSetShips) {
            DrawOneShips(new Coord(coords.x, coords.y), g);
            DrawSymbols(new Coord(coords.x + 1, coords.y), 4, g);

            DrawTwoShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
            DrawSymbols(new Coord(coords.x + 6, coords.y), 3, g);

            DrawThreeShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
            DrawSymbols(new Coord(coords.x + 12, coords.y), 2, g);

            DrawFourShips(Direction.RIGHT, new Coord(coords.x, coords.y), g);
            DrawSymbols(new Coord(coords.x + 19, coords.y), 1, g);
        }
    }
    public static void DrawOneShips(Coord coords, Graphics g){
        g.drawImage(getImage("ship.png"), (coords.x)*widthCell, (coords.y)*heightCell, 35, 35,null);
    }
    public static void DrawTwoShips(Direction direction, Coord coords, Graphics g){
        if(direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 2; x++, i++) {
                DrawOneShips(new Coord(x + 4, coords.y), g);
            }
        }
        else if(direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 2; x--, i++) {
                DrawOneShips(new Coord(x + 4, coords.y), g);
            }
        }else if(direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 2; y--, i++) {
                DrawOneShips(new Coord(coords.x + 4, y), g);
            }
        }else if(direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 2; y++, i++) {
                DrawOneShips(new Coord(coords.x + 4, y), g);
            }
        }
    }
    public static void DrawThreeShips(Direction direction, Coord coords, Graphics g){
        if(direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 3; x++, i++) {
                DrawOneShips(new Coord(x + 9, coords.y), g);
            }
        }
        else if(direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 3; x--, i++) {
                DrawOneShips(new Coord(x + 9, coords.y), g);
            }
        }else if(direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 3; y--, i++) {
                DrawOneShips(new Coord(coords.x + 9, y), g);
            }
        }else if(direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 3; y++, i++) {
                DrawOneShips(new Coord(coords.x + 9, y), g);
            }
        }
    }
    public static void DrawFourShips(Direction direction, Coord coords, Graphics g){

        if(direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 4; x++, i++) {
                DrawOneShips(new Coord(x + 15, coords.y), g);
            }
        }
        else if(direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 4; x--, i++) {
                DrawOneShips(new Coord(x + 15, coords.y), g);
            }
        }else if(direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 4; y--, i++) {
                DrawOneShips(new Coord(coords.x + 15, y), g);
            }
        }else if(direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 4; y++, i++) {
                DrawOneShips(new Coord(coords.x + 15, y), g);
            }
        }
    }

    public static void DrawSymbols(Coord coords, int number, Graphics g){
        g.drawImage(getImage("symbols/-.png"), (coords.x)*widthCell, coords.y*heightCell, 35, 35,null);
        g.drawImage(getImage("numbers/"+number+".png"), (coords.x+1)*widthCell, coords.y*heightCell, 35, 35,null);
    }
    /* ----------------------------- Main methods ----------------------------*/
    private void btnPvpLocalEvent() {
        if(isServer)
            new PVPLocal();
        else {
            try {
                TCPConnection tcpConnection = new TCPConnection(this, "127.0.0.1", 8189);
                tcpConnection.SendData("Hello world!");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void btnPvPEvent() {
    }

    private void btnPvSEvent() {
    }

    private void btnSettingsEvent(){
        new Settings().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connected!");
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String value) {
        System.out.println(value);
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Disconnected!");
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }
}
