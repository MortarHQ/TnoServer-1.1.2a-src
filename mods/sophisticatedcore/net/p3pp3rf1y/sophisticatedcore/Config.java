package net.p3pp3rf1y.sophisticatedcore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.p3pp3rf1y.sophisticatedcore.client.gui.SortButtonsPosition;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;
import org.apache.commons.lang3.tuple.Pair;

public class Config {
   public static final Config.Client CLIENT;
   public static final ModConfigSpec CLIENT_SPEC;
   public static final Config.Common COMMON;
   public static final ModConfigSpec COMMON_SPEC;

   private Config() {
   }

   static {
      Pair<Config.Client, ModConfigSpec> clientSpec = new Builder().configure(Config.Client::new);
      CLIENT_SPEC = (ModConfigSpec)clientSpec.getRight();
      CLIENT = (Config.Client)clientSpec.getLeft();
      Pair<Config.Common, ModConfigSpec> commonSpec = new Builder().configure(Config.Common::new);
      COMMON_SPEC = (ModConfigSpec)commonSpec.getRight();
      COMMON = (Config.Common)commonSpec.getLeft();
   }

   public static class Client {
      public final EnumValue<SortButtonsPosition> sortButtonsPosition;
      public final BooleanValue playButtonSound;
      public final BooleanValue mouseTweaksScrollEnabled;

      Client(Builder builder) {
         builder.comment("Client Settings").push("client");
         this.sortButtonsPosition = builder.comment("Positions where sort buttons can display to help with conflicts with controls from other mods")
            .defineEnum("sortButtonsPosition", SortButtonsPosition.TITLE_LINE_RIGHT);
         this.playButtonSound = builder.comment("Whether click sound should play when custom buttons are clicked in gui").define("playButtonSound", true);
         this.mouseTweaksScrollEnabled = builder.comment("Whether scrolling in inventory should be handled by Mouse Tweaks mod if it is in the pack")
            .define("mouseTweaksScrollEnabled", true);
         builder.pop();
      }
   }

   public static class Common {
      private boolean configChanged = false;
      public final Config.Common.EnabledItems enabledItems;

      public void initListeners(IEventBus modBus) {
         modBus.addListener(this::onConfigReload);
      }

      public void onConfigReload(Reloading event) {
         this.enabledItems.enabledMap.clear();
      }

      Common(Builder builder) {
         builder.comment("Common Settings").push("common");
         this.enabledItems = new Config.Common.EnabledItems(builder, () -> this.configChanged = true);
      }

      public void saveIfChanged() {
         if (this.configChanged) {
            this.configChanged = false;
            Config.COMMON_SPEC.save();
         }
      }

      public static class EnabledItems {
         private final ConfigValue<List<String>> itemsEnableList;
         private final Runnable onConfigChange;
         private final Map<ResourceLocation, Boolean> enabledMap = new ConcurrentHashMap<>();

         EnabledItems(Builder builder, Runnable onConfigChange) {
            this.itemsEnableList = builder.comment("Disable / enable any items here (disables their recipes)").define("enabledItems", new ArrayList());
            this.onConfigChange = onConfigChange;
         }

         public boolean isItemEnabled(Item item) {
            return RegistryHelper.getRegistryName(BuiltInRegistries.ITEM, item).map(this::isItemEnabled).orElse(false);
         }

         public boolean isItemEnabled(ResourceLocation itemRegistryName) {
            if (!Config.COMMON_SPEC.isLoaded()) {
               return true;
            }

            if (this.enabledMap.isEmpty()) {
               this.loadEnabledMap();
            }

            return this.enabledMap.computeIfAbsent(itemRegistryName, irn -> {
               this.addEnabledItemToConfig(itemRegistryName);
               return true;
            });
         }

         private void addEnabledItemToConfig(ResourceLocation itemRegistryName) {
            List<String> list = (List<String>)this.itemsEnableList.get();
            list.add(itemRegistryName + "|true");
            this.itemsEnableList.set(list);
            this.onConfigChange.run();
         }

         private void loadEnabledMap() {
            for (String itemEnabled : (List)this.itemsEnableList.get()) {
               String[] data = itemEnabled.split("\\|");
               if (data.length == 2) {
                  this.enabledMap.put(ResourceLocation.parse(data[0]), Boolean.valueOf(data[1]));
               } else {
                  SophisticatedCore.LOGGER.error("Wrong data for enabledItems - expected registry name|true/false when {} was provided", itemEnabled);
               }
            }
         }
      }
   }
}
