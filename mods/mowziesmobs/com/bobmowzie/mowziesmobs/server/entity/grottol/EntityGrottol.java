package com.bobmowzie.mowziesmobs.server.entity.grottol;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.datagen.MMBlockTags;
import com.bobmowzie.mowziesmobs.datagen.MMItemTags;
import com.bobmowzie.mowziesmobs.server.advancement.AdvancementHandler;
import com.bobmowzie.mowziesmobs.server.advancement.GrottolKillFortuneTrigger;
import com.bobmowzie.mowziesmobs.server.advancement.GrottolKillSilkTouchTrigger;
import com.bobmowzie.mowziesmobs.server.ai.EntityAIGrottolFindMinecart;
import com.bobmowzie.mowziesmobs.server.ai.MMAIAvoidEntity;
import com.bobmowzie.mowziesmobs.server.ai.MMEntityMoveHelper;
import com.bobmowzie.mowziesmobs.server.ai.MMPathNavigateGround;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationDieAI;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationTakeDamage;
import com.bobmowzie.mowziesmobs.server.ai.animation.SimpleAnimationAI;
import com.bobmowzie.mowziesmobs.server.block.ICopiedBlockProperties;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.entity.grottol.ai.EntityAIGrottolIdle;
import com.bobmowzie.mowziesmobs.server.item.ItemCapturedGrottol;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.loot.LootTableHandler;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.util.EnchantmentUtils;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import com.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityGrottol extends MowzieLLibraryEntity {
   public static final Animation DIE_ANIMATION = Animation.create(73);
   public static final Animation HURT_ANIMATION = Animation.create(10);
   public static final Animation IDLE_ANIMATION = EntityAIGrottolIdle.animation();
   public static final Animation BURROW_ANIMATION = Animation.create(20);
   private static final Animation[] ANIMATIONS = new Animation[]{DIE_ANIMATION, HURT_ANIMATION, IDLE_ANIMATION, BURROW_ANIMATION};
   public int fleeTime = 0;
   private int timeSinceFlee = 50;
   private int timeSinceMinecart = 0;
   private final BlackPinkRailLine reader = BlackPinkRailLine.create();
   private EntityGrottol.EnumDeathType death = EntityGrottol.EnumDeathType.NORMAL;
   private int timeSinceDeflectSound = 0;
   private static final EntityDataAccessor<Boolean> DEEPSLATE = SynchedEntityData.defineId(EntityGrottol.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> BLACKPINK = SynchedEntityData.defineId(EntityGrottol.class, EntityDataSerializers.BOOLEAN);

   public EntityGrottol(EntityType<? extends EntityGrottol> type, Level world) {
      super(type, world);
      this.xpReward = 15;
      this.moveControl = new MMEntityMoveHelper(this, 45.0F);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.setPathfindingMalus(PathType.DANGER_OTHER, 1.0F);
      this.setPathfindingMalus(PathType.WATER, 3.0F);
      this.setPathfindingMalus(PathType.WATER_BORDER, 3.0F);
      this.setPathfindingMalus(PathType.LAVA, 1.0F);
      this.setPathfindingMalus(PathType.DANGER_FIRE, 1.0F);
      this.setPathfindingMalus(PathType.DAMAGE_FIRE, 1.0F);
      this.setPathfindingMalus(PathType.DANGER_OTHER, 1.0F);
      this.setPathfindingMalus(PathType.DAMAGE_OTHER, 1.0F);
      this.goalSelector.addGoal(3, new FloatGoal(this));
      this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.3));
      this.goalSelector.addGoal(1, new EntityAIGrottolFindMinecart(this));
      this.goalSelector.addGoal(2, new MMAIAvoidEntity<EntityGrottol, Player>(this, Player.class, 16.0F, 0.5, 0.7) {
         private int fleeCheckCounter = 0;

         @Override
         protected void onSafe() {
            this.fleeCheckCounter = 0;
         }

         @Override
         protected void onPathNotFound() {
            if (this.fleeCheckCounter < 4) {
               this.fleeCheckCounter++;
            } else if (EntityGrottol.this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
               AnimationHandler.INSTANCE.sendAnimationMessage(this.entity, EntityGrottol.BURROW_ANIMATION);
            }
         }

         @Override
         public void tick() {
            super.tick();
            this.entity.fleeTime++;
         }

         @Override
         public void stop() {
            super.stop();
            this.entity.timeSinceFlee = 0;
            this.fleeCheckCounter = 0;
         }
      });
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(1, new AnimationTakeDamage<>(this));
      this.goalSelector.addGoal(1, new AnimationDieAI<>(this));
      this.goalSelector.addGoal(5, new EntityAIGrottolIdle(this));
      this.goalSelector.addGoal(2, new SimpleAnimationAI<>(this, BURROW_ANIMATION, false));
   }

   public int getMaxFallDistance() {
      return 256;
   }

   public int getMaxSpawnClusterSize() {
      return 1;
   }

   protected PathNavigation createNavigation(Level world) {
      return new MMPathNavigateGround(this, world);
   }

   public boolean isPushedByFluid() {
      return false;
   }

   protected float getWaterSlowDown() {
      return 1.0F;
   }

   public boolean displayFireAnimation() {
      return false;
   }

   public boolean isEffectiveAi() {
      return super.isEffectiveAi() && !this.isInMinecart();
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes()
         .add(Attributes.MAX_HEALTH, 20.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 1.0)
         .add(Attributes.STEP_HEIGHT, 1.15)
         .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5);
   }

   @Override
   protected ConfigHandler.SpawnConfig getSpawnConfig() {
      return ConfigHandler.COMMON.MOBS.GROTTOL.spawnConfig;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DEEPSLATE, false);
      builder.define(BLACKPINK, false);
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      return this.getEntitiesNearby(EntityGrottol.class, 20.0, 20.0, 20.0, 20.0).isEmpty() && super.checkSpawnRules(world, reason);
   }

   public boolean skipAttackInteraction(Entity entity) {
      if (entity instanceof Player player && EnchantmentUtils.getLevel(Enchantments.SILK_TOUCH, player.level(), player.getMainHandItem()) > 0) {
         if (!this.level().isClientSide && this.isAlive()) {
            this.spawnAtLocation(((ItemCapturedGrottol)ItemHandler.CAPTURED_GROTTOL.get()).create(this), 0.0F);
            BlockState state = Blocks.STONE.defaultBlockState();
            SoundType sound = state.getBlock().getSoundType(state, this.level(), this.blockPosition(), entity);
            this.level()
               .playSound(
                  null,
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  sound.getBreakSound(),
                  this.getSoundSource(),
                  (sound.getVolume() + 1.0F) / 2.0F,
                  sound.getPitch() * 0.8F
               );
            if (this.level() instanceof ServerLevel) {
               ((ServerLevel)this.level())
                  .sendParticles(
                     new BlockParticleOption(ParticleTypes.BLOCK, state),
                     this.getX(),
                     this.getY() + this.getBbHeight() / 2.0,
                     this.getZ(),
                     32,
                     this.getBbWidth() / 4.0F,
                     this.getBbHeight() / 4.0F,
                     this.getBbWidth() / 4.0F,
                     0.05
                  );
            }

            this.discard();
            if (player instanceof ServerPlayer serverPlayer) {
               ((GrottolKillSilkTouchTrigger)AdvancementHandler.GROTTOL_KILL_SILK_TOUCH_TRIGGER.value()).trigger(serverPlayer);
            }
         }

         return true;
      } else {
         return super.skipAttackInteraction(entity);
      }
   }

   @Override
   public boolean hurt(DamageSource source, float amount) {
      Entity entity = source.getEntity();
      if (!(entity instanceof Player player && !source.is(DamageTypeTags.IS_PROJECTILE))) {
         return entity instanceof Mob ? false : super.hurt(source, amount);
      } else if (!player.getMainHandItem().isCorrectToolForDrops(Blocks.DIAMOND_ORE.defaultBlockState())
         && !player.getMainHandItem().is(MMItemTags.CAN_HIT_GROTTOL)) {
         if (this.timeSinceDeflectSound >= 5) {
            this.timeSinceDeflectSound = 0;
            this.playSound((SoundEvent)MMSounds.ENTITY_GROTTOL_UNDAMAGED.get(), 0.4F, 2.0F);
         }

         return false;
      } else {
         if (EnchantmentUtils.getLevel(Enchantments.FORTUNE, player.level(), player.getMainHandItem()) > 0) {
            this.death = EntityGrottol.EnumDeathType.FORTUNE_PICKAXE;
            if (player instanceof ServerPlayer serverPlayer) {
               ((GrottolKillFortuneTrigger)AdvancementHandler.GROTTOL_KILL_FORTUNE_TRIGGER.value()).trigger(serverPlayer);
            }
         } else {
            this.death = EntityGrottol.EnumDeathType.PICKAXE;
         }

         return super.hurt(source, this.getHealth());
      }
   }

   private static BlockState findGroundBelow(Level world, BlockPos pos) {
      while (world.getBlockState(pos).isAir() && pos.getY() > world.getMinBuildHeight()) {
         pos = pos.below();
      }

      return world.getBlockState(pos);
   }

   @Override
   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         Entity e = this.getVehicle();
         if (isMinecart(e)) {
            AbstractMinecart minecart = (AbstractMinecart)e;
            this.reader.accept(minecart);
            boolean onRail = isBlockRail(this.level().getBlockState(e.blockPosition()).getBlock())
               || isBlockRail(findGroundBelow(this.level(), e.blockPosition()).getBlock());
            if (!e.onGround()
               || this.level().getBlockState(e.blockPosition().below()).isAir()
               || this.level().getBlockState(e.blockPosition()).isAir()
               || (this.timeSinceMinecart <= 3 || !(e.getDeltaMovement().length() < 0.001)) && onRail) {
               if (onRail) {
                  if (minecart.getDeltaMovement().length() < 0.001) {
                     minecart.setDeltaMovement(minecart.getForward().scale(2.7));
                  } else {
                     minecart.setDeltaMovement(minecart.getDeltaMovement().normalize().scale(2.7));
                  }

                  this.timeSinceMinecart++;
               }
            } else {
               minecart.ejectPassengers();
               this.timeSinceMinecart = 0;
            }
         }
      }

      if (this.level().isClientSide && this.isAlive() && this.random.nextInt(15) == 0) {
         double x = this.getX() + 0.5F * (2.0F * this.random.nextFloat() - 1.0F);
         double y = this.getY() + 0.8F + 0.3F * (2.0F * this.random.nextFloat() - 1.0F);
         double z = this.getZ() + 0.5F * (2.0F * this.random.nextFloat() - 1.0F);
         if (this.isBlackPinkInYourArea()) {
            this.level().addParticle(ParticleTypes.NOTE, x, y, z, this.random.nextDouble() / 2.0, 0.0, 0.0);
         } else {
            this.level().addParticle((ParticleOptions)ParticleHandler.SPARKLE.get(), x, y, z, 0.0, 0.0, 0.0);
         }
      }

      float moveX = (float)(this.getX() - this.xo);
      float moveZ = (float)(this.getZ() - this.zo);
      float speed = Mth.sqrt(moveX * moveX + moveZ * moveZ);
      if (this.frame % 6 == 0 && speed > 0.05) {
         this.playSound((SoundEvent)MMSounds.ENTITY_GROTTOL_STEP.get(), 1.0F, 1.8F);
      }

      if (this.timeSinceFlee < 50) {
         this.timeSinceFlee++;
      } else {
         this.fleeTime = 0;
      }

      if (this.timeSinceDeflectSound < 5) {
         this.timeSinceDeflectSound++;
      }

      if (!this.level().isClientSide && this.fleeTime >= 55 && this.getAnimation() == NO_ANIMATION && !this.isNoAi() && !this.hasEffect(EffectHandler.FROZEN)) {
         BlockState blockBeneath = this.level().getBlockState(this.blockPosition().below());
         if (this.isBlockDiggable(blockBeneath)) {
            AnimationHandler.INSTANCE.sendAnimationMessage(this, BURROW_ANIMATION);
         }
      }

      if (!this.level().isClientSide && this.getAnimation() == BURROW_ANIMATION && this.getAnimationTick() % 4 == 3) {
         this.playSound((SoundEvent)MMSounds.ENTITY_GROTTOL_BURROW.get(), 1.0F, 0.8F + this.random.nextFloat() * 0.4F);
         BlockState blockBeneath = this.level().getBlockState(this.blockPosition().below());
         if (this.isBlockDiggable(blockBeneath)) {
            Vec3 pos = new Vec3(0.5, 0.05, 0.0).yRot((float)Math.toRadians(-this.yBodyRot - 90.0F));
            if (this.level() instanceof ServerLevel) {
               ((ServerLevel)this.level())
                  .sendParticles(
                     new BlockParticleOption(ParticleTypes.BLOCK, blockBeneath),
                     this.getX() + pos.x,
                     this.getY() + pos.y,
                     this.getZ() + pos.z,
                     8,
                     0.25,
                     0.025,
                     0.25,
                     0.1
                  );
            }
         }
      }
   }

   @Override
   protected void onAnimationFinish(Animation animation) {
      if (animation == BURROW_ANIMATION) {
         this.discard();
      }
   }

   public static boolean isBlockRail(Block block) {
      return block == Blocks.RAIL || block == Blocks.ACTIVATOR_RAIL || block == Blocks.POWERED_RAIL || block == Blocks.DETECTOR_RAIL;
   }

   private boolean isBlackPinkInYourArea() {
      Entity e = this.getVehicle();
      return e instanceof AbstractMinecart ? this.getBlackpink() : false;
   }

   public boolean isInMinecart() {
      return isMinecart(this.getVehicle());
   }

   private static boolean isMinecart(Entity entity) {
      return entity instanceof Minecart;
   }

   protected void doPush(Entity entity) {
      if (!isMinecart(entity)) {
         super.doPush(entity);
      }
   }

   public boolean startRiding(Entity entity, boolean force) {
      return super.startRiding(entity, force);
   }

   public void stopRiding() {
      super.stopRiding();
   }

   protected SoundEvent getDeathSound() {
      this.playSound((SoundEvent)MMSounds.ENTITY_GROTTOL_DIE.get(), 1.0F, 1.3F);
      return null;
   }

   @Override
   public Animation getDeathAnimation() {
      return DIE_ANIMATION;
   }

   @Override
   public Animation getHurtAnimation() {
      return HURT_ANIMATION;
   }

   @Override
   public Animation[] getAnimations() {
      return ANIMATIONS;
   }

   public EntityGrottol.EnumDeathType getDeathType() {
      return this.death;
   }

   protected ResourceKey<LootTable> getDefaultLootTable() {
      return LootTableHandler.GROTTOL;
   }

   @Override
   protected ConfigHandler.CombatConfig getCombatConfig() {
      return ConfigHandler.COMMON.MOBS.GROTTOL.combatConfig;
   }

   public boolean isBlockDiggable(BlockState blockState) {
      if (blockState.is(MMBlockTags.CAN_GROTTOL_DIG)) {
         return true;
      }

      Block block = ((ICopiedBlockProperties)blockState.getBlock().properties()).mowziesMobs$getBaseBlock();
      return block != null && block.builtInRegistryHolder().is(MMBlockTags.CAN_GROTTOL_DIG);
   }

   public boolean getDeepslate() {
      return (Boolean)this.getEntityData().get(DEEPSLATE);
   }

   public void setDeepslate(boolean deepslate) {
      this.getEntityData().set(DEEPSLATE, deepslate);
   }

   public boolean getBlackpink() {
      return (Boolean)this.getEntityData().get(BLACKPINK);
   }

   public void setBlackpink(boolean blackpink) {
      this.getEntityData().set(BLACKPINK, blackpink);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putBoolean("deepslate", this.getDeepslate());
      compound.putBoolean("blackpink", this.getBlackpink());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setDeepslate(compound.getBoolean("deepslate"));
      this.setBlackpink(compound.getBoolean("blackpink"));
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      if (this.getY() < 8.0 && reason != MobSpawnType.MOB_SUMMONED) {
         this.setDeepslate(true);
      }

      return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
   }

   public enum EnumDeathType {
      NORMAL,
      PICKAXE,
      FORTUNE_PICKAXE;
   }
}
