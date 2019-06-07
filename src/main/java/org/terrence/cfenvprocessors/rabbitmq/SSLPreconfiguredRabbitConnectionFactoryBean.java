package org.terrence.cfenvprocessors.rabbitmq;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ThreadFactory;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ExceptionHandler;
import com.rabbitmq.client.MetricsCollector;
import com.rabbitmq.client.SaslConfig;
import com.rabbitmq.client.SocketConfigurator;
import com.rabbitmq.client.impl.nio.NioParams;

import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;

public class SSLPreconfiguredRabbitConnectionFactoryBean extends RabbitConnectionFactoryBean {

    @Override
    protected void createSSlContext() throws NoSuchAlgorithmException { // Error: The method createSSlContext() of type
                                                                        // SSLPreconfiguredRabbitConnectionFactoryBean
                                                                        // must override or implement a supertype method
        super.createSSLContext();
    }

    public void configureFrom(RabbitConnectionFactoryBean rcfb) {
        // pass in the original bean (rcfb)

        // call all the getters and pass the results to the corresponding setters

        // for the ones calling set on the internal cfb object get it by doing
        // createconnectionfactory or something on the original bean

        // get all the extra properties by getting the internal cfb then calling the
        // getters on that and invoking the corresponding setters on myself

        // ConnectionFactory ocf = original.connectionFactory;

        rcfb.setUseSSL(false);

        ConnectionFactory cf = rcfb.getObject();

        String a = cf.getHost();
        if (a != null) {
            setHost(a);
        }

        int b = cf.getPort(); // is 0 valid?
        if (b != 0) {
            setPort(b);
        }

        String c = cf.getUsername();
        if (c != null) {
            setUsername(c);
        }

        String d = cf.getPassword();
        if (d != null) {
            setPassword(d);
        }

        String f = cf.getVirtualHost();
        if (e != null) {
            setVirtualHost(f);
        }

        setUri(cf.getUri()); // get not defined

        int g = cf.getRequestedChannelMax(); // is 0 valid?
        if (g != 0) {
            setRequestedChannelMax(g);
        }

        int h = cf.getRequestedFrameMax(); // is 0 valid?
        if (h != 0) {
            setRequestedFrameMax(h);
        }

        int i = cf.getConnectionTimeout(); // is 0 valid?
        if (i != 0) {
            setConnectionTimeout(i);
        }

        int j = cf.getRequestedHeartbeat(); // is 0 valid?
        if (j != 0) {
            setRequestedHeartbeat(j);
        }

        Map<String, Object> k = cf.getClientProperties(); // null valid for Map?
        if (k != null) {
            setClientProperties(k);
        }

        SaslConfig l = cf.getSaslConfig(); // null valid for SaslConfig?
        if (l != null) {
            setSaslConfig(l);
        }

        SocketFactory m = cf.getSocketFactory(); // null valid for SocketFactory
        if (m != null) {
            setSocketFactory(cf.getSocketFactory());
        }

        SocketConfigurator n = cf.getSocketConfigurator(); // null valid for SocketConfigurator?
        if (n != null) {
            setSocketConfigurator(n);
        }

        ExecutorService o = cf.getSharedExecutor(); // get not defined
        if (o != null) {
            setSharedExecutor(o);
        }

        ThreadFactory p = cf.getThreadFactory(); // null valid for ThreadFactory?
        if (p != null) {
            setThreadFactory(p);
        }

        ExceptionHandler q = cf.getExceptionHandler(); // null valid for ExceptionHandler
        if (q != null) {
            setExceptionHandler(q);
        }

        setUseNio(cf.getUseNio()); // get not defined

        NioParams s = cf.getNioParams(); // null valid for NioParams?
        if (s != null) {
            setNioParams(s);
        }

        MetricsCollector t = cf.getMetricsCollector(); // null valid for MetricsCollector?
        if (t != null) {
            setMetricsCollector(t);
        }

        setAutomaticRecoveryEnabled(cf.getAutomaticRecoveryEnabledf()); // get not defined

        setTopologyRecoveryEnabled(cf.getTopologyRecoveryEnabled()); // get not defined

        int u = cf.getChannelRpcTimeout(); // is 0 valid?
        if (u != 0) {
            setChannelRpcTimeout(u);
        }

        setEnableHostnameVerification(cf.getEnableHostnameVerification()); // get not defined

    }

}