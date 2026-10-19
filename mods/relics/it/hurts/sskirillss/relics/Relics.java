package it.hurts.sskirillss.relics;

import it.hurts.sskirillss.relics.init.BadgeRegistry;
import it.hurts.sskirillss.relics.init.BlockRegistry;
import it.hurts.sskirillss.relics.init.CommandRegistry;
import it.hurts.sskirillss.relics.init.ConfigRegistry;
import it.hurts.sskirillss.relics.init.CreativeTabRegistry;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.DispenserBehaviorRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.init.LootCodecRegistry;
import it.hurts.sskirillss.relics.init.ParticleRegistry;
import it.hurts.sskirillss.relics.init.RelicContainerRegistry;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.init.TileRegistry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("relics")
public class Relics {
   public static final Logger LOGGER = LogManager.getLogger("relics");

   public Relics(IEventBus bus, ModContainer container) {
      bus.addListener(this::setupCommon);
      ItemRegistry.register(bus);
      TileRegistry.register(bus);
      BlockRegistry.register(bus);
      SoundRegistry.register(bus);
      BadgeRegistry.register(bus);
      EntityRegistry.register(bus);
      EffectRegistry.register(bus);
      CommandRegistry.register(bus);
      ParticleRegistry.register(bus);
      LootCodecRegistry.register(bus);
      CreativeTabRegistry.register(bus);
      DataComponentRegistry.register(bus);
      RelicContainerRegistry.register(bus);
   }

   private void setupCommon(FMLCommonSetupEvent event) {
      DispenserBehaviorRegistry.register();
      ConfigRegistry.register();
      InterModComms.sendTo("darkmodeeverywhere", "dme-shaderblacklist", () -> "relics");
   }
}
