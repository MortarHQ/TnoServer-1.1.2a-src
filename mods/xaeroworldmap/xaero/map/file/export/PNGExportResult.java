package xaero.map.file.export;

import java.nio.file.Path;
import net.minecraft.network.chat.Component;

public class PNGExportResult {
   private final PNGExportResultType type;
   private final Path folderToOpen;

   public PNGExportResult(PNGExportResultType type, Path folderToOpen) {
      this.type = type;
      this.folderToOpen = folderToOpen;
   }

   public PNGExportResultType getType() {
      return this.type;
   }

   public Path getFolderToOpen() {
      return this.folderToOpen;
   }

   public Component getMessage() {
      return this.type.getMessage();
   }
}
