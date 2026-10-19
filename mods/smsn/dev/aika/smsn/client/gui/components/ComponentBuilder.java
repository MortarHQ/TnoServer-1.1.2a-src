package dev.aika.smsn.client.gui.components;

import java.lang.reflect.Field;
import lombok.Generated;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ComponentBuilder {
   protected final ConfigEntryBuilder entryBuilder;
   protected final Object configObject;
   protected Class<?> defaultObject;
   protected String modId;

   public ComponentBuilder(ConfigEntryBuilder entryBuilder, Object configObject) {
      this.entryBuilder = entryBuilder;
      this.configObject = configObject;
   }

   public SwitchBuilder switchBuilder(Field field) {
      SwitchBuilder builder = new SwitchBuilder(this.entryBuilder, this.configObject, field);
      builder.setModId(this.modId);
      if (this.defaultObject != null) {
         builder.setDefaultObject(this.defaultObject);
      }

      return builder;
   }

   @Generated
   public ConfigEntryBuilder getEntryBuilder() {
      return this.entryBuilder;
   }

   @Generated
   public Object getConfigObject() {
      return this.configObject;
   }

   @Generated
   public ComponentBuilder setDefaultObject(Class<?> defaultObject) {
      this.defaultObject = defaultObject;
      return this;
   }

   @Generated
   public ComponentBuilder setModId(String modId) {
      this.modId = modId;
      return this;
   }
}
