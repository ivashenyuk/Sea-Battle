package com.company;

import javax.swing.*;
import java.awt.*;

public class FabryComponents {
    public static JLabel CreateJLableName(String text, int size) {
        JLabel lableName = new JLabel();
        lableName.setText(text);
        lableName.setFont(new Font("Segoe Script", Font.BOLD, size));
        //lableServer.setCaretColor((Color.red));
        lableName.setEnabled(true);
        lableName.setBackground(new Color(255, 255, 255, 10));
        lableName.setForeground(Color.RED);
        lableName.setBorder(BorderFactory.createEmptyBorder());
        return lableName;
    }
    public static JTextField CreateLTextField(String text){
        JTextField nameUser = new JTextField(16);

        nameUser.setBorder(BorderFactory.createLineBorder(new Color(41, 171, 226)));
        nameUser.setBackground(new Color(255, 255, 255, 10));
        nameUser.setForeground(Color.RED);
        nameUser.setText(text);
        nameUser.setFont(new Font("Segoe Script", Font.BOLD, 18));
        nameUser.setBounds(0, 50, 321, 35);
        nameUser.setMargin(new Insets(30, 0, 100, 0));
        nameUser.setMaximumSize(new Dimension(321, 35));

        return nameUser;
    }
}
