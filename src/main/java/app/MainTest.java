package app;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();

        JFrame application = appBuilder2
                .addLoginView()
                .addLoggedInView()
                .addLoginUseCase()
                .addSongRecommendUseCase("BQCRB4zB6Tqt2GouKfaYlrXhhEiT8sY7V_iI_4AlfuWITkdzWhPv3B7HvliqzJO2k41sompWCIIEBZ7wIi8gNNE4skdsPYAdQTBR0P2fT49QHRYDw9xbycJ585qR66s9b7RUgsfZb18LX1pbSQPmzbjlCTElwGsZvUJXCIMSUDKOzu-OyBgfpStIDO8HD_JHYMHdFDQBAY-41x7CKUVam0wNUaTLhBY4dUI5HPmtuoyBYg")
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);

        application.setVisible(true);
    }
}
