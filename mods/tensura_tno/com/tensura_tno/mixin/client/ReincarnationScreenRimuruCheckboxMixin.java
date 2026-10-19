package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.network.RimuruModePackets;
import com.tensura_tno.race.SlimeRaceHelper;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ReincarnationScreen.class, priority = 900)
public abstract class ReincarnationScreenRimuruCheckboxMixin {
   @Unique
   private static final int TENSURA_TNO$BOX_X = 162;
   @Unique
   private static final int TENSURA_TNO$BOX_Y = 150;
   @Unique
   private static final int TENSURA_TNO$BOX_SIZE = 10;
   @Unique
   private boolean tensuraTno$rimuruChecked = false;
   @Unique
   private boolean tensuraTno$fromCharacterReset = false;

   @Inject(method = "init", at = @At("TAIL"), require = 0, remap = false)
   private void tensuraTno$captureCharacterResetFlag(CallbackInfo ci) {
      this.tensuraTno$fromCharacterReset = RimuruModePackets.clientNextMenuFromCharacterReset;
      RimuruModePackets.clientNextMenuFromCharacterReset = false;
   }

   @Inject(method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At("TAIL"), require = 0)
   private void tensuraTno$renderRimuruCheckbox(GuiGraphics graphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci) {
      if (this.tensuraTno$shouldShowCheckbox()) {
         int x = this.tensuraTno$boxScreenX();
         int y = this.tensuraTno$boxScreenY();
         graphics.fill(x, y, x + 10, y + 10, -16777216);
         graphics.fill(x + 1, y + 1, x + 10 - 1, y + 10 - 1, -11184811);
         if (this.tensuraTno$mouseOverBox(mouseX, mouseY)) {
            graphics.renderOutline(x, y, 10, 10, -8331009);
         }

         if (this.tensuraTno$rimuruChecked) {
            graphics.fill(x + 2, y + 5, x + 4, y + 7, -1);
            graphics.fill(x + 4, y + 6, x + 8, y + 8, -1);
            graphics.fill(x + 5, y + 4, x + 8, y + 6, -1);
            graphics.fill(x + 6, y + 2, x + 8, y + 4, -1);
         }

         if (this.tensuraTno$mouseOverBox(mouseX, mouseY)) {
            graphics.renderTooltip(Minecraft.getInstance().font, Component.translatable("tensura_tno.reincarnation.rimuru_mode"), mouseX, mouseY);
         }
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, require = 0)
   private void tensuraTno$onCheckboxClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (button == 0) {
         if (this.tensuraTno$shouldShowCheckbox()) {
            if (this.tensuraTno$mouseOverBox((int)mouseX, (int)mouseY)) {
               this.tensuraTno$rimuruChecked = !this.tensuraTno$rimuruChecked;

               try {
                  NetworkManager.sendToServer(new RimuruModePackets.SetRimuruPendingPayload(this.tensuraTno$rimuruChecked));
               } catch (Throwable t) {
                  TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to send Rimuru-mode pending packet: {}", t.toString());
               }

               cir.setReturnValue(true);
            }
         }
      }
   }

   @Unique
   private boolean tensuraTno$shouldShowCheckbox() {
      try {
         Minecraft mc0 = Minecraft.getInstance();
         boolean integratedHost = mc0 != null && mc0.hasSingleplayerServer();
         if (!integratedHost && !RimuruModePackets.isClientCheckboxEnabled()) {
            return false;
         }

         ReincarnationScreen self = (ReincarnationScreen)this;
         ReincarnationMenu menu = (ReincarnationMenu)self.getMenu();
         if (menu == null) {
            return false;
         }

         if (menu.isChangeRaceOnly()) {
            return false;
         }

         if (this.tensuraTno$fromCharacterReset) {
            return false;
         }

         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null && mc.level != null) {
            try {
               if (mc.level.getGameRules().getBoolean(TensuraGameRules.RIMURU_MODE)) {
                  return false;
               }
            } catch (Throwable var10) {
            }

            int index = menu.selectedManasRaceIndex.get();
            List<ManasRace> pool = menu.getRacePool();
            if (pool != null && index >= 0 && index < pool.size()) {
               ManasRace race = pool.get(index);
               if (race == null) {
                  return false;
               }

               ResourceLocation id = race.getRegistryName();
               return id != null && SlimeRaceHelper.SLIME_RACE_IDS.contains(id);
            } else {
               return false;
            }
         } else {
            return false;
         }
      } catch (Throwable t) {
         return false;
      }
   }

   @Unique
   private int tensuraTno$boxScreenX() {
      ReincarnationScreen self = (ReincarnationScreen)this;
      return self.getGuiLeft() + 162;
   }

   @Unique
   private int tensuraTno$boxScreenY() {
      ReincarnationScreen self = (ReincarnationScreen)this;
      return self.getGuiTop() + 150;
   }

   @Unique
   private boolean tensuraTno$mouseOverBox(int mouseX, int mouseY) {
      int x = this.tensuraTno$boxScreenX();
      int y = this.tensuraTno$boxScreenY();
      return mouseX >= x && mouseX < x + 10 && mouseY >= y && mouseY < y + 10;
   }
}
