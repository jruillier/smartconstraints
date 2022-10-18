package eu.aronnax.smartconstraints.sourcerenderer.jte;

import eu.aronnax.smartconstraints.domain.port.coderenderer.TargetAnnotParamDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class JteHelper {

    @Inject
    public JteHelper() {}

    public String formatParams(List<TargetAnnotParamDto> targetAnnotParamDtos) {
        return targetAnnotParamDtos.stream()
                .map(this::formatParam)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(", "));
    }

    private String formatParam(TargetAnnotParamDto param) {

        final String valueAsString;

        if (param.stringValue() != null) {
            valueAsString = "\"" + param.stringValue() + "\"";
        } else if (param.nonStringValue() == null) {
            valueAsString = "null";
        } else if (param.nonStringValue().getClass().isArray()) {
            if (((Object[]) param.nonStringValue()).length == 0) {
                return null;
            }
            valueAsString = Arrays.toString((Object[]) param.nonStringValue());
        } else {
            valueAsString = param.nonStringValue().toString();
        }

        return param.name() + " = " + valueAsString;
    }
}
