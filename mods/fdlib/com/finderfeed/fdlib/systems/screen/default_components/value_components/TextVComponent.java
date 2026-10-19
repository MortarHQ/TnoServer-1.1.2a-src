package com.finderfeed.fdlib.systems.screen.default_components.value_components;

import com.finderfeed.fdlib.systems.screen.FDEditorComponent;
import com.finderfeed.fdlib.systems.screen.FDScreen;
import com.finderfeed.fdlib.systems.screen.default_components.text.FDColoredTextBox;
import java.lang.annotation.Annotation;
import net.minecraft.network.chat.Component;

public class TextVComponent extends FDColoredTextBox<Component> {
   public TextVComponent(FDScreen screen, String uniqueId) {
      super(screen, uniqueId, 0.0F, 0.0F, 0.0F, 12.0F);
   }

   @Override
   public boolean isWidthFixed() {
      return false;
   }

   public void setValue(Component value) {
      this.value = new StringBuilder(value.getString());
   }

   public Component getValue() {
      return Component.translatable(this.value.toString());
   }

   @Override
   public void applyOptions(FDEditorComponent owner, Annotation annotation) {
   }
}
