package com.blackgear.platform.core.neoforge;

import com.blackgear.platform.core.ModInstance;
import com.blackgear.platform.core.ParallelDispatch;
import com.blackgear.platform.core.util.EventBus;
import java.util.function.Consumer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;

public class ModInstanceBuilderImpl {
   public static ModInstance builder(
      String modId, Runnable common, Consumer<ParallelDispatch> postCommon, Runnable client, Consumer<ParallelDispatch> postClient
   ) {
      return new ModInstance(modId, common, postCommon, client, postClient) {
         @Override
         public void bootstrap() {
            IEventBus bus = EventBus.get(EventBus.MOD);
            if (bus == null) {
               throw new IllegalStateException("Failed to get Forge mod event bus");
            }

            bus.addListener(event -> this.onPostCommon.accept(new ForgeParallelDispatch(event)));
            bus.addListener(event -> this.onPostClient.accept(new ForgeParallelDispatch(event)));
            this.onCommon.run();
            if (FMLEnvironment.dist.isClient()) {
               this.onClient.run();
            }
         }
      };
   }
}
