package org.vadere.annotation.helptext;

import java.lang.annotation.*;

// prior to this annotation, attributes that had to be excluded from the help text had were annotated with @VadereAttribute(exclude = true)
// which conflated the meaning of that annotation used for excluding attributes in the GUI and the help text.
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface HelpIgnore {
}
