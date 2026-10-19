package com.foxablazeaqzl.wiki.mixin.client;

import com.foxablazeaqzl.wiki.client.FoxWikiTabButtons;
import io.github.manasmods.tensura.client.screen.EvolutionScreen;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvolutionScreen.class)
public abstract class EvolutionScreenMixin extends SimpleScreen {
   protected EvolutionScreenMixin(Component title, int width, int height) {
      super(title, width, height);
   }

   @Inject(method = "init", at = @At("TAIL"))
   private void foxWiki$addWikiButton(CallbackInfo ci) {
      FoxWikiTabButtons.disableOriginalThirdRightTab(this, guiLeft, guiTop);
      this.addRenderableWidget(FoxWikiTabButtons.createWikiButton(guiLeft, guiTop));
   }
}
