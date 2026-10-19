package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class HighlightSectionScreen extends AbstractStepEditorScreen {
   private static final String[] COLORS = new String[]{"blue", "white", "black", "red", "green", "input", "output", "slow", "medium", "fast"};
   private final StepXyzFieldHandle pos1Field = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private final StepTextFieldHandle durationField = new StepTextFieldHandle("duration");
   private int colorIndex = 0;

   public HighlightSectionScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.highlight_section.add"), scene, sceneIndex, parent);
   }

   public HighlightSectionScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.highlight_section.edit"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.highlight_section");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.xyz(this.pos1Field, "ponderer.ui.highlight_section.pos_from", "ponderer.ui.highlight_section.pos_from.tooltip", PickState.TargetField.POS1)
      );
      entries.add(
         FieldSpecs.xyz(this.pos2Field, "ponderer.ui.highlight_section.pos_to", "ponderer.ui.highlight_section.pos_to.tooltip", PickState.TargetField.POS2)
      );
      entries.add(FieldSpecs.ticksNumber(this.durationField, "ponderer.ui.duration", "ponderer.ui.highlight_section.duration.tooltip", "40", 50));
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.color",
            "ponderer.ui.color.tooltip",
            100,
            () -> this.colorIndex = (this.colorIndex + 1) % COLORS.length,
            () -> this.colorLabel(COLORS[this.colorIndex]),
            () -> getPaletteColor(COLORS[this.colorIndex])
         )
      );
   }

   private String colorLabel(String value) {
      String key = "ponderer.ui.color.option." + value;
      String translated = UIText.of(key);
      return key.equals(translated) ? value : translated;
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.pos1Field.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
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
   }

   @Override
   protected String getStepType() {
      return "highlight_section";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("colorIndex", String.valueOf(this.colorIndex));
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("colorIndex")) {
         try {
            this.colorIndex = Integer.parseInt(snapshot.get("colorIndex"));
         } catch (NumberFormatException var3) {
         }
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Integer p1x = this.parseInt(this.pos1Field.x(), "From X");
      Integer p1y = this.parseInt(this.pos1Field.y(), "From Y");
      Integer p1z = this.parseInt(this.pos1Field.z(), "From Z");
      if (p1x != null && p1y != null && p1z != null) {
         Integer p2x = this.parseOptionalInt(this.pos2Field.x(), "To X");
         Integer p2y = this.parseOptionalInt(this.pos2Field.y(), "To Y");
         Integer p2z = this.parseOptionalInt(this.pos2Field.z(), "To Z");
         boolean hasPos2 = p2x != null || p2y != null || p2z != null;
         if (!hasPos2 || p2x != null && p2y != null && p2z != null) {
            int duration = Math.max(1, this.parseIntOr(this.durationField.getValue(), 40));
            DslScene.DslStep s = new DslScene.DslStep();
            s.type = "highlight_section";
            s.blockPos = List.of(p1x, p1y, p1z);
            if (hasPos2) {
               s.blockPos2 = List.of(p2x, p2y, p2z);
            }

            s.duration = duration;
            s.color = COLORS[this.colorIndex];
            return s;
         } else {
            this.setErrorMessage(UIText.of("ponderer.ui.highlight_section.error.partial_to"));
            return null;
         }
      } else {
         return null;
      }
   }

   @Nullable
   private Integer parseOptionalInt(String raw, String label) {
      String trimmed = raw == null ? "" : raw.trim();
      return trimmed.isEmpty() ? null : this.parseInt(trimmed, label);
   }
}
