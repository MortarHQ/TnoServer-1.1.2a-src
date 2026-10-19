package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.input.KeybindSettings.Context;
import org.anti_ad.mc.common.input.KeybindSettings.KeyAction;
import org.jetbrains.annotations.NotNull;

public final class Hotkeys implements ConfigDeclaration {
   @NotNull
   public static final Hotkeys INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new Hotkeys()), "inventoryprofiles.config.category.hotkeys"
   );
   @NotNull
   private static final AsDelegate OPEN_CONFIG_MENU$delegate;
   @NotNull
   private static final AsDelegate RELOAD_CUSTOM_CONFIGS$delegate;
   @NotNull
   private static final AsDelegate SORT_INVENTORY$delegate;
   @NotNull
   private static final AsDelegate SORT_INVENTORY_IN_COLUMNS$delegate;
   @NotNull
   private static final AsDelegate SORT_INVENTORY_IN_ROWS$delegate;
   @NotNull
   private static final AsDelegate MOVE_ALL_ITEMS$delegate;
   @NotNull
   private static final AsDelegate THROW_ALL_ITEMS$delegate;
   @NotNull
   private static final AsDelegate SCROLL_TO_CHEST$delegate;
   @NotNull
   private static final AsDelegate SCROLL_TO_INVENTORY$delegate;
   @NotNull
   private static final AsDelegate OPEN_GUI_EDITOR$delegate;
   @NotNull
   private static final AsDelegate APPLY_PROFILE$delegate;
   @NotNull
   private static final AsDelegate NEXT_PROFILE$delegate;
   @NotNull
   private static final AsDelegate PREV_PROFILE$delegate;
   @NotNull
   private static final AsDelegate PROFILE_1$delegate;
   @NotNull
   private static final AsDelegate PROFILE_2$delegate;
   @NotNull
   private static final AsDelegate PROFILE_3$delegate;
   @NotNull
   private static final AsDelegate SAVE_AS_PROFILE$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_GUI_TOGGLE_FOR_SLOT$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_GAME_TOGGLE_FOR_SLOT$delegate;
   @NotNull
   private static final AsDelegate GLOBAL_BOOKMARK_TRADE$delegate;
   @NotNull
   private static final AsDelegate LOCAL_BOOKMARK_TRADE$delegate;
   @NotNull
   private static final AsDelegate DO_GLOBAL_TRADE$delegate;
   @NotNull
   private static final AsDelegate DO_LOCAL_TRADE$delegate;
   @NotNull
   private static final AsDelegate GLOBAL_BOOKMARK_TRADE1$delegate;
   @NotNull
   private static final AsDelegate LOCAL_BOOKMARK_TRADE1$delegate;
   @NotNull
   private static final AsDelegate DO_GLOBAL_TRADE1$delegate;
   @NotNull
   private static final AsDelegate DO_LOCAL_TRADE1$delegate;
   @NotNull
   private static final AsDelegate GLOBAL_BOOKMARK_TRADE2$delegate;
   @NotNull
   private static final AsDelegate LOCAL_BOOKMARK_TRADE2$delegate;
   @NotNull
   private static final AsDelegate DO_GLOBAL_TRADE2$delegate;
   @NotNull
   private static final AsDelegate DO_LOCAL_TRADE2$delegate;
   @NotNull
   private static final AsDelegate DUMP_ITEM_NBT_TO_CHAT$delegate;
   @NotNull
   private static final AsDelegate COPY_COMPONENTS$delegate;
   @NotNull
   private static final AsDelegate COPY_ITEM_ID$delegate;

   private Hotkeys() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigHotkey getOPEN_CONFIG_MENU() {
      return (ConfigHotkey)OPEN_CONFIG_MENU$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigHotkey getRELOAD_CUSTOM_CONFIGS() {
      return (ConfigHotkey)RELOAD_CUSTOM_CONFIGS$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigHotkey getSORT_INVENTORY() {
      return (ConfigHotkey)SORT_INVENTORY$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigHotkey getSORT_INVENTORY_IN_COLUMNS() {
      return (ConfigHotkey)SORT_INVENTORY_IN_COLUMNS$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigHotkey getSORT_INVENTORY_IN_ROWS() {
      return (ConfigHotkey)SORT_INVENTORY_IN_ROWS$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigHotkey getMOVE_ALL_ITEMS() {
      return (ConfigHotkey)MOVE_ALL_ITEMS$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final ConfigHotkey getTHROW_ALL_ITEMS() {
      return (ConfigHotkey)THROW_ALL_ITEMS$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_TO_CHEST() {
      return (ConfigHotkey)SCROLL_TO_CHEST$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_TO_INVENTORY() {
      return (ConfigHotkey)SCROLL_TO_INVENTORY$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigHotkey getOPEN_GUI_EDITOR() {
      return (ConfigHotkey)OPEN_GUI_EDITOR$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigHotkey getAPPLY_PROFILE() {
      return (ConfigHotkey)APPLY_PROFILE$delegate.getValue(this, $$delegatedProperties[10]);
   }

   @NotNull
   public final ConfigHotkey getNEXT_PROFILE() {
      return (ConfigHotkey)NEXT_PROFILE$delegate.getValue(this, $$delegatedProperties[11]);
   }

   @NotNull
   public final ConfigHotkey getPREV_PROFILE() {
      return (ConfigHotkey)PREV_PROFILE$delegate.getValue(this, $$delegatedProperties[12]);
   }

   @NotNull
   public final ConfigHotkey getPROFILE_1() {
      return (ConfigHotkey)PROFILE_1$delegate.getValue(this, $$delegatedProperties[13]);
   }

   @NotNull
   public final ConfigHotkey getPROFILE_2() {
      return (ConfigHotkey)PROFILE_2$delegate.getValue(this, $$delegatedProperties[14]);
   }

   @NotNull
   public final ConfigHotkey getPROFILE_3() {
      return (ConfigHotkey)PROFILE_3$delegate.getValue(this, $$delegatedProperties[15]);
   }

   @NotNull
   public final ConfigHotkey getSAVE_AS_PROFILE() {
      return (ConfigHotkey)SAVE_AS_PROFILE$delegate.getValue(this, $$delegatedProperties[16]);
   }

   @NotNull
   public final ConfigHotkey getAUTO_REFILL_GUI_TOGGLE_FOR_SLOT() {
      return (ConfigHotkey)AUTO_REFILL_GUI_TOGGLE_FOR_SLOT$delegate.getValue(this, $$delegatedProperties[17]);
   }

   @NotNull
   public final ConfigHotkey getAUTO_REFILL_GAME_TOGGLE_FOR_SLOT() {
      return (ConfigHotkey)AUTO_REFILL_GAME_TOGGLE_FOR_SLOT$delegate.getValue(this, $$delegatedProperties[18]);
   }

   @NotNull
   public final ConfigHotkey getGLOBAL_BOOKMARK_TRADE() {
      return (ConfigHotkey)GLOBAL_BOOKMARK_TRADE$delegate.getValue(this, $$delegatedProperties[19]);
   }

   @NotNull
   public final ConfigHotkey getLOCAL_BOOKMARK_TRADE() {
      return (ConfigHotkey)LOCAL_BOOKMARK_TRADE$delegate.getValue(this, $$delegatedProperties[20]);
   }

   @NotNull
   public final ConfigHotkey getDO_GLOBAL_TRADE() {
      return (ConfigHotkey)DO_GLOBAL_TRADE$delegate.getValue(this, $$delegatedProperties[21]);
   }

   @NotNull
   public final ConfigHotkey getDO_LOCAL_TRADE() {
      return (ConfigHotkey)DO_LOCAL_TRADE$delegate.getValue(this, $$delegatedProperties[22]);
   }

   @NotNull
   public final ConfigHotkey getGLOBAL_BOOKMARK_TRADE1() {
      return (ConfigHotkey)GLOBAL_BOOKMARK_TRADE1$delegate.getValue(this, $$delegatedProperties[23]);
   }

   @NotNull
   public final ConfigHotkey getLOCAL_BOOKMARK_TRADE1() {
      return (ConfigHotkey)LOCAL_BOOKMARK_TRADE1$delegate.getValue(this, $$delegatedProperties[24]);
   }

   @NotNull
   public final ConfigHotkey getDO_GLOBAL_TRADE1() {
      return (ConfigHotkey)DO_GLOBAL_TRADE1$delegate.getValue(this, $$delegatedProperties[25]);
   }

   @NotNull
   public final ConfigHotkey getDO_LOCAL_TRADE1() {
      return (ConfigHotkey)DO_LOCAL_TRADE1$delegate.getValue(this, $$delegatedProperties[26]);
   }

   @NotNull
   public final ConfigHotkey getGLOBAL_BOOKMARK_TRADE2() {
      return (ConfigHotkey)GLOBAL_BOOKMARK_TRADE2$delegate.getValue(this, $$delegatedProperties[27]);
   }

   @NotNull
   public final ConfigHotkey getLOCAL_BOOKMARK_TRADE2() {
      return (ConfigHotkey)LOCAL_BOOKMARK_TRADE2$delegate.getValue(this, $$delegatedProperties[28]);
   }

   @NotNull
   public final ConfigHotkey getDO_GLOBAL_TRADE2() {
      return (ConfigHotkey)DO_GLOBAL_TRADE2$delegate.getValue(this, $$delegatedProperties[29]);
   }

   @NotNull
   public final ConfigHotkey getDO_LOCAL_TRADE2() {
      return (ConfigHotkey)DO_LOCAL_TRADE2$delegate.getValue(this, $$delegatedProperties[30]);
   }

   @NotNull
   public final ConfigHotkey getDUMP_ITEM_NBT_TO_CHAT() {
      return (ConfigHotkey)DUMP_ITEM_NBT_TO_CHAT$delegate.getValue(this, $$delegatedProperties[31]);
   }

   @NotNull
   public final ConfigHotkey getCOPY_COMPONENTS() {
      return (ConfigHotkey)COPY_COMPONENTS$delegate.getValue(this, $$delegatedProperties[32]);
   }

   @NotNull
   public final ConfigHotkey getCOPY_ITEM_ID() {
      return (ConfigHotkey)COPY_ITEM_ID$delegate.getValue(this, $$delegatedProperties[33]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[34])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "OPEN_CONFIG_MENU", "getOPEN_CONFIG_MENU()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "RELOAD_CUSTOM_CONFIGS", "getRELOAD_CUSTOM_CONFIGS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "SORT_INVENTORY", "getSORT_INVENTORY()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "SORT_INVENTORY_IN_COLUMNS", "getSORT_INVENTORY_IN_COLUMNS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "SORT_INVENTORY_IN_ROWS", "getSORT_INVENTORY_IN_ROWS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "MOVE_ALL_ITEMS", "getMOVE_ALL_ITEMS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "THROW_ALL_ITEMS", "getTHROW_ALL_ITEMS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "SCROLL_TO_CHEST", "getSCROLL_TO_CHEST()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "SCROLL_TO_INVENTORY", "getSCROLL_TO_INVENTORY()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "OPEN_GUI_EDITOR", "getOPEN_GUI_EDITOR()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "APPLY_PROFILE", "getAPPLY_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[11] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "NEXT_PROFILE", "getNEXT_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[12] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "PREV_PROFILE", "getPREV_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[13] = Reflection.property1(
         (PropertyReference1)(new PropertyReference1Impl(Hotkeys.class, "PROFILE_1", "getPROFILE_1()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0))
      );
      var0[14] = Reflection.property1(
         (PropertyReference1)(new PropertyReference1Impl(Hotkeys.class, "PROFILE_2", "getPROFILE_2()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0))
      );
      var0[15] = Reflection.property1(
         (PropertyReference1)(new PropertyReference1Impl(Hotkeys.class, "PROFILE_3", "getPROFILE_3()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0))
      );
      var0[16] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "SAVE_AS_PROFILE", "getSAVE_AS_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[17] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "AUTO_REFILL_GUI_TOGGLE_FOR_SLOT", "getAUTO_REFILL_GUI_TOGGLE_FOR_SLOT()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[18] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "AUTO_REFILL_GAME_TOGGLE_FOR_SLOT", "getAUTO_REFILL_GAME_TOGGLE_FOR_SLOT()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[19] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "GLOBAL_BOOKMARK_TRADE", "getGLOBAL_BOOKMARK_TRADE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[20] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "LOCAL_BOOKMARK_TRADE", "getLOCAL_BOOKMARK_TRADE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[21] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_GLOBAL_TRADE", "getDO_GLOBAL_TRADE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[22] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_LOCAL_TRADE", "getDO_LOCAL_TRADE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[23] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "GLOBAL_BOOKMARK_TRADE1", "getGLOBAL_BOOKMARK_TRADE1()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[24] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "LOCAL_BOOKMARK_TRADE1", "getLOCAL_BOOKMARK_TRADE1()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[25] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_GLOBAL_TRADE1", "getDO_GLOBAL_TRADE1()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[26] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_LOCAL_TRADE1", "getDO_LOCAL_TRADE1()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[27] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "GLOBAL_BOOKMARK_TRADE2", "getGLOBAL_BOOKMARK_TRADE2()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[28] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "LOCAL_BOOKMARK_TRADE2", "getLOCAL_BOOKMARK_TRADE2()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[29] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_GLOBAL_TRADE2", "getDO_GLOBAL_TRADE2()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[30] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "DO_LOCAL_TRADE2", "getDO_LOCAL_TRADE2()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[31] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Hotkeys.class, "DUMP_ITEM_NBT_TO_CHAT", "getDUMP_ITEM_NBT_TO_CHAT()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[32] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "COPY_COMPONENTS", "getCOPY_COMPONENTS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[33] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Hotkeys.class, "COPY_ITEM_ID", "getCOPY_ITEM_ID()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      $$delegatedProperties = var0;
      OPEN_CONFIG_MENU$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,C", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      RELOAD_CUSTOM_CONFIGS$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,Y", KeybindSettings.Companion.getANY_DEFAULT()), "inventoryprofiles.config.category.inventory"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      SORT_INVENTORY$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[2]);
      SORT_INVENTORY_IN_COLUMNS$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      SORT_INVENTORY_IN_ROWS$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      MOVE_ALL_ITEMS$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,T", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      THROW_ALL_ITEMS$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,M", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[6]);
      SCROLL_TO_CHEST$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "MOUSE_SCROLL_UP", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[7]);
      SCROLL_TO_INVENTORY$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "MOUSE_SCROLL_DOWN", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[8]);
      OPEN_GUI_EDITOR$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,G", KeybindSettings.Companion.getGUI_DEFAULT()), "inventoryprofiles.config.category.profiles"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[9]);
      APPLY_PROFILE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,H", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
      NEXT_PROFILE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,Z", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[11]);
      PREV_PROFILE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,X", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[12]);
      PROFILE_1$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,1", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[13]);
      PROFILE_2$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,2", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[14]);
      PROFILE_3$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,3", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[15]);
      SAVE_AS_PROFILE$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(INSTANCE, "R,P", KeybindSettings.Companion.getGUI_DEFAULT()), "inventoryprofiles.config.category.auto_refill"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[16]);
      AUTO_REFILL_GUI_TOGGLE_FOR_SLOT$delegate = ConfigDeclarationBuilderKt.hotkey(
            INSTANCE, "LEFT_CONTROL,BUTTON_1", KeybindSettings.Companion.getANY_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[17]);
      AUTO_REFILL_GAME_TOGGLE_FOR_SLOT$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(
               INSTANCE, "LEFT_CONTROL,R", KeybindSettings.copy$default(KeybindSettings.Companion.getINGAME_DEFAULT(), null, null, true, false, null, 19, null)
            ),
            "inventoryprofiles.config.category.villager_trading"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[18]);
      GLOBAL_BOOKMARK_TRADE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "B", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[19]);
      LOCAL_BOOKMARK_TRADE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "L", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[20]);
      DO_GLOBAL_TRADE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "T", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[21]);
      DO_LOCAL_TRADE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_SHIFT,T", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[22]);
      GLOBAL_BOOKMARK_TRADE1$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[23]);
      LOCAL_BOOKMARK_TRADE1$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[24]);
      DO_GLOBAL_TRADE1$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[25]);
      DO_LOCAL_TRADE1$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[26]);
      GLOBAL_BOOKMARK_TRADE2$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[27]);
      LOCAL_BOOKMARK_TRADE2$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[28]);
      DO_GLOBAL_TRADE2$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[29]);
      DO_LOCAL_TRADE2$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT()), "inventoryprofiles.config.category.misc"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[30]);
      DUMP_ITEM_NBT_TO_CHAT$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[31]);
      COPY_COMPONENTS$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_CONTROL,C", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[32]);
      COPY_ITEM_ID$delegate = ConfigDeclarationBuilderKt.hotkey(
            INSTANCE, "LEFT_CONTROL,LEFT_SHIFT,C", new KeybindSettings(Context.GUI, KeyAction.PRESS, false, false, null, 16, null)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[33]);
   }
}
