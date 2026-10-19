package com.bobmowzie.mowziesmobs.server.entity.grottol;

import com.bobmowzie.mowziesmobs.server.message.MessageBlackPinkInYourArea;
import java.util.function.BiConsumer;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public final class BlackPinkInYourArea implements BiConsumer<Level, AbstractMinecart> {
   private BlackPinkInYourArea() {
   }

   public void accept(Level world, AbstractMinecart minecart) {
      PacketDistributor.sendToPlayersTrackingEntityAndSelf(minecart, MessageBlackPinkInYourArea.fromMinecraft(minecart), new CustomPacketPayload[0]);
      if (!world.isClientSide && minecart.getFirstPassenger() instanceof EntityGrottol grottol) {
         grottol.setBlackpink(true);
      }
   }

   public static BlackPinkInYourArea create() {
      return new BlackPinkInYourArea();
   }
}
