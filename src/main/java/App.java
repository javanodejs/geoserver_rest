import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTLayerList;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class App {


    public static  String RESTURL  = "http://192.168.1.9:8580/geoserver";
    public static String RESTUSER = "admin";
    public static String RESTPW   = "geoserver";
    public static URL URL;
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

    //创建工作空间
    public static void createWorkSpace() throws Exception{
        boolean created = publisher.createWorkspace("myWorkspace");
        System.out.println(created);
    }

    //罗列工作空间
    public static void showWorkSpace() throws Exception{
        RESTLayerList list = reader.getLayers();
        System.out.println(list.get(0).getName());
    }

    //发布shp服务
    public static void publishShp()throws Exception{
        File zipFile = new File("/home/liz-98k/IdeaProjects/geoserver/testdata/states.zip");
        boolean published = publisher.publishShp("myWorkspace", "11", "states", zipFile, "EPSG:4326", "default_point");
        System.out.println(published);
    }

    //罗列已发布shp
    //发布tiff数据
    public static void publishTIFF()throws Exception{

        File geotiff = new File("/home/liz-98k/IdeaProjects/geoserver/testdata/resttestdem.tif");
        System.out.println(geotiff);

        boolean pc = publisher.publishGeoTIFF("myWorkspace", "tif", "resttestdem",geotiff, "EPSG:4326", GSResourceEncoder.ProjectionPolicy.FORCE_DECLARED, "raster");
        System.out.println(pc);

    }
    //创造样式
    public static void publishStyle(){
        boolean ps = publisher.publishStyle(new File("/home/liz-98k/IdeaProjects/geoserver/testdata/restteststyle.sld"));
        System.out.println(ps);
    }


    //数据存储
    public static void store(){
        //publisher.createPostGISDatastore()
    }
    //
    public static void main(String[] args) throws Exception{
        //createWorkSpace();
        //showWorkSpace();
        //publishShp();
    }
}
