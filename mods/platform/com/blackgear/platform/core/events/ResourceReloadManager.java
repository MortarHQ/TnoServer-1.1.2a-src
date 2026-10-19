package com.blackgear.platform.core.events;

import com.blackgear.platform.core.events.neoforge.ResourceReloadManagerImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;

public class ResourceReloadManager {
   @ExpectPlatform
   @Transformed
   public static void registerClient(Consumer<ResourceReloadManager.ListenerEvent> listener) {
      ResourceReloadManagerImpl.registerClient(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerServer(Consumer<ResourceReloadManager.ListenerEvent> listener) {
      ResourceReloadManagerImpl.registerServer(listener);
   }

   public interface ListenerEvent {
      void register(ResourceLocation var1, PreparableReloadListener var2);
   }
}
