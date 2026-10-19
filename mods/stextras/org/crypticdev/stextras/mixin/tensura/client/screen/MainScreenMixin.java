package org.crypticdev.stextras.mixin.tensura.client.screen;

import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.client.screen.MainScreen;
import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import java.awt.Color;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.mixin.access.SimpleScreenAccessor;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.utils.PrestigeUtils;
import org.crypticdev.stextras.utils.TextUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MainScreen.class)
public abstract class MainScreenMixin {
   @Unique
   private static final int SETTINGS_Y_OFFSET = 21;
   @Unique
   private static final int SETTINGS_X_OFFSET = 4;
   @Unique
   private static final String SETTINGS_TEXTURE = "textures/gui/settings_tab_button.png";
   @Unique
   private static final String BACKGROUND_TEXTURE = "textures/gui/main/main_gui.png";
   @Unique
   private static final String ST_PRESTIGE_TEXTURE = "textures/gui/st_prestige_tab.png";
   @Shadow
   @Final
   @Mutable
   protected ResourceLocation BACKGROUND;
   @Shadow
   @Final
   @Mutable
   protected ResourceLocation SETTINGS;
   @Unique
   private SimpleButton stextras$prestigeButton;

   @Inject(method = "init()V", at = @At("TAIL"))
   private void stextras$moveSettingsAndAddPrestige(CallbackInfo ci) {
      Screen self = (Screen)this;
      List<? extends GuiEventListener> renderables = self.children();
      AbstractWidget settingsBtn = null;
      int rightMostX = Integer.MIN_VALUE;

      for (GuiEventListener r : renderables) {
         if (r instanceof AbstractWidget w && w.getWidth() == 24 && w.getHeight() == 21) {
            int x = w.getX();
            if (x > rightMostX) {
               rightMostX = x;
               settingsBtn = w;
            }
         }
      }

      if (settingsBtn != null) {
         int originalX = settingsBtn.getX();
         int originalY = settingsBtn.getY();
         settingsBtn.setY(originalY + 21);
         settingsBtn.setX(originalX + 4);
         ResourceLocation prestigeTex = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/st_prestige_tab.png");
         SimpleButton prestige = new SimpleButton(
            originalX, originalY, 24, 21, prestigeTex, null, Component.translatable("stextras.prestige_tab"), onPress -> ScreenHelper.openScreen(-5)
         );
         this.stextras$prestigeButton = prestige;
         this.stextras$registerChild(prestige);
      }
   }

   @Inject(method = "<init>()V", at = @At("TAIL"))
   private void stextras$swapTextures(CallbackInfo ci) {
      this.BACKGROUND = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/main/main_gui.png");
      this.SETTINGS = ResourceLocation.fromNamespaceAndPath("stextras", "textures/gui/settings_tab_button.png");
   }

   @Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("TAIL"))
   private void stextras$renderPrestigeButton(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
      if (this.stextras$prestigeButton != null) {
         this.stextras$prestigeButton.render(graphics, mouseX, mouseY, partialTicks);
      }
   }

   @Inject(method = "renderRaceStats", at = @At("HEAD"), cancellable = true, remap = false)
   private void stextras$drawCustomTextBlocks(GuiGraphics graphics, int mX, int mY, CallbackInfo ci) {
      ci.cancel();
      SimpleScreenAccessor ss = (SimpleScreenAccessor)this;
      int guiLeft = ss.stextras$getGuiLeft();
      int guiTop = ss.stextras$getGuiTop();
      Font font = Minecraft.getInstance().font;
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         IExistence existenceData = TensuraStorages.getExistenceFrom(player);
         Races raceData = RaceAPI.getRaceFrom(player);
         Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(player).getRace();
         if (!optional.isEmpty()) {
            ManasRaceInstance race = optional.get();
            Alignment alignment = existenceData.getAlignment();
            String raceName = race.getDisplayName().getString();

            Color color = switch (alignment) {
               case CHAOS -> Color.RED;
               case MAJIN -> Color.MAGENTA;
               case HOLY -> Color.YELLOW;
               default -> Color.WHITE;
            };
            double mana = existenceData.getMagicule();
            double aura = existenceData.getAura();
            double maxMana = player.getAttributeValue(TensuraAttributes.MAX_MAGICULE);
            double maxAura = player.getAttributeValue(TensuraAttributes.MAX_AURA);
            graphics.drawString(font, raceName, guiLeft + 95, guiTop + 36, color.getRGB());
            RenderHelper.drawTextWithTooltip(
               graphics,
               font,
               Component.translatable("stextras.main_menu.soul_grade", new Object[]{STExtarsStorage.Player.soulGradeLabel()}).getString(),
               guiLeft + 95,
               guiTop + 52,
               Color.WHITE.getRGB(),
               mX,
               mY,
               Component.translatable("stextras.main_menu.soul_grade.hover", new Object[]{STExtarsStorage.Player.getSoulGrade()}),
               (MainScreen)this
            );
            graphics.drawString(
               font,
               Component.translatable("tensura.main_menu.souls", new Object[]{existenceData.getSoulPoints() / 100 / 10.0}),
               guiLeft + 95,
               guiTop + 68,
               Color.WHITE.getRGB()
            );
            RenderHelper.drawTextWithTooltip(
               graphics,
               font,
               Component.translatable("tensura.main_menu.existence_points", new Object[]{RenderHelper.getShortenedNumber(maxMana + maxAura)}).getString(),
               guiLeft + 13,
               guiTop + 144,
               Color.ORANGE.getRGB(),
               mX,
               mY,
               Component.translatable("tensura.main_menu.existence_points", new Object[]{Math.round(maxMana + maxAura)}),
               (MainScreen)this
            );
            RenderHelper.drawTextWithTooltip(
               graphics,
               font,
               Component.translatable(
                     "tensura.main_menu.magicule", new Object[]{RenderHelper.getShortenedNumber(mana), RenderHelper.getShortenedNumber(maxMana)}
                  )
                  .getString(),
               guiLeft + 95,
               guiTop + 93,
               Color.WHITE.getRGB(),
               mX,
               mY,
               Component.translatable("tensura.main_menu.magicule", new Object[]{Math.round(mana), Math.round(maxMana)}),
               (MainScreen)this
            );
            RenderHelper.drawTextWithTooltip(
               graphics,
               font,
               Component.translatable("tensura.main_menu.aura", new Object[]{RenderHelper.getShortenedNumber(aura), RenderHelper.getShortenedNumber(maxAura)})
                  .getString(),
               guiLeft + 95,
               guiTop + 114,
               Color.WHITE.getRGB(),
               mX,
               mY,
               Component.translatable("tensura.main_menu.aura", new Object[]{Math.round(aura), Math.round(maxAura)}),
               (MainScreen)this
            );
            RenderHelper.drawTextWithTooltip(
               graphics,
               font,
               Component.translatable(
                     "stextras.main_menu.reincarnation_essence",
                     new Object[]{RenderHelper.getShortenedNumber(STExtarsStorage.Player.getReincarnationEssence())}
                  )
                  .getString(),
               guiLeft + 95,
               guiTop + 135,
               Color.WHITE.getRGB(),
               mX,
               mY,
               Component.translatable("stextras.main_menu.reincarnation_essence_hover", new Object[]{STExtarsStorage.Player.getReincarnationEssence()}),
               (MainScreen)this
            );
         }
      }
   }

   @Inject(method = "getResetCounterProgress", at = @At("RETURN"), cancellable = true, require = 0)
   private void stextras$modifyResetCounterProgress(CallbackInfoReturnable<Component> cir) {
      cir.setReturnValue(this.stextras$buildPrestigeRequirements());
   }

   @Unique
   private Component stextras$buildPrestigeRequirements() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player == null) {
         return Component.empty();
      }

      MutableComponent component = Component.literal("  ")
         .append(Component.literal(TextUtil.toSmallCaps("Prestige Requirements")))
         .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD});
      boolean allMet = true;
      boolean maxEvo = RaceAPI.getRaceFrom(player).getRace().map(r -> r.getNextEvolutions(player).isEmpty()).orElse(false);
      component.append("\n").append(this.stextras$formatEntry("Max Evo", "Completed", maxEvo));
      if (!maxEvo) {
         allMet = false;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(player);
      boolean awaken = existence.isTrueHero() || existence.isTrueDemonLord();
      component.append("\n").append(this.stextras$formatEntry("Awakened", "Completed", awaken));
      if (!awaken) {
         allMet = false;
      }

      int currentRe = STExtarsStorage.Client.player().getReincarnationEssence();
      int requiredRe = PrestigeUtils.getReincarnationEssenceCost(TensuraStorages.getPlayerDataFrom(player).getResetCounter());
      boolean enoughRe = currentRe >= requiredRe;
      String reProgress = TextUtil.formatNumber(currentRe) + " / " + TextUtil.formatNumber(requiredRe);
      component.append("\n").append(this.stextras$formatEntry("Essence", reProgress, enoughRe));
      if (!enoughRe) {
         allMet = false;
      }

      int kills = STExtarsStorage.Client.bossKills();
      int requiredKills = PrestigeUtils.requiredBossKills(TensuraStorages.getPlayerDataFrom(player).getResetCounter());
      boolean bossMet = kills >= requiredKills;
      component.append("\n").append(this.stextras$formatEntry("Boss Kills", kills + "/" + requiredKills, bossMet));
      if (!bossMet) {
         allMet = false;
      }

      boolean questsDone = STExtarsStorage.Client.quests().active(QuestCategory.REQUIRED).isEmpty();
      component.append("\n").append(this.stextras$formatEntry("Quests", "Completed", questsDone));
      if (!questsDone) {
         allMet = false;
      }

      return allMet
         ? Component.literal("  ")
            .append(Component.literal("✔ " + TextUtil.toSmallCaps("Ready to Prestige")))
            .withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD})
            .append("\n\n")
            .append(component)
            .append("\n\n")
            .append(Component.literal(TextUtil.toSmallCaps("Character reset to pres")).withStyle(ChatFormatting.GREEN))
         : component.append("\n\n").append(Component.literal(TextUtil.toSmallCaps("Character reset to pres")).withStyle(ChatFormatting.GREEN));
   }

   @Unique
   private Component stextras$formatEntry(String label, String value, boolean isMet) {
      MutableComponent bullet = Component.literal("  ● ").withStyle(isMet ? ChatFormatting.GREEN : ChatFormatting.RED);
      MutableComponent labelComp = Component.literal(TextUtil.toSmallCaps(label) + ": ").withStyle(ChatFormatting.GRAY);
      MutableComponent valueComp = Component.literal(value).withStyle(isMet ? ChatFormatting.WHITE : ChatFormatting.RED);
      MutableComponent icon = Component.literal(isMet ? " ✔" : " ✘").withStyle(isMet ? ChatFormatting.DARK_GREEN : ChatFormatting.DARK_RED);
      return bullet.append(labelComp).append(valueComp).append(icon);
   }

   @Unique
   private int stextras$getReincarnationEssence() {
      return STExtarsStorage.Player.getReincarnationEssence();
   }

   @Unique
   private void stextras$registerChild(GuiEventListener listener) {
      ((Screen)this).children().add(listener);
   }
}
