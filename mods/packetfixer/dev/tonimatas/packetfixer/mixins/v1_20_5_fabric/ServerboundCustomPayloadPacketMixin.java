package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_2817;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_2817.class)
public class ServerboundCustomPayloadPacketMixin {
   @ModifyConstant(method = "method_56475", constant = @Constant(intValue = 32767))
   private static int packetfixer$newSize(int value) {
      return Config.getPacketSize();
   }
}
