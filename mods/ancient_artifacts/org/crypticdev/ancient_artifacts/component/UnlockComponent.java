package org.crypticdev.ancient_artifacts.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record UnlockComponent(List<String> unlocks) {
   public static final UnlockComponent DEFAULT = new UnlockComponent(new ArrayList<>());
   public static final Codec<UnlockComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.list(Codec.STRING).fieldOf("unlocks").forGetter(UnlockComponent::unlocks)).apply(instance, UnlockComponent::new)
   );
   public static final StreamCodec<ByteBuf, UnlockComponent> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()), UnlockComponent::unlocks, UnlockComponent::new
   );

   public UnlockComponent() {
      this(new ArrayList<>());
   }

   public UnlockComponent append(String unlock) {
      this.unlocks.add(unlock);
      return this;
   }

   public boolean has(String string) {
      return this.unlocks.contains(string);
   }
}
