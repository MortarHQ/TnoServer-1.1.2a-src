package com.github.L_Ender.cataclysm.blocks.Property;

import com.github.L_Ender.cataclysm.init.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument.Type;

public enum CustomNoteBlockInstrument {
   KOBOLEDIATOR("kobolediator", ModSounds.NOTE_BLOCK_IMITATE_KOBOLEDIATOR, Type.MOB_HEAD),
   APTRGANGR("aptrgangr", ModSounds.NOTE_BLOCK_IMITATE_APTRGANGR, Type.MOB_HEAD),
   DRAUGR("draugr", ModSounds.NOTE_BLOCK_IMITATE_DRAUGR, Type.MOB_HEAD);

   private final String string;
   private final Holder<SoundEvent> soundEvent;
   private final Type type;

   CustomNoteBlockInstrument(String string, Holder sound, Type type) {
      this.string = string;
      this.soundEvent = sound;
      this.type = type;
   }

   public Type getType() {
      return this.type;
   }

   public Holder<SoundEvent> getSoundEvent() {
      return this.soundEvent;
   }

   public String getString() {
      return this.string;
   }

   public NoteBlockInstrument get() {
      return NoteBlockInstrument.valueOf(this.name());
   }
}
