package twilightforest.beanification.processors.inject;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Optional;
import java.util.Map.Entry;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Inject)
public class AutowiredAnnotationInjectBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      for (Entry<BeanDefinition<?>, Object> entry : context.beans().orElseThrow().entrySet()) {
         Object bean = entry.getValue();
         if (!(bean instanceof Record)) {
            Iterator<AnnotationData> it = this.distAnnotationRetriever
               .retrieve(scanData, ElementType.FIELD, Autowired.class)
               .filter(a -> this.internalReflectionHelper.classOrSuperEquals(a.clazz(), bean.getClass()))
               .iterator();

            while (it.hasNext()) {
               AnnotationData data = it.next();
               Optional<String> name = Optional.ofNullable(data.annotationData().get("value")).filter(String.class::isInstance).map(String.class::cast);

               for (Field field : this.internalReflectionHelper
                  .getAllAutowiredFieldsIncludingSuper(
                     bean.getClass(), data.memberName(), name.orElse("<beanification_shade_value>!beanification:internal:bean:DEFAULT!")
                  )) {
                  context.currentInjection().orElseThrow().set(field);
                  if (this.internalReflectionHelper.isStatic(field)) {
                     throw new IllegalStateException("@Autowired fields must be non-static inside Beans");
                  }

                  field.trySetAccessible();
                  field.set(
                     bean,
                     context.injector()
                        .orElseThrow()
                        .apply(
                           new BeanDefinition<>(
                              field.getType(), name.filter(s -> !s.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!")).orElse(null)
                           )
                        )
                  );
               }
            }
         }
      }
   }
}
