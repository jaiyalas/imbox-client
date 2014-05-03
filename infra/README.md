# imbox-infra

## Requirement

**Building**

  * JDK 1.8+
  * [Maven](https://maven.apache.org/) 

## Usage

**Building with Maven**

    > mvn clean
    > mvn compile
    > mvn package
    
or, just in single line

    > mvn clean compile package

## How to integrate with your POM file

**Deploy jar as a sub-repository**

Create a directory to place imbox-infra.jar

    > cd /where/your/repo/is/
    > mkdir -p libs/org/imbox/imbox-infra/<version>/

then copy imbox-infra-<version>.jar into above directory.

**Add repository into your POM file**

Open your pom.xml file and add the following code:

	 <repositories>
	    <repository>
	      <id>libs-local</id>
	      <name>libs</name>
	      <url>file://${project.basedir}/libs</url>
	    </repository>
	  </repositories>
	  
now all of those folders under libs/ can be loaded via Maven.

**Add dependency for imbox-infra**

Open your pom.xml file and add the following code:

    <dependencies>
        <dependency>
            <groupId>org.imbox</groupId>
            <artifactId>imbox-infra</artifactId>
            <version>0.2.1</version>
        </dependency>
    </dependencies>
    