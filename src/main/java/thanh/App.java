package thanh;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    public static JButton mButton_reserverdlist;
    public static JFrame MainFrame;
    public static JScrollPane scrollforReserverPanelSongList;
    int clicked = 0;

    public App() {
        super("");

        Container container = getContentPane();

        setupAppThemes();

        setupNorthPanel(container);

        setupLeftPanel(container);

        setUpReservedTable(container);
        //mainWESTpanel.setLayout(new BorderLayout());

        //TODO BORDER LAYOUT WEST PANEL SETUP


        //TODO RESERVED PANEL
        //JPanel ReservedPanel = new JPanel();
        //ReservedPanel.setLayout(new BorderLayout());

        //JPanel reserverPanelbutton = new JPanel();
        //reserverPanelbutton.setLayout(new BorderLayout());

        //ReserverPanelSongList = new JPanel();
        //ReserverPanelSongList.setPreferredSize(new Dimension(500, 210));
        //ReserverPanelSongList.setVisible(false);


        //scrollforReserverPanelSongList.setPreferredSize(new Dimension(620, 200));
        //scrollforReserverPanelSongList.setVerticalScrollBar(scrollforReserverPanelSongList.createVerticalScrollBar());

        /*scrollforReserverPanelSongList.getVerticalScrollBar().setPreferredSize(new Dimension(
                (int) scrollforReserverPanelSongList.getVerticalScrollBar().getPreferredSize()
                        .getWidth() * verticalScrollBarWidthCoefficient,
                (int) scrollforReserverPanelSongList.getVerticalScrollBar().getPreferredSize().getHeight()
        ));*/

        //ReserverPanelSongList.add(scrollforReserverPanelSongList);

        //ReservedPanel.add(reserverPanelbutton, BorderLayout.NORTH);
        //ReservedPanel.add(scrollforReserverPanelSongList, BorderLayout.SOUTH);

        //JLabel headerLabel;
        //JLabel statusLabel;
        //JPanel controlPanel;
        //headerLabel.setText("Control in action: JSlider");
        JSlider slider_volumn = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);
        //slider.setSize(new Dimension(150, 50));
        //final JLabel finalStatusLabel = statusLabel;
        //slider_volumn.setSize(new Dimension(150,15));
        slider_volumn.setPreferredSize(new Dimension(120, 15));
        slider_volumn.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //finalStatusLabel.setText("Value : " + ((JSlider) e.getSource()).getValue());
            }
        });

        JButton bButton_05 = new JButton();
        bButton_05.setText("^");
        bButton_05.setPreferredSize(new Dimension(50, 25));

        JButton bButton_settings = new JButton();
        bButton_settings.setText("*");
        bButton_settings.setPreferredSize(new Dimension(50, 25));



        //reserverPanelbutton.add(bButton_reserverdlist,BorderLayout.WEST);
        //ReservedPanel.add(bButton_07,BorderLayout.NORTH);
        //ReservedPanel.add(reserverPanelbutton, BorderLayout.NORTH);

        //FilePanel.add(slider_volumn);
        //FilePanel.add(bButton_05);
        //FilePanel.add(bButton_settings);


        //MainBottomPanel.add(FilePanel, BorderLayout.CENTER);
        //MainBottomPanel.add(ReservedPanel, BorderLayout.SOUTH);

        //container.add(MainNorthPanel, BorderLayout.NORTH);

        //setVisible(true);
        setPreferredSize(new Dimension(1280, 720));
        this.pack();
    }

    public static void main(String[] args) {
        //JFrame video = new Video();
        //video.setVisible(true);
        //http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Show_confirmation_dialog_for_closing_JFrame.htm
        MainFrame = new App();
        //MainFrame.setPreferredSize(new Dimension(800,480));
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
                JFrame SongList = new SongList();
                SongList.setVisible(true);
                break;

            case "Reserved List":
                //ReserverPanelSongList.setVisible(true);
                scrollforReserverPanelSongList.setVisible(true);
                clicked++;
                System.out.println(clicked);
                if (clicked == 2) {
                    //ReserverPanelSongList.setVisible(false);
                    scrollforReserverPanelSongList.setVisible(false);
                    clicked = 0;
                }
                //bButton_reserverdlist.setText("Hide");
                //MainFrame.pack();
                break;

            /*case "Hide":
                ReserverPanelSongList.setVisible(false);
                bButton_reserverdlist.setText("Reserved List");
                MainFrame.pack();
                break;*/
        }
    }

    public void setupAppThemes() {
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
        //setResizable(false);
        //setSize(new Dimension(800, 200));
        setTitle("KaraokeVitinh");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setupNorthPanel(Container container) {
        JButton mButton_SongList = new JButton();
        mButton_SongList.setFont(new Font("Arial", Font.BOLD, 10));
        mButton_SongList.setText("Song List");
        mButton_SongList.addActionListener(this);
        mButton_SongList.setPreferredSize(new Dimension(100, 25));

        mButton_reserverdlist = new JButton();
        mButton_reserverdlist.setFont(new Font("Arial", Font.BOLD, 10));
        mButton_reserverdlist.setText("Reserved List");
        mButton_reserverdlist.setPreferredSize(new Dimension(100, 25));
        mButton_reserverdlist.addActionListener(this);

        JPanel mainNorthPanel = new JPanel();
        mainNorthPanel.setBorder(new BevelBorder(1));
        mainNorthPanel.setLayout(new BorderLayout());

        JPanel mainNorthPanelinnerLayout = new JPanel(new FlowLayout());

        mainNorthPanelinnerLayout.add(mButton_reserverdlist);
        mainNorthPanelinnerLayout.add(mButton_SongList);

        mainNorthPanel.add(mainNorthPanelinnerLayout,BorderLayout.EAST);

        container.add(mainNorthPanel, BorderLayout.NORTH);
    }

    public void setupLeftPanel(Container container) {
        JPanel mainLEFTpanel = new JPanel();
        mainLEFTpanel.setBorder(new BevelBorder(1));

        JPanel playbackControl = new JPanel();
        //JPanel FilePanel = new JPanel();
        JButton mButton_stop = new JButton();
        mButton_stop.setText("[ ]");
        mButton_stop.setPreferredSize(new Dimension(50, 25));

        JButton mButton_backward = new JButton();
        mButton_backward.setText("<<");
        mButton_backward.setPreferredSize(new Dimension(50, 25));

        JButton mButton_play = new JButton();
        mButton_play.setText(">");
        mButton_play.setPreferredSize(new Dimension(50, 25));

        JButton mButton_fastforward = new JButton();
        mButton_fastforward.setText(">>");
        mButton_fastforward.setPreferredSize(new Dimension(50, 25));

        playbackControl.add(mButton_stop);
        playbackControl.add(mButton_backward);
        playbackControl.add(mButton_play);
        playbackControl.add(mButton_fastforward);

        mainLEFTpanel.add(playbackControl);

        container.add(mainLEFTpanel, BorderLayout.WEST);
    }

    public void setUpReservedTable(Container container) {
        String[] columnNames = {"#", ""};
        //Object[][] rowData = {{"", ""}};
        JTable table = new JTable();
        table.setRowHeight(25);
        table.setFont(new Font("Arial", Font.BOLD, 18));

        TableColumn column;
        DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        table.setModel(tablemodel);
        for (int i = 1; i <= 10; i++) {
            String format = String.format("%06d", i);
            //String result = String.format(format, i);
            tablemodel.addRow(new Object[]{String.format(format, i), "Trọn Kiếp Bình Yên - 123456"});
        }

        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                // no
                case 0:
                    column.setPreferredWidth(70);
                    break;
                case 1:
                    column.setPreferredWidth(300);
                    break;
                default:
                    column.setPreferredWidth(80);
            }
        }

        //int verticalScrollBarWidthCoefficient = 3;
        scrollforReserverPanelSongList = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        container.add(scrollforReserverPanelSongList, BorderLayout.EAST);
    }
}
