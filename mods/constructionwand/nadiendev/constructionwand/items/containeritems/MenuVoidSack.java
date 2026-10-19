package nadiendev.constructionwand.items.containeritems;

import nadiendev.constructionwand.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MenuVoidSack extends AbstractContainerMenu {
   private final SimpleContainer sackInventory;
   private final InteractionHand hand;
   private final Player player;
   private static final int SACK_ROWS = 4;
   private static final int SACK_COLS = 4;
   private static final int SACK_SIZE = 16;
   private static final int SACK_START_X = 52;
   private static final int SACK_START_Y = 18;
   private static final int PLAYER_START_X = 8;
   private static final int PLAYER_START_Y = 110;
   private static final int HOTBAR_START_Y = 168;

   public MenuVoidSack(int id, Inventory playerInv, InteractionHand hand) {
      super((MenuType)ModMenuTypes.VOID_SACK.get(), id);
      this.hand = hand;
      this.player = playerInv.player;
      ItemStack sack = this.player.getItemInHand(hand);
      this.sackInventory = ItemVoidSack.loadInventory(sack);
      this.buildSlots(playerInv);
   }

   public MenuVoidSack(int id, Inventory playerInv, FriendlyByteBuf buf) {
      super((MenuType)ModMenuTypes.VOID_SACK.get(), id);
      this.hand = buf.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
      this.player = playerInv.player;
      ItemStack sack = this.player.getItemInHand(this.hand);
      this.sackInventory = ItemVoidSack.loadInventory(sack);
      this.buildSlots(playerInv);
   }

   private void buildSlots(Inventory playerInv) {
      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 4; col++) {
            int index = row * 4 + col;
            this.addSlot(new Slot(this.sackInventory, index, 52 + col * 18, 18 + row * 18));
         }
      }

      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 110 + row * 18));
         }
      }

      for (int col = 0; col < 9; col++) {
         this.addSlot(new Slot(playerInv, col, 8 + col * 18, 168));
      }
   }

   public ItemStack quickMoveStack(Player player, int index) {
      ItemStack result = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(index);
      if (slot.hasItem()) {
         ItemStack stack = slot.getItem();
         result = stack.copy();
         if (index < 16) {
            if (!this.moveItemStackTo(stack, 16, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(stack, 0, 16, false)) {
            return ItemStack.EMPTY;
         }

         if (stack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return result;
   }

   public boolean stillValid(Player player) {
      ItemStack sack = player.getItemInHand(this.hand);
      return sack.getItem() instanceof ItemVoidSack;
   }

   public void removed(Player player) {
      super.removed(player);
      ItemStack sack = player.getItemInHand(this.hand);
      if (sack.getItem() instanceof ItemVoidSack) {
         ItemVoidSack.saveInventory(sack, this.sackInventory);
      }
   }

   public void toggleSendToContainer() {
      ItemStack sack = this.player.getItemInHand(this.hand);
      if (sack.getItem() instanceof ItemVoidSack) {
         boolean current = ItemVoidSack.isSendToContainer(sack);
         ItemVoidSack.setSendToContainer(sack, !current);
      }
   }

   public boolean isSendToContainer() {
      ItemStack sack = this.player.getItemInHand(this.hand);
      return ItemVoidSack.isSendToContainer(sack);
   }

   public boolean hasLinkedContainer() {
      ItemStack sack = this.player.getItemInHand(this.hand);
      return ItemVoidSack.getLinkedPos(sack) != null;
   }

   public InteractionHand getHand() {
      return this.hand;
   }
}
