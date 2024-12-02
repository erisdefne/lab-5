package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimilarityScorePanel extends JPanel {

    private final JLabel similarityScoreLabel;
    private final JButton backButton;

    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final LoggedInView loggedInView; // Reference to LoggedInView

    public SimilarityScorePanel(JPanel cardPanel, CardLayout cardLayout, LoggedInView loggedInView) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;
        this.loggedInView = loggedInView; // Reference to the original LoggedInView

        setLayout(new BorderLayout());

        // Initialize the label for displaying the similarity score
        similarityScoreLabel = new JLabel("Similarity score will be displayed here.");
        similarityScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        similarityScoreLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(similarityScoreLabel, BorderLayout.CENTER);

        // Initialize the "Back to Main Panel" button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Back to Main Panel button clicked!");

                // Switch the CardLayout back to the original LoggedInView (no reinitialization)
                cardLayout.show(cardPanel, loggedInView.getViewName());
            }
        });
        add(backButton, BorderLayout.SOUTH); // Place the button at the bottom of the panel
    }

    public void updateSimilarityScore(int similarityScore) {
        similarityScoreLabel.setText("Similarity score: " + similarityScore + "%");
    }

    public void displayErrorMessage(String errorMessage) {
        similarityScoreLabel.setText("Error: " + errorMessage);
    }
}
