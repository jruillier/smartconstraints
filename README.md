# SmartConstraints

SmartContraints is a Java annotation 
processor that collects validation 
constraints from your entities and
 generates corresponding meta annotations. 
 You can then use them easily in your DTOs. 

 ## How-To

Add the smartconstraints-annotation as 
a compile time dependency to your project.
(Not required at runtime) 

Add the @CopyConstraints annotation 
on a package-info.java in jour project, 
where meta-annotations will be generated. 

Add the from attribute to @CopyCpnstraints 
and specify the package where your entities
Are located. 

Add the smartconstraints-annotationprocessor
As a compile tile dependency to your project. 
(Not required at runtime) 

Run your build :-)

You can now use annotations like :
@Address.StreetName on your DTOs that
aggregates validations from original
 field , without any runtime 
dependency to it. 
