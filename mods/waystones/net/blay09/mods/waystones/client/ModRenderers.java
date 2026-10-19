package net.blay09.mods.waystones.client;

import java.util.Objects;
import net.blay09.mods.balm.api.client.rendering.BalmRenderers;
import net.blay09.mods.waystones.block.ModBlocks;
import net.blay09.mods.waystones.block.PortstoneBlock;
import net.blay09.mods.waystones.block.SharestoneBlock;
import net.blay09.mods.waystones.block.entity.ModBlockEntities;
import net.blay09.mods.waystones.client.render.PortstoneModel;
import net.blay09.mods.waystones.client.render.PortstoneRenderer;
import net.blay09.mods.waystones.client.render.SharestoneModel;
import net.blay09.mods.waystones.client.render.SharestoneRenderer;
import net.blay09.mods.waystones.client.render.WaystoneModel;
import net.blay09.mods.waystones.client.render.WaystoneRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModRenderers {
   public static ModelLayerLocation portstoneModel;
   public static ModelLayerLocation sharestoneModel;
   public static ModelLayerLocation waystoneModel;

   public static void initialize(BalmRenderers renderers) {
      portstoneModel = renderers.registerModel(
         ResourceLocation.fromNamespaceAndPath("waystones", "portstone"), () -> PortstoneModel.createLayer(CubeDeformation.NONE)
      );
      sharestoneModel = renderers.registerModel(
         ResourceLocation.fromNamespaceAndPath("waystones", "sharestone"), () -> SharestoneModel.createLayer(CubeDeformation.NONE)
      );
      waystoneModel = renderers.registerModel(
         ResourceLocation.fromNamespaceAndPath("waystones", "waystone"), () -> WaystoneModel.createLayer(CubeDeformation.NONE)
      );
      renderers.registerBlockEntityRenderer(ModBlockEntities.waystone::get, WaystoneRenderer::new);
      renderers.registerBlockEntityRenderer(ModBlockEntities.sharestone::get, SharestoneRenderer::new);
      renderers.registerBlockEntityRenderer(ModBlockEntities.portstone::get, PortstoneRenderer::new);
      renderers.registerBlockColorHandler((state, view, pos, tintIndex) -> -3909955, () -> new Block[]{ModBlocks.warpPlate});
      renderers.registerItemColorHandler((itemStack, tintIndex) -> -3909955, () -> new Item[]{ModBlocks.warpPlate.asItem()});
      renderers.registerBlockColorHandler(
         (state, view, pos, tintIndex) -> Objects.requireNonNull(((SharestoneBlock)state.getBlock()).getColor()).getTextColor() | 0xFF000000,
         () -> ModBlocks.sharestones
      );
      renderers.registerItemColorHandler(
         (stack, tintIndex) -> Objects.requireNonNull(((SharestoneBlock)Block.byItem(stack.getItem())).getColor()).getTextColor() | 0xFF000000,
         () -> ModBlocks.sharestones
      );
      renderers.registerBlockColorHandler(
         (state, view, pos, tintIndex) -> Objects.requireNonNull(((PortstoneBlock)state.getBlock()).getColor()).getTextColor() | 0xFF000000,
         () -> ModBlocks.portstones
      );
      renderers.registerItemColorHandler(
         (stack, tintIndex) -> Objects.requireNonNull(((PortstoneBlock)Block.byItem(stack.getItem())).getColor()).getTextColor() | 0xFF000000,
         () -> ModBlocks.portstones
      );
      renderers.setBlockRenderType(() -> ModBlocks.warpPlate, RenderType.cutout());
   }
}
