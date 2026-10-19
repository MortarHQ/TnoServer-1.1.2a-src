package net.unusual.block_factorys_bosses.network;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.attachment.entity.PlayerAnimationHandler;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record PlayerAnimationMessage(PlayerAnimationHandler.QueuedPlayerAnimation animation, int id) implements CustomPacketPayload {
   public static final Type<PlayerAnimationMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", "player_animation_msg"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PlayerAnimationMessage> STREAM_CODEC = StreamCodec.of(
      (buffer, message) -> {
         buffer.writeJsonWithCodec(PlayerAnimationHandler.QueuedPlayerAnimation.CODEC, message.animation);
         buffer.writeInt(message.id);
      },
      buffer -> new PlayerAnimationMessage(
         (PlayerAnimationHandler.QueuedPlayerAnimation)buffer.readJsonWithCodec(PlayerAnimationHandler.QueuedPlayerAnimation.CODEC), buffer.readInt()
      )
   );

   public PlayerAnimationMessage(PlayerAnimationHandler.TimedPlayerAnimation animation, int id) {
      this(new PlayerAnimationHandler.QueuedPlayerAnimation(List.of(animation)), id);
   }

   public Type<PlayerAnimationMessage> type() {
      return TYPE;
   }

   public static void handleData(PlayerAnimationMessage message, IPayloadContext context) {
      if (context.flow() == PacketFlow.CLIENTBOUND) {
         context.enqueueWork(() -> PlayerAnimationMessage.ClientHandler.handle(message, context)).exceptionally(e -> {
            context.connection().disconnect(Component.literal(e.getMessage()));
            return null;
         });
      }
   }

   @SubscribeEvent
   public static void registerMessage(FMLCommonSetupEvent event) {
      BossesRise.addNetworkMessage(TYPE, STREAM_CODEC, PlayerAnimationMessage::handleData);
   }

   private interface ClientHandler {
      static void handle(PlayerAnimationMessage message, IPayloadContext context) {
         if (context.player().level() instanceof ClientLevel level && level.getEntity(message.id()) instanceof Player player) {
            PlayerAnimationHandler.fromPlayer(player).startAnimation(player, message.animation());
         }
      }
   }
}
