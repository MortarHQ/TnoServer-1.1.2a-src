package net.unusual.block_factorys_bosses.entity;

import java.util.Comparator;
import java.util.Optional;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;

public class FireAreaEntity extends Entity {
   private static final String TAG_LIVING_TIME = "Dataliving_time";
   public static final EntityDataAccessor<Integer> DATA_LIVING_TIME = SynchedEntityData.defineId(FireAreaEntity.class, EntityDataSerializers.INT);
   private static final String TAG_COOKING_TIME = "block_factorys_bosses:cook_time";

   public FireAreaEntity(EntityType<FireAreaEntity> type, Level world) {
      super(type, world);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_LIVING_TIME, 130);
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Dataliving_time", (Integer)this.entityData.get(DATA_LIVING_TIME));
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      if (compound.contains("Dataliving_time")) {
         this.entityData.set(DATA_LIVING_TIME, compound.getInt("Dataliving_time"));
      }
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide()) {
         for (LivingEntity touchingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
            if (!touchingEntity.fireImmune()) {
               touchingEntity.hurt(this.damageSources().inFire(), 1.5F);
               touchingEntity.igniteForSeconds(4.0F);
            }
         }
      }
   }

   public void baseTick() {
      super.baseTick();
      Level level = this.level();
      this.setDeltaMovement(new Vec3(0.0, 0.0, 0.0));
      if ((Integer)this.getEntityData().get(DATA_LIVING_TIME) > 0) {
         this.getEntityData().set(DATA_LIVING_TIME, (Integer)this.getEntityData().get(DATA_LIVING_TIME) - 1);
      } else if (!level.isClientSide()) {
         this.discard();
      }

      if (this.isInWaterOrBubble() && !level.isClientSide()) {
         this.discard();
      }

      if ((Integer)this.getEntityData().get(DATA_LIVING_TIME) > 25 && level instanceof ServerLevel _level) {
         _level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.FIRE_AOE.get(), this.getX(), this.getY() + 0.05, this.getZ(), 4, 1.0, 0.0, 1.0, 0.0);
         _level.sendParticles(
            (SimpleParticleType)BossesRiseParticleTypes.BLAZING_FLAME.get(), this.getX(), this.getY() + 0.05, this.getZ(), 4, 1.0, 0.0, 1.0, 0.0
         );
      }

      Vec3 center = this.position();

      for (ItemEntity itemEntity : level.getEntitiesOfClass(ItemEntity.class, AABB.ofSize(center, 1.5, 1.5, 1.5), e -> true)
         .stream()
         .sorted(Comparator.comparingDouble(entity -> entity.distanceToSqr(center)))
         .toList()) {
         Optional<RecipeHolder<SmeltingRecipe>> smeltingRecipe = level.getRecipeManager()
            .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(itemEntity.getItem()), level);
         if (!smeltingRecipe.isEmpty()) {
            if (itemEntity.getPersistentData().getInt("block_factorys_bosses:cook_time") < 40) {
               itemEntity.getPersistentData()
                  .putInt("block_factorys_bosses:cook_time", itemEntity.getPersistentData().getInt("block_factorys_bosses:cook_time") + 1);
               level.addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.FANCY_SMOKE.get(),
                  itemEntity.getX(),
                  itemEntity.getY() + 0.3,
                  itemEntity.getZ(),
                  (Math.random() - 0.5) * 0.8,
                  1.0,
                  (Math.random() - 0.5) * 0.8
               );
            } else {
               itemEntity.getPersistentData().putInt("block_factorys_bosses:cook_time", 0);
               ItemStack smeltedItem = smeltingRecipe.<ItemStack>map(recipe -> ((SmeltingRecipe)recipe.value()).getResultItem(level.registryAccess()).copy())
                  .orElseThrow();
               ItemEntity smeltedItemEntity = new ItemEntity(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), smeltedItem);
               smeltedItemEntity.setPickUpDelay(5);
               level.addFreshEntity(smeltedItemEntity);
               if (!level.isClientSide()) {
                  itemEntity.playSound((SoundEvent)BossesRiseSounds.FIRE_SMELTED_ITEM.value(), 0.2F, 2.0F);
               }

               itemEntity.getItem().shrink(1);
            }
         }
      }
   }

   @Nonnull
   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }
}
