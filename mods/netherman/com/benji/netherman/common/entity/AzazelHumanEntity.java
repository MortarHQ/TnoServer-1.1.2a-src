package com.benji.netherman.common.entity;

import com.benji.netherman.client.ClientFogHandler;
import com.benji.netherman.client.events.ClientZoneAmbientEvents;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
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

public class AzazelHumanEntity extends Monster implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Integer> BOSS_STATE = SynchedEntityData.defineId(AzazelHumanEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DIALOGUE_TICK = SynchedEntityData.defineId(AzazelHumanEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> ATTACK_TIMER = SynchedEntityData.defineId(AzazelHumanEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Boolean> IS_PHASE_2 = SynchedEntityData.defineId(AzazelHumanEntity.class, EntityDataSerializers.BOOLEAN);
   public int forcedAttackGoal = 0;
   private int crackStage = 0;
   public boolean phase2Triggered = false;
   private final ServerBossEvent bossEvent = (ServerBossEvent)new ServerBossEvent(
         Component.literal("Azazel, The Awakened"), BossBarColor.RED, BossBarOverlay.PROGRESS
      )
      .setDarkenScreen(true);
   private float damageTakenRecently = 0.0F;
   private int damageTimer = 0;
   private int defendCooldown = 0;
   private static final int[] LINE_LENGTHS = new int[]{31, 33, 40, 31, 28, 33, 35, 39, 29, 29};
   public boolean isInstantKill = false;

   public float getDamageTakenRecently() {
      return this.damageTakenRecently;
   }

   public void resetDamageTaken() {
      this.damageTakenRecently = 0.0F;
   }

   public int getDefendCooldown() {
      return this.defendCooldown;
   }

   public void setDefendCooldown(int ticks) {
      this.defendCooldown = ticks;
   }

   public AzazelHumanEntity(EntityType<? extends Monster> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 1000.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.FOLLOW_RANGE, 100.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BOSS_STATE, 0);
      builder.define(DIALOGUE_TICK, 0);
      builder.define(ATTACK_TIMER, 0);
      builder.define(IS_PHASE_2, false);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
      if (this.getAttribute(Attributes.MAX_HEALTH) != null) {
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)AzazelConfig.HUMAN_MAX_HEALTH.get());
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((Double)AzazelConfig.HUMAN_MOVEMENT_SPEED.get());
         this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue((Double)AzazelConfig.HUMAN_KNOCKBACK_RESISTANCE.get());
      }

      this.setHealth(this.getMaxHealth());
      BlockPos doorPos = null;

      for (BlockPos p : BlockPos.betweenClosed(this.blockPosition().offset(-64, -20, -64), this.blockPosition().offset(64, 20, 64))) {
         if (level.getBlockState(p).is((Block)ModBlocks.MAZE_DOOR.get())) {
            doorPos = p;
            break;
         }
      }

      if (doorPos != null) {
         double dX = doorPos.getX() - this.getX();
         double dZ = doorPos.getZ() - this.getZ();
         float yaw = (float)(Mth.atan2(dZ, dX) * (180.0 / Math.PI)) - 90.0F;
         this.setYRot(yaw);
         this.setYBodyRot(yaw);
         this.setYHeadRot(yaw);
      }

      return super.finalizeSpawn(level, difficulty, reason, spawnData);
   }

   protected void registerGoals() {
      this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
      this.goalSelector.addGoal(0, new AzazelHumanUltimateGoal(this));
      this.goalSelector.addGoal(0, new AzazelHumanPhase2Goal(this));
      this.goalSelector.addGoal(1, new AzazelHumanMeleeGoal(this));
      this.goalSelector.addGoal(2, new AzazelHumanLongRangeGoal(this));
      this.goalSelector.addGoal(3, new AzazelHumanMidRangeGoal(this));
      this.goalSelector.addGoal(4, new AzazelHumanMovementGoal(this));
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      if ((Integer)this.entityData.get(BOSS_STATE) == 5) {
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

   public void travel(Vec3 travelVector) {
      if ((Integer)this.entityData.get(BOSS_STATE) < 5) {
         this.setDeltaMovement(Vec3.ZERO);
      } else {
         super.travel(travelVector);
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      int state = (Integer)this.entityData.get(BOSS_STATE);
      if (state == 0 && player.getItemInHand(hand).is(((Block)ModBlocks.AZAZEL_TROPHY.get()).asItem())) {
         if (!player.isCreative()) {
            player.getItemInHand(hand).shrink(1);
         }

         this.entityData.set(BOSS_STATE, 1);
         this.entityData.set(DIALOGUE_TICK, 0);
         if (this.level() instanceof ServerLevel sl) {
            sl.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 4.0, this.getZ(), 200, 2.0, 4.0, 2.0, 0.05);
            sl.playSound(null, this.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.HOSTILE, 1.0F, 0.5F);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.level().isClientSide()) {
         return false;
      }

      if (source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypes.CRAMMING)) {
         return false;
      }

      if (!source.is(DamageTypes.FELL_OUT_OF_WORLD) && !source.is(DamageTypes.GENERIC_KILL)) {
         int state = (Integer)this.entityData.get(BOSS_STATE);
         if (state == 100 || state == 101 || state == 110 || state == 111) {
            return false;
         }

         if (state < 5) {
            return false;
         }

         if (state != 11 && state != 12 && state != 13 && state != 22 && state != 70) {
            if (state >= 5 && this.random.nextFloat() < 0.25F) {
               this.entityData.set(BOSS_STATE, 40);
               this.entityData.set(ATTACK_TIMER, 40);
               this.playSound((SoundEvent)ModSounds.DODGE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
               if (this.level() instanceof ServerLevel sl) {
                  sl.sendParticles(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY() + 1.5, this.getZ(), 5, 0.5, 0.5, 0.5, 0.05);
               }

               return false;
            } else {
               if (state >= 5) {
                  this.damageTakenRecently += amount;
                  this.damageTimer = 40;
               }

               return super.hurt(source, amount);
            }
         } else {
            if (source.getEntity() != null) {
               this.playSound((SoundEvent)ModSounds.DODGE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
               if (this.level() instanceof ServerLevel sl) {
                  sl.sendParticles(ParticleTypes.CRIT, this.getX(), this.getY() + 3.5, this.getZ(), 10, 0.5, 0.5, 0.5, 0.1);
               }
            }

            return false;
         }
      } else {
         this.isInstantKill = true;
         return super.hurt(source, amount);
      }
   }

   private void startDeathCinematic() {
      this.entityData.set(BOSS_STATE, 100);
      this.entityData.set(DIALOGUE_TICK, 0);
      this.getNavigation().stop();
      this.setTarget(null);
      super.setHealth(1.0F);

      for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(100.0))) {
         p.removeEffect(ModEffects.PRAEMIUM);
      }
   }

   public void setHealth(float health) {
      int state = (Integer)this.entityData.get(BOSS_STATE);
      if (health <= 0.0F && state < 100 && !this.isInstantKill) {
         super.setHealth(1.0F);
         this.removeAllEffects();
         this.startDeathCinematic();
      } else {
         super.setHealth(health);
      }
   }

   public boolean canAttack(LivingEntity target) {
      int state = (Integer)this.entityData.get(BOSS_STATE);
      return state >= 100 ? false : super.canAttack(target);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         int state = (Integer)this.entityData.get(BOSS_STATE);
         int tick = (Integer)this.entityData.get(DIALOGUE_TICK);
         if (state == 100 || state == 101) {
            this.entityData.set(DIALOGUE_TICK, ++tick);
            if (tick == 1) {
               this.playSound((SoundEvent)ModSounds.ROAR.get(), 4.0F, 1.0F);
            }

            if (tick > 45 && tick <= 93 && tick % 2 == 0) {
               this.playSound((SoundEvent)ModSounds.AZAZEL_VOICE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
            }

            if (tick == 120) {
               this.entityData.set(BOSS_STATE, 101);
               this.playSound((SoundEvent)ModSounds.LAUGH.get(), 2.0F, 1.0F);
            }

            if (tick >= 180) {
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.playSound(null, this.blockPosition(), (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 4.0F, 1.0F);
                  serverLevel.sendParticles(ParticleTypes.EXPLOSION_EMITTER, this.getX(), this.getY() + 1.0, this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                  serverLevel.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 40, 1.0, 1.0, 1.0, 0.1);

                  for (int i = 0; i < 30; i++) {
                     Item goldItem = this.random.nextBoolean() ? Items.DIAMOND : Items.GOLD_NUGGET;
                     ItemEntity gold = new ItemEntity(serverLevel, this.getX(), this.getY() + 1.0, this.getZ(), new ItemStack(goldItem, 1));
                     gold.setDeltaMovement((this.random.nextDouble() - 0.5) * 0.5, 0.5 + this.random.nextDouble() * 0.3, (this.random.nextDouble() - 0.5) * 0.5);
                     serverLevel.addFreshEntity(gold);
                  }

                  BlockPos barrelPos = this.blockPosition();
                  serverLevel.setBlockAndUpdate(barrelPos, Blocks.BARREL.defaultBlockState());
                  if (serverLevel.getBlockEntity(barrelPos) instanceof BarrelBlockEntity barrel) {
                     ResourceLocation lootId = ResourceLocation.fromNamespaceAndPath("netherman", "chests/azazel_human_barrel");
                     ResourceKey<LootTable> lootKey = ResourceKey.create(Registries.LOOT_TABLE, lootId);
                     barrel.setLootTable(lootKey, this.random.nextLong());
                  }
               }

               this.bossEvent.removeAllPlayers();
               this.discard();
            }
         } else if (state != 110 && state != 111) {
            if (state >= 5 && state < 100 && this.isAlive() && this.forcedAttackGoal == 0) {
               float hpPct = this.getHealth() / this.getMaxHealth();
               if (hpPct <= 0.3F && !this.phase2Triggered) {
                  this.phase2Triggered = true;
                  this.forcedAttackGoal = 5;
               } else if (hpPct <= 0.7F && this.crackStage == 0) {
                  this.crackStage = 1;
                  this.forcedAttackGoal = 4;
               } else if (hpPct <= 0.5F && this.crackStage == 1) {
                  this.crackStage = 2;
                  this.forcedAttackGoal = 4;
               } else if (hpPct <= 0.1F && this.crackStage == 2) {
                  this.crackStage = 3;
                  this.forcedAttackGoal = 4;
               }
            }

            if (this.defendCooldown > 0) {
               this.defendCooldown--;
            }

            if (this.damageTimer > 0) {
               this.damageTimer--;
            } else {
               this.damageTakenRecently = 0.0F;
            }

            if ((state == 10 || state == 11) && this.tickCount % 35 == 0) {
               this.playSound((SoundEvent)ModSounds.GUARDIAN_WALK.get(), 4.0F, 0.6F + this.random.nextFloat() * 0.4F);
            } else if (state == 12 && this.tickCount % 12 == 0) {
               this.playSound((SoundEvent)ModSounds.GUARDIAN_WALK.get(), 4.0F, 1.2F + this.random.nextFloat() * 0.2F);
            }

            if (state == 14 || state == 40) {
               int attackTimer = (Integer)this.entityData.get(ATTACK_TIMER);
               if (attackTimer > 0) {
                  this.entityData.set(ATTACK_TIMER, attackTimer - 1);
                  if (state == 40 && attackTimer == 20) {
                     this.playSound((SoundEvent)ModSounds.LAUGH.get(), 2.0F, 1.0F);
                  }
               } else {
                  this.entityData.set(BOSS_STATE, 5);
               }
            }

            if (state >= 1 && state <= 4) {
               this.entityData.set(DIALOGUE_TICK, ++tick);
               if (state == 1 && tick >= 20) {
                  this.entityData.set(BOSS_STATE, 2);
                  this.entityData.set(DIALOGUE_TICK, 0);
               } else if (state == 2) {
                  int currentLine = Math.min(tick / 90, 9);
                  int lineTick = tick % 90;
                  int maxChars = LINE_LENGTHS[currentLine];
                  int charsVisible = lineTick / 2;
                  if (charsVisible <= maxChars && lineTick % 2 == 0) {
                     this.playSound((SoundEvent)ModSounds.AZAZEL_VOICE.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
                  }

                  if (lineTick == 0 && this.random.nextInt(3) == 0) {
                     SoundEvent[] sounds = new SoundEvent[]{
                        (SoundEvent)ModSounds.SPEECH_1.get(),
                        (SoundEvent)ModSounds.SPEECH_2.get(),
                        (SoundEvent)ModSounds.SPEECH_3.get(),
                        (SoundEvent)ModSounds.SPEECH_4.get()
                     };
                     this.playSound(sounds[this.random.nextInt(sounds.length)], 2.0F, 0.8F + this.random.nextFloat() * 0.4F);
                  }

                  if (tick >= 900) {
                     this.entityData.set(BOSS_STATE, 3);
                     this.entityData.set(DIALOGUE_TICK, 0);
                  }
               } else if (state == 3) {
                  if (tick >= 200) {
                     this.entityData.set(BOSS_STATE, 4);
                     this.entityData.set(DIALOGUE_TICK, 0);
                     this.playSound((SoundEvent)ModSounds.LAUGH.get(), 2.0F, 1.0F);
                     Vec3 forward = Vec3.directionFromRotation(0.0F, this.getYRot()).normalize();
                     this.setPos(this.getX() + forward.x * 2.0, this.getY(), this.getZ() + forward.z * 2.0);
                     if (this.level() instanceof ServerLevel sl) {
                        for (ServerPlayer p : sl.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0))) {
                           p.addEffect(new MobEffectInstance(ModEffects.PRAEMIUM, -1, 0, false, false, true));
                        }
                     }
                  }
               } else if (state == 4 && tick >= 30) {
                  this.entityData.set(BOSS_STATE, 5);
                  if (this.level() instanceof ServerLevel sl) {
                     for (ServerPlayer p : sl.getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(64.0))) {
                        this.bossEvent.addPlayer(p);
                     }
                  }
               }
            }

            if (state == 5 && this.tickCount % 20 == 0) {
               for (ServerPlayer p : this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(100.0))) {
                  p.addEffect(new MobEffectInstance(ModEffects.PRAEMIUM, -1, 0, false, false, true));
               }

               if (this.getTarget() != null && this.forcedAttackGoal == 0) {
                  float attackChance = this.entityData.get(IS_PHASE_2) ? 0.15F : 0.05F;
                  if (this.random.nextFloat() < attackChance) {
                     this.forcedAttackGoal = this.random.nextInt(3) + 1;
                     return;
                  }
               }
            }
         } else {
            this.setTarget(null);
            this.entityData.set(DIALOGUE_TICK, ++tick);
            if (state == 110) {
               if (tick == 1) {
                  this.playSound(SoundEvents.WARDEN_EMERGE, 1.5F, 1.0F);
               }

               if (this.level() instanceof ServerLevel sl) {
                  BlockState dirt = Blocks.PODZOL.defaultBlockState();

                  for (int i = 0; i < 4; i++) {
                     double dx = (this.random.nextDouble() - 0.5) * 4.0;
                     double dz = (this.random.nextDouble() - 0.5) * 4.0;
                     sl.sendParticles(
                        new BlockParticleOption(ParticleTypes.BLOCK, dirt), this.getX() + dx, this.getY(), this.getZ() + dz, 1, 0.0, 0.3, 0.0, 0.1
                     );
                     sl.sendParticles(ParticleTypes.LAVA, this.getX() + dx, this.getY(), this.getZ() + dz, 1, 0.0, 0.5, 0.0, 0.1);
                  }
               }

               if (tick >= 70) {
                  this.entityData.set(BOSS_STATE, 111);
                  this.entityData.set(DIALOGUE_TICK, 0);
               }
            } else if (state == 111) {
               this.setYRot(180.0F);
               this.setYBodyRot(180.0F);
               this.setYHeadRot(180.0F);
               this.setDeltaMovement(0.0, this.getDeltaMovement().y, -0.2);
               if (tick % 26 == 0) {
                  this.playSound((SoundEvent)ModSounds.GUARDIAN_WALK.get(), 4.0F, 0.6F + this.random.nextFloat() * 0.4F);
               }

               if (this.level() instanceof ServerLevel sl) {
                  for (Player p : sl.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(20.0))) {
                     ClientFogHandler.isInsideMansion = true;
                  }

                  if (tick % 5 == 0) {
                     BlockPos center = this.blockPosition();
                     boolean brokeBlocks = false;

                     for (int x = -3; x <= 3; x++) {
                        for (int y = 0; y <= 14; y++) {
                           BlockPos target = center.offset(x, y, -2);
                           BlockState block = sl.getBlockState(target);
                           boolean isEdge = Math.abs(x) == 3 || y >= 12;
                           if ((!isEdge || !(this.random.nextFloat() > 0.7F)) && !block.isAir() && block.getDestroySpeed(sl, target) >= 0.0F) {
                              sl.destroyBlock(target, false);
                              brokeBlocks = true;
                           }
                        }
                     }

                     if (brokeBlocks) {
                        this.playSound((SoundEvent)ModSounds.DODGE.get(), 1.0F, 0.8F);
                     }
                  }

                  if (tick % 40 == 0 && this.random.nextFloat() < 0.6F) {
                     AzazelEarthquakeEntity quake = (AzazelEarthquakeEntity)((EntityType)ModEntities.EARTHQUAKE_ENTITY.get()).create(sl);
                     if (quake != null) {
                        double dx = (this.random.nextDouble() - 0.5) * 20.0;
                        double dz = (this.random.nextDouble() - 0.5) * 20.0;
                        BlockPos quakePos = BlockPos.containing(this.getX() + dx, this.getY(), this.getZ() + dz);

                        while (sl.isEmptyBlock(quakePos.below()) && quakePos.getY() > sl.getMinBuildHeight()) {
                           quakePos = quakePos.below();
                        }

                        quake.moveTo(quakePos.getX() + 0.5, quakePos.getY(), quakePos.getZ() + 0.5, 0.0F, 0.0F);
                        sl.addFreshEntity(quake);
                     }
                  }
               }
            }
         }
      }
   }

   public void triggerScreenShake(float intensity, int ticks) {
      if (this.level() instanceof ServerLevel sl) {
         for (ServerPlayer var5 : sl.getPlayers(p -> p.distanceToSqr(this) < 4000.0)) {
            ;
         }
      }

      this.level().broadcastEntityEvent(this, (byte)101);
   }

   public void handleEntityEvent(byte id) {
      if (id == 101) {
         ClientZoneAmbientEvents.startScreenShake(30, 2.0F);
         BlockState state = Blocks.DEEPSLATE_BRICKS.defaultBlockState();

         for (int i = 0; i < 40; i++) {
            double dx = (this.random.nextDouble() - 0.5) * 6.0;
            double dz = (this.random.nextDouble() - 0.5) * 6.0;
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), this.getX() + dx, this.getY() + 0.5, this.getZ() + dz, 0.0, 0.5, 0.0);
            this.level().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + dx, this.getY() + 1.0, this.getZ() + dz, 0.0, 0.2, 0.0);
         }
      } else {
         super.handleEntityEvent(id);
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this,
            "controller",
            5,
            event -> {
               int state = (Integer)this.entityData.get(BOSS_STATE);

               return switch (state) {
                  case 0 -> event.setAndContinue(RawAnimation.begin().thenLoop("throme_headless"));
                  case 1 -> event.setAndContinue(RawAnimation.begin().thenPlay("throme_mask"));
                  case 2, 3 -> event.setAndContinue(RawAnimation.begin().thenLoop("throme_mask_idle"));
                  case 4 -> event.setAndContinue(RawAnimation.begin().thenPlay("throme_stand"));
                  default -> event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
                  case 10 -> event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
                  case 11 -> event.setAndContinue(RawAnimation.begin().thenLoop("defend_walk"));
                  case 12 -> event.setAndContinue(RawAnimation.begin().thenLoop("defend_run"));
                  case 13 -> event.setAndContinue(RawAnimation.begin().thenPlay("jump_attack"));
                  case 14 -> event.setAndContinue(RawAnimation.begin().thenPlay("fall").thenPlay("standup"));
                  case 20 -> event.setAndContinue(RawAnimation.begin().thenPlay("leg_attack").thenLoop("idle"));
                  case 21 -> event.setAndContinue(RawAnimation.begin().thenPlay("scythe_attack").thenLoop("idle"));
                  case 22 -> event.setAndContinue(RawAnimation.begin().thenPlay("defend_stay").thenLoop("idle"));
                  case 30 -> event.setAndContinue(RawAnimation.begin().thenPlay("spear_long").thenLoop("idle"));
                  case 31 -> event.setAndContinue(RawAnimation.begin().thenPlay("spear_mid").thenLoop("idle"));
                  case 32 -> event.setAndContinue(RawAnimation.begin().thenPlay("smoke").thenLoop("idle"));
                  case 40 -> event.setAndContinue(RawAnimation.begin().thenPlay("dodge").thenLoop("idle"));
                  case 50 -> event.setAndContinue(RawAnimation.begin().thenPlay("scythe_attack").thenLoop("idle"));
                  case 51 -> event.setAndContinue(RawAnimation.begin().thenPlay("spear_attack").thenLoop("idle"));
                  case 52 -> event.setAndContinue(RawAnimation.begin().thenPlay("spear_attack").thenLoop("idle"));
                  case 53 -> event.setAndContinue(RawAnimation.begin().thenPlay("spear_attack").thenLoop("idle"));
                  case 60 -> event.setAndContinue(RawAnimation.begin().thenPlay("leg_attack").thenLoop("idle"));
                  case 70 -> event.setAndContinue(RawAnimation.begin().thenPlay("phase").thenLoop("idle"));
                  case 100 -> event.setAndContinue(RawAnimation.begin().thenPlay("fall").thenLoop("fall_idle"));
                  case 101 -> event.setAndContinue(RawAnimation.begin().thenPlay("fall_death"));
                  case 110 -> event.setAndContinue(RawAnimation.begin().thenPlay("ground").thenLoop("ground_idle"));
                  case 111 -> this.getDeltaMovement().lengthSqr() < 0.01
                     ? event.setAndContinue(RawAnimation.begin().thenLoop("ground_idle"))
                     : event.setAndContinue(RawAnimation.begin().thenLoop("ground_walk"));
               };
            }
         )
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("BossState", (Integer)this.entityData.get(BOSS_STATE));
      tag.putInt("DialogueTick", (Integer)this.entityData.get(DIALOGUE_TICK));
      tag.putInt("CrackStage", this.crackStage);
      tag.putBoolean("IsPhase2", (Boolean)this.entityData.get(IS_PHASE_2));
      tag.putBoolean("Phase2Triggered", this.phase2Triggered);
   }

   public int getAmbientSoundInterval() {
      return 400 + this.random.nextInt(600);
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.random.nextBoolean() ? (SoundEvent)ModSounds.HUMAN_DAMAGE_1.get() : (SoundEvent)ModSounds.HUMAN_DAMAGE_2.get();
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      int rand = this.random.nextInt(3);
      return rand == 0 ? (SoundEvent)ModSounds.SPEECH_1.get() : (rand == 1 ? (SoundEvent)ModSounds.SPEECH_2.get() : (SoundEvent)ModSounds.SPEECH_3.get());
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(BOSS_STATE, tag.getInt("BossState"));
      this.entityData.set(DIALOGUE_TICK, tag.getInt("DialogueTick"));
      this.entityData.set(IS_PHASE_2, tag.getBoolean("IsPhase2"));
      this.phase2Triggered = tag.getBoolean("Phase2Triggered");
      this.crackStage = tag.getInt("CrackStage");
      if (!this.level().isClientSide() && this.getAttribute(Attributes.MAX_HEALTH) != null) {
         this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((Double)AzazelConfig.HUMAN_MAX_HEALTH.get());
         this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((Double)AzazelConfig.HUMAN_MOVEMENT_SPEED.get());
         this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue((Double)AzazelConfig.HUMAN_KNOCKBACK_RESISTANCE.get());
      }
   }
}
