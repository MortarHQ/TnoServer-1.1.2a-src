package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_2534;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_2534.class)
public class CompressionEncoderMixin {
   @ModifyConstant(
      method = "encode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V",
      constant = @Constant(intValue = 8388608)
   )
   private int packetfixer$newSize(int value) {
      return Config.getDecoderSize();
   }
}
