package lain.mods.cos.impl.inventory;

import com.mojang.datafixers.util.Pair;
import java.util.Optional;
import javax.annotation.Nullable;
import lain.mods.cos.impl.ModObjects;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ContainerCosArmor extends RecipeBookMenu<CraftingInput, CraftingRecipe> {
   private static final ResourceLocation[] ARMOR_SLOT_TEXTURES = new ResourceLocation[]{
      InventoryMenu.EMPTY_ARMOR_SLOT_BOOTS,
      InventoryMenu.EMPTY_ARMOR_SLOT_LEGGINGS,
      InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE,
      InventoryMenu.EMPTY_ARMOR_SLOT_HELMET
   };
   private static final EquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EquipmentSlot[]{
      EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
   };
   private final Player player;
   private final CraftingContainer craftingInventory = new TransientCraftingContainer(this, 2, 2);
   private final ResultContainer craftResultInventory = new ResultContainer();

   public ContainerCosArmor(Inventory invPlayer, InventoryCosArmor invCosArmor, final Player player, int windowId) {
      super(ModObjects.getTypeContainerCosArmor(), windowId);
      this.player = player;
      this.addSlot(new ResultSlot(player, this.craftingInventory, this.craftResultInventory, 0, 154, 28));

      for (int i = 0; i < 2; i++) {
         for (int j = 0; j < 2; j++) {
            this.addSlot(new Slot(this.craftingInventory, j + i * 2, 98 + j * 18, 18 + i * 18));
         }
      }

      for (int k = 0; k < 4; k++) {
         final EquipmentSlot equipmentslottype = VALID_EQUIPMENT_SLOTS[k];
         this.addSlot(
            new Slot(invPlayer, 39 - k, 8, 8 + k * 18) {
               public void setByPlayer(ItemStack pNewStack, ItemStack pOldStack) {
                  player.onEquipItem(equipmentslottype, pOldStack, pNewStack);
                  super.setByPlayer(pNewStack, pOldStack);
               }

               public boolean mayPickup(Player playerIn) {
                  ItemStack itemstack = this.getItem();
                  return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.has(itemstack, EnchantmentEffectComponents.PREVENT_ARMOR_CHANGE))
                     && super.mayPickup(playerIn);
               }

               @Nullable
               @OnlyIn(Dist.CLIENT)
               public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                  return Pair.of(InventoryMenu.BLOCK_ATLAS, ContainerCosArmor.ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
               }

               public int getMaxStackSize() {
                  return 1;
               }

               public boolean mayPlace(ItemStack stack) {
                  return stack.canEquip(equipmentslottype, player);
               }
            }
         );
      }

      for (int l = 0; l < 3; l++) {
         for (int j1 = 0; j1 < 9; j1++) {
            this.addSlot(new Slot(invPlayer, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
         }
      }

      for (int i1 = 0; i1 < 9; i1++) {
         this.addSlot(new Slot(invPlayer, i1, 8 + i1 * 18, 142));
      }

      this.addSlot(new Slot(invPlayer, 40, 77, 62) {
         @Nullable
         @OnlyIn(Dist.CLIENT)
         public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
         }
      });

      for (int i = 0; i < 4; i++) {
         final EquipmentSlot equipmentslottype = VALID_EQUIPMENT_SLOTS[i];
         this.addSlot(new Slot(invCosArmor, 3 - i, 98 + i * 18, 62) {
            @Nullable
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
               return Pair.of(InventoryMenu.BLOCK_ATLAS, ContainerCosArmor.ARMOR_SLOT_TEXTURES[equipmentslottype.getIndex()]);
            }

            public int getMaxStackSize() {
               return 1;
            }

            public boolean mayPlace(ItemStack stack) {
               return stack.canEquip(equipmentslottype, player);
            }
         });
      }
   }

   private static void updateCrafting(
      AbstractContainerMenu menu, Level world, Player player, CraftingContainer craftingInventory, ResultContainer craftResultInventory
   ) {
      if (!world.isClientSide) {
         CraftingInput craftingInput = craftingInventory.asCraftInput();
         ServerPlayer serverplayer = (ServerPlayer)player;
         ItemStack stack = ItemStack.EMPTY;
         Optional<RecipeHolder<CraftingRecipe>> optional = world.getServer().getRecipeManager().getRecipeFor(RecipeType.CRAFTING, craftingInput, world);
         if (optional.isPresent()) {
            RecipeHolder<CraftingRecipe> holder = optional.get();
            CraftingRecipe recipe = (CraftingRecipe)holder.value();
            if (craftResultInventory.setRecipeUsed(world, serverplayer, holder)) {
               ItemStack stack1 = recipe.assemble(craftingInput, world.registryAccess());
               if (stack1.isItemEnabled(world.enabledFeatures())) {
                  stack = stack1;
               }
            }
         }

         craftResultInventory.setItem(0, stack);
         menu.setRemoteSlot(0, stack);
         serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, stack));
      }
   }

   public boolean stillValid(Player playerIn) {
      return true;
   }

   public boolean canTakeItemForPickAll(ItemStack stack, Slot slotIn) {
      return slotIn.container != this.craftResultInventory && super.canTakeItemForPickAll(stack, slotIn);
   }

   public void clearCraftingContent() {
      this.craftResultInventory.clearContent();
      this.craftingInventory.clearContent();
   }

   public void fillCraftSlotsStackedContents(StackedContents arg0) {
      this.craftingInventory.fillStackedContents(arg0);
   }

   public RecipeBookType getRecipeBookType() {
      return RecipeBookType.CRAFTING;
   }

   public boolean shouldMoveToInventory(int slotIndex) {
      return slotIndex != this.getResultSlotIndex();
   }

   public int getGridHeight() {
      return this.craftingInventory.getHeight();
   }

   public int getResultSlotIndex() {
      return 0;
   }

   public int getSize() {
      return 5;
   }

   public int getGridWidth() {
      return this.craftingInventory.getWidth();
   }

   public boolean recipeMatches(RecipeHolder<CraftingRecipe> pRecipe) {
      return ((CraftingRecipe)pRecipe.value()).matches(this.craftingInventory.asCraftInput(), this.player.level());
   }

   public void removed(Player playerIn) {
      super.removed(playerIn);
      this.craftResultInventory.clearContent();
      if (!playerIn.level().isClientSide) {
         this.clearContainer(playerIn, this.craftingInventory);
      }
   }

   public void slotsChanged(Container inventoryIn) {
      updateCrafting(this, this.player.level(), this.player, this.craftingInventory, this.craftResultInventory);
   }

   public ItemStack quickMoveStack(Player player, int slotIndex) {
      ItemStack stack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(slotIndex);
      if (slot != null && slot.hasItem()) {
         ItemStack stack1 = slot.getItem();
         stack = stack1.copy();
         EquipmentSlot desiredSlot = player.getEquipmentSlotForItem(stack);
         if (slotIndex == 0) {
            if (!this.moveItemStackTo(stack1, 9, 45, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack1, stack);
         } else if (slotIndex >= 1 && slotIndex < 5) {
            if (!this.moveItemStackTo(stack1, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (slotIndex >= 5 && slotIndex < 9) {
            if (!this.moveItemStackTo(stack1, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (slotIndex >= 46 && slotIndex < 50) {
            if (!this.moveItemStackTo(stack1, 9, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (desiredSlot.getType() == Type.HUMANOID_ARMOR && !((Slot)this.slots.get(8 - desiredSlot.getIndex())).hasItem()) {
            int j = 8 - desiredSlot.getIndex();
            if (!this.moveItemStackTo(stack1, j, j + 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (desiredSlot.getType() == Type.HUMANOID_ARMOR && !((Slot)this.slots.get(49 - desiredSlot.getIndex())).hasItem()) {
            int j = 49 - desiredSlot.getIndex();
            if (!this.moveItemStackTo(stack1, j, j + 1, false)) {
               return ItemStack.EMPTY;
            }
         } else if (slotIndex >= 9 && slotIndex < 36) {
            if (!this.moveItemStackTo(stack1, 36, 45, false)) {
               return ItemStack.EMPTY;
            }
         } else if (slotIndex >= 36 && slotIndex < 45) {
            if (!this.moveItemStackTo(stack1, 9, 36, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(stack1, 9, 45, false)) {
            return ItemStack.EMPTY;
         }

         if (stack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (stack1.getCount() == stack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, stack1);
         if (slotIndex == 0) {
            player.drop(stack1, false);
         }
      }

      return stack;
   }
}
