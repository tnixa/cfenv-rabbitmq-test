# cfenv-rabbitmq-test

A CfEnvProcessor for RabbitMQ

It builds on top of : 

https://github.com/BarDweller/ssl-context-spring-boot-starter

https://github.com/BarDweller/bean-customizer-spring-boot


The former creates sslcontexts using application.properties entries.
The latter is used as quick common approach to tailoring idenitfied config beans in Spring. 

This cfenv processor will take the info from vcap services
and will write out sslcontext information for the ssl-context-spring-boot-starter
and will also create properties for a rabbitmq config customizer bean



Sets properties : 

- spring.data.rabbitmq.uri - to set the rabbitmq url/host/port/credentials etc.
- sslcontext.enabled - to enable the ssl context starter
- sslcontext.contexts.rabbitmq.trustedcert - to the base64 encoded trusted cert.
- cfenv.processor.rabbitmq.enabled - to enable the rabbitmq config post-processor that enables SSL for rabbitmq.
- cfenv.processor.rabbitmq.sslcontext - to identify which ssl context should be used for rabbitmq ('rabbitmq')
