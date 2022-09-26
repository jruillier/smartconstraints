package eu.aronnax.omnivalid.utils;

import dagger.Binds;
import dagger.Module;
import eu.aronnax.omnivalid.domain.StringUtilsPort;

@Module
public abstract class UtilsModuleApacheCommons3 {

    @Binds
    abstract StringUtilsPort stringUtilsPort(StringUtilsImpl stringUtils);


}
