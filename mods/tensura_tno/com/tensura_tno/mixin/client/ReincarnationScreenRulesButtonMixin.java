package com.tensura_tno.mixin.client;

import com.tensura_tno.client.screen.ReincarnationRulesScreen;
import com.tensura_tno.network.RimuruModePackets;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ReincarnationScreen.class, priority = 900)
public abstract class ReincarnationScreenRulesButtonMixin {
   @Unique
   private static final ResourceLocation TENSURA_TNO$RULES_TEXTURE = ResourceLocation.fromNamespaceAndPath(
      "tensura_tno", "textures/gui/reincarnation/rules_panel.png"
   );
   @Unique
   private static final int TENSURA_TNO$RULE_BUTTON_X = 131;
   @Unique
   private static final int TENSURA_TNO$RULE_BUTTON_Y = 4;
   @Unique
   private static final int TENSURA_TNO$RULE_BUTTON_SIZE = 16;
   @Unique
   private boolean tensuraTno$fromCharacterReset = false;

   @Inject(method = "init", at = @At("TAIL"), require = 0, remap = false)
   private void tensuraTno$captureCharacterResetFlag(CallbackInfo ci) {
      this.tensuraTno$fromCharacterReset = RimuruModePackets.clientNextMenuFromCharacterResetForRules;
      RimuruModePackets.clientNextMenuFromCharacterResetForRules = false;
   }

   @Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At("TAIL"), require = 0)
   private void tensuraTno$renderRulesButton(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
      if (this.tensuraTno$shouldShowRulesButton()) {
         int u = this.tensuraTno$mouseOverRulesButton(mouseX, mouseY) ? 2 : 20;
         graphics.blit(TENSURA_TNO$RULES_TEXTURE, this.tensuraTno$buttonScreenX(), this.tensuraTno$buttonScreenY(), u, 168, 16, 16);
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, require = 0)
   private void tensuraTno$openRulesScreen(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (button == 0 && this.tensuraTno$mouseOverRulesButton((int)mouseX, (int)mouseY)) {
         ReincarnationRulesScreen.prepareOpenFromReincarnationScreen();
         Minecraft.getInstance().setScreen(new ReincarnationRulesScreen((Screen)this));
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
         cir.setReturnValue(true);
      }
   }

   @Unique
   private int tensuraTno$buttonScreenX() {
      ReincarnationScreen self = (ReincarnationScreen)this;
      return self.getGuiLeft() + 131;
   }

   @Unique
   private int tensuraTno$buttonScreenY() {
      ReincarnationScreen self = (ReincarnationScreen)this;
      return self.getGuiTop() + 4;
   }

   @Unique
   private boolean tensuraTno$mouseOverRulesButton(int mouseX, int mouseY) {
      if (!this.tensuraTno$shouldShowRulesButton()) {
         return false;
      }

      int x = this.tensuraTno$buttonScreenX();
      int y = this.tensuraTno$buttonScreenY();
      return mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16;
   }

   @Unique
   private boolean tensuraTno$shouldShowRulesButton() {
      try {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player == null) {
            return false;
         } else if (!mc.hasSingleplayerServer() && mc.player.getPermissionLevel() < 2) {
            return false;
         } else {
            ReincarnationScreen self = (ReincarnationScreen)this;
            ReincarnationMenu menu = (ReincarnationMenu)self.getMenu();
            if (menu == null || menu.isChangeRaceOnly() || this.tensuraTno$fromCharacterReset) {
               return false;
            } else {
               return menu instanceof ReincarnationMenuAccessor accessor ? accessor.tensuraTno$getRacePoolType() == 0 : false;
            }
         }
      } catch (Throwable t) {
         return false;
      }
   }
}
