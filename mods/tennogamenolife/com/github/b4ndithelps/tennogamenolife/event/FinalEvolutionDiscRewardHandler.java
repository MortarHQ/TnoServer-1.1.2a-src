package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import java.util.Set;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class FinalEvolutionDiscRewardHandler {
   private static final String DISC_GIVEN_TAG = "tennogamenolife:final_evolution_disc_given";
   private static final String PENDING_RACE_TAG = "tennogamenolife:pending_final_evolution_disc_race";
   private static final Set<ResourceLocation> FINAL_RACES = Set.of(
      id("arutoshu"), id("ocain"), id("aranreivu"), id("hartyleif"), id("reginreivu"), id("kampf"), id("zeichen"), id("seher"), id("horou"), id("puraiya")
   );
   private static boolean initialized;

   private FinalEvolutionDiscRewardHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         RaceEvents.SET_RACE.register((SetRaceEvent)(oldRace, entity, newRace, evolution, teleport, message) -> {
            if (evolution && oldRace != null && entity instanceof ServerPlayer player) {
               ResourceLocation raceId = newRace.getRace().getRegistryName();
               ResourceLocation oldRaceId = oldRace.getRace().getRegistryName();
               if (raceId != null && !raceId.equals(oldRaceId) && FINAL_RACES.contains(raceId)) {
                  player.getPersistentData().putString("tennogamenolife:pending_final_evolution_disc_race", raceId.toString());
                  return EventResult.pass();
               } else {
                  return EventResult.pass();
               }
            } else {
               return EventResult.pass();
            }
         });
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide()) {
         CompoundTag persistentData = player.getPersistentData();
         String pendingRace = persistentData.getString("tennogamenolife:pending_final_evolution_disc_race");
         if (!pendingRace.isEmpty()) {
            persistentData.remove("tennogamenolife:pending_final_evolution_disc_race");
            ResourceLocation expectedRaceId = ResourceLocation.tryParse(pendingRace);
            if (expectedRaceId != null) {
               ManasRaceInstance currentRace = (ManasRaceInstance)RaceAPI.getRaceFrom(player).getRace().orElse(null);
               if (currentRace != null && expectedRaceId.equals(currentRace.getRace().getRegistryName())) {
                  if (!persistentData.getBoolean("tennogamenolife:final_evolution_disc_given")) {
                     ItemStack disc = new ItemStack((ItemLike)Tenreincarnationitems.MUSIC_DISC_GAME_OF_LIFE.get());
                     if (!player.getInventory().add(disc)) {
                        player.drop(disc, false);
                     }

                     persistentData.putBoolean("tennogamenolife:final_evolution_disc_given", true);
                  }
               }
            }
         }
      }
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tennogamenolife", path);
   }
}
