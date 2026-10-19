package net.darkhax.attributefix.impl;

import net.darkhax.attributefix.common.impl.AttributeFixMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod("attributefix")
@EventBusSubscriber(modid = "attributefix", bus = Bus.MOD)
public class NeoForgeMod {
   @SubscribeEvent
   public static void onLoadComplete(FMLLoadCompleteEvent event) {
      AttributeFixMod.getInstance().init();
   }
}
