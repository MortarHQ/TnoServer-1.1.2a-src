package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigInteger;
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean;
import org.anti_ad.mc.common.config.options.ConfigString;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.jetbrains.annotations.NotNull;

public final class GuiSettings implements ConfigDeclaration {
   @NotNull
   public static final GuiSettings INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new GuiSettings()), "inventoryprofiles.config.category.gui.general"
   );
   @NotNull
   private static final AsDelegate INVENTORY_OVERLAY_BUTTONS_VISIBLE$delegate;
   @NotNull
   private static final AsDelegate ENABLE_INVENTORY_EDITOR_BUTTON$delegate;
   @NotNull
   private static final AsDelegate ENABLE_INVENTORY_SETTINGS_BUTTON$delegate;
   @NotNull
   private static final AsDelegate ENABLE_PROFILES_UI$delegate;
   @NotNull
   private static final AsDelegate ENABLE_PROFILES_ANNOUNCEMENT$delegate;
   @NotNull
   private static final AsDelegate ENABLE_INVENTORY_BUTTONS$delegate;
   @NotNull
   private static final AsDelegate SHOW_BUTTONS_BOTH_SIDES$delegate;
   @NotNull
   private static final AsDelegate SHOW_FAST_RENAME_CHECKBOX$delegate;
   @NotNull
   private static final AsDelegate FAST_RENAME_CHECKBOX_VALUE$delegate;
   @NotNull
   private static final AsDelegate FAST_RENAME_SAVED_VALUE$delegate;
   @NotNull
   private static final AsDelegate SHOW_CONTINUOUS_CRAFTING_CHECKBOX$delegate;
   @NotNull
   private static final AsDelegate CONTINUOUS_CRAFTING_CHECKBOX_VALUE$delegate;
   @NotNull
   private static final AsDelegate CONTINUOUS_CRAFTING_SAVED_VALUE$delegate;
   @NotNull
   private static final AsDelegate SHOW_REGULAR_SORT_BUTTON$delegate;
   @NotNull
   private static final AsDelegate REGULAR_POST_ACTION$delegate;
   @NotNull
   private static final AsDelegate REGULAR_SORT_ORDER$delegate;
   @NotNull
   private static final AsDelegate REGULAR_CUSTOM_RULE$delegate;
   @NotNull
   private static final AsDelegate CHANGE_SORT_ORDER_WITH_SCROLL_WHEEL$delegate;
   @NotNull
   private static final AsDelegate SHOW_SORT_IN_COLUMNS_BUTTON$delegate;
   @NotNull
   private static final AsDelegate IN_COLUMNS_POST_ACTION$delegate;
   @NotNull
   private static final AsDelegate IN_COLUMNS_SORT_ORDER$delegate;
   @NotNull
   private static final AsDelegate IN_COLUMNS_CUSTOM_RULE$delegate;
   @NotNull
   private static final AsDelegate SHOW_SORT_IN_ROWS_BUTTON$delegate;
   @NotNull
   private static final AsDelegate IN_ROWS_POST_ACTION$delegate;
   @NotNull
   private static final AsDelegate IN_ROWS_SORT_ORDER$delegate;
   @NotNull
   private static final AsDelegate IN_ROWS_CUSTOM_RULE$delegate;
   @NotNull
   private static final AsDelegate SHOW_MOVE_ALL_BUTTON$delegate;
   @NotNull
   private static final AsDelegate SHOW_BUTTON_TOOLTIPS$delegate;
   @NotNull
   private static final AsDelegate SETTINGS_TOP$delegate;
   @NotNull
   private static final AsDelegate SETTINGS_LEFT$delegate;
   @NotNull
   private static final AsDelegate EDITOR_TOP$delegate;
   @NotNull
   private static final AsDelegate EDITOR_LEFT$delegate;

   private GuiSettings() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigKeyToggleBoolean getINVENTORY_OVERLAY_BUTTONS_VISIBLE() {
      return (ConfigKeyToggleBoolean)INVENTORY_OVERLAY_BUTTONS_VISIBLE$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_INVENTORY_EDITOR_BUTTON() {
      return (ConfigKeyToggleBoolean)ENABLE_INVENTORY_EDITOR_BUTTON$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_INVENTORY_SETTINGS_BUTTON() {
      return (ConfigKeyToggleBoolean)ENABLE_INVENTORY_SETTINGS_BUTTON$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_PROFILES_UI() {
      return (ConfigKeyToggleBoolean)ENABLE_PROFILES_UI$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigBoolean getENABLE_PROFILES_ANNOUNCEMENT() {
      return (ConfigBoolean)ENABLE_PROFILES_ANNOUNCEMENT$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getENABLE_INVENTORY_BUTTONS() {
      return (ConfigKeyToggleBoolean)ENABLE_INVENTORY_BUTTONS$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final ConfigBoolean getSHOW_BUTTONS_BOTH_SIDES() {
      return (ConfigBoolean)SHOW_BUTTONS_BOTH_SIDES$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_FAST_RENAME_CHECKBOX() {
      return (ConfigKeyToggleBoolean)SHOW_FAST_RENAME_CHECKBOX$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigEnum getFAST_RENAME_CHECKBOX_VALUE() {
      return (ConfigEnum)FAST_RENAME_CHECKBOX_VALUE$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigBoolean getFAST_RENAME_SAVED_VALUE() {
      return (ConfigBoolean)FAST_RENAME_SAVED_VALUE$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_CONTINUOUS_CRAFTING_CHECKBOX() {
      return (ConfigKeyToggleBoolean)SHOW_CONTINUOUS_CRAFTING_CHECKBOX$delegate.getValue(this, $$delegatedProperties[10]);
   }

   @NotNull
   public final ConfigEnum getCONTINUOUS_CRAFTING_CHECKBOX_VALUE() {
      return (ConfigEnum)CONTINUOUS_CRAFTING_CHECKBOX_VALUE$delegate.getValue(this, $$delegatedProperties[11]);
   }

   @NotNull
   public final ConfigBoolean getCONTINUOUS_CRAFTING_SAVED_VALUE() {
      return (ConfigBoolean)CONTINUOUS_CRAFTING_SAVED_VALUE$delegate.getValue(this, $$delegatedProperties[12]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_REGULAR_SORT_BUTTON() {
      return (ConfigKeyToggleBoolean)SHOW_REGULAR_SORT_BUTTON$delegate.getValue(this, $$delegatedProperties[13]);
   }

   @NotNull
   public final ConfigEnum getREGULAR_POST_ACTION() {
      return (ConfigEnum)REGULAR_POST_ACTION$delegate.getValue(this, $$delegatedProperties[14]);
   }

   @NotNull
   public final ConfigEnum getREGULAR_SORT_ORDER() {
      return (ConfigEnum)REGULAR_SORT_ORDER$delegate.getValue(this, $$delegatedProperties[15]);
   }

   @NotNull
   public final ConfigString getREGULAR_CUSTOM_RULE() {
      return (ConfigString)REGULAR_CUSTOM_RULE$delegate.getValue(this, $$delegatedProperties[16]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getCHANGE_SORT_ORDER_WITH_SCROLL_WHEEL() {
      return (ConfigKeyToggleBoolean)CHANGE_SORT_ORDER_WITH_SCROLL_WHEEL$delegate.getValue(this, $$delegatedProperties[17]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_SORT_IN_COLUMNS_BUTTON() {
      return (ConfigKeyToggleBoolean)SHOW_SORT_IN_COLUMNS_BUTTON$delegate.getValue(this, $$delegatedProperties[18]);
   }

   @NotNull
   public final ConfigEnum getIN_COLUMNS_POST_ACTION() {
      return (ConfigEnum)IN_COLUMNS_POST_ACTION$delegate.getValue(this, $$delegatedProperties[19]);
   }

   @NotNull
   public final ConfigEnum getIN_COLUMNS_SORT_ORDER() {
      return (ConfigEnum)IN_COLUMNS_SORT_ORDER$delegate.getValue(this, $$delegatedProperties[20]);
   }

   @NotNull
   public final ConfigString getIN_COLUMNS_CUSTOM_RULE() {
      return (ConfigString)IN_COLUMNS_CUSTOM_RULE$delegate.getValue(this, $$delegatedProperties[21]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_SORT_IN_ROWS_BUTTON() {
      return (ConfigKeyToggleBoolean)SHOW_SORT_IN_ROWS_BUTTON$delegate.getValue(this, $$delegatedProperties[22]);
   }

   @NotNull
   public final ConfigEnum getIN_ROWS_POST_ACTION() {
      return (ConfigEnum)IN_ROWS_POST_ACTION$delegate.getValue(this, $$delegatedProperties[23]);
   }

   @NotNull
   public final ConfigEnum getIN_ROWS_SORT_ORDER() {
      return (ConfigEnum)IN_ROWS_SORT_ORDER$delegate.getValue(this, $$delegatedProperties[24]);
   }

   @NotNull
   public final ConfigString getIN_ROWS_CUSTOM_RULE() {
      return (ConfigString)IN_ROWS_CUSTOM_RULE$delegate.getValue(this, $$delegatedProperties[25]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_MOVE_ALL_BUTTON() {
      return (ConfigKeyToggleBoolean)SHOW_MOVE_ALL_BUTTON$delegate.getValue(this, $$delegatedProperties[26]);
   }

   @NotNull
   public final ConfigBoolean getSHOW_BUTTON_TOOLTIPS() {
      return (ConfigBoolean)SHOW_BUTTON_TOOLTIPS$delegate.getValue(this, $$delegatedProperties[27]);
   }

   @NotNull
   public final ConfigInteger getSETTINGS_TOP() {
      return (ConfigInteger)SETTINGS_TOP$delegate.getValue(this, $$delegatedProperties[28]);
   }

   @NotNull
   public final ConfigInteger getSETTINGS_LEFT() {
      return (ConfigInteger)SETTINGS_LEFT$delegate.getValue(this, $$delegatedProperties[29]);
   }

   @NotNull
   public final ConfigInteger getEDITOR_TOP() {
      return (ConfigInteger)EDITOR_TOP$delegate.getValue(this, $$delegatedProperties[30]);
   }

   @NotNull
   public final ConfigInteger getEDITOR_LEFT() {
      return (ConfigInteger)EDITOR_LEFT$delegate.getValue(this, $$delegatedProperties[31]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[32])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "INVENTORY_OVERLAY_BUTTONS_VISIBLE",
               "getINVENTORY_OVERLAY_BUTTONS_VISIBLE()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "ENABLE_INVENTORY_EDITOR_BUTTON",
               "getENABLE_INVENTORY_EDITOR_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "ENABLE_INVENTORY_SETTINGS_BUTTON",
               "getENABLE_INVENTORY_SETTINGS_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "ENABLE_PROFILES_UI", "getENABLE_PROFILES_UI()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "ENABLE_PROFILES_ANNOUNCEMENT", "getENABLE_PROFILES_ANNOUNCEMENT()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "ENABLE_INVENTORY_BUTTONS", "getENABLE_INVENTORY_BUTTONS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_BUTTONS_BOTH_SIDES", "getSHOW_BUTTONS_BOTH_SIDES()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_FAST_RENAME_CHECKBOX", "getSHOW_FAST_RENAME_CHECKBOX()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "FAST_RENAME_CHECKBOX_VALUE", "getFAST_RENAME_CHECKBOX_VALUE()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "FAST_RENAME_SAVED_VALUE", "getFAST_RENAME_SAVED_VALUE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "SHOW_CONTINUOUS_CRAFTING_CHECKBOX",
               "getSHOW_CONTINUOUS_CRAFTING_CHECKBOX()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[11] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "CONTINUOUS_CRAFTING_CHECKBOX_VALUE",
               "getCONTINUOUS_CRAFTING_CHECKBOX_VALUE()Lorg/anti_ad/mc/common/config/options/ConfigEnum;",
               0
            )
         )
      );
      var0[12] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "CONTINUOUS_CRAFTING_SAVED_VALUE",
               "getCONTINUOUS_CRAFTING_SAVED_VALUE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[13] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_REGULAR_SORT_BUTTON", "getSHOW_REGULAR_SORT_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[14] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "REGULAR_POST_ACTION", "getREGULAR_POST_ACTION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[15] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "REGULAR_SORT_ORDER", "getREGULAR_SORT_ORDER()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[16] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "REGULAR_CUSTOM_RULE", "getREGULAR_CUSTOM_RULE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[17] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "CHANGE_SORT_ORDER_WITH_SCROLL_WHEEL",
               "getCHANGE_SORT_ORDER_WITH_SCROLL_WHEEL()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[18] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class,
               "SHOW_SORT_IN_COLUMNS_BUTTON",
               "getSHOW_SORT_IN_COLUMNS_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[19] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "IN_COLUMNS_POST_ACTION", "getIN_COLUMNS_POST_ACTION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[20] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "IN_COLUMNS_SORT_ORDER", "getIN_COLUMNS_SORT_ORDER()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[21] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "IN_COLUMNS_CUSTOM_RULE", "getIN_COLUMNS_CUSTOM_RULE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[22] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_SORT_IN_ROWS_BUTTON", "getSHOW_SORT_IN_ROWS_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[23] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "IN_ROWS_POST_ACTION", "getIN_ROWS_POST_ACTION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[24] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "IN_ROWS_SORT_ORDER", "getIN_ROWS_SORT_ORDER()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[25] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "IN_ROWS_CUSTOM_RULE", "getIN_ROWS_CUSTOM_RULE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[26] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_MOVE_ALL_BUTTON", "getSHOW_MOVE_ALL_BUTTON()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[27] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               GuiSettings.class, "SHOW_BUTTON_TOOLTIPS", "getSHOW_BUTTON_TOOLTIPS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[28] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "SETTINGS_TOP", "getSETTINGS_TOP()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0)
         )
      );
      var0[29] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "SETTINGS_LEFT", "getSETTINGS_LEFT()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0)
         )
      );
      var0[30] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "EDITOR_TOP", "getEDITOR_TOP()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0)
         )
      );
      var0[31] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(GuiSettings.class, "EDITOR_LEFT", "getEDITOR_LEFT()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0)
         )
      );
      $$delegatedProperties = var0;
      INVENTORY_OVERLAY_BUTTONS_VISIBLE$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      ENABLE_INVENTORY_EDITOR_BUTTON$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      ENABLE_INVENTORY_SETTINGS_BUTTON$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.keyToggleBool(INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()),
            "inventoryprofiles.config.category.profiles"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[2]);
      ENABLE_PROFILES_UI$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      ENABLE_PROFILES_ANNOUNCEMENT$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.inventory"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      ENABLE_INVENTORY_BUTTONS$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      SHOW_BUTTONS_BOTH_SIDES$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[6]);
      SHOW_FAST_RENAME_CHECKBOX$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[7]);
      FAST_RENAME_CHECKBOX_VALUE$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ContinuousCraftingCheckboxValue.UNCHECKED)
         .provideDelegate(INSTANCE, $$delegatedProperties[8]);
      FAST_RENAME_SAVED_VALUE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[9]);
      SHOW_CONTINUOUS_CRAFTING_CHECKBOX$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
      CONTINUOUS_CRAFTING_CHECKBOX_VALUE$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ContinuousCraftingCheckboxValue.REMEMBER)
         .provideDelegate(INSTANCE, $$delegatedProperties[11]);
      CONTINUOUS_CRAFTING_SAVED_VALUE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[12]);
      SHOW_REGULAR_SORT_BUTTON$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[13]);
      REGULAR_POST_ACTION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, PostAction.NONE).provideDelegate(INSTANCE, $$delegatedProperties[14]);
      REGULAR_SORT_ORDER$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, SortingMethodIndividual.GLOBAL)
         .provideDelegate(INSTANCE, $$delegatedProperties[15]);
      REGULAR_CUSTOM_RULE$delegate = ConfigDeclarationBuilderKt.string(INSTANCE, "@custom").provideDelegate(INSTANCE, $$delegatedProperties[16]);
      CHANGE_SORT_ORDER_WITH_SCROLL_WHEEL$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[17]);
      SHOW_SORT_IN_COLUMNS_BUTTON$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[18]);
      IN_COLUMNS_POST_ACTION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, PostAction.GROUP_IN_COLUMNS)
         .provideDelegate(INSTANCE, $$delegatedProperties[19]);
      IN_COLUMNS_SORT_ORDER$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, SortingMethodIndividual.GLOBAL)
         .provideDelegate(INSTANCE, $$delegatedProperties[20]);
      IN_COLUMNS_CUSTOM_RULE$delegate = ConfigDeclarationBuilderKt.string(INSTANCE, "@custom").provideDelegate(INSTANCE, $$delegatedProperties[21]);
      SHOW_SORT_IN_ROWS_BUTTON$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[22]);
      IN_ROWS_POST_ACTION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, PostAction.GROUP_IN_ROWS).provideDelegate(INSTANCE, $$delegatedProperties[23]);
      IN_ROWS_SORT_ORDER$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, SortingMethodIndividual.GLOBAL)
         .provideDelegate(INSTANCE, $$delegatedProperties[24]);
      IN_ROWS_CUSTOM_RULE$delegate = ConfigDeclarationBuilderKt.string(INSTANCE, "@custom").provideDelegate(INSTANCE, $$delegatedProperties[25]);
      SHOW_MOVE_ALL_BUTTON$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[26]);
      SHOW_BUTTON_TOOLTIPS$delegate = ConfigDeclarationBuilderKt.CATEGORY(ConfigDeclarationBuilderKt.bool(INSTANCE, true), "§§hide - default button possitions")
         .provideDelegate(INSTANCE, $$delegatedProperties[27]);
      SETTINGS_TOP$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 25, Integer.MIN_VALUE, Integer.MAX_VALUE)
         .provideDelegate(INSTANCE, $$delegatedProperties[28]);
      SETTINGS_LEFT$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 10, Integer.MIN_VALUE, Integer.MAX_VALUE)
         .provideDelegate(INSTANCE, $$delegatedProperties[29]);
      EDITOR_TOP$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 10, Integer.MIN_VALUE, Integer.MAX_VALUE)
         .provideDelegate(INSTANCE, $$delegatedProperties[30]);
      EDITOR_LEFT$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 10, Integer.MIN_VALUE, Integer.MAX_VALUE)
         .provideDelegate(INSTANCE, $$delegatedProperties[31]);
   }
}
