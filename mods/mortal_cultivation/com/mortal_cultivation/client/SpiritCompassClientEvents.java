package com.mortal_cultivation.client;

import com.mortal_cultivation.registry.item.CultivationItems;
import net.minecraft.client.renderer.item.CompassItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.LodestoneTracker;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class SpiritCompassClientEvents {
   private SpiritCompassClientEvents() {
   }

   @SubscribeEvent
   public static void setup(FMLClientSetupEvent event) {
      event.enqueueWork(
         () -> ItemProperties.register(
            (Item)CultivationItems.SPIRIT_COMPASS.get(),
            ResourceLocation.withDefaultNamespace("angle"),
            new CompassItemPropertyFunction((level, stack, entity) -> {
               LodestoneTracker tracker = (LodestoneTracker)stack.get(DataComponents.LODESTONE_TRACKER);
               return tracker == null ? null : (GlobalPos)tracker.target().orElse(null);
            })
         )
      );
   }
}
