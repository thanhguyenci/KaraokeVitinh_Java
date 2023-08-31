package thanh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Video extends JFrame implements ActionListener {
    public Video() {
        super("");
        //setResizable(false);
        setSize(new Dimension(640, 480));
        //setMinimumSize(new Dimension(800, 600));
        //setMaximumSize(new Dimension(1280, 720));
        //getContentPane().setBackground(Color.WHITE);
        setTitle("Video");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
