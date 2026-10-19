package net.blay09.mods.trashslot.network;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.trashslot.TrashHelper;
import net.blay09.mods.trashslot.api.ItemTrashedEvent;
import net.blay09.mods.trashslot.api.ItemUntrashedEvent;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class MessageTrashSlotClick implements CustomPacketPayload {
   public static Type<MessageTrashSlotClick> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("trashslot", "trash_slot_click"));
   public static StreamCodec<RegistryFriendlyByteBuf, MessageTrashSlotClick> CODEC = StreamCodec.composite(
      ItemStack.OPTIONAL_STREAM_CODEC, it -> it.itemStack, ByteBufCodecs.BOOL, it -> it.isRightClick, MessageTrashSlotClick::new
   );
   private final ItemStack itemStack;
   private final boolean isRightClick;

   public MessageTrashSlotClick(ItemStack itemStack, boolean isRightClick) {
      this.itemStack = itemStack;
      this.isRightClick = isRightClick;
   }

   public static void encode(RegistryFriendlyByteBuf buf, MessageTrashSlotClick message) {
      ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, message.itemStack);
      buf.writeBoolean(message.isRightClick);
   }

   public static MessageTrashSlotClick decode(RegistryFriendlyByteBuf buf) {
      ItemStack itemStack = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
      boolean isRightClick = buf.readBoolean();
      return new MessageTrashSlotClick(itemStack, isRightClick);
   }

   public static void handle(ServerPlayer player, MessageTrashSlotClick message) {
      if (!player.isSpectator()) {
         ItemStack actualMouseItem = player.containerMenu.getCarried().copy();
         if (TrashHelper.canDelete(actualMouseItem)) {
            if (ItemStack.isSameItem(actualMouseItem, message.itemStack)) {
               if (actualMouseItem.isEmpty()) {
                  ItemStack trashStack = TrashHelper.getTrashItem(player);
                  ItemStack mouseStack = message.isRightClick ? trashStack.split(1) : trashStack;
                  player.containerMenu.setCarried(mouseStack);
                  TrashHelper.setTrashItem(player, message.isRightClick ? trashStack : ItemStack.EMPTY);
                  Balm.getEvents().fireEvent(new ItemUntrashedEvent(player, mouseStack));
               } else {
                  ItemStack trashStack = message.isRightClick ? actualMouseItem.split(1) : actualMouseItem;
                  ItemTrashedEvent.Pre preEvent = new ItemTrashedEvent.Pre(player, trashStack);
                  Balm.getEvents().fireEvent(preEvent);
                  if (preEvent.isCanceled()) {
                     return;
                  }

                  TrashHelper.setTrashItem(player, trashStack);
                  player.containerMenu.setCarried(message.isRightClick ? actualMouseItem : ItemStack.EMPTY);
                  Balm.getEvents().fireEvent(new ItemTrashedEvent.Post(player, trashStack));
               }
            }
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
