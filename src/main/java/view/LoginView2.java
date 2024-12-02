package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginViewModel2;

/**
 * The View for when the user is logging into the program.
 */
public class LoginView2 extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "login";
    private final LoginViewModel2 loginViewModel2;
    private final JButton loginButton;
    private LoginController2 loginController2;

    public LoginView2(LoginViewModel2 loginViewModel2) {
        this.loginViewModel2 = loginViewModel2;
        this.loginViewModel2.addPropertyChangeListener(this);

        // Set up layout manager
        setLayout(new BorderLayout());

        // Create a central panel for all components
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        // App Title
        JLabel title = new JLabel("Spotilyze");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle
        JLabel subtitle = new JLabel("Log in to your Spotify account!");
        subtitle.setFont(new Font("Arial", Font.ITALIC, 18));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login Button
        loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setPreferredSize(new Dimension(100, 30)); // Set button size to 100x30 pixels
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.addActionListener(this);

        // Add components to the central panel
        centerPanel.add(Box.createVerticalGlue()); // Add space before title
        centerPanel.add(title);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between title and subtitle
        centerPanel.add(subtitle);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between subtitle and button
        centerPanel.add(loginButton);
        centerPanel.add(Box.createVerticalGlue()); // Add space after button

        // Add central panel to the main layout
        add(centerPanel, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController2 loginController2) {
        this.loginController2 = loginController2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            loginController2.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName()) && "logged in".equals(evt.getNewValue())) {
            JOptionPane.showMessageDialog(this, "Login successful! Switching to the logged-in view.");
        }
    }
}