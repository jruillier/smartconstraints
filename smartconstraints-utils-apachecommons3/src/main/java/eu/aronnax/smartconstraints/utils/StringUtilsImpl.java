package eu.aronnax.smartconstraints.utils;

import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@ApplicationScoped
public class StringUtilsImpl implements StringUtilsPort {

    @Inject
    public StringUtilsImpl() {}

    @Override
    public String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    @Override
    public boolean isNotBlank(String value) {
        return StringUtils.isNotBlank(value);
    }
}
