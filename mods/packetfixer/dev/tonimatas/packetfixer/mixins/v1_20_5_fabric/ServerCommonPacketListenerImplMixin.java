package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_8609;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_8609.class)
public class ServerCommonPacketListenerImplMixin {
   @ModifyConstant(method = "keepConnectionAlive", constant = @Constant(longValue = 15000L))
   private long packetfixer$tick$1(long constant) {
      return Config.getTimeout() * 1000L;
   }
}
