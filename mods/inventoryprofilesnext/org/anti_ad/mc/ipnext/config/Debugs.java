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
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.jetbrains.annotations.NotNull;

public final class Debugs implements ConfigDeclaration {
   @NotNull
   public static final Debugs INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new Debugs()), "inventoryprofiles.config.category.debugs"
   );
   @NotNull
   private static final AsDelegate TRACE_LOGS$delegate;
   @NotNull
   private static final AsDelegate DEBUG_RENDER$delegate;
   @NotNull
   private static final AsDelegate FORCE_NO_CLEAN_CURSOR$delegate;
   @NotNull
   private static final AsDelegate FORCE_SERVER_METHOD_FOR_LOCKED_SLOTS$delegate;
   @NotNull
   private static final AsDelegate DIFF_CALCULATOR$delegate;
   @NotNull
   private static final AsDelegate DEBUG_SCREEN$delegate;
   @NotNull
   private static final AsDelegate SCREEN_DEPTH_TEST$delegate;
   @NotNull
   private static final AsDelegate SCREEN_SPRITE_TEST$delegate;
   @NotNull
   private static final AsDelegate CLEAN_CURSOR$delegate;
   @NotNull
   private static final AsDelegate GEN_TAG_VANILLA_TXT$delegate;
   @NotNull
   private static final AsDelegate GEN_RULE_LIST$delegate;

   private Debugs() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigBoolean getTRACE_LOGS() {
      return (ConfigBoolean)TRACE_LOGS$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigBoolean getDEBUG_RENDER() {
      return (ConfigBoolean)DEBUG_RENDER$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigBoolean getFORCE_NO_CLEAN_CURSOR() {
      return (ConfigBoolean)FORCE_NO_CLEAN_CURSOR$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigBoolean getFORCE_SERVER_METHOD_FOR_LOCKED_SLOTS() {
      return (ConfigBoolean)FORCE_SERVER_METHOD_FOR_LOCKED_SLOTS$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigEnum getDIFF_CALCULATOR() {
      return (ConfigEnum)DIFF_CALCULATOR$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigHotkey getDEBUG_SCREEN() {
      return (ConfigHotkey)DEBUG_SCREEN$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final ConfigHotkey getSCREEN_DEPTH_TEST() {
      return (ConfigHotkey)SCREEN_DEPTH_TEST$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigHotkey getSCREEN_SPRITE_TEST() {
      return (ConfigHotkey)SCREEN_SPRITE_TEST$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigHotkey getCLEAN_CURSOR() {
      return (ConfigHotkey)CLEAN_CURSOR$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigButton getGEN_TAG_VANILLA_TXT() {
      return (ConfigButton)GEN_TAG_VANILLA_TXT$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigButton getGEN_RULE_LIST() {
      return (ConfigButton)GEN_RULE_LIST$delegate.getValue(this, $$delegatedProperties[10]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[11])[0] = Reflection.property1(
         (PropertyReference1)(new PropertyReference1Impl(Debugs.class, "TRACE_LOGS", "getTRACE_LOGS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0))
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "DEBUG_RENDER", "getDEBUG_RENDER()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0)
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Debugs.class, "FORCE_NO_CLEAN_CURSOR", "getFORCE_NO_CLEAN_CURSOR()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Debugs.class,
               "FORCE_SERVER_METHOD_FOR_LOCKED_SLOTS",
               "getFORCE_SERVER_METHOD_FOR_LOCKED_SLOTS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "DIFF_CALCULATOR", "getDIFF_CALCULATOR()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "DEBUG_SCREEN", "getDEBUG_SCREEN()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "SCREEN_DEPTH_TEST", "getSCREEN_DEPTH_TEST()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "SCREEN_SPRITE_TEST", "getSCREEN_SPRITE_TEST()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "CLEAN_CURSOR", "getCLEAN_CURSOR()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;", 0)
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "GEN_TAG_VANILLA_TXT", "getGEN_TAG_VANILLA_TXT()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0)
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(Debugs.class, "GEN_RULE_LIST", "getGEN_RULE_LIST()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0)
         )
      );
      $$delegatedProperties = var0;
      TRACE_LOGS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[0]);
      DEBUG_RENDER$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[1]);
      FORCE_NO_CLEAN_CURSOR$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[2]);
      FORCE_SERVER_METHOD_FOR_LOCKED_SLOTS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[3]);
      DIFF_CALCULATOR$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, DiffCalculatorType.SIMPLE).provideDelegate(INSTANCE, $$delegatedProperties[4]);
      DEBUG_SCREEN$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "Z,1", KeybindSettings.Companion.getANY_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      SCREEN_DEPTH_TEST$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "Z,2", KeybindSettings.Companion.getANY_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[6]);
      SCREEN_SPRITE_TEST$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "Z,3", KeybindSettings.Companion.getANY_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[7]);
      CLEAN_CURSOR$delegate = ConfigDeclarationBuilderKt.hotkey(INSTANCE, "X,1", KeybindSettings.Companion.getGUI_DEFAULT())
         .provideDelegate(INSTANCE, $$delegatedProperties[8]);
      GEN_TAG_VANILLA_TXT$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, GenerateTagVanillaTxtButtonInfo.INSTANCE)
         .provideDelegate(INSTANCE, $$delegatedProperties[9]);
      GEN_RULE_LIST$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, GenerateRuleListButtonInfo.INSTANCE)
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
   }
}
