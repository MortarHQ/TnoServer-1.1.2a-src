package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_2532;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_2532.class)
public class CompressionDecoderMixin {
   @ModifyConstant(method = "decode", constant = @Constant(intValue = 8388608))
   private int packetfixer$newSize(int value) {
      return Config.getDecoderSize();
   }
}
