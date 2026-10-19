package org.crypticdev.stextras.config;

import io.github.manasmods.manascore.config.api.Comment;
import io.github.manasmods.manascore.config.api.ManasConfig;
import io.github.manasmods.manascore.config.api.ManasSubConfig;
import java.util.ArrayList;
import java.util.List;

public class TensuraConfigPatcherStateConfig extends ManasConfig {
   public TensuraConfigPatcherStateConfig.EntryState startingRaces = new TensuraConfigPatcherStateConfig.EntryState();
   public TensuraConfigPatcherStateConfig.EntryState randomRaces = new TensuraConfigPatcherStateConfig.EntryState();
   public TensuraConfigPatcherStateConfig.EntryState startingSkills = new TensuraConfigPatcherStateConfig.EntryState();
   public TensuraConfigPatcherStateConfig.EntryState creatorUniqueSkills = new TensuraConfigPatcherStateConfig.EntryState();

   public String getFileName() {
      return "stextras/internal/tensura_config_patcher_state";
   }

   public static class EntryState extends ManasSubConfig {
      @Comment("Values STExtras has already injected into Tensura at least once.")
      public List<String> applied = new ArrayList<>();
      @Comment("Values that were auto-added once, then manually removed from Tensura's config and should stay out until manually re-added.")
      public List<String> optOut = new ArrayList<>();
   }
}
