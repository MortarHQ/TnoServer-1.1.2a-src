package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_9135;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(class_9135.class)
public interface ByteBufCodecsMixin {
   @ModifyConstant(method = "*()Lnet/minecraft/nbt/NbtAccounter;", constant = @Constant(longValue = 2097152L))
   private static long newSize(long value) {
      return Config.getNbtMaxSize();
   }
}
