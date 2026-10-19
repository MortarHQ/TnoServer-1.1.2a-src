package com.benji.netherman.common.entity;

import com.benji.netherman.NetherExp;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class TraderEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Boolean> SHOW_HINT = SynchedEntityData.defineId(TraderEntity.class, EntityDataSerializers.BOOLEAN);
   public static final EntityDataAccessor<Integer> TRADE_STATE = SynchedEntityData.defineId(TraderEntity.class, EntityDataSerializers.INT);
   public static final ResourceKey<LootTable> GOLD_TRADE_LOOT = ResourceKey.create(Registries.LOOT_TABLE, NetherExp.location("gameplay/trader_gold"));
   public static final ResourceKey<LootTable> DIAMOND_TRADE_LOOT = ResourceKey.create(Registries.LOOT_TABLE, NetherExp.location("gameplay/trader_diamond"));
   public static final ResourceKey<LootTable> NETHERITE_TRADE_LOOT = ResourceKey.create(Registries.LOOT_TABLE, NetherExp.location("gameplay/trader_netherite"));
   public int hintTicks = 0;
   public int tradeTimer = 0;
   private int pendingRewardTier = 0;
   private Player tradingPlayer = null;
   private static final RawAnimation TRADE_OPEN_ANIM = RawAnimation.begin().thenPlay("trade_open");
   private static final RawAnimation TRADE_CLOSE_ANIM = RawAnimation.begin().thenPlay("trade_close");
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public TraderEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SHOW_HINT, false);
      builder.define(TRADE_STATE, 0);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
   }

   private boolean isAcceptedItem(ItemStack stack) {
      return stack.is(Items.GOLD_INGOT) || stack.is(Items.DIAMOND) || stack.is(Items.NETHERITE_INGOT);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      if ((Integer)this.entityData.get(TRADE_STATE) != 0) {
         return InteractionResult.PASS;
      }

      if (this.isAcceptedItem(stack)) {
         if (stack.is(Items.GOLD_INGOT)) {
            this.pendingRewardTier = 1;
         } else if (stack.is(Items.DIAMOND)) {
            this.pendingRewardTier = 2;
         } else if (stack.is(Items.NETHERITE_INGOT)) {
            this.pendingRewardTier = 3;
         }

         if (!player.isCreative()) {
            stack.shrink(1);
         }

         this.tradingPlayer = player;
         this.entityData.set(TRADE_STATE, 1);
         this.tradeTimer = 80;
         this.getLookControl().setLookAt(player);
         this.playSound(SoundEvents.WANDERING_TRADER_TRADE, 1.0F, this.getVoicePitch());
         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         this.playSound(SoundEvents.VILLAGER_NO, 1.0F, this.getVoicePitch());
         this.hintTicks = 60;
         this.entityData.set(SHOW_HINT, true);
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.5, this.getZ(), 5, 0.3, 0.3, 0.3, 0.0);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         int tradeState = (Integer)this.entityData.get(TRADE_STATE);
         if (tradeState > 0) {
            this.tradeTimer--;
            if (tradeState == 1 && this.tradeTimer <= 0) {
               this.giveRewardToPlayer();
               this.playSound(SoundEvents.ITEM_PICKUP, 1.0F, 1.0F);
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 10, 0.3, 0.3, 0.3, 0.0);
               }

               this.entityData.set(TRADE_STATE, 2);
               this.tradeTimer = 80;
            } else if (tradeState == 2 && this.tradeTimer <= 0) {
               this.entityData.set(TRADE_STATE, 0);
               this.tradingPlayer = null;
            }
         } else if (this.hintTicks > 0) {
            this.hintTicks--;
            this.entityData.set(SHOW_HINT, true);
         } else if (this.tickCount % 10 == 0) {
            boolean holdingCurrency = false;

            for (Player p : this.level().getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(6.0))) {
               if (this.isAcceptedItem(p.getMainHandItem()) || this.isAcceptedItem(p.getOffhandItem())) {
                  holdingCurrency = true;
                  this.getLookControl().setLookAt(p);
                  break;
               }
            }

            this.entityData.set(SHOW_HINT, holdingCurrency);
         }
      }
   }

   private void giveRewardToPlayer() {
      if (this.tradingPlayer != null && this.level() instanceof ServerLevel serverLevel) {
         ResourceKey var10 = null;
         if (this.pendingRewardTier == 1) {
            var10 = GOLD_TRADE_LOOT;
         } else if (this.pendingRewardTier == 2) {
            var10 = DIAMOND_TRADE_LOOT;
         } else if (this.pendingRewardTier == 3) {
            var10 = NETHERITE_TRADE_LOOT;
         }

         if (var10 != null) {
            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(var10);
            LootParams lootParams = new net.minecraft.world.level.storage.loot.LootParams.Builder(serverLevel)
               .withParameter(LootContextParams.THIS_ENTITY, this)
               .withParameter(LootContextParams.ORIGIN, this.position())
               .create(LootContextParamSets.GIFT);

            for (ItemStack reward : lootTable.getRandomItems(lootParams)) {
               Vec3 dir = this.tradingPlayer.position().subtract(this.position()).normalize().scale(0.3);
               ItemEntity itemEntity = new ItemEntity(this.level(), this.getX(), this.getY() + 1.0, this.getZ(), reward);
               itemEntity.setDeltaMovement(dir.x, 0.3, dir.z);
               this.level().addFreshEntity(itemEntity);
            }
         }
      }
   }

   public void travel(Vec3 travelVector) {
      if ((Integer)this.entityData.get(TRADE_STATE) > 0) {
         this.getNavigation().stop();
         super.travel(Vec3.ZERO);
      } else {
         super.travel(travelVector);
      }
   }

   public float getVoicePitch() {
      return 0.8F + this.random.nextFloat() * 0.4F;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return SoundEvents.WANDERING_TRADER_AMBIENT;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.WANDERING_TRADER_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.WANDERING_TRADER_DEATH;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 5, event -> {
         int state = (Integer)this.entityData.get(TRADE_STATE);
         if (state == 1) {
            return event.setAndContinue(TRADE_OPEN_ANIM);
         } else if (state == 2) {
            return event.setAndContinue(TRADE_CLOSE_ANIM);
         } else {
            return event.isMoving() ? event.setAndContinue(WALK_ANIM) : event.setAndContinue(IDLE_ANIM);
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("TradeState", (Integer)this.entityData.get(TRADE_STATE));
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.entityData.set(TRADE_STATE, tag.getInt("TradeState"));
   }
}
