package it.hurts.sskirillss.relics.client.screen.description.experience;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.hurts.sskirillss.relics.client.screen.base.IAutoScaledScreen;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.base.ITabbedDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.ability.AbilityDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.ability.widgets.ExperienceSourcePageWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.widgets.BigExperienceCardWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.widgets.ExperienceGemWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.widgets.ResetExperienceActionWidget;
import it.hurts.sskirillss.relics.client.screen.description.experience.widgets.UpgradeExperienceActionWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.misc.DescriptionTab;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LogoWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LuckPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PlayerExperiencePlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsFixWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.TabWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionCache;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.relic.RelicDescriptionScreen;
import it.hurts.sskirillss.relics.client.screen.description.relic.widgets.RelicExperienceWidget;
import it.hurts.sskirillss.relics.client.screen.utils.ScreenUtils;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExperienceDescriptionScreen extends Screen implements IAutoScaledScreen, IRelicScreenProvider, ITabbedDescriptionScreen {
   public final Screen screen;
   public final int container;
   public final int slot;
   public ItemStack stack;
   private int page;
   private final int backgroundHeight = 256;
   private final int backgroundWidth = 418;
   public UpgradeExperienceActionWidget upgradeButton;
   public ResetExperienceActionWidget resetButton;

   public ExperienceDescriptionScreen(Player player, int container, int slot, Screen screen) {
      super(Component.empty());
      this.container = container;
      this.slot = slot;
      this.screen = screen;
      this.stack = DescriptionUtils.gatherRelicStack(player, slot);
      if (this.stack.getItem() instanceof IRelicItem relic) {
         List<String> sources = relic.getLevelingSourcesData()
            .getSources()
            .keySet()
            .stream()
            .filter(entry -> relic.isLevelingSourceEnabled(this.stack, entry))
            .toList();
         this.setPage(sources.indexOf(this.getSelectedSource()) / 5);
      }
   }

   public String getSelectedSource() {
      return DescriptionCache.getSelectedExperienceSource(this.stack);
   }

   public void setSelectedSource(String source) {
      DescriptionCache.setSelectedExperienceSource(this.stack, source);
   }

   protected void init() {
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic) {
         String var18 = this.getSelectedSource();
         if (relic.getLevelingSourceData(var18) != null) {
            this.updateCache(relic);
            int x = (this.width - 418) / 2;
            int y = (this.height - 256) / 2;
            List<String> sources = relic.getLevelingSourcesData()
               .getSources()
               .keySet()
               .stream()
               .filter(entryx -> relic.isLevelingSourceEnabled(this.stack, entryx))
               .toList();
            List<String> abilities = relic.getAbilitiesData()
               .getAbilities()
               .keySet()
               .stream()
               .filter(entryx -> relic.isAbilityEnabled(this.stack, entryx))
               .toList();
            int maxEntries = 5;
            if (sources.size() > maxEntries) {
               this.addRenderableWidget(new ExperienceSourcePageWidget(x + 289, y + 151, this, -1));
               this.addRenderableWidget(new ExperienceSourcePageWidget(x + 289, y + 186, this, 1));
            }

            int startIndex = this.page * maxEntries;
            int endIndex = Math.min(startIndex + maxEntries, sources.size());
            List<String> paginatedSources = startIndex < sources.size() && startIndex >= 0 ? sources.subList(startIndex, endIndex) : new ArrayList<>();
            this.addRenderableWidget(
               new TabWidget(
                  x + 81, y + 123, this, DescriptionTab.RELIC, new RelicDescriptionScreen(this.minecraft.player, this.container, this.slot, this.screen)
               )
            );
            int xOff = 19;
            if (!abilities.isEmpty()) {
               this.addRenderableWidget(
                  new TabWidget(
                     x + 81 + xOff,
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
                     x + 81 + xOff,
                     y + 123,
                     this,
                     DescriptionTab.EXPERIENCE,
                     new ExperienceDescriptionScreen(this.minecraft.player, this.container, this.slot, this.screen)
                  )
               );
            }

            this.addRenderableWidget(new BigExperienceCardWidget(x + 60, y + 47, this));
            this.addRenderableWidget(new LogoWidget(x + 313, y + 57, this));
            if (relic.isSomethingWrongWithLevelingPoints(this.stack)) {
               this.addRenderableWidget(new PointsFixWidget(x + 330, y + 33, this));
            }

            this.addRenderableWidget(new PointsPlateWidget(x + 313, y + 77, this));
            this.addRenderableWidget(new PlayerExperiencePlateWidget(x + 313, y + 102, this));
            this.addRenderableWidget(new LuckPlateWidget(x + 313, y + 127, this));
            if (!paginatedSources.isEmpty()) {
               int objectWidth = 32;
               int containerWidth = 209;
               int count = paginatedSources.size();
               int spacing = objectWidth + 8 + 3 * (maxEntries - count);
               xOff = containerWidth / 2 - (objectWidth * count + (spacing - objectWidth) * Math.max(count - 1, 0)) / 2;

               for (String entry : paginatedSources) {
                  this.addRenderableWidget(new ExperienceGemWidget(x + 77 + xOff, y + 153, this, entry));
                  xOff += spacing;
               }
            }

            this.addRenderableWidget(new RelicExperienceWidget(x + 142, y + 121, this));
            if (relic.isLevelingSourceUnlocked(this.stack, var18)) {
               this.upgradeButton = (UpgradeExperienceActionWidget)this.addRenderableWidget(new UpgradeExperienceActionWidget(x + 288, y + 70, this));
               this.resetButton = (ResetExperienceActionWidget)this.addRenderableWidget(new ResetExperienceActionWidget(x + 288, y + 90, this));
            }
         }
      }
   }

   public void rebuildWidgets() {
      this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
      super.rebuildWidgets();
   }

   public void tick() {
      super.tick();
      this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
   }

   public void renderBackground(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.renderBackground(guiGraphics, pMouseX, pMouseY, pPartialTick);
      LocalPlayer player = this.minecraft.player;
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic && player != null) {
         String source = this.getSelectedSource();
         LevelingSourceData sourceData = relic.getLevelingSourceData(source);
         if (sourceData != null) {
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
            List<String> sources = relic.getLevelingSourcesData()
               .getSources()
               .keySet()
               .stream()
               .filter(entry -> relic.isLevelingSourceEnabled(this.stack, entry))
               .toList();
            if (sources.size() > 5) {
               poseStack.pushPose();
               GUIRenderer.begin(DescriptionTextures.PAGE_COUNTER, poseStack).pos(x + 295, y + 177).end();
               MutableComponent page = Component.literal(String.valueOf(this.page + 1)).withStyle(ChatFormatting.BOLD);
               guiGraphics.drawString(this.minecraft.font, page, (int)(x + 296 - this.font.width(page) / 2.0F), y + 173, 16769656, true);
               poseStack.popPose();
            }

            poseStack.pushPose();
            poseStack.scale(0.75F, 0.75F, 1.0F);
            MutableComponent title = Component.translatableWithFallback(sourceData.getTranslationPath().apply(this.stack) + ".title", source);
            if (!relic.isLevelingSourceUnlocked(this.stack, source)) {
               title = ScreenUtils.stylizeWithReplacement(title, 1.0, Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT).withColor(10354864), source.length());
               RandomSource random = player.getRandom();
               float shakeX = MathUtils.randomFloat(random) * 0.5F;
               float shakeY = MathUtils.randomFloat(random) * 0.5F;
               poseStack.translate(shakeX, shakeY, 0.0F);
            } else {
               title.withStyle(ChatFormatting.BOLD);
            }

            guiGraphics.drawString(this.minecraft.font, title, (int)((x + 113) * 1.33F), (int)((y + 67) * 1.33F), 6696723, false);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.scale(0.5F, 0.5F, 0.5F);
            int var40 = 9;
            String ability = sourceData.getRequiredAbility();
            title = Component.literal("«")
               .append(
                  ability.isEmpty()
                     ? title
                     : Component.translatableWithFallback(
                        "tooltip.relics." + BuiltInRegistries.ITEM.getKey(this.stack.getItem()).getPath() + ".ability." + ability, ability
                     )
               )
               .append("»")
               .withStyle(ChatFormatting.BOLD);
            if (relic.isLevelingSourceUnlocked(this.stack, source)) {
               List<MutableComponent> components = new ArrayList<>();
               boolean wantsUpgrade = this.upgradeButton != null && this.upgradeButton.isHovered();
               boolean wantsReset = this.resetButton != null && this.resetButton.isHovered();
               int color = 6696723;
               MutableComponent component = Component.literal(String.valueOf(relic.getLevelingSourceData(source).getInitialValue()))
                  .withStyle(ChatFormatting.BOLD);
               if (wantsUpgrade) {
                  color = 2263842;
               }

               if (wantsReset) {
                  color = 11674146;
               }

               if (color != 6696723) {
                  float brightness = (float)(0.75 + 0.1F * Math.sin((Math.PI * 3.0 / 2.0) * player.tickCount / 20.0));
                  float[] hsb = Color.RGBtoHSB(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, null);
                  hsb[2] = Mth.clamp(brightness, 0.0F, 1.0F);
                  color = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
               }

               components.add(component.withColor(color));
               Pattern pattern = Pattern.compile("([^ .,!?;:]*%(1)\\$s[^ .,!?;:]*)");

               for (FormattedText line : this.font
                  .getSplitter()
                  .splitLines(
                     Component.translatable(sourceData.getTranslationPath().apply(this.stack) + ".description", new Object[]{"%1$s", title}), 340, Style.EMPTY
                  )) {
                  String unformattedLine = line.getString().replace("%%", "%");
                  int currentX = (x + 112) * 2;
                  int currentY = (y + 74) * 2 + var40;
                  Matcher matcher = pattern.matcher(unformattedLine);
                  int lastEnd = 0;

                  while (matcher.find()) {
                     String dynamicSegment = matcher.group(1);
                     int index = Integer.parseInt(matcher.group(2)) - 1;
                     String staticText = unformattedLine.substring(lastEnd, matcher.start());
                     if (!staticText.isEmpty()) {
                        guiGraphics.drawString(this.font, staticText, currentX, currentY, 6696723, false);
                        currentX += this.font.width(staticText);
                     }

                     if (index >= 0 && index < components.size()) {
                        MutableComponent dynamicComponent = components.get(index);
                        MutableComponent dynamicValue = Component.literal(
                              dynamicSegment.substring(0, dynamicSegment.indexOf(37))
                                 + dynamicComponent.getString()
                                 + dynamicSegment.substring(dynamicSegment.lastIndexOf(115) + 1)
                           )
                           .withStyle(dynamicComponent.getStyle());
                        guiGraphics.drawString(this.font, dynamicValue, currentX + 2, currentY + 1, color, false);
                        int frameStartX = currentX - 1;
                        int frameStartY = currentY - 1;
                        int frameEndX = currentX + this.font.width(dynamicValue) + 4;
                        int frameEndY = currentY + 9 + 1;
                        guiGraphics.fill(frameStartX, frameStartY, frameEndX, frameStartY + 1, -16777216 + color);
                        guiGraphics.fill(frameStartX, frameEndY - 1, frameEndX, frameEndY, -16777216 + color);
                        guiGraphics.fill(frameStartX, frameStartY, frameStartX + 1, frameEndY, -16777216 + color);
                        guiGraphics.fill(frameEndX - 1, frameStartY, frameEndX, frameEndY, -16777216 + color);
                        currentX += this.font.width(dynamicValue) + 3;
                        lastEnd = matcher.end();
                     }
                  }

                  if (lastEnd < unformattedLine.length()) {
                     guiGraphics.drawString(this.font, unformattedLine.substring(lastEnd), currentX, currentY, 6696723, false);
                  }

                  var40 += 10;
               }
            } else {
               List<Object> placeholders = Arrays.asList(relic.getLevelingSourceData(source).getInitialValue(), title);
               MutableComponent component = ScreenUtils.stylizeWithReplacement(
                  Component.translatable(sourceData.getTranslationPath().apply(this.stack) + ".description", placeholders.toArray()),
                  1.0,
                  Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT),
                  source.length()
               );

               for (FormattedCharSequence line : this.font.split(component, 340)) {
                  guiGraphics.drawString(this.font, line, (x + 112) * 2, (y + 74) * 2 + var40, 6696723, false);
                  var40 += 10;
               }
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
      return DescriptionTab.EXPERIENCE;
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

   public int getPage() {
      return this.page;
   }

   public void setPage(int page) {
      this.page = page;
   }
}
