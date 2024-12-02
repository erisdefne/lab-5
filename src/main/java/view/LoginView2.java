package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginViewModel2;

public class LoginView2 extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "login";
    private final LoginViewModel2 loginViewModel2;
    private final JButton loginButton;
    private LoginController2 loginController2;
    private final JTextField usernameField;
    private final JPasswordField passwordField;

    public LoginView2(LoginViewModel2 loginViewModel2) {
        this.loginViewModel2 = loginViewModel2;
        this.loginViewModel2.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login Screen");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }

    public LoginViewModel2 getLoginViewModel() {
        return loginViewModel2;
    }

    public void setLoginController(LoginController2 loginController2) {
        this.loginController2 = loginController2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            final String username = usernameField.getText();
            final String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username or Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                loginController2.execute(username, password);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            if ("logged in".equals(evt.getNewValue())) {
                JOptionPane.showMessageDialog(this, "Login successful! Switching to the logged-in view.");
            }
            else if ("error".equals(evt.getNewValue())) {
                JOptionPane.showMessageDialog(this, "Login failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
