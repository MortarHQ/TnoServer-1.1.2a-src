package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.client.particle.Options.RoarParticleOptions;
import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.AI.AnimalAIRandomSwimming;
import com.github.L_Ender.cataclysm.entity.AI.EntityAINearestTarget3D;
import com.github.L_Ender.cataclysm.entity.AI.HurtByNearestTargetGoal;
import com.github.L_Ender.cataclysm.entity.AI.MobAIFindWater;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.AnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.AI.SimpleAnimationGoal;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.LLibrary_Boss_Monster;
import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.etc.CMBossInfoServer;
import com.github.L_Ender.cataclysm.entity.etc.IHoldEntity;
import com.github.L_Ender.cataclysm.entity.etc.ISemiAquatic;
import com.github.L_Ender.cataclysm.entity.etc.SmartBodyHelper2;
import com.github.L_Ender.cataclysm.entity.etc.path.CMPathNavigateGround;
import com.github.L_Ender.cataclysm.entity.partentity.Cm_Part_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.cataclysm.message.MessageMusic;
import com.github.L_Ender.cataclysm.util.EntityUtil;
import com.github.L_Ender.cataclysm.world.data.CMWorldData;
import com.github.L_Ender.lionfishapi.server.animation.Animation;
import com.github.L_Ender.lionfishapi.server.animation.AnimationHandler;
import com.github.L_Ender.lionfishapi.server.animation.IAnimatedEntity;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;

public class The_Leviathan_Entity extends LLibrary_Boss_Monster implements ISemiAquatic, IHoldEntity {
   public static final Animation LEVIATHAN_GRAB = Animation.create(160);
   public static final Animation LEVIATHAN_GRAB_BITE = Animation.create(13);
   public static final Animation LEVIATHAN_BITE = Animation.create(24);
   public static final Animation LEVIATHAN_ABYSS_BLAST = Animation.create(184);
   public static final Animation LEVIATHAN_ABYSS_BLAST_FIRE = Animation.create(216);
   public static final Animation LEVIATHAN_RUSH = Animation.create(157);
   public static final Animation LEVIATHAN_STUN = Animation.create(90);
   public static final Animation LEVIATHAN_ABYSS_BLAST_PORTAL = Animation.create(142);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_UPPER_R = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_LOWER_R = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_UPPER_L = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_STRIKE_LOWER_L = Animation.create(44);
   public static final Animation LEVIATHAN_TENTACLE_HOLD = Animation.create(63);
   public static final Animation LEVIATHAN_TENTACLE_HOLD_BLAST = Animation.create(189);
   public static final Animation LEVIATHAN_TAIL_WHIPS = Animation.create(42);
   public static final Animation LEVIATHAN_BREAK_DIMENSION = Animation.create(156);
   public static final Animation LEVIATHAN_PHASE2 = Animation.create(200);
   public static final Animation LEVIATHAN_DEATH = Animation.create(210);
   public static final Animation LEVIATHAN_MINE = Animation.create(68);
   public final The_Leviathan_Part headPart;
   public final The_Leviathan_Part tailPart1;
   public final The_Leviathan_Part tailPart2;
   public final The_Leviathan_Part[] leviathanParts;
   public boolean blocksBylefttentacle = true;
   public boolean blocksByrighttentacle = true;
   public boolean CanGrab = true;
   public boolean CanAbyss_Blast = true;
   public boolean CanRush = true;
   public boolean CanTailWhips = true;
   public boolean CanBite = true;
   public boolean CanAbyss_Blast_Portal = true;
   public boolean CanCrackDimension = true;
   public boolean CanMine = true;
   public float NoSwimProgress = 0.0F;
   public float prevNoSwimProgress = 0.0F;
   public float LeftTentacleProgress = 0.0F;
   public float prevLeftTentacleProgress = 0.0F;
   public float RightTentacleProgress = 0.0F;
   public float prevRightTentacleProgress = 0.0F;
   private boolean isLandNavigator;
   public Vec3 teleportPos = null;
   public Abyss_Portal_Entity portalTarget = null;
   public boolean fullyThrough = true;
   public static final int GRAB_HUNTING_COOLDOWN = 70;
   public static final int RUSH_HUNTING_COOLDOWN = 100;
   public static final int BLAST_HUNTING_COOLDOWN = 140;
   public static final int CRACK_HUNTING_COOLDOWN = 250;
   public static final int BLAST_PORTAL_HUNTING_COOLDOWN = 120;
   public static final int TAIL_WHIPS_HUNTING_COOLDOWN = 100;
   public static final int BITE_COOLDOWN = 100;
   public static final int MELEE_COOLDOWN = 40;
   public static final int MINE_COOLDOWN = 100;
   private The_Leviathan_Entity.AttackMode mode = The_Leviathan_Entity.AttackMode.CIRCLE;
   private int hunting_cooldown = 160;
   private int makePortalCooldown = 0;
   private int bite_cooldown = 0;
   private int melee_cooldown = 0;
   private int waterstillTicks = 0;
   public double endPosX;
   public double endPosY;
   public double endPosZ;
   public double collidePosX;
   public double collidePosY;
   public double collidePosZ;
   private int destroyBlocksTick;
   private int blockBreakCounter;
   public float LayerBrightness;
   private Vec3 prevTailPos = new Vec3(0.0, 0.0, 0.0);
   private final CMBossInfoServer bossInfo = new CMBossInfoServer(this.getDisplayName(), BossBarColor.PURPLE, false, 5);
   private static final EntityDataAccessor<Integer> BLAST_CHANCE = SynchedEntityData.defineId(The_Leviathan_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> MODE_CHANCE = SynchedEntityData.defineId(The_Leviathan_Entity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> MELT_DOWN = SynchedEntityData.defineId(The_Leviathan_Entity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<UUID>> TONGUE_UUID = SynchedEntityData.defineId(
      The_Leviathan_Entity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   private static final EntityDataAccessor<Integer> TONGUE_ID = SynchedEntityData.defineId(The_Leviathan_Entity.class, EntityDataSerializers.INT);

   public The_Leviathan_Entity(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.xpReward = 500;
      if (worldIn.isClientSide) {
         this.socketPosArray = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
      }

      this.headPart = new The_Leviathan_Part(this, 2.8F, 2.8F);
      this.tailPart1 = new The_Leviathan_Part(this, 1.5F, 2.4F);
      this.tailPart2 = new The_Leviathan_Part(this, 1.3F, 2.4F);
      this.leviathanParts = new The_Leviathan_Part[]{this.headPart, this.tailPart1, this.tailPart2};
      this.switchNavigator(false);
      this.setConfigattribute(this, CMCommonConfig.Leviathan.healthMultiplier, CMCommonConfig.Leviathan.attackMultiplier);
   }

   public static Builder leviathan() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, 400.0)
         .add(Attributes.FOLLOW_RANGE, 64.0)
         .add(Attributes.ARMOR, 10.0)
         .add(Attributes.ATTACK_DAMAGE, 15.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.MOVEMENT_SPEED, 0.15);
   }

   @Override
   public float DamageCap() {
      return (float)CMCommonConfig.Leviathan.damageCap;
   }

   @Override
   public float NatureRegen() {
      return (float)CMCommonConfig.Leviathan.natureHeal;
   }

   @Override
   public float DpsCap() {
      return (float)CMCommonConfig.Leviathan.dpsCap;
   }

   public int DpsMulti() {
      return CMCommonConfig.Leviathan.dpsLimitTime;
   }

   @Override
   public double RangeLimit() {
      return CMCommonConfig.Leviathan.rangeCap;
   }

   protected PathNavigation createNavigation(Level worldIn) {
      return new WaterBoundPathNavigation(this, worldIn);
   }

   @Override
   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder p_326229_) {
      super.defineSynchedData(p_326229_);
      p_326229_.define(BLAST_CHANCE, 0);
      p_326229_.define(MODE_CHANCE, 0);
      p_326229_.define(MELT_DOWN, false);
      p_326229_.define(TONGUE_UUID, Optional.empty());
      p_326229_.define(TONGUE_ID, -1);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(2, new MobAIFindWater(this, 2.0));
      this.goalSelector.addGoal(3, new The_Leviathan_Entity.LeviathanAttackGoal(this));
      this.goalSelector.addGoal(4, new AnimalAIRandomSwimming(this, 1.0, 3, 15));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanGrabAttackGoal(this, LEVIATHAN_GRAB));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanAbyssDimensionAttackGoal(this, LEVIATHAN_BREAK_DIMENSION));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanStunGoal(this, LEVIATHAN_STUN));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanGrabBiteAttackGoal(this, LEVIATHAN_GRAB_BITE));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanBiteAttackGoal(this, LEVIATHAN_BITE));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanPhase2Goal(this, LEVIATHAN_PHASE2));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanTailWhipsAttackGoal(this, LEVIATHAN_TAIL_WHIPS));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanTentacleAttackGoal(this));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanBlastAttackGoal(this, LEVIATHAN_ABYSS_BLAST));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanBlastFireAttackGoal(this, LEVIATHAN_ABYSS_BLAST_FIRE));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanAbyssBlastPortalAttackGoal(this, LEVIATHAN_ABYSS_BLAST_PORTAL));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanRushAttackGoal(this, LEVIATHAN_RUSH));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanTentacleHoldAttackGoal(this, LEVIATHAN_TENTACLE_HOLD));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanTentacleHoldBlastAttackGoal(this, LEVIATHAN_TENTACLE_HOLD_BLAST));
      this.goalSelector.addGoal(0, new The_Leviathan_Entity.LeviathanMineAttackGoal(this, LEVIATHAN_MINE));
      this.targetSelector.addGoal(1, new HurtByNearestTargetGoal(this));
      this.targetSelector.addGoal(2, new EntityAINearestTarget3D(this, Player.class, false, true));
      this.targetSelector
         .addGoal(
            3,
            new EntityAINearestTarget3D<LivingEntity>(this, LivingEntity.class, 160, false, true, ModEntities.buildPredicateFromTag(ModTag.LEVIATHAN_TARGET))
         );
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.moveControl = new MoveControl(this);
         this.navigation = new CMPathNavigateGround(this, this.level());
         this.isLandNavigator = true;
      } else {
         this.moveControl = new The_Leviathan_Entity.LeviathanMoveController(this, 10.0F, 1.0F, 6.0F);
         this.navigation = new WaterBoundPathNavigation(this, this.level());
         this.isLandNavigator = false;
      }
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{
         LEVIATHAN_PHASE2,
         LEVIATHAN_BITE,
         LEVIATHAN_BREAK_DIMENSION,
         LEVIATHAN_GRAB,
         LEVIATHAN_TAIL_WHIPS,
         LEVIATHAN_ABYSS_BLAST_PORTAL,
         LEVIATHAN_GRAB_BITE,
         LEVIATHAN_ABYSS_BLAST,
         LEVIATHAN_RUSH,
         LEVIATHAN_TENTACLE_STRIKE_UPPER_R,
         LEVIATHAN_TENTACLE_STRIKE_UPPER_L,
         LEVIATHAN_TENTACLE_STRIKE_LOWER_L,
         LEVIATHAN_TENTACLE_STRIKE_LOWER_R,
         LEVIATHAN_STUN,
         LEVIATHAN_DEATH,
         LEVIATHAN_TENTACLE_HOLD,
         LEVIATHAN_TENTACLE_HOLD_BLAST,
         LEVIATHAN_ABYSS_BLAST_FIRE,
         LEVIATHAN_MINE
      };
   }

   public void travel(Vec3 travelVector) {
      if (this.isEffectiveAi() && this.isInWater()) {
         this.moveRelative(this.getSpeed(), travelVector);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
         if (this.getTarget() == null && this.getAnimation() == NO_ANIMATION) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
         }
      } else {
         super.travel(travelVector);
      }
   }

   private static Animation getRandomTantalcleStrike(RandomSource rand) {
      switch (rand.nextInt(4)) {
         case 0:
            return LEVIATHAN_TENTACLE_STRIKE_LOWER_L;
         case 1:
            return LEVIATHAN_TENTACLE_STRIKE_LOWER_R;
         case 2:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_L;
         case 3:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_R;
         default:
            return LEVIATHAN_TENTACLE_STRIKE_UPPER_R;
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (entityIn == this) {
         return true;
      } else if (super.isAlliedTo(entityIn)) {
         return true;
      } else {
         return !entityIn.getType().is(ModTag.TEAM_THE_LEVIATHAN) ? false : this.getTeam() == null && entityIn.getTeam() == null;
      }
   }

   @Override
   public boolean hurt(DamageSource source, float damage) {
      Entity entity = source.getDirectEntity();
      if (!(entity instanceof Abyss_Blast_Entity) && !(entity instanceof Portal_Abyss_Blast_Entity)) {
         if (this.destroyBlocksTick <= 0) {
            this.destroyBlocksTick = 20;
         }

         double range = this.calculateRange(source);
         boolean flag1 = this.canInFluidType(this.getEyeInFluidType());
         if (!flag1 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) && CMCommonConfig.Leviathan.ImmuneOutofWater) {
            if (entity instanceof Player player) {
               player.displayClientMessage(Component.translatable("entity.cataclysm.the_leviathan_immune"), true);
            }

            return false;
         } else {
            if (this.getAnimation() == LEVIATHAN_PHASE2 && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
               return false;
            }

            boolean attack = super.hurt(source, damage);
            if (this.getAnimation() == LEVIATHAN_RUSH && this.getAnimationTick() >= 38 && this.getAnimationTick() <= 54 && attack) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this, LEVIATHAN_STUN);
            }

            return attack;
         }
      } else {
         return false;
      }
   }

   @Override
   public int HealCooldown() {
      return 600;
   }

   public ItemEntity spawnAtLocation(ItemStack stack) {
      ItemEntity itementity = this.spawnAtLocation(stack, 0.0F);
      if (itementity != null) {
         itementity.setGlowingTag(true);
         itementity.setExtendedLifetime();
      }

      return itementity;
   }

   private boolean canInFluidType(FluidType type) {
      NeoForgeMod.WATER_TYPE.value();
      return type.canSwim(this.self());
   }

   public void onInsideBubbleColumn(boolean p_20322_) {
   }

   public void onAboveBubbleCol(boolean p_20313_) {
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return source.is(DamageTypes.IN_WALL) || source.is(DamageTypes.FALLING_BLOCK) || super.isInvulnerableTo(source);
   }

   public boolean attackEntityFromPart(The_Leviathan_Part leviathan_part, DamageSource source, float amount) {
      return this.hurt(source, amount);
   }

   @Override
   public void tick() {
      super.tick();
      this.setYRot(this.yHeadRot);
      if (this.isInWater() && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!this.isInWater() && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.getTongue() instanceof The_Leviathan_Tongue_Entity magneticWeapon) {
         this.entityData.set(TONGUE_ID, magneticWeapon.getId());
         magneticWeapon.setControllerUUID(this.getUUID());
      }

      if (!this.getPassengers().isEmpty() && ((Entity)this.getPassengers().get(0)).isShiftKeyDown() && this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST) {
         ((Entity)this.getPassengers().get(0)).setShiftKeyDown(false);
      }

      LivingEntity target = this.getTarget();
      if (!this.level().isClientSide) {
         float halfHealth = this.getMaxHealth() / 2.0F;
         if (!this.getMeltDown()) {
            this.bossInfo.setProgress((this.getHealth() - halfHealth) / (this.getMaxHealth() - halfHealth));
         } else {
            this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
            this.bossInfo.setRenderType(6);
         }
      }

      boolean groundAnimate = !this.isInWater();
      this.prevNoSwimProgress = this.NoSwimProgress;
      if (groundAnimate) {
         if (this.NoSwimProgress < 10.0F) {
            this.NoSwimProgress++;
         }
      } else if (this.NoSwimProgress > 0.0F) {
         this.NoSwimProgress--;
      }

      this.prevLeftTentacleProgress = this.LeftTentacleProgress;
      this.prevRightTentacleProgress = this.RightTentacleProgress;
      if (this.blocksByrighttentacle) {
         if (this.RightTentacleProgress < 10.0F) {
            this.RightTentacleProgress++;
         }
      } else if (this.RightTentacleProgress > 0.0F) {
         this.RightTentacleProgress--;
      }

      if (this.blocksBylefttentacle) {
         if (this.LeftTentacleProgress < 10.0F) {
            this.LeftTentacleProgress++;
         }
      } else if (this.LeftTentacleProgress > 0.0F) {
         this.LeftTentacleProgress--;
      }

      if (this.tickCount % 10 == 0) {
         this.blocksByrighttentacle = this.checkBlocksByTentacle(1.0F, -3.0F)
            || this.checkBlocksByTentacle(2.0F, -3.0F)
            || this.checkBlocksByTentacle(3.0F, -3.0F)
            || this.checkBlocksByTentacle(4.0F, -3.0F)
            || this.checkBlocksByTentacle(5.0F, -3.0F);
         this.blocksBylefttentacle = this.checkBlocksByTentacle(1.0F, 3.0F)
            || this.checkBlocksByTentacle(2.0F, 3.0F)
            || this.checkBlocksByTentacle(3.0F, 3.0F)
            || this.checkBlocksByTentacle(4.0F, 3.0F)
            || this.checkBlocksByTentacle(5.0F, 3.0F);
      }

      if (this.portalTarget != null && this.portalTarget.getLifespan() < 5) {
         this.portalTarget = null;
      }

      if (this.teleportPos != null) {
         this.setPos(this.teleportPos.x, this.teleportPos.y, this.teleportPos.z);
         this.teleportPos = null;
      }

      if (this.makePortalCooldown > 0) {
         this.makePortalCooldown--;
      }

      if (this.hunting_cooldown > 0) {
         this.hunting_cooldown--;
      }

      if (this.bite_cooldown > 0) {
         this.bite_cooldown--;
      }

      if (this.melee_cooldown > 0) {
         this.melee_cooldown--;
      }

      if (!this.isNoAi()) {
         if (this.getAnimation() == NO_ANIMATION && !this.getMeltDown() && this.isMeltDown() && this.isAlive()) {
            this.setAnimation(LEVIATHAN_PHASE2);
         }

         if (this.getAnimation() == NO_ANIMATION && this.getTarget() != null) {
            if (Math.abs(this.xo - this.getX()) < 0.01F && Math.abs(this.yo - this.getY()) < 0.01F && Math.abs(this.zo - this.getZ()) < 0.01F) {
               this.waterstillTicks++;
            } else {
               this.waterstillTicks = 0;
            }

            if (this.waterstillTicks > 40 && this.makePortalCooldown == 0) {
               this.createStuckPortal();
            }
         }

         if (!this.level().isClientSide) {
            if (this.destroyBlocksTick > 0) {
               this.destroyBlocksTick--;
               if (this.destroyBlocksTick == 0) {
                  if (CMCommonConfig.Leviathan.ignoreMobGriefing) {
                     this.blockbreak(0.5, 0.5, 0.5);
                  } else if (EventHooks.canEntityGrief(this.level(), this)) {
                     this.blockbreak(0.5, 0.5, 0.5);
                  }
               }
            }

            if (this.mode == The_Leviathan_Entity.AttackMode.MELEE) {
               if (CMCommonConfig.Leviathan.ignoreMobGriefing) {
                  this.blockbreak2();
               } else if (EventHooks.canEntityGrief(this.level(), this)) {
                  this.blockbreak2();
               }
            }
         }

         float f17 = this.getYRot() * (float) Math.PI / 180.0F;
         float pitch = this.getXRot() * (float) Math.PI / 180.0F;
         float f3 = Mth.sin(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         float f18 = Mth.cos(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         this.setPartPosition(this.headPart, f3 * -3.8F, -pitch * 3.0F, -f18 * -3.8F);
         this.setPartPosition(this.tailPart1, f3 * 3.3F, -pitch * -5.0F, -f18 * 3.3F);
         this.setPartPosition(this.tailPart2, f3 * 4.7F, -pitch * -8.0F, -f18 * 4.7F);
         Vec3[] avector3d = new Vec3[this.leviathanParts.length];

         for (int j = 0; j < this.leviathanParts.length; j++) {
            avector3d[j] = new Vec3(this.leviathanParts[j].getX(), this.leviathanParts[j].getY(), this.leviathanParts[j].getZ());
         }

         for (int l = 0; l < this.leviathanParts.length; l++) {
            this.leviathanParts[l].xo = avector3d[l].x;
            this.leviathanParts[l].yo = avector3d[l].y;
            this.leviathanParts[l].zo = avector3d[l].z;
            this.leviathanParts[l].xOld = avector3d[l].x;
            this.leviathanParts[l].yOld = avector3d[l].y;
            this.leviathanParts[l].zOld = avector3d[l].z;
         }
      }
   }

   public void aiStep() {
      super.aiStep();
      float yaw = this.getYRot() * (float) Math.PI / 180.0F;
      float pitch = this.getXRot() * (float) Math.PI / 180.0F;
      float fx = Mth.sin(yaw) * (1.0F - Math.abs(this.getXRot() / 90.0F));
      float fz = Mth.cos(yaw) * (1.0F - Math.abs(this.getXRot() / 90.0F));
      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST) {
         if (this.getAnimationTick() < 30 && this.level().isClientSide) {
            for (int i = 0; i < 20; i++) {
               float f = -Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
               float f2 = -Mth.sin(this.getXRot() * (float) (Math.PI / 180.0));
               float f3 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
               this.level()
                  .addParticle(
                     ParticleTypes.PORTAL,
                     this.getX() + f * 4.0,
                     this.getY() + f2 * 3.5,
                     this.getZ() + f3 * 4.0,
                     (this.random.nextDouble() - 0.5) * 2.0,
                     -this.random.nextDouble(),
                     (this.random.nextDouble() - 0.5) * 2.0
                  );
            }
         }

         if (this.getAnimationTick() >= 82) {
            this.setXRot(this.xRotO);
         }

         if (this.getAnimationTick() == 84 && this.getMeltDown()) {
            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, -1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.0, 1.0).yRot(-(60 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, 1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }
         }

         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 144;

         for (int j = 40; i <= 184; j--) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         if (this.getAnimationTick() == 84) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 90, 10);
         }
      }

      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST_FIRE) {
         if (this.getAnimationTick() < 30 && this.level().isClientSide) {
            for (int i = 0; i < 20; i++) {
               float f = -Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
               float f2 = -Mth.sin(this.getXRot() * (float) (Math.PI / 180.0));
               float f3 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
               this.level()
                  .addParticle(
                     ParticleTypes.PORTAL,
                     this.getX() + f * 4.0,
                     this.getY() + f2 * 3.5,
                     this.getZ() + f3 * 4.0,
                     (this.random.nextDouble() - 0.5) * 2.0,
                     -this.random.nextDouble(),
                     (this.random.nextDouble() - 0.5) * 2.0
                  );
            }
         }

         if (this.getAnimationTick() == 84 || this.getAnimationTick() == 129 || this.getAnimationTick() == 174) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.ABYSS_BLAST_ONLY_SHOOT.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         }

         if (this.getAnimationTick() >= 80 && this.getAnimationTick() <= 118
            || this.getAnimationTick() >= 125 && this.getAnimationTick() <= 163
            || this.getAnimationTick() >= 170 && this.getAnimationTick() <= 198) {
            this.setXRot(this.xRotO);
         }

         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 176;

         for (int j = 40; i <= 216; j--) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         if (this.getAnimationTick() == 84 || this.getAnimationTick() == 129 || this.getAnimationTick() == 174) {
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 20, 10);
         }
      }

      if (this.getAnimation() == LEVIATHAN_RUSH) {
         if (this.getAnimationTick() == 54) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 13.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 60, 10);
            this.roarDarkness(48.0, 48.0, 48.0, 48.0, 20);
         }

         if (this.getAnimationTick() == 57) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 14.0F);
         }

         if (this.getAnimationTick() == 60) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 15.0F);
         }
      }

      if (this.getAnimation() == LEVIATHAN_GRAB_BITE && this.getAnimationTick() == 2) {
         this.biteattack(6.5F, 1.5, 1.5, 1.5, 100);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_UPPER_R) {
         this.Tentacleattack(24, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_LOWER_R) {
         this.Tentacleattack(28, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_UPPER_L) {
         this.Tentacleattack(26, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_STRIKE_LOWER_L) {
         this.Tentacleattack(21, 9.0F, 2.0, 2.0, 2.0);
      }

      if (this.getAnimation() == LEVIATHAN_STUN) {
         if (this.getAnimationTick() == 52) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 0.8F);
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 8.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 40, 10);
            if (this.getMeltDown()) {
               for (int i = 0; i < 3; i++) {
                  Vec3 motion = new Vec3(0.5, -1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
                  this.shootAbyssOrb(motion.x, motion.y, motion.z);
               }

               for (int i = 0; i < 6; i++) {
                  Vec3 motion = new Vec3(1.0, 0.0, 1.0).yRot(-(60 * i) * (float) (Math.PI / 180.0));
                  this.shootAbyssOrb(motion.x, motion.y, motion.z);
               }

               for (int i = 0; i < 3; i++) {
                  Vec3 motion = new Vec3(0.5, 1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
                  this.shootAbyssOrb(motion.x, motion.y, motion.z);
               }
            }
         }

         if (this.getAnimationTick() == 55) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 9.0F);
         }
      }

      if (this.getAnimation() == LEVIATHAN_ABYSS_BLAST_PORTAL) {
         if (this.getAnimationTick() == 56) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_ROAR.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 60, 10);
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 13.0F);
            this.roarDarkness(48.0, 48.0, 48.0, 48.0, 80);
         }

         if (this.getAnimationTick() == 59) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 14.0F);
         }

         if (this.getAnimationTick() == 62) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 15.0F);
         }

         int i = 16;

         for (int j = 0; i <= 56; j++) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 102;

         for (int j = 40; i <= 142; j--) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }
      }

      this.SwingParticles();
      if (this.getAnimation() == LEVIATHAN_TAIL_WHIPS && this.getAnimationTick() == 18) {
         this.TailWhips();
      }

      if (this.getAnimation() == LEVIATHAN_BREAK_DIMENSION) {
         if (this.getAnimationTick() == 24) {
            this.level().playSound((Player)null, this, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 1.0F, 1.0F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.05F, 10, 10);
         }

         if (this.getAnimationTick() == 62) {
            this.level().playSound((Player)null, this, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 2.0F, 1.1F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 10, 10);

            for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(15.0))) {
               if (entity instanceof Dimensional_Rift_Entity rift && rift.getStage() < 4) {
                  rift.setStage(rift.getStage() + 1);
               }
            }
         }

         if (this.getAnimationTick() == 94) {
            this.level().playSound((Player)null, this, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 3.0F, 1.2F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.15F, 10, 10);

            for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(15.0))) {
               if (entity instanceof Dimensional_Rift_Entity rift && rift.getStage() < 4) {
                  rift.setStage(rift.getStage() + 1);
               }
            }
         }

         if (this.getAnimationTick() == 126) {
            this.level().playSound((Player)null, this, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.HOSTILE, 3.0F, 1.3F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.2F, 10, 10);

            for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(15.0))) {
               if (entity instanceof Dimensional_Rift_Entity rift && rift.getStage() < 4) {
                  rift.setStage(rift.getStage() + 1);
               }
            }
         }
      }

      if (this.getAnimation() == LEVIATHAN_BITE) {
         if (this.getAnimationTick() == 11) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         }

         if (this.getAnimationTick() == 13) {
            this.biteattack(6.0F, 1.5, 1.5, 1.5, 100);
         }
      }

      if (this.getAnimation() == LEVIATHAN_PHASE2) {
         if (this.getAnimationTick() == 1 && CMCommonConfig.Leviathan.SeparatePhaseMusic && !this.level().isClientSide && this.getBossMusic() != null) {
            PacketDistributor.sendToAllPlayers(new MessageMusic(this.getId(), false), new CustomPacketPayload[0]);
         }

         if (this.getAnimationTick() == 90) {
            if (!this.getMeltDown()) {
               this.setMeltDown(true);
            }

            if (CMCommonConfig.Leviathan.SeparatePhaseMusic && !this.level().isClientSide && this.getBossMusic() != null) {
               PacketDistributor.sendToAllPlayers(new MessageMusic(this.getId(), true), new CustomPacketPayload[0]);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, -1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, -0.75, 1.0).yRot(-(60 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.0, 1.0).yRot(-(60 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 6; i++) {
               Vec3 motion = new Vec3(1.0, 0.75, 1.0).yRot(-(60 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            for (int i = 0; i < 3; i++) {
               Vec3 motion = new Vec3(0.5, 1.25, 0.5).yRot(-(120 * i) * (float) (Math.PI / 180.0));
               this.shootAbyssOrb(motion.x, motion.y, motion.z);
            }

            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
            ScreenShake_Entity.ScreenShake(this.level(), this.position(), 30.0F, 0.1F, 40, 10);
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 13.0F);
         }

         if (this.getAnimationTick() == 93) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 14.0F);
         }

         if (this.getAnimationTick() == 96) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 1.2F, 15.0F);
         }

         if (this.getAnimationTick() >= 70 && this.getAnimationTick() <= 80) {
            this.Sphereparticle(this.getEyeHeight(), 0.0F, 8.0F);
         }
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD && this.getAnimationTick() == 32) {
         this.TentacleHoldattack(7.0F, 2.5, 2.5, 2.5, 80);
      }

      if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST) {
         int i = 44;

         for (int j = 0; i <= 84; j++) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }

         i = 149;

         for (int j = 40; i <= 189; j--) {
            float l = j * 0.025F;
            if (this.getAnimationTick() == i) {
               this.LayerBrightness = l;
            }

            i++;
         }
      }

      if (this.getAnimation() == LEVIATHAN_MINE) {
         if (this.getAnimationTick() == 28) {
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 0.9F, 8.0F);
         }

         if (this.getAnimationTick() == 31) {
            this.level().playSound((Player)null, this, (SoundEvent)ModSounds.LEVIATHAN_STUN_ROAR.get(), SoundSource.HOSTILE, 3.0F, 0.8F);
            this.RoarParticle(this.getX() + fx * -4.0F, this.getY() - pitch * 3.0F, this.getZ() - fz * -4.0F, 60, 102, 26, 204, 0.9F, 1.0F, 0.9F, 9.0F);
         }
      }
   }

   private void RoarParticle(double x, double y, double z, int duration, int r, int g, int b, float a, float start, float inc, float end) {
      if (this.level().isClientSide) {
         this.level().addParticle(new RoarParticleOptions(duration, r, g, b, a, start, inc, end), x, y, z, 0.0, 0.0, 0.0);
      }
   }

   private void roarDarkness(double x, double y, double z, double radius, int time) {
      List<LivingEntity> entities = this.getEntityLivingBaseNearby(x, y, z, radius);
      if (!this.level().isClientSide) {
         for (LivingEntity inRange : entities) {
            if ((!(inRange instanceof Player) || !((Player)inRange).getAbilities().invulnerable) && !this.isAlliedTo(inRange)) {
               inRange.addEffect(new MobEffectInstance(MobEffects.DARKNESS, time));
            }
         }
      }
   }

   @Override
   protected void AfterDefeatBoss(@Nullable LivingEntity living) {
      if (living != null) {
         CMWorldData worldData = CMWorldData.get(this.level(), Level.OVERWORLD);
         if (worldData != null) {
            boolean prev = worldData.isLeviathanDefeatedOnce();
            if (!prev) {
               worldData.setLeviathanDefeatedOnce(true);
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.getPlayers(EntitySelector.NO_SPECTATORS)
                     .forEach(
                        serverPlayer -> serverPlayer.displayClientMessage(
                           Component.translatable("entity.cataclysm.the_leviathan.defeat_message").withStyle(ChatFormatting.DARK_PURPLE), true
                        )
                     );
               }
            }
         }
      }
   }

   private void TailWhips() {
      if (!this.level().isClientSide) {
         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(7.0, 7.0, 7.0))) {
            if (!this.isAlliedTo(entity) && !(entity instanceof The_Leviathan_Entity) && entity != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean flag = entity.hurt(
                  damagesource,
                  (float)(
                     (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), entity.getMaxHealth() * (float)CMCommonConfig.Leviathan.TailSwingHpDamage)
                  )
               );
               if (entity.isDamageSourceBlocked(damagesource) && entity instanceof Player player) {
                  EntityUtil.disableShield(player, 120);
               }

               if (flag) {
                  this.launch(entity, true);
                  entity.addEffect(new MobEffectInstance(ModEffect.EFFECTBONE_FRACTURE, 200));
               }
            }
         }
      }
   }

   private void Sphereparticle(float height, float vec, float size) {
      if (this.level().isClientSide && this.tickCount % 2 == 0) {
         double d0 = this.getX();
         double d1 = this.getY() + height;
         double d2 = this.getZ();
         double theta = this.yBodyRot * (Math.PI / 180.0);
         theta += Math.PI / 2;
         double vecX = Math.cos(theta);
         double vecZ = Math.sin(theta);

         for (float i = -size; i <= size; i++) {
            for (float j = -size; j <= size; j++) {
               for (float k = -size; k <= size; k++) {
                  double d3 = j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d4 = i + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d5 = k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                  double d6 = Mth.sqrt((float)(d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 + this.random.nextGaussian() * 0.05;
                  this.level().addParticle(ParticleTypes.REVERSE_PORTAL, d0 + vec * vecX, d1, d2 + vec * vecZ, d3 / d6, d4 / d6, d5 / d6);
                  if (i != -size && i != size && j != -size && j != size) {
                     k += size * 2.0F - 1.0F;
                  }
               }
            }
         }
      }
   }

   public boolean isMeltDown() {
      return this.getHealth() <= this.getMaxHealth() / 2.0F;
   }

   public void shootAbyssOrb(double xMotion, double yMotion, double zMotion) {
      if (this.getTarget() != null) {
         Abyss_Orb_Entity fireball = new Abyss_Orb_Entity(
            this, xMotion, yMotion, zMotion, this.level(), (float)CMCommonConfig.Leviathan.AbyssOrbdamage, this.getTarget()
         );
         fireball.setPos(fireball.getX(), this.getEyeY(), fireball.getZ());
         fireball.setUp(40);
         if (!this.level().isClientSide) {
            this.level().addFreshEntity(fireball);
         }
      } else {
         Abyss_Orb_Entity fireball = new Abyss_Orb_Entity(this, xMotion, yMotion, zMotion, this.level(), (float)CMCommonConfig.Leviathan.AbyssOrbdamage, null);
         fireball.setPos(fireball.getX(), this.getEyeY(), fireball.getZ());
         fireball.setUp(40);
         if (!this.level().isClientSide) {
            this.level().addFreshEntity(fireball);
         }
      }
   }

   private void launch(Entity e, boolean huge) {
      double d0 = e.getX() - this.getX();
      double d1 = e.getZ() - this.getZ();
      double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
      float f = huge ? 10.0F : 5.0F;
      e.push(d0 / d2 * f, huge ? 1.0 : 0.2F, d1 / d2 * f);
   }

   private void Tailswing() {
      Vec3 bladePos = this.socketPosArray[0];
      double length = this.prevTailPos.subtract(bladePos).length();
      int numClouds = (int)Math.floor(2.0 * length);

      for (int i = 0; i < numClouds; i++) {
         double x = this.prevTailPos.x + i * (bladePos.x - this.prevTailPos.x) / numClouds;
         double y = this.prevTailPos.y + i * (bladePos.y - this.prevTailPos.y) / numClouds;
         double z = this.prevTailPos.z + i * (bladePos.z - this.prevTailPos.z) / numClouds;
         ParticleOptions type = ParticleTypes.EXPLOSION;
         this.level().addParticle(type, x, y, z, 0.0, 0.0, 0.0);
      }
   }

   private void SwingParticles() {
      if (this.level().isClientSide) {
         Vec3 bladePos = this.socketPosArray[0];
         if (this.getAnimation() == LEVIATHAN_TAIL_WHIPS && this.getAnimationTick() >= 10 && this.getAnimationTick() <= 34) {
            this.Tailswing();
         }

         this.prevTailPos = bladePos;
      }
   }

   private void blockbreak(double x, double y, double z) {
      boolean flag = false;
      AABB aabb = this.getBoundingBox().inflate(x, y, z);

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && !blockstate.is(ModTag.LEVIATHAN_IMMUNE)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            flag = this.level().destroyBlock(blockpos, false, this) || flag;
         }
      }
   }

   private void blockbreak2() {
      if (this.blockBreakCounter > 0) {
         this.blockBreakCounter--;
      } else {
         boolean flag = false;
         AABB aabb = this.getBoundingBox().inflate(0.2);

         for (BlockPos pos : BlockPos.betweenClosed(
            Mth.floor(aabb.minX), Mth.floor(this.getY()), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
         )) {
            BlockState blockstate = this.level().getBlockState(pos);
            if (!blockstate.isAir() && blockstate.canEntityDestroy(this.level(), pos, this) && !blockstate.is(ModTag.LEVIATHAN_IMMUNE)) {
               if (this.random.nextInt(5) == 0 && !blockstate.hasBlockEntity()) {
                  Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(
                     this.level(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, blockstate, 20
                  );
                  flag = this.level().destroyBlock(pos, false, this) || flag;
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
                  flag = this.level().destroyBlock(pos, false, this) || flag;
                  this.setDeltaMovement(this.getDeltaMovement().multiply(0.6F, 1.0, 0.6F));
               }
            }
         }

         if (flag) {
            this.blockBreakCounter = 10;
         }
      }
   }

   private void chargeblockbreaking() {
      boolean flag = false;
      AABB aabb = this.getBoundingBox().inflate(4.5, 0.5, 4.5);
      double yblockbreak = this.isInWater() ? aabb.minY : this.getY();

      for (BlockPos blockpos : BlockPos.betweenClosed(
         Mth.floor(aabb.minX), Mth.floor(yblockbreak), Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.floor(aabb.maxY), Mth.floor(aabb.maxZ)
      )) {
         BlockState blockstate = this.level().getBlockState(blockpos);
         if (!blockstate.isAir()
            && blockstate.canEntityDestroy(this.level(), blockpos, this)
            && !blockstate.is(ModTag.LEVIATHAN_IMMUNE)
            && EventHooks.onEntityDestroyBlock(this, blockpos, blockstate)) {
            flag = this.level().destroyBlock(blockpos, false, this) || flag;
         }
      }
   }

   private void chargeDamage() {
      if (this.tickCount % 4 == 0 && !this.level().isClientSide) {
         for (LivingEntity Lentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(3.0))) {
            if (!this.isAlliedTo(Lentity) && !(Lentity instanceof The_Leviathan_Entity) && Lentity != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean flag = Lentity.hurt(
                  damagesource,
                  (float)(
                     (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                        + Math.min(this.getAttributeValue(Attributes.ATTACK_DAMAGE), Lentity.getMaxHealth() * (float)CMCommonConfig.Leviathan.RushHpDamage)
                  )
               );
               if (Lentity instanceof Player player && Lentity.isDamageSourceBlocked(damagesource)) {
                  EntityUtil.disableShield(player, 120);
               }

               if (flag && Lentity.onGround()) {
                  double d0 = Lentity.getX() - this.getX();
                  double d1 = Lentity.getZ() - this.getZ();
                  double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                  float f = 1.5F;
                  Lentity.push(d0 / d2 * f, 0.5, d1 / d2 * f);
               }
            }
         }
      }
   }

   private void biteattack(float radius, double inflateX, double inflateY, double inflateZ, int stuntick) {
      double renderYaw = (this.yHeadRot + 90.0F) * Math.PI / 180.0;
      double renderPitch = (float)(-this.getXRot() * Math.PI / 180.0);
      this.endPosX = this.getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.getY() + radius * Math.sin(renderPitch);
      if (!this.level().isClientSide) {
         for (LivingEntity target : this.raytraceEntities(
               this.level(), inflateX, inflateY, inflateZ, new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.isAlliedTo(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean flag = target.hurt(
                  damagesource,
                  (float)(
                     (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5
                        + Math.min(
                           this.getAttributeValue(Attributes.ATTACK_DAMAGE) * 1.5, target.getMaxHealth() * (float)CMCommonConfig.Leviathan.TentacleHpDamage
                        )
                  )
               );
               if (target instanceof Player player && target.isDamageSourceBlocked(damagesource)) {
                  EntityUtil.disableShield(player, 200);
               }

               if (flag && stuntick > 0) {
                  target.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN, stuntick));
               }
            }
         }
      }
   }

   private void Tentacleattack(int anime, float radius, double inflateX, double inflateY, double inflateZ) {
      double renderYaw = (this.yHeadRot + 90.0F) * Math.PI / 180.0;
      double renderPitch = (float)(-this.getXRot() * Math.PI / 180.0);
      this.endPosX = this.getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.getY() + radius * Math.sin(renderPitch);
      if (this.getAnimationTick() == anime) {
         this.playSound((SoundEvent)ModSounds.LEVIATHAN_TENTACLE_STRIKE.get(), 1.0F, 1.0F);
         if (!this.level().isClientSide) {
            for (LivingEntity target : this.raytraceEntities(
                  this.level(),
                  inflateX,
                  inflateY,
                  inflateZ,
                  new Vec3(this.getX(), this.getY(), this.getZ()),
                  new Vec3(this.endPosX, this.endPosY, this.endPosZ)
               )
               .entities) {
               if (!this.isAlliedTo(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
                  DamageSource damagesource = this.damageSources().mobAttack(this);
                  boolean flag = target.hurt(
                     damagesource,
                     (float)(
                        (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE)
                           + Math.min(
                              this.getAttributeValue(Attributes.ATTACK_DAMAGE), target.getMaxHealth() * (float)CMCommonConfig.Leviathan.TentacleHpDamage
                           )
                     )
                  );
                  if (target instanceof Player player && target.isDamageSourceBlocked(damagesource)) {
                     EntityUtil.disableShield(player, 90);
                  }

                  if (flag) {
                     double d0 = target.getX() - this.getX();
                     double d1 = target.getZ() - this.getZ();
                     double d2 = Math.max(d0 * d0 + d1 * d1, 0.001);
                     target.push(d0 / d2 * 7.0, 0.2, d1 / d2 * 7.0);
                  }
               }
            }
         }
      }
   }

   private void TentacleHoldattack(float radius, double inflateX, double inflateY, double inflateZ, int shieldbreakticks) {
      double renderYaw = (this.yHeadRot + 90.0F) * Math.PI / 180.0;
      double renderPitch = (float)(-this.getXRot() * Math.PI / 180.0);
      this.endPosX = this.getX() + radius * Math.cos(renderYaw) * Math.cos(renderPitch);
      this.endPosZ = this.getZ() + radius * Math.sin(renderYaw) * Math.cos(renderPitch);
      this.endPosY = this.getY() + radius * Math.sin(renderPitch);
      if (!this.level().isClientSide) {
         for (LivingEntity target : this.raytraceEntities(
               this.level(), inflateX, inflateY, inflateZ, new Vec3(this.getX(), this.getY(), this.getZ()), new Vec3(this.endPosX, this.endPosY, this.endPosZ)
            )
            .entities) {
            if (!this.isAlliedTo(target) && !(target instanceof The_Leviathan_Entity) && target != this) {
               DamageSource damagesource = this.damageSources().mobAttack(this);
               boolean flag = target.hurt(damagesource, (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE) + target.getMaxHealth() * 0.1F);
               if (target instanceof Player player && target.isDamageSourceBlocked(damagesource) && shieldbreakticks > 0) {
                  EntityUtil.disableShield(player, shieldbreakticks);
               }

               if (flag && !target.getType().is(ModTag.IGNIS_CANT_POKE) && target.isAlive()) {
                  if (target.isShiftKeyDown()) {
                     target.setShiftKeyDown(false);
                  }

                  target.startRiding(this, true);
                  AnimationHandler.INSTANCE.sendAnimationMessage(this, LEVIATHAN_TENTACLE_HOLD_BLAST);
               }
            }
         }
      }
   }

   public void positionRider(Entity passenger, MoveFunction moveFunc) {
      if (this.hasPassenger(passenger)) {
         if (this.getAnimation() == LEVIATHAN_TENTACLE_HOLD_BLAST && this.getAnimationTick() == 169) {
            passenger.stopRiding();
         }

         this.setXRot(this.xRotO);
         this.yBodyRot = this.getYRot();
         this.yHeadRot = this.getYRot();
         float f17 = this.getYRot() * (float) Math.PI / 180.0F;
         float pitch = this.getXRot() * (float) Math.PI / 180.0F;
         float f3 = Mth.sin(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         float f18 = Mth.cos(f17) * (1.0F - Math.abs(this.getXRot() / 90.0F));
         moveFunc.accept(passenger, this.getX() + f3 * -8.25F, this.getY() + -pitch * 6.0F, this.getZ() + -f18 * -8.25F);
      }
   }

   public boolean shouldRiderSit() {
      return false;
   }

   @Nullable
   public LivingEntity getControllingPassenger() {
      return null;
   }

   @Override
   protected boolean canRide(Entity p_31508_) {
      return false;
   }

   @Override
   public boolean canBeAffected(MobEffectInstance p_31495_) {
      return p_31495_.getEffect() != ModEffect.EFFECTABYSSAL_BURN.get() && super.canBeAffected(p_31495_);
   }

   private The_Leviathan_Entity.BiteHitResult raytraceEntities(Level world, double inflateX, double inflateY, double inflateZ, Vec3 from, Vec3 to) {
      The_Leviathan_Entity.BiteHitResult result = new The_Leviathan_Entity.BiteHitResult();
      this.collidePosX = this.endPosX;
      this.collidePosY = this.endPosY;
      this.collidePosZ = this.endPosZ;

      for (LivingEntity entity : world.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(
               Math.min(this.getX(), this.collidePosX),
               Math.min(this.getY(), this.collidePosY),
               Math.min(this.getZ(), this.collidePosZ),
               Math.max(this.getX(), this.collidePosX),
               Math.max(this.getY(), this.collidePosY),
               Math.max(this.getZ(), this.collidePosZ)
            )
            .inflate(inflateX, inflateY, inflateZ)
      )) {
         float pad = 2.5F;
         AABB aabb = entity.getBoundingBox().inflate(pad, pad, pad);
         Optional<Vec3> hit = aabb.clip(from, to);
         if (aabb.contains(from)) {
            result.addEntityHit(entity);
         } else if (hit.isPresent()) {
            result.addEntityHit(entity);
         }
      }

      return result;
   }

   protected int increaseAirSupply(int currentAir) {
      return this.getMaxAirSupply();
   }

   public boolean isPushedByFluid() {
      return false;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("BlastChance", this.getBlastChance());
      compound.putBoolean("MeltDown", this.getMeltDown());
      compound.putInt("ModeChance", this.getModeChance());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setBlastChance(compound.getInt("BlastChance"));
      this.setModeChance(compound.getInt("ModeChance"));
      this.setMeltDown(compound.getBoolean("MeltDown"));
      if (this.hasCustomName()) {
         this.bossInfo.setName(this.getDisplayName());
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

   public void setCustomName(@Nullable Component name) {
      super.setCustomName(name);
      this.bossInfo.setName(this.getDisplayName());
   }

   public boolean getMeltDown() {
      return (Boolean)this.entityData.get(MELT_DOWN);
   }

   public void setMeltDown(boolean chance) {
      this.entityData.set(MELT_DOWN, chance);
   }

   public int getBlastChance() {
      return (Integer)this.entityData.get(BLAST_CHANCE);
   }

   public void setBlastChance(int chance) {
      this.entityData.set(BLAST_CHANCE, chance);
   }

   public int getModeChance() {
      return (Integer)this.entityData.get(MODE_CHANCE);
   }

   public void setModeChance(int chance) {
      this.entityData.set(MODE_CHANCE, chance);
   }

   @Nullable
   public UUID getTongueUUID() {
      return (UUID)((Optional)this.entityData.get(TONGUE_UUID)).orElse(null);
   }

   public void setTongueUUID(@Nullable UUID uniqueId) {
      this.entityData.set(TONGUE_UUID, Optional.ofNullable(uniqueId));
   }

   public Entity getTongue() {
      if (!this.level().isClientSide) {
         UUID id = this.getTongueUUID();
         return id == null ? null : ((ServerLevel)this.level()).getEntity(id);
      } else {
         int id = (Integer)this.entityData.get(TONGUE_ID);
         return id == -1 ? null : this.level().getEntity(id);
      }
   }

   public void createStuckPortal() {
      if (this.getTarget() != null) {
         Vec3 to = new Vec3(this.getTarget().getX(), this.getTarget().getY(), this.getTarget().getZ());
         this.createPortal2(this.getX(), this.getY() + 0.1, this.getZ(), to);
      }
   }

   public void createPortal2(double x, double y, double z, Vec3 to) {
      if (!this.level().isClientSide && this.portalTarget == null) {
         Abyss_Portal_Entity portal = (Abyss_Portal_Entity)((EntityType)ModEntities.ABYSS_PORTAL.get()).create(this.level());
         portal.setPos(x, y, z);
         portal.setLifespan(10000);
         portal.setEntrance(true);
         if (!this.level().isClientSide) {
            this.level().addFreshEntity(portal);
         }

         this.portalTarget = portal;
         portal.setDestination(BlockPos.containing(to.x, to.y, to.z));
         this.makePortalCooldown = 300;
      }
   }

   public void resetPortalLogic() {
      this.portalTarget = null;
   }

   public void teleportTo(Vec3 vec) {
      this.teleportPos = vec;
      this.fullyThrough = false;
   }

   private boolean checkBlocksByTentacle(float vec, float math) {
      double theta = this.yBodyRot * (Math.PI / 180.0);
      float f = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0));
      float f1 = Mth.sin(this.getYRot() * (float) (Math.PI / 180.0));
      theta += Math.PI / 2;
      double vecX = Math.cos(theta);
      double vecZ = Math.sin(theta);
      BlockPos pos1 = new BlockPos(
         Mth.floor(this.getX() + vec * vecX + f * math), Math.round((float)(this.getY() - 2.0)), Mth.floor(this.getZ() + vec * vecZ + f1 * math)
      );
      return this.level().getBlockState(pos1).blocksMotion();
   }

   protected BodyRotationControl createBodyControl() {
      return new SmartBodyHelper2(this);
   }

   @Override
   public boolean shouldEnterWater() {
      return true;
   }

   @Override
   public boolean shouldLeaveWater() {
      return false;
   }

   @Override
   public boolean shouldStopMoving() {
      return false;
   }

   @Override
   public int getWaterSearchRange() {
      return 32;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.LEVIATHAN_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)ModSounds.LEVIATHAN_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)ModSounds.LEVIATHAN_DEFEAT.get();
   }

   @Override
   public SoundEvent getBossMusic() {
      if (CMCommonConfig.Leviathan.SeparatePhaseMusic) {
         return this.getMeltDown() ? (SoundEvent)ModSounds.LEVIATHAN_MUSIC_2.get() : (SoundEvent)ModSounds.LEVIATHAN_MUSIC_1.get();
      } else {
         return (SoundEvent)ModSounds.LEVIATHAN_MUSIC.get();
      }
   }

   @Override
   protected boolean canPlayMusic() {
      if (CMCommonConfig.Leviathan.SeparatePhaseMusic) {
         return this.getAnimation() != LEVIATHAN_PHASE2 ? super.canPlayMusic() : this.getAnimationTick() > 90 && super.canPlayMusic();
      } else {
         return super.canPlayMusic();
      }
   }

   @Nullable
   @Override
   public Animation getDeathAnimation() {
      return LEVIATHAN_DEATH;
   }

   protected float getSoundVolume() {
      return this.isSilent() ? 0.0F : 2.0F;
   }

   private void setPartPosition(The_Leviathan_Part part, double offsetX, double offsetY, double offsetZ) {
      part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
   }

   public boolean isMultipartEntity() {
      return true;
   }

   @Nullable
   public PartEntity<?>[] getParts() {
      return this.leviathanParts;
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      Cm_Part_Entity.assignPartIDs(this);
   }

   public Vec3 getTonguePosition() {
      float f1 = -Mth.sin(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
      float f2 = -Mth.sin(this.getXRot() * (float) (Math.PI / 180.0));
      float f3 = Mth.cos(this.getYRot() * (float) (Math.PI / 180.0)) * Mth.cos(this.getXRot() * (float) (Math.PI / 180.0));
      return new Vec3(this.getX() + f1 * 3.0, this.getY() + f2 * 3.5, this.getZ() + f3 * 3.0);
   }

   private enum AttackMode {
      CIRCLE,
      MELEE,
      RANGE;
   }

   public static class BiteHitResult {
      private final List<LivingEntity> entities = new ArrayList<>();

      public void addEntityHit(LivingEntity entity) {
         this.entities.add(entity);
      }
   }

   static class LeviathanAbyssBlastPortalAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanAbyssBlastPortalAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = false;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         super.start();
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.hunting_cooldown = 120;
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
            double d0 = Math.min(target.getY(), this.entity.getY()) - 50.0;
            double d1 = Math.max(target.getY(), this.entity.getY()) + 3.0;
            float f = (float)Mth.atan2(target.getZ() - this.entity.getZ(), target.getX() - this.entity.getX());
            if (this.entity.getAnimationTick() == 56) {
               for (int l = 0; l < 9; l++) {
                  int j = (int)(5.0F * l);
                  double randomNearbyX = target.getX() + this.entity.random.nextGaussian() * 12.0;
                  double randomNearbyZ = target.getZ() + this.entity.random.nextGaussian() * 12.0;
                  this.spawnUnderPortal(randomNearbyX, randomNearbyZ, d0, d1, f, j);
               }
            }

            if (this.entity.getAnimationTick() == 56 || this.entity.getAnimationTick() == 76 || this.entity.getAnimationTick() == 96) {
               this.spawnUnderPortal(target.getX(), target.getZ(), d0, d1, f, 0);
            }
         }
      }

      private void spawnUnderPortal(double x, double z, double minY, double maxY, float rotation, int delay) {
         BlockPos blockpos = BlockPos.containing(x, maxY, z);
         boolean flag = false;
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.below();
            BlockState blockstate = this.entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(this.entity.level(), blockpos1, Direction.UP)) {
               if (!this.entity.level().isEmptyBlock(blockpos)) {
                  BlockState blockstate1 = this.entity.level().getBlockState(blockpos);
                  VoxelShape voxelshape = blockstate1.getCollisionShape(this.entity.level(), blockpos);
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
            this.entity
               .level()
               .addFreshEntity(
                  new Abyss_Blast_Portal_Entity(
                     this.entity.level(),
                     x,
                     blockpos.getY() + d0,
                     z,
                     rotation,
                     delay,
                     (float)CMCommonConfig.Leviathan.AbyssBlastDamage,
                     (float)CMCommonConfig.Leviathan.AbyssBlastHpDamage,
                     this.entity
                  )
               );
         }
      }

      private void spawnUpperPortal(double x, double z, double maxY, float rotation, int delay) {
         BlockPos blockpos = BlockPos.containing(x, maxY, z);
         boolean flag = false;
         double d0 = 0.0;

         do {
            BlockPos blockpos1 = blockpos.above();
            BlockState blockstate = this.entity.level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(this.entity.level(), blockpos1, Direction.DOWN)) {
               if (!this.entity.level().isEmptyBlock(blockpos)) {
                  BlockState blockstate1 = this.entity.level().getBlockState(blockpos);
                  VoxelShape voxelshape = blockstate1.getCollisionShape(this.entity.level(), blockpos);
                  if (!voxelshape.isEmpty()) {
                     d0 = voxelshape.max(Axis.Y);
                  }
               }

               flag = true;
               break;
            }

            blockpos = blockpos.above();
         } while (
            blockpos.getY() < Math.min(this.entity.level().getMaxBuildHeight(), this.entity.getBlockY() + 100)
               && !this.entity.level().getBlockState(blockpos).isSolid()
         );

         if (flag) {
            this.entity
               .level()
               .addFreshEntity(
                  new Abyss_Blast_Portal_Entity(
                     this.entity.level(),
                     x,
                     blockpos.getY() + d0 + 0.5,
                     z,
                     rotation,
                     delay,
                     (float)CMCommonConfig.Leviathan.AbyssBlastDamage,
                     (float)CMCommonConfig.Leviathan.AbyssBlastHpDamage,
                     this.entity
                  )
               );
         }
      }
   }

   static class LeviathanAbyssDimensionAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanAbyssDimensionAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanCrackDimension = false;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 0.0F);
         }

         super.start();
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.hunting_cooldown = 250;
         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }

      public void tick() {
         if (this.entity.getAnimationTick() == 24 && !this.entity.level().isClientSide) {
            double theta = this.entity.yBodyRot * (Math.PI / 180.0);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);
            Dimensional_Rift_Entity portal = new Dimensional_Rift_Entity(
               this.entity.level(), this.entity.getX() + vecX * 12.0, this.entity.getEyeY(), this.entity.getZ() + vecZ * 12.0, this.entity
            );
            portal.setStage(1);
            portal.setLifespan(600);
            if (!this.entity.level().isClientSide) {
               this.entity.level().addFreshEntity(portal);
            }
         }

         Dimensional_Rift_Entity rift = this.getClosestDimensionalRift();
         if (rift != null) {
            this.entity.getLookControl().setLookAt(rift, 30.0F, 90.0F);
         }
      }

      private Dimensional_Rift_Entity getClosestDimensionalRift() {
         List<Dimensional_Rift_Entity> list = this.entity
            .level()
            .getEntitiesOfClass(Dimensional_Rift_Entity.class, this.entity.getBoundingBox().inflate(15.0, 15.0, 15.0));
         Dimensional_Rift_Entity closest = null;
         if (!list.isEmpty()) {
            for (Dimensional_Rift_Entity entity : list) {
               if (closest == null || closest.distanceTo(entity) > entity.distanceTo(entity)) {
                  closest = entity;
               }
            }
         }

         return closest;
      }
   }

   class LeviathanAttackGoal extends Goal {
      private final The_Leviathan_Entity mob;
      private LivingEntity target;
      private int circlingTime = 0;
      private final int huntingTime = 0;
      private float MeleeModeTime = 0.0F;
      private static final int MELEE_MODE_TIME = 160;
      private float circleDistance = 18.0F;
      private boolean clockwise = false;

      public LeviathanAttackGoal(The_Leviathan_Entity mob) {
         this.mob = mob;
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         this.target = this.mob.getTarget();
         return this.target != null && this.target.isAlive() && this.mob.getAnimation() == IAnimatedEntity.NO_ANIMATION;
      }

      public boolean canContinueToUse() {
         this.target = this.mob.getTarget();
         return this.target != null;
      }

      public void start() {
         this.mob.mode = The_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = 36 + this.mob.random.nextInt(20);
         this.clockwise = this.mob.random.nextBoolean();
         this.mob.setAggressive(true);
      }

      public void stop() {
         this.mob.mode = The_Leviathan_Entity.AttackMode.CIRCLE;
         this.circlingTime = 0;
         this.MeleeModeTime = 0.0F;
         this.circleDistance = 36 + this.mob.random.nextInt(20);
         this.clockwise = this.mob.random.nextBoolean();
         this.target = this.mob.getTarget();
         if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(this.target)) {
            this.mob.setTarget((LivingEntity)null);
         }

         this.mob.getNavigation().stop();
         if (this.mob.getTarget() == null) {
            this.mob.setAggressive(false);
            this.mob.getNavigation().stop();
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public void tick() {
         LivingEntity target = this.mob.getTarget();
         if (target != null) {
            if (this.mob.mode == The_Leviathan_Entity.AttackMode.CIRCLE) {
               this.circlingTime++;
               The_Leviathan_Entity.this.circleEntity(target, this.circleDistance, 1.0F, this.clockwise, this.circlingTime, 0.0F, 1.0F);
               if (0 >= this.mob.hunting_cooldown) {
                  int i = Math.max(this.mob.getModeChance(), 2);
                  if (this.mob.random.nextInt(i) == 0) {
                     this.mob.mode = The_Leviathan_Entity.AttackMode.RANGE;
                  } else {
                     this.mob.mode = The_Leviathan_Entity.AttackMode.MELEE;
                  }
               }

               if (this.mob.getRandom().nextFloat() * 100.0F < 12.0F && this.mob.distanceToSqr(target) <= 49.0 && this.mob.melee_cooldown <= 0) {
                  Animation animation = The_Leviathan_Entity.getRandomTantalcleStrike(this.mob.random);
                  this.mob.setAnimation(animation);
               }
            } else if (this.mob.mode == The_Leviathan_Entity.AttackMode.RANGE) {
               if (this.mob.getRandom().nextFloat() * 100.0F < 3.0F && this.mob.CanAbyss_Blast_Portal) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST_PORTAL);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 2.0F * this.mob.getBlastChance() && this.mob.CanAbyss_Blast) {
                  if (this.mob.random.nextInt(2) == 0) {
                     this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST);
                  } else {
                     this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST_FIRE);
                  }
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 8.0F && this.mob.distanceToSqr(target) >= 700.0 && this.mob.CanGrab) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_GRAB);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 4.0F
                  && this.mob.distanceToSqr(target) < 900.0
                  && this.mob.distanceToSqr(target) >= 49.0
                  && this.mob.CanRush) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_RUSH);
               }
            } else if (this.mob.mode == The_Leviathan_Entity.AttackMode.MELEE) {
               this.mob.getNavigation().moveTo(target, 1.0);
               this.MeleeModeTime++;
               this.mob.getLookControl().setLookAt(target, 30.0F, 90.0F);
               if (this.MeleeModeTime >= 160.0F) {
                  this.mob.mode = The_Leviathan_Entity.AttackMode.RANGE;
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 15.0F && this.mob.distanceToSqr(target) < 49.0 && this.mob.CanTailWhips) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_TAIL_WHIPS);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 8.0F && this.mob.distanceToSqr(target) < 36.0 && this.mob.CanGrab) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_TENTACLE_HOLD);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 20.0F && this.mob.distanceToSqr(target) <= 30.0 && this.mob.CanBite) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_BITE);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 3.0F
                  && this.mob.CanCrackDimension
                  && this.mob.distanceToSqr(target) < 256.0
                  && this.mob.distanceToSqr(target) > 49.0) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_BREAK_DIMENSION);
               } else if (this.mob.getRandom().nextFloat() * 100.0F < 5.0F
                  && this.mob.CanMine
                  && this.mob.distanceToSqr(target) < 256.0
                  && this.mob.distanceToSqr(target) > 100.0) {
                  this.mob.setAnimation(The_Leviathan_Entity.LEVIATHAN_MINE);
               }
            }
         }
      }
   }

   static class LeviathanBiteAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBiteAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = false;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() < 13 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanBlastAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBlastAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null && this.entity.getAnimationTick() < 82) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 64 && !this.entity.level().isClientSide) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.level(),
               this.entity,
               this.entity.getX(),
               this.entity.getY(),
               this.entity.getZ(),
               (float)((this.entity.yHeadRot + dir) * Math.PI / 180.0),
               (float)(-this.entity.getXRot() * Math.PI / 180.0),
               80,
               dir,
               (float)CMCommonConfig.Leviathan.AbyssBlastDamage,
               (float)CMCommonConfig.Leviathan.AbyssBlastHpDamage
            );
            this.entity.level().addFreshEntity(DeathBeam);
         }
      }
   }

   static class LeviathanBlastFireAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanBlastFireAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST_ONLY_CHARGE.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null
            && (
               this.entity.getAnimationTick() < 80
                  || this.entity.getAnimationTick() > 118 && this.entity.getAnimationTick() < 125
                  || this.entity.getAnimationTick() > 163 && this.entity.getAnimationTick() < 170
            )) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if ((this.entity.getAnimationTick() == 64 || this.entity.getAnimationTick() == 109 || this.entity.getAnimationTick() == 154)
            && !this.entity.level().isClientSide) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.level(),
               this.entity,
               this.entity.getX(),
               this.entity.getY(),
               this.entity.getZ(),
               (float)((this.entity.yHeadRot + dir) * Math.PI / 180.0),
               (float)(-this.entity.getXRot() * Math.PI / 180.0),
               28,
               dir,
               (float)CMCommonConfig.Leviathan.AbyssBlastDamage,
               (float)CMCommonConfig.Leviathan.AbyssBlastHpDamage
            );
            this.entity.level().addFreshEntity(DeathBeam);
         }
      }
   }

   static class LeviathanGrabAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanGrabAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = false;
         this.entity.CanRush = true;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanMine = true;
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.hunting_cooldown = 70;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         Entity weapon = this.entity.getTongue();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         if (this.entity.getAnimationTick() == 25 && target != null && this.entity.getTongue() == null && !this.entity.level().isClientSide) {
            The_Leviathan_Tongue_Entity segment = (The_Leviathan_Tongue_Entity)((EntityType)ModEntities.THE_LEVIATHAN_TONGUE.get()).create(this.entity.level());
            segment.copyPosition(this.entity);
            segment.setPos(this.entity.getTonguePosition());
            segment.setControllerUUID(this.entity.getUUID());
            segment.setMaxDuration(120);
            this.entity.setTongueUUID(segment.getUUID());
            this.entity.level().addFreshEntity(segment);
         }

         if (this.entity.getAnimationTick() > 25
            && this.entity.getAnimationTick() <= 145
            && target != null
            && target.isAlive()
            && this.entity.distanceTo(target) < 8.5F) {
            if (weapon instanceof The_Leviathan_Tongue_Entity magneticWeapon && magneticWeapon.getComingBack()) {
               magneticWeapon.remove(RemovalReason.DISCARDED);
            }

            AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, The_Leviathan_Entity.LEVIATHAN_GRAB_BITE);
         }

         if (this.entity.getAnimationTick() > 155 && weapon instanceof The_Leviathan_Tongue_Entity magneticWeapon) {
            magneticWeapon.remove(RemovalReason.DISCARDED);
         }
      }
   }

   static class LeviathanGrabBiteAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanGrabBiteAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.LEVIATHAN_BITE.get(), SoundSource.HOSTILE, 1.0F, 1.0F);
         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         this.entity.setYRot(this.entity.yRotO);
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.hunting_cooldown = 70;
      }
   }

   static class LeviathanMineAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanMineAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = false;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
            float f = (float)Mth.atan2(target.getZ() - this.entity.getZ(), target.getX() - this.entity.getX());
            if (this.entity.getAnimationTick() == 31) {
               for (int l = 0; l < 35; l++) {
                  int j = (int)(2.0F * l);
                  double randomNearbyX = target.getX() + this.entity.random.nextGaussian() * 12.0;
                  double randomNearbyY = target.getY() + this.entity.random.nextGaussian() * 8.0;
                  double randomNearbyZ = target.getZ() + this.entity.random.nextGaussian() * 12.0;
                  this.spawnMines(randomNearbyX, randomNearbyY, randomNearbyZ, f, j);
               }
            }
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }

      private void spawnMines(double x, double y, double z, float rotation, int delay) {
         Abyss_Mine_Entity mine = new Abyss_Mine_Entity(this.entity.level(), x, y, z, rotation, delay, this.entity);
         if (mine.level().noCollision(mine)) {
            this.entity.level().addFreshEntity(mine);
         }
      }
   }

   static class LeviathanMoveController extends MoveControl {
      private final The_Leviathan_Entity entity;
      private final float speedMulti;
      private final float ySpeedMod;
      private final float yawLimit;
      private int stillTicks = 0;

      public LeviathanMoveController(The_Leviathan_Entity entity, float speedMulti, float ySpeedMod, float yawLimit) {
         super(entity);
         this.entity = entity;
         this.speedMulti = speedMulti;
         this.ySpeedMod = ySpeedMod;
         this.yawLimit = yawLimit;
      }

      public void tick() {
         if (this.entity.isInWater() && this.entity.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
            if (Math.abs(this.entity.xo - this.entity.getX()) < 0.01F
               && Math.abs(this.entity.yo - this.entity.getY()) < 0.01F
               && Math.abs(this.entity.zo - this.entity.getZ()) < 0.01F) {
               this.stillTicks++;
            } else {
               this.stillTicks = 0;
            }

            if (this.stillTicks > 40) {
               this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, -0.005, 0.0));
            }
         }

         if (this.entity.shouldStopMoving()) {
            this.entity.setSpeed(0.0F);
         } else {
            if (this.operation == Operation.MOVE_TO && !this.entity.getNavigation().isDone()) {
               double lvt_1_1_ = this.wantedX - this.entity.getX();
               double lvt_3_1_ = this.wantedY - this.entity.getY();
               double lvt_5_1_ = this.wantedZ - this.entity.getZ();
               double lvt_7_1_ = lvt_1_1_ * lvt_1_1_ + lvt_3_1_ * lvt_3_1_ + lvt_5_1_ * lvt_5_1_;
               if (lvt_7_1_ < 2.5000003E-7F) {
                  this.mob.setZza(0.0F);
               } else {
                  float lvt_9_1_ = (float)(Mth.atan2(lvt_5_1_, lvt_1_1_) * 180.0F / (float)Math.PI) - 90.0F;
                  this.entity.setYRot(this.rotlerp(this.entity.getYRot(), lvt_9_1_, this.yawLimit));
                  this.entity.yBodyRot = this.entity.getYRot();
                  this.entity.yHeadRot = this.entity.getYRot();
                  float lvt_10_1_ = (float)(this.speedModifier * this.speedMulti * 3.0 * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
                  if (this.entity.isInWater()) {
                     if (lvt_3_1_ > 0.0 && this.entity.horizontalCollision) {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, 0.08F, 0.0));
                     } else {
                        this.entity.setDeltaMovement(this.entity.getDeltaMovement().add(0.0, this.entity.getSpeed() * lvt_3_1_ * 0.6 * this.ySpeedMod, 0.0));
                     }

                     this.entity.setSpeed(lvt_10_1_ * 0.02F);
                     float lvt_11_1_ = -((float)(Mth.atan2(lvt_3_1_, Mth.sqrt((float)(lvt_1_1_ * lvt_1_1_ + lvt_5_1_ * lvt_5_1_))) * 180.0F / (float)Math.PI));
                     lvt_11_1_ = Mth.clamp(Mth.wrapDegrees(lvt_11_1_), -85.0F, 85.0F);
                     this.entity.setXRot(this.rotlerp(this.entity.getXRot(), lvt_11_1_, 5.0F));
                     float lvt_12_1_ = Mth.cos(this.entity.getXRot() * (float) (Math.PI / 180.0));
                     float lvt_13_1_ = Mth.sin(this.entity.getXRot() * (float) (Math.PI / 180.0));
                     this.entity.zza = lvt_12_1_ * lvt_10_1_;
                     this.entity.yya = -lvt_13_1_ * lvt_10_1_;
                  } else {
                     this.entity.setSpeed(lvt_10_1_ * 0.1F);
                  }
               }
            } else {
               this.entity.setSpeed(0.0F);
               this.entity.setXxa(0.0F);
               this.entity.setYya(0.0F);
               this.entity.setZza(0.0F);
            }
         }
      }
   }

   static class LeviathanPhase2Goal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanPhase2Goal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         super.start();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (this.entity.getAnimationTick() >= 90 && target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
      }
   }

   static class LeviathanRushAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanRushAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         super.start();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = false;
         this.entity.CanBite = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanTailWhips = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         this.entity.setModeChance(this.entity.getModeChance() + 1);
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
            if (this.entity.getAnimationTick() > 53 && this.entity.getAnimationTick() < 134) {
               double d0 = target.getX() - this.entity.getX();
               double d1 = target.getEyeY() - this.entity.getEyeY();
               double d2 = target.getZ() - this.entity.getZ();
               double d3 = Mth.sqrt((float)(d0 * d0 + d2 * d2));
               float targetYaw = (float)(Mth.atan2(d2, d0) * 180.0 / (float) Math.PI - 90.0);
               float targetPitch = (float)(-(Mth.atan2(d1, d3) * 180.0 / (float) Math.PI));
               this.entity.setXRot(this.entity.getXRot() + Mth.clamp(targetPitch - this.entity.getXRot(), -2.0F, 2.0F));
               if (d0 * d0 + d2 * d2 >= 9.0) {
                  this.entity.setYRot(this.entity.getYRot() + Mth.clamp(targetYaw - this.entity.getYRot(), -2.0F, 2.0F));
                  this.entity.yBodyRot = this.entity.getYRot();
               }

               if (Math.abs(Mth.wrapDegrees(targetYaw) - Mth.wrapDegrees(this.entity.getYRot())) < 4.0F) {
                  double distSq = d0 * d0 + d2 * d2;
                  if (distSq < 9.0) {
                     this.entity.setYRot(this.entity.yRotO);
                     this.entity.yBodyRot = this.entity.yRotO;
                     this.entity.setDeltaMovement(this.entity.getDeltaMovement().multiply(0.8, 1.0, 0.8));
                  } else {
                     if (this.entity.isInWater() && target.isInWater()) {
                        Vec3 vector3d = this.entity.getDeltaMovement();
                        Vec3 vector3d1 = new Vec3(target.getX() - this.entity.getX(), target.getY() - this.entity.getY(), target.getZ() - this.entity.getZ());
                        if (vector3d1.lengthSqr() > 1.0E-7) {
                           vector3d1 = vector3d1.normalize().scale(0.5).add(vector3d.scale(0.5));
                        }

                        this.entity.setDeltaMovement(vector3d1.x, vector3d1.y, vector3d1.z);
                     }

                     this.entity.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
                     this.entity.getNavigation().moveTo(target, 1.0);
                  }
               }
            }
         }
      }
   }

   static class LeviathanStunGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanStunGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         super.start();
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null && this.entity.getAnimationTick() > 28) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }
   }

   static class LeviathanTailWhipsAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTailWhipsAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = false;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }

         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 100;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 30.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanTentacleAttackGoal extends AnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleAttackGoal(The_Leviathan_Entity entity) {
         super(entity);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      @Override
      protected boolean test(Animation animation) {
         return animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_UPPER_R
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_LOWER_R
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_UPPER_L
            || animation == The_Leviathan_Entity.LEVIATHAN_TENTACLE_STRIKE_LOWER_L;
      }

      public void start() {
         this.entity.getNavigation().stop();
         super.start();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void stop() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.stop();
         this.entity.melee_cooldown = 40;
      }

      public void tick() {
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }
   }

   static class LeviathanTentacleHoldAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleHoldAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = false;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = true;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 70;
         this.entity.setBlastChance(this.entity.getBlastChance() + 1);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         this.entity.setModeChance(this.entity.getModeChance() - 1);
      }
   }

   static class LeviathanTentacleHoldBlastAttackGoal extends SimpleAnimationGoal<The_Leviathan_Entity> {
      public LeviathanTentacleHoldBlastAttackGoal(The_Leviathan_Entity entity, Animation animation) {
         super(entity, animation);
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
      }

      public void start() {
         this.entity.getNavigation().stop();
         this.entity.CanAbyss_Blast_Portal = true;
         this.entity.CanGrab = true;
         this.entity.CanRush = true;
         this.entity.CanCrackDimension = true;
         this.entity.CanAbyss_Blast = false;
         this.entity.CanTailWhips = true;
         this.entity.CanBite = true;
         this.entity.CanMine = true;
         this.entity.level().playSound((Player)null, this.entity, (SoundEvent)ModSounds.ABYSS_BLAST.get(), SoundSource.HOSTILE, 4.0F, 1.0F);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         super.start();
      }

      public void stop() {
         super.stop();
         this.entity.hunting_cooldown = 140;
         this.entity.setBlastChance(0);
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }
      }

      public void tick() {
         this.entity.getNavigation().stop();
         LivingEntity target = this.entity.getTarget();
         if (target != null) {
            this.entity.getLookControl().setLookAt(target, 30.0F, 90.0F);
         }

         float dir = 90.0F;
         if (this.entity.getAnimationTick() == 64 && !this.entity.level().isClientSide) {
            Abyss_Blast_Entity DeathBeam = new Abyss_Blast_Entity(
               (EntityType<? extends Abyss_Blast_Entity>)ModEntities.ABYSS_BLAST.get(),
               this.entity.level(),
               this.entity,
               this.entity.getX(),
               this.entity.getY(),
               this.entity.getZ(),
               (float)((this.entity.yHeadRot + dir) * Math.PI / 180.0),
               (float)(-this.entity.getXRot() * Math.PI / 180.0),
               80,
               dir,
               (float)CMCommonConfig.Leviathan.AbyssBlastDamage,
               (float)CMCommonConfig.Leviathan.AbyssBlastHpDamage
            );
            this.entity.level().addFreshEntity(DeathBeam);
         }
      }
   }
}
