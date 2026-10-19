package com.nododiiiii.ponderer.ui;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class FormState {
   private final Supplier<Map<String, String>> snapshotSupplier;
   private final Consumer<Map<String, String>> restoreConsumer;
   private Map<String, String> baselineSnapshot = new LinkedHashMap<>();

   public FormState(Supplier<Map<String, String>> snapshotSupplier, Consumer<Map<String, String>> restoreConsumer) {
      this.snapshotSupplier = snapshotSupplier;
      this.restoreConsumer = restoreConsumer;
   }

   public void captureBaseline() {
      this.baselineSnapshot = this.snapshot();
   }

   public Map<String, String> snapshot() {
      return new LinkedHashMap<>(this.snapshotSupplier.get());
   }

   public void restore(Map<String, String> snapshot) {
      this.restoreConsumer.accept(new LinkedHashMap<>(snapshot));
   }

   public void restoreBaseline() {
      this.restore(this.baselineSnapshot);
   }

   public Map<String, String> baselineSnapshot() {
      return new LinkedHashMap<>(this.baselineSnapshot);
   }

   public void setBaselineSnapshot(Map<String, String> snapshot) {
      this.baselineSnapshot = new LinkedHashMap<>(snapshot);
   }

   public int dirtyCount() {
      return diffCount(this.baselineSnapshot, this.snapshot());
   }

   public boolean hasUnsavedChanges() {
      return this.dirtyCount() > 0;
   }

   public static Map<String, String> snapshotOf(Iterable<? extends SnapshotParticipant> participants) {
      Map<String, String> snapshot = new LinkedHashMap<>();

      for (SnapshotParticipant participant : participants) {
         if (participant != null) {
            participant.snapshot(snapshot);
         }
      }

      return snapshot;
   }

   public static void restoreInto(Map<String, String> snapshot, Iterable<? extends SnapshotParticipant> participants) {
      for (SnapshotParticipant participant : participants) {
         if (participant != null) {
            participant.restore(snapshot);
         }
      }
   }

   public static int diffCount(Map<String, String> left, Map<String, String> right) {
      Set<String> keys = new LinkedHashSet<>(left.keySet());
      keys.addAll(right.keySet());
      int dirty = 0;

      for (String key : keys) {
         if (!Objects.equals(left.get(key), right.get(key))) {
            dirty++;
         }
      }

      return dirty;
   }
}
