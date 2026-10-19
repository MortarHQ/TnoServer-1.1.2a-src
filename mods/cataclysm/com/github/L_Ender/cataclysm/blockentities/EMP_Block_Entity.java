package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.EMP_Block;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EMP_Block_Entity extends BlockEntity {
   private float chompProgress;
   private float prevChompProgress;
   public int ticksExisted;

   public EMP_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.EMP.get(), pos, state);
   }

   public static void commonTick(Level level, BlockPos pos, BlockState state, EMP_Block_Entity entity) {
      entity.tick();
   }

   public void tick() {
      this.prevChompProgress = this.chompProgress;
      boolean powered = false;
      if (this.getBlockState().getBlock() instanceof EMP_Block) {
         powered = (Boolean)this.getBlockState().getValue(EMP_Block.POWERED);
      }

      boolean overload = false;
      if (this.getBlockState().getBlock() instanceof EMP_Block) {
         overload = (Boolean)this.getBlockState().getValue(EMP_Block.OVERLOAD);
      }

      if (powered && this.chompProgress < 15.0F) {
         this.chompProgress++;
      }

      if (!powered && this.chompProgress > 0.0F) {
         this.chompProgress--;
      }

      float x = this.getBlockPos().getX() + 0.5F;
      float y = this.getBlockPos().getY() + 0.5F;
      float z = this.getBlockPos().getZ() + 0.5F;
      if (!overload && this.chompProgress == 15.0F) {
         this.level.addParticle((ParticleOptions)ModParticle.EM_PULSE.get(), x, y, z, 0.0, 0.0, 0.0);
         ScreenShake_Entity.ScreenShake(this.level, Vec3.atCenterOf(this.getBlockPos()), 20.0F, 0.01F, 0, 20);
         this.level
            .playSound(
               (Player)null,
               this.getBlockPos(),
               (SoundEvent)ModSounds.EMP_ACTIVATED.get(),
               SoundSource.BLOCKS,
               4.0F,
               this.level.random.nextFloat() * 0.2F + 1.0F
            );
         this.level.setBlockAndUpdate(this.getBlockPos(), (BlockState)this.getBlockState().setValue(EMP_Block.OVERLOAD, true));
         AABB screamBox = new AABB(
            this.getBlockPos().getX() - 5.0F,
            this.getBlockPos().getY() - 5.0F,
            this.getBlockPos().getZ() - 5,
            this.getBlockPos().getX() + 5,
            this.getBlockPos().getY() + 5.0F,
            this.getBlockPos().getZ() + 5.0F
         );

         for (LivingEntity entity : this.level.getEntitiesOfClass(LivingEntity.class, screamBox)) {
            entity.hurt(CMDamageTypes.getDamageSource(this.level, CMDamageTypes.EMP), 3 + entity.getRandom().nextInt(3));
         }
      }

      this.ticksExisted++;
   }

   public float getChompProgress(float partialTick) {
      return this.prevChompProgress + (this.chompProgress - this.prevChompProgress) * partialTick;
   }
}
