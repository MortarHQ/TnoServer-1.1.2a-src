package it.hurts.octostudios.rarcompat.network.packets;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.charm.CloudInBottleItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import javax.annotation.Nonnull;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class DoubleJumpPacket implements CustomPacketPayload {
   public static final Type<DoubleJumpPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("rarcompat", "check_double_jump"));
   public static final StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPacket> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPacket>() {
      public void encode(RegistryFriendlyByteBuf buf, DoubleJumpPacket packet) {
      }

      @Nonnull
      public DoubleJumpPacket decode(@Nonnull RegistryFriendlyByteBuf buf) {
         return new DoubleJumpPacket();
      }
   };

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            Player player = ctx.player();
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.CLOUD_IN_A_BOTTLE.value());
            if (stack.getItem() instanceof CloudInBottleItem relic) {
               relic.addCount(stack, 1);
               relic.spreadRelicExperience(player, stack, 1);
               player.hasImpulse = true;
               player.fallDistance = 0.0F;
               player.awardStat(Stats.JUMP);
               player.jumpFromGround();
               Level var8 = player.getCommandSenderWorld();

               for (int i = 0; i < 50; i++) {
                  double angle = (Math.PI * 2) * i / 50.0;
                  ((ServerLevel)var8)
                     .sendParticles(ParticleTypes.CLOUD, player.getX() + Math.cos(angle), player.getY(), player.getZ() + Math.sin(angle), 0, 0.0, 0.0, 0.0, 0.0);
               }

               var8.playSound(null, player.blockPosition(), SoundEvents.WOOL_PLACE, player.getSoundSource(), 1.0F, 0.75F + player.getRandom().nextFloat());
            }
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
