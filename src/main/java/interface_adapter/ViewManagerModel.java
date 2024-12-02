package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String state;
    private String errorMessage;

    public String getState() {
        return state;
    }

    public void setState(String newState) {
        String oldState = this.state;
        this.state = newState;
        support.firePropertyChange("state", oldState, newState);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String newErrorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = newErrorMessage;
        support.firePropertyChange("error", oldErrorMessage, newErrorMessage);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
