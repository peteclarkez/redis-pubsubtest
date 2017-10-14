# Spring Boot Redis PubSub Test

Small sample project to try out some redis messaging config as a POC

Start Eureka-server and Publisher as is.
Start 2 Instances of Receiver
& Pass PORT and MGMTPORT as System parameters

e.g.

-DPORT=8081 -DMGMTPORT=8089<br/>
-DPORT=8082 -DMGMTPORT=8090

###TODO
- Find how to run all this with spring boot's maven plugin