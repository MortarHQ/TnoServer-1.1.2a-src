package com.bobmowzie.mowziesmobs.client.sound;

import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvent;

public class SculptorBossMusic extends BossMusic<EntitySculptor> {
   protected static SoundEvent soundEventIntro = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_INTRO.get();
   protected static SoundEvent soundEventLevel1_1 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL1_1.get();
   protected static SoundEvent soundEventLevel1_2 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL1_2.get();
   protected static SoundEvent soundEventLevel2_1 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL2_1.get();
   protected static SoundEvent soundEventLevel2_2 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL2_2.get();
   protected static SoundEvent soundEventTransition = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_TRANSITION.get();
   protected static SoundEvent soundEventLevel3_1 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL3_1.get();
   protected static SoundEvent soundEventLevel3_2 = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_LEVEL3_2.get();
   protected static SoundEvent soundEventEnding = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_ENDING.get();
   protected static SoundEvent soundEventOutro = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_OUTRO.get();
   protected static SoundEvent soundEventCombat = (SoundEvent)MMSounds.MUSIC_SCULPTOR_THEME_COMBAT.get();
   protected BossMusicSound soundIntro;
   protected BossMusicSound soundTransition;
   protected BossMusicSound soundEnding;
   protected BossMusicSound soundOutro;
   protected BossMusicSound currentSound;
   private int ticksInSection;
   private static final SortedMap<SculptorBossMusic.SculptorMusicSection, Float> SECTION_HEIGHTS = new TreeMap<>();
   private static final Map<SculptorBossMusic.SculptorMusicSection, SoundEvent> SECTION_SOUNDS = new HashMap<>();
   private SculptorBossMusic.SculptorMusicSection currentSection;

   public SculptorBossMusic() {
      super(null);
   }

   @Override
   public void tick() {
      super.tick();
      this.ticksInSection++;
      if (this.getBoss() != null) {
         if (this.currentSection == SculptorBossMusic.SculptorMusicSection.INTRO && this.ticksInSection == 35) {
            this.startMainTrack();
            return;
         }

         if (this.currentSection == SculptorBossMusic.SculptorMusicSection.TRANSITION && this.ticksInSection == 512) {
            if (this.getBoss().playerProgress() > SECTION_HEIGHTS.get(SculptorBossMusic.SculptorMusicSection.LEVEL3_2)) {
               this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.LEVEL3_2);
               return;
            }

            if (this.getBoss().playerProgress() > SECTION_HEIGHTS.get(SculptorBossMusic.SculptorMusicSection.LEVEL3_1)) {
               this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.LEVEL3_1);
               return;
            }
         }

         if (this.currentSection == SculptorBossMusic.SculptorMusicSection.ENDING && this.ticksInSection % 64 == 0 && this.getBoss().isTestPassed()) {
            this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.OUTRO, false);
            return;
         }

         if ((
               this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL1_1
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL1_2
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL2_1
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL2_2
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.TRANSITION
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL3_1
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.LEVEL3_2
                  || this.currentSection == SculptorBossMusic.SculptorMusicSection.ENDING
            )
            && this.ticksInSection % 128 == 0) {
            this.measureBreak();
         }

         if (this.currentSection != SculptorBossMusic.SculptorMusicSection.COMBAT && this.ticksInSection % 128 == 0 && this.getBoss().isFighting()) {
            this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.COMBAT);
         }
      }
   }

   private void startMainTrack() {
      this.ticksInSection = 0;
      if (this.getBoss() != null && this.getBoss().isFighting()) {
         this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.COMBAT);
      } else {
         this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.LEVEL1_1);
      }
   }

   private void measureBreak() {
      if (this.getBoss().isTestPassed()) {
         this.changeLevelSection(SculptorBossMusic.SculptorMusicSection.OUTRO, false);
      } else {
         SculptorBossMusic.SculptorMusicSection currentSectionIgnoreTransition = this.currentSection;
         if (this.currentSection == SculptorBossMusic.SculptorMusicSection.TRANSITION) {
            currentSectionIgnoreTransition = SculptorBossMusic.SculptorMusicSection.LEVEL3_1;
         }

         float playerProgress = this.getBoss().playerProgress();
         float currentSectionHeight = SECTION_HEIGHTS.get(currentSectionIgnoreTransition);
         SculptorBossMusic.SculptorMusicSection nextSection = SculptorBossMusic.SculptorMusicSection.LEVEL1_1;

         for (Entry<SculptorBossMusic.SculptorMusicSection, Float> sectionHeight : SECTION_HEIGHTS.entrySet()) {
            SculptorBossMusic.SculptorMusicSection section = sectionHeight.getKey();
            float height = sectionHeight.getValue();
            if (currentSectionHeight >= height) {
               height = (float)(height - 0.05);
            }

            if (playerProgress > height) {
               nextSection = section;
            }
         }

         if (nextSection != currentSectionIgnoreTransition) {
            if (!currentSectionIgnoreTransition.isHigherThan(SculptorBossMusic.SculptorMusicSection.TRANSITION)
               && (nextSection == SculptorBossMusic.SculptorMusicSection.LEVEL3_1 || nextSection == SculptorBossMusic.SculptorMusicSection.LEVEL3_2)) {
               nextSection = SculptorBossMusic.SculptorMusicSection.TRANSITION;
            }

            this.changeLevelSection(nextSection);
         }
      }
   }

   private void changeLevelSection(SculptorBossMusic.SculptorMusicSection section) {
      this.changeLevelSection(section, true);
   }

   private void changeLevelSection(SculptorBossMusic.SculptorMusicSection section, boolean loop) {
      if (this.currentSound != null) {
         this.currentSound.fadeOut();
      }

      SoundEvent requestedSoundEvent = SECTION_SOUNDS.get(section);
      this.currentSound = new BossMusicSound(requestedSoundEvent, this.getBoss(), this, loop);
      Minecraft.getInstance().getSoundManager().play(this.currentSound);
      this.currentSection = section;
      this.ticksInSection = 0;
   }

   @Override
   public void play() {
      super.play();
      this.currentSection = SculptorBossMusic.SculptorMusicSection.INTRO;
      this.soundIntro = new BossMusicSound(soundEventIntro, this.getBoss(), this, false);
      Minecraft.getInstance().getSoundManager().play(this.soundIntro);
      this.ticksInSection = 0;
   }

   @Override
   public void stop() {
      if (this.soundIntro != null) {
         this.soundIntro.doStop();
      }

      if (this.soundTransition != null) {
         this.soundTransition.doStop();
      }

      if (this.soundEnding != null) {
         this.soundEnding.doStop();
      }

      if (this.soundOutro != null) {
         this.soundOutro.doStop();
      }

      if (this.currentSound != null) {
         this.currentSound.doStop();
      }

      super.stop();
   }

   static {
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL1_1, 0.0F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL1_2, 0.1F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL2_1, 0.35F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL2_2, 0.5F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL3_1, 0.65F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.LEVEL3_2, 0.825F);
      SECTION_HEIGHTS.put(SculptorBossMusic.SculptorMusicSection.ENDING, 0.98F);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.INTRO, soundEventIntro);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL1_1, soundEventLevel1_1);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL1_2, soundEventLevel1_2);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL2_1, soundEventLevel2_1);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL2_2, soundEventLevel2_2);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.TRANSITION, soundEventTransition);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL3_1, soundEventLevel3_1);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.LEVEL3_2, soundEventLevel3_2);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.ENDING, soundEventEnding);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.OUTRO, soundEventOutro);
      SECTION_SOUNDS.put(SculptorBossMusic.SculptorMusicSection.COMBAT, soundEventCombat);
   }

   private enum SculptorMusicSection {
      COMBAT(-1),
      INTRO(0),
      LEVEL1_1(1),
      LEVEL1_2(2),
      LEVEL2_1(3),
      LEVEL2_2(4),
      TRANSITION(5),
      LEVEL3_1(6),
      LEVEL3_2(7),
      ENDING(8),
      OUTRO(9);

      private Integer order;

      SculptorMusicSection(int order) {
         this.order = order;
      }

      public boolean isHigherThan(SculptorBossMusic.SculptorMusicSection other) {
         return this.order > other.order;
      }
   }
}
