package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.anti_ad.mc.common.config.options.ConfigColorPicker;
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.config.options.ConfigInteger;
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean;
import org.anti_ad.mc.common.config.options.HandledConfigString;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.jetbrains.annotations.NotNull;

public final class LockedSlotsSettings implements ConfigDeclaration {
   @NotNull
   public static final LockedSlotsSettings INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new LockedSlotsSettings()), "§§vgap:3"
   );
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_DISABLE_USER_INTERACTION$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_SWITCH_CONFIG_MODIFIER$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_CONFIG_KEY$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_QUICK_CONFIG_KEY$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_QUICK_DISABLE$delegate;
   @NotNull
   private static final AsDelegate LOCK_SLOTS_CONFIG_SWITCH_TYPE$delegate;
   @NotNull
   private static final AsDelegate SHOW_LOCKED_SLOTS_BACKGROUND$delegate;
   @NotNull
   private static final AsDelegate SHOW_LOCKED_SLOTS_FOREGROUND$delegate;
   @NotNull
   private static final AsDelegate SHOW_LOCKED_SLOTS_BG_COLOR$delegate;
   @NotNull
   private static final AsDelegate SHOW_LOCKED_SLOTS_HOTBAR_COLOR$delegate;
   @NotNull
   private static final AsDelegate ALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR$delegate;
   @NotNull
   private static final AsDelegate LOCKED_SLOTS_FOREGROUND_STYLE$delegate;

   private LockedSlotsSettings() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigBoolean getLOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW() {
      return (ConfigBoolean)LOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigBoolean getLOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE() {
      return (ConfigBoolean)LOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getLOCK_SLOTS_DISABLE_USER_INTERACTION() {
      return (ConfigKeyToggleBoolean)LOCK_SLOTS_DISABLE_USER_INTERACTION$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY() {
      return (ConfigKeyToggleBoolean)LOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigInteger getLOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS() {
      return (ConfigInteger)LOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED() {
      return (ConfigKeyToggleBoolean)LOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final HandledConfigString getLOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST() {
      return (HandledConfigString)LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigHotkey getLOCK_SLOTS_SWITCH_CONFIG_MODIFIER() {
      return (ConfigHotkey)LOCK_SLOTS_SWITCH_CONFIG_MODIFIER$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigHotkey getLOCK_SLOTS_CONFIG_KEY() {
      return (ConfigHotkey)LOCK_SLOTS_CONFIG_KEY$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigHotkey getLOCK_SLOTS_QUICK_CONFIG_KEY() {
      return (ConfigHotkey)LOCK_SLOTS_QUICK_CONFIG_KEY$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigHotkey getLOCK_SLOTS_QUICK_DISABLE() {
      return (ConfigHotkey)LOCK_SLOTS_QUICK_DISABLE$delegate.getValue(this, $$delegatedProperties[10]);
   }

   @NotNull
   public final ConfigEnum getLOCK_SLOTS_CONFIG_SWITCH_TYPE() {
      return (ConfigEnum)LOCK_SLOTS_CONFIG_SWITCH_TYPE$delegate.getValue(this, $$delegatedProperties[11]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_LOCKED_SLOTS_BACKGROUND() {
      return (ConfigKeyToggleBoolean)SHOW_LOCKED_SLOTS_BACKGROUND$delegate.getValue(this, $$delegatedProperties[12]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSHOW_LOCKED_SLOTS_FOREGROUND() {
      return (ConfigKeyToggleBoolean)SHOW_LOCKED_SLOTS_FOREGROUND$delegate.getValue(this, $$delegatedProperties[13]);
   }

   @NotNull
   public final ConfigColorPicker getSHOW_LOCKED_SLOTS_BG_COLOR() {
      return (ConfigColorPicker)SHOW_LOCKED_SLOTS_BG_COLOR$delegate.getValue(this, $$delegatedProperties[14]);
   }

   @NotNull
   public final ConfigColorPicker getSHOW_LOCKED_SLOTS_HOTBAR_COLOR() {
      return (ConfigColorPicker)SHOW_LOCKED_SLOTS_HOTBAR_COLOR$delegate.getValue(this, $$delegatedProperties[15]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR() {
      return (ConfigKeyToggleBoolean)ALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR$delegate.getValue(this, $$delegatedProperties[16]);
   }

   @NotNull
   public final ConfigInteger getLOCKED_SLOTS_FOREGROUND_STYLE() {
      return (ConfigInteger)LOCKED_SLOTS_FOREGROUND_STYLE$delegate.getValue(this, $$delegatedProperties[17]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[18])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW",
               "getLOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE",
               "getLOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCK_SLOTS_DISABLE_USER_INTERACTION",
               "getLOCK_SLOTS_DISABLE_USER_INTERACTION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY",
               "getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS",
               "getLOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS()Lorg/anti_ad/mc/common/config/options/ConfigInteger;",
               0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED",
               "getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST",
               "getLOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST()Lorg/anti_ad/mc/common/config/options/HandledConfigString;",
               0
            )
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCK_SLOTS_SWITCH_CONFIG_MODIFIER",
               "getLOCK_SLOTS_SWITCH_CONFIG_MODIFIER()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;",
               0
            )
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class, "LOCK_SLOTS_CONFIG_KEY", "getLOCK_SLOTS_CONFIG_KEY()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCK_SLOTS_QUICK_CONFIG_KEY",
               "getLOCK_SLOTS_QUICK_CONFIG_KEY()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;",
               0
            )
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class, "LOCK_SLOTS_QUICK_DISABLE", "getLOCK_SLOTS_QUICK_DISABLE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[11] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCK_SLOTS_CONFIG_SWITCH_TYPE",
               "getLOCK_SLOTS_CONFIG_SWITCH_TYPE()Lorg/anti_ad/mc/common/config/options/ConfigEnum;",
               0
            )
         )
      );
      var0[12] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "SHOW_LOCKED_SLOTS_BACKGROUND",
               "getSHOW_LOCKED_SLOTS_BACKGROUND()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[13] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "SHOW_LOCKED_SLOTS_FOREGROUND",
               "getSHOW_LOCKED_SLOTS_FOREGROUND()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[14] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "SHOW_LOCKED_SLOTS_BG_COLOR",
               "getSHOW_LOCKED_SLOTS_BG_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[15] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "SHOW_LOCKED_SLOTS_HOTBAR_COLOR",
               "getSHOW_LOCKED_SLOTS_HOTBAR_COLOR()Lorg/anti_ad/mc/common/config/options/ConfigColorPicker;",
               0
            )
         )
      );
      var0[16] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "ALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR",
               "getALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[17] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               LockedSlotsSettings.class,
               "LOCKED_SLOTS_FOREGROUND_STYLE",
               "getLOCKED_SLOTS_FOREGROUND_STYLE()Lorg/anti_ad/mc/common/config/options/ConfigInteger;",
               0
            )
         )
      );
      $$delegatedProperties = var0;
      LOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[0]);
      LOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true)
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      LOCK_SLOTS_DISABLE_USER_INTERACTION$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.keyToggleBool(INSTANCE, ConfigScreenSettings.INSTANCE, false, KeybindSettings.Companion.getGUI_DEFAULT()),
            "inventoryprofiles.config.category.lock_slots.empty"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[2]);
      LOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      LOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 50, 20, 400)
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      LOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.handledString(
               INSTANCE, "carryon", new LockedSlotsSettings$LOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST$2(LockedSlotKeeper.INSTANCE)
            ),
            "inventoryprofiles.config.category.hotkeys"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[6]);
      LOCK_SLOTS_SWITCH_CONFIG_MODIFIER$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_ALT", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[7]);
      LOCK_SLOTS_CONFIG_KEY$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "BUTTON_1", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[8]);
      LOCK_SLOTS_QUICK_CONFIG_KEY$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[9]);
      LOCK_SLOTS_QUICK_DISABLE$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
      LOCK_SLOTS_CONFIG_SWITCH_TYPE$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.enum(INSTANCE, SwitchType.HOLD), "inventoryprofiles.config.category.lock_slots.gui"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[11]);
      SHOW_LOCKED_SLOTS_BACKGROUND$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[12]);
      SHOW_LOCKED_SLOTS_FOREGROUND$delegate = ConfigDeclarationBuilderKt.keyToggleBool(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, KeybindSettings.Companion.getGUI_DEFAULT()
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[13]);
      SHOW_LOCKED_SLOTS_BG_COLOR$delegate = ConfigDeclarationBuilderKt.color(
            INSTANCE, ColorKt.blue(ColorKt.green(ColorKt.red(ColorKt.asAlpha(192), 255), 85), 85)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[14]);
      SHOW_LOCKED_SLOTS_HOTBAR_COLOR$delegate = ConfigDeclarationBuilderKt.color(
            INSTANCE, ColorKt.blue(ColorKt.green(ColorKt.red(ColorKt.asAlpha(94), 255), 94), 65)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[15]);
      ALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[16]);
      LOCKED_SLOTS_FOREGROUND_STYLE$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 2, 1, 6).provideDelegate(INSTANCE, $$delegatedProperties[17]);
   }
}
