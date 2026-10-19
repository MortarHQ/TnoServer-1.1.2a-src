package org.anti_ad.mc.ipnext.input;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.IInputHandler;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.config.Hotkeys;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.jetbrains.annotations.NotNull;

public final class InventoryInputHandler implements IInputHandler {
   @NotNull
   public static final InventoryInputHandler INSTANCE = new InventoryInputHandler();

   private InventoryInputHandler() {
   }

   public final boolean onInput(int var1, int var2) {
      if (!VanillaUtil.INSTANCE.inGame()) {
         return false;
      }

      Screen var5 = Vanilla.INSTANCE.screen();
      AbstractContainerMenu var7 = Vanilla.INSTANCE.container();
      if (var5 != null && var5 instanceof AbstractContainerScreen) {
         HintClassData var3 = HintsManagerNG.INSTANCE.getHints(var5.getClass());
         HintClassData var4 = HintsManagerNG.INSTANCE.getHints(var7.getClass());
         if (!var3.getIgnore() && !var4.getIgnore() && !InventoryKt.isInputFieldActive((AbstractContainerScreen)var5)) {
            GeneralInventoryActions var6 = GeneralInventoryActions.INSTANCE;
            if (!onInput$run$0(Hotkeys.INSTANCE.getSORT_INVENTORY(), var7, new InventoryInputHandler$onInput$1$1(var6))
               && !onInput$run$0(Hotkeys.INSTANCE.getSORT_INVENTORY_IN_COLUMNS(), var7, new InventoryInputHandler$onInput$1$2(var6))
               && !onInput$run$0(Hotkeys.INSTANCE.getSORT_INVENTORY_IN_ROWS(), var7, new InventoryInputHandler$onInput$1$3(var6))
               && !onInput$run(Hotkeys.INSTANCE.getMOVE_ALL_ITEMS(), new InventoryInputHandler$onInput$1$4(var6))
               && !onInput$run(Hotkeys.INSTANCE.getTHROW_ALL_ITEMS(), new InventoryInputHandler$onInput$1$5(var6))
               && !onInput$run(Hotkeys.INSTANCE.getDUMP_ITEM_NBT_TO_CHAT(), new InventoryInputHandler$onInput$1$6(var6))
               && !onInput$run(Hotkeys.INSTANCE.getCOPY_COMPONENTS(), new InventoryInputHandler$onInput$1$7(var6))
               && !onInput$run(Hotkeys.INSTANCE.getCOPY_ITEM_ID(), new InventoryInputHandler$onInput$1$8(var6))
               && !onInput$run(Hotkeys.INSTANCE.getSCROLL_TO_CHEST(), new InventoryInputHandler$onInput$1$9(var6))
               && !onInput$run(Hotkeys.INSTANCE.getSCROLL_TO_INVENTORY(), new InventoryInputHandler$onInput$1$10(var6))) {
               return false;
            }

            return true;
         }
      }

      return false;
   }

   private static final boolean onInput$run(ConfigHotkey var0, Function0 var1) {
      try {
         if (var0.isActivated()) {
            var1.invoke();
            return true;
         }
      } catch (Throwable var2) {
         var2.printStackTrace();
      }

      return false;
   }

   private static final boolean onInput$run$0(ConfigHotkey var0, AbstractContainerMenu var1, Function1 var2) {
      try {
         if (var0.isActivated()) {
            var2.invoke(var1);
            return true;
         }
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

      return false;
   }
}
