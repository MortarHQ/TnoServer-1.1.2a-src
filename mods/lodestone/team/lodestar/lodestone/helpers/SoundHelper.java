package team.lodestar.lodestone.helpers;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class SoundHelper {
   public static void playSound(Entity target, SoundEvent soundEvent, float volume, float pitch) {
      playSound(target, soundEvent, target.getSoundSource(), volume, pitch);
   }

   public static void playSound(Entity target, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch) {
      target.level().playSound(null, target.getX(), target.getY(), target.getZ(), soundEvent, soundSource, volume, pitch);
   }
}
