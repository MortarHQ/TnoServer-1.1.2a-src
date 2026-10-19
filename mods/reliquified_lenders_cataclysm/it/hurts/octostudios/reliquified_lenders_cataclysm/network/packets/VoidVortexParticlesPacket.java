package it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets;

import io.netty.buffer.ByteBuf;
import it.hurts.octostudios.reliquified_lenders_cataclysm.entities.VoidVortexModifiedEntity;
import java.awt.Color;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record VoidVortexParticlesPacket(int vortexId, int targetId, int particlesID) implements CustomPacketPayload {
   public static final StreamCodec<ByteBuf, VoidVortexParticlesPacket> STREAM_CODEC = StreamCodec.ofMember(
      VoidVortexParticlesPacket::encode, VoidVortexParticlesPacket::decode
   );
   public static final Type<VoidVortexParticlesPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_lenders_cataclysm", "void_vortex_particles")
   );

   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static VoidVortexParticlesPacket decode(ByteBuf buf) {
      return new VoidVortexParticlesPacket(buf.readInt(), buf.readInt(), buf.readInt());
   }

   public void encode(ByteBuf buf) {
      buf.writeInt(this.vortexId);
      buf.writeInt(this.targetId);
      buf.writeInt(this.particlesID);
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         Level level = player.getCommandSenderWorld();
         if (level.isClientSide && level.getEntity(this.vortexId) instanceof VoidVortexModifiedEntity vortex) {
            switch (this.particlesID) {
               case 0:
                  if (!(level.getEntity(this.targetId) instanceof LivingEntity entity)) {
                     return;
                  }

                  spawnPullParticles(level, entity, vortex);
                  break;
               case 1:
                  spawnMergeParticles(level, vortex);
            }
         }
      });
   }

   private static void spawnPullParticles(Level level, Entity entity, VoidVortexModifiedEntity vortex) {
      if (!(entity instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying())) {
         Vec3 entityPos = entity.position();
         double step = 0.4F;
         if (entity.distanceTo(vortex) <= 0.5) {
            float radius = 0.5F;
            int particlesNum = (int)((Math.PI * 2) * radius / step);
            double angleIncrement = (Math.PI * 2) / particlesNum;

            for (int i = 0; i < particlesNum; i++) {
               double angle = i * angleIncrement;
               double x = entityPos.x + radius * Math.cos(angle);
               double y = entityPos.y + 0.1 + vortex.randomized(entity.getBoundingBox().getYsize());
               double z = entityPos.z + radius * Math.sin(angle);
               level.addParticle(vortex.getParticle(new Color(105, 0, 229), 0.15F), x, y, z, 0.0, 0.0, 0.0);
            }
         } else {
            Vec3 direction = vortex.position().subtract(entityPos).normalize();

            for (int i = 0; i < 8; i++) {
               Vec3 pos = entityPos.add(direction.scale(i * (step + 0.001F * vortex.getHeight())));
               double y = pos.y + 0.1;
               float diameterMin = 0.2F;
               if (entity instanceof VoidVortexModifiedEntity voidVortexEntity) {
                  y += voidVortexEntity.getHeight() * vortex.randomized(1.0);
                  diameterMin += 0.5F;
               } else {
                  y += vortex.randomized(entity.getBoundingBox().getYsize());
               }

               level.addParticle(
                  vortex.getParticle(new Color(112, 0, 156), diameterMin),
                  pos.x + vortex.randomized(entity.getBoundingBox().getXsize()),
                  y,
                  pos.z + vortex.randomized(entity.getBoundingBox().getZsize()),
                  0.0,
                  0.0,
                  0.0
               );
            }
         }
      }
   }

   private static void spawnMergeParticles(Level level, VoidVortexModifiedEntity vortex) {
      for (int i = 0; i < vortex.getHeight(); i++) {
         for (int j = 0; j < 24; j++) {
            level.addParticle(
               vortex.getParticle(new Color(61, 0, 135), 0.4F),
               vortex.getX() + Math.pow(-1.0, i) * vortex.randomized(2.0),
               vortex.getY() + 0.1 + i + vortex.randomized(0.4),
               vortex.getZ() + Math.pow(-1.0, i) * vortex.randomized(2.0),
               0.0,
               0.0,
               0.0
            );
         }
      }
   }
}
