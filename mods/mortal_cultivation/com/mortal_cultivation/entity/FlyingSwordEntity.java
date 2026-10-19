package com.mortal_cultivation.entity;

import com.mortal_cultivation.flight.FlyingSwordDeploymentData;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class FlyingSwordEntity extends Entity {
   private static final String SWORD_TAG = "Sword";
   private static final String OWNER_TAG = "Owner";
   private static final String DEPLOYMENT_TAG = "DeploymentId";
   private static final String RETURNING_TAG = "ReturningToOwner";
   private static final double CRUISE_SPEED = 0.85;
   private static final double SPRINT_SPEED = 1.3;
   private static final double RETURN_SPEED_MULTIPLIER = 1.7;
   private static final double RETURN_MAX_SPEED = 1.4449999999999998;
   private static final double RETURN_MIN_SPEED = 0.25;
   private static final double RETURN_ARRIVAL_DISTANCE = 0.55;
   private static final double CLIENT_LERP_TELEPORT_DISTANCE_SQR = 64.0;
   private static final double NORMAL_COLLISION_LENGTH = 1.55;
   private static final double NORMAL_COLLISION_WIDTH = 0.46;
   private static final double OVERSIZED_COLLISION_LENGTH = 3.0;
   private static final double OVERSIZED_COLLISION_WIDTH = 0.88;
   private static final double COLLISION_HEIGHT = 0.12;
   private static final TagKey<Item> OVERSIZED_FLYING_SWORDS = TagKey.create(
      Registries.ITEM, ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "oversized_flying_swords")
   );
   private static final EntityDataAccessor<ItemStack> DATA_SWORD = SynchedEntityData.defineId(FlyingSwordEntity.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER = SynchedEntityData.defineId(FlyingSwordEntity.class, EntityDataSerializers.OPTIONAL_UUID);
   private static final EntityDataAccessor<Boolean> DATA_RETURNING = SynchedEntityData.defineId(FlyingSwordEntity.class, EntityDataSerializers.BOOLEAN);
   @Nullable
   private UUID deploymentId;
   private float localStrafeInput;
   private float localForwardInput;
   private float localVerticalInput;
   @Nullable
   private ServerPlayer pendingDismountSyncPlayer;
   private int pendingDismountSyncTicks;
   private boolean awaitingDismountPositionSync;
   private int clientLerpSteps;
   private double clientLerpX;
   private double clientLerpY;
   private double clientLerpZ;
   private double clientLerpYRot;
   private double clientLerpXRot;

   public FlyingSwordEntity(EntityType<? extends FlyingSwordEntity> entityType, Level level) {
      super(entityType, level);
      this.blocksBuilding = true;
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_SWORD, ItemStack.EMPTY);
      builder.define(DATA_OWNER, Optional.empty());
      builder.define(DATA_RETURNING, false);
   }

   public ItemStack getSwordStack() {
      return (ItemStack)this.entityData.get(DATA_SWORD);
   }

   public void setSwordStack(ItemStack stack) {
      this.entityData.set(DATA_SWORD, stack.copy());
   }

   public boolean isOversizedSword() {
      return this.getSwordStack().is(OVERSIZED_FLYING_SWORDS);
   }

   public AABB getSwordBoundingBoxAt(Vec3 position) {
      double length = this.isOversizedSword() ? 3.0 : 1.55;
      double width = this.isOversizedSword() ? 0.88 : 0.46;
      double halfLength = length * 0.5;
      double halfWidth = width * 0.5;
      double yaw = this.getYRot() * (float) (Math.PI / 180.0);
      double forwardX = -Mth.sin((float)yaw);
      double forwardZ = Mth.cos((float)yaw);
      double halfX = Math.abs(forwardX) * halfLength + Math.abs(forwardZ) * halfWidth;
      double halfZ = Math.abs(forwardZ) * halfLength + Math.abs(forwardX) * halfWidth;
      return new AABB(position.x - halfX, position.y, position.z - halfZ, position.x + halfX, position.y + 0.12, position.z + halfZ);
   }

   protected AABB makeBoundingBox() {
      return this.getSwordBoundingBoxAt(this.position());
   }

   public void setYRot(float yRot) {
      super.setYRot(yRot);
      if (this.entityData != null) {
         this.setBoundingBox(this.makeBoundingBox());
      }
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (DATA_SWORD.equals(key)) {
         this.setBoundingBox(this.makeBoundingBox());
      }
   }

   public ItemStack takeSwordStack() {
      ItemStack stack = this.getSwordStack().copy();
      this.entityData.set(DATA_SWORD, ItemStack.EMPTY);
      return stack;
   }

   public Optional<UUID> getOwnerUUID() {
      return (Optional<UUID>)this.entityData.get(DATA_OWNER);
   }

   public void setOwnerUUID(UUID ownerUUID) {
      this.entityData.set(DATA_OWNER, Optional.of(ownerUUID));
   }

   public boolean isOwnedBy(Entity entity) {
      return this.getOwnerUUID().filter(entity.getUUID()::equals).isPresent();
   }

   public Optional<UUID> getDeploymentId() {
      return Optional.ofNullable(this.deploymentId);
   }

   public UUID getOrCreateDeploymentId() {
      if (this.deploymentId == null) {
         this.deploymentId = UUID.randomUUID();
      }

      return this.deploymentId;
   }

   public void setDeploymentId(UUID deploymentId) {
      this.deploymentId = deploymentId;
   }

   public boolean isReturningToOwner() {
      return (Boolean)this.entityData.get(DATA_RETURNING);
   }

   public void startReturningToOwner(ServerPlayer player) {
      if (!this.level().isClientSide && this.isOwnedBy(player)) {
         this.setLocalControlInput(0.0F, 0.0F, 0.0F);
         this.entityData.set(DATA_RETURNING, true);
         this.noPhysics = true;
         this.hasImpulse = true;
      }
   }

   public void discardWithoutDroppingSword() {
      if (!this.level().isClientSide) {
         this.takeSwordStack();
         this.ejectPassengers();
         this.discard();
      }
   }

   public void setLocalControlInput(float strafe, float forward, float vertical) {
      this.localStrafeInput = Mth.clamp(strafe, -1.0F, 1.0F);
      this.localForwardInput = Mth.clamp(forward, -1.0F, 1.0F);
      this.localVerticalInput = Mth.clamp(vertical, -1.0F, 1.0F);
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (!this.isOwnedBy(player)) {
         return InteractionResult.PASS;
      }

      if (player.isSecondaryUseActive()) {
         if (!this.level().isClientSide) {
            this.recallTo(player);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else if (!this.isVehicle()) {
         if (!this.level().isClientSide) {
            this.stopReturningToOwner();
            player.startRiding(this);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   protected boolean canAddPassenger(Entity passenger) {
      return this.getPassengers().isEmpty() && passenger instanceof Player && this.isOwnedBy(passenger);
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return this.getFirstPassenger() instanceof Player player && this.isOwnedBy(player) ? player : null;
   }

   protected Vec3 getPassengerAttachmentPoint(Entity passenger, EntityDimensions dimensions, float partialTick) {
      Vec3 passengerVehicleAttachment = passenger.getVehicleAttachmentPoint(this);
      return passengerVehicleAttachment.add(0.0, dimensions.height() + 0.02, 0.0);
   }

   protected void positionRider(Entity passenger, MoveFunction moveFunction) {
      super.positionRider(passenger, moveFunction);
      passenger.fallDistance = 0.0F;
   }

   public void tick() {
      super.tick();
      this.setNoGravity(true);
      this.noPhysics = this.isReturningToOwner();
      if (!this.level().isClientSide) {
         if (this.getSwordStack().isEmpty() && !this.restoreTrackedSwordStack()) {
            this.ejectPassengers();
            this.discard();
            return;
         }

         if (this.isSupersededDeployment()) {
            this.discardWithoutDroppingSword();
            return;
         }
      }

      this.tickPendingDismountSync();
      this.tickClientLerp();
      if (this.isReturningToOwner()) {
         if (!this.level().isClientSide) {
            Player owner = this.getLocalOwner();
            if (owner != null && owner.isAlive() && owner.level() == this.level()) {
               this.tickReturningToOwner(owner);
            } else {
               this.setDeltaMovement(Vec3.ZERO);
            }
         }
      } else {
         LivingEntity controller = this.getControllingPassenger();
         if (controller instanceof Player player && this.isControlledByLocalInstance()) {
            this.tickControlledFlight(player);
         } else if (controller == null && !this.level().isClientSide) {
            this.setDeltaMovement(Vec3.ZERO);
         }
      }
   }

   private void tickClientLerp() {
      if (this.level().isClientSide) {
         if (this.isControlledByLocalInstance()) {
            this.clientLerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
         } else {
            if (this.clientLerpSteps > 0) {
               this.lerpPositionAndRotationStep(
                  this.clientLerpSteps, this.clientLerpX, this.clientLerpY, this.clientLerpZ, this.clientLerpYRot, this.clientLerpXRot
               );
               this.clientLerpSteps--;
            }
         }
      }
   }

   private boolean restoreTrackedSwordStack() {
      ServerPlayer owner = this.getOnlineOwner();
      if (owner == null) {
         return false;
      } else {
         FlyingSwordDeploymentData.ActiveDeployment active = FlyingSwordDeploymentData.get(owner);
         if (active != null && active.matchesEntity(this)) {
            this.setSwordStack(active.copySwordStack());
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean isSupersededDeployment() {
      ServerPlayer owner = this.getOnlineOwner();
      if (owner == null) {
         return false;
      } else {
         FlyingSwordDeploymentData.ActiveDeployment active = FlyingSwordDeploymentData.get(owner);
         if (active == null) {
            FlyingSwordDeploymentData.track(owner, this);
            return false;
         } else {
            return !active.matchesEntity(this);
         }
      }
   }

   @Nullable
   private ServerPlayer getOnlineOwner() {
      return this.level().getServer() == null
         ? null
         : this.getOwnerUUID().map(ownerUuid -> this.level().getServer().getPlayerList().getPlayer(ownerUuid)).orElse(null);
   }

   @Nullable
   private Player getLocalOwner() {
      return this.getOwnerUUID().map(ownerUuid -> this.level().getPlayerByUUID(ownerUuid)).orElse(null);
   }

   private void tickReturningToOwner(Player owner) {
      Vec3 destination = this.getReturnDestination(owner);
      if (destination == null) {
         this.setDeltaMovement(Vec3.ZERO);
         this.hasImpulse = true;
      } else {
         Vec3 offset = destination.subtract(this.position());
         double distance = offset.length();
         if (distance <= 0.55) {
            this.setPos(destination);
            this.setDeltaMovement(Vec3.ZERO);
            this.hasImpulse = true;
            this.stopReturningToOwner();
         } else {
            double targetSpeed = Mth.clamp(distance * 0.16, 0.25, 1.4449999999999998);
            Vec3 desiredMovement = offset.scale(targetSpeed / distance);
            double steering = distance > 12.0 ? 0.22 : 0.34;
            Vec3 movement = this.getDeltaMovement().lerp(desiredMovement, steering);
            if (movement.lengthSqr() > 2.0880249999999996) {
               movement = movement.normalize().scale(1.4449999999999998);
            }

            if (movement.length() > distance) {
               movement = offset;
            }

            this.setDeltaMovement(movement);
            this.move(MoverType.SELF, movement);
            this.turnTowardMovement(movement);
            this.hasImpulse = true;
         }
      }
   }

   @Nullable
   private Vec3 getReturnDestination(Player player) {
      Vec3 horizontalLook = Vec3.directionFromRotation(0.0F, player.getYRot());
      Vec3 right = new Vec3(horizontalLook.z, 0.0, -horizontalLook.x);
      Vec3 base = player.position().add(0.0, 0.18, 0.0);
      Vec3[] candidates = new Vec3[]{
         base.add(horizontalLook.scale(1.5)),
         base.add(right.scale(1.5)),
         base.add(right.scale(-1.5)),
         base.add(horizontalLook.scale(-1.5)),
         base.add(horizontalLook.scale(1.2)).add(0.0, 1.1, 0.0),
         player.position().add(0.0, 2.1, 0.0)
      };

      for (Vec3 candidate : candidates) {
         AABB bounds = this.getSwordBoundingBoxAt(candidate);
         if (this.level().isInWorldBounds(BlockPos.containing(candidate))
            && this.level().getWorldBorder().isWithinBounds(bounds)
            && this.level().noCollision(this, bounds)) {
            return candidate;
         }
      }

      return null;
   }

   private void turnTowardMovement(Vec3 movement) {
      double horizontalDistance = movement.horizontalDistance();
      if (!(movement.lengthSqr() < 1.0E-6)) {
         float targetYaw = (float)(Mth.atan2(-movement.x, movement.z) * 180.0F / (float)Math.PI);
         float targetPitch = (float)(-Mth.atan2(movement.y, horizontalDistance) * 180.0F / (float)Math.PI);
         this.setRot(Mth.rotLerp(0.35F, this.getYRot(), targetYaw), Mth.lerp(0.35F, this.getXRot(), Mth.clamp(targetPitch, -45.0F, 45.0F)));
      }
   }

   private void stopReturningToOwner() {
      this.entityData.set(DATA_RETURNING, false);
      this.noPhysics = false;
      this.setDeltaMovement(Vec3.ZERO);
      this.hasImpulse = true;
   }

   private void tickPendingDismountSync() {
      if (!this.level().isClientSide && this.pendingDismountSyncPlayer != null) {
         if (--this.pendingDismountSyncTicks <= 0) {
            ServerPlayer player = this.pendingDismountSyncPlayer;
            this.pendingDismountSyncPlayer = null;
            if (!this.isRemoved() && player.level() == this.level()) {
               player.connection.send(new ClientboundTeleportEntityPacket(this));
            }
         }
      }
   }

   private void tickControlledFlight(Player player) {
      double backwardScale = this.localForwardInput < 0.0F ? 0.6 : 1.0;
      Vec3 input = new Vec3(this.localStrafeInput, this.localVerticalInput, this.localForwardInput * backwardScale);
      if (input.lengthSqr() > 1.0) {
         input = input.normalize();
      }

      float yaw = player.getYRot() * (float) (Math.PI / 180.0);
      Vec3 forward = new Vec3(-Mth.sin(yaw), 0.0, Mth.cos(yaw));
      Vec3 right = new Vec3(forward.z, 0.0, -forward.x);
      Vec3 direction = forward.scale(input.z).add(right.scale(input.x)).add(0.0, input.y, 0.0);
      if (direction.lengthSqr() > 1.0) {
         direction = direction.normalize();
      }

      if (direction.lengthSqr() > 1.0E-6) {
         double speed = player.isSprinting() ? 1.3 : 0.85;
         this.setDeltaMovement(this.getDeltaMovement().lerp(direction.scale(speed), 0.36));
      } else {
         this.setDeltaMovement(this.getDeltaMovement().scale(0.68));
      }

      Vec3 movement = this.getDeltaMovement();
      if (movement.lengthSqr() < 1.0E-5) {
         movement = Vec3.ZERO;
         this.setDeltaMovement(Vec3.ZERO);
      }

      this.move(MoverType.SELF, movement);
      if (this.horizontalCollision) {
         this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      }

      if (this.verticalCollision) {
         this.setDeltaMovement(this.getDeltaMovement().x, 0.0, this.getDeltaMovement().z);
      }

      float pitch = (float)Mth.clamp(-this.getDeltaMovement().y * 18.0, -12.0, 12.0);
      this.setRot(player.getYRot(), pitch);
      player.fallDistance = 0.0F;
      this.hasImpulse = true;
   }

   protected void removePassenger(Entity passenger) {
      super.removePassenger(passenger);
      this.setLocalControlInput(0.0F, 0.0F, 0.0F);
      this.setDeltaMovement(Vec3.ZERO);
      this.hasImpulse = true;
      if (passenger instanceof Player player && this.isOwnedBy(player)) {
         if (this.level().isClientSide) {
            this.awaitingDismountPositionSync = true;
            this.setOldPosAndRot();
         } else if (player instanceof ServerPlayer serverPlayer) {
            this.pendingDismountSyncPlayer = serverPlayer;
            this.pendingDismountSyncTicks = 1;
         }
      }
   }

   protected void addPassenger(Entity passenger) {
      super.addPassenger(passenger);
      if (!this.level().isClientSide && passenger instanceof Player && this.isOwnedBy(passenger)) {
         this.stopReturningToOwner();
      }

      if (this.level().isClientSide) {
         this.awaitingDismountPositionSync = false;
      } else if (passenger == this.pendingDismountSyncPlayer) {
         this.pendingDismountSyncPlayer = null;
         this.pendingDismountSyncTicks = 0;
      }
   }

   public void lerpTo(double x, double y, double z, float yRot, float xRot, int steps) {
      if (this.level().isClientSide && this.awaitingDismountPositionSync && !this.isVehicle()) {
         this.moveTo(x, y, z, yRot, xRot);
         this.clientLerpSteps = 0;
         this.awaitingDismountPositionSync = false;
      } else if (this.level().isClientSide) {
         if (this.position().distanceToSqr(x, y, z) > 64.0) {
            this.moveTo(x, y, z, yRot, xRot);
            this.clientLerpSteps = 0;
         } else {
            this.clientLerpX = x;
            this.clientLerpY = y;
            this.clientLerpZ = z;
            this.clientLerpYRot = yRot;
            this.clientLerpXRot = xRot;
            this.clientLerpSteps = Math.max(1, steps);
         }
      } else {
         super.lerpTo(x, y, z, yRot, xRot, steps);
      }
   }

   public double lerpTargetX() {
      return this.clientLerpSteps > 0 ? this.clientLerpX : this.getX();
   }

   public double lerpTargetY() {
      return this.clientLerpSteps > 0 ? this.clientLerpY : this.getY();
   }

   public double lerpTargetZ() {
      return this.clientLerpSteps > 0 ? this.clientLerpZ : this.getZ();
   }

   public float lerpTargetXRot() {
      return this.clientLerpSteps > 0 ? (float)this.clientLerpXRot : this.getXRot();
   }

   public float lerpTargetYRot() {
      return this.clientLerpSteps > 0 ? (float)this.clientLerpYRot : this.getYRot();
   }

   public void recallTo(Player player) {
      if (!this.level().isClientSide && this.isOwnedBy(player)) {
         if (player instanceof ServerPlayer serverPlayer) {
            FlyingSwordDeploymentData.clearIfEntityMatches(serverPlayer, this);
         }

         ItemStack stack = this.takeSwordStack();
         this.ejectPassengers();
         this.discard();
         if (!stack.isEmpty()) {
            player.getInventory().add(stack);
            if (!stack.isEmpty()) {
               player.drop(stack, false);
            }
         }
      }
   }

   public boolean isPushable() {
      return false;
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean isPickable() {
      return !this.isRemoved();
   }

   public boolean shouldRiderSit() {
      return false;
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      ItemStack stack = this.getSwordStack();
      if (!stack.isEmpty()) {
         compound.put("Sword", stack.save(this.registryAccess()));
      }

      this.getOwnerUUID().ifPresent(owner -> compound.putUUID("Owner", owner));
      if (this.deploymentId != null) {
         compound.putUUID("DeploymentId", this.deploymentId);
      }

      if (this.isReturningToOwner()) {
         compound.putBoolean("ReturningToOwner", true);
      }
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("Sword", 10)) {
         this.setSwordStack(ItemStack.parse(this.registryAccess(), compound.getCompound("Sword")).orElse(ItemStack.EMPTY));
      }

      if (compound.hasUUID("Owner")) {
         this.setOwnerUUID(compound.getUUID("Owner"));
      }

      if (compound.hasUUID("DeploymentId")) {
         this.deploymentId = compound.getUUID("DeploymentId");
      }

      this.entityData.set(DATA_RETURNING, compound.getBoolean("ReturningToOwner"));
      this.setNoGravity(true);
      this.setInvulnerable(true);
   }

   public void remove(RemovalReason reason) {
      if (!this.level().isClientSide && reason.shouldDestroy()) {
         ServerPlayer owner = this.getOnlineOwner();
         if (owner != null) {
            FlyingSwordDeploymentData.clearIfEntityMatches(owner, this);
         }
      }

      if (!this.level().isClientSide && reason.shouldDestroy() && !this.getSwordStack().isEmpty()) {
         this.spawnAtLocation(this.takeSwordStack(), 0.1F);
      }

      super.remove(reason);
   }
}
