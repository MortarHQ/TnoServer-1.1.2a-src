package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimatedObject;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.ModelSystem;
import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.model_system.tile_model_system.TileModelSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FDBlockEntity extends BlockEntity implements AnimatedObject {
   private ModelSystem system = new TileModelSystem(this);

   public FDBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public static void tick(BlockEntity entity) {
      ((FDBlockEntity)entity).tick();
   }

   public void tick() {
      this.tickModelSystem();
   }

   public void onLoad() {
      super.onLoad();
   }

   @Override
   public ModelSystem getModelSystem() {
      return this.system;
   }
}
