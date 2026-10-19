package org.anti_ad.mc.common.vanilla.accessors.menu;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.world.inventory.ItemCombinerMenu;
import org.jetbrains.annotations.NotNull;

public final class ItemCombinerMenuAccKt {
   public static final List get_inputSlotIndices_/* $VF was: get(inputSlotIndices) */(@NotNull ItemCombinerMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.inputSlotIndexes;
   }

   public static final EditBox get_nameField_/* $VF was: get(nameField) */(@NotNull AnvilScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.name;
   }

   public static final String get_nameFieldText_/* $VF was: get(nameFieldText) */(@NotNull AnvilScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.name.getValue();
   }

   public static final void set_nameFieldText_/* $VF was: set(nameFieldText) */(@NotNull AnvilScreen var0, String var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.name.setValue(var1);
   }
}
