package twilightforest.beanification.processors.finalize;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.PostConstruct;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Finalize)
public class PostConstructAnnotationFinalizeBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      for (Entry<BeanDefinition<?>, Object> entry : context.beans().orElseThrow().entrySet()) {
         Object bean = entry.getValue();
         Iterator<AnnotationData> it = this.distAnnotationRetriever
            .retrieve(scanData, ElementType.METHOD, PostConstruct.class)
            .filter(a -> a.clazz().equals(this.internalReflectionHelper.getType(bean.getClass())))
            .iterator();

         while (it.hasNext()) {
            String name = it.next().memberName();
            List<Method> methods = new ArrayList<>();

            try {
               methods.add(this.internalReflectionHelper.getDeclaredMethod(bean.getClass(), name));
            } catch (NoSuchMethodException var15) {
            }

            try {
               methods.add(this.internalReflectionHelper.getDeclaredMethod(bean.getClass(), name, IEventBus.class));
            } catch (NoSuchMethodException var14) {
            }

            try {
               methods.add(this.internalReflectionHelper.getDeclaredMethod(bean.getClass(), name, IEventBus.class, IEventBus.class));
            } catch (NoSuchMethodException var13) {
            }

            for (Method method : methods) {
               if (method.isAnnotationPresent(PostConstruct.class)) {
                  context.currentInjection().orElseThrow().set(method);
                  if (this.internalReflectionHelper.isStatic(method)) {
                     throw new IllegalStateException("@PostConstruct methods must be non-static");
                  }

                  method.trySetAccessible();
                  if (method.getParameterCount() == 2
                     && method.getParameterTypes()[0].equals(IEventBus.class)
                     && method.getParameterTypes()[1].equals(IEventBus.class)) {
                     boolean isModType = method.getAnnotation(PostConstruct.class).value() == PostConstruct.Bus.MOD;
                     method.invoke(
                        bean, isModType ? modContainer.getEventBus() : NeoForge.EVENT_BUS, isModType ? NeoForge.EVENT_BUS : modContainer.getEventBus()
                     );
                  } else if (method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(IEventBus.class)) {
                     method.invoke(
                        bean, method.getAnnotation(PostConstruct.class).value() == PostConstruct.Bus.MOD ? modContainer.getEventBus() : NeoForge.EVENT_BUS
                     );
                  } else {
                     if (method.getParameterCount() != 0) {
                        throw new IllegalStateException("@PostConstruct methods must not have parameters or only have one or two IEventBus parameter(s)");
                     }

                     method.invoke(bean);
                  }
               }
            }
         }
      }
   }
}
