package app;

import entity.CurrentUser;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();
        JFrame application = appBuilder2
                .addLoginView()
                .addLoginUseCase()
                .addTopArtistsUseCase("BQCARi57ZNJaSV632dXW_JPEHoEoOEgt1XSBN_JA1HpweXVNzTiLskSDAKSpnmU1854iuyEL0f9kZ8Snirjq_YrZ9i55_bdtezBU8bvwiGfrbxxMHztx3yGRdQ8v6bGU5txTjBSfrt66mCYgUIBlw-W43-D0wPTxjDIyH2ToGcvXStQuJvlqvd1IklY2k81_LxB7iFL8vW9nAcHzVFy2gplgk-ccQLyVqWnf8iKypiN_bg")
                .addLoggedInView() // Initially add the logged-in view without Top Artists
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
