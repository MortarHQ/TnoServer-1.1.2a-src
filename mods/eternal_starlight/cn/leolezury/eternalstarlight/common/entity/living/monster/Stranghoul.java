package cn.leolezury.eternalstarlight.common.entity.living.monster;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.PungencyFruitVinesBlock;
import cn.leolezury.eternalstarlight.common.block.entity.DryingRackBlockEntity;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESLootTables;
import cn.leolezury.eternalstarlight.common.entity.projectile.ThrownSpear;
import cn.leolezury.eternalstarlight.common.item.combat.SeedsLauncherItem;
import cn.leolezury.eternalstarlight.common.item.combat.SpearItem;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Stranghoul extends Monster implements NeutralMob, OwnableEntity, RangedAttackMob {
   private static final String TAG_HOME_POS = "home_pos";
   private static final String TAG_BABY = "baby";
   private static final String TAG_GROWTH_TICKS = "growth_ticks";
   private static final String TAG_BREED_COOLDOWN = "breed_cooldown";
   private static final String TAG_ADMIRATION_TICKS = "admiration_ticks";
   private static final String TAG_HIRER = "hirer";
   private static final String TAG_HIRED_TICKS_LEFT = "hired_ticks_left";
   private static final ResourceLocation SPEED_MODIFIER_BABY_ID = ResourceLocation.withDefaultNamespace("baby");
   private static final AttributeModifier SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_ID, 0.2, Operation.ADD_MULTIPLIED_BASE);
   private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
   private int remainingPersistentAngerTime;
   @Nullable
   private UUID persistentAngerTarget;
   public GlobalPos homePos = GlobalPos.of(Level.OVERWORLD, BlockPos.ZERO);
   private int growthTicks;
   private int breedCooldown = 24000;
   private int admirationTicks;
   private boolean makingLove = false;
   @Nullable
   private LivingEntity hirer;
   @Nullable
   private UUID hirerId;
   private int hiredTicksLeft;
   private boolean hiredEatAnim = false;
   protected static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Boolean> BARTERING = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<Integer> HIRER_ID = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(Stranghoul.class, EntityDataSerializers.BOOLEAN);

   @Nullable
   public LivingEntity getHirer() {
      if (this.level().isClientSide) {
         int id = this.getHirerId();
         return this.level().getEntity(id) instanceof LivingEntity living ? living : null;
      } else {
         return this.hirer;
      }
   }

   public void setHirer(@Nullable LivingEntity hirer) {
      if (hirer != null) {
         this.hirerId = hirer.getUUID();
         if (!this.level().isClientSide) {
            this.setHirerId(hirer.getId());
         }
      } else if (!this.level().isClientSide) {
         this.setHirerId(-1);
      }

      this.hirer = hirer;
   }

   public boolean isHired() {
      return this.getHirer() != null;
   }

   public boolean isEating() {
      return (Boolean)this.getEntityData().get(EATING);
   }

   public void setEating(boolean eating) {
      this.getEntityData().set(EATING, eating);
   }

   public boolean isBartering() {
      return (Boolean)this.getEntityData().get(BARTERING);
   }

   public void setBartering(boolean bartering) {
      this.getEntityData().set(BARTERING, bartering);
   }

   public int getHirerId() {
      return (Integer)this.getEntityData().get(HIRER_ID);
   }

   public void setHirerId(int hirerId) {
      this.getEntityData().set(HIRER_ID, hirerId);
   }

   public boolean isBaby() {
      return (Boolean)this.getEntityData().get(BABY);
   }

   public void setBaby(boolean baby) {
      this.getEntityData().set(BABY, baby);
      if (!this.level().isClientSide) {
         AttributeInstance instance = this.getAttribute(Attributes.MOVEMENT_SPEED);
         if (instance != null) {
            instance.removeModifier(SPEED_MODIFIER_BABY.id());
            if (baby) {
               instance.addTransientModifier(SPEED_MODIFIER_BABY);
            }
         }
      }
   }

   public void setEquipmentsOnGrownUp() {
      if (this.level() instanceof ServerLevelAccessor serverLevel) {
         DifficultyInstance difficulty = this.level().getCurrentDifficultyAt(this.blockPosition());
         this.populateDefaultEquipmentSlots(this.getRandom(), difficulty);
         this.populateDefaultEquipmentEnchantments(serverLevel, this.getRandom(), difficulty);
      }
   }

   public Stranghoul(EntityType<? extends Stranghoul> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(EATING, false).define(BARTERING, false).define(HIRER_ID, -1).define(BABY, false);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new Stranghoul.EatGoal());
      this.goalSelector.addGoal(2, new Stranghoul.SeedsLauncherAttackGoal());
      this.goalSelector.addGoal(3, new Stranghoul.SpearAttackGoal());
      this.goalSelector.addGoal(4, new RangedBowAttackGoal(this, 1.0, 20, 15.0F));
      this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, false));
      this.goalSelector.addGoal(6, new Stranghoul.FollowHirerGoal());
      this.goalSelector.addGoal(7, new Stranghoul.BreedGoal());
      this.goalSelector.addGoal(8, new Stranghoul.GoToWantedItemGoal());
      this.goalSelector.addGoal(9, new Stranghoul.GoHomeGoal());
      this.goalSelector.addGoal(10, new Stranghoul.PlantCropGoal());
      this.goalSelector.addGoal(12, new Stranghoul.CropGoal());
      this.goalSelector.addGoal(12, new Stranghoul.DryRottenFleshGoal());
      this.goalSelector.addGoal(13, new WaterAvoidingRandomStrollGoal(this, 1.0, 0.0F) {
         public boolean canUse() {
            return super.canUse() && !Stranghoul.this.isBartering();
         }
      });
      this.goalSelector.addGoal(14, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(15, new RandomLookAroundGoal(this));
      this.targetSelector.addGoal(0, new Stranghoul.HirerHurtByTargetGoal());
      this.targetSelector.addGoal(1, new Stranghoul.HirerHurtTargetGoal());
      this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]).setAlertOthers(new Class[0]));
      this.targetSelector
         .addGoal(
            3,
            new NearestAttackableTargetGoal(
               this,
               LivingEntity.class,
               true,
               living -> living.getType().is(ESTags.EntityTypes.STRANGHOUL_PREYS) && !living.getType().is(ESTags.EntityTypes.STRANGHOUL_CANNOT_HUNT)
            )
         );
      this.targetSelector
         .addGoal(4, new NearestAttackableTargetGoal(this, Player.class, true, living -> !this.isHired() && living.getHealth() / living.getMaxHealth() < 0.15));
      this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal(this, false));
   }

   public void performRangedAttack(LivingEntity target, float distanceFactor) {
      if (this.getMainHandItem().getItem() instanceof SeedsLauncherItem launcherItem) {
         if (this.getBoundingBox().inflate(6.0).intersects(target.getBoundingBox())) {
            ItemStack projectile = this.getProjectile(this.getMainHandItem());
            launcherItem.performShooting(this.level(), this, projectile, InteractionHand.MAIN_HAND);
         }
      } else if (this.getMainHandItem().getItem() instanceof SpearItem spearItem) {
         ThrownSpear spear = spearItem.createSpear(this.level(), this, this.getX(), this.getEyeY() - 0.1, this.getZ(), this.getMainHandItem());
         double x = target.getX() - this.getX();
         double y = target.getY(0.3333333333333333) - spear.getY();
         double z = target.getZ() - this.getZ();
         double dist = Math.sqrt(x * x + z * z);
         spear.shoot(x, y + dist * 0.2F, z, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
         this.level().addFreshEntity(spear);
      } else {
         ItemStack bow = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
         if (ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.NEOFORGE) {
            bow = this.getItemInHand(this.getMainHandItem().getItem() instanceof BowItem ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
         }

         ItemStack projectile = this.getProjectile(bow);
         AbstractArrow arrow = ProjectileUtil.getMobArrow(this, projectile, distanceFactor, bow);
         double x = target.getX() - this.getX();
         double y = target.getY(0.3333333333333333) - arrow.getY();
         double z = target.getZ() - this.getZ();
         double dist = Math.sqrt(x * x + z * z);
         arrow.shoot(x, y + dist * 0.2F, z, 1.6F, 14 - this.level().getDifficulty().getId() * 4);
         this.playSound(ESSoundEvents.STRANGHOUL_SHOOT.get(), 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         this.level().addFreshEntity(arrow);
      }
   }

   public ItemStack getProjectile(ItemStack stack) {
      if (stack.getItem() instanceof ProjectileWeaponItem weaponItem) {
         Predicate<ItemStack> predicate = weaponItem.getSupportedHeldProjectiles();
         ItemStack heldProjectile = ProjectileWeaponItem.getHeldProjectile(this, predicate);
         return heldProjectile.isEmpty()
            ? (
               weaponItem instanceof SeedsLauncherItem
                  ? ESItems.PUNGENCY_FRUIT_SEEDS.get().getDefaultInstance()
                  : ESItems.MALARITE_ARROW.get().getDefaultInstance()
            )
            : heldProjectile;
      } else {
         return ItemStack.EMPTY;
      }
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Monster.createMonsterAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.stranghoul.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.stranghoul.armor())
         .add(Attributes.ATTACK_DAMAGE, ESConfig.INSTANCE.mobsConfig.stranghoul.attackDamage())
         .add(Attributes.FOLLOW_RANGE, ESConfig.INSTANCE.mobsConfig.stranghoul.followRange())
         .add(Attributes.MOVEMENT_SPEED, 0.3);
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
      if (random.nextFloat() < 0.3F && this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
         this.setItemSlot(EquipmentSlot.HEAD, this.dyedLeatherArmor(Items.LEATHER_HELMET, random));
      }

      if (random.nextFloat() < 0.3F && this.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
         this.setItemSlot(EquipmentSlot.CHEST, this.dyedLeatherArmor(Items.LEATHER_CHESTPLATE, random));
      }

      if (random.nextFloat() < 0.3F && this.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
         this.setItemSlot(EquipmentSlot.LEGS, this.dyedLeatherArmor(Items.LEATHER_LEGGINGS, random));
      }

      if (random.nextFloat() < 0.3F && this.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
         this.setItemSlot(EquipmentSlot.FEET, this.dyedLeatherArmor(Items.LEATHER_BOOTS, random));
      }

      if (this.getMainHandItem().isEmpty()) {
         BuiltInRegistries.ITEM
            .getRandomElementOf(ESTags.Items.STRANGHOUL_CAN_USE, random)
            .ifPresent(item -> this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(item)));
      }
   }

   private ItemStack dyedLeatherArmor(Item item, RandomSource random) {
      ItemStack stack = item.getDefaultInstance();
      stack.set(DataComponents.DYED_COLOR, new DyedItemColor(ARGB32.lerp(random.nextFloat(), ARGB32.color(117, 135, 137), ARGB32.color(48, 55, 56)), true));
      return stack;
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(
      ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData
   ) {
      this.homePos = GlobalPos.of(level.getLevel().dimension(), this.blockPosition());
      if (this.random.nextFloat() < 0.1F) {
         this.setBaby(true);
      }

      if (!this.isBaby()) {
         this.populateDefaultEquipmentSlots(this.getRandom(), difficulty);
         this.populateDefaultEquipmentEnchantments(level, this.getRandom(), difficulty);
      }

      this.breedCooldown = this.random.nextInt(24000, 36000);
      return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide) {
         if (this.getTarget() != null && !this.getTarget().isAlive()) {
            this.setTarget(null);
         }

         if (this.hirer == null && this.hirerId != null && this.level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getEntity(this.hirerId) instanceof LivingEntity livingEntity) {
               this.hirer = livingEntity;
            }

            if (this.hirer == null) {
               this.hirerId = null;
            }
         }

         if (this.isAlive() && !this.dead && !this.hiredEatAnim && ESPlatform.INSTANCE.canEntityGrief(this.level(), this)) {
            for (ItemEntity itemEntity : this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(2.0, 1.0, 2.0))) {
               if (!itemEntity.isRemoved()
                  && !itemEntity.getItem().isEmpty()
                  && !itemEntity.hasPickUpDelay()
                  && itemEntity.getOwner() != this
                  && (
                     itemEntity.getItem().is(ESTags.Items.STRANGHOUL_FOOD)
                           && !this.getOffhandItem().has(DataComponents.FOOD)
                           && !this.getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)
                        || this.getTarget() == null
                           && !this.isBaby()
                           && !this.isEating()
                           && itemEntity.getItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)
                           && !this.getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)
                  )) {
                  this.spawnAtLocation(this.getOffhandItem());
                  this.setItemSlot(EquipmentSlot.OFFHAND, itemEntity.getItem());
                  itemEntity.discard();
               }
            }
         }

         if (this.isBaby()) {
            this.growthTicks++;
            if (this.growthTicks > 24000) {
               this.growthTicks = 0;
               this.setBaby(false);
               this.addHappyParticles();
               this.setEquipmentsOnGrownUp();
            }
         } else {
            this.breedCooldown--;
            this.breedCooldown = Math.max(this.breedCooldown, 0);
         }

         this.hiredTicksLeft--;
         this.hiredTicksLeft = Math.max(this.hiredTicksLeft, 0);
         if (this.hiredTicksLeft == 0) {
            this.setHirer(null);
         }

         if (this.getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES) && this.getTarget() == null && this.level() instanceof ServerLevel serverLevel) {
            this.admirationTicks++;
            if (this.admirationTicks >= 60) {
               LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(ESLootTables.GAMEPLAY_STRANGHOUL_BARTERING);
               List<ItemStack> items = lootTable.getRandomItems(
                  new net.minecraft.world.level.storage.loot.LootParams.Builder(serverLevel)
                     .withParameter(LootContextParams.THIS_ENTITY, this)
                     .create(LootContextParamSets.PIGLIN_BARTER)
               );
               Vec3 pos = LandRandomPos.getPos(this, 4, 2);
               List<Player> players = this.level().getNearbyPlayers(TargetingConditions.forNonCombat(), this, this.getBoundingBox().inflate(4.0));
               players.sort(Comparator.comparingDouble(p -> p.distanceTo(this)));
               if (!players.isEmpty()) {
                  pos = players.getFirst().position();
               }

               if (pos == null) {
                  pos = this.position();
               }

               for (ItemStack item : items) {
                  BehaviorUtils.throwItem(this, item, pos.add(0.0, 1.0, 0.0));
               }

               this.admirationTicks = 0;
               this.getOffhandItem().consume(1, this);
               this.swing(InteractionHand.OFF_HAND);
            }

            this.setBartering(true);
         } else {
            this.admirationTicks = 0;
            this.setBartering(false);
         }
      }
   }

   public boolean isAlliedTo(Entity entity) {
      LivingEntity hirer = this.getHirer();
      return super.isAlliedTo(entity)
         || entity instanceof Stranghoul
         || hirer != null
            && (
               hirer.isAlliedTo(entity)
                  || hirer == entity
                  || entity instanceof OwnableEntity ownable && ownable.getOwnerUUID() != null && ownable.getOwnerUUID().equals(this.getOwnerUUID())
            );
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if (ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.getData(player) <= 0
         && stack.is(ESTags.Items.STRANGHOUL_HIRING_FOOD)
         && !this.isHired()
         && !this.isBaby()
         && this.getTarget() == null) {
         this.setHirer(player);
         ESDataAttachments.STRANGHOUL_HIRING_COOLDOWN.setData(player, ESConfig.INSTANCE.mobsConfig.stranghoul.hiringCooldown());
         this.hiredTicksLeft = 24000;
         this.hiredEatAnim = true;
         this.spawnAtLocation(this.getOffhandItem());
         this.setItemSlot(EquipmentSlot.OFFHAND, stack.copyWithCount(1));
         this.setPersistenceRequired();
         stack.consume(1, player);
         if (player instanceof ServerPlayer serverPlayer) {
            ((PlayerTrigger)ESCriteriaTriggers.HIRE_STRANGHOUL.get()).trigger(serverPlayer);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         if (stack.is(ESTags.Items.STRANGHOUL_CURRENCIES) && !this.isBaby() && this.getOffhandItem().isEmpty() && this.getTarget() == null) {
            this.setItemSlot(EquipmentSlot.OFFHAND, stack.copyWithCount(1));
            stack.consume(1, player);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         }

         if (this.isHired() && player == this.getHirer()) {
            boolean infiniteMaterials = player.hasInfiniteMaterials();
            if (stack.is(ItemTags.HEAD_ARMOR)) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
                     player.setItemInHand(hand, this.getItemBySlot(EquipmentSlot.HEAD).copy());
                  }

                  this.setItemSlot(EquipmentSlot.HEAD, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.HEAD);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (stack.is(ItemTags.CHEST_ARMOR)) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                     player.setItemInHand(hand, this.getItemBySlot(EquipmentSlot.CHEST).copy());
                  }

                  this.setItemSlot(EquipmentSlot.CHEST, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.CHEST);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (stack.is(ItemTags.LEG_ARMOR)) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
                     player.setItemInHand(hand, this.getItemBySlot(EquipmentSlot.LEGS).copy());
                  }

                  this.setItemSlot(EquipmentSlot.LEGS, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.LEGS);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (stack.is(ItemTags.FOOT_ARMOR)) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
                     player.setItemInHand(hand, this.getItemBySlot(EquipmentSlot.FEET).copy());
                  }

                  this.setItemSlot(EquipmentSlot.FEET, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.FEET);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (stack.has(DataComponents.FOOD)) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getOffhandItem().isEmpty()) {
                     player.setItemInHand(hand, this.getOffhandItem().copy());
                  }

                  this.setItemSlot(EquipmentSlot.OFFHAND, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.OFFHAND);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            if (!stack.isEmpty()) {
               if (!this.level().isClientSide) {
                  if (!infiniteMaterials || !this.getMainHandItem().isEmpty()) {
                     player.setItemInHand(hand, this.getMainHandItem().copy());
                  }

                  this.setItemSlot(EquipmentSlot.MAINHAND, stack.copy());
                  this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
               }

               return InteractionResult.sidedSuccess(this.level().isClientSide);
            }

            for (EquipmentSlot slot : EquipmentSlot.values()) {
               if (!this.getItemBySlot(slot).isEmpty()) {
                  if (!this.level().isClientSide) {
                     player.setItemInHand(hand, this.getItemBySlot(slot).copy());
                     this.setItemSlot(slot, ItemStack.EMPTY);
                  }

                  return InteractionResult.sidedSuccess(this.level().isClientSide);
               }
            }
         }

         return super.mobInteract(player, hand);
      }
   }

   public boolean canBreed() {
      return !this.makingLove
         && this.breedCooldown <= 0
         && !this.isHired()
         && !this.isBaby()
         && this.level().getRawBrightness(this.blockPosition(), 0) < 12
         && this.level().getEntitiesOfClass(Stranghoul.class, this.getBoundingBox().inflate(10.0)).stream().filter(Stranghoul::isBaby).count() < 5L;
   }

   public final boolean unableToMoveToHirer() {
      return this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
   }

   public void tryToTeleportToHirer() {
      LivingEntity livingEntity = this.getHirer();
      if (livingEntity != null) {
         this.teleportToAroundBlockPos(livingEntity.blockPosition());
      }
   }

   private void teleportToAroundBlockPos(BlockPos blockPos) {
      for (int i = 0; i < 10; i++) {
         int j = this.random.nextIntBetweenInclusive(-3, 3);
         int k = this.random.nextIntBetweenInclusive(-3, 3);
         if (Math.abs(j) >= 2 || Math.abs(k) >= 2) {
            int l = this.random.nextIntBetweenInclusive(-1, 1);
            if (this.maybeTeleportTo(blockPos.getX() + j, blockPos.getY() + l, blockPos.getZ() + k)) {
               return;
            }
         }
      }
   }

   private boolean maybeTeleportTo(int i, int j, int k) {
      if (!this.canTeleportTo(new BlockPos(i, j, k))) {
         return false;
      }

      this.moveTo(i + 0.5F, j, k + 0.5F, this.getYRot(), this.getXRot());
      this.navigation.stop();
      return true;
   }

   private boolean canTeleportTo(BlockPos blockPos) {
      PathType pathType = WalkNodeEvaluator.getPathTypeStatic(this, blockPos);
      if (pathType != PathType.WALKABLE) {
         return false;
      }

      BlockState blockState = this.level().getBlockState(blockPos.below());
      if (blockState.getBlock() instanceof LeavesBlock) {
         return false;
      }

      BlockPos blockPos2 = blockPos.subtract(this.blockPosition());
      return this.level().noCollision(this, this.getBoundingBox().move(blockPos2));
   }

   public void addHappyParticles() {
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 5; i++) {
            double d = this.getRandom().nextGaussian() * 0.02;
            double e = this.getRandom().nextGaussian() * 0.02;
            double f = this.getRandom().nextGaussian() * 0.02;
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel, new ParticlePacket(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f)
               );
         }
      }
   }

   public void addAlliedParticles() {
      if (this.level() instanceof ServerLevel serverLevel) {
         for (int i = 0; i < 5; i++) {
            double d = this.getRandom().nextGaussian() * 0.02;
            double e = this.getRandom().nextGaussian() * 0.02;
            double f = this.getRandom().nextGaussian() * 0.02;
            ESPlatform.INSTANCE
               .sendToAllClients(
                  serverLevel,
                  new ParticlePacket((ParticleOptions)ESParticles.ALLIED.get(), this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d, e, f)
               );
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      return super.hurt(
         source, (source.getDirectEntity() instanceof LivingEntity living && living.getWeaponItem().is(ESTags.Items.STRANGHOUL_VULNERABLE_TO) ? 2 : 1) * amount
      );
   }

   public boolean canBeAffected(MobEffectInstance instance) {
      return !instance.is(MobEffects.HUNGER) && !instance.is(ESMobEffects.TEARY.asHolder()) && super.canBeAffected(instance);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      super.onSyncedDataUpdated(accessor);
      if (BABY.equals(accessor)) {
         this.refreshDimensions();
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      this.addPersistentAngerSaveData(compoundTag);
      compoundTag.put("home_pos", (Tag)GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, this.homePos).getOrThrow());
      compoundTag.putBoolean("baby", this.isBaby());
      compoundTag.putInt("growth_ticks", this.growthTicks);
      compoundTag.putInt("breed_cooldown", this.breedCooldown);
      compoundTag.putInt("admiration_ticks", this.admirationTicks);
      if (this.hirer != null) {
         compoundTag.putUUID("hirer", this.hirer.getUUID());
      }

      compoundTag.putInt("hired_ticks_left", this.hiredTicksLeft);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.readPersistentAngerSaveData(this.level(), compoundTag);
      if (compoundTag.contains("home_pos")) {
         GlobalPos.CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("home_pos"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Stranghoul home pos: {}", s))
            .ifPresent(pos -> this.homePos = pos);
      }

      if (compoundTag.contains("baby", 1)) {
         this.setBaby(compoundTag.getBoolean("baby"));
      }

      this.growthTicks = compoundTag.getInt("growth_ticks");
      this.breedCooldown = compoundTag.getInt("breed_cooldown");
      this.admirationTicks = compoundTag.getInt("admiration_ticks");
      if (compoundTag.hasUUID("hirer")) {
         this.hirerId = compoundTag.getUUID("hirer");
      }

      this.hiredTicksLeft = compoundTag.getInt("hired_ticks_left");
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return ESSoundEvents.STRANGHOUL_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.STRANGHOUL_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.STRANGHOUL_DEATH.get();
   }

   public int getRemainingPersistentAngerTime() {
      return this.remainingPersistentAngerTime;
   }

   public void setRemainingPersistentAngerTime(int i) {
      this.remainingPersistentAngerTime = i;
   }

   @Nullable
   public UUID getPersistentAngerTarget() {
      return this.persistentAngerTarget;
   }

   public void setPersistentAngerTarget(@Nullable UUID uuid) {
      this.persistentAngerTarget = uuid;
   }

   public void startPersistentAngerTimer() {
      this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.getRandom()));
   }

   @Nullable
   public UUID getOwnerUUID() {
      return this.hirerId;
   }

   public static boolean checkStranghoulSpawnRules(
      EntityType<? extends Stranghoul> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkAnyLightMonsterSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.stranghoul.canSpawn();
   }

   private class BreedGoal extends Goal {
      private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0).ignoreLineOfSight();
      protected final Stranghoul stranghoul = Stranghoul.this;
      protected final Level level = Stranghoul.this.level();
      @Nullable
      protected Stranghoul partner;
      private int loveTime;

      public BreedGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (this.stranghoul.getRandom().nextInt(reducedTickDelay(80)) == 0 && this.stranghoul.canBreed()) {
            this.partner = this.getFreePartner();
            return this.partner != null;
         } else {
            return false;
         }
      }

      public boolean canContinueToUse() {
         return this.partner != null && this.partner.isAlive() && this.loveTime < 60;
      }

      public void start() {
         this.stranghoul.makingLove = true;
         if (this.partner != null) {
            this.partner.makingLove = true;
         }
      }

      public void stop() {
         this.stranghoul.makingLove = false;
         if (this.partner != null) {
            this.partner.makingLove = false;
         }

         this.partner = null;
         this.loveTime = 0;
      }

      public void tick() {
         if (this.partner != null) {
            this.stranghoul.getLookControl().setLookAt(this.partner, 10.0F, this.stranghoul.getMaxHeadXRot());
            this.stranghoul.getNavigation().moveTo(this.partner, 1.0);
            this.loveTime++;
            if (this.loveTime >= this.adjustedTickDelay(60) && this.stranghoul.distanceToSqr(this.partner) < 9.0) {
               Stranghoul baby = new Stranghoul(ESEntities.STRANGHOUL.get(), this.level);
               baby.setBaby(true);
               baby.moveTo(this.stranghoul.getX(), this.stranghoul.getY(), this.stranghoul.getZ(), 0.0F, 0.0F);
               if (this.level instanceof ServerLevel serverLevel) {
                  baby.finalizeSpawn(serverLevel, this.level.getCurrentDifficultyAt(baby.blockPosition()), MobSpawnType.BREEDING, null);
               }

               this.level.addFreshEntity(baby);
               baby.addHappyParticles();
               this.stranghoul.breedCooldown = 24000;
               this.partner.breedCooldown = 24000;
               this.loveTime = 60;
            }
         }
      }

      @Nullable
      private Stranghoul getFreePartner() {
         List<Stranghoul> list = this.level
            .getNearbyEntities(Stranghoul.class, PARTNER_TARGETING, this.stranghoul, this.stranghoul.getBoundingBox().inflate(8.0))
            .stream()
            .filter(Stranghoul::canBreed)
            .sorted(Comparator.comparingDouble(s -> s.distanceTo(this.stranghoul)))
            .toList();
         return list.isEmpty() ? null : list.getFirst();
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   private class CropGoal extends MoveToBlockGoal {
      public CropGoal() {
         super(Stranghoul.this, 1.0, 20, 15);
      }

      public boolean canUse() {
         return !Stranghoul.this.isHired() && ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)
            ? Stranghoul.this.getMainHandItem().is(ItemTags.HOES) && !Stranghoul.this.isBartering() && super.canUse()
            : false;
      }

      public void tick() {
         super.tick();
         if (this.isReachedTarget()) {
            Level level = Stranghoul.this.level();
            BlockState blockState = level.getBlockState(this.blockPos);
            if (blockState.is((Block)ESBlocks.PUNGENCY_FRUIT_VINES.get())
               && (Integer)blockState.getValue(PungencyFruitVinesBlock.AGE) == 7
               && ESPlatform.INSTANCE.postEntityDestroyBlockEvent(level, this.blockPos, Stranghoul.this)) {
               level.destroyBlock(this.blockPos, true, Stranghoul.this);
               Stranghoul.this.swing(InteractionHand.MAIN_HAND);
               Stranghoul.this.addHappyParticles();
            }
         }
      }

      public double acceptedDistance() {
         return 2.5;
      }

      protected boolean isValidTarget(LevelReader level, BlockPos pos) {
         BlockState state = level.getBlockState(pos);
         return state.is((Block)ESBlocks.PUNGENCY_FRUIT_VINES.get()) && (Integer)state.getValue(PungencyFruitVinesBlock.AGE) == 7;
      }
   }

   private class DryRottenFleshGoal extends MoveToBlockGoal {
      public DryRottenFleshGoal() {
         super(Stranghoul.this, 1.0, 20, 15);
      }

      public boolean canUse() {
         return !Stranghoul.this.isHired() && ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)
            ? Stranghoul.this.getOffhandItem().is(Items.ROTTEN_FLESH) && !Stranghoul.this.isBartering() && super.canUse()
            : false;
      }

      public boolean canContinueToUse() {
         return Stranghoul.this.getOffhandItem().is(Items.ROTTEN_FLESH) && super.canContinueToUse();
      }

      public void tick() {
         super.tick();
         if (this.isReachedTarget()) {
            Level level = Stranghoul.this.level();
            if (this.isValidTarget(level, this.blockPos)) {
               if (level.getBlockEntity(this.blockPos) instanceof DryingRackBlockEntity entity) {
                  entity.setItem(Stranghoul.this.getOffhandItem().copyWithCount(1));
                  Stranghoul.this.getOffhandItem().consume(1, Stranghoul.this);
               }

               Stranghoul.this.swing(InteractionHand.OFF_HAND);
               Stranghoul.this.addHappyParticles();
            }
         }
      }

      public double acceptedDistance() {
         return 2.5;
      }

      protected boolean isValidTarget(LevelReader level, BlockPos pos) {
         return level.getBlockState(pos).is((Block)ESBlocks.DRYING_RACK.get())
            && level.getBlockEntity(pos) instanceof DryingRackBlockEntity entity
            && (entity.getItem().isEmpty() || entity.getItem().is(ESItems.ROTTEN_FLESH_JERKY.get()) || entity.getItem().is(Items.LEATHER));
      }
   }

   private class EatGoal extends Goal {
      private int eatTicks = 0;

      public void start() {
         this.eatTicks = 0;
         if (!Stranghoul.this.getOffhandItem().has(DataComponents.FOOD)) {
            BuiltInRegistries.ITEM.getRandomElementOf(ESTags.Items.STRANGHOUL_FOOD, Stranghoul.this.random).ifPresent(item -> {
               Stranghoul.this.spawnAtLocation(Stranghoul.this.getOffhandItem());
               Stranghoul.this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(item));
            });
         }

         if (Stranghoul.this.getOffhandItem().has(DataComponents.FOOD)) {
            Stranghoul.this.setEating(true);
         }
      }

      public void tick() {
         this.eatTicks++;
         ItemStack food = Stranghoul.this.getOffhandItem();
         if (this.eatTicks == 32) {
            FoodProperties foodProperties = (FoodProperties)food.get(DataComponents.FOOD);
            if (foodProperties != null) {
               Stranghoul.this.heal(foodProperties.nutrition() + foodProperties.saturation() * 3.0F);
            }

            Stranghoul.this.eat(Stranghoul.this.level(), food);
         }

         if (this.eatTicks % 4 == 0 && Stranghoul.this.level() instanceof ServerLevel serverLevel) {
            if (!food.isEmpty()) {
               for (int i = 0; i < (this.eatTicks == 32 ? 16 : 5); i++) {
                  Vec3 vec3 = new Vec3((Stranghoul.this.random.nextFloat() - 0.5F) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                  vec3 = vec3.xRot((float)(-Stranghoul.this.getXRot() * (Math.PI / 180.0)));
                  vec3 = vec3.yRot((float)(-Stranghoul.this.getYHeadRot() * (Math.PI / 180.0)));
                  double d0 = -Stranghoul.this.random.nextFloat() * 0.6 - 0.3;
                  Vec3 vec31 = new Vec3((Stranghoul.this.random.nextFloat() - 0.5F) * 0.3, d0, 0.6);
                  vec31 = vec31.xRot((float)(-Stranghoul.this.getXRot() * (Math.PI / 180.0)));
                  vec31 = vec31.yRot((float)(-Stranghoul.this.getYHeadRot() * (Math.PI / 180.0)));
                  vec31 = vec31.add(Stranghoul.this.getX(), Stranghoul.this.getEyeY(), Stranghoul.this.getZ());
                  ESPlatform.INSTANCE
                     .sendToAllClients(
                        serverLevel,
                        new ParticlePacket(new ItemParticleOption(ParticleTypes.ITEM, food), vec31.x, vec31.y, vec31.z, vec3.x, vec3.y + 0.05, vec3.z)
                     );
               }
            }

            Stranghoul.this.playSound(
               Stranghoul.this.getEatingSound(food),
               0.5F + 0.5F * Stranghoul.this.random.nextInt(2),
               (Stranghoul.this.random.nextFloat() - Stranghoul.this.random.nextFloat()) * 0.2F + 1.0F
            );
         }
      }

      public void stop() {
         Stranghoul.this.setEating(false);
         if (Stranghoul.this.hiredEatAnim) {
            Stranghoul.this.hiredEatAnim = false;
            Stranghoul.this.addAlliedParticles();
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }

      public boolean canUse() {
         return Stranghoul.this.hiredEatAnim
            ? true
            : Stranghoul.this.random.nextInt(reducedTickDelay(80)) == 0
               && (
                  Stranghoul.this.getHealth() / Stranghoul.this.getMaxHealth() < 0.4F
                     || Stranghoul.this.getOffhandItem().has(DataComponents.FOOD) && Stranghoul.this.getHealth() < Stranghoul.this.getMaxHealth()
               );
      }

      public boolean canContinueToUse() {
         return Stranghoul.this.getOffhandItem().has(DataComponents.FOOD) && this.eatTicks <= 32;
      }
   }

   private class FollowHirerGoal extends Goal {
      @Nullable
      private LivingEntity hirer;
      private final PathNavigation navigation = Stranghoul.this.getNavigation();
      private int timeToRecalcPath;
      private float oldWaterCost;

      public FollowHirerGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         LivingEntity livingEntity = Stranghoul.this.getHirer();
         if (livingEntity == null) {
            return false;
         }

         if (Stranghoul.this.unableToMoveToHirer()) {
            return false;
         }

         if (Stranghoul.this.distanceToSqr(livingEntity) < 100.0) {
            return false;
         }

         this.hirer = livingEntity;
         return true;
      }

      public boolean canContinueToUse() {
         if (this.navigation.isDone()) {
            return false;
         } else if (this.hirer == null) {
            return false;
         } else {
            return Stranghoul.this.unableToMoveToHirer() ? false : !(Stranghoul.this.distanceToSqr(this.hirer) <= 4.0);
         }
      }

      public void start() {
         this.timeToRecalcPath = 0;
         this.oldWaterCost = Stranghoul.this.getPathfindingMalus(PathType.WATER);
         Stranghoul.this.setPathfindingMalus(PathType.WATER, 0.0F);
      }

      public void stop() {
         this.hirer = null;
         this.navigation.stop();
         Stranghoul.this.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
      }

      public void tick() {
         boolean shouldTeleport = this.hirer != null && Stranghoul.this.distanceToSqr(this.hirer) >= 144.0;
         if (!shouldTeleport && this.hirer != null) {
            Stranghoul.this.getLookControl().setLookAt(this.hirer, 10.0F, Stranghoul.this.getMaxHeadXRot());
         }

         if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            if (shouldTeleport) {
               Stranghoul.this.tryToTeleportToHirer();
            } else if (this.hirer != null) {
               this.navigation.moveTo(this.hirer, 1.0);
            }
         }
      }
   }

   private class GoHomeGoal extends Goal {
      private int tryTicks = 0;

      public GoHomeGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         return Stranghoul.this.getRandom().nextInt(reducedTickDelay(120)) == 0
            && Stranghoul.this.homePos.dimension() == Stranghoul.this.level().dimension()
            && !Stranghoul.this.blockPosition().closerThan(Stranghoul.this.homePos.pos(), 20.0);
      }

      public boolean canContinueToUse() {
         return Stranghoul.this.homePos.dimension() == Stranghoul.this.level().dimension()
            && !Stranghoul.this.blockPosition().closerThan(Stranghoul.this.homePos.pos(), 10.0)
            && this.tryTicks < 600;
      }

      public void stop() {
         this.tryTicks = 0;
      }

      public void tick() {
         this.tryTicks++;
         Stranghoul.this.getNavigation()
            .moveTo(Stranghoul.this.homePos.pos().getX() + 0.5, Stranghoul.this.homePos.pos().getY() + 0.5, Stranghoul.this.homePos.pos().getZ() + 0.5, 1.0);
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   private class GoToWantedItemGoal extends Goal {
      private ItemEntity wantedItem;

      public GoToWantedItemGoal() {
         this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
      }

      public boolean canUse() {
         if (Stranghoul.this.isBaby()) {
            return false;
         }

         if (Stranghoul.this.getRandom().nextInt(reducedTickDelay(80)) == 0 && !Stranghoul.this.getOffhandItem().is(ESTags.Items.STRANGHOUL_CURRENCIES)) {
            List<ItemEntity> items = Stranghoul.this.level()
               .getEntitiesOfClass(ItemEntity.class, Stranghoul.this.getBoundingBox().inflate(15.0))
               .stream()
               .filter(i -> i.getItem().is(ESTags.Items.STRANGHOUL_CURRENCIES) && Stranghoul.this.hasLineOfSight(i))
               .sorted(Comparator.comparingDouble(i -> i.distanceTo(Stranghoul.this)))
               .toList();
            if (!items.isEmpty()) {
               this.wantedItem = items.getFirst();
               return true;
            }
         }

         return false;
      }

      public boolean canContinueToUse() {
         return this.wantedItem != null && this.wantedItem.isAlive();
      }

      public void stop() {
         this.wantedItem = null;
      }

      public void tick() {
         if (this.wantedItem != null) {
            Stranghoul.this.getLookControl().setLookAt(this.wantedItem, 10.0F, Stranghoul.this.getMaxHeadXRot());
            Stranghoul.this.getNavigation().moveTo(this.wantedItem, 1.0);
         }
      }

      public boolean requiresUpdateEveryTick() {
         return true;
      }
   }

   private class HirerHurtByTargetGoal extends TargetGoal {
      private LivingEntity hirerLastHurtBy;
      private int timestamp;

      public HirerHurtByTargetGoal() {
         super(Stranghoul.this, false);
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (Stranghoul.this.isHired()) {
            LivingEntity living = Stranghoul.this.getHirer();
            if (living == null) {
               return false;
            }

            this.hirerLastHurtBy = living.getLastHurtByMob();
            int i = living.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.hirerLastHurtBy, TargetingConditions.DEFAULT);
         } else {
            return false;
         }
      }

      public void start() {
         this.mob.setTarget(this.hirerLastHurtBy);
         LivingEntity living = Stranghoul.this.getHirer();
         if (living != null) {
            this.timestamp = living.getLastHurtByMobTimestamp();
         }

         super.start();
      }
   }

   private class HirerHurtTargetGoal extends TargetGoal {
      private LivingEntity hirerLastHurt;
      private int timestamp;

      public HirerHurtTargetGoal() {
         super(Stranghoul.this, false);
         this.setFlags(EnumSet.of(Flag.TARGET));
      }

      public boolean canUse() {
         if (Stranghoul.this.isHired()) {
            LivingEntity livingEntity = Stranghoul.this.getHirer();
            if (livingEntity == null) {
               return false;
            }

            this.hirerLastHurt = livingEntity.getLastHurtMob();
            int i = livingEntity.getLastHurtMobTimestamp();
            return i != this.timestamp && this.canAttack(this.hirerLastHurt, TargetingConditions.DEFAULT);
         } else {
            return false;
         }
      }

      public void start() {
         this.mob.setTarget(this.hirerLastHurt);
         LivingEntity livingEntity = Stranghoul.this.getHirer();
         if (livingEntity != null) {
            this.timestamp = livingEntity.getLastHurtMobTimestamp();
         }

         super.start();
      }
   }

   private class PlantCropGoal extends MoveToBlockGoal {
      public PlantCropGoal() {
         super(Stranghoul.this, 1.0, 20, 15);
      }

      public boolean canUse() {
         return !Stranghoul.this.isHired() && ESPlatform.INSTANCE.canEntityGrief(Stranghoul.this.level(), Stranghoul.this)
            ? Stranghoul.this.getMainHandItem().is(ItemTags.HOES) && !Stranghoul.this.isBartering() && super.canUse()
            : false;
      }

      public void tick() {
         super.tick();
         if (this.isReachedTarget()) {
            Level level = Stranghoul.this.level();
            BlockPos cropPos = this.blockPos.above();
            BlockState blockState = level.getBlockState(cropPos);
            if (blockState.isAir()) {
               level.setBlockAndUpdate(cropPos, ((PungencyFruitVinesBlock)ESBlocks.PUNGENCY_FRUIT_VINES.get()).defaultBlockState());
               level.gameEvent(GameEvent.BLOCK_CHANGE, cropPos, Context.of(Stranghoul.this));
               Stranghoul.this.swing(InteractionHand.MAIN_HAND);
               Stranghoul.this.addHappyParticles();
            }
         }
      }

      public double acceptedDistance() {
         return 2.5;
      }

      protected boolean isValidTarget(LevelReader level, BlockPos pos) {
         BlockState state = level.getBlockState(pos);
         return ((PungencyFruitVinesBlock)ESBlocks.PUNGENCY_FRUIT_VINES.get()).canPlaceSeeds(state, level, pos) && level.getBlockState(pos.above()).isAir();
      }
   }

   private class SeedsLauncherAttackGoal extends RangedAttackGoal {
      public SeedsLauncherAttackGoal() {
         super(Stranghoul.this, 1.0, 30, 5.0F);
      }

      public boolean canUse() {
         return super.canUse() && Stranghoul.this.getMainHandItem().getItem() instanceof SeedsLauncherItem;
      }

      public void start() {
         super.start();
         Stranghoul.this.setAggressive(true);
      }

      public void stop() {
         super.stop();
         Stranghoul.this.setAggressive(false);
      }
   }

   private class SpearAttackGoal extends RangedAttackGoal {
      public SpearAttackGoal() {
         super(Stranghoul.this, 1.0, 40, 10.0F);
      }

      public boolean canUse() {
         return super.canUse() && Stranghoul.this.getMainHandItem().getItem() instanceof SpearItem;
      }

      public void start() {
         super.start();
         Stranghoul.this.setAggressive(true);
         Stranghoul.this.startUsingItem(InteractionHand.MAIN_HAND);
      }

      public void stop() {
         super.stop();
         Stranghoul.this.stopUsingItem();
         Stranghoul.this.setAggressive(false);
      }
   }
}
