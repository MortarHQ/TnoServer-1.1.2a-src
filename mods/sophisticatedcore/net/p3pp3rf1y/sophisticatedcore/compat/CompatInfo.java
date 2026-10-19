package net.p3pp3rf1y.sophisticatedcore.compat;

import javax.annotation.Nullable;
import net.neoforged.fml.ModList;
import org.apache.maven.artifact.versioning.VersionRange;

public record CompatInfo(String modId, @Nullable VersionRange supportedVersionRange) {
   public CompatInfo(String modId) {
      this(modId, null);
   }

   public boolean isLoaded() {
      return ModList.get()
         .getModContainerById(this.modId())
         .map(container -> this.supportedVersionRange() == null || this.supportedVersionRange().containsVersion(container.getModInfo().getVersion()))
         .orElse(false);
   }
}
