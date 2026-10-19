package gardensofthedead.neoforge.datagen;

import gardensofthedead.GardensOfTheDead;
import gardensofthedead.registry.ModSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;

public class SoundDefinitionsProvider extends net.neoforged.neoforge.common.data.SoundDefinitionsProvider {
   public SoundDefinitionsProvider(PackOutput packOutput, ExistingFileHelper helper) {
      super(packOutput, "gardens_of_the_dead", helper);
   }

   public void registerSounds() {
      this.addWithSubtitle((SoundEvent)ModSoundEvents.WHISTLECANE_WHISTLE.get())
         .with(sound(GardensOfTheDead.id("block/whistlecane/whistle1")).attenuationDistance(32).weight(1))
         .with(sound(GardensOfTheDead.id("block/whistlecane/whistle2")).attenuationDistance(32).weight(2));
      this.addMusic();
   }

   private void addMusic() {
      this.addWithDefaultMusic((SoundEvent)ModSoundEvents.SOULBLIGHT_FOREST_MUSIC.get())
         .with(sound(GardensOfTheDead.id("music/soulblight_forest")).stream().volume(0.5).weight(7));
      this.addWithDefaultMusic((SoundEvent)ModSoundEvents.WHISTLING_WOODS_MUSIC.get())
         .with(sound(GardensOfTheDead.id("music/whistling_woods")).stream().volume(0.5).weight(7));
   }

   private SoundDefinition add(SoundEvent soundEvent) {
      SoundDefinition result = definition();
      this.add(soundEvent, result);
      return result;
   }

   private SoundDefinition addWithSubtitle(SoundEvent soundEvent) {
      return this.add(soundEvent).subtitle("%s.subtitle.%s".formatted("gardens_of_the_dead", soundEvent.getLocation().getPath()));
   }

   private SoundDefinition addWithDefaultMusic(SoundEvent soundEvent) {
      return this.add(soundEvent)
         .with(sound("music/game/nether/ballad_of_the_cats").stream())
         .with(sound("music/game/nether/concrete_halls").stream())
         .with(sound("music/game/nether/dead_voxel").stream())
         .with(sound("music/game/nether/warmth").stream());
   }
}
