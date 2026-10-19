package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.registry.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;

public class SoundDefinitions extends SoundDefinitionsProvider {
   public SoundDefinitions(PackOutput output, ExistingFileHelper helper) {
      super(output, "artifacts", helper);
   }

   public void registerSounds() {
      this.addSubtitled((SoundEvent)ModSoundEvents.MIMIC_OPEN.value()).with(sound(SoundEvents.CHEST_OPEN));
      this.addSubtitled((SoundEvent)ModSoundEvents.MIMIC_CLOSE.value()).with(sound(SoundEvents.CHEST_CLOSE));
      this.addSubtitled((SoundEvent)ModSoundEvents.MIMIC_HURT.value()).with(sounds("mimic/hurt", 3));
      this.addSubtitled((SoundEvent)ModSoundEvents.MIMIC_DEATH.value()).with(sounds("mimic/death", 2));
      this.add((SoundEvent)ModSoundEvents.WATER_STEP.value()).with(sound(SoundEvents.COD_FLOP)).subtitle("subtitles.block.generic.footsteps");
      this.addSubtitled((SoundEvent)ModSoundEvents.FART.value()).with(sounds("fart", 2));
      this.add((SoundEvent)ModSoundEvents.POP.value()).with(sound(ResourceLocation.parse("random/pop")).pitch(0.75).volume(0.5));
   }

   protected static Sound sound(SoundEvent soundEvent) {
      return sound(soundEvent.getLocation(), SoundType.EVENT);
   }

   protected static Sound sound(String path) {
      return sound(Artifacts.id(path));
   }

   protected static Sound[] sounds(String path, int count) {
      Sound[] result = new Sound[count];

      for (int i = 0; i < count; i++) {
         result[i] = sound(path + (i + 1));
      }

      return result;
   }

   private SoundDefinition add(SoundEvent soundEvent) {
      SoundDefinition result = definition();
      this.add(soundEvent, result);
      return result;
   }

   private SoundDefinition addSubtitled(SoundEvent soundEvent) {
      return this.add(soundEvent).subtitle("%s.subtitles.%s".formatted("artifacts", soundEvent.getLocation().getPath()));
   }
}
