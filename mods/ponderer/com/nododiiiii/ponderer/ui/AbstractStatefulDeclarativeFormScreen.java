package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeFormScreen;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractStatefulDeclarativeFormScreen extends AbstractDeclarativeFormScreen {
   private static final String BASELINE_COUNT_KEY = "__ponderer_baseline_count";
   private static final String BASELINE_KEY_PREFIX = "__ponderer_baseline_key_";
   private static final String BASELINE_VALUE_PREFIX = "__ponderer_baseline_value_";
   private final FormState formState = new FormState(this::snapshotState, this::restoreSnapshot);
   private boolean baselineCaptured = false;

   protected AbstractStatefulDeclarativeFormScreen(@Nullable Screen parent, String scopeKey, String titleKey) {
      super(parent, scopeKey, titleKey);
   }

   protected AbstractStatefulDeclarativeFormScreen(@Nullable Screen parent, String scopeKey, String titleKey, int preferredListWidth) {
      super(parent, scopeKey, titleKey, preferredListWidth);
   }

   @Override
   protected void init() {
      super.init();
      if (this.shouldAutoCaptureBaselineOnInit() && !this.baselineCaptured) {
         this.captureBaselineState();
      }
   }

   @Override
   protected final boolean hasUnsavedChanges() {
      return this.baselineCaptured && this.formState.hasUnsavedChanges();
   }

   @Override
   protected final int getUnsavedChangeCount() {
      return this.baselineCaptured ? this.formState.dirtyCount() : 0;
   }

   @Override
   protected void discardEdits() {
      this.clearStatusMessages();
      if (this.baselineCaptured) {
         this.restoreStateWithRebuild(this.formState.baselineSnapshot());
      }
   }

   protected boolean shouldAutoCaptureBaselineOnInit() {
      return true;
   }

   protected final void captureBaselineState() {
      this.formState.captureBaseline();
      this.baselineCaptured = true;
   }

   protected final void markStateSaved() {
      this.captureBaselineState();
   }

   protected final void restoreBaselineState(Map<String, String> snapshot) {
      this.formState.setBaselineSnapshot(snapshot);
      this.baselineCaptured = true;
   }

   protected final boolean isBaselineCaptured() {
      return this.baselineCaptured;
   }

   protected final Map<String, String> baselineStateSnapshot() {
      return this.formState.baselineSnapshot();
   }

   protected final void rebuildFormPreservingState() {
      this.restoreStateWithRebuild(this.snapshotState());
   }

   protected void prepareSnapshotForBuild(Map<String, String> snapshot) {
   }

   protected void afterSnapshotRestored(Map<String, String> snapshot) {
   }

   protected abstract Map<String, String> snapshotState();

   protected abstract void restoreSnapshot(Map<String, String> var1);

   protected final void appendBaselineSnapshotMetadata(Map<String, String> snapshot) {
      if (this.baselineCaptured) {
         Map<String, String> baseline = this.baselineStateSnapshot();
         snapshot.put("__ponderer_baseline_count", String.valueOf(baseline.size()));
         int index = 0;

         for (Entry<String, String> entry : baseline.entrySet()) {
            snapshot.put("__ponderer_baseline_key_" + index, entry.getKey());
            snapshot.put("__ponderer_baseline_value_" + index, entry.getValue());
            index++;
         }
      }
   }

   @Nullable
   protected final Map<String, String> extractBaselineSnapshotMetadata(Map<String, String> snapshot) {
      String rawCount = snapshot.remove("__ponderer_baseline_count");
      if (rawCount == null) {
         return null;
      }

      int count;
      try {
         count = Integer.parseInt(rawCount);
      } catch (NumberFormatException ignored) {
         return null;
      }

      Map<String, String> baseline = new LinkedHashMap<>();

      for (int i = 0; i < count; i++) {
         String key = snapshot.remove("__ponderer_baseline_key_" + i);
         String value = snapshot.remove("__ponderer_baseline_value_" + i);
         if (key != null && value != null) {
            baseline.put(key, value);
         }
      }

      return baseline;
   }

   private void restoreStateWithRebuild(Map<String, String> snapshot) {
      this.prepareSnapshotForBuild(snapshot);
      this.rebuildListPreservingScroll();
      this.restoreSnapshot(snapshot);
      this.afterSnapshotRestored(snapshot);
   }
}
