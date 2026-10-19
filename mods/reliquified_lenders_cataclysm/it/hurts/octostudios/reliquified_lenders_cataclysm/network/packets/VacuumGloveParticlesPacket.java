package it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets;

import io.netty.buffer.ByteBuf;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.RECParticleUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record VacuumGloveParticlesPacket(double radius, int targetId, double x, double y, double z) implements CustomPacketPayload {
   public static final StreamCodec<ByteBuf, VacuumGloveParticlesPacket> STREAM_CODEC = StreamCodec.ofMember(
      VacuumGloveParticlesPacket::encode, VacuumGloveParticlesPacket::decode
   );
   public static final Type<VacuumGloveParticlesPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_lenders_cataclysm", "vacuum_glove_particles")
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static VacuumGloveParticlesPacket decode(ByteBuf buf) {
      return new VacuumGloveParticlesPacket(buf.readDouble(), buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   public void encode(ByteBuf buf) {
      buf.writeDouble(this.radius);
      buf.writeInt(this.targetId);
      buf.writeDouble(this.x);
      buf.writeDouble(this.y);
      buf.writeDouble(this.z);
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            Player player = ctx.player();
            Level level = player.getCommandSenderWorld();
            if (level.isClientSide() && level.getEntity(this.targetId) instanceof LivingEntity entity) {
               RECParticleUtils.createCircleSegment(
                  ParticleUtils.constructSimpleSpark(new Color(111, 24, 157), 0.3F, 1, 0.8F),
                  level,
                  new Vec3(this.x, this.y, this.z),
                  entity.position(),
                  this.radius,
                  0.3F
               );
            }
         }
      );
   }
}
