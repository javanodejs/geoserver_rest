package com.speed.method.workspace;

import com.speed.method.shp.ShpService;
import com.speed.tool.RestManager;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

@Controller
@ResponseBody
@RequestMapping(value = "/workspace")
public class WorkSpaceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkSpaceService.class);

    private static final GeoServerRESTReader reader = RestManager.reader;
    private static final GeoServerRESTPublisher publisher = RestManager.publisher;

    //创建工作空间
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean createWorkSpace(String workspaceName, String uri) throws Exception{
        boolean created;

        if (uri == null || uri.trim().length() == 0) {
            created = publisher.createWorkspace(workspaceName);
        }else {
            URI ur = new URI(uri);
            created = publisher.createWorkspace(workspaceName, ur);
        }
        LOGGER.debug("Workspace create " + workspaceName);
        return created;
    }

    //删除工作空间
    @RequestMapping(value = "/delete/{name}", method = RequestMethod.DELETE)
    public boolean deleteWorkSpace(@PathVariable String name) throws Exception{
        boolean removed = publisher.removeWorkspace(name, true);
        LOGGER.debug("Workspace removed " + name);
        return removed;
    }


}
