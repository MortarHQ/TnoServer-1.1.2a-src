package it.hurts.octostudios.reliquified_twilight_forest.network;

import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ParasiteEvolveParticlePacket(int entityID) implements CustomPacketPayload {
   public static final Type<ParasiteEvolveParticlePacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "parasite_particles")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, ParasiteEvolveParticlePacket> STREAM_CODEC = CustomPacketPayload.codec(
      ParasiteEvolveParticlePacket::write, ParasiteEvolveParticlePacket::new
   );

   public ParasiteEvolveParticlePacket(RegistryFriendlyByteBuf buf) {
      this(buf.readInt());
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityID);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(ParasiteEvolveParticlePacket packet, IPayloadContext ctx) {
      if (ctx.flow().isClientbound()) {
         ctx.enqueueWork(
            () -> {
               if (ctx.player().level().getEntity(packet.entityID()) instanceof LivingEntity living) {
                  Vec3 center = living.position().add(0.0, living.getBbHeight() / 2.0F, 0.0);

                  for (int i = 0; i < 150; i++) {
                     ParticleOptions options = ParticleUtils.constructSimpleSpark(
                        new Color(living.getRandom().nextInt(230, 255), living.getRandom().nextInt(230, 255), 0),
                        living.getRandom().nextFloat() / 2.0F,
                        living.getRandom().nextInt(120, 200) + 0,
                        0.9F
                     );
                     Vec3 pos = new Vec3(living.getRandomX(0.5), living.getRandomY(), living.getRandomZ(0.5));
                     living.level().addParticle(options, pos.x, pos.y, pos.z, (center.x - pos.x) / 8.0, (center.y - pos.y) / 8.0, (center.z - pos.z) / 8.0);
                  }
               }
            }
         );
      }
   }
}
