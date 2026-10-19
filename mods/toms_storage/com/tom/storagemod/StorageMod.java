package com.tom.storagemod;

import com.tom.storagemod.api.MultiblockInventoryAPI;
import com.tom.storagemod.inventory.PlatformItemHandler;
import com.tom.storagemod.inventory.VanillaMultiblockInventories;
import com.tom.storagemod.inventory.compat.SophisticatedDoubleBlocks;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.platform.Platform;
import com.tom.storagemod.polymorph.PolymorphTerminalWidget;
import com.tom.storagemod.top.TheOneProbeHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("toms_storage")
public class StorageMod {
   public static final String modid = "toms_storage";
   public static final Logger LOGGER = LogManager.getLogger();
   public static boolean polymorph;

   public StorageMod(ModContainer mc, IEventBus bus) {
      bus.addListener(this::setup);
      bus.addListener(this::doClientStuff);
      if (FMLEnvironment.dist == Dist.CLIENT) {
         StorageModClient.preInit(mc, bus);
      }

      bus.addListener(this::registerCapabilities);
      bus.addListener(this::enqueueIMC);
      mc.registerConfig(Type.COMMON, Config.commonSpec);
      mc.registerConfig(Type.SERVER, Config.serverSpec);
      bus.register(Config.get());
      bus.register(NetworkHandler.class);
      Content.init();
      Platform.register(bus);
      polymorph = ModList.get().isLoaded("polymorph");
   }

   private void setup(FMLCommonSetupEvent event) {
      LOGGER.info("Tom's Storage Setup starting");
      MultiblockInventoryAPI.EVENT.register(VanillaMultiblockInventories::checkChest);
      if (ModList.get().isLoaded("sophisticatedstorage")) {
         SophisticatedDoubleBlocks.register();
      }
   }

   private void doClientStuff(FMLClientSetupEvent event) {
      StorageModClient.clientSetup();
      if (polymorph) {
         PolymorphTerminalWidget.register();
      }
   }

   private void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlockEntity(ItemHandler.BLOCK, Content.openCrateBE.get(), (be, side) -> new InvWrapper(be));
      event.registerBlockEntity(ItemHandler.BLOCK, Content.connectorBE.get(), (be, side) -> new PlatformItemHandler(be));
      event.registerBlockEntity(ItemHandler.BLOCK, Content.invInterfaceBE.get(), (be, side) -> new PlatformItemHandler(be));
      event.registerBlockEntity(ItemHandler.BLOCK, Content.filingCabinetBE.get(), (be, side) -> new InvWrapper(be.getInv()));
      event.registerBlockEntity(ItemHandler.BLOCK, Content.invProxyBE.get(), (be, side) -> new PlatformItemHandler(be));
   }

   public void enqueueIMC(InterModEnqueueEvent e) {
      if (ModList.get().isLoaded("theoneprobe")) {
         InterModComms.sendTo("theoneprobe", "getTheOneProbe", () -> TheOneProbeHandler.create());
      }
   }
}
