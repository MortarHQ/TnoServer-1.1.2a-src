package com.bobmowzie.mowziesmobs.server.message;

import com.bobmowzie.mowziesmobs.MMCommon;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record MessageBlackPinkInYourArea(int entityId) implements CustomPacketPayload {
   public static final Type<MessageBlackPinkInYourArea> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("mowziesmobs", "message_black_pink_in_your_area"));
   public static final StreamCodec<ByteBuf, MessageBlackPinkInYourArea> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, MessageBlackPinkInYourArea::entityId, MessageBlackPinkInYourArea::new
   );

   public static MessageBlackPinkInYourArea fromMinecraft(AbstractMinecart minecart) {
      return new MessageBlackPinkInYourArea(minecart.getId());
   }

   public static void handleClient(MessageBlackPinkInYourArea packet, IPayloadContext context) {
      context.enqueueWork(() -> {
         Level world = MMCommon.PROXY.getClientLevel();
         assert world != null;
         if (world.getEntity(packet.entityId) instanceof AbstractMinecart minecart) {
            MMCommon.PROXY.playBlackPinkSound(minecart);
            BlockPos pos = minecart.blockPosition();
            BlockState state = Blocks.STONE.defaultBlockState();
            SoundType sound = state.getBlock().getSoundType(state, world, pos, minecart);
            float scale = 0.75F;
            double x = minecart.getX();
            double y = minecart.getY() + 0.375 + 0.5 + (minecart.getDefaultDisplayOffset() - 8) / 16.0F * 0.75F;
            double z = minecart.getZ();
            world.playLocalSound(x, y, z, sound.getBreakSound(), minecart.getSoundSource(), (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F, false);
            MMCommon.PROXY.minecartParticles(world, minecart, 0.75F, x, y, z, state, pos);
         }
      });
   }

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
