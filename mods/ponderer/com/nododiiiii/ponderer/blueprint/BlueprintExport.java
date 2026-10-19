package com.nododiiiii.ponderer.blueprint;

import com.mojang.logging.LogUtils;
import com.nododiiiii.ponderer.util.SafePaths;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.AccessDeniedException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class BlueprintExport {
   private static final Logger LOGGER = LogUtils.getLogger();
   private static final String SAVE_ERROR_KEY_PREFIX = "ponderer.ui.save_error.";

   public static BlueprintExport.SaveResult saveBlueprint(Path dir, String fileName, boolean overwrite, Level level, BlockPos first, BlockPos second) {
      BoundingBox bb = BoundingBox.fromCorners(first, second);
      BlockPos origin = new BlockPos(bb.minX(), bb.minY(), bb.minZ());
      BlockPos bounds = new BlockPos(bb.getXSpan(), bb.getYSpan(), bb.getZSpan());
      StructureTemplate structure = new StructureTemplate();
      structure.fillFromWorld(level, origin, bounds, true, Blocks.AIR);
      CompoundTag data = structure.save(new CompoundTag());
      BlueprintItem.replaceStructureVoidWithAir(data);
      String baseName = fileName == null ? "" : fileName.trim();
      if (baseName.toLowerCase(Locale.ROOT).endsWith(".nbt")) {
         baseName = baseName.substring(0, baseName.length() - 4);
      }

      if (baseName.isEmpty()) {
         baseName = "blueprint";
      }

      SafePaths.FileNameValidationError baseNameError = SafePaths.diagnosePortableAssetName(baseName);
      if (baseNameError != null) {
         BlueprintExport.SaveResult failure = toFileNameFailure(baseName, baseNameError);
         LOGGER.warn(failure.englishMessage());
         return failure;
      }

      if (!overwrite) {
         baseName = findFirstValidFilename(baseName, dir, "nbt");
         if (baseName == null) {
            BlueprintExport.SaveResult failure = BlueprintExport.SaveResult.failure(
               "Cannot save blueprint because no safe output filename could be generated", "ponderer.ui.save_error.file_name_invalid"
            );
            LOGGER.warn(failure.englishMessage());
            return failure;
         }
      }

      fileName = baseName + ".nbt";
      Path file = SafePaths.resolveFileName(dir, fileName);
      if (file == null) {
         BlueprintExport.SaveResult failure = BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because output path '" + fileName + "' could not be resolved safely", "ponderer.ui.save_error.file_name_invalid"
         );
         LOGGER.warn(failure.englishMessage());
         return failure;
      }

      try {
         Files.createDirectories(dir);
         boolean overwritten = Files.deleteIfExists(file);

         try (OutputStream out = Files.newOutputStream(file, StandardOpenOption.CREATE)) {
            NbtIo.writeCompressed(data, out);
         }

         BlueprintExport.ExportResult exportResult = new BlueprintExport.ExportResult(file, dir, fileName, overwritten, origin, bounds);
         return BlueprintExport.SaveResult.success(exportResult, "Saved blueprint to " + file);
      } catch (IOException e) {
         BlueprintExport.SaveResult failure = mapIoFailure(file, e);
         LOGGER.error(failure.englishMessage(), e);
         return failure;
      }
   }

   @Nullable
   public static String findFirstValidFilename(String name, Path folder, String extension) {
      int index = 0;

      String filename;
      Path filepath;
      do {
         filename = index == 0 ? name + "." + extension : name + "_" + index + "." + extension;
         filepath = SafePaths.resolveFileName(folder, filename);
         if (filepath == null) {
            return null;
         }

         index++;
      } while (Files.exists(filepath));

      return filename.substring(0, filename.length() - extension.length() - 1);
   }

   private static BlueprintExport.SaveResult toFileNameFailure(String fileName, SafePaths.FileNameValidationError error) {
      return switch (error.code()) {
         case INVALID_CHARACTER -> BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because filename '" + fileName + "' contains invalid character '" + error.offendingText() + "'",
            "ponderer.ui.save_error.file_name_invalid_char",
            error.offendingText()
         );
         case RESERVED_NAME -> BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because filename '" + fileName + "' is a reserved Windows name", "ponderer.ui.save_error.file_name_reserved", fileName
         );
         case TRAILING_SPACE_OR_DOT -> BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because filename '" + fileName + "' ends with a space or dot", "ponderer.ui.save_error.file_name_trailing"
         );
         case EMPTY, DOT_SEGMENT -> BlueprintExport.SaveResult.failure(
            "Cannot save blueprint because filename '" + fileName + "' is invalid", "ponderer.ui.save_error.file_name_invalid"
         );
      };
   }

   private static BlueprintExport.SaveResult mapIoFailure(Path file, IOException e) {
      if (e instanceof AccessDeniedException) {
         return BlueprintExport.SaveResult.failure(
            "Failed to save blueprint to " + file + ": access denied",
            "ponderer.ui.save_error.access_denied",
            file.getFileName() != null ? file.getFileName().toString() : file.toString()
         );
      }

      if (e instanceof NoSuchFileException) {
         return BlueprintExport.SaveResult.failure(
            "Failed to save blueprint to " + file + ": target path does not exist", "ponderer.ui.save_error.path_missing", file.toString()
         );
      }

      String detail = "I/O error";
      if (e instanceof FileSystemException fileSystemException && fileSystemException.getReason() != null && !fileSystemException.getReason().isBlank()) {
         detail = "filesystem error: " + fileSystemException.getReason();
      } else if (e.getMessage() != null && !e.getMessage().isBlank()) {
         detail = "I/O error: " + e.getMessage();
      }

      return BlueprintExport.SaveResult.failure("Failed to save blueprint to " + file + ": " + detail, "ponderer.ui.save_error.io", detail);
   }

   public record ExportResult(Path file, Path dir, String fileName, boolean overwritten, BlockPos origin, BlockPos bounds) {
   }

   public static final class SaveResult {
      private final boolean success;
      @Nullable
      private final BlueprintExport.ExportResult exportResult;
      private final String englishMessage;
      @Nullable
      private final String uiMessageKey;
      private final Object[] uiMessageArgs;

      private SaveResult(
         boolean success, @Nullable BlueprintExport.ExportResult exportResult, String englishMessage, @Nullable String uiMessageKey, Object[] uiMessageArgs
      ) {
         this.success = success;
         this.exportResult = exportResult;
         this.englishMessage = englishMessage;
         this.uiMessageKey = uiMessageKey;
         this.uiMessageArgs = uiMessageArgs == null ? new Object[0] : Arrays.copyOf(uiMessageArgs, uiMessageArgs.length);
      }

      public boolean isSuccess() {
         return this.success;
      }

      @Nullable
      public BlueprintExport.ExportResult exportResult() {
         return this.exportResult;
      }

      public String englishMessage() {
         return this.englishMessage;
      }

      @Nullable
      public String uiMessageKey() {
         return this.uiMessageKey;
      }

      public Object[] uiMessageArgs() {
         return Arrays.copyOf(this.uiMessageArgs, this.uiMessageArgs.length);
      }

      public static BlueprintExport.SaveResult success(BlueprintExport.ExportResult exportResult, String englishMessage) {
         return new BlueprintExport.SaveResult(true, exportResult, englishMessage, null, new Object[0]);
      }

      public static BlueprintExport.SaveResult failure(String englishMessage, String uiMessageKey, Object... uiMessageArgs) {
         return new BlueprintExport.SaveResult(false, null, englishMessage, uiMessageKey, uiMessageArgs);
      }
   }
}
