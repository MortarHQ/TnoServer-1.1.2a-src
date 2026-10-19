package twilightforest.beanification.processors.inspect;

import java.lang.annotation.ElementType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

@BeanProcessor(BeanLifeCycle.Inspect)
public class ComponentAnnotationInspectBeanProcessor implements IBeanProcessor {
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
         if (ctor.getParameterCount() > 0) {
            List<BeanDefinition<?>> deps = new ArrayList<>();

            for (Parameter parameter : ctor.getParameters()) {
               String unresolvedName = parameter.getAnnotation(Autowired.class).value();
               deps.add(
                  new BeanDefinition<>(
                     parameter.getType(), unresolvedName.equals("<beanification_shade_value>!beanification:internal:bean:DEFAULT!") ? null : unresolvedName
                  )
               );
            }

            context.dependencies().orElseThrow().put(new BeanDefinition<>(c, name), deps);
         }
      }
   }
}
