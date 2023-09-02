package thanh;

import sun.swing.table.DefaultTableCellHeaderRenderer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame implements ActionListener {
    public static JButton mButton_reserverdlist;
    public static JFrame MainFrame;
    //public static JScrollPane scrollforReserverPanelSongList;
    int clicked = 0;
    //private static JScrollPane scrollPane;
    //private static DefaultTableModel model;
    //private DefaultTableCellRenderer cellRenderer;
    //static JTable table;

    public App() {
        super("");
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        setupforAppThemes();
        //setSize(new Dimension(640, 880));
        //setVisible(true);
        //setupformainNORTHPanel(container);
        //setupformainLEFTPanel(container);
        //setupformainRIGHTPanel(container);
        setUpforReservedTable(container);
        //JTableTest();
        //this.pack();
        //mainWESTpanel.setLayout(new BorderLayout());
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
        //this.pack();

/*
        scrollPane = new JScrollPane();
        JTable table = new JTable();
        scrollPane.setViewportView(table);
        model = (DefaultTableModel)table.getModel();
        model.addColumn("S.No");
        model.addColumn("First Name");
        model.addColumn("Last Name");
        model.addColumn("Email");
        model.addColumn("Contact");
        for(int i = 0;i < 4; i++) {
            model.addRow(new Object[0]);
            model.setValueAt(i+1, i, 0);
            model.setValueAt("Tutorials", i, 1);
            model.setValueAt("Point", i, 2);
            model.setValueAt("@tutorialspoint.com", i, 3);
            model.setValueAt("123456789", i, 4);
        }
        // set the column width for each column
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        //container.add(scrollPane);
        add(scrollPane);
        //setSize(475, 250);
        setResizable(false);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setVisible(true);
        */
    }

    public static void main(String[] args) {
        //JFrame video = new Video();
        //video.setVisible(true);
        //http://www.java2s.com/Tutorials/Java/Swing_How_to/JOptionPane/Show_confirmation_dialog_for_closing_JFrame.htm
        MainFrame = new App();

        //MainFrame.setPreferredSize(new Dimension(800,480));
        MainFrame.setSize(new Dimension(640, 480));
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
                //scrollforReserverPanelSongList.setVisible(true);
                //scrollPane.setVisible(true);
                clicked++;
                System.out.println(clicked);
                if (clicked == 2) {
                    //ReserverPanelSongList.setVisible(false);
                    //scrollforReserverPanelSongList.setVisible(false);
                    //scrollPane.setVisible(false);
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

    public void setupforAppThemes() {
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
        setTitle("KaraokeVitinh");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setPreferredSize(new Dimension(800, 480));
        //setSize(1280, 720);
        //setVisible(true);
        //this.pack();
    }

    public void setupformainNORTHPanel(Container container) {
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

        JPanel mainNORTHPanel = new JPanel();
        mainNORTHPanel.setBorder(new BevelBorder(1));
        mainNORTHPanel.setLayout(new BorderLayout());

        JPanel mainNORTHPanelinner = new JPanel(new FlowLayout());

        mainNORTHPanelinner.add(mButton_reserverdlist);
        mainNORTHPanelinner.add(mButton_SongList);

        mainNORTHPanel.add(mainNORTHPanelinner, BorderLayout.EAST);

        container.add(mainNORTHPanel, BorderLayout.NORTH);
    }

    public void setupformainLEFTPanel(Container container) {
        JPanel mainLEFTPanel = new JPanel();
        mainLEFTPanel.setBorder(new BevelBorder(1));

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

        mainLEFTPanel.add(playbackControl);

        container.add(mainLEFTPanel, BorderLayout.WEST);
    }

    public void setupformainRIGHTPanel(Container container) {

        JPanel mainRIGHTPanel = new JPanel();

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

        JButton mButton_05 = new JButton();
        mButton_05.setText("^");
        mButton_05.setPreferredSize(new Dimension(50, 25));

        JButton mButton_settings = new JButton();
        mButton_settings.setText("*");
        mButton_settings.setPreferredSize(new Dimension(50, 25));

        mainRIGHTPanel.add(slider_volumn);
        mainRIGHTPanel.add(mButton_05);
        mainRIGHTPanel.add(mButton_settings);

        container.add(mainRIGHTPanel, BorderLayout.EAST);
    }

    public static void ReserverdTable(JTable table) {
        table = new JTable();
        /*JTable table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };*/
        //table.setRowHeight(25);
        //table.setPreferredSize(new Dimension(800,600));
        //table.setSize(1280, 720);
        //table.setFont(new Font("Arial", Font.BOLD, 18));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        //DefaultTableModel model = null;
        //DefaultTableCellRenderer cellRenderer;
        //scrollPane = new JScrollPane();
        //scrollPane.setViewportView(table);

        //model = (DefaultTableModel) table.getModel();
        //model.addColumn("#");
        //model.addColumn(" ");
        //model.addColumn(" ");

        //model.addRow(new Object[0]);
        //model.setValueAt(i+1, i, 0);

        /*for (int i = 0; i < 4; i++) {
            String format = String.format("%06d", i);
            model.addRow(new Object[0]);
            model.setValueAt(String.format(format, i), i, 0);
            model.setValueAt("ANH CHỈ BIẾT CÂM NÍN NGHE TIẾNG EM KHÓC_(830083)", i, 1);
        }*/

        //table.getColumnModel().getColumn(0).setPreferredWidth(100);
        //table.getColumnModel().getColumn(1).setPreferredWidth(600);
        //table.setModel(tablemodel);
        /*for (int i = 1; i <= 10; i++) {
            String format = String.format("%06d", i);
            //String result = String.format(format, i);
            table.addRow(new Object[]{String.format(format, i), "ANH CHỈ BIẾT CÂM NÍN NGHE TIẾNG EM KHÓC_(830083)"});
        }*/

        //scrollPane.setPreferredSize(new Dimension(420, 100));
    }

    public void setUpforReservedTable(Container container) {
        //TODO TABLE OPTION 1
        Object[] columns = {"#", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            private static final long serialVersionUID = 1L;

            // TODO i set column 1 description editable
            // return false is noneditable
            @Override
            public boolean isCellEditable(int row, int column) {
                // make read only fields except column 0,13,14
                //return column == 1 || column == 2;// || column == 6;
                // || column == 2 || column == 3 || column == 4;
                // return column == 1 || column == 10;
                return false;
            }

            // TODO i declare column as below
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    default:
                        return String.class;
                }
            }
        };
        JTable table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(240, 240, 240);
                Color whiteColor = Color.WHITE;
                if (!comp.getBackground().equals(getSelectionBackground())) {
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    comp.setBackground(bg);
                }

                int rendererWidth = comp.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));

                //TODO OPTION 1 SET CELL CENTER
                //((JLabel) comp).setHorizontalAlignment(JLabel.CENTER);
                //OPTION 2
                //TODO OPTION 1 SET CELL CENTER
                //((DefaultTableCellRenderer) comp).setHorizontalAlignment(JLabel.CENTER);
                return comp;
            }
        };

        table.setModel(model);

        for (int i = 1; i <= 10; i++) {
            String format = String.format("%06d", i);
            //String result = String.format(format, i);
            model.addRow(new Object[]{String.format(format, i), "ANH CHỈ BIẾT CÂM NÍN NGHE TIẾNG EM KHÓC_(830083)"});
        }

        //TODO OPTION 1 SET TABLE HEADER CENTER
        //((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        //table.getTableHeader().setFont(new Font("Arial",Font.BOLD,18));

        //TODO OPTION 2 SET TABLE CELL CENTER
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(String.class, cellRenderer);

        //TODO OPTION 2 SET TABLE HEADER CENTER
        DefaultTableCellRenderer cellHeaderRenderer = new DefaultTableCellRenderer();
        cellHeaderRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getTableHeader().setDefaultRenderer(cellHeaderRenderer);


        //table.setAutoCreateRowSorter(true);
        //table.setRowHeight(25);
        //table.setFont(new Font("Arial", Font.BOLD, 18));
        //table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 10));
        //((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).set;

        /*((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(JLabel.RIGHT);*/

        /*DefaultTableCellHeaderRenderer tableHeader = new DefaultTableCellHeaderRenderer();
        tableHeader.setHorizontalAlignment(JLabel.CENTER);*/


        //table.getColumnModel().getColumn(0).setPreferredWidth(100);
        //table.getColumnModel().getColumn(1).setPreferredWidth(500);
        //JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
/*
        //TODO OPTION 2 SIMPLE TABLE
        JTable table = new JTable();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("#");
        model.addColumn(" ");
        for(int i = 0;i < 4; i++) {
            model.addRow(new Object[0]);
            //model.setValueAt(i+1, i, 0);
            model.setValueAt(String.format("%06d", i), i, 0);
            model.setValueAt("ANH CHỈ BIẾT CÂM NÍN NGHE TIẾNG EM KHÓC_(830083)", i, 1);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(500);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
*/
        //scrollPane.setPreferredSize(new Dimension(500, 100));
        //scrollPane.setVisible(true);
        //add(scrollPane);
        //table.setFillsViewportHeight(true);
        //String[] columnNames = {"#", ""};
        //Object[][] rowData = {{"", ""}};
        //JPanel ReservedTablePanel = new JPanel();
        //ReservedTablePanel.setBorder(new BevelBorder(1));
        //ReservedTablePanel.setPreferredSize(new Dimension(800, 600));
        //JButton button = new JButton();
        //button.setPreferredSize(new Dimension(100, 50));
        //ReservedTablePanel.add(button);
        //JTable table = new JTable();
        /*JTable table = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                int rendererWidth = component.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
                return component;
            }
        };*/
        //table.setRowHeight(25);
        //table.setPreferredSize(new Dimension(800,600));
        //table.setSize(1280, 720);
        //table.setFont(new Font("Arial", Font.BOLD, 18));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //DefaultTableModel tablemodel = new DefaultTableModel(columnNames, 0);
        //DefaultTableModel model = null;
        //DefaultTableCellRenderer cellRenderer;
        //scrollPane = new JScrollPane();
        //scrollPane = new JScrollPane();
        //scrollPane.setViewportView(table);

        //model = (DefaultTableModel) table.getModel();
        //model.addColumn("#");
        //model.addColumn(" ");
        //model.addRow(new Object[0]);
        //model.setValueAt(i+1, i, 0);

        /*for (int i = 0; i < 4; i++) {
            String format = String.format("%06d", i);
            model.addRow(new Object[0]);
            model.setValueAt(String.format(format, i), i, 0);
            model.setValueAt("ANH CHỈ BIẾT CÂM NÍN NGHE TIẾNG EM KHÓC_(830083)", i, 1);
        }*/

        //table.getColumnModel().getColumn(0).setPreferredWidth(100);
        //table.getColumnModel().getColumn(1).setPreferredWidth(600);
        //table.setModel(tablemodel);
        //scrollPane.setPreferredSize(new Dimension(420, 100));
        //TableColumn column;
        /*for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                // no
                case 0:
                    //column.setPreferredWidth(70);
                    column.setWidth(70);
                    break;
                case 1:
                    column.setPreferredWidth(600);
                    break;
                default:
                    column.setPreferredWidth(80);
            }
        }*/

        //int verticalScrollBarWidthCoefficient = 3;
        /*scrollforReserverPanelSongList = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);*/
        //setResizable(true);
        //table.setFillsViewportHeight(true);
        //setPreferredSize(new Dimension(640,480));
        //add(scrollPane);
        //setResizable(false);
        //setVisible(true);
        container.add(scrollPane, BorderLayout.SOUTH);
    }
}
