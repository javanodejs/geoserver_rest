package com.speed.tool;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;

import java.net.MalformedURLException;
import java.net.URL;

public class RestManager {
    public static  String RESTURL  = "http://192.168.1.9:8580/geoserver";
    public static String RESTUSER = "admin";
    public static String RESTPW   = "geoserver";
    public static java.net.URL URL;
    public static GeoServerRESTManager manager;
    public static GeoServerRESTReader reader;
    public static GeoServerRESTPublisher publisher;
    static {
        try {
            URL = new URL(RESTURL);
            manager = new GeoServerRESTManager(URL, RESTUSER, RESTPW);
            reader = manager.getReader();
            publisher = manager.getPublisher();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
