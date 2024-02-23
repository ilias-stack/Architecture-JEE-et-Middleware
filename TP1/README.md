# TP1
The project structure is organised and self expalanatory.
Inside the part2 folder exists the mini_framework folder that contains the IOC code inspired by spring framework.

## Table of Contents
- [Features](#features)
- [Usage via](#usage)
  - [Annotations](#Annotations)
  - [XMLFile](#XMLFile)
 
## Features
The mini_framework is an Inversion of Control (IOC) container inspired by Spring IOC. It provides dependency injection capabilities for managing object dependencies within an application.

## Usage
The injection can be based on two methods either you can create a .xml file inside your ressources folder or you can use the annotations.
```Note that the call of methods remains the same if the implementation is correct.```

### Annotations
Annotations provide a convenient way to define beans and manage dependencies within the framework. The following annotations are available:
- @Component
- @AutoWired
- @Qualifier
Each annotation has the same role as in spring.

### XMLFile
XML configuration allows for defining beans and their dependencies using an XML file. The structure follows a similar format to Spring's XML configuration.
eg:
```xml
<beans>
  <bean id="dao" className="part2.dao.DaoImpl"/>
  <bean id="metier" className="part2.metier.MetierImpl">
    <property name="dao" ref="dao"/>
  </bean>
</beans>
```
