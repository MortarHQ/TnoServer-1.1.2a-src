package com.tom.storagemod.block.entity;

import com.tom.storagemod.Content;
import com.tom.storagemod.StorageMod;
import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.menu.CraftingTerminalMenu;
import com.tom.storagemod.polymorph.PolymorphHelper;
import com.tom.storagemod.util.CraftingMatrix;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.CraftingInput.Positioned;
import net.minecraft.world.level.block.state.BlockState;

public class CraftingTerminalBlockEntity extends StorageTerminalBlockEntity implements Clearable {
   private Optional<RecipeHolder<CraftingRecipe>> currentRecipe = Optional.empty();
   private final CraftingContainer craftMatrix = new CraftingMatrix(3, 3, () -> {
      if (this.level != null && !this.level.isClientSide) {
         this.onCraftingMatrixChanged();
      }

      this.setChanged();
   });
   private ResultContainer craftResult = new ResultContainer();
   private HashSet<CraftingTerminalMenu> craftingListeners = new HashSet<>();
   private boolean refillingGrid;
   private int craftingCooldown;
   private WeakReference<Player> polymorphPlayer;
   private boolean reading;

   public CraftingTerminalBlockEntity(BlockPos pos, BlockState state) {
      super(Content.craftingTerminalBE.get(), pos, state);
   }

   @Override
   public AbstractContainerMenu createMenu(int id, Inventory plInv, Player arg2) {
      return new CraftingTerminalMenu(id, plInv, this);
   }

   @Override
   public Component getDisplayName() {
      return Component.translatable("menu.toms_storage.crafting_terminal");
   }

   @Override
   public void saveAdditional(CompoundTag compound, Provider provider) {
      super.saveAdditional(compound, provider);
      ListTag listnbt = new ListTag();

      for (int i = 0; i < this.craftMatrix.getContainerSize(); i++) {
         ItemStack itemstack = this.craftMatrix.getItem(i);
         if (!itemstack.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.putByte("Slot", (byte)i);
            listnbt.add(itemstack.save(provider, tag));
         }
      }

      compound.put("CraftingTable", listnbt);
   }

   @Override
   public void loadAdditional(CompoundTag compound, Provider provider) {
      super.loadAdditional(compound, provider);
      this.reading = true;
      ListTag listnbt = compound.getList("CraftingTable", 10);

      for (int i = 0; i < listnbt.size(); i++) {
         CompoundTag tag = listnbt.getCompound(i);
         int j = tag.getByte("Slot") & 255;
         if (j >= 0 && j < this.craftMatrix.getContainerSize()) {
            this.craftMatrix.setItem(j, ItemStack.parseOptional(provider, tag));
         }
      }

      this.reading = false;
   }

   public CraftingContainer getCraftingInv() {
      return this.craftMatrix;
   }

   public ResultContainer getCraftResult() {
      return this.craftResult;
   }

   public void craft(Player thePlayer) {
      if (this.currentRecipe.isPresent()) {
         Positioned craftinginput$positioned = this.craftMatrix.asPositionedCraftInput();
         CraftingInput craftinginput = craftinginput$positioned.input();
         NonNullList<ItemStack> remainder = ((CraftingRecipe)this.currentRecipe.get().value()).getRemainingItems(craftinginput);
         boolean playerInvUpdate = false;
         this.refillingGrid = true;
         int x = craftinginput$positioned.left();
         int y = craftinginput$positioned.top();

         for (int k = 0; k < craftinginput.height(); k++) {
            for (int l = 0; l < craftinginput.width(); l++) {
               int i = l + x + (k + y) * this.craftMatrix.getWidth();
               ItemStack slot = this.craftMatrix.getItem(i);
               ItemStack oldItem = slot.copy();
               ItemStack rem = (ItemStack)remainder.get(l + k * craftinginput.width());
               if (!slot.isEmpty()) {
                  this.craftMatrix.removeItem(i, 1);
                  slot = this.craftMatrix.getItem(i);
               }

               if (slot.isEmpty() && !oldItem.isEmpty()) {
                  StoredItemStack is = this.pullStack(new StoredItemStack(oldItem), 1L);
                  if (is == null && (this.getModes() & 32) != 0) {
                     for (int j = 0; j < thePlayer.getInventory().getContainerSize(); j++) {
                        ItemStack st = thePlayer.getInventory().getItem(j);
                        if (ItemStack.isSameItemSameComponents(oldItem, st)) {
                           st = thePlayer.getInventory().removeItem(j, 1);
                           if (!st.isEmpty()) {
                              is = new StoredItemStack(st, 1L);
                              playerInvUpdate = true;
                              break;
                           }
                        }
                     }
                  }

                  if (is != null) {
                     this.craftMatrix.setItem(i, is.getActualStack());
                     slot = this.craftMatrix.getItem(i);
                  }
               }

               if (!rem.isEmpty()) {
                  if (slot.isEmpty()) {
                     this.craftMatrix.setItem(i, rem);
                  } else {
                     rem = this.pushStack(rem);
                     if (!rem.isEmpty() && !thePlayer.getInventory().add(rem)) {
                        thePlayer.drop(rem, false);
                     }
                  }
               }
            }
         }

         this.refillingGrid = false;
         this.onCraftingMatrixChanged();
         this.craftingCooldown = this.craftingCooldown + this.craftResult.getItem(0).getCount();
         if (playerInvUpdate) {
            thePlayer.containerMenu.broadcastChanges();
         }
      }
   }

   public void unregisterCrafting(Player playerIn, CraftingTerminalMenu containerCraftingTerminal) {
      this.craftingListeners.remove(containerCraftingTerminal);
      if (this.polymorphPlayer != null && this.polymorphPlayer.get() == playerIn) {
         this.polymorphPlayer = null;
      }
   }

   public void registerCrafting(CraftingTerminalMenu containerCraftingTerminal) {
      this.craftingListeners.add(containerCraftingTerminal);
   }

   protected void onCraftingMatrixChanged() {
      if (!this.refillingGrid) {
         CraftingInput input = this.craftMatrix.asCraftInput();
         if (this.currentRecipe.isEmpty() || !((CraftingRecipe)this.currentRecipe.get().value()).matches(input, this.level)) {
            this.currentRecipe = this.getRecipe(input);
         }

         if (this.currentRecipe.isEmpty()) {
            this.craftResult.setItem(0, ItemStack.EMPTY);
         } else {
            this.craftResult.setItem(0, ((CraftingRecipe)this.currentRecipe.get().value()).assemble(input, this.level.registryAccess()));
         }

         this.craftingListeners.forEach(CraftingTerminalMenu::onCraftMatrixChanged);
         this.craftResult.setRecipeUsed(this.currentRecipe.orElse(null));
         if (!this.reading) {
            this.setChanged();
         }
      }
   }

   private Optional<RecipeHolder<CraftingRecipe>> getRecipe(CraftingInput input) {
      if (StorageMod.polymorph && this.polymorphPlayer != null) {
         Player pl = this.polymorphPlayer.get();
         return PolymorphHelper.getRecipe(pl, RecipeType.CRAFTING, input, this.level);
      } else {
         return this.level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, this.level);
      }
   }

   public void clear(Player player) {
      for (int i = 0; i < this.craftMatrix.getContainerSize(); i++) {
         ItemStack st = this.craftMatrix.removeItemNoUpdate(i);
         if (!st.isEmpty()) {
            StoredItemStack st0 = this.pushStack(new StoredItemStack(st));
            if (st0 != null) {
               ItemStack is = st0.getActualStack();
               player.getInventory().add(is);
               if (!is.isEmpty()) {
                  this.dropItem(is);
               }
            }
         }
      }

      this.onCraftingMatrixChanged();
   }

   @Override
   public void updateServer() {
      super.updateServer();
      this.craftingCooldown = 0;
   }

   public boolean canCraft() {
      return this.craftingCooldown + this.craftResult.getItem(0).getCount() <= this.craftResult.getItem(0).getMaxStackSize();
   }

   public void polymorphUpdate(Player playerIn) {
      this.polymorphPlayer = new WeakReference<>(playerIn);
      this.currentRecipe = Optional.empty();
      this.onCraftingMatrixChanged();
   }

   public void setCraftSlot(int x, int y, ItemStack actualStack) {
      this.craftMatrix.setItem(x + y * 3, actualStack);
   }

   public void clearContent() {
      this.craftMatrix.clearContent();
   }
}
