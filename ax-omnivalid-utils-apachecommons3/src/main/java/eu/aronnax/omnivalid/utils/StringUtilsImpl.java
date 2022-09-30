package eu.aronnax.omnivalid.utils;

import org.apache.commons.lang3.StringUtils;

import eu.aronnax.omnivalid.domainport.StringUtilsPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StringUtilsImpl implements StringUtilsPort {

    @Inject
    public StringUtilsImpl() {}

    @Override
    public String capitalize(String str) {
        return StringUtils.capitalize(str);
    }
}
