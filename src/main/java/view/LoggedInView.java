package view;

import interface_adapter.compare_playlists.PlaylistSimilarityController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoggedInView extends JPanel {

    private final String viewName = "logged in";

    // Buttons for different functionalities
    private JButton genreDistributionButton;
    private JButton topSongsButton;
    private JButton topArtistsButton;
    private JButton recommendSongsButton;
    private JButton comparePlaylistsButton;

    // Text fields for user input (only shown after "Compare Playlists" button is clicked)
    private JTextField playlist1NameField;
    private JTextField playlist1OwnerField;
    private JTextField playlist2NameField;
    private JTextField playlist2OwnerField;
    private JButton submitButton;

    // Controller for Playlist Similarity
    private PlaylistSimilarityController playlistSimilarityController;

    // The CardLayout and panel to switch to
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public LoggedInView(JPanel cardPanel, CardLayout cardLayout) {
        this.cardPanel = cardPanel;
        this.cardLayout = cardLayout;

        setLayout(new GridLayout(4, 2, 10, 10)); // 4 rows, 2 columns with gaps

        // Initialize buttons
        genreDistributionButton = new JButton("Genre Distribution");
        topSongsButton = new JButton("Top Songs");
        topArtistsButton = new JButton("Top Artists");
        recommendSongsButton = new JButton("Recommend Songs");
        comparePlaylistsButton = new JButton("Compare Playlists");

        // Add initial buttons to the panel
        add(genreDistributionButton);
        add(topSongsButton);
        add(topArtistsButton);
        add(recommendSongsButton);
        add(comparePlaylistsButton);

        // Add action listener for "Compare Playlists" button
        comparePlaylistsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPlaylistComparisonInputs();
            }
        });
    }

    /**
     * Displays input fields for entering playlist names and owners.
     * This method is triggered when the "Compare Playlists" button is clicked.
     */
    private void showPlaylistComparisonInputs() {
        // Clear the panel to show new inputs
        removeAll();
        revalidate();
        repaint();

        setLayout(new GridLayout(6, 2, 10, 10)); // Adjust layout for input fields

        // Initialize input fields
        JLabel playlist1NameLabel = new JLabel("Playlist 1 Name:");
        playlist1NameField = new JTextField();
        JLabel playlist1OwnerLabel = new JLabel("Playlist 1 Owner:");
        playlist1OwnerField = new JTextField();

        JLabel playlist2NameLabel = new JLabel("Playlist 2 Name:");
        playlist2NameField = new JTextField();
        JLabel playlist2OwnerLabel = new JLabel("Playlist 2 Owner:");
        playlist2OwnerField = new JTextField();

        // Initialize submit button
        submitButton = new JButton("Submit");

        // Add components to the panel
        add(playlist1NameLabel);
        add(playlist1NameField);
        add(playlist1OwnerLabel);
        add(playlist1OwnerField);

        add(playlist2NameLabel);
        add(playlist2NameField);
        add(playlist2OwnerLabel);
        add(playlist2OwnerField);

        add(new JLabel()); // Empty cell for alignment
        add(submitButton);

        // Add action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPlaylistComparison();
            }
        });
    }

    /**
     * Processes the playlist comparison by sending user inputs to the Controller.
     */
    private void processPlaylistComparison() {
        String playlist1Name = playlist1NameField.getText().trim();
        String playlist1Owner = playlist1OwnerField.getText().trim();
        String playlist2Name = playlist2NameField.getText().trim();
        String playlist2Owner = playlist2OwnerField.getText().trim();

        if (playlist1Name.isEmpty() || playlist1Owner.isEmpty() ||
                playlist2Name.isEmpty() || playlist2Owner.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please fill in all fields!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        if (playlistSimilarityController != null) {
            // Trigger comparison through the controller
            playlistSimilarityController.comparePlaylists(
                    playlist1Name,
                    playlist1Owner,
                    playlist2Name,
                    playlist2Owner
            );

            // Switch to the panel that shows the similarity score
            cardLayout.show(cardPanel, "Similarity Score Panel");
        }
    }

    public String getViewName() {
        return viewName;
    }

    // Setter for PlaylistSimilarityController
    public void setPlaylistSimilarityController(PlaylistSimilarityController playlistSimilarityController) {
        this.playlistSimilarityController = playlistSimilarityController;
    }
}
