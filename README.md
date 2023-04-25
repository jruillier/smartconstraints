[![Maven Central](https://img.shields.io/maven-central/v/eu.aronnax.smartconstraints/smartconstraints-annotation.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22eu.aronnax.smartconstraints%22%20AND%20a:%22smartconstraints-annotation%22)
[![Build+Tests](https://github.com/jruillier/smartconstraints/actions/workflows/maven.yml/badge.svg)](https://github.com/jruillier/smartconstraints/actions/workflows/maven.yml)

<!-- ![Coverage](.github/badges/jacoco.svg) -->

# SmartConstraints

SmartContraints is a Java annotation
processor that collects validation
constraints from your entities and
generates corresponding composed constraints annotations. 
You can then use them easily in your DTOs. 


SmartConstraints is a Work In Progress.
Stay tuned for incoming release on maven central :-)

## Example

With given entity:

```java
public class AddressEntity {

    @NotNull
    @Size(min = 5, max = 5)
    private String zipCode;

}
```

### Without SmartConstraints

Your DTO would need to repeat constraints, like:

```java
public class AddressDto {

    @NotNull
    @Size(min = 5, max = 5)
    private String zipCode;

}
```
- ❌ You repeat yourself
- ❌ If your constraintes have to change, your Entity and your DTO can become out of sync if you forget to update one of them

### With SmartConstraints

You could simply write:

```java
public class AddressDto {

    @ValidZipCode
    private String zipCode;

}
```
- ✅ DRY, and readable
- ✅ (Almost) no extra work
- ✅ Constraints are always in sync

## Maven How-To
 
Define a property to define SmartConstraints version:
```xml
   <properties>
        <smartconstraints.version>0.5.1</smartconstraints.version>
    </properties>
```

Add the `smartconstraints-annotation` as 
a `provided` dependency to your project.
(Not required at runtime) 

```xml
    <dependencies>
        <dependency>
            <groupId>eu.aronnax.smartconstraints</groupId>
            <artifactId>smartconstraints-annotation</artifactId>
            <version>${smartconstraints.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
```

Add the `smartconstraints-annotationprocessor`
as a `maven-compiler-plugin` `annotationProcessor`:

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>eu.aronnax.smartconstraints</groupId>
                            <artifactId>smartconstraints-annotationprocessor</artifactId>
                            <version>${smartconstraints.version}</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

Add the `@CopyJavaxConstraints` annotation
on a `package-info.java` in your project,
where composed annotations will be generated.

Add the `from` attribute to `@CopyJavaxConstraints`
and specify the package where your entities
are located.

Add the `smartconstraints-annotationprocessor`
As a compile time dependency to your project. 
(Not required at runtime) 

Run your build :-)

You can now use annotations like :
`@Address_Constraints.ValidStreetName` on your DTOs that
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

Your project must use `javax.validation.constraints.*` or `jakarta.validation.constraints.*` annotations.

## Roadmap

#### 1.1

- [ ] Support multi-round annotation processor

#### 1.0 (2023-summer)

- [ ] Additional testing

#### 0.6 (2023-06)

- [ ] Support validation groups

#### 0.5 (2023-04-24)

- [x] Also support jakarta constraints

#### 0.4 (2023-02-24)

- [x] Support javax constraints

## Contribute

Project is still in alpha stage. Any contribution is welcome.

To build project :
`mvn clean install`
