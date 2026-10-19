package twilightforest.beanification.processors.gather;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Bean;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(value = BeanLifeCycle.Gather, priority = 1)
public class BeanAnnotationGatherBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      List<BeanAnnotationGatherBeanProcessor.Data> list = new ArrayList<>();
      Iterator<AnnotationData> it = this.distAnnotationRetriever.retrieve(scanData, ElementType.METHOD, Bean.class).iterator();

      while (it.hasNext()) {
         AnnotationData data = it.next();
         Method method = this.internalReflectionHelper.getDeclaredMethod(Class.forName(data.clazz().getClassName()), data.memberName());
         method.trySetAccessible();
         if (!this.internalReflectionHelper.isStatic(method)) {
            throw new IllegalStateException("@Bean methods must be static");
         }

         Bean annotation = method.getAnnotation(Bean.class);
         String name = Objects.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!", annotation.value()) ? null : annotation.value();
         list.add(
            new BeanAnnotationGatherBeanProcessor.Data(
               annotation.priority(),
               new BeanDefinition<>(method.getReturnType(), name),
               () -> method.getParameterCount() == 0
                  ? method.invoke(null)
                  : method.invoke(
                     null,
                     Arrays.stream(method.getParameters())
                        .map(
                           p -> {
                              String unresolvedName = p.getAnnotation(Autowired.class).value();
                              BeanDefinition<?> depDef = new BeanDefinition<>(
                                 p.getType(), unresolvedName.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!") ? null : unresolvedName
                              );
                              context.currentInjection().orElseThrow().set(method);
                              return context.injector().orElseThrow().apply(depDef);
                           }
                        )
                        .toArray()
                  )
            )
         );
      }

      list.stream()
         .sorted(Comparator.comparingInt(BeanAnnotationGatherBeanProcessor.Data::priority))
         .forEach(datax -> context.gather().orElseThrow().put(datax.definition, datax.factory));
   }

   private record Data(int priority, BeanDefinition<?> definition, BeanContext.ThrowingSupplier<Object> factory) {
   }
}
