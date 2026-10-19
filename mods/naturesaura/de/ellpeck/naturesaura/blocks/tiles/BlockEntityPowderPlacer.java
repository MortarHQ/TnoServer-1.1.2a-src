package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.entities.EntityEffectInhibitor;
import de.ellpeck.naturesaura.items.ModItems;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityPowderPlacer extends BlockEntityImpl {
   public BlockEntityPowderPlacer(BlockPos pos, BlockState state) {
      super(ModBlockEntities.POWDER_PLACER, pos, state);
   }

   @Override
   public void onRedstonePowerChange(int newPower) {
      if (this.redstonePower <= 0 && newPower > 0) {
         List<EntityEffectInhibitor> powders = this.level
            .getEntitiesOfClass(
               EntityEffectInhibitor.class, new AABB(Vec3.atCenterOf(this.worldPosition), Vec3.atCenterOf(this.worldPosition.offset(1, 2, 1))), Entity::isAlive
            );

         for (Direction facing : Direction.values()) {
            if (facing.getAxis().isHorizontal()) {
               BlockEntity tile = this.level.getBlockEntity(this.worldPosition.relative(facing));
               if (tile != null) {
                  IItemHandler handler = (IItemHandler)this.level
                     .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, facing.getOpposite());
                  if (handler != null) {
                     if (powders.isEmpty()) {
                        for (int i = 0; i < handler.getSlots(); i++) {
                           ItemStack stack = handler.extractItem(i, Integer.MAX_VALUE, true);
                           if (!stack.isEmpty() && stack.getItem() == ModItems.EFFECT_POWDER) {
                              EntityEffectInhibitor.place(
                                 this.level, stack, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1, this.worldPosition.getZ() + 0.5
                              );
                              handler.extractItem(i, Integer.MAX_VALUE, false);
                              break;
                           }
                        }
                     } else {
                        for (EntityEffectInhibitor powder : powders) {
                           ItemStack drop = powder.getDrop();

                           for (int i = 0; i < handler.getSlots(); i++) {
                              ItemStack remain = handler.insertItem(i, drop, false);
                              if (remain.isEmpty()) {
                                 powder.kill();
                                 break;
                              }

                              if (remain.getCount() != drop.getCount()) {
                                 powder.setAmount(remain.getCount());
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      super.onRedstonePowerChange(newPower);
   }
}
