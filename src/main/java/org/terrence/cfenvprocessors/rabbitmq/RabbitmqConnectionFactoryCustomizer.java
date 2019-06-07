package org.terrence.cfenvprocessors.rabbitmq;

import javax.net.ssl.SSLContext;

import org.ozzy.beancustomizer.config.BeanCustomizer;
import org.ozzy.beancustomizer.config.ExtensibleTypedBeanProcessor;
import org.ozzy.sslcontext.config.SslcontextConfig;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Expects cfenv.processor.rabbitmq.enabled=true
 * cfenv.processor.rabbitmq.sslcontext=name-of-sslcontext-bean
 */
@Configuration
@ConditionalOnProperty(name = "cfenv.processor.rabbitmq.enabled", havingValue = "true")
@AutoConfigureAfter({ ExtensibleTypedBeanProcessor.class, SslcontextConfig.class })
public class RabbitmqConnectionFactoryCustomizer implements BeanCustomizer {

    @Autowired
    SslcontextConfig scc;

    @Autowired
    ApplicationContext ctx;

    private final String ctxName;

    public RabbitmqConnectionFactoryCustomizer(@Value("${cfenv.processor.rabbitmq.sslcontext}") final String ctxName) {
        System.out.println("RCFBC constructor");
        this.ctxName = ctxName;
    }

    private SSLContext getContext() {
        return ctx.getBean(ctxName, SSLContext.class);
    }

    @Override
    public Class getType() {
        return RabbitConnectionFactoryBean.class;
    }

    @Override
    public Object postProcessBeforeInit(Object original) {
        try {
            System.out.println("postProcessBeforeInit");
            return original;
        } catch (Exception e) {
            throw new FatalBeanException("Unable to add SSL to RabbitMQ bean", e);
        }
    }

    @Override
    public Object postProcessAfterInit(Object original) {
        return original;
    }

    // Create default bean to customize if the user code didn't have one.
    @Bean
    @ConditionalOnMissingBean
    public RabbitConnectionFactoryBean defaultConnectionFactory() {
        System.out.println("defaultConnectionFactory before try");
        try {
            final RabbitConnectionFactoryBean bean = new RabbitConnectionFactoryBean();
            System.out.println("defaultConnectionFactory after new RCFB");
            bean.afterPropertiesSet();
            System.out.println("defaultConnectionFactory after bean.propertiesSet");
            return bean;
        } catch (final Exception e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }

}