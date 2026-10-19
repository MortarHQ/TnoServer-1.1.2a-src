package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class SharedTextScreen extends AbstractStepEditorScreen {
   private static final String[] COLORS = new String[]{"", "white", "black", "red", "green", "blue", "input", "output", "slow", "medium", "fast"};
   private final StepTextFieldHandle keyField = new StepTextFieldHandle("key");
   private final StepXyzFieldHandle pointField = new StepXyzFieldHandle("point");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");
   private int colorIndex = 0;
   private boolean placeNearTarget = false;

   public SharedTextScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.shared_text"), scene, sceneIndex, parent);
   }

   public SharedTextScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.shared_text"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.shared_text");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.keyField, "ponderer.ui.shared_text.key", "ponderer.ui.shared_text.key.tooltip", UIText.of("ponderer.ui.shared_text.key.hint"), 140
         )
      );
      entries.add(FieldSpecs.xyz(this.pointField, "ponderer.ui.point", "ponderer.ui.point.tooltip", PickState.TargetField.POINT, true));
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.duration.tooltip.text", "60", 50));
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.color",
            "ponderer.ui.color.tooltip",
            100,
            () -> this.colorIndex = (this.colorIndex + 1) % COLORS.length,
            () -> this.colorIndex == 0 ? UIText.of("ponderer.ui.none") : this.colorLabel(COLORS[this.colorIndex]),
            () -> this.colorIndex == 0 ? 16777215 : getPaletteColor(COLORS[this.colorIndex])
         )
      );
      entries.add(
         FieldSpecs.toggle(
            "ponderer.ui.place_near", "ponderer.ui.place_near.tooltip", () -> this.placeNearTarget, () -> this.placeNearTarget = !this.placeNearTarget
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.key != null) {
         this.keyField.setValue(step.key);
      }

      if (step.point != null && step.point.size() >= 3) {
         this.pointField.setValue(step.point.get(0), step.point.get(1), step.point.get(2));
      }

      if (step.duration != null) {
         this.durationField.setValue(String.valueOf(step.duration));
      }

      if (step.color != null) {
         for (int i = 0; i < COLORS.length; i++) {
            if (COLORS[i].equalsIgnoreCase(step.color)) {
               this.colorIndex = i;
               break;
            }
         }
      }

      this.placeNearTarget = Boolean.TRUE.equals(step.placeNearTarget);
   }

   private String colorLabel(String value) {
      String key = "ponderer.ui.color.option." + value;
      String translated = UIText.of(key);
      return key.equals(translated) ? value : translated;
   }

   @Override
   protected String getStepType() {
      return "shared_text";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("colorIndex", String.valueOf(this.colorIndex));
      snapshot.put("placeNearTarget", String.valueOf(this.placeNearTarget));
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("colorIndex")) {
         try {
            this.colorIndex = Integer.parseInt(snapshot.get("colorIndex"));
         } catch (NumberFormatException var3) {
         }
      }

      if (snapshot.containsKey("placeNearTarget")) {
         this.placeNearTarget = Boolean.parseBoolean(snapshot.get("placeNearTarget"));
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      String key = this.keyField.getValue().trim();
      if (key.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.shared_text.error.required"));
         return null;
      }

      DslScene.DslStep s = new DslScene.DslStep();
      s.type = "shared_text";
      s.key = key;
      Double px = this.parseDouble(this.pointField.x(), "X");
      Double py = this.parseDouble(this.pointField.y(), "Y");
      Double pz = this.parseDouble(this.pointField.z(), "Z");
      if (px != null && py != null && pz != null) {
         s.point = List.of(px, py, pz);
         s.duration = this.parseIntOr(this.durationField.getValue(), 60);
         if (this.colorIndex > 0) {
            s.color = COLORS[this.colorIndex];
         }

         if (this.placeNearTarget) {
            s.placeNearTarget = true;
         }

         return s;
      } else {
         return null;
      }
   }
}
