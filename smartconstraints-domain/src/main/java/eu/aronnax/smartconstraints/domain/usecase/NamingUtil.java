package eu.aronnax.smartconstraints.domain.usecase;

public class NamingUtil {

    public static String extractSimpleName(String qualifiedName) {
        return qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1);
    }

    public static String extractPackageName(String qualifiedName) {
        return qualifiedName.substring(0, qualifiedName.lastIndexOf('.'));
    }
}
