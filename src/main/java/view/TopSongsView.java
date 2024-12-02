package view;

import interface_adapter.top_songs.TopSongsViewModel;
import use_case.topsongs.TopSongsInteractor.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class TopSongsView extends JPanel {
    private final JTextArea textArea;
    private final JButton goBackButton;

    public TopSongsView() {
        setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Top Songs", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        add(title, BorderLayout.NORTH);

        // Text area to display songs
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Go Back button
        goBackButton = new JButton("Go Back");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(goBackButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Updates the view with data from the TopSongsViewModel.
     *
     * @param viewModel The view model containing the songs or an error message.
     */
    public void updateView(TopSongsViewModel viewModel) {
        if (viewModel.getError() != null) {
            textArea.setText("Error: " + viewModel.getError());
        } else {
            List<Song> songs = viewModel.getSongs();
            if (songs == null || songs.isEmpty()) {
                textArea.setText("No songs available.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < songs.size(); i++) {
                Song song = songs.get(i);
                sb.append(i + 1).append(". ").append(song.toString()).append("\n");
            }
            textArea.setText(sb.toString());
        }
    }

    /**
     * Sets an ActionListener for the Go Back button.
     *
     * @param listener The ActionListener to be called when the button is clicked.
     */
    public void setGoBackButtonListener(ActionListener listener) {
        goBackButton.addActionListener(listener);
    }
}