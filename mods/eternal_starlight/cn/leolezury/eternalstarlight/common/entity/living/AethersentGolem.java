package cn.leolezury.eternalstarlight.common.entity.living;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.projectile.AethersentMeteor;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AethersentGolem extends AbstractGolem {
   public final AnimationState shootAnimationState = new AnimationState();
   public final AnimationState shootEndAnimationState = new AnimationState();
   public Vec3 leftMuzzlePos = Vec3.ZERO;
   public Vec3 rightMuzzlePos = Vec3.ZERO;
   private Vec3 lookPos = Vec3.ZERO;
   private int peaceTicks = 80;
   public boolean shouldAddShootParticle = false;
   public boolean shootPosTracked = true;
   public boolean useLeftHand = true;

   public AethersentGolem(EntityType<? extends AethersentGolem> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.aethersentGolem.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.aethersentGolem.armor())
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new AethersentGolem.GolemLookAtTargetGoal());
   }

   public void stopAllAnimStates() {
      this.shootAnimationState.stop();
      this.shootEndAnimationState.stop();
   }

   public void handleEntityEvent(byte b) {
      if (b == 100) {
         this.stopAllAnimStates();
         this.shootAnimationState.start(this.tickCount);
         this.shootPosTracked = false;
         this.useLeftHand = !this.useLeftHand;
      } else {
         super.handleEntityEvent(b);
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (!itemStack.is(ESItems.AETHERSENT_INGOT.get())) {
         return InteractionResult.PASS;
      }

      float f = this.getHealth();
      this.heal(25.0F);
      if (this.getHealth() == f) {
         return InteractionResult.PASS;
      }

      itemStack.consume(1, player);
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i <= 10; i++) {
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket(
                     ExplosionShockParticleOptions.AETHERSENT,
                     this.getX() + (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.0F,
                     this.getY(),
                     this.getZ() + (this.random.nextFloat() - 0.5F) * this.getBbWidth() * 2.0F,
                     0.0,
                     1.0,
                     0.0
                  )
               );
         }
      }

      return InteractionResult.sidedSuccess(this.level().isClientSide);
   }

   public void tick() {
      super.tick();
      if (this.peaceTicks >= 80) {
         this.lookPos = new Vec3(this.lookPos.x, this.getEyePosition().y, this.lookPos.z);
      }

      this.peaceTicks++;
      if (!this.level().isClientSide) {
         if (this.tickCount % 20 == 0) {
            List<AethersentMeteor> meteors = this.level()
               .getEntitiesOfClass(AethersentMeteor.class, this.getBoundingBox().inflate(75.0))
               .stream()
               .filter(AethersentMeteor::isNatural)
               .toList();
            List<LivingEntity> mobTargets = this.level()
               .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(50.0))
               .stream()
               .filter(living -> living.getType().is(ESTags.EntityTypes.AETHERSENT_GOLEM_TARGETS))
               .toList();
            if (!meteors.isEmpty() || !mobTargets.isEmpty()) {
               this.level().broadcastEntityEvent(this, (byte)100);
               meteors.forEach(meteor -> meteor.dropAndDiscard(true));
               mobTargets.forEach(living -> living.hurt(this.damageSources().magic(), 8.0F));
               if (!meteors.isEmpty()) {
                  this.lookPos = meteors.getFirst().position();
               } else {
                  this.lookPos = mobTargets.getFirst().position();
               }

               this.playSound(ESSoundEvents.AETHERSENT_GOLEM_SHOOT.get());
               this.peaceTicks = 0;
            }
         }
      } else if (this.shootAnimationState.isStarted()) {
         if (this.shouldAddShootParticle) {
            if (this.useLeftHand) {
               for (int i = 0; i < 15; i++) {
                  Vec3 speed = new Vec3(
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                        this.random.nextFloat() * 0.05F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                     )
                     .normalize()
                     .scale(0.15);
                  this.level()
                     .addParticle(ParticleTypes.LARGE_SMOKE, this.leftMuzzlePos.x, this.leftMuzzlePos.y, this.leftMuzzlePos.z, speed.x, speed.y, speed.z);
               }

               this.level().addParticle(ESExplosionParticleOptions.AETHERSENT, this.leftMuzzlePos.x, this.leftMuzzlePos.y, this.leftMuzzlePos.z, 0.0, 0.0, 0.0);
            } else {
               for (int i = 0; i < 15; i++) {
                  Vec3 speed = new Vec3(
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                        this.random.nextFloat() * 0.05F,
                        (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                     )
                     .normalize()
                     .scale(0.15);
                  this.level()
                     .addParticle(ParticleTypes.LARGE_SMOKE, this.rightMuzzlePos.x, this.rightMuzzlePos.y, this.rightMuzzlePos.z, speed.x, speed.y, speed.z);
               }

               this.level()
                  .addParticle(ESExplosionParticleOptions.AETHERSENT, this.rightMuzzlePos.x, this.rightMuzzlePos.y, this.rightMuzzlePos.z, 0.0, 0.0, 0.0);
            }

            this.shouldAddShootParticle = false;
         }

         if ((float)this.shootAnimationState.getAccumulatedTime() / 1000.0F * 20.0F > 60.0F) {
            this.shootAnimationState.stop();
            this.shootEndAnimationState.start(this.tickCount);
         }
      }
   }

   public boolean isPushable() {
      return false;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.AETHERSENT_GOLEM_HURT.get();
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return ESSoundEvents.AETHERSENT_GOLEM_DEATH.get();
   }

   private class GolemLookAtTargetGoal extends Goal {
      public GolemLookAtTargetGoal() {
         this.setFlags(EnumSet.of(Flag.LOOK));
      }

      public boolean canUse() {
         return true;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         AethersentGolem.this.getLookControl()
            .setLookAt(AethersentGolem.this.lookPos.x, AethersentGolem.this.lookPos.y, AethersentGolem.this.lookPos.z, 360.0F, 360.0F);
      }
   }
}
