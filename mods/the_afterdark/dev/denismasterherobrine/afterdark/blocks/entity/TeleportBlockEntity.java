package dev.denismasterherobrine.afterdark.blocks.entity;

import dev.denismasterherobrine.afterdark.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TeleportBlockEntity extends BlockEntity {
   public static String TELEPORT_BE_ID = "teleport_block_entity";
   public int MAX_TELEPORTS = Config.INSTANCE.TeleportCatalystUses;
   private int remainingTeleports = 0;

   public TeleportBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BuiltInRegistries.BLOCK_ENTITY_TYPE.get(ResourceLocation.fromNamespaceAndPath("the_afterdark", TELEPORT_BE_ID)), pos, state);
   }

   public int getRemainingTeleports() {
      return this.remainingTeleports;
   }

   public void setRemainingTeleports(int remainingTeleports) {
      this.remainingTeleports = remainingTeleports;
      this.setChanged();
   }

   public void renewTeleports() {
      this.remainingTeleports = this.MAX_TELEPORTS;
      this.setChanged();
   }

   public void loadAdditional(CompoundTag nbt, Provider registryLookup) {
      super.loadAdditional(nbt, registryLookup);
      this.remainingTeleports = nbt.getInt("RemainingTeleports");
   }

   protected void saveAdditional(CompoundTag nbt, Provider registryLookup) {
      super.saveAdditional(nbt, registryLookup);
      nbt.putInt("RemainingTeleports", this.remainingTeleports);
   }
}
