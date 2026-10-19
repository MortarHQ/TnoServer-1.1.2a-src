package net.p3pp3rf1y.sophisticatedbackpacks.network;

import io.netty.buffer.ByteBuf;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.api.IBlockToolSwapUpgrade;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.util.PlayerInventoryProvider;

public record BlockToolSwapPayload(BlockPos pos) implements CustomPacketPayload {
   public static final Type<BlockToolSwapPayload> TYPE = new Type(SophisticatedBackpacks.getRL("block_tool_swap"));
   public static final StreamCodec<ByteBuf, BlockToolSwapPayload> STREAM_CODEC = StreamCodec.composite(
      BlockPos.STREAM_CODEC, BlockToolSwapPayload::pos, BlockToolSwapPayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(BlockToolSwapPayload payload, IPayloadContext context) {
      Player player = context.player();
      AtomicBoolean result = new AtomicBoolean(false);
      AtomicBoolean anyUpgradeCanInteract = new AtomicBoolean(false);
      PlayerInventoryProvider.get().runOnBackpacks(player, (backpack, inventoryName, identifier, slot) -> {
         BackpackWrapper.fromStack(backpack).getUpgradeHandler().getWrappersThatImplement(IBlockToolSwapUpgrade.class).forEach(upgrade -> {
            if (upgrade.canProcessBlockInteract() && !result.get()) {
               anyUpgradeCanInteract.set(true);
               result.set(upgrade.onBlockInteract(player.level(), payload.pos, player.level().getBlockState(payload.pos), player));
            }
         });
         return result.get();
      });
      if (!anyUpgradeCanInteract.get()) {
         player.displayClientMessage(Component.translatable("gui.sophisticatedbackpacks.status.no_tool_swap_upgrade_present"), true);
      } else {
         if (!result.get()) {
            player.displayClientMessage(Component.translatable("gui.sophisticatedbackpacks.status.no_tool_found_for_block"), true);
         }
      }
   }
}
