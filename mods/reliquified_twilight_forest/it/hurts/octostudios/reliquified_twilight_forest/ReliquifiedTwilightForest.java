package it.hurts.octostudios.reliquified_twilight_forest;

import it.hurts.octostudios.reliquified_twilight_forest.init.ConfigRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.EffectRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.EntityRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.GlobalLootModifierSerializerCodecRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ParticleRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod("reliquified_twilight_forest")
public class ReliquifiedTwilightForest {
   public static final String MOD_ID = "reliquified_twilight_forest";

   public ReliquifiedTwilightForest(IEventBus bus) {
      bus.addListener(this::setupCommon);
      ConfigRegistry.register();
      ItemRegistry.register(bus);
      EffectRegistry.register(bus);
      EntityRegistry.register(bus);
      ParticleRegistry.register(bus);
      DataComponentRegistry.register(bus);
      GlobalLootModifierSerializerCodecRegistry.register(bus);
   }

   private void setupCommon(FMLCommonSetupEvent event) {
   }
}
