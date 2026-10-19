package net.unusual.block_factorys_bosses.entity.boss;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.Holder;
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
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerVariables;
import net.unusual.block_factorys_bosses.block.entity.BossSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.SlowRotMoveControl;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import net.unusual.block_factorys_bosses.network.ParticleEventMessage;
import net.unusual.block_factorys_bosses.network.ShutUpPacket;
import net.unusual.block_factorys_bosses.network.UpdateBossBarTypeMessage;
import net.unusual.block_factorys_bosses.procedures.BossCancelDieProcedure;
import net.unusual.block_factorys_bosses.util.BossHandling;
import net.unusual.block_factorys_bosses.util.RiseUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

@Deprecated
@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class AbstractBossEntity extends Monster implements GeoEntity, IParticleAttachment {
   public static final EntityDataAccessor<Integer> DATA_BOSS_PHASE = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_PHASE = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_SPAWN_ANIMTIME = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_COOLDOWN = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_ATTACK_ANIMTIME = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_HIT_ANIMTIME = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_BATTLE_TIME = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DIE_ANIMTIME = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<String> DATA_SOURCE_UUID = SynchedEntityData.defineId(AbstractBossEntity.class, EntityDataSerializers.STRING);
   public static final EntityDataAccessor<Boolean> DATA_CINEMATIC_BLACK_SCREEN = SynchedEntityData.defineId(
      AbstractBossEntity.class, EntityDataSerializers.BOOLEAN
   );
   public static final int INTRO_PHASE = -1;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private AbstractBossEntity.State state;
   private int timer = 0;
   protected AbstractBossEntity.Attack attack;
   protected final ServerBossEvent bossEvent;

   protected AbstractBossEntity(EntityType<? extends Monster> entityType, Level level) {
      super(entityType, level);
      this.xpReward = 0;
      this.setNoAi(false);
      this.setPersistenceRequired();
      this.moveControl = new SlowRotMoveControl(this);
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
      this.setState("idle");
      this.bossEvent = this.createBossEvent();
   }

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

   public void onBossSpawnerSpawn(BossSpawnerBlockEntity spawner) {
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

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("BossPhase", (Integer)this.entityData.get(DATA_BOSS_PHASE));
      compound.putInt("AttackPhase", (Integer)this.entityData.get(DATA_ATTACK_PHASE));
      compound.putInt("SpawnAnimtime", (Integer)this.entityData.get(DATA_SPAWN_ANIMTIME));
      compound.putInt("AttackCooldown", (Integer)this.entityData.get(DATA_ATTACK_COOLDOWN));
      compound.putInt("AttackAnimtime", (Integer)this.entityData.get(DATA_ATTACK_ANIMTIME));
      compound.putInt("BattleTime", (Integer)this.entityData.get(DATA_BATTLE_TIME));
      compound.putInt("DieAnimtime", (Integer)this.entityData.get(DATA_DIE_ANIMTIME));
      compound.putString("SourceUUID", (String)this.entityData.get(DATA_SOURCE_UUID));
      compound.putBoolean("CinematicBlack", (Boolean)this.entityData.get(DATA_CINEMATIC_BLACK_SCREEN));
      compound.putString("State", this.state.name());
      compound.putInt("Timer", this.timer);
      compound.putBoolean("BossBarVisible", this.bossEvent.isVisible());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
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
         this.setState(compound.getString("State"));
         this.timer = compound.getInt("Timer");
         RiseUtil.ifBoolPresent(compound, "BossBarVisible", this.bossEvent::setVisible);
      }
   }

   public void tick() {
      super.tick();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
      this.timer++;
   }

   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   public abstract Map<String, AbstractBossEntity.State> getStates();

   public AbstractBossEntity.State getState() {
      return this.state;
   }

   public void setState(String name) {
      if (this.timer != 0) {
         this.timer = -this.getAnimTransitionTime();
      }

      if (!this.getStates().containsKey(name)) {
         this.log("State not found: " + name);
      } else {
         this.state = this.getStates().get(name);
         if (name.equals("idle")) {
            this.stopTriggeredAnim("main_controller", null);
         } else {
            this.triggerAnim("main_controller", name);
         }
      }
   }

   public boolean isState(String name) {
      return this.state.name.equals(name);
   }

   public int getTimer() {
      return this.timer;
   }

   public boolean isTimerDone() {
      return this.timer > this.state.duration;
   }

   public boolean isAnimTransitioning() {
      return this.timer < 0;
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

   public void onDamageTaken(DamageContainer damageContainer) {
      this.maybeCancelDeath(damageContainer);
   }

   private void maybeCancelDeath(DamageContainer damageContainer) {
      if (this.isDeadOrDying()) {
         if (!damageContainer.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            if (this.shouldCancelDeath()) {
               if (this.isDeadOrDying()) {
                  this.setHealth(0.1F);
               }

               BossCancelDieProcedure.execute(this);
            }
         }
      }
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

   public boolean shouldCancelDeath() {
      boolean isDead = this.isState("dead");
      this.setState("dead");
      return !isDead;
   }

   public void lookAtTarget() {
      if (this.getTarget() instanceof LivingEntity target) {
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

   protected void stopSoundForNearbyPlayers(SoundEvent event) {
      if (this.level() instanceof ServerLevel serverLevel) {
         if (BossesRiseSounds.CANCELABLE_SOUNDS.contains(event.getLocation())) {
            ShutUpPacket shutUpPacket = new ShutUpPacket();
            serverLevel.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0)).forEach(player -> player.connection.send(shutUpPacket));
         }
      }
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
      return this.getTarget() instanceof LivingEntity target && AABB.ofSize(this.position(), aabbSize, aabbSize, aabbSize).contains(target.position());
   }

   public LivingEntity getSourceEntity() {
      try {
         if (this.level() instanceof ServerLevel level
            && level.getEntity(UUID.fromString((String)this.entityData.get(DATA_SOURCE_UUID))) instanceof LivingEntity entity) {
            return entity;
         }
      } catch (Exception var4) {
      }

      LivingEntity entity = this.getTarget();
      return (LivingEntity)(entity instanceof LivingEntity ? entity : this);
   }

   public abstract AbstractBossEntity.Attack chooseAttack();

   public void doAttack(@Nullable AbstractBossEntity.Attack attack) {
      if (!this.level().isClientSide() && attack != null) {
         this.lookAtTarget();
         this.attack = attack;
         this.setState(attack.name);
         this.entityData.set(DATA_ATTACK_COOLDOWN, 0);
         this.entityData.set(DATA_ATTACK_PHASE, attack.id());
         if (attack.sound() != null && this.getAnimTransitionTime() == 0) {
            this.playSound(attack.sound(), 8.0F, 1.0F);
         }

         this.entityData.set(DATA_ATTACK_ANIMTIME, attack.animTime());
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(ParticleEventMessage.TYPE, ParticleEventMessage.STREAM_CODEC, ParticleEventMessage::handleData);
   }

   public void handleParticleEvent(String name) {
   }

   public record Attack(int id, String name, @Nullable SoundEvent sound, int animTime) {
      public Attack(int id, String name, Holder<SoundEvent> sound, int animTime) {
         this(id, name, sound == null ? null : (SoundEvent)sound.value(), animTime);
      }

      public Attack(String name, Holder<SoundEvent> sound) {
         this(0, name, sound, 0);
      }

      public Attack(String name) {
         this(name, null);
      }
   }

   public record AttackPattern(int weight, AbstractBossEntity.Attack[] attacks) {
      public int length() {
         return this.attacks.length;
      }

      @Nullable
      public AbstractBossEntity.Attack get(int index) {
         return index >= this.attacks.length ? null : this.attacks[index];
      }
   }

   public record AttackPatternPool(AbstractBossEntity.AttackPattern[] attackPatterns) {
      public int totalWeight() {
         return Arrays.stream(this.attackPatterns).mapToInt(AbstractBossEntity.AttackPattern::weight).sum();
      }

      public AbstractBossEntity.AttackPattern selectRandom() {
         int randomWeight = (int)(Math.random() * this.totalWeight());
         int weightSum = 0;

         for (AbstractBossEntity.AttackPattern pattern : this.attackPatterns) {
            weightSum += pattern.weight;
            if (randomWeight < weightSum) {
               return pattern;
            }
         }

         return null;
      }
   }

   public record State(String name, int duration) {
      public State(String name) {
         this(name, 0);
      }
   }
}
