package it.hurts.octostudios.rarcompat.network.packets;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.feet.BunnyHoppersItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class PowerJumpPacket implements CustomPacketPayload {
   public static final Type<PowerJumpPacket> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("rarcompat", "power_jump"));
   public static final StreamCodec<RegistryFriendlyByteBuf, PowerJumpPacket> STREAM_CODEC = new StreamCodec<RegistryFriendlyByteBuf, PowerJumpPacket>() {
      public void encode(RegistryFriendlyByteBuf buf, PowerJumpPacket packet) {
      }

      @Nonnull
      public PowerJumpPacket decode(@Nonnull RegistryFriendlyByteBuf buf) {
         return new PowerJumpPacket();
      }
   };

   public void handle(IPayloadContext ctx) {
      ctx.enqueueWork(
         () -> {
            Player player = ctx.player();
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.BUNNY_HOPPERS.value());
            if (stack.getItem() instanceof BunnyHoppersItem relic) {
               if (relic.getTime(stack) >= relic.getStatValue(stack, "hold", "duration") - 1.0) {
                  relic.spreadRelicExperience(player, stack, 1);
               }

               List<ItemStack> countRelic = CuriosApi.getCuriosInventory(player)
                  .map(inventory -> inventory.findCurios((Item)ModItems.BUNNY_HOPPERS.value()).stream().<ItemStack>map(SlotResult::stack).toList())
                  .orElse(List.of());

               for (ItemStack entry : countRelic) {
                  if (player.tickCount % countRelic.size() == 0) {
                     relic.addTime(entry, 1);
                  }
               }
            }
         }
      );
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return !(o instanceof PowerJumpPacket other) ? false : other.canEqual(this);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof PowerJumpPacket;
   }

   @Override
   public int hashCode() {
      int result = 1;
      return 1;
   }

   @Override
   public String toString() {
      return "PowerJumpPacket()";
   }
}
