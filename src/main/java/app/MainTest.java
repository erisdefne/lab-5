package app;

import entity.CurrentUser;

import javax.swing.*;

public class MainTest {

    public static void main(String[] args) {
        AppBuilder2 appBuilder2 = new AppBuilder2();
        JFrame application = appBuilder2
                .addLoginView()
                .addLoginUseCase()
                .addTopArtistsUseCase("BQDxy-20NRosNfn9-PA7dEf5rfBaQrwkPTpi-kr0TlzmyfGy091XHjLthuqJPyUDkqRRvnTya089TR3sqEqJw5WhLuKbCG8WTc6aMXSL6R7UJllMdVQU97mO2YK2G1N_L3oSU5rqYMVBSeMC5vEZCnr28eONDLpl-lqupeT2PMvA_S8GkyQ7LT0MhD6-jNZhYbWWNx59dWoTscuZSy8VfwBCGzn1F9xau3lumDSc_BqlFg")
                .addLoggedInView() // Initially add the logged-in view without Top Artists
                .build();

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
