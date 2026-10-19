package cc.sighs.oelib.util.spi;

import java.lang.annotation.Annotation;
import java.util.Set;
import java.util.function.Predicate;

public interface IAnnotationScanner {
   Set<Class<?>> findAnnotatedClasses(Class<? extends Annotation> var1, Set<String> var2, Predicate<Class<?>> var3);
}
