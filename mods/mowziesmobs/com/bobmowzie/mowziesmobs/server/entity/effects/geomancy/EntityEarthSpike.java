package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieAnimationController;
import com.bobmowzie.mowziesmobs.client.particle.AdvancedTerrainParticle;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.bluff.EntityBluff;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;

public class EntityEarthSpike extends EntityGeomancyBase {
   private boolean emerged = false;
   private int damageDelay = -1;
   protected MowzieAnimationController<EntityEarthSpike> controller = new MowzieAnimationController(this, "controller", 0, this::predicate, 0.0);
   private static RawAnimation EMERGE = RawAnimation.begin().thenPlay("emerge");

   public EntityEarthSpike(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityEarthSpike(EntityType<? extends EntityMagicEffect> type, Level worldIn, LivingEntity caster, BlockState blockState) {
      super(type, worldIn, caster, blockState, null);
      this.setDeathTime(180);
      this.setTier(EntityGeomancyBase.GeomancyTier.SMALL);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.damageDelay >= 0) {
         this.damageDelay--;
      }

      if (!this.emerged) {
         this.emerged = true;
         this.damageDelay = 2;
         this.playSound((SoundEvent)MMSounds.ENTITY_BLUFF_SPIKE_EMERGE.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
         if (this.level().isClientSide()) {
            for (int i = 0; i < 30; i++) {
               Vec3 offset = new Vec3(0.6 + this.random.nextFloat() * 0.2, 0.1, 0.0).yRot(this.random.nextFloat() * (float) Math.PI * 2.0F);
               Vec3 vel = offset.normalize()
                  .scale(this.random.nextGaussian() * 0.12)
                  .yRot(this.random.nextFloat() * 0.2F - 0.1F)
                  .add(0.0, this.random.nextDouble() * 0.45 + 0.2, 0.0)
                  .add(this.getForward().scale(0.1));
               AdvancedTerrainParticle.spawnTerrainParticle(
                  this.level(),
                  ParticleHandler.TERRAIN,
                  this.getX() + offset.x,
                  this.getY(),
                  this.getZ() + offset.z,
                  vel.x,
                  vel.y,
                  vel.z,
                  0.0,
                  0.4F + this.random.nextGaussian() * 0.3,
                  0.94F,
                  25.0F + this.random.nextFloat() * 10.0F,
                  this.getBlock(),
                  new ParticleComponent[]{new ParticleComponent.Gravity(1.0F)}
               );
            }
         }
      }

      if (this.damageDelay == 0 && !this.level().isClientSide()) {
         this.damageDelay = -1;
         List<Entity> entitiesHit = this.level()
            .getEntities(this, this.getBoundingBox().inflate(0.4), e -> e.canBeHitByProjectile() && e != this.getCaster() && !(e instanceof ItemEntity));
         double damage = 10.0;
         if (this.getCaster() != null && this.getCaster() instanceof EntityBluff) {
            AttributeInstance attrib = this.getCaster().getAttribute(Attributes.ATTACK_DAMAGE);
            if (attrib != null) {
               damage = attrib.getValue();
            }

            damage *= ConfigHandler.COMMON.MOBS.BLUFF.combatConfig.attackMultiplier.get();
         }

         for (Entity entity : entitiesHit) {
            if ((!(this.getCaster() instanceof EntityBluff) || !(entity instanceof EntityBluff)) && !(entity instanceof ItemEntity)) {
               entity.hurt(this.damageSources().mobProjectile(this, this.getCaster()), (float)damage);
               float applyKnockbackResistance = 0.0F;
               if (entity instanceof LivingEntity) {
                  applyKnockbackResistance = (float)((LivingEntity)entity).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
               }

               double y = 0.0;
               if (entity.onGround()) {
                  y += 0.15 * (1.0F - applyKnockbackResistance);
               }

               entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, y, 0.0));
               if (entity instanceof ServerPlayer) {
                  ((ServerPlayer)entity).connection.send(new ClientboundSetEntityMotionPacket(entity));
               }
            }
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(this.controller);
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.emerged = compound.getBoolean("emerged");
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("emerged", this.emerged);
   }

   protected <E extends GeoEntity> PlayState predicate(AnimationState<E> event) {
      event.getController().setAnimation(EMERGE);
      return PlayState.CONTINUE;
   }

   @Override
   protected void explode() {
      super.explode();
   }
}
