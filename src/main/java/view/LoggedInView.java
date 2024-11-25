package view;

import javax.swing.*;
import java.awt.*;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10)); // 3 rows, 2 columns with gaps

        // Add buttons
        add(new JButton("TopSongs"));
        add(new JButton("2"));
        add(new JButton("3"));
        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
    }

    public String getViewName() {
        return viewName;
    }
}
