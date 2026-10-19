package com.tensura_guild.dialogue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

public class GuildDialogueManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new GsonBuilder().create();
   private static final Logger LOGGER = LogUtils.getLogger();
   public static final String DIRECTORY = "dialogue";
   private static Map<ResourceLocation, GuildDialogue> DIALOGUES = Map.of();

   public GuildDialogueManager() {
      super(GSON, "dialogue");
   }

   protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
      Map<ResourceLocation, GuildDialogue> map = new HashMap<>();
      object.forEach(
         (id, json) -> GuildDialogue.CODEC
            .parse(JsonOps.INSTANCE, json)
            .resultOrPartial(error -> LOGGER.error("Failed to parse guild dialogue {}: {}", id, error))
            .ifPresent(dialogue -> map.put(id, dialogue))
      );
      DIALOGUES = Map.copyOf(map);
      LOGGER.info("Loaded {} guild dialogue(s)", DIALOGUES.size());
   }

   public static GuildDialogue get(ResourceLocation id) {
      return DIALOGUES.get(id);
   }
}
