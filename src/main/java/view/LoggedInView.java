package view;

import interface_adapter.top_songs.TopSongsController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    JButton topSongsButton;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        // Add buttons
        topSongsButton = new JButton("TopSongs");
        add(topSongsButton);
        add(new JButton("2"));
        add(new JButton("3"));
        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
    }

    public String getViewName() {
        return viewName;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == topSongsButton) {
            TopSongsController.execute();
        }
    }
}
