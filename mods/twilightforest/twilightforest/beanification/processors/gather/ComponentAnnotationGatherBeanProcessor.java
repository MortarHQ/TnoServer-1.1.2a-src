package twilightforest.beanification.processors.gather;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.Component;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.internal.BeanConstructorLocater;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Gather)
public class ComponentAnnotationGatherBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;
   @InternalAutowired
   private BeanConstructorLocater beanConstructorLocater;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      Iterator<AnnotationData> it = this.distAnnotationRetriever.retrieve(scanData, ElementType.TYPE, Component.class).iterator();

      while (it.hasNext()) {
         AnnotationData data = it.next();
         Class<?> c = Class.forName(data.clazz().getClassName());
         Component annotation = this.internalReflectionHelper.getAnnotation(c, Component.class);
         String name = Objects.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!", annotation.value()) ? null : annotation.value();
         Constructor<?> ctor = this.beanConstructorLocater.locate(c);
         context.gather()
            .orElseThrow()
            .put(
               new BeanDefinition<>(c, name),
               () -> ctor.getParameterCount() == 0
                  ? ctor.newInstance()
                  : ctor.newInstance(
                     Arrays.stream(ctor.getParameters())
                        .map(
                           p -> {
                              String unresolvedName = p.getAnnotation(Autowired.class).value();
                              BeanDefinition<?> depDef = new BeanDefinition<>(
                                 p.getType(), unresolvedName.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!") ? null : unresolvedName
                              );
                              context.currentInjection().orElseThrow().set(ctor);
                              return context.injector().orElseThrow().apply(depDef);
                           }
                        )
                        .toArray()
                  )
            );
      }
   }
}
