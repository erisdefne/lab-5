package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The View for the logged-in state of the application.
 */
public class LoggedInView extends JPanel {

    private final String viewName = "logged in";
    private final JButton genreDistribution;
    private final JButton topSongsButton;
    private final JButton tempoAnalyserButton;

    public LoggedInView() {
        setLayout(new GridLayout(3, 2, 10, 10));

        // Initialize and add buttons
        genreDistribution = new JButton("GenreDistribution");
        add(genreDistribution);

        topSongsButton = new JButton("TopSongs");
        add(topSongsButton);

        tempoAnalyserButton = new JButton("Tempo Analyser");
        add(tempoAnalyserButton);

        add(new JButton("4"));
        add(new JButton("5"));
        add(new JButton("6"));
    }

    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the action listener for the "TopSongs" button.
     * @param actionListener the ActionListener to handle button clicks
     */
    public void setTopSongsActionListener(ActionListener actionListener) {
        topSongsButton.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "GenreDistribution" button.
     * @param actionListener the ActionListener to handle button clicks
     */
    public void setGenreDistributionActionListener(ActionListener actionListener) {
        genreDistribution.addActionListener(actionListener);
    }

    /**
     * Sets the action listener for the "Tempo Analyser" button.
     * @param actionListener the ActionListener to handle button clicks
     */
    public void setTempoAnalyserActionListener(ActionListener actionListener) {
        tempoAnalyserButton.addActionListener(actionListener);
    }
}
