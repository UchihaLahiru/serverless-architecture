package launch;

import transport.http.server.HttpServer;

public class HttpServerLauncher implements Runnable {
    @Override
    public void run() {
        HttpServer.initServer();
    }
}
