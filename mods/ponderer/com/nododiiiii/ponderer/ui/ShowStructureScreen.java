package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.PondererClientCommands;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import com.nododiiiii.ponderer.util.SafePaths;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class ShowStructureScreen extends AbstractStepEditorScreen {
   private final StepTextFieldHandle scaleField = new StepTextFieldHandle("scale");
   private final StepTextFieldHandle rotationField = new StepTextFieldHandle("rotation");
   private final StepXyzFieldHandle posField = new StepXyzFieldHandle("pos");
   private final StepXyzFieldHandle pos2Field = new StepXyzFieldHandle("pos2");
   private final StepTextFieldHandle structureField = new StepTextFieldHandle("structure");
   private boolean waitingDownload;
   private String waitingSourceId;
   private DslScene.DslStep pendingStep;

   public ShowStructureScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.show_structure"), scene, sceneIndex, parent);
   }

   public ShowStructureScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.show_structure"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.show_structure");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.structureField,
            "ponderer.ui.show_structure.structure",
            "ponderer.ui.show_structure.structure.tooltip",
            UIText.of("ponderer.ui.show_structure.structure.hint"),
            105,
            FieldDecorators.textAction(20, this::openFilePicker, () -> "S", () -> 16777215, UIText.of("ponderer.ui.show_structure.browse.tooltip"))
         )
      );
      entries.add(FieldSpecs.number(this.scaleField, "ponderer.ui.show_structure.scale", "ponderer.ui.show_structure.scale.tooltip", "1.0", 60, null));
      entries.add(FieldSpecs.number(this.rotationField, "ponderer.ui.show_structure.rotation", "ponderer.ui.show_structure.rotation.tooltip", "0", 60, null));
      entries.add(
         FieldSpecs.xyz(this.posField, "ponderer.ui.show_structure.pos_from", "ponderer.ui.show_structure.pos_from.tooltip", PickState.TargetField.POS1)
      );
      entries.add(FieldSpecs.xyz(this.pos2Field, "ponderer.ui.show_structure.pos_to", "ponderer.ui.show_structure.pos_to.tooltip", PickState.TargetField.POS2));
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.scale != null) {
         this.scaleField.setValue(String.valueOf(step.scale));
      }

      if (step.rotation != null) {
         this.rotationField.setValue(String.valueOf(step.rotation));
      }

      if (step.blockPos != null && step.blockPos.size() >= 3) {
         this.posField.setValue(step.blockPos.get(0), step.blockPos.get(1), step.blockPos.get(2));
      }

      if (step.blockPos2 != null && step.blockPos2.size() >= 3) {
         this.pos2Field.setValue(step.blockPos2.get(0), step.blockPos2.get(1), step.blockPos2.get(2));
      }

      if (step.structure != null && !step.structure.isBlank()) {
         this.structureField.setValue(step.structure);
      }
   }

   private void openFilePicker() {
      Path structuresDir = SceneStore.getStructureDir();
      CompletableFuture.<String>supplyAsync(() -> {
         try {
            String defaultPath = Files.exists(structuresDir) ? structuresDir.toAbsolutePath().toString() + File.separator : null;
            MemoryStack stack = MemoryStack.stackPush();

            try {
               PointerBuffer filters = stack.mallocPointer(1);
               filters.put(stack.UTF8("*.nbt"));
               filters.flip();
               return TinyFileDialogs.tinyfd_openFileDialog(UIText.of("ponderer.ui.show_structure.browse"), defaultPath, filters, "NBT files (*.nbt)", false);
            } finally {
               stack.pop();
            }
         } catch (Exception e) {
            return null;
         }
      }).thenAcceptAsync(result -> {
         if (result != null) {
            Path selected = Path.of(result);
            if (selected.startsWith(structuresDir)) {
               Path relative = structuresDir.relativize(selected);
               String refPath = relative.toString().replace('\\', '/');
               if (refPath.toLowerCase().endsWith(".nbt")) {
                  refPath = refPath.substring(0, refPath.length() - 4);
               }

               this.structureField.setValue("ponderer:" + refPath);
            } else {
               String fileName = selected.getFileName().toString();
               if (fileName.toLowerCase().endsWith(".nbt")) {
                  fileName = fileName.substring(0, fileName.length() - 4);
               }

               fileName = SafePaths.sanitizeWindowsFileName(fileName, "structure");
               Path target = SafePaths.resolveFileName(structuresDir, fileName + ".nbt");
               if (target == null) {
                  this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.copy_failed"));
                  return;
               }

               try {
                  Files.createDirectories(target.getParent());
                  Files.copy(selected, target, StandardCopyOption.REPLACE_EXISTING);
                  this.structureField.setValue("ponderer:" + fileName);
               } catch (Exception e) {
                  this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.copy_failed"));
               }
            }
         }
      }, Minecraft.getInstance());
   }

   @Override
   protected String getStepType() {
      return "show_structure";
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      if (this.waitingDownload) {
         this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.wait_download"));
         return null;
      }

      this.clearStatusMessages();
      DslScene.DslStep s = new DslScene.DslStep();
      s.type = "show_structure";
      String sv = this.scaleField.getValue().trim();
      if (!sv.isEmpty()) {
         Float sc = this.parseFloat(sv, "Scale");
         if (sc == null) {
            return null;
         }

         s.scale = sc;
      }

      String rv = this.rotationField.getValue().trim();
      if (!rv.isEmpty()) {
         Float rotation = this.parseFloat(rv, "Rotation");
         if (rotation == null) {
            return null;
         }

         s.rotation = rotation;
      }

      Integer px = this.parseOptionalInt(this.posField.x(), "From X");
      Integer py = this.parseOptionalInt(this.posField.y(), "From Y");
      Integer pz = this.parseOptionalInt(this.posField.z(), "From Z");
      boolean hasPos1 = px != null || py != null || pz != null;
      if (!hasPos1 || px != null && py != null && pz != null) {
         String pos2X = this.pos2Field.x().trim();
         String pos2Y = this.pos2Field.y().trim();
         String pos2Z = this.pos2Field.z().trim();
         boolean hasPos2 = !pos2X.isEmpty() || !pos2Y.isEmpty() || !pos2Z.isEmpty();
         Integer px2 = null;
         Integer py2 = null;
         Integer pz2 = null;
         if (hasPos2) {
            if (pos2X.isEmpty() || pos2Y.isEmpty() || pos2Z.isEmpty()) {
               this.setErrorMessage(UIText.of("ponderer.ui.show_structure.error.partial_to"));
               return null;
            }

            px2 = this.parseInt(pos2X, "To X");
            py2 = this.parseInt(pos2Y, "To Y");
            pz2 = this.parseInt(pos2Z, "To Z");
            if (px2 == null || py2 == null || pz2 == null) {
               return null;
            }
         }

         if (hasPos2 && !hasPos1) {
            this.setErrorMessage(UIText.of("ponderer.ui.show_structure.error.partial_from"));
            return null;
         }

         if (hasPos1) {
            s.blockPos = List.of(px, py, pz);
         }

         if (hasPos2) {
            s.blockPos2 = List.of(px2, py2, pz2);
         }

         String structure = this.structureField.getValue().trim();
         if (!structure.isEmpty()) {
            if (this.isNumeric(structure)) {
               this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.no_index"));
               return null;
            }

            if (structure.toLowerCase().startsWith("minecraft:") || structure.toLowerCase().startsWith("ponderer:")) {
               ResourceLocation source = ResourceLocation.tryParse(structure);
               if (source == null) {
                  this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.invalid_id"));
                  return null;
               }

               ResourceLocation target = source.getNamespace().equals("ponderer")
                  ? source
                  : ResourceLocation.fromNamespaceAndPath("ponderer", source.getPath());
               if (source.getNamespace().equals("ponderer") && this.localStructureExists(target)) {
                  s.structure = target.toString();
                  return s;
               }

               if (source.getNamespace().equals("ponderer") && SceneStore.ensureBuiltinStructure(target.getPath())) {
                  s.structure = target.toString();
                  return s;
               }

               s.structure = target.toString();
               this.pendingStep = s;
               this.waitingDownload = true;
               this.waitingSourceId = source.toString();
               if (this.confirmButton != null) {
                  this.confirmButton.active = false;
               }

               if (this.structureField.widget() != null) {
                  this.structureField.widget().setEditable(false);
               }

               PondererClientCommands.requestStructureDownload(source);
               this.setErrorMessage(UIText.of("ponderer.ui.show_structure.structure.error.wait_download"));
               return null;
            }

            s.structure = structure;
         }

         return s;
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.show_structure.error.partial_from"));
         return null;
      }
   }

   @Nullable
   private Integer parseOptionalInt(String raw, String label) {
      String trimmed = raw == null ? "" : raw.trim();
      return trimmed.isEmpty() ? null : this.parseInt(trimmed, label);
   }

   public static void onDownloadResult(String sourceId, String targetId, boolean success, String message) {
      if (Minecraft.getInstance().screen instanceof ShowStructureScreen screen) {
         screen.handleDownloadResult(sourceId, success, message);
      }
   }

   private void handleDownloadResult(String sourceId, boolean success, String message) {
      if (this.waitingDownload) {
         if (this.waitingSourceId == null || sourceId == null || this.waitingSourceId.equals(sourceId)) {
            this.waitingDownload = false;
            this.waitingSourceId = null;
            if (this.confirmButton != null) {
               this.confirmButton.active = true;
            }

            if (this.structureField.widget() != null) {
               this.structureField.widget().setEditable(true);
            }

            if (success && this.pendingStep != null) {
               DslScene.DslStep stepToSave = this.pendingStep;
               this.pendingStep = null;
               if (this.attachKeyFrame) {
                  stepToSave.attachKeyFrame = true;
               }

               if (this.isEditMode()) {
                  this.parent.replaceStepAndSave(this.editIndex, stepToSave);
               } else {
                  this.parent.addStepAndSave(stepToSave);
               }

               Minecraft.getInstance().setScreen(this.parent);
            } else {
               this.pendingStep = null;
               this.setErrorMessage(
                  message != null && !message.isBlank() ? message : UIText.of("ponderer.ui.show_structure.structure.error.not_found", sourceId)
               );
            }
         }
      }
   }

   private boolean isNumeric(String value) {
      for (int i = 0; i < value.length(); i++) {
         if (!Character.isDigit(value.charAt(i))) {
            return false;
         }
      }

      return !value.isEmpty();
   }

   private boolean localStructureExists(ResourceLocation id) {
      Path path = this.resolveLocalStructurePath(id);
      return path != null && Files.exists(path);
   }

   private Path resolveLocalStructurePath(ResourceLocation id) {
      return SafePaths.resolveNamespacedPath(SceneStore.getStructureDir(), id, "ponderer", ".nbt");
   }
}
