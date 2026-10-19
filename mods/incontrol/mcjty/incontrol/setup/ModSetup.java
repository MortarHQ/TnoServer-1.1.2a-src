package mcjty.incontrol.setup;

import mcjty.incontrol.ForgeEventHandlers;
import mcjty.incontrol.areas.AreaParser;
import mcjty.incontrol.compat.EnigmaSupport;
import mcjty.incontrol.compat.LostCitySupport;
import mcjty.incontrol.events.EventsParser;
import mcjty.incontrol.rules.RulesManager;
import mcjty.incontrol.rules.support.RuleCache;
import mcjty.incontrol.spawner.SpawnerParser;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModSetup {
   public static boolean lostcities = false;
   public static boolean gamestages = false;
   public static boolean sereneSeasons = false;
   public static boolean baubles = false;
   public static boolean enigma = false;
   public static boolean customnpcs = false;
   private Logger logger;
   public RuleCache cache = new RuleCache();
   public static final TagKey<Block> CAVE_BLOCK = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("incontrol", "cave_block"));

   public void init() {
      this.logger = LogManager.getLogger("incontrol");
      this.setupModCompat();
      NeoForge.EVENT_BUS.register(new ForgeEventHandlers());
      RulesManager.setRulePath(FMLPaths.CONFIGDIR.get());
      SpawnerParser.setRulePath(FMLPaths.CONFIGDIR.get());
      EventsParser.setRulePath(FMLPaths.CONFIGDIR.get());
      AreaParser.setRulePath(FMLPaths.CONFIGDIR.get());
   }

   public Logger getLogger() {
      return this.logger;
   }

   private void setupModCompat() {
      lostcities = ModList.get().isLoaded("lostcities");
      gamestages = ModList.get().isLoaded("gamestages");
      sereneSeasons = ModList.get().isLoaded("sereneseasons");
      baubles = ModList.get().isLoaded("baubles");
      enigma = ModList.get().isLoaded("enigma");
      customnpcs = ModList.get().isLoaded("customnpcs");
      if (lostcities) {
         LostCitySupport.register();
         this.logger.log(Level.INFO, "Enabling support for Lost Cities");
      }

      if (gamestages) {
         this.logger.log(Level.INFO, "Enabling support for Game Stages");
      }

      if (sereneSeasons) {
         this.logger.log(Level.INFO, "Enabling support for Serene Seasons");
      }

      if (baubles) {
         this.logger.log(Level.INFO, "Enabling support for Baubles");
      }

      if (enigma) {
         EnigmaSupport.register();
         this.logger.log(Level.INFO, "Enabling support for EnigmaScript");
      }

      if (customnpcs) {
         this.logger.log(Level.INFO, "Enabling support for CustomNPCs");
      }
   }
}
