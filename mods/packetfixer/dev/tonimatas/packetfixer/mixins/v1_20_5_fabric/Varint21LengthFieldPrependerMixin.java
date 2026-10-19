package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_2552;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = class_2552.class, priority = 1001)
public class Varint21LengthFieldPrependerMixin {
   @ModifyConstant(
      method = "encode(Lio/netty/channel/ChannelHandlerContext;Lio/netty/buffer/ByteBuf;Lio/netty/buffer/ByteBuf;)V",
      constant = @Constant(intValue = 3)
   )
   private int packetfixer$newSize(int value) {
      return Config.getVarInt21Size();
   }
}
