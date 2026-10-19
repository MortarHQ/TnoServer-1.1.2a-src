package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IShawarmaSpit;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.ShawarmaSpitBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModParticles;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ShawarmaSpitBlockEntity extends BaseBlockEntity implements IShawarmaSpit {
   private static final int MAX_ITEMS = 8;
   public static final String COOKING_ITEM = "CookingItem";
   public static final String COOKED_ITEM = "CookedItem";
   public static final String COOK_TIME = "CookTime";
   private final CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
   public ItemStack cookingItem = ItemStack.EMPTY;
   public ItemStack cookedItem = ItemStack.EMPTY;
   public int cookTime;

   public ShawarmaSpitBlockEntity(BlockPos pPos, BlockState pBlockState) {
      super(ModBlocks.SHAWARMA_SPIT_BE.get(), pPos, pBlockState);
   }

   @Override
   public boolean onPutCookingItem(Level level, ItemStack itemStack) {
      if (this.cookingItem.isEmpty() && this.cookedItem.isEmpty()) {
         SingleRecipeInput singleRecipeInput = new SingleRecipeInput(itemStack);
         return this.quickCheck
            .getRecipeFor(singleRecipeInput, level)
            .map(
               recipe -> {
                  this.cookingItem = itemStack.split(8);
                  this.cookedItem = ((CampfireCookingRecipe)recipe.value()).assemble(singleRecipeInput, level.registryAccess());
                  this.cookedItem.setCount(this.cookingItem.getCount());
                  this.cookTime = ((CampfireCookingRecipe)recipe.value()).getCookingTime();
                  this.refresh();
                  if (level instanceof ServerLevel) {
                     level.playSound(
                        null,
                        this.worldPosition.getX() + 0.5,
                        this.worldPosition.getY() + 0.5,
                        this.worldPosition.getZ() + 0.5,
                        SoundEvents.ITEM_FRAME_ADD_ITEM,
                        SoundSource.BLOCKS,
                        0.5F + level.random.nextFloat(),
                        level.random.nextFloat() * 0.7F + 0.6F
                     );
                  }

                  return true;
               }
            )
            .orElse(false);
      } else {
         return false;
      }
   }

   @Override
   public boolean onTakeCookedItem(Level level, LivingEntity entity) {
      ItemStack mainHandItem = entity.getMainHandItem();
      if (this.cookTime <= 0 && !this.cookedItem.isEmpty()) {
         this.giveItem(level, entity, mainHandItem, this.cookedItem.copy());
         return true;
      } else if (this.cookTime > 0 && !this.cookingItem.isEmpty()) {
         this.giveItem(level, entity, mainHandItem, this.cookingItem.copy());
         return true;
      } else {
         return false;
      }
   }

   private void giveItem(Level level, LivingEntity entity, ItemStack mainHandItem, ItemStack copy) {
      this.cookingItem = ItemStack.EMPTY;
      this.cookedItem = ItemStack.EMPTY;
      this.cookTime = 0;
      this.refresh();
      if ((Boolean)this.getBlockState().getValue(ShawarmaSpitBlock.POWERED)) {
         entity.hurt(level.damageSources().inFire(), 1.0F);
      }

      ItemUtils.getItemToLivingEntity(entity, copy);
      if (level instanceof ServerLevel) {
         level.playSound(
            null,
            this.worldPosition.getX() + 0.5,
            this.worldPosition.getY() + 0.5,
            this.worldPosition.getZ() + 0.5,
            SoundEvents.ITEM_FRAME_REMOVE_ITEM,
            SoundSource.BLOCKS,
            0.5F + level.random.nextFloat(),
            level.random.nextFloat() * 0.7F + 0.6F
         );
      }
   }

   public void tick() {
      if (this.cookingItem.isEmpty()) {
         if (!this.cookedItem.isEmpty()) {
            this.spawnParticles();
         }
      } else {
         this.spawnParticles();
         if (this.cookTime > 0) {
            this.cookTime--;
         } else {
            if (this.level instanceof ServerLevel) {
               this.level
                  .playSound(
                     null,
                     this.worldPosition.getX() + 0.5,
                     this.worldPosition.getY() + 0.5,
                     this.worldPosition.getZ() + 0.5,
                     SoundEvents.FIRE_EXTINGUISH,
                     SoundSource.BLOCKS,
                     0.5F + this.level.random.nextFloat(),
                     this.level.random.nextFloat() * 0.7F + 0.6F
                  );
            }

            this.cookingItem = ItemStack.EMPTY;
            this.refresh();
         }
      }
   }

   private void spawnParticles() {
      if (this.level instanceof ServerLevel serverLevel) {
         if (this.level.random.nextFloat() < 0.25F) {
            serverLevel.sendParticles(
               (SimpleParticleType)ModParticles.COOKING.get(),
               this.worldPosition.getX() + 0.5,
               this.worldPosition.getY() + 0.5,
               this.worldPosition.getZ() + 0.5,
               1,
               0.25,
               0.2,
               0.25,
               0.1F
            );
         }

         if (this.level.random.nextInt(20) == 0) {
            serverLevel.playSound(
               null,
               this.worldPosition.getX() + 0.5,
               this.worldPosition.getY() + 0.5,
               this.worldPosition.getZ() + 0.5,
               SoundEvents.CAMPFIRE_CRACKLE,
               SoundSource.BLOCKS,
               0.5F + this.level.random.nextFloat(),
               this.level.random.nextFloat() * 0.7F + 0.6F
            );
         }
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.put("CookingItem", this.cookingItem.saveOptional(registries));
      tag.put("CookedItem", this.cookedItem.saveOptional(registries));
      tag.putInt("CookTime", this.cookTime);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("CookingItem")) {
         this.cookingItem = ItemStack.parseOptional(registries, tag.getCompound("CookingItem"));
      }

      if (tag.contains("CookedItem")) {
         this.cookedItem = ItemStack.parseOptional(registries, tag.getCompound("CookedItem"));
      }

      this.cookTime = tag.getInt("CookTime");
   }
}
