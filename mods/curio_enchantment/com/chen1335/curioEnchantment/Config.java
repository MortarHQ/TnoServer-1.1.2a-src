package com.chen1335.curioEnchantment;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

@EventBusSubscriber(modid = "curio_enchantment")
public class Config {
   private static final Builder BUILDER = new Builder();
   static final ModConfigSpec SPEC = BUILDER.build();

   @SubscribeEvent
   static void onLoad(ModConfigEvent event) {
   }
}
