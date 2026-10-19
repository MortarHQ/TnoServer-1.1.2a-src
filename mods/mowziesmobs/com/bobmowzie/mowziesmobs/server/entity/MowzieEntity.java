package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.IntermittentAnimation;
import com.bobmowzie.mowziesmobs.client.sound.BossMusic;
import com.bobmowzie.mowziesmobs.client.sound.BossMusicPlayer;
import com.bobmowzie.mowziesmobs.server.ai.Cooldown;
import com.bobmowzie.mowziesmobs.server.bossinfo.MMBossInfoServer;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.world.spawn.SpawnHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public abstract class MowzieEntity extends PathfinderMob implements IEntityWithComplexSpawn, IntermittentAnimatableEntity {
   private static final byte START_IA_HEALTH_UPDATE_ID = 4;
   private static final byte MUSIC_PLAY_ID = 67;
   private static final byte MUSIC_STOP_ID = 68;
   public int frame;
   public float targetDistance = -1.0F;
   public float targetAngle = -1.0F;
   public boolean active;
   public LivingEntity blockingEntity = null;
   public boolean playsHurtAnimation = true;
   protected boolean dropAfterDeathAnim = true;
   public boolean hurtInterruptsAnimation = false;
   private final List<IntermittentAnimation<?>> intermittentAnimations = new ArrayList<>();
   public Vec3[] socketPosArray;
   protected boolean prevOnGround;
   protected boolean prevPrevOnGround;
   protected boolean willLandSoon;
   protected Vec3 prevDeltaMovement = Vec3.ZERO;
   private int killDataRecentlyHit;
   private DamageSource killDataCause;
   private Player killDataAttackingPlayer;
   protected final MMBossInfoServer bossInfo = this.initBossInfo();
   private static final ResourceLocation HEALTH_CONFIG_MODIFIER = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "health_config_modifier");
   private static final ResourceLocation ATTACK_CONFIG_MODIFIER = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "attack_config_modifier");
   private static final EntityDataAccessor<Boolean> STRAFING = SynchedEntityData.defineId(MowzieEntity.class, EntityDataSerializers.BOOLEAN);
   public boolean renderingInGUI = false;
   public Cooldown[] cooldowns;

   public MowzieEntity(EntityType<? extends MowzieEntity> type, Level world) {
      super(type, world);
      if (world.isClientSide) {
         this.socketPosArray = new Vec3[0];
      }

      ConfigHandler.CombatConfig combatConfig = this.getCombatConfig();
      if (combatConfig != null) {
         AttributeInstance maxHealthAttr = this.getAttribute(Attributes.MAX_HEALTH);
         if (maxHealthAttr != null) {
            double difference = maxHealthAttr.getBaseValue() * (Double)this.getCombatConfig().healthMultiplier.get() - maxHealthAttr.getBaseValue();
            maxHealthAttr.addTransientModifier(new AttributeModifier(HEALTH_CONFIG_MODIFIER, difference, Operation.ADD_VALUE));
            this.setHealth(this.getMaxHealth());
         }

         AttributeInstance attackDamageAttr = this.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attackDamageAttr != null) {
            double difference = attackDamageAttr.getBaseValue() * (Double)this.getCombatConfig().attackMultiplier.get() - attackDamageAttr.getBaseValue();
            attackDamageAttr.addTransientModifier(new AttributeModifier(ATTACK_CONFIG_MODIFIER, difference, Operation.ADD_VALUE));
         }
      }
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(STRAFING, false);
   }

   public void setStrafing(boolean strafing) {
      this.entityData.set(STRAFING, strafing);
      if (!strafing) {
         this.setXxa(0.0F);
      }
   }

   public boolean isStrafing() {
      return (Boolean)this.entityData.get(STRAFING);
   }

   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return null;
   }

   protected ConfigHandler.CombatConfig getCombatConfig() {
      return null;
   }

   public static boolean spawnPredicate(EntityType type, LevelAccessor world, MobSpawnType reason, BlockPos spawnPos, RandomSource rand) {
      if (!(world instanceof ServerLevelAccessor)) {
         return false;
      }

      ConfigHandler.SpawnConfig spawnConfig = SpawnHandler.SPAWN_CONFIGS.get(type);
      if (spawnConfig != null) {
         if (rand.nextDouble() > (Double)spawnConfig.extraRarity.get()) {
            return false;
         }

         if (reason != MobSpawnType.SPAWNER && world instanceof ServerLevel serverLevel) {
            List<? extends String> dimensionNames = (List<? extends String>)spawnConfig.dimensions.get();
            if (serverLevel.dimension() != null) {
               ResourceLocation currDimensionName = serverLevel.dimension().location();
               if (!dimensionNames.contains(currDimensionName.toString())) {
                  return false;
               }
            }
         }

         float heightMax = ((Integer)spawnConfig.heightMax.get()).intValue();
         float heightMin = ((Integer)spawnConfig.heightMin.get()).intValue();
         if (spawnPos.getY() > heightMax && heightMax >= -64.0F) {
            return false;
         }

         if (spawnPos.getY() < heightMin && heightMin >= -64.0F) {
            return false;
         }

         if ((Boolean)spawnConfig.needsDarkness.get() && !Monster.isDarkEnoughToSpawn((ServerLevelAccessor)world, spawnPos, rand)) {
            return false;
         }

         BlockState block = world.getBlockState(spawnPos.below());
         ResourceLocation blockName = block.getBlock().builtInRegistryHolder().key().location();
         List<? extends String> allowedBlocks = (List<? extends String>)spawnConfig.allowedBlocks.get();
         List<? extends String> allowedBlockTags = (List<? extends String>)spawnConfig.allowedBlockTags.get();
         if (blockName == null) {
            return false;
         }

         if (allowedBlocks.isEmpty() && allowedBlockTags.isEmpty()) {
            if (!block.isValidSpawn(world, spawnPos.below(), type)) {
               return false;
            }
         } else {
            boolean isBlockAllowed = false;
            if (!allowedBlocks.isEmpty() && (allowedBlocks.contains(blockName.toString()) || allowedBlocks.contains(blockName.getPath()))) {
               isBlockAllowed = true;
            }

            if (!isBlockAllowed && !allowedBlockTags.isEmpty() && isBlockTagAllowed(allowedBlockTags, block)) {
               isBlockAllowed = true;
            }

            if (!isBlockAllowed) {
               return false;
            }
         }

         if ((Boolean)spawnConfig.needsSeeSky.get() && !world.canSeeSkyFromBelowWater(spawnPos)) {
            return false;
         }

         if ((Boolean)spawnConfig.needsCantSeeSky.get() && world.canSeeSkyFromBelowWater(spawnPos)) {
            return false;
         }

         List<? extends String> avoidStructures = (List<? extends String>)spawnConfig.avoidStructures.get();
         Registry<StructureSet> structureSetRegistry = world.registryAccess().registryOrThrow(Registries.STRUCTURE_SET);
         ServerLevel serverLevel = (ServerLevel)world;
         ChunkGeneratorStructureState generatorState = serverLevel.getChunkSource().getGeneratorState();
         ChunkPos chunkPos = new ChunkPos(spawnPos);

         for (String structureName : avoidStructures) {
            Optional<StructureSet> structureSetOptional = structureSetRegistry.getOptional(ResourceLocation.tryParse(structureName));
            if (!structureSetOptional.isEmpty()) {
               Optional<ResourceKey<StructureSet>> resourceKeyOptional = structureSetRegistry.getResourceKey(structureSetOptional.get());
               if (!resourceKeyOptional.isEmpty()) {
                  Optional<Reference<StructureSet>> holderOptional = structureSetRegistry.getHolder(resourceKeyOptional.get());
                  if (!holderOptional.isEmpty() && generatorState.hasStructureChunkInRange((Holder)holderOptional.get(), chunkPos.x, chunkPos.z, 3)) {
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   private static boolean isBlockTagAllowed(List<? extends String> allowedBlockTags, BlockState block) {
      for (String allowedBlockTag : allowedBlockTags) {
         ResourceLocation location = ResourceLocation.tryParse(allowedBlockTag);
         if (location != null) {
            TagKey<Block> tagKey = TagKey.create(Registries.BLOCK, location);
            if (block.is(tagKey)) {
               return true;
            }
         }
      }

      return false;
   }

   protected boolean isWithinDistance(BlockPos pos, int distance) {
      return pos.closerThan(this.blockPosition(), distance);
   }

   public void checkDespawn() {
      if (!EventHooks.checkMobDespawn(this)) {
         if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
         } else if (!this.isPersistenceRequired() && !this.requiresCustomPersistence()) {
            Entity entity = this.level().getNearestPlayer(this, -1.0);
            if (entity != null) {
               double distance = entity.distanceToSqr(this);
               int despawnDistance = this.getDespawnDistance();
               int despawnRadius = despawnDistance * despawnDistance;
               if (distance > despawnRadius && this.removeWhenFarAway(distance)) {
                  this.discard();
               }

               int noDespawnDistance = this.getNoDespawnDistance();
               int noDespawnRadius = noDespawnDistance * noDespawnDistance;
               if (this.noActionTime > 600 && this.random.nextInt(800) == 0 && distance > noDespawnRadius && this.removeWhenFarAway(distance)) {
                  this.discard();
               } else if (distance < noDespawnRadius) {
                  this.noActionTime = 0;
               }
            }
         } else {
            this.noActionTime = 0;
         }
      }
   }

   public int getDespawnDistance() {
      return this.getType().getCategory().getDespawnDistance();
   }

   public int getNoDespawnDistance() {
      return this.getType().getCategory().getNoDespawnDistance();
   }

   public Vec3 getPrevDeltaMovement() {
      return this.prevDeltaMovement;
   }

   public void tick() {
      this.prevPrevOnGround = this.prevOnGround;
      this.prevOnGround = this.onGround();
      this.prevDeltaMovement = this.getDeltaMovement();
      super.tick();
      this.frame++;
      if (this.tickCount % 4 == 0) {
         this.bossInfo.update();
      }

      if (this.getTarget() != null) {
         this.targetDistance = this.distanceTo(this.getTarget()) - this.getTarget().getBbWidth() / 2.0F;
         this.targetAngle = (float)this.getAngleBetweenEntities(this, this.getTarget());
      }

      this.willLandSoon = !this.onGround() && this.level().noCollision(this.getBoundingBox().move(this.getDeltaMovement()));
      if (!this.level().isClientSide && this.hasBossMusic()) {
         if (this.canPlayMusic()) {
            this.level().broadcastEntityEvent(this, (byte)67);
         } else {
            this.level().broadcastEntityEvent(this, (byte)68);
         }
      }

      if (this.cooldowns != null) {
         for (Cooldown cooldown : this.cooldowns) {
            cooldown.tick();
         }
      }
   }

   protected boolean canPlayMusic() {
      return !this.isSilent() && this.getTarget() instanceof Player;
   }

   public boolean canPlayerHearMusic(Player player) {
      return player != null && this.canAttack(player) && this.distanceTo(player) < 2500.0F;
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
   }

   public void writeSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
   }

   public void readSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
      this.yRotO = this.getYRot();
      this.yBodyRotO = this.yBodyRot = this.yHeadRotO = this.yHeadRot;
   }

   public boolean doHurtTarget(Entity entityIn) {
      return this.doHurtTarget(entityIn, 1.0F, 1.0F);
   }

   public boolean doHurtTarget(Entity entityIn, float damageMultiplier, float applyKnockbackMultiplier) {
      return this.doHurtTarget(entityIn, damageMultiplier, applyKnockbackMultiplier, false);
   }

   public boolean doHurtTarget(Entity target, float damageMultiplier, float applyKnockbackMultiplier, boolean canDisableShield) {
      float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damageMultiplier;
      DamageSource damagesource = this.damageSources().mobAttack(this);
      if (this.level() instanceof ServerLevel serverLevel) {
         damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, damagesource, damage);
      }

      boolean wasHurt = target.hurt(damagesource, damage);
      if (wasHurt) {
         if (target instanceof LivingEntity livingTarget) {
            float knockback = this.getKnockback(livingTarget, damagesource) * applyKnockbackMultiplier;
            if (knockback > 0.0F) {
               livingTarget.knockback(
                  knockback * 0.5F, Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(this.getYRot() * (float) (Math.PI / 180.0))
               );
               this.setDeltaMovement(this.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }
         }

         if (this.level() instanceof ServerLevel serverLevel) {
            EnchantmentHelper.doPostAttackEffects(serverLevel, target, damagesource);
         }

         this.setLastHurtMob(target);
         this.playAttackSound();
      }

      return wasHurt;
   }

   public float getHealthRatio() {
      return this.getHealth() / this.getMaxHealth();
   }

   public double getAngleBetweenEntities(Entity first, Entity second) {
      return Math.atan2(second.getZ() - first.getZ(), second.getX() - first.getX()) * (180.0 / Math.PI) + 90.0;
   }

   public double getDotProductBodyFacingEntity(Entity second) {
      Vec3 vecBetween = second.position().subtract(this.position());
      vecBetween = vecBetween.normalize();
      return vecBetween.dot(Vec3.directionFromRotation(0.0F, this.yBodyRot).normalize());
   }

   public List<Player> getPlayersNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> nearbyEntities = this.level().getEntities(this, this.getBoundingBox().inflate(distanceX, distanceY, distanceZ));
      return nearbyEntities.stream()
         .filter(entityNeighbor -> entityNeighbor instanceof Player && this.distanceTo(entityNeighbor) <= radius + entityNeighbor.getBbWidth() / 2.0F)
         .map(entityNeighbor -> (Player)entityNeighbor)
         .collect(Collectors.toList());
   }

   public List<LivingEntity> getAttackableEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> nearbyEntities = this.level().getEntities(this, this.getBoundingBox().inflate(distanceX, distanceY, distanceZ));
      return nearbyEntities.stream()
         .filter(
            entityNeighbor -> entityNeighbor instanceof LivingEntity
               && ((LivingEntity)entityNeighbor).attackable()
               && (!(entityNeighbor instanceof Player) || !((Player)entityNeighbor).isCreative())
               && this.distanceTo(entityNeighbor) <= radius + entityNeighbor.getBbWidth() / 2.0F
         )
         .map(entityNeighbor -> (LivingEntity)entityNeighbor)
         .collect(Collectors.toList());
   }

   public List<LivingEntity> getEntityLivingBaseNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      return this.getEntitiesNearby(LivingEntity.class, distanceX, distanceY, distanceZ, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
      return this.level()
         .getEntitiesOfClass(entityClass, this.getBoundingBox().inflate(r, r, r), e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double dX, double dY, double dZ, double r) {
      return this.level()
         .getEntitiesOfClass(
            entityClass,
            this.getBoundingBox().inflate(dX, dY, dZ),
            e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F && e.getY() <= this.getY() + dY
         );
   }

   protected void tickDeath() {
      this.deathTime++;
      int deathDuration = this.getDeathDuration();
      if (this.deathTime >= deathDuration && this.level() instanceof ServerLevel serverLevel) {
         this.lastHurtByPlayer = this.killDataAttackingPlayer;
         this.lastHurtByPlayerTime = this.killDataRecentlyHit;
         if (this.dropAfterDeathAnim && this.killDataCause != null) {
            this.dropAllDeathLoot(serverLevel, this.killDataCause);
         }

         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   protected abstract int getDeathDuration();

   protected void dropAllDeathLoot(@NotNull ServerLevel level, @NotNull DamageSource source) {
      if (!this.dropAfterDeathAnim || this.deathTime > 0) {
         super.dropAllDeathLoot(level, source);
      }
   }

   public void die(DamageSource cause) {
      if (!this.dead) {
         this.killDataCause = cause;
         this.killDataRecentlyHit = this.lastHurtByPlayerTime;
         this.killDataAttackingPlayer = this.lastHurtByPlayer;
      }

      super.die(cause);
      if (!this.isRemoved()) {
         this.bossInfo.update();
      }
   }

   protected void addIntermittentAnimation(IntermittentAnimation animation) {
      animation.setID((byte)this.intermittentAnimations.size());
      this.intermittentAnimations.add(animation);
   }

   public void handleEntityEvent(byte id) {
      if (id >= 4 && id - 4 < this.intermittentAnimations.size()) {
         this.intermittentAnimations.get(id - 4).start();
      } else if (id == 67) {
         BossMusicPlayer.requestBossMusic(this);
      } else if (id == 68) {
         BossMusicPlayer.stopBossMusic(this);
      } else {
         super.handleEntityEvent(id);
      }
   }

   @Override
   public byte getOffsetEntityState() {
      return 4;
   }

   public Vec3 circleEntityPosition(Entity target, float radius, float speed, boolean direction, int circleFrame, float offset) {
      int directionInt = direction ? 1 : -1;
      double t = directionInt * circleFrame * 0.5 * speed / radius + offset;
      return target.position().add(radius * Math.cos(t), 0.0, radius * Math.sin(t));
   }

   protected void repelEntities(float x, float y, float z, float radius) {
      for (Entity entity : this.getEntityLivingBaseNearby(x, y, z, radius)) {
         if (entity.isPickable() && !entity.noPhysics) {
            double angle = (this.getAngleBetweenEntities(this, entity) + 90.0) * Math.PI / 180.0;
            entity.setDeltaMovement(-0.1 * Math.cos(angle), entity.getDeltaMovement().y, -0.1 * Math.sin(angle));
         }
      }
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossInfo.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossInfo.removePlayer(player);
   }

   public void load(CompoundTag compound) {
      super.load(compound);
      if (this.hasCustomName()) {
         this.bossInfo.setName(this.getDisplayName());
      }
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.cooldowns != null) {
         for (Cooldown cooldown : this.cooldowns) {
            compound.putInt(cooldown.getName(), cooldown.getTimer());
         }
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (this.cooldowns != null) {
         for (Cooldown cooldown : this.cooldowns) {
            cooldown.setTimer(compound.getInt(cooldown.getName()));
         }
      }
   }

   public void setCustomName(Component name) {
      super.setCustomName(name);
      this.bossInfo.setName(this.getDisplayName());
   }

   public boolean hasBossBar() {
      return false;
   }

   protected MMBossInfoServer initBossInfo() {
      return new MMBossInfoServer(this);
   }

   public boolean resetHealthOnPlayerRespawn() {
      return false;
   }

   public BossBarColor bossBarColor() {
      return BossBarColor.PURPLE;
   }

   public void setSocketPosArray(int index, Vec3 pos) {
      if (this.socketPosArray != null && this.socketPosArray.length > index) {
         this.socketPosArray[index] = pos;
      }
   }

   public boolean canBePushedByEntity(Entity entity) {
      return true;
   }

   public void push(Entity entityIn) {
      if (!this.isSleeping() && !this.isPassengerOfSameVehicle(entityIn) && !entityIn.noPhysics && !this.noPhysics) {
         double d0 = entityIn.getX() - this.getX();
         double d1 = entityIn.getZ() - this.getZ();
         double d2 = Mth.absMax(d0, d1);
         if (d2 >= 0.01F) {
            d2 = Math.sqrt(d2);
            d0 /= d2;
            d1 /= d2;
            double d3 = 1.0 / d2;
            if (d3 > 1.0) {
               d3 = 1.0;
            }

            d0 *= d3;
            d1 *= d3;
            d0 *= 0.05F;
            d1 *= 0.05F;
            if (!this.isVehicle() && this.canBePushedByEntity(entityIn)) {
               this.push(-d0, 0.0, -d1);
            }

            if (!entityIn.isVehicle()) {
               entityIn.push(d0, 0.0, d1);
            }
         }
      }
   }

   public boolean hasBossMusic() {
      return false;
   }

   public BossMusic<?> getBossMusic() {
      return null;
   }

   public void playSound(List<DeferredHolder<SoundEvent, SoundEvent>> sounds, float volume, float pitch) {
      SoundEvent sound = (SoundEvent)sounds.get(this.random.nextInt(sounds.size())).get();
      this.playSound(sound, volume, pitch);
   }
}
