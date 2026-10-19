package com.foxablazeaqzl.wiki.mixin.client;

import com.foxablazeaqzl.wiki.client.FoxWikiTabButtons;
import io.github.manasmods.tensura.client.screen.templates.SimpleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(
   targets = {
         "com.trbeyond.gui.tensura.MainScreen",
         "com.trbeyond.gui.tensura.MissionsScreen",
         "com.trbeyond.gui.tensura.ContractsScreen",
         "com.trbeyond.gui.tensura.CharacterScreen",
         "com.trbeyond.gui.tensura.EvolutionScreen"
   },
   remap = false
)
public abstract class TrBeyondTensuraScreenMixin extends SimpleScreen {
   protected TrBeyondTensuraScreenMixin(Component title, int width, int height) {
      super(title, width, height);
   }

   @Inject(method = {"init()V", "method_25426()V"}, at = @At("TAIL"), remap = false, require = 0)
   private void foxWiki$addWikiButton(CallbackInfo ci) {
      FoxWikiTabButtons.disableOriginalThirdRightTab(this, guiLeft, guiTop);
      this.addRenderableWidget(FoxWikiTabButtons.createWikiButton(guiLeft, guiTop));
   }
}
