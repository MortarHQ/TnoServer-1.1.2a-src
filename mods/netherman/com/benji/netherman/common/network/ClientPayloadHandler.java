package com.benji.netherman.common.network;

import com.benji.netherman.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
   public static void handleTotemAnimation() {
      Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack((ItemLike)ModItems.CHANCE_TOTEM.get()));
   }

   public static void handleCustomTotemAnimation(TotemAnimationPayload payload, IPayloadContext context) {
      context.enqueueWork(() -> Minecraft.getInstance().gameRenderer.displayItemActivation(payload.stack()));
   }
}
