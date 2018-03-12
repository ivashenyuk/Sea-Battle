package com.company;


import com.google.gson.Gson;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class Game extends JFrame implements TCPConnectionListener {
    public static Ship placeOfBattleUser[][] =
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
    public static Ship placeOfBattleEnemy[][] =
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
    public static JPanel jPanel = null;

    private JButton btnPvpLocal = null;
    private JButton btnPvP = null;
    private JButton btnPvS = null;
    private JButton btnSettings = null;
    public static TCPConnection tcpConnection = null;
    private MyEvents events = null;

    public static Player playe1 = new Player();
    private Coord coord;
    private Coord sizeWindow = new Coord(1060, 800);

    public static Coord coordWaterPolo1 = new Coord(1, 5);
    public static Coord coordWaterPolo2 = new Coord(coordWaterPolo1.x + 16, coordWaterPolo1.y);
    private final Coord coordVS = new Coord(((coordWaterPolo1.x + 12) * widthCell - 7), ((coordWaterPolo1.y + 4) * heightCell));

    private static boolean isServer = false;
    private JLabel lableServer;

    int _positionMouseX = 0;
    int _positionMouseY = 0;

    /*==========================Begin block checked============================*/
    private static boolean isSetShips = false;
    public static boolean isReceiveShips = false;
    public static boolean isSend = false;
    /*===========================End block checked=============================*/

    public Game() {
        this.initPanel();
        this.initFrame();
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sea Battle");
        setLocation(550, 100);
        setResizable(false);
        setVisible(true);
        setSize(sizeWindow.x, sizeWindow.y);
        setIconImage(getImage("icon.png"));
    }

    private void initPanel() {
        jPanel = null;
        btnPvpLocal = null;
        btnPvP = null;
        btnPvS = null;
        btnSettings = null;
        events = null;
        events = new MyEvents();
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(getImage("background1.png"), 0, 0, this);

                DrawWaterPolo(coordWaterPolo1, g);
                DrawShipsOnWaterPolo(new Coord(coordWaterPolo1.x, coordWaterPolo1.y), g);
                //DrawBeaten(coordWaterPolo1, g);
                DrawWaterPolo(coordWaterPolo2, g);
                DrawShipsOnWaterPoloEnemy(new Coord(coordWaterPolo2.x, coordWaterPolo2.y), g);
                //DrawBeaten(coordWaterPolo2, g);

                g.drawImage(getImage("VS.png"), coordVS.x, coordVS.y, 108, 72, this);

                DrawMainButtons(new Coord(1, 1));
                DrawShips(new Coord(4, 3), g);

                DrawBeatenEnemy(coordWaterPolo2, g);
                DrawBeatenUser(coordWaterPolo1, g);
                repaint();
            }

        };
        jPanel.setLayout(null);
        jPanel.setSize(1200, 800);

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isSend = true;
                FireOnShip(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isSend = false;
            }
        });

        lableServer = new JLabel();
        lableServer.setText(Settings.howSelect);
        lableServer.setFont(new Font("Segoe Script", Font.BOLD, 24));
        //lableServer.setCaretColor((Color.red));
        lableServer.setEnabled(true);
        lableServer.setBackground(new Color(255, 255, 255, 10));
        lableServer.setForeground(Color.RED);
        lableServer.setBorder(BorderFactory.createEmptyBorder());
        lableServer.setBounds(400, 700, 100, 50);
        jPanel.add(lableServer);

        add(jPanel);
    }

    public static Image getImage(String name) {
        String filename = "E://img/" + name;
        Image gras = Toolkit.getDefaultToolkit().getImage(filename);
        return gras;
    }

    public static void DrawShipsOnWaterPolo(Coord coords, Graphics g) {
        for (int x = coords.x, i = 0; x < coords.x + 10; x++, i++) {
            for (int y = coords.y, j = 0; y < coords.y + 10; y++, j++) {
                if (placeOfBattleUser[i][j].isHere) {
                    if (placeOfBattleUser[i][j].coord != null)
                        DrawOneShips(placeOfBattleUser[i][j].coord, g);
                    //g.drawImage(getImage("slip.png"), x * 36, y*36, null);
                }
            }
        }
    }

    public static void DrawShipsOnWaterPoloEnemy(Coord coords, Graphics g) {
        for (int x = coords.x, i = 0; x < coords.x + 10; x++, i++) {
            for (int y = coords.y, j = 0; y < coords.y + 10; y++, j++) {
                if (placeOfBattleEnemy[i][j].isHere) {
                    if (placeOfBattleEnemy[i][j].coord != null)
                        DrawOneShips(new Coord(placeOfBattleEnemy[i][j].coord.x + 16, placeOfBattleEnemy[i][j].coord.y), g);
                    //g.drawImage(getImage("slip.png"), x * 36, y*36, null);
                }
            }
        }
    }

    public static void DrawShipsOnWaterPolo(Coord coords, Graphics g, int tmp) {
        for (int x = coords.x + 1, i = 0; x < coords.x + 11; x++, i++) {
            for (int y = coords.y + 1, j = 0; y < coords.y + 11; y++, j++) {
                if (placeOfBattleUser[i][j].isHere)
                    g.drawImage(getImage("ship.png"), (placeOfBattleUser[i][j].coord.x) * 36, (placeOfBattleUser[i][j].coord.y - 2) * 36, null);
            }
        }
    }

    private void DrawBeatenEnemy(Coord coords, Graphics g) {
        for (int x = coords.x + 1, i = 0; x < coords.x + 11; x++, i++) {
            for (int y = coords.y + 1, j = 0; y < coords.y + 11; y++, j++) {
                if (placeOfBattleEnemy[i][j].slip)
                    g.drawImage(getImage("slip.png"), x * 36, y * 36, this);
                else if (placeOfBattleEnemy[i][j].beaten)
                    g.drawImage(getImage("beaten_ship.png"), x * 36, y * 36, this);
            }
        }
    }

    private void DrawBeatenUser(Coord coords, Graphics g) {
        for (int x = coords.x + 1, i = 0; x < coords.x + 11; x++, i++) {
            for (int y = coords.y + 1, j = 0; y < coords.y + 11; y++, j++) {
                if (placeOfBattleUser[i][j].slip)
                    g.drawImage(getImage("slip.png"), x * 36, y * 36, this);
                else if (placeOfBattleUser[i][j].beaten)
                    g.drawImage(getImage("beaten_ship.png"), x * 36, y * 36, this);
            }
        }
    }

    public static void DrawWaterPolo(Coord coords, Graphics g) {
        g.drawImage(getImage("waterpolo.png"), coords.x * widthCell, coords.y * heightCell, 500, 500, null);
    }

    private void DrawMainButtons(Coord coords) {
        for (int x = coords.x, i = 1; x <= 28; x += 7, i++) {
            if (btnPvpLocal == null || btnPvP == null || btnPvS == null || btnSettings == null) {

                Image img = null;
                try {
                    img = ImageIO.read(new File("E://img/buttons/button" + i + ".png"));
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
                if (i == 1 && Player.ipAddress != "")
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
    public static void DrawShips(Coord coords, Graphics g) {
        if (!isSetShips) {
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

    public static void DrawOneShips(Coord coords, Graphics g) {
        g.drawImage(getImage("ship.png"), (coords.x) * widthCell, (coords.y) * heightCell, 35, 35, null);
    }

    public static void DrawTwoShips(Direction direction, Coord coords, Graphics g) {
        if (direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 2; x++, i++) {
                DrawOneShips(new Coord(x + 4, coords.y), g);
            }
        } else if (direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 2; x--, i++) {
                DrawOneShips(new Coord(x + 4, coords.y), g);
            }
        } else if (direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 2; y--, i++) {
                DrawOneShips(new Coord(coords.x + 4, y), g);
            }
        } else if (direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 2; y++, i++) {
                DrawOneShips(new Coord(coords.x + 4, y), g);
            }
        }
    }

    public static void DrawThreeShips(Direction direction, Coord coords, Graphics g) {
        if (direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 3; x++, i++) {
                DrawOneShips(new Coord(x + 9, coords.y), g);
            }
        } else if (direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 3; x--, i++) {
                DrawOneShips(new Coord(x + 9, coords.y), g);
            }
        } else if (direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 3; y--, i++) {
                DrawOneShips(new Coord(coords.x + 9, y), g);
            }
        } else if (direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 3; y++, i++) {
                DrawOneShips(new Coord(coords.x + 9, y), g);
            }
        }
    }

    public static void DrawFourShips(Direction direction, Coord coords, Graphics g) {

        if (direction == Direction.RIGHT) {
            for (int x = coords.x, i = 0; i < 4; x++, i++) {
                DrawOneShips(new Coord(x + 15, coords.y), g);
            }
        } else if (direction == Direction.LEFT) {
            for (int x = coords.x, i = 0; i < 4; x--, i++) {
                DrawOneShips(new Coord(x + 15, coords.y), g);
            }
        } else if (direction == Direction.UP) {
            for (int y = coords.y, i = 0; i < 4; y--, i++) {
                DrawOneShips(new Coord(coords.x + 15, y), g);
            }
        } else if (direction == Direction.DOWN) {
            for (int y = coords.y, i = 0; i < 4; y++, i++) {
                DrawOneShips(new Coord(coords.x + 15, y), g);
            }
        }
    }

    public static void DrawSymbols(Coord coords, int number, Graphics g) {
        g.drawImage(getImage("symbols/-.png"), (coords.x) * widthCell, coords.y * heightCell, 35, 35, null);
        g.drawImage(getImage("numbers/" + number + ".png"), (coords.x + 1) * widthCell, coords.y * heightCell, 35, 35, null);
    }

    /* ----------------------------- Main methods ----------------------------*/
    private synchronized void btnPvpLocalEvent() {
        if (Settings.group != null) {
            if (Settings.howSelect == "server") {
                Thread threadServer = new Thread(new PVPLocal());
                threadServer.setDaemon(true);
                threadServer.start();
            } else if (Settings.howSelect == "client") {
                Thread threadClient = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tcpConnection = new TCPConnection(Game.this, "127.0.0.1", 8189);
                            Gson gson = new Gson();
                            tcpConnection.SendData(gson.toJson(placeOfBattleUser), 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                threadClient.setDaemon(true);
                threadClient.start();
            }
        }
    }

    private void btnPvPEvent() {
    }

    private void btnPvSEvent() {
    }

    private void btnSettingsEvent() {
        new Settings().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /* ----------------------------- End Main methods ----------------------------*/
    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connected!");
    }

    static Thread sentSreverData = null;

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String user, int enemyOrUser) {
        enemyOrUser -= 48;
        Gson gson = new Gson();
        if (enemyOrUser == 1) {
            if (isReceiveShips) {
                Game.placeOfBattleEnemy = gson.fromJson("[" + user, Ship[][].class);
            } else {
                isReceiveShips = true;
                Game.placeOfBattleEnemy = gson.fromJson(user, Ship[][].class);
            }
        } else if (enemyOrUser == 0) {
            Game.placeOfBattleUser = gson.fromJson("[" + user, Ship[][].class);
        }
        if (sentSreverData == null) {
            sentSreverData = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("before send");
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("sending...");
                            tcpConnection.SendData(gson.toJson(placeOfBattleEnemy), 0);
                    }
                }
            });

            sentSreverData.setDaemon(true);
            sentSreverData.start();
        }

        Game.jPanel.repaint();
    }

    @Override
    public synchronized void onDisconnect(TCPConnection tcpConnection) {
        System.out.println("Disconnected!");
    }

    @Override
    public synchronized void onExeption(TCPConnection tcpConnection, Exception ex) {
        System.out.println("TCPConnection exeption: " + ex);
    }

    private void FireOnShip(MouseEvent e) {
        _positionMouseX = e.getX();
        _positionMouseY = e.getY();
        int _y = (_positionMouseY / Game.heightCell);
        int _x = (_positionMouseX / Game.widthCell);
        if (isInWaterPoloShip(_x, _y)) {
            Gson gson = new Gson();
            if (!CheckPlaceForShip(_x, _y)) {
                placeOfBattleEnemy[_x - 18][_y - 6].slip = true;
            } else {
                placeOfBattleEnemy[_x - 18][_y - 6].beaten = true;
            }
            if (isReceiveShips) {
                if (tcpConnection != null) {
                    tcpConnection.SendData(gson.toJson(placeOfBattleEnemy), 0);
                    if (Settings.howSelect == "server") {
                        PVPLocal.SendMsgAllClient(gson.toJson(placeOfBattleEnemy), 0);
                    }
                }
            }
        }
    }

    private boolean isInWaterPoloShip(int _x, int _y) {
        if (_x > coordWaterPolo2.x && _x <= coordWaterPolo2.x + 10 &&
                _y > coordWaterPolo2.y && _y <= coordWaterPolo2.y + 10) {
            return true;
        }
        return false;
    }

    private boolean CheckPlaceForShip(int indexX, int indexY) {
        if (Game.placeOfBattleEnemy[indexX - 18][indexY - 6].isHere)
            return true;
        return false;
    }
}
