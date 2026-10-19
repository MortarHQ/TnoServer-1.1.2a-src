package com.iafenvoy.uranus.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.iafenvoy.uranus.client.model.tabula.TabulaModelContainer;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.ItemTransform.Deserializer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum TabulaModelHandler implements JsonDeserializationContext {
   INSTANCE;

   private final Gson gson = new GsonBuilder()
      .registerTypeAdapter(ItemTransform.class, new Deserializer())
      .registerTypeAdapter(ItemTransforms.class, new net.minecraft.client.renderer.block.model.ItemTransforms.Deserializer())
      .create();

   public TabulaModelContainer loadTabulaModel(InputStream stream) {
      return (TabulaModelContainer)this.gson.fromJson(new InputStreamReader(stream), TabulaModelContainer.class);
   }

   public <T> T deserialize(JsonElement json, Type type) throws JsonParseException {
      return (T)this.gson.fromJson(json, type);
   }
}
