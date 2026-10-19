package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class ChangeInterfaceSlotScreen extends AbstractStepEditorScreen {
   private final LinkedHashMap<Integer, DslScene.InterfaceSlotBinding> slotBindings = new LinkedHashMap<>();
   private boolean initialBindingsLoaded = false;

   public ChangeInterfaceSlotScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.change_interface_slot"), scene, sceneIndex, parent);
   }

   public ChangeInterfaceSlotScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.change_interface_slot"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.change_interface_slot.slots",
            "ponderer.ui.change_interface_slot.slots.tooltip",
            70,
            this::openSlotEditor,
            () -> UIText.of("ponderer.ui.change_interface_slot.edit")
         )
      );
      this.refreshInfoMessage();
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      this.slotBindings.clear();
      if (step != null && step.interfaceSlots != null) {
         for (DslScene.InterfaceSlotBinding binding : step.interfaceSlots) {
            if (binding != null && binding.slotIndex != null && binding.ingredientId != null && !binding.ingredientId.isBlank()) {
               this.slotBindings
                  .put(
                     binding.slotIndex,
                     new DslScene.InterfaceSlotBinding(binding.slotIndex, binding.slotX, binding.slotY, binding.ingredientId, binding.ingredientKind)
                  );
            }
         }
      }

      this.initialBindingsLoaded = true;
      this.refreshInfoMessage();
   }

   @Override
   protected String getStepType() {
      return "change_interface_slot";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      this.ensureInitialBindingsLoaded();
      InterfaceSlotEditState.writeBindingsToSnapshot(snapshot, this.slotBindings);
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      this.slotBindings.clear();
      this.slotBindings.putAll(InterfaceSlotEditState.readBindingsFromSnapshot(snapshot));
      this.initialBindingsLoaded = true;
      this.refreshInfoMessage();
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.ensureInitialBindingsLoaded();
      this.clearStatusMessages();
      if (this.slotBindings.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.change_interface_slot.error.empty"));
         return null;
      } else {
         DslScene.DslStep step = new DslScene.DslStep();
         step.type = "change_interface_slot";
         step.duration = null;
         step.interfaceSlots = new ArrayList<>(this.slotBindings.values());
         step.interfaceSlots.sort(Comparator.comparingInt(binding -> binding.slotIndex == null ? Integer.MAX_VALUE : binding.slotIndex));
         return step;
      }
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.change_interface_slot");
   }

   private void openSlotEditor() {
      this.ensureInitialBindingsLoaded();
      this.clearStatusMessages();
      Map<String, String> snapshot = this.snapshotForm();
      snapshot.put("_keyFrame", String.valueOf(this.attachKeyFrame));
      InterfaceSlotEditState.startEdit(
         snapshot, this.slotBindings, this.getStepType(), this.editIndex, this.insertAfterIndex, this.scene, this.sceneIndex, this.parent
      );
      InterfaceSlotEditState.openPonderUIForEdit();
   }

   private void ensureInitialBindingsLoaded() {
      if (!this.initialBindingsLoaded) {
         this.slotBindings.clear();
         if (this.isEditMode() && this.existingStep != null && this.existingStep.interfaceSlots != null) {
            for (DslScene.InterfaceSlotBinding binding : this.existingStep.interfaceSlots) {
               if (binding != null && binding.slotIndex != null && binding.ingredientId != null && !binding.ingredientId.isBlank()) {
                  this.slotBindings
                     .put(
                        binding.slotIndex,
                        new DslScene.InterfaceSlotBinding(binding.slotIndex, binding.slotX, binding.slotY, binding.ingredientId, binding.ingredientKind)
                     );
               }
            }
         } else {
            this.slotBindings.putAll(this.deriveInitialBindings());
         }

         this.initialBindingsLoaded = true;
         this.refreshInfoMessage();
      }
   }

   private LinkedHashMap<Integer, DslScene.InterfaceSlotBinding> deriveInitialBindings() {
      LinkedHashMap<Integer, DslScene.InterfaceSlotBinding> result = new LinkedHashMap<>();
      if (this.scene != null && this.scene.scenes != null && this.sceneIndex >= 0 && this.sceneIndex < this.scene.scenes.size()) {
         List<DslScene.DslStep> steps = this.scene.scenes.get(this.sceneIndex).steps;
         if (steps != null && !steps.isEmpty()) {
            int limit = this.insertAfterIndex >= 0 ? Math.min(this.insertAfterIndex, steps.size() - 1) : steps.size() - 1;

            for (int i = 0; i <= limit; i++) {
               DslScene.DslStep step = steps.get(i);
               if (step != null && step.type != null) {
                  if ("show_interface".equalsIgnoreCase(step.type)) {
                     result.clear();
                  } else if ("change_interface_slot".equalsIgnoreCase(step.type) && step.interfaceSlots != null) {
                     result.clear();

                     for (DslScene.InterfaceSlotBinding binding : step.interfaceSlots) {
                        if (binding != null && binding.slotIndex != null && binding.ingredientId != null && !binding.ingredientId.isBlank()) {
                           result.put(
                              binding.slotIndex,
                              new DslScene.InterfaceSlotBinding(binding.slotIndex, binding.slotX, binding.slotY, binding.ingredientId, binding.ingredientKind)
                           );
                        }
                     }
                  }
               }
            }

            return result;
         } else {
            return result;
         }
      } else {
         return result;
      }
   }

   private void refreshInfoMessage() {
      this.ensureInitialBindingsCountSafe();
      this.setInfoMessage(UIText.of("ponderer.ui.change_interface_slot.info", this.slotBindings.size()));
   }

   private void ensureInitialBindingsCountSafe() {
      if (this.initialBindingsLoaded) {
         ;
      }
   }
}
