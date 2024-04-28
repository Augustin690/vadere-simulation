package org.vadere.util.other;

import org.jetbrains.annotations.NotNull;
import org.vadere.util.reflection.VadereAttribute;

public class Strings {
    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }

    @NotNull
    public static String generateHeaderName(Class<?> clazz) {
        var annotation = (VadereAttribute)clazz.getAnnotation(VadereAttribute.class);
        if(annotation != null) {
            if (!annotation.name().isEmpty())
                return splitCamelCase(annotation.name());
        }
        return splitCamelCase(removeAttribute(clazz.getSimpleName()));
    }
    // is used to strip the "Attributes" from the name of the class, e.g. AttributesAgent -> Agent
    // but since we want to keep the "Attributes" name, we need to check for it
    @NotNull
    public static String removeAttribute(String name) {
        if (name.equals("Attributes"))
            return name;
        if(name.startsWith("Attribute"))
            return name.substring("Attributes".length());
        return name;
    }
}
