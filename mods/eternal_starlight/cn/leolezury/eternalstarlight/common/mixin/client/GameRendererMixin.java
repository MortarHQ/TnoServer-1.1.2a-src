package cn.leolezury.eternalstarlight.common.mixin.client;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.entity.projectile.SoulitSpectator;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
   @Shadow
   @Nullable
   PostChain postEffect;

   @Shadow
   protected abstract void loadEffect(ResourceLocation var1);

   @Inject(method = "checkEntityPostEffect(Lnet/minecraft/world/entity/Entity;)V", at = @At("TAIL"))
   private void checkEntityPostEffect(Entity entity, CallbackInfo ci) {
      if (this.postEffect == null) {
         if (entity instanceof SoulitSpectator) {
            this.loadEffect(EternalStarlight.id("shaders/post/soulit_spectator.json"));
         } else if (ESClientHandler.tearyEffect) {
            this.loadEffect(ResourceLocation.withDefaultNamespace("shaders/post/blur.json"));
         }
      }
   }
}
