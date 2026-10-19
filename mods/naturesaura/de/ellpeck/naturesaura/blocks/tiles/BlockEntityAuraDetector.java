package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityAuraDetector extends BlockEntityImpl implements ITickableBlockEntity {
   public int redstonePower;

   public BlockEntityAuraDetector(BlockPos pos, BlockState state) {
      super(ModBlockEntities.AURA_DETECTOR, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 20L == 0L) {
         int totalAmount = IAuraChunk.triangulateAuraInArea(this.level, this.worldPosition, 25);
         int power = Mth.clamp(Mth.ceil(totalAmount / 2000000.0F * 15.0F), 0, 15);
         if (this.redstonePower != power) {
            this.redstonePower = power;
            this.level.updateNeighbourForOutputSignal(this.worldPosition, this.getBlockState().getBlock());
         }
      }
   }
}
