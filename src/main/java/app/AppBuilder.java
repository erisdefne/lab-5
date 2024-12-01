package app;

import javax.swing.JFrame;

// import data_access.DataGetterClass;
import interface_adapter.TempoAnalyser.TempoAnalysisController;
import interface_adapter.TempoAnalyser.TempoAnalysisPresenter;
import use_case.TempoAnalyser.TempoAnalysisInputBoundary;
import use_case.TempoAnalyser.TempoAnalysisInteractor;
import view.TempoAnalysisView;

/**
 * The AppBuilder class is responsible for building the main application window
 * for the Tempo Analyser.
 */
public class AppBuilder {

    // Constants for the JFrame dimensions
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;

    /**
     * Builds the main application window (JFrame) for the Tempo Analyser.
     *
     * @return A JFrame object representing the main application window.
     */
    public static JFrame buildApp() {
        final TempoAnalysisInputBoundary interactor = new TempoAnalysisInteractor();
        final TempoAnalysisPresenter presenter = new TempoAnalysisPresenter();
        final TempoAnalysisController controller = new TempoAnalysisController(interactor);
        final TempoAnalysisView view = new TempoAnalysisView(controller, presenter);

        final JFrame frame = new JFrame("Tempo Analyser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        return frame;
    }
}
