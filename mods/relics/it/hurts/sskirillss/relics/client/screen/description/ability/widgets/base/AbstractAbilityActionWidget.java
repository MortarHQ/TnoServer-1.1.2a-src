package it.hurts.sskirillss.relics.client.screen.description.ability.widgets.base;

import com.mojang.blaze3d.systems.RenderSystem;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.ITickingWidget;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.base.AbstractDescriptionWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.leveling.PacketRelicTweak;
import java.awt.Color;
import java.util.Locale;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;

public abstract class AbstractAbilityActionWidget extends AbstractDescriptionWidget implements IHoverableWidget, ITickingWidget {
   private final PacketRelicTweak.Operation operation;
   private final AbilityDescriptionScreen screen;

   public AbstractAbilityActionWidget(int x, int y, PacketRelicTweak.Operation operation, AbilityDescriptionScreen screen) {
      super(x, y, 14, 13);
      this.operation = operation;
      this.screen = screen;
   }

   @Override
   public abstract boolean isLocked();

   public String getAbility() {
      return this.screen.getSelectedAbility();
   }

   @Override
   public void onPress() {
      if (!this.isLocked()) {
         NetworkHandler.sendToServer(
            new PacketRelicTweak(this.getScreen().getContainer(), this.getScreen().getSlot(), this.getAbility(), this.operation, Screen.hasShiftDown())
         );
      }
   }

   public void renderWidget(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      String actionId = this.operation.toString().toLowerCase(Locale.ROOT);
      guiGraphics.blit(
         ResourceLocation.fromNamespaceAndPath(
            "relics", "textures/gui/description/ability/" + actionId + "_button_" + (this.isLocked() ? "inactive" : "active") + ".png"
         ),
         this.getX(),
         this.getY(),
         0.0F,
         0.0F,
         this.width,
         this.height,
         this.width,
         this.height
      );
      if (this.isHovered) {
         guiGraphics.blit(DescriptionTextures.ACTION_BUTTON_OUTLINE, this.getX(), this.getY(), 0.0F, 0.0F, this.width, this.height, this.width, this.height);
      }
   }

   @Override
   public void onTick() {
      if (this.minecraft.player != null) {
         RandomSource random = this.minecraft.player.getRandom();
         if (this.isHovered() && this.minecraft.player.tickCount % 5 == 0) {
            ParticleStorage.addParticle(
               this.screen,
               new ExperienceParticleData(
                  new Color(200 + random.nextInt(50), 150 + random.nextInt(100), 0),
                  this.getX() + random.nextInt(this.width),
                  this.getY() + random.nextInt(this.height / 4),
                  1.0F + random.nextFloat() * 0.25F,
                  50 + random.nextInt(50)
               )
            );
         }
      }
   }

   public PacketRelicTweak.Operation getOperation() {
      return this.operation;
   }

   public AbilityDescriptionScreen getScreen() {
      return this.screen;
   }
}
