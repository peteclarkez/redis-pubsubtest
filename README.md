# Spring Boot Redis PubSub Test

Small sample project to try out some redis messaging config as a POC

Start Eureka-server and Publisher as is.
Start 2 Instances of Receiver
& Pass PORT and MGMTPORT as System parameters

e.g.

-DPORT=8081 -DMGMTPORT=8089<br/>
-DPORT=8082 -DMGMTPORT=8090

### Extras

As part of this project I've created Actuator End points to be able to control the Redis Publishers and Subscriptions.

As well as Actuator EndPoints there are also MVC Endpoints to control the list of names/topics that are used for communications.

I've also used Lombok for the domain objects.


### TODO

- Find how to run all this with spring boot's maven plugin
- Add option to use consul instead of Eureka & listen to the consul cloud bus for app availability events
- Start Testing Failover in the case that a Receiver is removed
