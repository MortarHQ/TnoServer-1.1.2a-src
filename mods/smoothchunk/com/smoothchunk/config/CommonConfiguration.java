package com.smoothchunk.config;

import com.cupboard.config.ICommonConfig;
import com.google.gson.JsonObject;

public class CommonConfiguration implements ICommonConfig {
   public int chunkSaveDelay = 300;
   public int chunkUnloadLimit = 20;
   public boolean debugLogging = false;
   public boolean disableProtoSave = true;

   public JsonObject serialize() {
      JsonObject root = new JsonObject();
      JsonObject entry = new JsonObject();
      entry.addProperty("desc:", "Delay before a chunk is saved to disk, default: 300 seconds");
      entry.addProperty("chunkSaveDelay", this.chunkSaveDelay);
      root.add("chunkSaveDelay", entry);
      JsonObject entry2 = new JsonObject();
      entry2.addProperty("desc:", "Maximum amount of chunks unloaded per tick, default: 20, vanilla:200");
      entry2.addProperty("chunkUnloadLimit", this.chunkUnloadLimit);
      root.add("chunkUnloadLimit", entry2);
      JsonObject entry4 = new JsonObject();
      entry4.addProperty("desc:", "Disables saving of protochunks(not fully generated chunks) to reduce saving lag. Default: true");
      entry4.addProperty("disableProtoSave", this.disableProtoSave);
      root.add("disableProtoSave", entry4);
      JsonObject entry3 = new JsonObject();
      entry3.addProperty("desc:", "Enables debug logging of how many chunks got saved in a tick. default: false");
      entry3.addProperty("debugLogging", this.debugLogging);
      root.add("debugLogging", entry3);
      return root;
   }

   public void deserialize(JsonObject data) {
      this.chunkSaveDelay = data.get("chunkSaveDelay").getAsJsonObject().get("chunkSaveDelay").getAsInt();
      this.debugLogging = data.get("debugLogging").getAsJsonObject().get("debugLogging").getAsBoolean();
      this.chunkUnloadLimit = data.get("chunkUnloadLimit").getAsJsonObject().get("chunkUnloadLimit").getAsInt();
      this.disableProtoSave = data.get("disableProtoSave").getAsJsonObject().get("disableProtoSave").getAsBoolean();
   }
}
