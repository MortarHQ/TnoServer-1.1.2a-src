package it.hurts.sskirillss.relics.client.screen.description.relic;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.badges.base.AbstractBadge;
import it.hurts.sskirillss.relics.badges.base.RelicBadge;
import it.hurts.sskirillss.relics.client.screen.base.IAutoScaledScreen;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.base.ITabbedDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.experience.ExperienceDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.general.misc.DescriptionTab;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LogoWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LuckPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PlayerExperiencePlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsFixWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.RelicBadgeWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.TabWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.particles.ExperienceParticleData;
import it.hurts.sskirillss.relics.client.screen.description.relic.widgets.BigRelicCardWidget;
import it.hurts.sskirillss.relics.client.screen.description.relic.widgets.RelicExperienceWidget;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.init.BadgeRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredHolder;

@OnlyIn(Dist.CLIENT)
public class RelicDescriptionScreen extends Screen implements IAutoScaledScreen, IRelicScreenProvider, ITabbedDescriptionScreen {
   public final Screen screen;
   public final int container;
   public final int slot;
   public ItemStack stack;
   private final int backgroundHeight = 256;
   private final int backgroundWidth = 418;

   public RelicDescriptionScreen(Player player, int container, int slot, Screen screen) {
      super(Component.empty());
      this.container = container;
      this.slot = slot;
      this.screen = screen;
      this.stack = DescriptionUtils.gatherRelicStack(player, slot);
   }

   protected void init() {
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic) {
         this.updateCache(relic);
         int var9 = (this.width - 418) / 2;
         int y = (this.height - 256) / 2;
         Map sources = relic.getLevelingSourcesData().getSources();
         Map abilities = relic.getAbilitiesData().getAbilities();
         this.addRenderableWidget(
            new TabWidget(
               var9 + 81, y + 123, this, DescriptionTab.RELIC, new RelicDescriptionScreen(this.minecraft.player, this.container, this.slot, this.screen)
            )
         );
         byte xOff = 19;
         if (!abilities.isEmpty()) {
            this.addRenderableWidget(
               new TabWidget(
                  var9 + 81 + xOff,
                  y + 123,
                  this,
                  DescriptionTab.ABILITY,
                  new AbilityDescriptionScreen(this.minecraft.player, this.container, this.slot, this.screen)
               )
            );
            xOff += 19;
         }

         if (!sources.isEmpty()) {
            this.addRenderableWidget(
               new TabWidget(
                  var9 + 81 + xOff,
                  y + 123,
                  this,
                  DescriptionTab.EXPERIENCE,
                  new ExperienceDescriptionScreen(this.minecraft.player, this.container, this.slot, this.screen)
               )
            );
         }

         this.addRenderableWidget(new BigRelicCardWidget(var9 + 60, y + 47, this));
         this.addRenderableWidget(new LogoWidget(var9 + 313, y + 57, this));
         if (relic.isSomethingWrongWithLevelingPoints(this.stack)) {
            this.addRenderableWidget(new PointsFixWidget(var9 + 330, y + 33, this));
         }

         this.addRenderableWidget(new PointsPlateWidget(var9 + 313, y + 77, this));
         this.addRenderableWidget(new PlayerExperiencePlateWidget(var9 + 313, y + 102, this));
         this.addRenderableWidget(new LuckPlateWidget(var9 + 313, y + 127, this));
         xOff = 0;

         for (RelicBadge badge : BadgeRegistry.BADGES
            .getEntries()
            .stream()
            .<AbstractBadge>map(DeferredHolder::get)
            .filter(entry -> entry instanceof RelicBadge)
            .map(entry -> (RelicBadge)entry)
            .toList()) {
            if (badge.isVisible(this.stack)) {
               this.addRenderableWidget(new RelicBadgeWidget(var9 + 270 - xOff, y + 63, this, badge));
               xOff += 15;
            }
         }

         this.addRenderableWidget(new RelicExperienceWidget(var9 + 142, y + 121, this));
      }
   }

   public void rebuildWidgets() {
      this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
      super.rebuildWidgets();
   }

   public void tick() {
      super.tick();
      this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
      LocalPlayer player = this.minecraft.player;
      if (player != null && this.stack != null && this.stack.getItem() instanceof IRelicItem) {
         RandomSource random = player.getRandom();
         int x = (this.width - 418) / 2;
         int y = (this.height - 256) / 2;
         if (player.tickCount % 3 == 0) {
            ParticleStorage.addParticle(
               this,
               new ExperienceParticleData(
                  new Color(140, random.nextInt(50), 255),
                  x + 73 + random.nextInt(20),
                  y + 73 + random.nextInt(20),
                  1.5F + random.nextFloat() * 0.5F,
                  100 + random.nextInt(50)
               )
            );
         }
      }
   }

   public void renderBackground(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.renderBackground(guiGraphics, pMouseX, pMouseY, pPartialTick);
      LocalPlayer player = this.minecraft.player;
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic && player != null) {
         RelicData relicData = relic.getRelicData();
         if (relicData != null) {
            int level = relic.getRelicLevel(this.stack);
            PoseStack poseStack = guiGraphics.pose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, DescriptionTextures.SPACE_BACKGROUND);
            int x = (this.width - 418) / 2;
            int y = (this.height - 256) / 2;
            int yOff = 0;
            int xOff = 0;
            GUIRenderer.begin(DescriptionTextures.SPACE_BACKGROUND, poseStack)
               .texSize(418, 4096)
               .patternSize(418, 256)
               .pos(x + 209.0F, y + 128.0F)
               .animation(
                  AnimationData.builder()
                     .frame(0, 2)
                     .frame(1, 2)
                     .frame(2, 2)
                     .frame(3, 2)
                     .frame(4, 2)
                     .frame(5, 2)
                     .frame(6, 2)
                     .frame(7, 2)
                     .frame(8, 2)
                     .frame(9, 2)
                     .frame(10, 2)
                     .frame(11, 2)
                     .frame(12, 2)
                     .frame(13, 2)
                     .frame(14, 2)
                     .frame(15, 2)
               )
               .end();
            GUIRenderer.begin(DescriptionTextures.BIG_CARD_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(x + 67, y + 57).end();
            GUIRenderer.begin(DescriptionTextures.TOP_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(x + 107, y + 47).end();
            GUIRenderer.begin(DescriptionTextures.BOTTOM_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(x + 60, y + 133).end();
            poseStack.pushPose();
            poseStack.scale(0.75F, 0.75F, 1.0F);
            guiGraphics.drawString(
               this.minecraft.font,
               Component.literal(this.stack.getDisplayName().getString().replace("[", "").replace("]", "")).withStyle(ChatFormatting.BOLD),
               (int)((x + 113) * 1.33F),
               (int)((y + 67) * 1.33F),
               6696723,
               false
            );
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            int var17 = 9;

            for (FormattedCharSequence line : this.minecraft
               .font
               .split(Component.translatable("tooltip.relics." + BuiltInRegistries.ITEM.getKey(this.stack.getItem()).getPath() + ".description"), 340)) {
               guiGraphics.drawString(this.minecraft.font, line, (x + 112) * 2, (y + 74) * 2 + var17, 6696723, false);
               var17 += 10;
            }

            poseStack.popPose();
         }
      }
   }

   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);

      for (GuiEventListener listener : this.children()) {
         if (listener instanceof AbstractButton button && button.isHovered() && button instanceof IHoverableWidget widget) {
            guiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
            widget.onHovered(guiGraphics, pMouseX, pMouseY);
         }
      }
   }

   public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
      if (this.minecraft.options.keyInventory.isActiveAndMatches(InputConstants.getKey(pKeyCode, pScanCode))) {
         this.onClose();
         return true;
      } else {
         return super.keyPressed(pKeyCode, pScanCode, pModifiers);
      }
   }

   public void onClose() {
      this.screen.rebuildWidgets();
      Minecraft.getInstance().setScreen(this.screen);
   }

   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public int getAutoScale() {
      return 0;
   }

   @Override
   public DescriptionTab getTab() {
      return DescriptionTab.RELIC;
   }

   @Override
   public int getContainer() {
      return this.container;
   }

   @Override
   public int getSlot() {
      return this.slot;
   }

   @Override
   public ItemStack getStack() {
      return this.stack;
   }
}
