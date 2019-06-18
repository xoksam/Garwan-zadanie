# Garwan-zadanie
Tento projekt vznikol ako zadanie z pohovoru spoločnosti Garwan Consulting, s.r.o.
Ide o Spring Boot aplikáciu naprogramovanú v jazyku Java 8 s využitím viacerých technológií. 

##Použité technológie

- Maven 3.6.1
- Java 8
- REST API
- Spring Boot, Data, Security, WEB
- JWT
- Auth0
- JPA
- Hibernate 
- mySQL

## Návod ako zbuildiť deployovatelný artefakt
**Kedže ide o Maven projekt, je nutné ho mať nainštalovaný!**

    git clone https://github.com/xoksam/Garwan-zadanie.git
    cd Garwan-zadanie
    mvn clean install
Maven by mal takto vytvoriť v podadresári `target` spustiteľný `.jar` súbor. Čiže na jeho spustenie stačí zadať

    cd target
    java -jar eshop-0.0.1-SNAPSHOT
