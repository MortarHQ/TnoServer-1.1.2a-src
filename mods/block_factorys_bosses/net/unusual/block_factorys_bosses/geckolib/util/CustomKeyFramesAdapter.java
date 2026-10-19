package net.unusual.block_factorys_bosses.geckolib.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.util.GsonHelper;
import software.bernie.geckolib.animation.Animation.Keyframes;
import software.bernie.geckolib.animation.keyframe.event.data.CustomInstructionKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.ParticleKeyframeData;
import software.bernie.geckolib.animation.keyframe.event.data.SoundKeyframeData;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.loading.json.raw.Cube;
import software.bernie.geckolib.loading.json.raw.FaceUV;
import software.bernie.geckolib.loading.json.raw.LocatorClass;
import software.bernie.geckolib.loading.json.raw.LocatorValue;
import software.bernie.geckolib.loading.json.raw.MinecraftGeometry;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.raw.ModelProperties;
import software.bernie.geckolib.loading.json.raw.PolyMesh;
import software.bernie.geckolib.loading.json.raw.PolysUnion;
import software.bernie.geckolib.loading.json.raw.TextureMesh;
import software.bernie.geckolib.loading.json.raw.UVFaces;
import software.bernie.geckolib.loading.json.raw.UVUnion;
import software.bernie.geckolib.loading.json.typeadapter.BakedAnimationsAdapter;
import software.bernie.geckolib.loading.object.BakedAnimations;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CustomKeyFramesAdapter implements JsonDeserializer<Keyframes> {
   public static final Gson GEO_GSON = new GsonBuilder()
      .setLenient()
      .registerTypeAdapter(Bone.class, Bone.deserializer())
      .registerTypeAdapter(Cube.class, Cube.deserializer())
      .registerTypeAdapter(FaceUV.class, FaceUV.deserializer())
      .registerTypeAdapter(LocatorClass.class, LocatorClass.deserializer())
      .registerTypeAdapter(LocatorValue.class, LocatorValue.deserializer())
      .registerTypeAdapter(MinecraftGeometry.class, MinecraftGeometry.deserializer())
      .registerTypeAdapter(Model.class, Model.deserializer())
      .registerTypeAdapter(ModelProperties.class, ModelProperties.deserializer())
      .registerTypeAdapter(PolyMesh.class, PolyMesh.deserializer())
      .registerTypeAdapter(PolysUnion.class, PolysUnion.deserializer())
      .registerTypeAdapter(TextureMesh.class, TextureMesh.deserializer())
      .registerTypeAdapter(UVFaces.class, UVFaces.deserializer())
      .registerTypeAdapter(UVUnion.class, UVUnion.deserializer())
      .registerTypeAdapter(Keyframes.class, new CustomKeyFramesAdapter())
      .registerTypeAdapter(BakedAnimations.class, new BakedAnimationsAdapter())
      .create();

   public Keyframes deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
      JsonObject obj = json.getAsJsonObject();
      SoundKeyframeData[] sounds = buildSoundFrameData(obj);
      ParticleKeyframeData[] particles = buildParticleFrameData(obj);
      CustomInstructionKeyframeData[] customInstructions = buildCustomFrameData(obj);
      return new Keyframes(sounds, particles, customInstructions);
   }

   private static SoundKeyframeData[] buildSoundFrameData(JsonObject rootObj) {
      JsonObject soundsObj = GsonHelper.getAsJsonObject(rootObj, "sound_effects", new JsonObject());
      SoundKeyframeData[] sounds = new SoundKeyframeData[soundsObj.size()];
      int index = 0;

      for (Entry<String, JsonElement> entry : soundsObj.entrySet()) {
         sounds[index] = new SoundKeyframeData(Double.parseDouble(entry.getKey()) * 20.0, GsonHelper.getAsString(entry.getValue().getAsJsonObject(), "effect"));
         index++;
      }

      return sounds;
   }

   private static ParticleKeyframeData[] buildParticleFrameData(JsonObject rootObj) {
      JsonObject particlesObj = GsonHelper.getAsJsonObject(rootObj, "particle_effects", new JsonObject());
      List<ParticleKeyframeData> list = new ArrayList<>();

      for (Entry<String, JsonElement> entry : particlesObj.entrySet()) {
         double startTick = Double.parseDouble(entry.getKey()) * 20.0;
         if (entry.getValue() instanceof JsonObject object) {
            String effect = GsonHelper.getAsString(object, "effect", "");
            String locator = GsonHelper.getAsString(object, "locator", "");
            String script = GsonHelper.getAsString(object, "pre_effect_script", "");
            list.add(new ParticleKeyframeData(startTick, effect, locator, script));
         } else {
            Object var15 = entry.getValue();
            if (var15 instanceof JsonArray) {
               for (JsonElement element : (JsonArray)var15) {
                  if (element instanceof JsonObject object) {
                     String effect = GsonHelper.getAsString(object, "effect", "");
                     String locator = GsonHelper.getAsString(object, "locator", "");
                     String script = GsonHelper.getAsString(object, "pre_effect_script", "");
                     list.add(new ParticleKeyframeData(startTick, effect, locator, script));
                  }
               }
            }
         }
      }

      return list.toArray(new ParticleKeyframeData[0]);
   }

   private static CustomInstructionKeyframeData[] buildCustomFrameData(JsonObject rootObj) {
      JsonObject customInstructionsObj = GsonHelper.getAsJsonObject(rootObj, "timeline", new JsonObject());
      CustomInstructionKeyframeData[] customInstructions = new CustomInstructionKeyframeData[customInstructionsObj.size()];
      int index = 0;

      for (Entry<String, JsonElement> entry : customInstructionsObj.entrySet()) {
         String instructions = "";
         if (entry.getValue() instanceof JsonArray array) {
            instructions = ((ObjectArrayList)GEO_GSON.fromJson(array, ObjectArrayList.class)).toString();
         } else if (entry.getValue() instanceof JsonPrimitive primitive) {
            instructions = primitive.getAsString();
         }

         customInstructions[index] = new CustomInstructionKeyframeData(Double.parseDouble(entry.getKey()) * 20.0, instructions);
         index++;
      }

      return customInstructions;
   }
}
