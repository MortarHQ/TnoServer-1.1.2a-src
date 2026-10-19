package io.github.Memoires.mysticism.network.s2c;

import io.github.Memoires.mysticism.ability.subclass.IButchering;
import io.github.Memoires.mysticism.ability.subclass.IFlarefrostStorehouse;
import io.github.Memoires.mysticism.ability.subclass.IRupturing;
import io.github.Memoires.mysticism.client.screen.BookStorageScreen;
import io.github.Memoires.mysticism.client.screen.ButcheringScreen;
import io.github.Memoires.mysticism.client.screen.FlarefrostStorehouseScreen;
import io.github.Memoires.mysticism.client.screen.ProjectileEjectionScreen;
import io.github.Memoires.mysticism.client.screen.RupturingScreen;
import io.github.Memoires.mysticism.client.screen.WeaponStorageScreen;
import io.github.Memoires.mysticism.menu.BookStorageMenu;
import io.github.Memoires.mysticism.menu.FlarefrostStorehouseMenu;
import io.github.Memoires.mysticism.menu.ProjectileEjectionMenu;
import io.github.Memoires.mysticism.menu.WeaponStorageMenu;
import io.github.Memoires.mysticism.menu.container.BookStorageContainer;
import io.github.Memoires.mysticism.menu.container.WeaponStorageContainer;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Style;

class ClientAccess {
   public static <S extends ManasSkill & IFlarefrostStorehouse<S>> void handleOpenProjectileEjectionMenuPayload(OpenProjectileEjectionMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (optional.isPresent()) {
            SpatialStorageContainer container = new SpatialStorageContainer(message.size(), message.stackSize());
            ProjectileEjectionMenu menu = new ProjectileEjectionMenu(message.containerId(), player.getInventory(), container, (S)optional.get().getSkill());
            player.containerMenu = menu;
            Minecraft.getInstance().setScreen(new ProjectileEjectionScreen(menu, player.getInventory()));
         }
      }
   }

   public static <S extends ManasSkill & IFlarefrostStorehouse<S>> void handleOpenFlarefrostStorehouseMenuPayload(OpenFlarefrostStorehouseMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (optional.isPresent()) {
            SpatialStorageContainer container = new SpatialStorageContainer(message.size(), message.stackSize());
            FlarefrostStorehouseMenu menu = new FlarefrostStorehouseMenu(message.containerId(), player.getInventory(), container, (S)optional.get().getSkill());
            player.containerMenu = menu;
            Minecraft.getInstance().setScreen(new FlarefrostStorehouseScreen(menu, player.getInventory()));
         }
      }
   }

   public static <S extends ManasSkill & IButchering<S>> void handleOpenButcheringMenuPayload(OpenButcheringMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (!optional.isEmpty() && optional.get().getSkill() instanceof IButchering) {
            Minecraft.getInstance().setScreen(new ButcheringScreen(optional.get().getDisplayName().setStyle(Style.EMPTY), message.targetId()));
         }
      }
   }

   public static <S extends ManasSkill & IRupturing<S>> void handleOpenRupturingMenuPayload(OpenRupturingMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (!optional.isEmpty() && optional.get().getSkill() instanceof IRupturing) {
            Minecraft.getInstance().setScreen(new RupturingScreen(optional.get().getDisplayName().setStyle(Style.EMPTY), message.targetIds()));
         }
      }
   }

   public static void handleOpenBookStorageMenuPayload(OpenCustomStorageMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (!optional.isEmpty()) {
            BookStorageContainer container = new BookStorageContainer(message.size(), message.stackSize());
            BookStorageMenu menu = new BookStorageMenu(message.containerId(), player.getInventory(), container, optional.get().getSkill(), message.page());
            player.containerMenu = menu;
            Minecraft.getInstance().setScreen(new BookStorageScreen(menu, player.getInventory(), container.getContainerSize(), message.page()));
         }
      }
   }

   public static void handleOpenWeaponStorageMenuPayload(OpenCustomStorageMenuPayload message) {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null && player.getId() == message.entityId()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(message.skill());
         if (!optional.isEmpty()) {
            WeaponStorageContainer container = new WeaponStorageContainer(message.size(), message.stackSize());
            WeaponStorageMenu menu = new WeaponStorageMenu(
               message.containerId(), player.getInventory(), container, optional.get().getSkill(), message.page(), optional.get().isMastered(player)
            );
            player.containerMenu = menu;
            Minecraft.getInstance().setScreen(new WeaponStorageScreen(menu, player.getInventory(), container.getContainerSize(), message.page()));
         }
      }
   }
}
