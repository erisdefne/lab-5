package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final AppBuilder2 appBuilder2 = new AppBuilder2();

        final JFrame application = appBuilder2
                .addTempoAnalyserUseCase()
                .build();

        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
