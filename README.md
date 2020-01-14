# Readme

## 1. About
Service-Oriented Management And Querying Of UML-Models (SOMQM) ...

## 2. Content
Contains ...

## 3. Disclosure
 * Content in folder <project directory>/libs/mdt-uml2 is from https://www.eclipse.org/modeling/mdt/downloads/?project=uml2 .

## 4. Build

### 4.1. Preconditions
 * Java 1.8
 * Maven 3.6.1
 
### 4.2. Clone project repository

### 4.3. Dependencies
Needed MDT/UML2 libraries are not in Maven Central repository.
Therefore they are provided in <project directory>/libs/mdt-uml2 .
Browse to <project directory> and execute the following commands to import them to the local Maven repository:
 * ´mvn install:install-file -Dfile=libs/mdt-uml2/org.eclipse.uml2.common_2.5.0.v20181203-1331.jar -DgroupId=org.eclipse.uml2 -DartifactId=common -Dversion=2.5.0.v20181203-1331 -Dpackaging=jar´
 * ´mvn install:install-file -Dfile=libs/mdt-uml2/org.eclipse.uml2.types_2.5.0.v20181203-1331.jar -DgroupId=org.eclipse.uml2 -DartifactId=types -Dversion=2.5.0.v20181203-1331 -Dpackaging=jar´
 * ´mvn install:install-file -Dfile=libs/mdt-uml2/org.eclipse.uml2.uml.profile.standard_1.5.0.v20181203-1331.jar -DgroupId=org.eclipse.uml2.uml.profile -DartifactId=standard -Dversion=1.5.0.v20181203-1331 -Dpackaging=jar´
 * ´mvn install:install-file -Dfile=libs/mdt-uml2/org.eclipse.uml2.uml.resources_5.5.0.v20181203-1331.jar -DgroupId=org.eclipse.uml2.uml -DartifactId=resources -Dversion=5.5.0.v20181203-1331 -Dpackaging=jar´
 * ´mvn install:install-file -Dfile=libs/mdt-uml2/org.eclipse.uml2.uml_5.5.0.v20181203-1331.jar -DgroupId=org.eclipse.uml2 -DartifactId=uml -Dversion=5.5.0.v20181203-1331 -Dpackaging=jar´

### 4.4. Development Build
 1) Install PostgreSQL 10.3 (set username: 'postgres', password: '1234' and port: 5432).
 2) Start pgAdmin 4 (part of the PostgreSQL installation), connect to server 'localhost' (using password: '1234') and create new database named 'somqm'.
 3) Browse to <project directory> and perform ´mvn clean install -DskipTests´. This way e.g. the JPA metamodel classes get generated (otherwise IDEs will complain about not existing Entity_ classes).
 4) Setup IntelliJ Java coding style according to Google style-guide:
   Download style-file: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
   File --> Settings... --> Editor --> Code Style --> Java --> Gear icon --> Import Scheme --> Intellij IDEA code style XML --> <select style-file> --> OK.
   Verify that "GoogleStyle" is selected in the dropdown-field next to 'Scheme:' (at least for this project).
   Auto-format the code before committing: click on a package or class and click on "Reformat code".
 5) Import project as Maven project in JetBrains IntelliJ IDEA.
 6) Run at.ac.tuwien.big.ame.somqm.server.ServerApplication.java .
#### 4.4.1. Helpful notes
 * The IntelliJ tool-window 'Database' allows to directly interact with the PostgreSQL database.
 * The IntelliJ tool-window 'Persistence' allows to show the JPA mapping entities (e.g. as ER diagram).
   To enable it: File --> Project Structure... --> Modules --> Right-click on Maven module 'server' --> Add --> JPA --> OK.
   Now open tool-window 'Persistence' --> expand 'server' --> right-click on 'entityManagerFactory' --> ER Diagram.

### 4.5. Production Build
 * Execute ´mvn clean package -Pprod -DskipTests´.
 * Deploy created server.jar (e.g. to Amazon AWS Elastic Beanstalk).
 * Run e.g. with ´java -jar server.jar --APP_CONTEXTPATH=/ --APP_PORT=5000 --DB_URL="jdbc:mysql://myDBHost:myDBPort/myDBUser?useSSL=true&serverTimezone=UTC&useLegacyDatetimeCode=false" --DB_USER=myDBUser --DB_PASSWORD=myDBPassword --DB_HIBERNATE_DDLAUTO=none´ 

## 5. Development

### 5.1 Server
#### 5.1.1 REST API Documentation
The REST API of the server is documented via Swagger and is located under http://localhost:8080/somqm/swagger-ui.html.

### 5.2 Known issues
  * server: Currently, some entities have assigned a wrong package. This occurs because the package of referenced entities is not looked up (instead the current package is used, which is wrong).
    This concerns the following entities (only in class model types, as GenMyModel currently only supports packages for class models):
      o Type of class model attributes and parameters and return type of class operations; this includes primitive types (e.g. void)
      o Source and target of class model relations

### 5.3 Potential improvements
  * server: JAXB dependencies can maybe removed from pom.xml. First test if ServerApplicationTests is running with Java > 1.9 and without this dependencies.
  * server: When querying a model it always gets loaded from the database and an MDT/UML2 model is build freshly in the RAM. Maybe a caching should be implemented, that holds several models. This would increase performance, especially when querying the same model multiple times.
  * server: Loading a whole UmlModel entity is costly (as field 'content' can get large). Maybe some service-calls which doesnt access 'content' but load whole entity can be optimized.

### 5.4 Future work
  * server: All source code comments starting with "TODO: Future work:" and all disabled tests where reason starts with "Future work".
  * server: Currently only one model per type and project is permitted. Allow multiple models per type (per project).
  * server: A nice to have feature would be to get an image-representation of an uml-model via REST. A promising approach is therefore maybe to programmatically use Papyrus: https://www.eclipse.org/forums/index.php/t/1077752/.
  * server: Support UML models with profiles.
