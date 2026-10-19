package com.github.ysbbbbbb.kaleidoscopecookery.blockentity.kitchen;

import com.github.ysbbbbbb.kaleidoscopecookery.api.blockentity.ISteamer;
import com.github.ysbbbbbb.kaleidoscopecookery.block.kitchen.SteamerBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.blockentity.BaseBlockEntity;
import com.github.ysbbbbbb.kaleidoscopecookery.crafting.recipe.SteamerRecipe;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModParticles;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModRecipes;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagMod;
import com.github.ysbbbbbb.kaleidoscopecookery.util.ItemUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SteamerBlockEntity extends BaseBlockEntity implements ISteamer {
   public static final int MAX_LIT_LEVEL = 4;
   public static final String COOKING_PROGRESS_TAG = "CookingProgress";
   public static final String COOKING_TIME_TAG = "CookingTime";
   public static final String ITEMS_TAG = "Items";
   private final CachedCheck<SingleRecipeInput, SteamerRecipe> quickCheck = RecipeManager.createCheck(ModRecipes.STEAMER_RECIPE);
   private final NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);
   private final int[] cookingProgress = new int[8];
   private final int[] cookingTime = new int[8];
   private int litLevel = 0;

   public SteamerBlockEntity(BlockPos pos, BlockState state) {
      super(ModBlocks.STEAMER_BE.get(), pos, state);
   }

   public static void saveSplit(CompoundTag tag1, CompoundTag tag2, Level level, NonNullList<ItemStack> items, int[] cookingProgress, int[] cookingTime) {
      NonNullList<ItemStack> first = NonNullList.withSize(4, ItemStack.EMPTY);
      NonNullList<ItemStack> second = NonNullList.withSize(4, ItemStack.EMPTY);

      for (int i = 0; i < 4; i++) {
         first.set(i, (ItemStack)items.get(i));
         second.set(i, (ItemStack)items.get(i + 4));
      }

      int[] firstCookingProgress = new int[4];
      int[] secondCookingProgress = new int[4];
      int[] firstCookingTime = new int[4];
      int[] secondCookingTime = new int[4];
      System.arraycopy(cookingProgress, 0, firstCookingProgress, 0, 4);
      System.arraycopy(cookingProgress, 4, secondCookingProgress, 0, 4);
      System.arraycopy(cookingTime, 0, firstCookingTime, 0, 4);
      System.arraycopy(cookingTime, 4, secondCookingTime, 0, 4);
      ContainerHelper.saveAllItems(tag1, first, false, level.registryAccess());
      if (!tag1.isEmpty()) {
         tag1.putIntArray("CookingProgress", firstCookingProgress);
         tag1.putIntArray("CookingTime", firstCookingTime);
      }

      ContainerHelper.saveAllItems(tag2, second, false, level.registryAccess());
      if (!tag2.isEmpty()) {
         tag2.putIntArray("CookingProgress", secondCookingProgress);
         tag2.putIntArray("CookingTime", secondCookingTime);
      }
   }

   public void tick(Level level) {
      if (level.getGameTime() % 5L == 0L) {
         this.updateLitLevel(level);

         for (int i = 0; i < this.items.size(); i++) {
            if (this.cookingTime[i] == -1) {
               this.makeRipeParticles(level, this.worldPosition);
               break;
            }
         }
      }

      if (this.litLevel > 0) {
         this.cookingTick(level, this.worldPosition, this.getBlockState(), this);
      } else {
         this.cooldownTick(level, this.worldPosition, this.getBlockState(), this);
      }
   }

   public void mergeItem(ItemStack stack, Level level) {
      CompoundTag data = ((CustomData)stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY)).copyTag();
      NonNullList<ItemStack> merge = NonNullList.withSize(8, ItemStack.EMPTY);
      int[] mergeCookingProgress = new int[8];
      int[] mergeCookingTime = new int[8];
      if (data.contains("Items", 9)) {
         NonNullList<ItemStack> itemsInStack = NonNullList.withSize(4, ItemStack.EMPTY);
         ContainerHelper.loadAllItems(data, itemsInStack, level.registryAccess());

         for (int i = 0; i < 4; i++) {
            merge.set(i + 4, (ItemStack)itemsInStack.get(i));
         }
      }

      if (data.contains("CookingProgress", 11)) {
         int[] times = data.getIntArray("CookingProgress");
         int length = Math.min(mergeCookingProgress.length - 4, times.length);
         System.arraycopy(times, 0, mergeCookingProgress, 4, length);
      }

      if (data.contains("CookingTime", 11)) {
         int[] times = data.getIntArray("CookingTime");
         int length = Math.min(mergeCookingTime.length - 4, times.length);
         System.arraycopy(times, 0, mergeCookingTime, 4, length);
      }

      for (int i = 0; i < 4; i++) {
         merge.set(i, (ItemStack)this.items.get(i));
         mergeCookingProgress[i] = this.cookingProgress[i];
         mergeCookingTime[i] = this.cookingTime[i];
      }

      ContainerHelper.saveAllItems(data, merge, false, level.registryAccess());
      data.putIntArray("CookingProgress", mergeCookingProgress);
      data.putIntArray("CookingTime", mergeCookingTime);
      BlockItem.setBlockEntityData(stack, this.getType(), data);
   }

   public List<ItemStack> dropAsItem(Level level) {
      List<ItemStack> drops = Lists.newArrayList();
      boolean half = (Boolean)this.getBlockState().getValue(SteamerBlock.HALF);
      ItemStack first = ((Item)ModItems.STEAMER.get()).getDefaultInstance();
      if (this.items.stream().allMatch(ItemStack::isEmpty)) {
         drops.add(first);
         if (!half) {
            drops.add(((Item)ModItems.STEAMER.get()).getDefaultInstance());
         }

         return drops;
      } else {
         CompoundTag tag1 = new CompoundTag();
         CompoundTag tag2 = new CompoundTag();
         saveSplit(tag1, tag2, level, this.items, this.cookingProgress, this.cookingTime);
         BlockItem.setBlockEntityData(first, this.getType(), tag1);
         drops.add(first);
         if (!half) {
            ItemStack second = ((Item)ModItems.STEAMER.get()).getDefaultInstance();
            BlockItem.setBlockEntityData(second, this.getType(), tag2);
            drops.add(second);
         }

         return drops;
      }
   }

   @Override
   public void updateLitLevel(Level level) {
      BlockPos pos = this.getBlockPos();
      if (this.hasHeatSource(level)) {
         this.litLevel = 4;
      } else if (level.getBlockEntity(pos.below()) instanceof SteamerBlockEntity steamer) {
         if ((Boolean)steamer.getBlockState().getValue(SteamerBlock.HALF)) {
            this.litLevel = 0;
         } else {
            this.litLevel = Math.max(steamer.litLevel - 1, 0);
         }
      }
   }

   @Override
   public boolean hasHeatSource(Level level) {
      BlockState belowState = level.getBlockState(this.worldPosition.below());
      return belowState.hasProperty(BlockStateProperties.LIT)
         ? (Boolean)belowState.getValue(BlockStateProperties.LIT)
         : belowState.is(TagMod.HEAT_SOURCE_BLOCKS_WITHOUT_LIT);
   }

   private void cookingTick(Level level, BlockPos pos, BlockState state, SteamerBlockEntity steamer) {
      BlockState aboveState = level.getBlockState(pos.above());
      boolean aboveIsSteamer = aboveState.is((Block)ModBlocks.STEAMER.get());
      if (!aboveIsSteamer) {
         this.makeCookingParticles(level, pos);
         if (!(Boolean)state.getValue(SteamerBlock.HAS_LID)) {
            return;
         }
      }

      boolean hasCooking = false;

      for (int i = 0; i < steamer.items.size(); i++) {
         ItemStack stack = (ItemStack)steamer.items.get(i);
         if (!stack.isEmpty()) {
            hasCooking = true;
            int progress = steamer.cookingProgress[i]++;
            if (progress >= steamer.cookingTime[i]) {
               SingleRecipeInput container = new SingleRecipeInput(stack);
               ItemStack resultStack = steamer.quickCheck
                  .getRecipeFor(container, level)
                  .map(r -> ((SteamerRecipe)r.value()).assemble(container, level.registryAccess()))
                  .orElse(stack);
               if (!resultStack.isEmpty()) {
                  steamer.items.set(i, resultStack);
                  steamer.cookingTime[i] = -1;
                  level.sendBlockUpdated(pos, state, state, 3);
               }
            }
         }
      }

      if (hasCooking) {
         setChanged(level, pos, state);
      }
   }

   private void cooldownTick(Level level, BlockPos pos, BlockState state, SteamerBlockEntity steamer) {
      boolean hasCooking = false;

      for (int i = 0; i < steamer.items.size(); i++) {
         if (steamer.cookingProgress[i] > 0) {
            hasCooking = true;
            steamer.cookingProgress[i] = Mth.clamp(steamer.cookingProgress[i] - 2, 0, steamer.cookingTime[i]);
         }
      }

      if (hasCooking) {
         setChanged(level, pos, state);
      }
   }

   public void makeCookingParticles(Level level, BlockPos pos) {
      if (level instanceof ServerLevel serverLevel && level.random.nextFloat() < 0.1F) {
         RandomSource random = serverLevel.random;
         boolean half = (Boolean)this.getBlockState().getValue(SteamerBlock.HALF);
         double yOffset = half ? 0.5 : 1.0;
         serverLevel.sendParticles(
            (SimpleParticleType)ModParticles.COOKING.get(),
            pos.getX() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1),
            pos.getY() + yOffset + random.nextDouble() / 2.0,
            pos.getZ() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1),
            1,
            0.0,
            0.0,
            0.0,
            0.05
         );
      }
   }

   public void makeRipeParticles(Level level, BlockPos pos) {
      if (level instanceof ServerLevel serverLevel && level.random.nextFloat() < 0.5F) {
         RandomSource random = serverLevel.random;
         boolean half = (Boolean)this.getBlockState().getValue(SteamerBlock.HALF);
         double yOffset = half ? 0.25 : 0.75;
         serverLevel.sendParticles(
            (SimpleParticleType)ModParticles.COOKING.get(),
            pos.getX() + 0.5 + random.nextDouble() / 1.25 * (random.nextBoolean() ? 1 : -1),
            pos.getY() + yOffset + random.nextDouble() / 2.0,
            pos.getZ() + 0.5 + random.nextDouble() / 1.25 * (random.nextBoolean() ? 1 : -1),
            1,
            0.0,
            0.0,
            0.0,
            0.05
         );
      }
   }

   public Optional<RecipeHolder<SteamerRecipe>> getSteamerRecipe(Level level, ItemStack stack) {
      return this.items.stream().noneMatch(ItemStack::isEmpty) ? Optional.empty() : this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), level);
   }

   @Override
   public boolean placeFood(Level level, LivingEntity user, ItemStack food) {
      BlockPos above = this.getBlockPos().above();
      if (level.getBlockState(above).isFaceSturdy(level, above, Direction.DOWN)) {
         return false;
      }

      Optional<RecipeHolder<SteamerRecipe>> steamerRecipe = this.getSteamerRecipe(level, food);
      if (steamerRecipe.isEmpty()) {
         return false;
      }

      int cookTime = ((SteamerRecipe)steamerRecipe.get().value()).getCookTick();
      if (cookTime <= 0) {
         return false;
      }

      boolean added = false;
      boolean half = (Boolean)this.getBlockState().getValue(SteamerBlock.HALF);
      int endIndex = half ? 4 : 8;

      for (int i = 0; i < endIndex && !food.isEmpty(); i++) {
         ItemStack itemstack = (ItemStack)this.items.get(i);
         if (itemstack.isEmpty()) {
            this.cookingTime[i] = cookTime;
            this.cookingProgress[i] = 0;
            this.items.set(i, food.split(1));
            added = true;
         }
      }

      this.refresh();
      return added;
   }

   @Override
   public boolean takeFood(Level level, LivingEntity user) {
      BlockPos above = this.getBlockPos().above();
      if (level.getBlockState(above).isFaceSturdy(level, above, Direction.DOWN)) {
         return false;
      }

      BlockState blockState = this.getBlockState();
      boolean isAllEmpty = true;
      boolean half = (Boolean)blockState.getValue(SteamerBlock.HALF);
      int preferredSlot = user instanceof Player player ? player.getInventory().selected : -1;
      int endIndex = half ? 4 : 8;

      for (int i = 0; i < endIndex; i++) {
         ItemStack stack = (ItemStack)this.items.get(i);
         if (!stack.isEmpty()) {
            isAllEmpty = false;
            ItemUtils.getItemToLivingEntity(user, stack, preferredSlot);
            this.items.set(i, ItemStack.EMPTY);
            this.cookingTime[i] = 0;
            this.cookingProgress[i] = 0;
         }
      }

      boolean hasLid = (Boolean)blockState.getValue(SteamerBlock.HAS_LID);
      boolean isAboveSteamer = level.getBlockState(this.getBlockPos().above()).is(this.getBlockState().getBlock());
      if (isAllEmpty && !hasLid && !isAboveSteamer) {
         ItemUtils.getItemToLivingEntity(user, ((Item)ModItems.STEAMER.get()).getDefaultInstance(), preferredSlot);

         for (int i = endIndex - 4; i < endIndex; i++) {
            this.items.set(i, ItemStack.EMPTY);
            this.cookingTime[i] = 0;
            this.cookingProgress[i] = 0;
         }

         level.playSound(null, this.getBlockPos(), blockState.getSoundType().getBreakSound(), SoundSource.BLOCKS);
         if (half) {
            level.setBlockAndUpdate(this.getBlockPos(), Blocks.AIR.defaultBlockState());
         } else {
            this.setChanged();
            level.setBlockAndUpdate(this.getBlockPos(), (BlockState)blockState.setValue(SteamerBlock.HALF, true));
         }

         return true;
      } else {
         this.refresh();
         return !isAllEmpty;
      }
   }

   public NonNullList<ItemStack> getItems() {
      return this.items;
   }

   public void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.items.clear();
      ContainerHelper.loadAllItems(tag, this.items, registries);
      if (tag.contains("CookingProgress", 11)) {
         int[] times = tag.getIntArray("CookingProgress");
         int length = Math.min(this.cookingTime.length, times.length);
         System.arraycopy(times, 0, this.cookingProgress, 0, length);
      }

      if (tag.contains("CookingTime", 11)) {
         int[] times = tag.getIntArray("CookingTime");
         int length = Math.min(this.cookingTime.length, times.length);
         System.arraycopy(times, 0, this.cookingTime, 0, length);
      } else if (tag.contains("CookingTime", 9)) {
         ListTag list = tag.getList("CookingTime", 2);
         int length = Math.min(this.cookingTime.length, list.size());

         for (int i = 0; i < length; i++) {
            this.cookingTime[i] = list.getShort(i);
         }
      }
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      ContainerHelper.saveAllItems(tag, this.items, true, registries);
      tag.putIntArray("CookingProgress", this.cookingProgress);
      tag.putIntArray("CookingTime", this.cookingTime);
   }

   public int[] getCookingProgress() {
      return this.cookingProgress;
   }

   public int[] getCookingTime() {
      return this.cookingTime;
   }
}
