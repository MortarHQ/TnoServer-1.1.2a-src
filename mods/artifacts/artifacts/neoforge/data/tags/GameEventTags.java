package artifacts.neoforge.data.tags;

import artifacts.registry.ModGameEvents;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.GameEventTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class GameEventTags extends GameEventTagsProvider {
   public GameEventTags(PackOutput arg, CompletableFuture<Provider> completableFuture, ExistingFileHelper existingFileHelper) {
      super(arg, completableFuture, "artifacts", existingFileHelper);
   }

   protected void addTags(Provider arg) {
      this.tag(net.minecraft.tags.GameEventTags.VIBRATIONS)
         .addAll(ModGameEvents.GAME_EVENTS.getEntries().stream().map(holder -> (ResourceKey)holder.unwrapKey().orElseThrow()).toList());
      this.tag(net.minecraft.tags.GameEventTags.WARDEN_CAN_LISTEN)
         .addAll(ModGameEvents.GAME_EVENTS.getEntries().stream().map(holder -> (ResourceKey)holder.unwrapKey().orElseThrow()).toList());
   }
}
