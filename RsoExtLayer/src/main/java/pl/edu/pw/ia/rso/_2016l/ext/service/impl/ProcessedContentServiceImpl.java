package pl.edu.pw.ia.rso._2016l.ext.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.ia.rso._2016l.ext.misc.Constants;
import pl.edu.pw.ia.rso._2016l.ext.service.InternalLayerConnectorService;
import pl.edu.pw.ia.rso._2016l.ext.service.ProcessedContentService;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maczaj on 18.05.16.
 */
@Service
public class ProcessedContentServiceImpl implements ProcessedContentService {

    @Autowired
    private InternalLayerConnectorService connector;


    @Override
    public void getProcessedFiles(List<Long> fileIds, ServletOutputStream output) throws IOException {

           Iterator<Long> it = fileIds.iterator();

        while(it.hasNext()){
            Long id = it.next();
            connector.getFile(id, output);

            if( it.hasNext()) {
                output.write("\n".getBytes());
                output.write(Constants.FILE_SEPARATOR.getBytes());
                output.write("\n".getBytes());
            }
        }
    }
}
