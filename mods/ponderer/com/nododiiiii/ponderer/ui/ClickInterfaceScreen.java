package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class ClickInterfaceScreen extends AbstractStepEditorScreen {
   private static final String CLICK_ACTION_LEFT = "left";
   private static final String CLICK_ACTION_RIGHT = "right";
   private final StepXyzFieldHandle pointField = new StepXyzFieldHandle("point");
   private String clickAction = "left";

   public ClickInterfaceScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.click_interface"), scene, sceneIndex, parent);
   }

   public ClickInterfaceScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.click_interface"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.click_interface");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.xyz(
            this.pointField,
            "ponderer.ui.click_interface.point",
            "ponderer.ui.click_interface.point.tooltip",
            UIText.of("ponderer.ui.click_interface.point.hint_x"),
            UIText.of("ponderer.ui.click_interface.point.hint_y"),
            UIText.of("ponderer.ui.click_interface.point.hint_z"),
            FieldDecorators.pointPick(PickState.TargetField.POINT, false)
         )
      );
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.click_interface.action", "ponderer.ui.click_interface.action.tooltip", 70, this::cycleClickAction, this::clickActionLabel
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.pos != null && step.pos.size() >= 2) {
         this.pointField.xHandle().setValue(formatCoord(step.pos.get(0)));
         this.pointField.yHandle().setValue(formatCoord(step.pos.get(1)));
         this.pointField.zHandle().setValue(step.pos.size() >= 3 ? formatCoord(step.pos.get(2)) : "0.000");
      }

      if ("right".equalsIgnoreCase(step.action)) {
         this.clickAction = "right";
      } else {
         this.clickAction = "left";
      }
   }

   @Override
   protected String getStepType() {
      return "click_interface";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("clickAction", this.clickAction);
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("clickAction") && "right".equalsIgnoreCase(snapshot.get("clickAction"))) {
         this.clickAction = "right";
      } else {
         this.clickAction = "left";
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      Double x = parseDouble(this.pointField.x());
      Double y = parseDouble(this.pointField.y());
      Double z = parseDouble(this.pointField.z());
      if (x != null && y != null) {
         DslScene.DslStep step = new DslScene.DslStep();
         step.type = "click_interface";
         step.action = this.clickAction;
         step.pos = List.of(x, y, z == null ? 0.0 : z);
         step.duration = null;
         return step;
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.click_interface.error.invalid_point"));
         return null;
      }
   }

   private void cycleClickAction() {
      this.clickAction = "left".equals(this.clickAction) ? "right" : "left";
   }

   private String clickActionLabel() {
      return "left".equals(this.clickAction) ? UIText.of("ponderer.ui.click_interface.action.left") : UIText.of("ponderer.ui.click_interface.action.right");
   }

   @Nullable
   private static Double parseDouble(String raw) {
      if (raw == null) {
         return null;
      }

      String value = raw.trim();
      if (value.isEmpty()) {
         return null;
      }

      try {
         return Double.parseDouble(value);
      } catch (Exception ignored) {
         return null;
      }
   }

   private static String formatCoord(double v) {
      return String.format(Locale.ROOT, "%.3f", v);
   }
}
