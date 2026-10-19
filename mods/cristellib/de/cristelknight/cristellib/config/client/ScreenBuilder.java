package de.cristelknight.cristellib.config.client;

import com.mojang.datafixers.util.Pair;
import de.cristelknight.cristellib.CristelLib;
import de.cristelknight.cristellib.CristelLibExpectPlatform;
import de.cristelknight.cristellib.CristelLibRegistry;
import de.cristelknight.cristellib.StructureConfig;
import de.cristelknight.cristellib.autoconfig.ACConfig;
import de.cristelknight.cristellib.autoconfig.ACInfoData;
import de.cristelknight.cristellib.config.ConfigType;
import de.cristelknight.cristellib.config.client.simple.ClientConfigRegistry;
import de.cristelknight.cristellib.config.client.simple.SimpleConfigScreen;
import de.cristelknight.cristellib.config.client.simple.SimpleScreenBuilder;
import de.cristelknight.cristellib.config.serialize.ed.EDConfig;
import de.cristelknight.cristellib.config.serialize.ed.EDConfigTransformer;
import de.cristelknight.cristellib.config.serialize.ed.NestedEDConfig;
import de.cristelknight.cristellib.config.serialize.placement.PlacementConfig;
import de.cristelknight.cristellib.config.simple.ConfigRegistry;
import de.cristelknight.cristellib.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerListEntry;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenBuilder {
   private final String modID;
   private ConfigEntryBuilder entryBuilder;
   private final Set<ClientStructureConfig> clientStructureConfigs = new HashSet<>();

   public ScreenBuilder(String modID) {
      this.modID = modID;
   }

   public Screen create(Screen parent, boolean structure, boolean simple) {
      ConfigBuilder builder = ConfigBuilder.create()
         .setTitle(
            Component.translatable("§7" + CristelLibExpectPlatform.getModDisplayName(this.modID) + " Configuration (via %s§7)", new Object[]{Util.CRISTEL_LIB})
         );
      builder.setParentScreen(parent);
      builder.setSavingRunnable(this::onConfigSave);
      this.addToBuilder(builder, structure, simple);
      return builder.build();
   }

   public void addToBuilder(ConfigBuilder builder, boolean structure, boolean simple) {
      this.entryBuilder = builder.entryBuilder();
      if (structure) {
         for (StructureConfig structureConfig : this.sorted((Set<StructureConfig>)CristelLibRegistry.getConfigs().get(this.modID))) {
            if (structureConfig.getType().equals(ConfigType.PLACEMENT)) {
               this.addPlacementCategory(builder, structureConfig);
            } else {
               this.addEDCategory(builder, structureConfig);
            }
         }
      }

      if (simple) {
         for (SimpleConfigScreen simpleConfigScreen : ClientConfigRegistry.getScreens(this.modID)) {
            SimpleScreenBuilder.addConfigToCategory(builder, this.entryBuilder, simpleConfigScreen);
         }
      }
   }

   private void addPlacementCategory(ConfigBuilder builder, StructureConfig structureConfig) {
      ConfigCategory placementCategory = builder.getOrCreateCategory(Component.translatable("cristellib.placementCategoryTitle"));
      this.addHeader(structureConfig, placementCategory, this.entryBuilder);
      Map<ResourceLocation, PlacementConfig> placementConfigs = structureConfig.placementConfig;
      Map<ResourceLocation, PlacementConfig> defaultPlacementConfigs = structureConfig.getDefaultStructurePlacement();
      Map<ResourceLocation, ClientPlacementConfig> clientPlacementConfigs = new HashMap<>();

      for (ResourceLocation structureSetLocation : Util.sortedKeyList(placementConfigs)) {
         PlacementConfig currentConfig = placementConfigs.get(structureSetLocation);
         PlacementConfig defaultConfig = defaultPlacementConfigs.get(structureSetLocation);
         if (defaultConfig == null) {
            getWarn(structureConfig, structureSetLocation);
         } else {
            String structureSetName = structureConfig.toDefaultString(structureSetLocation);
            SubCategoryBuilder subCategory = this.entryBuilder.startSubCategory(Component.literal(structureSetName));
            subCategory.setTooltip(SimpleScreenBuilder.tooltip(structureSetName, structureConfig.getComments()));
            ClientPlacementConfig clientPlacementConfig = new ClientPlacementConfig(
               this.frequencyEntry(this.entryBuilder, "frequency", currentConfig.frequency(), defaultConfig.frequency(), subCategory),
               this.intEntry(this.entryBuilder, "salt", currentConfig.salt(), defaultConfig.salt(), subCategory),
               this.intEntry(this.entryBuilder, "separation", currentConfig.separation(), defaultConfig.separation(), subCategory),
               this.intEntry(this.entryBuilder, "spacing", currentConfig.spacing(), defaultConfig.spacing(), subCategory)
            );
            placementCategory.addEntry(subCategory.build());
            clientPlacementConfigs.put(structureSetLocation, clientPlacementConfig);
         }
      }

      this.clientStructureConfigs.add(new ClientStructureConfig(structureConfig, clientPlacementConfigs, null));
   }

   private void addEDCategory(ConfigBuilder builder, StructureConfig structureConfig) {
      ConfigCategory edCategory = builder.getOrCreateCategory(Component.translatable("cristellib.toggleCategoryTitle"));
      this.addHeader(structureConfig, edCategory, this.entryBuilder);
      Map<String, NestedEDConfig> nestedStructureMap = EDConfigTransformer.mapToNestedStructuresWithValues(structureConfig.enableDisableConfig, structureConfig);
      Map<ResourceLocation, ClientEDConfig> clientEDConfigs = new HashMap<>();

      for (String structureSetName : Util.sortedKeyList(nestedStructureMap)) {
         Map<String, BooleanListEntry> structures = new HashMap<>();
         SubCategoryBuilder rootSubCategory = this.entryBuilder.startSubCategory(Component.literal(structureSetName));
         rootSubCategory.setTooltip(SimpleScreenBuilder.tooltip(structureSetName, structureConfig.getComments()));
         NestedEDConfig nestedEDConfig = nestedStructureMap.get(structureSetName);
         if (!this.checkForSingle(nestedEDConfig, structureSetName, structureConfig, structures, edCategory)) {
            this.addEDSubCategory(rootSubCategory, null, nestedEDConfig, "", structures, structureConfig, structureSetName);
            edCategory.addEntry(rootSubCategory.build());
         }

         clientEDConfigs.put(structureConfig.toDefaultRL(structureSetName), new ClientEDConfig(structures));
      }

      this.clientStructureConfigs.add(new ClientStructureConfig(structureConfig, null, clientEDConfigs));
   }

   private void addEDSubCategory(
      SubCategoryBuilder rootCategory,
      SubCategoryBuilder parent,
      NestedEDConfig edConfig,
      String pathPrefix,
      Map<String, BooleanListEntry> structures,
      StructureConfig structureConfig,
      String structureSetName
   ) {
      Map<String, NestedEDConfig.Entry> entries = edConfig.entries();

      for (String key : Util.sortedKeyList(entries)) {
         NestedEDConfig.Entry value = entries.get(key);
         String fullPath = pathPrefix.isEmpty() ? key : pathPrefix + "/" + key;
         if (value.isBoolean()) {
            BooleanListEntry toggle = this.entryBuilder
               .startBooleanToggle(Component.literal(key), value.value())
               .setDefaultValue(true)
               .setTooltipSupplier(() -> SimpleScreenBuilder.tooltip(structureSetName + "." + fullPath, structureConfig.getComments()))
               .build();
            structures.put(fullPath, toggle);
            if (!this.getEDSubWarn(structureConfig, fullPath, structureSetName)) {
               if (parent == null) {
                  rootCategory.add(toggle);
               } else {
                  parent.add(toggle);
               }
            }
         } else {
            SubCategoryBuilder subCategory = this.entryBuilder.startSubCategory(Component.literal(key));
            subCategory.setTooltip(SimpleScreenBuilder.tooltip(structureSetName + "." + fullPath, structureConfig.getComments()));
            this.addEDSubCategory(rootCategory, subCategory, value.nested(), fullPath, structures, structureConfig, structureSetName);
            if (parent == null) {
               rootCategory.add(subCategory.build());
            } else {
               parent.add(subCategory.build());
            }
         }
      }
   }

   private boolean checkForSingle(
      NestedEDConfig nestedEDConfig,
      String structureSetName,
      StructureConfig structureConfig,
      Map<String, BooleanListEntry> structures,
      ConfigCategory edCategory
   ) {
      if (nestedEDConfig.entries().size() != 1) {
         return false;
      }

      NestedEDConfig.Entry entry = nestedEDConfig.entries().values().stream().findAny().get();
      if (!entry.isBoolean()) {
         return false;
      }

      String key = nestedEDConfig.entries().keySet().stream().findAny().get();
      BooleanListEntry toggle = this.entryBuilder
         .startBooleanToggle(Component.literal(key), entry.value())
         .setDefaultValue(true)
         .setTooltipSupplier(() -> SimpleScreenBuilder.tooltip(structureSetName + "." + key, structureConfig.getComments()))
         .build();
      structures.put(key, toggle);
      if (this.getEDSubWarn(structureConfig, key, structureSetName)) {
         return true;
      }

      edCategory.addEntry(toggle);
      return true;
   }

   private void onConfigSave() {
      for (ClientStructureConfig clientStructureConfig : this.clientStructureConfigs) {
         StructureConfig structureConfig = clientStructureConfig.structureConfig();
         if (structureConfig.getType().equals(ConfigType.PLACEMENT)) {
            this.updatePlacements(clientStructureConfig.structureConfig(), clientStructureConfig.clientPlacementConfigs());
         } else {
            this.updateEDs(clientStructureConfig.structureConfig(), clientStructureConfig.clientEDConfigs());
         }

         structureConfig.writeConfig(true);
         structureConfig.addSetsToRuntimePack();
      }

      SimpleScreenBuilder.saveConfigs(this.modID);
   }

   private void updatePlacements(StructureConfig structureConfig, Map<ResourceLocation, ClientPlacementConfig> clientPlacementConfigs) {
      Map<ResourceLocation, PlacementConfig> placementConfigs = structureConfig.placementConfig;

      for (ResourceLocation structureName : clientPlacementConfigs.keySet()) {
         placementConfigs.put(structureName, clientPlacementConfigs.get(structureName).toPlacement());
      }
   }

   private void updateEDs(StructureConfig structureConfig, Map<ResourceLocation, ClientEDConfig> clientEDConfigs) {
      Map<ResourceLocation, EDConfig> placementConfigs = structureConfig.enableDisableConfig;

      for (ResourceLocation structureName : clientEDConfigs.keySet()) {
         placementConfigs.put(structureName, clientEDConfigs.get(structureName).toED());
      }
   }

   private IntegerListEntry intEntry(ConfigEntryBuilder configEntry, String name, int value, int defaultValue, SubCategoryBuilder subCategory) {
      IntegerListEntry intEntry = configEntry.startIntField(Component.literal(name), value).setDefaultValue(defaultValue).build();
      subCategory.add(intEntry);
      return intEntry;
   }

   private DoubleListEntry frequencyEntry(ConfigEntryBuilder configEntry, String name, double value, double defaultValue, SubCategoryBuilder subCategory) {
      DoubleListEntry intEntry = configEntry.startDoubleField(Component.literal(name), value).setDefaultValue(defaultValue).setMin(0.0).setMax(1.0).build();
      subCategory.add(intEntry);
      return intEntry;
   }

   private static void getWarn(StructureConfig structureConfig, ResourceLocation structureSetName) {
      CristelLib.LOGGER
         .warn(
            "Structure Set: {} has no default config, skipping!\nThis probably indicates that this config file is outdated and should be deleted to re-create it. (Path: {})",
            structureSetName.toString(),
            structureConfig.getPath()
         );
   }

   private boolean getEDSubWarn(StructureConfig structureConfig, String fullPath, String structureSetName) {
      ResourceLocation setLocation = structureConfig.toDefaultRL(structureSetName);
      List<ResourceLocation> structures = structureConfig.getDefaultStructures().get(setLocation);
      if (structures == null) {
         getWarn(structureConfig, setLocation);
         return true;
      } else if (!structures.contains(structureConfig.toDefaultRL(fullPath))) {
         CristelLib.LOGGER
            .warn(
               "Structure: {} has no default config, skipping!\nThis probably indicates that this config file is outdated and should be deleted to re-create it. (Path: {})",
               fullPath,
               structureConfig.getPath()
            );
         return true;
      } else {
         return false;
      }
   }

   public void addHeader(StructureConfig structureConfig, ConfigCategory configCategory, ConfigEntryBuilder entryBuilder) {
      if (structureConfig.isAutoGenerated && !ACInfoData.currentData.containsKey(this.modID)) {
         configCategory.addEntry(
            entryBuilder.startTextDescription(
                  Component.translatable("cristellib.autoCategoryInfo", new Object[]{Util.CRISTEL_LIB})
                     .withStyle(
                        s -> s.withClickEvent(
                           new ClickEvent(
                              Action.OPEN_URL, "https://github.com/Cristelknight999/Cristel-Lib/wiki/5.-Controlling-Structure-Auto-Config-(for-Mod-Authors)"
                           )
                        )
                     )
               )
               .build()
         );
      } else if (structureConfig.getHeader() != null && !structureConfig.getHeader().isEmpty()) {
         configCategory.addEntry(entryBuilder.startTextDescription(Component.literal(this.getHeader(structureConfig.getHeader()))).build());
      }
   }

   public String getHeader(String header) {
      String separator = "=====";
      if (header.contains(separator)) {
         int start = header.lastIndexOf(separator);
         header = header.substring(0, start);
         List<String> list = new ArrayList<>(header.lines().toList());
         if (list.size() >= 2 && list.get(list.size() - 2).isBlank()) {
            list.removeLast();
            StringBuilder builder = new StringBuilder();
            list.forEach(line -> builder.append(line).append("\n"));
            header = builder.toString();
         }
      }

      return header.replace("\t", "    ").trim();
   }

   public List<StructureConfig> sorted(Set<StructureConfig> structureConfigs) {
      List<StructureConfig> sortedList = new ArrayList<>();
      structureConfigs.forEach(structureConfig -> {
         if (structureConfig.getType().equals(ConfigType.ENABLE_DISABLE)) {
            sortedList.addFirst(structureConfig);
         } else {
            sortedList.addLast(structureConfig);
         }
      });
      return sortedList;
   }

   public static Pair<Boolean, Boolean> shouldCreateScreen(String modID, boolean mainStructure) {
      if (!modID.equals("cristellib") && !modID.equals("minecraft")) {
         boolean structure = mainStructure
            && CristelLibRegistry.getConfigs().containsKey(modID)
            && !ConfigRegistry.get(ACConfig.class).clientExcludedMods().contains(modID);
         return new Pair(structure, ClientConfigRegistry.hasScreens(modID));
      } else {
         return new Pair(false, false);
      }
   }

   public static Set<String> allConfigMods(boolean structure) {
      Set<String> allMods = new HashSet<>(ClientConfigRegistry.getAllConfigsWithScreen().keySet());
      if (structure) {
         allMods.addAll(new HashSet<>(CristelLibRegistry.getConfigs().keySet()));
      }

      return allMods;
   }
}
