package app;

import entity.CurrentUser;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();
        JFrame application = appBuilder2
                .addLoginView()
                .addLoginUseCase()
                .addTopArtistsUseCase("BQDwHiA7VgOPhHBEwmcBemzSGiFr4RuLx7oBWNC32g4JvHOhH2nXKgAWGMhXITxLZBZQIeC9tQ32tepdSaBgZlFn5oVBIpkPFs1ITsR9Lv09tXNfjAZp1hInxg1atKvGbBgV40DP8Jw-gLh-64voRUJz6i61_dc8-bMrHjTMz2azLT1deUtm2eKKaPrO5Dw4yRndI7rxeBnALKErJ8fCT-2jLIiyEnDhKcgJ6DkdQUGIIA")
                .addLoggedInView() // Initially add the logged-in view without Top Artists
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
