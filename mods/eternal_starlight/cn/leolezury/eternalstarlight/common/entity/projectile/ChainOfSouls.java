package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.Grappling;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.GatheringTrailParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;

public class ChainOfSouls extends Projectile implements Grappling {
   private static final String TAG_REACHED_TARGET = "reached_target";
   private static final String TAG_LENGTH = "length";
   private static final String TAG_TIP_DIRECTION = "tip_direction";
   private static final String TAG_TARGET = "target";
   private static final String TAG_WEAPON = "weapon";
   private static final double SPEED = 5.0;
   public static final EntityDataAccessor<Boolean> REACHED_TARGET = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Float> LENGTH = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.FLOAT);
   public static final EntityDataAccessor<Direction> TIP_DIRECTION = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.DIRECTION);
   public static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(ChainOfSouls.class, EntityDataSerializers.INT);
   @Nullable
   private ItemStack firedFromWeapon;
   private int absorbSoulTicks;
   @Nullable
   private Entity target;
   @Nullable
   private UUID targetId;

   public Entity getTarget() {
      return this.level().isClientSide ? this.level().getEntity(this.getTargetId()) : this.target;
   }

   public void setTarget(Entity target) {
      this.targetId = target.getUUID();
      this.target = target;
   }

   public ChainOfSouls(EntityType<? extends ChainOfSouls> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
   }

   public ChainOfSouls(Level level, Player player, @Nullable ItemStack weapon) {
      this(ESEntities.CHAIN_OF_SOULS.get(), level);
      this.setOwner(player);
      this.setPos(player.getX(), player.getEyeY() - 0.1, player.getZ());
      this.setDeltaMovement(player.getViewVector(1.0F).scale(5.0));
      this.firedFromWeapon = weapon;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(REACHED_TARGET, false).define(LENGTH, 0.0F).define(TIP_DIRECTION, Direction.DOWN).define(TARGET_ID, -1);
   }

   public boolean shouldRenderAtSqrDistance(double d) {
      return true;
   }

   protected double getDefaultGravity() {
      return 0.05;
   }

   public boolean isOnFire() {
      return false;
   }

   public void tick() {
      super.tick();
      Player player = this.getPlayerOwner();
      if (!this.level().isClientSide) {
         if (this.target == null && this.targetId != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.targetId);
            if (entity != null) {
               this.target = entity;
            }

            if (this.target == null) {
               this.targetId = null;
            }
         }

         if (this.target == null) {
            this.absorbSoulTicks = 0;
         } else {
            this.setTargetId(this.target.getId());
            this.setDeltaMovement(Vec3.ZERO);
            if (!this.isValidTarget(this.target)) {
               this.target = null;
               this.targetId = null;
            } else {
               Vec3 targetPos = this.target.position().add(0.0, this.target.getBbHeight() / 2.0F, 0.0);
               this.setPos(targetPos);
               if (this.target instanceof LivingEntity && !(this.target instanceof ArmorStand) && this.level() instanceof ServerLevel serverLevel) {
                  Player playerOwner = this.getPlayerOwner();
                  if (playerOwner != null) {
                     float damage = (float)ESConfig.INSTANCE.itemsConfig.chainOfSouls.soulAbsorbDamage();
                     DamageSource damageSource = ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.SOUL_ABSORB, this, playerOwner);
                     if (this.getWeaponItem() != null) {
                        damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), this.target, damageSource, damage);
                     }

                     if (this.target.hurt(damageSource, damage)) {
                        EnchantmentHelper.doPostAttackEffects(serverLevel, this.target, damageSource);
                        playerOwner.heal((float)(damage * ESConfig.INSTANCE.itemsConfig.chainOfSouls.healPercentage()));
                        this.playSound(ESSoundEvents.CHAIN_OF_SOULS_ABSORB.get());

                        for (int i = 0; i < 7; i++) {
                           serverLevel.sendParticles(
                              ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER_OMINOUS,
                              this.target.getRandomX(1.0),
                              this.target.getRandomY(),
                              this.target.getRandomZ(1.0),
                              5,
                              0.0,
                              0.0,
                              0.0,
                              0.0
                           );
                        }

                        for (int i = 0; i < 2; i++) {
                           Vec3 randomPlayerPos = new Vec3(playerOwner.getRandomX(1.0), playerOwner.getRandomY(), playerOwner.getRandomZ(1.0));
                           Vec3 randomTargetPos = new Vec3(this.target.getRandomX(1.0), this.target.getRandomY(), this.target.getRandomZ(1.0));
                           ESPlatform.INSTANCE
                              .sendToTrackingClients(
                                 serverLevel,
                                 this,
                                 new ParticlePacket(
                                    GatheringTrailParticleOptions.SOUL_THIN,
                                    randomTargetPos.x,
                                    randomTargetPos.y,
                                    randomTargetPos.z,
                                    randomPlayerPos.x - randomTargetPos.x,
                                    randomPlayerPos.y - randomTargetPos.y,
                                    randomPlayerPos.z - randomTargetPos.z
                                 )
                              );
                        }
                     }

                     if (!this.target.getType().is(ESTags.EntityTypes.CHAIN_OF_SOULS_CANNOT_PULL)) {
                        Vec3 ownerPos = playerOwner.position().add(0.0, playerOwner.getBbHeight() / 2.0F, 0.0);
                        Vec3 posDiff = ownerPos.subtract(targetPos);
                        if (posDiff.length() > this.length() * 1.2F) {
                           double scale = 1.0 - Math.sqrt(Math.min(posDiff.lengthSqr(), 4096.0)) / 64.0;
                           this.target.addDeltaMovement(posDiff.normalize().scale(scale * scale));
                           this.target.hurtMarked = true;
                        }
                     }

                     this.absorbSoulTicks++;
                     if (this.absorbSoulTicks > 50) {
                        this.discard();
                     }
                  }
               }
            }
         }

         AtomicBoolean blockSupport = new AtomicBoolean(false);
         ESBlockUtil.getBlocksInBoundingBox(this.getBoundingBox().inflate(0.1, 0.05, 0.1).move(0.0, 0.05, 0.0))
            .forEach(
               pos -> blockSupport.set(
                  blockSupport.get()
                     || this.level()
                        .getBlockState(pos)
                        .getCollisionShape(this.level(), pos)
                        .toAabbs()
                        .stream()
                        .anyMatch(box -> box.move(pos).intersects(this.getBoundingBox()))
               )
            );
         if (this.reachedTarget() && this.target == null && !blockSupport.get()) {
            this.applyGravity();
         }
      }

      if (player != null && (this.level().isClientSide() || !this.shouldRetract(player))) {
         HitResult hitResult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
         if (hitResult.getType() != Type.MISS && this.target == null) {
            this.onHit(hitResult);
         }

         if (this.target == null) {
            this.setPos(hitResult.getLocation());
         }

         this.checkInsideBlocks();
      } else {
         this.discard();
      }
   }

   private boolean shouldRetract(Player player) {
      if (!player.isRemoved()
         && player.isAlive()
         && this.firedFromWeapon != null
         && (
            ItemStack.isSameItemSameComponents(player.getMainHandItem(), this.firedFromWeapon)
               || ItemStack.isSameItemSameComponents(player.getOffhandItem(), this.firedFromWeapon)
         )
         && !(this.distanceToSqr(player) > this.getMaxRange() * this.getMaxRange())) {
         return false;
      }

      this.discard();
      return true;
   }

   private double getMaxRange() {
      return ESConfig.INSTANCE.itemsConfig.chainOfSouls.maxRange();
   }

   public boolean isValidTarget(Entity entity) {
      return entity instanceof LivingEntity && entity.isAlive() && entity.distanceToSqr(this) <= this.getMaxRange() * this.getMaxRange();
   }

   protected boolean canHitEntity(Entity entity) {
      return entity != this.getOwner();
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
      if (!this.level().isClientSide
         && this.target == null
         && this.isValidTarget(hitResult.getEntity())
         && ESEntityUtil.shouldHarm(this.getPlayerOwner(), hitResult.getEntity())) {
         this.setTarget(hitResult.getEntity());
         Player player = this.getPlayerOwner();
         if (player != null && !this.reachedTarget()) {
            double d = player.getEyePosition().subtract(hitResult.getLocation()).length();
            this.setLength(Math.max((float)d * 0.5F - 1.0F, 1.5F));
         }

         this.setReachedTarget(true);
      }
   }

   protected void onHitBlock(BlockHitResult hitResult) {
      super.onHitBlock(hitResult);
      this.setDeltaMovement(Vec3.ZERO);
      if (!this.level().isClientSide) {
         Player player = this.getPlayerOwner();
         if (player != null && !this.reachedTarget()) {
            double d = player.getEyePosition().subtract(hitResult.getLocation()).length();
            this.setLength(Math.max((float)d * 0.5F - 3.0F, 1.5F));
         }

         this.setTipDirection(hitResult.getDirection().getOpposite());
         this.setReachedTarget(true);
      }
   }

   @Nullable
   public ItemStack getWeaponItem() {
      return this.firedFromWeapon;
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("reached_target", this.reachedTarget());
      compoundTag.putFloat("length", this.length());
      compoundTag.putInt("tip_direction", this.getTipDirection().get3DDataValue());
      if (this.target != null) {
         compoundTag.putUUID("target", this.target.getUUID());
      }

      if (this.firedFromWeapon != null && !this.firedFromWeapon.isEmpty()) {
         compoundTag.put("weapon", this.firedFromWeapon.save(this.registryAccess(), new CompoundTag()));
      }
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setReachedTarget(compoundTag.getBoolean("reached_target"));
      this.setLength(compoundTag.getFloat("length"));
      this.setTipDirection(Direction.from3DDataValue(compoundTag.getInt("tip_direction")));
      if (compoundTag.hasUUID("target")) {
         this.targetId = compoundTag.getUUID("target");
      }

      if (compoundTag.contains("weapon", 10)) {
         this.firedFromWeapon = (ItemStack)ItemStack.parse(this.registryAccess(), compoundTag.getCompound("weapon")).orElse(null);
      } else {
         this.firedFromWeapon = null;
      }
   }

   private void setReachedTarget(boolean reachedTarget) {
      this.getEntityData().set(REACHED_TARGET, reachedTarget);
   }

   private void setLength(float length) {
      this.getEntityData().set(LENGTH, length);
   }

   private void setTipDirection(Direction direction) {
      this.getEntityData().set(TIP_DIRECTION, direction);
   }

   private void setTargetId(int targetId) {
      this.getEntityData().set(TARGET_ID, targetId);
   }

   @Override
   public boolean reachedTarget() {
      return (Boolean)this.getEntityData().get(REACHED_TARGET);
   }

   @Override
   public boolean shouldPull() {
      return this.getTarget() == null;
   }

   @Override
   public float length() {
      return (Float)this.getEntityData().get(LENGTH);
   }

   public Direction getTipDirection() {
      return (Direction)this.getEntityData().get(TIP_DIRECTION);
   }

   public int getTargetId() {
      return (Integer)this.getEntityData().get(TARGET_ID);
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   public void remove(RemovalReason removalReason) {
      this.updateOwnerInfo(null);
      super.remove(removalReason);
   }

   public void setOwner(@Nullable Entity entity) {
      super.setOwner(entity);
      this.updateOwnerInfo(this);
   }

   private void updateOwnerInfo(@Nullable ChainOfSouls chain) {
      Player player = this.getPlayerOwner();
      if (player != null) {
         ESDataAttachments.GRAPPLING.setData(player, chain == null ? -1 : chain.getId());
      }
   }

   @Nullable
   public Player getPlayerOwner() {
      Entity entity = this.getOwner();
      return entity instanceof Player ? (Player)entity : null;
   }

   public boolean canChangeDimensions(Level level, Level level2) {
      return false;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      if (this.getPlayerOwner() == null) {
         this.kill();
      }
   }
}
