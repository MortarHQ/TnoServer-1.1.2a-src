package net.unusual.block_factorys_bosses.data.util;

import java.util.Objects;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class BossesRiseSoundProvider extends SoundDefinitionsProvider {
   protected BossesRiseSoundProvider(PackOutput output, ExistingFileHelper helper) {
      super(output, "block_factorys_bosses", helper);
   }

   public void generateNewSoundWithSubtitle(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, String subtitle, float volume, float pitch) {
      this.generateNewSound(event, baseSoundDirectory, numberOfSounds, volume, pitch, subtitle);
   }

   public void generateNewSoundWithSubtitle(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, String subtitle) {
      this.generateNewSound(event, baseSoundDirectory, numberOfSounds, 1.0F, 1.0F, subtitle);
   }

   public void generateNewSound(Holder<SoundEvent> event, int numberOfSounds, float volume, float pitch, @Nullable String subtitle) {
      String[] splitSoundName = Objects.requireNonNull(event.getKey()).location().getPath().split("\\.", 3);
      if (splitSoundName.length == 3) {
         this.generateNewSound(event, splitSoundName[1] + "/" + splitSoundName[2], numberOfSounds, volume, pitch, subtitle);
      } else if (splitSoundName.length == 2) {
         this.generateNewSound(event, splitSoundName[1], numberOfSounds, volume, pitch, subtitle);
      }
   }

   public void generateNewSound(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, float volume, float pitch, @Nullable String subtitle) {
      SoundDefinition definition = SoundDefinition.definition();
      if (subtitle != null) {
         this.createSubtitleAndLangEntry(event, definition, subtitle);
      }

      for (int i = 1; i <= numberOfSounds; i++) {
         definition.with(Sound.sound(BossesRise.prefix(baseSoundDirectory + (numberOfSounds > 1 ? "_" + i : "")), SoundType.SOUND).volume(volume).pitch(pitch));
      }

      this.add(event, definition);
   }

   public void generateNewSoundMC(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, @Nullable String subtitle) {
      SoundDefinition definition = SoundDefinition.definition();
      if (subtitle != null) {
         this.createSubtitleAndLangEntry(event, definition, subtitle);
      }

      for (int i = 1; i <= numberOfSounds; i++) {
         definition.with(Sound.sound(ResourceLocation.withDefaultNamespace(baseSoundDirectory + (numberOfSounds > 1 ? i : "")), SoundType.SOUND));
      }

      this.add(event, definition);
   }

   public void generateExistingSoundWithSubtitle(Holder<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
      this.generateExistingSoundWithSubtitle(event, referencedSound, subtitle, 1.0F, 1.0F);
   }

   public void generateSoundWithExistingSubtitle(Holder<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
      this.add(event, SoundDefinition.definition().subtitle(subtitle).with(Sound.sound(referencedSound.getLocation(), SoundType.EVENT)));
   }

   public void generateExistingSoundWithSubtitle(Holder<SoundEvent> event, SoundEvent referencedSound, String subtitle, float volume, float pitch) {
      SoundDefinition definition = SoundDefinition.definition();
      this.createSubtitleAndLangEntry(event, definition, subtitle);
      this.add(event, definition.with(Sound.sound(referencedSound.getLocation(), SoundType.EVENT).volume(volume).pitch(pitch)));
   }

   public void generateExistingSoundWithSubtitle(Holder<SoundEvent> event, SoundEvent referencedSound, float volume, float pitch) {
      this.add(event, SoundDefinition.definition().with(Sound.sound(referencedSound.getLocation(), SoundType.EVENT).volume(volume).pitch(pitch)));
   }

   public void makeStepSound(Holder<SoundEvent> event, SoundEvent referencedSound) {
      this.add(
         event, SoundDefinition.definition().subtitle("subtitles.block.generic.footsteps").with(Sound.sound(referencedSound.getLocation(), SoundType.EVENT))
      );
   }

   public void makeNewStepSound(Holder<SoundEvent> event, int numberOfSounds) {
      String[] splitSoundName = Objects.requireNonNull(event.getKey()).location().getPath().split("\\.", 3);
      if (splitSoundName.length == 3) {
         this.makeNewStepSound(event, splitSoundName[1] + "/" + splitSoundName[2], numberOfSounds);
      } else if (splitSoundName.length == 2) {
         this.makeNewStepSound(event, splitSoundName[1], numberOfSounds);
      }
   }

   public void makeNewStepSound(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds) {
      SoundDefinition definition = SoundDefinition.definition();

      for (int i = 1; i <= numberOfSounds; i++) {
         definition.with(Sound.sound(BossesRise.prefix(baseSoundDirectory + (numberOfSounds > 1 ? "_" + i : "")), SoundType.SOUND));
      }

      this.add(event, definition.subtitle("subtitles.block.generic.footsteps"));
   }

   public void makeNewGenericSound(Holder<SoundEvent> event, String baseSoundDirectory, int numberOfSounds, @Nullable String type) {
      SoundDefinition definition = SoundDefinition.definition();

      for (int i = 1; i <= numberOfSounds; i++) {
         definition.with(Sound.sound(BossesRise.prefix(baseSoundDirectory + (numberOfSounds > 1 ? "_" + i : "")), SoundType.SOUND));
      }

      this.add(event, type != null ? definition.subtitle("subtitles.block.generic." + type) : definition);
   }

   public void makeMusicDisc(Holder<SoundEvent> event, String discName) {
      this.add(event, SoundDefinition.definition().with(Sound.sound(BossesRise.prefix("music/" + discName), SoundType.SOUND).stream()));
   }

   public void generateParrotSound(Holder<SoundEvent> event, SoundEvent referencedSound, String subtitle) {
      SoundDefinition definition = SoundDefinition.definition();
      this.createSubtitleAndLangEntry(event, definition, subtitle);
      this.add(event, definition.with(Sound.sound(referencedSound.getLocation(), SoundType.EVENT).pitch(1.8F).volume(0.6F)));
   }

   public void generateMusicSound(Holder<SoundEvent> event) {
      this.generateMusicSound(event, Objects.requireNonNull(event.getKey()).location().getPath().split("\\.", 2)[1]);
   }

   public void generateMusicSound(Holder<SoundEvent> event, String path) {
      this.add(event, SoundDefinition.definition().with(Sound.sound(BossesRise.prefix("music/" + path), SoundType.SOUND).stream()));
   }

   protected void add(Holder<SoundEvent> soundEvent, SoundDefinition definition) {
      super.add((SoundEvent)soundEvent.value(), definition);
   }

   private void createSubtitleAndLangEntry(Holder<SoundEvent> event, SoundDefinition definition, String subtitle) {
      String[] splitSoundName = Objects.requireNonNull(event.getKey()).location().getPath().split("\\.", 2);
      String subtitleKey = "subtitles.block_factorys_bosses." + splitSoundName[0] + "." + splitSoundName[1];
      definition.subtitle(subtitleKey);
      BossesRiseLangProvider.AUTO_GENERATOR.put(subtitleKey, subtitle);
   }
}
