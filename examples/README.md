# Creating a plugin

There are 3 folders given:
- `/plugins`: containing example of existing plugins in the system in the form of zip file.
- `/interfaces`: containing simplified abstraction for each components to what the user has to implement.
- `/templates`: containing the template to help creating a new plugin component.

Steps to create a plugin:
- Including all jars in `/jars` directory.
- Copy required component's template from `/templates` directory.
- Start coding from the copied template.

Steps to integrate a plugin:
- Plugins can be copied directly to the `/plugins` directory or plugins can be loaded through GUI Load Plugins section which will copy plugins to `/plugins` directory.
- Recompile app using `ant`
- Rerun app