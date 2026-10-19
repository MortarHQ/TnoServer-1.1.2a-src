package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.entity.living.goal.MoveToTargetGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.RandomFlyGoal;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import java.util.Collection;
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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PowerableMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class TinyCreteor extends Monster implements PowerableMob {
   private static final String TAG_IGNITED = "ignited";
   private static final String TAG_POWERED = "powered";
   private static final String TAG_SWELL = "swell";
   protected static final EntityDataAccessor<Boolean> IGNITED = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> POWERED = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Integer> SWELL = SynchedEntityData.defineId(TinyCreteor.class, EntityDataSerializers.INT);
   public int oldSwell;
   public float rollAngle;
   public float prevRollAngle;

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

   public TinyCreteor(EntityType<? extends TinyCreteor> entityType, Level level) {
      super(entityType, level);
      this.moveControl = new FlyingMoveControl(this, 20, true);
      this.setNoGravity(true);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IGNITED, false).define(POWERED, false).define(SWELL, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new MoveToTargetGoal(this, 1.0));
      this.goalSelector.addGoal(2, new RandomFlyGoal(this));
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.targetSelector.addGoal(0, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.tinyCreteor.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.tinyCreteor.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.tinyCreteor.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.tinyCreteor.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.0)
         .add(Attributes.FLYING_SPEED, 1.0);
   }

   protected PathNavigation createNavigation(Level level) {
      FlyingPathNavigation navigation = new FlyingPathNavigation(this, level) {
         public boolean isStableDestination(BlockPos blockPos) {
            return this.level.getBlockState(blockPos).isAir();
         }
      };
      navigation.setCanOpenDoors(true);
      navigation.setCanFloat(true);
      navigation.setCanPassDoors(true);
      return navigation;
   }

   public void aiStep() {
      super.aiStep();
      this.oldSwell = this.getSwell();
      if (!this.level().isClientSide) {
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
            this.explode();
         }

         BlockHitResult result = this.level().clip(new ClipContext(this.position(), this.position().subtract(0.0, 2.0, 0.0), Block.COLLIDER, Fluid.NONE, this));
         if (this.onGround() || result.getType() != Type.MISS && result.getLocation().distanceTo(this.position()) < 0.5) {
            this.addDeltaMovement(new Vec3(0.0, 0.01, 0.0));
         }
      } else {
         this.prevRollAngle = this.rollAngle;
         this.rollAngle = this.rollAngle + this.getSwell() / 80.0F * 30.0F;
         if (this.getSwell() == 0) {
            this.rollAngle = Mth.approachDegrees(this.rollAngle, 0.0F, 15.0F);
         }
      }
   }

   public float getSwellProgress(float partialTick) {
      return Mth.lerp(partialTick, this.oldSwell, this.getSwell()) / 80.0F;
   }

   public void thunderHit(ServerLevel serverLevel, LightningBolt lightningBolt) {
      super.thunderHit(serverLevel, lightningBolt);
      this.setPowered(true);
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
         this.dead = true;
         this.level().explode(this, this.getX(), this.getY(), this.getZ(), this.isPowered() ? 3.0F : 2.0F, ExplosionInteraction.NONE);
         if (this.level() instanceof ServerLevel serverLevel) {
            for (int i = 0; i < 12; i++) {
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

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.CRETEOR_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.CRETEOR_DEATH.get();
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("ignited", this.isIgnited());
      compoundTag.putBoolean("powered", this.isPowered());
      compoundTag.putInt("swell", this.getSwell());
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setIgnited(compoundTag.getBoolean("ignited"));
      this.setPowered(compoundTag.getBoolean("powered"));
      this.setSwell(compoundTag.getInt("swell"));
   }
}
