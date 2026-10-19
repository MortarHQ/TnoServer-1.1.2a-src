package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.init.TFBlockEntities;

public class OminousCandleBlockEntity extends BlockEntity {
   private double heightScalar0;
   private double heightScalar1;
   private double heightScalar2;
   private double heightScalar3;

   public OminousCandleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
      super(type, pos, blockState);
   }

   public OminousCandleBlockEntity(BlockPos pos, BlockState state) {
      this((BlockEntityType<?>)TFBlockEntities.OMINOUS_CANDLE.get(), pos, state);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public double getVisualHeight(int index) {
      return switch (index) {
         case 0 -> this.heightScalar0;
         case 1 -> this.heightScalar1;
         case 2 -> this.heightScalar2;
         case 3 -> this.heightScalar3;
         default -> 0.0;
      };
   }

   public void setVisualHeightScalar(double heightScalar, int index) {
      switch (index) {
         case 0:
            this.heightScalar0 = heightScalar;
            break;
         case 1:
            this.heightScalar1 = heightScalar;
            break;
         case 2:
            this.heightScalar2 = heightScalar;
            break;
         case 3:
            this.heightScalar3 = heightScalar;
      }
   }
}
