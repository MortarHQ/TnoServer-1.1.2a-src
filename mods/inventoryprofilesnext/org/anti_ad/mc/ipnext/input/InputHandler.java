package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.common.gui.debug.DepthTestScreen;
import org.anti_ad.mc.common.gui.debug.SpriteTestScreen;
import org.anti_ad.mc.common.gui.screen.BaseOverlay;
import org.anti_ad.mc.common.input.GlobalInputHandler;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaScreenUtil;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.glue.IScreenMarker;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.Hotkeys;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.debug.ModpackInputHandler;
import org.anti_ad.mc.ipnext.gui.DebugScreen;
import org.anti_ad.mc.ipnext.gui.inject.ContainerScreenEventHandler;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.parser.CustomDataFileLoader;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nInputHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InputHandler.kt\norg/anti_ad/mc/ipnext/input/InputHandler\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n74#2,7:95\n81#2,3:103\n1#3:102\n*S KotlinDebug\n*F\n+ 1 InputHandler.kt\norg/anti_ad/mc/ipnext/input/InputHandler\n*L\n49#1:95,7\n49#1:103,3\n*E\n"
)
public final class InputHandler implements IInputHandler {
   @NotNull
   public static final InputHandler INSTANCE = new InputHandler();

   private InputHandler() {
   }

   public final boolean onInput(int var1, int var2) {
      try {
         if (ModpackInputHandler.INSTANCE.onInput(var1, var2)) {
            return true;
         }

         if (Hotkeys.INSTANCE.getRELOAD_CUSTOM_CONFIGS().isActivated() && VanillaUtil.INSTANCE.inGame() && Vanilla.INSTANCE.worldNullable() != null) {
            CustomDataFileLoader.INSTANCE.reload(true);
         }

         if (Hotkeys.INSTANCE.getOPEN_GUI_EDITOR().isActivated()) {
            ContainerScreenEventHandler.INSTANCE.showEditor();
            return true;
         }

         if (InventoryInputHandler.INSTANCE.onInput(var1, var2)) {
            return true;
         }

         if (ModSettings.INSTANCE.getDEBUG().getBooleanValue()) {
            BaseOverlay var4;
            if ((
                  var4 = Debugs.INSTANCE.getDEBUG_SCREEN().isActivated()
                     ? (BaseOverlay)(new DebugScreen())
                     : (
                        Debugs.INSTANCE.getSCREEN_DEPTH_TEST().isActivated()
                           ? (BaseOverlay)(new DepthTestScreen())
                           : (Debugs.INSTANCE.getSCREEN_SPRITE_TEST().isActivated() ? (BaseOverlay)(new SpriteTestScreen()) : null)
                     )
               )
               != null) {
               VanillaScreenUtil.INSTANCE.openDistinctScreenQuiet((IScreenMarker)var4);
               return true;
            }

            if (Debugs.INSTANCE.getCLEAN_CURSOR().isActivated()) {
               GeneralInventoryActions.INSTANCE.cleanCursor();
            }
         }

         return false;
      } catch (Throwable var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public final void onClientInit() {
      GlobalInputHandler.INSTANCE.register(this);
      GlobalInputHandler.INSTANCE.registerCancellable(CancellableInputHandler.INSTANCE);
   }
}
