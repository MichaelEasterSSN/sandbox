
package com.screenscape.example;

import java.util.concurrent.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

class NetworkSourceCheck implements Runnable {
    public int type;
    public String url;
    private ConcurrentMap<Integer, NetworkInfo> networkInfoMap;
    private DefaultHttpClient client = new DefaultHttpClient();

    public NetworkSourceCheck(int type, String url, ConcurrentMap<Integer, NetworkInfo> networkInfoMap) {
        this.type = type;
        this.url = url;
        this.networkInfoMap = networkInfoMap;
    }

    protected boolean checkUrl() {
        boolean result = false;

        if (url.trim().isEmpty()) {
            result = (new Random().nextInt() % 2 == 0);
        } else {
            try {
                HttpRequestBase httpRequest;

                if (type != NetworkSource.NETWORK_TYPE_SSN) {
                    httpRequest = new HttpHead(url);
                } else {
                    httpRequest = new HttpPost(url);
                }

                HttpResponse response = client.execute(httpRequest);

                result = (response.getStatusLine().getStatusCode() == 200);

                if (response.getEntity() != null ) {
                    response.getEntity().consumeContent();
                }
            } catch (Exception ex) {
                System.err.println("TRACER checkUrl caught exception: " + ex.getMessage());
            }
        }

        return result;
    }

    public void run() {
        try {
            NetworkInfo networkInfo = new NetworkInfo();
            networkInfo.type = type;
            networkInfo.url = url;
            networkInfo.isOK = checkUrl();
            networkInfo.updatedOn = new Date().toString();

            System.out.println("TRACER " + new Date().toString() + " updating network info for " + url);

            if (networkInfoMap.containsKey(type)) {
                networkInfoMap.replace(type, networkInfo);
            } else {
                networkInfoMap.putIfAbsent(type, networkInfo);
            }
        } catch (Exception ex) {
            System.err.println("TRACER caught exception : " + ex.getMessage());
        }
    }
}

