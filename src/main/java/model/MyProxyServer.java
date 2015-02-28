package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by hosainfathelbab on 2/20/15.
 */
@Entity
@Table(name = "proxy")
public class MyProxyServer {
    private String id;
    private String country;
    private String ip;
    private int port;
    private String protocol;
    // speed has values from 1 to 10 , highest speed is 10
    private int speed = 5;
    private Date lastUpdated = new Date(System.currentTimeMillis());

    public MyProxyServer() {}



    public MyProxyServer(String country, String ip, int port, String protocol) {
        this.country = country;
        this.ip = ip;
        this.port = port;
        this.protocol = protocol;
    }

    @Id
    @Column(name = "proxyId", unique = true, nullable = false)
    public String getId() {
        return ip+":"+port;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "ipNumber")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Column(name = "portNumber")
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Column(name = "type")
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Column(name = "speed")
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Column(name = "lastUpdated")
    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString(){
        return this.getId();
    }
}
