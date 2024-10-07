# Installatie handleiding LABflow

In dit document geven we uitleg rond hoe je de LABflow web applicatie kan recreëren en zelf kan installeren.

**Inhoudstafel:**

- **1** - Hoe project aanmaken
- **2** - Project klaar voor development
- **2.1** - database
- **2.2** - backend
- **2.3** - frontend
- **3** - project deployen naar productie
- **3.1** - database
- **3.2** - backend
- **3.3** - frontend

## 1 - Hoe project aanmaken

Eerst en vooral gaan we bespreken hoe je de LABflow applicatie zelf van scratch kan opstarten. Hieronder wordt stap voor stap uitgelijnd hoe je tot op hetzelfde startpunt kan komen als wij zijn gestart.

**surf naar volgende website [spring boot initialiser](https://start.spring.io/)**

> op volgende site kan je een java springboot project starten zonder zelf te moeten configureren. Deze site gaat voor jouw een pom.xml file maken met alle nodige info voor een springboot project en jij kan zelf selecteren welke versies je wil gebruiken van java, maven, etc.

**settings:**

- project: Maven
- language: Java
- springboot: 3.3.4
- group: com.thomasmore.blc
- artifact: LabFlow
- name: LabFlow
- packaging: jar
- java version: 17

**Je krijgt een ZIP file en deze moet je unzippen en openen in je IntelliJ**

**surf naar [mvnrepository.com](https://mvnrepository.com/) en voeg volgende dependencies ook nog toe:**

- [ ] [SQLite JDBC](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc)
- [ ] [Spring Boot Starter Web](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web)
- [ ] [Hibernate ORM hibernate community dialects](https://mvnrepository.com/artifact/org.hibernate.orm/hibernate-community-dialects)
- [ ] [Spring Boot DevTools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
- [ ] [Spring Boot Starter Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test)

## 2 - klaar voor development

### 2.1 - Backend

>bij het openen van het project krijg je normaal 2 grote errors

- .env ontbreekt
- Alle files met .db extensie ontbreken

**ga naar application.properties**

> path: src > main > resources > application.properties

plaats volgende code in deze file:

```# .env import
spring.config.import=file:.env[.properties]

spring.application.name=labflow

# database connection

# the datasource url points to our database

spring.datasource.url=jdbc:sqlite:databases/labflow.db
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.driver-class-name=org.sqlite.JDBC

spring.datasource.username=${env.DATABASE_USERNAME}
spring.datasource.password=${env.DATABASE_PASSWORD}

spring.jpa.show-sql=true
```

**maak een nieuwe .env aan:**

1. maak een duplicaat van de file `.env.example`
2. daar plaats je 2 zaken in `DATABASE_USERNAME=admin123` en `DATABASE_PASSWORD=admin123`

**maak de database map en file aan:**

1. in de root van je applicatie maak je een map `databases` aan
2. daarin zet je een file `labflow.db`

> path: :databases/labflow.db

3. open rechts de tool voor databases
4. sleep de labflow.db file in het venster van de database tool

**Laat het project runnen en kijk of het werkt!**
> werkt het toch niet contacteer [Nathan Neve](mailto:r0742822@student.thomasmore.be) of [César Van Leuffelen](mailto:r0929448@student.thomasmore.be)
