package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.mixin.PonderUIAccessor;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.ponder.foundation.PonderScene;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;

public final class PickState {
   private static boolean active = false;
   private static PickState.TargetField targetField;
   private static Map<String, String> formSnapshot = new HashMap<>();
   @Nullable
   private static StepEditorContext context;
   private static boolean useHalfOffset = false;
   @Nullable
   private static BlockPos pickedPos;
   @Nullable
   private static Direction pickedFace;
   @Nullable
   private static Double pickedUiX;
   @Nullable
   private static Double pickedUiY;

   private PickState() {
   }

   public static void startPick(
      PickState.TargetField target,
      Map<String, String> snapshot,
      String stepType,
      int editIndex,
      int insertAfterIndex,
      DslScene scene,
      int sceneIndex,
      SceneEditorScreen parent,
      boolean halfOffset
   ) {
      active = true;
      targetField = target;
      formSnapshot = new HashMap<>(snapshot);
      context = new StepEditorContext(stepType, editIndex, insertAfterIndex, scene, sceneIndex, parent);
      useHalfOffset = halfOffset;
      pickedPos = null;
   }

   public static void completePick(BlockPos pos, Direction face) {
      if (active) {
         pickedPos = pos;
         pickedFace = face;
         pickedUiX = null;
         pickedUiY = null;
         reopenEditor();
      }
   }

   public static void completeUiPick(double normalizedX, double normalizedY) {
      if (active) {
         pickedPos = null;
         pickedFace = null;
         pickedUiX = normalizedX;
         pickedUiY = normalizedY;
         reopenEditor();
      }
   }

   public static void cancelPick() {
      if (active) {
         pickedPos = null;
         pickedUiX = null;
         pickedUiY = null;
         reopenEditor();
      }
   }

   private static void reopenEditor() {
      String xKey;
      String yKey;
      String zKey;
      switch (targetField) {
         case POS2:
            xKey = "pos2X";
            yKey = "pos2Y";
            zKey = "pos2Z";
            break;
         case LOOK_AT:
            xKey = "lookAtX";
            yKey = "lookAtY";
            zKey = "lookAtZ";
            break;
         case POINT:
         case UI_POINT:
            xKey = "pointX";
            yKey = "pointY";
            zKey = "pointZ";
            break;
         default:
            xKey = "posX";
            yKey = "posY";
            zKey = "posZ";
      }

      if (targetField == PickState.TargetField.UI_POINT && pickedUiX != null && pickedUiY != null) {
         formSnapshot.put(xKey, formatUiCoord(pickedUiX));
         formSnapshot.put(yKey, formatUiCoord(pickedUiY));
         formSnapshot.put(zKey, "0.000");
      } else if (pickedPos != null) {
         if (useHalfOffset && pickedFace != null) {
            Axis faceAxis = pickedFace.getAxis();
            double ox = faceAxis != Axis.X ? pickedPos.getX() + 0.5 : pickedPos.getX();
            double oy = faceAxis != Axis.Y ? pickedPos.getY() + 0.5 : pickedPos.getY();
            double oz = faceAxis != Axis.Z ? pickedPos.getZ() + 0.5 : pickedPos.getZ();
            formSnapshot.put(xKey, String.valueOf(ox));
            formSnapshot.put(yKey, String.valueOf(oy));
            formSnapshot.put(zKey, String.valueOf(oz));
         } else {
            formSnapshot.put(xKey, String.valueOf(pickedPos.getX()));
            formSnapshot.put(yKey, String.valueOf(pickedPos.getY()));
            formSnapshot.put(zKey, String.valueOf(pickedPos.getZ()));
         }
      }

      StepEditorContext reopenContext = context;
      active = false;
      pickedPos = null;
      pickedFace = null;
      pickedUiX = null;
      pickedUiY = null;
      context = null;
      if (reopenContext != null) {
         reopenContext.reopenEditor(formSnapshot);
      } else {
         formSnapshot.clear();
      }
   }

   public static void openPonderUIForPick() {
      if (active && context != null) {
         ResourceLocation itemId = getItemId();
         if (itemId == null) {
            cancelPick();
         } else {
            PonderUI ponderUI = PonderUI.of(itemId);
            PonderUIAccessor accessor = (PonderUIAccessor)ponderUI;
            List<PonderScene> ponderScenes = accessor.ponderer$getScenes();

            for (int i = 0; i < ponderScenes.size(); i++) {
               SceneRuntime.SceneMatch match = SceneRuntime.findBySceneId(ponderScenes.get(i).getId());
               if (match != null && match.sceneIndex() == context.sceneIndex() && match.scene().id.equals(context.scene().id)) {
                  accessor.ponderer$setIndex(i);
                  accessor.ponderer$getLazyIndex().startWithValue(i);
                  ponderScenes.get(i).begin();
                  break;
               }
            }

            Minecraft.getInstance().setScreen(ponderUI);
         }
      }
   }

   @Nullable
   private static ResourceLocation getItemId() {
      return context != null && context.scene().items != null && !context.scene().items.isEmpty()
         ? ResourceLocation.tryParse(context.scene().items.get(0))
         : null;
   }

   public static boolean isActive() {
      return active;
   }

   public static PickState.TargetField getTargetField() {
      return targetField;
   }

   public static boolean isUiPointPickActive() {
      return active && targetField == PickState.TargetField.UI_POINT;
   }

   public static boolean isHalfOffset() {
      return useHalfOffset;
   }

   public static void reset() {
      active = false;
      pickedPos = null;
      pickedFace = null;
      pickedUiX = null;
      pickedUiY = null;
      context = null;
      formSnapshot.clear();
   }

   private static String formatUiCoord(double value) {
      double clamped = Math.max(-2.0, Math.min(2.0, value));
      double rounded = Math.round(clamped * 1000.0) / 1000.0;
      if (Math.abs(rounded) < 5.0E-4) {
         rounded = 0.0;
      }

      return String.format(Locale.ROOT, "%.3f", rounded);
   }

   public enum TargetField {
      POS1,
      POS2,
      LOOK_AT,
      POINT,
      UI_POINT;
   }
}
