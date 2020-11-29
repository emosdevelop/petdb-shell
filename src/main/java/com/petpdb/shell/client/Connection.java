package com.petpdb.shell.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class Connection implements Runnable {

    private final static int END_OF_STREAM = -1;

    private final ByteBuffer readBuffer = ByteBuffer.allocate(1024 * 1024);
    private final BlockingQueue<String> requestQueue = new ArrayBlockingQueue<>(1);
    private final BlockingQueue<String> responseQueue = new ArrayBlockingQueue<>(1);
    private final Selector selector;
    private final SocketChannel channel;

    public Connection(InetSocketAddress address) throws IOException {
        this.selector = Selector.open();
        this.channel = SocketChannel.open(address);
        channel.configureBlocking(false);
        channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    @Override
    public void run() {
        while (this.channel.isOpen()) {
            try {
                int channels = this.selector.select();
                if (channels == 0) continue;
                var keys = this.selector.selectedKeys().iterator();
                if (keys.hasNext()) {
                    var key = keys.next();
                    keys.remove();
                    if (!key.isValid()) throw new IOException();
                    else if (key.isWritable()) {
                        this.write(key);
                    } else if (key.isReadable()) {
                        this.read(key);
                    }
                }
            } catch (IOException | InterruptedException e) {
                this.close();
            }
        }
    }

    private void write(SelectionKey key) throws IOException, InterruptedException {
        String request = this.requestQueue.take();
        this.channel.write(ByteBuffer.wrap(request.getBytes(StandardCharsets.UTF_8)));
        key.interestOps(SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException, InterruptedException {
        this.readBuffer.clear();
        int bytes = this.channel.read(this.readBuffer);
        if (bytes == END_OF_STREAM) throw new IOException();
        this.responseQueue.put(new String(this.readBuffer.array(), 0, bytes, StandardCharsets.UTF_8));
        key.interestOps(SelectionKey.OP_WRITE);
    }

    public void close() {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newRequest(String keyword) {
        try {
            this.requestQueue.put(keyword);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        String response = null;
        try {
            response = this.responseQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
