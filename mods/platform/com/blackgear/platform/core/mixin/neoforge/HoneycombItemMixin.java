package com.blackgear.platform.core.mixin.neoforge;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Map;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneycombItem.class)
public class HoneycombItemMixin {
   @Inject(method = "lambda$static$0()Lcom/google/common/collect/BiMap;", at = @At("RETURN"), cancellable = true)
   private static void createUnwaxedToWaxedMap(CallbackInfoReturnable<BiMap<Block, Block>> cir) {
      cir.setReturnValue(HashBiMap.create((Map)cir.getReturnValue()));
   }
}
