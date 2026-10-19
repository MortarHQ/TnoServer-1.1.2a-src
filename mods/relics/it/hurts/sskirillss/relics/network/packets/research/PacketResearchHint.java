package it.hurts.sskirillss.relics.network.packets.research;

import com.google.common.collect.Multimap;
import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.Map.Entry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketResearchHint implements CustomPacketPayload {
   private final int container;
   private final int slot;
   private final String ability;
   private final int amount;
   public static final Type<PacketResearchHint> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "research_hint"));
   public static final StreamCodec<ByteBuf, PacketResearchHint> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      PacketResearchHint::getContainer,
      ByteBufCodecs.INT,
      PacketResearchHint::getSlot,
      ByteBufCodecs.STRING_UTF8,
      PacketResearchHint::getAbility,
      ByteBufCodecs.INT,
      PacketResearchHint::getAmount,
      PacketResearchHint::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            if (!ctx.player().level().isClientSide()) {
               ServerPlayer player = (ServerPlayer)ctx.player();
               if (player.containerMenu.containerId != this.container) {
                  causeError(player);
               } else {
                  ItemStack stack = DescriptionUtils.gatherRelicStack(player, this.slot);
                  if (stack.getItem() instanceof IRelicItem relic) {
                     RandomSource var9 = player.getRandom();
                     int cost = relic.getResearchHintPlayerExperienceCost(this.ability) * this.amount;
                     if (EntityUtils.getPlayerTotalExperience(player) >= cost) {
                        player.giveExperiencePoints(-cost);
                        this.research(stack, this.amount);
                        if (relic.testAbilityResearch(stack, this.ability)) {
                           relic.setAbilityResearched(stack, this.ability, true);
                           player.connection
                              .send(
                                 new ClientboundSoundPacket(
                                    Holder.direct((SoundEvent)SoundRegistry.FINISH_RESEARCH.get()),
                                    SoundSource.PLAYERS,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    1.0F,
                                    1.0F,
                                    var9.nextLong()
                                 )
                              );
                        } else {
                           player.connection
                              .send(
                                 new ClientboundSoundPacket(
                                    Holder.direct((SoundEvent)SoundRegistry.CONNECT_STARS.get()),
                                    SoundSource.PLAYERS,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    0.75F,
                                    0.75F + var9.nextFloat() * 0.5F,
                                    var9.nextLong()
                                 )
                              );
                        }

                        try {
                           player.containerMenu.getSlot(this.slot).set(stack);
                        } catch (Exception e) {
                           e.printStackTrace();
                           causeError(player);
                        }
                     }
                  } else {
                     causeError(player);
                  }
               }
            }
         }
      );
   }

   public void research(ItemStack stack, int amount) {
      if (stack.getItem() instanceof IRelicItem relic) {
         Multimap var11 = relic.getResearchData(this.ability).getLinks();
         Multimap links = relic.getResearchLinks(stack, this.ability);
         int iteration = 0;

         for (Entry<Integer, Integer> entry : links.entries()) {
            Integer start = entry.getKey();
            Integer end = entry.getValue();
            if (!var11.containsEntry(start, end) && !var11.containsEntry(end, start)) {
               relic.removeResearchLink(stack, this.ability, start, end);
               if (++iteration >= amount) {
                  break;
               }
            }
         }

         iteration = 0;

         for (Entry<Integer, Integer> entry : var11.entries()) {
            Integer start = entry.getKey();
            Integer end = entry.getValue();
            if (!links.containsEntry(start, end) && !links.containsEntry(end, start)) {
               relic.addResearchLink(stack, this.ability, start, end);
               if (++iteration >= amount) {
                  break;
               }
            }
         }
      }
   }

   private static void causeError(Player player) {
      player.displayClientMessage(Component.translatable("info.relics.researching.wrong_container").withStyle(ChatFormatting.RED), false);
      player.closeContainer();
   }

   public int getContainer() {
      return this.container;
   }

   public int getSlot() {
      return this.slot;
   }

   public String getAbility() {
      return this.ability;
   }

   public int getAmount() {
      return this.amount;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketResearchHint other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         if (this.getContainer() != other.getContainer()) {
            return false;
         }

         if (this.getSlot() != other.getSlot()) {
            return false;
         }

         if (this.getAmount() != other.getAmount()) {
            return false;
         }

         Object this$ability = this.getAbility();
         Object other$ability = other.getAbility();
         return this$ability == null ? other$ability == null : this$ability.equals(other$ability);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketResearchHint;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getContainer();
      result = result * 59 + this.getSlot();
      result = result * 59 + this.getAmount();
      Object $ability = this.getAbility();
      return result * 59 + ($ability == null ? 43 : $ability.hashCode());
   }

   @Override
   public String toString() {
      return "PacketResearchHint(container="
         + this.getContainer()
         + ", slot="
         + this.getSlot()
         + ", ability="
         + this.getAbility()
         + ", amount="
         + this.getAmount()
         + ")";
   }

   public PacketResearchHint(int container, int slot, String ability, int amount) {
      this.container = container;
      this.slot = slot;
      this.ability = ability;
      this.amount = amount;
   }
}
