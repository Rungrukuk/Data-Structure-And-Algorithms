package DataStructureAndAlgorithms.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Practice {
    String problemName();

    String category();

    String description() default "";
}