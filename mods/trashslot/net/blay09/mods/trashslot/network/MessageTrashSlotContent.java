package net.blay09.mods.trashslot.network;

import net.blay09.mods.trashslot.client.TrashSlotClient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class MessageTrashSlotContent implements CustomPacketPayload {
   public static Type<MessageTrashSlotContent> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("trashslot", "trash_slot_content"));
   private final ItemStack itemStack;

   public MessageTrashSlotContent(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public static void encode(RegistryFriendlyByteBuf buf, MessageTrashSlotContent message) {
      ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, message.itemStack);
   }

   public static MessageTrashSlotContent decode(RegistryFriendlyByteBuf buf) {
      ItemStack itemStack = (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);
      return new MessageTrashSlotContent(itemStack);
   }

   public static void handle(Player player, MessageTrashSlotContent message) {
      TrashSlotClient.receivedTrashSlotContent(message.itemStack);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
