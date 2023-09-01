package thanh;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class App extends JFrame implements ActionListener {
    public static JPanel ReserverPanelcontrol, ReserverPanelSongList;
    public static JButton bButton_07;
    public static JFrame MainFrame;

    public static JScrollPane scrollforReserverPanelSongList;

    public App() {
        super("");
        //setSize(new Dimension(640, 200));
        setTitle("KaraokeVitinh");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container container = getContentPane();

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException
                 | UnsupportedLookAndFeelException e) {
            System.err.println(e.getCause());
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        JButton mButton_01 = new JButton();
        mButton_01.setText("Song List");
        mButton_01.addActionListener(this);

        mButton_01.setPreferredSize(new Dimension(100, 25));

        JPanel TopPanel = new JPanel();
        TopPanel.setLayout(new BorderLayout());
        TopPanel.add(mButton_01, BorderLayout.EAST);

        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new BorderLayout());

        JPanel ControlPanel = new JPanel();

        JPanel FilePanel = new JPanel();

        //TODO RESERVED PANEL
        JPanel ReservedPanel = new JPanel();
        ReservedPanel.setLayout(new BorderLayout());

        JPanel ReserverPanelcontrol = new JPanel();
        ReserverPanelSongList = new JPanel();
        ReserverPanelSongList.setPreferredSize(new Dimension(540, 210));
        ReserverPanelSongList.setVisible(false);

        String[] columnNames = {"#", ""};
        Object[][] rowData = {{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""},{"", ""}};
        JTable table = new JTable(rowData,columnNames);
        SongList.setTableRowsSize(table);
        //table.setRowHeight(35);
        //DefaultTableModel tablemodel = new DefaultTableModel(rowData, columnNames);
        //sortera = new TableRowSorter<>(tablemodel);
        //String format = String.format("%07d", 0);
        //String result = String.format(format, num);
        /*for (int i = 1; i <= 10000; i++) {
            String format = String.format("%06d", i);
            //String result = String.format(format, i);
            tablemodel.addRow(new Object[]{String.format(format, i), " - Trọn Kiếp Bình Yên - 123456"});
        }*/

        scrollforReserverPanelSongList = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollforReserverPanelSongList.setPreferredSize(new Dimension(540, 200));

        ReserverPanelSongList.add(scrollforReserverPanelSongList);

        ReservedPanel.add(ReserverPanelcontrol, BorderLayout.NORTH);
        ReservedPanel.add(ReserverPanelSongList, BorderLayout.SOUTH);

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

        bButton_07 = new JButton();
        bButton_07.setText("Show Reserved List");
        bButton_07.setPreferredSize(new Dimension(150, 25));
        bButton_07.addActionListener(this);

        ReserverPanelcontrol.add(bButton_07);
        //ReservedPanel.add(bButton_07,BorderLayout.NORTH);
        ReservedPanel.add(ReserverPanelcontrol, BorderLayout.NORTH);

        FilePanel.add(slider);
        FilePanel.add(bButton_05);
        FilePanel.add(bButton_06);

        BottomPanel.add(ControlPanel, BorderLayout.WEST);
        BottomPanel.add(FilePanel, BorderLayout.EAST);

        BottomPanel.add(ReservedPanel, BorderLayout.SOUTH);

        container.add(TopPanel, BorderLayout.NORTH);
        container.add(BottomPanel, BorderLayout.SOUTH);
        //setVisible(true);

        this.pack();
    }

    public static void main(String[] args) throws LineUnavailableException {
        //JFrame Video = new Video();
        //Video.setVisible(true);

        //http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Show_confirmation_dialog_for_closing_JFrame.htm
        MainFrame = new App();
        MainFrame.setVisible(true);
        /*MainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                int result = JOptionPane.showConfirmDialog(MainFrame,
                        "Do you want to Exit ?", "Exit Confirmation : ",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                else if (result == JOptionPane.NO_OPTION)
                    MainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
        });*/
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = ((JButton) e.getSource()).getActionCommand();
        System.out.println(command);

        switch (command) {
            case "Song List":
                //System.out.println(command);
                JFrame SongList = new SongList();
                SongList.setVisible(true);
                break;

            case "Show Reserved List":
                ReserverPanelSongList.setVisible(true);
                bButton_07.setText("Hide Reserved List");
                MainFrame.pack();
                break;

            case "Hide Reserved List":
                ReserverPanelSongList.setVisible(false);
                bButton_07.setText("Show Reserved List");
                MainFrame.pack();
                break;
        }
    }

}
