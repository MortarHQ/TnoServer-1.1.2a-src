package noobanidus.mods.lootr.common.api;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.adapter.ILootrDataAdapter;
import noobanidus.mods.lootr.common.api.adapter.ILootrItemFrameAdapter;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.api.data.entity.ILootrCart;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface IPlatformAPI {
   void performEntityOpen(ILootrEntity var1, ServerPlayer var2);

   @Deprecated
   default void performCartOpen(ILootrCart cart, ServerPlayer player) {
      this.performEntityOpen(cart, player);
   }

   @Deprecated
   default void performCartOpen(ILootrCart cart) {
      this.performEntityOpen(cart);
   }

   void performEntityOpen(ILootrEntity var1);

   @Deprecated
   default void performCartClose(ILootrCart cart, ServerPlayer player) {
      this.performEntityClose(cart, player);
   }

   void performEntityClose(ILootrEntity var1, ServerPlayer var2);

   @Deprecated
   default void performCartClose(ILootrCart cart) {
      this.performEntityClose(cart);
   }

   void performEntityClose(ILootrEntity var1);

   void performBlockOpen(ILootrBlockEntity var1, ServerPlayer var2);

   void performBlockOpen(ILootrBlockEntity var1);

   void performBlockClose(ILootrBlockEntity var1, ServerPlayer var2);

   void performBlockClose(ILootrBlockEntity var1);

   DataToCopy copySpecificData(BlockEntity var1);

   void restoreSpecificData(DataToCopy var1, BlockEntity var2);

   @Deprecated
   default void copyEntityData(AbstractMinecartContainer entity1, AbstractMinecartContainer entity2) {
      entity2.setXRot(entity1.getXRot());
      entity2.setYRot(entity1.getYRot());
      entity2.setYHeadRot(entity1.getYHeadRot());
      if (entity1.getLootTable() != null) {
         entity2.setLootTable(entity1.getLootTable(), entity1.getLootTableSeed());
      }
   }

   default void copyEntityData(ILootrDataAdapter<Entity> adapter, Entity entity1, ILootrEntity entity3) {
      Entity entity2 = entity3.asEntity();
      entity2.setXRot(entity1.getXRot());
      entity2.setYRot(entity1.getYRot());
      entity2.setYHeadRot(entity1.getYHeadRot());
      ResourceKey<LootTable> lootTable = adapter.getLootTable(entity1);
      long seed = adapter.getLootSeed(entity1);
      if (lootTable != null && entity2 instanceof AbstractMinecartContainer entity4) {
         entity4.setLootTable(lootTable, seed);
      }
   }

   default void copyEntityData(ILootrItemFrameAdapter<Entity> adapter, Entity entity1, ILootrEntity entity3) {
      Entity entity2 = entity3.asEntity();
      entity2.setXRot(entity1.getXRot());
      entity2.setYRot(entity1.getYRot());
      entity2.setYHeadRot(entity1.getYHeadRot());
   }

   void refreshPlayerSection(ServerPlayer var1);

   void performPotBreak(ILootrBlockEntity var1, ServerPlayer var2);

   boolean shouldDoInitialSave();
}
