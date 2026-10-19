package io.github.Memoires.mysticism.entity.magic.misc;

import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.field.MagicExplosion;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BlackHoleEntity extends Entity {
   private static final EntityDataAccessor<Float> DATA_SIZE_MODIFIER = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.FLOAT);
   private static final EntityDataAccessor<Integer> DATA_MAX_LIFETIME = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<CompoundTag> INSTANCE_TAG = SynchedEntityData.defineId(BlackHoleEntity.class, EntityDataSerializers.COMPOUND_TAG);
   private static final double BASE_GRAVITY_RADIUS = 20.0;
   private static final double BASE_EVENT_HORIZON = 1.0;
   private static final double BASE_PULL_STRENGTH = 0.12;
   private static final double BASE_RIP_RADIUS = 12.0;
   private static final int RIPS_PER_TICK = 2;
   private int maxRadius = 0;
   private static final int MAX_FALLING_BLOCKS = 150;
   private static final double HARD_RADIUS_CAP = 16.0;
   private int activeFallingBlocks = 0;
   @Nullable
   private UUID ownerUUID;
   @Nullable
   private Entity cachedOwner;
   protected float damage = 0.0F;
   protected float secondaryDamage = 0.0F;
   protected int triggerTimer = -1;
   protected double apCost = 0.0;
   protected double mpCost = 0.0;
   protected int mode = 0;
   protected boolean elementalAttack = false;
   protected boolean limitedGriefing = false;
   protected float maxDamage;
   protected float maxSecondaryDamage;
   protected float maxSize;

   public BlackHoleEntity(EntityType<BlackHoleEntity> entityType, Level level) {
      super(entityType, level);
      this.noPhysics = true;
   }

   public BlackHoleEntity(Level pLevel, @Nullable LivingEntity pOwner) {
      this((EntityType<BlackHoleEntity>)MysticismSkillEntityTypes.BLACK_HOLE.get(), pLevel);
      this.setOwner(pOwner);
      this.noPhysics = true;
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_SIZE_MODIFIER, 1.0F);
      builder.define(INSTANCE_TAG, new CompoundTag());
      builder.define(DATA_MAX_LIFETIME, 120);
   }

   public float getSizeModifier() {
      return (Float)this.entityData.get(DATA_SIZE_MODIFIER);
   }

   public void setSizeModifier(float size) {
      float cap = this.maxSize > 0.0F ? this.maxSize : 10.0F;
      this.entityData.set(DATA_SIZE_MODIFIER, Math.min(size, cap));
      this.refreshDimensions();
   }

   public ManasSkillInstance getSkill() {
      return ((CompoundTag)this.entityData.get(INSTANCE_TAG)).isEmpty() ? null : ManasSkillInstance.fromNBT((CompoundTag)this.entityData.get(INSTANCE_TAG));
   }

   public void setSkill(@Nullable ManasSkillInstance instance) {
      this.entityData.set(INSTANCE_TAG, instance == null ? new CompoundTag() : instance.toNBT());
   }

   public void setSkill(LivingEntity entity, ManasSkillInstance instance, TensuraSkill skill, int mode) {
      this.setSkill(entity, instance, skill, mode, 1.0F);
   }

   public void setSkill(LivingEntity entity, ManasSkillInstance instance, TensuraSkill skill, int mode, float costMultiplier) {
      this.setSkill(instance);
      this.setApCost(skill.getAuraCost(entity, instance, mode) * costMultiplier);
      this.setMpCost(skill.getMagiculeCost(entity, instance, mode) * costMultiplier);
   }

   public EntityDimensions getDimensions(Pose pose) {
      return super.getDimensions(pose).scale(this.getSizeModifier());
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putFloat("BlackHoleSize", this.getSizeModifier());
      tag.putFloat("MaxSize", this.maxSize);
      tag.putShort("MaxRadius", (short)this.maxRadius);
      tag.putFloat("Damage", this.damage);
      tag.putFloat("MaxDamage", this.maxDamage);
      tag.putFloat("SecondaryDamage", this.secondaryDamage);
      tag.putFloat("MaxSecondaryDamage", this.maxSecondaryDamage);
      if (this.ownerUUID != null) {
         tag.putUUID("Owner", this.ownerUUID);
      }

      if (this.getSkill() != null) {
         tag.put("skill", (Tag)this.entityData.get(INSTANCE_TAG));
      }

      tag.putInt("Mode", this.mode);
      tag.putBoolean("ElementalAttack", this.elementalAttack);
      tag.putBoolean("LimitedGriefing", this.limitedGriefing);
      tag.putDouble("APCost", this.apCost);
      tag.putDouble("MPCost", this.mpCost);
      tag.putInt("MaxLifetime", this.getMaxLifetime());
      tag.putInt("TickCount", this.tickCount);
      tag.putInt("TriggerTimer", this.triggerTimer);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.contains("BlackHoleSize")) {
         this.setSizeModifier(tag.getFloat("BlackHoleSize"));
      }

      this.setMaxSize(tag.getFloat("MaxSize"));
      this.setMaxRadius(tag.getShort("MaxRadius"));
      this.setDamage(tag.getFloat("Damage"));
      this.setMaxDamage(tag.getFloat("MaxDamage"));
      this.setSecondaryDamage(tag.getFloat("SecondaryDamage"));
      this.setMaxSecondaryDamage(tag.getFloat("MaxSecondaryDamage"));
      if (tag.hasUUID("Owner")) {
         this.ownerUUID = tag.getUUID("Owner");
      }

      if (tag.contains("skill", 10)) {
         this.entityData.set(INSTANCE_TAG, tag.getCompound("skill"));
      }

      this.setMode(tag.getInt("Mode"));
      this.setElementalAttack(tag.getBoolean("ElementalAttack"));
      this.setLimitedGriefing(tag.getBoolean("LimitedGriefing"));
      this.setApCost(tag.getDouble("APCost"));
      this.setMpCost(tag.getDouble("MPCost"));
      if (tag.contains("MaxLifetime")) {
         this.setMaxLifeTime(tag.getInt("MaxLifetime"));
      }

      if (tag.contains("TickCount")) {
         this.tickCount = tag.getInt("TickCount");
      }

      if (tag.contains("TriggerTimer")) {
         this.triggerTimer = tag.getInt("TriggerTimer");
      }
   }

   public void tick() {
      super.tick();
      double centerX = this.getX();
      double centerY = this.getY() + 0.5 * this.getSizeModifier();
      double centerZ = this.getZ();
      if (this.level().isClientSide) {
         this.spawnParticles(centerX, centerY, centerZ);
      } else {
         if (this.triggerTimer > 0) {
            this.triggerTimer--;
         } else if (this.triggerTimer == 0) {
            this.triggerTimer = -1;
            this.discard();
            this.triggerExplosion(centerX, centerY, centerZ);
            return;
         }

         if (this.tickCount >= this.getMaxLifetime()) {
            this.discard();
            this.triggerExplosion(centerX, centerY, centerZ);
            return;
         }

         this.gravity(centerX, centerY, centerZ);
      }
   }

   private void triggerExplosion(double cx, double cy, double cz) {
      float scaleMultiplier = this.getSizeModifier();
      MagicExplosion explosion = new MagicExplosion(this.level(), this.getOwner());
      explosion.setSecondaryDamage(100.0F * scaleMultiplier);
      explosion.setSize(25.0F * scaleMultiplier);
      explosion.setTickEachHit(1);
      explosion.setPos(cx, cy + 0.2, cz);
      explosion.setVisualOnly(false);
      this.level().addFreshEntity(explosion);
   }

   private void gravity(double cx, double cy, double cz) {
      float currentScale = this.getSizeModifier();
      double baseGravityRadius = Math.min(20.0 * (1.0 + (currentScale - 1.0) * 0.5), 16.0);
      double eventHorizon = Math.min(1.0 * currentScale, 16.0);
      double pullStrength = 0.12 * Math.sqrt(currentScale);
      double instantSuckRadius = Math.min(3.0 * currentScale, 16.0);
      if (this.maxRadius > 0) {
         baseGravityRadius = Math.min(baseGravityRadius, this.maxRadius);
         instantSuckRadius = Math.min(instantSuckRadius, this.maxRadius);
      }

      double livingGravityRadius = Math.min(baseGravityRadius * 1.25, 16.0);
      if (this.maxRadius > 0) {
         livingGravityRadius = Math.min(livingGravityRadius, this.maxRadius);
      }

      double searchRadius = Math.max(baseGravityRadius, livingGravityRadius);
      AABB pullZone = new AABB(cx - searchRadius, cy - searchRadius, cz - searchRadius, cx + searchRadius, cy + searchRadius, cz + searchRadius);

      for (Entity target : this.level().getEntities(this, pullZone)) {
         if (!target.isSpectator()
            && target.isAlive()
            && !(
               target instanceof LivingEntity living
                  && (living.hasInfiniteMaterials() || SkillUtils.isSkillToggled(living, (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get()))
            )
            && this.targetNotAlly(target)) {
            boolean isLiving = target instanceof LivingEntity;
            double activeRadius = isLiving ? livingGravityRadius : baseGravityRadius;
            double activePullStrength = isLiving ? pullStrength * 1.5 : pullStrength;
            double dx = cx - target.getX();
            double targetY = target instanceof FallingBlockEntity ? target.getY() + 0.25 : target.getEyeY();
            double dy = cy - targetY;
            double dz = cz - target.getZ();
            double distanceSq = dx * dx + dy * dy + dz * dz;
            double distance = Math.sqrt(distanceSq);
            if (!(distance > activeRadius) && !(distance <= 0.01)) {
               if (distance <= eventHorizon) {
                  if (target instanceof ItemEntity || target instanceof FallingBlockEntity) {
                     target.discard();
                     this.setSizeModifier(currentScale + 0.01F);
                  } else if (target instanceof LivingEntity) {
                     float primaryDmg = this.computeScaledDamage(this.damage, this.maxDamage, currentScale);
                     float secondaryDmg = this.computeScaledDamage(this.secondaryDamage, this.maxSecondaryDamage, currentScale);
                     DamageSource source = TensuraDamageTypes.getIndirectEntityDamageSource(
                           this.level(), TensuraDamageTypes.GRAVITY_ELEMENTAL, this.getOwner(), null
                        )
                        .tensura$setResistanceBypassLevel(1.0F)
                        .tensura$setBarrierBypassLevel(3.0F)
                        .tensura$setDodgeBypass();
                     DamageSource secondSource = TensuraDamageTypes.getIndirectEntityDamageSource(
                           this.level(), TensuraDamageTypes.MAGIC_GENERIC, this.getOwner(), null
                        )
                        .tensura$setResistanceBypassLevel(1.0F)
                        .tensura$setBarrierBypassLevel(3.0F)
                        .tensura$setDodgeBypass();
                     TensuraDamageHelper.hurtDouble(target, source, primaryDmg, secondSource, secondaryDmg);
                     if (!target.isAlive()) {
                        this.setSizeModifier(currentScale + 0.08F);
                     }
                  }
               } else {
                  double forceModifier = (activeRadius - distance) / activeRadius;
                  double force = forceModifier * activePullStrength;
                  if (target instanceof Player) {
                     force *= 2.5;
                  }

                  Vec3 currentVelocity = target.getDeltaMovement();
                  double orbitX = -dz / distance * 0.02 * forceModifier;
                  double orbitZ = dx / distance * 0.02 * forceModifier;
                  double motionX = currentVelocity.x + dx / distance * force + orbitX;
                  double motionY = currentVelocity.y + dy / distance * force;
                  double motionZ = currentVelocity.z + dz / distance * force + orbitZ;
                  if (target instanceof Player) {
                     motionY += dy / distance * force * 0.5;
                     if (target.onGround()) {
                        motionY += 0.4;
                     }
                  }

                  if (target instanceof FallingBlockEntity) {
                     motionY = motionY * 0.85 + dy / distance * (force * 0.5);
                  }

                  target.setDeltaMovement(motionX, motionY, motionZ);
                  target.hasImpulse = true;
                  target.hurtMarked = true;
               }
            }
         }
      }

      if (this.tickCount % 10 == 0 && this.level() instanceof ServerLevel serverLevel && this.shouldGrief()) {
         int blockRadius = (int)Math.floor(eventHorizon + 0.5);
         BlockPos centerPos = BlockPos.containing(cx, cy, cz);

         for (BlockPos pos : BlockPos.betweenClosed(
            centerPos.offset(-blockRadius, -blockRadius, -blockRadius), centerPos.offset(blockRadius, blockRadius, blockRadius)
         )) {
            BlockState state = this.level().getBlockState(pos);
            if (!state.isAir() && state.getDestroySpeed(this.level(), pos) >= 0.0F && pos.distToCenterSqr(cx, cy, cz) <= eventHorizon * eventHorizon) {
               serverLevel.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
         }

         this.ripSurroundingBlocks(serverLevel, cx, cy, cz, instantSuckRadius, eventHorizon);
      }
   }

   private float computeScaledDamage(float baseDamage, float maxDamage, float currentScale) {
      if (baseDamage <= 0.0F) {
         return 30.0F;
      }

      if (maxDamage <= baseDamage) {
         return baseDamage;
      }

      float sizeCap = this.maxSize > 0.0F ? this.maxSize : 10.0F;
      float t = (currentScale - 1.0F) / (sizeCap - 1.0F);
      t = Math.clamp(t, 0.0F, 1.0F);
      return baseDamage + t * (maxDamage - baseDamage);
   }

   private void ripSurroundingBlocks(ServerLevel world, double cx, double cy, double cz, double instantSuckRadius, double eventHorizon) {
      BlockPos centerPos = BlockPos.containing(cx, cy, cz);
      RandomSource random = world.getRandom();
      int spawnedThisCycle = 0;
      int r = (int)Math.ceil(instantSuckRadius);

      for (BlockPos targetPos : BlockPos.betweenClosed(centerPos.offset(-r, -r, -r), centerPos.offset(r, r, r))) {
         if (spawnedThisCycle >= 150) {
            break;
         }

         double distSqr = targetPos.distToCenterSqr(cx, cy, cz);
         if (!(distSqr > instantSuckRadius * instantSuckRadius) && !(distSqr <= (eventHorizon + 0.5) * (eventHorizon + 0.5))) {
            BlockState state = world.getBlockState(targetPos);
            if (!state.isAir() && !(state.getDestroySpeed(world, targetPos) < 0.0F)) {
               this.pullBlocks(world, targetPos, cx, cy, cz, 0.08);
               spawnedThisCycle++;
            }
         }
      }

      if (!this.limitedGriefing) {
         if (spawnedThisCycle < 150) {
            double currentScale = this.getSizeModifier();
            double outerRipRadius = Math.min(12.0 * currentScale, 16.0);
            if (this.maxRadius > 0) {
               outerRipRadius = Math.min(outerRipRadius, this.maxRadius);
            }

            int remainingBudget = 150 - spawnedThisCycle;
            int cap = Math.min(2, remainingBudget);
            int chippedBlocksCount = 0;

            for (int i = 0; i < 30 && chippedBlocksCount < cap; i++) {
               int rx = random.nextInt((int)outerRipRadius * 2 + 1) - (int)outerRipRadius;
               int ry = random.nextInt((int)outerRipRadius * 2 + 1) - (int)outerRipRadius;
               int rz = random.nextInt((int)outerRipRadius * 2 + 1) - (int)outerRipRadius;
               BlockPos targetPos = centerPos.offset(rx, ry, rz);
               double distSqr = targetPos.distToCenterSqr(cx, cy, cz);
               if (!(distSqr > outerRipRadius * outerRipRadius) && !(distSqr <= instantSuckRadius * instantSuckRadius)) {
                  BlockState state = world.getBlockState(targetPos);
                  if (!state.isAir() && !(state.getDestroySpeed(world, targetPos) < 0.0F)) {
                     this.pullBlocks(world, targetPos, cx, cy, cz, 0.03);
                     chippedBlocksCount++;
                  }
               }
            }
         }
      }
   }

   private void pullBlocks(ServerLevel world, BlockPos targetPos, double cx, double cy, double cz, double initialForce) {
      BlockState state = world.getBlockState(targetPos);
      if (!state.isAir() && !state.getFluidState().isSource() && !(state.getDestroySpeed(world, targetPos) < 0.0F)) {
         FallingBlockEntity fallingBlock = FallingBlockEntity.fall(world, targetPos, state);
         fallingBlock.dropItem = false;
         fallingBlock.cancelDrop = true;
         double dx = cx - fallingBlock.getX();
         double dy = cy - fallingBlock.getY();
         double dz = cz - fallingBlock.getZ();
         double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);
         if (dist > 0.001) {
            Vec3 initialPull = new Vec3(dx / dist, dy / dist, dz / dist).scale(initialForce);
            fallingBlock.setDeltaMovement(initialPull);
         }

         world.setBlockAndUpdate(targetPos, Blocks.AIR.defaultBlockState());
      }
   }

   private void spawnParticles(double cx, double cy, double cz) {
      RandomSource random = this.level().getRandom();
      float scale = this.getSizeModifier();

      for (int i = 0; i < 4; i++) {
         double angle = random.nextDouble() * Math.PI * 2.0;
         double radius = (1.0 + random.nextDouble() * 3.0) * scale;
         double height = (random.nextDouble() - 0.5) * 1.0 * scale;
         double spawnX = cx + Math.cos(angle) * radius;
         double spawnY = cy + height;
         double spawnZ = cz + Math.sin(angle) * radius;
         double dx = (cx - spawnX) * 0.06;
         double dy = (cy - spawnY) * 0.06;
         double dz = (cz - spawnZ) * 0.06;
         double swirlSpeed = 0.22 * scale / (radius + 0.2);
         double tangentX = -Math.sin(angle) * swirlSpeed;
         double tangentZ = Math.cos(angle) * swirlSpeed;
         this.level().addParticle(ParticleTypes.MYCELIUM, spawnX, spawnY, spawnZ, dx + tangentX, dy, dz + tangentZ);
      }
   }

   public void setOwner(@Nullable Entity pOwner) {
      if (pOwner != null) {
         this.ownerUUID = pOwner.getUUID();
         this.cachedOwner = pOwner;
      }
   }

   @Nullable
   public Entity getOwner() {
      if (this.cachedOwner != null && !this.cachedOwner.isRemoved()) {
         return this.cachedOwner;
      } else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverLevel) {
         this.cachedOwner = serverLevel.getEntity(this.ownerUUID);
         return this.cachedOwner;
      } else {
         return null;
      }
   }

   protected boolean ownedBy(Entity pEntity) {
      return pEntity.getUUID().equals(this.ownerUUID);
   }

   public void setMaxRadius(int pLife) {
      this.maxRadius = pLife;
   }

   public int getMaxRadius() {
      return this.maxRadius;
   }

   public void setMaxLifeTime(int pLife) {
      this.entityData.set(DATA_MAX_LIFETIME, pLife);
   }

   public int getMaxLifetime() {
      return (Integer)this.entityData.get(DATA_MAX_LIFETIME);
   }

   public float getMaxSize() {
      return this.maxSize;
   }

   public void setMaxSize(float maxSize) {
      this.maxSize = maxSize;
   }

   public float getDamage() {
      return this.damage;
   }

   public void setDamage(float damage) {
      this.damage = damage;
   }

   public float getSecondaryDamage() {
      return this.secondaryDamage;
   }

   public void setSecondaryDamage(float secondaryDamage) {
      this.secondaryDamage = secondaryDamage;
   }

   public int getTriggerTimer() {
      return this.triggerTimer;
   }

   public double getApCost() {
      return this.apCost;
   }

   public void setApCost(double apCost) {
      this.apCost = apCost;
   }

   public double getMpCost() {
      return this.mpCost;
   }

   public void setMpCost(double mpCost) {
      this.mpCost = mpCost;
   }

   public int getMode() {
      return this.mode;
   }

   public void setMode(int mode) {
      this.mode = mode;
   }

   public boolean isElementalAttack() {
      return this.elementalAttack;
   }

   public void setElementalAttack(boolean elementalAttack) {
      this.elementalAttack = elementalAttack;
   }

   public boolean isLimitedGriefing() {
      return this.limitedGriefing;
   }

   public void setLimitedGriefing(boolean limitedGriefing) {
      this.limitedGriefing = limitedGriefing;
   }

   protected boolean shouldGrief() {
      return this.getOwner() != null && !this.getOwner().getType().equals(EntityType.PLAYER)
         ? this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)
         : TensuraGameRules.canSkillGrief(this.level());
   }

   public boolean targetNotAlly(Entity entity) {
      if (this.getOwner() == null) {
         return true;
      } else {
         return entity.isAlliedTo(this.getOwner()) ? false : entity != this.getOwner();
      }
   }

   public float getMaxDamage() {
      return this.maxDamage;
   }

   public void setMaxDamage(float maxDamage) {
      this.maxDamage = maxDamage;
   }

   public float getMaxSecondaryDamage() {
      return this.maxSecondaryDamage;
   }

   public void setMaxSecondaryDamage(float maxSecondaryDamage) {
      this.maxSecondaryDamage = maxSecondaryDamage;
   }
}
