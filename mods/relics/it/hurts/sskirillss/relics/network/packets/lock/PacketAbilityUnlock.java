package it.hurts.sskirillss.relics.network.packets.lock;

import io.netty.buffer.ByteBuf;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
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

public class PacketAbilityUnlock implements CustomPacketPayload {
   private final int container;
   private final int slot;
   private final String ability;
   private final int unlocks;
   public static final Type<PacketAbilityUnlock> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("relics", "ability_unlock"));
   public static final StreamCodec<ByteBuf, PacketAbilityUnlock> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.INT,
      PacketAbilityUnlock::getContainer,
      ByteBufCodecs.INT,
      PacketAbilityUnlock::getSlot,
      ByteBufCodecs.STRING_UTF8,
      PacketAbilityUnlock::getAbility,
      ByteBufCodecs.INT,
      PacketAbilityUnlock::getUnlocks,
      PacketAbilityUnlock::new
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
            if (stack.getItem() instanceof IRelicItem relic) {
               relic.setLockUnlocks(stack, this.ability, this.unlocks);

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

   public int getUnlocks() {
      return this.unlocks;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof PacketAbilityUnlock other)) {
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

         if (this.getUnlocks() != other.getUnlocks()) {
            return false;
         }

         Object this$ability = this.getAbility();
         Object other$ability = other.getAbility();
         return this$ability == null ? other$ability == null : this$ability.equals(other$ability);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PacketAbilityUnlock;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getContainer();
      result = result * 59 + this.getSlot();
      result = result * 59 + this.getUnlocks();
      Object $ability = this.getAbility();
      return result * 59 + ($ability == null ? 43 : $ability.hashCode());
   }

   @Override
   public String toString() {
      return "PacketAbilityUnlock(container="
         + this.getContainer()
         + ", slot="
         + this.getSlot()
         + ", ability="
         + this.getAbility()
         + ", unlocks="
         + this.getUnlocks()
         + ")";
   }

   public PacketAbilityUnlock(int container, int slot, String ability, int unlocks) {
      this.container = container;
      this.slot = slot;
      this.ability = ability;
      this.unlocks = unlocks;
   }
}
