package fzzyhmstrs.emi_loot.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fzzyhmstrs.emi_loot.EMILoot;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.EntityType;
import org.joml.Vector3f;

public class ClientResourceData {
   public static final Object2IntMap<EntityType<?>> MOB_OFFSETS = new Object2IntOpenHashMap();
   public static final Object2FloatMap<EntityType<?>> MOB_SCALES = new Object2FloatOpenHashMap();
   public static final Map<EntityType<?>, Vector3f> MOB_ROTATIONS = new HashMap<>();

   public static class EntityOffsetsReloadListener implements ResourceManagerReloadListener {
      public void onResourceManagerReload(ResourceManager manager) {
         ClientResourceData.MOB_OFFSETS.clear();
         ClientResourceData.MOB_SCALES.clear();
         ClientResourceData.MOB_ROTATIONS.clear();
         manager.listResources("entity_fixers", path -> path.getPath().endsWith(".json")).forEach(this::load);
         if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
            EMILoot.LOGGER.info(ClientResourceData.MOB_OFFSETS.toString());
         }

         if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
            EMILoot.LOGGER.info(ClientResourceData.MOB_ROTATIONS.toString());
         }

         if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
            EMILoot.LOGGER.info(ClientResourceData.MOB_SCALES.toString());
         }
      }

      private void load(ResourceLocation fileId, Resource resource) {
         if (EMILoot.config.isDebug(EMILoot.Type.MOB)) {
            EMILoot.LOGGER.info("Reading entity fixers from file: " + fileId.toString());
         }

         try {
            BufferedReader reader = resource.openAsReader();
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            json.entrySet().forEach(entry -> {
               JsonElement element = (JsonElement)entry.getValue();
               ResourceLocation mobId = ResourceLocation.parse((String)entry.getKey());
               if (BuiltInRegistries.ENTITY_TYPE.containsKey(mobId)) {
                  if (!element.isJsonObject()) {
                     throw new IllegalArgumentException("Element in mobfixer " + fileId + "not properly formatted");
                  }

                  JsonObject object = element.getAsJsonObject();
                  JsonElement offset = object.get("offset");
                  if (offset != null && offset.isJsonPrimitive()) {
                     ClientResourceData.MOB_OFFSETS.put((EntityType)BuiltInRegistries.ENTITY_TYPE.get(mobId), offset.getAsInt());
                  }

                  JsonElement scaling = object.get("scale");
                  if (scaling != null && scaling.isJsonPrimitive()) {
                     ClientResourceData.MOB_SCALES.put((EntityType)BuiltInRegistries.ENTITY_TYPE.get(mobId), scaling.getAsFloat());
                  }

                  float x = 0.0F;
                  float y = 0.0F;
                  float z = 0.0F;
                  JsonElement xEl = object.get("x");
                  if (xEl != null && xEl.isJsonPrimitive()) {
                     x = xEl.getAsFloat();
                  }

                  JsonElement yEl = object.get("y");
                  if (yEl != null && yEl.isJsonPrimitive()) {
                     y = yEl.getAsFloat();
                  }

                  JsonElement zEl = object.get("z");
                  if (zEl != null && zEl.isJsonPrimitive()) {
                     z = zEl.getAsFloat();
                  }

                  if (x != 0.0F || y != 0.0F || z != 0.0F) {
                     ClientResourceData.MOB_ROTATIONS.put((EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(mobId), new Vector3f(x, y, z));
                  }
               }
            });
         } catch (Exception e) {
            EMILoot.LOGGER.error("Failed to open or read Entity Offsets file: " + fileId);
         }
      }
   }
}
