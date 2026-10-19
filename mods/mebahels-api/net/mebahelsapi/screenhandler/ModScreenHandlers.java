package net.mebahelsapi.screenhandler;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.class_2338;
import net.minecraft.class_2378;
import net.minecraft.class_2960;
import net.minecraft.class_3917;
import net.minecraft.class_7923;

public class ModScreenHandlers {
   public static final class_3917<ModChestScreenHandler> BASE_CHEST_SCREEN_HANDLER = new ExtendedScreenHandlerType(
      ModChestScreenHandler::new, class_2338.field_48404.method_56430()
   );

   public static void registerScreenHandlers() {
      class_2378.method_10230(class_7923.field_41187, class_2960.method_60655("mebahelsapi", "base_chest"), BASE_CHEST_SCREEN_HANDLER);
   }
}
