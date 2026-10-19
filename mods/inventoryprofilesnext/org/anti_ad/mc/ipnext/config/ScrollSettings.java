package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigBoolean;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.jetbrains.annotations.NotNull;

public final class ScrollSettings implements ConfigDeclaration {
   @NotNull
   public static final ScrollSettings INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new ScrollSettings()), "inventoryprofiles.config.category.scrolling.general"
   );
   @NotNull
   private static final AsDelegate DIRECTION_ABSOLUTE$delegate;
   @NotNull
   private static final AsDelegate DISABLE_FOR_NON_EMPTY_BUNDLES$delegate;
   @NotNull
   private static final AsDelegate SCROLL_FULL_STACK$delegate;
   @NotNull
   private static final AsDelegate SCROLL_LEAVE_LAST$delegate;
   @NotNull
   private static final AsDelegate SCROLL_SPREAD$delegate;
   @NotNull
   private static final AsDelegate SCROLL_THROW$delegate;
   @NotNull
   private static final AsDelegate TEMP_DISABLE$delegate;
   @NotNull
   private static final AsDelegate SCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE$delegate;

   private ScrollSettings() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigBoolean getDIRECTION_ABSOLUTE() {
      return (ConfigBoolean)DIRECTION_ABSOLUTE$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigBoolean getDISABLE_FOR_NON_EMPTY_BUNDLES() {
      return (ConfigBoolean)DISABLE_FOR_NON_EMPTY_BUNDLES$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_FULL_STACK() {
      return (ConfigHotkey)SCROLL_FULL_STACK$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_LEAVE_LAST() {
      return (ConfigHotkey)SCROLL_LEAVE_LAST$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_SPREAD() {
      return (ConfigHotkey)SCROLL_SPREAD$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigHotkey getSCROLL_THROW() {
      return (ConfigHotkey)SCROLL_THROW$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final ConfigHotkey getTEMP_DISABLE() {
      return (ConfigHotkey)TEMP_DISABLE$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigBoolean getSCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE() {
      return (ConfigBoolean)SCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE$delegate.getValue(this, $$delegatedProperties[7]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[8])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ScrollSettings.class, "DIRECTION_ABSOLUTE", "getDIRECTION_ABSOLUTE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ScrollSettings.class,
               "DISABLE_FOR_NON_EMPTY_BUNDLES",
               "getDISABLE_FOR_NON_EMPTY_BUNDLES()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ScrollSettings.class, "SCROLL_FULL_STACK", "getSCROLL_FULL_STACK()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ScrollSettings.class, "SCROLL_LEAVE_LAST", "getSCROLL_LEAVE_LAST()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ScrollSettings.class, "SCROLL_SPREAD", "getSCROLL_SPREAD()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ScrollSettings.class, "SCROLL_THROW", "getSCROLL_THROW()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(ScrollSettings.class, "TEMP_DISABLE", "getTEMP_DISABLE()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               ScrollSettings.class,
               "SCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE",
               "getSCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      $$delegatedProperties = var0;
      DIRECTION_ABSOLUTE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[0]);
      DISABLE_FOR_NON_EMPTY_BUNDLES$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, true), "inventoryprofiles.config.category.scroll.modifiers"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      SCROLL_FULL_STACK$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_SHIFT", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[2]);
      SCROLL_LEAVE_LAST$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "Z", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      SCROLL_SPREAD$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "X", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      SCROLL_THROW$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "C", KeybindSettings.Companion.getGUI_EXTRA())
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      TEMP_DISABLE$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.hotkey(INSTANCE, "LEFT_CONTROL", KeybindSettings.Companion.getGUI_EXTRA()),
            "inventoryprofiles.config.category.scroll.single"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[6]);
      SCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[7]);
   }
}
