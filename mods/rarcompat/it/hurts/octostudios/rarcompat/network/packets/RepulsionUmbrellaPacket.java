package it.hurts.octostudios.rarcompat.network.packets;

import it.hurts.octostudios.rarcompat.items.UmbrellaItem;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class RepulsionUmbrellaPacket implements CustomPacketPayload {
   public static final Type<RepulsionUmbrellaPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("rarcompat", "repulsion"));
   public static final StreamCodec<RegistryFriendlyByteBuf, RepulsionUmbrellaPacket> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, RepulsionUmbrellaPacket>() {
      public void encode(RegistryFriendlyByteBuf buf, RepulsionUmbrellaPacket packet) {
      }

      @Nonnull
      public RepulsionUmbrellaPacket decode(@Nonnull RegistryFriendlyByteBuf buf) {
         return new RepulsionUmbrellaPacket();
      }
   };

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         ItemStack stack = player.getMainHandItem();
         if (stack.getItem() instanceof UmbrellaItem relic) {
            Level level = player.getCommandSenderWorld();
            level.playSound(null, player.blockPosition(), SoundEvents.PHANTOM_FLAP, SoundSource.MASTER, 1.0F, 1.0F + player.getRandom().nextFloat() * 0.25F);
            player.getCooldowns().addCooldown(relic, (int)(relic.getStatValue(stack, "glider", "cooldown") * 20.0));
            relic.spreadRelicExperience(player, stack, 1);
            relic.addCharges(stack, -1);
            Vec3 pos = player.position().add(player.getLookAngle());
            if (!level.isClientSide()) {
               ((ServerLevel)level).sendParticles(ParticleTypes.CLOUD, pos.x, pos.y, pos.z, 40, 0.5, 0.5, 0.5, 0.05F);
            }
         }
      });
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof RepulsionUmbrellaPacket other) ? false : other.canEqual(this);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RepulsionUmbrellaPacket;
   }

   @Override
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Override
   public String toString() {
      return "RepulsionUmbrellaPacket()";
   }
}
