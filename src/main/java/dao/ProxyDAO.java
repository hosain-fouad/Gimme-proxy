package dao;

import model.MyProxyServer;

import java.util.List;

/**
 * Created by hosainfathelbab on 2/23/15.
 */
public interface ProxyDAO {

    public boolean isExist(MyProxyServer proxy);
    public void add(MyProxyServer proxy);
    public List<MyProxyServer> getAllProxies();
    public List<MyProxyServer> getAllProxiesByCountry();
    public List<MyProxyServer> getAllProxiesByType();
    void removeProxy(MyProxyServer proxy);
}
