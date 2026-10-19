package com.b1n_ry.yigd.client.render;

import com.b1n_ry.yigd.block.entity.GraveBlockEntity;
import com.b1n_ry.yigd.config.GraveRenderingConfig;
import com.b1n_ry.yigd.config.YigdConfig;
import com.b1n_ry.yigd.events.YigdEvents;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart.Cube;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class GraveBlockEntityRenderer implements BlockEntityRenderer<GraveBlockEntity> {
   private static final Gson GSON = new Gson();
   private final Map<Type, SkullModelBase> skullModels;
   private final Font textRenderer;
   private final Minecraft client;
   private final boolean adaptRenderer;
   private static ModelPart graveModel = getGraveModel();
   @Nullable
   private static GraveBlockEntityRenderer.TextRenderInfo textRenderInfo = null;
   @Nullable
   private static GraveBlockEntityRenderer.SkullRenderInfo skullRenderInfo = null;
   private static final Map<String, Material> CUBOID_SPRITES = new HashMap<>();
   private static final RenderType OUTLINE_RENDER_LAYER = RenderType.outline(ResourceLocation.parse("textures/misc/white.png"));
   public static boolean syncedGlowing = true;
   public static int syncedGlowingMaxDistance = Integer.MAX_VALUE;
   public static double syncedDeathSightDistance = 2.147483647E9;

   public GraveBlockEntityRenderer(Context context) {
      this.skullModels = SkullBlockRenderer.createSkullRenderers(context.getModelSet());
      this.textRenderer = context.getFont();
      this.client = Minecraft.getInstance();
      this.adaptRenderer = YigdConfig.getConfig().graveRendering.adaptRenderer;
   }

   public void render(
      @NotNull GraveBlockEntity entity, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int light, int overlay
   ) {
      GraveRenderingConfig config = YigdConfig.getConfig().graveRendering;
      if (config.useCustomFeatureRenderer) {
         BlockState state = entity.getBlockState();
         Direction direction = (Direction)state.getValue(BlockStateProperties.HORIZONTAL_FACING);

         float rotation = switch (direction) {
            case SOUTH -> (float) Math.PI;
            case WEST -> (float) (Math.PI / 2);
            case EAST -> (float) (Math.PI * 3.0 / 2.0);
            default -> 0.0F;
         };
         poseStack.pushPose();
         poseStack.rotateAround(Axis.YP.rotation(rotation), 0.5F, 0.5F, 0.5F);
         if (config.useGlowingEffect && entity.isUnclaimed()) {
            OutlineBufferSource consumerProvider = this.client.levelRenderer.renderBuffers.outlineBufferSource();
            this.renderGlowingOutline(entity, tickDelta, poseStack, consumerProvider, light, overlay);
         }

         if (config.useSkullRenderer) {
            this.renderOwnerSkull(entity, tickDelta, poseStack, multiBufferSource, light, overlay);
         }

         if (config.useTextRenderer) {
            this.renderGraveText(entity, tickDelta, poseStack, multiBufferSource, light, overlay);
         }

         this.renderGraveModel(entity, tickDelta, poseStack, multiBufferSource, light, overlay);
         poseStack.popPose();
      }
   }

   private void renderOwnerSkull(GraveBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
      ResolvableProfile skullOwner = entity.getGraveSkull();
      if (skullOwner != null) {
         Type type = Types.PLAYER;
         RenderType renderLayer = SkullBlockRenderer.getRenderType(type, skullOwner);
         this.renderSkull(entity, tickDelta, matrices, vertexConsumers, light, overlay, renderLayer);
      }
   }

   private void renderSkull(
      GraveBlockEntity ignoredEntity,
      float ignoredTickDelta,
      PoseStack matrices,
      MultiBufferSource vertexConsumers,
      int light,
      int ignoredOverlay,
      RenderType renderLayer
   ) {
      if (skullRenderInfo != null) {
         Type type = Types.PLAYER;
         SkullModelBase model = this.skullModels.get(type);
         matrices.pushPose();
         matrices.translate(0.5F, 0.25F, 0.5F);
         int[] rotation = skullRenderInfo.rotation;
         matrices.translate(0.0, -(4.0F - skullRenderInfo.height) / 16.0, -(8.0F - skullRenderInfo.depth) / 16.0);
         Quaternionf angle = new Quaternionf()
            .rotateXYZ((float)Math.toRadians(rotation[0]), (float)Math.toRadians(rotation[1]), (float)Math.toRadians(rotation[2]));
         matrices.mulPose(angle);
         matrices.scale(skullRenderInfo.scaleFace, skullRenderInfo.scaleFace, skullRenderInfo.scaleDepth);
         matrices.translate(-0.5F, -0.25F, -0.5F);
         SkullBlockRenderer.renderSkull(null, 0.0F, 0.0F, matrices, vertexConsumers, light, model, renderLayer);
         matrices.popPose();
      }
   }

   private void renderGraveText(
      GraveBlockEntity entity, float ignoredTickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int ignoredOverlay
   ) {
      Component graveText = entity.getGraveText();
      if (graveText != null && textRenderInfo != null) {
         matrices.pushPose();
         matrices.translate(0.5, textRenderInfo.height / 16.0F, textRenderInfo.depth / 16.0F - 1.0E-4F);
         matrices.scale(-1.0F, -1.0F, 0.0F);
         int textWidth = this.textRenderer.width(graveText.getString());
         float scale = textRenderInfo.width / (textWidth * 16.0F);
         matrices.scale(scale, scale, scale);
         matrices.translate(-textWidth / 2.0, -4.5, 0.0);
         this.textRenderer.drawInBatch(graveText, 0.0F, 0.0F, 16777215, false, matrices.last().pose(), vertexConsumers, DisplayMode.NORMAL, 0, light);
         matrices.popPose();
      }
   }

   private void renderGraveModel(GraveBlockEntity entity, float ignoredTickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
      for (Entry<String, Material> cuboid : CUBOID_SPRITES.entrySet()) {
         String key = cuboid.getKey();
         ModelPart part = graveModel.getChild(key);
         if (this.adaptRenderer && key.equals("ground")) {
            Level world = entity.getLevel();
            if (world != null) {
               BlockPos underPos = entity.getBlockPos().below();
               BlockState blockUnder = entity.getLevel().getBlockState(underPos);
               if (blockUnder != null && blockUnder.isSolidRender(world, underPos)) {
                  Cube cuboidPart = part.getRandomCube(world.random);
                  float scaleX = cuboidPart.maxX - cuboidPart.minX;
                  float scaleZ = cuboidPart.maxZ - cuboidPart.minZ;
                  matrices.pushPose();
                  matrices.translate(cuboidPart.minX / 16.0F + 5.0E-4F, cuboidPart.maxY / 16.0F - 1.0F, cuboidPart.minZ / 16.0F + 5.0E-4F);
                  matrices.scale(0.999F * (scaleX / 16.0F), 1.0F, 0.999F * (scaleZ / 16.0F));
                  this.client
                     .getBlockRenderer()
                     .renderBatched(blockUnder, underPos, world, matrices, vertexConsumers.getBuffer(RenderType.cutout()), false, world.random);
                  matrices.popPose();
                  continue;
               }
            }
         }

         VertexConsumer consumer = cuboid.getValue().buffer(vertexConsumers, RenderType::entityCutout);
         part.render(matrices, consumer, light, overlay);
      }
   }

   private void renderGlowingOutline(
      GraveBlockEntity entity, float ignoredTickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay
   ) {
      LocalPlayer player = this.client.player;
      YigdEvents.RenderGlowingGraveEvent event = (YigdEvents.RenderGlowingGraveEvent)NeoForge.EVENT_BUS
         .post(new YigdEvents.RenderGlowingGraveEvent(entity, player));
      if (event.isRenderGlowing()) {
         VertexConsumer consumer = vertexConsumers.getBuffer(OUTLINE_RENDER_LAYER);
         graveModel.render(matrices, consumer, light, overlay);
         if (YigdConfig.getConfig().graveRendering.useSkullRenderer) {
            this.renderSkull(entity, ignoredTickDelta, matrices, vertexConsumers, light, overlay, OUTLINE_RENDER_LAYER);
         }
      }
   }

   public static void reloadModelFromJson(JsonObject json) throws IllegalStateException {
      CUBOID_SPRITES.clear();
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      JsonArray textureSize = json.getAsJsonArray("texture_size");
      JsonObject textures = json.getAsJsonObject("textures");
      JsonArray elements = json.getAsJsonArray("elements");
      JsonObject features = json.has("features") ? json.getAsJsonObject("features") : null;
      int uvX = textureSize.get(0).getAsInt();
      int uvY = textureSize.get(1).getAsInt();
      Map<String, String> nameIds = new HashMap<>();

      for (Entry<String, JsonElement> e : textures.entrySet()) {
         String key = e.getKey();
         String value = e.getValue().getAsString();
         nameIds.put(key, value);
      }

      int i = 0;

      for (JsonElement e : elements) {
         JsonObject o = e.getAsJsonObject();
         String name = o.has("name") ? o.get("name").getAsString() : String.valueOf(i++);
         JsonArray from = o.getAsJsonArray("from");
         JsonArray to = o.getAsJsonArray("to");
         JsonObject faces = o.getAsJsonObject("faces");
         float minX = Float.MAX_VALUE;
         float minY = Float.MAX_VALUE;
         String textureName = "";

         for (Entry<String, JsonElement> face : faces.entrySet()) {
            JsonObject value = face.getValue().getAsJsonObject();
            JsonArray uv = value.getAsJsonArray("uv");
            textureName = value.get("texture").getAsString();
            minX = Math.min(minX, uv.get(0).getAsFloat());
            minY = Math.min(minY, uv.get(1).getAsFloat());
         }

         minX *= uvX / 16.0F;
         minY *= uvY / 16.0F;
         textureName = textureName.replaceFirst("#", "");
         if (nameIds.containsKey(textureName)) {
            textureName = nameIds.get(textureName);
         }

         ResourceLocation texture = ResourceLocation.parse(textureName);
         Material sprite = new Material(InventoryMenu.BLOCK_ATLAS, texture);
         CUBOID_SPRITES.put(name, sprite);
         float fromX = from.get(0).getAsFloat();
         float fromY = from.get(1).getAsFloat();
         float fromZ = from.get(2).getAsFloat();
         float toX = to.get(0).getAsFloat();
         float toY = to.get(1).getAsFloat();
         float toZ = to.get(2).getAsFloat();
         float lowerX = Math.min(fromX, toX);
         float lowerY = Math.min(fromY, toY);
         float lowerZ = Math.min(fromZ, toZ);
         float higherX = Math.max(fromX, toX);
         float higherY = Math.max(fromY, toY);
         float higherZ = Math.max(fromZ, toZ);
         addChildPart(root, name, (int)minX, (int)minY, lowerX, lowerY, lowerZ, higherX - lowerX, higherY - lowerY, higherZ - lowerZ);
      }

      if (features != null) {
         if (features.has("text")) {
            textRenderInfo = (GraveBlockEntityRenderer.TextRenderInfo)GSON.fromJson(features.get("text"), GraveBlockEntityRenderer.TextRenderInfo.class);
         }

         if (features.has("skull")) {
            skullRenderInfo = (GraveBlockEntityRenderer.SkullRenderInfo)GSON.fromJson(features.get("skull"), GraveBlockEntityRenderer.SkullRenderInfo.class);
         }
      }

      graveModel = LayerDefinition.create(modelData, uvX, uvY).bakeRoot();
   }

   private static ModelPart getGraveModel() {
      MeshDefinition modelData = new MeshDefinition();
      PartDefinition root = modelData.getRoot();
      addChildPart(root, "ground", 0, 0, 0.0F, 0.0F, 0.0F, 16.0F, 1.0F, 16.0F);
      addChildPart(root, "base", 0, 21, 2.0F, 1.0F, 10.0F, 12.0F, 2.0F, 5.0F);
      addChildPart(root, "bust", 0, 28, 3.0F, 3.0F, 11.0F, 10.0F, 12.0F, 3.0F);
      addChildPart(root, "top", 0, 17, 4.0F, 15.0F, 11.0F, 8.0F, 1.0F, 3.0F);
      return LayerDefinition.create(modelData, 64, 64).bakeRoot();
   }

   private static void addChildPart(
      PartDefinition root, String name, int uvX, int uvY, float minX, float minY, float minZ, float sizeX, float sizeY, float sizeZ
   ) {
      root.addOrReplaceChild(
         name,
         CubeListBuilder.create().texOffs(uvX, uvY).addBox(minX, minY, minZ, sizeX, sizeY, sizeZ),
         PartPose.offsetAndRotation(sizeX + minX * 2.0F, sizeY + minY * 2.0F, 0.0F, 0.0F, 0.0F, (float) Math.PI)
      );
   }

   private record SkullRenderInfo(float depth, float height, int[] rotation, float scaleFace, float scaleDepth) {
   }

   private record TextRenderInfo(float depth, float height, float width) {
   }
}
