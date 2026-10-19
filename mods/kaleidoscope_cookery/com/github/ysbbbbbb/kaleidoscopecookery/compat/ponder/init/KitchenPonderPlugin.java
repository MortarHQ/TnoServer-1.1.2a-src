package com.github.ysbbbbbb.kaleidoscopecookery.compat.ponder.init;

import javax.annotation.ParametersAreNonnullByDefault;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KitchenPonderPlugin implements PonderPlugin {
   public String getModId() {
      return "kaleidoscope_cookery";
   }

   public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
      KitchenBlockPonderScreen.register(helper);
   }

   public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
      KitchenBlockPonderTag.register(helper);
   }

   public static void init() {
      PonderIndex.addPlugin(new KitchenPonderPlugin());
   }
}
