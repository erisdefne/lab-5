package view;

import javax.swing.*;
import java.awt.*;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";

    public LoggedInView() {
        JLabel message = new JLabel("Welcome to the Logged In View!");
        message.setHorizontalAlignment(SwingConstants.CENTER);

        setLayout(new BorderLayout());
        add(message, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }
}