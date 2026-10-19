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
import org.anti_ad.mc.common.config.options.ConfigString;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.jetbrains.annotations.NotNull;

public final class EditProfiles implements ConfigDeclaration {
   @NotNull
   public static final EditProfiles INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new EditProfiles()), "inventoryprofiles.config.category.still_under_construction"
   );
   @NotNull
   private static final AsDelegate OPEN_CONFIG_PROFILES_HELP$delegate;
   @NotNull
   private static final AsDelegate OPEN_SERVER_PROFILES$delegate;
   @NotNull
   private static final AsDelegate QUICK_SLOT_1_PROFILE$delegate;
   @NotNull
   private static final AsDelegate QUICK_SLOT_2_PROFILE$delegate;
   @NotNull
   private static final AsDelegate QUICK_SLOT_3_PROFILE$delegate;
   @NotNull
   private static final AsDelegate INCLUDE_CUSTOM_NAME$delegate;

   private EditProfiles() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigButton getOPEN_CONFIG_PROFILES_HELP() {
      return (ConfigButton)OPEN_CONFIG_PROFILES_HELP$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigButton getOPEN_SERVER_PROFILES() {
      return (ConfigButton)OPEN_SERVER_PROFILES$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigString getQUICK_SLOT_1_PROFILE() {
      return (ConfigString)QUICK_SLOT_1_PROFILE$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigString getQUICK_SLOT_2_PROFILE() {
      return (ConfigString)QUICK_SLOT_2_PROFILE$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigString getQUICK_SLOT_3_PROFILE() {
      return (ConfigString)QUICK_SLOT_3_PROFILE$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final ConfigBoolean getINCLUDE_CUSTOM_NAME() {
      return (ConfigBoolean)INCLUDE_CUSTOM_NAME$delegate.getValue(this, $$delegatedProperties[5]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[6])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "OPEN_CONFIG_PROFILES_HELP", "getOPEN_CONFIG_PROFILES_HELP()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "OPEN_SERVER_PROFILES", "getOPEN_SERVER_PROFILES()Lorg/anti_ad/mc/common/config/options/ConfigButton;", 0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "QUICK_SLOT_1_PROFILE", "getQUICK_SLOT_1_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "QUICK_SLOT_2_PROFILE", "getQUICK_SLOT_2_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "QUICK_SLOT_3_PROFILE", "getQUICK_SLOT_3_PROFILE()Lorg/anti_ad/mc/common/config/options/ConfigString;", 0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               EditProfiles.class, "INCLUDE_CUSTOM_NAME", "getINCLUDE_CUSTOM_NAME()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      $$delegatedProperties = var0;
      OPEN_CONFIG_PROFILES_HELP$delegate = ConfigDeclarationBuilderKt.button(INSTANCE, OpenProfilesHelpButtonInfo.INSTANCE)
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      OPEN_SERVER_PROFILES$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.button(INSTANCE, OpenProfilesConfigButtonInfo.INSTANCE), "inventoryprofiles.config.category.quick_slots"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      QUICK_SLOT_1_PROFILE$delegate = ConfigDeclarationBuilderKt.string(INSTANCE, "").provideDelegate(INSTANCE, $$delegatedProperties[2]);
      QUICK_SLOT_2_PROFILE$delegate = ConfigDeclarationBuilderKt.string(INSTANCE, "").provideDelegate(INSTANCE, $$delegatedProperties[3]);
      QUICK_SLOT_3_PROFILE$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.string(INSTANCE, ""), "inventoryprofiles.config.category.still_under_construction"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      INCLUDE_CUSTOM_NAME$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[5]);
   }
}
