package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;
import net.minecraft.client.Minecraft;

public class CommandParamScreen extends AbstractJeiAwareFormScreen {
   private static final int CHOICE_BUTTON_WIDTH = 100;
   private final List<CommandParamScreen.FieldDef> fieldDefs;
   private final Consumer<Map<String, String>> onExecute;
   private final Map<String, String> textValues = new LinkedHashMap<>();
   private final Map<String, Integer> choiceSelections = new HashMap<>();
   private final Map<String, Boolean> toggleStates = new HashMap<>();
   private final Map<String, HintableTextFieldWidget> textInputs = new LinkedHashMap<>();
   private final Map<String, String> defaultValues = new HashMap<>();
   private final Map<String, String> toggleDependencies = new HashMap<>();
   private final Map<String, String> fieldDisablesToggle = new HashMap<>();
   private final Map<String, Map<String, Supplier<String>>> toggleAutoFill = new HashMap<>();
   private final Map<String, CommandParamScreen.VisibilityRule> fieldVisibility = new HashMap<>();
   private boolean suppressFieldResponder = false;
   private boolean collectingEntries = false;

   private CommandParamScreen(String titleKey, List<CommandParamScreen.FieldDef> fieldDefs, Consumer<Map<String, String>> onExecute) {
      super(new FunctionScreen(), "ponderer.ui.scope.editor", titleKey, 300, JeiCompat::setActiveScreen);
      this.fieldDefs = fieldDefs;
      this.onExecute = onExecute;
   }

   public static CommandParamScreen.Builder builder(String titleKey) {
      return new CommandParamScreen.Builder(titleKey);
   }

   public void setDefaultValue(String fieldId, String value) {
      this.defaultValues.put(fieldId, value);
   }

   public void addToggleDependency(String childToggleId, String parentToggleId) {
      this.toggleDependencies.put(childToggleId, parentToggleId);
   }

   public void addFieldDisablesToggle(String fieldId, String toggleId) {
      this.fieldDisablesToggle.put(fieldId, toggleId);
   }

   public void addToggleAutoFill(String toggleId, String fieldId, Supplier<String> valueSupplier) {
      this.toggleAutoFill.computeIfAbsent(toggleId, key -> new HashMap<>()).put(fieldId, valueSupplier);
   }

   public void showFieldWhenValue(String fieldId, String controllerFieldId, String expectedValue) {
      this.fieldVisibility.put(fieldId, new CommandParamScreen.VisibilityRule(controllerFieldId, expectedValue));
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      this.textInputs.clear();
      this.collectingEntries = true;

      try {
         for (CommandParamScreen.FieldDef def : this.fieldDefs) {
            if (this.isFieldVisible(def)) {
               if (def instanceof CommandParamScreen.TextFieldDef textDef) {
                  if (textDef.sceneSelector) {
                     entries.add(
                        FieldSpecs.labeledButton(
                           textDef.labelKey,
                           textDef.hintKey,
                           () -> this.openSceneSelector(textDef.id, textDef.sceneMultiSelect),
                           () -> this.currentSceneSelectionButtonLabel(textDef),
                           this.sceneSelectorTooltipGetter(textDef)
                        )
                     );
                  } else {
                     entries.add(
                        FieldSpecs.text(
                           FieldBindings.transientString(() -> this.currentTextValue(textDef.id), value -> this.handleTextChanged(textDef.id, value)),
                           textDef.labelKey,
                           null,
                           textDef.hintKey,
                           -1,
                           entry -> {
                              entry.field().setMaxLength(32500);
                              this.textInputs.put(textDef.id, entry.field());
                           },
                           this.textDecoratorsFor(textDef)
                        )
                     );
                  }
               } else if (def instanceof CommandParamScreen.ChoiceFieldDef choiceDef) {
                  this.ensureChoiceState(choiceDef);
                  entries.add(
                     FieldSpecs.choice(
                        choiceDef.labelKey,
                        null,
                        100,
                        () -> this.cycleChoice(choiceDef),
                        () -> UIText.of(choiceDef.optionLabelKeys.get(this.choiceSelections.getOrDefault(choiceDef.id, 0))),
                        () -> 16777215,
                        null
                     )
                  );
               } else {
                  CommandParamScreen.ToggleFieldDef toggleDef = (CommandParamScreen.ToggleFieldDef)def;
                  this.toggleStates.putIfAbsent(toggleDef.id, toggleDef.defaultValue);
                  entries.add(
                     FieldSpecs.toggle(
                        toggleDef.labelKey,
                        null,
                        () -> this.toggleStates.getOrDefault(toggleDef.id, toggleDef.defaultValue),
                        () -> this.handleToggle(toggleDef.id)
                     )
                  );
               }
            }
         }
      } finally {
         this.collectingEntries = false;
      }
   }

   @Override
   protected boolean saveEdits() {
      this.clearStatusMessages();
      Map<String, String> values = new HashMap<>();

      for (CommandParamScreen.FieldDef def : this.fieldDefs) {
         if (def instanceof CommandParamScreen.TextFieldDef textDef) {
            String value = this.currentTextValue(textDef.id).trim();
            if (textDef.required && value.isEmpty()) {
               this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", UIText.of(textDef.labelKey)));
               return false;
            }

            values.put(textDef.id, value);
         } else if (def instanceof CommandParamScreen.ChoiceFieldDef choiceDef) {
            this.ensureChoiceState(choiceDef);
            values.put(choiceDef.id, choiceDef.values.get(this.choiceSelections.get(choiceDef.id)));
         } else {
            CommandParamScreen.ToggleFieldDef toggleDef = (CommandParamScreen.ToggleFieldDef)def;
            values.put(toggleDef.id, String.valueOf(this.toggleStates.getOrDefault(toggleDef.id, toggleDef.defaultValue)));
         }
      }

      this.deactivateJei();
      Minecraft.getInstance().setScreen(null);
      this.onExecute.accept(values);
      return true;
   }

   @Override
   protected void prepareSnapshotForBuild(Map<String, String> snapshot) {
      this.restoreSnapshot(snapshot);
   }

   @Override
   protected boolean rebuildOnJeiStateChange() {
      return true;
   }

   @Override
   protected boolean isSaveButtonActive() {
      return true;
   }

   @Override
   protected void restoreSnapshot(Map<String, String> snapshot) {
      for (CommandParamScreen.FieldDef def : this.fieldDefs) {
         if (def instanceof CommandParamScreen.TextFieldDef textDef) {
            this.textValues.put(textDef.id, snapshot.getOrDefault(textDef.id, this.defaultValues.getOrDefault(textDef.id, "")));
         } else if (def instanceof CommandParamScreen.ChoiceFieldDef choiceDef) {
            String targetValue = snapshot.get(choiceDef.id);
            int index = targetValue == null ? 0 : choiceDef.values.indexOf(targetValue);
            this.choiceSelections.put(choiceDef.id, Math.max(0, index));
         } else {
            CommandParamScreen.ToggleFieldDef toggleDef = (CommandParamScreen.ToggleFieldDef)def;
            boolean state = Boolean.parseBoolean(snapshot.getOrDefault(toggleDef.id, String.valueOf(toggleDef.defaultValue)));
            this.toggleStates.put(toggleDef.id, state);
         }
      }
   }

   @Override
   protected Map<String, String> snapshotState() {
      Map<String, String> snapshot = new LinkedHashMap<>();

      for (CommandParamScreen.FieldDef def : this.fieldDefs) {
         if (def instanceof CommandParamScreen.TextFieldDef textDef) {
            snapshot.put(textDef.id, this.currentTextValue(textDef.id));
         } else if (def instanceof CommandParamScreen.ChoiceFieldDef choiceDef) {
            this.ensureChoiceState(choiceDef);
            snapshot.put(choiceDef.id, choiceDef.values.get(this.choiceSelections.get(choiceDef.id)));
         } else {
            CommandParamScreen.ToggleFieldDef toggleDef = (CommandParamScreen.ToggleFieldDef)def;
            snapshot.put(toggleDef.id, String.valueOf(this.toggleStates.getOrDefault(toggleDef.id, toggleDef.defaultValue)));
         }
      }

      return snapshot;
   }

   private void handleTextChanged(String fieldId, String value) {
      this.textValues.put(fieldId, value);
      this.clearStatusMessages();
      if (!this.suppressFieldResponder) {
         String toggleToDisable = this.fieldDisablesToggle.get(fieldId);
         if (toggleToDisable != null && !this.collectingEntries) {
            this.toggleStates.put(toggleToDisable, false);

            for (Entry<String, String> dependency : this.toggleDependencies.entrySet()) {
               if (dependency.getValue().equals(toggleToDisable)) {
                  this.toggleStates.put(dependency.getKey(), false);
               }
            }

            this.rebuildListPreservingScroll();
         }
      }
   }

   private void handleToggle(String id) {
      boolean newState = !this.toggleStates.getOrDefault(id, false);
      String parent = this.toggleDependencies.get(id);
      if (parent == null || !newState || this.toggleStates.getOrDefault(parent, false)) {
         this.toggleStates.put(id, newState);
         if (newState) {
            Map<String, Supplier<String>> fills = this.toggleAutoFill.get(id);
            if (fills != null) {
               for (Entry<String, Supplier<String>> fill : fills.entrySet()) {
                  this.setTextValue(fill.getKey(), fill.getValue().get());
               }
            }
         } else {
            for (Entry<String, String> dependency : this.toggleDependencies.entrySet()) {
               if (dependency.getValue().equals(id)) {
                  this.toggleStates.put(dependency.getKey(), false);
               }
            }
         }

         this.clearStatusMessages();
         this.rebuildListPreservingScroll();
      }
   }

   private void openSceneSelector(String targetFieldId, boolean multiSelect) {
      List<DslScene> scenes = SceneRuntime.getScenes();
      if (scenes.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.no_scenes"));
      } else if (multiSelect) {
         Minecraft.getInstance().setScreen(new PonderItemGridScreen(selectedIds -> {
            this.setTextValue(targetFieldId, String.join(",", selectedIds));
            Minecraft.getInstance().setScreen(this);
         }, () -> Minecraft.getInstance().setScreen(this), true));
      } else {
         Minecraft.getInstance().setScreen(new PonderItemGridScreen(sceneId -> {
            this.setTextValue(targetFieldId, sceneId);
            Minecraft.getInstance().setScreen(this);
         }, () -> Minecraft.getInstance().setScreen(this)));
      }
   }

   private void cycleChoice(CommandParamScreen.ChoiceFieldDef choiceDef) {
      this.ensureChoiceState(choiceDef);
      int next = (this.choiceSelections.get(choiceDef.id) + 1) % choiceDef.values.size();
      this.choiceSelections.put(choiceDef.id, next);
      this.clearStatusMessages();
      this.rebuildListPreservingScroll();
   }

   private void ensureChoiceState(CommandParamScreen.ChoiceFieldDef choiceDef) {
      this.choiceSelections.computeIfAbsent(choiceDef.id, ignored -> {
         String baselineValue = this.defaultValues.get(choiceDef.id);
         if (baselineValue != null) {
            int index = choiceDef.values.indexOf(baselineValue);
            if (index >= 0) {
               return index;
            }
         }

         return 0;
      });
   }

   private String currentTextValue(String fieldId) {
      return this.textValues.getOrDefault(fieldId, this.defaultValues.getOrDefault(fieldId, ""));
   }

   private String currentSceneSelectionButtonLabel(CommandParamScreen.TextFieldDef textDef) {
      int selectedCount = this.selectedSceneCount(this.currentTextValue(textDef.id));
      return selectedCount <= 0
         ? UIText.of(textDef.sceneMultiSelect ? "ponderer.ui.export.select_scenes" : "ponderer.ui.function_page.select_scene")
         : UIText.of("ponderer.ui.export.selected_scenes", selectedCount);
   }

   private int selectedSceneCount(String rawValue) {
      if (rawValue != null && !rawValue.isBlank()) {
         int count = 0;

         for (String part : rawValue.split(",")) {
            if (!part.trim().isEmpty()) {
               count++;
            }
         }

         return count;
      } else {
         return 0;
      }
   }

   @Nullable
   private Supplier<String> sceneSelectorTooltipGetter(CommandParamScreen.TextFieldDef textDef) {
      return () -> {
         int selectedCount = this.selectedSceneCount(this.currentTextValue(textDef.id));
         if (selectedCount > 0) {
            return UIText.of("ponderer.ui.export.selected_scenes", selectedCount);
         } else {
            return textDef.hintKey != null && !textDef.hintKey.isBlank() ? UIText.of(textDef.hintKey) : "";
         }
      };
   }

   private FieldDecorator[] textDecoratorsFor(CommandParamScreen.TextFieldDef textDef) {
      List<FieldDecorator> specs = new ArrayList<>();
      if (textDef.jeiMode != null) {
         specs.add(FieldDecorators.jei(textDef.jeiMode));
      }

      if (textDef.sceneSelector) {
         specs.add(FieldDecorators.sceneSelector(() -> this.openSceneSelector(textDef.id, textDef.sceneMultiSelect)));
      }

      return specs.toArray(FieldDecorator[]::new);
   }

   private boolean isFieldVisible(CommandParamScreen.FieldDef def) {
      CommandParamScreen.VisibilityRule rule = this.fieldVisibility.get(fieldId(def));
      return rule == null ? true : rule.expectedValue().equals(this.currentFieldValue(rule.controllerFieldId()));
   }

   private String currentFieldValue(String fieldId) {
      CommandParamScreen.FieldDef def = this.findFieldDef(fieldId);
      if (def instanceof CommandParamScreen.TextFieldDef textDef) {
         return this.currentTextValue(textDef.id);
      } else if (def instanceof CommandParamScreen.ChoiceFieldDef choiceDef) {
         this.ensureChoiceState(choiceDef);
         return choiceDef.values.get(this.choiceSelections.get(choiceDef.id));
      } else {
         return def instanceof CommandParamScreen.ToggleFieldDef toggleDef
            ? String.valueOf(this.toggleStates.getOrDefault(toggleDef.id, toggleDef.defaultValue))
            : "";
      }
   }

   private CommandParamScreen.FieldDef findFieldDef(String fieldId) {
      for (CommandParamScreen.FieldDef def : this.fieldDefs) {
         if (fieldId(def).equals(fieldId)) {
            return def;
         }
      }

      throw new IllegalArgumentException("Unknown field id: " + fieldId);
   }

   private static String fieldId(CommandParamScreen.FieldDef def) {
      if (def instanceof CommandParamScreen.TextFieldDef textDef) {
         return textDef.id;
      } else {
         return def instanceof CommandParamScreen.ChoiceFieldDef choiceDef ? choiceDef.id : ((CommandParamScreen.ToggleFieldDef)def).id;
      }
   }

   private void setTextValue(String fieldId, @Nullable String value) {
      this.clearStatusMessages();
      this.textValues.put(fieldId, value == null ? "" : value);
      HintableTextFieldWidget field = this.textInputs.get(fieldId);
      if (field != null) {
         this.suppressFieldResponder = true;
         field.setValue(value == null ? "" : value);
         this.suppressFieldResponder = false;
      }
   }

   public static class Builder {
      private final String titleKey;
      private final List<CommandParamScreen.FieldDef> fields = new ArrayList<>();
      private Consumer<Map<String, String>> onExecute = ignored -> {};

      private Builder(String titleKey) {
         this.titleKey = titleKey;
      }

      public CommandParamScreen.Builder textField(String id, String labelKey, String hintKey, boolean required) {
         this.fields.add(new CommandParamScreen.TextFieldDef(id, labelKey, hintKey, required, null, false, false));
         return this;
      }

      public CommandParamScreen.Builder itemField(String id, String labelKey, String hintKey, boolean required) {
         this.fields.add(new CommandParamScreen.TextFieldDef(id, labelKey, hintKey, required, IdFieldMode.ITEM, false, false));
         return this;
      }

      public CommandParamScreen.Builder sceneIdField(String id, String labelKey, @Nullable String hintKey, boolean required) {
         this.fields.add(new CommandParamScreen.TextFieldDef(id, labelKey, hintKey, required, null, true, false));
         return this;
      }

      public CommandParamScreen.Builder sceneIdField(String id, String labelKey, @Nullable String hintKey, boolean required, boolean multiSelect) {
         this.fields.add(new CommandParamScreen.TextFieldDef(id, labelKey, hintKey, required, null, true, multiSelect));
         return this;
      }

      public CommandParamScreen.Builder choiceField(String id, String labelKey, List<String> optionLabelKeys, List<String> values) {
         this.fields.add(new CommandParamScreen.ChoiceFieldDef(id, labelKey, optionLabelKeys, values));
         return this;
      }

      public CommandParamScreen.Builder toggleField(String id, String labelKey, boolean defaultValue) {
         this.fields.add(new CommandParamScreen.ToggleFieldDef(id, labelKey, defaultValue));
         return this;
      }

      public CommandParamScreen.Builder onExecute(Consumer<Map<String, String>> callback) {
         this.onExecute = callback;
         return this;
      }

      public CommandParamScreen build() {
         return new CommandParamScreen(this.titleKey, List.copyOf(this.fields), this.onExecute);
      }
   }

   public record ChoiceFieldDef(String id, String labelKey, List<String> optionLabelKeys, List<String> values) implements CommandParamScreen.FieldDef {
   }

   public sealed interface FieldDef permits CommandParamScreen.TextFieldDef, CommandParamScreen.ChoiceFieldDef, CommandParamScreen.ToggleFieldDef {
   }

   public record TextFieldDef(
      String id, String labelKey, @Nullable String hintKey, boolean required, @Nullable IdFieldMode jeiMode, boolean sceneSelector, boolean sceneMultiSelect
   ) implements CommandParamScreen.FieldDef {
   }

   public record ToggleFieldDef(String id, String labelKey, boolean defaultValue) implements CommandParamScreen.FieldDef {
   }

   private record VisibilityRule(String controllerFieldId, String expectedValue) {
   }
}
