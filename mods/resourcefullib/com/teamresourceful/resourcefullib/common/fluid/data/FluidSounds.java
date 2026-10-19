package com.teamresourceful.resourcefullib.common.fluid.data;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.sounds.SoundEvent;

public record FluidSounds(Map<String, SoundEvent> sounds) {
   public static final String BUCKET_EMPTY = "bucket_empty";
   public static final String BUCKET_FILL = "bucket_fill";

   public FluidSounds() {
      this(new HashMap<>());
   }

   public void add(String name, SoundEvent sound) {
      this.sounds.put(name, sound);
   }

   public SoundEvent getOrDefault(String name, SoundEvent defaultSound) {
      return this.sounds.getOrDefault(name, defaultSound);
   }

   public boolean has(String name) {
      return this.sounds.containsKey(name);
   }
}
