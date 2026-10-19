package com.mongoose.royalvariations.common.attachments;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class RVAttachment {
   public static final Codec<RVAttachment> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.fieldOf("flashTick").forGetter(o -> o.flash),
            Codec.INT.fieldOf("markedTick").forGetter(o -> o.marked),
            Codec.INT.fieldOf("gazeTick").forGetter(o -> o.gaze)
         )
         .apply(instance, RVAttachment::new)
   );
   private int flash = 0;
   private int marked = 0;
   private int gaze = 0;

   public RVAttachment() {
      this(0, 0, 0);
   }

   public RVAttachment(int flash, int marked, int gaze) {
      this.flash = flash;
      this.marked = marked;
      this.gaze = gaze;
   }

   public int getFlashTick() {
      return this.flash;
   }

   public void setFlashTick(int tick) {
      this.flash = tick;
   }

   public int getMarkedTick() {
      return this.marked;
   }

   public void setMarkedTick(int tick) {
      this.marked = tick;
   }

   public int getPressingGazeTick() {
      return this.gaze;
   }

   public void setPressingGazeTick(int tick) {
      this.gaze = tick;
   }
}
