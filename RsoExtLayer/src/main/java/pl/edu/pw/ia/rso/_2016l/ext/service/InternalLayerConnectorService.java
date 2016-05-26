package pl.edu.pw.ia.rso._2016l.ext.service;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by maczaj on 18.05.16.
 */
public interface InternalLayerConnectorService {

    public void getFile(long id, ServletOutputStream output) throws IOException;

    public void checkVitality() throws IOException;

}
