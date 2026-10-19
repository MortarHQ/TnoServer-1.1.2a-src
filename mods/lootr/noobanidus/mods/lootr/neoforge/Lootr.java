package noobanidus.mods.lootr.neoforge;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.PlatformAPI;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.command.CommandLootr;
import noobanidus.mods.lootr.neoforge.config.ConfigManager;
import noobanidus.mods.lootr.neoforge.impl.LootrAPIImpl;
import noobanidus.mods.lootr.neoforge.impl.LootrRegistryImpl;
import noobanidus.mods.lootr.neoforge.impl.PlatformAPIImpl;
import noobanidus.mods.lootr.neoforge.init.ModAdvancements;
import noobanidus.mods.lootr.neoforge.init.ModBlockEntities;
import noobanidus.mods.lootr.neoforge.init.ModBlocks;
import noobanidus.mods.lootr.neoforge.init.ModEntities;
import noobanidus.mods.lootr.neoforge.init.ModItems;
import noobanidus.mods.lootr.neoforge.init.ModLoot;
import noobanidus.mods.lootr.neoforge.init.ModParticles;
import noobanidus.mods.lootr.neoforge.init.ModStats;
import noobanidus.mods.lootr.neoforge.init.ModTabs;

@Mod("lootr")
public class Lootr {
   public Lootr(ModContainer modContainer, IEventBus modBus) {
      LootrAPI.INSTANCE = new LootrAPIImpl();
      LootrRegistry.INSTANCE = new LootrRegistryImpl();
      PlatformAPI.INSTANCE = new PlatformAPIImpl();
      modContainer.registerConfig(Type.COMMON, ConfigManager.COMMON_CONFIG);
      modContainer.registerConfig(Type.CLIENT, ConfigManager.CLIENT_CONFIG);
      NeoForge.EVENT_BUS.addListener(this::onCommands);
      ModTabs.register(modBus);
      ModBlockEntities.register(modBus);
      ModBlocks.register(modBus);
      ModEntities.register(modBus);
      ModItems.register(modBus);
      ModLoot.register(modBus);
      ModStats.register(modBus);
      ModAdvancements.register(modBus);
      ModParticles.register(modBus);
   }

   public static ResourceLocation rl(String path) {
      return LootrAPI.rl(path);
   }

   public void onCommands(RegisterCommandsEvent event) {
      CommandLootr.register(event.getDispatcher());
   }
}
