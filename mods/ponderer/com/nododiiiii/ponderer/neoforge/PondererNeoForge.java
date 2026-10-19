package com.nododiiiii.ponderer.neoforge;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.blueprint.BlueprintFeature;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot.ReplayEventInterceptors;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot.ReplaySessionManager;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.registry.ModItems;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@Mod("ponderer")
public class PondererNeoForge {
   static List<String> pendingOrphanedPacks = new ArrayList<>();
   static List<SceneStore.PackUpdateInfo> pendingPackUpdates = new ArrayList<>();

   public PondererNeoForge(IEventBus modEventBus, ModContainer modContainer) {
      ModItems.init();
      NeoForgeRegistrationHelper.modEventBus = modEventBus;
      PondererServices.REGISTRATION.init();
      modContainer.registerConfig(Type.CLIENT, Config.CLIENT_SPEC);
      modContainer.registerConfig(Type.SERVER, Config.SERVER_SPEC);
      modEventBus.addListener(this::onRegisterPayloads);
      modEventBus.addListener(this::onBuildCreativeTab);
      NeoForge.EVENT_BUS.register(ReplaySessionManager.class);
      NeoForge.EVENT_BUS.register(ReplayEventInterceptors.class);
      if (FMLEnvironment.dist.isClient()) {
         PondererNeoForgeClient.init(modEventBus);
      }
   }

   private void onRegisterPayloads(RegisterPayloadHandlersEvent event) {
      ((NeoForgeNetworkHelper)PondererServices.NETWORK).registerPayloads(event);
   }

   private void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES && BlueprintFeature.shouldShowBlueprintInCreativeTab()) {
         event.accept(new ItemStack((ItemLike)ModItems.BLUEPRINT.get()));
      }
   }
}
