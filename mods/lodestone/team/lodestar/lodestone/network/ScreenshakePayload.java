package team.lodestar.lodestone.network;

import java.util.function.Consumer;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeBuilder;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class ScreenshakePayload extends OneSidedPayloadData {
   public final ScreenshakeInstance instance;

   public ScreenshakePayload(FriendlyByteBuf byteBuf) {
      this((ScreenshakeInstance)ScreenshakeInstance.STREAM_CODEC.decode(byteBuf));
   }

   public ScreenshakePayload(Consumer<ScreenshakeBuilder> constructor) {
      ScreenshakeBuilder builder = ScreenshakeBuilder.create();
      constructor.accept(builder);
      this.instance = builder.build();
   }

   public ScreenshakePayload(ScreenshakeInstance instance) {
      this.instance = instance;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void handle(IPayloadContext context) {
      ScreenshakeHandler.addScreenshake(this.instance);
   }

   @Override
   public void serialize(FriendlyByteBuf byteBuf) {
      ScreenshakeInstance.STREAM_CODEC.encode(byteBuf, this.instance);
   }
}
