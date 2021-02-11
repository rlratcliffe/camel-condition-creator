# Camel Condition Creator

Debugging in Apache Camel can be difficult. This is a project containing a class with two methods that can be used for more easily creating a condition within a breakpoint. This enables stopping a processor in a route, either by its id or its index in the route.

## Installation

Clone this repo and then change directory to it. Then run `mvn clean install`

## Usage

After using `mvn clean install` to install it:
- Open Intellij
- File -> Project Structure
- Modules -> Dependencies -> + (Add dependency)
- Locate your maven repo and find the jar in the following folder: dev -> rlratcliffe > camel-condition-creator -> <version>
- Click apply/okay

Inside of Intellij, triple tap shift to open up searching within Intellij and search for CamelInternalProcessor. Put a breakpoint around the `processor.process` calls. If using 3.7.1, it will be around line 286.

After right clicking on the breakpoint, in the condition field, start typing new CamelConditionCreator() and use either the `whenAtId` method or `whenAtIndex` method.

```
new CamelConditionCreator(exchange, processor).whenAtId("route1", "fourth")

new CamelConditionCreator(exchange, processor).whenAtIndex("route1", 3)
```

Both of those will access the fourth log listed here.

```java
            .log(LoggingLevel.INFO, "First log")
            .log(LoggingLevel.INFO, "Second log")
            .log(LoggingLevel.INFO, "Third log")
            .log(LoggingLevel.INFO, "Fourth log ").id("fourth")
```

Routes can either be accessed by the name automatically given to them ("route1", "route2", etc) or by the set by them with `.routeId("firstRoute")`.

## Contributing
Pull requests are welcome.
