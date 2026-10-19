package fuzs.illagerinvasion.world.inventory;

import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.illagerinvasion.init.ModSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;

public class ImbuingMenu extends AbstractContainerMenu {
   private final Container input;
   private final ResultContainer output = new ResultContainer();
   private final ContainerLevelAccess access;
   public final DataSlot invalidState;

   public ImbuingMenu(int containerId, Inventory inventory) {
      this(containerId, inventory, ContainerLevelAccess.NULL);
   }

   public ImbuingMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
      super((MenuType)ModRegistry.IMBUING_MENU_TYPE.value(), containerId);
      this.access = access;
      this.invalidState = this.addDataSlot(DataSlot.standalone());
      this.input = new SimpleContainer(3) {
         public void setChanged() {
            super.setChanged();
            ImbuingMenu.this.slotsChanged(this);
         }
      };
      this.addSlot(new Slot(this.input, 0, 26, 54) {
         public boolean mayPlace(ItemStack stack) {
            return stack.is(Items.ENCHANTED_BOOK);
         }
      });
      this.addSlot(new Slot(this.input, 1, 80, 54) {
         public boolean mayPlace(ItemStack stack) {
            return stack.isEnchanted();
         }
      });
      this.addSlot(new Slot(this.input, 2, 134, 54) {
         public boolean mayPlace(ItemStack stack) {
            return stack.is((Item)ModItems.HALLOWED_GEM_ITEM.value());
         }
      });
      this.addSlot(new Slot(this.output, 3, 80, 14) {
         public boolean mayPlace(ItemStack stack) {
            return false;
         }

         public boolean mayPickup(Player playerEntity) {
            return true;
         }

         public void onTake(Player playerEntity, ItemStack itemStack) {
            ImbuingMenu.this.input.setItem(0, ItemStack.EMPTY);
            ImbuingMenu.this.input.setItem(1, ItemStack.EMPTY);
            ItemStack stack = ImbuingMenu.this.input.getItem(2);
            stack.shrink(1);
            ImbuingMenu.this.input.setItem(2, stack);
            ImbuingMenu.this.invalidState.set(ImbuingMenu.InvalidImbuingState.ALL_GOOD.ordinal());
            playerEntity.playSound((SoundEvent)ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(), 1.0F, 1.0F);
         }
      });

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(inventory, i, 8 + i * 18, 142));
      }
   }

   public void slotsChanged(Container inventory) {
      if (inventory == this.input) {
         this.updateResult();
      }

      super.slotsChanged(inventory);
   }

   public boolean stillValid(Player player) {
      return stillValid(this.access, player, (Block)ModRegistry.IMBUING_TABLE_BLOCK.value());
   }

   public void updateResult() {
      ItemStack imbuingItem = this.input.getItem(1);
      ItemStack bookItem = this.input.getItem(0);
      ItemStack gemItem = this.input.getItem(2);
      ItemStack imbuingResult = imbuingItem.copy();
      if (!bookItem.isEmpty() && !gemItem.isEmpty() && !imbuingItem.isEmpty()) {
         ItemEnchantments bookEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(bookItem);
         if (bookEnchantments.size() != 1) {
            this.invalidState.set(ImbuingMenu.InvalidImbuingState.TOO_MANY_ENCHANTMENTS.ordinal());
         } else {
            Holder<Enchantment> enchantment = (Holder<Enchantment>)bookEnchantments.keySet().iterator().next();
            if (!enchantment.is(ModRegistry.IMBUING_ENCHANTMENT_TAG)) {
               this.invalidState.set(ImbuingMenu.InvalidImbuingState.INVALID_ENCHANTMENT.ordinal());
            } else if (bookEnchantments.getLevel(enchantment) != ((Enchantment)enchantment.value()).getMaxLevel()) {
               this.invalidState.set(ImbuingMenu.InvalidImbuingState.NOT_AT_MAX_LEVEL.ordinal());
            } else if (!((Enchantment)enchantment.value()).canEnchant(imbuingItem)) {
               this.invalidState.set(ImbuingMenu.InvalidImbuingState.INVALID_ITEM.ordinal());
            } else {
               ItemEnchantments imbueMap = EnchantmentHelper.getEnchantmentsForCrafting(imbuingItem);
               if (imbueMap.getLevel(enchantment) == ((Enchantment)enchantment.value()).getMaxLevel()) {
                  int imbueLevel = bookEnchantments.getLevel(enchantment) + 1;
                  imbuingResult.enchant(enchantment, imbueLevel);
                  this.output.setItem(0, imbuingResult);
                  this.invalidState.set(ImbuingMenu.InvalidImbuingState.ALL_GOOD.ordinal());
                  return;
               }

               this.invalidState.set(ImbuingMenu.InvalidImbuingState.AT_WRONG_LEVEL.ordinal());
            }
         }
      } else {
         this.invalidState.set(ImbuingMenu.InvalidImbuingState.ALL_GOOD.ordinal());
      }

      this.output.setItem(0, ItemStack.EMPTY);
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack itemStack = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot.hasItem()) {
         ItemStack itemStack2 = slot.getItem();
         itemStack = itemStack2.copy();
         if (index == 3) {
            if (!this.moveItemStackTo(itemStack2, 3, 39, true)) {
               return ItemStack.EMPTY;
            }

            slot.onQuickCraft(itemStack2, itemStack);
         } else if (index != 0 && index != 1 && index != 2) {
            if (index >= 3 && index < 39 && !this.moveItemStackTo(itemStack2, 0, 3, false)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemStack2, 3, 39, false)) {
            return ItemStack.EMPTY;
         }

         if (itemStack2.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (itemStack2.getCount() == itemStack.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(player, itemStack2);
      }

      return itemStack;
   }

   public void removed(Player player) {
      super.removed(player);
      this.access.execute((level, blockPos) -> this.clearContainer(player, this.input));
   }

   public enum InvalidImbuingState {
      ALL_GOOD(CommonComponents.EMPTY),
      TOO_MANY_ENCHANTMENTS(Component.translatable("container.imbue.tooManyEnchantments")),
      NOT_AT_MAX_LEVEL(Component.translatable("container.imbue.notAtMaxLevel")),
      AT_WRONG_LEVEL(Component.translatable("container.imbue.atWrongLevel")),
      INVALID_ENCHANTMENT(Component.translatable("container.imbue.invalidEnchantment")),
      INVALID_ITEM(Component.translatable("container.imbue.invalidItem"));

      public final Component component;

      InvalidImbuingState(Component component) {
         this.component = component;
      }

      public String getTranslationKey() {
         return ((TranslatableContents)this.component.getContents()).getKey();
      }
   }
}
