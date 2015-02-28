package engines;

import dao.ProxyDAO;
import model.MyProxyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by hosainfathelbab on 2/24/15.
 */
@Component
@Scope("prototype")
public class ProxyCleanerEngine extends Thread{

    @Autowired
    private ProxyDAO proxyDAO;

    private static final int SLEEP_TIME = 10 * 60 * 1000;

    @Override
    public void run() {
        while(true){
            try {
                clean();
                sleep(SLEEP_TIME);
            }
            catch (Exception e){
                System.err.println("[error in ProxyCleanerEngine] " + e);
            }
        }
    }

    private void clean(){
        for(MyProxyServer proxy : proxyDAO.getAllProxies()){
            if(!isReachable(proxy)){
                proxyDAO.removeProxy(proxy);
            }
        }
    }


    private boolean isReachable(MyProxyServer proxy){
        Socket socket = null;
        boolean reachable = false;
        try {
            socket = new Socket(proxy.getIp(), proxy.getPort());
            reachable = true;
        } catch (Exception e) {
            System.err.println("Proxy " + proxy + " is not reachable.");
        } finally {
            if (socket != null) try { socket.close(); } catch(IOException e) {}
        }
        return reachable;
    }


}
