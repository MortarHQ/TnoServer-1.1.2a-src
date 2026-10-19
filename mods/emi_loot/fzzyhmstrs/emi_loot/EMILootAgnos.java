package fzzyhmstrs.emi_loot;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public abstract class EMILootAgnos {
   public static EMILootAgnos delegate;

   public static String getModName(String namespace) {
      return delegate.getModNameAgnos(namespace);
   }

   protected abstract String getModNameAgnos(String var1);

   public static boolean isDevelopmentEnvironment() {
      return delegate.isDevelopmentEnvironmentAgnos();
   }

   protected abstract boolean isDevelopmentEnvironmentAgnos();

   public static boolean isModLoaded(String id) {
      return delegate.isModLoadedAgnos(id);
   }

   protected abstract boolean isModLoadedAgnos(String var1);

   public static LootTable loadLootTable(ResourceLocation id, LootTable lootTable) {
      return delegate.loadLootTableAgnos(id, lootTable);
   }

   protected abstract LootTable loadLootTableAgnos(ResourceLocation var1, LootTable var2);

   public static void releaseBuffer(FriendlyByteBuf buf) {
      delegate.releaseBufferAgnos(buf);
   }

   protected abstract void releaseBufferAgnos(FriendlyByteBuf var1);

   public static SpawnEggItem getSpawnEgg(EntityType<?> type) {
      return delegate.getAllSpawnEggsAgnos(type);
   }

   @Nullable
   protected abstract SpawnEggItem getAllSpawnEggsAgnos(EntityType<?> var1);

   static {
      try {
         Class.forName("fzzyhmstrs.emi_loot.fabric.EMILootAgnosFabric");
      } catch (Throwable var2) {
      }

      try {
         Class.forName("fzzyhmstrs.emi_loot.neoforge.EMILootAgnosNeoForge");
      } catch (Throwable var1) {
      }
   }
}
