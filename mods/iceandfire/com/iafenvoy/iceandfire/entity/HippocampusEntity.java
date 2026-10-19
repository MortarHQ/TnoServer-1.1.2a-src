package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.ai.AquaticAIFindWaterTargetGoal;
import com.iafenvoy.iceandfire.entity.ai.AquaticAIGetInWaterGoal;
import com.iafenvoy.iceandfire.entity.ai.HippocampusAIWanderGoal;
import com.iafenvoy.iceandfire.entity.util.ChainBuffer;
import com.iafenvoy.iceandfire.entity.util.ICustomMoveController;
import com.iafenvoy.iceandfire.entity.util.ISyncMount;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafSounds;
import com.iafenvoy.iceandfire.registry.tag.IafItemTags;
import com.iafenvoy.iceandfire.screen.handler.HippocampusScreenHandler;
import com.iafenvoy.uranus.animation.Animation;
import com.iafenvoy.uranus.animation.AnimationHandler;
import com.iafenvoy.uranus.animation.IAnimatedEntity;
import com.iafenvoy.uranus.object.RegistryHelper;
import dev.architectury.registry.menu.ExtendedMenuProvider;
import dev.architectury.registry.menu.MenuRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class HippocampusEntity
   extends TamableAnimal
   implements ExtendedMenuProvider,
   ISyncMount,
   IAnimatedEntity,
   ICustomMoveController,
   ContainerListener,
   Saddleable {
   public static final int INV_SLOT_SADDLE = 0;
   public static final int INV_SLOT_CHEST = 1;
   public static final int INV_SLOT_ARMOR = 2;
   public static final int INV_BASE_COUNT = 3;
   private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(HippocampusEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> SADDLE = SynchedEntityData.defineId(HippocampusEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Integer> ARMOR = SynchedEntityData.defineId(HippocampusEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> CHESTED = SynchedEntityData.defineId(HippocampusEntity.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Byte> CONTROL_STATE = SynchedEntityData.defineId(HippocampusEntity.class, EntityDataSerializers.BYTE);
   public static Animation ANIMATION_SPEAK;
   public float onLandProgress;
   public ChainBuffer tail_buffer;
   public SimpleContainer inventory;
   public float sitProgress;
   private int animationTick;
   private Animation currentAnimation;

   public HippocampusEntity(EntityType<? extends HippocampusEntity> entityType, Level worldIn) {
      super(entityType, worldIn);
      ANIMATION_SPEAK = Animation.create(15);
      this.setPathfindingMalus(PathType.WATER, 0.0F);
      this.moveControl = new HippocampusEntity.HippoMoveControl(this);
      if (worldIn.isClientSide) {
         this.tail_buffer = new ChainBuffer();
      }

      this.createInventory();
   }

   public static int getIntFromArmor(ItemStack stack) {
      if (!stack.isEmpty() && stack.getItem() == Items.IRON_HORSE_ARMOR) {
         return 1;
      } else if (!stack.isEmpty() && stack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
         return 2;
      } else {
         return !stack.isEmpty() && stack.getItem() == Items.DIAMOND_HORSE_ARMOR ? 3 : 0;
      }
   }

   public static Builder bakeAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, 40.0)
         .add(Attributes.MOVEMENT_SPEED, 0.3)
         .add(Attributes.ATTACK_DAMAGE, 1.0)
         .add(Attributes.STEP_HEIGHT, 1.0);
   }

   protected PathNavigation createNavigation(Level level) {
      return new AmphibiousPathNavigation(this, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new AquaticAIFindWaterTargetGoal(this));
      this.goalSelector.addGoal(2, new AquaticAIGetInWaterGoal(this, 1.0));
      this.goalSelector.addGoal(3, new HippocampusAIWanderGoal(this, 1.0));
      this.goalSelector.addGoal(4, new BreedGoal(this, 1.0));
      this.addBehaviourGoals();
   }

   protected void addBehaviourGoals() {
      this.goalSelector.addGoal(0, new TemptGoal(this, 1.0, Ingredient.of(IafItemTags.TEMPT_HIPPOCAMPUS), false));
   }

   public int getBaseExperienceReward() {
      return 2;
   }

   public float getWalkTargetValue(BlockPos pos) {
      return this.level().getBlockState(pos.below()).is(Blocks.WATER) ? 10.0F : this.level().getMaxLocalRawBrightness(pos) - 0.5F;
   }

   public boolean isAlliedTo(Entity entityIn) {
      if (this.isTame()) {
         LivingEntity livingentity = this.getOwner();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal tameable) {
            return tameable.isOwnedBy(livingentity);
         }

         if (livingentity != null) {
            return livingentity.isAlliedTo(entityIn);
         }
      }

      return super.isAlliedTo(entityIn);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, 0);
      builder.define(ARMOR, 0);
      builder.define(SADDLE, Boolean.FALSE);
      builder.define(CHESTED, Boolean.FALSE);
      builder.define(CONTROL_STATE, (byte)0);
   }

   public LivingEntity getControllingPassenger() {
      if (this.getFirstPassenger() instanceof Mob mob) {
         return mob;
      } else {
         return this.isSaddled() && this.getFirstPassenger() instanceof Player player ? player : null;
      }
   }

   public ItemStack equipItemIfPossible(ItemStack itemStackIn) {
      if (itemStackIn == null) {
         return ItemStack.EMPTY;
      } else {
         EquipmentSlot equipmentSlot = this.getEquipmentSlotForItem(itemStackIn);
         int j = equipmentSlot.getIndex() - 500 + 2;
         if (j >= 0 && j < this.inventory.getContainerSize()) {
            this.inventory.setItem(j, itemStackIn);
            return itemStackIn;
         } else {
            return ItemStack.EMPTY;
         }
      }
   }

   protected void dropEquipment() {
      super.dropEquipment();
      if (this.inventory != null && !this.level().isClientSide) {
         for (int i = 0; i < this.inventory.getContainerSize(); i++) {
            ItemStack itemstack = this.inventory.getItem(i);
            if (!itemstack.isEmpty()
               && EnchantmentHelper.getItemEnchantmentLevel(
                     RegistryHelper.getEnchantment(this.level().registryAccess(), Enchantments.VANISHING_CURSE), itemstack
                  )
                  == 0) {
               this.spawnAtLocation(itemstack);
            }
         }
      }

      if (this.isChested()) {
         if (!this.level().isClientSide) {
            this.spawnAtLocation(Blocks.CHEST);
         }

         this.setChested(false);
      }
   }

   protected void dropChestItems() {
      for (int i = 3; i < 18; i++) {
         if (!this.inventory.getItem(i).isEmpty()) {
            if (!this.level().isClientSide) {
               this.spawnAtLocation(this.inventory.getItem(i), 1.0F);
            }

            this.inventory.removeItemNoUpdate(i);
         }
      }
   }

   private void updateControlState(int i, boolean newState) {
      byte prevState = (Byte)this.entityData.get(CONTROL_STATE);
      if (newState) {
         this.entityData.set(CONTROL_STATE, (byte)(prevState | 1 << i));
      } else {
         this.entityData.set(CONTROL_STATE, (byte)(prevState & ~(1 << i)));
      }
   }

   @Override
   public byte getControlState() {
      return (Byte)this.entityData.get(CONTROL_STATE);
   }

   @Override
   public void setControlState(byte state) {
      this.entityData.set(CONTROL_STATE, state);
   }

   public boolean canRide(Entity rider) {
      return true;
   }

   public void positionRider(Entity passenger, MoveFunction callback) {
      super.positionRider(passenger, callback);
      if (this.hasPassenger(passenger)) {
         this.yBodyRot = this.getYRot();
         this.setYBodyRot(passenger.getYRot());
      }

      double ymod1 = this.onLandProgress * -0.02;
      passenger.setPos(this.getX(), this.getY() + 0.6F + ymod1, this.getZ());
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide && this.random.nextInt(900) == 0 && this.deathTime == 0) {
         this.heal(1.0F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.getControllingPassenger() != null && this.tickCount % 20 == 0) {
         this.getControllingPassenger().addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 30, 0, true, false));
      }

      if (this.level().isClientSide) {
         this.tail_buffer.calculateChainSwingBuffer(40.0F, 10, 1.0F, this);
      }

      boolean inWater = this.isInWater();
      if (!inWater && this.onLandProgress < 20.0F) {
         this.onLandProgress++;
      } else if (inWater && this.onLandProgress > 0.0F) {
         this.onLandProgress--;
      }

      boolean sitting = this.isOrderedToSit();
      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }
   }

   protected void tickRidden(Player player, Vec3 travelVector) {
      super.tickRidden(player, travelVector);
      Vec2 vec2 = this.getRiddenRotation(player);
      this.setRot(vec2.y, vec2.x);
      this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
      if (this.isControlledByLocalInstance()) {
         Vec3 vec3 = this.getDeltaMovement();
         if (this.isGoingUp()) {
            if (!this.isInWater() && this.onGround()) {
               this.jumpFromGround();
            } else if (this.isInWater()) {
               this.setDeltaMovement(vec3.add(0.0, 0.04F, 0.0));
            }
         }

         if (this.isGoingDown() && this.isInWater()) {
            this.setDeltaMovement(vec3.add(0.0, -0.025F, 0.0));
         }
      }
   }

   protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
      float f = player.xxa * 0.5F;
      float f1 = player.zza;
      if (f1 <= 0.0F) {
         f1 *= 0.25F;
      }

      return new Vec3(f, 0.0, f1);
   }

   protected Vec2 getRiddenRotation(LivingEntity entity) {
      return new Vec2(entity.getXRot() * 0.5F, entity.getYRot());
   }

   protected float getRiddenSpeed(Player player) {
      float speed = (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.6F;
      if (this.isInWater()) {
         speed *= ((Double)IafCommonConfig.INSTANCE.hippocampus.swimSpeedMod.getValue()).floatValue();
      } else {
         speed *= 0.2F;
      }

      return speed;
   }

   public boolean isGoingUp() {
      return ((Byte)this.entityData.get(CONTROL_STATE) & 1) == 1;
   }

   public boolean isGoingDown() {
      return ((Byte)this.entityData.get(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean isBlinking() {
      return this.tickCount % 50 > 43;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("Variant", this.getVariant());
      compound.putBoolean("Chested", this.isChested());
      compound.putBoolean("Saddled", this.isSaddled());
      compound.putInt("Armor", this.getArmorValue());
      compound.put(
         "Items",
         (Tag)ItemStack.OPTIONAL_CODEC
            .listOf()
            .encodeStart(RegistryOps.create(NbtOps.INSTANCE, this.level().registryAccess()), this.inventory.getItems())
            .resultOrPartial(IceAndFire.LOGGER::error)
            .orElse(new ListTag())
      );
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setVariant(compound.getInt("Variant"));
      this.setChested(compound.getBoolean("Chested"));
      this.setSaddled(compound.getBoolean("Saddled"));
      this.setArmor(compound.getInt("Armor"));
      this.createInventory();
      List<ItemStack> stacks = ItemStack.OPTIONAL_CODEC
         .listOf()
         .parse(RegistryOps.create(NbtOps.INSTANCE, this.level().registryAccess()), compound.get("Items"))
         .resultOrPartial(IceAndFire.LOGGER::error)
         .orElse(List.of());
      if (this.inventory != null) {
         for (int i = 0; i < stacks.size() && i < this.inventory.getContainerSize(); i++) {
            this.inventory.setItem(i, stacks.get(i));
         }
      }
   }

   protected int getInventorySize() {
      return this.isChested() ? 18 : 3;
   }

   protected void createInventory() {
      SimpleContainer simplecontainer = this.inventory;
      this.inventory = new SimpleContainer(this.getInventorySize());
      if (simplecontainer != null) {
         simplecontainer.removeListener(this);
         int i = Math.min(simplecontainer.getContainerSize(), this.inventory.getContainerSize());

         for (int j = 0; j < i; j++) {
            ItemStack itemstack = simplecontainer.getItem(j);
            if (!itemstack.isEmpty()) {
               this.inventory.setItem(j, itemstack.copy());
            }
         }
      }

      this.inventory.addListener(this);
      this.updateContainerEquipment();
   }

   protected void updateContainerEquipment() {
      if (!this.level().isClientSide) {
         this.setSaddled(!this.inventory.getItem(0).isEmpty());
         this.setChested(!this.inventory.getItem(1).isEmpty());
         this.setArmor(getIntFromArmor(this.inventory.getItem(2)));
      }
   }

   public boolean hasInventoryChanged(Container pInventory) {
      return this.inventory != pInventory;
   }

   public boolean isSaddleable() {
      return this.isAlive() && !this.isBaby() && this.isTame();
   }

   public void equipSaddle(ItemStack stack, @Nullable SoundSource soundCategory) {
      this.inventory.setItem(0, new ItemStack(Items.SADDLE));
   }

   public boolean isSaddled() {
      return (Boolean)this.entityData.get(SADDLE);
   }

   public void setSaddled(boolean saddle) {
      this.entityData.set(SADDLE, saddle);
   }

   public boolean isChested() {
      return (Boolean)this.entityData.get(CHESTED);
   }

   public void setChested(boolean chested) {
      this.entityData.set(CHESTED, chested);
      if (!chested) {
         this.dropChestItems();
      }
   }

   public int getArmorValue() {
      return (Integer)this.entityData.get(ARMOR);
   }

   public void setArmor(int armorType) {
      this.entityData.set(ARMOR, armorType);

      double armorValue = switch (armorType) {
         case 1 -> 10.0;
         case 2 -> 20.0;
         case 3 -> 30.0;
         default -> 0.0;
      };
      this.getAttribute(Attributes.ARMOR).setBaseValue(armorValue);
   }

   public int getVariant() {
      return (Integer)this.entityData.get(VARIANT);
   }

   public void setVariant(int variant) {
      this.entityData.set(VARIANT, variant);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, SpawnGroupData spawnDataIn) {
      SpawnGroupData data = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn);
      this.setVariant(this.getRandom().nextInt(6));
      return data;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, ANIMATION_SPEAK};
   }

   public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
      if (ageable instanceof HippocampusEntity) {
         HippocampusEntity hippo = new HippocampusEntity((EntityType<? extends HippocampusEntity>)IafEntities.HIPPOCAMPUS.get(), this.level());
         hippo.setVariant(this.getRandom().nextBoolean() ? this.getVariant() : ((HippocampusEntity)ageable).getVariant());
         return hippo;
      } else {
         return null;
      }
   }

   public void travel(Vec3 pTravelVector) {
      if (this.isControlledByLocalInstance() && this.isInWater()) {
         this.moveRelative(0.1F, pTravelVector);
         this.move(MoverType.SELF, this.getDeltaMovement());
         this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
      } else {
         super.travel(pTravelVector);
      }
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(IafItemTags.BREED_HIPPOCAMPUS);
   }

   public void playAmbientSound() {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playAmbientSound();
   }

   protected void playHurtSound(DamageSource source) {
      if (this.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.playHurtSound(source);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemstack = player.getItemInHand(hand);
      if (itemstack.is(IafItemTags.BREED_HIPPOCAMPUS) && this.getAge() == 0 && !this.isInLove()) {
         this.setOrderedToSit(false);
         this.setInLove(player);
         this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
         if (!player.isCreative()) {
            itemstack.shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else if (!itemstack.is(IafItemTags.HEAL_HIPPOCAMPUS)) {
         if (this.isOwnedBy(player) && itemstack.getItem() == Items.STICK) {
            this.setOrderedToSit(!this.isOrderedToSit());
            return InteractionResult.SUCCESS;
         }

         if (this.isOwnedBy(player) && itemstack.isEmpty() && player.isShiftKeyDown()) {
            if (player instanceof ServerPlayer serverPlayer) {
               MenuRegistry.openExtendedMenu(serverPlayer, this);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide);
         } else if (this.isOwnedBy(player) && this.isSaddled() && !this.isBaby() && !player.isPassenger()) {
            this.doPlayerRide(player);
            return InteractionResult.SUCCESS;
         } else {
            return super.mobInteract(player, hand);
         }
      } else {
         if (!this.level().isClientSide) {
            this.heal(5.0F);
            this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);

            for (int i = 0; i < 3; i++) {
               this.level()
                  .addParticle(
                     new ItemParticleOption(ParticleTypes.ITEM, itemstack),
                     this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.getY() + this.random.nextFloat() * this.getBbHeight(),
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (!player.isCreative()) {
               itemstack.shrink(1);
            }
         }

         if (!this.isTame() && this.getRandom().nextInt(3) == 0) {
            this.tame(player);

            for (int i = 0; i < 6; i++) {
               this.level()
                  .addParticle(
                     ParticleTypes.HEART,
                     this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     this.getY() + this.random.nextFloat() * this.getBbHeight(),
                     this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth(),
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   protected void doPlayerRide(Player pPlayer) {
      this.setOrderedToSit(false);
      if (!this.level().isClientSide) {
         pPlayer.setYRot(this.getYRot());
         pPlayer.setXRot(this.getXRot());
         pPlayer.startRiding(this);
      }
   }

   @Nullable
   public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
      return new HippocampusScreenHandler(syncId, this.inventory, inv, this);
   }

   public void saveExtraData(FriendlyByteBuf buf) {
      buf.writeInt(this.getId());
   }

   @Override
   public void up(boolean up) {
      this.updateControlState(0, up);
   }

   @Override
   public void down(boolean down) {
      this.updateControlState(1, down);
   }

   @Override
   public void attack(boolean attack) {
   }

   @Override
   public void strike(boolean strike) {
   }

   @Override
   public void dismount(boolean dismount) {
      this.updateControlState(2, dismount);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)IafSounds.HIPPOCAMPUS_IDLE.get();
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return (SoundEvent)IafSounds.HIPPOCAMPUS_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)IafSounds.HIPPOCAMPUS_DIE.get();
   }

   public boolean isPersistenceRequired() {
      return true;
   }

   public Player getRidingPlayer() {
      return this.getControllingPassenger() instanceof Player player ? player : null;
   }

   public int getInventoryColumns() {
      return 5;
   }

   public void containerChanged(Container pInvBasic) {
      boolean flag = this.isSaddled();
      this.updateContainerEquipment();
      if (this.tickCount > 20 && !flag && this.isSaddled()) {
         this.playSound(SoundEvents.HORSE_SADDLE, 0.5F, 1.0F);
      }
   }

   public void tick() {
      super.tick();
      this.setAirSupply(this.getMaxAirSupply());
   }

   class HippoMoveControl extends MoveControl {
      private final HippocampusEntity hippo = HippocampusEntity.this;

      public HippoMoveControl(HippocampusEntity hippocampusEntity) {
         super(hippocampusEntity);
      }

      private void updateSpeed() {
         if (this.hippo.isInWater()) {
            this.hippo.setDeltaMovement(this.hippo.getDeltaMovement().add(0.0, 0.005, 0.0));
         } else if (this.hippo.onGround()) {
            this.hippo.setSpeed(Math.max(this.hippo.getSpeed() / 4.0F, 0.06F));
         }
      }

      public void tick() {
         this.updateSpeed();
         if (this.operation == Operation.MOVE_TO && !this.hippo.getNavigation().isDone()) {
            double d0 = this.wantedX - this.hippo.getX();
            double d1 = this.wantedY - this.hippo.getY();
            double d2 = this.wantedZ - this.hippo.getZ();
            double distance = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            if (distance < 1.0E-5F) {
               this.mob.setSpeed(0.0F);
            } else {
               d1 /= distance;
               float minRotation = (float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F;
               this.hippo.setYRot(this.rotlerp(this.hippo.getYRot(), minRotation, 90.0F));
               this.hippo.yBodyRot = this.hippo.getYRot();
               float maxSpeed = (float)(this.speedModifier * this.hippo.getAttributeValue(Attributes.MOVEMENT_SPEED));
               maxSpeed *= 0.6F;
               if (this.hippo.isInWater()) {
                  maxSpeed *= ((Double)IafCommonConfig.INSTANCE.hippocampus.swimSpeedMod.getValue()).floatValue();
               } else {
                  maxSpeed *= 0.2F;
               }

               this.hippo.setSpeed(Mth.lerp(0.125F, this.hippo.getSpeed(), maxSpeed));
               this.hippo.setDeltaMovement(this.hippo.getDeltaMovement().add(0.0, this.hippo.getSpeed() * d1 * 0.1, 0.0));
            }
         } else {
            this.hippo.setSpeed(0.0F);
         }
      }
   }
}
