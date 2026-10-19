package com.benji.netherman.common.entity;

import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.EvokerFangs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
public class AzazelEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Boolean> IS_AGGRO = SynchedEntityData.defineId(AzazelEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(AzazelEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> PHASE_STATE = SynchedEntityData.defineId(AzazelEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> MERCY_TICK = SynchedEntityData.defineId(AzazelEntity.class, EntityDataSerializers.INT);
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(
         Component.literal("The Divine Chariot Azazel"), BossBarColor.RED, BossBarOverlay.PROGRESS
      )
      .setDarkenScreen(true);
   private final Map<UUID, Integer> midasProximityTracker = new HashMap<>();
   private final List<BlockPos> prisonBlocks = new ArrayList<>();
   private BlockPos prisonCenter = null;
   private Player prisonTarget = null;
   public boolean isInstantKill = false;
   private int hitCounter = 0;
   private int attackTimer = 0;
   private boolean playedPraySound = false;
   private int nextShieldThreshold = -1;
   private int arrowAttackVariant = 0;
   private int totalAttacksPerformed = 0;
   private boolean hasOfferedMercy = false;
   private static final RawAnimation IDLE_PRAY_ANIM = RawAnimation.begin().thenLoop("idle_pray");
   private static final RawAnimation WIND_ATTACK_ANIM = RawAnimation.begin().thenPlay("wind_attack");
   private static final RawAnimation DEFENCE_STUN_ANIM = RawAnimation.begin().thenPlay("defence_stun");
   private static final RawAnimation WHEEL_ANIM = RawAnimation.begin().thenPlay("wheel");
   private static final RawAnimation ARROW_ATTACK_ANIM = RawAnimation.begin().thenPlay("arrow_attack");
   private static final RawAnimation PRAY_ANIM = RawAnimation.begin().thenPlay("pray");
   private static final RawAnimation CINEMATIC_MERCY_ANIM = RawAnimation.begin().thenPlay("cinematic_mercy");
   private static final RawAnimation CINEMATIC_DEATH_ANIM = RawAnimation.begin().thenPlay("cinematic_death");
   private static final RawAnimation CINEMATIC_SPAWN_ANIM = RawAnimation.begin().thenPlay("cinematic_spawn");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public AzazelEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 800.0).add(Attributes.MOVEMENT_SPEED, 0.2).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(IS_AGGRO, false);
      builder.define(ATTACK_STATE, 0);
      builder.define(PHASE_STATE, 0);
      builder.define(MERCY_TICK, 0);
   }

   public boolean addEffect(MobEffectInstance effect, @Nullable Entity source) {
      return effect.getEffect() == ModEffects.MANIPULATION.get() ? false : super.addEffect(effect, source);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
      if (this.getAttribute(Attributes.MAX_HEALTH) != null) {
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)AzazelConfig.MAX_HEALTH.get());
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((Double)AzazelConfig.MOVEMENT_SPEED.get());
         this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue((Double)AzazelConfig.KNOCKBACK_RESISTANCE.get());
      }

      this.setHealth(this.getMaxHealth());
      return super.finalizeSpawn(level, difficulty, reason, spawnData);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new AzazelEntity.AzazelMoveGoal(this));
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 64.0F));
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true, false));
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if ((Boolean)this.entityData.get(IS_AGGRO)) {
         this.bossEvent.addPlayer(player);
      }
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossEvent.removePlayer(player);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void travel(Vec3 travelVector) {
      int attackState = (Integer)this.entityData.get(ATTACK_STATE);
      if (attackState < 6 && attackState != 2) {
         super.travel(travelVector);
      } else {
         this.setDeltaMovement(Vec3.ZERO);
      }
   }

   private void startPullAttack() {
      this.entityData.set(ATTACK_STATE, 11);
      this.attackTimer = 60;
      this.playSound(SoundEvents.PHANTOM_SWOOP, 2.0F, 0.5F);
   }

   private void startLaunchAttack() {
      this.entityData.set(ATTACK_STATE, 12);
      this.attackTimer = 80;
      this.playSound((SoundEvent)ModSounds.AZAZEL_PRAY.get(), 1.0F, 1.5F);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (source.is(DamageTypes.FELL_OUT_OF_WORLD) || source.is(DamageTypes.GENERIC_KILL)) {
         this.isInstantKill = true;
      }

      if (source.is(DamageTypes.IN_WALL)) {
         return false;
      }

      int attackState = (Integer)this.entityData.get(ATTACK_STATE);
      if (attackState >= 7 && attackState <= 10) {
         return false;
      }

      if (attackState == 2) {
         this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 1.5F);
         return false;
      }

      if (attackState == 6) {
         this.entityData.set(ATTACK_STATE, 0);
         this.triggerAggro();
         return super.hurt(source, amount);
      }

      if (!(Boolean)this.entityData.get(IS_AGGRO)) {
         this.triggerAggro();
      }

      if (source.getEntity() instanceof LivingEntity) {
         if (this.nextShieldThreshold == -1) {
            int minInit = Math.min((Integer)AzazelConfig.SHIELD_HITS_MIN.get(), (Integer)AzazelConfig.SHIELD_HITS_MAX.get());
            int maxInit = Math.max((Integer)AzazelConfig.SHIELD_HITS_MIN.get(), (Integer)AzazelConfig.SHIELD_HITS_MAX.get());
            this.nextShieldThreshold = minInit + this.random.nextInt(maxInit - minInit + 1);
         }

         this.hitCounter++;
         if (this.hitCounter >= this.nextShieldThreshold && attackState == 0) {
            this.hitCounter = 0;
            int minHits = Math.min((Integer)AzazelConfig.SHIELD_HITS_MIN.get(), (Integer)AzazelConfig.SHIELD_HITS_MAX.get());
            int maxHits = Math.max((Integer)AzazelConfig.SHIELD_HITS_MIN.get(), (Integer)AzazelConfig.SHIELD_HITS_MAX.get());
            this.nextShieldThreshold = minHits + this.random.nextInt(maxHits - minHits + 1);
            this.startDefenseStun();
         }
      }

      return super.hurt(source, amount);
   }

   private void triggerAggro() {
      this.entityData.set(IS_AGGRO, true);
      this.playSound((SoundEvent)ModSounds.AZAZEL_IDLE_4.get(), 1.5F, 1.0F);

      for (ServerPlayer player : this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0))) {
         this.bossEvent.addPlayer(player);
         Component title = Component.translatable("entity.netherman.azazel.mistake")
            .withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD});
         player.connection.send(new ClientboundSetTitlesAnimationPacket(10, 80, 20));
         player.connection.send(new ClientboundSetTitleTextPacket(title));
         player.connection.send(new ClientboundSetSubtitleTextPacket(Component.empty()));
      }
   }

   private void startDefenseStun() {
      this.entityData.set(ATTACK_STATE, 2);
      this.attackTimer = 100;
      this.heal(20.0F);
      this.playSound((SoundEvent)ModSounds.DEFENCE.get(), 1.0F, 1.0F);
   }

   private void startWindAttack() {
      this.entityData.set(ATTACK_STATE, 1);
      this.attackTimer = 60;
      this.playSound(SoundEvents.PHANTOM_SWOOP, 2.0F, 0.5F);
   }

   private void startWheelAttack() {
      this.entityData.set(ATTACK_STATE, 3);
      this.attackTimer = 90;
      this.playSound((SoundEvent)ModSounds.WHEEL_ATTACK.get(), 1.0F, 1.0F);
   }

   private void startPrayAttack() {
      this.entityData.set(ATTACK_STATE, 5);
      this.attackTimer = 180;
      this.playSound((SoundEvent)ModSounds.AZAZEL_PRAY.get(), 1.0F, 1.0F);

      for (BelieverEntity believer : this.level().getEntitiesOfClass(BelieverEntity.class, this.getBoundingBox().inflate(40.0))) {
         believer.setProtected(150);
      }
   }

   private void startDeathCinematic() {
      if (this.level() instanceof ServerLevel sl) {
         this.clearPrison(sl);
      }

      this.entityData.set(ATTACK_STATE, 9);
      this.entityData.set(MERCY_TICK, 0);
      this.getNavigation().stop();
      this.setTarget(null);

      for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(100.0))) {
         p.removeEffect(ModEffects.ANXIETY);
      }
   }

   public void setHealth(float health) {
      int attackState = (Integer)this.entityData.get(ATTACK_STATE);
      if (health <= this.getMaxHealth() * 0.05F && attackState < 9 && !this.isInstantKill) {
         super.setHealth(1.0F);
         this.removeAllEffects();
         this.startDeathCinematic();
      } else {
         super.setHealth(health);
      }
   }

   public void remove(RemovalReason reason) {
      if (this.level() instanceof ServerLevel sl) {
         this.clearPrison(sl);
      }

      super.remove(reason);
   }

   private void startMercyPhase() {
      this.entityData.set(ATTACK_STATE, 6);
      this.entityData.set(MERCY_TICK, 0);
      this.getNavigation().stop();
      this.setTarget(null);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if ((Integer)this.entityData.get(ATTACK_STATE) == 6) {
         ItemStack stack = player.getItemInHand(hand);
         Item item = stack.getItem();
         boolean isWeapon = item == Items.IRON_SWORD
            || item == Items.DIAMOND_SWORD
            || item == Items.NETHERITE_SWORD
            || item == Items.IRON_AXE
            || item == Items.DIAMOND_AXE
            || item == Items.NETHERITE_AXE;
         if (isWeapon) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            this.entityData.set(ATTACK_STATE, 7);
            this.entityData.set(MERCY_TICK, 0);
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0, player.getZ(), 50, 0.5, 0.5, 0.5, 0.05);
            }

            this.playSound(SoundEvents.ITEM_BREAK, 1.0F, 0.5F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
      }

      return super.mobInteract(player, hand);
   }

   private void startArrowAttack() {
      this.entityData.set(ATTACK_STATE, 4);
      this.attackTimer = 120;
      this.arrowAttackVariant = this.random.nextInt(3);
      this.playSound((SoundEvent)ModSounds.ARROW_ATTACK.get(), 1.0F, 1.0F);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         boolean isAggro = (Boolean)this.entityData.get(IS_AGGRO);
         int attackState = (Integer)this.entityData.get(ATTACK_STATE);
         int currentPhase = (Integer)this.entityData.get(PHASE_STATE);
         if (attackState >= 6 && attackState <= 10) {
            this.setTarget(null);
            int mercyTick = (Integer)this.entityData.get(MERCY_TICK);
            this.entityData.set(MERCY_TICK, ++mercyTick);
            if (attackState == 6) {
               int textLen = 42;
               int revealSpeed = 2;
               int finishTextTick = textLen * revealSpeed;
               if (mercyTick <= finishTextTick && mercyTick % revealSpeed == 0) {
                  this.playSound((SoundEvent)ModSounds.AZAZEL_VOICE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
               } else if (mercyTick == finishTextTick + 1) {
                  this.playSound((SoundEvent)ModSounds.CLOCK.get(), 1.0F, 1.0F);
               } else if (mercyTick > finishTextTick + 200) {
                  this.entityData.set(ATTACK_STATE, 0);
                  this.triggerAggro();
                  Player closestPlayer = this.level().getNearestPlayer(this, 30.0);
                  if (closestPlayer != null) {
                     this.setTarget(closestPlayer);
                  }
               }
            } else if (attackState == 7) {
               int textLen = 28;
               int revealSpeed = 2;
               int finishTextTick = textLen * revealSpeed;
               if (mercyTick <= finishTextTick && mercyTick % revealSpeed == 0) {
                  this.playSound((SoundEvent)ModSounds.AZAZEL_VOICE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
               } else if (mercyTick == finishTextTick + 20) {
                  this.entityData.set(ATTACK_STATE, 8);
                  this.entityData.set(MERCY_TICK, 0);

                  for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(100.0))) {
                     p.removeEffect(ModEffects.ANXIETY);
                  }
               }
            } else if (attackState == 8) {
               for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(30.0))) {
                  p.lookAt(Anchor.EYES, this.position().add(0.0, this.getEyeHeight(), 0.0));
               }

               if (mercyTick >= 40) {
                  this.playSound((SoundEvent)ModSounds.FLASH.get(), 1.0F, 1.0F);
                  if (this.level() instanceof ServerLevel serverLevel) {
                     serverLevel.sendParticles(ParticleTypes.FLASH, this.getX(), this.getY() + 2.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                  }

                  this.discard();
               }
            } else if (attackState == 9) {
               int textLen = 56;
               int revealSpeed = 2;
               if (mercyTick == 1) {
                  this.playSound((SoundEvent)ModSounds.AZAZEL_IDLE_4.get(), 1.5F, 1.0F);
               } else if (mercyTick == 40) {
                  this.playSound((SoundEvent)ModSounds.BREATH_AZAZEL.get(), 1.5F, 1.0F);
               }

               if (mercyTick <= textLen * revealSpeed && mercyTick % revealSpeed == 0) {
                  this.playSound((SoundEvent)ModSounds.AZAZEL_VOICE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
               }

               if (mercyTick >= 200) {
                  if (this.level() instanceof ServerLevel serverLevel) {
                     serverLevel.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 4.0F, 1.0F);
                     serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                     serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 40, 1.0, 1.0, 1.0, 0.1);

                     for (int i = 0; i < 30; i++) {
                        Item goldItem = this.random.nextBoolean() ? Items.GOLD_INGOT : Items.GOLD_NUGGET;
                        ItemEntity gold = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0, this.getZ(), new ItemStack(goldItem, 1));
                        gold.setDeltaMovement(
                           (this.random.nextDouble() - 0.5) * 0.5, 0.5 + this.random.nextDouble() * 0.3, (this.random.nextDouble() - 0.5) * 0.5
                        );
                        serverLevel.addFreshEntity(gold);
                     }

                     BlockPos barrelPos = this.blockPosition();
                     serverLevel.setBlockAndUpdate(barrelPos, Blocks.BARREL.defaultBlockState());
                     if (serverLevel.getBlockEntity(barrelPos) instanceof BarrelBlockEntity barrel) {
                        ResourceLocation lootId = ResourceLocation.fromNamespaceAndPath("netherman", "chests/azazel_barrel");
                        ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, lootId);
                        barrel.setLootTable(lootKey, this.random.nextLong());
                     }
                  }

                  this.bossEvent.removeAllPlayers();
                  this.discard();
               }
            } else if (attackState == 10) {
               if (mercyTick == 1) {
                  this.playSound((SoundEvent)ModSounds.SPAWN_UNIT.get(), 1.0F, 1.0F);
               }

               if (mercyTick >= 40) {
                  this.entityData.set(ATTACK_STATE, 0);
                  this.entityData.set(MERCY_TICK, 0);
               }
            }

            return;
         }

         if (this.tickCount % 20 == 0) {
            int wingRand = this.random.nextInt(3);
            SoundEvent wingSound = wingRand == 0
               ? (SoundEvent)ModSounds.WING_1.get()
               : (wingRand == 1 ? (SoundEvent)ModSounds.WING_2.get() : (SoundEvent)ModSounds.WING_3.get());
            this.playSound(wingSound, 1.0F, this.getVoicePitch());
         }

         float healthPct = this.getHealth() / this.getMaxHealth();
         if (healthPct <= 0.25F && currentPhase < 2) {
            this.entityData.set(PHASE_STATE, 2);
            this.playSound((SoundEvent)ModSounds.AZAZEL_PHASE.get(), 1.0F, 1.0F);
         } else if (healthPct <= 0.5F && currentPhase < 1) {
            this.entityData.set(PHASE_STATE, 1);
            this.playSound((SoundEvent)ModSounds.AZAZEL_PHASE.get(), 1.0F, 1.0F);
         }

         if (!isAggro) {
            if (this.tickCount % 240 == 0) {
               this.playSound((SoundEvent)ModSounds.IDLE_PRAY.get(), 1.0F, 1.0F);
            }

            for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(30.0))) {
               if (p.hasEffect(MobEffects.BAD_OMEN)) {
                  this.triggerAggro();
                  break;
               }
            }
         } else {
            if (this.tickCount % 20 == 0) {
               for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(100.0))) {
                  p.addEffect(new MobEffectInstance(ModEffects.ANXIETY, 300, 0, false, false, true));
               }
            }

            if (attackState > 0) {
               this.attackTimer--;
               if (attackState == 1) {
                  this.performWindAttack();
               } else if (attackState == 3) {
                  this.performWheelAttack();
               } else if (attackState == 4) {
                  this.performArrowAttack();
               } else if (attackState == 11) {
                  this.performPullAttack();
               } else if (attackState == 12) {
                  this.performLaunchAttack();
               } else if (attackState == 13) {
                  this.performMidasAttack();
               } else if (attackState == 14) {
                  this.performPrisonAttack();
               }

               if (this.attackTimer <= 0) {
                  this.entityData.set(ATTACK_STATE, 0);
               }
            } else {
               if (this.getTarget() != null && this.random.nextInt((Integer)AzazelConfig.ATTACK_CHANCE.get()) == 0) {
                  if (!this.hasOfferedMercy && this.totalAttacksPerformed >= 5) {
                     this.startMercyPhase();
                     this.hasOfferedMercy = true;
                  } else {
                     boolean hasClosePlayers = !this.level()
                        .getEntitiesOfClass(Player.class, this.getBoundingBox().inflate((Double)AzazelConfig.MELEE_ATTACK_RADIUS.get()))
                        .isEmpty();
                     boolean hasBelievers = !this.level().getEntitiesOfClass(BelieverEntity.class, this.getBoundingBox().inflate(40.0)).isEmpty();
                     if (!hasClosePlayers || this.random.nextInt(100) >= (Integer)AzazelConfig.MELEE_ATTACK_CHANCE.get()) {
                        List<Integer> availableAttacks = new ArrayList<>();
                        availableAttacks.add(0);
                        availableAttacks.add(1);
                        availableAttacks.add(2);
                        availableAttacks.add(3);
                        availableAttacks.add(5);
                        if (hasBelievers) {
                           availableAttacks.add(4);
                        }

                        int choice = availableAttacks.get(this.random.nextInt(availableAttacks.size()));
                        if (choice == 0) {
                           this.startWindAttack();
                        } else if (choice == 1) {
                           this.startWheelAttack();
                        } else if (choice == 2) {
                           this.startArrowAttack();
                        } else if (choice == 3) {
                           this.startPullAttack();
                        } else if (choice == 4) {
                           this.startPrayAttack();
                        } else if (choice == 5) {
                           this.startPrisonAttack();
                        }
                     } else if (this.random.nextBoolean()) {
                        this.startLaunchAttack();
                     } else {
                        this.startMidasAttack();
                     }

                     this.totalAttacksPerformed++;
                  }
               }

               this.handlePassiveSummons();
            }
         }
      }
   }

   private void startPrisonAttack() {
      this.prisonTarget = this.getTarget() instanceof Player ? (Player)this.getTarget() : this.level().getNearestPlayer(this, 40.0);
      if (this.prisonTarget != null) {
         this.entityData.set(ATTACK_STATE, 14);
         this.attackTimer = (Integer)AzazelConfig.PRISON_DURATION.get();
         this.prisonCenter = this.prisonTarget.blockPosition();
         this.prisonBlocks.clear();
         this.playSound((SoundEvent)ModSounds.AZAZEL_IDLE_4.get(), 1.5F, 1.0F);
      }
   }

   private void performPrisonAttack() {
      if (this.level() instanceof ServerLevel serverLevel && this.prisonCenter != null) {
         if (this.attackTimer <= 0) {
            this.clearPrison(serverLevel);
         } else {
            int maxTime = (Integer)AzazelConfig.PRISON_DURATION.get();
            int elapsed = maxTime - this.attackTimer;
            if (elapsed >= 10 && elapsed <= 50 && elapsed % 4 == 0) {
               int step = (elapsed - 10) / 4;
               int radius = (Integer)AzazelConfig.PRISON_RADIUS.get();
               int configMaxHeight = (Integer)AzazelConfig.PRISON_MAX_HEIGHT.get();

               for (int x = -radius; x <= radius; x++) {
                  for (int z = -radius; z <= radius; z++) {
                     if (Math.round(Math.sqrt(x * x + z * z)) == radius) {
                        int maxH = Math.max(3, configMaxHeight - Math.abs(x * 31 + z * 17) % 4);
                        int currentYOffset = step;
                        if (currentYOffset <= maxH) {
                           BlockPos pos = new BlockPos(this.prisonCenter.getX() + x, this.prisonCenter.getY() + currentYOffset, this.prisonCenter.getZ() + z);
                           if (serverLevel.getBlockState(pos).canBeReplaced()) {
                              serverLevel.setBlockAndUpdate(pos, ((Block)ModBlocks.BLACKSTONE_COLUMN.get()).defaultBlockState());
                              this.prisonBlocks.add(pos);
                              serverLevel.sendParticles(
                                 ParticleTypes.REVERSE_PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.2, 0.2, 0.2, 0.02
                              );
                           }
                        }
                     }
                  }
               }
            }

            if (elapsed > 50 && this.prisonTarget != null) {
               int highestBlockY = this.getHighestPrisonBlock();
               int radius = (Integer)AzazelConfig.PRISON_RADIUS.get();
               if (this.prisonTarget.getY() + 1.0 > highestBlockY) {
                  serverLevel.playSound(null, this.prisonCenter, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 1.0F, 0.5F);

                  for (int yOffset = 1; yOffset <= 5; yOffset++) {
                     int buildY = highestBlockY + yOffset;

                     for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                           if (Math.round(Math.sqrt(x * x + z * z)) == radius) {
                              BlockPos pos = new BlockPos(this.prisonCenter.getX() + x, buildY, this.prisonCenter.getZ() + z);
                              if (serverLevel.getBlockState(pos).canBeReplaced()) {
                                 serverLevel.setBlockAndUpdate(pos, ((Block)ModBlocks.GRAND_DOOR_PART.get()).defaultBlockState());
                                 this.prisonBlocks.add(pos);
                                 serverLevel.sendParticles(
                                    ParticleTypes.LARGE_SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2, 0.2, 0.2, 0.2, 0.0
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void clearPrison(ServerLevel level) {
      for (BlockPos pos : this.prisonBlocks) {
         BlockState state = level.getBlockState(pos);
         if (state.getBlock() == ModBlocks.BLACKSTONE_COLUMN.get() || state.getBlock() == ModBlocks.GRAND_DOOR_PART.get()) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            if (state.getBlock() == ModBlocks.BLACKSTONE_COLUMN.get()) {
               level.levelEvent(2001, pos, Block.getId(state));
            }
         }
      }

      this.prisonBlocks.clear();
   }

   private int getHighestPrisonBlock() {
      if (this.prisonBlocks.isEmpty()) {
         return this.prisonCenter != null ? this.prisonCenter.getY() : 0;
      }

      int highest = this.prisonBlocks.get(0).getY();

      for (BlockPos pos : this.prisonBlocks) {
         if (pos.getY() > highest) {
            highest = pos.getY();
         }
      }

      return highest;
   }

   private void startMidasAttack() {
      this.entityData.set(ATTACK_STATE, 13);
      this.attackTimer = 120;
      this.midasProximityTracker.clear();
      this.playSound(SoundEvents.EVOKER_PREPARE_WOLOLO, 2.0F, 1.0F);
   }

   private void performMidasAttack() {
      if (this.level() instanceof ServerLevel serverLevel) {
         int maxTime = 120;
         int currentAttackTick = maxTime - this.attackTimer;
         if (this.attackTimer == 110) {
            boolean spawnStatues = this.random.nextBoolean();
            if (spawnStatues) {
               int bossUnitCount = (Integer)AzazelConfig.MIDAS_BOSSUNIT_COUNT.get();

               for (int i = 0; i < bossUnitCount; i++) {
                  double angle = (Math.PI * 2) / Math.max(1, bossUnitCount) * i;
                  double offsetX = Math.cos(angle) * 4.0;
                  double offsetZ = Math.sin(angle) * 4.0;
                  StatueBossunitEntity unit = (StatueBossunitEntity)((EntityType)ModEntities.STATUE_BOSSUNIT.get()).create(serverLevel);
                  if (unit != null) {
                     unit.setPos(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ);
                     serverLevel.addFreshEntity(unit);
                  }
               }
            } else if (!spawnStatues) {
               LivingEntity currentTarget = this.getTarget();
               if (currentTarget == null) {
                  currentTarget = serverLevel.getNearestPlayer(this, 40.0);
               }

               int guardianCount = (Integer)AzazelConfig.MIDAS_GUARDIAN_COUNT.get();

               for (int i = 0; i < guardianCount; i++) {
                  double angle = (Math.PI * 2) / Math.max(1, guardianCount) * i;
                  double offsetX = Math.cos(angle) * 4.0;
                  double offsetZ = Math.sin(angle) * 4.0;
                  GuardianEntity guardian = (GuardianEntity)((EntityType)ModEntities.GUARDIAN.get()).create(serverLevel);
                  if (guardian != null) {
                     guardian.moveTo(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ, this.getYRot(), 0.0F);
                     if (currentTarget != null) {
                        guardian.setTarget(currentTarget);
                     }

                     guardian.startSpawning();
                     serverLevel.addFreshEntity(guardian);
                  }
               }
            }
         }

         double radius = (double)currentAttackTick / maxTime * 30.0;
         if (radius > 0.0) {
            for (int i = 0; i < 50; i++) {
               double angle = this.random.nextDouble() * 2.0 * Math.PI;
               double offsetX = Math.cos(angle) * radius;
               double offsetZ = Math.sin(angle) * radius;
               serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY() + 0.2, this.getZ() + offsetZ, 2, 0.1, 0.1, 0.1, 0.05);
            }
         }

         for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(35.0))) {
            double dist = player.distanceTo(this);
            if (Math.abs(dist - radius) <= 1.5) {
               player.hurt(this.damageSources().inFire(), ((Double)AzazelConfig.MIDAS_FIRE_DAMAGE.get()).floatValue());
               player.igniteForSeconds(5.0F);
            }

            if (dist <= 6.0) {
               UUID uuid = player.getUUID();
               int ticks = this.midasProximityTracker.getOrDefault(uuid, 0) + 1;
               if (ticks >= (Integer)AzazelConfig.MIDAS_GOLD_TIME.get()) {
                  this.turnRandomItemToGold(player);
                  ticks = 0;
                  serverLevel.sendParticles(ParticleTypes.WAX_ON, player.getX(), player.getY() + 1.0, player.getZ(), 15, 0.5, 0.5, 0.5, 0.1);
                  serverLevel.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.HOSTILE, 1.0F, 1.0F);
               }

               this.midasProximityTracker.put(uuid, ticks);
            } else {
               this.midasProximityTracker.put(player.getUUID(), 0);
            }
         }
      }
   }

   private void turnRandomItemToGold(Player player) {
      List<Integer> validSlots = new ArrayList<>();

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         ItemStack stack = player.getInventory().getItem(i);
         if (!stack.isEmpty() && stack.getItem() != Items.RAW_GOLD) {
            validSlots.add(i);
         }
      }

      if (!validSlots.isEmpty()) {
         int randomSlot = validSlots.get(this.random.nextInt(validSlots.size()));
         ItemStack oldStack = player.getInventory().getItem(randomSlot);
         int count = oldStack.getCount();
         player.getInventory().setItem(randomSlot, new ItemStack(Items.RAW_GOLD, count));
      }
   }

   private void performPullAttack() {
      for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(20.0))) {
         if (this.attackTimer > 15) {
            Vec3 pullVec = this.position().subtract(player.position()).normalize().scale(0.6);
            player.setDeltaMovement(player.getDeltaMovement().add(pullVec.x, 0.0, pullVec.z));
            player.hurtMarked = true;
         } else if (this.attackTimer == 15) {
            player.setDeltaMovement(player.getDeltaMovement().x, 1.5, player.getDeltaMovement().z);
            player.hurtMarked = true;
            player.hurt(this.damageSources().mobAttack(this), ((Double)AzazelConfig.PULL_ATTACK_DAMAGE.get()).floatValue());
         }
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 5; i++) {
            double radius = 4.0;
            double angle = this.random.nextDouble() * 2.0 * Math.PI;
            double offsetX = Math.cos(angle) * radius;
            double offsetZ = Math.sin(angle) * radius;
            double motionX = -offsetX * 0.1;
            double motionY = (this.random.nextDouble() - 0.5) * 0.1;
            double motionZ = -offsetZ * 0.1;
            serverLevel.sendParticles(ParticleTypes.FLAME, this.getX() + offsetX, this.getY() + 1.0, this.getZ() + offsetZ, 0, motionX, motionY, motionZ, 1.0);
         }
      }
   }

   private void performLaunchAttack() {
      if (this.attackTimer == 40) {
         for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(5.0))) {
            player.hurt(this.damageSources().mobAttack(this), ((Double)AzazelConfig.LAUNCH_ATTACK_DAMAGE.get()).floatValue());
            player.setDeltaMovement(player.getDeltaMovement().x, 2.5, player.getDeltaMovement().z);
            player.hurtMarked = true;
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.EXPLOSION, player.getX(), player.getY(), player.getZ(), 2, 0.5, 0.5, 0.5, 0.1);
               serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 1.0F, 1.2F);
            }
         }
      }
   }

   private void performWindAttack() {
      for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(20.0))) {
         Vec3 knockbackVec = player.position().subtract(this.position()).normalize().scale(0.8);
         player.setDeltaMovement(player.getDeltaMovement().add(knockbackVec.x, 0.2, knockbackVec.z));
         player.hurtMarked = true;
         if (this.attackTimer == 30) {
            player.hurt(this.damageSources().mobAttack(this), ((Double)AzazelConfig.WIND_ATTACK_DAMAGE.get()).floatValue());
         }
      }

      if (this.level() instanceof ServerLevel serverLevel) {
         double radius = 3.0 + Math.sin(this.tickCount * 0.5) * 2.0;
         double angle = this.tickCount * 0.4;
         serverLevel.sendParticles(
            ParticleTypes.FLAME, this.getX() + Math.cos(angle) * radius, this.getY() + 2.0, this.getZ() + Math.sin(angle) * radius, 5, 0.1, 0.1, 0.1, 0.05
         );
      }
   }

   private void performWheelAttack() {
      if (this.attackTimer <= 50 && this.getTarget() != null) {
         LivingEntity target = this.getTarget();
         Vec3 dashVec = target.position().subtract(this.position()).normalize().scale(1.2);
         this.setDeltaMovement(dashVec.x, this.getDeltaMovement().y, dashVec.z);
         if (this.tickCount % 3 == 0) {
            for (Player player : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(1.0))) {
               player.hurt(this.damageSources().mobAttack(this), ((Double)AzazelConfig.WHEEL_ATTACK_DAMAGE.get()).floatValue());
               player.setDeltaMovement(dashVec.scale(1.5));
               player.hurtMarked = true;
            }
         }
      }
   }

   private void performArrowAttack() {
      LivingEntity target = this.getTarget();
      if (target != null) {
         if (this.arrowAttackVariant == 0 && this.attackTimer == 100) {
            for (int i = 0; i < 30; i++) {
               double offsetX = (this.random.nextDouble() - 0.5) * 10.0;
               double offsetZ = (this.random.nextDouble() - 0.5) * 10.0;
               Arrow arrow = new Arrow(this.level(), target.getX() + offsetX, target.getY() + 10.0, target.getZ() + offsetZ, new ItemStack(Items.ARROW), null);
               arrow.setDeltaMovement(0.0, -1.5, 0.0);
               this.level().addFreshEntity(arrow);
            }
         } else if (this.arrowAttackVariant == 1 && (this.attackTimer == 100 || this.attackTimer == 80 || this.attackTimer == 60)) {
            for (int i = 0; i < 20; i++) {
               double offsetX = (this.random.nextDouble() - 0.5) * 2.0;
               double offsetZ = (this.random.nextDouble() - 0.5) * 2.0;
               EvokerFangs fangs = new EvokerFangs(
                  this.level(), target.getX() + offsetX, target.getY(), target.getZ() + offsetZ, this.random.nextFloat() * 360.0F, 0, this
               );
               this.level().addFreshEntity(fangs);
            }
         } else if (this.arrowAttackVariant == 2 && this.attackTimer == 100 && this.level() instanceof ServerLevel serverLevel) {
            int laserCount = 10;
            double radius = 7.0;

            for (int i = 0; i < laserCount; i++) {
               double angle = (Math.PI * 2) * i / laserCount;
               double offsetX = Math.cos(angle) * radius;
               double offsetZ = Math.sin(angle) * radius;
               LaserEntity laser = (LaserEntity)((EntityType)ModEntities.LASER.get()).create(serverLevel);
               if (laser != null) {
                  laser.setPos(this.getX() + offsetX, this.getY(), this.getZ() + offsetZ);
                  serverLevel.addFreshEntity(laser);
               }
            }
         }
      }
   }

   private void handlePassiveSummons() {
      if (this.random.nextInt((Integer)AzazelConfig.PASSIVE_SUMMON_CHANCE.get()) == 0 && this.level() instanceof ServerLevel serverLevel) {
         this.playSound((SoundEvent)ModSounds.SPAWN_UNIT.get(), 1.0F, 1.0F);
         serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 2.0, this.getZ(), 30, 1.5, 1.5, 1.5, 0.05);
         boolean spawnBossUnits = this.random.nextBoolean();
         if (spawnBossUnits) {
            for (int i = 0; i < 2; i++) {
               StatueBossunitEntity unit = (StatueBossunitEntity)((EntityType)ModEntities.STATUE_BOSSUNIT.get()).create(serverLevel);
               if (unit != null) {
                  unit.setPos(this.getX() + (this.random.nextDouble() - 0.5) * 10.0, this.getY(), this.getZ() + (this.random.nextDouble() - 0.5) * 10.0);
                  serverLevel.addFreshEntity(unit);
               }
            }
         } else {
            for (int i = 0; i < 4; i++) {
               StatueEntity statue = (StatueEntity)((EntityType)ModEntities.STATUE.get()).create(serverLevel);
               if (statue != null) {
                  statue.setPos(this.getX() + (this.random.nextDouble() - 0.5) * 10.0, this.getY(), this.getZ() + (this.random.nextDouble() - 0.5) * 10.0);
                  serverLevel.addFreshEntity(statue);
               }
            }
         }
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if ((Boolean)this.entityData.get(IS_AGGRO)) {
         if ((Integer)this.entityData.get(PHASE_STATE) == 2) {
            return (SoundEvent)ModSounds.BREATH_AZAZEL.get();
         }

         int rand = this.random.nextInt(3);
         return rand == 0
            ? (SoundEvent)ModSounds.AZAZEL_IDLE_1.get()
            : (rand == 1 ? (SoundEvent)ModSounds.AZAZEL_IDLE_2.get() : (SoundEvent)ModSounds.AZAZEL_IDLE_3.get());
      } else {
         return null;
      }
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.random.nextBoolean() ? (SoundEvent)ModSounds.AZAZEL_DAMAGE_1.get() : (SoundEvent)ModSounds.AZAZEL_DAMAGE_2.get();
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putBoolean("IsAggro", (Boolean)this.entityData.get(IS_AGGRO));
      tag.putInt("HitCounter", this.hitCounter);
      tag.putBoolean("PlayedPraySound", this.playedPraySound);
      tag.putInt("MercyTick", (Integer)this.entityData.get(MERCY_TICK));
      tag.putInt("PhaseState", (Integer)this.entityData.get(PHASE_STATE));
      tag.putBoolean("HasOfferedMercy", this.hasOfferedMercy);
      tag.putInt("TotalAttacks", this.totalAttacksPerformed);
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("IsAggro")) {
         this.entityData.set(IS_AGGRO, tag.getBoolean("IsAggro"));
      }

      this.hitCounter = tag.getInt("HitCounter");
      if (tag.contains("PlayedPraySound")) {
         this.playedPraySound = tag.getBoolean("PlayedPraySound");
      }

      if (tag.contains("MercyTick")) {
         this.entityData.set(MERCY_TICK, tag.getInt("MercyTick"));
      }

      if (tag.contains("TotalAttacks")) {
         this.totalAttacksPerformed = tag.getInt("TotalAttacks");
      }

      if (tag.contains("HasOfferedMercy")) {
         this.hasOfferedMercy = tag.getBoolean("HasOfferedMercy");
      }

      if (tag.contains("PhaseState")) {
         this.entityData.set(PHASE_STATE, tag.getInt("PhaseState"));
      }

      if (!this.level().isClientSide() && this.getAttribute(Attributes.MAX_HEALTH) != null) {
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)AzazelConfig.MAX_HEALTH.get());
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((Double)AzazelConfig.MOVEMENT_SPEED.get());
         this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue((Double)AzazelConfig.KNOCKBACK_RESISTANCE.get());
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         boolean isAggro = (Boolean)this.entityData.get(IS_AGGRO);
         int state = (Integer)this.entityData.get(ATTACK_STATE);
         if (!isAggro) {
            return event.setAndContinue(IDLE_PRAY_ANIM);
         } else if (state == 1 || state == 11) {
            return event.setAndContinue(WIND_ATTACK_ANIM);
         } else if (state == 2) {
            return event.setAndContinue(DEFENCE_STUN_ANIM);
         } else if (state == 3) {
            return event.setAndContinue(WHEEL_ANIM);
         } else if (state == 4) {
            return event.setAndContinue(ARROW_ATTACK_ANIM);
         } else if (state == 13 || state == 14) {
            return event.setAndContinue(IDLE_ANIM);
         } else if (state == 5 || state == 12) {
            return event.setAndContinue(PRAY_ANIM);
         } else if (state == 8) {
            return event.setAndContinue(CINEMATIC_MERCY_ANIM);
         } else if (state == 9) {
            return event.setAndContinue(CINEMATIC_DEATH_ANIM);
         } else {
            return state == 10 ? event.setAndContinue(CINEMATIC_SPAWN_ANIM) : event.setAndContinue(IDLE_ANIM);
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   class AzazelMoveGoal extends Goal {
      private final AzazelEntity azazel;

      public AzazelMoveGoal(AzazelEntity azazel) {
         this.azazel = azazel;
         this.setFlags(EnumSet.of(Flag.MOVE));
      }

      public boolean canUse() {
         int state = (Integer)this.azazel.entityData.get(AzazelEntity.ATTACK_STATE);
         return state >= 6 && state <= 10 ? false : (Boolean)this.azazel.entityData.get(AzazelEntity.IS_AGGRO) && state == 0 && this.azazel.getTarget() != null;
      }

      public void tick() {
         LivingEntity target = this.azazel.getTarget();
         if (target != null) {
            if (this.azazel.distanceToSqr(target) > 400.0) {
               this.azazel.getNavigation().moveTo(target, 1.0);
            } else {
               this.azazel.getNavigation().stop();
            }
         }
      }
   }
}
