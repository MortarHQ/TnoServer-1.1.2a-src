package com.mafuyu404.oneenoughitem.client;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@EventBusSubscriber(modid = "oneenoughitem", value = Dist.CLIENT)
public class ModKeyMappings {
   public static final String CATEGORY = "key.categories.oneenoughitem";
   public static KeyMapping OPEN_EDITOR = new KeyMapping("key.oneenoughitem.open_editor", Type.KEYSYM, 82, "key.categories.oneenoughitem");

   @SubscribeEvent
   public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(OPEN_EDITOR);
   }
}
