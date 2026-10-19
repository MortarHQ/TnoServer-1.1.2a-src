package it.hurts.octostudios.rarcompat.network.packets;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.charm.HeliumFlamingoItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class FlamingoSwimPacket implements CustomPacketPayload {
   private final boolean toggled;
   public static final Type<FlamingoSwimPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("rarcompat", "swim"));
   public static final StreamCodec<RegistryFriendlyByteBuf, FlamingoSwimPacket> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL, FlamingoSwimPacket::isToggled, FlamingoSwimPacket::new
   );

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.HELIUM_FLAMINGO.value());
         if (stack.getItem() instanceof HeliumFlamingoItem relic) {
            if (this.toggled) {
               player.setSprinting(true);
               relic.setToggled(stack, true);
            } else {
               player.setSprinting(false);
               relic.setTime(stack, (int)relic.getStatValue(stack, "flying", "time") + 10);
               relic.setToggled(stack, false);
            }
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public boolean isToggled() {
      return this.toggled;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FlamingoSwimPacket other)) {
         return false;
      } else {
         return !other.canEqual(this) ? false : this.isToggled() == other.isToggled();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof FlamingoSwimPacket;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      return result * 59 + (this.isToggled() ? 79 : 97);
   }

   @Override
   public String toString() {
      return "FlamingoSwimPacket(toggled=" + this.isToggled() + ")";
   }

   public FlamingoSwimPacket(boolean toggled) {
      this.toggled = toggled;
   }
}
