package com.petpdb.shell.service;

import com.petpdb.shell.client.PetDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;

@Service
public class PetDBService {

    private final static Logger LOGGER = LoggerFactory.getLogger(PetDBService.class);

    private PetDB db;
    @Value("${petdb.port}")
    private String port;
    @Value("${petdb.host}")
    private String host;

    public String call(String query) {
        return db.call(query);
    }

    public void close() {
        this.db.close();
    }

    public void open() {
        this.db.open();
    }

    @PostConstruct
    public void connect() throws IOException {
        LOGGER.info(String.format("Connecting to PetDB Server - port:%s host:%s", this.port, this.host));
        this.db = new PetDB(new InetSocketAddress(host, Integer.parseInt(port)));
    }
}
