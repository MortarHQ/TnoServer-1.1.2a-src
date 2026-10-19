package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import com.mojang.serialization.Dynamic;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.Brain.Provider;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Yeti extends Animal {
   private static final String TAG_FUR = "fur";
   private static final String TAG_FUR_GROWTH_TICKS = "fur_growth_ticks";
   public static final CompoundTag NOT_SHEARED = (CompoundTag)Util.make(() -> {
      CompoundTag tag = new CompoundTag();
      tag.putBoolean("fur", true);
      return tag;
   });
   protected static final EntityDataAccessor<Integer> ROLL_STATE = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Boolean> HAS_FUR = SynchedEntityData.defineId(Yeti.class, EntityDataSerializers.BOOLEAN);
   public AnimationState idleAnimationState = new AnimationState();
   public AnimationState rollStartAnimationState = new AnimationState();
   public AnimationState rollAnimationState = new AnimationState();
   public AnimationState rollEndAnimationState = new AnimationState();
   private int rollTicks = 0;
   private int furGrowthTicks = 0;
   private int rollCooldown = 200;
   public float rollAngle;
   public float prevRollAngle;

   public int getRollState() {
      return (Integer)this.getEntityData().get(ROLL_STATE);
   }

   public void setRollState(int rollState) {
      this.getEntityData().set(ROLL_STATE, rollState);
   }

   public boolean hasFur() {
      return (Boolean)this.getEntityData().get(HAS_FUR);
   }

   public void setHasFur(boolean hasFur) {
      this.getEntityData().set(HAS_FUR, hasFur);
   }

   public void setRollTicks(int rollTicks) {
      this.rollTicks = rollTicks;
   }

   public int getRollTicks() {
      return this.rollTicks;
   }

   public void setRollCooldown(int rollCooldown) {
      this.rollCooldown = rollCooldown;
   }

   public int getRollCooldown() {
      return this.rollCooldown;
   }

   public Yeti(EntityType<? extends Yeti> entityType, Level level) {
      super(entityType, level);
      this.getNavigation().setCanFloat(true);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.yeti.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.yeti.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(ROLL_STATE, 0).define(HAS_FUR, true);
   }

   protected Brain<?> makeBrain(Dynamic<?> dynamic) {
      return YetiAi.makeBrain(this.brainProvider().makeBrain(dynamic));
   }

   public Brain<Yeti> getBrain() {
      return super.getBrain();
   }

   protected Provider<Yeti> brainProvider() {
      return Brain.provider(YetiAi.MEMORY_TYPES, YetiAi.SENSOR_TYPES);
   }

   protected void customServerAiStep() {
      this.level().getProfiler().push("YetiBrain");
      this.getBrain().tick((ServerLevel)this.level(), this);
      this.level().getProfiler().popPush("YetiActivityUpdate");
      YetiAi.updateActivity(this);
      this.level().getProfiler().pop();
      super.customServerAiStep();
   }

   public void aiStep() {
      super.aiStep();
      if (this.rollTicks > 0) {
         this.rollTicks--;
      }

      if (this.rollCooldown > 0) {
         this.rollCooldown--;
      }

      if (!this.level().isClientSide) {
         if (!this.hasFur()) {
            if (this.furGrowthTicks < 3600) {
               this.furGrowthTicks++;
            }

            if (this.furGrowthTicks >= 3600) {
               this.setHasFur(true);
               this.furGrowthTicks = 0;
            }
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
         this.prevRollAngle = this.rollAngle;
         this.rollAngle = this.rollAngle + (float)(this.position().subtract(new Vec3(this.xOld, this.yOld, this.zOld)).length() / 0.011538462F);
         this.rollAngle = Mth.wrapDegrees(this.rollAngle);
      }
   }

   public void stopAllAnimStates() {
      this.rollStartAnimationState.stop();
      this.rollAnimationState.stop();
      this.rollEndAnimationState.stop();
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
      super.onSyncedDataUpdated(accessor);
      if (accessor.equals(ROLL_STATE) && this.getRollState() != 0) {
         this.stopAllAnimStates();
         switch (this.getRollState()) {
            case 1:
               this.rollAngle = 0.0F;
               this.prevRollAngle = 0.0F;
               this.rollStartAnimationState.start(this.tickCount);
               break;
            case 2:
               this.rollAnimationState.start(this.tickCount);
               break;
            case 3:
               this.rollEndAnimationState.start(this.tickCount);
         }
      }
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      if (ESPlatform.INSTANCE.isShears(itemStack) && this.hasFur()) {
         if (!this.level().isClientSide) {
            this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
            this.gameEvent(GameEvent.SHEAR, player);
            itemStack.hurtAndBreak(1, player, getSlotForHand(hand));
            int i = 1 + this.getRandom().nextInt(3);

            for (int j = 0; j < i; j++) {
               ItemEntity itemEntity = this.spawnAtLocation((ItemLike)ESItems.WHITE_YETI_FUR.get(), 1);
               if (itemEntity != null) {
                  itemEntity.setDeltaMovement(
                     itemEntity.getDeltaMovement()
                        .add(
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F,
                           this.getRandom().nextFloat() * 0.05F,
                           (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F
                        )
                  );
               }
            }

            this.furGrowthTicks = this.getRandom().nextInt(1200);
            this.setHasFur(false);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public boolean isFood(ItemStack itemStack) {
      return YetiAi.getTemptations().test(itemStack);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
      return new Yeti(ESEntities.YETI.get(), serverLevel);
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("fur", this.hasFur());
      compoundTag.putInt("fur_growth_ticks", this.furGrowthTicks);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("fur", 1)) {
         this.setHasFur(compoundTag.getBoolean("fur"));
      }

      this.furGrowthTicks = compoundTag.getInt("fur_growth_ticks");
   }

   public static boolean checkYetiSpawnRules(EntityType<? extends Yeti> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.yeti.canSpawn();
   }
}
