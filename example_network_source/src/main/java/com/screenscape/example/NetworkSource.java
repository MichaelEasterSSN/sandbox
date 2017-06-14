
package com.screenscape.example;

import java.util.concurrent.*;

public class NetworkSource {
    static final int NETWORK_TYPE_GOOGLE = 1;
    static final int NETWORK_TYPE_YOUTUBE = 2;
    static final int NETWORK_TYPE_SSN = 3;

    static final String GOOGLE_URL = "http://www.google.com";
    static final String YOUTUBE_URL = "http://www.youtube.com";
    static final String SSN_URL = "http://devapp01a.dev.screenscape.local:8080/api/rest/system/ping";

    private ScheduledExecutorService scheduler;
    private ConcurrentMap<Integer, NetworkInfo> networkInfoMap;

    public NetworkSource() {
        int poolSize = 2;
        scheduler = Executors.newScheduledThreadPool(poolSize);
        networkInfoMap = new ConcurrentHashMap<Integer, NetworkInfo>();

        Runnable sourceCheck1 = new NetworkSourceCheck(NETWORK_TYPE_GOOGLE, GOOGLE_URL, networkInfoMap);
        Runnable sourceCheck2 = new NetworkSourceCheck(NETWORK_TYPE_YOUTUBE, YOUTUBE_URL, networkInfoMap);
        Runnable sourceCheck3 = new NetworkSourceCheck(NETWORK_TYPE_SSN, SSN_URL, networkInfoMap);

        long delay = 10L;
        scheduler.scheduleAtFixedRate(sourceCheck1, 0L, delay, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(sourceCheck2, 0L, delay, TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(sourceCheck3, 0L, delay, TimeUnit.SECONDS);
    }

    public NetworkInfo getNetworkInfo(int type) {
        return networkInfoMap.get(type);
    }
}


