package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Rookfish extends Squid implements Bucketable {
   private static final String TAG_FROM_BUCKET = "from_bucket";
   private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Rookfish.class, EntityDataSerializers.BOOLEAN);

   public Rookfish(EntityType<? extends Rookfish> type, Level level) {
      super(type, level);
   }

   protected ParticleOptions getInkParticle() {
      return ColorParticleOption.create(ESParticles.COLORED_INK.get(), ARGB32.color(255, 51, 61, 58));
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(FROM_BUCKET, false);
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
   }

   public boolean fromBucket() {
      return (Boolean)this.entityData.get(FROM_BUCKET);
   }

   public void setFromBucket(boolean fromBucket) {
      this.entityData.set(FROM_BUCKET, fromBucket);
   }

   public void saveToBucketTag(ItemStack itemStack) {
      Bucketable.saveDefaultDataToBucketTag(this, itemStack);
   }

   public void loadFromBucketTag(CompoundTag compoundTag) {
      Bucketable.loadDefaultDataFromBucketTag(this, compoundTag);
   }

   public ItemStack getBucketItemStack() {
      return ESItems.ROOKFISH_BUCKET.get().getDefaultInstance();
   }

   public SoundEvent getPickupSound() {
      return SoundEvents.BUCKET_FILL_FISH;
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("from_bucket", this.fromBucket());
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      this.setFromBucket(compoundTag.getBoolean("from_bucket"));
   }

   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
      super.dropCustomDeathLoot(serverLevel, damageSource, bl);
      if (damageSource.is(DamageTypes.DROWN) && serverLevel.getRandom().nextInt(10) == 0) {
         this.spawnAtLocation((ItemLike)ESItems.MUSIC_DISC_BRISK.get());
      }
   }

   public static boolean checkRookfishSpawnRules(
      EntityType<? extends Rookfish> type, LevelAccessor levelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource
   ) {
      int seaLevel = 48;
      int minY = seaLevel - 13;
      return blockPos.getY() >= minY
         && blockPos.getY() <= seaLevel
         && levelAccessor.getFluidState(blockPos.below()).is(FluidTags.WATER)
         && levelAccessor.getBlockState(blockPos.above()).is(Blocks.WATER);
   }
}
