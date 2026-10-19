package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import net.minecraft.class_2550;
import net.minecraft.class_8703;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(class_2550.class)
public abstract class Varint21FrameDecoderMixin {
   @Mutable
   @Shadow
   @Final
   private ByteBuf field_45691;

   @Inject(method = "decode", at = @At("HEAD"))
   private void packetfixer$checkNumber(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list, CallbackInfo ci) {
      this.field_45691 = Unpooled.directBuffer(class_8703.method_53015(byteBuf.readableBytes()) + 2);
   }

   @ModifyConstant(method = "copyVarint", constant = @Constant(intValue = 3))
   private static int packetfixer$newSize(int value, ByteBuf byteBuf) {
      return class_8703.method_53015(byteBuf.readableBytes()) + 2;
   }
}
