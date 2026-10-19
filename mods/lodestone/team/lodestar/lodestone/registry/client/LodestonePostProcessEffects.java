package team.lodestar.lodestone.registry.client;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;
import team.lodestar.lodestone.systems.postprocess.effects.BloomPostProcessor;
import team.lodestar.lodestone.systems.postprocess.effects.PhysicallyBasedBloomPostProcessor;

@EventBusSubscriber(modid = "lodestone", bus = Bus.MOD, value = Dist.CLIENT)
public class LodestonePostProcessEffects {
   public static final BloomPostProcessor BLOOM = register(BloomPostProcessor::new);
   public static final PhysicallyBasedBloomPostProcessor PB_BLOOM = register(PhysicallyBasedBloomPostProcessor::new);

   private static <T extends PostProcessor> T register(Supplier<T> supplier) {
      return Minecraft.getInstance() == null ? null : supplier.get();
   }

   @SubscribeEvent
   public static void setupPostProcessEffects(FMLClientSetupEvent event) {
      PostProcessHandler.addInstance(BLOOM);
      PostProcessHandler.addInstance(PB_BLOOM);
   }

   public static void enableBloom() {
      BLOOM.setActive(true);
   }

   public static void forceDisableBloom() {
      BLOOM.forceDisable();
   }

   public static void enablePhysicallyBasedBloom() {
      PB_BLOOM.setActive(true);
   }

   public static void forceDisablePhysicallyBasedBloom() {
      PB_BLOOM.forceDisable();
   }
}
