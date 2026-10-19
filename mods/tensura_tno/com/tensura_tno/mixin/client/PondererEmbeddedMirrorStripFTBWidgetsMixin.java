package com.tensura_tno.mixin.client;

import com.tensura_tno.TensuraTNOMod;
import java.lang.reflect.Field;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "com.nododiiiii.ponderer.neoforge.sticksnapshot.client.ClientInputHandler", remap = false)
public class PondererEmbeddedMirrorStripFTBWidgetsMixin {
   @Inject(method = "attachMirrorToPonder(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At("TAIL"), remap = false, require = 0)
   private static void tensuraTno$stripFtbWidgets(Screen mirrorScreen, CallbackInfo ci) {
      if (mirrorScreen != null) {
         try {
            tensuraTno$removeFtbFromList(mirrorScreen.children());
            tensuraTno$removeFtbFromList(tensuraTno$readScreenListField(mirrorScreen, "renderables"));
            tensuraTno$removeFtbFromList(tensuraTno$readScreenListField(mirrorScreen, "narratables"));
         } catch (Throwable t) {
            TensuraTNOMod.LOGGER.debug("[tensura_tno] 镜像屏剥 FTB widget 失败: {}", t.toString());
         }
      }
   }

   private static void tensuraTno$removeFtbFromList(List<?> list) {
      if (list != null && !list.isEmpty()) {
         list.removeIf(entry -> {
            if (entry == null) {
               return false;
            }

            String name = entry.getClass().getName();
            return name.startsWith("dev.ftb.mods.");
         });
      }
   }

   private static List<?> tensuraTno$readScreenListField(Screen screen, String fieldName) {
      try {
         Field field = Screen.class.getDeclaredField(fieldName);
         field.setAccessible(true);
         if (field.get(screen) instanceof List<?> list) {
            return list;
         }
      } catch (ReflectiveOperationException var5) {
      }

      return null;
   }
}
