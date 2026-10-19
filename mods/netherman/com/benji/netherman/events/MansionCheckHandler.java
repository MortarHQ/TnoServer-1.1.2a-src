package com.benji.netherman.events;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.network.FogSyncS2CPacket;
import com.benji.netherman.common.network.ModMessages;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "netherman")
public class MansionCheckHandler {
   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide() && player.tickCount % 20 == 0) {
         ServerLevel level = (ServerLevel)player.level();
         ServerPlayer serverPlayer = (ServerPlayer)player;
         Registry<Structure> registry = level.registryAccess().registryOrThrow(Registries.STRUCTURE);
         Structure mansion = (Structure)registry.get(NetherExp.location("mansion_nether"));
         boolean isInside = false;
         if (mansion != null) {
            StructureStart start = level.structureManager().getStructureAt(player.blockPosition(), mansion);
            isInside = start.isValid();
         }

         ModMessages.sendToPlayer(new FogSyncS2CPacket(isInside), serverPlayer);
      }
   }
}
