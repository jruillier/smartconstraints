# SmartConstraints

SmartContraints is a Java annotation 
processor that collects validation 
constraints from your entities and
 generates corresponding meta annotations. 
 You can then use them easily in your DTOs. 

 ## How-To

Add the ˋsmartconstraints-annotation` as 
a compile time dependency to your project.
(Not required at runtime) 

Add the ˋ@CopyConstraints` annotation 
on a ˋpackage-info.javaˋ in jour project, 
where meta-annotations will be generated. 

Add the `from` attribute to ˋ@CopyCpnstraints` 
and specify the package where your entities
are located. 

Add the ˋsmartconstraints-annotationprocessor`
As a compile time dependency to your project. 
(Not required at runtime) 

Run your build :-)

You can now use annotations like :
ˋ@Address.StreetNameˋ on your DTOs that
aggregates validations from original
 field , without any runtime 
dependency to ˋAddressEntity`. 

## Notes

SmartContraints as been designed as a
 tool to help adoption of 
CleanArchitecture principles.  

SmartConstraints also works with source
entities located in third party libraries. 
