package io.github.Memoires.mysticism.handler;

import io.github.Memoires.mysticism.event.MysticismEntityEvent;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class DirewolfHandler {
   public static void init() {
      MysticismEntityEvent.ENTITY_FORCE_DISMOUNT.register((MysticismEntityEvent.EntityForceDismount)(player, entities) -> {
         if (!entities.isEmpty()) {
            if (checkDirewolf().contains(RaceAPI.getRaceFrom(player))) {
               for (Entity entity : entities) {
                  if (entity instanceof LivingEntity living) {
                     living.stopRiding();
                  }
               }
            }
         }
      });
   }

   public static List<ManasRace> checkDirewolf() {
      List<ManasRace> list = new ArrayList<>();
      list.add((ManasRace)MysticismRaces.DIREWOLF.get());
      list.add((ManasRace)MysticismRaces.BLACK_FANG.get());
      list.add((ManasRace)MysticismRaces.BLUE_FANG.get());
      list.add((ManasRace)MysticismRaces.BROWN_FANG.get());
      list.add((ManasRace)MysticismRaces.DARKNESS_FANG.get());
      list.add((ManasRace)MysticismRaces.GREEN_FANG.get());
      list.add((ManasRace)MysticismRaces.GUITAR_WOLF.get());
      list.add((ManasRace)MysticismRaces.LIGHT_FANG.get());
      list.add((ManasRace)MysticismRaces.PURPLE_FANG.get());
      list.add((ManasRace)MysticismRaces.RED_FANG.get());
      list.add((ManasRace)MysticismRaces.STAR_WOLF.get());
      return list;
   }
}
