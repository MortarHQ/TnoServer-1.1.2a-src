package cc.sighs.oelib.dev.example.net;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.dev.example.ExampleBlock;
import cc.sighs.oelib.dev.example.FluidRenderExampleMenu;
import cc.sighs.oelib.network.api.INetworkContext;
import cc.sighs.oelib.network.api.INetworkPacket;
import cc.sighs.oelib.network.api.NetworkPacket;
import cc.sighs.oelib.network.api.Side;
import cc.sighs.oelib.network.serialization.JsonCodec;
import cc.sighs.oelib.network.serialization.NetFieldCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantment;

@NetworkPacket(modId = "oelib", id = "open_gui", side = Side.SERVER)
public record OpenGuiPacket(
   BlockPos pos, @JsonCodec(holder = ComponentSerialization.class) Component title, @NetFieldCodec(holder = Enchantment.class) Holder<Enchantment> enchantment
) implements INetworkPacket<OpenGuiPacket> {
   @Override
   public void handle(INetworkContext context) {
      context.enqueueWork(() -> {
         ServerPlayer player = context.sender();
         if (player != null) {
            double distanceSq = player.distanceToSqr(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5);
            if (distanceSq <= 4.0 && player.level().getBlockState(this.pos).getBlock() instanceof ExampleBlock) {
               player.openMenu(new FluidRenderExampleMenu.Provider(this.pos));
               String enchantmentName = "unknown";
               if (this.enchantment != null) {
                  enchantmentName = this.enchantment.unwrapKey().map(k -> k.location().toString()).orElse("unregistered");
               } else {
                  OELib.LOGGER.warn("Received OpenGuiPacket with null enchantment!");
               }

               OELib.LOGGER.info("Open fluid example: {} with enchantment {}", this.title.getString(), enchantmentName);
            }
         }
      });
   }
}
