package org.terrence.cfenvprocessors.rabbitmq;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

public class RabbitmqCfEnvProcessor implements CfEnvProcessor {

    private static final Logger LOG = Logger.getLogger(RabbitmqCfEnvProcessor.class.getName());

    public RabbitmqCfEnvProcessor() {
    }

    @Override
    public boolean accept(CfService service) {
        boolean match = service.existsByLabelStartsWith("messages-for-rabbitmq");
        System.out.println("Match [" + match + "] to service " + service.toString());
        return match;
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder()
                .propertyPrefixes("cfenv.processor.rabbitmq,sslcontext,spring.data.rabbitmq").serviceName("RabbitMQ")
                .build();
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {

        Map<String, Object> credentials = cfCredentials.getMap();

        String uri = null;
        String trustedcert = null;
        Map<String, Object> connection = (Map<String, Object>) credentials.get("connection");
        if (connection != null) {
            Map<String, Object> details = (Map<String, Object>) connection.get("amqps");
            if (details != null) {
                List<String> uris = (List<String>) details.get("composed");
                if (uris.size() > 0) {
                    uri = uris.get(0);
                }
                Map<String, Object> certinfo = (Map<String, Object>) details.get("certificate");
                trustedcert = certinfo.get("certificate_base64").toString();
            }
        }

        if (uri != null && trustedcert != null) {
            properties.put("spring.data.rabbitmq.uri", uri);

            properties.put("sslcontext.enabled", true);
            properties.put("sslcontext.contexts.rabbitmq.trustedcert", trustedcert);

            properties.put("cfenv.processor.rabbitmq.enabled", true);
            properties.put("cfenv.processor.rabbitmq.sslcontext", "rabbitmq");
        } else {
            LOG.warning("Unable to process vcap services entry for rabbitmq uri:" + (uri != null) + " cert:"
                    + (trustedcert != null));
        }
    }
}