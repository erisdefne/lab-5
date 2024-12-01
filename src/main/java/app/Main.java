package app;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        final AppBuilder2 appBuilder2 = new AppBuilder2();

        final JFrame application = appBuilder2
                .addLoginView()
                .addLoggedInView()
                .addLoginUseCase()
                .addTopSongsUseCase()
                .addGenreDistributionUseCase()
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);

        application.setVisible(true);
    }
}
