package team.creative.creativecore.common.config.premade;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class SoundConfig {
   public ResourceLocation event;
   public float volume;
   public float pitch;

   public SoundConfig(ResourceLocation location, float volume, float pitch) {
      this.event = location;
      this.volume = volume;
      this.pitch = pitch;
   }

   public SoundConfig(ResourceLocation location) {
      this(location, 1.0F, 1.0F);
   }

   public void play(Entity entity, SoundSource category) {
      entity.level().playSound(null, entity, (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(this.event), category, this.volume, this.pitch);
   }

   public void play(Level level, double x, double y, double z, SoundSource category) {
      level.playSound(null, x, y, z, (SoundEvent)BuiltInRegistries.SOUND_EVENT.get(this.event), category, this.volume, this.pitch);
   }

   @Override
   public boolean equals(Object obj) {
      return !(obj instanceof SoundConfig sound)
         ? super.equals(obj)
         : sound.event.equals(this.event) && sound.volume == this.volume && sound.pitch == this.pitch;
   }
}
