[![Maven Central](https://img.shields.io/maven-central/v/eu.aronnax.smartconstraints/smartconstraints-annotation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22eu.aronnax.smartconstraints%22%20AND%20a:%22smartconstraints-annotation%22)

# SmartConstraints

SmartContraints is a Java annotation 
processor that collects validation 
constraints from your entities and
 generates corresponding meta annotations. 
 You can then use them easily in your DTOs. 

SmartConstraints is a Work In Progress. 
Stay tuned for incoming release on maven central :-)

 ## How-To

Add the `smartconstraints-annotation` as 
a compile time dependency to your project.
(Not required at runtime) 

Add the `@CopyJavaxConstraints` annotation 
on a `package-info.java` in jour project, 
where meta-annotations will be generated. 

Add the `from` attribute to `@CopyJavaxConstraints` 
and specify the package where your entities
are located. 

Add the `smartconstraints-annotationprocessor`
As a compile time dependency to your project. 
(Not required at runtime) 

Run your build :-)

You can now use annotations like :
`@AddressConstraints.ValidStreetName` on your DTOs that
aggregates validations from original
 field , without any runtime 
dependency to `AddressEntity`. 

## Notes

SmartContraints as been designed as a
 tool to help adoption of 
CleanArchitecture principles.  

SmartConstraints also works with source
entities located in third party libraries. 

## Requirements

Your project must be at least a Java 17 project.

Your project must use `javax.validation.constraints.*` annotations.

## Contribute

Project is still in alpha stage. Any contribution is welcome.

To build project :
`mvn clean install`
