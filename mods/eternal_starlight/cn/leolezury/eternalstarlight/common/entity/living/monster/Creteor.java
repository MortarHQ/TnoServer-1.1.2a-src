package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import java.util.Collection;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Creteor extends Monster implements PowerableMob {
   private static final String TAG_ACTIVATED = "activated";
   private static final String TAG_IGNITED = "ignited";
   private static final String TAG_POWERED = "powered";
   private static final String TAG_SWELL = "swell";
   private static final String TAG_SPIN_TICKS = "spin_ticks";
   public AnimationState idleAnimationState = new AnimationState();
   protected static final EntityDataAccessor<Boolean> ACTIVATED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Integer> SWELL = SynchedEntityData.defineId(Creteor.class, EntityDataSerializers.INT);
   public int oldSwell;
   public int spinTicks;
   public float rollAngle;
   public float prevRollAngle;
   public float spin;
   public float prevSpin;

   public boolean isActivated() {
      return (Boolean)this.getEntityData().get(ACTIVATED);
   }

   public void setActivated(boolean activated) {
      this.getEntityData().set(ACTIVATED, activated);
   }

   public boolean isIgnited() {
      return (Boolean)this.getEntityData().get(IGNITED);
   }

   public void setIgnited(boolean ignited) {
      this.getEntityData().set(IGNITED, ignited);
   }

   public boolean isPowered() {
      return (Boolean)this.getEntityData().get(POWERED);
   }

   public void setPowered(boolean powered) {
      this.getEntityData().set(POWERED, powered);
   }

   public int getSwell() {
      return (Integer)this.getEntityData().get(SWELL);
   }

   public void setSwell(int swell) {
      this.getEntityData().set(SWELL, swell);
   }

   public Creteor(EntityType<? extends Creteor> type, Level level) {
      super(type, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ACTIVATED, false).define(IGNITED, false).define(POWERED, false).define(SWELL, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new Creteor.CreteorDoNothingGoal());
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true) {
         public boolean canUse() {
            return super.canUse() && Creteor.this.isActivated();
         }
      });
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.creteor.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.creteor.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.creteor.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.creteor.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3);
   }

   public void aiStep() {
      super.aiStep();
      this.oldSwell = this.getSwell();
      if (this.getSwell() < 80) {
         this.spinTicks = 0;
      } else {
         this.spinTicks++;
      }

      if (!this.level().isClientSide) {
         if ((this.getTarget() != null || this.isIgnited()) && !this.isActivated()) {
            this.setActivated(true);
         }

         if (this.getSwell() < 80) {
            LivingEntity target = this.getTarget();
            if (this.isIgnited()) {
               this.setSwell(this.getSwell() + 1);
            } else if (target == null) {
               this.setSwell(this.getSwell() - 1);
            } else if (this.distanceToSqr(target) > 25.0) {
               this.setSwell(this.getSwell() - 1);
            } else if (!this.getSensing().hasLineOfSight(target)) {
               this.setSwell(this.getSwell() - 1);
            } else {
               this.setSwell(this.getSwell() + 1);
            }

            this.setSwell(Mth.clamp(this.getSwell(), 0, 80));
         } else {
            this.setNoGravity(true);
            this.hurtMarked = true;
            if (this.spinTicks <= 40) {
               this.setDeltaMovement(new Vec3(0.0, 0.1, 0.0));
            } else {
               this.setDeltaMovement(Vec3.ZERO);
            }

            if (this.spinTicks == 1) {
               this.playSound(ESSoundEvents.CRETEOR_CHARGE.get());
            }

            if (this.spinTicks > 80) {
               this.explode();
            }
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
         this.prevRollAngle = this.rollAngle;
         this.rollAngle = this.rollAngle + (float)(this.position().subtract(new Vec3(this.xOld, this.yOld, this.zOld)).length() / 0.011538462F);
         this.rollAngle = Mth.wrapDegrees(this.rollAngle);
         if (!this.isActivated()) {
            this.rollAngle = 0.0F;
         }

         this.prevSpin = this.spin;
         if (this.getSwell() >= 80) {
            this.spin += 90.0F;
         }
      }
   }

   public void setDeltaMovement(Vec3 vec3) {
      super.setDeltaMovement(this.isActivated() ? vec3 : new Vec3(0.0, vec3.y, 0.0));
   }

   public float getSwellProgress(float partialTick) {
      return this.getSwell() < 80
         ? Mth.lerp(partialTick, this.oldSwell, this.getSwell()) / 160.0F
         : Math.min(this.spinTicks + 80 + partialTick, 160.0F) / 160.0F;
   }

   public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
      super.thunderHit(serverLevel, lightningBolt);
      this.setPowered(true);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.isActivated()) {
         this.setActivated(true);
      }

      return super.hurt(source, amount);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
      ItemStack itemStack = player.getItemInHand(interactionHand);
      if (itemStack.is(ItemTags.CREEPER_IGNITERS)) {
         SoundEvent soundEvent = itemStack.is(Items.FIRE_CHARGE) ? SoundEvents.FIRECHARGE_USE : SoundEvents.FLINTANDSTEEL_USE;
         this.level().playSound(player, this.getX(), this.getY(), this.getZ(), soundEvent, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
         if (!this.level().isClientSide) {
            this.setIgnited(true);
            if (!itemStack.isDamageableItem()) {
               itemStack.shrink(1);
            } else {
               itemStack.hurtAndBreak(1, player, getSlotForHand(interactionHand));
            }
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, interactionHand);
      }
   }

   private void explode() {
      if (!this.level().isClientSide) {
         float f = this.isPowered() ? 2.0F : 1.0F;
         this.dead = true;
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F * f, ExplosionInteraction.MOB);
         if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 25; i++) {
               Vec3 speed = new Vec3(
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                     this.random.nextFloat() * 0.05F,
                     (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                  )
                  .normalize();
               ESPlatform.INSTANCE
                  .sendToAllClients(
                     serverLevel,
                     new ParticlePacket(
                        ExplosionShockParticleOptions.AETHERSENT,
                        this.getEyePosition().x + speed.x * 1.2,
                        this.getEyePosition().y + speed.y * 1.2,
                        this.getEyePosition().z + speed.z * 1.2,
                        speed.x,
                        speed.y,
                        speed.z
                     )
                  );
            }
         }

         this.playSound(ESSoundEvents.CRETEOR_EXPLODE.get());
         this.spawnLingeringCloud();
         this.triggerOnDeathMobEffects(RemovalReason.KILLED);
         this.discard();
         if (ESConfig.INSTANCE.mobsConfig.tinyCreteor.canSpawn()) {
            int splitCount = this.getRandom().nextInt(2, 5);

            for (int i = 0; i < splitCount; i++) {
               TinyCreteor tinyCreteor = new TinyCreteor(ESEntities.TINY_CRETEOR.get(), this.level());
               tinyCreteor.setPos(this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0));
               tinyCreteor.setPowered(this.isPowered());
               tinyCreteor.setTarget(this.getTarget());
               this.level().addFreshEntity(tinyCreteor);
               tinyCreteor.hurtMarked = true;
               tinyCreteor.setDeltaMovement(
                  new Vec3(
                     (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F,
                     this.getRandom().nextFloat() * 0.05F,
                     (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F
                  )
               );
            }
         }
      }
   }

   private void spawnLingeringCloud() {
      Collection<MobEffectInstance> collection = this.getActiveEffects();
      if (!collection.isEmpty()) {
         AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
         areaEffectCloud.setRadius(2.5F);
         areaEffectCloud.setRadiusOnUse(-0.5F);
         areaEffectCloud.setWaitTime(10);
         areaEffectCloud.setDuration(areaEffectCloud.getDuration() / 2);
         areaEffectCloud.setRadiusPerTick(-areaEffectCloud.getRadius() / areaEffectCloud.getDuration());

         for (MobEffectInstance mobEffectInstance : collection) {
            areaEffectCloud.addEffect(new MobEffectInstance(mobEffectInstance));
         }

         this.level().addFreshEntity(areaEffectCloud);
      }
   }

   protected void playStepSound(BlockPos blockPos, BlockState blockState) {
      super.playStepSound(blockPos, blockState);
      this.playSound(ESSoundEvents.CRETEOR_ROLL.get());
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.CRETEOR_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.CRETEOR_DEATH.get();
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("activated", this.isActivated());
      compoundTag.putBoolean("ignited", this.isIgnited());
      compoundTag.putBoolean("powered", this.isPowered());
      compoundTag.putInt("swell", this.getSwell());
      compoundTag.putInt("spin_ticks", this.spinTicks);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setActivated(compoundTag.getBoolean("activated"));
      this.setIgnited(compoundTag.getBoolean("ignited"));
      this.setPowered(compoundTag.getBoolean("powered"));
      this.setSwell(compoundTag.getInt("swell"));
      this.spinTicks = compoundTag.getInt("spin_ticks");
   }

   public static boolean checkCreteorSpawnRules(
      EntityType<? extends Creteor> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.creteor.canSpawn();
   }

   class CreteorDoNothingGoal extends Goal {
      public CreteorDoNothingGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         return !Creteor.this.isActivated();
      }
   }
}
