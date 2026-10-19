package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlacksmithEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Boolean> SHOW_HINT = SynchedEntityData.defineId(BlacksmithEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> PAYMENT_STATE = SynchedEntityData.defineId(BlacksmithEntity.class, EntityDataSerializers.INT);
   public int hintTimer = 0;
   private BlockPos targetAnvil = null;
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public BlacksmithEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.0).add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SHOW_HINT, false);
      builder.define(PAYMENT_STATE, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
   }

   public void knockback(double strength, double x, double z) {
   }

   public void push(double x, double y, double z) {
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (this.level().isClientSide()) {
         return InteractionResult.sidedSuccess(true);
      }

      ItemStack stack = player.getItemInHand(hand);
      int state = (Integer)this.entityData.get(PAYMENT_STATE);
      if (state == 0) {
         if (stack.is(Items.RAW_GOLD)) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            this.entityData.set(PAYMENT_STATE, 1);
            this.playSound(SoundEvents.VILLAGER_YES, 1.0F, this.getVoicePitch());
            return InteractionResult.sidedSuccess(false);
         }

         if (stack.is(Items.NETHERITE_SCRAP)) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            this.entityData.set(PAYMENT_STATE, 2);
            this.playSound(SoundEvents.VILLAGER_YES, 1.0F, this.getVoicePitch());
            return InteractionResult.sidedSuccess(false);
         }
      } else if (state == 1) {
         ItemStack result = this.getDisassemblyResult(stack);
         if (!result.isEmpty()) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            this.dropItemToPlayer(player, result);
            this.level().playSound(null, this.blockPosition(), SoundEvents.LAVA_EXTINGUISH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            this.level().playSound(null, this.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.5F, 1.0F);
            this.spawnFlameParticles();
            this.entityData.set(PAYMENT_STATE, 0);
            return InteractionResult.sidedSuccess(false);
         }
      } else if (state == 2 && stack.isDamageableItem() && !(stack.getItem() instanceof ArmorItem)) {
         ItemStack repaired = stack.copy();
         repaired.setDamageValue(0);
         if (!player.isCreative()) {
            stack.shrink(1);
         }

         this.dropItemToPlayer(player, repaired);
         this.level().playSound(null, this.blockPosition(), SoundEvents.VILLAGER_WORK_WEAPONSMITH, SoundSource.NEUTRAL, 1.0F, 1.0F);
         this.spawnFlameParticles();
         this.entityData.set(PAYMENT_STATE, 0);
         return InteractionResult.sidedSuccess(false);
      }

      this.entityData.set(SHOW_HINT, true);
      this.hintTimer = 80;
      this.playSound(SoundEvents.VILLAGER_NO, 1.0F, this.getVoicePitch());
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.5, this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
      }

      return InteractionResult.sidedSuccess(false);
   }

   private ItemStack getDisassemblyResult(ItemStack tool) {
      if (!(tool.getItem() instanceof TieredItem tieredItem)) {
         return ItemStack.EMPTY;
      } else {
         Tier tier = tieredItem.getTier();
         if (tier != Tiers.WOOD && tier != Tiers.STONE) {
            Item material = Items.IRON_INGOT;
            if (tier == Tiers.GOLD) {
               material = Items.GOLD_INGOT;
            } else if (tier == Tiers.DIAMOND) {
               material = Items.DIAMOND;
            } else if (tier == Tiers.NETHERITE) {
               material = Items.NETHERITE_INGOT;
            }

            int count = 1;
            Item item = tool.getItem();
            byte var8;
            if (item instanceof PickaxeItem || item instanceof AxeItem) {
               var8 = 3;
            } else if (!(item instanceof SwordItem) && !(item instanceof HoeItem)) {
               if (!(item instanceof ShovelItem)) {
                  return ItemStack.EMPTY;
               }

               var8 = 1;
            } else {
               var8 = 2;
            }

            return new ItemStack(material, var8);
         } else {
            return ItemStack.EMPTY;
         }
      }
   }

   private void dropItemToPlayer(Player player, ItemStack stack) {
      Vec3 dir = player.position().subtract(this.position()).normalize().scale(0.3);
      ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.0, this.getZ(), stack);
      itemEntity.setDeltaMovement(dir.x, 0.3, dir.z);
      this.level().addFreshEntity(itemEntity);
   }

   private void spawnFlameParticles() {
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(ParticleTypes.FLAME, this.getX(), this.getY() + 1.0, this.getZ(), 15, 0.3, 0.3, 0.3, 0.05);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         if (this.hintTimer > 0) {
            this.hintTimer--;
            if (this.hintTimer <= 0) {
               this.entityData.set(SHOW_HINT, false);
            }
         }

         if (this.tickCount % 20 == 0) {
            if (this.targetAnvil != null && !(this.level().getBlockState(this.targetAnvil).getBlock() instanceof AnvilBlock)) {
               this.targetAnvil = null;
            }

            if (this.targetAnvil == null) {
               for (BlockPos pos : BlockPos.betweenClosed(this.blockPosition().offset(-3, -1, -3), this.blockPosition().offset(3, 1, 3))) {
                  if (this.level().getBlockState(pos).getBlock() instanceof AnvilBlock) {
                     this.targetAnvil = pos;
                     break;
                  }
               }
            }
         }

         if (this.targetAnvil != null) {
            double dx = this.targetAnvil.getX() + 0.5 - this.getX();
            double dz = this.targetAnvil.getZ() + 0.5 - this.getZ();
            float targetYaw = (float)(Math.atan2(-dx, dz) * (180.0 / Math.PI));
            this.setYRot(targetYaw);
            this.setYHeadRot(targetYaw);
            this.yBodyRot = targetYaw;
         }
      }
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)ModSounds.BLACKSMITH_IDLE.get();
   }

   public int getAmbientSoundInterval() {
      return 400;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.PILLAGER_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.PILLAGER_DEATH;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("PaymentState", (Integer)this.entityData.get(PAYMENT_STATE));
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(PAYMENT_STATE, tag.getInt("PaymentState"));
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, event -> event.setAndContinue(IDLE_ANIM)));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
