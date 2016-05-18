package pl.edu.pw.ia.rso._2016l.ext.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.pw.ia.rso._2016l.ext.service.ProcessedContentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by maczaj on 18.05.16.
 */
@Controller
@RequestMapping(value = "/ext")
public class RestrictedContentServingController {


    @Autowired
    private ProcessedContentService mediator;

    @RequestMapping(method = RequestMethod.POST)
    public void serve (@RequestHeader("X-Requested-Files") String ids, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(ids)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        List<Long> fileIds = Arrays.asList(ids.split(",")).stream().map( id -> Long.parseLong(id)).collect(Collectors.toList());

        response.setContentType("text/plain");
        mediator.getProcessedFiles(fileIds, response.getOutputStream());

        response.flushBuffer();

//        TODO: exception obsluzyc czy niech leci do klienta?
    }

}
