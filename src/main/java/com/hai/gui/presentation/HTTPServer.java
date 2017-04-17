package com.hai.gui.presentation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import sun.nio.cs.StandardCharsets;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

/**
 * Created by mrsfy on 16-Apr-17.
 */
public class HTTPServer {

    private static HTTPServer _instance;

    public static HTTPServer getInstance() {
        if (_instance == null)
            _instance = new HTTPServer();

        return _instance;
    }

    private HttpServer server = null;

    public void start() {
        server.start();
        Logger.getLogger(getClass().getName()).info("HTTP Server started.");
    }

    private HTTPServer() {
        try {
            server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 80),0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new HTTPHandler());

    }

    private class HTTPHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            URI uri = t.getRequestURI();
            String response = readFile(getClass().getClassLoader().getResource("www" + uri.getPath()).getFile().replaceFirst("/", ""), Charset.defaultCharset());// "Path: " + uri.getPath() + "\n";
            Logger.getLogger(this.getClass().getName()).info(response);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        private String readFile(String path, Charset encoding)
                throws IOException
        {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        }
    }
}
