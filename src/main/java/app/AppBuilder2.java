package app;

import javax.swing.*;
import java.awt.*;

import entity.CurrentUser;
import interface_adapter.ViewManagerModel;
import interface_adapter.compare_playlists.PlaylistSimilarityController;
import interface_adapter.compare_playlists.PlaylistSimilarityPresenter;
import interface_adapter.compare_playlists.PlaylistSimilarityViewModel;
import view.SimilarityScorePanel;
import interface_adapter.genre_distribution.GenreDistributionController;
import interface_adapter.genre_distribution.GenreDistributionPresenter;
import interface_adapter.genre_distribution.GenreDistributionViewModel;
import interface_adapter.login.LoginController2;
import interface_adapter.login.LoginPresenter2;
import interface_adapter.song_recommend.SongRecommendController;
import interface_adapter.song_recommend.SongRecommendPresenter;
import interface_adapter.login.LoginViewModel2;
import interface_adapter.tempo_analyser.TempoAnalyserController;
import interface_adapter.tempo_analyser.TempoAnalyserPresenter;
import interface_adapter.top_songs.TopSongsController;
import interface_adapter.top_songs.TopSongsPresenter;
import interface_adapter.top_songs.TopSongsViewModel;
import use_case.TempoAnalyser.TempoAnalyserInteractor;
import use_case.compare_playlists.ComparePlaylistInteractor;
import use_case.compare_playlists.SpotifyPlaylistRepository;
import use_case.genre_distribution.GenreDistributionInteractor;
import interface_adapter.top_artists.TopArtistsController;
import interface_adapter.top_artists.TopArtistsPresenter;
import interface_adapter.song_recommend.SongRecommendViewModel;
import use_case.login.LoginInteractor2;
import use_case.topsongs.TopSongsInteractor;
import view.*;
import use_case.top_artists.TopArtistsInteractor;
import use_case.song_recommend.SongRecommendInteractor;

public class AppBuilder2 {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private LoginView2 loginView2;
    private LoggedInView loggedInView;
    private final CurrentUser currentUser = new CurrentUser();
    private SongRecommendController songRecommendController;
    private SongRecommendPresenter songRecommendPresenter;
    private SongRecommendViewModel songRecommendViewModel;
    private SimilarityScorePanel similarityScorePanel;
    private PlaylistSimilarityPresenter playlistSimilarityPresenter;
    private PlaylistSimilarityController playlistSimilarityController;
    private JFrame application;

    public AppBuilder2() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder2 addLoginView() {
        LoginViewModel2 loginViewModel2 = new LoginViewModel2();
        loginView2 = new LoginView2(loginViewModel2);
        cardPanel.add(loginView2, loginView2.getViewName());
        return this;
    }

    public AppBuilder2 addLoggedInView() {
        loggedInView = new LoggedInView(cardPanel, cardLayout);
        if (topArtistsController != null && topArtistsPresenter != null) {
            loggedInView.setTopArtistsController(topArtistsController);
            loggedInView.setTopArtistsPresenter(topArtistsPresenter);
        }
        if (songRecommendController != null && songRecommendPresenter != null) {
            loggedInView.setSongRecommendController(songRecommendController);
            loggedInView.setSongRecommendPresenter(songRecommendPresenter);
        }
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder2 addLoginUseCase() {
        LoginPresenter2 loginPresenter2 = new LoginPresenter2(viewManagerModel);
        LoginInteractor2 loginInteractor2 = new LoginInteractor2(loginPresenter2, currentUser);
        LoginController2 loginController2 = new LoginController2(loginInteractor2);
        loginView2.setLoginController(loginController2);

        viewManagerModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                String newState = evt.getNewValue().toString();
                cardLayout.show(cardPanel, newState);

                // Adjust the frame size dynamically based on the current view
                if ("login".equals(newState)) {
                    application.setSize(400, 300);
                } else {
                    application.setSize(1200, 800);
                }
                application.setLocationRelativeTo(null);
            }
        });
        return this;
    }

    private TopArtistsController topArtistsController;
    private TopArtistsPresenter topArtistsPresenter;

    public AppBuilder2 addTopArtistsUseCase() {
        topArtistsPresenter = new TopArtistsPresenter();
        TopArtistsInteractor interactor = new TopArtistsInteractor(topArtistsPresenter, currentUser);
        topArtistsController = new TopArtistsController(interactor);
        return this;
    }

    public AppBuilder2 addTopSongsUseCase() {
        TopSongsViewModel topSongsViewModel = new TopSongsViewModel();
        TopSongsPresenter topSongsPresenter = new TopSongsPresenter(topSongsViewModel);
        TopSongsInteractor topSongsInteractor = new TopSongsInteractor(topSongsPresenter);
        TopSongsController topSongsController = new TopSongsController(topSongsInteractor);

        TopSongsView topSongsView = new TopSongsView();

        loggedInView.setTopSongsActionListener(e -> {
            topSongsController.execute(currentUser);
            topSongsView.updateView(topSongsViewModel);
            cardPanel.add(topSongsView, "topSongs");
            cardLayout.show(cardPanel, "topSongs");
        });

        topSongsView.setGoBackButtonListener(e -> cardLayout.show(cardPanel, loggedInView.getViewName()));

        return this;
    }

    public AppBuilder2 addGenreDistributionUseCase() {
        GenreDistributionViewModel viewModel = new GenreDistributionViewModel();
        GenreDistributionPresenter presenter = new GenreDistributionPresenter(viewModel);
        GenreDistributionInteractor interactor = new GenreDistributionInteractor(presenter);
        GenreDistributionController controller = new GenreDistributionController(interactor);
        GenreDistributionView view = new GenreDistributionView();

        loggedInView.setGenreDistributionActionListener(e -> {
            controller.fetchAndPresentGenreDistribution(currentUser);
            view.updateView(viewModel);
            cardPanel.add(view, "genreDistribution");
            cardLayout.show(cardPanel, "genreDistribution");
        });

        view.setGoBackButtonListener(e -> cardLayout.show(cardPanel, loggedInView.getViewName()));

        return this;
    }

    public AppBuilder2 addSongRecommendUseCase() {
        SongRecommendViewModel viewModel = new SongRecommendViewModel();
        SongRecommendPresenter presenter = new SongRecommendPresenter(viewManagerModel, viewModel);
        SongRecommendInteractor interactor = new SongRecommendInteractor(presenter, currentUser);
        SongRecommendController controller = new SongRecommendController(interactor);

        loggedInView.setSongRecommendController(controller);
        loggedInView.setSongRecommendPresenter(presenter);
        return this;
    }

    public AppBuilder2 addSimilarityScorePanel() {
        similarityScorePanel = new SimilarityScorePanel(cardPanel, cardLayout, loggedInView); // Pass loggedInView
        cardPanel.add(similarityScorePanel, "Similarity Score Panel");
        return this;
    }


    public AppBuilder2 addComparePlaylistsUseCase() {
        PlaylistSimilarityViewModel playlistSimilarityViewModel = new PlaylistSimilarityViewModel();

        // Pass SimilarityScorePanel to the Presenter
        playlistSimilarityPresenter = new PlaylistSimilarityPresenter(playlistSimilarityViewModel, similarityScorePanel);

        ComparePlaylistInteractor comparePlaylistInteractor = new ComparePlaylistInteractor(new SpotifyPlaylistRepository(), playlistSimilarityPresenter);
        playlistSimilarityController = new PlaylistSimilarityController(comparePlaylistInteractor, playlistSimilarityPresenter);

        loggedInView.setPlaylistSimilarityController(playlistSimilarityController);

        return this;
    }

    public AppBuilder2 addTempoAnalyserUseCase() {
        TempoAnalyserPresenter presenter = new TempoAnalyserPresenter();
        TempoAnalyserInteractor interactor = new TempoAnalyserInteractor(presenter);
        TempoAnalyserController controller = new TempoAnalyserController(interactor);

        TempoAnalyserView view = new TempoAnalyserView();
        view.setController(controller);
        view.setPresenter(presenter);

        view.setGoBackButtonListener(e -> cardLayout.show(cardPanel, loggedInView.getViewName()));

        loggedInView.setTempoAnalyserActionListener(e -> {
            cardPanel.add(view, "tempoAnalyser");
            cardLayout.show(cardPanel, "tempoAnalyser");
        });

        return this;
    }

    public JFrame build() {
        application = new JFrame("Spotilyze");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);
        cardLayout.show(cardPanel, loginView2.getViewName());
        application.setSize(400, 300);
        return application;
    }
}
