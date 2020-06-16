package com.android.cagadroid.dummyia;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

 // An authentication item that is to be shown in the recycler view
public class AuthItem {
    private Integer userID = 0;
    private Double conf = 0.0;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Double getConf() {
        return conf;
    }

    public void setConf(Double conf) {
        this.conf = conf;
    }
}
