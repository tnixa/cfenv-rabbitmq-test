# icd-mongodb-cfenv-processor

A CfEnvProcessor for ICD MongoDB.

Or.. it will be, when it's done =)

It builds on top of : 

https://github.com/BarDweller/ssl-context-spring-boot-starter

https://github.com/BarDweller/bean-customizer-spring-boot


The former creates sslcontexts using application.properties entries.
The latter is a quick common approach to tailoring config beans in spring. 

The intent is this cfenv processor will take the info from vcap services
and will write out sslcontext information for the ssl-context-spring-boot-starter
and will also create properties for a mongo config customizer bean,
and of course, for spring-data-mongo. 

Net result should be spring-data configured to talk to mongo, with a 
customized config that will add an ssl context based on the vcap_services info.

At the mo, this project is just a test framework making sure that the above will work.

It does not (yet) contain the actual cf-enf-processor, and isn't suitable yet
to be used as a library.
