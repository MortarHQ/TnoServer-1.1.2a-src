package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigButton;
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.ipnext.debug.GenerateTagsAsJson;
import org.anti_ad.mc.ipnext.integration.MergePriority;
import org.jetbrains.annotations.NotNull;

public final class Modpacks implements ConfigDeclaration {
   @NotNull
   public static final Modpacks INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new Modpacks()), "inventoryprofiles.config.category.modpacks"
   );
   @NotNull
   private static final AsDelegate MAKE_BLOCK_SCREEN_GENERATOR_SCRIPT$delegate;
   @NotNull
   private static final AsDelegate DIFF_CALCULATOR_PRIORITY$delegate;
   @NotNull
   private static final AsDelegate EXPORT_HINTS$delegate;
   @NotNull
   private static final AsDelegate EXPORT_EXTERNAL_HINTS$delegate;
   @NotNull
   private static final AsDelegate GEN_TEST_ARENA$delegate;

   private Modpacks() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigButton getMAKE_BLOCK_SCREEN_GENERATOR_SCRIPT() {
      return (ConfigButton)MAKE_BLOCK_SCREEN_GENERATOR_SCRIPT$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigEnum getDIFF_CALCULATOR_PRIORITY() {
      return (ConfigEnum)DIFF_CALCULATOR_PRIORITY$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigButton getEXPORT_HINTS() {
      return (ConfigButton)EXPORT_HINTS$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigButton getEXPORT_EXTERNAL_HINTS() {
      return (ConfigButton)EXPORT_EXTERNAL_HINTS$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigHotkey getGEN_TEST_ARENA() {
      return (ConfigHotkey)GEN_TEST_ARENA$delegate.getValue(this, $$delegatedProperties[4]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[5])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Modpacks.class,
               "MAKE_BLOCK_SCREEN_GENERATOR_SCRIPT",
               "getMAKE_BLOCK_SCREEN_GENERATOR_SCRIPT()Lorg/anti_ad/mc/common/config/options/ConfigButton;",
               0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Modpacks.class, "DIFF_CALCULATOR_PRIORITY", "getDIFF_CALCULATOR_PRIORITY()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Modpacks.class, "EXPORT_HINTS", "getEXPORT_HINTS()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0)
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Modpacks.class, "EXPORT_EXTERNAL_HINTS", "getEXPORT_EXTERNAL_HINTS()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Modpacks.class, "GEN_TEST_ARENA", "getGEN_TEST_ARENA()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      $$delegatedProperties = var0;
      MAKE_BLOCK_SCREEN_GENERATOR_SCRIPT$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, GenerateTagsAsJson.INSTANCE)
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      DIFF_CALCULATOR_PRIORITY$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, MergePriority.EXTERNAL).provideDelegate(INSTANCE, $$delegatedProperties[1]);
      EXPORT_HINTS$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, new ExportHints(true)).provideDelegate(INSTANCE, $$delegatedProperties[2]);
      EXPORT_EXTERNAL_HINTS$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, new ExportHints(false)).provideDelegate(INSTANCE, $$delegatedProperties[3]);
      GEN_TEST_ARENA$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "", KeybindSettings.Companion.getINGAME_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
   }
}
