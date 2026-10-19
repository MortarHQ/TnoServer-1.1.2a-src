package com.nododiiiii.ponderer.ui;

import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public class StepTextFieldHandle implements HintableTextWidgetBinding {
   private final String snapshotKey;
   private String value = "";
   @Nullable
   private HintableTextFieldWidget widget;

   public StepTextFieldHandle(String snapshotKey) {
      this.snapshotKey = snapshotKey;
   }

   @Override
   public void attach(HintableTextFieldWidget widget) {
      this.widget = widget;
      if (!this.value.equals(widget.getValue())) {
         widget.setValue(this.value);
      }
   }

   public void setValue(@Nullable String value) {
      this.value = normalize(value);
      if (this.widget != null && !this.value.equals(this.widget.getValue())) {
         this.widget.setValue(this.value);
      }
   }

   public void set(@Nullable String value) {
      this.value = normalize(value);
   }

   public String getValue() {
      if (this.widget != null) {
         this.value = this.widget.getValue();
      }

      return this.value;
   }

   @Nullable
   @Override
   public HintableTextFieldWidget widget() {
      return this.widget;
   }

   public String get() {
      return this.getValue();
   }

   @Override
   public String snapshotKey() {
      return this.snapshotKey;
   }

   public String deserialize(String raw) {
      return raw;
   }

   @Override
   public void snapshot(Map<String, String> snapshot) {
      snapshot.put(this.snapshotKey, this.getValue());
   }

   @Override
   public void restore(Map<String, String> snapshot) {
      if (snapshot.containsKey(this.snapshotKey)) {
         this.setValue(snapshot.get(this.snapshotKey));
      }
   }

   private static String normalize(@Nullable String value) {
      return value != null ? value : "";
   }
}
