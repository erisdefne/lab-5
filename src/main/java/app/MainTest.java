package app;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();

        JFrame application = appBuilder2
                .addLoginView()
                .addLoggedInView()
                .addLoginUseCase()
                .addSongRecommendUseCase("BQByOEUh6cwcK6P96sT_wFdrMqMGqMH0CEXycNkXsyAKHeZxFSlo9UNt-urG-_iIGgX1yQpqozY22FS2AVoh7E4UND5ASOvbpzjcmnCAFspngnjhKyGAIdvy-3PSOJXs2s1dj5wwckor2NxTd5cZCIVK1mGJwcSR1t_0HeCuZx-ubxliwgYwLcc0pcxAk_EQhKzNW0GmGrVjSkMQm_Lds0vK3bl-Pqc22oBF4vwJcjcOuw")
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);

        application.setVisible(true);
    }
}
