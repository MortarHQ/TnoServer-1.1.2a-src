package com.tom.storagemod;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
   private static final Config INSTANCE = new Config();
   public boolean onlyTrims;
   public boolean runMultithreaded;
   public int invConnectorScanRange;
   public int invConnectorCableRange = 0;
   public int wirelessRange;
   public int advWirelessRange;
   public int wirelessTermBeaconLvl;
   public int wirelessTermBeaconLvlCrossDim;
   public int invLinkBeaconLvl;
   public int invLinkBeaconRange;
   public int invLinkBeaconLvlSameDim;
   public int invLinkBeaconLvlCrossDim;
   public int basicHopperCooldown;
   private Set<String> blockedMods = new HashSet<>();
   private Set<Block> blockedBlocks = new HashSet<>();
   static final ModConfigSpec commonSpec;
   public static final Config.Common COMMON;
   static final ModConfigSpec serverSpec;
   public static final Config.Server SERVER;

   public static Config get() {
      return INSTANCE;
   }

   private void load(ModConfig modConfig) {
      if (modConfig.getType() == Type.SERVER) {
         this.onlyTrims = (Boolean)SERVER.onlyTrimsConnect.get();
         this.invConnectorScanRange = (Integer)SERVER.invConnectorScanRange.get();
         this.invConnectorCableRange = (Integer)SERVER.invConnectorCableRange.get();
         this.wirelessRange = (Integer)SERVER.wirelessRange.get();
         this.advWirelessRange = (Integer)SERVER.advWirelessRange.get();
         this.wirelessTermBeaconLvl = (Integer)SERVER.wirelessTermBeaconLvl.get();
         this.wirelessTermBeaconLvlCrossDim = (Integer)SERVER.wirelessTermBeaconLvlCrossDim.get();
         this.invLinkBeaconLvl = (Integer)SERVER.invLinkBeaconLvl.get();
         this.invLinkBeaconRange = (Integer)SERVER.invLinkBeaconRange.get();
         this.invLinkBeaconLvlSameDim = (Integer)SERVER.invLinkBeaconLvlSameDim.get();
         this.invLinkBeaconLvlCrossDim = (Integer)SERVER.invLinkBeaconLvlCrossDim.get();
         this.runMultithreaded = SERVER.runMultithreaded.getAsBoolean();
         this.basicHopperCooldown = (Integer)SERVER.basicHopperCooldown.get();
      } else if (modConfig.getType() == Type.COMMON) {
         this.blockedMods = new HashSet<>((Collection<? extends String>)COMMON.blockedMods.get());
         this.blockedBlocks = ((List)COMMON.blockedBlocks.get())
            .stream()
            .<ResourceLocation>map(ResourceLocation::tryParse)
            .filter(e -> e != null)
            .<Block>map(BuiltInRegistries.BLOCK::get)
            .filter(e -> e != null && e != Blocks.AIR)
            .collect(Collectors.toSet());
      }
   }

   @SubscribeEvent
   public void onLoad(Loading configEvent) {
      StorageMod.LOGGER.info("Loaded Tom's Simple Storage config file {}", configEvent.getConfig().getFileName());
      this.load(configEvent.getConfig());
   }

   @SubscribeEvent
   public void onFileChange(Reloading configEvent) {
      StorageMod.LOGGER.info("Tom's Simple Storage config just got changed on the file system!");
      this.load(configEvent.getConfig());
   }

   public Set<Block> getBlockedBlocks() {
      return this.blockedBlocks;
   }

   public Set<String> getBlockedMods() {
      return this.blockedMods;
   }

   static {
      Pair<Config.Common, ModConfigSpec> specPair = new Builder().configure(Config.Common::new);
      commonSpec = (ModConfigSpec)specPair.getRight();
      COMMON = (Config.Common)specPair.getLeft();
      specPair = new Builder().configure(Config.Server::new);
      serverSpec = (ModConfigSpec)specPair.getRight();
      SERVER = (Config.Server)specPair.getLeft();
   }

   public static class Common {
      public ConfigValue<List<? extends String>> blockedMods;
      public ConfigValue<List<? extends String>> blockedBlocks;

      public Common(Builder builder) {
         builder.comment(
               new String[]{
                  "IMPORTANT NOTICE:",
                  "THIS IS ONLY THE COMMON CONFIG. It does not contain all the values adjustable for Tom's Simple Storage",
                  "The settings have been moved to toms_storage-server.toml",
                  "That file is PER WORLD, meaning you have to go into 'saves/<world name>/serverconfig' to adjust it. Those changes will then only apply for THAT WORLD.",
                  "You can then take that config file and put it in the 'defaultconfigs' folder to make it apply automatically to all NEW worlds you generate FROM THERE ON.",
                  "This may appear confusing to many of you, but it is a new sensible way to handle configuration, because the server configuration is synced when playing multiplayer."
               }
            )
            .define("importantInfo", true);
         this.blockedMods = builder.comment("List of mod ids whose blocks is ignored by the inventory connector")
            .translation("config.toms_storage.inv_blocked_mods")
            .defineList("blockedMods", Collections.emptyList(), () -> "", s -> true);
         this.blockedBlocks = builder.comment("List of block ids ignored by the inventory connector")
            .translation("config.toms_storage.inv_blocked_blocks")
            .defineList("blockedBlocks", Collections.emptyList(), () -> "", s -> true);
      }
   }

   public static class Server {
      public IntValue invConnectorScanRange;
      public IntValue invConnectorCableRange;
      public IntValue wirelessRange;
      public BooleanValue onlyTrimsConnect;
      public BooleanValue runMultithreaded;
      public IntValue advWirelessRange;
      public IntValue wirelessTermBeaconLvl;
      public IntValue wirelessTermBeaconLvlCrossDim;
      public IntValue invLinkBeaconLvl;
      public IntValue invLinkBeaconRange;
      public IntValue invLinkBeaconLvlSameDim;
      public IntValue invLinkBeaconLvlCrossDim;
      public IntValue basicHopperCooldown;

      private Server(Builder builder) {
         this.invConnectorScanRange = builder.comment("Inventory Connector Range")
            .translation("config.toms_storage.inventory_connector_range")
            .defineInRange("inventoryConnectorRange", 16, 4, 256);
         this.onlyTrimsConnect = builder.comment("Only Allow Trims to Connect Inventories")
            .translation("config.toms_storage.only_trims_connect")
            .define("onlyTrimsConnect", false);
         this.invConnectorCableRange = builder.comment("Inventory Cable Connector Maximum number of cables")
            .translation("config.toms_storage.inv_cable_connector_max_scan")
            .defineInRange("invCableConnectorMaxScanSize", 2048, 16, Integer.MAX_VALUE);
         this.wirelessRange = builder.comment("Wireless terminal reach")
            .translation("config.toms_storage.wireless_reach")
            .defineInRange("wirelessReach", 16, 4, 64);
         this.advWirelessRange = builder.comment("Wireless terminal reach")
            .translation("config.toms_storage.adv_wireless_range")
            .defineInRange("advWirelessRange", 64, 16, 512);
         this.wirelessTermBeaconLvl = builder.comment(
               new String[]{
                  "Adv Wireless terminal requied beacon level for infinite range",
                  "Value of 0 only requires a single beacon block nearby",
                  "Value of -1 disables this feature entirely"
               }
            )
            .translation("config.toms_storage.adv_wireless_beacon_lvl")
            .defineInRange("wirelessTermBeaconLvl", 1, -1, 4);
         this.wirelessTermBeaconLvlCrossDim = builder.comment(
               new String[]{
                  "Adv Wireless terminal requied beacon level for cross dimensional access",
                  "Value of 0 only requires a single beacon block nearby",
                  "Value of -1 disables this feature entirely"
               }
            )
            .translation("config.toms_storage.adv_wireless_beacon_lvl_dim")
            .defineInRange("wirelessTermBeaconLvlDim", 4, -1, 4);
         this.invLinkBeaconLvl = builder.comment(
               new String[]{
                  "Inventory Cable Connector requied beacon level for inventory linking",
                  "Value of 0 only requires a single beacon block",
                  "Value of -1 disables this feature entirely"
               }
            )
            .translation("config.toms_storage.inv_link_beacon_lvl")
            .defineInRange("invLinkBeaconLvl", 0, -1, 4);
         this.invLinkBeaconRange = builder.comment(new String[]{"Inventory Linking range with beacons", "Value of 0 disables this feature entirely"})
            .translation("config.toms_storage.inv_link_beacon_range")
            .defineInRange("invLinkBeaconRange", 4096, 0, Integer.MAX_VALUE);
         this.invLinkBeaconLvlSameDim = builder.comment(
               new String[]{
                  "Inventory Cable Connector requied beacon level for same dimension access with unlimited range",
                  "Value of 0 only requires a single beacon block",
                  "Value of -1 disables this feature entirely"
               }
            )
            .translation("config.toms_storage.inv_link_beacon_lvl_same_dim")
            .defineInRange("invLinkBeaconLvlSameDim", 1, -1, 4);
         this.invLinkBeaconLvlCrossDim = builder.comment(
               new String[]{
                  "Inventory Cable Connector requied beacon level for cross dimensional access",
                  "Value of 0 only requires a single beacon block",
                  "Value of -1 disables this feature entirely"
               }
            )
            .translation("config.toms_storage.inv_link_beacon_lvl_dim")
            .defineInRange("invLinkBeaconLvlCrossDim", 2, -1, 4);
         this.runMultithreaded = builder.comment("Use multithreading to improve performance")
            .translation("config.toms_storage.run_multithreaded")
            .define("runMultithreaded", true);
         this.basicHopperCooldown = builder.comment("Base tick cooldown for Basic Inventory Hopper item transfer")
            .translation("config.toms_storage.basic_hopper_cooldown")
            .defineInRange("basicHopperCooldown", 10, 1, 200);
      }
   }
}
