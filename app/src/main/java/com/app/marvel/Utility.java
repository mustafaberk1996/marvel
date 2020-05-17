package com.app.marvel;

import java.util.Date;

public class Utility {

    public static String getHashForApi(long timeStamp) {
        long ts = timeStamp;
        String forHash = ts + Constants.PRIVATE_API_KEY + Constants.PUBLIC_API_KEY;
        String hash = MD5.getMd5(forHash);
        return hash;
    }
}
