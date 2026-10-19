package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_3248;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_3248.class)
public class ServerLoginPacketListenerImplMixin {
   @ModifyConstant(method = "tick", constant = @Constant(intValue = 600))
   private static int packetfixer$tick(int constant) {
      return Config.getTimeout() * 20;
   }
}
