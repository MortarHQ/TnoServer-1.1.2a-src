package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.IChoppingBoard;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.ChoppingBoardRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class ChoppingBoardBlockEntity extends BaseBlockEntity implements IChoppingBoard {
   private static final String MODEL_ID = "ModelId";
   private static final String CURRENT_CUT_STACK = "CurrentCutStack";
   private static final String RESULT_ITEM = "ResultItem";
   private static final String MAX_CUT_COUNT = "MaxCutCount";
   private static final String CURRENT_CUT_COUNT = "CurrentCutCount";
   @Nullable
   public ModelResourceLocation[] cacheModels = null;
   @Nullable
   public ModelResourceLocation previousModel = null;
   @Nullable
   private ResourceLocation modelId = null;
   private int maxCutCount = 0;
   private int currentCutCount = 0;
   private ItemStack currentCutStack = ItemStack.EMPTY;
   private ItemStack result = ItemStack.EMPTY;

   public ChoppingBoardBlockEntity(BlockPos pos, BlockState blockState) {
      super(ModBlocks.CHOPPING_BOARD_BE.get(), pos, blockState);
   }

   public static void popResource(Level level, BlockPos pos, ItemStack stack) {
      if (!level.isClientSide && !stack.isEmpty()) {
         ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5, stack, 0.0, 0.0, 0.0);
         entity.setDefaultPickUpDelay();
         level.addFreshEntity(entity);
      }
   }

   @Override
   public boolean onPutItem(Level level, LivingEntity user, ItemStack putOnItem) {
      if (!this.result.isEmpty()) {
         return false;
      } else {
         SingleRecipeInput container = new SingleRecipeInput(putOnItem);
         Optional<RecipeHolder<ChoppingBoardRecipe>> recipeOptional = level.getRecipeManager().getRecipeFor(ModRecipes.CHOPPING_BOARD_RECIPE, container, level);
         if (recipeOptional.isPresent()) {
            ChoppingBoardRecipe recipe = (ChoppingBoardRecipe)recipeOptional.get().value();
            this.modelId = recipe.getModelId();
            this.maxCutCount = recipe.getCutCount();
            this.currentCutCount = 0;
            this.currentCutStack = putOnItem.split(1);
            this.result = recipe.assemble(container, level.registryAccess());
            this.refresh();
            level.playSound(null, this.worldPosition, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.2F);
            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean onCutItem(Level level, LivingEntity user, ItemStack cutterItem) {
      if (this.result.isEmpty()) {
         return false;
      } else if (this.currentCutCount >= this.maxCutCount) {
         popResource(level, this.worldPosition, this.result.copy());
         this.resetBoardData();
         level.playSound(null, this.worldPosition, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 2.0F + level.random.nextFloat() * 0.2F);
         return true;
      } else if (cutterItem.is(TagMod.KITCHEN_KNIFE)) {
         this.currentCutCount++;
         this.playParticlesSound();
         this.refresh();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean onTakeOut(Level level, LivingEntity user) {
      if (this.currentCutCount == 0 && !this.currentCutStack.isEmpty()) {
         if (user instanceof Player player) {
            ItemHandlerHelper.giveItemToPlayer(player, this.currentCutStack);
         } else {
            popResource(level, this.worldPosition, this.currentCutStack);
         }

         this.resetBoardData();
         level.playSound(null, this.worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 1.0F, 1.2F + level.random.nextFloat() * 0.2F);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void playParticlesSound() {
      if (this.level instanceof ServerLevel serverLevel) {
         RandomSource random = serverLevel.getRandom();
         serverLevel.sendParticles(
            ParticleTypes.CRIT,
            this.worldPosition.getX() + 0.25 + random.nextDouble() / 2.0,
            this.worldPosition.getY() + 0.25,
            this.worldPosition.getZ() + 0.25 + random.nextDouble() / 2.0,
            2,
            0.0,
            0.0,
            0.0,
            0.1
         );
         serverLevel.playSound(null, this.worldPosition, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.5F + this.level.random.nextFloat() * 0.4F);
      }
   }

   private void resetBoardData() {
      this.modelId = null;
      this.result = ItemStack.EMPTY;
      this.currentCutStack = ItemStack.EMPTY;
      this.currentCutCount = 0;
      this.maxCutCount = 0;
      this.refresh();
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (this.modelId != null) {
         tag.putString("ModelId", this.modelId.toString());
      }

      tag.putInt("MaxCutCount", this.maxCutCount);
      tag.putInt("CurrentCutCount", this.currentCutCount);
      tag.put("CurrentCutStack", this.currentCutStack.saveOptional(registries));
      tag.put("ResultItem", this.result.saveOptional(registries));
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("ModelId")) {
         this.modelId = ResourceLocation.parse(tag.getString("ModelId"));
      } else {
         this.modelId = null;
      }

      this.maxCutCount = tag.getInt("MaxCutCount");
      this.currentCutCount = tag.getInt("CurrentCutCount");
      if (tag.contains("CurrentCutStack")) {
         this.currentCutStack = ItemStack.parseOptional(registries, tag.getCompound("CurrentCutStack"));
      }

      if (tag.contains("ResultItem")) {
         this.result = ItemStack.parseOptional(registries, tag.getCompound("ResultItem"));
      }
   }

   @Nullable
   public ResourceLocation getModelId() {
      return this.modelId;
   }

   public int getMaxCutCount() {
      return this.maxCutCount;
   }

   public int getCurrentCutCount() {
      return this.currentCutCount;
   }

   public ItemStack getCurrentCutStack() {
      return this.currentCutStack;
   }
}
