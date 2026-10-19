package twilightforest.beanification.processors.staticinject;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Objects;
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

@BeanProcessor(BeanLifeCycle.StaticInject)
public class AutowiredAnnotationStaticInjectBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      Iterator<AnnotationData> it = this.distAnnotationRetriever.retrieve(scanData, ElementType.FIELD, Autowired.class).iterator();

      while (it.hasNext()) {
         AnnotationData data = it.next();
         context.currentInjection().orElseThrow().set(data.clazz());
         Class<?> type = Class.forName(data.clazz().getClassName());
         Field field = this.internalReflectionHelper.getDeclaredField(type, data.memberName());
         context.currentInjection().orElseThrow().set(field);
         Autowired annotation = field.getAnnotation(Autowired.class);
         String name = Objects.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!", annotation.value()) ? null : annotation.value();
         if (this.internalReflectionHelper.isStatic(field)) {
            field.trySetAccessible();
            field.set(null, context.injector().orElseThrow().apply(new BeanDefinition<>(field.getType(), name)));
         }
      }
   }
}
