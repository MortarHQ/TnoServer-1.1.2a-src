package dev.aika.smsn.client.gui.components;

import dev.aika.smsn.annotation.Components;
import java.lang.reflect.Field;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class SwitchBuilder extends AbstractComponentBuilder<Boolean> {
   private final Components.Switch annotation;
   public static final String DefaultCheckedText = "true";
   public static final String DefaultUncheckedText = "false";

   SwitchBuilder(ConfigEntryBuilder entryBuilder, Object object, Field field) {
      super(entryBuilder, object);
      this.field = field;
      this.annotation = field.getAnnotation(Components.Switch.class);
   }

   @Override
   public AbstractConfigListEntry<Boolean> build() {
      BooleanToggleBuilder builder = this.entryBuilder
         .startBooleanToggle(Component.translatable(String.format("%s.%s", this.translatableKeyPrefix(), this.field.getName())), this.getValue())
         .setSaveConsumer(this::setValue)
         .setYesNoTextSupplier(this::yesNoTextSupplier);
      if (this.defaultObject != null) {
         builder = (BooleanToggleBuilder)builder.setDefaultValue(this.getDefaultValue());
      }

      return builder.build();
   }

   private Component yesNoTextSupplier(Boolean value) {
      return this.annotation == null
         ? Component.translatable(String.format("config.%s.@switch.%s", this.modId, value ? "true" : "false"))
         : Component.translatable(String.format("config.%s.@switch.%s", this.modId, value ? this.annotation.checked() : this.annotation.unchecked()));
   }
}
