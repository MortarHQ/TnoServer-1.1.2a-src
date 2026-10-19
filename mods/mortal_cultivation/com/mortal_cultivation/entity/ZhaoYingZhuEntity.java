package com.mortal_cultivation.entity;

import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import com.mortal_cultivation.item.ZhaoYingZhuItem;
import com.mortal_cultivation.registry.item.CultivationItems;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class ZhaoYingZhuEntity extends Entity implements GeoEntity {
   private static final String OWNER_TAG = "Owner";
   private static final String TARGET_TAG = "Target";
   private static final String CARRIED_ITEM_TAG = "CarriedItem";
   private static final String STAGE_TAG = "Stage";
   private static final String FILLED_TAG = "Filled";
   private static final String ABSORPTION_START_TIME_TAG = "AbsorptionStartGameTime";
   private static final String WAIT_TICKS_TAG = "WaitTicks";
   private static final int APPROACHING = 0;
   private static final int ABSORBING = 1;
   private static final int WAITING_FOR_SHATTER = 2;
   private static final int RETURNING = 3;
   private static final int MAX_OWNER_WAIT_TICKS = 6000;
   private static final double APPROACH_SPEED = 0.28;
   private static final double RETURN_SPEED = 0.45;
   private static final double DIRECT_RETURN_DISTANCE = 24.0;
   private static final EntityDataAccessor<Integer> DATA_STAGE = SynchedEntityData.defineId(ZhaoYingZhuEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_FILLED = SynchedEntityData.defineId(ZhaoYingZhuEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Long> DATA_ABSORPTION_START_TIME = SynchedEntityData.defineId(ZhaoYingZhuEntity.class, EntityDataSerializers.LONG);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   @Nullable
   private UUID ownerUuid;
   private BlockPos targetPos = BlockPos.ZERO;
   private ItemStack carriedStack = ItemStack.EMPTY;
   private int ownerWaitTicks;
   private boolean itemHandled;

   public ZhaoYingZhuEntity(EntityType<? extends ZhaoYingZhuEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.noCulling = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_STAGE, 0);
      builder.define(DATA_FILLED, false);
      builder.define(DATA_ABSORPTION_START_TIME, 0L);
   }

   public void configure(Player player, BlockPos targetPos, ItemStack stack) {
      this.ownerUuid = player.getUUID();
      this.targetPos = targetPos.immutable();
      this.carriedStack = stack.copy();
      this.entityData.set(DATA_STAGE, 0);
      this.entityData.set(DATA_FILLED, ZhaoYingZhuItem.hasStoredRunes(stack));
   }

   public void tick() {
      super.tick();
      this.setNoGravity(true);
      if (!this.level().isClientSide) {
         switch (this.entityData.get(DATA_STAGE)) {
            case 0:
               this.tickApproaching();
               break;
            case 1:
               this.tickAbsorbing();
               break;
            case 2:
               this.tickWaitingForShatter();
               break;
            case 3:
               this.tickReturning();
               break;
            default:
               this.startReturning(false);
         }
      }
   }

   private void tickApproaching() {
      AncientSteleBlockEntity stele = this.getTargetStele();
      if (stele != null && stele.isReservedBy(this.getUUID())) {
         Vec3 hoverPos = this.getHoverPosition();
         if (this.moveToward(hoverPos, 0.28)) {
            this.setPos(hoverPos);
            this.setDeltaMovement(Vec3.ZERO);
            if (stele.beginAbsorption(this.getUUID())) {
               this.entityData.set(DATA_ABSORPTION_START_TIME, this.level().getGameTime());
               this.entityData.set(DATA_STAGE, 1);
            } else {
               this.startReturning(false);
            }
         }
      } else {
         this.startReturning(false);
      }
   }

   private void tickAbsorbing() {
      this.setPos(this.getHoverPosition());
      this.setDeltaMovement(Vec3.ZERO);
      AncientSteleBlockEntity stele = this.getTargetStele();
      if (stele != null && stele.isOwnedByOrb(this.getUUID())) {
         if (stele.isShattering()) {
            this.markFilled();
            this.ownerWaitTicks = 0;
            this.entityData.set(DATA_STAGE, 2);
         }
      } else {
         this.startReturning(false);
      }
   }

   private void tickWaitingForShatter() {
      this.setPos(this.getHoverPosition());
      this.setDeltaMovement(Vec3.ZERO);
      if (this.getTargetStele() == null) {
         this.ownerWaitTicks = 0;
         this.entityData.set(DATA_STAGE, 3);
      }
   }

   private void tickReturning() {
      if (this.level() instanceof ServerLevel serverLevel) {
         ServerPlayer owner = this.ownerUuid == null ? null : serverLevel.getServer().getPlayerList().getPlayer(this.ownerUuid);
         if (owner == null) {
            this.setDeltaMovement(Vec3.ZERO);
            if (++this.ownerWaitTicks >= 6000) {
               this.dropCarriedItem();
            }
         } else {
            this.ownerWaitTicks = 0;
            if (owner.level() == this.level() && !(this.distanceToSqr(owner) > 576.0)) {
               Vec3 destination = getMainHandPosition(owner);
               if (this.moveToward(destination, 0.45)) {
                  this.giveCarriedItem(owner);
               }
            } else {
               this.giveCarriedItem(owner);
            }
         }
      }
   }

   private static Vec3 getMainHandPosition(Player player) {
      double yaw = Math.toRadians(player.getYRot());
      Vec3 right = new Vec3(-Math.cos(yaw), 0.0, -Math.sin(yaw));
      double handSide = player.getMainArm() == HumanoidArm.RIGHT ? 0.32 : -0.32;
      return player.getEyePosition().add(player.getLookAngle().scale(0.45)).add(right.scale(handSide)).add(0.0, -0.45, 0.0);
   }

   private boolean moveToward(Vec3 destination, double speed) {
      Vec3 offset = destination.subtract(this.position());
      double distance = offset.length();
      if (!(distance <= speed) && !(distance < 1.0E-4)) {
         Vec3 movement = offset.scale(speed / distance);
         this.setDeltaMovement(movement);
         this.move(MoverType.SELF, movement);
         this.hasImpulse = true;
         return false;
      } else {
         this.setPos(destination);
         this.setDeltaMovement(Vec3.ZERO);
         return true;
      }
   }

   private Vec3 getHoverPosition() {
      return Vec3.atBottomCenterOf(this.targetPos).add(0.0, 2.1, 0.0);
   }

   @Nullable
   private AncientSteleBlockEntity getTargetStele() {
      return this.level().getBlockEntity(this.targetPos) instanceof AncientSteleBlockEntity stele ? stele : null;
   }

   private void startReturning(boolean filled) {
      AncientSteleBlockEntity stele = this.getTargetStele();
      if (stele != null && !stele.isShattering()) {
         stele.cancelOrb(this.getUUID());
      }

      if (filled) {
         this.markFilled();
      }

      this.ownerWaitTicks = 0;
      this.entityData.set(DATA_STAGE, 3);
   }

   private void markFilled() {
      this.entityData.set(DATA_FILLED, true);
      if (this.carriedStack.isEmpty()) {
         this.carriedStack = new ItemStack((ItemLike)CultivationItems.ZHAO_YING_ZHU.get());
      }

      ZhaoYingZhuItem.setStoredRunes(this.carriedStack);
   }

   public float getRuneAbsorptionProgress(float partialTick) {
      if ((Boolean)this.entityData.get(DATA_FILLED)) {
         return 1.0F;
      }

      if ((Integer)this.entityData.get(DATA_STAGE) != 1) {
         return 0.0F;
      }

      long startTime = (Long)this.entityData.get(DATA_ABSORPTION_START_TIME);
      double elapsedTicks = (float)(this.level().getGameTime() - startTime) + partialTick;
      return Mth.clamp((float)(elapsedTicks / 100.0), 0.0F, 1.0F);
   }

   public boolean isFilled() {
      return (Boolean)this.entityData.get(DATA_FILLED);
   }

   private void giveCarriedItem(ServerPlayer player) {
      ItemStack stack = this.takeCarriedStack();
      this.itemHandled = true;
      if (!stack.isEmpty()) {
         player.getInventory().add(stack);
         if (!stack.isEmpty()) {
            player.drop(stack, false);
         }
      }

      this.discard();
   }

   private void dropCarriedItem() {
      ItemStack stack = this.takeCarriedStack();
      this.itemHandled = true;
      if (!stack.isEmpty()) {
         this.spawnAtLocation(stack, 0.1F);
      }

      this.discard();
   }

   private ItemStack takeCarriedStack() {
      ItemStack stack = this.carriedStack;
      this.carriedStack = ItemStack.EMPTY;
      if (stack.isEmpty()) {
         stack = new ItemStack((ItemLike)CultivationItems.ZHAO_YING_ZHU.get());
         if ((Boolean)this.entityData.get(DATA_FILLED)) {
            ZhaoYingZhuItem.setStoredRunes(stack);
         }
      }

      return stack;
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.ownerUuid != null) {
         tag.putUUID("Owner", this.ownerUuid);
      }

      tag.putLong("Target", this.targetPos.asLong());
      if (!this.carriedStack.isEmpty()) {
         tag.put("CarriedItem", this.carriedStack.save(this.registryAccess()));
      }

      tag.putInt("Stage", (Integer)this.entityData.get(DATA_STAGE));
      tag.putBoolean("Filled", (Boolean)this.entityData.get(DATA_FILLED));
      tag.putLong("AbsorptionStartGameTime", (Long)this.entityData.get(DATA_ABSORPTION_START_TIME));
      tag.putInt("WaitTicks", this.ownerWaitTicks);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.hasUUID("Owner")) {
         this.ownerUuid = tag.getUUID("Owner");
      }

      if (tag.contains("Target", 4)) {
         this.targetPos = BlockPos.of(tag.getLong("Target"));
      }

      if (tag.contains("CarriedItem", 10)) {
         this.carriedStack = ItemStack.parse(this.registryAccess(), tag.getCompound("CarriedItem")).orElse(ItemStack.EMPTY);
      }

      this.entityData.set(DATA_STAGE, Mth.clamp(tag.getInt("Stage"), 0, 3));
      this.entityData.set(DATA_FILLED, tag.getBoolean("Filled"));
      this.entityData.set(DATA_ABSORPTION_START_TIME, tag.getLong("AbsorptionStartGameTime"));
      this.ownerWaitTicks = Math.max(0, tag.getInt("WaitTicks"));
      this.noPhysics = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return false;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isAttackable() {
      return false;
   }

   public void remove(RemovalReason reason) {
      if (!this.level().isClientSide && reason.shouldDestroy() && !this.itemHandled) {
         AncientSteleBlockEntity stele = this.getTargetStele();
         if (stele != null) {
            if (stele.isShattering()) {
               this.markFilled();
            } else {
               stele.cancelOrb(this.getUUID());
            }
         }

         ItemStack stack = this.takeCarriedStack();
         this.itemHandled = true;
         if (!stack.isEmpty()) {
            this.spawnAtLocation(stack, 0.1F);
         }
      }

      super.remove(reason);
   }
}
