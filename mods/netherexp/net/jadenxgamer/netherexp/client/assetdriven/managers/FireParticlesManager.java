package net.jadenxgamer.netherexp.client.assetdriven.managers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.assetdriven.FireParticles;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

public class FireParticlesManager extends SimpleJsonResourceReloadListener {
   private static final Gson GSON = new Gson();

   public FireParticlesManager() {
      super(GSON, "netherexp/fire_particles");
   }

   protected void apply(Map<ResourceLocation, JsonElement> elements, ResourceManager manager, ProfilerFiller profiler) {
      FireParticles.FIRE_PARTICLES.clear();

      for (Entry<ResourceLocation, JsonElement> entry : elements.entrySet()) {
         try {
            JsonObject json = entry.getValue().getAsJsonObject();
            FireParticles fireParticles = FireParticles.parseSetting(json);
            if (json.has("blocks")) {
               for (ResourceLocation block : this.parseBlocksField(json)) {
                  FireParticles.FIRE_PARTICLES.put(block, fireParticles);
               }
            }
         } catch (Exception e) {
            NetherExp.LOGGER.warn("Couldn't load fire particles {}", e.getMessage());
         }
      }
   }

   private List<ResourceLocation> parseBlocksField(JsonObject json) {
      List<ResourceLocation> blocks = new ArrayList<>();
      JsonElement blocksElement = json.get("blocks");
      if (blocksElement.isJsonPrimitive()) {
         String blockId = blocksElement.getAsString();
         blocks.add(ResourceLocation.parse(blockId));
      } else if (blocksElement.isJsonArray()) {
         for (JsonElement element : blocksElement.getAsJsonArray()) {
            if (element.isJsonPrimitive()) {
               String blockId = element.getAsString();
               blocks.add(ResourceLocation.parse(blockId));
            }
         }
      }

      return blocks;
   }
}
