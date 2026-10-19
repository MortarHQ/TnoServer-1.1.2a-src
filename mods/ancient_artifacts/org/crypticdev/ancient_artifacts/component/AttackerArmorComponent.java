package org.crypticdev.ancient_artifacts.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record AttackerArmorComponent(int attackerArmor) {
   public static final AttackerArmorComponent DEFAULT = new AttackerArmorComponent(0);
   public static final Codec<AttackerArmorComponent> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(Codec.INT.fieldOf("attacker_armor").forGetter(AttackerArmorComponent::attackerArmor))
         .apply(instance, AttackerArmorComponent::new)
   );
   public static final StreamCodec<ByteBuf, AttackerArmorComponent> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, AttackerArmorComponent::attackerArmor, AttackerArmorComponent::new
   );
}
