package de.ellpeck.naturesaura.blocks.tiles;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityItemDistributor extends BlockEntityImpl implements ITickableBlockEntity {
   private int cooldown;
   private Direction currentSide = Direction.NORTH;
   public boolean isRandomMode;

   public BlockEntityItemDistributor(BlockPos pos, BlockState state) {
      super(ModBlockEntities.ITEM_DISTRIBUTOR, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.cooldown > 0) {
            this.cooldown--;
         } else {
            this.cooldown = 1;
            IItemHandler above = this.getHandler(Direction.UP);
            if (above != null) {
               IItemHandler dest = this.getNextSide();
               if (dest != null) {
                  for (int i = 0; i < above.getSlots(); i++) {
                     ItemStack stack = above.extractItem(i, 1, true);
                     if (!stack.isEmpty()) {
                        for (int j = 0; j < dest.getSlots(); j++) {
                           ItemStack remain = dest.insertItem(j, stack, false);
                           if (!ItemStack.matches(remain, stack)) {
                              above.extractItem(i, 1, false);
                              this.cooldown = 3;
                              return;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private IItemHandler getHandler(Direction direction) {
      BlockPos offset = this.worldPosition.relative(direction);
      BlockEntity tile = this.level.getBlockEntity(offset);
      return tile == null
         ? null
         : (IItemHandler)this.level.getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, direction.getOpposite());
   }

   private IItemHandler getNextSide() {
      if (this.isRandomMode) {
         List<IItemHandler> handlers = new ArrayList<>();

         for (int i = 0; i < 4; i++) {
            IItemHandler handler = this.getHandler(Direction.values()[i]);
            if (handler != null) {
               handlers.add(handler);
            }
         }

         return handlers.isEmpty() ? null : handlers.get(this.level.random.nextInt(handlers.size()));
      } else {
         for (int i = 0; i < 4; i++) {
            this.currentSide = this.currentSide.getClockWise();
            IItemHandler handler = this.getHandler(this.currentSide);
            if (handler != null) {
               return handler;
            }
         }

         return null;
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.TILE) {
         compound.putInt("cooldown", this.cooldown);
         compound.putInt("side", this.currentSide.ordinal());
      }

      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.putBoolean("random", this.isRandomMode);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.TILE) {
         this.cooldown = compound.getInt("cooldown");
         this.currentSide = Direction.values()[compound.getInt("side")];
      }

      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.isRandomMode = compound.getBoolean("random");
      }
   }
}
