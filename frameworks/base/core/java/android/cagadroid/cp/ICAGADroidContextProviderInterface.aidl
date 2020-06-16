package android.cagadroid.cp;
import android.cagadroid.cp.ICAGADroidIASchemeInterface;

interface ICAGADroidContextProviderInterface {

    /**
     * Check how a certain user is authenticated (part of the GetContext API)
     * The output is a map between AuthenticatorID and Authentication Confidence for the given userID
     * @param userID: ID of the user
     */
    Map checkUserAuth(int userID);

    /**
     * Get the currently-identified main user (part of the GetContext API)
     */
    int getCurrentUser();

    /**
     * Get the list of registered userIDs (the UserList API)
     * The output is a Map between usernames and userIDs
     */
    Map getUserList();
    
    /**
     * Register an IA scheme with the Context Provider (part of RegisterIA API)
     * @param AuthenticatorID: ID of the Authenticator to be registered (as defined in the policy xml file)
     * @param RemoteName: name of the system service that implements the IA scheme
     */
    void registerIA(int AuthenticatorID, ICAGADroidIASchemeInterface RemoteName);
    
    /**
     * De-register an IA scheme with the Context Provider (part of RegisterIA API)
     * @param AuthenticatorID: ID of the Authenticator to be de-registered (as defined in the policy xml file)
     */
    void deregisterIA(int AuthenticatorID);
}
