package com.mortal_cultivation.block.entity;

import com.mortal_cultivation.ability.skill.unique.AlchemyProdigySkill;
import com.mortal_cultivation.block.AlchemyFurnaceBlock;
import com.mortal_cultivation.gui.menu.AlchemyFurnaceMenu;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.recipe.AlchemyFurnaceRecipe;
import com.mortal_cultivation.registry.RecipeRegistry;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import com.mortal_cultivation.util.FuelSpeedManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AlchemyFurnaceBlockEntity extends BlockEntity implements MenuProvider {
   private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
      protected void onContentsChanged(int slot) {
         AlchemyFurnaceBlockEntity.this.setChanged();
      }
   };
   private int burnTime = 0;
   private int totalBurnTime = 0;
   private int cookTime = 0;
   private int cookTimeTotal = 0;
   private int originalCookTimeTotal = 0;
   private UUID acceleratingPlayer = null;
   private AlchemyFurnaceRecipe currentRecipe = null;
   private double currentFuelSpeedMultiplier = 1.0;
   protected final ContainerData data = new ContainerData() {
      public int get(int index) {
         switch (index) {
            case 0:
               return AlchemyFurnaceBlockEntity.this.burnTime;
            case 1:
               return AlchemyFurnaceBlockEntity.this.totalBurnTime;
            case 2:
               return AlchemyFurnaceBlockEntity.this.cookTime;
            case 3:
               return AlchemyFurnaceBlockEntity.this.cookTimeTotal;
            default:
               return 0;
         }
      }

      public void set(int index, int value) {
         switch (index) {
            case 0:
               AlchemyFurnaceBlockEntity.this.burnTime = value;
               break;
            case 1:
               AlchemyFurnaceBlockEntity.this.totalBurnTime = value;
               break;
            case 2:
               AlchemyFurnaceBlockEntity.this.cookTime = value;
               break;
            case 3:
               AlchemyFurnaceBlockEntity.this.cookTimeTotal = value;
         }
      }

      public int getCount() {
         return 4;
      }
   };

   public ItemStackHandler getItemHandler() {
      return this.itemHandler;
   }

   public AlchemyFurnaceBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)BlockEntityRegistry.ALCHEMY_FURNACE.get(), pos, state);
   }

   public ContainerData getContainerData() {
      return this.data;
   }

   public Component getDisplayName() {
      return Component.empty();
   }

   @Nullable
   public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
      return new AlchemyFurnaceMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition), this.itemHandler, this.data);
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      tag.put("inventory", this.itemHandler.serializeNBT(provider));
      tag.putInt("burnTime", this.burnTime);
      tag.putInt("totalBurnTime", this.totalBurnTime);
      tag.putInt("cookTime", this.cookTime);
      tag.putInt("cookTimeTotal", this.cookTimeTotal);
      tag.putInt("originalCookTimeTotal", this.originalCookTimeTotal);
      tag.putDouble("currentFuelSpeedMultiplier", this.currentFuelSpeedMultiplier);
      if (this.acceleratingPlayer != null) {
         tag.putUUID("acceleratingPlayer", this.acceleratingPlayer);
      }
   }

   public void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      if (tag.contains("inventory")) {
         this.itemHandler.deserializeNBT(provider, tag.getCompound("inventory"));
      }

      this.burnTime = tag.getInt("burnTime");
      this.totalBurnTime = tag.getInt("totalBurnTime");
      this.cookTime = tag.getInt("cookTime");
      this.cookTimeTotal = tag.getInt("cookTimeTotal");
      this.originalCookTimeTotal = tag.getInt("originalCookTimeTotal");
      this.currentFuelSpeedMultiplier = tag.getDouble("currentFuelSpeedMultiplier");
      if (this.currentFuelSpeedMultiplier == 0.0) {
         this.currentFuelSpeedMultiplier = 1.0;
      }

      if (tag.hasUUID("acceleratingPlayer")) {
         this.acceleratingPlayer = tag.getUUID("acceleratingPlayer");
      }
   }

   public void dropItems(Level level, BlockPos pos) {
      SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

      for (int i = 0; i < this.itemHandler.getSlots(); i++) {
         inventory.setItem(i, this.itemHandler.getStackInSlot(i));
      }

      Containers.dropContents(level, pos, inventory);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, AlchemyFurnaceBlockEntity blockEntity) {
      if (!level.isClientSide()) {
         AlchemyFurnaceRecipe.Input inventory = createRecipeInput(blockEntity);
         boolean recipeStillValid = false;
         if (blockEntity.currentRecipe != null) {
            recipeStillValid = blockEntity.currentRecipe.matches(inventory, level);
            if (!recipeStillValid) {
               blockEntity.cookTime = 0;
               blockEntity.currentRecipe = null;
               blockEntity.data.set(2, 0);
               blockEntity.setChanged();
            }
         }

         if (blockEntity.currentRecipe == null) {
            Optional<RecipeHolder<AlchemyFurnaceRecipe>> recipe = level.getRecipeManager()
               .getRecipeFor((RecipeType)RecipeRegistry.ALCHEMY_FURNACE_TYPE.get(), inventory, level);
            if (recipe.isPresent()) {
               AlchemyFurnaceRecipe potentialRecipe = (AlchemyFurnaceRecipe)recipe.get().value();
               if (canOutputItems(blockEntity, potentialRecipe)) {
                  blockEntity.currentRecipe = potentialRecipe;
                  blockEntity.originalCookTimeTotal = potentialRecipe.getCookingTime();
                  blockEntity.cookTimeTotal = blockEntity.originalCookTimeTotal;
                  blockEntity.data.set(3, blockEntity.cookTimeTotal);
               }
            }
         }

         if (blockEntity.burnTime > 0) {
            boolean canContinueBurning = blockEntity.currentRecipe != null && canOutputItems(blockEntity, blockEntity.currentRecipe);
            if (blockEntity.currentRecipe == null) {
               blockEntity.burnTime = Math.max(0, blockEntity.burnTime - 2);
            } else if (canContinueBurning) {
               blockEntity.burnTime--;
            }

            blockEntity.data.set(0, blockEntity.burnTime);
            blockEntity.setChanged();
         }

         boolean needBurnFuel = blockEntity.burnTime <= 0 && blockEntity.currentRecipe != null;
         if (needBurnFuel) {
            ItemStack fuelStack = blockEntity.itemHandler.getStackInSlot(1);
            int fuelBurnTime = getBurnDuration(fuelStack);
            if (fuelBurnTime > 0) {
               double speedMultiplier = FuelSpeedManager.getSpeedMultiplier(fuelStack);
               blockEntity.currentFuelSpeedMultiplier = speedMultiplier;
               blockEntity.burnTime = fuelBurnTime;
               blockEntity.totalBurnTime = fuelBurnTime;
               blockEntity.data.set(0, blockEntity.burnTime);
               blockEntity.data.set(1, blockEntity.totalBurnTime);
               if (fuelStack.hasCraftingRemainingItem()) {
                  blockEntity.itemHandler.setStackInSlot(1, fuelStack.getCraftingRemainingItem());
               } else {
                  fuelStack.shrink(1);
                  if (fuelStack.isEmpty()) {
                     blockEntity.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
                  }
               }

               blockEntity.setChanged();
            } else {
               blockEntity.currentFuelSpeedMultiplier = 1.0;
            }
         }

         boolean isCurrentlyBurning = blockEntity.burnTime > 0;
         boolean stateChanged = false;
         if ((Boolean)state.getValue(AlchemyFurnaceBlock.LIT) != isCurrentlyBurning) {
            level.setBlock(pos, (BlockState)state.setValue(AlchemyFurnaceBlock.LIT, isCurrentlyBurning), 3);
            stateChanged = true;
         }

         if (state.getValue(AlchemyFurnaceBlock.PART) == AlchemyFurnaceBlock.AlchemyFurnacePart.BASE) {
            BlockState topState = level.getBlockState(pos.above());
            if (topState.getBlock() instanceof AlchemyFurnaceBlock && (Boolean)topState.getValue(AlchemyFurnaceBlock.LIT) != isCurrentlyBurning) {
               level.setBlock(pos.above(), (BlockState)topState.setValue(AlchemyFurnaceBlock.LIT, isCurrentlyBurning), 3);
               stateChanged = true;
            }
         }

         if (blockEntity.burnTime > 0 && blockEntity.currentRecipe != null) {
            AlchemyFurnaceBlockEntity.AlchemyAccelerationInfo accelerationInfo = getAlchemyAccelerationInfo(level, pos);
            boolean hasAcceleration = false;
            double cookTimeFactor = 1.0;
            if (blockEntity.acceleratingPlayer == null && accelerationInfo != null) {
               blockEntity.acceleratingPlayer = accelerationInfo.player.getUUID();
               hasAcceleration = true;
               cookTimeFactor = accelerationInfo.cookTimeFactor;
            } else if (blockEntity.acceleratingPlayer != null) {
               Player currentPlayer = level.getPlayerByUUID(blockEntity.acceleratingPlayer);
               if (currentPlayer != null && accelerationInfo != null && currentPlayer.equals(accelerationInfo.player)) {
                  hasAcceleration = true;
                  cookTimeFactor = accelerationInfo.cookTimeFactor;
               } else {
                  blockEntity.acceleratingPlayer = null;
               }
            }

            int targetCookTimeTotal = hasAcceleration
               ? Math.max(1, (int)Math.round(blockEntity.originalCookTimeTotal * cookTimeFactor))
               : blockEntity.originalCookTimeTotal;
            if (targetCookTimeTotal != blockEntity.cookTimeTotal) {
               if (blockEntity.cookTimeTotal > 0) {
                  double progress = (double)blockEntity.cookTime / blockEntity.cookTimeTotal;
                  blockEntity.cookTime = (int)(progress * targetCookTimeTotal);
               }

               blockEntity.cookTimeTotal = targetCookTimeTotal;
               blockEntity.data.set(3, blockEntity.cookTimeTotal);
            }

            if (canOutputItems(blockEntity, blockEntity.currentRecipe)) {
               int speedIncrease = (int)Math.max(1.0, blockEntity.currentFuelSpeedMultiplier);
               blockEntity.cookTime += speedIncrease;
               blockEntity.data.set(2, blockEntity.cookTime);
               if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                  craftItem(blockEntity);
                  blockEntity.cookTime = 0;
                  blockEntity.currentRecipe = null;
                  blockEntity.originalCookTimeTotal = 0;
                  blockEntity.acceleratingPlayer = null;
                  blockEntity.data.set(2, 0);
                  blockEntity.setChanged();
               }
            }
         } else if (blockEntity.cookTime > 0 && blockEntity.burnTime <= 0) {
            blockEntity.cookTime = Math.max(0, blockEntity.cookTime - 2);
            blockEntity.data.set(2, 0);
            blockEntity.setChanged();
         }
      }
   }

   private static AlchemyFurnaceRecipe.Input createRecipeInput(AlchemyFurnaceBlockEntity blockEntity) {
      List<ItemStack> items = new ArrayList<>(blockEntity.itemHandler.getSlots());

      for (int i = 0; i < blockEntity.itemHandler.getSlots(); i++) {
         items.add(blockEntity.itemHandler.getStackInSlot(i).copy());
      }

      return new AlchemyFurnaceRecipe.Input(items);
   }

   private static boolean canOutputItems(AlchemyFurnaceBlockEntity blockEntity, AlchemyFurnaceRecipe recipe) {
      ItemStack mainResultStack = recipe.getResults().get(0);
      ItemStack mainOutputStack = blockEntity.itemHandler.getStackInSlot(4);
      if (!mainOutputStack.isEmpty()) {
         if (!mainOutputStack.is(mainResultStack.getItem())) {
            return false;
         }

         if (mainOutputStack.getCount() + mainResultStack.getCount() > mainOutputStack.getMaxStackSize()) {
            return false;
         }
      }

      for (int i = 1; i < recipe.getResults().size(); i++) {
         ItemStack resultStack = recipe.getResults().get(i);
         int outputSlot = 4 + i;
         if (outputSlot >= 7) {
            return false;
         }

         ItemStack outputStack = blockEntity.itemHandler.getStackInSlot(outputSlot);
         if (!outputStack.isEmpty()) {
            if (!outputStack.is(resultStack.getItem())) {
               return false;
            }

            if (outputStack.getCount() + resultStack.getCount() > outputStack.getMaxStackSize()) {
               return false;
            }
         }
      }

      return true;
   }

   private static AlchemyFurnaceBlockEntity.AlchemyAccelerationInfo getAlchemyAccelerationInfo(Level level, BlockPos furnacePos) {
      if (level.isClientSide) {
         return null;
      }

      AlchemyFurnaceBlockEntity.AlchemyAccelerationInfo bestInfo = null;

      for (Player player : level.players()) {
         boolean hasProdigy = AlchemyProdigySkill.isPlayerHasAlchemyProdigy(player);
         boolean hasFireRoot = SpiritRootData.hasPrimaryRoot(player, SpiritRootType.FIRE);
         if (hasProdigy || hasFireRoot) {
            boolean isMastered = false;
            if (hasProdigy) {
               try {
                  if (SkillUtils.hasSkill(player, (ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get())) {
                     ManasSkillInstance skillInstance = SkillUtils.getSkillOrNull(player, (ManasSkill)CultivationSkills.ALCHEMY_PRODIGY.get());
                     if (skillInstance != null) {
                        isMastered = skillInstance.isMastered(player);
                     }
                  }
               } catch (Exception e) {
                  isMastered = false;
               }
            }

            double maxRange = hasProdigy ? (isMastered ? 100.0 : 25.0) : 25.0;
            double distance = player.distanceToSqr(furnacePos.getX(), furnacePos.getY(), furnacePos.getZ());
            if (!(distance > maxRange)) {
               double cookTimeFactor = hasProdigy ? (isMastered ? 0.1 : 0.5) : 1.0;
               if (hasFireRoot) {
                  cookTimeFactor *= 0.8;
               }

               if (!(cookTimeFactor >= 1.0) && (bestInfo == null || cookTimeFactor < bestInfo.cookTimeFactor)) {
                  bestInfo = new AlchemyFurnaceBlockEntity.AlchemyAccelerationInfo(player, cookTimeFactor);
               }
            }
         }
      }

      return bestInfo;
   }

   private static void craftItem(AlchemyFurnaceBlockEntity blockEntity) {
      AlchemyFurnaceRecipe recipe = blockEntity.currentRecipe;
      if (recipe != null) {
         Optional<int[]> consumption = recipe.getInputConsumption(createRecipeInput(blockEntity));
         if (!consumption.isEmpty() && canOutputItems(blockEntity, recipe)) {
            int[] counts = consumption.get();

            for (int slot = 0; slot < counts.length; slot++) {
               if (counts[slot] > 0) {
                  blockEntity.itemHandler.extractItem(slot, counts[slot], false);
               }
            }

            for (int i = 0; i < recipe.getResults().size(); i++) {
               ItemStack resultStack = recipe.getResults().get(i).copy();
               int outputSlot = 4 + i;
               if (outputSlot >= 7) {
                  break;
               }

               ItemStack outputStack = blockEntity.itemHandler.getStackInSlot(outputSlot);
               if (outputStack.isEmpty()) {
                  blockEntity.itemHandler.setStackInSlot(outputSlot, resultStack);
               } else {
                  outputStack.grow(resultStack.getCount());
               }
            }
         }
      }
   }

   private static int getBurnDuration(ItemStack stack) {
      return stack.isEmpty() ? 0 : stack.getBurnTime(null);
   }

   private static class AlchemyAccelerationInfo {
      public final Player player;
      public final double cookTimeFactor;

      public AlchemyAccelerationInfo(Player player, double cookTimeFactor) {
         this.player = player;
         this.cookTimeFactor = cookTimeFactor;
      }
   }
}
