package android.cagadroid.ra;

interface ICAGADroidReAuthenticatorInterface {

    /**
     * Prompt the user for explicit re-authentication (the Re-Authentication API)
     * This function will not return the result of the re-authentication
     * If the re-authentication is successful, it will be sensed by the Context Provider
     * @param: recommended_type: Whether there is a preference for the type of re-authentication to be performed (the details is deployment=specific)
     */
    void reAuth(int recommended_type);
}
