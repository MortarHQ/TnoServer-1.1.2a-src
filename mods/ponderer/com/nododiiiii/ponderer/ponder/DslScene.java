package com.nododiiiii.ponderer.ponder;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class DslScene {
   public String id;
   public List<String> items = List.of();
   public LocalizedText title;
   public String structure;
   public List<String> structures = List.of();
   public List<String> tags = List.of();
   @Nullable
   public Boolean editable;
   public List<DslScene.SceneSegment> scenes = List.of();
   public String nbtFilter;
   @Nullable
   public String pack;
   @Nullable
   public String triggerMode;
   @Nullable
   public String hintStyle;
   @Nullable
   public String triggerStructure;
   @Nullable
   public Integer triggerStructureRange;
   @Nullable
   public List<Integer> triggerCoord1;
   @Nullable
   public List<Integer> triggerCoord2;
   @Nullable
   public Boolean onlyFirstTime;
   @Nullable
   public String hintFrequency;
   @Nullable
   public String hintAuto;
   @Nullable
   public String hintTitle;
   @Nullable
   public String hintSubtitle;
   @Nullable
   public String hintTitleText;
   @Nullable
   public String hintSubtitleText;
   public transient String sourceFile;

   @Nullable
   public static String extractPackPrefix(@Nullable String sourceFile) {
      if (sourceFile == null) {
         return null;
      }

      if (sourceFile.startsWith("[")) {
         int close = sourceFile.indexOf(93);
         if (close > 0) {
            return sourceFile.substring(0, close + 1);
         }
      }

      return null;
   }

   @Nullable
   public String getPackPrefix() {
      return this.pack != null && !this.pack.isEmpty() ? "[" + this.pack + "]" : null;
   }

   public String sceneKey() {
      return this.pack != null && !this.pack.isEmpty() ? "[" + this.pack + "] " + this.id : this.id;
   }

   public boolean isEditable() {
      return !Boolean.FALSE.equals(this.editable);
   }

   public boolean isEditable(boolean defaultEditable) {
      return this.editable == null ? defaultEditable : this.editable;
   }

   public static class DslStep {
      public String type;
      public String structure;
      public Integer duration;
      public Integer height;
      public LocalizedText text;
      public String key;
      public List<Double> point;
      public String entity;
      public List<Double> pos;
      public List<Double> motion;
      public List<Double> lookAt;
      public Float yaw;
      public Float pitch;
      public Float degrees;
      public Float scale;
      public Float rotation;
      public Integer count;
      public List<Integer> bounds;
      public String direction;
      public String linkId;
      public String action;
      public String item;
      public String sound;
      public Float soundVolume;
      public String source;
      public String color;
      public String block;
      public Map<String, String> blockProperties;
      public List<Integer> blockPos;
      public List<Integer> blockPos2;
      public List<Double> offset;
      public Float rotX;
      public Float rotY;
      public Float rotZ;
      public String nbt;
      public Boolean reDrawBlocks;
      public Boolean destroyParticles;
      public Boolean spawnParticles;
      public Boolean immediateDisplay;
      public Boolean smartDisplay;
      public String entranceAnimation;
      public Integer entranceDuration;
      public Integer entranceInterval;
      public Boolean placeNearTarget;
      public Boolean attachKeyFrame;
      public Boolean whileSneaking;
      public Boolean whileCTRL;
      public Boolean enableNbt;
      public Boolean fullScene;
      public List<DslScene.InterfaceSlotBinding> interfaceSlots;

      public int durationOrDefault(int fallback) {
         return this.duration == null ? fallback : Math.max(this.duration, 0);
      }
   }

   public static class InterfaceSlotBinding {
      public Integer slotIndex;
      @Nullable
      public Integer slotX;
      @Nullable
      public Integer slotY;
      public String ingredientId;
      @Nullable
      public String ingredientKind;

      public InterfaceSlotBinding() {
      }

      public InterfaceSlotBinding(int slotIndex, String ingredientId, @Nullable String ingredientKind) {
         this(slotIndex, null, null, ingredientId, ingredientKind);
      }

      public InterfaceSlotBinding(int slotIndex, @Nullable Integer slotX, @Nullable Integer slotY, String ingredientId, @Nullable String ingredientKind) {
         this.slotIndex = slotIndex;
         this.slotX = slotX;
         this.slotY = slotY;
         this.ingredientId = ingredientId;
         this.ingredientKind = ingredientKind;
      }
   }

   public static class SceneSegment {
      public String id;
      public LocalizedText title;
      public List<DslScene.DslStep> steps = List.of();
   }
}
