package net.blay09.mods.waystones.network.message;

import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.waystones.block.entity.WaystoneBlockEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class RequestEditWaystoneMessage implements CustomPacketPayload {
   public static final Type<RequestEditWaystoneMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "request_edit_waystone"));
   private final BlockPos pos;

   public RequestEditWaystoneMessage(BlockPos pos) {
      this.pos = pos;
   }

   public static void encode(FriendlyByteBuf buf, RequestEditWaystoneMessage message) {
      buf.writeBlockPos(message.pos);
   }

   public static RequestEditWaystoneMessage decode(FriendlyByteBuf buf) {
      BlockPos pos = buf.readBlockPos();
      return new RequestEditWaystoneMessage(pos);
   }

   public static void handle(ServerPlayer player, RequestEditWaystoneMessage message) {
      BlockPos pos = message.pos;
      if (!(player.distanceToSqr(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F) > 64.0)) {
         if (player.level().getBlockEntity(pos) instanceof WaystoneBlockEntityBase waystoneBlockEntity) {
            waystoneBlockEntity.getSettingsMenuProvider().ifPresent(menuProvider -> Balm.getNetworking().openGui(player, menuProvider));
         }
      }
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
