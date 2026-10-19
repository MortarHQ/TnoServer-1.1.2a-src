package com.faboslav.variantsandventures.common.entity.mob;

import com.faboslav.variantsandventures.common.VariantsAndVentures;
import com.faboslav.variantsandventures.common.entity.ai.goal.LeaveWaterGoal;
import com.faboslav.variantsandventures.common.entity.ai.goal.TargetAboveWaterGoal;
import com.faboslav.variantsandventures.common.entity.ai.goal.WanderAroundOnSurfaceGoal;
import com.faboslav.variantsandventures.common.init.VariantsAndVenturesSoundEvents;
import com.faboslav.variantsandventures.common.util.AdvancementHelper;
import com.faboslav.variantsandventures.common.versions.VersionedBlockPathType;
import com.faboslav.variantsandventures.common.versions.VersionedEntitySpawnReason;
import com.faboslav.variantsandventures.common.versions.VersionedGameRulesProvider;
import com.faboslav.variantsandventures.common.versions.VersionedInteractionResult;
import com.faboslav.variantsandventures.common.versions.VersionedNbt;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.function.Predicate;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class MurkEntity extends Skeleton implements Shearable {
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(MurkEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(MurkEntity.class, EntityDataSerializers.BOOLEAN);
   public static final String VARIANT_NBT_KEY = "Variant";
   public static final String SHEARED_NBT_KEY = "Sheared";
   private boolean targetingUnderwater;
   private final WaterBoundPathNavigation waterNavigation;
   private final GroundPathNavigation landNavigation;
   private final Predicate<LivingEntity> PLAYER_FILTER = entity -> entity == null ? false : !this.level().isDay() || entity.isInWater();

   public MurkEntity(EntityType<? extends Skeleton> entityType, Level world) {
      super(entityType, world);
      this.moveControl = new MurkEntity.MurkMoveControl(this);
      this.setPathfindingMalus(VersionedBlockPathType.WATER, 0.0F);
      this.waterNavigation = new WaterBoundPathNavigation(this, world);
      this.landNavigation = new GroundPathNavigation(this, world);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
      this.setVariant(MurkEntity.Variant.getRandom(this.random));
      return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
   }

   public static boolean canSpawn(EntityType<MurkEntity> type, ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos pos, RandomSource random) {
      return spawnReason != VersionedEntitySpawnReason.NATURAL
         ? true
         : world.getFluidState(pos.below()).is(FluidTags.WATER) && isValidSpawnDepth(world, pos) && random.nextInt(40) == 0;
   }

   private static boolean isValidSpawnDepth(LevelAccessor world, BlockPos pos) {
      return pos.getY() < world.getSeaLevel() - 5;
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new WanderAroundOnSurfaceGoal(this, 1.0));
      this.goalSelector.addGoal(5, new LeaveWaterGoal(this, 1.0));
      this.goalSelector.addGoal(6, new TargetAboveWaterGoal(this, 1.0, this.level().getSeaLevel()));
      this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0));
      this.targetSelector.addGoal(1, new HurtByTargetGoal(this, new Class[0]));
      this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, this.PLAYER_FILTER));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, IronGolem.class, true));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Axolotl.class, true, false));
      this.targetSelector.addGoal(3, new NearestAttackableTargetGoal(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
      super.registerGoals();
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(SHEARED, false);
   }

   public void addAdditionalSaveData(CompoundTag nbt) {
      super.addAdditionalSaveData(nbt);
      nbt.putInt("Variant", this.getVariant().getId());
      nbt.putBoolean("Sheared", this.isSheared());
   }

   public void readAdditionalSaveData(CompoundTag nbt) {
      super.readAdditionalSaveData(nbt);
      this.setVariant(MurkEntity.Variant.VARIANTS[VersionedNbt.getInt(nbt, "Variant", MurkEntity.Variant.PURPLE.ordinal())]);
      this.setSheared(VersionedNbt.getBoolean(nbt, "Sheared", false));
   }

   public boolean checkSpawnObstruction(LevelReader world) {
      return world.isUnobstructed(this);
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createMurkAttributes() {
      return Skeleton.createAttributes().add(Attributes.MAX_HEALTH, 16.0);
   }

   protected SoundEvent getAmbientSound() {
      return this.isInWater() ? VariantsAndVenturesSoundEvents.ENTITY_MURK_AMBIENT_WATER.get() : VariantsAndVenturesSoundEvents.ENTITY_MURK_AMBIENT.get();
   }

   public void playAmbientSound() {
      SoundEvent soundEvent = this.getAmbientSound();
      if (soundEvent != null) {
         this.playSound(soundEvent, this.isInWater() ? 0.25F : this.getSoundVolume(), this.getVoicePitch());
      }
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return this.isInWater() ? VariantsAndVenturesSoundEvents.ENTITY_MURK_HURT_WATER.get() : VariantsAndVenturesSoundEvents.ENTITY_MURK_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return this.isInWater() ? VariantsAndVenturesSoundEvents.ENTITY_MURK_DEATH_WATER.get() : VariantsAndVenturesSoundEvents.ENTITY_MURK_DEATH.get();
   }

   protected void playStepSound(BlockPos blockPos, BlockState blockState) {
      this.playSound(this.isInWater() ? VariantsAndVenturesSoundEvents.ENTITY_MURK_STEP.get() : SoundEvents.SKELETON_STEP, 0.15F, 1.0F);
   }

   public void tick() {
      if (!VariantsAndVentures.getConfig().enableMurk) {
         this.discard();
      }

      super.tick();
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      ItemStack possibleBow = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
      if (possibleBow.is(Items.BOW)) {
         ItemStack possibleProjectile = this.getProjectile(possibleBow);
         if (possibleProjectile != ItemStack.EMPTY) {
            AbstractArrow abstractArrow = this.getArrow(possibleProjectile, velocity, possibleBow);
            double d = target.getX() - this.getX();
            double e = target.getY(0.3333333333333333) - abstractArrow.getY();
            double f = target.getZ() - this.getZ();
            double g = Math.sqrt(d * d + f * f);
            abstractArrow.shoot(d, e + g * 0.2F, f, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
            this.level().addFreshEntity(abstractArrow);
            this.playSound(VariantsAndVenturesSoundEvents.ENTITY_MURK_ATTACK.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         }
      }
   }

   public boolean isPushedByFluid() {
      return !this.isSwimming();
   }

   public boolean shouldDiscardFriction() {
      return this.isSwimming();
   }

   private boolean isTargetingUnderwater() {
      if (this.targetingUnderwater) {
         return true;
      }

      LivingEntity livingEntity = this.getTarget();
      return livingEntity != null && livingEntity.isInWater();
   }

   public void setTargetingUnderwater(boolean targetingUnderwater) {
      this.targetingUnderwater = targetingUnderwater;
   }

   public void travel(Vec3 movementInput) {
      if (this.isEffectiveAi() && this.isInWater() && this.isTargetingUnderwater()) {
         this.moveRelative(0.01F, movementInput);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(movementInput);
      }
   }

   public void updateSwimming() {
      if (!this.level().isClientSide()) {
         if (this.isEffectiveAi() && this.isInWater() && this.isTargetingUnderwater()) {
            this.navigation = this.waterNavigation;
            this.setSwimming(true);
         } else {
            this.navigation = this.landNavigation;
            this.setSwimming(false);
         }
      }
   }

   public boolean hasFinishedCurrentPath() {
      Path path = this.getNavigation().getPath();
      if (path != null) {
         BlockPos blockPos = path.getTarget();
         if (blockPos != null) {
            double d = this.distanceToSqr(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            return d < 4.0;
         }
      }

      return false;
   }

   public boolean isSheared() {
      return (Boolean)this.entityData.get(SHEARED);
   }

   public void setSheared(boolean sheared) {
      this.entityData.set(SHEARED, sheared);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (itemStack.is(Items.SHEARS) && this.readyForShearing()) {
         if (!this.level().isClientSide()) {
            this.shear(SoundSource.PLAYERS);
            this.gameEvent(GameEvent.SHEAR, player);
            EquipmentSlot equipment = Player.getSlotForHand(hand);
            itemStack.hurtAndBreak(1, player, equipment);
         }

         return VersionedInteractionResult.success(this);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void shear(SoundSource soundSource) {
      this.level().playSound(null, this, VariantsAndVenturesSoundEvents.ENTITY_MURK_SHEAR.get(), soundSource, 1.0F, 1.0F);
      this.dropShearedItems();
      this.setSheared(true);
   }

   private void dropShearedItems() {
      Level world = this.level();
      if (world instanceof ServerLevel && VersionedGameRulesProvider.getGameRules(this).getBoolean(GameRules.RULE_DOMOBLOOT)) {
         LootTable shearingLootTable = world.getServer()
            .reloadableRegistries()
            .getLootTable(
               ResourceKey.create(
                  Registries.LOOT_TABLE, VariantsAndVentures.makeID(String.format(Locale.ROOT, "shearing/murk_%s", this.getVariant().getName()))
               )
            );
         LootParams lootContextParameterSet = new net.minecraft.world.level.storage.loot.LootParams.Builder((ServerLevel)world)
            .withParameter(LootContextParams.ORIGIN, this.position())
            .withParameter(LootContextParams.THIS_ENTITY, this)
            .create(LootContextParamSets.GIFT);
         ObjectArrayList<ItemStack> shearingDrops = shearingLootTable.getRandomItems(lootContextParameterSet);
         ObjectListIterator var5 = shearingDrops.iterator();

         while (var5.hasNext()) {
            ItemStack shearingDrop = (ItemStack)var5.next();
            this.spawnAtLocation(shearingDrop);
         }
      }
   }

   public boolean readyForShearing() {
      return !this.isSheared() && this.isAlive();
   }

   public boolean isFreezeConverting() {
      return false;
   }

   public boolean isShaking() {
      return false;
   }

   protected void doFreezeConversion() {
   }

   public boolean canFreeze() {
      return false;
   }

   public void die(DamageSource damageSource) {
      super.die(damageSource);
      AdvancementHelper.triggerMonsterHunter(this.level(), damageSource);
   }

   public boolean canAttackTarget(@Nullable LivingEntity target) {
      if (target == null) {
         return false;
      }

      boolean isDay = this.level().isDay();
      return !isDay || target.isInWater();
   }

   private void setNavigation(PathNavigation navigation) {
      this.navigation = navigation;
   }

   public void setLandNavigation() {
      this.navigation = this.landNavigation;
   }

   public void setWaterNavigation() {
      this.navigation = this.waterNavigation;
   }

   public MurkEntity.Variant getVariant() {
      return MurkEntity.Variant.VARIANTS[this.entityData.get(VARIANT)];
   }

   private void setVariant(MurkEntity.Variant variant) {
      this.entityData.set(VARIANT, variant.getId());
   }

   private final class MurkMoveControl extends MoveControl {
      private final MurkEntity murk;

      public MurkMoveControl(MurkEntity murk) {
         super(murk);
         this.murk = murk;
      }

      public void tick() {
         LivingEntity livingEntity = this.murk.getTarget();
         if (this.murk.isTargetingUnderwater() && this.murk.isInWater()) {
            if (livingEntity != null && livingEntity.getY() > this.murk.getY() || this.murk.targetingUnderwater) {
               this.murk.setDeltaMovement(this.murk.getDeltaMovement().add(0.0, 0.002, 0.0));
            }

            if (this.operation != Operation.MOVE_TO || this.murk.getNavigation().isDone()) {
               this.murk.setSpeed(0.0F);
               return;
            }

            double d = this.wantedX - this.murk.getX();
            double e = this.wantedY - this.murk.getY();
            double f = this.wantedZ - this.murk.getZ();
            double g = Math.sqrt(d * d + e * e + f * f);
            e /= g;
            float h = (float)(Mth.atan2(f, d) * 180.0F / (float)Math.PI) - 90.0F;
            this.murk.setYRot(this.rotlerp(this.murk.getYRot(), h, 90.0F));
            this.murk.yBodyRot = this.murk.getYRot();
            float i = (float)(this.speedModifier * this.murk.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float j = Mth.lerp(0.125F, this.murk.getSpeed(), i);
            this.murk.setSpeed(j);
            this.murk.setDeltaMovement(this.murk.getDeltaMovement().add(j * d * 0.005, j * e * 0.1, j * f * 0.005));
         } else {
            if (!this.murk.onGround()) {
               this.murk.setDeltaMovement(this.murk.getDeltaMovement().add(0.0, -0.008, 0.0));
            }

            super.tick();
         }
      }
   }

   public enum Variant {
      PURPLE(0, "purple"),
      RED(1, "red"),
      YELLOW(2, "yellow");

      public static final MurkEntity.Variant[] VARIANTS = Arrays.stream(values())
         .sorted(Comparator.comparingInt(MurkEntity.Variant::getId))
         .toArray(MurkEntity.Variant[]::new);
      private final int id;
      private final String name;

      Variant(int id, String name) {
         this.id = id;
         this.name = name;
      }

      public int getId() {
         return this.id;
      }

      public String getName() {
         return this.name;
      }

      private static MurkEntity.Variant getRandom(RandomSource random) {
         return (MurkEntity.Variant)Util.getRandom(Arrays.stream(VARIANTS).toArray(MurkEntity.Variant[]::new), random);
      }
   }
}
