package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.p3pp3rf1y.sophisticatedcore.init.ModParticles;

public class JukeboxUpgradeNoteParticleData extends ParticleType<JukeboxUpgradeNoteParticleData> implements ParticleOptions {
   private final MapCodec<JukeboxUpgradeNoteParticleData> codec = MapCodec.unit(this::getType);
   private final StreamCodec<RegistryFriendlyByteBuf, JukeboxUpgradeNoteParticleData> streamCodec = StreamCodec.unit(this);

   public JukeboxUpgradeNoteParticleData() {
      super(false);
   }

   public JukeboxUpgradeNoteParticleData getType() {
      return ModParticles.JUKEBOX_NOTE.get();
   }

   public MapCodec<JukeboxUpgradeNoteParticleData> codec() {
      return this.codec;
   }

   public StreamCodec<? super RegistryFriendlyByteBuf, JukeboxUpgradeNoteParticleData> streamCodec() {
      return this.streamCodec;
   }
}
