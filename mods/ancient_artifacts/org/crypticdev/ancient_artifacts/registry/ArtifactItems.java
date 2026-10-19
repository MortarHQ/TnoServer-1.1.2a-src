package org.crypticdev.ancient_artifacts.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.component.AttackerArmorComponent;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.component.UnlockComponent;
import org.crypticdev.ancient_artifacts.platform.PlatformItems;

public final class ArtifactItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("ancient_artifacts", Registries.ITEM);
   private static final Map<ArtifactDefinition, RegistrySupplier<Item>> ITEMS_BY_ARTIFACT = new LinkedHashMap<>();

   private ArtifactItems() {
   }

   public static void init() {
      ITEMS.register();
   }

   public static Item get(ArtifactDefinition definition) {
      return (Item)supplier(definition).get();
   }

   public static RegistrySupplier<Item> supplier(ArtifactDefinition definition) {
      RegistrySupplier<Item> supplier = ITEMS_BY_ARTIFACT.get(definition);
      if (supplier == null) {
         throw new IllegalArgumentException("Unknown artifact definition: " + definition.id());
      } else {
         return supplier;
      }
   }

   private static RegistrySupplier<Item> registerArtifact(ArtifactDefinition definition) {
      RegistrySupplier<Item> item = ITEMS.register(
         definition.id(),
         () -> PlatformItems.createArtifactItem(definition, artifactProperties(definition.defaultRefinement()).arch$tab(ArtifactItemTab.ArtifactTab))
      );
      ITEMS_BY_ARTIFACT.put(definition, item);
      return item;
   }

   private static Properties artifactProperties(RefinementComponent refinement) {
      return new Properties()
         .stacksTo(1)
         .component(ArtifactDataComponents.REFINEMENT.get(), refinement)
         .component(ArtifactDataComponents.UNLOCKS.get(), new UnlockComponent())
         .component(ArtifactDataComponents.ATTACKER_ARMOR.get(), new AttackerArmorComponent(0));
   }

   static {
      for (ArtifactDefinition definition : Artifacts.ALL) {
         registerArtifact(definition);
      }
   }
}
