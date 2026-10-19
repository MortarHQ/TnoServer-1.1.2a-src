package it.hurts.sskirillss.relics.network.packets.research;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.function.IntFunction;
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
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketManageLink implements CustomPacketPayload {
   private final int container;
   private final int slot;
   private final String ability;
   private final PacketManageLink.Operation operation;
   private final int from;
   private final int to;
   public static final Type<PacketManageLink> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "manage_link"));
   public static final StreamCodec<ByteBuf, PacketManageLink> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      PacketManageLink::getContainer,
      ByteBufCodecs.INT,
      PacketManageLink::getSlot,
      ByteBufCodecs.STRING_UTF8,
      PacketManageLink::getAbility,
      ByteBufCodecs.idMapper(PacketManageLink.Operation.BY_ID, PacketManageLink.Operation::getId),
      PacketManageLink::getOperation,
      ByteBufCodecs.INT,
      PacketManageLink::getFrom,
      ByteBufCodecs.INT,
      PacketManageLink::getTo,
      PacketManageLink::new
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
                     RandomSource var8 = player.getRandom();
                     switch (this.operation) {
                        case ADD:
                           relic.addResearchLink(stack, this.ability, this.from, this.to);
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
                                       var8.nextLong()
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
                                       0.75F + var8.nextFloat() * 0.5F,
                                       var8.nextLong()
                                    )
                                 );
                           }
                           break;
                        case REMOVE:
                           relic.removeResearchLink(stack, this.ability, this.from, this.to);
                     }

                     try {
                        player.containerMenu.getSlot(this.slot).set(stack);
                     } catch (Exception e) {
                        e.printStackTrace();
                        causeError(player);
                     }
                  } else {
                     causeError(player);
                  }
               }
            }
         }
      );
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

   public PacketManageLink.Operation getOperation() {
      return this.operation;
   }

   public int getFrom() {
      return this.from;
   }

   public int getTo() {
      return this.to;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketManageLink other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getContainer() != other.getContainer()) {
         return false;
      } else if (this.getSlot() != other.getSlot()) {
         return false;
      } else if (this.getFrom() != other.getFrom()) {
         return false;
      } else if (this.getTo() != other.getTo()) {
         return false;
      } else {
         Object this$ability = this.getAbility();
         Object other$ability = other.getAbility();
         if (this$ability == null ? other$ability == null : this$ability.equals(other$ability)) {
            Object this$operation = this.getOperation();
            Object other$operation = other.getOperation();
            return this$operation == null ? other$operation == null : this$operation.equals(other$operation);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketManageLink;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getContainer();
      result = result * 59 + this.getSlot();
      result = result * 59 + this.getFrom();
      result = result * 59 + this.getTo();
      Object $ability = this.getAbility();
      result = result * 59 + ($ability == null ? 43 : $ability.hashCode());
      Object $operation = this.getOperation();
      return result * 59 + ($operation == null ? 43 : $operation.hashCode());
   }

   @Override
   public String toString() {
      return "PacketManageLink(container="
         + this.getContainer()
         + ", slot="
         + this.getSlot()
         + ", ability="
         + this.getAbility()
         + ", operation="
         + this.getOperation()
         + ", from="
         + this.getFrom()
         + ", to="
         + this.getTo()
         + ")";
   }

   public PacketManageLink(int container, int slot, String ability, PacketManageLink.Operation operation, int from, int to) {
      this.container = container;
      this.slot = slot;
      this.ability = ability;
      this.operation = operation;
      this.from = from;
      this.to = to;
   }

   public enum Operation {
      ADD(0),
      REMOVE(1);

      public static final IntFunction<PacketManageLink.Operation> BY_ID = ByIdMap.continuous(
         PacketManageLink.Operation::getId, values(), OutOfBoundsStrategy.ZERO
      );
      private final int id;

      public int getId() {
         return this.id;
      }

      Operation(final int id) {
         this.id = id;
      }
   }
}
