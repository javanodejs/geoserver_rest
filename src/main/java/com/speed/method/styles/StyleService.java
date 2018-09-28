package com.speed.method.styles;

import com.speed.method.shp.ShpService;
import com.speed.tool.RestManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTStyleList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping(value = "/style")
public class StyleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StyleService.class);

    private static final GeoServerRESTReader reader = RestManager.reader;
    private static final GeoServerRESTPublisher publisher = RestManager.publisher;
    //创建图层样式
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createStyle(@RequestParam String fileName) {
        System.out.println(fileName);
        boolean ps = publisher.publishStyle(new File(fileName));
        return ps;
    }

    //移除样式
    @RequestMapping(value = "/delete/{style}", method = RequestMethod.DELETE)
    public boolean deleteStyle(String style){
        boolean removed = publisher.removeStyle(style, true);
        return removed;
    }

    //样式列表
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<String> listStyle(){
        List<String> styleNames = new ArrayList<String>();
        RESTStyleList styleList = reader.getStyles();
        for (int i=0; i<styleList.size(); i++) {
            styleNames.add(styleList.get(i).getName());
        }
        return styleNames;
    }

    //获取样式内容
    @RequestMapping(value = "/{style}", method = RequestMethod.GET)
    public String getStyleSld(@PathVariable String style){
        String sld = reader.getSLD(style);

        return sld;
    }
}
