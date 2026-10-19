package com.github.L_Ender.cataclysm.blockentities;

import com.github.L_Ender.cataclysm.blocks.Altar_Of_Amethyst_Block;
import com.github.L_Ender.cataclysm.crafting.AltarOfAmethystRecipe;
import com.github.L_Ender.cataclysm.init.ModRecipeTypes;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.RecipeManager.CachedCheck;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AltarOfAmethyst_Block_Entity extends BlockEntity {
   private final ItemStackHandler inventory = this.createHandler();
   private int cookingTime;
   private int cookingTimeTotal;
   private final CachedCheck<SingleRecipeInput, AltarOfAmethystRecipe> quickCheck = RecipeManager.createCheck((RecipeType)ModRecipeTypes.AMETHYST_BLESS.get());

   public AltarOfAmethyst_Block_Entity(BlockPos pos, BlockState state) {
      super((BlockEntityType)ModTileentites.ALTAR_OF_AMETHYST.get(), pos, state);
   }

   public static void cookingTick(Level level, BlockPos pos, BlockState state, AltarOfAmethyst_Block_Entity skillet) {
      ItemStack cookingStack = skillet.getStoredStack();
      if (cookingStack.isEmpty()) {
         skillet.cookingTime = 0;
      } else {
         skillet.cookAndOutputItems(cookingStack, level, pos);
      }
   }

   private void cookAndOutputItems(ItemStack cookingStack, Level level, BlockPos pos) {
      this.cookingTime++;
      if (this.cookingTime >= this.cookingTimeTotal) {
         Optional<RecipeHolder<AltarOfAmethystRecipe>> recipe = this.getMatchingRecipe(cookingStack);
         if (recipe.isPresent()) {
            ItemStack resultStack = ((AltarOfAmethystRecipe)recipe.get().value()).assemble(new SingleRecipeInput(cookingStack), level.registryAccess());
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), resultStack.copy());
            this.cookingTime = 0;
            this.inventory.extractItem(0, 1, false);
         }
      }
   }

   public boolean isCooking() {
      return this.hasStoredStack();
   }

   private Optional<RecipeHolder<AltarOfAmethystRecipe>> getMatchingRecipe(ItemStack stack) {
      return this.level == null ? Optional.empty() : this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), this.level);
   }

   public void loadAdditional(CompoundTag compound, Provider registries) {
      super.loadAdditional(compound, registries);
      this.inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
      this.cookingTime = compound.getInt("CookTime");
      this.cookingTimeTotal = compound.getInt("CookTimeTotal");
   }

   public void saveAdditional(CompoundTag compound, Provider registries) {
      super.saveAdditional(compound, registries);
      compound.put("Inventory", this.inventory.serializeNBT(registries));
      compound.putInt("CookTime", this.cookingTime);
      compound.putInt("CookTimeTotal", this.cookingTimeTotal);
   }

   public ItemStack addItemToCook(ItemStack addedStack, Player player) {
      Optional<RecipeHolder<AltarOfAmethystRecipe>> recipe = this.getMatchingRecipe(addedStack);
      if (recipe.isPresent() && this.getStoredStack().isEmpty()) {
         ItemStack remainderStack = this.inventory.insertItem(0, addedStack.copy(), false);
         if (!ItemStack.matches(remainderStack, addedStack)) {
            this.cookingTimeTotal = Altar_Of_Amethyst_Block.getCookingTime(((AltarOfAmethystRecipe)recipe.get().value()).getTime());
            this.cookingTime = 0;
            return remainderStack;
         }
      }

      return addedStack;
   }

   public ItemStack removeItem() {
      return this.inventory.extractItem(0, this.getStoredStack().getMaxStackSize(), false);
   }

   public IItemHandler getInventory() {
      return this.inventory;
   }

   public ItemStack getStoredStack() {
      return this.inventory.getStackInSlot(0);
   }

   public boolean hasStoredStack() {
      return !this.getStoredStack().isEmpty();
   }

   private ItemStackHandler createHandler() {
      return new ItemStackHandler() {
         protected void onContentsChanged(int slot) {
            AltarOfAmethyst_Block_Entity.this.inventoryChanged();
         }
      };
   }

   public void setRemoved() {
      super.setRemoved();
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      return this.saveWithoutMetadata(registries);
   }

   protected void inventoryChanged() {
      super.setChanged();
      if (this.level != null) {
         this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 2);
      }
   }
}
