# openIE
Prototype for Modular Open Information Extraction System

# Prerequisites

- Java 1.8
- Maven
- Ant


# Installation
All source codes are placed in the /src directory. Application is developed using IntelliJ IDEA.

- Clone this repo.
- Open file `build.properties` and modify `jdk.home.1.8` to JAVA_HOME path
- Compile classes: run `$ ant`

# Run program
```
$ java -cp .:out/artifacts/OpenIE_jar/*:jars/*:target/classes id.ac.itb.gui.OpenIeJFrame
```

# Creating a plugin
To create your own plugin, please see `/examples` for more information.