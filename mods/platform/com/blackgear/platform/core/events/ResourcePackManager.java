package com.blackgear.platform.core.events;

import com.blackgear.platform.core.events.neoforge.ResourcePackManagerImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.RepositorySource;

public class ResourcePackManager {
   @ExpectPlatform
   @Transformed
   public static void registerPack(Consumer<ResourcePackManager.Event> listener) {
      ResourcePackManagerImpl.registerPack(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerBuiltResourcePack(ResourceLocation packId, String modId, String packName) {
      ResourcePackManagerImpl.registerBuiltResourcePack(packId, modId, packName);
   }

   public interface Event {
      void register(PackType var1, RepositorySource var2);

      default void register(PackType packType, Supplier<Pack> supplier) {
         if (supplier != null) {
            this.register(packType, loader -> {
               Pack pack = supplier.get();
               if (pack != null) {
                  loader.accept(pack);
               }
            });
         }
      }
   }
}
