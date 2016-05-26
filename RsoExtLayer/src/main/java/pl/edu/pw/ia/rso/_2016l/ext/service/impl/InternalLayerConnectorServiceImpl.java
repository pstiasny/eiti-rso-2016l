package pl.edu.pw.ia.rso._2016l.ext.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.edu.pw.ia.rso._2016l.ext.exception.SystemUnavailableException;
import pl.edu.pw.ia.rso._2016l.ext.service.InternalLayerConnectorService;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maczaj on 18.05.16.
 */
@Service
@Slf4j
public class InternalLayerConnectorServiceImpl implements InternalLayerConnectorService, InitializingBean {

    @Value("${rso.dl.buffer.size}")
    private int BUFFER_SIZE;

    @Value("${user.agent}")
    private String USER_AGENT;

    @Autowired
    private ApplicationContext context;

    private List<String> internals = new ArrayList<>();

    private boolean alive = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        Environment env = context.getEnvironment();
        ((AbstractEnvironment) env).getPropertySources().forEach(src -> {
            if (src instanceof MapPropertySource) {
                MapPropertySource source = (MapPropertySource) src;
                String[] propertyNames = source.getPropertyNames();
                Arrays.asList(propertyNames).stream().filter(name -> name.startsWith("rso.url.node_")).forEach(p -> {
                    String value = (String) source.getProperty(p);
                    log.info("Registering internal node: " + value);
                    internals.add(value);
                });
            }
        });
    }

    @Override
    public void getFile(long id, ServletOutputStream output) throws IOException {
//        TODO: obadac doca pod katem algorytmu wyboru wezla do ktorego uderzac
        if (!alive) {
            throw new SystemUnavailableException();
        }

        URL url = new URL("url do wewn, pewnie jakiś mądry algorytm zeby puknąć we włąściwy");
        ReadableByteChannel channel = Channels.newChannel(url.openStream());
        ByteBuffer bf = ByteBuffer.allocate(BUFFER_SIZE);
        int read = 0;
        do {
            output.write(channel.read(bf));
        } while (read > 0);

    }

    /**
     * Vilatity check performed every minute
     */
    @Scheduled(cron = "0 * * * * *")
    @Override
    public void checkVitality() throws IOException {
//        TODO: ping kazdego internala
        int available = 0;
        for (String node : internals) {
            URL url = new URL(node + "/api/ping");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            TODO: w wewn przydaloby sie API do pinga
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", USER_AGENT);
            int respCode = connection.getResponseCode();

            if(respCode == 200) {
                ++available;
            }

        }
//        FIXME: system jest dead jak wszystkie wewn leza czy jak tyle co sie replikuje?
        alive = available != 0;
    }

}
