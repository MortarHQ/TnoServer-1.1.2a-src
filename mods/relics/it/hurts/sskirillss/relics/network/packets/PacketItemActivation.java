package it.hurts.sskirillss.relics.network.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketItemActivation implements CustomPacketPayload {
   private final ItemStack stack;
   public static final Type<PacketItemActivation> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "item_activation"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PacketItemActivation> STREAM_CODEC = StreamCodec.composite(
      ItemStack.STREAM_CODEC, PacketItemActivation::getStack, PacketItemActivation::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> Minecraft.getInstance().gameRenderer.displayItemActivation(this.stack));
   }

   public ItemStack getStack() {
      return this.stack;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketItemActivation other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$stack = this.getStack();
         Object other$stack = other.getStack();
         return this$stack == null ? other$stack == null : this$stack.equals(other$stack);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketItemActivation;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $stack = this.getStack();
      return result * 59 + ($stack == null ? 43 : $stack.hashCode());
   }

   @Override
   public String toString() {
      return "PacketItemActivation(stack=" + this.getStack() + ")";
   }

   public PacketItemActivation(ItemStack stack) {
      this.stack = stack;
   }
}
