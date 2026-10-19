package com.mortal_cultivation.entity;

import com.mortal_cultivation.item.TenThousandSoulBannerItem;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.Random;
import java.util.UUID;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TenThousandSoulBannerEntity extends Entity implements GeoEntity {
   private static final RawAnimation IDLE_ANIMATION = RawAnimation.begin().thenLoop("idle");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private static final EntityDataAccessor<Integer> DATA_SOUL_COUNT = SynchedEntityData.defineId(TenThousandSoulBannerEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DATA_SOUL_CAPACITY = SynchedEntityData.defineId(
      TenThousandSoulBannerEntity.class, EntityDataSerializers.INT
   );
   private static final double DAMAGE_RADIUS = 16.0;
   private static final float DAMAGE_AMOUNT = 10.0F;
   private static final int DEFAULT_SOUL_CAPACITY = 500;
   private static final int MAX_SOUL_CAPACITY = 10000;
   private static final int CAPACITY_UPGRADE_AMOUNT = 500;
   private static final Random random = new Random();
   private int damageTimer = 0;
   private UUID ownerUuid;

   public TenThousandSoulBannerEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
      this.noCulling = true;
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_SOUL_COUNT, 0);
      builder.define(DATA_SOUL_CAPACITY, 500);
   }

   public boolean isPickable() {
      return true;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isAttackable() {
      return false;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return false;
   }

   public boolean canCollideWith(Entity entity) {
      return false;
   }

   public void push(Entity entity) {
   }

   public void push(double x, double y, double z) {
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private PlayState predicate(AnimationState<TenThousandSoulBannerEntity> event) {
      event.getController().setAnimation(IDLE_ANIMATION);
      return PlayState.CONTINUE;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (this.level().isClientSide) {
         return InteractionResult.SUCCESS;
      }

      ItemStack itemInHand = player.getItemInHand(hand);
      if (itemInHand.is(Items.NETHER_STAR)) {
         if (this.upgradeSoulCapacity()) {
            if (!player.isCreative()) {
               itemInHand.shrink(1);
            }

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.5F);
            player.sendSystemMessage(
               Component.translatable("mortal_cultivation.entity.ten_thousand_soul_banner.capacity_upgraded", new Object[]{this.getSoulCapacity()})
            );
            return InteractionResult.SUCCESS;
         } else {
            player.sendSystemMessage(Component.translatable("mortal_cultivation.entity.ten_thousand_soul_banner.max_capacity", new Object[]{10000}));
            return InteractionResult.FAIL;
         }
      } else if (player.isShiftKeyDown() && itemInHand.isEmpty()) {
         ItemStack bannerItem = new ItemStack((ItemLike)CultivationItems.TEN_THOUSAND_SOUL_BANNER.get());
         TenThousandSoulBannerItem.setSoulCount(bannerItem, this.getSoulCount());
         TenThousandSoulBannerItem.setSoulCapacity(bannerItem, this.getSoulCapacity());
         player.setItemInHand(hand, bannerItem);
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 1.0F, 1.0F);
         this.discard();
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public void tick() {
      super.tick();
      this.setDeltaMovement(Vec3.ZERO);
      if (!this.level().isClientSide && this.level() instanceof ServerLevel) {
         this.handleDamageLogic();
         if (this.getSoulCount() > 0) {
            this.createSoulParticlesAroundBanner((ServerLevel)this.level());
         }
      }
   }

   private void createSoulParticlesAroundBanner(ServerLevel world) {
      ResourceLocation soulParticleId = ResourceLocation.fromNamespaceAndPath("tensura", "soul");
      ParticleOptions soulParticle = (ParticleOptions)BuiltInRegistries.PARTICLE_TYPE.get(soulParticleId);
      if (soulParticle != null && this.tickCount % 5 == 0) {
         for (int i = 0; i < 3; i++) {
            double phi = random.nextDouble() * Math.PI * 2.0;
            double theta = random.nextDouble() * Math.PI;
            double radius = 1.0 + random.nextDouble() * 2.0;
            double x = this.getX() + radius * Math.sin(theta) * Math.cos(phi);
            double y = this.getY() + 1.0 + radius * Math.cos(theta);
            double z = this.getZ() + radius * Math.sin(theta) * Math.sin(phi);
            double speedX = (this.getX() - x) * 0.02;
            double speedY = (this.getY() + 1.0 - y) * 0.02;
            double speedZ = (this.getZ() - z) * 0.02;
            world.sendParticles(soulParticle, x, y, z, 0, speedX, speedY, speedZ, 0.03);
         }
      }
   }

   private void handleDamageLogic() {
      this.damageTimer++;
      if (this.damageTimer >= 20) {
         this.damageTimer = 0;
         AABB damageArea = new AABB(this.getX() - 16.0, this.getY() - 16.0, this.getZ() - 16.0, this.getX() + 16.0, this.getY() + 16.0, this.getZ() + 16.0);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, damageArea)) {
            if (!(entity instanceof Player)) {
               double distance = this.distanceTo(entity);
               if (distance <= 16.0) {
                  DamageSource damageSource = this.level().damageSources().magic();
                  boolean wasAlive = entity.isAlive();
                  if (entity.hurt(damageSource, 10.0F) && wasAlive && !entity.isAlive()) {
                     this.collectSoul();
                  }
               }
            }
         }
      }
   }

   private void collectSoul() {
      int currentSouls = this.getSoulCount();
      int capacity = this.getSoulCapacity();
      if (currentSouls < capacity) {
         this.setSoulCount(currentSouls + 1);
      }
   }

   public int getSoulCount() {
      return (Integer)this.entityData.get(DATA_SOUL_COUNT);
   }

   public void setSoulCount(int count) {
      this.entityData.set(DATA_SOUL_COUNT, Math.min(count, this.getSoulCapacity()));
   }

   public int getSoulCapacity() {
      return (Integer)this.entityData.get(DATA_SOUL_CAPACITY);
   }

   public void setSoulCapacity(int capacity) {
      this.entityData.set(DATA_SOUL_CAPACITY, Math.min(capacity, 10000));
   }

   public boolean upgradeSoulCapacity() {
      int currentCapacity = this.getSoulCapacity();
      if (currentCapacity < 10000) {
         this.setSoulCapacity(currentCapacity + 500);
         return true;
      } else {
         return false;
      }
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("SoulCount", this.getSoulCount());
      compound.putInt("SoulCapacity", this.getSoulCapacity());
      if (this.ownerUuid != null) {
         compound.putUUID("OwnerUUID", this.ownerUuid);
      }
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("SoulCount")) {
         this.setSoulCount(compound.getInt("SoulCount"));
      }

      if (compound.contains("SoulCapacity")) {
         this.setSoulCapacity(compound.getInt("SoulCapacity"));
      }

      if (compound.hasUUID("OwnerUUID")) {
         this.ownerUuid = compound.getUUID("OwnerUUID");
      }
   }

   public UUID getOwnerUuid() {
      return this.ownerUuid;
   }

   public void setOwnerUuid(UUID ownerUuid) {
      this.ownerUuid = ownerUuid;
   }

   public boolean isOwnedBy(Entity entity) {
      return entity != null && this.ownerUuid != null && this.ownerUuid.equals(entity.getUUID());
   }
}
