package com.teamresourceful.resourcefullib.common.utils.modinfo.neoforge;

import com.teamresourceful.resourcefullib.common.utils.modinfo.ModInfo;
import java.nio.file.Path;
import java.util.List;
import net.neoforged.neoforgespi.language.IModInfo;

public record NeoForgeModInfo(IModInfo info) implements ModInfo {
   @Override
   public String displayName() {
      return this.info.getDisplayName();
   }

   @Override
   public String id() {
      return this.info.getModId();
   }

   @Override
   public String version() {
      return this.info.getVersion().toString();
   }

   @Override
   public List<Path> getPaths() {
      return List.of(this.info.getOwningFile().getFile().getFilePath());
   }
}
