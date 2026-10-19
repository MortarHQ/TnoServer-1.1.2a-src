package fzzyhmstrs.emi_loot.neoforge;

import fzzyhmstrs.emi_loot.EMILootAgnos;
import java.util.Optional;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.event.EventHooks;
import org.apache.commons.lang3.text.WordUtils;

public class EMILootAgnosNeoForge extends EMILootAgnos {
   @Override
   protected String getModNameAgnos(String namespace) {
      if (namespace.equals("c")) {
         return "Common";
      }

      Optional<? extends ModContainer> container = ModList.get().getModContainerById(namespace);
      if (container.isPresent()) {
         return container.get().getModInfo().getDisplayName();
      }

      container = ModList.get().getModContainerById(namespace.replace('_', '-'));
      return container.isPresent() ? container.get().getModInfo().getDisplayName() : WordUtils.capitalizeFully(namespace.replace('_', ' '));
   }

   @Override
   protected boolean isDevelopmentEnvironmentAgnos() {
      return !FMLLoader.isProduction();
   }

   @Override
   protected boolean isModLoadedAgnos(String id) {
      return ModList.get().isLoaded(id);
   }

   @Override
   protected LootTable loadLootTableAgnos(ResourceLocation id, LootTable lootTable) {
      return EventHooks.loadLootTable(id, lootTable);
   }

   @Override
   protected void releaseBufferAgnos(FriendlyByteBuf buf) {
   }

   @Override
   protected SpawnEggItem getAllSpawnEggsAgnos(EntityType<?> type) {
      return SpawnEggItem.byId(type);
   }

   static {
      EMILootAgnos.delegate = new EMILootAgnosNeoForge();
   }
}
