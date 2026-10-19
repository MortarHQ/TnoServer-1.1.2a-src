package gardensofthedead.registry;

import java.util.function.Supplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

public class ModSoundTypes {
   public static final SoundType WHISTLECANE = ModSoundTypes.Builder.copy(SoundType.BAMBOO).setPitch(0.5F).build();

   private static class Builder {
      private float volume = 1.0F;
      private float pitch = 1.0F;
      Supplier<SoundEvent> breakSound;
      Supplier<SoundEvent> stepSound;
      Supplier<SoundEvent> placeSound;
      Supplier<SoundEvent> hitSound;
      Supplier<SoundEvent> fallSound;

      public static ModSoundTypes.Builder copy(SoundType soundType) {
         return new ModSoundTypes.Builder()
            .setVolume(soundType.getVolume())
            .setPitch(soundType.getPitch())
            .setBreakSound(soundType::getBreakSound)
            .setStepSound(soundType::getStepSound)
            .setPlaceSound(soundType::getPlaceSound)
            .setHitSound(soundType::getHitSound)
            .setFallSound(soundType::getFallSound);
      }

      public SoundType build() {
         return new ModSoundTypes.CustomSoundType(this.volume, this.pitch, this.breakSound, this.stepSound, this.placeSound, this.hitSound, this.fallSound);
      }

      public ModSoundTypes.Builder setVolume(float volume) {
         this.volume = volume;
         return this;
      }

      public ModSoundTypes.Builder setPitch(float pitch) {
         this.pitch = pitch;
         return this;
      }

      public ModSoundTypes.Builder setBreakSound(Supplier<SoundEvent> breakSound) {
         this.breakSound = breakSound;
         return this;
      }

      public ModSoundTypes.Builder setStepSound(Supplier<SoundEvent> stepSound) {
         this.stepSound = stepSound;
         return this;
      }

      public ModSoundTypes.Builder setPlaceSound(Supplier<SoundEvent> placeSound) {
         this.placeSound = placeSound;
         return this;
      }

      public ModSoundTypes.Builder setHitSound(Supplier<SoundEvent> hitSound) {
         this.hitSound = hitSound;
         return this;
      }

      public ModSoundTypes.Builder setFallSound(Supplier<SoundEvent> fallSound) {
         this.fallSound = fallSound;
         return this;
      }
   }

   private static class CustomSoundType extends SoundType {
      private final Supplier<SoundEvent> breakSound;
      private final Supplier<SoundEvent> stepSound;
      private final Supplier<SoundEvent> placeSound;
      private final Supplier<SoundEvent> hitSound;
      private final Supplier<SoundEvent> fallSound;

      public CustomSoundType(
         float volume,
         float pitch,
         Supplier<SoundEvent> breakSound,
         Supplier<SoundEvent> stepSound,
         Supplier<SoundEvent> placeSound,
         Supplier<SoundEvent> hitSound,
         Supplier<SoundEvent> fallSound
      ) {
         super(volume, pitch, null, null, null, null, null);
         this.breakSound = breakSound;
         this.stepSound = stepSound;
         this.placeSound = placeSound;
         this.hitSound = hitSound;
         this.fallSound = fallSound;
      }

      public SoundEvent getBreakSound() {
         return this.breakSound.get();
      }

      public SoundEvent getStepSound() {
         return this.stepSound.get();
      }

      public SoundEvent getPlaceSound() {
         return this.placeSound.get();
      }

      public SoundEvent getHitSound() {
         return this.hitSound.get();
      }

      public SoundEvent getFallSound() {
         return this.fallSound.get();
      }
   }
}
