# openIE
Prototype for Modular Open Information Extraction System

# Prerequisites

- Java 1.8
- [Ant](http://ant.apache.org/)

# Installation
All source codes are placed in the /src directory. Application is developed using IntelliJ IDEA.

- Clone this repo.
- Open file `build.properties` and modify `jdk.home.1.8` to `JAVA_HOME` path. E.g. `jdk.home.1.8=C:/Program Files/Java/jdk1.8.0_92` (Windows) and `jdk.home.1.8=/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home` (Mac OS X)
- Compile classes: run `$ ant` (This will generate `out` and `target` directories)

# Run program
This will open the main application window.
## UNIX
```
$ java -cp .:out/artifacts/OpenIE_jar/*:jars/*:target/classes id.ac.itb.gui.OpenIeJFrame
```

## Windows
```
$ java -cp .;out/artifacts/OpenIE_jar/*;jars/*;target/classes id.ac.itb.gui.OpenIeJFrame
```

# Creating a plugin
To create your own plugin, please see `/examples` for more information.