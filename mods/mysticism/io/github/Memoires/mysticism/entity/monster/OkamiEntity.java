package io.github.Memoires.mysticism.entity.monster;

import io.github.Memoires.mysticism.entity.variant.OkamiVariant;
import io.github.Memoires.mysticism.registry.entity.MysticismEntityTypes;
import io.github.Memoires.mysticism.registry.entity.MysticismMonsterEntityTypes;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.HumanoidConsumeItem;
import io.github.manasmods.tensura.entity.ai.behaviour.misc.WakeUp;
import io.github.manasmods.tensura.entity.ai.behaviour.path.SubordinateFollowOwner;
import io.github.manasmods.tensura.entity.ai.sensor.SleepSensor;
import io.github.manasmods.tensura.entity.template.PlayerLikeEntity;
import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.entity.template.subclass.INameEvolution;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.registry.entity.TensuraEntityTypes;
import io.github.manasmods.tensura.registry.item.TensuraToolItems;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.BreedWithPartner;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.InteractWithDoor;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.schedule.SmartBrainSchedule;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;
import software.bernie.geckolib.util.GeckoLibUtil;

public class OkamiEntity extends PlayerLikeEntity implements SmartBrainOwner<OkamiEntity>, INameEvolution, ISubordinate, GeoEntity, VariantHolder<OkamiVariant> {
   private SmartBrainSchedule schedule;
   private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(OkamiEntity.class, EntityDataSerializers.INT);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public OkamiEntity(EntityType<? extends OkamiEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
      this.xpReward = 5;
   }

   public static Builder setAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.ATTACK_DAMAGE, 1.5)
         .add(Attributes.MAX_HEALTH, 44.0)
         .add(Attributes.MOVEMENT_SPEED, 0.2F)
         .add(Attributes.FOLLOW_RANGE, 32.0)
         .add(Attributes.KNOCKBACK_RESISTANCE, 0.0)
         .add(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, 1.0)
         .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_ID_TYPE_VARIANT, 0);
   }

   public boolean checkSpawnRules(LevelAccessor pLevel, MobSpawnType pSpawnReason) {
      return TensuraEntityTypes.rollChance(MysticismEntityTypes.MYSTICISM_CONFIG.SpawnChance.okamiChance, pLevel.getRandom())
         && super.checkSpawnRules(pLevel, pSpawnReason);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getTypeVariant());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.entityData.set(DATA_ID_TYPE_VARIANT, compound.getInt("Variant"));
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
      super.onSyncedDataUpdated(pKey);
   }

   public int getChestSlots() {
      return 18 + 9 * this.getCurrentEvolutionState();
   }

   public boolean canSleep() {
      return true;
   }

   @NotNull
   public EntityDimensions getDefaultDimensions(Pose pPose) {
      if (this.canSleep() && this.isSleeping()) {
         EntityDimensions entitydimensions = super.getDefaultDimensions(pPose);
         return entitydimensions.scale(1.9F, 0.35F);
      } else {
         EntityDimensions entitydimensions = super.getDefaultDimensions(pPose);
         if (this.shouldSwim()) {
            return entitydimensions.scale(1.0F, 0.25F);
         } else {
            return !this.isOrderedToSit() && !this.isInSittingPose() ? entitydimensions : entitydimensions.scale(1.0F, 0.75F);
         }
      }
   }

   protected void updatePoses() {
   }

   public boolean canMate(Animal pOtherAnimal) {
      if (pOtherAnimal == this) {
         return false;
      } else {
         return pOtherAnimal.getClass() != this.getClass() ? false : this.isInLove() && pOtherAnimal.isInLove();
      }
   }

   public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
      OkamiEntity baby = (OkamiEntity)((EntityType)MysticismMonsterEntityTypes.OKAMI.get()).create(pLevel);
      if (baby == null) {
         return null;
      }

      UUID uuid = this.getOwnerUUID();
      if (uuid != null) {
         baby.setOwnerUUID(uuid);
         baby.setTame(true, true);
      }

      return baby;
   }

   protected boolean shouldWakeUp() {
      return super.shouldWakeUp();
   }

   public float getAgeScale() {
      float multiplier = 1.0F;
      return multiplier * (this.isBaby() ? 0.75F : 1.0F);
   }

   public void evolve() {
      int current = this.getCurrentEvolutionState();
      if (current < this.getMaxEvolutionState()) {
         this.setCurrentEvolutionState(current + 1);
         this.gainMovementSpeed(this, 0.05);
         this.gainSwimSpeed(this, 1.0);
         this.gainMaxHealth(this, 10.0);
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      InteractionResult eating = this.handleEating(player, hand, itemstack);
      if (eating.consumesAction()) {
         return eating;
      }

      if (!this.level().isClientSide) {
         if (this.isTame() && this.isOwnedBy(player)) {
            InteractionResult interaction = this.getInventoryInteraction(player, hand);
            if (interaction != InteractionResult.PASS) {
               return interaction;
            }

            this.cycleCommands(this, player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         } else {
            return super.mobInteract(player, hand);
         }
      } else {
         boolean flag = this.isOwnedBy(player) || this.isTame();
         return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
      }
   }

   public void ate() {
      super.ate();
      this.heal(3.0F);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData) {
      if (!pReason.equals(MobSpawnType.BUCKET)) {
         this.setVariant((OkamiVariant)Util.getRandom(OkamiVariant.values(), this.random));
         this.populateDefaultEquipmentSlots(this.random, pDifficulty);
      }

      return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData);
   }

   protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
      super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
      if (!(pRandom.nextFloat() >= 0.5F)) {
         int i = pRandom.nextInt(3);
         ItemStack stack = new ItemStack((ItemLike)TensuraToolItems.STONE_SHORT_SWORD.get());
         this.inventory.setItem(this.getSlotId(EquipmentSlot.MAINHAND), stack);
         this.updateContainerEquipment();
      }
   }

   @Nullable
   public Item getEquipmentForArmor(EquipmentSlot pSlot, int pChance) {
      return switch (pSlot) {
         case HEAD -> pChance == 2 ? Items.LEATHER_HELMET : null;
         case CHEST -> pChance == 2 ? Items.LEATHER_CHESTPLATE : (pChance == 4 ? Items.CHAINMAIL_CHESTPLATE : null);
         case LEGS -> pChance == 2 ? Items.LEATHER_LEGGINGS : (pChance == 4 ? Items.CHAINMAIL_LEGGINGS : null);
         case FEET -> pChance == 2 ? Items.LEATHER_BOOTS : (pChance == 4 ? Items.CHAINMAIL_BOOTS : null);
         default -> null;
      };
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TensuraSoundEvents.DIREWOLF_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource pDamageSource) {
      return (SoundEvent)TensuraSoundEvents.DIREWOLF_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TensuraSoundEvents.DIREWOLF_DEATH.get();
   }

   @NotNull
   public SoundSource getSoundSource() {
      return SoundSource.NEUTRAL;
   }

   protected @NotNull Provider<?> brainProvider() {
      return new SmartBrainProvider(this, true);
   }

   protected void customServerAiStep() {
      super.customServerAiStep();
      this.tickBrain(this);
   }

   public List<ExtendedSensor<OkamiEntity>> getSensors() {
      return ObjectArrayList.of(new ExtendedSensor[]{new NearbyLivingEntitySensor(), new HurtBySensor(), new SleepSensor()});
   }

   public BrainActivityGroup<OkamiEntity> getCoreTasks() {
      return BrainActivityGroup.coreTasks(new Behavior[]{new WakeUp().stopIf(entity -> {
         if (!TensuraBehaviourHelper.canContinueToSleep(this)) {
            this.stopSleeping();
            return true;
         } else {
            return false;
         }
      }), new LookAtTarget(), new FloatToSurfaceOfFluid(), TensuraTamableEntity.getMoveToWalkTarget()});
   }

   public BrainActivityGroup<OkamiEntity> getIdleTasks() {
      return BrainActivityGroup.idleTasks(
         new Behavior[]{
            new FirstApplicableBehaviour(
               new ExtendedBehaviour[]{
                  new BreedWithPartner(),
                  TensuraBehaviourHelper.getMoveToWanderPos(),
                  TensuraBehaviourHelper.getPreyTargeting(this, entity -> false),
                  new SubordinateFollowOwner(),
                  new SetPlayerLookTarget(),
                  new SetRandomLookTarget()
               }
            ),
            new InteractWithDoor(),
            new HumanoidConsumeItem().startCondition(entity -> this.shouldHeal()).stopIf(entity -> !this.shouldHeal()),
            new OneRandomBehaviour(new ExtendedBehaviour[]{new SetRandomWalkTarget(), new Idle().runFor(entity -> this.getRandom().nextInt(30, 60))})
         }
      );
   }

   public BrainActivityGroup<OkamiEntity> getFightTasks() {
      return BrainActivityGroup.fightTasks(
         new Behavior[]{
            new InvalidateAttackTarget().invalidateIf((mob, target) -> this.shouldStopTarget(mob, target)),
            new StrafeTarget().stopStrafingWhen(entity -> !this.usingRangedWeapon()).startCondition(mob -> ((PlayerLikeEntity)mob).usingRangedWeapon()),
            new SetWalkTargetToAttackTarget().speedMod((owner, target) -> 2.0F).startCondition(entity -> !this.usingRangedWeapon()),
            new FirstApplicableBehaviour(
                  new ExtendedBehaviour[]{
                     PlayerLikeEntity.getSpearAttack(20).attackInterval(entity -> 5).attackRadius(25.0F),
                     PlayerLikeEntity.getCrossbowAttack().attackInterval(entity -> 10).attackRadius(30.0F),
                     PlayerLikeEntity.getBowAttack().attackInterval(entity -> 10).attackRadius(25.0F),
                     new AnimatableMeleeAttack(1)
                        .attackInterval(entity -> 5)
                        .whenStarting(entity -> ((OkamiEntity)entity).triggerAnim("miscController", "punch1"))
                  }
               )
               .startCondition(entity -> !this.usingRangedWeapon())
         }
      );
   }

   public Map<Activity, BrainActivityGroup<? extends OkamiEntity>> getAdditionalTasks() {
      return (Map<Activity, BrainActivityGroup<? extends OkamiEntity>>)Util.make(
         new Object2ObjectOpenHashMap(), map -> map.put(Activity.REST, TensuraBehaviourHelper.getHumanoidSleepActivityGroup(this))
      );
   }

   public SmartBrainSchedule getSchedule() {
      if (this.schedule == null) {
         this.schedule = new SmartBrainSchedule().activityAt(10, Activity.IDLE).activityAt(12000, Activity.REST);
      }

      return this.schedule;
   }

   public void startSleeping(BlockPos blockPos) {
      super.startSleeping(blockPos);
      BrainUtils.setMemory(this, MemoryModuleType.LAST_SLEPT, this.level().getGameTime());
      BrainUtils.clearMemory(this, MemoryModuleType.WALK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.LOOK_TARGET);
      BrainUtils.clearMemory(this, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
   }

   public void stopSleeping() {
      super.stopSleeping();
      BrainUtils.setMemory(this, MemoryModuleType.LAST_WOKEN, this.level().getGameTime());
   }

   protected PlayState loopController(AnimationState<OkamiEntity> state) {
      String name;
      if (this.isSleeping()) {
         state.getController().setAnimationSpeed(this.isSleeping() ? 0.5 : 1.0);
         name = "Sleep";
      } else if (this.isInSittingPose()) {
         name = "sit";
      } else if (state.isMoving()) {
         double speed = this.getDeltaMovement().horizontalDistanceSqr();
         if (speed > 0.02) {
            name = "run2";
         } else {
            name = "walk";
         }
      } else {
         name = "idle ";
      }

      state.getController().setAnimationSpeed(this.isSprinting() ? 2.0 : 1.5);
      return state.setAndContinue(RawAnimation.begin().thenLoop(name));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController[]{
            new AnimationController(this, "loopController", 3, this::loopController), new AnimationController(this, "miscController", 0, event -> {
               event.getController().setAnimationSpeed(1.8F);
               return PlayState.STOP;
            }).triggerableAnim("punch1", RawAnimation.begin().then("punch1", LoopType.PLAY_ONCE))
         }
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void setVariant(OkamiVariant variant) {
      this.entityData.set(DATA_ID_TYPE_VARIANT, variant.getId() & 0xFF);
   }

   private int getTypeVariant() {
      return (Integer)this.entityData.get(DATA_ID_TYPE_VARIANT);
   }

   public OkamiVariant getVariant() {
      return OkamiVariant.byId(this.getTypeVariant() & 0xFF);
   }
}
