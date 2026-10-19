package dev.tonimatas.packetfixer.mixins.v1_20_5_fabric;

import dev.tonimatas.packetfixer.common.Config;
import net.minecraft.class_2505;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(class_2505.class)
public class NbtAccounterMixin {
   @Shadow
   @Final
   private long field_11557;

   @Redirect(method = "accountBytes(J)V", at = @At(value = "FIELD", target = "Lnet/minecraft/nbt/NbtAccounter;quota:J"))
   private long packetfixer$newSize(class_2505 instance) {
      return Config.isForceUnlimitedNbtEnabled() ? Config.getNbtMaxSize() : this.field_11557;
   }
}
