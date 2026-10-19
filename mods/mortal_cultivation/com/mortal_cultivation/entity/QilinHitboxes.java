package com.mortal_cultivation.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public final class QilinHitboxes {
   public static final float BASE_WIDTH = 1.5625F;
   public static final float BASE_HEIGHT = 1.9062564F;
   private static final String ASSETS = "/assets/mortal_cultivation/";
   private static final List<QilinHitboxes.Bone> BONES = readBones();
   private static final Map<String, QilinHitboxes.Animation> ANIMATIONS = readAnimations();
   private static final AABB PHYSICAL_REST = physicalRestBounds();
   private final AzureMossQilinEntity parent;
   private final AzureMossQilinPart[] parts;
   private final Matrix4f[] transforms;
   private final Vector3f point = new Vector3f();

   public QilinHitboxes(AzureMossQilinEntity parent) {
      this.parent = parent;
      this.parts = BONES.stream()
         .filter(bone -> bone.points.length > 0)
         .map(bone -> new AzureMossQilinPart(parent, bone.name))
         .toArray(AzureMossQilinPart[]::new);
      this.transforms = new Matrix4f[BONES.size()];

      for (int i = 0; i < this.transforms.length; i++) {
         this.transforms[i] = new Matrix4f();
      }
   }

   public AzureMossQilinPart[] parts() {
      return this.parts;
   }

   public void setId(int parentId) {
      for (int i = 0; i < this.parts.length; i++) {
         this.parts[i].setId(parentId + i + 1);
      }
   }

   public void update() {
      AzureMossQilinEntity.Attack attack = this.parent.getAttack();
      String animationName = attack == AzureMossQilinEntity.Attack.NONE
         ? (this.parent.getDeltaMovement().horizontalDistanceSqr() > 1.0E-5 ? "walk" : "idle")
         : attack.animation;
      QilinHitboxes.Animation animation = ANIMATIONS.get(animationName);
      float seconds = attack == AzureMossQilinEntity.Attack.NONE ? this.parent.tickCount / 20.0F : this.parent.getAttackTicks() / 20.0F;
      if (animation.loop) {
         seconds %= animation.length;
      }

      seconds = Math.min(seconds, animation.length);
      Matrix4f world = worldTransform(this.parent.yBodyRot, this.parent.getScale());
      int partIndex = 0;

      for (int i = 0; i < BONES.size(); i++) {
         QilinHitboxes.Bone bone = BONES.get(i);
         QilinHitboxes.Channels channels = animation.channels.get(bone.name);
         Vector3f rotation = new Vector3f(bone.rotation);
         Vector3f translation = new Vector3f();
         Vector3f scale = new Vector3f(1.0F);
         if (channels != null) {
            rotation.add(channels.rotation.sample(seconds, new Vector3f()));
            channels.position.sample(seconds, translation);
            channels.scale.sample(seconds, scale);
         }

         if (attack == AzureMossQilinEntity.Attack.NONE && bone.name.equals("head")) {
            rotation.set(bone.rotation)
               .add(
                  -Mth.clamp(this.parent.getXRot(), -25.0F, 25.0F),
                  -Mth.clamp(Mth.wrapDegrees(this.parent.yHeadRot - this.parent.yBodyRot), -35.0F, 35.0F),
                  0.0F
               );
         }

         Matrix4f matrix = this.transforms[i].set(bone.parent < 0 ? world : this.transforms[bone.parent]);
         applyBone(matrix, bone.pivot, rotation, translation, scale);
         if (bone.points.length != 0) {
            AABB bounds = transformBounds(matrix, bone.points, this.point).move(this.parent.getX(), this.parent.getY(), this.parent.getZ()).inflate(0.015);
            this.parts[partIndex++].updateBounds(bounds);
         }
      }
   }

   public static AABB physicalBounds(AzureMossQilinEntity entity) {
      float scale = entity.getScale();
      return transformBounds(worldTransform(entity.yBodyRot, scale), corners(PHYSICAL_REST), new Vector3f()).move(entity.getX(), entity.getY(), entity.getZ());
   }

   public AABB fullBounds() {
      AABB bounds = this.parent.getBoundingBox();

      for (AzureMossQilinPart part : this.parts) {
         bounds = bounds.minmax(part.getBoundingBox());
      }

      return bounds;
   }

   private static Matrix4f worldTransform(float bodyYaw, float scale) {
      return new Matrix4f().rotateY((180.0F - bodyYaw) * (float) (Math.PI / 180.0)).scale(scale / 16.0F);
   }

   private static void applyBone(Matrix4f matrix, Vector3f pivot, Vector3f rotation, Vector3f translation, Vector3f scale) {
      matrix.translate(-translation.x, translation.y, translation.z)
         .translate(-pivot.x, pivot.y, pivot.z)
         .rotateZ(rotation.z * (float) (Math.PI / 180.0))
         .rotateY(-rotation.y * (float) (Math.PI / 180.0))
         .rotateX(-rotation.x * (float) (Math.PI / 180.0))
         .scale(scale)
         .translate(pivot.x, -pivot.y, -pivot.z);
   }

   private static AABB transformBounds(Matrix4f transform, Vector3f[] points, Vector3f work) {
      double minX = Double.POSITIVE_INFINITY;
      double minY = minX;
      double minZ = minX;
      double maxX = Double.NEGATIVE_INFINITY;
      double maxY = maxX;
      double maxZ = maxX;

      for (Vector3f source : points) {
         transform.transformPosition(source, work);
         minX = Math.min(minX, work.x);
         minY = Math.min(minY, work.y);
         minZ = Math.min(minZ, work.z);
         maxX = Math.max(maxX, work.x);
         maxY = Math.max(maxY, work.y);
         maxZ = Math.max(maxZ, work.z);
      }

      return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
   }

   private static List<QilinHitboxes.Bone> readBones() {
      JsonObject geometry = json("geo/azure_moss_qilin.geo.json").getAsJsonArray("minecraft:geometry").get(0).getAsJsonObject();
      List<QilinHitboxes.Bone> bones = new ArrayList<>();
      Map<String, Integer> indexes = new HashMap<>();

      for (JsonElement element : geometry.getAsJsonArray("bones")) {
         JsonObject bone = element.getAsJsonObject();
         String name = bone.get("name").getAsString();
         int parent = bone.has("parent") ? indexes.get(bone.get("parent").getAsString()) : -1;
         List<Vector3f> points = new ArrayList<>();
         if (bone.has("cubes")) {
            for (JsonElement cubeElement : bone.getAsJsonArray("cubes")) {
               JsonObject cube = cubeElement.getAsJsonObject();
               Vector3f origin = vector(cube.get("origin"), 0.0F);
               Vector3f size = vector(cube.get("size"), 0.0F);
               float inflate = cube.has("inflate") ? cube.get("inflate").getAsFloat() : 0.0F;
               Matrix4f transform = new Matrix4f();
               applyBone(transform, vector(cube.get("pivot"), 0.0F), vector(cube.get("rotation"), 0.0F), new Vector3f(), new Vector3f(1.0F));

               for (int corner = 0; corner < 8; corner++) {
                  Vector3f point = new Vector3f(
                     -(origin.x + ((corner & 1) == 0 ? -inflate : size.x + inflate)),
                     origin.y + ((corner & 2) == 0 ? -inflate : size.y + inflate),
                     origin.z + ((corner & 4) == 0 ? -inflate : size.z + inflate)
                  );
                  points.add(transform.transformPosition(point));
               }
            }
         }

         indexes.put(name, bones.size());
         bones.add(new QilinHitboxes.Bone(name, parent, vector(bone.get("pivot"), 0.0F), vector(bone.get("rotation"), 0.0F), points.toArray(Vector3f[]::new)));
      }

      return List.copyOf(bones);
   }

   private static AABB physicalRestBounds() {
      Matrix4f[] matrices = new Matrix4f[BONES.size()];
      AABB result = null;

      for (int i = 0; i < BONES.size(); i++) {
         QilinHitboxes.Bone bone = BONES.get(i);
         Matrix4f matrix = bone.parent < 0 ? new Matrix4f() : new Matrix4f(matrices[bone.parent]);
         applyBone(matrix, bone.pivot, bone.rotation, new Vector3f(), new Vector3f(1.0F));
         matrices[i] = matrix;
         if (bone.name.equals("body") || bone.name.contains("_leg_") || bone.name.contains("_shin_")) {
            AABB bounds = transformBounds(matrix, bone.points, new Vector3f());
            result = result == null ? bounds : result.minmax(bounds);
         }
      }

      return result;
   }

   private static Vector3f[] corners(AABB box) {
      Vector3f[] points = new Vector3f[8];

      for (int i = 0; i < 8; i++) {
         points[i] = new Vector3f(
            (float)((i & 1) == 0 ? box.minX : box.maxX), (float)((i & 2) == 0 ? box.minY : box.maxY), (float)((i & 4) == 0 ? box.minZ : box.maxZ)
         );
      }

      return points;
   }

   private static Map<String, QilinHitboxes.Animation> readAnimations() {
      Map<String, QilinHitboxes.Animation> result = new HashMap<>();

      for (Entry<String, JsonElement> entry : json("animations/azure_moss_qilin.animation.json").getAsJsonObject("animations").entrySet()) {
         JsonObject animation = entry.getValue().getAsJsonObject();
         Map<String, QilinHitboxes.Channels> channels = new HashMap<>();

         for (Entry<String, JsonElement> bone : animation.getAsJsonObject("bones").entrySet()) {
            JsonObject data = bone.getValue().getAsJsonObject();
            channels.put(
               bone.getKey(), new QilinHitboxes.Channels(track(data.get("rotation"), 0.0F), track(data.get("position"), 0.0F), track(data.get("scale"), 1.0F))
            );
         }

         String name = entry.getKey().substring(entry.getKey().lastIndexOf(46) + 1);
         result.put(
            name,
            new QilinHitboxes.Animation(
               animation.get("animation_length").getAsFloat(), animation.has("loop") && animation.get("loop").getAsBoolean(), Map.copyOf(channels)
            )
         );
      }

      return Map.copyOf(result);
   }

   private static QilinHitboxes.Track track(JsonElement element, float fallback) {
      List<QilinHitboxes.Keyframe> frames = new ArrayList<>();
      if (element != null && element.isJsonObject() && !element.getAsJsonObject().has("vector")) {
         for (Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            JsonElement frame = entry.getValue();
            boolean cubic = frame.isJsonObject()
               && frame.getAsJsonObject().has("lerp_mode")
               && frame.getAsJsonObject().get("lerp_mode").getAsString().equals("catmullrom");
            frames.add(new QilinHitboxes.Keyframe(Float.parseFloat(entry.getKey()), vector(frame, fallback), cubic));
         }
      } else {
         frames.add(new QilinHitboxes.Keyframe(0.0F, vector(element, fallback), false));
      }

      frames.sort(Comparator.comparingDouble(QilinHitboxes.Keyframe::time));
      return new QilinHitboxes.Track(frames.toArray(QilinHitboxes.Keyframe[]::new));
   }

   private static Vector3f vector(JsonElement element, float fallback) {
      if (element == null) {
         return new Vector3f(fallback);
      } else if (element.isJsonPrimitive()) {
         return new Vector3f(element.getAsFloat());
      } else if (element.isJsonObject()) {
         JsonObject object = element.getAsJsonObject();
         return vector(object.has("post") ? object.get("post") : object.get("vector"), fallback);
      } else {
         JsonArray array = element.getAsJsonArray();
         return new Vector3f(array.get(0).getAsFloat(), array.get(1).getAsFloat(), array.get(2).getAsFloat());
      }
   }

   private static JsonObject json(String path) {
      try (InputStream input = QilinHitboxes.class.getResourceAsStream("/assets/mortal_cultivation/" + path)) {
         if (input == null) {
            throw new IllegalStateException("Missing qilin geometry: " + path);
         } else {
            return JsonParser.parseReader(new InputStreamReader(input, StandardCharsets.UTF_8)).getAsJsonObject();
         }
      } catch (IOException exception) {
         throw new IllegalStateException("Cannot read qilin geometry: " + path, exception);
      }
   }

   private record Animation(float length, boolean loop, Map<String, QilinHitboxes.Channels> channels) {
   }

   private record Bone(String name, int parent, Vector3f pivot, Vector3f rotation, Vector3f[] points) {
   }

   private record Channels(QilinHitboxes.Track rotation, QilinHitboxes.Track position, QilinHitboxes.Track scale) {
   }

   private record Keyframe(float time, Vector3f value, boolean cubic) {
   }

   private record Track(QilinHitboxes.Keyframe[] frames) {
      Vector3f sample(float time, Vector3f result) {
         if (time <= this.frames[0].time) {
            return result.set(this.frames[0].value);
         }

         for (int i = 1; i < this.frames.length; i++) {
            QilinHitboxes.Keyframe to = this.frames[i];
            QilinHitboxes.Keyframe from = this.frames[i - 1];
            if (!(time > to.time)) {
               float t = (time - from.time) / (to.time - from.time);
               if (!to.cubic) {
                  return result.set(from.value).lerp(to.value, t);
               }

               Vector3f a = this.frames[Math.max(0, i - 2)].value;
               Vector3f b = from.value;
               Vector3f c = to.value;
               Vector3f d = this.frames[Math.min(this.frames.length - 1, i + 1)].value;
               return result.set(cubic(a.x, b.x, c.x, d.x, t), cubic(a.y, b.y, c.y, d.y, t), cubic(a.z, b.z, c.z, d.z, t));
            }
         }

         return result.set(this.frames[this.frames.length - 1].value);
      }

      private static float cubic(float a, float b, float c, float d, float t) {
         return 0.5F * (2.0F * b + (-a + c) * t + (2.0F * a - 5.0F * b + 4.0F * c - d) * t * t + (-a + 3.0F * b - 3.0F * c + d) * t * t * t);
      }
   }
}
