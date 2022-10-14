package eu.aronnax.smartconstraints.utils;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.smartconstraints.domain.port.stringutils.StringUtilsPort;

@Module
public abstract class UtilsModuleApacheCommons3 {

    @Binds
    abstract StringUtilsPort stringUtilsPort(StringUtilsImpl stringUtils);
}
