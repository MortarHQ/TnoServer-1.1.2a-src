package it.hurts.sskirillss.relics.client.screen.description.research;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.client.screen.base.IAutoScaledScreen;
import it.hurts.sskirillss.relics.client.screen.base.IHoverableWidget;
import it.hurts.sskirillss.relics.client.screen.base.IRelicScreenProvider;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LogoWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.LuckPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PlayerExperiencePlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsFixWidget;
import it.hurts.sskirillss.relics.client.screen.description.general.widgets.PointsPlateWidget;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionTextures;
import it.hurts.sskirillss.relics.client.screen.description.misc.DescriptionUtils;
import it.hurts.sskirillss.relics.client.screen.description.research.misc.BurnPoint;
import it.hurts.sskirillss.relics.client.screen.description.research.particles.ResearchParticleData;
import it.hurts.sskirillss.relics.client.screen.description.research.widgets.HintWidget;
import it.hurts.sskirillss.relics.client.screen.description.research.widgets.StarWidget;
import it.hurts.sskirillss.relics.client.screen.description.research.widgets.TipWidget;
import it.hurts.sskirillss.relics.client.screen.utils.ParticleStorage;
import it.hurts.sskirillss.relics.client.screen.utils.ScreenUtils;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.research.StarData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.research.PacketManageLink;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.RenderUtils;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import it.hurts.sskirillss.relics.utils.data.GUIRenderer;
import it.hurts.sskirillss.relics.utils.data.SpriteAnchor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector2f;

@OnlyIn(Dist.CLIENT)
public class AbilityResearchScreen extends Screen implements IAutoScaledScreen, IRelicScreenProvider {
   public final Screen screen;
   public final int container;
   public final int slot;
   public ItemStack stack;
   public final String ability;
   public int backgroundHeight = 256;
   public int backgroundWidth = 418;
   public int x;
   public int y;
   @Nullable
   public StarData selectedStar;
   private List<BurnPoint> points = new ArrayList<>();
   private List<StarWidget> stars = new ArrayList<>();
   private final int maxResearchProgress = 40;
   private int researchProgress = 0;

   public AbilityResearchScreen(Player player, int container, int slot, Screen screen, String ability) {
      super(Component.empty());
      this.container = container;
      this.slot = slot;
      this.screen = screen;
      this.ability = ability;
      this.stack = DescriptionUtils.gatherRelicStack(player, slot);
   }

   public int getTotalConnectionsCount(StarData star) {
      return this.stack.getItem() instanceof IRelicItem relic ? relic.getAbilityData(this.ability).getResearchData().getConnectedStars(star).size() : 0;
   }

   public int getOccupiedConnectionsCount(StarData star) {
      if (this.stack.getItem() instanceof IRelicItem relic) {
         int var4 = star.getIndex();
         return (int)relic.getResearchLinks(this.stack, this.ability)
            .entries()
            .stream()
            .filter(entry -> (Integer)entry.getKey() == var4 || (Integer)entry.getValue() == var4)
            .map(entry -> entry.getKey() < entry.getValue() ? entry.getKey() + "-" + entry.getValue() : entry.getValue() + "-" + entry.getKey())
            .distinct()
            .count();
      } else {
         return 0;
      }
   }

   protected void init() {
      super.init();
      this.x = (this.width - this.backgroundWidth) / 2;
      this.y = (this.height - this.backgroundHeight) / 2;
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic) {
         this.researchProgress = 0;
         this.stars.clear();
         this.points.clear();
         this.addRenderableWidget(new LogoWidget(this.x + 313, this.y + 57, this));
         if (relic.isSomethingWrongWithLevelingPoints(this.stack)) {
            this.addRenderableWidget(new PointsFixWidget(this.x + 330, this.y + 33, this));
         }

         this.addRenderableWidget(new PointsPlateWidget(this.x + 313, this.y + 77, this));
         this.addRenderableWidget(new PlayerExperiencePlateWidget(this.x + 313, this.y + 102, this));
         this.addRenderableWidget(new LuckPlateWidget(this.x + 313, this.y + 127, this));
         this.addRenderableWidget(new TipWidget(this.x + 117, this.y + 207, this));
         this.addRenderableWidget(new HintWidget(this.x + 192, this.y + 198, this));
         int starSize = 17;

         for (StarData entry : relic.getAbilityData(this.ability).getResearchData().getStars().values()) {
            this.stars
               .add(
                  (StarWidget)this.addWidget(
                     new StarWidget(
                        (int)(this.x + 67 + entry.getX() * 5.0F - starSize / 2.0F), (int)(this.y + 54 + entry.getY() * 5.0F - starSize / 2.0F), this, entry
                     )
                  )
               );
         }
      }
   }

   public void rebuildWidgets() {
      this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
      super.rebuildWidgets();
   }

   public void tick() {
      if (this.stack.getItem() instanceof IRelicItem relic) {
         super.tick();
         this.stack = DescriptionUtils.gatherRelicStack(this.minecraft.player, this.slot);
         RandomSource var10 = this.minecraft.player.getRandom();
         if (relic.isAbilityResearched(this.stack, this.ability) && this.researchProgress >= 0 && this.researchProgress < 40) {
            this.researchProgress++;
            if (this.researchProgress % 3 == 0) {
               ResearchData researchData = relic.getResearchData(this.ability);

               for (Entry<Integer, Integer> link : relic.getResearchLinks(this.stack, this.ability).entries()) {
                  Vec2 start = researchData.getStars().get(link.getKey()).getPos();
                  Vec2 end = researchData.getStars().get(link.getValue()).getPos();
                  this.executeForConnection(
                     start,
                     end,
                     0.75F,
                     point -> ParticleStorage.addParticle(
                        this,
                        new ResearchParticleData(
                           new Color(100 + var10.nextInt(150), var10.nextInt(25), 200 + var10.nextInt(50)),
                           point.x + MathUtils.randomFloat(var10),
                           point.y + MathUtils.randomFloat(var10),
                           1.0F + var10.nextFloat() * 0.25F,
                           20 + var10.nextInt(40 + this.researchProgress),
                           var10.nextFloat() * 0.025F
                        )
                     )
                  );
               }
            }
         }

         if (this.minecraft.player.tickCount % 3 == 0) {
            Multimap<Integer, Integer> links = relic.getResearchLinks(this.stack, this.ability);

            for (Entry<Integer, Integer> pair : links.entries()) {
               Integer start = pair.getKey();
               Integer end = pair.getValue();
               Optional<StarWidget> startStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == start).findFirst();
               Optional<StarWidget> endStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == end).findFirst();
               if (startStar.isPresent() && endStar.isPresent()) {
                  this.executeForConnection(
                     startStar.get().getStar().getPos(),
                     endStar.get().getStar().getPos(),
                     1.1F,
                     point -> this.addStaticPoint((int)point.x, (int)point.y, 0.06F, Pair.of(start, end))
                  );
               }
            }

            for (Pair<Integer, Integer> pair : this.points.stream().map(BurnPoint::getLink).filter(Objects::nonNull).toList()) {
               Integer start = (Integer)pair.getKey();
               Integer end = (Integer)pair.getValue();
               if (!links.containsEntry(start, end) && !links.containsEntry(end, start)) {
                  this.points
                     .stream()
                     .filter(
                        point -> point.getLink() != null
                           && ((Integer)point.getLink().getKey()).equals(start)
                           && ((Integer)point.getLink().getValue()).equals(end)
                     )
                     .forEach(entry -> {
                        entry.setLink(null);
                        entry.setTicker(point -> {
                           int time = point.getLifeTime();
                           if (time > 0) {
                              point.setLifeTime(--time);
                              float diff = Mth.clamp(point.getLifeTime(), 0.01F, point.getMaxLifeTime()) / point.getMaxLifeTime();
                              point.setScaleO(point.getScale());
                              point.setScale(point.getScale() * diff);
                           }
                        });
                     });
               }
            }
         }

         for (BurnPoint point : this.points) {
            point.tick();
         }
      }
   }

   private void addAbstractPoint(BurnPoint point) {
      if (this.points.size() < 256) {
         Optional<BurnPoint> optional = this.points.stream().filter(entry -> entry.getX() == point.getX() && entry.getY() == point.getY()).findAny();
         if (optional.isPresent()) {
            if (optional.get().getLifeTime() > 0) {
               return;
            }
         } else {
            optional = this.points.stream().filter(entry -> entry.getLifeTime() <= 0).findFirst();
         }

         if (optional.isEmpty()) {
            this.points.add(point);
         } else {
            optional.get().set(point);
         }
      }
   }

   private void addLivingPoint(int x, int y, int lifeTime, float scale) {
      this.addAbstractPoint(BurnPoint.builder(x, y, scale).lifeTime(lifeTime).maxLifeTime(lifeTime).ticker(point -> {
         int time = point.getLifeTime();
         if (time >= 0) {
            point.setLifeTime(--time);
            float diff = Mth.clamp(point.getLifeTime(), 0.01F, point.getMaxLifeTime()) / point.getMaxLifeTime();
            point.setScaleO(point.getScale());
            point.setScale(point.getMaxScale() * diff);
         }
      }).build());
   }

   private void addStaticPoint(int x, int y, float scale, Pair<Integer, Integer> link) {
      this.addAbstractPoint(BurnPoint.builder(x, y, scale).scale(0.0F).lifeTime(1).maxLifeTime(8).ticker(point -> {
         point.setScaleO(point.getScale());
         if (this.researchProgress <= 0) {
            int time = point.getLifeTime();
            if (time >= point.getMaxLifeTime()) {
               return;
            }

            point.setLifeTime(++time);
            float diff = Mth.clamp(point.getLifeTime(), 0.01F, point.getMaxLifeTime()) / point.getMaxLifeTime();
            point.setScale(point.getMaxScale() * diff);
         } else {
            point.setScale(point.getScale() + this.researchProgress * 7.5E-4F);
         }
      }).link(link).build());
   }

   public void renderBackground(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.renderBackground(guiGraphics, pMouseX, pMouseY, pPartialTick);
      LocalPlayer player = this.minecraft.player;
      if (this.stack != null && this.stack.getItem() instanceof IRelicItem relic && player != null) {
         RelicData relicData = relic.getRelicData();
         if (relicData != null) {
            PoseStack poseStack = guiGraphics.pose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            GUIRenderer.begin(DescriptionTextures.SPACE_BACKGROUND, poseStack)
               .texSize(418, 4096)
               .patternSize(this.backgroundWidth, this.backgroundHeight)
               .pos(this.x + this.backgroundWidth / 2.0F, this.y + this.backgroundHeight / 2.0F)
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
            float color = (float)(0.5 + Math.sin((player.tickCount + pPartialTick) * 0.1F) * 0.1F);
            GUIRenderer.begin(DescriptionTextures.getAbilityCardTexture(this.stack, this.ability), poseStack)
               .anchor(SpriteAnchor.TOP_LEFT)
               .color(color, color, color, 1.0F)
               .pos(this.x + 67, this.y + 54)
               .texSize(110, 155)
               .end();
            ResearchData researchData = relic.getAbilityData(this.ability).getResearchData();

            for (Entry<Integer, Integer> link : relic.getResearchLinks(this.stack, this.ability).entries()) {
               Vec2 start = researchData.getStars().get(link.getKey()).getPos();
               Vec2 end = researchData.getStars().get(link.getValue()).getPos();
               this.drawLink(poseStack, this.getScaledPos(start), this.getScaledPos(end), pMouseX, pMouseY, pPartialTick);
            }

            if (this.selectedStar != null) {
               Vec2 pos = this.selectedStar.getPos();
               this.drawLink(poseStack, this.getScaledPos(pos), new Vec2(pMouseX, pMouseY), pMouseX, pMouseY, pPartialTick);
            }

            for (StarWidget widget : this.stars) {
               widget.renderWidget(guiGraphics, pMouseX, pMouseY, pPartialTick);
            }

            poseStack.pushPose();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, DescriptionTextures.RESEARCH_FOG);
            List<Vector2f> positions = Lists.newArrayList(new Vector2f[]{new Vector2f(pMouseX, pMouseY)});
            List<Float> scales = Lists.newArrayList(new Float[]{0.15F});
            List<Float> noises = Lists.newArrayList(new Float[]{10.0F});
            this.addLivingPoint(pMouseX, pMouseY, 3, 0.1F);

            for (BurnPoint point : this.points) {
               boolean shouldRender = point.getLifeTime() > 0;
               positions.add(new Vector2f(shouldRender ? point.getX() : -100.0F, shouldRender ? point.getY() : -100.0F));
               scales.add(Mth.lerp(pPartialTick, point.getScaleO(), point.getScale()));
               noises.add(point.getScale() * 75.0F);
            }

            poseStack.translate(0.0F, 0.0F, 5000.0F);
            RenderUtils.renderRevealingPanel(
               poseStack, this.x + 67, this.y + 54, 110.0F, 155.0F, positions, scales, noises, (player.tickCount + pPartialTick) / 50.0F
            );
            GUIRenderer.begin(DescriptionTextures.RESEARCH_BACKGROUND, poseStack).anchor(SpriteAnchor.TOP_LEFT).pos(this.x + 60, this.y + 45).end();
            poseStack.popPose();
            poseStack.pushPose();
            MutableComponent title = Component.translatableWithFallback(
               "tooltip.relics." + BuiltInRegistries.ITEM.getKey(this.stack.getItem()).getPath() + ".ability." + this.ability, this.ability
            );
            if (!relic.isAbilityUnlocked(this.stack, this.ability)) {
               title = ScreenUtils.stylizeWithReplacement(
                  title, 1.0, Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT).withColor(10354864), this.ability.length()
               );
               RandomSource random = player.getRandom();
               float shakeX = MathUtils.randomFloat(random) * 0.5F;
               float shakeY = MathUtils.randomFloat(random) * 0.5F;
               poseStack.translate(shakeX, shakeY, 0.0F);
            } else {
               title.withStyle(ChatFormatting.BOLD);
            }

            poseStack.translate((int)(this.x + 184 + 51.0F - this.minecraft.font.width(title) / 2.0F / 1.3F), this.y + 67, 0.0F);
            poseStack.scale(0.75F, 0.75F, 1.0F);
            guiGraphics.drawString(this.minecraft.font, title, 0, 0, 6696723, false);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(this.x + 184 + 51.0F, this.y + 100, 0.0F);
            poseStack.scale(0.5F, 0.5F, 1.0F);
            int yOff = 0;
            scales = new ArrayList<>();

            for (StatData stat : relic.getAbilityData(this.ability).getStats().values()) {
               scales.add(
                  (Number)stat.getFormatValue()
                     .apply(relic.getStatValue(this.stack, this.ability, stat.getId(), relic.getAbilityLevel(this.stack, this.ability)))
               );
            }

            MutableComponent component = Component.translatable(
               "tooltip.relics." + BuiltInRegistries.ITEM.getKey(this.stack.getItem()).getPath() + ".ability." + this.ability + ".description",
               scales.toArray()
            );
            int startColor = 15007999;
            int endColor = 6696723;
            float progress = this.researchProgress / 40.0F;
            int startRed = startColor >> 16 & 0xFF;
            int startGreen = startColor >> 8 & 0xFF;
            int startBlue = startColor & 0xFF;
            int endRed = endColor >> 16 & 0xFF;
            int endGreen = endColor >> 8 & 0xFF;
            int endBlue = endColor & 0xFF;
            int red = (int)(startRed + (endRed - startRed) * progress);
            int green = (int)(startGreen + (endGreen - startGreen) * progress);
            int blue = (int)(startBlue + (endBlue - startBlue) * progress);
            int colorx = red << 16 | green << 8 | blue;
            if (this.researchProgress < 40) {
               component.withColor(colorx);
            }

            MutableComponent var42 = ScreenUtils.stylizeWithReplacement(
               component, 1.0F - progress, Style.EMPTY.withFont(ScreenUtils.ILLAGER_ALT_FONT), this.ability.length()
            );

            for (FormattedCharSequence line : this.minecraft.font.split(var42, 180)) {
               guiGraphics.drawString(this.minecraft.font, line, -(this.minecraft.font.width(line) / 2.0F), yOff, 6696723, false);
               yOff += 10;
            }

            poseStack.popPose();
         }
      }
   }

   public Vec2 getScaledPos(Vec2 pos) {
      int scale = 5;
      return new Vec2(this.x + 67 + pos.x * scale, this.y + 54 + pos.y * scale);
   }

   private void drawLink(PoseStack poseStack, Vec2 start, Vec2 end, int mouseX, int mouseY, float partialTick) {
      if (this.stack.getItem() instanceof IRelicItem relic) {
         poseStack.pushPose();
         float var13 = (float)(Math.sin((this.minecraft.player.tickCount + partialTick + start.length()) * 0.2F) * 0.1F);
         float color = 1.25F + var13;
         if (!relic.isAbilityResearched(this.stack, this.ability) && this.isHoveringConnection(start, end, mouseX, mouseY)) {
            RenderSystem.setShaderColor(color, 0.25F, 0.25F, 0.75F + var13);
         } else {
            RenderSystem.setShaderColor(color, color, color, 0.75F + var13);
         }

         RenderSystem.enableBlend();
         int width = 6;
         int height = 4;
         int distance = (int)Math.sqrt(start.distanceToSqr(end));
         poseStack.translate(start.x, start.y, 0.0F);
         poseStack.mulPose(Axis.ZP.rotationDegrees(this.getAngle(start, end)));
         poseStack.translate(-(width / 2.0F), -(height / 2.0F), 0.0F);
         RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/research/line.png"));
         GUIRenderer.begin(ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/research/line.png"), poseStack)
            .pos(0.0F, 0.0F)
            .texSize(width, height * 6)
            .patternSize(distance, height)
            .anchor(SpriteAnchor.TOP_LEFT)
            .animation(AnimationData.builder().frame(0, 2).frame(1, 2).frame(2, 2).frame(3, 2).frame(4, 2).frame(5, 2))
            .end();
         poseStack.mulPose(Axis.ZP.rotationDegrees(-this.getAngle(start, end)));
         RenderSystem.disableBlend();
         poseStack.popPose();
      }
   }

   private void executeForConnection(Vec2 start, Vec2 end, float step, Consumer<Vec2> task) {
      int steps = (int)(Math.sqrt(start.distanceToSqr(end)) / step);
      Vec2 direction = new Vec2(end.x - start.x, end.y - start.y).normalized();

      for (int i = 0; i <= steps; i++) {
         Vec2 point = new Vec2(direction.x * step * i, direction.y * step * i).add(start);
         task.accept(this.getScaledPos(point));
      }
   }

   private boolean isHoveringConnection(Vec2 start, Vec2 end, int mouseX, int mouseY) {
      float minDistance = 4.0F;
      float thickness = 4.0F;
      float x1 = start.x;
      float y1 = start.y;
      float x2 = end.x;
      float y2 = end.y;
      double distanceToStart = Math.hypot(mouseX - x1, mouseY - y1);
      double distanceToEnd = Math.hypot(mouseX - x2, mouseY - y2);
      if (!(distanceToStart < minDistance) && !(distanceToEnd < minDistance)) {
         double collinearity = (x2 - x1) * (mouseY - y1) - (y2 - y1) * (mouseX - x1);
         double lineLength = Math.hypot(x2 - x1, y2 - y1);
         double distanceFromLine = Math.abs(collinearity / lineLength);
         return distanceFromLine > thickness / 2.0F
            ? false
            : Math.min(x1, x2) - thickness / 2.0F <= mouseX
               && mouseX <= Math.max(x1, x2) + thickness / 2.0F
               && Math.min(y1, y2) - thickness / 2.0F <= mouseY
               && mouseY <= Math.max(y1, y2) + thickness / 2.0F;
      } else {
         return false;
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

   public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
      if (this.stack.getItem() instanceof IRelicItem relic && !relic.isAbilityResearched(this.stack, this.ability) && pButton == 0) {
         ResearchData researchData = relic.getResearchData(this.ability);
         Pair<Integer, Integer> toRemove = null;

         for (Entry<Integer, Integer> link : relic.getResearchLinks(this.stack, this.ability).entries()) {
            if (this.isHoveringConnection(
               this.getScaledPos(researchData.getStars().get(link.getKey()).getPos()),
               this.getScaledPos(researchData.getStars().get(link.getValue()).getPos()),
               (int)pMouseX,
               (int)pMouseY
            )) {
               toRemove = Pair.of(link.getKey(), link.getValue());
            }
         }

         if (toRemove != null) {
            this.removeLink((Integer)toRemove.getKey(), (Integer)toRemove.getValue());
         }
      }

      return super.mouseClicked(pMouseX, pMouseY, pButton);
   }

   public void addLink(int start, int end) {
      Optional<StarWidget> startStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == start).findFirst();
      Optional<StarWidget> endStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == end).findFirst();
      if (!startStar.isEmpty() && !endStar.isEmpty()) {
         RandomSource random = this.minecraft.player.getRandom();
         NetworkHandler.sendToServer(new PacketManageLink(this.container, this.slot, this.ability, PacketManageLink.Operation.ADD, start, end));
         this.executeForConnection(
            startStar.get().getStar().getPos(),
            endStar.get().getStar().getPos(),
            0.25F,
            point -> ParticleStorage.addParticle(
               this,
               new ResearchParticleData(
                  new Color(100 + random.nextInt(150), random.nextInt(25), 200 + random.nextInt(50)),
                  point.x + MathUtils.randomFloat(random),
                  point.y + MathUtils.randomFloat(random),
                  1.0F + random.nextFloat() * 0.25F,
                  20 + random.nextInt(60),
                  random.nextFloat() * 0.025F
               )
            )
         );
      }
   }

   public void removeLink(int start, int end) {
      Optional<StarWidget> startStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == start).findFirst();
      Optional<StarWidget> endStar = this.stars.stream().filter(entry -> entry.getStar().getIndex() == end).findFirst();
      if (!startStar.isEmpty() && !endStar.isEmpty()) {
         RandomSource random = this.minecraft.player.getRandom();
         NetworkHandler.sendToServer(new PacketManageLink(this.container, this.slot, this.ability, PacketManageLink.Operation.REMOVE, start, end));
         this.executeForConnection(
            startStar.get().getStar().getPos(),
            endStar.get().getStar().getPos(),
            0.1F,
            point -> ParticleStorage.addParticle(
               this,
               new ResearchParticleData(
                  new Color(100 + random.nextInt(150), random.nextInt(25), 200 + random.nextInt(50)),
                  point.x + MathUtils.randomFloat(random),
                  point.y + MathUtils.randomFloat(random),
                  1.0F + random.nextFloat() * 0.25F,
                  10 + random.nextInt(50),
                  random.nextFloat() * 0.01F
               )
            )
         );
         this.minecraft
            .getSoundManager()
            .play(
               SimpleSoundInstance.forUI((SoundEvent)SoundRegistry.DISCONNECT_STARS.get(), 0.75F + this.minecraft.player.getRandom().nextFloat() * 0.5F, 0.75F)
            );
      }
   }

   public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
      if (this.stack.getItem() instanceof IRelicItem relic && pButton == 0 && this.selectedStar != null) {
         for (StarWidget widget : this.stars) {
            if (widget.isHovered()) {
               Multimap<Integer, Integer> links = relic.getResearchLinks(this.stack, this.ability);
               int start = this.selectedStar.getIndex();
               int end = widget.getStar().getIndex();
               StarData star = widget.getStar();
               if (start != end
                  && this.getOccupiedConnectionsCount(star) < this.getTotalConnectionsCount(star)
                  && !links.containsEntry(start, end)
                  && !links.containsEntry(end, start)) {
                  this.addLink(start, end);
                  break;
               }
            }
         }

         this.selectedStar = null;
      }

      return super.mouseReleased(pMouseX, pMouseY, pButton);
   }

   public void onClose() {
      this.screen.rebuildWidgets();
      this.minecraft.setScreen(this.screen);
   }

   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public int getAutoScale() {
      return 0;
   }

   public float getAngle(Vec2 from, Vec2 to) {
      float angle = (float)Math.toDegrees(Math.atan2(to.y - from.y, to.x - from.x));
      if (angle < 0.0F) {
         angle += 360.0F;
      }

      return angle;
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
