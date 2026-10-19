package io.github.jodlodi.twilighttweaks.event;

import io.github.jodlodi.twilighttweaks.TwilightTweaks;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@ParametersAreNonnullByDefault
@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD, modid = "twilighttweaks")
public class ClientEventSubscriber {
   @SubscribeEvent
   public static void clientSetup(FMLClientSetupEvent evt) {
      ItemBlockRenderTypes.setRenderLayer((Block)TwilightTweaks.BOSS_SPAWNER_REMNANT.get(), RenderType.cutout());
   }
}
