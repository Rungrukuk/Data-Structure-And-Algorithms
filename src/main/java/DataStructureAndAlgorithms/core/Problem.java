package DataStructureAndAlgorithms.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Problem {
    String name();

    String category() default "General";

    String description() default "";
}