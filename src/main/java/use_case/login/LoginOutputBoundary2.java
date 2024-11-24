package use_case.login;

/**
 * The output boundary for the Login Use Case.
 */
public interface LoginOutputBoundary2 {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoginOutputData2 outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
