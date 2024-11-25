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

        JLabel title = new JLabel("Login Screen");
        title.setHorizontalAlignment(SwingConstants.CENTER);

        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);

        setLayout(new BorderLayout());
        add(title, BorderLayout.NORTH);
        add(loginButton, BorderLayout.CENTER);
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
