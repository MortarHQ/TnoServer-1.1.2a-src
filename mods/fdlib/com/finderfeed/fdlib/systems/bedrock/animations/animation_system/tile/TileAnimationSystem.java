package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.tile;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationSystem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.fml.util.thread.EffectiveSide;

public abstract class TileAnimationSystem extends AnimationSystem {
   private BlockEntity tile;

   protected TileAnimationSystem(BlockEntity tile) {
      this.tile = tile;
   }

   public BlockEntity getTile() {
      return this.tile;
   }

   public static TileAnimationSystem create(BlockEntity tile) {
      return EffectiveSide.get().isClient() ? new ClientTileAnimationSystem(tile) : new ServerTileAnimationSystem(tile);
   }
}
