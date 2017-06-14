
package com.screenscape.example;

public class NetworkInfo {
    public int type;
    public String url;
    public boolean isOK;
    public String updatedOn;

    public String toString() {
        return "type: " + type + " url: " + url + " isOK: " + isOK + " updatedOn: " + updatedOn;
    }
}

