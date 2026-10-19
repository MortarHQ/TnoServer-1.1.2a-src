package com.finderfeed.fdlib.systems.bedrock.animations;

import com.finderfeed.fdlib.shunting_yard.RPNVector3f;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.resources.ResourceLocation;

public class Animation {
   public static final String TRANSITION = "transition";
   public static final String TO_NULL_TRANSITION = "transition_to_null";
   private ResourceLocation name;
   private ResourceLocation fileLocation;
   private int animTime;
   private Animation.LoopMode defaultLoopMode;
   private HashMap<String, BoneAnimationData> datas;

   public Animation() {
   }

   public Animation(ResourceLocation fileLocation) {
      this.fileLocation = fileLocation;
   }

   public void applyAnimation(AnimationContext context, FDModel model, float elapsedTime) {
      try {
         for (Entry<String, BoneAnimationData> entry : this.datas.entrySet()) {
            BoneAnimationData data = entry.getValue();
            data.apply(model, context, elapsedTime);
         }
      } catch (Exception e) {
         throw new RuntimeException("Error while applying animation: " + this.getName(), e);
      }
   }

   public Animation createTransitionTo(AnimationContext context, Animation next, float elapsedTime, int toNullTime, boolean nextReversed) {
      Animation animation;
      HashMap<String, BoneAnimationData> dataHashMap;
      if (next != null) {
         animation = new TransitionAnimation(next);
         animation.name = ResourceLocation.tryBuild("fdlib", "transition");
         animation.defaultLoopMode = next.getDefaultLoopMode();
         animation.animTime = next.getAnimTime();
         dataHashMap = this.createToAnimationDatas(next, context, toNullTime, elapsedTime, nextReversed);
      } else {
         animation = new Animation();
         animation.name = ResourceLocation.tryBuild("fdlib", "transition_to_null");
         animation.defaultLoopMode = Animation.LoopMode.ONCE;
         animation.animTime = toNullTime;
         dataHashMap = this.createToNullDatas(context, toNullTime, elapsedTime);
      }

      animation.datas = dataHashMap;
      return animation;
   }

   private HashMap<String, BoneAnimationData> createToNullDatas(AnimationContext context, int toNullTime, float elapsedTime) {
      HashMap<String, BoneAnimationData> map = new HashMap<>();

      for (Entry<String, BoneAnimationData> entry : this.datas.entrySet()) {
         BoneAnimationData data = entry.getValue().createTransitionData(null, null, context, toNullTime, elapsedTime, false);
         map.put(entry.getKey(), data);
      }

      return map;
   }

   private HashMap<String, BoneAnimationData> createToAnimationDatas(
      Animation next, AnimationContext context, int toNullTime, float elapsedTime, boolean reversed
   ) {
      HashMap<String, BoneAnimationData> map = new HashMap<>(next.datas);

      for (Entry<String, BoneAnimationData> entry : this.datas.entrySet()) {
         BoneAnimationData d = next.datas.get(entry.getKey());
         if (d != null || toNullTime != 0) {
            BoneAnimationData data = entry.getValue().createTransitionData(next, d, context, toNullTime, elapsedTime, reversed);
            map.put(entry.getKey(), data);
         }
      }

      return map;
   }

   public boolean isTransition() {
      return this.name.getPath().equals("transition");
   }

   public boolean isToNullTransition() {
      return this.name.getPath().equals("transition_to_null");
   }

   public Animation.LoopMode getDefaultLoopMode() {
      return this.defaultLoopMode;
   }

   public ResourceLocation getFileLocation() {
      return this.fileLocation;
   }

   public int getAnimTime() {
      return this.animTime;
   }

   public ResourceLocation getName() {
      return this.name;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof Animation that) {
         if (this instanceof TransitionAnimation tthis && that instanceof TransitionAnimation tthat) {
            return tthis.getTransitionTo() == tthat.getTransitionTo();
         } else if (this instanceof TransitionAnimation tthis) {
            return tthis.getTransitionTo() == that;
         } else {
            return that instanceof TransitionAnimation tthat ? this == tthat.getTransitionTo() : that == this;
         }
      } else {
         return false;
      }
   }

   public void load(ResourceLocation name, JsonObject object) {
      this.name = name;
      Animation.LoopMode mode = this.parseLoopMode(name, object);
      int length = Math.round(object.get("animation_length").getAsFloat() * 20.0F);
      this.animTime = length;
      JsonObject bones = object.getAsJsonObject("bones");
      HashMap<String, BoneAnimationData> animationDatas = new HashMap<>();

      for (Entry<String, JsonElement> entry : bones.entrySet()) {
         String boneName = entry.getKey();
         JsonElement bone = entry.getValue();
         BoneAnimationData data = this.parseData(boneName, bone.getAsJsonObject());
         animationDatas.put(boneName, data);
      }

      this.datas = animationDatas;
      this.defaultLoopMode = mode;
   }

   private BoneAnimationData parseData(String name, JsonObject object) {
      List<KeyFrame> positions = null;
      List<KeyFrame> rotations = null;
      List<KeyFrame> scales = null;
      if (object.has("position")) {
         positions = this.parseKeyFrames(Animation.KeyFrameLoadType.POSITION, object.get("position"));
      }

      if (object.has("rotation")) {
         rotations = this.parseKeyFrames(Animation.KeyFrameLoadType.ROTATION, object.get("rotation"));
      }

      if (object.has("scale")) {
         scales = this.parseKeyFrames(Animation.KeyFrameLoadType.SCALE, object.get("scale"));
      }

      return new BoneAnimationData(name, positions, rotations, scales, true);
   }

   private List<KeyFrame> parseKeyFrames(Animation.KeyFrameLoadType loadType, JsonElement keyFrames) {
      List<KeyFrame> frames = new ArrayList<>();
      if (keyFrames.isJsonObject()) {
         Map<String, JsonElement> map = keyFrames.getAsJsonObject().asMap();

         for (Entry<String, JsonElement> entry : map.entrySet()) {
            int timestamp = Math.round(Float.parseFloat(entry.getKey()) * 20.0F);
            JsonElement keyframe = entry.getValue();
            KeyFrame keyFrame = this.parseKeyFrame(loadType, timestamp, keyframe);
            frames.add(keyFrame);
         }
      } else if (keyFrames.isJsonArray()) {
         JsonArray array = keyFrames.getAsJsonArray();
         String v1 = array.get(0).getAsString();
         String v2 = array.get(1).getAsString();
         String v3 = array.get(2).getAsString();
         if (loadType == Animation.KeyFrameLoadType.POSITION) {
            v1 = "-(" + v1 + ")";
         } else if (loadType == Animation.KeyFrameLoadType.ROTATION) {
            v1 = "-(" + v1 + ")";
            v2 = "-(" + v2 + ")";
         }

         frames.add(new KeyFrame(new RPNVector3f(v1, v2, v3), 0, InterpolationMode.LINEAR));
      } else if (keyFrames.isJsonPrimitive()) {
         String value = keyFrames.getAsString();
         frames.add(new KeyFrame(new RPNVector3f(value, value, value), 0, InterpolationMode.LINEAR));
      }

      return frames;
   }

   private KeyFrame parseKeyFrame(Animation.KeyFrameLoadType type, int timestamp, JsonElement element) {
      try {
         if (element.isJsonPrimitive()) {
            String value = element.getAsString();
            return new KeyFrame(new RPNVector3f(value, value, value), timestamp, InterpolationMode.LINEAR);
         }

         if (element.isJsonArray()) {
            JsonArray array = element.getAsJsonArray();
            String v1 = array.get(0).getAsString();
            String v2 = array.get(1).getAsString();
            String v3 = array.get(2).getAsString();
            if (type == Animation.KeyFrameLoadType.POSITION) {
               v1 = "-(" + v1 + ")";
            } else if (type == Animation.KeyFrameLoadType.ROTATION) {
               v1 = "-(" + v1 + ")";
               v2 = "-(" + v2 + ")";
            }

            return new KeyFrame(new RPNVector3f(v1, v2, v3), timestamp, InterpolationMode.LINEAR);
         } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            RPNVector3f pre = null;
            if (object.has("pre")) {
               JsonArray array = object.getAsJsonArray("pre");
               String v1 = array.get(0).getAsString();
               String v2 = array.get(1).getAsString();
               String v3 = array.get(2).getAsString();
               if (type == Animation.KeyFrameLoadType.POSITION) {
                  v1 = "-(" + v1 + ")";
               } else if (type == Animation.KeyFrameLoadType.ROTATION) {
                  v1 = "-(" + v1 + ")";
                  v2 = "-(" + v2 + ")";
               }

               pre = new RPNVector3f(v1, v2, v3);
            }

            JsonArray array = object.getAsJsonArray("post");
            String v1 = array.get(0).getAsString();
            String v2 = array.get(1).getAsString();
            String v3 = array.get(2).getAsString();
            if (type == Animation.KeyFrameLoadType.POSITION) {
               v1 = "-(" + v1 + ")";
            } else if (type == Animation.KeyFrameLoadType.ROTATION) {
               v1 = "-(" + v1 + ")";
               v2 = "-(" + v2 + ")";
            }

            InterpolationMode mode = this.parseMode(object);
            return new KeyFrame(pre, new RPNVector3f(v1, v2, v3), timestamp, mode);
         } else {
            throw new RuntimeException("Unknown keyframe format.");
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException("Error while loading animation: " + this.name);
      }
   }

   private InterpolationMode parseMode(JsonObject keyFrame) {
      if (keyFrame.has("lerp_mode")) {
         String mode = keyFrame.get("lerp_mode").getAsString();
         if (mode.equals("catmullrom")) {
            return InterpolationMode.CATMULLROM;
         } else {
            throw new RuntimeException("Unknown lerp mode: " + mode);
         }
      } else {
         return InterpolationMode.LINEAR;
      }
   }

   private Animation.LoopMode parseLoopMode(ResourceLocation name, JsonObject animation) {
      if (animation.has("loop")) {
         JsonElement element = animation.get("loop");
         String s = element.getAsString();
         if (s.equals("true")) {
            return Animation.LoopMode.LOOP;
         } else if (s.equals("hold_on_last_frame")) {
            return Animation.LoopMode.HOLD_ON_LAST_FRAME;
         } else {
            throw new RuntimeException("Unknown loop mode in animation: " + name);
         }
      } else {
         return Animation.LoopMode.ONCE;
      }
   }

   private enum KeyFrameLoadType {
      POSITION(-1.0F, 1.0F, 1.0F),
      ROTATION(-1.0F, -1.0F, 1.0F),
      SCALE(1.0F, 1.0F, 1.0F);

      float xMod;
      float yMod;
      float zMod;

      KeyFrameLoadType(float x, float y, float z) {
         this.xMod = x;
         this.yMod = y;
         this.zMod = z;
      }
   }

   public enum LoopMode {
      LOOP,
      ONCE,
      HOLD_ON_LAST_FRAME;
   }
}
