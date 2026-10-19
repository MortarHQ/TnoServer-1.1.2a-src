package twilightforest.beanification.processors.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.CircularDependencyException;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Validate)
public class DependencyValidateBeanProcessor implements IBeanProcessor {
   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      context.dependencies()
         .orElseThrow()
         .keySet()
         .forEach(bean -> this.process(new ArrayList<>(), (BeanDefinition<?>)bean, context.dependencies().orElseThrow()));
   }

   private void process(List<BeanDefinition<?>> stack, BeanDefinition<?> currentBean, Map<BeanDefinition<?>, List<BeanDefinition<?>>> lookup) {
      if (stack.contains(currentBean)) {
         throw new CircularDependencyException(this.printCircularError(stack, currentBean));
      }

      stack.add(currentBean);
      List<BeanDefinition<?>> deps = lookup.get(currentBean);
      if (deps != null) {
         deps.forEach(dep -> this.process(new ArrayList<>(stack), (BeanDefinition<?>)dep, lookup));
      }
   }

   private String printCircularError(List<BeanDefinition<?>> chain, BeanDefinition<?> dep) {
      BeanDefinition<?> parent = chain.getFirst();
      StringBuilder builder = new StringBuilder("Validating: ").append(parent.type());
      if (parent.name() != null) {
         builder.append("@").append(parent.name());
      }

      chain.stream().skip(1L).forEach(e -> {
         builder.append("\n").append("Chain: ").append(e.type());
         if (e.name() != null) {
            builder.append("@").append(e.name());
         }
      });
      builder.append("\n").append("Dependency: ").append(dep.type());
      if (dep.name() != null) {
         builder.append("@").append(dep.name());
      }

      return builder.toString();
   }
}
