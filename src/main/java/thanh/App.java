package thanh;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    public App() {
        super("");
        //setResizable(false);
        setSize(new Dimension(640, 200));
        //setMinimumSize(new Dimension(800, 600));
        //setMaximumSize(new Dimension(1280, 720));
        //getContentPane().setBackground(Color.WHITE);
        setTitle("KaraokeVitinh");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        Container container = getContentPane();

        try {
            //UIManager.setLookAndFeel("Nimbus");
            //UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            //UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                //Nimbus, Metal, Ocean
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 UnsupportedLookAndFeelException e) {
            System.err.println(e.getCause());
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        JButton mButton_01 = new JButton();
        mButton_01.setText("Song List");

        mButton_01.setPreferredSize(new Dimension(100, 25));

        JPanel TopPanel = new JPanel();
        //TopPanel.setBorder(null);
        TopPanel.setLayout(new BorderLayout());
        TopPanel.add(mButton_01, BorderLayout.EAST);

        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new BorderLayout());

        JPanel ControlPanel = new JPanel();
        //ControlPanel.setLayout(new BorderLayout());
        JPanel FilePanel = new JPanel();
        JPanel ReservedPanel = new JPanel();

        JButton bButton_01 = new JButton();
        bButton_01.setText("[]");
        bButton_01.setPreferredSize(new Dimension(50, 35));

        JButton bButton_02 = new JButton();
        bButton_02.setText("<<");
        bButton_02.setPreferredSize(new Dimension(50, 35));

        JButton bButton_03 = new JButton();
        bButton_03.setText(">");
        bButton_03.setPreferredSize(new Dimension(50, 35));

        JButton bButton_04 = new JButton();
        bButton_04.setText(">>");
        bButton_04.setPreferredSize(new Dimension(50, 35));

        ControlPanel.add(bButton_01);
        ControlPanel.add(bButton_02);
        ControlPanel.add(bButton_03);
        ControlPanel.add(bButton_04);

        //JLabel headerLabel;
        //JLabel statusLabel;
        //JPanel controlPanel;
        //headerLabel.setText("Control in action: JSlider");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        //slider.setSize(new Dimension(150, 50));
        //final JLabel finalStatusLabel = statusLabel;
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //finalStatusLabel.setText("Value : " + ((JSlider) e.getSource()).getValue());
            }
        });

        JButton bButton_05 = new JButton();
        bButton_05.setText("^");
        bButton_05.setPreferredSize(new Dimension(50, 35));

        JButton bButton_06 = new JButton();
        bButton_06.setText("*");
        bButton_06.setPreferredSize(new Dimension(50, 35));

        JButton bButton_07 = new JButton();
        bButton_07.setText("Reserved List");
        bButton_07.setPreferredSize(new Dimension(150, 25));
        ReservedPanel.add(bButton_07);

        FilePanel.add(slider);
        FilePanel.add(bButton_05);
        FilePanel.add(bButton_06);

        BottomPanel.add(ControlPanel, BorderLayout.WEST);
        BottomPanel.add(FilePanel, BorderLayout.EAST);

        BottomPanel.add(ReservedPanel, BorderLayout.SOUTH);

        container.add(TopPanel, BorderLayout.NORTH);
        container.add(BottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new App();
        JFrame Video = new Video();
        Video.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
