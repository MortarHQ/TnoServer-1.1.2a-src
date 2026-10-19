package it.hurts.octostudios.reliquified_twilight_forest.network;

import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.ScaledCloakItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ScaledCloakWallClimbPacket(boolean isColliding) implements CustomPacketPayload {
   public static final Type<ScaledCloakWallClimbPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "scaled_cloak_wall_climb")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ScaledCloakWallClimbPacket> STREAM_CODEC = CustomPacketPayload.codec(
      ScaledCloakWallClimbPacket::write, ScaledCloakWallClimbPacket::new
   );

   public ScaledCloakWallClimbPacket(RegistryFriendlyByteBuf buf) {
      this(buf.readBoolean());
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeBoolean(this.isColliding);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(ScaledCloakWallClimbPacket packet, IPayloadContext ctx) {
      if (!ctx.flow().isClientbound()) {
         ctx.enqueueWork(() -> {
            Player player = ctx.player();
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.SCALED_CLOAK.get());
            int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
            if (stack.getItem() instanceof ScaledCloakItem relic) {
               int var7 = (int)Math.round(relic.getStatValue(stack, "wall_crawler", "max_time"));
               if (packet.isColliding()) {
                  player.fallDistance = 0.0F;
                  if (time > 0) {
                     stack.set(DataComponentRegistry.TIME, --time);
                  }

                  if (player.tickCount % 20 == 0) {
                     relic.spreadRelicExperience(player, stack, 1);
                  }
               }

               if (player.onGround() && time != var7) {
                  stack.set(DataComponentRegistry.TIME, var7);
               }
            }
         });
      }
   }
}
