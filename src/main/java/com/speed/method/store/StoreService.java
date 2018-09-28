package com.speed.method.store;

import com.speed.entity.DBLayerEntity;
import com.speed.method.shp.ShpService;
import com.speed.tool.RestManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.encoder.GSAbstractStoreEncoder;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import it.geosolutions.geoserver.rest.encoder.feature.GSFeatureTypeEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
@ResponseBody
@RequestMapping(value = "/store")
public class StoreService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreService.class);

    private static final GeoServerRESTReader reader = RestManager.reader;
    private static final GeoServerRESTPublisher publisher = RestManager.publisher;


    public GSPostGISDatastoreEncoder getStoreEncoder(String datastoreName){
        boolean exposePrimaryKeys = true;
        boolean validateConnections = false;
        String primaryKeyMetadataTable = "test";

        String description = "description";
        String dsNamespace = "http://www.geo-solutions.it";

        GSPostGISDatastoreEncoder datastoreEncoder = new GSPostGISDatastoreEncoder();
        datastoreEncoder.setName(datastoreName);
        datastoreEncoder.setDescription(description);
        datastoreEncoder.setNamespace(dsNamespace);

        String pgHost      = System.getProperty("pgHost", "192.168.1.194");
        int pgPort      = Integer.parseInt(System.getProperty("pgPort", "5432"));
        String pgDatabase  = System.getProperty("pgDatabase", "osm_postgis");
        String pgSchema    = System.getProperty("pgSchema", "public");
        String pgUser      = System.getProperty("pgUser", "postgres");
        String pgPassword  = System.getProperty("pgPassword", "password");

        datastoreEncoder.setHost(pgHost);
        datastoreEncoder.setPort(pgPort);
        datastoreEncoder.setDatabase(pgDatabase);
        datastoreEncoder.setSchema(pgSchema);
        datastoreEncoder.setUser(pgUser);
        datastoreEncoder.setPassword(pgPassword);
        datastoreEncoder.setExposePrimaryKeys(exposePrimaryKeys);
        datastoreEncoder.setValidateConnections(validateConnections);
        datastoreEncoder.setPrimaryKeyMetadataTable(primaryKeyMetadataTable);
        return datastoreEncoder;
    }


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createDBstore(@RequestParam String workspace, @RequestParam String datastoreName){
        GSPostGISDatastoreEncoder datastoreEncoder = getStoreEncoder(datastoreName);
        boolean ps = publisher.createPostGISDatastore(workspace,datastoreEncoder);
        return ps;
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public boolean publicPostData(DBLayerEntity dbLayer) throws Exception{
/*        GSFeatureTypeEncoder fte = new GSFeatureTypeEncoder();
        fte.setProjectionPolicy(GSResourceEncoder.ProjectionPolicy.REPROJECT_TO_DECLARED);
        fte.addKeyword("KEYWORD");
        fte.setTitle("easia_gaul_0_aggr");
        fte.setName("easia_gaul_0_aggr");
        fte.setSRS("EPSG:4326");

        final GSLayerEncoder layerEncoder = new GSLayerEncoder();
        //layerEncoder.setDefaultStyle("default_polygon");

        boolean ok = publisher.publishDBLayer("myWorkspace", "resttestpostgis", fte, layerEncoder);
        return true;*/
        boolean ok = publisher.publishDBLayer(dbLayer.getWorkspace(), dbLayer.getStorename(),
                dbLayer.getLayername(),dbLayer.getSrs(),
                dbLayer.getDefaultstyle());
        return ok;
    }
    //获取data store
    public boolean publishPostData() {
        RESTDataStore dataStore = reader.getDatastore("postgis","resttestpostgis");
        return true;
    }
}
