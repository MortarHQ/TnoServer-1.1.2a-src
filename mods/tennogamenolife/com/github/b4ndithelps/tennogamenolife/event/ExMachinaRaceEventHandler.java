package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.config.TenreincarnationConfig;
import com.github.b4ndithelps.tennogamenolife.race.ExMachina.IExMachinaDecorativeRace;
import com.github.b4ndithelps.tennogamenolife.registry.item.Tenreincarnationitems;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public class ExMachinaRaceEventHandler {
   private static final String WEAPON_GIVEN_TAG = "tennogamenolife:ex_machina_weapon_given";

   @SubscribeEvent
   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         checkAndGiveWeapon(player);
      }
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide && player.tickCount % 20 == 0 && player instanceof ServerPlayer serverPlayer) {
         checkAndGiveWeapon(serverPlayer);
      }
   }

   private static void checkAndGiveWeapon(ServerPlayer player) {
      if (!(player.containerMenu instanceof ReincarnationMenu)) {
         boolean isExMachinaRace = RaceAPI.getRaceFrom(player).getRace().map(instance -> instance.getRace() instanceof IExMachinaDecorativeRace).orElse(false);
         CompoundTag persistentData = getEquipmentPersistentData(player, "tennogamenolife:ex_machina_weapon_given");
         if (!isExMachinaRace) {
            persistentData.putBoolean("tennogamenolife:ex_machina_weapon_given", false);
         } else if (TenreincarnationConfig.shouldGiveExMachinaWeaponOnStart() && !persistentData.getBoolean("tennogamenolife:ex_machina_weapon_given")) {
            if (hasExMachinaWeapon(player)) {
               persistentData.putBoolean("tennogamenolife:ex_machina_weapon_given", true);
            } else {
               ItemStack weapon = new ItemStack((ItemLike)Tenreincarnationitems.EX_MACHINAS_WEAPON.get());
               if (player.getInventory().add(weapon)) {
                  persistentData.putBoolean("tennogamenolife:ex_machina_weapon_given", true);
               }
            }
         }
      }
   }

   private static boolean hasExMachinaWeapon(Player player) {
      if (!player.getMainHandItem().is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get())
         && !player.getOffhandItem().is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get())) {
         for (ItemStack stack : player.getInventory().items) {
            if (stack.is((Item)Tenreincarnationitems.EX_MACHINAS_WEAPON.get())) {
               return true;
            }
         }

         return false;
      } else {
         return true;
      }
   }

   private static CompoundTag getEquipmentPersistentData(Player player, String key) {
      CompoundTag root = player.getPersistentData();
      CompoundTag persisted = root.getCompound("PlayerPersisted");
      if (root.contains(key)) {
         if (!persisted.contains(key)) {
            persisted.putBoolean(key, root.getBoolean(key));
         }

         root.remove(key);
      }

      root.put("PlayerPersisted", persisted);
      return persisted;
   }
}
