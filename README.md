# Prototype for IoT Solution (Black and White Images API)

## Überblick
Dies ist ein Prototyp für eine IoT-Lösung, die mit Java Spring Boot entwickelt wurde. Die Anwendung bietet zwei APIs: eine zum Hochladen und Komprimieren von Schwarz-Weiß-Bildern und eine zum Abrufen und Dekomprimieren der gespeicherten Bilder. Diese Lösung wurde speziell für die Verarbeitung von Bildern, die von IoT-Geräten kommen, konzipiert.

![cloud_architecture_aws_iot (3)](https://github.com/user-attachments/assets/ff4c318a-6041-4736-b5dc-5f19f9bc26f8)


## Technologien
- **Programmiersprache**: Java 21
- **Framework**: Spring Boot
- **Build-Tool**: Maven
- **Betriebssystem**: Windows 11

## Maven-Abhängigkeiten

### Web
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
### Security
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
Hinweis: Obwohl Spring Boot Security integriert ist, wurde es für Entwicklungs- und Testzwecke deaktiviert. Für die Sicherheit der API, wird empfohlen Security zu aktivieren

### JPA - Daten Persistance
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```
### H2-Datenbank
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
### Swagger - API Documentation
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-ui</artifactId>
    <version>1.5.10</version>
</dependency>
```

## Installation und Konfiguration
### Voraussetzungen
- JDK 21
- Maven
- Git 
- Zugang zum  Remote-Repository (GitHub)
  
### Schritte zur Installation
1- Klone das Projekt-Repository:
```bash
git clone https://github.com/Zoegrizzly/BlackAndWhite_IoTPublic.git
```

2- Navigiere in das Verzeichnis des geklonten Projekts:
```bash
cd <DEIN-PROJECT-DIRECTORY>
```
3- Baue das Projekt mit Maven:
```bash
mvn clean install
```
4- Starte die Anwendung:
```bash
mvn spring-boot:run
```

## Funktionsweise und Architektur

Die Anwendung besteht aus zwei Haupt-APIs:

- uploadImage API: Diese API empfängt Bilddaten (Schwarz-Weiß-Bilder) vom API Gateway, komprimiert die Bilder und speichert die komprimierten Bilddaten (in Base64 kodiert) in der H2-Datenbank.

- recoverImage API: Diese API ermöglicht es, anhand der Bild-ID das komprimierte Bild aus der Datenbank abzurufen, es zu dekomprimieren und an den Benutzer zurückzugeben.

### Interaktion mit der Anwendung
Auch ohne Benutzeroberfläche kann die Backend-API der Anwendung mit Swagger getestet werden. Swagger ist ein Framework, das eine benutzerfreundliche Oberfläche für die API-Dokumentation und das Testen bietet. Es ermöglicht Entwicklern, API-Endpunkte einfach zu testen, ohne dass externe Tools benötigt werden. Die Applikation ist hier erreichbar: http://localhost:8080/swagger-ui/index.html
![swagger](https://github.com/user-attachments/assets/992995ce-ed42-4cf1-841d-1c3a811a347a)



### Serverless-Architektur mit AWS
Obwohl das vorgeschlagene Modell on-premise ist, gibt es auch eine Option für den Einsatz in der Cloud mit einer serverlosen Architektur auf AWS. Dabei könnten AWS Lambda und API Gateway verwendet werden.

![cloud_architecture_aws_iot (3)](https://github.com/user-attachments/assets/3e6728a9-804b-4cd4-b5b5-addfb5b814aa)


Vorteile einer Serverless-Architektur:

- Effizienz: Serverless-Lösungen benötigen keine ständige Verwaltung und Wartung von Servern, was die Betriebskosten senkt und eine hohe Skalierbarkeit ermöglicht.
  
- Ressourcennutzung: Sie zahlen nur für die tatsächliche Nutzung und können somit Kosten sparen, insbesondere bei unregelmäßigen oder schwankenden Workloads.
  
- Technologische Flexibilität: Serverless-Architekturen bieten einfache Integrationen und Anpassungen an verschiedene Geschäftsanforderungen, ohne sich um die zugrunde liegende Infrastruktur kümmern zu müssen.

### On-primise Architektur
![software_architecture_black_white_photos_IoT](https://github.com/user-attachments/assets/92e2eba2-808d-42a9-81ca-b9ac96fdc51a)


