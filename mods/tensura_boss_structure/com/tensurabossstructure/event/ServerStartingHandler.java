package com.tensurabossstructure.event;

import com.mojang.datafixers.util.Pair;
import com.tensurabossstructure.TensuraBossStructure;
import com.tensurabossstructure.mixin.StructureTemplatePoolAccessor;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent.UpdateCause;

@EventBusSubscriber
public class ServerStartingHandler {
   @SubscribeEvent
   public static void onTagsUpdated(TagsUpdatedEvent event) {
      if (event.getUpdateCause() == UpdateCause.SERVER_DATA_LOAD) {
         TensuraBossStructure.LOGGER.info("=== Adding otherworlder house to villages via TagsUpdatedEvent ===");
         RegistryAccess registryAccess = event.getRegistryAccess();
         addToPool(
            registryAccess,
            ResourceLocation.withDefaultNamespace("village/plains/houses"),
            ResourceLocation.fromNamespaceAndPath("tensura_boss_structure", "otherworlder"),
            10
         );
         addToPool(
            registryAccess,
            ResourceLocation.withDefaultNamespace("village/taiga/houses"),
            ResourceLocation.fromNamespaceAndPath("tensura_boss_structure", "otherworlder"),
            10
         );
         TensuraBossStructure.LOGGER.info("=== Otherworlder house injection complete ===");
      }
   }

   private static void addToPool(RegistryAccess registryAccess, ResourceLocation poolLocation, ResourceLocation structureLocation, int weight) {
      Registry<StructureTemplatePool> registry = registryAccess.registryOrThrow(Registries.TEMPLATE_POOL);
      StructureTemplatePool pool = (StructureTemplatePool)registry.get(poolLocation);
      if (pool == null) {
         TensuraBossStructure.LOGGER.warn("Pool not found: {}", poolLocation);
      } else {
         StructureTemplatePoolAccessor poolAccessor = (StructureTemplatePoolAccessor)pool;
         TensuraBossStructure.LOGGER.info("Found pool: {} with {} existing elements", poolLocation, poolAccessor.getTemplates().size());
         if (!(poolAccessor.getRawTemplates() instanceof ArrayList)) {
            poolAccessor.setRawTemplates(new ArrayList<>(poolAccessor.getRawTemplates()));
         }

         SinglePoolElement element = (SinglePoolElement)SinglePoolElement.single(structureLocation.toString()).apply(Projection.RIGID);
         poolAccessor.getRawTemplates().add(0, Pair.of(element, 1));
         ObjectArrayList<StructurePoolElement> templates = new ObjectArrayList(poolAccessor.getTemplates());
         int originalSize = templates.size();
         templates.add(0, element);
         poolAccessor.setTemplates(templates);
         TensuraBossStructure.LOGGER
            .info("Added {} to {} at index 0 (pool size: {} -> {})", new Object[]{structureLocation, poolLocation, originalSize, templates.size()});
      }
   }
}
