package twilightforest.beanification.processors;

import net.neoforged.fml.ModContainer;
import net.neoforged.neoforgespi.language.ModFileScanData;
import twilightforest.beanification.BeanContext;

public interface IBeanProcessor {
   void process(BeanContext.BeanLifeCycleContext var1, ModContainer var2, ModFileScanData var3) throws Throwable;
}
