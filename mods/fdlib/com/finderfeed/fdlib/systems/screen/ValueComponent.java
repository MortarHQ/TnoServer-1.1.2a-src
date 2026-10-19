package com.finderfeed.fdlib.systems.screen;

import java.lang.annotation.Annotation;

public abstract class ValueComponent<T> extends FDScreenComponent {
   public ValueComponent(FDScreen screen, String uniqueId, float x, float y, float width, float height) {
      super(screen, uniqueId, x, y, width, height);
   }

   public abstract boolean isWidthFixed();

   public abstract void setValue(T var1);

   public abstract T getValue();

   public abstract void applyOptions(FDEditorComponent var1, Annotation var2);
}
