package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LightBlockRemover implements IEntityTick<ServerLevel> {
   private final LivingEntity entity;
   public static final float deathMaxAge = 70.0F;

   public LightBlockRemover(LivingEntity entity) {
      this.entity = entity;
   }

   public void tick(ServerLevel level) {
      this.entity.deathTime++;
      float interceptedTime = MathUtils.ratioLerp(this.entity.deathTime, 0.5F, 70.0F, 0.0F) * 0.7F;
      level.setBlockAndUpdate(
         this.entity.blockPosition(), (BlockState)Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, Math.round((1.0F - interceptedTime) * 15.0F))
      );
      if (this.entity.deathTime == 49) {
         SoundUtils.playSound(level, this.entity.position(), (SoundEvent)BMDSounds.VOID_BLOSSOM_FALL.get(), SoundSource.HOSTILE, 1.5F, 32.0, null);
      }

      if (this.entity.deathTime == 70.0F) {
         if (level.getBlockState(this.entity.blockPosition()).getBlock() == Blocks.LIGHT) {
            level.setBlockAndUpdate(this.entity.blockPosition(), Blocks.AIR.defaultBlockState());
         }

         level.broadcastEntityEvent(this.entity, (byte)60);
         this.entity.remove(RemovalReason.KILLED);
      }
   }
}
