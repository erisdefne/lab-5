package interface_adapter.song_recommend;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SongRecommendViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private static SongRecommendState state = new SongRecommendState();

    public static SongRecommendState getState() {
        return state;
    }

    public void setState(SongRecommendState state) {
        this.state = state;
    }

    public String getViewName() {
        return "SongRecommendationView"; // View name to switch to
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
