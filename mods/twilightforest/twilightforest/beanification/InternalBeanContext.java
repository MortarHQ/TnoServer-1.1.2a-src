package twilightforest.beanification;

import java.lang.reflect.Field;
import javax.annotation.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import twilightforest.beanification.internal.BeanConstructorLocater;
import twilightforest.beanification.internal.BeanContextConfig;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.internal.ListInjector;

@Internal
public class InternalBeanContext extends AbstractBeanContext {
   static InternalBeanContext INSTANCE = new InternalBeanContext();

   private InternalBeanContext() {
   }

   public static <T> T inject(Class<T> type) {
      return inject(type, null);
   }

   public static <T> T inject(Class<T> type, @Nullable String name) {
      return INSTANCE.injectInternal(type, name);
   }

   static void injectInto(Object target) {
      for (Field field : target.getClass().getDeclaredFields()) {
         if (field.isAnnotationPresent(InternalAutowired.class)) {
            InternalAutowired annotation = field.getAnnotation(InternalAutowired.class);
            field.trySetAccessible();

            try {
               field.set(
                  target,
                  inject(
                     field.getType(), annotation.value().equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!") ? null : annotation.value()
                  )
               );
            } catch (Throwable e) {
               throw new RuntimeException("Internal Bean Injection failed", e);
            }
         }
      }
   }

   static {
      INSTANCE.registerInternal(DistAnnotationRetriever.class, null, new DistAnnotationRetriever());
      INSTANCE.registerInternal(InternalReflectionHelper.class, null, new InternalReflectionHelper());
      INSTANCE.registerInternal(BeanContextConfig.class, null, new BeanContextConfig());
      INSTANCE.registerInternal(BeanConstructorLocater.class, null, new BeanConstructorLocater());
      INSTANCE.registerInternal(ListInjector.class, null, new ListInjector());
      INSTANCE.freeze();
      INSTANCE.getBeans().values().forEach(InternalBeanContext::injectInto);
   }
}
