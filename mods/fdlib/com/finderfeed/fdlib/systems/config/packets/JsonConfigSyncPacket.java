package com.finderfeed.fdlib.systems.config.packets;

import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.config.JsonConfig;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:update_all_configs")
public class JsonConfigSyncPacket extends FDPacket {
   private List<String> names;
   private List<String> configs;

   public JsonConfigSyncPacket() {
      this.names = new ArrayList<>();
      this.configs = new ArrayList<>();

      for (JsonConfig entry : FDRegistries.CONFIGS) {
         if (!entry.isClientside()) {
            this.names.add(FDRegistries.CONFIGS.getKey(entry).toString());
            this.configs.add(entry.getLoadedJson());
         }
      }
   }

   public JsonConfigSyncPacket(FriendlyByteBuf buf) {
      int namesLen = buf.readInt();
      this.names = new ArrayList<>();

      for (int i = 0; i < namesLen; i++) {
         this.names.add(buf.readUtf());
      }

      this.configs = new ArrayList<>();

      for (int i = 0; i < namesLen; i++) {
         this.configs.add(buf.readUtf());
      }
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.names.size());

      for (String name : this.names) {
         buf.writeUtf(name);
      }

      for (String sconfig : this.configs) {
         buf.writeUtf(sconfig);
      }
   }

   @Override
   public void clientAction(IPayloadContext context) {
      for (int i = 0; i < this.names.size(); i++) {
         JsonConfig config = (JsonConfig)FDRegistries.CONFIGS.get(ResourceLocation.parse(this.names.get(i)));
         if (!config.isClientside()) {
            JsonObject object = JsonParser.parseString(this.configs.get(i)).getAsJsonObject();
            config.setLoadedJson(object);
            config.parseJson(object);
         }
      }
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
