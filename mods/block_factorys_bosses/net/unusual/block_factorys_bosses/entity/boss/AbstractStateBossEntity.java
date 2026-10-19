package net.unusual.block_factorys_bosses.entity.boss;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.SlowRotMoveControl;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.unusual.block_factorys_bosses.network.ShutUpPacket;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;
import net.unusual.block_factorys_bosses.state.StateController;
import net.unusual.block_factorys_bosses.util.BossHandling;
import net.unusual.block_factorys_bosses.util.RiseUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractStateBossEntity extends Monster implements GeoEntity, IParticleAttachment {
   public static final EntityDataAccessor<Integer> DATA_BOSS_PHASE = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_PHASE = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_COOLDOWN = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_HIT_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_BATTLE_TIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DIE_ANIMTIME = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<String> DATA_SOURCE_UUID = SynchedEntityData.defineId(AbstractStateBossEntity.class, EntityDataSerializers.STRING);
   public static final EntityDataAccessor<Boolean> DATA_CINEMATIC_BLACK_SCREEN = SynchedEntityData.defineId(
      AbstractStateBossEntity.class, EntityDataSerializers.BOOLEAN
   );
   public static final int INTRO_PHASE = -1;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   protected final ServerBossEvent bossEvent;

   protected AbstractStateBossEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 0;
      this.setNoAi(false);
      this.setPersistenceRequired();
      this.moveControl = new SlowRotMoveControl(this);
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
      this.bossEvent = this.createBossEvent();
   }

   public abstract <T extends AbstractStateBossEntity> StateController<T> getStateController();

   protected abstract ServerBossEvent createBossEvent();

   protected abstract int getBossType();

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if (BossHandling.TRACKED_BOSSES.get(this.bossEvent.getId()) == null) {
         BossHandling.TRACKED_BOSSES.put(this.bossEvent.getId(), new BossHandling.TrackedBoss(this.getBossType(), true));
      }

      this.bossEvent.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   public void remove(RemovalReason reason) {
      BossHandling.TRACKED_BOSSES.remove(this.bossEvent.getId());
      super.remove(reason);
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   public void setPlayingMusic(boolean music) {
      if (!this.level().isClientSide) {
         BossHandling.TrackedBoss boss = BossHandling.TRACKED_BOSSES.get(this.bossEvent.getId());
         if (boss != null && boss.playingMusic() != music) {
            BossHandling.TrackedBoss augmented = boss.setMusic(music);
            BossHandling.TRACKED_BOSSES.put(this.bossEvent.getId(), augmented);
            this.bossEvent.getPlayers().forEach(player -> player.connection.send(new UpdateBossBarTypeMessage(this.bossEvent.getId(), augmented)));
         }
      }
   }

   public void setBarVisible(boolean visible) {
      if (!this.level().isClientSide) {
         this.bossEvent.setVisible(visible);
      }
   }

   public void onBossSpawnerSpawn() {
   }

   protected Vec3 toVec3(Vector3d vec) {
      return new Vec3(vec.x, vec.y, vec.z);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_BOSS_PHASE, 0);
      builder.define(DATA_ATTACK_PHASE, 0);
      builder.define(DATA_SPAWN_ANIMTIME, 0);
      builder.define(DATA_ATTACK_COOLDOWN, 0);
      builder.define(DATA_ATTACK_ANIMTIME, 0);
      builder.define(DATA_HIT_ANIMTIME, 0);
      builder.define(DATA_BATTLE_TIME, 0);
      builder.define(DATA_DIE_ANIMTIME, 0);
      builder.define(DATA_SOURCE_UUID, "");
      builder.define(DATA_CINEMATIC_BLACK_SCREEN, false);
   }

   public int getBossPhase() {
      return (Integer)this.getEntityData().get(DATA_BOSS_PHASE);
   }

   public void setBossPhase(int phase) {
      this.getEntityData().set(DATA_BOSS_PHASE, phase);
   }

   public void incrementBattleTime() {
      this.entityData.set(DATA_BATTLE_TIME, (Integer)this.entityData.get(DATA_BATTLE_TIME) + 1);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      this.getStateController().addAdditionalSaveData(compound);
      compound.putInt("BossPhase", (Integer)this.entityData.get(DATA_BOSS_PHASE));
      compound.putInt("AttackPhase", (Integer)this.entityData.get(DATA_ATTACK_PHASE));
      compound.putInt("SpawnAnimtime", (Integer)this.entityData.get(DATA_SPAWN_ANIMTIME));
      compound.putInt("AttackCooldown", (Integer)this.entityData.get(DATA_ATTACK_COOLDOWN));
      compound.putInt("AttackAnimtime", (Integer)this.entityData.get(DATA_ATTACK_ANIMTIME));
      compound.putInt("BattleTime", (Integer)this.entityData.get(DATA_BATTLE_TIME));
      compound.putInt("DieAnimtime", (Integer)this.entityData.get(DATA_DIE_ANIMTIME));
      compound.putString("SourceUUID", (String)this.entityData.get(DATA_SOURCE_UUID));
      compound.putBoolean("CinematicBlack", (Boolean)this.entityData.get(DATA_CINEMATIC_BLACK_SCREEN));
      compound.putBoolean("BossBarVisible", this.bossEvent.isVisible());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.getStateController().readAdditionalSaveData(compound);
      if (compound.contains("BossPhase")) {
         this.entityData.set(DATA_BOSS_PHASE, compound.getInt("BossPhase"));
         this.entityData.set(DATA_ATTACK_PHASE, compound.getInt("AttackPhase"));
         this.entityData.set(DATA_SPAWN_ANIMTIME, compound.getInt("SpawnAnimtime"));
         this.entityData.set(DATA_ATTACK_COOLDOWN, compound.getInt("AttackCooldown"));
         this.entityData.set(DATA_ATTACK_ANIMTIME, compound.getInt("AttackAnimtime"));
         this.entityData.set(DATA_BATTLE_TIME, compound.getInt("BattleTime"));
         this.entityData.set(DATA_DIE_ANIMTIME, compound.getInt("DieAnimtime"));
         this.entityData.set(DATA_SOURCE_UUID, compound.getString("SourceUUID"));
         this.entityData.set(DATA_CINEMATIC_BLACK_SCREEN, compound.getBoolean("CinematicBlack"));
         RiseUtil.ifBoolPresent(compound, "BossBarVisible", this.bossEvent::setVisible);
      }
   }

   public void tick() {
      super.tick();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public boolean getCinematicBlackScreen() {
      return (Boolean)this.getEntityData().get(DATA_CINEMATIC_BLACK_SCREEN);
   }

   public void setCinematicBlackScreen(boolean enabled) {
      this.getEntityData().set(DATA_CINEMATIC_BLACK_SCREEN, enabled);
   }

   public int getAnimTransitionTime() {
      return 0;
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public static void init(RegisterSpawnPlacementsEvent event) {
   }

   public void playSound(SoundEvent sound, float volume, float pitch) {
      if (!this.isSilent()) {
         this.stopSoundForNearbyPlayers(sound);
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), sound, SoundSource.HOSTILE, volume, pitch);
      }
   }

   public void playSound(SoundEvent sound) {
      this.playSound(sound, 8.0F, 1.0F);
   }

   public SoundEvent getDeathSound() {
      return (SoundEvent)BossesRiseSounds.SILENCE.value();
   }

   protected abstract ResourceLocation getNoHitAdvancement();

   protected abstract ResourceLocation getKillAdvancement();

   public double getHealthRatio() {
      return this.getHealth() / this.getMaxHealth();
   }

   public void log(Object message) {
      if (!this.level().isClientSide() && !FMLEnvironment.production) {
         BossesRise.LOGGER.debug(message);
      }
   }

   protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
      super.actuallyHurt(damageSource, damageAmount);
      this.maybeCancelDeath(damageSource);
   }

   protected void maybeCancelDeath(DamageSource damageContainer) {
      if (this.isDeadOrDying()) {
         if (!damageContainer.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (this.shouldCancelDeath()) {
               if (this.isDeadOrDying()) {
                  this.setHealth(0.1F);
               }

               this.cancelDeath();
            }
         }
      }
   }

   public boolean shouldCancelDeath() {
      return false;
   }

   protected void cancelDeath() {
   }

   public void die(DamageSource source) {
      super.die(source);
      if (this.level() instanceof ServerLevel world) {
         for (Player p : world.getNearbyPlayers(TargetingConditions.DEFAULT, this, AABB.ofSize(this.position(), 256.0, 256.0, 256.0))) {
            ServerPlayer player = (ServerPlayer)p;
            PlayerAdvancements playerAdvancements = player.getAdvancements();
            ServerAdvancementManager advancementManager = world.getServer().getAdvancements();
            AdvancementHolder killBossUnderMinute = advancementManager.get(BossesRise.prefix("kill_boss_under_minute"));
            AdvancementHolder bossNoHit = advancementManager.get(this.getNoHitAdvancement());
            AdvancementHolder killAdvancement = advancementManager.get(this.getKillAdvancement());
            if (killBossUnderMinute != null && (Integer)this.getEntityData().get(DATA_BATTLE_TIME) <= 1200) {
               AdvancementProgress progress = playerAdvancements.getOrStartProgress(killBossUnderMinute);
               if (!progress.isDone()) {
                  for (String criteria : progress.getRemainingCriteria()) {
                     playerAdvancements.award(killBossUnderMinute, criteria);
                  }
               }
            }

            PlayerVariables playerVars = (PlayerVariables)player.getData(BossesRiseDataAttachments.PLAYER_VARIABLES);
            if (bossNoHit != null && playerVars.boss_no_hit) {
               AdvancementProgress progress = playerAdvancements.getOrStartProgress(bossNoHit);
               if (!progress.isDone()) {
                  for (String criteria : progress.getRemainingCriteria()) {
                     playerAdvancements.award(bossNoHit, criteria);
                  }
               }
            }

            if (killAdvancement != null) {
               AdvancementProgress progress = playerAdvancements.getOrStartProgress(killAdvancement);
               if (!progress.isDone()) {
                  for (String criteria : progress.getRemainingCriteria()) {
                     playerAdvancements.award(killAdvancement, criteria);
                  }
               }
            }

            playerVars.boss_no_hit = true;
            playerVars.syncPlayerVariables(player);
         }

         this.discard();
      }
   }

   @Nullable
   public ItemEntity spawnAtLocation(ItemStack itemStack, float yOffset) {
      ItemEntity itemEntity = super.spawnAtLocation(itemStack, yOffset);
      if (itemEntity != null && itemStack.is(BossesRiseTags.Items.GLOWING_LOOT)) {
         itemEntity.setGlowingTag(true);
      }

      return itemEntity;
   }

   public void lookAtTarget() {
      if (this.getTarget() instanceof LivingEntity) {
         LivingEntity target = this.getTarget();
         this.lookAt(Anchor.EYES, new Vec3(target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ()));
      }
   }

   public void forEachNearbyEntity(double aabbSize, Vec3 offset, Predicate<Entity> filter, Consumer<LivingEntity> callback) {
      this.level()
         .getEntitiesOfClass(LivingEntity.class, AABB.ofSize(this.position().add(offset), aabbSize, aabbSize, aabbSize), e -> e != this && filter.test(e))
         .forEach(callback);
   }

   public void forEachNearbyEntity(double aabbSize, Vec3 offset, Consumer<LivingEntity> callback) {
      this.forEachNearbyEntity(aabbSize, offset, e -> true, callback);
   }

   public void forEachNearbyEntity(double aabbSize, Consumer<LivingEntity> callback) {
      this.forEachNearbyEntity(aabbSize, Vec3.ZERO, e -> true, callback);
   }

   public void forEachNearbyPlayer(double aabbSize, Consumer<Player> callback) {
      this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.position(), aabbSize, aabbSize, aabbSize)).forEach(callback);
   }

   protected void stopSoundForNearbyPlayers(SoundEvent sound) {
      if (this.level() instanceof ServerLevel serverLevel) {
         if (BossesRiseSounds.CANCELABLE_SOUNDS.contains(sound.getLocation())) {
            ShutUpPacket shutUpPacket = new ShutUpPacket();
            serverLevel.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0)).forEach(player -> player.connection.send(shutUpPacket));
         }
      }
   }

   protected List<Player> getNearbyPlayers(double aabbSize) {
      return this.level().getEntitiesOfClass(Player.class, AABB.ofSize(this.position(), aabbSize, aabbSize, aabbSize));
   }

   public boolean attackEntity(Entity target, float damageScale) {
      return this.attackEntity(target, damageScale, this.damageSources().mobAttack(this));
   }

   public boolean attackEntity(Entity target, float damageScale, DamageSource damageSource) {
      if (damageScale <= 0.0F) {
         return false;
      }

      ServerLevel serverLevel = this.level() instanceof ServerLevel level ? level : null;
      float damage = damageScale * (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
      if (serverLevel != null) {
         damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), target, damageSource, damage);
      }

      if (!target.hurt(damageSource, damage)) {
         return false;
      }

      if (serverLevel != null) {
         EnchantmentHelper.doPostAttackEffects(serverLevel, target, damageSource);
      }

      this.setLastHurtMob(target);
      return true;
   }

   public void meleeAttack(double aabbSize, double reach, double pushForce, float damageScale, Consumer<LivingEntity> callback) {
      this.forEachNearbyEntity(aabbSize, this.getLookOffset(reach), entity -> {
         if (this.attackEntity(entity, damageScale)) {
            Vec3 pushForceVec = entity.position().subtract(this.position()).normalize().scale(pushForce);
            entity.push(pushForceVec.x, 0.2, pushForceVec.z);
            callback.accept(entity);
         }
      });
   }

   public void meleeAttack(double aabbSize, double reach, double pushForce, Consumer<LivingEntity> callback) {
      this.meleeAttack(aabbSize, reach, pushForce, 1.0F, callback);
   }

   public void meleeAttack(double aabbSize, double reach, double pushForce, float damageScale) {
      this.meleeAttack(aabbSize, reach, pushForce, damageScale, e -> {});
   }

   public void meleeAttack(double aabbSize, double reach, double pushForce) {
      this.meleeAttack(aabbSize, reach, pushForce, 1.0F, e -> {});
   }

   public Vec3 getLookOffset(double length, double height) {
      float f = -this.getYRot() * (float) (Math.PI / 180.0);
      float x = Mth.sin(f);
      float z = Mth.cos(f);
      return new Vec3(x * length, height, z * length);
   }

   public Vec3 getLookOffset(double length) {
      return this.getLookOffset(length, 1.5);
   }

   public boolean isTargetNear(double aabbSize) {
      return this.getTarget() instanceof LivingEntity && AABB.ofSize(this.position(), aabbSize, aabbSize, aabbSize).contains(this.getTarget().position());
   }

   public LivingEntity getSourceEntity() {
      try {
         if (this.level() instanceof ServerLevel level
            && level.getEntity(UUID.fromString((String)this.entityData.get(DATA_SOURCE_UUID))) instanceof LivingEntity entity) {
            return entity;
         }
      } catch (Exception var4) {
      }

      return (LivingEntity)(this.getTarget() instanceof LivingEntity ? this.getTarget() : this);
   }

   public void simulatePlayerKill() {
      this.hurt(new DamageSource(this.level().holderOrThrow(DamageTypes.PLAYER_ATTACK), this.getSourceEntity()), 999.0F);
   }

   public void handleParticleEvent(String name) {
   }
}
