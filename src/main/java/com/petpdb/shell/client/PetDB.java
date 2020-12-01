package com.petpdb.shell.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;


public final class PetDB {

    public final static AtomicBoolean isRunning = new AtomicBoolean();

    private final ExecutorService pool = Executors.newFixedThreadPool(1);
    private final InetSocketAddress address;
    private final Connection connection;

    public PetDB(InetSocketAddress address) throws IOException {
        this.address = address;
        this.connection = new Connection(address);
    }

    public void open() {
        if (PetDB.isRunning.get()) {
            throw new PetDBClientConnectionException(
                    "Connection to PetDB server is already open");
        }
        PetDB.isRunning.set(true);
        this.pool.submit(this.connection);
    }

    public void close() {
        PetDB.isRunning.set(false);
        this.connection.close();
    }

    public String call(String query) {
        if (query.isEmpty() || query.isBlank()) {
            return "";
        } else if (this.pool.isShutdown() ||
                this.pool.isTerminated() ||
                !PetDB.isRunning.get()) {
            PetDB.isRunning.set(false);
            throw new PetDBClientConnectionException(
                    "Connection to PetDB server is not open");
        }
        this.connection.newRequest(query.trim());
        return this.connection.getResponse();
    }

    public InetSocketAddress getAddress() {
        return this.address;
    }
}
