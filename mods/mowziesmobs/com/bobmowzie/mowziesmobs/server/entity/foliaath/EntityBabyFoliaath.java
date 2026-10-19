package com.bobmowzie.mowziesmobs.server.entity.foliaath;

import com.bobmowzie.mowziesmobs.client.model.tools.ControlledAnimation;
import com.bobmowzie.mowziesmobs.server.ai.animation.AnimationBabyFoliaathEatAI;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.MowzieEntity;
import com.bobmowzie.mowziesmobs.server.entity.MowzieLLibraryEntity;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.ilexiconn.llibrary.server.animation.Animation;
import com.ilexiconn.llibrary.server.animation.AnimationHandler;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags.Items;
import org.jetbrains.annotations.NotNull;

public class EntityBabyFoliaath extends MowzieLLibraryEntity {
   private static final EntityDataAccessor<Integer> GROWTH = SynchedEntityData.defineId(EntityBabyFoliaath.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> INFANT = SynchedEntityData.defineId(EntityBabyFoliaath.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Boolean> HUNGRY = SynchedEntityData.defineId(EntityBabyFoliaath.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<ItemStack> EATING = SynchedEntityData.defineId(EntityBabyFoliaath.class, EntityDataSerializers.ITEM_STACK);
   public static final Animation EAT_ANIMATION = Animation.create(20);
   public ControlledAnimation activate = new ControlledAnimation(5);
   private double prevActivate;

   public EntityBabyFoliaath(EntityType<? extends EntityBabyFoliaath> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      super.registerGoals();
      this.goalSelector.addGoal(1, new AnimationBabyFoliaathEatAI<>(this, EAT_ANIMATION));
   }

   public static Builder createAttributes() {
      return MowzieEntity.createAttributes().add(Attributes.MAX_HEALTH, 1.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   protected boolean isMovementNoisy() {
      return false;
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public void push(double x, double y, double z) {
      super.push(0.0, y, 0.0);
   }

   protected void pushEntities() {
   }

   @Override
   public void tick() {
      super.tick();
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
      this.yBodyRot = 0.0F;
      if (this.arePlayersCarryingMeat(this.getPlayersNearby(3.0, 3.0, 3.0, 3.0)) && this.getAnimation() == NO_ANIMATION && this.getHungry()) {
         this.activate.increaseTimer();
      } else {
         this.activate.decreaseTimer();
      }

      if (this.activate.getTimer() == 1 && this.prevActivate - this.activate.getTimer() < 0.0) {
         this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_GRUNT.get(), 0.5F, 1.5F);
      }

      this.prevActivate = this.activate.getTimer();
      if (!this.level().isClientSide && this.getHungry() && this.getAnimation() == NO_ANIMATION) {
         for (ItemEntity meat : this.getMeatsNearby(0.4, 0.2, 0.4, 0.4)) {
            ItemStack stack = meat.getItem().split(1);
            if (!stack.isEmpty()) {
               this.setEating(stack);
               AnimationHandler.INSTANCE.sendAnimationMessage(this, EAT_ANIMATION);
               this.playSound((SoundEvent)MMSounds.ENTITY_FOLIAATH_BABY_EAT.get(), 0.5F, 1.2F);
               this.incrementGrowth();
               this.setHungry(false);
               break;
            }
         }
      }

      if (this.level().isClientSide
         && this.getAnimation() == EAT_ANIMATION
         && (
            this.getAnimationTick() == 3
               || this.getAnimationTick() == 7
               || this.getAnimationTick() == 11
               || this.getAnimationTick() == 15
               || this.getAnimationTick() == 19
         )) {
         for (int i = 0; i <= 5; i++) {
            this.level()
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, this.getEating()),
                  this.getX(),
                  this.getY() + 0.2,
                  this.getZ(),
                  this.random.nextFloat() * 0.2 - 0.1,
                  this.random.nextFloat() * 0.2,
                  this.random.nextFloat() * 0.2 - 0.1
               );
         }
      }

      if (!this.level().isClientSide) {
         if (this.tickCount % 20 == 0 && !this.getHungry()) {
            this.incrementGrowth();
         }

         this.setInfant(this.getGrowth() < 600);
         if (this.getInfant()) {
            this.setHungry(false);
         }

         if (this.getGrowth() == 600) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 1200) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 1800) {
            this.setHungry(true);
         }

         if (this.getGrowth() == 2400) {
            EntityFoliaath adultFoliaath = new EntityFoliaath((EntityType<? extends EntityFoliaath>)EntityHandler.FOLIAATH.get(), this.level());
            adultFoliaath.setPos(this.getX(), this.getY(), this.getZ());
            adultFoliaath.setCanDespawn(false);
            this.level().addFreshEntity(adultFoliaath);
            this.discard();
         }
      }
   }

   @Override
   public Animation getDeathAnimation() {
      return null;
   }

   @Override
   public Animation getHurtAnimation() {
      return null;
   }

   private boolean arePlayersCarryingMeat(List<Player> players) {
      if (!players.isEmpty()) {
         for (Player player : players) {
            ItemStack stack = player.getMainHandItem();
            if ((stack.is(Items.FOODS_RAW_MEAT) || stack.is(Items.FOODS_COOKED_MEAT)) && stack.getFoodProperties(null) != null) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public void die(DamageSource source) {
      super.die(source);

      for (int i = 0; i < 10; i++) {
         this.level()
            .addParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, Blocks.JUNGLE_LEAVES.defaultBlockState()),
               this.getX(),
               this.getY() + 0.2,
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
      }

      this.discard();
   }

   public boolean isPushable() {
      return false;
   }

   @Override
   public void push(Entity collider) {
      this.setDeltaMovement(0.0, this.getDeltaMovement().y, 0.0);
   }

   protected SoundEvent getDeathSound() {
      this.playSound(SoundEvents.GRASS_BREAK, 1.0F, 0.8F);
      return null;
   }

   public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
      if (world.isUnobstructed(this) && world.noCollision(this) && !world.containsAnyLiquid(this.getBoundingBox())) {
         BlockPos ground = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getBoundingBox().minY) - 1, Mth.floor(this.getZ()));
         BlockState block = world.getBlockState(ground);
         if (block.getBlock() == Blocks.GRASS_BLOCK || block.is(BlockTags.DIRT) || block.is(BlockTags.LEAVES)) {
            this.playSound(SoundEvents.GRASS_HIT, 1.0F, 0.8F);
            return true;
         }
      }

      return false;
   }

   public List<ItemEntity> getMeatsNearby(double distanceX, double distanceY, double distanceZ, double radius) {
      List<Entity> list = this.level().getEntities(this, this.getBoundingBox().inflate(distanceX, distanceY, distanceZ));
      ArrayList<ItemEntity> listEntityItem = new ArrayList<>();

      for (Entity entityNeighbor : list) {
         if (entityNeighbor instanceof ItemEntity itemEntity && this.distanceTo(entityNeighbor) <= radius) {
            ItemStack stack = itemEntity.getItem();
            if ((stack.is(Items.FOODS_RAW_MEAT) || stack.is(Items.FOODS_COOKED_MEAT)) && stack.getFoodProperties(null) != null) {
               listEntityItem.add(itemEntity);
            }
         }
      }

      return listEntityItem;
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("tickGrowth", this.getGrowth());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setGrowth(compound.getInt("tickGrowth"));
   }

   public boolean requiresCustomPersistence() {
      return true;
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(GROWTH, 0);
      builder.define(INFANT, true);
      builder.define(HUNGRY, false);
      builder.define(EATING, ItemStack.EMPTY);
   }

   public int getGrowth() {
      return (Integer)this.getEntityData().get(GROWTH);
   }

   public void setGrowth(int growth) {
      this.getEntityData().set(GROWTH, growth);
   }

   public void incrementGrowth() {
      this.setGrowth(this.getGrowth() + 1);
   }

   public boolean getInfant() {
      return (Boolean)this.getEntityData().get(INFANT);
   }

   public void setInfant(boolean infant) {
      this.getEntityData().set(INFANT, infant);
   }

   public boolean getHungry() {
      return (Boolean)this.getEntityData().get(HUNGRY);
   }

   public void setHungry(boolean hungry) {
      this.getEntityData().set(HUNGRY, hungry);
   }

   public void setEating(ItemStack stack) {
      this.getEntityData().set(EATING, stack);
   }

   public ItemStack getEating() {
      return (ItemStack)this.getEntityData().get(EATING);
   }

   @Override
   public Animation[] getAnimations() {
      return new Animation[]{EAT_ANIMATION};
   }
}
