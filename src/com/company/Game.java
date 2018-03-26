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
    public static Ship placeOfBattleUser[][];
    public static Ship placeOfBattleEnemy[][];
    public static final int widthCell = 36;
    public static final int heightCell = 36;
    public static JPanel jPanel = null;
    public static Player playe1 = new Player();

    public static JButton btnPvpLocal = null;
    public static JButton btnPvP = null;
    public static JButton btnPvS = null;
    public static JButton btnSettings = null;
    public static TCPConnection tcpConnection = null;

    private Coord sizeWindow = new Coord(1060, 800);

    public static Coord coordWaterPolo1 = new Coord(1, 5);
    public static Coord coordWaterPolo2 = new Coord(coordWaterPolo1.x + 16, coordWaterPolo1.y);
    private final Coord coordVS = new Coord(((coordWaterPolo1.x + 12) * widthCell - 7), ((coordWaterPolo1.y + 4) * heightCell));

    private JLabel lableCongratulation;

    int _positionMouseX = 0;
    int _positionMouseY = 0;

    /*==========================Begin block checked============================*/
    private static boolean isSetShips = false;
    public static boolean isReceiveShips = false;
    public static boolean stepIsTrue = true;
    /*===========================End block checked=============================*/
    public static Player playerUser = null;
    public static Player playerEnemy = null;
    private static Thread sentSreverData = null;
    public static Image img = null;
    public static boolean isEndGame = false;

    private static String cheatCode = "";
    private static boolean threadSleep = true;


    public Game(String[] args) {
        if (args.length > 0)
            cheatCode = args[0];
        System.out.println(cheatCode);
        this.SetShipNULL();
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
        try {
            img = ImageIO.read(new File("img/buttons/button.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        jPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (Settings.howSelect != "") {
                    String str = Settings.howSelect;
                    setTitle("Sea Battle - \"" + str.substring(0, 1).toUpperCase() + str.substring(1) + "\"");
                }
                super.paintComponent(g);
                g.drawImage(getImage("background1.png"), 0, 0, this);


                DrawWaterPolo(coordWaterPolo1, g);
                DrawShipsOnWaterPolo(new Coord(coordWaterPolo1.x, coordWaterPolo1.y), g);
                //DrawBeaten(coordWaterPolo1, g);
                DrawWaterPolo(coordWaterPolo2, g);
                if (cheatCode.equals("ShowEnemyShips"))
                DrawShipsOnWaterPoloEnemy(new Coord(coordWaterPolo2.x, coordWaterPolo2.y), g);
                //DrawBeaten(coordWaterPolo2, g);

                g.drawImage(getImage("VS.png"), coordVS.x, coordVS.y, 108, 72, this);

                DrawMainButtons(new Coord(1, 1));
                DrawShips(new Coord(4, 3), g);

                DrawBeatenEnemy(coordWaterPolo2, g);
                DrawBeatenUser(coordWaterPolo1, g);

                if (lableCongratulation == null) {
                    lableCongratulation = new JLabel();
                    lableCongratulation.setText("");
                }
                if (playerUser != null && playerEnemy != null) {
                    lableCongratulation.setText("You are playing...");
                    if (playerUser.allShips == HowMathIsBeatenUser()) {
                        lableCongratulation.setText("You lose! You failed!");
                        DrawShipsOnWaterPoloEnemy(new Coord(coordWaterPolo2.x, coordWaterPolo2.y), g);
                        stepIsTrue = false;
                        isEndGame = true;
                        try {
                            if (threadSleep) {
                                Thread.sleep(1100);
                                threadSleep = false;
                                System.out.println(threadSleep);
                            } else Thread.sleep(0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (tcpConnection != null)
                            tcpConnection.Disconnect();
                        tcpConnection = null;
                    } else if (playerEnemy.allShips == HowMathIsBeatenEnemy()) {
                        lableCongratulation.setText("Congratulation, You wined!");
                        DrawShipsOnWaterPoloEnemy(new Coord(coordWaterPolo2.x, coordWaterPolo2.y), g);
                        stepIsTrue = false;
                        isEndGame = true;
                        try {
                            if (threadSleep) {
                                Thread.sleep(1100);
                                threadSleep = false;
                                System.out.println(threadSleep);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (tcpConnection != null)
                            tcpConnection.Disconnect();
                        tcpConnection = null;
                    }
                }
                lableCongratulation.setFont(new Font("Segoe Script", Font.BOLD, 32));
                lableCongratulation.setEnabled(true);
                lableCongratulation.setBackground(new Color(255, 255, 255, 10));
                lableCongratulation.setForeground(Color.RED);
                lableCongratulation.setBorder(BorderFactory.createEmptyBorder());
                lableCongratulation.setBounds(40, 605, 500, 50);
                add(lableCongratulation);

                JLabel youLable = new JLabel(playe1.name);
                youLable.setFont(new Font("Segoe Script", Font.LAYOUT_LEFT_TO_RIGHT, 28));
                youLable.setEnabled(true);
                youLable.setBackground(new Color(255, 255, 255, 10));
                youLable.setForeground(Color.RED);
                youLable.setBorder(BorderFactory.createEmptyBorder());
                youLable.setBounds(6 * widthCell, 4 * heightCell, 500, 50);
                add(youLable);

                JLabel enemyLable = new JLabel("Enemy");
                enemyLable.setFont(new Font("Segoe Script", Font.LAYOUT_LEFT_TO_RIGHT, 28));
                enemyLable.setEnabled(true);
                enemyLable.setBackground(new Color(255, 255, 255, 10));
                enemyLable.setForeground(Color.RED);
                enemyLable.setBorder(BorderFactory.createEmptyBorder());
                enemyLable.setBounds(21 * widthCell + 15, 4 * heightCell, 500, 50);
                add(enemyLable);
                Color oldColor = new Color(255, 255, 255, 50);
                Color newColor = new Color(0, 0, 255, 15);
                g.setColor(newColor);
                if (stepIsTrue)
                    g.fillRect(2 * widthCell, 6 * heightCell, 10 * widthCell, 10 * heightCell);
                else
                    g.fillRect(18 * widthCell, 6 * heightCell, 10 * widthCell, 10 * heightCell);
                g.setColor(oldColor);
                repaint();
            }
        };
        jPanel.setLayout(null);
        jPanel.setSize(1200, 800);

        jPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!isEndGame)
                    FireOnShip(e);
            }
        });

        add(jPanel);
    }

    public static Image getImage(String name) {
        String filename = "img/" + name;
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

    /*------------------ Draw Main Buttons -------------------------*/
    public void DrawMainButtons(Coord coords) {
        for (int x = coords.x, i = 1; x <= 28; x += 7, i++) {
            if (btnPvpLocal == null || btnPvP == null || btnPvS == null || btnSettings == null) {
                JButton button = new JButton(new ImageIcon(img));

                int _x = (x) * widthCell;
                int _y = (coords.y) * heightCell;
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setHorizontalTextPosition(SwingConstants.CENTER);
                button.setFont(new Font("Segoe Script", Font.BOLD, 24));
                button.setForeground(Color.RED);
                button.setEnabled(false);
                button.setBounds(_x, _y, 215, 37);

                if (i == 4)
                    button.setEnabled(true);
                switch (i) {
                    case 1:
                        if (btnPvpLocal == null) {
                            /*==================================1================================*/
                            if (playe1.ipAddress != "" && playe1.countShip4 == 0 && playe1.countShip3 == 0
                                    && playe1.countShip2 == 0 && playe1.countShip1 == 0 && Settings.IndexCurrentTab == 0) {
                                button.setEnabled(true);
                            }
                            button.setText("PVPLocal");
                            btnPvpLocal = button;
                        }
                        break;
                    case 2:
                        if (btnPvP == null) {
                            if (playe1.ipAddress != "" && playe1.countShip4 == 0 && playe1.countShip3 == 0
                                    && playe1.countShip2 == 0 && playe1.countShip1 == 0 && Settings.IndexCurrentTab == 1) {
                                button.setEnabled(true);
                            }
                            button.setText("PVP");
                            btnPvP = button;
                        }
                        break;
                    case 3:
                        if (btnPvS == null) {
                            if (playe1.ipAddress != "" && playe1.countShip4 == 0 && playe1.countShip3 == 0
                                    && playe1.countShip2 == 0 && playe1.countShip1 == 0 && Settings.IndexCurrentTab == 2) {
                                button.setEnabled(true);
                            }
                            button.setText("PVS");
                            btnPvS = button;
                        }
                        break;
                    case 4:
                        if (btnSettings == null) {

                            button.setText("Settings");
                            btnSettings = button;
                        }
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
                                SetButtonEnubleToFalse();
                                btnSettingsEvent();
                            }
                        } else if (System.identityHashCode(btnPvS) == System.identityHashCode(button)) {
                            if (btnPvS.isEnabled()) {
                                isEndGame = false;
                                btnPvpLocal = null;
                                btnPvP = null;
                                SetButtonEnubleToFalse();
                                btnPvSEvent();
                            }
                        } else if (System.identityHashCode(btnPvP) == System.identityHashCode(button)) {
                            if (btnPvP.isEnabled()) {
                                isEndGame = false;
                                btnPvpLocal = null;
                                btnPvS = null;
                                SetButtonEnubleToFalse();
                                btnPvPEvent();
                            }
                        } else if (System.identityHashCode(btnPvpLocal) == System.identityHashCode(button)) {
                            if (btnPvpLocal.isEnabled()) {
                                isEndGame = false;
                                btnPvS = null;
                                btnPvP = null;
                                SetButtonEnubleToFalse();
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
                Game.stepIsTrue = false;
                Thread threadServer = new Thread(new PVPLocal());
                threadServer.setDaemon(true);
                threadServer.start();
            } else if (Settings.howSelect == "client") {
                Thread threadClient = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (playe1.ipAddress != "") {
                                tcpConnection = new TCPConnection(Game.this, playe1.ipAddress, 8189);
                                tcpConnection.SendData(new Gson().toJson(placeOfBattleUser), 1);
                            }
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
        if (Settings.group != null) {
            Thread threadClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (playe1.ipAddress != "") {
                            tcpConnection = new TCPConnection(Game.this, playe1.ipAddress, 8190);
                            tcpConnection.SendData(new Gson().toJson(placeOfBattleUser), 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadClient.setDaemon(true);
            threadClient.start();
        }
    }

    private void btnPvSEvent() {
        if (Settings.group != null) {
            Thread threadClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (playe1.ipAddress != "") {
                            tcpConnection = new TCPConnection(Game.this, playe1.ipAddress, 8191);
                            tcpConnection.SendData(Settings.Complexity, 7);
                            tcpConnection.SendData(new Gson().toJson(placeOfBattleUser), 1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadClient.setDaemon(true);
            threadClient.start();
        }
    }

    private void btnSettingsEvent() {
        if (isEndGame) {
            if (tcpConnection != null && tcpConnection.rxThread != null)
                tcpConnection.Disconnect();
            SetShipNULL();
        }
        new Settings().setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    /* ----------------------------- End Main methods ----------------------------*/
    @Override
    public synchronized void onConnectionReady(TCPConnection tcpConnection) {
        System.out.println("Connected!");
    }

    @Override
    public synchronized void onReceive(TCPConnection tcpConnection, String user, int enemyOrUser) {
        enemyOrUser -= 48;

        if (Settings.IndexCurrentTab == 0) {
            Gson gson = new Gson();
            if (enemyOrUser == 1) {
                if (isReceiveShips) {
                    Game.placeOfBattleEnemy = gson.fromJson("[" + user, Ship[][].class);
                } else {
                    isReceiveShips = true;
                    Game.placeOfBattleEnemy = gson.fromJson(user, Ship[][].class);
                    if (playerUser == null && playerEnemy == null) {
                        playerUser = new Player();
                        playerEnemy = new Player();
                        Game.jPanel.repaint();
                    }
                }
            } else if (enemyOrUser == 0) {
                Game.placeOfBattleUser = gson.fromJson("[" + user, Ship[][].class);
            }
            if (sentSreverData == null) {
                sentSreverData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            tcpConnection.SendData(gson.toJson(placeOfBattleEnemy), 0);
                        }
                    }
                });
                sentSreverData.setDaemon(true);
                sentSreverData.start();
            }
        } else if (Settings.IndexCurrentTab == 1 || Settings.IndexCurrentTab == 2) {
            if (enemyOrUser == 1) {
                if (user.charAt(0) == '[' && user.charAt(1) == '[')
                    Game.placeOfBattleEnemy = new Gson().fromJson(user, Ship[][].class);
                else
                    Game.placeOfBattleEnemy = new Gson().fromJson("[" + user, Ship[][].class);
                if (Game.playerUser == null && Game.playerEnemy == null) {
                    Game.playerUser = Game.playe1;
                    Game.playerEnemy = new Player();
                }

            } else if (enemyOrUser == 0) {
                if (user.charAt(0) == '[' && user.charAt(1) == '[')
                    Game.placeOfBattleUser = new Gson().fromJson(user, Ship[][].class);
                else
                    Game.placeOfBattleUser = new Gson().fromJson("[" + user, Ship[][].class);
            }
            if (sentSreverData == null) {
                sentSreverData = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            if (tcpConnection.socket.isClosed()) {
                                sentSreverData.interrupt();
                                break;
                            }
                            if (Settings.IndexCurrentTab == 1 || Settings.IndexCurrentTab == 2) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                            tcpConnection.SendData(new Gson().toJson(placeOfBattleEnemy), 0);
                        }
                    }
                });
                sentSreverData.setDaemon(true);
                sentSreverData.start();
            }

        }
        Game.jPanel.repaint();
    }

    @Override
    public synchronized void onReceive1(TCPConnection tcpConnection, int step) {
        if (step != 0)
            Game.stepIsTrue = true;
        else
            Game.stepIsTrue = false;
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
        if (stepIsTrue) {
            _positionMouseX = e.getX();
            _positionMouseY = e.getY();
            int _y = (_positionMouseY / Game.heightCell);
            int _x = (_positionMouseX / Game.widthCell);
            if (!CheckPlaceForShip(_x, _y)) {
                stepIsTrue = false;
                SetCircleSlip();
                if (tcpConnection != null) {
                    tcpConnection.YourStep(4);
                    if (Settings.howSelect == "server") {
                        PVPLocal.SendYourStep(4);
                    }
                }
            }

            if (isInWaterPoloShip(_x, _y)) {
                if (!CheckPlaceForShip(_x, _y)) {
                    placeOfBattleEnemy[_x - 18][_y - 6].slip = true;
                } else {
                    placeOfBattleEnemy[_x - 18][_y - 6].beaten = true;
                }
                if (isReceiveShips) {
                    if (tcpConnection != null) {
                        tcpConnection.SendData(new Gson().toJson(placeOfBattleEnemy), 0);
                        if (Settings.howSelect == "server") {
                            PVPLocal.SendMsgAllClient(new Gson().toJson(placeOfBattleEnemy), 0);
                        }
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
        if (indexX - 18 >= 0 && indexY - 6 >= 0 && indexX - 18 < 10 && indexY - 6 < 10)
            if (Game.placeOfBattleEnemy[indexX - 18][indexY - 6].isHere)
                return true;
        return false;
    }

    private void SetCircleSlip() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (placeOfBattleEnemy[i][j].beaten) {
                    SetCircleSlip(new Coord(i, j));
                }
            }
        }
    }

    private void SetCircleSlip(Coord coord) {
        for (int x = coord.x - 1; x <= coord.x + 1; x++) {
            for (int y = coord.y - 1; y <= coord.y + 1; y++) {
                if (x >= 0 && x < 10 &&
                        y >= 0 && y < 10 &&
                        !CheckPlaceForShip(x + 18, y + 6)) {
                    placeOfBattleEnemy[x][y].slip = true;
                }
            }
        }
    }

    private int HowMathIsBeatenEnemy() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (placeOfBattleEnemy[i][j].beaten)
                    count++;
            }
        }
        return count;
    }

    private int HowMathIsBeatenUser() {
        int count = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (placeOfBattleUser[i][j].beaten)
                    count++;
            }
        }
        return count;
    }

    private void SetButtonEnubleToFalse() {
        if (btnPvS != null && btnPvS.isEnabled())
            btnPvS.setEnabled(false);
        if (btnPvP != null && btnPvP.isEnabled())
            btnPvP.setEnabled(false);
        if (btnPvpLocal != null && btnPvpLocal.isEnabled())
            btnPvpLocal.setEnabled(false);
    }

    public static void SetShipNULL() {
        playe1 = new Player();
        Game.playerUser = null;
        Game.playerEnemy = null;
        Game.isEndGame = false;

        /*==========================Begin block checked============================*/
        isSetShips = false;
        isReceiveShips = false;
        stepIsTrue = true;
        /*===========================End block checked=============================*/
        sentSreverData = null;
        PVPLocal.isReceiveShips = false;
        placeOfBattleUser = new Ship[][]
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
        ;
        placeOfBattleEnemy = new Ship[][]
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
    }
}