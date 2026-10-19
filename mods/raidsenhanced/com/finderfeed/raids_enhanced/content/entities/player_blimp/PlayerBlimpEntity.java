package com.finderfeed.raids_enhanced.content.entities.player_blimp;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.AnimationTicker;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.finderfeed.fdlib.util.math.FDMathUtil;
import com.finderfeed.raids_enhanced.init.REAnimations;
import com.finderfeed.raids_enhanced.init.REItems;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import net.minecraft.BlockUtil.FoundRectangle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;

public class PlayerBlimpEntity extends FDVehicle {
   public static final String ROTATION_LAYER = "ROTATION";
   public static final String DANGLING_LIGHTS = "DANGLING_LIGHTS";
   private boolean inputLeft;
   private boolean inputRight;
   private boolean inputForward;
   private boolean inputBackward;
   private boolean inputUp;
   private boolean inputDown;
   private int lerpSteps;
   private double lerpX;
   private double lerpY;
   private double lerpZ;
   private double lerpYRot;
   private double lerpXRot;
   private float blimpRotationSpeed = 0.0F;
   private float perpellerRotation;
   private float perpellerRotationO;
   private int perpellerStartTicks = 0;
   private double lastKnownSpeed;
   private int rotationDirection;
   private Vec3 oldPos;

   public PlayerBlimpEntity(EntityType<? extends PlayerBlimpEntity> type, Level level) {
      super(type, level);
      this.setNoGravity(true);
      this.getAnimationSystem().startAnimation("PERPELLER", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_PERPELLER).build());
      this.getAnimationSystem().setAnimationsApplyListener(this::onAnimationsApplied);
   }

   private void onAnimationsApplied(FDModel model, Float pticks) {
      this.getAnimationSystem().setVariable("variable.propeller_rotation", FDMathUtil.lerp(this.perpellerRotationO, this.perpellerRotation, pticks));
   }

   @Override
   public void tick() {
      if (!this.level().isClientSide && this.isUnderWater()) {
         this.ejectPassengers();
      }

      if (this.getHurtTime() > 0) {
         this.setHurtTime(this.getHurtTime() - 1);
      }

      if (this.getDamage() > 0.0F) {
         this.setDamage(this.getDamage() - 1.0F);
      }

      super.tick();
      this.tickLerp();
      if (this.isControlledByLocalInstance()) {
         this.blimpMovement();
         if (this.level().isClientSide) {
            this.controlBlimp();
            this.sendRotationToServer();
         }

         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.95F));
      } else {
         this.setDeltaMovement(Vec3.ZERO);
         this.blimpRotationSpeed = 0.0F;
      }

      if (this.oldPos == null) {
         this.oldPos = this.position();
      }

      if (this.level().isClientSide) {
         this.perpellerRotationO = this.perpellerRotation;
         if (this.getControllingPassenger() != null) {
            Vec3 delta = this.position().subtract(this.oldPos).multiply(1.0, 0.0, 1.0);
            double speed = Mth.clamp(delta.length(), 0.0, 10.0);
            int startTicksTime = 20;
            if (speed > 0.0 && !(this.getGroundFriction() > 0.0F)) {
               this.perpellerStartTicks = Mth.clamp(this.perpellerStartTicks + 1, 0, startTicksTime);
               this.lastKnownSpeed = speed;
            } else {
               this.perpellerStartTicks = Mth.clamp(this.perpellerStartTicks - 1, 0, startTicksTime);
            }

            float p = (float)this.perpellerStartTicks / startTicksTime;
            this.perpellerRotation = this.perpellerRotation + (float)this.lastKnownSpeed * 50.0F * p;
         } else {
            this.lastKnownSpeed = 0.0;
            this.perpellerStartTicks = Mth.clamp(this.perpellerStartTicks - 1, 0, Integer.MAX_VALUE);
         }
      } else {
         if (this.tickCount % 4 == 0) {
            if (this.getGroundFriction() > 0.0F) {
               this.getAnimationSystem().stopAnimation("SOMEOTHERIDLE");
            } else {
               this.getAnimationSystem()
                  .startAnimation("SOMEOTHERIDLE", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_IDLE).setToNullTransitionTime(10).build());
            }
         }

         if (this.getFirstPassenger() instanceof Player) {
            if (this.rotationDirection == 0) {
               this.getAnimationSystem().stopAnimation("ROTATION");
               this.getAnimationSystem().stopAnimation("DANGLING_LIGHTS");
            } else if (this.rotationDirection == 1) {
               this.getAnimationSystem().startAnimation("DANGLING_LIGHTS", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_DANGLE_LIGHTS).build());
               this.getAnimationSystem().startAnimation("ROTATION", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_TURN_LEFT).build());
            } else if (this.rotationDirection == -1) {
               this.getAnimationSystem().startAnimation("DANGLING_LIGHTS", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_DANGLE_LIGHTS).build());
               this.getAnimationSystem().startAnimation("ROTATION", AnimationTicker.builder(REAnimations.PLAYER_BLIMP_TURN_RIGHT).build());
            }
         } else {
            this.getAnimationSystem().stopAnimation("ROTATION");
            this.getAnimationSystem().stopAnimation("DANGLING_LIGHTS");
         }
      }

      this.oldPos = this.position();
      this.collectEntitiesAround();
   }

   private void collectEntitiesAround() {
      List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
      if (!list.isEmpty()) {
         boolean flag = !this.level().isClientSide && !(this.getControllingPassenger() instanceof Player);

         for (Entity entity : list) {
            if (!entity.hasPassenger(this)) {
               if (flag
                  && this.getPassengers().size() < this.getMaxPassengers()
                  && !entity.isPassenger()
                  && this.hasEnoughSpaceFor(entity)
                  && entity instanceof LivingEntity
                  && !(entity instanceof WaterAnimal)
                  && !(entity instanceof Player)) {
                  entity.startRiding(this);
               } else {
                  this.push(entity);
               }
            }
         }
      }
   }

   public boolean hasEnoughSpaceFor(Entity entity) {
      return entity.getBbWidth() < this.getBbWidth();
   }

   public void setRotatingState(int rotationDirection) {
      this.rotationDirection = rotationDirection;
   }

   private void sendRotationToServer() {
      int rotationDirection = 0;
      if (this.inputLeft) {
         rotationDirection = 1;
      } else if (this.inputRight) {
         rotationDirection = -1;
      }

      PacketDistributor.sendToServer(new PlayerBlimpRotatingPacket(this.getId(), rotationDirection), new CustomPacketPayload[0]);
   }

   private void blimpMovement() {
      float groundFriction = this.getGroundFriction();
      if (groundFriction > 0.0F) {
         Vec3 movement = this.getDeltaMovement();
         this.setDeltaMovement(movement.x * 0.1, movement.y, movement.z * 0.1);
      }
   }

   protected void positionRider(Entity entity, MoveFunction func) {
      super.positionRider(entity, func);
   }

   protected Vec3 getPassengerAttachmentPoint(Entity p_294665_, EntityDimensions p_295933_, float p_295585_) {
      float f = 0.0F;
      if (this.getPassengers().size() > 1) {
         int i = this.getPassengers().indexOf(p_294665_);
         if (i == 0) {
            f = 0.2F;
         } else {
            f = -0.6F;
         }

         if (p_294665_ instanceof Animal) {
            f += 0.2F;
         }
      }

      return new Vec3(0.0, p_295933_.height() / 3.0F, f).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
   }

   private void tickLerp() {
      if (this.isControlledByLocalInstance()) {
         this.lerpSteps = 0;
         this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
      }

      if (this.lerpSteps > 0) {
         this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
         this.lerpSteps--;
      }
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.EVENTS;
   }

   public void lerpTo(double p_38299_, double p_38300_, double p_38301_, float p_38302_, float p_38303_, int p_38304_) {
      this.lerpX = p_38299_;
      this.lerpY = p_38300_;
      this.lerpZ = p_38301_;
      this.lerpYRot = p_38302_;
      this.lerpXRot = p_38303_;
      this.lerpSteps = 10;
   }

   public double lerpTargetX() {
      return this.lerpSteps > 0 ? this.lerpX : this.getX();
   }

   public double lerpTargetY() {
      return this.lerpSteps > 0 ? this.lerpY : this.getY();
   }

   public double lerpTargetZ() {
      return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
   }

   public float lerpTargetXRot() {
      return this.lerpSteps > 0 ? (float)this.lerpXRot : this.getXRot();
   }

   public float lerpTargetYRot() {
      return this.lerpSteps > 0 ? (float)this.lerpYRot : this.getYRot();
   }

   private void controlBlimp() {
      if (this.isVehicle()) {
         boolean rotating = false;
         float rotspeedPerTick = 0.25F;
         float maxRotSpeed = 3.0F;
         if (this.inputLeft) {
            this.blimpRotationSpeed -= rotspeedPerTick;
            rotating = true;
         }

         if (this.inputRight) {
            this.blimpRotationSpeed += rotspeedPerTick;
            rotating = true;
         }

         if (!rotating) {
            if (this.blimpRotationSpeed > 0.0F) {
               this.blimpRotationSpeed -= rotspeedPerTick;
               if (this.blimpRotationSpeed < 0.0F) {
                  this.blimpRotationSpeed = 0.0F;
               }
            } else if (this.blimpRotationSpeed < 0.0F) {
               this.blimpRotationSpeed += rotspeedPerTick;
               if (this.blimpRotationSpeed > 0.0F) {
                  this.blimpRotationSpeed = 0.0F;
               }
            }
         }

         this.blimpRotationSpeed = Mth.clamp(this.blimpRotationSpeed, -maxRotSpeed, maxRotSpeed);
         this.setYRot(this.getYRot() + this.blimpRotationSpeed);
         float speed = 0.0F;
         if (this.inputRight != this.inputLeft && !this.inputUp && !this.inputDown) {
            speed += 0.005F;
         }

         if (this.inputForward) {
            speed += 0.04F;
         }

         if (this.inputBackward) {
            speed -= 0.04F;
         }

         float speedVertical = 0.0F;
         if (this.inputUp) {
            speedVertical += 0.04F;
         }

         if (this.inputDown) {
            speedVertical -= 0.04F;
         }

         this.setDeltaMovement(
            this.getDeltaMovement()
               .add(Mth.sin(-this.getYRot() * (float) (Math.PI / 180.0)) * speed, speedVertical, Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * speed)
         );
      }
   }

   public float getGroundFriction() {
      AABB aabb = this.getBoundingBox();
      AABB aabb1 = new AABB(aabb.minX, aabb.minY - 0.001, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
      int i = Mth.floor(aabb1.minX) - 1;
      int j = Mth.ceil(aabb1.maxX) + 1;
      int k = Mth.floor(aabb1.minY) - 1;
      int l = Mth.ceil(aabb1.maxY) + 1;
      int i1 = Mth.floor(aabb1.minZ) - 1;
      int j1 = Mth.ceil(aabb1.maxZ) + 1;
      VoxelShape voxelshape = Shapes.create(aabb1);
      float f = 0.0F;
      int k1 = 0;
      MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

      for (int l1 = i; l1 < j; l1++) {
         for (int i2 = i1; i2 < j1; i2++) {
            int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
            if (j2 != 2) {
               for (int k2 = k; k2 < l; k2++) {
                  if (j2 <= 0 || k2 != k && k2 != l - 1) {
                     blockpos$mutableblockpos.set(l1, k2, i2);
                     BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos);
                     if (!(blockstate.getBlock() instanceof WaterlilyBlock)
                        && Shapes.joinIsNotEmpty(
                           blockstate.getCollisionShape(this.level(), blockpos$mutableblockpos).move(l1, k2, i2), voxelshape, BooleanOp.AND
                        )) {
                        f += blockstate.getFriction(this.level(), blockpos$mutableblockpos, this);
                        k1++;
                     }
                  }
               }
            }
         }
      }

      return f / k1;
   }

   public boolean isPushedByFluid(FluidType type) {
      return true;
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      InteractionResult interactionresult = super.interact(player, hand);
      if (hand == InteractionHand.MAIN_HAND) {
         if (interactionresult != InteractionResult.PASS || this.isUnderWater()) {
            return interactionresult;
         }

         if (player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
         }

         if (!this.level().isClientSide) {
            if (player.getVehicle() == this) {
               player.stopRiding();
               return InteractionResult.CONSUME;
            } else {
               return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
            }
         } else {
            return InteractionResult.SUCCESS;
         }
      } else {
         return interactionresult;
      }
   }

   public boolean canRiderInteract() {
      return true;
   }

   public void setInput(boolean left, boolean right, boolean forward, boolean backward, boolean shifting, boolean jumping) {
      this.inputLeft = left;
      this.inputRight = right;
      this.inputForward = forward;
      this.inputBackward = backward;
      this.inputUp = jumping;
      this.inputDown = shifting;
   }

   public Vec3 getDismountLocationForPassenger(LivingEntity p_38357_) {
      Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth() * Mth.SQRT_OF_TWO, p_38357_.getBbWidth(), p_38357_.getYRot());
      double d0 = this.getX() + vec3.x;
      double d1 = this.getZ() + vec3.z;
      BlockPos blockpos = BlockPos.containing(d0, this.getBoundingBox().maxY, d1);
      BlockPos blockpos1 = blockpos.below();
      if (!this.level().isWaterAt(blockpos1)) {
         List<Vec3> list = Lists.newArrayList();
         double d2 = this.level().getBlockFloorHeight(blockpos);
         if (DismountHelper.isBlockFloorValid(d2)) {
            list.add(new Vec3(d0, blockpos.getY() + d2, d1));
         }

         double d3 = this.level().getBlockFloorHeight(blockpos1);
         if (DismountHelper.isBlockFloorValid(d3)) {
            list.add(new Vec3(d0, blockpos1.getY() + d3, d1));
         }

         UnmodifiableIterator var14 = p_38357_.getDismountPoses().iterator();

         while (var14.hasNext()) {
            Pose pose = (Pose)var14.next();

            for (Vec3 vec31 : list) {
               if (DismountHelper.canDismountTo(this.level(), vec31, p_38357_, pose)) {
                  p_38357_.setPose(pose);
                  return vec31;
               }
            }
         }
      }

      return super.getDismountLocationForPassenger(p_38357_);
   }

   public LivingEntity getControllingPassenger() {
      return this.getFirstPassenger() instanceof LivingEntity livingentity ? livingentity : super.getControllingPassenger();
   }

   public boolean canCollideWith(Entity p_38376_) {
      return Boat.canVehicleCollide(this, p_38376_);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean isPushable() {
      return true;
   }

   public AABB getBoundingBoxForCulling() {
      return new AABB(this.getX() - 3.0, this.getY() - 1.0, this.getZ() - 3.0, this.getX() + 3.0, this.getY() + 6.0, this.getZ() + 3.0);
   }

   public Vec3 getRelativePortalPosition(Axis p_38335_, FoundRectangle p_38336_) {
      return LivingEntity.resetForwardDirectionOfRelativePortalPosition(super.getRelativePortalPosition(p_38335_, p_38336_));
   }

   protected boolean canAddPassenger(Entity entity) {
      return this.getPassengers().size() < this.getMaxPassengers();
   }

   protected int getMaxPassengers() {
      return 2;
   }

   public boolean isPickable() {
      return !this.isRemoved();
   }

   protected double getDefaultGravity() {
      return 0.0;
   }

   protected Item getDropItem() {
      return REItems.PLAYER_BLIMP.get();
   }

   @EventBusSubscriber(modid = "raidsenhanced")
   public static class Events {
      @SubscribeEvent
      public static void hurtEvent(LivingIncomingDamageEvent event) {
         DamageSource source = event.getSource();
         if (source != null && source.is(DamageTypes.FALL) && event.getEntity().getVehicle() instanceof PlayerBlimpEntity) {
            event.setCanceled(true);
         }
      }
   }
}
