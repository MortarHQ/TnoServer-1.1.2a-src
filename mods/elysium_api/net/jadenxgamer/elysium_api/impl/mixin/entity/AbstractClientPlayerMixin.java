package net.jadenxgamer.elysium_api.impl.mixin.entity;

import java.util.List;
import net.jadenxgamer.elysium_api.ElysiumAPI;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {
   @Unique
   private static final List<String> DEV_CAPE_PLAYERS = List.of("JadenXgamer", "AlwaysMuddy", "Dev");

   @Shadow
   @Nullable
   protected abstract PlayerInfo getPlayerInfo();

   @Inject(method = "getSkin", at = @At("TAIL"))
   private void elysium_api$getCustomCapeTexture(CallbackInfoReturnable<PlayerSkin> cir) {
      PlayerInfo info = this.getPlayerInfo();
      if (info != null && DEV_CAPE_PLAYERS.contains(info.getProfile().getName())) {
         ResourceLocation capeTexture = ElysiumAPI.elysiumPath("textures/entity/cape/jadenxgamer.png");
         ((PlayerSkinAccessor)cir.getReturnValue()).elysium_api$setCapeTexture(capeTexture);
      }
   }
}
