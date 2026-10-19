package com.smoothmovement;

import java.util.Random;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("smoothmovement")
public class SmoothMovement {
   public static final String MODID = "smoothmovement";
   public static final Logger LOGGER = LogManager.getLogger();
   public static Random rand = new Random();

   public SmoothMovement(IEventBus modEventBus, ModContainer modContainer) {
      Compat.hourglass = FMLLoader.getLoadingModList().getModFileById("hourglass") != null
         || FMLLoader.getLoadingModList().getModFileById("betterdays") != null;
      NeoForge.EVENT_BUS.addListener(this::commandRegister);
   }

   @SubscribeEvent
   public void commandRegister(RegisterCommandsEvent event) {
      event.getDispatcher().register(new Command().build(event.getBuildContext()));
   }

   public static double getDistanceSquared(double x1, double y1, double z1, double x2, double y2, double z2) {
      double xDiff = x1 - x2;
      double yDiff = y1 - y2;
      double zDiff = z1 - z2;
      return xDiff * xDiff + yDiff * yDiff + zDiff * zDiff;
   }
}
