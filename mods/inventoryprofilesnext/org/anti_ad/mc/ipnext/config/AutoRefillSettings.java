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
import org.anti_ad.mc.common.config.options.ConfigHotkey;
import org.anti_ad.mc.common.config.options.ConfigInteger;
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean;
import org.anti_ad.mc.common.config.options.HandledConfigString;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.jetbrains.annotations.NotNull;

public final class AutoRefillSettings implements ConfigDeclaration {
   @NotNull
   public static final AutoRefillSettings INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.CATEGORY(ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new AutoRefillSettings()), "§§vgap:3"),
      "inventoryprofiles.config.category.auto-refill.general"
   );
   @NotNull
   private static final AsDelegate DISABLE_FOR_DROP_ITEM$delegate;
   @NotNull
   private static final AsDelegate IGNORE_LOCKED_SLOTS$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_PREFER_SMALLER_STACKS$delegate;
   @NotNull
   private static final AsDelegate REFILL_ARMOR$delegate;
   @NotNull
   private static final AsDelegate REFILL_BEFORE_TOOL_BREAK$delegate;
   @NotNull
   private static final AsDelegate AUTOREFILL_BLACKLIST$delegate;
   @NotNull
   private static final AsDelegate STACKABLE_THRESHOLD$delegate;
   @NotNull
   private static final AsDelegate TOOL_DAMAGE_THRESHOLD$delegate;
   @NotNull
   private static final AsDelegate THRESHOLD_UNIT$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_WAIT_TICK$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_ENABLE_PER_SLOT_CONFIG$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_ENABLE_INDICATOR_ICONS$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS$delegate;
   @NotNull
   private static final AsDelegate DISABLE_FOR_LOYALTY_ITEMS$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_MATCH_CUSTOM_NAME$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_MATCH_NBT$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_MATCH_NBT_TYPE$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_IGNORE_NBT_FOR_BUCKETS$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_MATCH_ANY_FOOD$delegate;
   @NotNull
   private static final AsDelegate AUTO_REFILL_MATCH_HARMFUL_FOOD$delegate;
   @NotNull
   private static final AsDelegate ALLOW_BREAK_FOR_NON_ENCHANTED$delegate;
   @NotNull
   private static final AsDelegate TOOL_MAX_DURABILITY_THRESHOLD$delegate;
   @NotNull
   private static final AsDelegate ALLOW_ALERTS_WITHOUT_TOOL_PROTECTION$delegate;
   @NotNull
   private static final AsDelegate VISUAL_DURABILITY_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate TYPE_VISUAL_DURABILITY_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate AUDIO_DURABILITY_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate NUMBER_OF_NOTIFICATIONS$delegate;
   @NotNull
   private static final AsDelegate NOTIFICATION_STEP$delegate;
   @NotNull
   private static final AsDelegate VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate AUDIO_REPLACE_SUCCESS_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate TYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate VISUAL_REPLACE_FAILED_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate AUDIO_REPLACE_FAILED_NOTIFICATION$delegate;
   @NotNull
   private static final AsDelegate TYPE_VISUAL_REPLACE_FAILED_NOTIFICATION$delegate;

   private AutoRefillSettings() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigKeyToggleBoolean getDISABLE_FOR_DROP_ITEM() {
      return (ConfigKeyToggleBoolean)DISABLE_FOR_DROP_ITEM$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigBoolean getIGNORE_LOCKED_SLOTS() {
      return (ConfigBoolean)IGNORE_LOCKED_SLOTS$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_PREFER_SMALLER_STACKS() {
      return (ConfigBoolean)AUTO_REFILL_PREFER_SMALLER_STACKS$delegate.getValue(this, $$delegatedProperties[2]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getREFILL_ARMOR() {
      return (ConfigKeyToggleBoolean)REFILL_ARMOR$delegate.getValue(this, $$delegatedProperties[3]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getREFILL_BEFORE_TOOL_BREAK() {
      return (ConfigKeyToggleBoolean)REFILL_BEFORE_TOOL_BREAK$delegate.getValue(this, $$delegatedProperties[4]);
   }

   @NotNull
   public final HandledConfigString getAUTOREFILL_BLACKLIST() {
      return (HandledConfigString)AUTOREFILL_BLACKLIST$delegate.getValue(this, $$delegatedProperties[5]);
   }

   @NotNull
   public final ConfigInteger getSTACKABLE_THRESHOLD() {
      return (ConfigInteger)STACKABLE_THRESHOLD$delegate.getValue(this, $$delegatedProperties[6]);
   }

   @NotNull
   public final ConfigInteger getTOOL_DAMAGE_THRESHOLD() {
      return (ConfigInteger)TOOL_DAMAGE_THRESHOLD$delegate.getValue(this, $$delegatedProperties[7]);
   }

   @NotNull
   public final ConfigEnum getTHRESHOLD_UNIT() {
      return (ConfigEnum)THRESHOLD_UNIT$delegate.getValue(this, $$delegatedProperties[8]);
   }

   @NotNull
   public final ConfigInteger getAUTO_REFILL_WAIT_TICK() {
      return (ConfigInteger)AUTO_REFILL_WAIT_TICK$delegate.getValue(this, $$delegatedProperties[9]);
   }

   @NotNull
   public final ConfigHotkey getAUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS() {
      return (ConfigHotkey)AUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS$delegate.getValue(this, $$delegatedProperties[10]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUTO_REFILL_ENABLE_PER_SLOT_CONFIG() {
      return (ConfigKeyToggleBoolean)AUTO_REFILL_ENABLE_PER_SLOT_CONFIG$delegate.getValue(this, $$delegatedProperties[11]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUTO_REFILL_ENABLE_INDICATOR_ICONS() {
      return (ConfigKeyToggleBoolean)AUTO_REFILL_ENABLE_INDICATOR_ICONS$delegate.getValue(this, $$delegatedProperties[12]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS() {
      return (ConfigKeyToggleBoolean)AUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS$delegate.getValue(this, $$delegatedProperties[13]);
   }

   @NotNull
   public final ConfigBoolean getDISABLE_FOR_LOYALTY_ITEMS() {
      return (ConfigBoolean)DISABLE_FOR_LOYALTY_ITEMS$delegate.getValue(this, $$delegatedProperties[14]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_MATCH_CUSTOM_NAME() {
      return (ConfigBoolean)AUTO_REFILL_MATCH_CUSTOM_NAME$delegate.getValue(this, $$delegatedProperties[15]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_MATCH_NBT() {
      return (ConfigBoolean)AUTO_REFILL_MATCH_NBT$delegate.getValue(this, $$delegatedProperties[16]);
   }

   @NotNull
   public final ConfigEnum getAUTO_REFILL_MATCH_NBT_TYPE() {
      return (ConfigEnum)AUTO_REFILL_MATCH_NBT_TYPE$delegate.getValue(this, $$delegatedProperties[17]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_IGNORE_NBT_FOR_BUCKETS() {
      return (ConfigBoolean)AUTO_REFILL_IGNORE_NBT_FOR_BUCKETS$delegate.getValue(this, $$delegatedProperties[18]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_MATCH_ANY_FOOD() {
      return (ConfigBoolean)AUTO_REFILL_MATCH_ANY_FOOD$delegate.getValue(this, $$delegatedProperties[19]);
   }

   @NotNull
   public final ConfigBoolean getAUTO_REFILL_MATCH_HARMFUL_FOOD() {
      return (ConfigBoolean)AUTO_REFILL_MATCH_HARMFUL_FOOD$delegate.getValue(this, $$delegatedProperties[20]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getALLOW_BREAK_FOR_NON_ENCHANTED() {
      return (ConfigKeyToggleBoolean)ALLOW_BREAK_FOR_NON_ENCHANTED$delegate.getValue(this, $$delegatedProperties[21]);
   }

   @NotNull
   public final ConfigInteger getTOOL_MAX_DURABILITY_THRESHOLD() {
      return (ConfigInteger)TOOL_MAX_DURABILITY_THRESHOLD$delegate.getValue(this, $$delegatedProperties[22]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getALLOW_ALERTS_WITHOUT_TOOL_PROTECTION() {
      return (ConfigKeyToggleBoolean)ALLOW_ALERTS_WITHOUT_TOOL_PROTECTION$delegate.getValue(this, $$delegatedProperties[23]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVISUAL_DURABILITY_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)VISUAL_DURABILITY_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[24]);
   }

   @NotNull
   public final ConfigEnum getTYPE_VISUAL_DURABILITY_NOTIFICATION() {
      return (ConfigEnum)TYPE_VISUAL_DURABILITY_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[25]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUDIO_DURABILITY_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)AUDIO_DURABILITY_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[26]);
   }

   @NotNull
   public final ConfigInteger getNUMBER_OF_NOTIFICATIONS() {
      return (ConfigInteger)NUMBER_OF_NOTIFICATIONS$delegate.getValue(this, $$delegatedProperties[27]);
   }

   @NotNull
   public final ConfigInteger getNOTIFICATION_STEP() {
      return (ConfigInteger)NOTIFICATION_STEP$delegate.getValue(this, $$delegatedProperties[28]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVISUAL_REPLACE_SUCCESS_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[29]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUDIO_REPLACE_SUCCESS_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)AUDIO_REPLACE_SUCCESS_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[30]);
   }

   @NotNull
   public final ConfigEnum getTYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION() {
      return (ConfigEnum)TYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[31]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getVISUAL_REPLACE_FAILED_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)VISUAL_REPLACE_FAILED_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[32]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getAUDIO_REPLACE_FAILED_NOTIFICATION() {
      return (ConfigKeyToggleBoolean)AUDIO_REPLACE_FAILED_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[33]);
   }

   @NotNull
   public final ConfigEnum getTYPE_VISUAL_REPLACE_FAILED_NOTIFICATION() {
      return (ConfigEnum)TYPE_VISUAL_REPLACE_FAILED_NOTIFICATION$delegate.getValue(this, $$delegatedProperties[34]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[35])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "DISABLE_FOR_DROP_ITEM", "getDISABLE_FOR_DROP_ITEM()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "IGNORE_LOCKED_SLOTS", "getIGNORE_LOCKED_SLOTS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_PREFER_SMALLER_STACKS",
               "getAUTO_REFILL_PREFER_SMALLER_STACKS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[3] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "REFILL_ARMOR", "getREFILL_ARMOR()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[4] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "REFILL_BEFORE_TOOL_BREAK",
               "getREFILL_BEFORE_TOOL_BREAK()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[5] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "AUTOREFILL_BLACKLIST", "getAUTOREFILL_BLACKLIST()Lorg/anti_ad/mc/common/config/options/HandledConfigString;", 0
            )
         )
      );
      var0[6] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "STACKABLE_THRESHOLD", "getSTACKABLE_THRESHOLD()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[7] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "TOOL_DAMAGE_THRESHOLD", "getTOOL_DAMAGE_THRESHOLD()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[8] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(AutoRefillSettings.class, "THRESHOLD_UNIT", "getTHRESHOLD_UNIT()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0)
         )
      );
      var0[9] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "AUTO_REFILL_WAIT_TICK", "getAUTO_REFILL_WAIT_TICK()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[10] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS",
               "getAUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS()Lorg/anti_ad/mc/common/config/options/ConfigHotkey;",
               0
            )
         )
      );
      var0[11] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_ENABLE_PER_SLOT_CONFIG",
               "getAUTO_REFILL_ENABLE_PER_SLOT_CONFIG()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[12] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_ENABLE_INDICATOR_ICONS",
               "getAUTO_REFILL_ENABLE_INDICATOR_ICONS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[13] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS",
               "getAUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[14] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "DISABLE_FOR_LOYALTY_ITEMS", "getDISABLE_FOR_LOYALTY_ITEMS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[15] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_MATCH_CUSTOM_NAME",
               "getAUTO_REFILL_MATCH_CUSTOM_NAME()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[16] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "AUTO_REFILL_MATCH_NBT", "getAUTO_REFILL_MATCH_NBT()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[17] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "AUTO_REFILL_MATCH_NBT_TYPE", "getAUTO_REFILL_MATCH_NBT_TYPE()Lorg/anti_ad/mc/common/config/options/ConfigEnum;", 0
            )
         )
      );
      var0[18] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_IGNORE_NBT_FOR_BUCKETS",
               "getAUTO_REFILL_IGNORE_NBT_FOR_BUCKETS()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[19] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "AUTO_REFILL_MATCH_ANY_FOOD", "getAUTO_REFILL_MATCH_ANY_FOOD()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;", 0
            )
         )
      );
      var0[20] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUTO_REFILL_MATCH_HARMFUL_FOOD",
               "getAUTO_REFILL_MATCH_HARMFUL_FOOD()Lorg/anti_ad/mc/common/config/options/ConfigBoolean;",
               0
            )
         )
      );
      var0[21] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "ALLOW_BREAK_FOR_NON_ENCHANTED",
               "getALLOW_BREAK_FOR_NON_ENCHANTED()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[22] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "TOOL_MAX_DURABILITY_THRESHOLD",
               "getTOOL_MAX_DURABILITY_THRESHOLD()Lorg/anti_ad/mc/common/config/options/ConfigInteger;",
               0
            )
         )
      );
      var0[23] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "ALLOW_ALERTS_WITHOUT_TOOL_PROTECTION",
               "getALLOW_ALERTS_WITHOUT_TOOL_PROTECTION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[24] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "VISUAL_DURABILITY_NOTIFICATION",
               "getVISUAL_DURABILITY_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[25] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "TYPE_VISUAL_DURABILITY_NOTIFICATION",
               "getTYPE_VISUAL_DURABILITY_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;",
               0
            )
         )
      );
      var0[26] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUDIO_DURABILITY_NOTIFICATION",
               "getAUDIO_DURABILITY_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[27] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "NUMBER_OF_NOTIFICATIONS", "getNUMBER_OF_NOTIFICATIONS()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[28] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class, "NOTIFICATION_STEP", "getNOTIFICATION_STEP()Lorg/anti_ad/mc/common/config/options/ConfigInteger;", 0
            )
         )
      );
      var0[29] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "VISUAL_REPLACE_SUCCESS_NOTIFICATION",
               "getVISUAL_REPLACE_SUCCESS_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[30] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUDIO_REPLACE_SUCCESS_NOTIFICATION",
               "getAUDIO_REPLACE_SUCCESS_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[31] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "TYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION",
               "getTYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;",
               0
            )
         )
      );
      var0[32] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "VISUAL_REPLACE_FAILED_NOTIFICATION",
               "getVISUAL_REPLACE_FAILED_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[33] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "AUDIO_REPLACE_FAILED_NOTIFICATION",
               "getAUDIO_REPLACE_FAILED_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[34] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               AutoRefillSettings.class,
               "TYPE_VISUAL_REPLACE_FAILED_NOTIFICATION",
               "getTYPE_VISUAL_REPLACE_FAILED_NOTIFICATION()Lorg/anti_ad/mc/common/config/options/ConfigEnum;",
               0
            )
         )
      );
      $$delegatedProperties = var0;
      DISABLE_FOR_DROP_ITEM$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      IGNORE_LOCKED_SLOTS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[1]);
      AUTO_REFILL_PREFER_SMALLER_STACKS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[2]);
      REFILL_ARMOR$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[3]);
      REFILL_BEFORE_TOOL_BREAK$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[4]);
      AUTOREFILL_BLACKLIST$delegate = ConfigDeclarationBuilderKt.handledString(
            INSTANCE, "", new AutoRefillSettings$AUTOREFILL_BLACKLIST$2(AutoRefillHandler.INSTANCE)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[5]);
      STACKABLE_THRESHOLD$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 0, 0, 64).provideDelegate(INSTANCE, $$delegatedProperties[6]);
      TOOL_DAMAGE_THRESHOLD$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 10, 0, 100).provideDelegate(INSTANCE, $$delegatedProperties[7]);
      THRESHOLD_UNIT$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ThresholdUnit.ABSOLUTE).provideDelegate(INSTANCE, $$delegatedProperties[8]);
      AUTO_REFILL_WAIT_TICK$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 0, 0, 100).provideDelegate(INSTANCE, $$delegatedProperties[9]);
      AUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS$delegate = ConfigDeclarationBuilderKt.hotkey(
            INSTANCE, "LEFT_ALT", KeybindSettings.copy$default(KeybindSettings.Companion.getINGAME_DEFAULT(), null, null, true, false, null, 27, null)
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[10]);
      AUTO_REFILL_ENABLE_PER_SLOT_CONFIG$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[11]);
      AUTO_REFILL_ENABLE_INDICATOR_ICONS$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[12]);
      AUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null),
            "inventoryprofiles.config.category.auto-refill.matching"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[13]);
      DISABLE_FOR_LOYALTY_ITEMS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[14]);
      AUTO_REFILL_MATCH_CUSTOM_NAME$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[15]);
      AUTO_REFILL_MATCH_NBT$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[16]);
      AUTO_REFILL_MATCH_NBT_TYPE$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, AutoRefillNbtMatchType.EXACT)
         .provideDelegate(INSTANCE, $$delegatedProperties[17]);
      AUTO_REFILL_IGNORE_NBT_FOR_BUCKETS$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, true).provideDelegate(INSTANCE, $$delegatedProperties[18]);
      AUTO_REFILL_MATCH_ANY_FOOD$delegate = ConfigDeclarationBuilderKt.bool(INSTANCE, false).provideDelegate(INSTANCE, $$delegatedProperties[19]);
      AUTO_REFILL_MATCH_HARMFUL_FOOD$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.bool(INSTANCE, false), "inventoryprofiles.config.category.auto-refill.non-enchanted"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[20]);
      ALLOW_BREAK_FOR_NON_ENCHANTED$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[21]);
      TOOL_MAX_DURABILITY_THRESHOLD$delegate = ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.int(INSTANCE, 500, 0, 5000), "inventoryprofiles.config.category.auto-refill.notifications"
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[22]);
      ALLOW_ALERTS_WITHOUT_TOOL_PROTECTION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[23]);
      VISUAL_DURABILITY_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[24]);
      TYPE_VISUAL_DURABILITY_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ToolReplaceVisualNotification.HOTBAR)
         .provideDelegate(INSTANCE, $$delegatedProperties[25]);
      AUDIO_DURABILITY_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[26]);
      NUMBER_OF_NOTIFICATIONS$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 3, 1, 10).provideDelegate(INSTANCE, $$delegatedProperties[27]);
      NOTIFICATION_STEP$delegate = ConfigDeclarationBuilderKt.int(INSTANCE, 5, 1, 5).provideDelegate(INSTANCE, $$delegatedProperties[28]);
      VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[29]);
      AUDIO_REPLACE_SUCCESS_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[30]);
      TYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ToolReplaceVisualNotification.HOTBAR)
         .provideDelegate(INSTANCE, $$delegatedProperties[31]);
      VISUAL_REPLACE_FAILED_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[32]);
      AUDIO_REPLACE_FAILED_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(
            INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null
         )
         .provideDelegate(INSTANCE, $$delegatedProperties[33]);
      TYPE_VISUAL_REPLACE_FAILED_NOTIFICATION$delegate = ConfigDeclarationBuilderKt.enum(INSTANCE, ToolReplaceVisualNotification.SUBTITLE)
         .provideDelegate(INSTANCE, $$delegatedProperties[34]);
   }
}
