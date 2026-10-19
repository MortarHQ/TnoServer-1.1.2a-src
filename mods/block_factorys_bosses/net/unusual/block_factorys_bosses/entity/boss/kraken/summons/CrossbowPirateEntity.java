package net.unusual.block_factorys_bosses.entity.boss.kraken.summons;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.unusual.block_factorys_bosses.block.entity.KrakenSpawnerBlockEntity;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CrossbowPirateEntity extends Monster implements GeoEntity, CrossbowAttackMob, OwnableByAllEntity, KrakenSpawnerBlockEntity.SpawnerTied {
   private static final EntityDataAccessor<Boolean> CHARGING_CROSSBOW = SynchedEntityData.defineId(CrossbowPirateEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(
      CrossbowPirateEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("animation.bf_bs.melee_pirate_idle");
   public static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("animation.bf_bs.melee_pirate_walk");
   public static final RawAnimation RANGED_1 = RawAnimation.begin().thenLoop("animation.bf_bs.melee_ranged_attack_1");
   public static final RawAnimation RANGED_2 = RawAnimation.begin().thenLoop("animation.bf_bs.melee_ranged_attack_2");
   public static final RawAnimation RANGED_3 = RawAnimation.begin().thenLoop("animation.bf_bs.melee_ranged_attack_3");
   public static final RawAnimation RANGED_4 = RawAnimation.begin().thenLoop("animation.bf_bs.melee_ranged_attack_4");
   public static final RawAnimation SPAWN_ANIM = RawAnimation.begin().thenPlay("animation.bf_bs.melee_pirate_spawn_2");
   public static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenPlay("animation.bf_bs.melee_pirate_death");
   private static final int DEATH_DURATION = 22;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

   public CrossbowPirateEntity(EntityType<CrossbowPirateEntity> type, Level world) {
      super(type, world);
      this.setPersistenceRequired();
      this.xpReward = 15;
   }

   public void die(DamageSource damageSource) {
      super.die(damageSource);
      if (this.dead) {
         this.triggerAnim(null, "death");
         if (this.isPassenger()) {
            this.removeVehicle();
         }
      }
   }

   protected void tickDeath() {
      this.deathTime++;
      if (this.deathTime >= 22 && !this.level().isClientSide() && !this.isRemoved()) {
         this.level().broadcastEntityEvent(this, (byte)60);
         this.remove(RemovalReason.KILLED);
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(CHARGING_CROSSBOW, false);
      builder.define(OWNER_UUID, Optional.empty());
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new RangedCrossbowAttackGoal(this, 1.0, 8.0F));
      this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(1, new OwnableByAllEntity.OwnerHurtByTargetGoal(this));
      this.targetSelector.addGoal(2, new OwnableByAllEntity.OwnerHurtTargetGoal(this));
      this.targetSelector.addGoal(3, new OwnableByAllEntity.DoNotAttackOwnerGoal(this, Player.class, false, false));
      this.targetSelector.addGoal(4, new HurtByTargetGoal(this, new Class[0]));
   }

   public SoundEvent getAmbientSound() {
      return SoundEvents.SKELETON_AMBIENT;
   }

   public SoundEvent getHurtSound(DamageSource ds) {
      return SoundEvents.SKELETON_HURT;
   }

   public SoundEvent getDeathSound() {
      return SoundEvents.SKELETON_DEATH;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.getOwnerUUID() != null) {
         compound.putUUID("Owner", this.getOwnerUUID());
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.hasUUID("Owner")) {
         this.setOwnerUUID(compound.getUUID("Owner"));
      }
   }

   public static void init(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)BossesRiseEntities.CROSSBOW_PIRATE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         (entityType, world, reason, pos, random) -> world.getDifficulty() != Difficulty.PEACEFUL,
         Operation.REPLACE
      );
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder builder = Mob.createMobAttributes();
      builder = builder.add(Attributes.MOVEMENT_SPEED, 0.25);
      builder = builder.add(Attributes.MAX_HEALTH, 15.0);
      builder = builder.add(Attributes.ARMOR, 2.0);
      builder = builder.add(Attributes.ATTACK_DAMAGE, 9.0);
      builder = builder.add(Attributes.FOLLOW_RANGE, 16.0);
      return builder.add(Attributes.STEP_HEIGHT, 0.6);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      SpawnGroupData data = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
      this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.CROSSBOW));
      return data;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<CrossbowPirateEntity> controller = new AnimationController(
         this,
         "main_controller",
         5,
         state -> !this.getEntityData().get(CHARGING_CROSSBOW)
               && (!(this.getMainHandItem().getItem() instanceof CrossbowItem) || !CrossbowItem.isCharged(this.getMainHandItem()))
            ? (state.isMoving() ? state.setAndContinue(WALK_ANIM) : state.setAndContinue(IDLE_ANIM))
            : state.setAndContinue(RANGED_2)
      );
      controller.triggerableAnim("spawn", SPAWN_ANIM);
      controller.triggerableAnim("death", DEATH_ANIM);
      controllers.add(controller);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   public boolean canFireProjectileWeapon(ProjectileWeaponItem projectileWeapon) {
      return projectileWeapon == Items.CROSSBOW;
   }

   public void setChargingCrossbow(boolean b) {
      this.entityData.set(CHARGING_CROSSBOW, b);
   }

   public void onCrossbowAttackPerformed() {
      this.noActionTime = 0;
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      this.performCrossbowAttack(this, 1.6F);
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
   }

   protected boolean shouldDropLoot() {
      return this.getOwnerUUID() == null;
   }
}
