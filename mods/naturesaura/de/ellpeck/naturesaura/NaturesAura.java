package de.ellpeck.naturesaura;

import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.chunk.effect.DrainSpotEffects;
import de.ellpeck.naturesaura.compat.Compat;
import de.ellpeck.naturesaura.events.CommonEvents;
import de.ellpeck.naturesaura.proxy.ClientProxy;
import de.ellpeck.naturesaura.proxy.IProxy;
import de.ellpeck.naturesaura.proxy.ServerProxy;
import de.ellpeck.naturesaura.recipes.ModRecipes;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("naturesaura")
public final class NaturesAura {
   public static final String MOD_ID = "naturesaura";
   public static final String MOD_NAME = "Nature's Aura";
   public static final Logger LOGGER = LogManager.getLogger("Nature's Aura");
   public static NaturesAura instance;
   public static IProxy proxy;

   public NaturesAura(ModContainer container) {
      instance = this;
      Builder builder = new Builder();
      ModConfig.instance = new ModConfig(builder);
      container.registerConfig(Type.COMMON, builder.build());
      proxy = FMLEnvironment.dist.isClient() ? new ClientProxy(container) : new ServerProxy();
      container.getEventBus().addListener(this::setup);
   }

   public void setup(FMLCommonSetupEvent event) {
      this.preInit(event);
      this.init(event);
      this.postInit(event);
   }

   private void preInit(FMLCommonSetupEvent event) {
      Compat.setup(event);
      new Multiblocks();
      NeoForge.EVENT_BUS.register(new CommonEvents());
      proxy.preInit(event);
   }

   private void init(FMLCommonSetupEvent event) {
      event.enqueueWork(ModConfig.instance::apply);
      ModRecipes.init();
      DrainSpotEffects.init();
      proxy.init(event);
   }

   private void postInit(FMLCommonSetupEvent event) {
      proxy.postInit(event);
      LOGGER.info("-- Nature's Aura Fake Player Information --");
      LOGGER.info("Name: {}", ModRegistry.FAKE_PLAYER.getName());
      LOGGER.info("UUID: {}", ModRegistry.FAKE_PLAYER.getId());
      LOGGER.info("-------------------------------------------");
   }
}
