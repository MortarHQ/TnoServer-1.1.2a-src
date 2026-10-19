package com.blorb.morerelics.network;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.relics.WeaversSpool;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public class SilkCounterPayloadHandler {
   public static final IPayloadHandler<SilkCounterPayload> CLIENT_HANDLER = SilkCounterPayloadHandler::handleClientSide;
   public static final IPayloadHandler<SilkCounterPayload> SERVER_HANDLER = SilkCounterPayloadHandler::handleServerSide;

   public static void handleClientSide(SilkCounterPayload payload, IPayloadContext context) {
      EntityUtils.findEquippedCurios(context.player(), (Item)MoreRelics.WEAVERS_SPOOL.get()).forEach(stack -> {
         WeaversSpool item = (WeaversSpool)stack.getItem();
         stack.set(DataComponentHandler.INT_COUNTER, DataComponentHandler.getIntegerCounter(stack).getUpdatedCounter(payload.silkCounter()));
      });
   }

   public static void handleServerSide(SilkCounterPayload payload, IPayloadContext context) {
   }
}
