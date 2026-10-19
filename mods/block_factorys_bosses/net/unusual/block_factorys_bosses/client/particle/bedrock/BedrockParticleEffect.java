package net.unusual.block_factorys_bosses.client.particle.bedrock;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.client.particle.BRParticleRenderType;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleAppearanceBillboard;
import net.unusual.block_factorys_bosses.client.particle.bedrock.appearance.ParticleAppearanceTinting;
import net.unusual.block_factorys_bosses.client.particle.bedrock.curve.ParticleCurve;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.EmitterLifetimeEvents;
import net.unusual.block_factorys_bosses.client.particle.bedrock.event.ParticleEvent;
import net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime.EmitterLifetime;
import net.unusual.block_factorys_bosses.client.particle.bedrock.lifetime.ParticleLifetimeExpression;
import net.unusual.block_factorys_bosses.client.particle.bedrock.motion.ParticleMotion;
import net.unusual.block_factorys_bosses.client.particle.bedrock.motion.ParticleMotionCollision;
import net.unusual.block_factorys_bosses.client.particle.bedrock.rate.EmitterRate;
import net.unusual.block_factorys_bosses.client.particle.bedrock.shape.EmitterShape;
import net.unusual.block_factorys_bosses.client.particle.bedrock.simple.ParticleInitialSpeed;
import net.unusual.block_factorys_bosses.client.particle.bedrock.simple.ParticleInitialSpin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.loading.math.MathParser;
import software.bernie.geckolib.loading.math.MathValue;
import software.bernie.geckolib.loading.math.value.Constant;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public record BedrockParticleEffect(
   ResourceLocation textureLocation,
   ParticleRenderType particleRenderType,
   Map<String, ParticleCurve> curves,
   Map<String, ParticleEvent> events,
   EmitterLifetimeEvents lifetimeEvents,
   EmitterLifetime lifetime,
   EmitterRate rate,
   EmitterShape shape,
   ParticleInitialSpeed particleInitialSpeed,
   ParticleInitialSpin particleInitialSpin,
   ParticleLifetimeExpression particleLifetime,
   @Nullable ParticleMotion particleMotion,
   @Nullable ParticleMotionCollision particleMotionCollision,
   ParticleAppearanceBillboard particleAppearance,
   ParticleAppearanceTinting particleAppearanceTinting
) {
   public static MathValue parseMolangJson(@Nullable JsonElement jsonElement, double defaultValue) {
      return (MathValue)(jsonElement == null ? new Constant(defaultValue) : MathParser.parseJson(jsonElement));
   }

   @Nullable
   public static String getOneComponentOf(JsonObject component, Set<String> validKeys) {
      String foundKey = null;

      for (String key : component.keySet()) {
         if (validKeys.contains(key)) {
            if (foundKey != null) {
               throw new IllegalArgumentException("Found conflicting components: " + foundKey + " and " + key);
            }

            foundKey = key;
         }
      }

      return foundKey;
   }

   public static BedrockParticleEffect deserialize(Reader reader) {
      return (BedrockParticleEffect)BedrockParticleEffect.BedrockParticleEffectDeserializer.INSTANCE.fromJson(reader, BedrockParticleEffect.class);
   }

   public static class BedrockParticleEffectDeserializer implements JsonDeserializer<BedrockParticleEffect> {
      private static final Logger LOGGER = LogManager.getLogger(BedrockParticleEffect.BedrockParticleEffectDeserializer.class);
      public static final Gson INSTANCE = new GsonBuilder()
         .registerTypeAdapter(BedrockParticleEffect.class, new BedrockParticleEffect.BedrockParticleEffectDeserializer())
         .create();

      public BedrockParticleEffect deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
         JsonObject json = jsonElement.getAsJsonObject();
         String formatVersion = json.get("format_version").getAsString();
         if (!formatVersion.equals("1.10.0")) {
            LOGGER.warn("Unknown bedrock particle format version: {}", formatVersion);
         }

         JsonObject particleEffect = json.getAsJsonObject("particle_effect");
         JsonObject particleDescription = particleEffect.getAsJsonObject("description");
         JsonObject basicRenderParameters = particleDescription.getAsJsonObject("basic_render_parameters");
         JsonObject components = particleEffect.getAsJsonObject("components");
         return new BedrockParticleEffect(
            ResourceLocation.parse(basicRenderParameters.get("texture").getAsString()),
            this.getRenderTypeFromMaterial(basicRenderParameters.get("material").getAsString()),
            particleEffect.has("curves") ? this.parseCurves(particleEffect.getAsJsonObject("curves")) : Map.of(),
            particleEffect.has("events") ? this.parseEvents(particleEffect.getAsJsonObject("events")) : Map.of(),
            EmitterLifetimeEvents.deserializeFromComponents(components),
            EmitterLifetime.deserialize(components),
            EmitterRate.deserialize(components),
            EmitterShape.deserialize(components),
            ParticleInitialSpeed.deserializeFromComponents(components),
            ParticleInitialSpin.deserializeFromComponents(components),
            ParticleLifetimeExpression.deserializeFromComponents(components),
            ParticleMotion.deserialize(components),
            ParticleMotionCollision.deserializeFromComponents(components),
            ParticleAppearanceBillboard.deserializeFromComponents(components),
            ParticleAppearanceTinting.deserializeFromComponents(components)
         );
      }

      private ParticleRenderType getRenderTypeFromMaterial(String materialName) {
         return switch (materialName) {
            case "particles_add" -> ParticleRenderType.PARTICLE_SHEET_LIT;
            case "particles_alpha" -> ParticleRenderType.PARTICLE_SHEET_OPAQUE;
            case "particles_blend" -> BRParticleRenderType.PARTICLE_SHEET_BLEND;
            case "particles_blend_no_cull" -> BRParticleRenderType.PARTICLE_SHEET_BLEND_NO_CULL;
            default -> throw new IllegalArgumentException("Unknown basic_render_parameters.material: " + materialName);
         };
      }

      private Map<String, ParticleCurve> parseCurves(JsonObject curves) {
         HashMap<String, ParticleCurve> output = new HashMap<>();

         for (Entry<String, JsonElement> entry : curves.entrySet()) {
            String name = entry.getKey();
            if (!name.startsWith("variable.")) {
               throw new IllegalArgumentException("Name of curve " + name + " must start with \"variable.\".");
            }

            try {
               ParticleCurve curve = ParticleCurve.deserialize(entry.getValue().getAsJsonObject());
               output.put(name.substring("variable.".length()), curve);
            } catch (Exception ex) {
               throw new RuntimeException("Error while parsing curve " + name, ex);
            }
         }

         return ImmutableMap.copyOf(output);
      }

      private Map<String, ParticleEvent> parseEvents(JsonObject events) {
         HashMap<String, ParticleEvent> output = new HashMap<>();

         for (Entry<String, JsonElement> entry : events.entrySet()) {
            String name = entry.getKey();

            try {
               ParticleEvent event = ParticleEvent.deserialize(entry.getValue().getAsJsonObject());
               output.put(name, event);
            } catch (Exception ex) {
               throw new RuntimeException("Error while parsing event " + name, ex);
            }
         }

         return ImmutableMap.copyOf(output);
      }
   }
}
