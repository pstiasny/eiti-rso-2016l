package pl.edu.pw.ia.rso._2016l.ext.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.pw.ia.rso._2016l.ext.misc.Constants;
import pl.edu.pw.ia.rso._2016l.ext.service.InternalLayerConnectorService;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by maczaj on 18.05.16.
 */
@Service
@Slf4j
public class InternalLayerConnectorServiceImpl implements InternalLayerConnectorService{


    @Override
    public void getFile(long id, ServletOutputStream output) throws IOException {
//        TODO: obadac doca pod katem algorytmu wyboru wezla do ktorego uderzac
            URL url = new URL("url do wewn, pewnie jakiś mądry algorytm zeby puknąć we włąściwy");
            ReadableByteChannel channel = Channels.newChannel(url.openStream());
            ByteBuffer bf = ByteBuffer.allocate(Constants.BUFFER_SIZE);
            int read = 0;
            do {
                output.write(channel.read(bf));
            } while (read > 0);

    }
}
