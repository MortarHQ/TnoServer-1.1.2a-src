package it.hurts.sskirillss.relics.network.packets.leveling;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@Deprecated(forRemoval = true)
public class FixLevelingPoints implements CustomPacketPayload {
   private final int container;
   private final int slot;
   public static final Type<FixLevelingPoints> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "fix_leveling_points"));
   public static final StreamCodec<ByteBuf, FixLevelingPoints> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT, FixLevelingPoints::getContainer, ByteBufCodecs.INT, FixLevelingPoints::getSlot, FixLevelingPoints::new
   );

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
            if (!(stack.getItem() instanceof IRelicItem relic)) {
               causeError(player);
            } else if (relic.isSomethingWrongWithLevelingPoints(stack)) {
               relic.setRelicLevelingPoints(stack, relic.getRelicLevel(stack));

               for (AbilityData data : relic.getAbilitiesData().getAbilities().values()) {
                  relic.setAbilityLevel(stack, data.getId(), 0);
               }

               try {
                  player.containerMenu.getSlot(this.slot).set(stack);
               } catch (Exception e) {
                  e.printStackTrace();
                  causeError(player);
               }
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

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof FixLevelingPoints other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else {
         return this.getContainer() != other.getContainer() ? false : this.getSlot() == other.getSlot();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof FixLevelingPoints;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getContainer();
      return result * 59 + this.getSlot();
   }

   @Override
   public String toString() {
      return "FixLevelingPoints(container=" + this.getContainer() + ", slot=" + this.getSlot() + ")";
   }

   public FixLevelingPoints(int container, int slot) {
      this.container = container;
      this.slot = slot;
   }
}
