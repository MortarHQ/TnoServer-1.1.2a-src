package net.darkhax.bookshelf.common.mixin.patch.potions;

import net.darkhax.bookshelf.common.api.service.Services;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionBrewing.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PotionBrewing.class)
public class MixinPotionBrewing {
   @Inject(method = "addVanillaMixes", at = @At("RETURN"))
   private static void onBootstrap(Builder builder, CallbackInfo ci) {
      Services.CONTENT_PROVIDERS.get().forEach(provider -> provider.registerBrewing(builder));
   }
}
