package android.cagadroid.spd;


interface ICAGADroidSecurityPolicyDatabaseInterface {

    /* The definition of the search Policy API */
    int searchPolicy(String sub, String obj, int operation,
                               int auth_source, double auth_conf,
                               int user);
}

