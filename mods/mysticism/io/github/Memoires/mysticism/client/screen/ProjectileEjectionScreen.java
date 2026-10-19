package io.github.Memoires.mysticism.client.screen;

import io.github.Memoires.mysticism.menu.ProjectileEjectionMenu;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ProjectileEjectionScreen extends AbstractContainerScreen<ProjectileEjectionMenu<?>> {
   private static final ResourceLocation BG = ResourceLocation.fromNamespaceAndPath("mysticism", "textures/gui/kamui_ejection.png");
   private static final int[][] COORDS_3 = new int[][]{{44, 81}, {79, 81}, {114, 81}};
   private static final int[][] COORDS_5 = new int[][]{{44, 46}, {114, 46}, {79, 81}, {44, 116}, {114, 116}};
   private static final int slotWidth = 18;
   private static final int slotHeight = 18;

   public ProjectileEjectionScreen(ProjectileEjectionMenu pMenu, Inventory pPlayerInventory) {
      super(pMenu, pPlayerInventory, pMenu.getSkill().getName());
      this.imageWidth = 175;
      this.imageHeight = 249;
   }

   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      guiGraphics.blit(BG, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(((ProjectileEjectionMenu)this.getMenu()).getPlayer())
         .getSkill((ManasSkill)UniqueMysticismSkills.PHASER.get());
      if (opt.isPresent()) {
         int[][] coords = opt.get().isMastered(((ProjectileEjectionMenu)this.getMenu()).getPlayer()) ? COORDS_5 : COORDS_3;

         for (int[] c : coords) {
            int x = this.leftPos + c[0];
            int y = this.topPos + c[1];
            guiGraphics.blit(BG, x, y, 177, 0, 18, 18);
         }
      }
   }

   protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {
      guiGraphics.drawCenteredString(this.font, this.title, this.titleLabelX + 82, this.titleLabelY, 11184810);
   }

   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      this.renderTooltip(guiGraphics, pMouseX, pMouseY);
   }
}
