package android.cagadroid.sm;


interface ICAGADroidSecurityManagerInterface {

    /* The definition of the check Policy API 
     * @param: sub: the Subject name
     * @param: obj: the Object name
     * @param: operation: the type of operation that is requested by the subject
    */
    int checkPolicy(String sub, String obj, int operation);
}

