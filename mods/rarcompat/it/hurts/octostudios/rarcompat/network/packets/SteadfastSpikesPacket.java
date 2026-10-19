package it.hurts.octostudios.rarcompat.network.packets;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.feet.SteadfastSpikesItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class SteadfastSpikesPacket implements CustomPacketPayload {
   public static final Type<SteadfastSpikesPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("rarcompat", "spikes"));
   public static final StreamCodec<RegistryFriendlyByteBuf, SteadfastSpikesPacket> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, SteadfastSpikesPacket>() {
      public void encode(RegistryFriendlyByteBuf buf, SteadfastSpikesPacket packet) {
      }

      @Nonnull
      public SteadfastSpikesPacket decode(@Nonnull RegistryFriendlyByteBuf buf) {
         return new SteadfastSpikesPacket();
      }
   };

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.STEADFAST_SPIKES.value());
         if (stack.getItem() instanceof SteadfastSpikesItem) {
            player.fallDistance = 0.0F;
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof SteadfastSpikesPacket other) ? false : other.canEqual(this);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof SteadfastSpikesPacket;
   }

   @Override
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Override
   public String toString() {
      return "SteadfastSpikesPacket()";
   }
}
