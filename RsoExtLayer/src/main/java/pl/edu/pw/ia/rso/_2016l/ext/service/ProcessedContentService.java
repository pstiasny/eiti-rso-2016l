package pl.edu.pw.ia.rso._2016l.ext.service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by maczaj on 18.05.16.
 */
public interface ProcessedContentService {

    void getProcessedFiles(List<Long> fileIds, ServletOutputStream outStream) throws IOException;
}
