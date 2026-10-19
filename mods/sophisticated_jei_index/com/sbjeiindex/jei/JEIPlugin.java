package com.sbjeiindex.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
   public static final ResourceLocation PLUGIN_UID = ResourceLocation.fromNamespaceAndPath("sophisticated_jei_index", "jei_plugin");

   public ResourceLocation getPluginUid() {
      return PLUGIN_UID;
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
   }
}
