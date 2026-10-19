package it.hurts.octostudios.reliquified_twilight_forest.network;

import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.Random;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ExecutionEffectPacket(int entityId, Color color) implements CustomPacketPayload {
   public static final Type<ExecutionEffectPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "execution_effect"));
   public static final StreamCodec<RegistryFriendlyByteBuf, ExecutionEffectPacket> STREAM_CODEC = CustomPacketPayload.codec(
      ExecutionEffectPacket::write, ExecutionEffectPacket::new
   );

   public ExecutionEffectPacket(RegistryFriendlyByteBuf buf) {
      this(buf.readInt(), new Color(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt()));
   }

   public void write(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.entityId);
      buf.writeInt(this.color.getRed());
      buf.writeInt(this.color.getGreen());
      buf.writeInt(this.color.getBlue());
      buf.writeInt(this.color.getAlpha());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(ExecutionEffectPacket packet, IPayloadContext ctx) {
      if (!ctx.flow().isServerbound()) {
         ctx.enqueueWork(
            () -> {
               Level level = ctx.player().level();
               Random random = new Random();
               if (level.getEntity(packet.entityId) instanceof LivingEntity entity) {
                  AABB aabb = entity.getBoundingBox();
                  double size = aabb.getSize();

                  for (int i = 0; i < size * 32.0; i++) {
                     level.addParticle(
                        ParticleUtils.constructSimpleSpark(packet.color(), 0.5F, entity.getRandom().nextInt(40, 60), random.nextFloat(0.9F, 0.97F)),
                        entity.getRandomX(0.5),
                        entity.getRandomY(),
                        entity.getRandomZ(0.5),
                        0.0,
                        random.nextFloat(0.005F, 0.05F),
                        0.0
                     );
                  }
               }
            }
         );
      }
   }
}
