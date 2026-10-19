package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.EndSplashParticleOption;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.init.RVSounds;
import com.mongoose.royalvariations.utils.MobUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent.EnderEntity;

public class ThrownRoyalEnderPearl extends ThrowableItemProjectile {
   public ThrownRoyalEnderPearl(EntityType<? extends ThrownRoyalEnderPearl> p_37491_, Level p_37492_) {
      super(p_37491_, p_37492_);
   }

   public ThrownRoyalEnderPearl(Level p_37499_, LivingEntity p_37500_) {
      super((EntityType)RVEntityType.ROYAL_ENDER_PEARL.get(), p_37500_, p_37499_);
   }

   protected Item getDefaultItem() {
      return (Item)RVItems.ROYAL_ENDER_PEARL.get();
   }

   protected void onHitEntity(EntityHitResult p_37502_) {
      super.onHitEntity(p_37502_);
      p_37502_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);
   }

   protected void onHit(HitResult p_37504_) {
      super.onHit(p_37504_);

      for (int i = 0; i < 32; i++) {
         this.level()
            .addParticle(
               ParticleTypes.PORTAL,
               this.getX(),
               this.getY() + this.random.nextDouble() * 2.0,
               this.getZ(),
               this.random.nextGaussian(),
               0.0,
               this.random.nextGaussian()
            );
      }

      if (this.level() instanceof ServerLevel serverlevel && !this.isRemoved()) {
         Entity entity = this.getOwner();
         if (entity != null && isAllowedToTeleportOwner(entity, serverlevel)) {
            if (entity.isPassenger()) {
               entity.unRide();
            }

            if (entity instanceof ServerPlayer serverplayer) {
               if (serverplayer.connection.isAcceptingMessages()) {
                  EnderEntity event = EventHooks.onEnderTeleport(serverplayer, this.getX(), this.getY(), this.getZ());
                  if (!event.isCanceled()) {
                     entity.changeDimension(
                        new DimensionTransition(
                           serverlevel, event.getTarget(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING
                        )
                     );
                     entity.resetFallDistance();
                     this.playSound((SoundEvent)RVSounds.END_SPLASH.get(), 2.0F, 1.0F);
                     if (this.level() instanceof ServerLevel serverLevel) {
                        for (int i = 0; i < 20; i++) {
                           double d0 = this.random.nextGaussian() * 0.02;
                           double d1 = this.random.nextGaussian() * 0.02;
                           double d2 = this.random.nextGaussian() * 0.02;
                           double d3 = 10.0;
                           serverLevel.sendParticles(
                              (SimpleParticleType)RVParticleTypes.END_POOF.get(),
                              entity.getX(1.0) - d0 * d3,
                              entity.getRandomY() - d1 * d3,
                              entity.getRandomZ(1.0) - d2 * d3,
                              0,
                              d0,
                              d1,
                              d2,
                              1.0
                           );
                        }

                        serverLevel.sendParticles(
                           new EndSplashParticleOption(4.0F, 2), entity.getX(), entity.getY() + 0.5, entity.getZ(), 1, 0.0, 0.0, 0.0, 0.0
                        );
                     }

                     for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(4.0))) {
                        if (!MobUtil.areAllies(entity, livingEntity)
                           && livingEntity instanceof Enemy
                           && livingEntity.hurt(this.damageSources().indirectMagic(this, this.getOwner()), 7.0F)) {
                           livingEntity.addEffect(new MobEffectInstance(RVEffects.HEAVINESS_OF_THE_END, 140));
                        }
                     }
                  }
               }
            } else {
               entity.changeDimension(
                  new DimensionTransition(
                     serverlevel, this.position(), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot(), DimensionTransition.DO_NOTHING
                  )
               );
               entity.resetFallDistance();
               this.playSound(serverlevel, this.position());
            }
         }

         this.discard();
      } else {
         this.discard();
      }
   }

   private static boolean isAllowedToTeleportOwner(Entity entity, Level level) {
      if (entity.level().dimension() != level.dimension()) {
         return entity.canUsePortal(true);
      } else {
         return entity instanceof LivingEntity livingentity ? livingentity.isAlive() && !livingentity.isSleeping() : entity.isAlive();
      }
   }

   public void tick() {
      Entity entity = this.getOwner();
      if (entity instanceof Player && !entity.isAlive()) {
         this.discard();
      } else {
         super.tick();
      }
   }

   private void playSound(Level level, Vec3 pos) {
      level.playSound(null, pos.x, pos.y, pos.z, SoundEvents.PLAYER_TELEPORT, SoundSource.PLAYERS);
   }

   public boolean canChangeDimensions(Level oldLevel, Level newLevel) {
      return oldLevel.dimension() == Level.END && this.getOwner() instanceof ServerPlayer serverplayer
         ? super.canChangeDimensions(oldLevel, newLevel) && serverplayer.seenCredits
         : super.canChangeDimensions(oldLevel, newLevel);
   }

   protected void onInsideBlock(BlockState state) {
      super.onInsideBlock(state);
      if (state.is(Blocks.END_GATEWAY) && this.getOwner() instanceof ServerPlayer serverplayer) {
         serverplayer.onInsideBlock(state);
      }
   }
}
