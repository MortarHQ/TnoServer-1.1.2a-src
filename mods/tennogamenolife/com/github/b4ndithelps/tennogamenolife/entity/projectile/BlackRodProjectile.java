package com.github.b4ndithelps.tennogamenolife.entity.projectile;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierPart;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.util.TensuraExplosionDamageCalculator;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlackRodProjectile extends Projectile implements GeoEntity {
   public static final ResourceKey<DamageType> BLACK_ARROW_PHYSICAL = ResourceKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tennogamenolife", "black_arrow_physical")
   );
   private static final EntityDataAccessor<Boolean> STUCK = SynchedEntityData.defineId(BlackRodProjectile.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> FALLING_AFTER_HIT = SynchedEntityData.defineId(BlackRodProjectile.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> ATTACHED_ENTITY_ID = SynchedEntityData.defineId(BlackRodProjectile.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Vector3f> ATTACHED_OFFSET = SynchedEntityData.defineId(BlackRodProjectile.class, EntityDataSerializers.VECTOR3);
   private static final int EXPLOSION_DELAY_TICKS = 80;
   private static final int MAX_FLIGHT_TICKS = 100;
   private static final float DIRECT_DAMAGE = 25.0F;
   private static final float EXPLOSION_DAMAGE = 50.0F;
   private static final double EXPLOSION_RADIUS = 4.0;
   private static final double ENTITY_PENETRATION_DEPTH = 0.25;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private ManasSkillInstance skillInstance;
   private UUID attachedEntityUuid;
   private BlockPos attachedBlockPos;
   private Vec3 attachedOffset = Vec3.ZERO;
   private int stuckTicks;
   private int flightTicks;

   public BlackRodProjectile(EntityType<? extends BlackRodProjectile> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = false;
      this.setNoGravity(false);
   }

   public void setSkillInstance(ManasSkillInstance skillInstance) {
      this.skillInstance = skillInstance;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(STUCK, false);
      builder.define(FALLING_AFTER_HIT, false);
      builder.define(ATTACHED_ENTITY_ID, -1);
      builder.define(ATTACHED_OFFSET, new Vector3f());
   }

   public void tick() {
      super.tick();
      if ((Boolean)this.entityData.get(STUCK)) {
         this.tickArmed();
      } else {
         this.tickFlying();
      }
   }

   private void tickFlying() {
      Vec3 movement = this.getDeltaMovement();
      if (!this.level().isClientSide() && movement.lengthSqr() >= 1.0E-7) {
         Vec3 start = this.position();
         Vec3 end = start.add(movement);
         HitResult blockHit = this.level().clip(new ClipContext(start, end, Block.COLLIDER, Fluid.NONE, this));
         Vec3 collisionEnd = blockHit.getType() == Type.MISS ? end : blockHit.getLocation();
         EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
            this, start, collisionEnd, this.getBoundingBox().expandTowards(movement).inflate(1.0), this::canHitEntity, start.distanceToSqr(collisionEnd)
         );
         if (entityHit != null) {
            this.stickIntoEntity(entityHit);
            return;
         }

         if (blockHit.getType() == Type.BLOCK) {
            this.stickIntoBlock((BlockHitResult)blockHit);
            return;
         }
      }

      this.advanceWithPhysics(movement);
      this.flightTicks++;
      if (!this.level().isClientSide() && this.flightTicks >= 100) {
         this.explode();
      }
   }

   public void alignRotationToMovement() {
      this.alignRotationToDirection(this.getDeltaMovement());
   }

   private void alignRotationToDirection(Vec3 direction) {
      if (!(direction.lengthSqr() < 1.0E-7)) {
         float yaw = (float)(Math.atan2(direction.x, direction.z) * 180.0 / Math.PI);
         float pitch = (float)(Math.atan2(direction.y, direction.horizontalDistance()) * 180.0 / Math.PI);
         this.setYRot(yaw);
         this.yRotO = yaw;
         this.setXRot(pitch);
         this.xRotO = pitch;
      }
   }

   private void stickIntoEntity(EntityHitResult hitResult) {
      Entity target = hitResult.getEntity();
      Vec3 movement = this.getDeltaMovement();
      Vec3 direction = movement.lengthSqr() < 1.0E-7 ? Vec3.ZERO : movement.normalize();
      Vec3 surfaceImpact = target.getBoundingBox().clip(this.position(), this.position().add(movement)).orElse(hitResult.getLocation());
      Vec3 impact = surfaceImpact.add(direction.scale(0.25));
      this.setPos(impact);
      if (target instanceof LivingEntity living && living.isAlive()) {
         this.dealCombinedDamage(living, 25.0F);
      }

      this.attachedEntityUuid = target.getUUID();
      this.attachedBlockPos = null;
      this.attachedOffset = impact.subtract(target.getBoundingBox().getCenter());
      this.entityData.set(ATTACHED_OFFSET, new Vector3f((float)this.attachedOffset.x, (float)this.attachedOffset.y, (float)this.attachedOffset.z));
      this.entityData.set(ATTACHED_ENTITY_ID, target.getId());
      this.beginStuckState();
      this.level().playSound(null, this.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.PLAYERS, 1.0F, 0.65F);
   }

   private void stickIntoBlock(BlockHitResult hitResult) {
      Vec3 movement = this.getDeltaMovement();
      Vec3 direction = movement.lengthSqr() < 1.0E-7 ? Vec3.ZERO : movement.normalize();
      this.alignRotationToDirection(movement);
      this.setPos(hitResult.getLocation().subtract(direction.scale(0.05)));
      this.attachedBlockPos = hitResult.getBlockPos().immutable();
      this.attachedEntityUuid = null;
      this.entityData.set(ATTACHED_ENTITY_ID, -1);
      this.beginStuckState();
      this.level().playSound(null, this.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.PLAYERS, 1.0F, 0.8F);
   }

   private void beginStuckState() {
      this.entityData.set(STUCK, true);
      this.entityData.set(FALLING_AFTER_HIT, false);
      this.setDeltaMovement(Vec3.ZERO);
      this.stuckTicks = 0;
      this.hurtMarked = true;
   }

   private void tickArmed() {
      if ((Boolean)this.entityData.get(FALLING_AFTER_HIT)) {
         this.tickFallingAfterHit();
         this.tickArmedFuse();
      } else {
         int attachedId = (Integer)this.entityData.get(ATTACHED_ENTITY_ID);
         Entity attached = attachedId >= 0 ? this.level().getEntity(attachedId) : null;
         boolean wasAttachedToEntity = attachedId >= 0 || this.attachedEntityUuid != null;
         if (!this.level().isClientSide() && attached == null && this.attachedEntityUuid != null && this.level() instanceof ServerLevel serverLevel) {
            attached = serverLevel.getEntity(this.attachedEntityUuid);
            if (attached != null) {
               this.entityData.set(ATTACHED_ENTITY_ID, attached.getId());
            }
         }

         if (attached != null && attached.isAlive()) {
            Vector3f offset = (Vector3f)this.entityData.get(ATTACHED_OFFSET);
            this.setPos(attached.getBoundingBox().getCenter().add(offset.x(), offset.y(), offset.z()));
         } else if (!this.level().isClientSide()) {
            if (wasAttachedToEntity) {
               this.startFallingAfterHit();
            } else if (this.attachedBlockPos != null) {
               BlockState support = this.level().getBlockState(this.attachedBlockPos);
               if (support.getCollisionShape(this.level(), this.attachedBlockPos).isEmpty()) {
                  this.startFallingAfterHit();
               }
            } else {
               this.startFallingAfterHit();
            }
         }

         this.tickArmedFuse();
      }
   }

   private void tickArmedFuse() {
      if (!this.level().isClientSide()) {
         this.stuckTicks++;
         if (this.stuckTicks >= 80) {
            this.explode();
         }
      }
   }

   private void startFallingAfterHit() {
      this.entityData.set(FALLING_AFTER_HIT, true);
      this.entityData.set(ATTACHED_ENTITY_ID, -1);
      this.attachedEntityUuid = null;
      this.attachedBlockPos = null;
      this.noPhysics = false;
      this.setNoGravity(false);
      this.setDeltaMovement(0.0, -0.05, 0.0);
      this.hurtMarked = true;
   }

   private void tickFallingAfterHit() {
      Vec3 movement = this.getDeltaMovement();
      if (movement.lengthSqr() < 1.0E-7) {
         movement = new Vec3(0.0, -0.05, 0.0);
         this.setDeltaMovement(movement);
      }

      if (!this.level().isClientSide()) {
         Vec3 start = this.position();
         BlockHitResult blockHit = this.level().clip(new ClipContext(start, start.add(movement), Block.COLLIDER, Fluid.NONE, this));
         if (blockHit.getType() == Type.BLOCK) {
            this.restickAfterFalling(blockHit, movement);
            return;
         }
      }

      this.advanceWithPhysics(movement);
   }

   private void restickAfterFalling(BlockHitResult hitResult, Vec3 movement) {
      Vec3 direction = movement.lengthSqr() < 1.0E-7 ? Vec3.ZERO : movement.normalize();
      this.alignRotationToDirection(movement);
      this.setPos(hitResult.getLocation().subtract(direction.scale(0.05)));
      this.attachedBlockPos = hitResult.getBlockPos().immutable();
      this.entityData.set(FALLING_AFTER_HIT, false);
      this.setDeltaMovement(Vec3.ZERO);
      this.hurtMarked = true;
      this.level().playSound(null, this.blockPosition(), SoundEvents.ARROW_HIT, SoundSource.PLAYERS, 0.7F, 0.8F);
   }

   private void advanceWithPhysics(Vec3 movement) {
      this.alignRotationToDirection(movement);
      Vec3 nextMovement = movement.scale(0.99);
      if (!this.isNoGravity()) {
         nextMovement = nextMovement.add(0.0, -0.05, 0.0);
      }

      this.setDeltaMovement(nextMovement);
      this.setPos(this.position().add(movement));
      this.checkInsideBlocks();
   }

   private void explode() {
      Entity owner = this.getOwner();
      AABB bounds = this.getBoundingBox().inflate(4.0);

      for (LivingEntity target : this.level()
         .getEntitiesOfClass(
            LivingEntity.class, bounds, targetx -> targetx.isAlive() && targetx != owner && targetx.getBoundingBox().distanceToSqr(this.position()) <= 16.0
         )) {
         this.dealCombinedDamage(target, 50.0F);
      }

      this.createTerrainExplosion();
      this.discard();
   }

   private void createTerrainExplosion() {
      boolean canGrief = TensuraGameRules.canSkillGrief(this.level());
      if (canGrief
         && ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker())
            .grief(this.skillInstance, this.level(), this.getOwner(), this.getX(), this.getY(), this.getZ())
            .isFalse()) {
         canGrief = false;
      }

      TensuraExplosionDamageCalculator calculator = new TensuraExplosionDamageCalculator(canGrief, false, Optional.empty(), Optional.of(0.0F));
      this.level()
         .explode(
            this,
            null,
            calculator,
            this.getX(),
            this.getY(),
            this.getZ(),
            4.0F,
            false,
            canGrief ? ExplosionInteraction.BLOCK : ExplosionInteraction.NONE,
            ParticleTypes.EXPLOSION,
            ParticleTypes.EXPLOSION_EMITTER,
            SoundEvents.GENERIC_EXPLODE
         );
      if (canGrief) {
         ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker())
            .grief(this.skillInstance, this.level(), this.getOwner(), this.getX(), this.getY(), this.getZ());
      }
   }

   private void dealCombinedDamage(LivingEntity target, float totalDamage) {
      Entity owner = this.getOwner();
      DamageSource physical = TensuraDamageTypes.getIndirectEntityDamageSource(this.level(), BLACK_ARROW_PHYSICAL, owner, this);
      DamageSource darkness = TensuraDamageTypes.getIndirectEntityDamageSource(this.level(), TensuraDamageTypes.DARKNESS_ELEMENTAL, owner, this);
      this.configureBypass(physical, false);
      this.configureBypass(darkness, true);
      target.invulnerableTime = 0;
      TensuraDamageHelper.hurtDouble(target, physical, totalDamage * 0.5F, darkness, totalDamage * 0.5F);
   }

   private void configureBypass(DamageSource source, boolean darkness) {
      TensuraDamageSource tensuraSource = (TensuraDamageSource)source;
      tensuraSource.tensura$setAbilityInstance(this.skillInstance);
      tensuraSource.tensura$setBarrierBypassLevel(Float.MAX_VALUE);
      tensuraSource.tensura$setDodgeBypass();
      if (darkness) {
         tensuraSource.tensura$setMagicType(MagicType.SPIRITUAL);
         tensuraSource.tensura$setElement(Element.DARKNESS);
      }
   }

   protected boolean canHitEntity(Entity target) {
      return target != this.getOwner() && !(target instanceof BarrierPart) && super.canHitEntity(target);
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isAttackable() {
      return false;
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putBoolean("Stuck", (Boolean)this.entityData.get(STUCK));
      tag.putBoolean("FallingAfterHit", (Boolean)this.entityData.get(FALLING_AFTER_HIT));
      tag.putInt("StuckTicks", this.stuckTicks);
      tag.putInt("FlightTicks", this.flightTicks);
      tag.putDouble("AttachedOffsetX", this.attachedOffset.x);
      tag.putDouble("AttachedOffsetY", this.attachedOffset.y);
      tag.putDouble("AttachedOffsetZ", this.attachedOffset.z);
      if (this.attachedEntityUuid != null) {
         tag.putUUID("AttachedEntity", this.attachedEntityUuid);
      }

      tag.putBoolean("HasAttachedBlock", this.attachedBlockPos != null);
      if (this.attachedBlockPos != null) {
         tag.putLong("AttachedBlock", this.attachedBlockPos.asLong());
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.entityData.set(STUCK, tag.getBoolean("Stuck"));
      this.entityData.set(FALLING_AFTER_HIT, tag.getBoolean("FallingAfterHit"));
      this.stuckTicks = tag.getInt("StuckTicks");
      this.flightTicks = tag.getInt("FlightTicks");
      this.attachedOffset = new Vec3(tag.getDouble("AttachedOffsetX"), tag.getDouble("AttachedOffsetY"), tag.getDouble("AttachedOffsetZ"));
      this.entityData.set(ATTACHED_OFFSET, new Vector3f((float)this.attachedOffset.x, (float)this.attachedOffset.y, (float)this.attachedOffset.z));
      if (tag.hasUUID("AttachedEntity")) {
         this.attachedEntityUuid = tag.getUUID("AttachedEntity");
      }

      if (tag.getBoolean("HasAttachedBlock")) {
         this.attachedBlockPos = BlockPos.of(tag.getLong("AttachedBlock"));
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
