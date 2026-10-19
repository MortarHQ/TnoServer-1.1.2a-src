package org.anti_ad.mc.ipnext.config;

import kotlin.jvm.internal.PropertyReference1;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.anti_ad.mc.common.config.builder.ConfigDeclaration;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilder;
import org.anti_ad.mc.common.config.builder.ConfigDeclarationBuilderKt;
import org.anti_ad.mc.common.config.options.ConfigKeyToggleBoolean;
import org.anti_ad.mc.common.extensions.AsDelegate;
import org.jetbrains.annotations.NotNull;

public final class Tweaks implements ConfigDeclaration {
   @NotNull
   public static final Tweaks INSTANCE;
   @NotNull
   private static final ConfigDeclarationBuilder builder = ConfigDeclarationBuilderKt.CATEGORY(
      ConfigDeclarationBuilderKt.CATEGORY(
         ConfigDeclarationBuilderKt.CATEGORY(
            ConfigDeclarationBuilderKt.createBuilder(INSTANCE = new Tweaks()), "inventoryprofiles.config.category.client_side_tweaks"
         ),
         "§§vgap:-5"
      ),
      "inventoryprofiles.config.category.inventory"
   );
   @NotNull
   private static final AsDelegate PREVENT_CLOSE_GUI_DROP_ITEM$delegate;
   @NotNull
   private static final AsDelegate CONTAINER_SWIPE_MOVING_ITEMS$delegate;
   @NotNull
   private static final AsDelegate SWIPE_MOVE_CRAFTING_RESULT_SLOT$delegate;

   private Tweaks() {
   }

   @NotNull
   public final ConfigDeclarationBuilder getBuilder() {
      return builder;
   }

   @NotNull
   public final ConfigKeyToggleBoolean getPREVENT_CLOSE_GUI_DROP_ITEM() {
      return (ConfigKeyToggleBoolean)PREVENT_CLOSE_GUI_DROP_ITEM$delegate.getValue(this, $$delegatedProperties[0]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getCONTAINER_SWIPE_MOVING_ITEMS() {
      return (ConfigKeyToggleBoolean)CONTAINER_SWIPE_MOVING_ITEMS$delegate.getValue(this, $$delegatedProperties[1]);
   }

   @NotNull
   public final ConfigKeyToggleBoolean getSWIPE_MOVE_CRAFTING_RESULT_SLOT() {
      return (ConfigKeyToggleBoolean)SWIPE_MOVE_CRAFTING_RESULT_SLOT$delegate.getValue(this, $$delegatedProperties[2]);
   }

   static {
      KProperty[] var0;
      (var0 = new KProperty[3])[0] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Tweaks.class, "PREVENT_CLOSE_GUI_DROP_ITEM", "getPREVENT_CLOSE_GUI_DROP_ITEM()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;", 0
            )
         )
      );
      var0[1] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Tweaks.class,
               "CONTAINER_SWIPE_MOVING_ITEMS",
               "getCONTAINER_SWIPE_MOVING_ITEMS()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      var0[2] = Reflection.property1(
         (PropertyReference1)(
            new PropertyReference1Impl(
               Tweaks.class,
               "SWIPE_MOVE_CRAFTING_RESULT_SLOT",
               "getSWIPE_MOVE_CRAFTING_RESULT_SLOT()Lorg/anti_ad/mc/common/config/options/ConfigKeyToggleBoolean;",
               0
            )
         )
      );
      $$delegatedProperties = var0;
      PREVENT_CLOSE_GUI_DROP_ITEM$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[0]);
      CONTAINER_SWIPE_MOVING_ITEMS$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, true, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[1]);
      SWIPE_MOVE_CRAFTING_RESULT_SLOT$delegate = ConfigDeclarationBuilderKt.keyToggleBool$default(INSTANCE, ConfigScreenSettings.INSTANCE, false, null, 4, null)
         .provideDelegate(INSTANCE, $$delegatedProperties[2]);
   }
}
