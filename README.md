# icd-mongodb-cfenv-processor

A CfEnvProcessor for ICD MongoDB.

It builds on top of : 

https://github.com/BarDweller/ssl-context-spring-boot-starter

https://github.com/BarDweller/bean-customizer-spring-boot


The former creates sslcontexts using application.properties entries.
The latter is used as quick common approach to tailoring idenitfied config beans in Spring. 

This cfenv processor will take the info from vcap services
and will write out sslcontext information for the ssl-context-spring-boot-starter
and will also create properties for a mongo config customizer bean,
and of course, for spring-data-mongo. 

Net result is spring-data configured to talk to mongo, with a 
customized config that will add an ssl context based on the vcap_services info.

Sets properties : 

- spring.data.mongodb.uri - to set the mongodb url/host/port/credentials etc.
- sslcontext.enabled - to enable the ssl context starter
- sslcontext.contexts.mongodb.trustedcert - to the base64 encoded trusted cert.
- cfenv.processor.icdmongo.enabled - to enable the mongo config post-processor that enables SSL for mongo.
- cfenv.processor.icdmongo.sslcontext - to identify which ssl context should be used for mongo ('mongodb')
