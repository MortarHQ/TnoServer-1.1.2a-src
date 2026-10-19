package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.network.SlimeHumanFormPackets;
import com.tensura_tno.race.SlimeRaceHelper;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MainScreen.class, priority = 900, remap = false)
public abstract class MainScreenSlimeHumanFormCheckboxMixin {
   @Unique
   private static final int TENSURA_TNO$BOX_X = 50;
   @Unique
   private static final int TENSURA_TNO$BOX_Y = 120;
   @Unique
   private static final int TENSURA_TNO$BOX_SIZE = 7;

   @Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("TAIL"), require = 0)
   private void tensuraTno$renderSlimeHumanFormCheckbox(GuiGraphics graphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      if (tensuraTno$shouldShowCheckbox()) {
         int x = tensuraTno$boxScreenX();
         int y = tensuraTno$boxScreenY();
         graphics.fill(x, y, x + 7, y + 7, -16777216);
         graphics.fill(x + 1, y + 1, x + 7 - 1, y + 7 - 1, -11184811);
         if (tensuraTno$mouseOverBox(mouseX, mouseY)) {
            graphics.renderOutline(x, y, 7, 7, -8331009);
         }

         if (SlimeHumanFormPackets.clientChecked) {
            graphics.fill(x + 1, y + 3, x + 3, y + 5, -1);
            graphics.fill(x + 2, y + 4, x + 4, y + 6, -1);
            graphics.fill(x + 3, y + 3, x + 5, y + 5, -1);
            graphics.fill(x + 4, y + 2, x + 6, y + 4, -1);
            graphics.fill(x + 5, y + 1, x + 6, y + 3, -1);
         }

         if (tensuraTno$mouseOverBox(mouseX, mouseY)) {
            Minecraft minecraft = Minecraft.getInstance();
            graphics.renderTooltip(minecraft.font, Component.translatable("tensura_tno.main.slime_human_form"), mouseX, mouseY);
         }
      }
   }

   @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true, require = 0)
   private void tensuraTno$onSlimeHumanFormCheckboxClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
      if (button == 0) {
         if (tensuraTno$shouldShowCheckbox()) {
            if (tensuraTno$mouseOverBox((int)mouseX, (int)mouseY)) {
               SlimeHumanFormPackets.clientChecked = !SlimeHumanFormPackets.clientChecked;

               try {
                  NetworkManager.sendToServer(new SlimeHumanFormPackets.SetHumanFormPayload(SlimeHumanFormPackets.clientChecked));
               } catch (Throwable t) {
                  TensuraTNOMod.LOGGER.warn("[TensuraTNO] Failed to send slime human-form toggle packet: {}", t.toString());
               }

               try {
                  ScreenHelper.clicked();
               } catch (Throwable var8) {
               }

               cir.setReturnValue(true);
            }
         }
      }
   }

   @Unique
   private static boolean tensuraTno$shouldShowCheckbox() {
      if (!SlimeHumanFormPackets.clientUnlocked) {
         return false;
      }

      Minecraft minecraft = Minecraft.getInstance();
      return minecraft != null && minecraft.player != null && SlimeRaceHelper.isSlimeRace(minecraft.player);
   }

   @Unique
   private static int tensuraTno$boxScreenX() {
      return tensuraTno$readIntField(MainScreen.class, "guiLeft") + 50;
   }

   @Unique
   private static int tensuraTno$boxScreenY() {
      return tensuraTno$readIntField(MainScreen.class, "guiTop") + 120;
   }

   @Unique
   private static boolean tensuraTno$mouseOverBox(int mouseX, int mouseY) {
      int x = tensuraTno$boxScreenX();
      int y = tensuraTno$boxScreenY();
      return mouseX >= x && mouseX < x + 7 && mouseY >= y && mouseY < y + 7;
   }

   @Unique
   private static int tensuraTno$readIntField(Class<?> type, String name) {
      Field field = tensuraTno$findField(type, name);
      if (field == null) {
         return 0;
      }

      try {
         field.setAccessible(true);
         return Modifier.isStatic(field.getModifiers()) ? field.getInt(null) : field.getInt(type);
      } catch (IllegalAccessException ignored) {
         return 0;
      }
   }

   @Unique
   private static Field tensuraTno$findField(Class<?> type, String name) {
      for (Class<?> cursor = type; cursor != null; cursor = cursor.getSuperclass()) {
         try {
            return cursor.getDeclaredField(name);
         } catch (NoSuchFieldException ignored) {
         }
      }

      return null;
   }
}
