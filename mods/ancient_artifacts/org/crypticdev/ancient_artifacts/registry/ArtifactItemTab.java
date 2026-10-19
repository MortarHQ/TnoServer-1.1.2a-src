package org.crypticdev.ancient_artifacts.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

public class ArtifactItemTab {
   public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create("ancient_artifacts", Registries.CREATIVE_MODE_TAB);
   public static RegistrySupplier<CreativeModeTab> ArtifactTab;

   public static void init() {
      ArtifactTab = CREATIVE_MODE_TABS.register(
         "artifact_tab",
         () -> CreativeTabRegistry.create(
            Component.translatable("category.artifact_tab"), () -> ArtifactItems.get(Artifacts.CrimsonMizuhiki).getDefaultInstance()
         )
      );
      CREATIVE_MODE_TABS.register();
   }
}
