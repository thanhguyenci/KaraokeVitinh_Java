package thanh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class SongList extends JFrame implements ActionListener {

    public SongList() {
        super("");
        setSize(new Dimension(640, 480));
        setTitle("Song List");
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
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
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        JPanel center = new JPanel();
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new BorderLayout());

        JPanel BottomPanela = new JPanel();

        JButton buttonAdd = new JButton();
        buttonAdd.setText("+");
        buttonAdd.setPreferredSize(new Dimension(50, 35));

        JButton buttonRemove = new JButton();
        buttonRemove.setText("-");
        buttonRemove.setPreferredSize(new Dimension(50, 35));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());




        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        JLabel search = new JLabel("Search : ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(search, gbc);
        gbc.gridx++;
        //gbc.gridy++;
        searchPanel.add(textField, gbc);


        BottomPanela.add(buttonAdd);
        BottomPanela.add(buttonRemove);
        BottomPanela.add(searchPanel);
        //JTextField textField = new JTextField();

        BottomPanel.add(BottomPanela,BorderLayout.WEST);

        JScrollPane scroll = new JScrollPane(center,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scroll.setLayout(layout);
        scroll.setPreferredSize(new Dimension(640, 480));

        container.add(scroll,BorderLayout.CENTER);
        container.add(BottomPanel,BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        final JFrame SongList = new SongList();
        SongList.setVisible(true);
        SongList.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
