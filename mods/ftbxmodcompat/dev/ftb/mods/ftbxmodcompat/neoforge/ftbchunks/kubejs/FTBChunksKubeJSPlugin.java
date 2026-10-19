package dev.ftb.mods.ftbxmodcompat.neoforge.ftbchunks.kubejs;

import dev.architectury.event.CompoundEventResult;
import dev.ftb.mods.ftbchunks.api.ClaimResult;
import dev.ftb.mods.ftbchunks.api.ClaimedChunk;
import dev.ftb.mods.ftbchunks.api.ClaimResult.StandardProblem;
import dev.ftb.mods.ftbchunks.api.event.ClaimedChunkEvent;
import dev.ftb.mods.ftbchunks.api.event.ClaimedChunkEvent.After;
import dev.ftb.mods.ftbchunks.api.event.ClaimedChunkEvent.Before;
import dev.ftb.mods.ftbxmodcompat.FTBXModCompat;
import dev.ftb.mods.ftbxmodcompat.kubejs.KJSUtil;
import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import net.minecraft.commands.CommandSourceStack;

public class FTBChunksKubeJSPlugin implements KubeJSPlugin {
   public void init() {
      ClaimedChunkEvent.BEFORE_CLAIM.register((Before)(source, chunk) -> this.before(source, chunk, "claim"));
      ClaimedChunkEvent.BEFORE_LOAD.register((Before)(source, chunk) -> this.before(source, chunk, "load"));
      ClaimedChunkEvent.BEFORE_UNCLAIM.register((Before)(source, chunk) -> this.before(source, chunk, "unclaim"));
      ClaimedChunkEvent.BEFORE_UNLOAD.register((Before)(source, chunk) -> this.before(source, chunk, "unload"));
      ClaimedChunkEvent.AFTER_CLAIM.register((After)(source, chunk) -> this.after(source, chunk, "claim"));
      ClaimedChunkEvent.AFTER_LOAD.register((After)(source, chunk) -> this.after(source, chunk, "load"));
      ClaimedChunkEvent.AFTER_UNCLAIM.register((After)(source, chunk) -> this.after(source, chunk, "unclaim"));
      ClaimedChunkEvent.AFTER_UNLOAD.register((After)(source, chunk) -> this.after(source, chunk, "unload"));
      FTBXModCompat.LOGGER.info("[FTB Chunks] Enabled KubeJS integration");
   }

   public void registerEvents(EventGroupRegistry registry) {
      registry.register(FTBChunksKubeJSEvents.EVENT_GROUP);
   }

   public void registerTypeWrappers(TypeWrapperRegistry registry) {
      registry.register(ClaimResult.class, (cx, from, target) -> StandardProblem.valueOf(from.toString().toUpperCase()));
   }

   private CompoundEventResult<ClaimResult> before(CommandSourceStack source, ClaimedChunk chunk, String id) {
      BeforeEventJS event = new BeforeEventJS(source, chunk);
      EventResult result = FTBChunksKubeJSEvents.BEFORE.post(ScriptType.SERVER, id, event);
      return KJSUtil.asCompoundArchResult(result, event.getResult());
   }

   private void after(CommandSourceStack source, ClaimedChunk chunk, String id) {
      FTBChunksKubeJSEvents.AFTER.post(ScriptType.SERVER, id, new AfterEventJS(source, chunk));
   }
}
