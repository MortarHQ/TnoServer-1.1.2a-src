package twilightforest.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.block.CinderFurnaceBlock;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;

public class CinderFurnaceBlockEntity extends FurnaceBlockEntity {
   private static final int SMELT_LOG_FACTOR = 10;

   public CinderFurnaceBlockEntity(BlockPos pos, BlockState state) {
      super(pos, state);
   }

   public BlockEntityType<?> getType() {
      return (BlockEntityType<?>)TFBlockEntities.CINDER_FURNACE.get();
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CinderFurnaceBlockEntity te) {
      boolean flag = te.isBurning();
      boolean flag1 = false;
      if (te.isBurning()) {
         te.litTime--;
      }

      if (!level.isClientSide()) {
         ItemStack itemstack = (ItemStack)te.items.get(1);
         if (te.isBurning() || !itemstack.isEmpty() && !((ItemStack)te.items.get(0)).isEmpty()) {
            RecipeHolder<?> recipe = (RecipeHolder<?>)level.getRecipeManager()
               .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput((ItemStack)te.items.getFirst()), level)
               .orElse(null);
            if (recipe != null && !te.isBurning() && te.canBurn(level, recipe.value())) {
               te.litTime = te.getBurnDuration(itemstack);
               te.litDuration = te.litTime;
               if (te.isBurning()) {
                  flag1 = true;
                  if (!itemstack.isEmpty()) {
                     Item item = itemstack.getItem();
                     itemstack.shrink(1);
                     if (itemstack.isEmpty()) {
                        ItemStack item1 = item.getCraftingRemainingItem(itemstack);
                        te.items.set(1, item1);
                     }
                  }
               }
            }

            if (recipe != null && te.isBurning() && te.canBurn(level, recipe.value())) {
               te.cookingProgress = te.cookingProgress + te.getCurrentSpeedMultiplier(level);
               if (te.cookingProgress >= te.cookingTotalTime) {
                  te.cookingProgress = 0;
                  te.cookingTotalTime = te.getRecipeBurnTime(level);
                  te.smeltItem(level, recipe.value());
                  flag1 = true;
               }
            } else {
               te.cookingProgress = 0;
            }
         } else if (te.cookingProgress > 0) {
            te.cookingProgress = Mth.clamp(te.cookingProgress - 2, 0, te.cookingTotalTime);
         }

         if (flag != te.isBurning()) {
            flag1 = true;
            level.setBlock(pos, (BlockState)level.getBlockState(pos).setValue(CinderFurnaceBlock.LIT, te.isBurning()), 3);
         }

         if (te.isBurning() && te.litTime % 5 == 0) {
            te.cinderizeNearbyLog(level, pos);
         }
      }

      if (flag1) {
         te.setChanged();
      }
   }

   private boolean isBurning() {
      return this.litTime > 0;
   }

   protected int getRecipeBurnTime(Level level) {
      return level.getRecipeManager()
         .getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput((ItemStack)this.items.getFirst()), level)
         .map(recipeHolder -> ((SmeltingRecipe)recipeHolder.value()).getCookingTime())
         .orElse(200);
   }

   private void cinderizeNearbyLog(Level level, BlockPos origin) {
      RandomSource rand = level.getRandom();
      int dx = rand.nextInt(2) - rand.nextInt(2);
      int dy = rand.nextInt(2) - rand.nextInt(2);
      int dz = rand.nextInt(2) - rand.nextInt(2);
      BlockPos pos = origin.offset(dx, dy, dz);
      if (level.hasChunkAt(pos)) {
         BlockState nearbyBlock = level.getBlockState(pos);
         if (!nearbyBlock.is(TFBlocks.CINDER_LOG) && nearbyBlock.is(BlockTags.LOGS)) {
            level.setBlock(pos, ((RotatedPillarBlock)TFBlocks.CINDER_LOG.get()).withPropertiesOf(nearbyBlock), 2);
            level.levelEvent(2004, pos, 0);
            level.levelEvent(2004, pos, 0);
            level.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
         }
      }
   }

   private int getCurrentSpeedMultiplier(Level level) {
      return this.getCurrentMultiplier(level, 2);
   }

   private int getCurrentMultiplier(Level level, int factor) {
      int logs = this.countNearbyLogs(level);
      return logs < factor ? 1 : logs / factor + (level.getRandom().nextInt(factor) >= logs % factor ? 0 : 1);
   }

   private int countNearbyLogs(Level level) {
      int count = 0;

      for (int dx = -1; dx <= 1; dx++) {
         for (int dy = -1; dy <= 1; dy++) {
            for (int dz = -1; dz <= 1; dz++) {
               BlockPos pos = this.getBlockPos().offset(dx, dy, dz);
               if (level.hasChunkAt(pos) && level.getBlockState(pos).is((Block)TFBlocks.CINDER_LOG.get())) {
                  count++;
               }
            }
         }
      }

      return count;
   }

   protected boolean canBurn(Level level, Recipe<?> recipe) {
      if (((ItemStack)this.items.get(0)).isEmpty()) {
         return false;
      }

      ItemStack itemstack = recipe.getResultItem(level.registryAccess());
      if (itemstack.isEmpty()) {
         return false;
      }

      ItemStack itemstack1 = (ItemStack)this.items.get(2);
      if (itemstack1.isEmpty()) {
         return true;
      }

      if (!itemstack1.is(itemstack.getItem())) {
         return false;
      }

      int result = itemstack1.getCount() + this.getMaxOutputStacks(level, (ItemStack)this.items.getFirst(), itemstack);
      return result <= this.getMaxStackSize() && result <= itemstack1.getMaxStackSize();
   }

   public int getMaxOutputStacks(Level level, ItemStack input, ItemStack output) {
      return this.canMultiply(input) ? output.getCount() * this.getCurrentMaxSmeltMultiplier(level) : output.getCount();
   }

   public void smeltItem(Level level, Recipe<?> recipe) {
      if (this.canBurn(level, recipe)) {
         ItemStack itemstack = (ItemStack)this.items.getFirst();
         ItemStack itemstack1 = recipe.getResultItem(level.registryAccess());
         itemstack1.setCount(itemstack1.getCount() * this.getCurrentSmeltMultiplier(level));
         ItemStack itemstack2 = (ItemStack)this.items.get(2);
         if (itemstack2.isEmpty()) {
            this.items.set(2, itemstack1.copy());
         } else if (itemstack2.getItem() == itemstack1.getItem()) {
            itemstack2.grow(itemstack1.getCount());
         }

         if (itemstack.getItem() == Blocks.WET_SPONGE.asItem()
            && !((ItemStack)this.items.get(1)).isEmpty()
            && ((ItemStack)this.items.get(1)).getItem() == Items.BUCKET) {
            this.items.set(1, new ItemStack(Items.WATER_BUCKET));
         }

         itemstack.shrink(1);
      }
   }

   private boolean canMultiply(ItemStack input) {
      return input.is(ItemTags.LOGS) || input.is(net.neoforged.neoforge.common.Tags.Items.ORES);
   }

   private int getCurrentSmeltMultiplier(Level level) {
      return this.getCurrentMultiplier(level, 10);
   }

   private int getCurrentMaxSmeltMultiplier(Level level) {
      return (int)Math.ceil(this.countNearbyLogs(level) / 10.0F);
   }
}
