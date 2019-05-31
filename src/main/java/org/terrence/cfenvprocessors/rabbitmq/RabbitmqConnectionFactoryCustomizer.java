package org.terrence.cfenvprocessors.rabbitmq;

import javax.net.ssl.SSLContext;

import com.mongodb.MongoClientOptions;

import org.ozzy.beancustomizer.config.BeanCustomizer;
import org.ozzy.beancustomizer.config.ExtensibleTypedBeanProcessor;
import org.ozzy.sslcontext.config.SslcontextConfig;
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
import org.springframework.data.mongodb.core.MongoClientOptionsFactoryBean;

import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;

/**
 * Expects cfenv.processor.icdmongo.enabled=true
 * cfenv.processor.icdmongo.sslcontext=name-of-sslcontext-bean
 */
@Configuration
@ConditionalOnProperty(name = "cfenv.processor.icdmongo.enabled", havingValue = "true")
@AutoConfigureAfter({ ExtensibleTypedBeanProcessor.class, SslcontextConfig.class })
public class RabbitmqConnectionFactoryCustomizer implements BeanCustomizer {

    @Autowired
    SslcontextConfig scc;

    @Autowired
    ApplicationContext ctx;

    private final String ctxName;

    public MongoClientOptionsCustomizer(@Value("${cfenv.processor.icdmongo.sslcontext}") final String ctxName){
        this.ctxName = ctxName;
    }

    private SSLContext getContext() {
        return ctx.getBean(ctxName, SSLContext.class);
    }

    @Override
    public Class getType() {
        return MongoClientOptions.class;
    }

    @Override
    public Object postProcessBeforeInit(Object original) {
        try {
            MongoClientOptions o = (MongoClientOptions) original;
            return MongoClientOptions.builder(o).sslEnabled(true).socketFactory(getContext().getSocketFactory())
                    .build();
        } catch (Exception e) {
            throw new FatalBeanException("Unable to add SSL to MongoOptions bean", e);
        }
    }

    @Override
    public Object postProcessAfterInit(Object original) {
        return original;
    }

    // Create default bean to customize if the user code didn't have one.
    @Bean
    @ConditionalOnMissingBean
    public MongoClientOptions defaultMongoClientOptions() {
        try {
            final MongoClientOptionsFactoryBean bean = new MongoClientOptionsFactoryBean();
            bean.afterPropertiesSet();
            MongoClientOptions mco = bean.getObject();
            return mco;
        } catch (final Exception e) {
            throw new BeanCreationException(e.getMessage(), e);
        }
    }

}