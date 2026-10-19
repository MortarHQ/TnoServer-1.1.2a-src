package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.blocks.BlockGratedChute;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityGratedChute extends BlockEntityImpl implements ITickableBlockEntity {
   public boolean isBlacklist;
   public final ItemStackHandlerNA items = new ItemStackHandlerNA(1, this, true) {
      @Override
      protected boolean canExtract(ItemStack stack, int slot, int amount) {
         return BlockEntityGratedChute.this.redstonePower <= 0;
      }

      @Override
      protected boolean canInsert(ItemStack stack, int slot) {
         return BlockEntityGratedChute.this.isBlacklist != BlockEntityGratedChute.this.isItemInFrame(stack);
      }
   };
   private int cooldown;

   public BlockEntityGratedChute(BlockPos pos, BlockState state) {
      super(ModBlockEntities.GRATED_CHUTE, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide) {
         if (this.cooldown <= 0) {
            this.cooldown = 6;
            if (this.redstonePower > 0) {
               return;
            }

            ItemStack curr = this.items.getStackInSlot(0);
            if (!curr.isEmpty()) {
               BlockState state = this.level.getBlockState(this.worldPosition);
               Direction facing = (Direction)state.getValue(BlockGratedChute.FACING);
               BlockEntity tile = this.level.getBlockEntity(this.worldPosition.relative(facing));
               if (tile != null) {
                  IItemHandler handler = (IItemHandler)this.level
                     .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, facing.getOpposite());
                  if (handler != null) {
                     for (int i = 0; i < handler.getSlots(); i++) {
                        ItemStack theoreticalDrain = this.items.extractItem(0, 1, true);
                        if (!theoreticalDrain.isEmpty()) {
                           ItemStack left = handler.insertItem(i, theoreticalDrain, false);
                           if (left.isEmpty()) {
                              this.items.extractItem(0, 1, false);
                              break;
                           }
                        }
                     }
                  }
               }
            }

            if (curr.isEmpty() || curr.getCount() < curr.getMaxStackSize()) {
               List<ItemEntity> items = this.level
                  .getEntitiesOfClass(
                     ItemEntity.class,
                     new AABB(
                        this.worldPosition.getX(),
                        this.worldPosition.getY() + 0.5,
                        this.worldPosition.getZ(),
                        this.worldPosition.getX() + 1,
                        this.worldPosition.getY() + 2,
                        this.worldPosition.getZ() + 1
                     )
                  );
               Iterator var10 = items.iterator();

               while (true) {
                  if (!var10.hasNext()) {
                     BlockEntity tileUp = this.level.getBlockEntity(this.worldPosition.above());
                     if (tileUp != null) {
                        IItemHandler handlerUp = (IItemHandler)this.level
                           .getCapability(ItemHandler.BLOCK, tileUp.getBlockPos(), tileUp.getBlockState(), tileUp, Direction.DOWN);
                        if (handlerUp != null) {
                           for (int i = 0; i < handlerUp.getSlots(); i++) {
                              ItemStack theoreticalDrain = handlerUp.extractItem(i, 1, true);
                              if (!theoreticalDrain.isEmpty()) {
                                 ItemStack left = this.items.insertItem(0, theoreticalDrain, false);
                                 if (left.isEmpty()) {
                                    handlerUp.extractItem(i, 1, false);
                                    return;
                                 }
                              }
                           }
                        }
                     }
                     break;
                  }

                  ItemEntity item = (ItemEntity)var10.next();
                  if (item.isAlive()) {
                     ItemStack stack = item.getItem();
                     if (!stack.isEmpty()) {
                        ItemStack left = this.items.insertItem(0, stack, false);
                        if (!ItemStack.matches(stack, left)) {
                           if (left.isEmpty()) {
                              item.kill();
                           } else {
                              item.setItem(left);
                           }
                           break;
                        }
                     }
                  }
               }
            }
         } else {
            this.cooldown--;
         }
      }
   }

   private boolean isItemInFrame(ItemStack stack) {
      List<ItemFrame> frames = Helper.getAttachedItemFrames(this.level, this.worldPosition);
      if (frames.isEmpty()) {
         return false;
      }

      for (ItemFrame frame : frames) {
         ItemStack frameStack = frame.getItem();
         if (Helper.areItemsEqual(stack, frameStack, true)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         compound.putInt("cooldown", this.cooldown);
         compound.put("items", this.items.serializeNBT(registries));
         compound.putBoolean("blacklist", this.isBlacklist);
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         this.cooldown = compound.getInt("cooldown");
         this.items.deserializeNBT(registries, compound.getCompound("items"));
         this.isBlacklist = compound.getBoolean("blacklist");
      }
   }
}
