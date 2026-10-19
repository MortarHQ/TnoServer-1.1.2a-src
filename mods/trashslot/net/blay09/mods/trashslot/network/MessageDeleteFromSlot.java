package net.blay09.mods.trashslot.network;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.trashslot.TrashHelper;
import net.blay09.mods.trashslot.api.ItemTrashedEvent;
import net.blay09.mods.trashslot.api.TrashSlotEmptiedEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MessageDeleteFromSlot implements CustomPacketPayload {
   public static Type<MessageDeleteFromSlot> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("trashslot", "delete_from_slot"));
   private final int slotNumber;
   private final boolean isDeleteAll;

   public MessageDeleteFromSlot(int slotNumber, boolean isDeleteAll) {
      this.slotNumber = slotNumber;
      this.isDeleteAll = isDeleteAll;
   }

   public static void encode(FriendlyByteBuf buf, MessageDeleteFromSlot message) {
      buf.writeVarInt(message.slotNumber);
      buf.writeBoolean(message.isDeleteAll);
   }

   public static MessageDeleteFromSlot decode(FriendlyByteBuf buf) {
      int slotNumber = buf.readVarInt();
      boolean isDeleteAll = buf.readBoolean();
      return new MessageDeleteFromSlot(slotNumber, isDeleteAll);
   }

   public static void handle(ServerPlayer player, MessageDeleteFromSlot message) {
      if (!player.isSpectator()) {
         if (message.slotNumber == -1) {
            ItemStack itemStack = TrashHelper.getTrashItem(player);
            TrashHelper.setTrashItem(player, ItemStack.EMPTY);
            Balm.getNetworking().reply(new MessageTrashSlotContent(ItemStack.EMPTY));
            Balm.getEvents().fireEvent(new TrashSlotEmptiedEvent(player, itemStack));
         } else if (player.containerMenu.getCarried().isEmpty()) {
            AbstractContainerMenu container = player.containerMenu;
            Slot deleteSlot = (Slot)container.slots.get(message.slotNumber);
            if (!(deleteSlot instanceof ResultSlot)) {
               if (message.isDeleteAll) {
                  ItemStack deleteStack = deleteSlot.getItem().copy();
                  if (!deleteStack.isEmpty() && attemptDeleteFromSlot(player, container, message.slotNumber)) {
                     for (int i = 0; i < container.slots.size(); i++) {
                        ItemStack slotStack = ((Slot)container.slots.get(i)).getItem();
                        if (!slotStack.isEmpty() && ItemStack.isSameItemSameComponents(slotStack, deleteStack) && !attemptDeleteFromSlot(player, container, i)) {
                           break;
                        }
                     }
                  }
               } else {
                  attemptDeleteFromSlot(player, container, message.slotNumber);
               }

               Balm.getNetworking().reply(new MessageTrashSlotContent(TrashHelper.getTrashItem(player)));
            }
         }
      }
   }

   private static boolean attemptDeleteFromSlot(Player player, AbstractContainerMenu container, int slotNumber) {
      ItemStack itemStack = ((Slot)container.slots.get(slotNumber)).getItem().copy();
      if (!TrashHelper.canDelete(itemStack)) {
         return false;
      } else {
         container.clicked(slotNumber, 0, ClickType.PICKUP, player);
         ItemStack mouseStack = container.getCarried();
         ItemTrashedEvent.Pre preEvent = new ItemTrashedEvent.Pre(player, mouseStack);
         Balm.getEvents().fireEvent(preEvent);
         if (!preEvent.isCanceled() && ItemStack.matches(itemStack, mouseStack)) {
            container.setCarried(ItemStack.EMPTY);
            TrashHelper.setTrashItem(player, mouseStack);
            Balm.getEvents().fireEvent(new ItemTrashedEvent.Post(player, mouseStack));
            return !itemStack.isEmpty();
         } else {
            ((ServerPlayer)player).connection.send(new ClientboundContainerSetSlotPacket(-1, 0, 0, mouseStack));
            return false;
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
