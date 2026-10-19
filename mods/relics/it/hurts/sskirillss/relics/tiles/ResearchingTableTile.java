package it.hurts.sskirillss.relics.tiles;

import it.hurts.sskirillss.relics.init.TileRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ResearchingTableTile extends BlockEntity {
   private ItemStack stack = ItemStack.EMPTY;
   public int ticksExisted;

   public ResearchingTableTile(BlockPos pos, BlockState state) {
      super((BlockEntityType)TileRegistry.RESEARCHING_TABLE.get(), pos, state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, ResearchingTableTile tile) {
      if (level != null) {
         tile.ticksExisted++;
      }
   }

   protected void loadAdditional(CompoundTag compound, Provider provider) {
      super.loadAdditional(compound, provider);
      this.stack = ItemStack.parseOptional(provider, compound.getCompound("stack"));
   }

   protected void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      if (this.stack != null && !this.stack.isEmpty()) {
         compound.put("stack", this.stack.save(provider, new CompoundTag()));
      }
   }

   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag tag = super.getUpdateTag(provider);
      this.saveAdditional(tag, provider);
      return tag;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public ItemStack getStack() {
      return this.stack;
   }

   public void setStack(ItemStack stack) {
      this.stack = stack;
   }
}
