package it.hurts.sskirillss.relics.system.casts.handlers;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.hurts.sskirillss.relics.init.HotkeyRegistry;
import it.hurts.sskirillss.relics.init.SoundRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastStage;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.misc.CastType;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.abilities.SpellCastPacket;
import it.hurts.sskirillss.relics.system.casts.abilities.AbilityCache;
import it.hurts.sskirillss.relics.system.casts.abilities.AbilityReference;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.RenderUtils;
import it.hurts.sskirillss.relics.utils.data.AnimationData;
import java.util.Collection;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent.MouseScrollingEvent;
import net.neoforged.neoforge.client.event.InputEvent.MouseButton.Pre;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@OnlyIn(Dist.CLIENT)
public class HUDRenderHandler {
   public static final ResourceLocation CARD_FRAME_ACTIVE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/card_frame_active.png");
   public static final ResourceLocation CARD_FRAME_INACTIVE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/card_frame_inactive.png");
   public static final ResourceLocation CARD_POINTER_ACTIVE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/card_pointer_active.png");
   public static final ResourceLocation CARD_POINTER_INACTIVE = ResourceLocation.fromNamespaceAndPath(
      "relics", "textures/hud/abilities/card_pointer_inactive.png"
   );
   public static final ResourceLocation ARROW_RIGHT = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/arrow_right.png");
   public static final ResourceLocation ARROW_LEFT = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/arrow_left.png");
   public static final ResourceLocation ARROW_RIGHT_OUTLINE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/arrow_right_outline.png");
   public static final ResourceLocation ARROW_LEFT_OUTLINE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/arrow_left_outline.png");
   public static final ResourceLocation STATE_TOGGLEABLE = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/widgets/toggleable.png");
   public static final ResourceLocation STATE_CYCLICAL = ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/widgets/cyclical.png");
   private static final Minecraft MC = Minecraft.getInstance();
   private static int selectedIndex = 0;
   private static boolean animationDown = false;
   private static int animationDelta = 0;
   private static int castShakeDelta = 0;
   private static int mouseDelta = 0;

   public static void render(GuiGraphics guiGraphics, float partialTicks) {
      if (animationDelta != 0) {
         PoseStack poseStack = guiGraphics.pose();
         Window window = MC.getWindow();
         LocalPlayer player = MC.player;
         if (player != null && !CacheHandler.REFERENCES.isEmpty()) {
            int x = window.getGuiScaledWidth() / 2;
            int y = -38;
            poseStack.pushPose();
            poseStack.translate(0.0F, (animationDelta - (animationDelta != 5 ? partialTicks * (animationDown ? -1 : 1) : 0.0F)) * 16.0F, 0.0F);
            float shakeOffset = castShakeDelta > 0 ? (castShakeDelta - partialTicks) * 0.25F : 0.0F;
            drawAbility(guiGraphics, player, -2, x - 74 - shakeOffset, y, partialTicks);
            drawAbility(guiGraphics, player, -1, x - 39 - shakeOffset, y, partialTicks);
            drawAbility(guiGraphics, player, 0, x, y, partialTicks);
            drawAbility(guiGraphics, player, 1, x + 39 + shakeOffset, y, partialTicks);
            drawAbility(guiGraphics, player, 2, x + 74 + shakeOffset, y, partialTicks);
            RenderSystem.setShaderTexture(0, ARROW_LEFT);
            RenderUtils.renderTextureFromCenter(
               poseStack, x - 100 - shakeOffset, y + 2, 0.0F, 0.0F, 11.0F, 30.0F, 11.0F, 30.0F, 1.0F + (mouseDelta < 0 ? Math.abs(mouseDelta) * 0.01F : 0.0F)
            );
            if (mouseDelta < 0) {
               RenderSystem.setShaderTexture(0, ARROW_LEFT_OUTLINE);
               RenderUtils.renderTextureFromCenter(
                  poseStack, x - 100 - shakeOffset, y + 2, 0.0F, 0.0F, 13.0F, 32.0F, 13.0F, 32.0F, 1.0F + Math.abs(mouseDelta) * 0.01F
               );
            }

            RenderSystem.setShaderTexture(0, ARROW_RIGHT);
            RenderUtils.renderTextureFromCenter(
               poseStack, x + 100 + shakeOffset, y + 2, 0.0F, 0.0F, 11.0F, 30.0F, 11.0F, 30.0F, 1.0F + (mouseDelta > 0 ? Math.abs(mouseDelta) * 0.01F : 0.0F)
            );
            if (mouseDelta > 0) {
               RenderSystem.setShaderTexture(0, ARROW_RIGHT_OUTLINE);
               RenderUtils.renderTextureFromCenter(
                  poseStack, x + 100 + shakeOffset, y + 2, 0.0F, 0.0F, 13.0F, 32.0F, 13.0F, 32.0F, 1.0F + Math.abs(mouseDelta) * 0.01F
               );
            }

            RenderSystem.disableBlend();
            AbilityReference selectedAbility = getAbilityByIndex(selectedIndex);
            AbilityCache selectedCache = getCacheByIndex(selectedIndex);
            if (selectedAbility != null && selectedCache != null) {
               ItemStack stack = selectedAbility.getSlot().gatherStack(player);
               String registryName = BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath();
               MutableComponent name = Component.translatable("tooltip.relics." + registryName + ".ability." + selectedAbility.getId());
               guiGraphics.drawString(MC.font, name, x - MC.font.width(name) / 2, y - 38, 16777215, true);
               poseStack.popPose();
               poseStack.pushPose();
               poseStack.translate((animationDelta - (animationDelta != 5 ? partialTicks * (animationDown ? -1 : 1) : 0.0F)) * 16.0F, 0.0F, 0.0F);
               int yOff = 0;
               int var18 = -70;
               int var19 = 25;

               for (Entry<String, Boolean> entry : selectedCache.getPredicates().entrySet()) {
                  String predicateName = entry.getKey();
                  boolean isCompleted = entry.getValue();
                  RenderSystem.setShaderTexture(
                     0,
                     isCompleted
                        ? ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/icons/completed.png")
                        : ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/icons/" + registryName + "/" + predicateName + ".png")
                  );
                  RenderUtils.renderTextureFromCenter(poseStack, var18, var19 + yOff, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 16.0F, 0.5F);
                  poseStack.scale(0.5F, 0.5F, 0.5F);
                  guiGraphics.drawString(
                     MC.font,
                     Component.translatable("tooltip.relics." + registryName + ".ability." + selectedAbility.getId() + ".predicate." + predicateName)
                        .withStyle(isCompleted ? ChatFormatting.STRIKETHROUGH : ChatFormatting.RESET),
                     (var18 + 7) * 2,
                     (var19 - 2 + yOff) * 2,
                     isCompleted ? 12517304 : 15826844,
                     true
                  );
                  poseStack.scale(2.0F, 2.0F, 2.0F);
                  yOff += 10;
               }

               poseStack.popPose();
            }
         }
      }
   }

   private static void drawAbility(GuiGraphics guiGraphics, LocalPlayer player, int realIndex, float x, float y, float partialTicks) {
      int relativeIndex = getRelativeIndex(realIndex);
      AbilityReference ability = getAbilityByIndex(relativeIndex);
      AbilityCache cache = getCacheByIndex(relativeIndex);
      if (ability != null && cache != null) {
         ItemStack stack = ability.getSlot().gatherStack(player);
         if (stack.getItem() instanceof IRelicItem relic) {
            PoseStack var24 = guiGraphics.pose();
            boolean isLocked = !relic.canPlayerUseAbility(player, stack, ability.getId());
            ResourceLocation card = ResourceLocation.fromNamespaceAndPath(
               "relics",
               "textures/abilities/"
                  + BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath()
                  + "/"
                  + (String)relic.getAbilityData(ability.getId()).getIcon().apply(player, stack, ability.getId())
                  + ".png"
            );
            RenderSystem.setShaderTexture(0, card);
            RenderSystem.enableBlend();
            var24.pushPose();
            int width = 20;
            int height = 29;
            float scale = (float)(
               1.0
                  + Mth.clamp(Math.pow(15.0, -Math.abs(realIndex)), 0.0, 0.2F)
                  + (
                     realIndex == 0
                        ? Math.sin((player.tickCount + partialTicks) * 0.1F) * 0.05F + (castShakeDelta > 0 ? (castShakeDelta - partialTicks) * 0.02F : 0.0F)
                        : 0.0
                  )
            );
            RenderUtils.renderTextureFromCenter(var24, x - scale + 1.0F, y - scale + 2.0F, width, height, scale + 0.025F);
            int cooldown = relic.getAbilityCooldown(stack, ability.getId());
            int cap = relic.getAbilityCooldownCap(stack, ability.getId());
            String iconDescription = "";
            if (cooldown > 0) {
               RenderSystem.setShaderTexture(0, card);
               RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
               float percentage = cooldown / (cap / 100.0F) / 100.0F;
               RenderUtils.renderTextureFromCenter(
                  var24,
                  x - scale,
                  y + 2.0F - scale + height * scale / 2.0F - height * scale / 2.0F * percentage,
                  0.0F,
                  height - height * percentage,
                  width,
                  height,
                  width,
                  height * percentage,
                  scale + 0.025F
               );
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            }

            RenderSystem.setShaderTexture(0, isLocked ? CARD_FRAME_INACTIVE : CARD_FRAME_ACTIVE);
            RenderUtils.renderTextureFromCenter(var24, x, y + 1.0F, 0.0F, 0.0F, 30.0F, 41.0F, 30.0F, 41.0F, scale);
            if (relic.isAbilityTicking(stack, ability.getId())) {
               CastType type = relic.getAbilityData(ability.getId()).getCastData().getType();
               if (type == CastType.TOGGLEABLE) {
                  RenderSystem.setShaderTexture(0, STATE_TOGGLEABLE);
                  RenderSystem.enableBlend();
                  RenderUtils.renderAnimatedTextureFromCenter(
                     var24,
                     x,
                     y + 1.0F,
                     34.0F,
                     630.0F,
                     34.0F,
                     45.0F,
                     scale,
                     AnimationData.builder()
                        .frame(0, 1)
                        .frame(1, 1)
                        .frame(2, 1)
                        .frame(3, 1)
                        .frame(4, 1)
                        .frame(5, 1)
                        .frame(6, 1)
                        .frame(7, 1)
                        .frame(8, 1)
                        .frame(9, 1)
                        .frame(10, 1)
                        .frame(11, 1)
                        .frame(12, 1)
                        .frame(13, 1)
                  );
                  RenderSystem.disableBlend();
               } else {
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float)(Math.sin(player.tickCount * 0.5F) * 0.25 + 0.75));
                  RenderSystem.enableBlend();
                  RenderSystem.setShaderTexture(0, STATE_CYCLICAL);
                  RenderUtils.renderTextureFromCenter(var24, x - 0.5F, y + 1.0F, 33.0F, 45.0F, scale);
                  RenderSystem.disableBlend();
                  RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               }
            }

            if (realIndex == 0) {
               RenderSystem.setShaderTexture(0, isLocked ? CARD_POINTER_INACTIVE : CARD_POINTER_ACTIVE);
               RenderUtils.renderTextureFromCenter(var24, x, y - 20.0F, 0.0F, 0.0F, 6.0F, 11.0F, 6.0F, 11.0F, scale);
            }

            if (cooldown > 0) {
               RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/widgets/icons/cooldown.png"));
               RenderSystem.enableBlend();
               drawAbilityStatusIcon(
                  cache,
                  guiGraphics,
                  x - scale,
                  y - scale,
                  22.0F,
                  352.0F,
                  scale - 0.1F,
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
                     .frame(9, 1)
                     .frame(10, 1)
                     .frame(11, 1)
                     .frame(12, 1)
                     .frame(13, 1)
                     .frame(14, 1)
                     .frame(15, 1),
                  cap - cooldown,
                  partialTicks
               );
               RenderSystem.disableBlend();
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               iconDescription = String.valueOf(MathUtils.round(cooldown / 20.0, 1));
            } else {
               Collection<Boolean> infoEntries = cache.getPredicates().values();
               int successPredicates = 0;

               for (boolean value : infoEntries) {
                  if (value) {
                     successPredicates++;
                  }
               }

               int failedPredicates = infoEntries.size() - successPredicates;
               if (failedPredicates > 0) {
                  RenderSystem.setShaderTexture(0, ResourceLocation.fromNamespaceAndPath("relics", "textures/hud/abilities/widgets/icons/locked.png"));
                  RenderSystem.enableBlend();
                  drawAbilityStatusIcon(
                     cache,
                     guiGraphics,
                     x - scale,
                     y - scale,
                     20.0F,
                     80.0F,
                     scale - 0.1F,
                     AnimationData.builder().frame(0, 20).frame(1, 2).frame(2, 2).frame(3, 2).frame(2, 2).frame(1, 2),
                     player.tickCount,
                     partialTicks
                  );
                  RenderSystem.disableBlend();
                  iconDescription = successPredicates + "/" + infoEntries.size();
               }
            }

            if (!iconDescription.isEmpty()) {
               var24.scale(0.5F, 0.5F, 0.5F);
               MutableComponent descriptionComponent = Component.literal(iconDescription).withStyle(ChatFormatting.BOLD);
               guiGraphics.drawString(
                  MC.font,
                  descriptionComponent,
                  (int)(x * 2.0F - MC.font.width(descriptionComponent) / 2.0F),
                  (int)((y - 6.0F + scale * 15.0F) * 2.0F),
                  16777215,
                  true
               );
            }

            var24.popPose();
         }
      }
   }

   private static void drawAbilityStatusIcon(
      AbilityCache cache,
      GuiGraphics guiGraphics,
      float x,
      float y,
      float texWidth,
      float texHeight,
      float scale,
      @Nullable AnimationData animation,
      long ticks,
      float partialTicks
   ) {
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(x + 1.0F, y, 0.0F);
      if (cache.getIconShakeDelta() != 0) {
         float color = cache.getIconShakeDelta() * 0.04F;
         RenderSystem.setShaderColor(1.0F, 1.0F - color, 1.0F - color, 1.0F);
         poseStack.mulPose(Axis.ZP.rotation((float)Math.sin(((float)ticks + partialTicks) * 0.75F) * 0.1F));
         scale += (cache.getIconShakeDelta() - partialTicks) * 0.025F;
      }

      if (animation != null) {
         RenderUtils.renderAnimatedTextureFromCenter(poseStack, 0.0F, 0.0F, texWidth, texHeight, texWidth, texWidth, scale, animation, ticks);
      } else {
         RenderUtils.renderTextureFromCenter(poseStack, 0.0F, 0.0F, texWidth, texHeight, scale);
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      poseStack.popPose();
   }

   private static int getRelativeIndex(int offset) {
      int current = selectedIndex;
      int sum = current + offset;
      int max = CacheHandler.REFERENCES.size() - 1;
      return sum > max ? Math.min(max, sum - (max + 1)) : (sum < 0 ? Math.max(0, sum + max + 1) : sum);
   }

   @Nullable
   private static AbilityReference getAbilityByIndex(int index) {
      return CacheHandler.REFERENCES.isEmpty()
         ? null
         : (AbilityReference)CacheHandler.REFERENCES.keySet().toArray()[Mth.clamp(index, 0, CacheHandler.REFERENCES.size() - 1)];
   }

   @Nullable
   private static AbilityCache getCacheByIndex(int index) {
      return CacheHandler.REFERENCES.isEmpty()
         ? null
         : (AbilityCache)CacheHandler.REFERENCES.values().toArray()[Mth.clamp(index, 0, CacheHandler.REFERENCES.size() - 1)];
   }

   private static void applyDelta(int delta) {
      int current = selectedIndex;
      int sum = current + delta;
      int max = CacheHandler.REFERENCES.size() - 1;
      selectedIndex = sum > max ? sum - max - 1 : (sum < 0 ? max : sum);
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class CastEvents {
      @SubscribeEvent
      public static void onKeyPressed(Pre event) {
         if (HUDRenderHandler.animationDelta != 0 && event.getAction() == 1 && event.getButton() == 0) {
            Minecraft MC = Minecraft.getInstance();
            if (MC.screen == null) {
               Player player = MC.player;
               if (player != null) {
                  AbilityReference ability = HUDRenderHandler.getAbilityByIndex(HUDRenderHandler.selectedIndex);
                  AbilityCache cache = HUDRenderHandler.getCacheByIndex(HUDRenderHandler.selectedIndex);
                  if (ability != null && cache != null) {
                     ItemStack stack = ability.getSlot().gatherStack(player);
                     if (stack.getItem() instanceof IRelicItem relic) {
                        if (!relic.canPlayerUseAbility(player, stack, ability.getId())) {
                           int delta = cache.getIconShakeDelta();
                           cache.setIconShakeDelta(Math.min(20, delta + (delta > 0 ? 5 : 15)));
                           MC.getSoundManager()
                              .play(
                                 SimpleSoundInstance.forUI(
                                    relic.isAbilityOnCooldown(stack, ability.getId())
                                       ? (SoundEvent)SoundRegistry.ABILITY_COOLDOWN.get()
                                       : (SoundEvent)SoundRegistry.ABILITY_LOCKED.get(),
                                    1.0F
                                 )
                              );
                           event.setCanceled(true);
                        } else {
                           boolean isTicking = relic.isAbilityTicking(stack, ability.getId());
                           CastType type = relic.getAbilityData(ability.getId()).getCastData().getType();
                           MC.getSoundManager().play(SimpleSoundInstance.forUI((SoundEvent)SoundRegistry.ABILITY_CAST.get(), 1.0F));
                           switch (type) {
                              case INSTANTANEOUS:
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.INSTANTANEOUS, CastStage.END, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.END);
                                 break;
                              case CYCLICAL:
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.CYCLICAL, CastStage.START, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.START);
                                 break;
                              case INTERRUPTIBLE: {
                                 CastStage stage = isTicking ? CastStage.END : CastStage.START;
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.INTERRUPTIBLE, stage, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, stage);
                                 break;
                              }
                              case TOGGLEABLE: {
                                 CastStage stage = isTicking ? CastStage.END : CastStage.START;
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.TOGGLEABLE, stage, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, stage);
                              }
                           }

                           HUDRenderHandler.castShakeDelta = 10;
                           Minecraft.getInstance().mouseHandler.releaseMouse();
                        }
                     }
                  }
               }
            }
         }
      }

      @SubscribeEvent
      public static void onPlayerTick(Post event) {
         Player player = event.getEntity();
         if (player instanceof LocalPlayer) {
            AbilityReference ability = HUDRenderHandler.getAbilityByIndex(HUDRenderHandler.selectedIndex);
            if (ability != null) {
               ItemStack stack = ability.getSlot().gatherStack(player);
               if (stack.getItem() instanceof IRelicItem relic && relic.getAbilitiesData().getAbilities().containsKey(ability.getId())) {
                  boolean isTicking = relic.isAbilityTicking(stack, ability.getId());
                  boolean isCasting = Minecraft.getInstance().mouseHandler.isLeftPressed();
                  AbilityData entry = relic.getAbilityData(ability.getId());
                  if (entry != null) {
                     CastType type = entry.getCastData().getType();
                     switch (type) {
                        case CYCLICAL:
                           if (isTicking) {
                              if (isCasting) {
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.CYCLICAL, CastStage.TICK, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.TICK);
                              } else {
                                 NetworkHandler.sendToServer(new SpellCastPacket(CastType.CYCLICAL, CastStage.END, ability.serializeNBT()));
                                 relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.END);
                              }
                           }
                           break;
                        case INTERRUPTIBLE:
                           if (isTicking) {
                              NetworkHandler.sendToServer(new SpellCastPacket(CastType.INTERRUPTIBLE, CastStage.TICK, ability.serializeNBT()));
                              relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.TICK);
                           }
                           break;
                        case TOGGLEABLE:
                           if (isTicking) {
                              NetworkHandler.sendToServer(new SpellCastPacket(CastType.TOGGLEABLE, CastStage.TICK, ability.serializeNBT()));
                              relic.castActiveAbility(stack, player, ability.getId(), type, CastStage.TICK);
                           }
                     }
                  }
               }
            }
         }
      }
   }

   @EventBusSubscriber(Dist.CLIENT)
   public static class GeneralEvents {
      @SubscribeEvent(priority = EventPriority.HIGHEST)
      public static void onMouseScroll(MouseScrollingEvent event) {
         if (HotkeyRegistry.ACTIVE_ABILITIES_LIST.isDown() && !CacheHandler.REFERENCES.isEmpty()) {
            int current = HUDRenderHandler.selectedIndex;
            HUDRenderHandler.applyDelta(event.getScrollDeltaY() > 0.0 ? -1 : 1);
            if (current != HUDRenderHandler.selectedIndex) {
               HUDRenderHandler.mouseDelta = event.getScrollDeltaY() > 0.0 ? -10 : 10;
               LocalPlayer player = Minecraft.getInstance().player;
               if (player != null) {
                  player.playSound((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), 0.5F, 1.5F + player.getRandom().nextFloat() * 0.25F);
               }
            }

            event.setCanceled(true);
         }
      }

      @SubscribeEvent
      public static void onPlayerTick(Post event) {
         Player player = event.getEntity();
         if (player instanceof LocalPlayer) {
            if (HUDRenderHandler.castShakeDelta > 0) {
               HUDRenderHandler.castShakeDelta--;
            }

            if (HUDRenderHandler.mouseDelta > 0) {
               HUDRenderHandler.mouseDelta--;
            } else if (HUDRenderHandler.mouseDelta < 0) {
               HUDRenderHandler.mouseDelta++;
            }

            if (HotkeyRegistry.ACTIVE_ABILITIES_LIST.isDown()) {
               AbilityReference ability = HUDRenderHandler.getAbilityByIndex(HUDRenderHandler.selectedIndex);
               if (ability != null) {
                  ItemStack stack = ability.getSlot().gatherStack(player);
                  if (stack.getItem() instanceof IRelicItem relic
                     && relic.getAbilityData(ability.getId()) != null
                     && relic.canPlayerUseAbility(player, stack, ability.getId())) {
                     relic.tickActiveAbilitySelection(stack, player, ability.getId());
                  }
               }

               if (HUDRenderHandler.animationDelta < 5) {
                  HUDRenderHandler.animationDelta++;
               }

               HUDRenderHandler.animationDown = true;
            } else {
               if (HUDRenderHandler.animationDelta > 0) {
                  HUDRenderHandler.animationDelta--;
               }

               HUDRenderHandler.animationDown = false;
            }

            if (HUDRenderHandler.animationDelta != 0) {
               if (HUDRenderHandler.selectedIndex > CacheHandler.REFERENCES.size() || HUDRenderHandler.selectedIndex < 0) {
                  HUDRenderHandler.selectedIndex = 0;
               }
            }
         }
      }
   }
}
