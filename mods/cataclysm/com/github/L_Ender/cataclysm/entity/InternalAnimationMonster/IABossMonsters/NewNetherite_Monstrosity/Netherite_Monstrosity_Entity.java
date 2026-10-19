package com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.NewNetherite_Monstrosity;

import com.github.L_Ender.cataclysm.blockentities.Boss_Respawn_Spawner_Block_Entity;
import com.github.L_Ender.cataclysm.client.particle.Options.RingParticleOptions;
import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Kobolediator_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalAttackGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalMoveGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AI.InternalStateGoal;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.IABoss_monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.FowardMoveController;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModBlocks;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.util.CMMathUtil;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;

public class Netherite_Monstrosity_Entity extends IABoss_monster {
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.getDisplayName(), BossBarColor.RED, false, 0);
   public int frame;
   public float LayerBrightness;
   public float oLayerBrightness;
   public int LayerTicks;
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState sleepAnimationState = new AnimationState();
   public AnimationState awakeAnimationState = new AnimationState();
   public AnimationState smashsAnimationState = new AnimationState();
   public AnimationState phaseAnimationState = new AnimationState();
   public AnimationState fireAnimationState = new AnimationState();
   public AnimationState drainAnimationState = new AnimationState();
   public AnimationState shouldercheckAnimationState = new AnimationState();
   public AnimationState overpowerAnimationState = new AnimationState();
   public AnimationState flareshotAnimationState = new AnimationState();
   public AnimationState deathAnimationState = new AnimationState();
   public final Netherite_Monstrosity_Part headPart;
   public final Netherite_Monstrosity_Part[] monstrosityParts;
   private static final EntityDataAccessor<Boolean> IS_BERSERK = SynchedEntityData.defineId(Netherite_Monstrosity_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> IS_AWAKEN = SynchedEntityData.defineId(Netherite_Monstrosity_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> MAGAZINE = SynchedEntityData.defineId(Netherite_Monstrosity_Entity.class, EntityDataSerializers.INT);
   private int blockBreakCounter;
   private int shoot_cooldown = 0;
   public static final int SHOOT_COOLDOWN = 240;
   private boolean onLava = false;
   private int check_cooldown = 0;
   public static final int CHECK_COOLDOWN = 80;
   private int overpower_cooldown = 0;
   public static final int OVERPOWER_COOLDOWN = 160;
   private int flare_shoot_cooldown = 0;
   public static final int FLARE_SHOOT_COOLDOWN = 120;

   public Netherite_Monstrosity_Entity(EntityType entity, Level world) {
      super(entity, world);
      this.xpReward = 500;
      this.moveControl = new FowardMoveController(this);
      this.headPart = new Netherite_Monstrosity_Part(this, 1.6F, 2.5F);
      this.monstrosityParts = new Netherite_Monstrosity_Part[]{this.headPart};
      this.setPathfindingMalus(PathType.UNPASSABLE_RAIL, 0.0F);
      this.setPathfindingMalus(PathType.LAVA, 0.0F);
      this.setPathfindingMalus(PathType.WATER, -1.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 0.0F);
      this.setConfigattribute(this, CMCommonConfig.NetheriteMonstrosity.healthMultiplier, CMCommonConfig.NetheriteMonstrosity.attackMultiplier);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0, 80));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, AbstractVillager.class, true));
      this.goalSelector.addGoal(5, new InternalMoveGoal(this, false, 1.0) {
         @Override
         public boolean canUse() {
            return super.canUse();
         }
      });
      this.goalSelector.addGoal(3, new InternalAttackGoal(this, 0, 3, 0, 58, 12, 6.0F) {
         @Override
         public boolean canUse() {
            return super.canUse() && Netherite_Monstrosity_Entity.this.getRandom().nextFloat() * 100.0F < 32.0F;
         }
      });
      this.goalSelector.addGoal(0, new Netherite_Monstrosity_Entity.NMDoNothingGoal());
      this.goalSelector
         .addGoal(
            1,
            new InternalStateGoal(this, 2, 2, 0, 40, 0) {
               @Override
               public void start() {
                  super.start();
                  Netherite_Monstrosity_Entity.this.setHomePos(
                     GlobalPos.of(Netherite_Monstrosity_Entity.this.level().dimension(), Netherite_Monstrosity_Entity.this.blockPosition())
                  );
                  Netherite_Monstrosity_Entity.this.setIsAwaken(true);
               }

               @Override
               public void tick() {
                  this.entity.setDeltaMovement(0.0, this.entity.getDeltaMovement().y, 0.0);
               }
            }
         );
      this.goalSelector.addGoal(0, new Netherite_Monstrosity_Entity.MonstrosityPhaseChangeGoal(this, 0, 4, 0, 54));
      this.goalSelector.addGoal(3, new Netherite_Monstrosity_Entity.Magmashoot(this, 0, 6, 0, 44, 20, 40.0F, 19, 16.0F));
      this.goalSelector.addGoal(3, new Netherite_Monstrosity_Entity.Flareshoot(this, 0, 10, 0, 60, 35, 26.0F, 35, 18.0F));
      this.goalSelector
         .addGoal(
            3,
            new InternalAttackGoal(this, 0, 7, 0, 60, 25, 1.0F) {
               @Override
               public boolean canUse() {
                  LivingEntity target = this.entity.getTarget();
                  return target != null
                     && target.isAlive()
                     && this.entity.getAttackState() == 0
                     && this.entity.isInLava()
                     && Netherite_Monstrosity_Entity.this.getMagazine() >= CMCommonConfig.NetheriteMonstrosity.Lavabombmagazine;
               }
            }
         );
      this.goalSelector.addGoal(3, new Netherite_Monstrosity_Entity.ShoulderCheck(this, 0, 8, 0, 70, 19, 16.0F, 19, 49, 12.0F));
      this.goalSelector
         .addGoal(
            3,
            new InternalAttackGoal(this, 0, 9, 0, 75, 7, 10.0F) {
               @Override
               public boolean canUse() {
                  return super.canUse()
                     && Netherite_Monstrosity_Entity.this.getRandom().nextFloat() * 100.0F < 40.0F
                     && Netherite_Monstrosity_Entity.this.overpower_cooldown <= 0;
               }

               @Override
               public void stop() {
                  super.stop();
                  Netherite_Monstrosity_Entity.this.overpower_cooldown = 160;
               }
            }
         );
   }

   public static Builder netherite_monstrosity() {
      return Monster.createMonsterAttributes()
         .add(Attributes.FOLLOW_RANGE, 50.0)
         .add(Attributes.MOVEMENT_SPEED, 0.25)
         .add(Attributes.ATTACK_DAMAGE, 25.0)
         .add(Attributes.MAX_HEALTH, 600.0)
         .add(Attributes.ARMOR, 12.0)
         .add(Attributes.ARMOR_TOUGHNESS, 5.0)
         .add(Attributes.STEP_HEIGHT, 4.5)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public boolean attackEntityFromPart(Netherite_Monstrosity_Part netherite_monstrosity_part, DamageSource source, float amount) {
      return this.hurt(source, amount);
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      if (this.getAttackState() == 4 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      Entity entity = source.getDirectEntity();
      if (entity instanceof AbstractGolem) {
         damage *= 0.5F;
      }

      boolean attack = super.hurt(source, damage);
      if (attack && !this.getIsAwaken() && this.isAlive()) {
         this.setIsAwaken(true);
      }

      return attack;
   }

   public boolean hurtParts(Netherite_Monstrosity_Part part, DamageSource source, float damage) {
      if (part == this.headPart) {
         damage = Math.min(Float.MAX_VALUE, damage * 1.5F);
      }

      return super.hurt(source, damage);
   }

   protected int decreaseAirSupply(int air) {
      return air;
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.NetheriteMonstrosity.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.NetheriteMonstrosity.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.NetheriteMonstrosity.dpsCap;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.NetheriteMonstrosity.rangeCap;
   }

   public boolean canBeCollidedWith() {
      return this.isAlive() && CMCommonConfig.NetheriteMonstrosity.BodyCollided && this.getAttackState() != 8;
   }

   public boolean isPushable() {
      return false;
   }

   public AnimationState getAnimationState(String input) {
      if (input == "idle") {
         return this.idleAnimationState;
      } else if (input == "sleep") {
         return this.sleepAnimationState;
      } else if (input == "awake") {
         return this.awakeAnimationState;
      } else if (input == "smash") {
         return this.smashsAnimationState;
      } else if (input == "phase_two") {
         return this.phaseAnimationState;
      } else if (input == "fire") {
         return this.fireAnimationState;
      } else if (input == "death") {
         return this.deathAnimationState;
      } else if (input == "drain") {
         return this.drainAnimationState;
      } else if (input == "shoulder_check") {
         return this.shouldercheckAnimationState;
      } else if (input == "overpower") {
         return this.overpowerAnimationState;
      } else {
         return input == "flare_shot" ? this.flareshotAnimationState : new AnimationState();
      }
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(IS_BERSERK, false);
      p_326229_.define(IS_AWAKEN, false);
      p_326229_.define(MAGAZINE, 0);
   }

   public boolean isSleep() {
      return this.getAttackState() == 1 || this.getAttackState() == 2;
   }

   public boolean canStandOnFluid(FluidState p_230285_1_) {
      return p_230285_1_.is(FluidTags.LAVA);
   }

   public void setIsBerserk(boolean isBerserk) {
      this.entityData.set(IS_BERSERK, isBerserk);
   }

   public boolean getIsBerserk() {
      return (Boolean)this.entityData.get(IS_BERSERK);
   }

   public void setOnLava(boolean lava) {
      this.onLava = lava;
   }

   public boolean getOnLava() {
      return this.onLava;
   }

   public void setIsAwaken(boolean isAwaken) {
      this.entityData.set(IS_AWAKEN, isAwaken);
      this.bossInfo.setVisible(isAwaken);
      if (isAwaken) {
         this.PlayerCounter(this.level());
      }
   }

   public boolean getIsAwaken() {
      return (Boolean)this.entityData.get(IS_AWAKEN);
   }

   public void setMagazine(int isAwaken) {
      this.entityData.set(MAGAZINE, isAwaken);
   }

   public int getMagazine() {
      return (Integer)this.entityData.get(MAGAZINE);
   }

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossInfo.setName(this.getDisplayName());
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> p_21104_) {
      if (ATTACK_STATE.equals(p_21104_)) {
         switch (this.getAttackState()) {
            case 0:
               this.stopAllAnimationStates();
               break;
            case 1:
               this.stopAllAnimationStates();
               this.sleepAnimationState.startIfStopped(this.tickCount);
               break;
            case 2:
               this.stopAllAnimationStates();
               this.awakeAnimationState.startIfStopped(this.tickCount);
               break;
            case 3:
               this.stopAllAnimationStates();
               this.smashsAnimationState.startIfStopped(this.tickCount);
               break;
            case 4:
               this.stopAllAnimationStates();
               this.phaseAnimationState.startIfStopped(this.tickCount);
               break;
            case 5:
               this.stopAllAnimationStates();
               this.deathAnimationState.startIfStopped(this.tickCount);
               break;
            case 6:
               this.stopAllAnimationStates();
               this.fireAnimationState.startIfStopped(this.tickCount);
               break;
            case 7:
               this.stopAllAnimationStates();
               this.drainAnimationState.startIfStopped(this.tickCount);
               break;
            case 8:
               this.stopAllAnimationStates();
               this.shouldercheckAnimationState.startIfStopped(this.tickCount);
               break;
            case 9:
               this.stopAllAnimationStates();
               this.overpowerAnimationState.startIfStopped(this.tickCount);
               break;
            case 10:
               this.stopAllAnimationStates();
               this.flareshotAnimationState.startIfStopped(this.tickCount);
         }
      }

      super.onSyncedDataUpdated(p_21104_);
   }

   public void stopAllAnimationStates() {
      this.sleepAnimationState.stop();
      this.awakeAnimationState.stop();
      this.smashsAnimationState.stop();
      this.phaseAnimationState.stop();
      this.deathAnimationState.stop();
      this.fireAnimationState.stop();
      this.drainAnimationState.stop();
      this.overpowerAnimationState.stop();
      this.shouldercheckAnimationState.stop();
      this.flareshotAnimationState.stop();
   }

   @Override
   public void die(DamageSource p_21014_) {
      super.die(p_21014_);
      this.setAttackState(5);
   }

   @Override
   public int deathtimer() {
      return 60;
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (CMCommonConfig.NetheriteMonstrosity.respawner
         && !this.level().isClientSide
         && this.getHomePos() != null
         && this.level() instanceof ServerLevel serverLevel) {
         MinecraftServer server = serverLevel.getServer();
         ServerLevel targetLevel = server.getLevel(this.getHomePos().dimension());
         if (targetLevel != null) {
            BlockPos targetPos = this.getHomePos().pos();
            BlockState blockState = ((Block)ModBlocks.BOSS_RESPAWNER.get()).defaultBlockState();
            targetLevel.setBlock(targetPos, blockState, 2);
            if (targetLevel.getBlockEntity(targetPos) instanceof Boss_Respawn_Spawner_Block_Entity spawnerBlockEntity) {
               spawnerBlockEntity.setEntityId((EntityType<?>)ModEntities.NETHERITE_MONSTROSITY.get());
               spawnerBlockEntity.setTheItem(((Item)ModItems.MONSTROUS_EYE.get()).getDefaultInstance());
            }
         }
      }
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("is_Berserk", this.getIsBerserk());
      compound.putBoolean("is_Awaken", this.getIsAwaken());
      compound.putInt("Magazine", this.getMagazine());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setIsBerserk(compound.getBoolean("is_Berserk"));
      this.setIsAwaken(compound.getBoolean("is_Awaken"));
      this.setMagazine(compound.getInt("Magazine"));
      if (this.hasCustomName()) {
         this.bossInfo.setName(this.getDisplayName());
      }
   }

   private void floatStrider() {
      if (this.isInLava()) {
         CollisionContext lvt_1_1_ = CollisionContext.of(this);
         if (lvt_1_1_.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition().below(), true)
            && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
            this.setOnGround(true);
         }
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_MONSTROSITY) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   @Override
   public void tick() {
      super.tick();
      this.floatStrider();
      if (this.level().isClientSide()) {
         this.idleAnimationState.animateWhen(this.getAttackState() == 0 && this.getIsAwaken(), this.tickCount);
      } else {
         this.bossInfo.setLife(this.getLife());
      }

      this.frame++;
      float moveX = (float)(this.getX() - this.xo);
      float moveZ = (float)(this.getZ() - this.zo);
      float speed = Mth.sqrt(moveX * moveX + moveZ * moveZ);
      if (!this.isSilent() && this.frame % 25 == 1 && speed > 0.05 && this.getIsAwaken() && this.getAttackState() != 8) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYSTEP.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.08F, 0, 5);
      }

      this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
      if (this.getAttackState() == 0) {
         this.BlockBreaking();
      }

      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      }

      if (this.shoot_cooldown > 0) {
         this.shoot_cooldown--;
      }

      if (this.overpower_cooldown > 0) {
         this.overpower_cooldown--;
      }

      if (this.check_cooldown > 0) {
         this.check_cooldown--;
      }

      if (this.flare_shoot_cooldown > 0) {
         this.flare_shoot_cooldown--;
      }

      this.setHeadPart();
      if (this.level().isClientSide) {
         this.LayerTicks++;
         this.LayerBrightness = this.LayerBrightness + (0.0F - this.LayerBrightness) * 0.8F;
      }
   }

   private void setHeadPart() {
      if (!this.isNoAi()) {
         float f17 = this.yBodyRot * (float) (Math.PI / 180.0);
         float pitch = this.getXRot() * (float) (Math.PI / 180.0);
         float f3 = Mth.sin(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         float f18 = Mth.cos(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         Vec3[] avector3d = new Vec3[this.monstrosityParts.length];

         for (int j = 0; j < this.monstrosityParts.length; j++) {
            avector3d[j] = new Vec3(this.monstrosityParts[j].getX(), this.monstrosityParts[j].getY(), this.monstrosityParts[j].getZ());
         }

         float headY = 0.0F;
         float headxz = 0.0F;
         if (this.getAttackState() == 3) {
            int end = 40;
            float f = 0.0F;
            if (this.attackTicks > end) {
               f = CMMathUtil.cullAnimationTick(this.attackTicks, 1.2F, 1.0F, 13, end);
            } else {
               f = CMMathUtil.cullAnimationTick(this.attackTicks, 2.0F, 1.0F, 13, end);
            }

            headxz = -1.6F * f;
            headY = -2.2F * f;
         }

         if (this.getAttackState() == 6) {
            float f = CMMathUtil.cullAnimationTick(this.attackTicks, 0.5F, 1.0F, 0, 40);
            headxz = 4.0F * f;
            headY = 1.2F * f;
         }

         if (this.getAttackState() == 8) {
            float f = CMMathUtil.cullAnimationTick(this.attackTicks, 2.0F, 1.0F, 0, 30);
            headxz = -4.0F * f;
         }

         this.setPartPosition(this.headPart, f3 * -1.65F + f3 * headxz, pitch + 2.4F + headY, -f18 * -1.65F - f18 * headxz);

         for (int l = 0; l < this.monstrosityParts.length; l++) {
            this.monstrosityParts[l].xo = avector3d[l].x;
            this.monstrosityParts[l].yo = avector3d[l].y;
            this.monstrosityParts[l].zo = avector3d[l].z;
            this.monstrosityParts[l].xOld = avector3d[l].x;
            this.monstrosityParts[l].yOld = avector3d[l].y;
            this.monstrosityParts[l].zOld = avector3d[l].z;
         }
      }
   }

   @Override
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_29678_, DifficultyInstance p_29679_, MobSpawnType spawnType, @Nullable SpawnGroupData p_29681_) {
      if (spawnType == MobSpawnType.COMMAND || spawnType == MobSpawnType.SPAWN_EGG || MobSpawnType.isSpawner(spawnType) || spawnType == MobSpawnType.DISPENSER) {
         this.setIsAwaken(true);
      }

      return super.finalizeSpawn(p_29678_, p_29679_, spawnType, p_29681_);
   }

   public void aiStep() {
      super.aiStep();
      if (this.getAttackState() == 2 && this.attackTicks == 2) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYAWAKEN.get(), 10.0F, 1.0F);
      }

      if (this.getAttackState() == 3 && this.attackTicks == 19) {
         this.EarthQuake(6.25);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
         this.Makeparticle(4.75F, 2.5F);
         this.Makeparticle(4.75F, -2.5F);
      }

      if (this.getAttackState() == 4) {
         if (this.attackTicks == 10) {
            this.playSound((SoundEvent)ModSounds.MONSTROSITYGROWL.get(), 3.0F, 1.0F);
         }

         if (this.attackTicks == 16) {
            this.Roarparticle(3.5F, 2.7F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 8.0F);
         }

         if (this.attackTicks == 17) {
            this.berserkBlockBreaking(8, 8, 8);
            this.EarthQuake(6.25);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
            this.Makeparticle(4.4F, 2.0F);
            this.Makeparticle(4.4F, -2.0F);
         }

         if (this.attackTicks == 18) {
            this.Roarparticle(3.5F, 2.7F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 9.0F);
         }

         if (this.attackTicks == 20) {
            this.Roarparticle(3.5F, 2.7F, 10, 255, 255, 255, 0.4F, 1.0F, 0.8F, 9.0F);
         }
      }

      if (this.getAttackState() == 5 && this.attackTicks == 26) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYLAND.get(), 1.0F, 1.0F);
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
      }

      if (this.getAttackState() == 6 && this.attackTicks == 19) {
         this.playSound((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 3.0F, 0.75F);
      }

      if (this.getAttackState() == 7) {
         if (this.attackTicks == 24) {
            this.setMagazine(0);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
            this.doAbsorptionEffects(4, 1, 4);
            this.playSound(SoundEvents.BUCKET_FILL_LAVA, 6.0F, 0.5F);
            this.heal(15.0F * (float)CMCommonConfig.NetheriteMonstrosity.healthMultiplier);
         }

         if (this.attackTicks == 26) {
            this.doAbsorptionEffects(8, 2, 8);
            this.heal(15.0F * (float)CMCommonConfig.NetheriteMonstrosity.healthMultiplier);
         }

         if (this.attackTicks == 28) {
            this.doAbsorptionEffects(16, 4, 16);
            this.heal(15.0F * (float)CMCommonConfig.NetheriteMonstrosity.healthMultiplier);
         }
      }

      if (this.getAttackState() == 8) {
         if (this.attackTicks == 22
            || this.attackTicks == 27
            || this.attackTicks == 32
            || this.attackTicks == 37
            || this.attackTicks == 42
            || this.attackTicks == 47) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.15F, 0, 6);
            this.playSound((SoundEvent)ModSounds.MONSTROSITYSTEP.get(), 1.0F, 1.0F);
         }

         if (this.attackTicks > 19 && this.attackTicks < 49 && !this.level().isClientSide) {
            if (CMCommonConfig.NetheriteMonstrosity.ignoreMobGriefing) {
               this.ChargeBlockBreaking();
            } else if (EventHooks.canEntityGrief(this.level(), this)) {
               this.ChargeBlockBreaking();
            }

            double yaw = Math.toRadians(this.getYRot() + 90.0F);
            double xExpand = 2.0 * Math.cos(yaw);
            double zExpand = 2.0 * Math.sin(yaw);
            AABB attackRange = this.getBoundingBox().inflate(0.75, 0.75, 0.75).expandTowards(xExpand, 0.0, zExpand);
            DamageSource damagesource = this.damageSources().mobAttack(this);

            for (LivingEntity Lentity : this.level().getEntitiesOfClass(LivingEntity.class, attackRange)) {
               if (!this.isAlliedTo(Lentity) && !(Lentity instanceof Netherite_Monstrosity_Entity) && Lentity != this) {
                  boolean flag = Lentity.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.4F);
                  if (flag) {
                     double theta = this.yBodyRot * (Math.PI / 180.0);
                     theta += Math.PI / 2;
                     double vec = -2.5;
                     double vecX = Math.cos(theta);
                     double vecZ = Math.sin(theta);
                     double d0 = Lentity.getX() - (this.getX() + vec * vecX);
                     double d1 = Lentity.getZ() - (this.getZ() + vec * vecZ);
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.05);
                     double vel = 4.0;
                     Lentity.push(d0 / d2 * vel, 0.3, d1 / d2 * vel);
                     Lentity.addEffect(new MobEffectInstance(ModEffect.EFFECTBONE_FRACTURE, 100));
                  }
               }
            }
         }
      }

      if (this.getAttackState() == 9) {
         if (this.attackTicks == 9) {
            this.OverPowerKnockBack(7.0);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 20.0F, 0.3F, 0, 20);
            this.Makeparticle(-0.3F, 3.4F);
            this.Makeparticle(-0.3F, -3.4F);
            if (!this.level().isClientSide) {
               this.CircleFlameJet(-0.3F, 3.4F, this.getIsBerserk() ? 14 : 7, this.getIsBerserk() ? 8 : 4, 3.0);
               this.CircleFlameJet(-0.3F, -3.4F, this.getIsBerserk() ? 14 : 7, this.getIsBerserk() ? 8 : 4, 3.0);
            }

            this.playSound((SoundEvent)ModSounds.REMNANT_STOMP.get(), 1.0F, 0.7F);
         }

         if (this.attackTicks == 26) {
            this.playSound((SoundEvent)ModSounds.MONSTROSITYGROWL.get(), 3.0F, 1.0F);
         }

         if (this.attackTicks == 32) {
            this.Roarparticle(2.0F, 4.5F, 10, 255, 255, 255, 0.4F, 1.0F, 0.6F, 4.5F);
         }

         if (this.attackTicks == 34) {
            this.Roarparticle(2.0F, 4.5F, 10, 255, 255, 255, 0.4F, 1.0F, 0.6F, 5.0F);
         }

         if (this.attackTicks == 36) {
            this.Roarparticle(2.0F, 4.5F, 10, 255, 255, 255, 0.4F, 1.0F, 0.6F, 5.5F);
         }

         if (this.attackTicks == 31) {
            this.StompDamage(0.6F, 4, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 5, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(4.5F, 0.0F);
         }

         if (this.attackTicks == 33) {
            this.StompDamage(0.6F, 6, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 7, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(6.5F, 0.0F);
         }

         if (this.attackTicks == 35) {
            this.StompDamage(0.6F, 8, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 9, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(8.5F, 0.0F);
         }

         if (this.attackTicks == 37) {
            this.StompDamage(0.6F, 10, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 11, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(10.5F, 0.0F);
         }

         if (this.attackTicks == 39) {
            this.StompDamage(0.6F, 12, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 13, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(12.5F, 0.0F);
         }

         if (this.attackTicks == 41) {
            this.StompDamage(0.6F, 14, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.StompDamage(0.6F, 15, 5, 1.05F, -2.0F, 0.0F, 0, 0.7F);
            this.Stompsound(14.5F, 0.0F);
         }
      }

      if (this.getAttackState() == 10 && this.attackTicks == 35) {
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), 15.0F, 0.08F, 0, 10);
         this.playSound((SoundEvent)ModSounds.MONSTROSITYSHOOT.get(), 3.0F, 0.75F);
      }
   }

   private void CircleFlameJet(float vec, float math, int vertexrune, int rune, double time) {
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);

      for (int i = 0; i < vertexrune; i++) {
         float throwAngle = i * (float) Math.PI / (vertexrune / 2);

         for (int k = 0; k < rune; k++) {
            double d2 = 1.1 * (k + 1);
            int d3 = (int)(time * (k + 1));
            this.spawnJet(
               this.getX() + vec * vecX + f * math + Mth.cos(throwAngle) * 1.25 * d2,
               this.getZ() + vec * vecZ + f1 * math + Mth.sin(throwAngle) * 1.25 * d2,
               this.getY() - 2.0,
               this.getY() + 2.0,
               throwAngle,
               d3
            );
         }
      }
   }

   private void spawnJet(double x, double z, double minY, double maxY, float rotation, int delay) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(minY) - 1);

      if (flag) {
         this.level()
            .addFreshEntity(
               new Flame_Jet_Entity(this.level(), x, blockpos.getY() + d0, z, rotation, delay, (float)CMCommonConfig.NetheriteMonstrosity.FlameJetDamage, this)
            );
      }
   }

   private void Stompsound(float distance, float math) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
      this.level()
         .playSound(
            (Player)null,
            this.getX() + distance * vecX + f * math,
            this.getY(),
            this.getZ() + distance * vecZ + f1 * math,
            (SoundEvent)ModSounds.REMNANT_STOMP.get(),
            this.getSoundSource(),
            0.6F,
            1.0F
         );
   }

   private void StompDamage(float spreadarc, int distance, int height, float mxy, float vec, float math, int shieldbreakticks, float damage) {
      double bodyRotRad = this.yBodyRot * (Math.PI / 180.0);
      double cosBodyRot = Math.cos(bodyRotRad);
      double sinBodyRot = Math.sin(bodyRotRad);
      double facingAngle = bodyRotRad + (Math.PI / 2);
      int hitY = Mth.floor(this.getBoundingBox().minY - 0.5);
      double spread = Math.PI * spreadarc;
      int arcLen = Mth.ceil(distance * spread);
      double commonOffsetX = vec * -sinBodyRot + cosBodyRot * math;
      double commonOffsetZ = vec * cosBodyRot + sinBodyRot * math;
      double baseX = this.getX() + commonOffsetX;
      double baseZ = this.getZ() + commonOffsetZ;
      float factor = 1.0F - distance / 12.0F;

      for (int i = 0; i < arcLen; i++) {
         double thetaRatio = arcLen > 1 ? (double)i / (arcLen - 1) : 0.5;
         double theta = (thetaRatio - 0.5) * spread + facingAngle;
         double vx = Math.cos(theta);
         double vz = Math.sin(theta);
         double px = baseX + vx * distance;
         double pz = baseZ + vz * distance;
         int hitX = Mth.floor(px);
         int hitZ = Mth.floor(pz);
         BlockPos pos = new BlockPos(hitX, hitY + height, hitZ);
         BlockState block = this.level().getBlockState(pos);
         int maxDepth = 30;

         for (int depthCount = 0; depthCount < maxDepth && block.getRenderShape() != RenderShape.MODEL; depthCount++) {
            pos = pos.below();
            block = this.level().getBlockState(pos);
         }

         if (block.getRenderShape() != RenderShape.MODEL) {
            block = Blocks.AIR.defaultBlockState();
         }

         this.spawnBlocks(hitX, hitY + height, hitZ, (int)(this.getY() - height), block, px, pz, mxy, vx, vz, factor, shieldbreakticks, damage);
      }
   }

   private boolean notLavaCliff(double distance) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      double px = this.getX() + vecX * distance;
      double pz = this.getZ() + vecZ * distance;
      double checkHeight = -2.5;
      Vec3 forwardPosition = new Vec3(px, this.getY() + checkHeight, pz);
      BlockState blockStateBelow = this.level().getBlockState(BlockPos.containing(forwardPosition));
      return !blockStateBelow.isAir();
   }

   private void spawnBlocks(
      int hitX,
      int hitY,
      int hitZ,
      int lowestYCheck,
      BlockState blockState,
      double px,
      double pz,
      float mxy,
      double vx,
      double vz,
      float factor,
      int shieldbreakticks,
      float damage
   ) {
      DamageSource damagesource = this.damageSources().mobAttack(this);
      BlockPos blockpos = new BlockPos(hitX, hitY, hitZ);
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = this.level().getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(this.level(), blockpos1, Direction.UP)) {
            if (!this.level().isEmptyBlock(blockpos)) {
               BlockState blockstate1 = this.level().getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(this.level(), blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= Mth.floor(lowestYCheck) - 1);

      Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(this.level(), hitX + 0.5, blockpos.getY() + d0 + 0.5, hitZ + 0.5, blockState, 10);
      fallingBlockEntity.push(0.0, 0.2 + this.getRandom().nextGaussian() * 0.04, 0.0);
      this.level().addFreshEntity(fallingBlockEntity);
      AABB selection = new AABB(px - 0.5, blockpos.getY() + d0 - 1.0, pz - 0.5, px + 0.5, blockpos.getY() + d0 + mxy, pz + 0.5);
      List<LivingEntity> hit = this.level().getEntitiesOfClass(LivingEntity.class, selection);
      if (!hit.isEmpty()) {
         for (LivingEntity entity : hit) {
            if (!this.isAlliedTo(entity) && !(entity instanceof Kobolediator_Entity) && entity != this) {
               boolean flag = entity.hurt(damagesource, (float)(this.getAttributeValue(Attributes.ATTACK_DAMAGE) * damage));
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag) {
                  double magnitude = 10.0;
                  double x = vx * Math.max(factor, 0.2) * magnitude;
                  double y = 0.0;
                  if (entity.onGround()) {
                     y += 0.15;
                  }

                  double z = vz * Math.max(factor, 0.2) * magnitude;
                  entity.setDeltaMovement(entity.getDeltaMovement().add(x, y, z));
               }
            }
         }
      }
   }

   private void doAbsorptionEffects(int x, int y, int z) {
      int MthX = Mth.floor(this.getX());
      int MthY = Mth.floor(this.getY());
      int MthZ = Mth.floor(this.getZ());

      for (int k2 = -x; k2 <= x; k2++) {
         for (int l2 = -z; l2 <= z; l2++) {
            for (int j = 0; j <= y; j++) {
               int i3 = MthX + k2;
               int k = MthY + j;
               int l = MthZ + l2;
               BlockPos blockpos = new BlockPos(i3, k, l);
               this.doAbsorptionEffect(blockpos);
            }
         }
      }
   }

   private void doAbsorptionEffect(BlockPos pos) {
      BlockState state = this.level().getBlockState(pos);
      if (!this.level().isClientSide && state.is(Blocks.LAVA)) {
         this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
      }
   }

   private void EarthQuake(double area) {
      this.playSound((SoundEvent)ModSounds.EXPLOSION.get(), 1.5F, 1.0F + this.getRandom().nextFloat() * 0.1F);
      if (!this.level().isClientSide) {
         DamageSource damagesource = this.damageSources().mobAttack(this);

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(area))) {
            if (!this.isAlliedTo(entity) && !(entity instanceof Netherite_Monstrosity_Entity) && entity != this) {
               boolean flag = entity.hurt(
                  damagesource,
                  (float)(
                     (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + Math.min(
                           this.getAttributeValue(Attributes.ATTACK_DAMAGE), entity.getMaxHealth() * (float)CMCommonConfig.NetheriteMonstrosity.SmashHpdamage
                        )
                  )
               );
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player) {
                  EntityUtil.disableShield(player, 120);
               }

               if (flag) {
                  this.launch(entity, 2.0, 0.6);
                  if (this.getIsBerserk()) {
                     entity.igniteForSeconds(6.0F);
                  }
               }
            }
         }
      }
   }

   private void OverPowerKnockBack(double area) {
      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(area))) {
         if (!this.isAlliedTo(entity) && !(entity instanceof Netherite_Monstrosity_Entity) && entity != this) {
            this.launch(entity, 3.0, 0.35);
         }
      }
   }

   private void Makeparticle(float vec, float math) {
      if (this.level().isClientSide) {
         float f = Mth.cos(this.yBodyRot * (float) (Math.PI / 180.0));
         float f1 = Mth.sin(this.yBodyRot * (float) (Math.PI / 180.0));
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (int i1 = 0; i1 < 80 + this.random.nextInt(12); i1++) {
            double DeltaMovementX = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementY = this.getRandom().nextGaussian() * 0.07;
            double DeltaMovementZ = this.getRandom().nextGaussian() * 0.07;
            float angle = (float) (Math.PI / 180.0) * this.yBodyRot + i1;
            double extraX = 2.0F * Mth.sin((float)(Math.PI + angle));
            double extraY = 0.3F;
            double extraZ = 2.0F * Mth.cos(angle);
            int hitX = Mth.floor(this.getX() + vec * vecX + extraX);
            int hitY = Mth.floor(this.getY());
            int hitZ = Mth.floor(this.getZ() + vec * vecZ + extraZ);
            BlockPos hit = new BlockPos(hitX, hitY, hitZ);
            BlockState block = this.level().getBlockState(hit.below());
            if (this.getIsBerserk()) {
               this.level()
                  .addParticle(
                     ParticleTypes.FLAME,
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            } else if (block.getRenderShape() != RenderShape.INVISIBLE) {
               this.level()
                  .addParticle(
                     new BlockParticleOption(ParticleTypes.BLOCK, block),
                     this.getX() + vec * vecX + extraX + f * math,
                     this.getY() + extraY,
                     this.getZ() + vec * vecZ + extraZ + f1 * math,
                     DeltaMovementX,
                     DeltaMovementY,
                     DeltaMovementZ
                  );
            }
         }

         if (this.getIsBerserk()) {
            this.level()
               .addParticle(
                  new RingParticleOptions(0.0F, (float) (Math.PI / 2), 35, 204, 78, 5, 1.0F, 30.0F, false, 0),
                  this.getX() + vec * vecX + f * math,
                  this.getY() + 0.2F,
                  this.getZ() + vec * vecZ + f1 * math,
                  0.0,
                  0.0,
                  0.0
               );
         } else {
            this.level()
               .addParticle(
                  new RingParticleOptions(0.0F, (float) (Math.PI / 2), 35, 255, 255, 255, 1.0F, 30.0F, false, 0),
                  this.getX() + vec * vecX + f * math,
                  this.getY() + 0.2F,
                  this.getZ() + vec * vecZ + f1 * math,
                  0.0,
                  0.0,
                  0.0
               );
         }
      }
   }

   private void Roarparticle(float vec, float y, int duration, int r, int g, int b, float a, float start, float inc, float end) {
      if (this.level().isClientSide) {
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);
         this.level()
            .addParticle(
               new RoarParticleOptions(duration, r, g, b, a, start, inc, end),
               this.getX() + vec * vecX,
               this.getY() + y,
               this.getZ() + vec * vecZ,
               0.0,
               0.0,
               0.0
            );
      }
   }

   private void launch(Entity e, double XZpower, double Ypower) {
      double d0 = e.getX() - this.getX();
      double d1 = e.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      e.push(d0 / d2 * XZpower, Ypower, d1 / d2 * XZpower);
   }

   private void ChargeBlockBreaking() {
      boolean flag = false;
      AABB aabb = this.getBoundingBox().inflate(0.5, 0.2, 0.5);

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && !blockstate.is(ModTag.NETHERITE_MONSTROSITY_IMMUNE)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            if (this.random.nextInt(6) == 0 && !blockstate.hasBlockEntity()) {
               Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                  this.level(), blockpos.getX() + 0.5, blockpos.getY() + 0.5, blockpos.getZ() + 0.5, blockstate, 20
               );
               flag = this.level().destroyBlock(blockpos, false, this) || flag;
               fallingBlockEntity.setDeltaMovement(
                  fallingBlockEntity.getDeltaMovement()
                     .add(
                        this.position()
                           .subtract(fallingBlockEntity.position())
                           .multiply(
                              (-1.2 + this.random.nextDouble()) / 3.0, 0.2 + this.getRandom().nextGaussian() * 0.15, (-1.2 + this.random.nextDouble()) / 3.0
                           )
                     )
               );
               this.level().addFreshEntity(fallingBlockEntity);
            } else {
               flag = this.level().destroyBlock(blockpos, false, this) || flag;
            }
         }
      }
   }

   private void berserkBlockBreaking(int x, int y, int z) {
      int MthX = Mth.floor(this.getX());
      int MthY = Mth.floor(this.getY());
      int MthZ = Mth.floor(this.getZ());
      if (!this.level().isClientSide && EventHooks.canEntityGrief(this.level(), this)) {
         for (int k2 = -x; k2 <= x; k2++) {
            for (int l2 = -z; l2 <= z; l2++) {
               for (int j = 0; j <= y; j++) {
                  int i3 = MthX + k2;
                  int k = MthY + j;
                  int l = MthZ + l2;
                  BlockPos blockpos = new BlockPos(i3, k, l);
                  BlockState block = this.level().getBlockState(blockpos);
                  BlockEntity tileEntity = this.level().getBlockEntity(blockpos);
                  if (!block.isAir() && !block.is(ModTag.NETHERITE_MONSTROSITY_IMMUNE)) {
                     if (tileEntity == null && this.random.nextInt(4) + 1 == 4) {
                        this.level().removeBlock(blockpos, true);
                        Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(this.level(), i3 + 0.5, k + 0.5, l + 0.5, block, 5);
                        this.level().setBlock(blockpos, block.getFluidState().createLegacyBlock(), 3);
                        fallingBlockEntity.setDeltaMovement(
                           fallingBlockEntity.getDeltaMovement()
                              .add(
                                 this.position()
                                    .subtract(fallingBlockEntity.position())
                                    .multiply(
                                       (-1.2 + this.random.nextDouble()) / 3.0,
                                       (-1.1 + this.random.nextDouble()) / 3.0,
                                       (-1.2 + this.random.nextDouble()) / 3.0
                                    )
                              )
                        );
                        this.level().addFreshEntity(fallingBlockEntity);
                     } else {
                        this.level().destroyBlock(new BlockPos(i3, k, l), this.shouldDropItem(tileEntity));
                     }
                  }
               }
            }
         }
      }
   }

   private void BlockBreaking() {
      if (!this.isNoAi() && !this.level().isClientSide && this.blockBreakCounter == 0 && EventHooks.canEntityGrief(this.level(), this)) {
         AABB box = this.getBoundingBox();
         BlockPos min = new BlockPos(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ));
         BlockPos max = new BlockPos(Mth.floor(box.maxX), Mth.floor(box.maxY), Mth.floor(box.maxZ));

         for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            BlockState blockState = this.level().getBlockState(pos);
            if (!blockState.isAir() && !blockState.is(ModTag.NETHERITE_MONSTROSITY_IMMUNE)) {
               BlockEntity tileEntity = this.level().getBlockEntity(pos);
               if (this.level().destroyBlock(pos, this.shouldDropItem(tileEntity))) {
                  this.blockBreakCounter = 10;
               }
            }
         }
      }
   }

   private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
      return offset.xRot(-xRot * (float) (Math.PI / 180.0)).yRot(-yRot * (float) (Math.PI / 180.0));
   }

   private boolean shouldDropItem(BlockEntity tileEntity) {
      return tileEntity == null ? this.random.nextInt(3) + 1 == 3 : true;
   }

   public boolean isBerserk() {
      return this.getHealth() <= this.getMaxHealth() * 0.4F;
   }

   protected boolean isAffectedByFluids() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setDeltaMovement(itementity.getDeltaMovement().multiply(0.0, 3.5, 0.0));
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   private void setPartPosition(Netherite_Monstrosity_Part part, double offsetX, double offsetY, double offsetZ) {
      part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.monstrosityParts;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      Cm_Part_Entity.assignPartIDs(this);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.MONSTROSITYHURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.MONSTROSITYDEATH.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      return (SoundEvent)ModSounds.MONSTROSITY_MUSIC.get();
   }

   @Override
   protected boolean canPlayMusic() {
      return super.canPlayMusic() && this.getIsAwaken();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new CMPathNavigateGround(this, worldIn);
   }

   public void startSeenByPlayer(ServerPlayer player) {
      super.startSeenByPlayer(player);
      this.bossInfo.addPlayer(player);
   }

   public void stopSeenByPlayer(ServerPlayer player) {
      super.stopSeenByPlayer(player);
      this.bossInfo.removePlayer(player);
   }

   @Override
   public boolean removeWhenFarAway(double p_21542_) {
      return false;
   }

   @Override
   protected boolean shouldDespawnInPeaceful() {
      return false;
   }

   @Override
   protected boolean canRide(Entity p_31508_) {
      return false;
   }

   static class Flareshoot extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final float random;

      public Flareshoot(
         Netherite_Monstrosity_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) >= 10.0F
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.getSensing().hasLineOfSight(target)
            && this.entity.flare_shoot_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.flare_shoot_cooldown = 120;
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         int lavabombcount = 5;
         if (target != null && this.entity.attackTicks == this.attackshot) {
            for (int i = 0; i < lavabombcount; i++) {
               float f = Mth.cos(this.entity.yBodyRot * (float) (Math.PI / 180.0));
               float f1 = Mth.sin(this.entity.yBodyRot * (float) (Math.PI / 180.0));
               double theta = this.entity.yBodyRot * (Math.PI / 180.0);
               theta += Math.PI / 2;
               double vecX = Math.cos(theta);
               double vecZ = Math.sin(theta);
               double vec = 2.2;
               double math = 3.4;
               Flare_Bomb_Entity lava = new Flare_Bomb_Entity((EntityType<Flare_Bomb_Entity>)ModEntities.FLARE_BOMB.get(), this.entity.level(), this.entity);
               lava.setPosRaw(this.entity.getX() + vec * vecX + f * math, this.entity.getY(0.65), this.entity.getZ() + vec * vecZ + f1 * math);
               double d0 = target.getX() - lava.getX();
               double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - lava.getY();
               double d2 = target.getZ() - lava.getZ();
               double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
               lava.shoot(d0, d1 + d3 * 0.2F, d2, 1.0F, 1 + i * 8);
               this.entity.level().addFreshEntity(lava);
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class Magmashoot extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final float random;

      public Magmashoot(
         Netherite_Monstrosity_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) >= 14.0F
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.getSensing().hasLineOfSight(target)
            && this.entity.getMagazine() < CMCommonConfig.NetheriteMonstrosity.Lavabombmagazine
            && this.entity.shoot_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.shoot_cooldown = 240;
         this.entity.setMagazine(this.entity.getMagazine() + 1);
      }

      @Override
      public void tick() {
         LivingEntity target = this.entity.getTarget();
         super.tick();
         int lavabombcount = CMCommonConfig.NetheriteMonstrosity.Lavabombamount;
         if (target != null && this.entity.attackTicks == this.attackshot) {
            for (int i = 0; i < lavabombcount; i++) {
               Lava_Bomb_Entity lava = new Lava_Bomb_Entity((EntityType<Lava_Bomb_Entity>)ModEntities.LAVA_BOMB.get(), this.entity.level(), this.entity);
               double d0 = target.getX() - this.entity.headPart.getX();
               double d1 = target.getBoundingBox().minY + target.getBbHeight() / 3.0F - lava.getY();
               double d2 = target.getZ() - this.entity.headPart.getZ();
               double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
               lava.setMaxLavaTime(
                  CMCommonConfig.NetheriteMonstrosity.LavabombDuration
                     + this.entity.getRandom().nextInt(CMCommonConfig.NetheriteMonstrosity.LavabombRandomDuration)
               );
               lava.shoot(d0, d1 + d3 * 0.2F, d2, 1.0F, 24 - this.entity.level().getDifficulty().getId() * 4);
               this.entity.level().addFreshEntity(lava);
            }
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class MonstrosityPhaseChangeGoal extends Goal {
      protected final Netherite_Monstrosity_Entity entity;
      private final int getattackstate;
      private final int attackstate;
      private final int attackendstate;
      private final int attackMaxtick;

      public MonstrosityPhaseChangeGoal(Netherite_Monstrosity_Entity entity, int getattackstate, int attackstate, int attackendstate, int attackMaxtick) {
         this.entity = entity;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
         this.getattackstate = getattackstate;
         this.attackstate = attackstate;
         this.attackendstate = attackendstate;
         this.attackMaxtick = attackMaxtick;
      }

      public boolean canUse() {
         return !this.entity.getIsBerserk() && this.entity.getAttackState() == this.getattackstate && this.entity.isBerserk();
      }

      public void start() {
         this.entity.setIsBerserk(true);
         if (this.getattackstate != this.attackstate) {
            this.entity.setAttackState(this.attackstate);
         }
      }

      public void stop() {
         this.entity.setAttackState(this.attackendstate);
      }

      public boolean canContinueToUse() {
         return this.attackMaxtick > 0 ? this.entity.attackTicks <= this.attackMaxtick : this.canUse();
      }
   }

   class NMDoNothingGoal extends Goal {
      public NMDoNothingGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity target = Netherite_Monstrosity_Entity.this.getTarget();
         return !Netherite_Monstrosity_Entity.this.getIsAwaken()
            && (
               target == null
                  || !target.isAlive()
                  || !(Netherite_Monstrosity_Entity.this.distanceToSqr(target) < 255.0)
                  || !Netherite_Monstrosity_Entity.this.getSensing().hasLineOfSight(target)
            );
      }

      public void tick() {
         Netherite_Monstrosity_Entity.this.setDeltaMovement(0.0, Netherite_Monstrosity_Entity.this.getDeltaMovement().y, 0.0);
      }

      public void stop() {
         Netherite_Monstrosity_Entity.this.setIsAwaken(true);
         Netherite_Monstrosity_Entity.this.setAttackState(2);
      }

      public boolean isInterruptable() {
         return false;
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   static class ShoulderCheck extends InternalAttackGoal {
      private final Netherite_Monstrosity_Entity entity;
      private final int attackshot;
      private final int attackendshot;
      private final float random;

      public ShoulderCheck(
         Netherite_Monstrosity_Entity entity,
         int getAttackState,
         int attackstate,
         int attackendstate,
         int attackMaxtick,
         int attackseetick,
         float attackrange,
         int attackshot,
         int attackendshot,
         float random
      ) {
         super(entity, getAttackState, attackstate, attackendstate, attackMaxtick, attackseetick, attackrange);
         this.entity = entity;
         this.attackshot = attackshot;
         this.attackendshot = attackendshot;
         this.random = random;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      public boolean canUse() {
         LivingEntity target = this.entity.getTarget();
         return super.canUse()
            && target != null
            && this.entity.distanceTo(target) >= 5.75F
            && this.entity.getRandom().nextFloat() * 100.0F < this.random
            && this.entity.getSensing().hasLineOfSight(target)
            && this.entity.check_cooldown <= 0;
      }

      @Override
      public void start() {
         super.start();
      }

      @Override
      public void stop() {
         super.stop();
         this.entity.check_cooldown = 80;
      }

      @Override
      public void tick() {
         super.tick();
         if (this.entity.attackTicks > this.attackshot
            && this.entity.attackTicks < this.attackendshot
            && this.entity.getMoveControl() instanceof FowardMoveController onyxMoveController) {
            onyxMoveController.forward(1.0F, 2.0F);
         }
      }

      @Override
      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }
}
