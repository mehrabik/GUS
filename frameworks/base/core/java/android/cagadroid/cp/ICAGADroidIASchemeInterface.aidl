package android.cagadroid.cp;

interface ICAGADroidIASchemeInterface {

    /**
     * To be implemented by IA schemes
     * The output is a map between userID and authentication confidence
     * The scheme should get the list of userIDs from the CP using the UserList API
     */
    Map getAuthContext();
}
