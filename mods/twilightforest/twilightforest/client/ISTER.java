package twilightforest.client;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.registries.DeferredHolder;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.block.AbstractSkullCandleBlock;
import twilightforest.block.AbstractTrophyBlock;
import twilightforest.block.BrazierBlock;
import twilightforest.block.CandelabraBlock;
import twilightforest.block.CritterBlock;
import twilightforest.block.JarBlock;
import twilightforest.block.KeepsakeCasketBlock;
import twilightforest.block.MasonJarBlock;
import twilightforest.block.SkullChestBlock;
import twilightforest.block.entity.BrazierBlockEntity;
import twilightforest.block.entity.CandelabraBlockEntity;
import twilightforest.block.entity.KeepsakeCasketBlockEntity;
import twilightforest.block.entity.SkullChestBlockEntity;
import twilightforest.client.event.ClientGameEvents;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.KnightmetalShieldModel;
import twilightforest.client.model.entity.LichModel;
import twilightforest.client.model.entity.TrophyBlockModel;
import twilightforest.client.renderer.block.JarRenderer;
import twilightforest.client.renderer.block.SkullCandleRenderer;
import twilightforest.client.renderer.block.SkullChestRenderer;
import twilightforest.client.renderer.block.TrophyRenderer;
import twilightforest.client.renderer.entity.LichRenderer;
import twilightforest.components.item.CandelabraData;
import twilightforest.components.item.JarLid;
import twilightforest.components.item.SkullCandles;
import twilightforest.config.TFConfig;
import twilightforest.enums.BossVariant;
import twilightforest.enums.extensions.TFItemDisplayContextEnumExtension;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataComponents;
import twilightforest.item.KnightmetalShieldItem;
import twilightforest.item.MysticCrownItem;

public class ISTER extends BlockEntityWithoutLevelRenderer {
   @Autowired(dist = Dist.CLIENT)
   private static TFItemDisplayContextEnumExtension itemDisplayContextEnumExtension;
   public static final Supplier<ISTER> INSTANCE = Suppliers.memoize(ISTER::new);
   public static final IClientItemExtensions CLIENT_ITEM_EXTENSION = (IClientItemExtensions)Util.make(() -> new IClientItemExtensions() {
      public BlockEntityWithoutLevelRenderer getCustomRenderer() {
         return ISTER.INSTANCE.get();
      }
   });
   private final SkullChestBlockEntity skullChest = new SkullChestBlockEntity(BlockPos.ZERO, ((Block)TFBlocks.SKULL_CHEST.get()).defaultBlockState());
   private final KeepsakeCasketBlockEntity keepsakeCasket = new KeepsakeCasketBlockEntity(
      BlockPos.ZERO, ((Block)TFBlocks.KEEPSAKE_CASKET.get()).defaultBlockState()
   );
   private final Map<Block, ChestBlockEntity> chestEntities = (Map<Block, ChestBlockEntity>)Util.make(new HashMap(), map -> {
      makeInstance(map, TFBlocks.TWILIGHT_OAK_CHEST);
      makeInstance(map, TFBlocks.CANOPY_CHEST);
      makeInstance(map, TFBlocks.MANGROVE_CHEST);
      makeInstance(map, TFBlocks.DARK_CHEST);
      makeInstance(map, TFBlocks.TIME_CHEST);
      makeInstance(map, TFBlocks.TRANSFORMATION_CHEST);
      makeInstance(map, TFBlocks.MINING_CHEST);
      makeInstance(map, TFBlocks.SORTING_CHEST);
      makeInstance(map, TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.CANOPY_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.MANGROVE_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.DARK_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.TIME_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.TRANSFORMATION_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.MINING_TRAPPED_CHEST);
      makeInstance(map, TFBlocks.SORTING_TRAPPED_CHEST);
   });
   private KnightmetalShieldModel shield = new KnightmetalShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(TFModelLayers.KNIGHTMETAL_SHIELD));
   private Map<BossVariant, TrophyBlockModel> trophies = TrophyRenderer.createTrophyRenderers(Minecraft.getInstance().getEntityModels());
   private Map<Type, SkullModelBase> skulls = SkullBlockRenderer.createSkullRenderers(Minecraft.getInstance().getEntityModels());
   private final CandelabraBlockEntity candelabra = new CandelabraBlockEntity(BlockPos.ZERO, ((Block)TFBlocks.CANDELABRA.get()).defaultBlockState());
   private final BrazierBlockEntity brazier = new BrazierBlockEntity(BlockPos.ZERO, ((Block)TFBlocks.BRAZIER.get()).defaultBlockState());
   private final Map<CritterBlock, BlockEntity> critters = new HashMap<>();

   private ISTER() {
      super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
   }

   public void onResourceManagerReload(ResourceManager manager) {
      this.shield = new KnightmetalShieldModel(Minecraft.getInstance().getEntityModels().bakeLayer(TFModelLayers.KNIGHTMETAL_SHIELD));
      this.trophies = TrophyRenderer.createTrophyRenderers(Minecraft.getInstance().getEntityModels());
      this.skulls = SkullBlockRenderer.createSkullRenderers(Minecraft.getInstance().getEntityModels());
      TwilightForestMod.LOGGER.debug("Reloaded ISTER!");
   }

   public static void makeInstance(Map<Block, ChestBlockEntity> map, DeferredHolder<Block, ? extends ChestBlock> registryObject) {
      ChestBlock block = (ChestBlock)registryObject.get();
      map.put(block, (ChestBlockEntity)block.newBlockEntity(BlockPos.ZERO, block.defaultBlockState()));
   }

   public void renderByItem(ItemStack stack, ItemDisplayContext camera, PoseStack pose, MultiBufferSource buffers, int light, int overlay) {
      Item item = stack.getItem();
      if (item instanceof BlockItem blockItem) {
         Block block = blockItem.getBlock();
         Minecraft minecraft = Minecraft.getInstance();
         if (block instanceof AbstractTrophyBlock trophyBlock) {
            BossVariant variant = trophyBlock.getVariant();
            TrophyBlockModel trophy = this.trophies.get(variant);
            if (camera == ItemDisplayContext.GUI) {
               ModelResourceLocation back = ModelResourceLocation.standalone(
                  TwilightForestMod.prefix("item/" + ((AbstractTrophyBlock)block).getVariant().getTrophyType().getModelName())
               );
               BakedModel modelBack = minecraft.getItemRenderer().getItemModelShaper().getModelManager().getModel(back);
               Lighting.setupForFlatItems();
               BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
               pose.pushPose();
               Lighting.setupForFlatItems();
               pose.translate(0.5F, 0.5F, -1.5F);
               minecraft.getItemRenderer()
                  .render(
                     TrophyRenderer.stack,
                     ItemDisplayContext.GUI,
                     false,
                     pose,
                     bufferSource,
                     15728880,
                     OverlayTexture.NO_OVERLAY,
                     modelBack.applyTransform(camera, pose, false)
                  );
               pose.popPose();
               bufferSource.endBatch();
               Lighting.setupFor3DItems();
               pose.pushPose();
               pose.translate(0.5F, 0.5F, 0.5F);
               pose.mulPose(Axis.XP.rotationDegrees(30.0F));
               pose.mulPose(Axis.YN.rotationDegrees(TFConfig.rotateTrophyHeadsGui && !minecraft.isPaused() ? ClientGameEvents.time % 360 : -45.0F));
               pose.translate(-0.5F, -0.5F, -0.5F);
               pose.translate(0.0F, 0.25F, 0.0F);
               if (trophyBlock.getVariant() == BossVariant.UR_GHAST) {
                  pose.translate(0.0F, 0.5F, 0.0F);
               }

               if (trophyBlock.getVariant() == BossVariant.ALPHA_YETI) {
                  pose.translate(0.0F, -0.15F, 0.0F);
               }

               TrophyRenderer.render(
                  null,
                  180.0F,
                  trophy,
                  variant,
                  !minecraft.isPaused() ? ClientGameEvents.time + minecraft.getTimer().getRealtimeDeltaTicks() : 0.0F,
                  pose,
                  buffers,
                  light,
                  camera
               );
               pose.popPose();
            } else {
               TrophyRenderer.render(
                  null,
                  180.0F,
                  trophy,
                  variant,
                  !minecraft.isPaused() ? ClientGameEvents.time + minecraft.getTimer().getRealtimeDeltaTicks() : 0.0F,
                  pose,
                  buffers,
                  light,
                  camera
               );
            }
         } else if (block instanceof KeepsakeCasketBlock) {
            int damage = (Integer)stack.getOrDefault(TFDataComponents.CASKET_DAMAGE, 0);
            if (minecraft.getBlockEntityRenderDispatcher().getRenderer(this.keepsakeCasket) instanceof SkullChestRenderer<?> renderer) {
               renderer.renderCasket(0.0F, pose, buffers, light, overlay, renderer.getTextureLocation(damage), Direction.NORTH);
            }
         } else if (block instanceof SkullChestBlock) {
            if (minecraft.getBlockEntityRenderDispatcher().getRenderer(this.skullChest) instanceof SkullChestRenderer<?> renderer) {
               renderer.renderCasket(0.0F, pose, buffers, light, overlay, renderer.getTextureLocation(0), Direction.NORTH);
            }
         } else if (block instanceof ChestBlock) {
            minecraft.getBlockEntityRenderDispatcher().renderItem(this.chestEntities.get(block), pose, buffers, light, overlay);
         } else if (block instanceof AbstractSkullCandleBlock candleBlock) {
            ResolvableProfile profile = (ResolvableProfile)stack.get(DataComponents.PROFILE);
            if (profile != null && !profile.isResolved()) {
               stack.remove(DataComponents.PROFILE);
               profile.resolve().thenAcceptAsync(p_329787_ -> stack.set(DataComponents.PROFILE, p_329787_), minecraft);
               return;
            }

            Type type = candleBlock.getType();
            SkullModelBase base = this.skulls.get(type);
            RenderType renderType = SkullCandleRenderer.getRenderType(type, profile);
            SkullCandleRenderer.renderSkull(null, 180.0F, 0.0F, pose, buffers, light, base, renderType);
            pose.translate(0.0F, 0.5F, 0.0F);
            SkullCandles skullCandles = (SkullCandles)stack.getOrDefault(TFDataComponents.SKULL_CANDLES, SkullCandles.DEFAULT);
            minecraft.getBlockRenderer()
               .renderSingleBlock(
                  (BlockState)AbstractSkullCandleBlock.candleColorToCandle(AbstractSkullCandleBlock.CandleColors.colorFromInt(skullCandles.color()))
                     .defaultBlockState()
                     .setValue(CandleBlock.CANDLES, skullCandles.count()),
                  pose,
                  buffers,
                  light,
                  OverlayTexture.NO_OVERLAY,
                  ModelData.EMPTY,
                  RenderType.cutout()
               );
         } else if (block instanceof CandelabraBlock) {
            minecraft.getBlockRenderer().renderSingleBlock(((Block)TFBlocks.CANDELABRA.get()).defaultBlockState(), pose, buffers, light, overlay);
            CandelabraData candelabraData = (CandelabraData)stack.get(TFDataComponents.CANDELABRA_DATA);
            if (candelabraData != null) {
               CandelabraBlockEntity copy = this.candelabra;
               copy.setData(candelabraData);
               minecraft.getBlockEntityRenderDispatcher().renderItem(copy, pose, buffers, light, overlay);
            }
         } else if (block instanceof CritterBlock critter) {
            BlockEntity blockEntity = this.critters
               .computeIfAbsent(critter, critterBlock -> critterBlock.newBlockEntity(BlockPos.ZERO, critterBlock.defaultBlockState()));
            minecraft.getBlockEntityRenderDispatcher().getRenderer(blockEntity).render(null, 0.0F, pose, buffers, light, overlay);
         } else if (block instanceof JarBlock jarBlock) {
            JarRenderer.renderJarModel(block.defaultBlockState(), minecraft.getBlockRenderer(), pose, buffers, light, overlay);
            JarLid jarLid = (JarLid)stack.getComponents().get((DataComponentType)TFDataComponents.JAR_LID.get());
            Item lid = jarLid != null && JarRenderer.LIDS.containsKey(jarLid.lid()) ? jarLid.lid() : jarBlock.getDefaultLid();
            JarRenderer.renderModel(JarRenderer.LIDS.get(lid), block.defaultBlockState(), minecraft.getBlockRenderer(), pose, buffers, light, overlay);
            if (jarBlock instanceof MasonJarBlock) {
               ItemContainerContents contents = (ItemContainerContents)stack.getComponents().get(DataComponents.CONTAINER);
               if (contents != null) {
                  BufferSource bufferSource = minecraft.renderBuffers().bufferSource();
                  pose.pushPose();
                  pose.translate(0.5, 0.4375, 0.5);
                  pose.scale(0.5F, 0.5F, 0.5F);
                  minecraft.getItemRenderer()
                     .render(
                        contents.copyOne(),
                        itemDisplayContextEnumExtension.JARRED,
                        false,
                        pose,
                        bufferSource,
                        light,
                        OverlayTexture.NO_OVERLAY,
                        minecraft.getItemRenderer().getModel(contents.copyOne(), null, null, 1)
                     );
                  pose.popPose();
                  bufferSource.endBatch();
               }
            }
         } else if (block instanceof BrazierBlock) {
            minecraft.getBlockEntityRenderDispatcher().renderItem(this.brazier, pose, buffers, light, overlay);
         }
      } else if (item instanceof KnightmetalShieldItem) {
         pose.pushPose();
         pose.scale(1.0F, -1.0F, -1.0F);
         Material material = new Material(Sheets.SHIELD_SHEET, TwilightForestMod.prefix("entity/knightmetal_shield"));
         VertexConsumer vertexconsumer = material.sprite()
            .wrap(ItemRenderer.getFoilBufferDirect(buffers, this.shield.renderType(material.atlasLocation()), true, stack.hasFoil()));
         this.shield.renderToBuffer(pose, vertexconsumer, light, overlay);
         pose.popPose();
      } else if (item instanceof MysticCrownItem && this.trophies.get(BossVariant.LICH) instanceof LichModel<?> lichModel) {
         lichModel.hat.yRot = 0.0F;
         pose.pushPose();
         pose.scale(1.0F, -1.0F, -1.0F);
         if (camera == ItemDisplayContext.GUI) {
            pose.translate(0.5F, -0.1F, 0.0F);
            pose.last().normal().rotate((float) (Math.PI / 2), 0.0F, 1.0F, 0.0F);
            pose.mulPose(Axis.XP.rotationDegrees(30.0F));
            pose.mulPose(Axis.YP.rotationDegrees(45.0F));
         } else {
            pose.translate(0.5F, 0.0F, -0.5F);
            pose.scale(1.05F, 1.05F, 1.05F);
         }

         lichModel.hat.render(pose, buffers.getBuffer(RenderType.entityCutoutNoCull(LichRenderer.TEXTURE)), light, overlay);
         pose.popPose();
      }
   }
}
