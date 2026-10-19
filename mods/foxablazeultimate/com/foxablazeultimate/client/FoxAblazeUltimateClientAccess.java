package com.foxablazeultimate.client;

import com.foxablazeultimate.client.screen.BeelzebubStorageScreen;
import com.foxablazeultimate.client.screen.PredationFilterOverlay;
import com.foxablazeultimate.client.screen.RaphaelNamingScreen;
import com.foxablazeultimate.client.screen.ShubWarehouseScreen;
import com.foxablazeultimate.menu.BeelzebubStorageMenu;
import com.foxablazeultimate.menu.RaphaelNamingMenu;
import com.foxablazeultimate.network.OpenBeelzebubStoragePayload;
import com.foxablazeultimate.network.OpenRaphaelNamingPayload;
import com.foxablazeultimate.network.OpenShubWarehousePayload;
import com.foxablazeultimate.network.SyncPredationFilterPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class FoxAblazeUltimateClientAccess {
   private FoxAblazeUltimateClientAccess() {
   }

   public static void handleOpenBeelzebubStorage(OpenBeelzebubStoragePayload msg) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(player).getSkill(msg.skill());
         if (!opt.isEmpty()) {
            SpatialStorageContainer container = new SpatialStorageContainer(msg.size(), msg.stackSize());
            LivingEntity owner = (LivingEntity)(player.level().getEntity(msg.entityId()) instanceof LivingEntity le ? le : player);
            BeelzebubStorageMenu menu = new BeelzebubStorageMenu(msg.containerId(), player.getInventory(), owner, container, opt.get().getSkill(), msg.page());
            player.containerMenu = menu;
            Minecraft.getInstance().setScreen(new BeelzebubStorageScreen(menu, player.getInventory(), container.getContainerSize(), msg.page()));
         }
      }
   }

   public static void handleOpenRaphaelNaming(OpenRaphaelNamingPayload msg) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         RaphaelNamingMenu menu = new RaphaelNamingMenu(msg.containerId(), player.getInventory());
         player.containerMenu = menu;
         Minecraft.getInstance().setScreen(new RaphaelNamingScreen(menu, player.getInventory(), msg.defaultName(), msg.skill()));
      }
   }

   public static void handleOpenShubWarehouse(OpenShubWarehousePayload msg) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().setScreen(new ShubWarehouseScreen(msg));
      }
   }

   public static void handleSyncPredationFilter(SyncPredationFilterPayload msg) {
      PredationFilterOverlay.applySync(msg.slotCount(), msg.items());
   }

   public static void showWisdomCrystalEasterEgg() {
      WisdomCrystalEasterEggOverlay.trigger();
   }
}
