package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.item.combat.WhipItem;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.Nullable;

public abstract class Whip extends Entity {
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   private static final String TAG_OWNER = "owner";
   private static final String TAG_WEAPON = "weapon";
   private static final String TAG_DAMAGE_SCALE = "damage_scale";
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> FOIL = SynchedEntityData.defineId(Whip.class, EntityDataSerializers.BOOLEAN);
   private float damageScale = 1.0F;
   @Nullable
   private ItemStack firedFromWeapon;
   @Nullable
   private Entity owner;
   @Nullable
   private UUID ownerId;
   private int oldAnimationTicks;
   private int animationTicks;

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   private void setOwnerId(int ownerId) {
      this.getEntityData().set(OWNER_ID, ownerId);
   }

   public int getOwnerId() {
      return (Integer)this.getEntityData().get(OWNER_ID);
   }

   public Entity getOwner() {
      return this.level().isClientSide ? this.level().getEntity(this.getOwnerId()) : this.owner;
   }

   public void setOwner(Entity owner) {
      if (owner != null) {
         this.ownerId = owner.getUUID();
      } else {
         this.ownerId = null;
      }

      this.owner = owner;
      this.updateOwnerInfo(this);
   }

   public float getAnimationTicks(float partialTicks) {
      return Mth.lerp(partialTicks, this.oldAnimationTicks, this.animationTicks);
   }

   public Whip(EntityType<? extends Whip> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
   }

   public Whip(EntityType<? extends Whip> entityType, Level level, Player player, @Nullable ItemStack weapon, float damageScale) {
      this(entityType, level);
      this.setOwner(player);
      this.setPos(player.getEyePosition());
      this.firedFromWeapon = weapon;
      if (this.firedFromWeapon != null) {
         this.entityData.set(FOIL, this.firedFromWeapon.hasFoil());
      }

      this.damageScale = damageScale;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(SPAWNED_TICKS, 0).define(OWNER_ID, -1).define(FOIL, false);
   }

   public boolean shouldRenderAtSqrDistance(double d) {
      return true;
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   public boolean isFoil() {
      return (Boolean)this.entityData.get(FOIL);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.owner == null && this.ownerId != null && this.level() instanceof ServerLevel serverLevel) {
            Entity entity = serverLevel.getEntity(this.ownerId);
            if (entity != null) {
               this.owner = entity;
            }

            if (this.owner == null) {
               this.ownerId = null;
            }
         }

         this.setOwnerId(this.owner != null ? this.owner.getId() : -1);
         if (this.getSpawnedTicks() >= this.getLifespan()) {
            this.discard();
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
         Player player = this.getPlayerOwner();
         if (player == null
            || player.isRemoved()
            || !player.isAlive()
            || this.firedFromWeapon == null
            || !ItemStack.isSameItemSameComponents(player.getMainHandItem(), this.firedFromWeapon)) {
            this.discard();
         }

         if (player != null) {
            this.setPos(player.getEyePosition());
            if (this.getSpawnedTicks() == this.getLifespan() / 2) {
               this.playSound(ESSoundEvents.WHIP_CRACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
               Vec3 endPos = ESMathUtil.rotationToPosition(
                  player.getEyePosition(),
                  this.getWhipRange((float)player.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE)),
                  -this.owner.getXRot(),
                  this.owner.getYHeadRot() + 90.0F
               );
               BlockHitResult hitResult = this.level()
                  .clip(new ClipContext(player.getEyePosition(), endPos, Block.COLLIDER, Fluid.NONE, CollisionContext.of(this)));
               if (hitResult.getType() != Type.MISS) {
                  endPos = hitResult.getLocation();
               }

               List<Entity> entities = this.level().getEntitiesOfClass(Entity.class, new AABB(player.getEyePosition(), endPos).inflate(1.0));
               entities.sort(Comparator.comparingDouble(e -> e.distanceToSqr(this)));

               for (Entity pickedEntity : entities) {
                  LivingEntity entity = null;
                  if (pickedEntity instanceof LivingEntity living) {
                     entity = living;
                  }

                  if (ESPlatform.INSTANCE.getPartEntityParent(pickedEntity) instanceof LivingEntity living) {
                     entity = living;
                  }

                  if (entity != null) {
                     AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius() + 1.5F);
                     if (ESEntityUtil.shouldHarm(player, entity)
                        && entity.isPickable()
                        && (aabb.contains(player.getEyePosition()) || aabb.clip(player.getEyePosition(), endPos).isPresent())) {
                        DamageSource damageSource = this.damageSources().playerAttack(player);
                        float damage = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE) * this.damageScale;
                        float knockback = player.getKnockback(entity, damageSource);
                        if (this.level() instanceof ServerLevel serverLevel && this.getWeaponItem() != null) {
                           damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
                           knockback = EnchantmentHelper.modifyKnockback(serverLevel, this.getWeaponItem(), player, damageSource, knockback);
                        }

                        if (entity.hurt(damageSource, damage)) {
                           if (this.getWeaponItem() != null && this.getWeaponItem().getItem() instanceof WhipItem whipItem) {
                              whipItem.doPostHurtEffects(this, entity);
                           }

                           if (this.level() instanceof ServerLevel serverLevel) {
                              EnchantmentHelper.doPostAttackEffectsWithItemSource(serverLevel, entity, damageSource, this.getWeaponItem());
                           }

                           entity.knockback(
                              knockback * 0.5F, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0))
                           );
                        }
                     }
                  }
               }
            }
         }
      } else {
         int currentOwnerId = this.owner != null ? this.owner.getId() : -1;
         if (currentOwnerId != this.getOwnerId()) {
            this.setOwner(this.level().getEntity(this.getOwnerId()));
         }

         this.oldAnimationTicks = this.animationTicks;
         if (this.animationTicks == 0) {
            this.animationTicks = this.getSpawnedTicks();
         } else {
            this.animationTicks++;
         }
      }
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         this.discard();
         return true;
      } else {
         return false;
      }
   }

   public abstract int getLifespan();

   public abstract float getWhipRange(float var1);

   @Nullable
   public ItemStack getWeaponItem() {
      return this.firedFromWeapon;
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      compoundTag.putInt("spawned_ticks", this.getSpawnedTicks());
      if (this.ownerId != null) {
         compoundTag.putUUID("owner", this.ownerId);
      }

      if (this.firedFromWeapon != null && !this.firedFromWeapon.isEmpty()) {
         compoundTag.put("weapon", this.firedFromWeapon.save(this.registryAccess(), new CompoundTag()));
      }

      compoundTag.putFloat("damage_scale", this.damageScale);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      this.setSpawnedTicks(compoundTag.getInt("spawned_ticks"));
      if (compoundTag.hasUUID("owner")) {
         this.ownerId = compoundTag.getUUID("owner");
      }

      if (compoundTag.contains("weapon", 10)) {
         this.firedFromWeapon = (ItemStack)ItemStack.parse(this.registryAccess(), compoundTag.getCompound("weapon")).orElse(null);
         if (this.firedFromWeapon != null) {
            this.entityData.set(FOIL, this.firedFromWeapon.hasFoil());
         }
      } else {
         this.firedFromWeapon = null;
      }

      if (compoundTag.contains("damage_scale", 99)) {
         this.damageScale = compoundTag.getFloat("damage_scale");
      }
   }

   public boolean isOnFire() {
      return false;
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   public void remove(RemovalReason removalReason) {
      this.updateOwnerInfo(null);
      super.remove(removalReason);
   }

   private void updateOwnerInfo(@Nullable Whip whip) {
      Player player = this.getPlayerOwner();
      if (player != null) {
         ESDataAttachments.WHIP.setData(player, whip == null ? -1 : whip.getId());
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
}
