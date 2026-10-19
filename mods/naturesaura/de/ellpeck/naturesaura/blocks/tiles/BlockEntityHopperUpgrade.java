package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityHopperUpgrade extends BlockEntityImpl implements ITickableBlockEntity {
   public BlockEntityHopperUpgrade(BlockPos pos, BlockState state) {
      super(ModBlockEntities.HOPPER_UPGRADE, pos, state);
   }

   private static boolean isValidHopper(BlockEntity tile) {
      if (tile instanceof HopperBlockEntity) {
         return (Boolean)tile.getLevel().getBlockState(tile.getBlockPos()).getValue(HopperBlock.ENABLED);
      } else {
         return tile instanceof BlockEntityGratedChute ? ((BlockEntityGratedChute)tile).redstonePower <= 0 : false;
      }
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 10L == 0L) {
         if (IAuraChunk.getAuraInArea(this.level, this.worldPosition, 25) < 100000) {
            return;
         }

         BlockEntity tile = this.level.getBlockEntity(this.worldPosition.below());
         if (!isValidHopper(tile)) {
            return;
         }

         IItemHandler handler = (IItemHandler)this.level.getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, Direction.UP);
         if (handler == null) {
            return;
         }

         List<ItemEntity> items = this.level.getEntitiesOfClass(ItemEntity.class, new AABB(this.worldPosition).inflate(7.0));
         if (items.isEmpty()) {
            return;
         }

         int drainPerItem = 100;
         if (!this.canUseRightNow(drainPerItem * items.size())) {
            return;
         }

         for (ItemEntity item : items) {
            if (item.isAlive() && !item.hasPickUpDelay()) {
               ItemStack stack = item.getItem();
               if (!stack.isEmpty()) {
                  ItemStack copy = stack.copy();

                  for (int i = 0; i < handler.getSlots(); i++) {
                     copy = handler.insertItem(i, copy, false);
                     if (copy.isEmpty()) {
                        break;
                     }
                  }

                  if (!ItemStack.matches(stack, copy)) {
                     item.setItem(copy);
                     if (copy.isEmpty()) {
                        item.kill();
                     }

                     BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 25, this.worldPosition);
                     IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, drainPerItem);
                     PacketHandler.sendToAllAround(
                        this.level,
                        this.worldPosition,
                        32,
                        new PacketParticles((float)item.getX(), (float)item.getY(), (float)item.getZ(), PacketParticles.Type.HOPPER_UPGRADE)
                     );
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
