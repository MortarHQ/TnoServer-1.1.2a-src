package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.anti_ad.mc.common.config.options.ConfigButton;
import org.anti_ad.mc.common.config.options.ConfigColorPicker;
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.config.options.ConfigInteger;
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean;
import org.anti_ad.mc.common.config.options.ConfigString;
import org.anti_ad.mc.common.config.options.HandledConfigString;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsObject;
import org.jetbrains.annotations.NotNull;

public final class ModSettings implements ConfigDeclaration {
   @NotNull
   public static final ModSettings INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new ModSettings()), "§§vgap:3"
   );
   @NotNull
   private static final AsDelegate OPEN_CONFIG_FOLDER$delegate;
   @NotNull
   private static final AsDelegate RELOAD_RULE_FILES$delegate;
   @NotNull
   private static final AsDelegate SORT_ORDER$delegate;
   @NotNull
   private static final AsDelegate CUSTOM_RULE$delegate;
   @NotNull
   private static final AsDelegate CREATIVE_SORT_ORDER_TYPE$delegate;
   @NotNull
   private static final AsDelegate CATEGORY_PRIORITY_LIST$delegate;
   @NotNull
   private static final AsDelegate CATEGORY_ORIGINAL_ORDER$delegate;
   @NotNull
   private static final AsDelegate INCLUDE_HOTBAR_MODIFIER$delegate;
   @NotNull
   private static final AsDelegate MOVE_ALL_MODIFIER$delegate;
   @NotNull
   private static final AsDelegate MOVE_FOCUS_MACH_MODIFIER$delegate;
   @NotNull
   private static final AsDelegate MOVE_JUST_REFILL_MODIFIER$delegate;
   @NotNull
   private static final AsDelegate ALWAYS_INCLUDE_HOTBAR$delegate;
   @NotNull
   private static final AsDelegate ALWAYS_MOVE_ALL$delegate;
   @NotNull
   private static final AsDelegate ALWAYS_THROW_ALL$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_ITEMS$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_ITEMS_ANIMATED$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_ITEMS_FOREGROUND$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_ITEMS_COLOR$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_ITEMS_BG_COLOR$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_FOUSED_WAIT_TICKS$delegate;
   @NotNull
   private static final AsDelegate ENABLE_PROFILES$delegate;
   @NotNull
   private static final AsDelegate PROFILES_PER_SERVER$delegate;
   @NotNull
   private static final AsDelegate ENABLE_LOCK_SLOTS$delegate;
   @NotNull
   private static final AsDelegate ENABLE_LOCK_SLOTS_PER_SERVER$delegate;
   @NotNull
   private static final AsDelegate ENABLE_AUTO_REFILL$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_ENABLE$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_GROUP_1$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_GROUP_2$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_LOCAL_COLOR$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_GLOBAL_COLOR$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_LOCAL_COLOR1$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_GLOBAL_COLOR1$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_LOCAL_COLOR2$delegate;
   @NotNull
   private static final AsDelegate VILLAGER_TRADING_GLOBAL_COLOR2$delegate;
   @NotNull
   private static final AsDelegate ENABLE_UPDATES_CHECK$delegate;
   @NotNull
   private static final AsDelegate ADD_INTERVAL_BETWEEN_CLICKS$delegate;
   @NotNull
   private static final AsDelegate INTERVAL_BETWEEN_CLICKS_MS$delegate;
   @NotNull
   private static final AsDelegate AUTO_CRAFT_DELAY$delegate;
   @NotNull
   private static final AsDelegate CONTINUOUS_CRAFTING_METHOD$delegate;
   @NotNull
   private static final AsDelegate HIGHLIGHT_CLICKING_SLOT$delegate;
   @NotNull
   private static final AsDelegate RESTOCK_HOTBAR$delegate;
   @NotNull
   private static final AsDelegate SORT_AT_CURSOR$delegate;
   @NotNull
   private static final AsDelegate MOVE_ALL_AT_CURSOR$delegate;
   @NotNull
   private static final AsDelegate STOP_AT_SCREEN_CLOSE$delegate;
   @NotNull
   private static final AsDelegate IGNORE_DURABILITY$delegate;
   @NotNull
   private static final AsDelegate DEBUG$delegate;
   @NotNull
   private static final AsDelegate FOR_MODPACK_DEVS$delegate;
   @NotNull
   private static final AsDelegate FIRST_RUN$delegate;

   private ModSettings() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigButton getOPEN_CONFIG_FOLDER() {
      return (ConfigButton)OPEN_CONFIG_FOLDER$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigButton getRELOAD_RULE_FILES() {
      return (ConfigButton)RELOAD_RULE_FILES$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigEnum getSORT_ORDER() {
      return (ConfigEnum)SORT_ORDER$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigString getCUSTOM_RULE() {
      return (ConfigString)CUSTOM_RULE$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigEnum getCREATIVE_SORT_ORDER_TYPE() {
      return (ConfigEnum)CREATIVE_SORT_ORDER_TYPE$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final HandledConfigString getCATEGORY_PRIORITY_LIST() {
      return (HandledConfigString)CATEGORY_PRIORITY_LIST$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final HandledConfigString getCATEGORY_ORIGINAL_ORDER() {
      return (HandledConfigString)CATEGORY_ORIGINAL_ORDER$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigHotkey getINCLUDE_HOTBAR_MODIFIER() {
      return (ConfigHotkey)INCLUDE_HOTBAR_MODIFIER$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigHotkey getMOVE_ALL_MODIFIER() {
      return (ConfigHotkey)MOVE_ALL_MODIFIER$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigHotkey getMOVE_FOCUS_MACH_MODIFIER() {
      return (ConfigHotkey)MOVE_FOCUS_MACH_MODIFIER$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigHotkey getMOVE_JUST_REFILL_MODIFIER() {
      return (ConfigHotkey)MOVE_JUST_REFILL_MODIFIER$delegate.getValue(this, $$delegatedProperties[10]);
   }

   @NotNull
   public final ConfigBoolean getALWAYS_INCLUDE_HOTBAR() {
      return (ConfigBoolean)ALWAYS_INCLUDE_HOTBAR$delegate.getValue(this, $$delegatedProperties[11]);
   }

   @NotNull
   public final ConfigBoolean getALWAYS_MOVE_ALL() {
      return (ConfigBoolean)ALWAYS_MOVE_ALL$delegate.getValue(this, $$delegatedProperties[12]);
   }

   @NotNull
   public final ConfigBoolean getALWAYS_THROW_ALL() {
      return (ConfigBoolean)ALWAYS_THROW_ALL$delegate.getValue(this, $$delegatedProperties[13]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getHIGHLIGHT_FOUSED_ITEMS() {
      return (ConfigKeyToggleBoolean)HIGHLIGHT_FOUSED_ITEMS$delegate.getValue(this, $$delegatedProperties[14]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getHIGHLIGHT_FOUSED_ITEMS_ANIMATED() {
      return (ConfigKeyToggleBoolean)HIGHLIGHT_FOUSED_ITEMS_ANIMATED$delegate.getValue(this, $$delegatedProperties[15]);
   }

   @NotNull
   public final ConfigBoolean getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND() {
      return (ConfigBoolean)HIGHLIGHT_FOUSED_ITEMS_FOREGROUND$delegate.getValue(this, $$delegatedProperties[16]);
   }

   @NotNull
   public final ConfigColorPicker getHIGHLIGHT_FOUSED_ITEMS_COLOR() {
      return (ConfigColorPicker)HIGHLIGHT_FOUSED_ITEMS_COLOR$delegate.getValue(this, $$delegatedProperties[17]);
   }

   @NotNull
   public final ConfigColorPicker getHIGHLIGHT_FOUSED_ITEMS_BG_COLOR() {
      return (ConfigColorPicker)HIGHLIGHT_FOUSED_ITEMS_BG_COLOR$delegate.getValue(this, $$delegatedProperties[18]);
   }

   @NotNull
   public final ConfigInteger getHIGHLIGHT_FOUSED_WAIT_TICKS() {
      return (ConfigInteger)HIGHLIGHT_FOUSED_WAIT_TICKS$delegate.getValue(this, $$delegatedProperties[19]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_PROFILES() {
      return (ConfigKeyToggleBoolean)ENABLE_PROFILES$delegate.getValue(this, $$delegatedProperties[20]);
   }

   @NotNull
   public final ConfigBoolean getPROFILES_PER_SERVER() {
      return (ConfigBoolean)PROFILES_PER_SERVER$delegate.getValue(this, $$delegatedProperties[21]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_LOCK_SLOTS() {
      return (ConfigKeyToggleBoolean)ENABLE_LOCK_SLOTS$delegate.getValue(this, $$delegatedProperties[22]);
   }

   @NotNull
   public final ConfigBoolean getENABLE_LOCK_SLOTS_PER_SERVER() {
      return (ConfigBoolean)ENABLE_LOCK_SLOTS_PER_SERVER$delegate.getValue(this, $$delegatedProperties[23]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_AUTO_REFILL() {
      return (ConfigKeyToggleBoolean)ENABLE_AUTO_REFILL$delegate.getValue(this, $$delegatedProperties[24]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVILLAGER_TRADING_ENABLE() {
      return (ConfigKeyToggleBoolean)VILLAGER_TRADING_ENABLE$delegate.getValue(this, $$delegatedProperties[25]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVILLAGER_TRADING_GROUP_1() {
      return (ConfigKeyToggleBoolean)VILLAGER_TRADING_GROUP_1$delegate.getValue(this, $$delegatedProperties[26]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVILLAGER_TRADING_GROUP_2() {
      return (ConfigKeyToggleBoolean)VILLAGER_TRADING_GROUP_2$delegate.getValue(this, $$delegatedProperties[27]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_LOCAL_COLOR() {
      return (ConfigColorPicker)VILLAGER_TRADING_LOCAL_COLOR$delegate.getValue(this, $$delegatedProperties[28]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_GLOBAL_COLOR() {
      return (ConfigColorPicker)VILLAGER_TRADING_GLOBAL_COLOR$delegate.getValue(this, $$delegatedProperties[29]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_LOCAL_COLOR1() {
      return (ConfigColorPicker)VILLAGER_TRADING_LOCAL_COLOR1$delegate.getValue(this, $$delegatedProperties[30]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_GLOBAL_COLOR1() {
      return (ConfigColorPicker)VILLAGER_TRADING_GLOBAL_COLOR1$delegate.getValue(this, $$delegatedProperties[31]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_LOCAL_COLOR2() {
      return (ConfigColorPicker)VILLAGER_TRADING_LOCAL_COLOR2$delegate.getValue(this, $$delegatedProperties[32]);
   }

   @NotNull
   public final ConfigColorPicker getVILLAGER_TRADING_GLOBAL_COLOR2() {
      return (ConfigColorPicker)VILLAGER_TRADING_GLOBAL_COLOR2$delegate.getValue(this, $$delegatedProperties[33]);
   }

   @NotNull
   public final ConfigBoolean getENABLE_UPDATES_CHECK() {
      return (ConfigBoolean)ENABLE_UPDATES_CHECK$delegate.getValue(this, $$delegatedProperties[34]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getADD_INTERVAL_BETWEEN_CLICKS() {
      return (ConfigKeyToggleBoolean)ADD_INTERVAL_BETWEEN_CLICKS$delegate.getValue(this, $$delegatedProperties[35]);
   }

   @NotNull
   public final ConfigInteger getINTERVAL_BETWEEN_CLICKS_MS() {
      return (ConfigInteger)INTERVAL_BETWEEN_CLICKS_MS$delegate.getValue(this, $$delegatedProperties[36]);
   }

   @NotNull
   public final ConfigInteger getAUTO_CRAFT_DELAY() {
      return (ConfigInteger)AUTO_CRAFT_DELAY$delegate.getValue(this, $$delegatedProperties[37]);
   }

   @NotNull
   public final ConfigInteger getCONTINUOUS_CRAFTING_METHOD() {
      return (ConfigInteger)CONTINUOUS_CRAFTING_METHOD$delegate.getValue(this, $$delegatedProperties[38]);
   }

   @NotNull
   public final ConfigBoolean getHIGHLIGHT_CLICKING_SLOT() {
      return (ConfigBoolean)HIGHLIGHT_CLICKING_SLOT$delegate.getValue(this, $$delegatedProperties[39]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getRESTOCK_HOTBAR() {
      return (ConfigKeyToggleBoolean)RESTOCK_HOTBAR$delegate.getValue(this, $$delegatedProperties[40]);
   }

   @NotNull
   public final ConfigBoolean getSORT_AT_CURSOR() {
      return (ConfigBoolean)SORT_AT_CURSOR$delegate.getValue(this, $$delegatedProperties[41]);
   }

   @NotNull
   public final ConfigBoolean getMOVE_ALL_AT_CURSOR() {
      return (ConfigBoolean)MOVE_ALL_AT_CURSOR$delegate.getValue(this, $$delegatedProperties[42]);
   }

   @NotNull
   public final ConfigBoolean getSTOP_AT_SCREEN_CLOSE() {
      return (ConfigBoolean)STOP_AT_SCREEN_CLOSE$delegate.getValue(this, $$delegatedProperties[43]);
   }

   @NotNull
   public final ConfigBoolean getIGNORE_DURABILITY() {
      return (ConfigBoolean)IGNORE_DURABILITY$delegate.getValue(this, $$delegatedProperties[44]);
   }

   @NotNull
   public final ConfigBoolean getDEBUG() {
      return (ConfigBoolean)DEBUG$delegate.getValue(this, $$delegatedProperties[45]);
   }

   @NotNull
   public final ConfigBoolean getFOR_MODPACK_DEVS() {
      return (ConfigBoolean)FOR_MODPACK_DEVS$delegate.getValue(this, $$delegatedProperties[46]);
   }

   @NotNull
   public final ConfigBoolean getFIRST_RUN() {
      return (ConfigBoolean)FIRST_RUN$delegate.getValue(this, $$delegatedProperties[47]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[48])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "OPEN_CONFIG_FOLDER", "getOPEN_CONFIG_FOLDER()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0)
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "RELOAD_RULE_FILES", "getRELOAD_RULE_FILES()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0)
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "SORT_ORDER", "getSORT_ORDER()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "CUSTOM_RULE", "getCUSTOM_RULE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0)
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "CREATIVE_SORT_ORDER_TYPE", "getCREATIVE_SORT_ORDER_TYPE()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "CATEGORY_PRIORITY_LIST", "getCATEGORY_PRIORITY_LIST()Lorg/anti_ad/mc/common/config/options/HandledConfigString;", 0
            )
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "CATEGORY_ORIGINAL_ORDER", "getCATEGORY_ORIGINAL_ORDER()Lorg/anti_ad/mc/common/config/options/HandledConfigString;", 0
            )
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "INCLUDE_HOTBAR_MODIFIER", "getINCLUDE_HOTBAR_MODIFIER()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "MOVE_ALL_MODIFIER", "getMOVE_ALL_MODIFIER()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "MOVE_FOCUS_MACH_MODIFIER", "getMOVE_FOCUS_MACH_MODIFIER()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "MOVE_JUST_REFILL_MODIFIER", "getMOVE_JUST_REFILL_MODIFIER()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[11] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ALWAYS_INCLUDE_HOTBAR", "getALWAYS_INCLUDE_HOTBAR()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[12] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "ALWAYS_MOVE_ALL", "getALWAYS_MOVE_ALL()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[13] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "ALWAYS_THROW_ALL", "getALWAYS_THROW_ALL()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[14] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "HIGHLIGHT_FOUSED_ITEMS", "getHIGHLIGHT_FOUSED_ITEMS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[15] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "HIGHLIGHT_FOUSED_ITEMS_ANIMATED",
               "getHIGHLIGHT_FOUSED_ITEMS_ANIMATED()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[16] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "HIGHLIGHT_FOUSED_ITEMS_FOREGROUND",
               "getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[17] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "HIGHLIGHT_FOUSED_ITEMS_COLOR",
               "getHIGHLIGHT_FOUSED_ITEMS_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[18] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "HIGHLIGHT_FOUSED_ITEMS_BG_COLOR",
               "getHIGHLIGHT_FOUSED_ITEMS_BG_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[19] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "HIGHLIGHT_FOUSED_WAIT_TICKS", "getHIGHLIGHT_FOUSED_WAIT_TICKS()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[20] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ENABLE_PROFILES", "getENABLE_PROFILES()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[21] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "PROFILES_PER_SERVER", "getPROFILES_PER_SERVER()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[22] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ENABLE_LOCK_SLOTS", "getENABLE_LOCK_SLOTS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[23] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ENABLE_LOCK_SLOTS_PER_SERVER", "getENABLE_LOCK_SLOTS_PER_SERVER()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[24] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ENABLE_AUTO_REFILL", "getENABLE_AUTO_REFILL()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[25] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "VILLAGER_TRADING_ENABLE", "getVILLAGER_TRADING_ENABLE()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[26] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "VILLAGER_TRADING_GROUP_1", "getVILLAGER_TRADING_GROUP_1()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[27] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "VILLAGER_TRADING_GROUP_2", "getVILLAGER_TRADING_GROUP_2()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[28] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_LOCAL_COLOR",
               "getVILLAGER_TRADING_LOCAL_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[29] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_GLOBAL_COLOR",
               "getVILLAGER_TRADING_GLOBAL_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[30] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_LOCAL_COLOR1",
               "getVILLAGER_TRADING_LOCAL_COLOR1()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[31] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_GLOBAL_COLOR1",
               "getVILLAGER_TRADING_GLOBAL_COLOR1()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[32] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_LOCAL_COLOR2",
               "getVILLAGER_TRADING_LOCAL_COLOR2()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[33] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "VILLAGER_TRADING_GLOBAL_COLOR2",
               "getVILLAGER_TRADING_GLOBAL_COLOR2()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[34] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "ENABLE_UPDATES_CHECK", "getENABLE_UPDATES_CHECK()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[35] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class,
               "ADD_INTERVAL_BETWEEN_CLICKS",
               "getADD_INTERVAL_BETWEEN_CLICKS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[36] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "INTERVAL_BETWEEN_CLICKS_MS", "getINTERVAL_BETWEEN_CLICKS_MS()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[37] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "AUTO_CRAFT_DELAY", "getAUTO_CRAFT_DELAY()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0)
         )
      );
      var0[38] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "CONTINUOUS_CRAFTING_METHOD", "getCONTINUOUS_CRAFTING_METHOD()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[39] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "HIGHLIGHT_CLICKING_SLOT", "getHIGHLIGHT_CLICKING_SLOT()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[40] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "RESTOCK_HOTBAR", "getRESTOCK_HOTBAR()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[41] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "SORT_AT_CURSOR", "getSORT_AT_CURSOR()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[42] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "MOVE_ALL_AT_CURSOR", "getMOVE_ALL_AT_CURSOR()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[43] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ModSettings.class, "STOP_AT_SCREEN_CLOSE", "getSTOP_AT_SCREEN_CLOSE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[44] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "IGNORE_DURABILITY", "getIGNORE_DURABILITY()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[45] = Reflection.property1(
         (PropertyReference1)(new PropertyReference1Impl(ModSettings.class, "DEBUG", "getDEBUG()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0))
      );
      var0[46] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "FOR_MODPACK_DEVS", "getFOR_MODPACK_DEVS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[47] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ModSettings.class, "FIRST_RUN", "getFIRST_RUN()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      $$delegatedProperties = var0;
      OPEN_CONFIG_FOLDER$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, OpenConfigFolderButtonInfo.INSTANCE)
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      RELOAD_RULE_FILES$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.button(INSTANCE, ReloadRuleFileButtonInfo.INSTANCE), "inventoryprofiles.config.category.inventory_sorting"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      SORT_ORDER$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, SortingMethod.DEFAULT).provideDelegate(INSTANCE, $$delegatedProperties[2]);
      CUSTOM_RULE$delegate = ConfigDeclarationBuilderKt.CATEGORY(ConfigDeclarationBuilderKt.string(INSTANCE, "@custom"), "§§hide")
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      CREATIVE_SORT_ORDER_TYPE$delegate = ConfigDeclarationBuilderKt.enumForMinMCVersion(INSTANCE, 1193, 1192, CreativeMenuSortOrder.SEARCH_TAB)
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      CATEGORY_PRIORITY_LIST$delegate = ConfigDeclarationBuilderKt.handledStringForMinMCVersion(
            INSTANCE,
            1193,
            1192,
            "itemGroup.tools, itemGroup.combat, itemGroup.redstone, itemGroup.coloredBlocks, itemGroup.functional, itemGroup.natural, itemGroup.buildingBlocks, itemGroup.foodAndDrink, itemGroup.ingredients, itemGroup.spawnEggs",
            new ModSettings$CATEGORY_PRIORITY_LIST$2(ItemTypeExtensionsObject.INSTANCE)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      CATEGORY_ORIGINAL_ORDER$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.handledStringForMinMCVersion(
               INSTANCE,
               1193,
               1192,
               ItemTypeExtensionsObject.INSTANCE.makeDefaultList(),
               new ModSettings$CATEGORY_ORIGINAL_ORDER$2(ItemTypeExtensionsObject.INSTANCE)
            ),
            "inventoryprofiles.config.category.move_matching_items"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[6]);
      INCLUDE_HOTBAR_MODIFIER$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_ALT", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[7]);
      MOVE_ALL_MODIFIER$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_SHIFT", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[8]);
      MOVE_FOCUS_MACH_MODIFIER$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_CONTROL", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[9]);
      MOVE_JUST_REFILL_MODIFIER$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "CAPS_LOCK", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
      ALWAYS_INCLUDE_HOTBAR$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[11]);
      ALWAYS_MOVE_ALL$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[12]);
      ALWAYS_THROW_ALL$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, false), "inventoryprofiles.config.category.highlight_focused_items"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[13]);
      HIGHLIGHT_FOUSED_ITEMS$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[14]);
      HIGHLIGHT_FOUSED_ITEMS_ANIMATED$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[15]);
      HIGHLIGHT_FOUSED_ITEMS_FOREGROUND$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[16]);
      HIGHLIGHT_FOUSED_ITEMS_COLOR$delegate = ConfigDeclarationBuilderKt.color(
            INSTANCE, ColorKt.blue(ColorKt.green(ColorKt.red(ColorKt.asAlpha(112), 1), 182), 11)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[17]);
      HIGHLIGHT_FOUSED_ITEMS_BG_COLOR$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.blue(ColorKt.green(ColorKt.red(ColorKt.asAlpha(170), 1), 182), 11)), "§§hide"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[18]);
      HIGHLIGHT_FOUSED_WAIT_TICKS$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.int(INSTANCE, 5, 3, 15), "inventoryprofiles.config.category.profiles"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[19]);
      ENABLE_PROFILES$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null), "§§hide"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[20]);
      PROFILES_PER_SERVER$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.lock_slots"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[21]);
      ENABLE_LOCK_SLOTS$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[22]);
      ENABLE_LOCK_SLOTS_PER_SERVER$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.auto_refill"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[23]);
      ENABLE_AUTO_REFILL$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null),
            "inventoryprofiles.config.category.villager_trading"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[24]);
      VILLAGER_TRADING_ENABLE$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[25]);
      VILLAGER_TRADING_GROUP_1$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[26]);
      VILLAGER_TRADING_GROUP_2$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[27]);
      VILLAGER_TRADING_LOCAL_COLOR$delegate = ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 150), 1), 11))
         .provideDelegate(INSTANCE, $$delegatedProperties[28]);
      VILLAGER_TRADING_GLOBAL_COLOR$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 1), 150), 11)), "§§vgap:5"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[29]);
      VILLAGER_TRADING_LOCAL_COLOR1$delegate = ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 53), 63), 162))
         .provideDelegate(INSTANCE, $$delegatedProperties[30]);
      VILLAGER_TRADING_GLOBAL_COLOR1$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 255), 118), 61)), "§§vgap:5"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[31]);
      VILLAGER_TRADING_LOCAL_COLOR2$delegate = ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 246), 223), 101))
         .provideDelegate(INSTANCE, $$delegatedProperties[32]);
      VILLAGER_TRADING_GLOBAL_COLOR2$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.color(INSTANCE, ColorKt.b(ColorKt.g(ColorKt.r(130, 215), 116), 235)), "inventoryprofiles.config.category.privacy"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[33]);
      ENABLE_UPDATES_CHECK$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.advanced_options"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[34]);
      ADD_INTERVAL_BETWEEN_CLICKS$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[35]);
      INTERVAL_BETWEEN_CLICKS_MS$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 10, 1, 500).provideDelegate(INSTANCE, $$delegatedProperties[36]);
      AUTO_CRAFT_DELAY$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 1, 1, 20).provideDelegate(INSTANCE, $$delegatedProperties[37]);
      CONTINUOUS_CRAFTING_METHOD$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 1, 1, 2).provideDelegate(INSTANCE, $$delegatedProperties[38]);
      HIGHLIGHT_CLICKING_SLOT$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[39]);
      RESTOCK_HOTBAR$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[40]);
      SORT_AT_CURSOR$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[41]);
      MOVE_ALL_AT_CURSOR$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[42]);
      STOP_AT_SCREEN_CLOSE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[43]);
      IGNORE_DURABILITY$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.debugs"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[44]);
      DEBUG$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[45]);
      FOR_MODPACK_DEVS$delegate = ConfigDeclarationBuilderKt.CATEGORY(ConfigDeclarationBuilderKt.bool(INSTANCE, false), "§§hide - first run")
         .provideDelegate(INSTANCE, $$delegatedProperties[46]);
      FIRST_RUN$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[47]);
   }
}
