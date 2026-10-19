package twilightforest.world.components.structures.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import twilightforest.TwilightForestMod;

public abstract class CodecResourceReloadListener<T> extends SimpleJsonResourceReloadListener {
   protected final Gson gson;
   private final Codec<T> codec;

   public CodecResourceReloadListener(String directory, Codec<T> codec) {
      this(new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create(), directory, codec);
   }

   public CodecResourceReloadListener(Gson gson, String directory, Codec<T> codec) {
      super(gson, directory);
      this.gson = gson;
      this.codec = codec;
   }

   protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller profiler) {
      List<Entry<ResourceLocation, JsonElement>> nonTwilight = new ArrayList<>();

      for (Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
         ResourceLocation location = entry.getKey();
         if (!location.getPath().contains("entries")) {
            if ("twilightforest".equals(location.getNamespace())) {
               JsonElement jsonElement = entry.getValue();
               this.deserialize(manager, this.initDynamicOps(), location, jsonElement);
            } else {
               nonTwilight.add(entry);
            }
         }
      }

      for (Entry<ResourceLocation, JsonElement> entry : nonTwilight) {
         ResourceLocation location = entry.getKey();
         JsonElement jsonElement = entry.getValue();
         this.deserialize(manager, this.initDynamicOps(), location, jsonElement);
      }
   }

   protected void deserialize(ResourceManager manager, DynamicOps<JsonElement> ops, ResourceLocation location, JsonElement jsonElement) {
      try {
         Optional<T> checkFile = this.codec.parse(ops, jsonElement).result();
         if (checkFile.isPresent()) {
            this.forLocation(manager, location, checkFile.get());
         } else {
            TwilightForestMod.LOGGER.error("Listener '{}' failed to load resource {}", this.getName(), location);
         }
      } catch (Exception e) {
         TwilightForestMod.LOGGER.error("Listener '{}' couldn't read element {}", this.getName(), location, e);
      }
   }

   protected DynamicOps<JsonElement> initDynamicOps() {
      return JsonOps.INSTANCE;
   }

   protected abstract void forLocation(ResourceManager var1, ResourceLocation var2, T var3);

   public void registerListener(AddReloadListenerEvent event) {
      event.addListener(this);
   }
}
