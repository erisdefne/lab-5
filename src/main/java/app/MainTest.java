package app;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();

        JFrame application = appBuilder2
                .addLoginView()
                .addLoggedInView()
                .addLoginUseCase()
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);

        application.setVisible(true);
    }
}
