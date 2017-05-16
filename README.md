# openIE
Prototype for Modular Open Information Extraction System

# Installation
All source codes are placed in the /src directory. Application is developed using IntelliJ IDEA.

- Clone this repo.
- Install dependencies: run `$ mvn install`
- Modify `path.variable.maven_repository` to local maven repository path
- Compile classes: run `$ ant`

# Run program
```
$ java -cp .:out/artifacts/OpenIE_jar/*:target/classes id.ac.itb.gui.OpenIeJFrame
```

# Creating a plugin
To create your own plugin, please see `/examples` for more information.