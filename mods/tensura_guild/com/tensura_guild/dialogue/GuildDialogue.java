package com.tensura_guild.dialogue;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record GuildDialogue(List<GuildDialogue.Line> lines) {
   public static final Codec<GuildDialogue> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(GuildDialogue.Line.CODEC.listOf().fieldOf("lines").forGetter(GuildDialogue::lines)).apply(instance, GuildDialogue::new)
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, GuildDialogue> STREAM_CODEC = GuildDialogue.Line.STREAM_CODEC
      .apply(ByteBufCodecs.list())
      .map(GuildDialogue::new, GuildDialogue::lines);

   public record Line(String speaker, String text, boolean speakerTranslatable, boolean textTranslatable) {
      public static final Codec<GuildDialogue.Line> CODEC = GuildDialogue.Line.SerializedLine.CODEC
         .xmap(GuildDialogue.Line.SerializedLine::toLine, GuildDialogue.Line::toSerialized)
         .validate(GuildDialogue.Line::validate);
      public static final StreamCodec<RegistryFriendlyByteBuf, GuildDialogue.Line> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         GuildDialogue.Line::speaker,
         ByteBufCodecs.STRING_UTF8,
         GuildDialogue.Line::text,
         ByteBufCodecs.BOOL,
         GuildDialogue.Line::speakerTranslatable,
         ByteBufCodecs.BOOL,
         GuildDialogue.Line::textTranslatable,
         GuildDialogue.Line::new
      );

      public Line(String speaker, String text) {
         this(speaker, text, false, false);
      }

      private static DataResult<GuildDialogue.Line> validate(GuildDialogue.Line line) {
         if (line.speaker().isEmpty()) {
            return DataResult.error(() -> "Guild dialogue line needs speaker or speaker_key");
         } else {
            return line.text().isEmpty() ? DataResult.error(() -> "Guild dialogue line needs text or text_key") : DataResult.success(line);
         }
      }

      private GuildDialogue.Line.SerializedLine toSerialized() {
         return new GuildDialogue.Line.SerializedLine(
            this.speakerTranslatable ? Optional.empty() : Optional.of(this.speaker),
            this.speakerTranslatable ? Optional.of(this.speaker) : Optional.empty(),
            this.textTranslatable ? Optional.empty() : Optional.of(this.text),
            this.textTranslatable ? Optional.of(this.text) : Optional.empty()
         );
      }

      private record SerializedLine(Optional<String> speaker, Optional<String> speakerKey, Optional<String> text, Optional<String> textKey) {
         private static final Codec<GuildDialogue.Line.SerializedLine> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                  Codec.STRING.optionalFieldOf("speaker").forGetter(GuildDialogue.Line.SerializedLine::speaker),
                  Codec.STRING.optionalFieldOf("speaker_key").forGetter(GuildDialogue.Line.SerializedLine::speakerKey),
                  Codec.STRING.optionalFieldOf("text").forGetter(GuildDialogue.Line.SerializedLine::text),
                  Codec.STRING.optionalFieldOf("text_key").forGetter(GuildDialogue.Line.SerializedLine::textKey)
               )
               .apply(instance, GuildDialogue.Line.SerializedLine::new)
         );

         private GuildDialogue.Line toLine() {
            return new GuildDialogue.Line(
               this.speakerKey.orElseGet(() -> this.speaker.orElse("")),
               this.textKey.orElseGet(() -> this.text.orElse("")),
               this.speakerKey.isPresent(),
               this.textKey.isPresent()
            );
         }
      }
   }
}
