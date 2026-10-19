package it.hurts.sskirillss.relics.network.packets.leveling;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import java.util.function.IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketRelicTweak implements CustomPacketPayload {
   private final int container;
   private final int slot;
   private final String ability;
   private final PacketRelicTweak.Operation operation;
   private final boolean withShift;
   public static final Type<PacketRelicTweak> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "relic_tweak"));
   public static final StreamCodec<ByteBuf, PacketRelicTweak> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      PacketRelicTweak::getContainer,
      ByteBufCodecs.INT,
      PacketRelicTweak::getSlot,
      ByteBufCodecs.STRING_UTF8,
      PacketRelicTweak::getAbility,
      ByteBufCodecs.idMapper(PacketRelicTweak.Operation.BY_ID, PacketRelicTweak.Operation::getId),
      PacketRelicTweak::getOperation,
      ByteBufCodecs.BOOL,
      PacketRelicTweak::isWithShift,
      PacketRelicTweak::new
   );

   public PacketRelicTweak(int container, int slot, String ability, PacketRelicTweak.Operation operation) {
      this(container, slot, ability, operation, false);
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(() -> {
         Player player = ctx.player();
         if (player.containerMenu.containerId != this.container) {
            causeError(player);
         } else {
            ItemStack stack = DescriptionUtils.gatherRelicStack(player, this.slot);
            if (stack.getItem() instanceof IRelicItem relic) {
               AbilityData entry = relic.getAbilityData(this.ability);
               if (entry != null) {
                  switch (this.operation) {
                     case RESET:
                        if (!relic.reset(player, stack, this.ability)) {
                           return;
                        }
                        break;
                     case UPGRADE:
                        boolean result = false;
                        if (this.withShift) {
                           while (relic.upgrade(player, stack, this.ability)) {
                              result = true;
                           }
                        } else {
                           result = relic.upgrade(player, stack, this.ability);
                        }

                        if (!result) {
                           return;
                        }
                        break;
                     case REROLL:
                        boolean result = false;
                        if (this.withShift) {
                           while (relic.getAbilityQuality(stack, this.ability) != relic.getMaxQuality() && relic.reroll(player, stack, this.ability)) {
                              result = true;
                           }
                        } else {
                           result = relic.reroll(player, stack, this.ability);
                        }

                        if (!result) {
                           return;
                        }
                        break;
                     default:
                        throw new MatchException(null, null);
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
      });
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

   public PacketRelicTweak.Operation getOperation() {
      return this.operation;
   }

   public boolean isWithShift() {
      return this.withShift;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketRelicTweak other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getContainer() != other.getContainer()) {
         return false;
      } else if (this.getSlot() != other.getSlot()) {
         return false;
      } else if (this.isWithShift() != other.isWithShift()) {
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
      return other instanceof PacketRelicTweak;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getContainer();
      result = result * 59 + this.getSlot();
      result = result * 59 + (this.isWithShift() ? 79 : 97);
      Object $ability = this.getAbility();
      result = result * 59 + ($ability == null ? 43 : $ability.hashCode());
      Object $operation = this.getOperation();
      return result * 59 + ($operation == null ? 43 : $operation.hashCode());
   }

   @Override
   public String toString() {
      return "PacketRelicTweak(container="
         + this.getContainer()
         + ", slot="
         + this.getSlot()
         + ", ability="
         + this.getAbility()
         + ", operation="
         + this.getOperation()
         + ", withShift="
         + this.isWithShift()
         + ")";
   }

   public PacketRelicTweak(int container, int slot, String ability, PacketRelicTweak.Operation operation, boolean withShift) {
      this.container = container;
      this.slot = slot;
      this.ability = ability;
      this.operation = operation;
      this.withShift = withShift;
   }

   public enum Operation {
      RESET(0),
      UPGRADE(1),
      REROLL(2);

      public static final IntFunction<PacketRelicTweak.Operation> BY_ID = ByIdMap.continuous(
         PacketRelicTweak.Operation::getId, values(), OutOfBoundsStrategy.ZERO
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
