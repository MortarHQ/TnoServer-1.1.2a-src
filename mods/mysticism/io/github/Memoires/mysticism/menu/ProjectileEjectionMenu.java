package io.github.Memoires.mysticism.menu;

import io.github.Memoires.mysticism.ability.subclass.IProjectileEjection;
import io.github.Memoires.mysticism.data.MysticismItemTags;
import io.github.Memoires.mysticism.menu.slot.ProjectileSlot;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import java.util.Optional;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ProjectileEjectionMenu<S extends ManasSkill & IProjectileEjection<S>> extends AbstractContainerMenu {
   private final S skill;
   private final Player player;
   public final SpatialStorageContainer projectileContainer;
   private static final int[][] COORDS_3 = new int[][]{{44, 81}, {79, 81}, {114, 81}};
   private static final int[][] COORDS_5 = new int[][]{{44, 46}, {114, 46}, {79, 81}, {44, 116}, {114, 116}};

   public ProjectileEjectionMenu(int id, Inventory inv, SpatialStorageContainer container, S skill) {
      super(null, id);
      this.skill = skill;
      this.player = inv.player;
      this.projectileContainer = new SpatialStorageContainer(5, 128);

      for (int i = 0; i < 5; i++) {
         this.projectileContainer.setItem(i, container.getItem(i + skill.getSpatialStorageIdOffset()));
      }

      container.startOpen(inv.player);
      this.addPlayerInventory(inv);
      this.addProjectileSlots();
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 168 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 226));
      }
   }

   private void addProjectileSlots() {
      boolean improved = SkillAPI.getSkillsFrom(this.player)
         .getSkill((ManasSkill)UniqueMysticismSkills.PHASER.get())
         .map(si -> si.isMastered(this.player))
         .orElse(false);
      int[][] coords = improved ? COORDS_5 : COORDS_3;
      int usedStorageSlots = Math.min(coords.length, this.projectileContainer.getContainerSize());

      for (int i = 0; i < usedStorageSlots; i++) {
         int x = coords[i][0];
         int y = coords[i][1];
         this.addSlot(new ProjectileSlot(this.projectileContainer, i, x, y));
      }
   }

   public void removed(Player pPlayer) {
      ManasSkillInstance instance = this.getSkillInstance(pPlayer);
      if (instance != null) {
         SpatialStorageContainer container = this.projectileContainer;
         if (container instanceof SpatialStorageContainer && instance.getSkill() instanceof IProjectileEjection projectileEjection) {
            projectileEjection.saveContainer(instance, pPlayer, container);
         }
      }

      this.projectileContainer.stopOpen(pPlayer);
      super.removed(pPlayer);
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
      return super.canTakeItemForPickAll(pStack, pSlot);
   }

   @Nullable
   public ManasSkillInstance getSkillInstance(Player player) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(this.skill);
      return optional.orElse(null);
   }

   @NotNull
   public ItemStack quickMoveStack(Player player, int index) {
      Slot from = (Slot)this.slots.get(index);
      if (!from.hasItem()) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = from.getItem();
      ItemStack copy = stack.copy();
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.PHASER.get());
      if (opt.isPresent()) {
         ManasSkillInstance instance = opt.get();
         int storageEnd = instance.isMastered(player) ? 5 : 3;
         int invEnd = this.slots.size();
         if (index < storageEnd) {
            if (!this.moveItemStackTo(stack, storageEnd, invEnd, true)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (!stack.is(MysticismItemTags.KAMUI_PROJECTILE)) {
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(stack, 0, storageEnd, false)) {
               return ItemStack.EMPTY;
            }
         }

         if (stack.isEmpty()) {
            from.set(ItemStack.EMPTY);
         } else {
            from.setChanged();
         }

         if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
         }

         from.onTake(player, stack);
      }

      return copy;
   }

   public S getSkill() {
      return this.skill;
   }

   public Player getPlayer() {
      return this.player;
   }
}
