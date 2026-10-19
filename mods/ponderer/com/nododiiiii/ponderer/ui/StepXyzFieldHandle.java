package com.nododiiiii.ponderer.ui;

import java.util.Map;

public class StepXyzFieldHandle implements SnapshotParticipant {
   private final StepTextFieldHandle x;
   private final StepTextFieldHandle y;
   private final StepTextFieldHandle z;

   public StepXyzFieldHandle(String snapshotPrefix) {
      this(snapshotPrefix + "X", snapshotPrefix + "Y", snapshotPrefix + "Z");
   }

   public StepXyzFieldHandle(String xKey, String yKey, String zKey) {
      this.x = new StepTextFieldHandle(xKey);
      this.y = new StepTextFieldHandle(yKey);
      this.z = new StepTextFieldHandle(zKey);
   }

   public StepTextFieldHandle xHandle() {
      return this.x;
   }

   public StepTextFieldHandle yHandle() {
      return this.y;
   }

   public StepTextFieldHandle zHandle() {
      return this.z;
   }

   public void setValue(double x, double y, double z) {
      this.x.setValue(String.valueOf(x));
      this.y.setValue(String.valueOf(y));
      this.z.setValue(String.valueOf(z));
   }

   public void setValue(int x, int y, int z) {
      this.x.setValue(String.valueOf(x));
      this.y.setValue(String.valueOf(y));
      this.z.setValue(String.valueOf(z));
   }

   public String x() {
      return this.x.getValue();
   }

   public String y() {
      return this.y.getValue();
   }

   public String z() {
      return this.z.getValue();
   }

   @Override
   public void snapshot(Map<String, String> snapshot) {
      this.x.snapshot(snapshot);
      this.y.snapshot(snapshot);
      this.z.snapshot(snapshot);
   }

   @Override
   public void restore(Map<String, String> snapshot) {
      this.x.restore(snapshot);
      this.y.restore(snapshot);
      this.z.restore(snapshot);
   }
}
