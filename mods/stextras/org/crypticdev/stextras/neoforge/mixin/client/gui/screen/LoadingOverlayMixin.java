package org.crypticdev.stextras.neoforge.mixin.client.gui.screen;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.LoadingOverlay;
import net.minecraft.server.packs.resources.ReloadInstance;
import org.crypticdev.stextras.support.LoadingOverlayHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LoadingOverlay.class, priority = 4000)
public abstract class LoadingOverlayMixin implements LoadingOverlayHooks.Access {
   @Shadow
   @Final
   private boolean fadeIn;
   @Shadow
   private long fadeInStart;
   @Shadow
   private long fadeOutStart;
   @Shadow
   @Final
   private Minecraft minecraft;
   @Shadow
   private float currentProgress;
   @Shadow
   @Final
   private ReloadInstance reload;
   @Shadow
   @Final
   private Consumer<Optional<Throwable>> onFinish;
   @Unique
   private int stextras$lastWidth;

   @Inject(method = "registerTextures(Lnet/minecraft/client/Minecraft;)V", at = @At("TAIL"), cancellable = true)
   private static void stextras$injectTextures(Minecraft mc, CallbackInfo ci) {
      LoadingOverlayHooks.injectTextures(mc, ci);
   }

   @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"), cancellable = true)
   private void stextras$injectRender(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      LoadingOverlayHooks.injectRender(this, gfx, mouseX, mouseY, partialTicks, ci);
   }

   @Override
   public boolean stextras$isFadeIn() {
      return this.fadeIn;
   }

   @Override
   public long stextras$getFadeInStart() {
      return this.fadeInStart;
   }

   @Override
   public void stextras$setFadeInStart(long value) {
      this.fadeInStart = value;
   }

   @Override
   public long stextras$getFadeOutStart() {
      return this.fadeOutStart;
   }

   @Override
   public void stextras$setFadeOutStart(long value) {
      this.fadeOutStart = value;
   }

   @Override
   public Minecraft stextras$getMinecraft() {
      return this.minecraft;
   }

   @Override
   public float stextras$getCurrentProgress() {
      return this.currentProgress;
   }

   @Override
   public void stextras$setCurrentProgress(float value) {
      this.currentProgress = value;
   }

   @Override
   public ReloadInstance stextras$getReload() {
      return this.reload;
   }

   @Override
   public Consumer<Optional<Throwable>> stextras$getOnFinish() {
      return this.onFinish;
   }

   @Override
   public int stextras$getLastWidth() {
      return this.stextras$lastWidth;
   }

   @Override
   public void stextras$setLastWidth(int value) {
      this.stextras$lastWidth = value;
   }
}
