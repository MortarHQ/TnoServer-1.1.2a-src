package net.blay09.mods.waystones.permission;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.resources.ResourceLocation;

public class ModPermissions {
   public static final ResourceLocation EDIT_ALL = ResourceLocation.fromNamespaceAndPath("waystones", "edit_all");

   public static void initialize() {
      Balm.getPermissions().registerPermission(EDIT_ALL, context -> context.getPlayer().map(it -> it.getAbilities().instabuild).orElse(false));
   }
}
