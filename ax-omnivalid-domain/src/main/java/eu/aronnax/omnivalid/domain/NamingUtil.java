package eu.aronnax.omnivalid.domain;

class NamingUtil {

    static String extractSimpleName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf('.')+1);
    }

    static String extractPackageName(String qualifiedName) {
        return qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
    }

}
