# Sample AEM project with React Web Components

Sample AEM project with integrated standard **HTL** and **React Web Components**.

## How to integrate **React Web Components**
* check **ui.frontend.react** module
* check components **/apps/aem-integrations/components/react/***

## Modules

The main parts of the template are:

* core: Java bundle containing all core functionality like OSGi services, listeners or schedulers, as well as component-related Java code such as servlets or request filters.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, and templates
* ui.frontend: dedicated front-end build mechanism (general Webpack project)
* ui.frontend.react: dedicated front-end build mechanism (React Web Components Webpack project)
* ui.content: contains sample content using the components from the ui.apps
* ui.config: contains runmode specific OSGi configs for the project
* ui.tests: Selenium based UI tests
* it.tests: Java based integration tests
* all: a single content package that embeds all of the compiled modules (bundles and content packages) including any vendor dependencies
* analyse: this module runs analysis on the project which provides additional validation for deploying into AEMaaCS

## How to build

To build all the modules and deploy the `all` package to a local instance of AEM, run in the project root directory the following command:

    mvn clean install -PautoInstallSinglePackage -PautoInstallSinglePackagePublish

**Note**: This project is generated for AEM as Cloud Service