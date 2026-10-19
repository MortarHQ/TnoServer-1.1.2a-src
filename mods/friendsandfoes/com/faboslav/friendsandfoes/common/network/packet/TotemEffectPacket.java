package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.faboslav.friendsandfoes.common.util.TotemUtil;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public record TotemEffectPacket(Item item, int entityId) implements Packet<TotemEffectPacket> {
   public static final ResourceLocation ID = FriendsAndFoes.makeID("totem_effect_packet");
   public static final ClientboundPacketType<TotemEffectPacket> TYPE = new TotemEffectPacket.Handler();

   public static void sendToClient(Player player, Item totem) {
      TotemEffectPacket totemEffectPacket = new TotemEffectPacket(totem, player.getId());
      MessageHandler.DEFAULT_CHANNEL.sendToPlayer(totemEffectPacket, player);
      MessageHandler.DEFAULT_CHANNEL.sendToAllLoaded(totemEffectPacket, player.level(), player.blockPosition());
   }

   public PacketType<TotemEffectPacket> type() {
      return TYPE;
   }

   public static class Handler implements ClientboundPacketType<TotemEffectPacket> {
      public ResourceLocation id() {
         return TotemEffectPacket.ID;
      }

      public Runnable handle(TotemEffectPacket packet) {
         return () -> {
            Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId());
            if (entity instanceof Entity) {
               Item item = packet.item;
               ItemStack itemStack = item.getDefaultInstance();
               if (item == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
                  TotemUtil.playActivateAnimation(itemStack, entity, (ParticleType<?>)FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
               } else if (item == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
                  TotemUtil.playActivateAnimation(itemStack, entity, (ParticleType<?>)FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING.get());
               }
            }
         };
      }

      public TotemEffectPacket decode(RegistryFriendlyByteBuf buf) {
         return new TotemEffectPacket((Item)((Reference)BuiltInRegistries.ITEM.getHolder(buf.readResourceLocation()).get()).value(), buf.readInt());
      }

      public void encode(TotemEffectPacket packet, RegistryFriendlyByteBuf buf) {
         buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(packet.item));
         buf.writeInt(packet.entityId);
      }
   }
}
