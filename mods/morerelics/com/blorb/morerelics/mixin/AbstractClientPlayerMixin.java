package com.blorb.morerelics.mixin;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.render.MonochromePlayerLayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public class AbstractClientPlayerMixin {
   @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
   private void useCustomSkin(CallbackInfoReturnable<PlayerSkin> cir) {
      AbstractClientPlayer player = (AbstractClientPlayer)this;
      if (player != null && !player.isInvisible() && player.hasEffect(MoreRelics.NULLIFICATION)) {
         PlayerSkin originalSkin = (PlayerSkin)cir.getReturnValue();
         if (originalSkin != null) {
            ResourceLocation originalTexture = originalSkin.texture();
            GameProfile profile = player.getGameProfile();
            ResourceLocation customSkin = MonochromePlayerLayer.getGrayscaleSkin(originalTexture, profile).getNow(originalTexture);
            PlayerSkin modifiedSkin = new PlayerSkin(
               customSkin, originalSkin.textureUrl(), originalSkin.capeTexture(), originalSkin.elytraTexture(), originalSkin.model(), originalSkin.secure()
            );
            cir.setReturnValue(modifiedSkin);
         }
      }
   }
}
