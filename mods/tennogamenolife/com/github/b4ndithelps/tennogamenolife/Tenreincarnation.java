package com.github.b4ndithelps.tennogamenolife;

import com.github.b4ndithelps.tennogamenolife.command.JorCommand;
import com.github.b4ndithelps.tennogamenolife.command.LaserRainDebugCommand;
import com.github.b4ndithelps.tennogamenolife.config.TNOConfigInjector;
import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationConfig;
import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationGeneralConfig;
import com.github.b4ndithelps.tennogamenolife.event.FinalEvolutionDiscRewardHandler;
import com.github.b4ndithelps.tennogamenolife.event.FourthProtectionEvents;
import com.github.b4ndithelps.tennogamenolife.event.SkillAvailabilityHandler;
import com.github.b4ndithelps.tennogamenolife.geckolib.PolyMeshBakedModelFactory;
import com.github.b4ndithelps.tennogamenolife.registry.block.TenreincarnationBlocks;
import com.github.b4ndithelps.tennogamenolife.registry.effect.ModEffects;
import com.github.b4ndithelps.tennogamenolife.registry.entity.TenreincarnationEntities;
import com.github.b4ndithelps.tennogamenolife.registry.item.TenreincarnationCreativeTabs;
import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import com.github.b4ndithelps.tennogamenolife.registry.race.Tenreincarnationraces;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import com.github.b4ndithelps.tennogamenolife.registry.sound.TenreincarnationSounds;
import com.mojang.logging.LogUtils;
import io.github.manasmods.manascore.config.ConfigRegistry;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mod("tennogamenolife")
public class Tenreincarnation {
   public static final String MODID = "tennogamenolife";
   private static final Logger LOGGER = LogUtils.getLogger();

   public Tenreincarnation(IEventBus modEventBus, ModContainer modContainer) {
      GeckoLibUtil.addCustomBakedModelFactory("tennogamenolife", new PolyMeshBakedModelFactory());
      modEventBus.addListener(this::setup);
      modContainer.registerConfig(Type.COMMON, TenreincarnationConfig.COMMON_SPEC);
      ConfigRegistry.registerConfig(new TenreincarnationGeneralConfig());
      this.register(modEventBus);
      NeoForge.EVENT_BUS.register(this);
      SkillAvailabilityHandler.init();
      FinalEvolutionDiscRewardHandler.init();
      FourthProtectionEvents.init();
      ModEffects.MOB_EFFECTS.register(modEventBus);
      modEventBus.addListener(this::onAttributeCreate);
      TNOConfigInjector.addToConfig();
      LOGGER.info("TenNoGameNoLife 模组初始化完成！");
   }

   private void setup(FMLCommonSetupEvent event) {
      LOGGER.info("TenNoGameNoLife 模组设置中...");
   }

   private void onAttributeCreate(EntityAttributeCreationEvent event) {
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
   }

   @SubscribeEvent
   public void onRegisterCommands(RegisterCommandsEvent event) {
      LOGGER.info("注册命令: /aqzl");
      JorCommand.register(event.getDispatcher());
      LOGGER.info("注册命令: /ngl debug laser_rain");
      LaserRainDebugCommand.register(event.getDispatcher());
   }

   @SubscribeEvent
   public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      Player player = event.getEntity();
      if (player != null) {
         LOGGER.info("Player {} logged in", player.getName().getString());
      }
   }

   private void register(IEventBus modEventBus) {
      TenreincarnationSkill.register(modEventBus);
      Tenreincarnationraces.init();
      Tenreincarnationitems.register(modEventBus);
      TenreincarnationBlocks.register(modEventBus);
      TenreincarnationEntities.register(modEventBus);
      TenreincarnationSounds.register(modEventBus);
      TenreincarnationCreativeTabs.register(modEventBus);
   }
}
