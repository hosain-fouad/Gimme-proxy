package engines;

import dao.ProxyDAO;
import model.MyProxyServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import us.codecraft.xsoup.Xsoup;
import utils.Parser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by hosainfathelbab on 2/24/15.
 */
@Component
@Scope("prototype")
public class ProxyGeneratorEngine extends Thread{


    @Autowired
    private ProxyDAO proxyDAO;

    private final static Logger LOG = Logger.getLogger(ProxyGeneratorEngine.class.getName());

    private static final int SLEEP_TIME = 5 * 60 * 1000;

    @Override
    public void run() {
        while(true){
            try {
                updateProxyList("http://proxylist.hidemyass.com/");
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                LOG.severe("[error in ProxyGeneratorEngine] " + e);
            }
        }
    }

    public List<MyProxyServer> updateProxyList(String url) throws IOException, ParserConfigurationException, SAXException {
        Document doc = Jsoup.connect(url).get();
        List<String> ipList = new ArrayList<String>() ;
        for(Element element : Xsoup.compile("//*[@id=\"listable\"]/tbody/tr/td[2]/span").evaluate(doc).getElements()){
            ipList.add(Parser.getIp(element));
        }

        List<String> portList = new ArrayList<String>() ;
        for(Element element : Xsoup.compile("//*[@id=\"listable\"]/tbody/tr/td[3]").evaluate(doc).getElements()){
            portList.add(element.html().replaceAll("\n", "").replaceAll(" ", ""));
        }

        List<String> protocolList = new ArrayList<String>() ;
        for(Element element : Xsoup.compile("//*[@id=\"listable\"]/tbody/tr/td[7]").evaluate(doc).getElements()){
            protocolList.add(element.html().replaceAll("\n","").replaceAll(" ",""));
        }

        List<String> countryList = new ArrayList<String>() ;
        for(Element element : Xsoup.compile("//*[@id=\"listable\"]/tbody/tr/td[4]/span").evaluate(doc).getElements()){
            String countryHtml = element.html();
            countryList.add(countryHtml.substring(countryHtml.lastIndexOf(">")+1));
        }

        List<MyProxyServer> proxies = new ArrayList<MyProxyServer>(countryList.size());
        for (int i =0 ; i< countryList.size() ; i++){
            proxies.add(new MyProxyServer(countryList.get(i), ipList.get(i), Integer.parseInt(portList.get(i)), protocolList.get(i)));
        }

        for(MyProxyServer proxy : proxies){
            if(!proxyDAO.isExist(proxy)){
                proxyDAO.add(proxy);
            }
        }
        return proxies;
    }
}
