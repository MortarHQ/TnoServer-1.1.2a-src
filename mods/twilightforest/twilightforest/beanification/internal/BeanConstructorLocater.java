package twilightforest.beanification.internal;

import java.lang.reflect.Constructor;
import org.jetbrains.annotations.ApiStatus.Internal;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.InternalAutowired;

@Internal
public class BeanConstructorLocater {
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;

   public Constructor<?> locate(Class<?> c) {
      Constructor<?> targetConstructor = null;
      boolean hasAutowiredCtor = false;

      for (Constructor<?> constructor : this.internalReflectionHelper.getConstructors(c)) {
         if (targetConstructor == null && constructor.getParameterCount() == 0) {
            targetConstructor = constructor;
         } else if (this.internalReflectionHelper.allParametersHaveAnnotation(constructor.getParameterAnnotations(), Autowired.class)) {
            if (hasAutowiredCtor) {
               throw new IllegalArgumentException(
                  "Conflicting Constructors found: " + constructor.toGenericString() + " and " + targetConstructor.toGenericString()
               );
            }

            hasAutowiredCtor = true;
            targetConstructor = constructor;
         }
      }

      if (targetConstructor == null) {
         throw new IllegalStateException("Could not find any Constructors in class " + c);
      } else {
         return targetConstructor;
      }
   }
}
