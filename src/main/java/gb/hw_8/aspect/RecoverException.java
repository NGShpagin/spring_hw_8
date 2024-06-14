package gb.hw_8.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.NoSuchElementException;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecoverException {
    Class<? extends RuntimeException>[] noRecoverFor() default  {IllegalArgumentException.class};
}
