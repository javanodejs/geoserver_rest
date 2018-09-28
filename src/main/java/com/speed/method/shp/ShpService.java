package com.speed.method.shp;

import com.speed.tool.RestManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.*;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URI;

//shp文件操作
@Controller
@ResponseBody
@RequestMapping(value = "/shp")
public class ShpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShpService.class);

    private static final GeoServerRESTReader reader = RestManager.reader;
    private static final GeoServerRESTPublisher publisher = RestManager.publisher;


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "success test";
    }

    //发布shp服务
    @RequestMapping(value = "/publish/shp", method = RequestMethod.POST)
    public boolean publishShp(@RequestParam String fileName,@RequestParam String workspace,
                              @RequestParam String storename,@RequestParam String layerName,
                              @RequestParam String srs,@RequestParam String defaultStyle)throws Exception{
        File zipFile = new File(fileName);
        boolean published = publisher.publishShp(workspace,storename, layerName, zipFile, srs, defaultStyle);
        return published;
    }

    //发布tif数据服务
    @RequestMapping(value = "/publish/tif", method = RequestMethod.POST)
    public boolean publishGeoTIFF(@RequestParam String fileName,@RequestParam String workspace,
                                  @RequestParam String storename,@RequestParam String layerName,
                                  @RequestParam String srs,@RequestParam String defaultStyle) throws Exception{
        File geotiff = new File(fileName);
        boolean pc = publisher.publishGeoTIFF(workspace, storename, layerName,geotiff, srs, GSResourceEncoder.ProjectionPolicy.FORCE_DECLARED, defaultStyle);
        return pc;
    }

    //显示所有发布图层或根据名称查询图层
    @RequestMapping(value = "/layers", method = RequestMethod.GET)
    public void getLayers(){
        RESTLayer layer = reader.getLayer("roa_4m");
        System.out.println(layer.getResourceUrl());
    }

    //根据图层名称删除发布图层
    @RequestMapping(value = "/delete/{layerName}", method = RequestMethod.DELETE)
    public String deleteLayer(@PathVariable String layerName){
        RESTLayer layer = reader.getLayer(layerName);
        if (layer.getType() == RESTLayer.Type.VECTOR) {
            deleteFeatureType(layer);
        }
        else if (layer.getType() == RESTLayer.Type.RASTER){
            deleteCoverage(layer);
        }
        LOGGER.debug("delete layer sucess");
        return "delete success";
    }


    private void deleteFeatureType(RESTLayer layer) {
        RESTFeatureType featureType = reader.getFeatureType(layer);
        RESTDataStore datastore = reader.getDatastore(featureType);
        boolean removed = publisher.unpublishFeatureType(datastore.getWorkspaceName(),
                datastore.getName(), layer.getName());
    }
    private void deleteCoverage(RESTLayer layer) {
        RESTCoverage coverage = reader.getCoverage(layer);
        RESTCoverageStore coverageStore = reader.getCoverageStore(coverage);

        boolean removed = publisher.unpublishCoverage(coverageStore.getWorkspaceName(),
                coverageStore.getName(), coverage.getName());
    }
}
