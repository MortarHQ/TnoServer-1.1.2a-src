package twilightforest.beanification.processors.staticinject;

import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Iterator;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import net.neoforged.neoforgespi.language.ModFileScanData.AnnotationData;
import twilightforest.beanification.BeanContext;
import twilightforest.beanification.BeanLifeCycle;
import twilightforest.beanification.Directory;
import twilightforest.beanification.InternalAutowired;
import twilightforest.beanification.internal.DistAnnotationRetriever;
import twilightforest.beanification.internal.InternalReflectionHelper;
import twilightforest.beanification.internal.ListInjector;
import twilightforest.beanification.processors.BeanProcessor;
import twilightforest.beanification.processors.IBeanProcessor;

@BeanProcessor(BeanLifeCycle.StaticInject)
public class DirectoryAnnotationStaticInjectBeanProcessor implements IBeanProcessor {
   @InternalAutowired
   private DistAnnotationRetriever distAnnotationRetriever;
   @InternalAutowired
   private InternalReflectionHelper internalReflectionHelper;
   @InternalAutowired
   private ListInjector listInjector;

   @Override
   public void process(BeanContext.BeanLifeCycleContext context, ModContainer modContainer, ModFileScanData scanData) throws Throwable {
      Iterator<AnnotationData> it = this.distAnnotationRetriever.retrieve(scanData, ElementType.FIELD, Directory.class).iterator();

      while (it.hasNext()) {
         AnnotationData data = it.next();
         context.currentInjection().orElseThrow().set(data.clazz());
         Class<?> type = Class.forName(data.clazz().getClassName());
         Field field = this.internalReflectionHelper.getDeclaredField(type, data.memberName());
         context.currentInjection().orElseThrow().set(field);
         Directory annotation = field.getAnnotation(Directory.class);
         if (this.internalReflectionHelper.isStatic(field)) {
            field.trySetAccessible();
            field.set(null, this.listInjector.inject(context, scanData, type, annotation.value(), annotation.recursive()));
         }
      }
   }
}
