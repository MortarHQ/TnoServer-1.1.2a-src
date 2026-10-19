package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;

public class PlaySoundScreen extends AbstractStepEditorScreen {
   private static final String[] SOURCES = new String[]{"master", "music", "record", "weather", "block", "hostile", "neutral", "player", "ambient", "voice"};
   private final StepTextFieldHandle soundField = new StepTextFieldHandle("sound");
   private final StepTextFieldHandle volumeField = new StepTextFieldHandle("volume");
   private final StepTextFieldHandle pitchField = new StepTextFieldHandle("pitch");
   private int sourceIndex = 0;

   public PlaySoundScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent) {
      super(Component.translatable("ponderer.ui.play_sound"), scene, sceneIndex, parent);
   }

   public PlaySoundScreen(DslScene scene, int sceneIndex, SceneEditorScreen parent, int editIndex, DslScene.DslStep step) {
      super(Component.translatable("ponderer.ui.play_sound"), scene, sceneIndex, parent, editIndex, step);
   }

   @Override
   protected String getHeaderTitle() {
      return UIText.of("ponderer.ui.play_sound");
   }

   @Override
   protected void collectStepEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.text(
            this.soundField, "ponderer.ui.play_sound.sound", "ponderer.ui.play_sound.sound.tooltip", UIText.of("ponderer.ui.play_sound.sound.hint"), 140
         )
      );
      entries.add(FieldSpecs.number(this.volumeField, "ponderer.ui.play_sound.volume", "ponderer.ui.play_sound.volume.tooltip", "1.0", 50, null));
      entries.add(FieldSpecs.number(this.pitchField, "ponderer.ui.play_sound.pitch", "ponderer.ui.play_sound.pitch.tooltip", "1.0", 50, null));
      entries.add(
         FieldSpecs.choice(
            "ponderer.ui.play_sound.source",
            "ponderer.ui.play_sound.source.tooltip",
            100,
            () -> this.sourceIndex = (this.sourceIndex + 1) % SOURCES.length,
            () -> this.sourceLabel(SOURCES[this.sourceIndex])
         )
      );
   }

   @Override
   protected void populateFromStep(DslScene.DslStep step) {
      super.populateFromStep(step);
      if (step.sound != null) {
         this.soundField.setValue(step.sound);
      }

      if (step.soundVolume != null) {
         this.volumeField.setValue(String.valueOf(step.soundVolume));
      }

      if (step.pitch != null) {
         this.pitchField.setValue(String.valueOf(step.pitch));
      }

      if (step.source != null) {
         for (int i = 0; i < SOURCES.length; i++) {
            if (SOURCES[i].equalsIgnoreCase(step.source)) {
               this.sourceIndex = i;
               break;
            }
         }
      }
   }

   private String sourceLabel(String value) {
      String key = "ponderer.ui.play_sound.source." + value;
      String translated = UIText.of(key);
      return key.equals(translated) ? value : translated;
   }

   @Override
   protected String getStepType() {
      return "play_sound";
   }

   @Override
   protected void appendCustomSnapshot(Map<String, String> snapshot) {
      snapshot.put("sourceIndex", String.valueOf(this.sourceIndex));
   }

   @Override
   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
      if (snapshot.containsKey("sourceIndex")) {
         try {
            this.sourceIndex = Integer.parseInt(snapshot.get("sourceIndex"));
         } catch (NumberFormatException var3) {
         }
      }
   }

   @Nullable
   @Override
   protected DslScene.DslStep buildStep() {
      this.clearStatusMessages();
      String sound = this.soundField.getValue().trim();
      if (sound.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.play_sound.error.required"));
         return null;
      }

      DslScene.DslStep s = new DslScene.DslStep();
      s.type = "play_sound";
      s.sound = sound;
      float vol = (float)this.parseDoubleOr(this.volumeField.getValue(), 1.0);
      if (vol != 1.0F) {
         s.soundVolume = vol;
      }

      float p = (float)this.parseDoubleOr(this.pitchField.getValue(), 1.0);
      if (p != 1.0F) {
         s.pitch = p;
      }

      if (this.sourceIndex > 0) {
         s.source = SOURCES[this.sourceIndex];
      }

      return s;
   }
}
