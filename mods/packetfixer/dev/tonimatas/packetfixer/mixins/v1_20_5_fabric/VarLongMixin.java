package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_8704;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_8704.class)
public class VarLongMixin {
   @ModifyConstant(method = "getByteSize", constant = @Constant(intValue = 10))
   private static int packetfixer$getByteSize$newSize(int value) {
      return Config.getVarLong();
   }

   @ModifyConstant(method = "read", constant = @Constant(intValue = 10))
   private static int packetfixer$read$newSize(int constant) {
      return Config.getVarLong();
   }

   @ModifyConstant(method = "read", constant = @Constant(stringValue = "VarLong too big"))
   private static String packetfixer$read$newSize(String constant) {
      return "VarInt too big. Packet Fixer configured to " + Config.getVarLong() + ". You can modify it in the Packet Fixer config.";
   }
}
