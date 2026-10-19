package com.mortal_cultivation.client.animation;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.mortal_cultivation.MortalCultivation;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class FlightAnimationLibrary {
   public static final ResourceLocation RESOURCE = ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "player_animations/qi_flight.animation.json");
   private static volatile Map<String, FlightAnimationLibrary.AnimationClip> animations = Map.of();

   private FlightAnimationLibrary() {
   }

   public static void reload(ResourceManager resourceManager) {
      Objects.requireNonNull(resourceManager, "resourceManager");

      try {
         Map<String, FlightAnimationLibrary.AnimationClip> loadedAnimations = load(resourceManager);
         animations = loadedAnimations;
         MortalCultivation.LOGGER.info("Loaded {} flight animations from {}", loadedAnimations.size(), RESOURCE);
      } catch (IOException | JsonParseException exception) {
         MortalCultivation.LOGGER.error("Failed to load flight animations from {}", RESOURCE, exception);
      }
   }

   public static Set<String> animationIds() {
      return animations.keySet();
   }

   public static Optional<FlightAnimationLibrary.AnimationClip> getAnimation(String animationId) {
      return Optional.ofNullable(animations.get(normalizeAnimationId(animationId)));
   }

   public static Optional<FlightAnimationLibrary.AnimationClip> getAnimation(ResourceLocation animationId) {
      Objects.requireNonNull(animationId, "animationId");
      return getAnimation(animationId.getPath());
   }

   public static FlightAnimationLibrary.BonePose sample(String animationId, String bone, double timeSeconds) {
      return getAnimation(animationId).map(animation -> animation.sample(bone, timeSeconds)).orElse(FlightAnimationLibrary.BonePose.ZERO);
   }

   public static FlightAnimationLibrary.BonePose sample(ResourceLocation animationId, String bone, double timeSeconds) {
      return getAnimation(animationId).map(animation -> animation.sample(bone, timeSeconds)).orElse(FlightAnimationLibrary.BonePose.ZERO);
   }

   public static Vec3 sample(String animationId, String bone, FlightAnimationLibrary.TransformChannel channel, double timeSeconds) {
      return getAnimation(animationId).map(animation -> animation.sample(bone, channel, timeSeconds)).orElse(Vec3.ZERO);
   }

   public static Vec3 sample(ResourceLocation animationId, String bone, FlightAnimationLibrary.TransformChannel channel, double timeSeconds) {
      return getAnimation(animationId).map(animation -> animation.sample(bone, channel, timeSeconds)).orElse(Vec3.ZERO);
   }

   private static Map<String, FlightAnimationLibrary.AnimationClip> load(ResourceManager resourceManager) throws IOException {
      Resource resource = (Resource)resourceManager.getResource(RESOURCE).orElseThrow(() -> new FileNotFoundException(RESOURCE.toString()));

      try (Reader reader = resource.openAsReader()) {
         JsonElement rootElement = JsonParser.parseReader(reader);
         if (!rootElement.isJsonObject()) {
            throw new JsonParseException("Animation root must be a JSON object");
         }

         JsonObject root = rootElement.getAsJsonObject();
         JsonObject animationObject = requiredObject(root, "animations", "root");
         Map<String, FlightAnimationLibrary.AnimationClip> loadedAnimations = new LinkedHashMap<>();

         for (Entry<String, JsonElement> entry : animationObject.entrySet()) {
            String animationId = normalizeAnimationId(entry.getKey());
            if (!entry.getValue().isJsonObject()) {
               throw new JsonParseException("Animation " + animationId + " must be an object");
            }

            loadedAnimations.put(animationId, parseAnimation(animationId, entry.getValue().getAsJsonObject()));
         }

         return Map.copyOf(loadedAnimations);
      }
   }

   private static FlightAnimationLibrary.AnimationClip parseAnimation(String animationId, JsonObject animationObject) {
      double lengthSeconds = readNumber(animationObject, "animation_length", 0.0);
      boolean looping = readLoop(animationObject.get("loop"));
      JsonObject boneObject = requiredObject(animationObject, "bones", "animation " + animationId);
      Map<String, FlightAnimationLibrary.BoneTrack> bones = new LinkedHashMap<>();
      double latestKeyframe = 0.0;

      for (Entry<String, JsonElement> entry : boneObject.entrySet()) {
         if (!entry.getValue().isJsonObject()) {
            throw new JsonParseException("Bone " + entry.getKey() + " in animation " + animationId + " must be an object");
         }

         JsonObject channels = entry.getValue().getAsJsonObject();
         FlightAnimationLibrary.KeyframeTrack rotation = parseTrack(channels.get("rotation"), animationId + "/" + entry.getKey() + "/rotation");
         FlightAnimationLibrary.KeyframeTrack position = parseTrack(channels.get("position"), animationId + "/" + entry.getKey() + "/position");
         latestKeyframe = Math.max(latestKeyframe, Math.max(rotation.endTime(), position.endTime()));
         bones.put(entry.getKey(), new FlightAnimationLibrary.BoneTrack(rotation, position));
      }

      if (lengthSeconds <= 0.0) {
         lengthSeconds = latestKeyframe;
      }

      if (!(lengthSeconds < 0.0) && Double.isFinite(lengthSeconds)) {
         return new FlightAnimationLibrary.AnimationClip(animationId, lengthSeconds, looping, Map.copyOf(bones));
      } else {
         throw new JsonParseException("Invalid animation length for " + animationId + ": " + lengthSeconds);
      }
   }

   private static FlightAnimationLibrary.KeyframeTrack parseTrack(JsonElement trackElement, String context) {
      if (trackElement != null && !trackElement.isJsonNull()) {
         List<FlightAnimationLibrary.Keyframe> keyframes = new ArrayList<>();
         if (trackElement.isJsonArray()) {
            keyframes.add(new FlightAnimationLibrary.Keyframe(0.0, readVector(trackElement, context), FlightAnimationLibrary.Interpolation.LINEAR));
         } else {
            if (!trackElement.isJsonObject()) {
               throw new JsonParseException("Animation track " + context + " must be an array or object");
            }

            JsonObject trackObject = trackElement.getAsJsonObject();
            Optional<Vec3> directVector = findVector(trackObject, context);
            directVector.ifPresent(vectorx -> keyframes.add(new FlightAnimationLibrary.Keyframe(0.0, vectorx, readInterpolation(trackObject))));

            for (Entry<String, JsonElement> entry : trackObject.entrySet()) {
               double timeSeconds;
               try {
                  timeSeconds = Double.parseDouble(entry.getKey());
               } catch (NumberFormatException ignored) {
                  continue;
               }

               if (timeSeconds < 0.0 || !Double.isFinite(timeSeconds)) {
                  throw new JsonParseException("Invalid keyframe time in " + context + ": " + entry.getKey());
               }

               JsonElement keyframeElement = entry.getValue();
               Vec3 vector = readVector(keyframeElement, context + " at " + entry.getKey());
               FlightAnimationLibrary.Interpolation interpolation = keyframeElement.isJsonObject()
                  ? readInterpolation(keyframeElement.getAsJsonObject())
                  : FlightAnimationLibrary.Interpolation.LINEAR;
               keyframes.add(new FlightAnimationLibrary.Keyframe(timeSeconds, vector, interpolation));
            }
         }

         if (keyframes.isEmpty()) {
            return FlightAnimationLibrary.KeyframeTrack.EMPTY;
         }

         keyframes.sort((left, right) -> Double.compare(left.timeSeconds(), right.timeSeconds()));
         List<FlightAnimationLibrary.Keyframe> uniqueKeyframes = new ArrayList<>(keyframes.size());

         for (FlightAnimationLibrary.Keyframe keyframe : keyframes) {
            int lastIndex = uniqueKeyframes.size() - 1;
            if (lastIndex >= 0 && Double.compare(uniqueKeyframes.get(lastIndex).timeSeconds(), keyframe.timeSeconds()) == 0) {
               uniqueKeyframes.set(lastIndex, keyframe);
            } else {
               uniqueKeyframes.add(keyframe);
            }
         }

         return new FlightAnimationLibrary.KeyframeTrack(List.copyOf(uniqueKeyframes));
      } else {
         return FlightAnimationLibrary.KeyframeTrack.EMPTY;
      }
   }

   private static Optional<Vec3> findVector(JsonObject object, String context) {
      if (object.has("post")) {
         return Optional.of(readVector(object.get("post"), context + "/post"));
      } else {
         return object.has("vector") ? Optional.of(readVector(object.get("vector"), context + "/vector")) : Optional.empty();
      }
   }

   private static Vec3 readVector(JsonElement element, String context) {
      if (element == null || element.isJsonNull()) {
         throw new JsonParseException("Missing vector in " + context);
      }

      if (element.isJsonObject()) {
         return findVector(element.getAsJsonObject(), context).orElseThrow(() -> new JsonParseException("Missing vector in " + context));
      }

      if (element.isJsonArray() && element.getAsJsonArray().size() == 3) {
         try {
            double x = element.getAsJsonArray().get(0).getAsDouble();
            double y = element.getAsJsonArray().get(1).getAsDouble();
            double z = element.getAsJsonArray().get(2).getAsDouble();
            if (Double.isFinite(x) && Double.isFinite(y) && Double.isFinite(z)) {
               return new Vec3(x, y, z);
            } else {
               throw new JsonParseException("Vector in " + context + " contains a non-finite number");
            }
         } catch (UnsupportedOperationException | NumberFormatException exception) {
            throw new JsonParseException("Vector in " + context + " must contain numbers", exception);
         }
      } else {
         throw new JsonParseException("Vector in " + context + " must contain exactly three numbers");
      }
   }

   private static FlightAnimationLibrary.Interpolation readInterpolation(JsonObject keyframeObject) {
      String interpolation = readString(keyframeObject, "lerp_mode");
      if (interpolation == null) {
         interpolation = readString(keyframeObject, "easing");
      }

      return "catmullrom".equalsIgnoreCase(interpolation) ? FlightAnimationLibrary.Interpolation.CATMULL_ROM : FlightAnimationLibrary.Interpolation.LINEAR;
   }

   private static boolean readLoop(JsonElement loopElement) {
      if (loopElement == null || loopElement.isJsonNull() || !loopElement.isJsonPrimitive()) {
         return false;
      } else {
         return loopElement.getAsJsonPrimitive().isBoolean() ? loopElement.getAsBoolean() : "true".equalsIgnoreCase(loopElement.getAsString());
      }
   }

   private static double readNumber(JsonObject object, String key, double fallback) {
      JsonElement element = object.get(key);
      if (element != null && !element.isJsonNull()) {
         try {
            return element.getAsDouble();
         } catch (UnsupportedOperationException | NumberFormatException exception) {
            throw new JsonParseException("Property " + key + " must be a number", exception);
         }
      } else {
         return fallback;
      }
   }

   private static String readString(JsonObject object, String key) {
      JsonElement element = object.get(key);
      return element != null && !element.isJsonNull() && element.isJsonPrimitive() ? element.getAsString() : null;
   }

   private static JsonObject requiredObject(JsonObject parent, String key, String context) {
      JsonElement element = parent.get(key);
      if (element != null && element.isJsonObject()) {
         return element.getAsJsonObject();
      } else {
         throw new JsonParseException("Property " + key + " in " + context + " must be an object");
      }
   }

   private static String normalizeAnimationId(String animationId) {
      Objects.requireNonNull(animationId, "animationId");
      int namespaceSeparator = animationId.indexOf(58);
      return namespaceSeparator >= 0 ? animationId.substring(namespaceSeparator + 1) : animationId;
   }

   public static final class AnimationClip {
      private final String id;
      private final double lengthSeconds;
      private final boolean looping;
      private final Map<String, FlightAnimationLibrary.BoneTrack> bones;

      private AnimationClip(String id, double lengthSeconds, boolean looping, Map<String, FlightAnimationLibrary.BoneTrack> bones) {
         this.id = id;
         this.lengthSeconds = lengthSeconds;
         this.looping = looping;
         this.bones = bones;
      }

      public String id() {
         return this.id;
      }

      public double lengthSeconds() {
         return this.lengthSeconds;
      }

      public boolean looping() {
         return this.looping;
      }

      public Set<String> bones() {
         return this.bones.keySet();
      }

      public FlightAnimationLibrary.BonePose sample(String bone, double timeSeconds) {
         FlightAnimationLibrary.BoneTrack track = this.bones.get(bone);
         if (track == null) {
            return FlightAnimationLibrary.BonePose.ZERO;
         }

         double sampleTime = this.resolveTime(timeSeconds);
         return new FlightAnimationLibrary.BonePose(track.rotation().sample(sampleTime, this.looping), track.position().sample(sampleTime, this.looping));
      }

      public Vec3 sample(String bone, FlightAnimationLibrary.TransformChannel channel, double timeSeconds) {
         Objects.requireNonNull(channel, "channel");
         FlightAnimationLibrary.BoneTrack track = this.bones.get(bone);
         if (track == null) {
            return Vec3.ZERO;
         }

         double sampleTime = this.resolveTime(timeSeconds);

         return switch (channel) {
            case ROTATION -> track.rotation().sample(sampleTime, this.looping);
            case POSITION -> track.position().sample(sampleTime, this.looping);
         };
      }

      private double resolveTime(double timeSeconds) {
         if (!Double.isFinite(timeSeconds)) {
            return 0.0;
         } else if (this.looping && this.lengthSeconds > 0.0) {
            double wrappedTime = timeSeconds % this.lengthSeconds;
            return wrappedTime < 0.0 ? wrappedTime + this.lengthSeconds : wrappedTime;
         } else {
            return Math.max(0.0, Math.min(timeSeconds, this.lengthSeconds));
         }
      }
   }

   public record BonePose(Vec3 rotationDegrees, Vec3 position) {
      public static final FlightAnimationLibrary.BonePose ZERO = new FlightAnimationLibrary.BonePose(Vec3.ZERO, Vec3.ZERO);

      public BonePose {
         Objects.requireNonNull(rotationDegrees, "rotationDegrees");
         Objects.requireNonNull(position, "position");
      }
   }

   private record BoneTrack(FlightAnimationLibrary.KeyframeTrack rotation, FlightAnimationLibrary.KeyframeTrack position) {
   }

   private enum Interpolation {
      LINEAR,
      CATMULL_ROM;
   }

   private record Keyframe(double timeSeconds, Vec3 value, FlightAnimationLibrary.Interpolation interpolation) {
   }

   private static final class KeyframeTrack {
      private static final FlightAnimationLibrary.KeyframeTrack EMPTY = new FlightAnimationLibrary.KeyframeTrack(List.of());
      private final List<FlightAnimationLibrary.Keyframe> keyframes;

      private KeyframeTrack(List<FlightAnimationLibrary.Keyframe> keyframes) {
         this.keyframes = keyframes;
      }

      private double endTime() {
         return this.keyframes.isEmpty() ? 0.0 : this.keyframes.getLast().timeSeconds();
      }

      private Vec3 sample(double timeSeconds, boolean looping) {
         if (this.keyframes.isEmpty()) {
            return Vec3.ZERO;
         }

         if (this.keyframes.size() == 1 || timeSeconds <= this.keyframes.getFirst().timeSeconds()) {
            return this.keyframes.getFirst().value();
         }

         if (timeSeconds >= this.keyframes.getLast().timeSeconds()) {
            return this.keyframes.getLast().value();
         }

         int leftIndex = this.findLeftKeyframe(timeSeconds);
         FlightAnimationLibrary.Keyframe left = this.keyframes.get(leftIndex);
         FlightAnimationLibrary.Keyframe right = this.keyframes.get(leftIndex + 1);
         double duration = right.timeSeconds() - left.timeSeconds();
         double progress = duration <= 0.0 ? 0.0 : (timeSeconds - left.timeSeconds()) / duration;
         if (left.interpolation() != FlightAnimationLibrary.Interpolation.CATMULL_ROM) {
            return lerp(left.value(), right.value(), progress);
         }

         int previousIndex = looping && leftIndex == 0 && this.keyframes.size() > 2 ? this.keyframes.size() - 2 : Math.max(0, leftIndex - 1);
         int nextIndex = looping && leftIndex + 2 >= this.keyframes.size() && this.keyframes.size() > 2
            ? 1
            : Math.min(this.keyframes.size() - 1, leftIndex + 2);
         Vec3 previous = this.keyframes.get(previousIndex).value();
         Vec3 next = this.keyframes.get(nextIndex).value();
         return catmullRom(previous, left.value(), right.value(), next, progress);
      }

      private int findLeftKeyframe(double timeSeconds) {
         int low = 0;
         int high = this.keyframes.size() - 1;

         while (high - low > 1) {
            int middle = low + high >>> 1;
            if (this.keyframes.get(middle).timeSeconds() <= timeSeconds) {
               low = middle;
            } else {
               high = middle;
            }
         }

         return low;
      }

      private static Vec3 lerp(Vec3 start, Vec3 end, double progress) {
         return new Vec3(start.x + (end.x - start.x) * progress, start.y + (end.y - start.y) * progress, start.z + (end.z - start.z) * progress);
      }

      private static Vec3 catmullRom(Vec3 p0, Vec3 p1, Vec3 p2, Vec3 p3, double progress) {
         return new Vec3(
            catmullRom(p0.x, p1.x, p2.x, p3.x, progress), catmullRom(p0.y, p1.y, p2.y, p3.y, progress), catmullRom(p0.z, p1.z, p2.z, p3.z, progress)
         );
      }

      private static double catmullRom(double p0, double p1, double p2, double p3, double progress) {
         double squared = progress * progress;
         double cubed = squared * progress;
         return 0.5 * (2.0 * p1 + (-p0 + p2) * progress + (2.0 * p0 - 5.0 * p1 + 4.0 * p2 - p3) * squared + (-p0 + 3.0 * p1 - 3.0 * p2 + p3) * cubed);
      }
   }

   public enum TransformChannel {
      ROTATION,
      POSITION;
   }
}
