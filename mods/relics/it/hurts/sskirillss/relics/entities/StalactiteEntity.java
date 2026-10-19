package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class StalactiteEntity extends ThrowableProjectile {
   private float damage;
   private float stun;

   public StalactiteEntity(EntityType<? extends ThrowableProjectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public StalactiteEntity(Level level, float damage, float stun) {
      super((EntityType)EntityRegistry.STALACTITE.get(), level);
      this.damage = damage;
      this.stun = stun;
   }

   public void tick() {
      super.tick();
      if (this.tickCount > 200) {
         this.discard();
      }

      Level level = this.level();
      if (!level.isClientSide()) {
         ((ServerLevel)level)
            .sendParticles(ParticleUtils.constructSimpleSpark(new Color(100, 0, 255), 0.1F, 40, 0.9F), this.xo, this.yo, this.zo, 1, 0.025, 0.025, 0.025, 0.01F);
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      BlockPos pos = result.getBlockPos();
      BlockState state = this.level().getBlockState(pos);
      if (state.blocksMotion()) {
         this.level().playSound(null, pos, SoundEvents.BASALT_BREAK, SoundSource.MASTER, 0.75F, 1.75F);
         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult result) {
      if (!this.level().isClientSide()
         && result.getEntity() instanceof LivingEntity entity
         && (this.getOwner() == null || !entity.getStringUUID().equals(this.getOwner().getStringUUID()))) {
         boolean mayContinue = false;
         if (this.getOwner() instanceof Player player) {
            if (EntityUtils.hurt(entity, this.level().damageSources().thrown(this, player), this.damage)) {
               mayContinue = true;
            }
         } else if (entity.hurt(this.level().damageSources().magic(), this.damage)) {
            mayContinue = true;
         }

         if (mayContinue) {
            entity.addEffect(new MobEffectInstance(EffectRegistry.STUN, Math.round(this.stun), 0, true, false));
         }

         this.discard();
      }
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.damage = compound.getFloat("Damage");
      this.stun = compound.getFloat("Stun");
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putFloat("Damage", this.damage);
      compound.putFloat("Stun", this.stun);
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getStun() {
      return this.stun;
   }

   public void setStun(float stun) {
      this.stun = stun;
   }
}
