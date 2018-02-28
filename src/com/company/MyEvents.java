package com.company;

import javax.swing.*;
import java.awt.event.*;

public class MyEvents implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null,"ALERT MESSAGE","TITLE", JOptionPane.INFORMATION_MESSAGE);
        }
}
