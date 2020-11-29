package com.petpdb.shell.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public final class PetDB {

    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    private final InetSocketAddress address;
    private Connection connection;
    private boolean isRunning;

    public PetDB(InetSocketAddress address) {
        this.address = address;
        try {
            this.connection = new Connection(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open() {
        if (this.isRunning) {
            throw new PetDBClientConnectionException(
                    "Connection to PetDB server is already open");
        }
        this.isRunning = true;
        this.pool.submit(this.connection);
    }

    public void close() {
        this.isRunning = false;
        this.connection.close();
        this.pool.shutdown();
    }

    public String call(String query) {
        if (query.isEmpty() || query.isBlank()) {
            return "";
        } else if (this.pool.isShutdown() ||
                this.pool.isTerminated() ||
                !this.isRunning) {
            this.isRunning = false;
            throw new PetDBClientConnectionException(
                    "Connection to PetDB server is not open");
        }
        this.connection.newRequest(query.trim());
        return this.connection.getResponse();
    }
}
