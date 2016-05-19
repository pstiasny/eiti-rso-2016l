package pl.edu.pw.ia.rso._2016l.ext.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.edu.pw.ia.rso._2016l.ext.service.InternalLayerConnectorService;
import pl.edu.pw.ia.rso._2016l.ext.service.ProcessedContentService;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by maczaj on 18.05.16.
 */
@Service
public class ProcessedContentServiceImpl implements ProcessedContentService {

    @Value("${rso.file.separator}")
    private String FILES_SEPARATOR;

    @Autowired
    private InternalLayerConnectorService connector;


    @Override
    public void getProcessedFiles(List<Long> fileIds, ServletOutputStream output) throws IOException {

        Iterator<Long> it = fileIds.iterator();

        while (it.hasNext()) {
            Long id = it.next();
            connector.getFile(id, output);

            if (it.hasNext()) {
                output.write("\n".getBytes());
                output.write(FILES_SEPARATOR.getBytes());
                output.write("\n".getBytes());
            }
        }
    }
}
