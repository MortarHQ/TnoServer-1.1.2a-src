package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.emi;

import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.common.CraftingContainerRecipeTransferHandlerServer;
import net.p3pp3rf1y.sophisticatedcore.util.StreamCodecHelper;

public record EmiTransferRecipePayload(
   ResourceLocation recipeId,
   ResourceLocation recipeTypeId,
   int action,
   List<Integer> slots,
   List<Integer> crafting,
   int output,
   List<ItemStack> stacks,
   boolean maxTransfer
) implements CustomPacketPayload {
   public static final Type<EmiTransferRecipePayload> TYPE = new Type(SophisticatedCore.getRL("emi_transfer_recipe"));
   public static final StreamCodec<RegistryFriendlyByteBuf, EmiTransferRecipePayload> STREAM_CODEC = StreamCodecHelper.composite(
      ResourceLocation.STREAM_CODEC,
      EmiTransferRecipePayload::recipeId,
      ResourceLocation.STREAM_CODEC,
      EmiTransferRecipePayload::recipeTypeId,
      ByteBufCodecs.INT,
      EmiTransferRecipePayload::action,
      ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
      EmiTransferRecipePayload::slots,
      ByteBufCodecs.INT.apply(ByteBufCodecs.list()),
      EmiTransferRecipePayload::crafting,
      ByteBufCodecs.INT,
      EmiTransferRecipePayload::output,
      ItemStack.OPTIONAL_LIST_STREAM_CODEC,
      EmiTransferRecipePayload::stacks,
      ByteBufCodecs.BOOL,
      EmiTransferRecipePayload::maxTransfer,
      EmiTransferRecipePayload::new
   );

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handlePayload(EmiTransferRecipePayload payload, IPayloadContext context) {
      RecipeType<?> recipeType = (RecipeType<?>)BuiltInRegistries.RECIPE_TYPE.get(payload.recipeTypeId);
      if (recipeType != null) {
         Player player = context.player();
         CraftingContainerRecipeTransferHandlerServer.setItemsWithStacks(
            player, payload.recipeId, recipeType, payload.stacks, payload.crafting, payload.slots, payload.maxTransfer
         );
         if (player.containerMenu instanceof StorageContainerMenuBase<?> container) {
            Slot var6 = null;
            if (payload.output >= 0 && payload.output < container.getTotalSlotsNumber()) {
               var6 = container.getSlot(payload.output);
            }

            if (var6 != null) {
               if (payload.action == 1) {
                  container.clicked(var6.index, 0, ClickType.PICKUP, player);
               } else if (payload.action == 2) {
                  container.clicked(var6.index, 0, ClickType.QUICK_MOVE, player);
               }
            }
         }
      }
   }
}
