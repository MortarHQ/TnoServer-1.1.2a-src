package twilightforest.beanification.processors.inject;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map.Entry;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanDefinition;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.Directory;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.internal.ListInjector;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.Inject)
public class DirectoryAnnotationInjectBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;
   @InternalAutowired
   private ListInjector listInjector;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      for (Entry<BeanDefinition<?>, Object> entry : context.beans().orElseThrow().entrySet()) {
         Object bean = entry.getValue();
         if (!(bean instanceof Record)) {
            Iterator<AnnotationData> it = this.distAnnotationRetriever
               .retrieve(scanData, ElementType.FIELD, Directory.class)
               .filter(a -> this.internalReflectionHelper.classOrSuperEquals(a.clazz(), bean.getClass()))
               .iterator();

            while (it.hasNext()) {
               AnnotationData data = it.next();

               for (Field field : this.internalReflectionHelper.getAllDirectoryFieldsIncludingSuper(bean.getClass(), data.memberName())) {
                  context.currentInjection().orElseThrow().set(field);
                  if (this.internalReflectionHelper.isStatic(field)) {
                     throw new IllegalStateException("@Directory fields must be non-static inside Beans");
                  }

                  field.trySetAccessible();
                  Directory annotation = field.getAnnotation(Directory.class);
                  field.set(bean, this.listInjector.inject(context, scanData, bean.getClass(), annotation.value(), annotation.recursive()));
               }
            }
         }
      }
   }
}
