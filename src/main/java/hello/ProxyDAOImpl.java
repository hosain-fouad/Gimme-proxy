package hello;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hosainfathelbab on 2/23/15.
 */
@Transactional
@Repository(value = "proxyDAO")
public class ProxyDAOImpl implements ProxyDAO {

    @Autowired
    private SessionFactory sessionFactory;

//    @Autowired
//    HibernateTransactionManager hibernateTransactionManager;

    @Override
    public boolean isExist(MyProxyServer proxy) {
        return sessionFactory.getCurrentSession().get(MyProxyServer.class, proxy.getId()) != null;
    }

    @Override
    public void add(MyProxyServer proxy) {
        sessionFactory.getCurrentSession().persist(proxy);
    }

    @Override
    public List<MyProxyServer> getAllProxies() {
        return sessionFactory.getCurrentSession().createQuery("from MyProxyServer").list();
    }

    @Override
    public List<MyProxyServer> getAllProxiesByCountry() {
        return null;
    }

    @Override
    public List<MyProxyServer> getAllProxiesByType() {
        return null;
    }

    @Override
    public void removeProxy(MyProxyServer proxy) {
        sessionFactory.getCurrentSession().delete(proxy);
    }
}
