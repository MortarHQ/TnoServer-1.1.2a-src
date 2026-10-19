package twilightforest.beanification.processors.construct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Construct)
public class ConstructBeanProcessor implements IBeanProcessor {
   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      HashMap<BeanDefinition<?>, List<BeanDefinition<?>>> deps = new HashMap<>(context.dependencies().orElseThrow());
      deps.replaceAll((k, v) -> new ArrayList<>(v));
      HashMap<BeanDefinition<?>, BeanContext.ThrowingSupplier<Object>> toProcess = new HashMap<>(context.gather().orElseThrow());

      while (!toProcess.isEmpty()) {
         List<BeanDefinition<?>> toRemove = new ArrayList<>();
         toProcess.forEach((def, factory) -> {
            List<BeanDefinition<?>> currentDeps = deps.get(def);
            if (currentDeps == null || toProcess.keySet().stream().noneMatch(currentDeps::contains)) {
               toRemove.add((BeanDefinition<?>)def);

               try {
                  context.register().orElseThrow().accept((BeanDefinition<?>)def, factory.get());
               } catch (Throwable ex) {
                  throw new RuntimeException("Failed to construct Bean: " + def, ex);
               }
            }
         });
         toRemove.forEach(toProcess::remove);
      }
   }
}
