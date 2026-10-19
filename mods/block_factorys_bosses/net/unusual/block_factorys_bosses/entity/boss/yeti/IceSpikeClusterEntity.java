package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.attachment.entity.GauntletAttachment;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IceSpikeClusterEntity extends Entity implements OwnableByAllEntity, GeoEntity, DangerZonesProvider {
   public static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(
      IceSpikeClusterEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final EntityDataAccessor<Boolean> DATA_HELD = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> DATA_REMAINING_HIT = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_HIT_ANIMATION_TIME = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIM_TIME = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> DATA_EVIL = SynchedEntityData.defineId(IceSpikeClusterEntity.class, EntityDataSerializers.BOOLEAN);
   public static final int SPAWN_ANIMATION_DURATION = 5;
   public static final int SPAWN_DELAY_DEFAULT = 16;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private final DangerZonesProvider.DangerZone zone;
   private float damage = 10.0F;

   public IceSpikeClusterEntity(EntityType<IceSpikeClusterEntity> type, Level world) {
      super(type, world);
      this.zone = new DangerZonesProvider.DangerZone();
      this.zone.setOffset(new Vector3f(0.0F, 0.0F, 0.0F));
      this.scaleZone();
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_OWNER_UUID, Optional.empty());
      builder.define(DATA_HELD, false);
      builder.define(DATA_REMAINING_HIT, 5);
      builder.define(DATA_HIT_ANIMATION_TIME, 0);
      builder.define(DATA_SPAWN_ANIM_TIME, 0);
      builder.define(DATA_DELAY, 16);
      builder.define(DATA_EVIL, false);
   }

   private void scaleZone() {
      float size = this.getDimensions(Pose.STANDING).width();
      this.zone.setSize(size, size);
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(3.0);
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
   }

   public int getDelay() {
      return (Integer)this.entityData.get(DATA_DELAY);
   }

   public void setDelay(int scale) {
      this.entityData.set(DATA_DELAY, scale);
   }

   public boolean getEvil() {
      return (Boolean)this.entityData.get(DATA_EVIL);
   }

   public void setEvil(boolean evil) {
      this.entityData.set(DATA_EVIL, evil);
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getDamage() {
      return this.damage;
   }

   public boolean getHeld() {
      return (Boolean)this.entityData.get(DATA_HELD);
   }

   public void setHeld(boolean held) {
      this.entityData.set(DATA_HELD, held);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      if (DATA_HELD.equals(key)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(key);
   }

   public EntityDimensions getDimensions(Pose pose) {
      return this.getHeld() ? super.getDimensions(pose).scale(0.1F) : super.getDimensions(pose);
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (stack.is(BossesRiseItems.ICE_GAUNTLET)) {
         GauntletAttachment attachment = GauntletAttachment.fromPlayer(player);
         int index = attachment.getNewShardIndex(player.getRandom());
         if (index == -1) {
            return InteractionResult.PASS;
         }

         this.entityData.set(DATA_REMAINING_HIT, index);
         attachment.getShards()[index] = this.getUUID();
         player.startUsingItem(hand);
         PlayerAnimationHandler.fromPlayer(player).startAnimation(player, GauntletAttachment.GRAB_AND_HOLD);
         if (player.level() instanceof ServerLevel level) {
            level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 15, 0.25, 0.5, 0.25, 0.0);
         }

         this.setHeld(true);
         this.level()
            .playSound(
               null,
               this.blockPosition(),
               (SoundEvent)BossesRiseSounds.ICICLE_PICKUP.value(),
               SoundSource.PLAYERS,
               0.9F,
               (float)Mth.nextDouble(this.random, 0.15, 0.35)
            );
         this.setOwnerUUID(player.getUUID());
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void tick() {
      if (this.getOwner() instanceof ServerPlayer player && this.getHeld() && !player.isUsingItem()) {
         this.setHeld(false);
      }

      super.tick();
   }

   public Vec3 position() {
      return this.getOwner() instanceof Player player && player.isUsingItem() && this.getHeld() ? this.heldPos(player, 1.0F) : super.position();
   }

   public Vec3 heldPos(Player player, float partialTick) {
      int place = (Integer)this.entityData.get(DATA_REMAINING_HIT);
      float range = 0.85F;
      float rot = place % 2 == 1 ? 90.0F : -90.0F;
      float offset = place / 2 % 2 == 1 ? range : -range;
      Vec3 eyePosition = player.getEyePosition(partialTick);
      Vec3 viewVector = player.calculateViewVector(0.0F, player.getViewYRot(partialTick));
      return eyePosition.add(viewVector.x * range * 0.5, viewVector.y * range * 0.5 - this.getBbHeight() * 0.5 + offset, viewVector.z * range * 0.5)
         .add(viewVector.scale(range).yRot(rot * (float) (Math.PI / 180.0)));
   }

   public boolean hurt(DamageSource damagesource, float amount) {
      double x = this.getX();
      double y = this.getY();
      double z = this.getZ();
      this.playSound((SoundEvent)BossesRiseSounds.ICICLE_HURT.value());
      if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) == 0) {
         int health = (Integer)this.getEntityData().get(DATA_REMAINING_HIT);
         if (health > 1) {
            this.getEntityData().set(DATA_REMAINING_HIT, health - 1);
            this.level().playSound(null, BlockPos.containing(x, y, z), (SoundEvent)BossesRiseSounds.ICICLE_BREAK.value(), SoundSource.NEUTRAL, 1.0F, 3.0F);
            this.getEntityData().set(DATA_HIT_ANIMATION_TIME, 8);
         } else {
            this.remove(RemovalReason.KILLED);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public boolean fireImmune() {
      return true;
   }

   public void remove(RemovalReason reason) {
      if (reason == RemovalReason.KILLED) {
         BlockPos pos = BlockPos.containing(this.position());
         if (this.random.nextInt(10) == 0) {
            this.level().playSound(null, pos, (SoundEvent)BossesRiseSounds.ICICLE_BREAK.value(), SoundSource.NEUTRAL, 0.5F, 0.5F);
         }

         this.level().levelEvent(2001, pos, Block.getId(((Block)BossesRiseBlocks.ICE_BLOCK_PARTICULES.get()).defaultBlockState()));
      }

      super.remove(reason);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      tag.putBoolean("held", (Boolean)this.entityData.get(DATA_HELD));
      tag.putInt("health", (Integer)this.entityData.get(DATA_REMAINING_HIT));
      tag.putInt("hurt_animation", (Integer)this.entityData.get(DATA_HIT_ANIMATION_TIME));
      tag.putInt("spawn_animation", (Integer)this.entityData.get(DATA_SPAWN_ANIM_TIME));
      if (this.getOwnerUUID() != null) {
         tag.putUUID("owner", this.getOwnerUUID());
      }

      tag.putInt("delay", (Integer)this.entityData.get(DATA_DELAY));
      tag.putBoolean("evil", (Boolean)this.entityData.get(DATA_EVIL));
      tag.putInt("age", this.tickCount);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      this.entityData.set(DATA_HELD, tag.getBoolean("held"));
      this.entityData.set(DATA_REMAINING_HIT, tag.getInt("health"));
      this.entityData.set(DATA_HIT_ANIMATION_TIME, tag.getInt("hurt_animation"));
      this.entityData.set(DATA_SPAWN_ANIM_TIME, tag.getInt("spawn_animation"));
      if (tag.hasUUID("owner")) {
         this.setOwnerUUID(tag.getUUID("owner"));
      }

      this.entityData.set(DATA_DELAY, tag.getInt("delay"));
      this.entityData.set(DATA_EVIL, tag.getBoolean("evil"));
      this.tickCount = tag.getInt("age");
   }

   public void baseTick() {
      if (this.firstTick && this.level() instanceof ServerLevel level) {
         level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
         level.getEntities(this, this.getBoundingBox(), entity1 -> IceSpikeEntity.canHurt(entity1, this)).forEach(entity -> {
            if (entity instanceof LivingEntity living) {
               living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
            }
         });
      }

      super.baseTick();
      if (this.level() instanceof ServerLevel level) {
         if ((Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) > 0) {
            this.getEntityData().set(DATA_HIT_ANIMATION_TIME, (Integer)this.getEntityData().get(DATA_HIT_ANIMATION_TIME) - 1);
         }

         if (this.tickCount >= this.getDelay() && !this.getHeld()) {
            if (this.tickCount == this.getDelay()) {
               this.playSound((SoundEvent)BossesRiseSounds.ICICLE_SPAWN.value(), 0.5F, 1.0F + this.random.nextFloat() * 0.25F);
               level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 10, 0.5, 0.5, 0.5, 0.0);
               level.getEntities(this, this.getBoundingBox(), entity1 -> IceSpikeEntity.canHurt(entity1, this)).forEach(entity -> {
                  entity.hurt(level.damageSources().freeze(), this.damage);
                  entity.setTicksFrozen(200);
               });
            }

            int spawnTime = (Integer)this.getEntityData().get(DATA_SPAWN_ANIM_TIME);
            this.getEntityData().set(DATA_SPAWN_ANIM_TIME, spawnTime + 1);
            if (spawnTime >= 400 + this.getDelay()) {
               this.discard();
               level.sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 5, 0.25, 0.25, 0.25, 0.0);
            }
         }
      }
   }

   public boolean canFreeze() {
      return false;
   }

   public boolean isPickable() {
      return true;
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   @Override
   public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
      return this.tickCount < this.getDelay() ? List.of(this.zone) : List.of();
   }
}
