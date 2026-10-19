package twilightforest.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.TwilightForestMod;
import twilightforest.config.TFConfig;
import twilightforest.inventory.UncraftingMenu;

public record UncraftingGuiPacket(int operationType) implements CustomPacketPayload {
   public static final Type<UncraftingGuiPacket> TYPE = new Type(TwilightForestMod.prefix("switch_uncrafting_operation"));
   public static final StreamCodec<RegistryFriendlyByteBuf, UncraftingGuiPacket> STREAM_CODEC = CustomPacketPayload.codec(
      UncraftingGuiPacket::write, UncraftingGuiPacket::new
   );

   public UncraftingGuiPacket(FriendlyByteBuf buf) {
      this(buf.readInt());
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.operationType());
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(UncraftingGuiPacket message, IPayloadContext ctx) {
      if (ctx.flow().isServerbound()) {
         ctx.enqueueWork(() -> {
            if (ctx.player().containerMenu instanceof UncraftingMenu uncrafting) {
               switch (message.operationType()) {
                  case 0:
                     uncrafting.unrecipeInCycle++;
                     break;
                  case 1:
                     uncrafting.unrecipeInCycle--;
                     break;
                  case 2:
                     if (!TFConfig.disableIngredientSwitching) {
                        uncrafting.ingredientsInCycle++;
                     }
                     break;
                  case 3:
                     if (!TFConfig.disableIngredientSwitching) {
                        uncrafting.ingredientsInCycle--;
                     }
                     break;
                  case 4:
                     uncrafting.recipeInCycle++;
                     break;
                  case 5:
                     uncrafting.recipeInCycle--;
               }

               if (message.operationType() < 4) {
                  uncrafting.slotsChanged(uncrafting.tinkerInput);
               }

               if (message.operationType() >= 4) {
                  uncrafting.slotsChanged(uncrafting.assemblyMatrix);
               }
            }
         });
      }
   }
}
