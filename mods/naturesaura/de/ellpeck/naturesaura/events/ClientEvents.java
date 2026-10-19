package de.ellpeck.naturesaura.events;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityAuraTimer;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityGratedChute;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityItemDistributor;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityNatureAltar;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityRFConverter;
import de.ellpeck.naturesaura.items.ItemAuraCache;
import de.ellpeck.naturesaura.items.ItemRangeVisualizer;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.packet.PacketAuraChunk;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MyceliumBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.items.IItemHandler;
import org.apache.commons.lang3.mutable.MutableInt;
import org.joml.Matrix4fStack;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {
   public static final ResourceLocation OVERLAYS = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/gui/overlays.png");
   public static final ResourceLocation BOOK_GUI = ResourceLocation.fromNamespaceAndPath("naturesaura", "textures/gui/book.png");
   public static final List<PacketAuraChunk> PENDING_AURA_CHUNKS = new ArrayList<>();
   private static final ItemStack ITEM_FRAME = new ItemStack(Items.ITEM_FRAME);
   private static final ItemStack DISPENSER = new ItemStack(Blocks.DISPENSER);
   private static final Map<ResourceLocation, Tuple<ItemStack, Boolean>> SHOWING_EFFECTS = new HashMap<>();
   private static ItemStack heldCache = ItemStack.EMPTY;
   private static ItemStack heldEye = ItemStack.EMPTY;
   private static ItemStack heldOcular = ItemStack.EMPTY;
   private static BlockPos hoveringAuraSpot;

   @SubscribeEvent
   public void onDebugRender(DebugText event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.getDebugOverlay().showDebugScreen() && (Boolean)ModConfig.instance.debugText.get()) {
         String prefix = ChatFormatting.GREEN + "[Nature's Aura]" + ChatFormatting.RESET + " ";
         List<String> left = event.getLeft();
         if (mc.player.isCreative() || mc.player.isSpectator()) {
            left.add("");
            MutableInt amount = new MutableInt(1000000);
            MutableInt spots = new MutableInt();
            MutableInt chunks = new MutableInt();
            IAuraChunk.getSpotsInArea(mc.level, mc.player.blockPosition(), 35, (blockPos, drainSpot) -> {
               spots.increment();
               amount.add(drainSpot);
            });
            Helper.getAuraChunksWithSpotsInArea(mc.level, mc.player.blockPosition(), 35, c -> chunks.increment());
            NumberFormat format = NumberFormat.getInstance();
            left.add(prefix + "A: " + format.format(amount.intValue()) + " (S: " + spots.intValue() + ", C: " + chunks.intValue() + ")");
            left.add(prefix + "AT: " + IAuraType.forLevel(mc.level).getName());
         }
      }
   }

   @SubscribeEvent
   public void onClientTick(Post event) {
      heldCache = ItemStack.EMPTY;
      heldEye = ItemStack.EMPTY;
      heldOcular = ItemStack.EMPTY;
      Minecraft mc = Minecraft.getInstance();
      if (mc.level == null) {
         ItemRangeVisualizer.clear();
         PENDING_AURA_CHUNKS.clear();
      } else {
         PENDING_AURA_CHUNKS.removeIf(next -> next.tryHandle(mc.level));
         if (!mc.isPaused()) {
            if (mc.level.getGameTime() % 20L == 0L) {
               int amount = Mth.floor(190.0 * (Double)ModConfig.instance.excessParticleAmount.get());

               for (int i = 0; i < amount; i++) {
                  int x = Mth.floor(mc.player.getX()) + mc.level.random.nextInt(64) - 32;
                  int y = Mth.floor(mc.player.getY()) + mc.level.random.nextInt(32) - 16;
                  int z = Mth.floor(mc.player.getZ()) + mc.level.random.nextInt(64) - 32;
                  BlockPos pos = Helper.getClosestAirAboveGround(mc.level, new BlockPos(x, y, z), 16).below();
                  BlockState state = mc.level.getBlockState(pos);
                  Block block = state.getBlock();
                  if (block instanceof BonemealableBlock || block instanceof LeavesBlock || block instanceof MyceliumBlock) {
                     int excess = IAuraChunk.triangulateAuraInArea(mc.level, pos, 45) - 1000000;
                     if (excess > 0) {
                        int chance = Math.max(10, 50 - excess / 25000);
                        if (mc.level.random.nextInt(chance) <= 0) {
                           NaturesAuraAPI.instance()
                              .spawnMagicParticle(
                                 pos.getX() + mc.level.random.nextFloat(),
                                 pos.getY() + 0.5F,
                                 pos.getZ() + mc.level.random.nextFloat(),
                                 mc.level.random.nextGaussian() * 0.01F,
                                 mc.level.random.nextFloat() * 0.025F,
                                 mc.level.random.nextGaussian() * 0.01F,
                                 block instanceof MyceliumBlock ? 8871073 : BiomeColors.getAverageGrassColor(mc.level, pos),
                                 Math.min(2.0F, 1.0F + mc.level.random.nextFloat() * (excess / 30000.0F)),
                                 Math.min(300, 100 + mc.level.random.nextInt(excess / 3000 + 1)),
                                 0.0F,
                                 false,
                                 true
                              );
                        }
                     }
                  }
               }
            }

            if (Helper.isHoldingItem(mc.player, ModItems.RANGE_VISUALIZER) && mc.level.getGameTime() % 3L == 0L) {
               NaturesAuraAPI.IInternalHooks inst = NaturesAuraAPI.instance();
               inst.setParticleSpawnRange(512);
               inst.setParticleDepth(false);

               for (BlockPos pos : ItemRangeVisualizer.VISUALIZED_RAILS.get(mc.level.dimension().location())) {
                  NaturesAuraAPI.instance()
                     .spawnMagicParticle(
                        pos.getX() + mc.level.random.nextFloat(),
                        pos.getY() + mc.level.random.nextFloat(),
                        pos.getZ() + mc.level.random.nextFloat(),
                        0.0,
                        0.0,
                        0.0,
                        14744319,
                        mc.level.random.nextFloat() * 10.0F + 2.0F,
                        100,
                        0.0F,
                        false,
                        true
                     );
               }

               inst.setParticleDepth(true);
               inst.setParticleSpawnRange(32);
            }

            heldCache = Helper.getEquippedItem(s -> s.getItem() instanceof ItemAuraCache, mc.player, false);
            heldEye = Helper.getEquippedItem(s -> s.getItem() == ModItems.EYE, mc.player, true);
            heldOcular = Helper.getEquippedItem(s -> s.getItem() == ModItems.EYE_IMPROVED, mc.player, false);
            if (!heldOcular.isEmpty() && mc.level.getGameTime() % 20L == 0L) {
               SHOWING_EFFECTS.clear();
               Helper.getAuraChunksWithSpotsInArea(mc.level, mc.player.blockPosition(), 100, chunk -> chunk.getActiveEffectIcons(mc.player, SHOWING_EFFECTS));
            }
         }
      }
   }

   @SubscribeEvent
   public void onLevelRender(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         Vec3 view = mc.gameRenderer.getMainCamera().getPosition();
         Tesselator tesselator = Tesselator.getInstance();
         mc.getProfiler().push("naturesaura:onLevelRender");
         RenderSystem.enableDepthTest();
         Matrix4fStack mv = RenderSystem.getModelViewStack();
         mv.pushMatrix();
         mv.mul(event.getPoseStack().last().pose());
         mv.translate((float)(-view.x), (float)(-view.y), (float)(-view.z));
         RenderSystem.applyModelViewMatrix();
         RenderSystem.enableBlend();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         hoveringAuraSpot = null;
         if (mc.getDebugOverlay().showDebugScreen() && (mc.player.isCreative() || mc.player.isSpectator()) && (Boolean)ModConfig.instance.debugLevel.get()) {
            Vec3 playerEye = mc.player.getEyePosition(event.getPartialTick().getGameTimeDeltaPartialTick(true));
            Vec3 playerView = mc.player.getViewVector(event.getPartialTick().getGameTimeDeltaPartialTick(true)).normalize();
            double range = mc.player.blockInteractionRange();
            BufferBuilder builder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            IAuraChunk.getSpotsInArea(
               mc.level,
               mc.player.blockPosition(),
               64,
               (pos, spot) -> {
                  Helper.renderWeirdBox(
                     builder, pos.getX(), pos.getY(), pos.getZ(), 1.0F, 1.0F, 1.0F, spot > 0 ? 0.0F : 1.0F, spot > 0 ? 1.0F : 0.0F, 0.0F, 0.35F
                  );
                  if (playerEye.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5) <= range * range) {
                     for (float d = 0.0F; d <= range; d += 0.5F) {
                        if (pos.equals(BlockPos.containing(playerEye.add(playerView.scale(d))))) {
                           hoveringAuraSpot = pos;
                           break;
                        }
                     }
                  }
               }
            );
            MeshData data = builder.build();
            if (data != null) {
               BufferUploader.drawWithShader(data);
            }
         }

         if (Helper.isHoldingItem(mc.player, ModItems.RANGE_VISUALIZER)) {
            RenderSystem.disableCull();
            ResourceLocation dim = mc.level.dimension().location();
            BufferBuilder builder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            for (BlockPos pos : ItemRangeVisualizer.VISUALIZED_BLOCKS.get(dim)) {
               if (mc.level.isLoaded(pos)) {
                  BlockState state = mc.level.getBlockState(pos);
                  Block block = state.getBlock();
                  if (block instanceof IVisualizable) {
                     this.renderVisualize(builder, (IVisualizable)block, mc.level, pos);
                  }
               }
            }

            for (Entity entity : ItemRangeVisualizer.VISUALIZED_ENTITIES.get(dim)) {
               if (entity.isAlive() && entity instanceof IVisualizable) {
                  this.renderVisualize(builder, (IVisualizable)entity, mc.level, entity.blockPosition());
               }
            }

            MeshData data = builder.build();
            if (data != null) {
               BufferUploader.drawWithShader(data);
            }

            RenderSystem.enableCull();
         }

         mv.popMatrix();
         RenderSystem.applyModelViewMatrix();
         RenderSystem.disableBlend();
         mc.getProfiler().pop();
      }
   }

   private void renderVisualize(BufferBuilder buffer, IVisualizable visualize, Level level, BlockPos pos) {
      AABB box = visualize.getVisualizationBounds(level, pos);
      if (box != null) {
         box = box.inflate(0.05F);
         int color = visualize.getVisualizationColor(level, pos);
         Helper.renderWeirdBox(
            buffer,
            (float)box.minX,
            (float)box.minY,
            (float)box.minZ,
            (float)(box.maxX - box.minX),
            (float)(box.maxY - box.minY),
            (float)(box.maxZ - box.minZ),
            (color >> 16 & 0xFF) / 255.0F,
            (color >> 8 & 0xFF) / 255.0F,
            (color & 0xFF) / 255.0F,
            0.5F
         );
      }
   }

   @SubscribeEvent
   public void onOverlayRender(net.neoforged.neoforge.client.event.RenderGuiLayerEvent.Post event) {
      Minecraft mc = Minecraft.getInstance();
      GuiGraphics graphics = event.getGuiGraphics();
      PoseStack stack = graphics.pose();
      Window res = mc.getWindow();
      if (event.getName() == VanillaGuiLayers.HOTBAR && !mc.options.hideGui && mc.player != null) {
         if (!heldCache.isEmpty()) {
            IAuraContainer container = (IAuraContainer)heldCache.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY, null);
            if (container != null) {
               int width = Mth.ceil((float)container.getStoredAura() / container.getMaxAura() * 80.0F);
               int conf = (Integer)ModConfig.instance.cacheBarLocation.get();
               int x = res.getGuiScaledWidth() / 2 + (conf == 0 ? -173 - (mc.player.getOffhandItem().isEmpty() ? 0 : 29) : 93);
               int y = res.getGuiScaledHeight() - 8;
               stack.pushPose();
               int color = container.getAuraColor();
               graphics.setColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
               if (width < 80) {
                  graphics.blit(OVERLAYS, x + width, y, width, 0.0F, 80 - width, 6, 256, 256);
               }

               if (width > 0) {
                  graphics.blit(OVERLAYS, x, y, 0.0F, 6.0F, width, 6, 256, 256);
               }

               float scale = 0.75F;
               stack.pushPose();
               stack.scale(scale, scale, scale);
               String s = heldCache.getHoverName().getString();
               graphics.drawString(mc.font, s, conf == 1 ? x / scale : (x + 80) / scale - mc.font.width(s), (y - 7) / scale, color, true);
               stack.popPose();
               graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
               stack.pushPose();
            }
         }

         if (!heldEye.isEmpty() || !heldOcular.isEmpty()) {
            stack.pushPose();
            int conf = (Integer)ModConfig.instance.auraBarLocation.get();
            if (!mc.getDebugOverlay().showDebugScreen() && (conf != 2 || !(mc.screen instanceof ChatScreen))) {
               int color = IAuraType.forLevel(mc.level).getColor();
               graphics.setColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
               int totalAmount = IAuraChunk.triangulateAuraInArea(mc.level, mc.player.blockPosition(), 35);
               float totalPercentage = totalAmount / 2000000.0F;
               String text = I18n.get("info.naturesaura.aura_in_area", new Object[0]);
               float textScale = 0.75F;
               int startX = conf % 2 == 0 ? 3 : res.getGuiScaledWidth() - 3 - 6;
               int startY = conf < 2
                  ? 10
                  : (heldOcular.isEmpty() || !(totalPercentage > 1.0F) && !(totalPercentage < 0.0F) ? 0 : -26) + res.getGuiScaledHeight() - 60;
               float plusOffX = conf % 2 == 0 ? 7.0F : -7.0F;
               float textX = conf % 2 == 0 ? 3.0F : res.getGuiScaledWidth() - 3 - mc.font.width(text) * textScale;
               float textY = conf < 2 ? 3.0F : res.getGuiScaledHeight() - 3 - 6;
               int tHeight = Mth.ceil(Mth.clamp(totalPercentage, 0.0F, 1.0F) * 50.0F);
               int y = !heldOcular.isEmpty() && totalPercentage > 1.0F ? startY + 26 : startY;
               if (tHeight < 50) {
                  graphics.blit(OVERLAYS, startX, y, 6.0F, 12.0F, 6, 50 - tHeight, 256, 256);
               }

               if (tHeight > 0) {
                  graphics.blit(OVERLAYS, startX, y + 50 - tHeight, 0.0F, 62 - tHeight, 6, tHeight, 256, 256);
               }

               if (!heldOcular.isEmpty()) {
                  int topHeight = Mth.ceil(Mth.clamp((totalPercentage - 1.0F) * 2.0F, 0.0F, 1.0F) * 25.0F);
                  if (topHeight > 0) {
                     if (topHeight < 25) {
                        graphics.blit(OVERLAYS, startX, startY, 18.0F, 12.0F, 6, 25 - topHeight, 256, 256);
                     }

                     graphics.blit(OVERLAYS, startX, startY + 25 - topHeight, 12.0F, 37 - topHeight, 6, topHeight, 256, 256);
                  }

                  int bottomHeight = Mth.floor(Mth.clamp((totalPercentage + 1.0F) * 2.0F - 1.0F, 0.0F, 1.0F) * 25.0F);
                  if (bottomHeight < 25) {
                     graphics.blit(OVERLAYS, startX, startY + 51, 18.0F, 12.0F, 6, 25 - bottomHeight, 256, 256);
                     if (bottomHeight > 0) {
                        graphics.blit(OVERLAYS, startX, startY + 51 + 25 - bottomHeight, 12.0F, 37 - bottomHeight, 6, bottomHeight, 256, 256);
                     }
                  }
               }

               if (totalPercentage > (heldOcular.isEmpty() ? 1.0F : 1.5F)) {
                  graphics.drawString(mc.font, "+", startX + plusOffX, startY - 0.5F, color, true);
               }

               if (totalPercentage < (heldOcular.isEmpty() ? 0.0F : -0.5F)) {
                  graphics.drawString(mc.font, "-", startX + plusOffX, startY - 0.5F + (heldOcular.isEmpty() ? 44 : 70), color, true);
               }

               stack.pushPose();
               stack.scale(textScale, textScale, textScale);
               graphics.drawString(mc.font, text, textX / textScale, textY / textScale, color, true);
               stack.popPose();
               if (!heldOcular.isEmpty()) {
                  stack.pushPose();
                  int stackX = conf % 2 == 0 ? 10 : res.getGuiScaledWidth() - 22;
                  int stackY = conf < 2 ? 10 : res.getGuiScaledHeight() - 60;

                  for (Tuple<ItemStack, Boolean> effect : SHOWING_EFFECTS.values()) {
                     ItemStack itemStack = (ItemStack)effect.getA();
                     Helper.renderItemInGui(graphics, itemStack, stackX, stackY, 1.0F);
                     if ((Boolean)effect.getB()) {
                        RenderSystem.disableDepthTest();
                        graphics.blit(OVERLAYS, stackX, stackY, 240.0F, 0.0F, 16, 16, 256, 256);
                        RenderSystem.enableDepthTest();
                     }

                     stackY += 12;
                  }

                  stack.popPose();
               }
            }

            if (mc.hitResult instanceof BlockHitResult blockHitResult) {
               BlockPos pos = blockHitResult.getBlockPos();
               if (pos != null) {
                  BlockEntity tile = mc.level.getBlockEntity(pos);
                  int x = res.getGuiScaledWidth() / 2;
                  int y = res.getGuiScaledHeight() / 2;
                  IAuraContainer container;
                  if (tile != null
                     && (
                           container = (IAuraContainer)tile.getLevel()
                              .getCapability(NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, tile.getBlockPos(), tile.getBlockState(), tile, null)
                        )
                        != null) {
                     BlockState state = mc.level.getBlockState(pos);
                     ItemStack blockStack = state.getBlock().getCloneItemStack(state, blockHitResult, mc.level, pos, mc.player);
                     this.drawContainerInfo(
                        graphics,
                        container.getStoredAura(),
                        container.getMaxAura(),
                        container.getAuraColor(),
                        mc,
                        res,
                        35,
                        blockStack.getHoverName().getString(),
                        null
                     );
                     if (tile instanceof BlockEntityNatureAltar) {
                        IItemHandler itemHandler = (IItemHandler)tile.getLevel()
                           .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
                        ItemStack tileStack = itemHandler.getStackInSlot(0);
                        if (!tileStack.isEmpty()) {
                           IAuraContainer stackCont = (IAuraContainer)tileStack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
                           if (stackCont != null) {
                              this.drawContainerInfo(
                                 graphics,
                                 stackCont.getStoredAura(),
                                 stackCont.getMaxAura(),
                                 stackCont.getAuraColor(),
                                 mc,
                                 res,
                                 55,
                                 tileStack.getHoverName().getString(),
                                 null
                              );
                           }
                        }
                     }
                  } else if (tile instanceof BlockEntityRFConverter) {
                     EnergyStorage storage = ((BlockEntityRFConverter)tile).storage;
                     this.drawContainerInfo(
                        graphics,
                        storage.getEnergyStored(),
                        storage.getMaxEnergyStored(),
                        13388054,
                        mc,
                        res,
                        35,
                        I18n.get("block.naturesaura.rf_converter", new Object[0]),
                        storage.getEnergyStored() + " / " + storage.getMaxEnergyStored() + " RF"
                     );
                  } else if (tile instanceof BlockEntityGratedChute chute) {
                     IItemHandler itemHandler = (IItemHandler)tile.getLevel()
                        .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
                     ItemStack itemStack = itemHandler.getStackInSlot(0);
                     if (itemStack.isEmpty()) {
                        graphics.drawString(
                           mc.font,
                           ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC + I18n.get("info.naturesaura.empty", new Object[0]),
                           x + 5,
                           y - 11,
                           16777215
                        );
                     } else {
                        Helper.renderItemInGui(graphics, itemStack, x + 2, y - 18, 1.0F);
                     }

                     Helper.renderItemInGui(graphics, ITEM_FRAME, x - 24, y - 24, 1.0F);
                     int u = chute.isBlacklist ? 240 : 224;
                     RenderSystem.disableDepthTest();
                     graphics.blit(OVERLAYS, x - 18, y - 18, u, 0.0F, 16, 16, 256, 256);
                     RenderSystem.enableDepthTest();
                  } else if (tile instanceof BlockEntityItemDistributor distributor) {
                     Helper.renderItemInGui(graphics, DISPENSER, x - 24, y - 24, 1.0F);
                     int u = !distributor.isRandomMode ? 240 : 224;
                     RenderSystem.disableDepthTest();
                     graphics.blit(OVERLAYS, x - 18, y - 18, u, 0.0F, 16, 16, 256, 256);
                     RenderSystem.enableDepthTest();
                  } else if (tile instanceof BlockEntityAuraTimer timer) {
                     IItemHandler itemHandler = (IItemHandler)tile.getLevel()
                        .getCapability(ItemHandler.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, null);
                     ItemStack itemStack = itemHandler.getStackInSlot(0);
                     if (!itemStack.isEmpty()) {
                        Helper.renderItemInGui(graphics, itemStack, x - 20, y - 20, 1.0F);
                        graphics.drawString(mc.font, ChatFormatting.GRAY + this.createTimeString(timer.getTotalTime()), x + 5, y - 11, 16777215);
                        graphics.drawString(
                           mc.font,
                           ChatFormatting.GRAY + I18n.get("info.naturesaura.remaining", new Object[]{this.createTimeString(timer.getTimeLeft())}),
                           x + 5,
                           y + 3,
                           16777215
                        );
                     }
                  }
               }
            }

            graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            stack.popPose();
         }

         if (hoveringAuraSpot != null) {
            NumberFormat format = NumberFormat.getInstance();
            IAuraChunk.DrainSpot spot = IAuraChunk.getAuraChunk(mc.level, hoveringAuraSpot).getActualDrainSpot(hoveringAuraSpot, false);
            ChatFormatting color = spot.intValue() > 0 ? ChatFormatting.GREEN : ChatFormatting.RED;
            graphics.drawString(
               mc.font, "Pos: " + spot.pos.toShortString(), res.getGuiScaledWidth() / 2.0F + 5.0F, res.getGuiScaledHeight() / 2.0F - 20.0F, 16777215, true
            );
            graphics.drawString(
               mc.font,
               "Amount: " + color + format.format(spot.intValue()),
               res.getGuiScaledWidth() / 2.0F + 5.0F,
               res.getGuiScaledHeight() / 2.0F - 10.0F,
               16777215,
               true
            );
            if (spot.originalSpreadPos != null) {
               graphics.drawString(
                  mc.font,
                  "Dist from Original: " + (int)Math.sqrt(spot.pos.distSqr(spot.originalSpreadPos)) + " (" + spot.originalSpreadPos.toShortString() + ")",
                  res.getGuiScaledWidth() / 2.0F + 5.0F,
                  res.getGuiScaledHeight() / 2.0F,
                  16777215,
                  true
               );
            }
         }
      }
   }

   private String createTimeString(int totalTicks) {
      int ticks = totalTicks % 20;
      int seconds = totalTicks / 20 % 60;
      int minutes = totalTicks / 20 / 60 % 60;
      int hours = totalTicks / 20 / 60 / 60;
      return String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, ticks);
   }

   private void drawContainerInfo(GuiGraphics graphics, int stored, int max, int color, Minecraft mc, Window res, int yOffset, String name, String textBelow) {
      graphics.setColor((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
      int x = res.getGuiScaledWidth() / 2 - 40;
      int y = res.getGuiScaledHeight() / 2 + yOffset;
      int width = Mth.ceil((float)stored / max * 80.0F);
      if (width < 80) {
         graphics.blit(OVERLAYS, x + width, y, width, 0.0F, 80 - width, 6, 256, 256);
      }

      if (width > 0) {
         graphics.blit(OVERLAYS, x, y, 0.0F, 6.0F, width, 6, 256, 256);
      }

      graphics.drawString(mc.font, name, x + 40 - mc.font.width(name) / 2.0F, y - 9, color, true);
      if (textBelow != null) {
         graphics.drawString(mc.font, textBelow, x + 40 - mc.font.width(textBelow) / 2.0F, y + 7, color, true);
      }
   }
}
