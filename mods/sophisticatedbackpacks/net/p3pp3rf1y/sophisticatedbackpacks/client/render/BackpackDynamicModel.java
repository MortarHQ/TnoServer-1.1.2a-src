package net.p3pp3rf1y.sophisticatedbackpacks.client.render;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.IQuadTransformer;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import net.neoforged.neoforge.client.model.pipeline.QuadBakingVertexConsumer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapeHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.BackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.wrapper.IBackpackWrapper;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedcore.renderdata.RenderInfo;
import net.p3pp3rf1y.sophisticatedcore.renderdata.TankPosition;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedBatteryUpgrade.BatteryRenderInfo;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedTankUpgrade.TankRenderInfo;

public class BackpackDynamicModel implements IUnbakedGeometry<BackpackDynamicModel> {
   static final ItemDisplayContext WORN = ItemDisplayContext.valueOf("SOPHISTICATEDBACKPACKS_WORN");
   private final Map<BackpackDynamicModel.ModelPart, ResourceLocation> modelPartParents;

   private BackpackDynamicModel(Map<BackpackDynamicModel.ModelPart, ResourceLocation> modelPartParents) {
      this.modelPartParents = modelPartParents;
   }

   public BakedModel bake(
      IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelTransform, ItemOverrides overrides
   ) {
      Builder<BackpackDynamicModel.ModelPart, BakedModel> builder = ImmutableMap.builder();
      Builder<BackpackDynamicModel.ModelPart, VoxelShape> shapeBuilder = ImmutableMap.builder();
      this.modelPartParents.forEach((part, parentModel) -> {
         BlockModel model = createPartModel(context, parentModel);
         model.resolveParents(baker::getModel);
         BakedModel bakedModel = model.bake(baker, spriteGetter, modelTransform);
         if (bakedModel != null) {
            builder.put(part, bakedModel);
         }

         VoxelShape shape = getModelShape(model);
         if (!shape.isEmpty()) {
            shapeBuilder.put(part, shape);
         }
      });
      return new BackpackDynamicModel.BackpackBakedModel(builder.build(), shapeBuilder.build(), context.getTransforms());
   }

   private static BlockModel createPartModel(IGeometryBakingContext context, ResourceLocation parentModel) {
      Builder<String, Either<Material, String>> textures = ImmutableMap.builder();
      if (context.hasMaterial("clips")) {
         textures.put("clips", Either.left(context.getMaterial("clips")));
      }

      return new BlockModel(parentModel, Collections.emptyList(), textures.build(), true, null, ItemTransforms.NO_TRANSFORMS, Collections.emptyList());
   }

   private static VoxelShape getModelShape(BlockModel model) {
      List<BlockElement> elements = model.getElements();
      if (elements.isEmpty()) {
         return Shapes.empty();
      }

      VoxelShape shape = Shapes.empty();

      for (BlockElement element : elements) {
         if (element.rotation != null && Math.abs(element.rotation.angle()) > 1.0E-6F) {
            return Shapes.empty();
         }

         shape = Shapes.joinUnoptimized(
            shape,
            Shapes.box(
               Math.min(element.from.x(), element.to.x()) / 16.0,
               Math.min(element.from.y(), element.to.y()) / 16.0,
               Math.min(element.from.z(), element.to.z()) / 16.0,
               Math.max(element.from.x(), element.to.x()) / 16.0,
               Math.max(element.from.y(), element.to.y()) / 16.0,
               Math.max(element.from.z(), element.to.z()) / 16.0
            ),
            BooleanOp.OR
         );
      }

      return shape.optimize();
   }

   public static final class BackpackBakedModel implements IDynamicBakedModel {
      private static final ModelProperty<Boolean> HAS_TANK_LEFT = new ModelProperty();
      private static final ModelProperty<Boolean> HAS_TANK_RIGHT = new ModelProperty();
      private static final ModelProperty<Boolean> HAS_BATTERY = new ModelProperty();
      private static final ModelProperty<TankRenderInfo> LEFT_TANK_RENDER_INFO = new ModelProperty();
      private static final ModelProperty<TankRenderInfo> RIGHT_TANK_RENDER_INFO = new ModelProperty();
      private static final ModelProperty<BatteryRenderInfo> BATTERY_RENDER_INFO = new ModelProperty();
      private int cachedLeftTankSteps = -1;
      private final Map<BackpackDynamicModel.BackpackBakedModel.FluidCacheKey, List<BakedQuad>> leftTankFluidCache = new HashMap<>();
      private int cachedRightTankSteps = -1;
      private final Map<BackpackDynamicModel.BackpackBakedModel.FluidCacheKey, List<BakedQuad>> rightTankFluidCache = new HashMap<>();
      private int cachedBatterySteps = -1;
      private final Map<Integer, List<BakedQuad>> batteryChargeCache = new HashMap<>();
      private ItemDisplayContext lastContext = ItemDisplayContext.NONE;
      private final BackpackDynamicModel.BackpackItemOverrideList overrideList = new BackpackDynamicModel.BackpackItemOverrideList(this);
      private final Map<BackpackDynamicModel.ModelPart, BakedModel> models;
      private final Map<BackpackDynamicModel.ModelPart, VoxelShape> partShapes;
      private final Map<BackpackShapeHelper.ShapeKey, VoxelShape> shapeCache = new ConcurrentHashMap<>();
      @Nullable
      private AABB leftTankFluidBounds;
      @Nullable
      private AABB rightTankFluidBounds;
      @Nullable
      private AABB batteryChargeBounds;
      private boolean tankLeft;
      @Nullable
      private TankRenderInfo leftTankRenderInfo = null;
      private boolean tankRight;
      @Nullable
      private TankRenderInfo rightTankRenderInfo = null;
      private boolean battery;
      @Nullable
      private BatteryRenderInfo batteryRenderInfo = null;
      private final ItemTransforms itemTransforms;
      private final BackpackDynamicModel.BackpackBakedModel.TankBakedModel tankBakedModel = new BackpackDynamicModel.BackpackBakedModel.TankBakedModel();

      public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
         return ChunkRenderTypeSet.of(new RenderType[]{RenderType.cutout(), RenderType.translucent()});
      }

      public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
         return List.of(this.tankBakedModel, this);
      }

      public BackpackBakedModel(
         Map<BackpackDynamicModel.ModelPart, BakedModel> models, Map<BackpackDynamicModel.ModelPart, VoxelShape> partShapes, ItemTransforms itemTransforms
      ) {
         this.models = models;
         this.partShapes = partShapes;
         this.itemTransforms = itemTransforms;
      }

      public Optional<VoxelShape> getShape(Direction dir, boolean leftTank, boolean rightTank, boolean battery) {
         if (!this.hasPartShapes()) {
            return Optional.empty();
         }

         BackpackShapeHelper.ShapeKey key = BackpackShapeHelper.ShapeKey.of(dir, leftTank, rightTank, battery);
         return Optional.of(this.shapeCache.computeIfAbsent(key, k -> this.composeShape(dir, leftTank, rightTank, battery)));
      }

      private boolean hasPartShapes() {
         return this.partShapes.containsKey(BackpackDynamicModel.ModelPart.BASE)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.LEFT_POUCH)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.LEFT_TANK)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.RIGHT_POUCH)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.RIGHT_TANK)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.FRONT_POUCH)
            && this.partShapes.containsKey(BackpackDynamicModel.ModelPart.BATTERY);
      }

      private VoxelShape composeShape(Direction dir, boolean leftTank, boolean rightTank, boolean battery) {
         BackpackShapeHelper.ShapeKey key = BackpackShapeHelper.ShapeKey.of(dir, leftTank, rightTank, battery);
         return BackpackShapeHelper.composeAndRotate(key, this::getPartShape);
      }

      private VoxelShape getPartShape(BackpackShapeHelper.Part part) {
         return switch (part) {
            case BASE -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.BASE);
            case BATTERY -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.BATTERY);
            case FRONT_POUCH -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.FRONT_POUCH);
            case LEFT_POUCH -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.LEFT_POUCH);
            case LEFT_TANK -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.LEFT_TANK);
            case RIGHT_POUCH -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.RIGHT_POUCH);
            case RIGHT_TANK -> (VoxelShape)this.partShapes.get(BackpackDynamicModel.ModelPart.RIGHT_TANK);
         };
      }

      @Nullable
      public BakedQuad getDisplayItemQuad() {
         BakedModel displayItemModel = this.models.get(BackpackDynamicModel.ModelPart.DISPLAY_ITEM);
         if (displayItemModel == null) {
            return null;
         }

         List<BakedQuad> quads = displayItemModel.getQuads(null, null, RandomSource.create(), ModelData.EMPTY, null);
         return quads.isEmpty() ? null : quads.getFirst();
      }

      public List<BakedQuad> getQuads(
         @Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType
      ) {
         if (state != null) {
            this.setPropertiesFromModelData(extraData);
         }

         if (renderType == RenderType.translucent()) {
            return this.tankBakedModel.getQuads(state, side, rand, extraData, renderType);
         }

         List<BakedQuad> ret = new ArrayList<>(this.models.get(BackpackDynamicModel.ModelPart.BASE).getQuads(state, side, rand, extraData, renderType));
         if (state == null) {
            this.addLeftSide(state, side, rand, extraData, ret, this.tankLeft, renderType);
            this.addRightSide(state, side, rand, extraData, ret, this.tankRight, renderType);
            this.addFront(state, side, rand, extraData, ret, this.battery, renderType);
         } else {
            this.addLeftSide(state, side, rand, extraData, ret, (Boolean)state.getValue(BackpackBlock.LEFT_TANK), renderType);
            this.addRightSide(state, side, rand, extraData, ret, (Boolean)state.getValue(BackpackBlock.RIGHT_TANK), renderType);
            this.addFront(state, side, rand, extraData, ret, (Boolean)state.getValue(BackpackBlock.BATTERY), renderType);
         }

         if (this.lastContext != BackpackDynamicModel.WORN) {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.STRAPS).getQuads(state, side, rand, extraData, renderType));
         }

         return ret;
      }

      private void setPropertiesFromModelData(ModelData extraData) {
         this.tankLeft = extraData.has(HAS_TANK_LEFT) && (Boolean)extraData.get(HAS_TANK_LEFT);
         this.leftTankRenderInfo = (TankRenderInfo)extraData.get(LEFT_TANK_RENDER_INFO);
         this.tankRight = extraData.has(HAS_TANK_RIGHT) && (Boolean)extraData.get(HAS_TANK_RIGHT);
         this.rightTankRenderInfo = (TankRenderInfo)extraData.get(RIGHT_TANK_RENDER_INFO);
         this.battery = extraData.has(HAS_BATTERY) && (Boolean)extraData.get(HAS_BATTERY);
         this.batteryRenderInfo = (BatteryRenderInfo)extraData.get(BATTERY_RENDER_INFO);
      }

      private void addFront(
         @Nullable BlockState state,
         @Nullable Direction side,
         RandomSource rand,
         ModelData extraData,
         List<BakedQuad> ret,
         boolean battery,
         @Nullable RenderType renderType
      ) {
         if (battery) {
            if (this.batteryRenderInfo != null && this.batteryRenderInfo.getChargeRatio() != 0.0F) {
               float ratio = this.batteryRenderInfo.getChargeRatio();
               if (this.cachedBatterySteps < 0) {
                  ret.addAll(this.getBatteryChargeFromModel(state, rand, extraData, renderType, ratio));
               } else {
                  int step = ratioToStep(ratio, this.cachedBatterySteps);
                  ret.addAll(
                     this.batteryChargeCache
                        .computeIfAbsent(step, s -> this.getBatteryChargeFromModel(state, rand, extraData, renderType, stepToRatio(s, this.cachedBatterySteps)))
                  );
               }
            }

            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.BATTERY).getQuads(state, side, rand, extraData, renderType));
         } else {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.FRONT_POUCH).getQuads(state, side, rand, extraData, renderType));
         }
      }

      private List<BakedQuad> getBatteryChargeFromModel(
         @Nullable BlockState state, RandomSource rand, ModelData extraData, @Nullable RenderType renderType, float chargeRatio
      ) {
         BakedModel chargeModel = this.models.get(BackpackDynamicModel.ModelPart.BATTERY_CHARGE);
         if (chargeModel == null) {
            return Collections.emptyList();
         }

         List<BakedQuad> src = getAllQuads(chargeModel, state, rand, extraData, renderType);
         if (src.isEmpty()) {
            return Collections.emptyList();
         }

         Axis batteryFillAxis = getHorizontalFillAxis(src);
         if (this.cachedBatterySteps < 0) {
            this.cachedBatterySteps = computeStepsFromModelUV(src, batteryFillAxis);
            if (this.cachedBatterySteps <= 0) {
               batteryFillAxis = batteryFillAxis == Axis.X ? Axis.Z : Axis.X;
               this.cachedBatterySteps = computeStepsFromModelUV(src, batteryFillAxis);
            }
         }

         AABB bounds = this.batteryChargeBounds != null ? this.batteryChargeBounds : computeBoundsFromQuads(src);
         this.batteryChargeBounds = bounds;
         if (bounds == null) {
            return Collections.emptyList();
         }

         int step = ratioToStep(chargeRatio, this.cachedBatterySteps);
         if (step <= 0) {
            return Collections.emptyList();
         }

         if (step >= this.cachedBatterySteps) {
            step = this.cachedBatterySteps;
         }

         float stepRatio = step >= this.cachedBatterySteps ? 0.9999F : stepToRatio(step, this.cachedBatterySteps);
         BackpackDynamicModel.BackpackBakedModel.SliceSpec s = horizontalSliceSpecFromUv(src, bounds, stepRatio, batteryFillAxis);
         return sliceQuadsAxis(src, s.axis(), s.cut(), s.keepGreaterOrEqual());
      }

      private static BackpackDynamicModel.BackpackBakedModel.SliceSpec horizontalSliceSpecFromUv(List<BakedQuad> quads, AABB b, float ratio, Axis axis) {
         ratio = Mth.clamp(ratio, 0.0F, 1.0F);
         boolean lowUAtLowCoord = isLowUAtLowCoord(quads, axis);

         return switch (axis) {
            case X -> lowUAtLowCoord
               ? new BackpackDynamicModel.BackpackBakedModel.SliceSpec(Axis.X, b.minX + (b.maxX - b.minX) * ratio, false)
               : new BackpackDynamicModel.BackpackBakedModel.SliceSpec(Axis.X, b.maxX - (b.maxX - b.minX) * ratio, true);
            case Z -> lowUAtLowCoord
               ? new BackpackDynamicModel.BackpackBakedModel.SliceSpec(Axis.Z, b.minZ + (b.maxZ - b.minZ) * ratio, false)
               : new BackpackDynamicModel.BackpackBakedModel.SliceSpec(Axis.Z, b.maxZ - (b.maxZ - b.minZ) * ratio, true);
            default -> new BackpackDynamicModel.BackpackBakedModel.SliceSpec(Axis.X, b.minX + (b.maxX - b.minX) * ratio, false);
         };
      }

      private static Axis getHorizontalFillAxis(List<BakedQuad> quads) {
         int xSteps = computeStepsFromModelUV(quads, Axis.X);
         int zSteps = computeStepsFromModelUV(quads, Axis.Z);
         return zSteps > xSteps ? Axis.Z : Axis.X;
      }

      private static boolean isLowUAtLowCoord(List<BakedQuad> quads, Axis axis) {
         float minU = Float.POSITIVE_INFINITY;
         float maxU = Float.NEGATIVE_INFINITY;
         float coordAtMinU = 0.0F;
         float coordAtMaxU = 0.0F;
         boolean found = false;

         for (BakedQuad q : quads) {
            int[] v = q.getVertices();
            int stride = v.length / 4;

            for (int i = 0; i < 4; i++) {
               int base = i * stride;
               float x = Float.intBitsToFloat(v[base]);
               float z = Float.intBitsToFloat(v[base + 2]);
               float u = Float.intBitsToFloat(v[base + 4]);
               float coord = axis == Axis.Z ? z : x;
               if (u < minU) {
                  minU = u;
                  coordAtMinU = coord;
                  found = true;
               }

               if (u > maxU) {
                  maxU = u;
                  coordAtMaxU = coord;
                  found = true;
               }
            }
         }

         return !found || coordAtMinU <= coordAtMaxU;
      }

      private void addRightSide(
         @Nullable BlockState state,
         @Nullable Direction side,
         RandomSource rand,
         ModelData extraData,
         List<BakedQuad> ret,
         boolean tankRight,
         @Nullable RenderType renderType
      ) {
         if (tankRight) {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.RIGHT_TANK).getQuads(state, side, rand, extraData, renderType));
         } else {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.RIGHT_POUCH).getQuads(state, side, rand, extraData, renderType));
         }
      }

      private void addLeftSide(
         @Nullable BlockState state,
         @Nullable Direction side,
         RandomSource rand,
         ModelData extraData,
         List<BakedQuad> ret,
         boolean tankLeft,
         @Nullable RenderType renderType
      ) {
         if (tankLeft) {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.LEFT_TANK).getQuads(state, side, rand, extraData, renderType));
         } else {
            ret.addAll(this.models.get(BackpackDynamicModel.ModelPart.LEFT_POUCH).getQuads(state, side, rand, extraData, renderType));
         }
      }

      private List<BakedQuad> getTankFluidFromModel(
         @Nullable BlockState state,
         RandomSource rand,
         ModelData extraData,
         @Nullable RenderType renderType,
         BackpackDynamicModel.ModelPart fluidPart,
         FluidStack fluidStack,
         float ratio,
         boolean isLeft
      ) {
         if (fluidStack != FluidStack.EMPTY && !Mth.equal(ratio, 0.0F)) {
            BakedModel fluidModel = this.models.get(fluidPart);
            if (fluidModel == null) {
               return Collections.emptyList();
            }

            List<BakedQuad> src = getAllQuads(fluidModel, state, rand, extraData, renderType);
            if (src.isEmpty()) {
               return Collections.emptyList();
            }

            int steps = isLeft ? this.cachedLeftTankSteps : this.cachedRightTankSteps;
            if (steps < 0) {
               steps = computeStepsFromModelUV(src, Axis.Y);
               if (isLeft) {
                  this.cachedLeftTankSteps = steps;
               } else {
                  this.cachedRightTankSteps = steps;
               }
            }

            AABB cached = isLeft ? this.leftTankFluidBounds : this.rightTankFluidBounds;
            AABB max = cached != null ? cached : computeBoundsFromQuads(src);
            if (isLeft) {
               this.leftTankFluidBounds = max;
            } else {
               this.rightTankFluidBounds = max;
            }

            if (max == null) {
               return Collections.emptyList();
            }

            int step = ratioToStep(ratio, steps);
            if (step <= 0) {
               return Collections.emptyList();
            }

            if (step >= steps) {
               step = steps;
            }

            float stepRatio = stepToRatio(step, steps);
            double cut = max.minY + (max.maxY - max.minY) * stepRatio;
            List<BakedQuad> sliced = sliceQuadsAxis(src, Axis.Y, cut, false);
            if (sliced.isEmpty()) {
               return Collections.emptyList();
            }

            IClientFluidTypeExtensions props = IClientFluidTypeExtensions.of(fluidStack.getFluid());
            ResourceLocation stillTex = props.getStillTexture(fluidStack);
            TextureAtlasSprite newSprite = (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTex);
            int argb = props.getTintColor(fluidStack);
            List<BakedQuad> ret = respriteAndTintQuads(sliced, newSprite, argb);
            BakedQuad top = buildFluidTopQuad((float)cut, max, src, newSprite, argb);
            if (top != null) {
               ret.add(top);
            }

            return ret;
         } else {
            return Collections.emptyList();
         }
      }

      @Nullable
      private static BakedQuad buildFluidTopQuad(float y, AABB max, List<BakedQuad> src, TextureAtlasSprite sprite, int argb) {
         if (y <= (float)max.minY + 1.0E-6F) {
            return null;
         }

         float a = (argb >>> 24 & 0xFF) / 255.0F;
         float r = (argb >>> 16 & 0xFF) / 255.0F;
         float g = (argb >>> 8 & 0xFF) / 255.0F;
         float b = (argb & 0xFF) / 255.0F;
         double pxPerUnitX = computePixelsPerUnitFromU(src, Direction.NORTH, Direction.SOUTH, Axis.X);
         double pxPerUnitZ = computePixelsPerUnitFromU(src, Direction.EAST, Direction.WEST, Axis.Z);
         if (pxPerUnitX <= 0.0) {
            pxPerUnitX = 16.0;
         }

         if (pxPerUnitZ <= 0.0) {
            pxPerUnitZ = pxPerUnitX;
         }

         double widthX = max.maxX - max.minX;
         double depthZ = max.maxZ - max.minZ;
         double uPixels = widthX * pxPerUnitX;
         double vPixels = depthZ * pxPerUnitZ;
         int sw = sprite.contents().width();
         int sh = sprite.contents().height();
         if (sw > 0 && sh > 0) {
            float u0 = sprite.getU0();
            float v0 = sprite.getV0();
            float du = (float)(uPixels / sw * (sprite.getU1() - sprite.getU0()));
            float dv = (float)(vPixels / sh * (sprite.getV1() - sprite.getV0()));
            float u1 = u0 + du;
            float v1 = v0 + dv;
            QuadBakingVertexConsumer qb = new QuadBakingVertexConsumer();
            qb.setSprite(sprite);
            qb.setDirection(Direction.UP);
            qb.setTintIndex(-1);
            Vec3i n = Direction.UP.getNormal();
            float x0 = (float)max.minX;
            float x1 = (float)max.maxX;
            float z0 = (float)max.minZ;
            float z1 = (float)max.maxZ;
            qb.addVertex(x0, y, z0).setColor(r, g, b, a).setUv(u0, v0).setNormal(n.getX(), n.getY(), n.getZ());
            qb.addVertex(x0, y, z1).setColor(r, g, b, a).setUv(u0, v1).setNormal(n.getX(), n.getY(), n.getZ());
            qb.addVertex(x1, y, z1).setColor(r, g, b, a).setUv(u1, v1).setNormal(n.getX(), n.getY(), n.getZ());
            qb.addVertex(x1, y, z0).setColor(r, g, b, a).setUv(u1, v0).setNormal(n.getX(), n.getY(), n.getZ());
            return qb.bakeQuad();
         } else {
            return null;
         }
      }

      private static double computePixelsPerUnitFromU(List<BakedQuad> quads, Direction d1, Direction d2, Axis modelAxis) {
         double bestSpan = -1.0;
         double bestPxPerUnit = -1.0;

         for (BakedQuad q : quads) {
            Direction dir = q.getDirection();
            if (dir == d1 || dir == d2) {
               int[] v = q.getVertices();
               int stride = v.length / 4;
               float minCoord = Float.POSITIVE_INFINITY;
               float maxCoord = Float.NEGATIVE_INFINITY;
               float minU = Float.POSITIVE_INFINITY;
               float maxU = Float.NEGATIVE_INFINITY;

               for (int i = 0; i < 4; i++) {
                  int base = i * stride;
                  float x = Float.intBitsToFloat(v[base]);
                  float y = Float.intBitsToFloat(v[base + 1]);
                  float z = Float.intBitsToFloat(v[base + 2]);
                  float u = Float.intBitsToFloat(v[base + 4]);

                  float coord = switch (modelAxis) {
                     case X -> x;
                     case Z -> z;
                     case Y -> y;
                     default -> throw new MatchException(null, null);
                  };
                  minCoord = Math.min(minCoord, coord);
                  maxCoord = Math.max(maxCoord, coord);
                  minU = Math.min(minU, u);
                  maxU = Math.max(maxU, u);
               }

               double modelSpan = maxCoord - minCoord;
               if (!(modelSpan <= 1.0E-6)) {
                  TextureAtlasSprite s = q.getSprite();
                  if (s != null) {
                     double uDen = s.getU1() - s.getU0();
                     if (!(Math.abs(uDen) < 1.0E-9)) {
                        double uNormSpan = Math.abs(maxU - minU) / uDen;
                        int texW = s.contents().width();
                        if (texW > 0) {
                           double uPixels = uNormSpan * texW;
                           double pxPerUnit = uPixels / modelSpan;
                           if (modelSpan > bestSpan) {
                              bestSpan = modelSpan;
                              bestPxPerUnit = pxPerUnit;
                           }
                        }
                     }
                  }
               }
            }
         }

         return bestPxPerUnit;
      }

      private static int ratioToStep(float ratio, int steps) {
         ratio = Mth.clamp(ratio, 0.0F, 1.0F);
         if (steps <= 0) {
            return 0;
         }

         int step = Mth.floor(ratio * steps + 1.0E-6F);
         return Mth.clamp(step, 0, steps);
      }

      private static float stepToRatio(int step, int steps) {
         return steps <= 0 ? 0.0F : (float)step / steps;
      }

      private static List<BakedQuad> getAllQuads(
         BakedModel m, @Nullable BlockState state, RandomSource rand, ModelData extraData, @Nullable RenderType renderType
      ) {
         List<BakedQuad> all = m.getQuads(state, null, rand, extraData, renderType);
         if (!all.isEmpty()) {
            return all;
         }

         List<BakedQuad> ret = Lists.newArrayList();

         for (Direction d : Direction.values()) {
            ret.addAll(m.getQuads(state, d, rand, extraData, renderType));
         }

         return ret;
      }

      @Nullable
      private static AABB computeBoundsFromQuads(List<BakedQuad> quads) {
         if (quads.isEmpty()) {
            return null;
         }

         float minX = Float.POSITIVE_INFINITY;
         float minY = Float.POSITIVE_INFINITY;
         float minZ = Float.POSITIVE_INFINITY;
         float maxX = Float.NEGATIVE_INFINITY;
         float maxY = Float.NEGATIVE_INFINITY;
         float maxZ = Float.NEGATIVE_INFINITY;

         for (BakedQuad q : quads) {
            int[] v = q.getVertices();
            int stride = v.length / 4;

            for (int i = 0; i < 4; i++) {
               int base = i * stride;
               float x = Float.intBitsToFloat(v[base]);
               float y = Float.intBitsToFloat(v[base + 1]);
               float z = Float.intBitsToFloat(v[base + 2]);
               minX = Math.min(minX, x);
               minY = Math.min(minY, y);
               minZ = Math.min(minZ, z);
               maxX = Math.max(maxX, x);
               maxY = Math.max(maxY, y);
               maxZ = Math.max(maxZ, z);
            }
         }

         return Float.isFinite(minX) && Float.isFinite(minY) && Float.isFinite(minZ) ? new AABB(minX, minY, minZ, maxX, maxY, maxZ) : null;
      }

      private static List<BakedQuad> sliceQuadsAxis(List<BakedQuad> src, Axis axis, double cut, boolean keepGreaterOrEqual) {
         List<BakedQuad> out = new ArrayList<>(src.size());

         for (BakedQuad q : src) {
            BakedQuad sliced = sliceQuadAxis(q, axis, (float)cut, keepGreaterOrEqual);
            if (sliced != null) {
               out.add(sliced);
            }
         }

         return out;
      }

      @Nullable
      private static BakedQuad sliceQuadAxis(BakedQuad q, Axis axis, float cut, boolean keepGreaterOrEqual) {
         int[] v = q.getVertices();
         int stride = v.length / 4;
         BackpackDynamicModel.BackpackBakedModel.Vert[] in = new BackpackDynamicModel.BackpackBakedModel.Vert[4];

         for (int i = 0; i < 4; i++) {
            int base = i * stride;
            in[i] = new BackpackDynamicModel.BackpackBakedModel.Vert(
               Float.intBitsToFloat(v[base]),
               Float.intBitsToFloat(v[base + 1]),
               Float.intBitsToFloat(v[base + 2]),
               Float.intBitsToFloat(v[base + 4]),
               Float.intBitsToFloat(v[base + 5])
            );
         }

         List<BackpackDynamicModel.BackpackBakedModel.Vert> out = clipAgainstPlane(Arrays.asList(in), axis, cut, keepGreaterOrEqual);
         if (out.isEmpty()) {
            return null;
         }

         while (out.size() < 4) {
            out.add(out.get(out.size() - 1));
         }

         if (out.size() > 4) {
            out = out.subList(0, 4);
         }

         QuadBakingVertexConsumer qb = new QuadBakingVertexConsumer();
         qb.setSprite(q.getSprite());
         qb.setDirection(q.getDirection());
         qb.setTintIndex(q.getTintIndex());
         Vec3i n = q.getDirection().getNormal();

         for (int i = 0; i < 4; i++) {
            BackpackDynamicModel.BackpackBakedModel.Vert p = out.get(i);
            qb.addVertex(p.x, p.y, p.z).setColor(1.0F, 1.0F, 1.0F, 1.0F).setUv(p.u, p.v).setNormal(n.getX(), n.getY(), n.getZ());
         }

         return qb.bakeQuad();
      }

      private static List<BackpackDynamicModel.BackpackBakedModel.Vert> clipAgainstPlane(
         List<BackpackDynamicModel.BackpackBakedModel.Vert> poly, Axis axis, float cut, boolean keepGE
      ) {
         List<BackpackDynamicModel.BackpackBakedModel.Vert> out = new ArrayList<>(poly.size() + 2);
         BackpackDynamicModel.BackpackBakedModel.Vert prev = poly.get(poly.size() - 1);
         boolean prevIn = inside(prev, axis, cut, keepGE);

         for (BackpackDynamicModel.BackpackBakedModel.Vert cur : poly) {
            boolean curIn = inside(cur, axis, cut, keepGE);
            if (prevIn && curIn) {
               out.add(cur);
            } else if (prevIn && !curIn) {
               out.add(intersect(prev, cur, axis, cut));
            } else if (!prevIn && curIn) {
               out.add(intersect(prev, cur, axis, cut));
               out.add(cur);
            }

            prev = cur;
            prevIn = curIn;
         }

         return out;
      }

      private static boolean inside(BackpackDynamicModel.BackpackBakedModel.Vert p, Axis axis, float cut, boolean keepGE) {
         float c = switch (axis) {
            case X -> p.x;
            case Z -> p.z;
            case Y -> p.y;
            default -> throw new MatchException(null, null);
         };
         float eps = 1.0E-6F;
         return keepGE ? c + eps >= cut : c <= cut + eps;
      }

      private static BackpackDynamicModel.BackpackBakedModel.Vert intersect(
         BackpackDynamicModel.BackpackBakedModel.Vert a, BackpackDynamicModel.BackpackBakedModel.Vert b, Axis axis, float cut
      ) {
         float ca = switch (axis) {
            case X -> a.x;
            case Z -> a.z;
            case Y -> a.y;
            default -> throw new MatchException(null, null);
         };

         float cb = switch (axis) {
            case X -> b.x;
            case Z -> b.z;
            case Y -> b.y;
            default -> throw new MatchException(null, null);
         };
         float denom = cb - ca;
         float t = denom == 0.0F ? 0.0F : (cut - ca) / denom;
         t = Mth.clamp(t, 0.0F, 1.0F);
         return new BackpackDynamicModel.BackpackBakedModel.Vert(
            Mth.lerp(t, a.x, b.x), Mth.lerp(t, a.y, b.y), Mth.lerp(t, a.z, b.z), Mth.lerp(t, a.u, b.u), Mth.lerp(t, a.v, b.v)
         );
      }

      private static List<BakedQuad> respriteAndTintQuads(List<BakedQuad> src, TextureAtlasSprite newSprite, int argb) {
         float a = (argb >>> 24 & 0xFF) / 255.0F;
         float r = (argb >>> 16 & 0xFF) / 255.0F;
         float g = (argb >>> 8 & 0xFF) / 255.0F;
         float b = (argb & 0xFF) / 255.0F;
         float[] cols = new float[]{a, r, g, b};
         List<BakedQuad> out = new ArrayList<>(src.size());

         for (BakedQuad q : src) {
            out.add(respriteAndTintQuad(q, newSprite, cols));
         }

         return out;
      }

      private static BakedQuad respriteAndTintQuad(BakedQuad q, TextureAtlasSprite newSprite, float[] cols) {
         TextureAtlasSprite oldSprite = q.getSprite();
         int[] in = q.getVertices();
         QuadBakingVertexConsumer qb = new QuadBakingVertexConsumer();
         qb.setSprite(newSprite);
         qb.setDirection(q.getDirection());
         qb.setTintIndex(-1);
         Vec3i n = q.getDirection().getNormal();

         for (int vi = 0; vi < 4; vi++) {
            int base = vi * IQuadTransformer.STRIDE;
            float x = Float.intBitsToFloat(in[base + IQuadTransformer.POSITION]);
            float y = Float.intBitsToFloat(in[base + IQuadTransformer.POSITION + 1]);
            float z = Float.intBitsToFloat(in[base + IQuadTransformer.POSITION + 2]);
            float uOld = Float.intBitsToFloat(in[base + IQuadTransformer.UV0]);
            float vOld = Float.intBitsToFloat(in[base + IQuadTransformer.UV0 + 1]);
            float uNew = remapU(oldSprite, newSprite, uOld);
            float vNew = remapV(oldSprite, newSprite, vOld);
            int packedUv2 = in[base + IQuadTransformer.UV2];
            int lightU = packedUv2 & 65535;
            int lightV = packedUv2 >>> 16 & 65535;
            qb.addVertex(x, y, z).setColor(cols[1], cols[2], cols[3], cols[0]).setUv(uNew, vNew).setUv2(lightU, lightV).setNormal(n.getX(), n.getY(), n.getZ());
            if (IQuadTransformer.UV1 >= 0) {
               int packedUv1 = in[base + IQuadTransformer.UV1];
               int ovU = packedUv1 & 65535;
               int ovV = packedUv1 >>> 16 & 65535;
               qb.setUv1(ovU, ovV);
            }
         }

         return qb.bakeQuad();
      }

      private static float remapU(TextureAtlasSprite oldS, TextureAtlasSprite newS, float u) {
         float denom = oldS.getU1() - oldS.getU0();
         if (denom == 0.0F) {
            return newS.getU0();
         }

         float t = (u - oldS.getU0()) / denom;
         return newS.getU0() + t * (newS.getU1() - newS.getU0());
      }

      private static float remapV(TextureAtlasSprite oldS, TextureAtlasSprite newS, float v) {
         float denom = oldS.getV1() - oldS.getV0();
         if (denom == 0.0F) {
            return newS.getV0();
         }

         float t = (v - oldS.getV0()) / denom;
         return newS.getV0() + t * (newS.getV1() - newS.getV0());
      }

      private static int computeStepsFromModelUV(List<BakedQuad> quads, Axis fillAxis) {
         if (quads.isEmpty()) {
            return 0;
         }

         double bestModelSpan = -1.0;
         double bestPixelSpan = -1.0;

         for (BakedQuad q : quads) {
            if (fillAxis != Axis.Y || q.getDirection() != Direction.UP && q.getDirection() != Direction.DOWN) {
               int[] v = q.getVertices();
               int stride = v.length / 4;
               float minCoord = Float.POSITIVE_INFINITY;
               float maxCoord = Float.NEGATIVE_INFINITY;
               float minU = Float.POSITIVE_INFINITY;
               float maxU = Float.NEGATIVE_INFINITY;
               float minV = Float.POSITIVE_INFINITY;
               float maxV = Float.NEGATIVE_INFINITY;

               for (int i = 0; i < 4; i++) {
                  int base = i * stride;
                  float x = Float.intBitsToFloat(v[base]);
                  float y = Float.intBitsToFloat(v[base + 1]);
                  float z = Float.intBitsToFloat(v[base + 2]);
                  float u = Float.intBitsToFloat(v[base + 4]);
                  float vv = Float.intBitsToFloat(v[base + 5]);

                  float coord = switch (fillAxis) {
                     case X -> x;
                     case Z -> z;
                     case Y -> y;
                     default -> throw new MatchException(null, null);
                  };
                  minCoord = Math.min(minCoord, coord);
                  maxCoord = Math.max(maxCoord, coord);
                  minU = Math.min(minU, u);
                  maxU = Math.max(maxU, u);
                  minV = Math.min(minV, vv);
                  maxV = Math.max(maxV, vv);
               }

               double modelSpan = maxCoord - minCoord;
               if (!(modelSpan <= 1.0E-6)) {
                  TextureAtlasSprite s = q.getSprite();
                  double pixelSpan;
                  if (fillAxis == Axis.X) {
                     double denom = s.getU1() - s.getU0();
                     if (Math.abs(denom) < 1.0E-9) {
                        continue;
                     }

                     double uNormSpan = Math.abs(maxU - minU) / denom;
                     int texW = s.contents().width();
                     pixelSpan = uNormSpan * texW;
                  } else if (fillAxis == Axis.Y) {
                     double denom = s.getV1() - s.getV0();
                     if (Math.abs(denom) < 1.0E-9) {
                        continue;
                     }

                     double vNormSpan = Math.abs(maxV - minV) / denom;
                     int texH = s.contents().height();
                     pixelSpan = vNormSpan * texH;
                  } else {
                     double denom = s.getU1() - s.getU0();
                     if (Math.abs(denom) < 1.0E-9) {
                        continue;
                     }

                     double uNormSpan = Math.abs(maxU - minU) / denom;
                     int texW = s.contents().width();
                     pixelSpan = uNormSpan * texW;
                  }

                  if (modelSpan > bestModelSpan) {
                     bestModelSpan = modelSpan;
                     bestPixelSpan = pixelSpan;
                  }
               }
            }
         }

         return bestPixelSpan <= 0.0 ? 0 : Mth.clamp((int)Math.round(bestPixelSpan), 1, 64);
      }

      public boolean useAmbientOcclusion() {
         return true;
      }

      public boolean isGui3d() {
         return true;
      }

      public boolean usesBlockLight() {
         return true;
      }

      public boolean isCustomRenderer() {
         return true;
      }

      public TextureAtlasSprite getParticleIcon() {
         return this.models.get(BackpackDynamicModel.ModelPart.BASE).getParticleIcon();
      }

      public ItemOverrides getOverrides() {
         return this.overrideList;
      }

      public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
         if (transformType == ItemDisplayContext.NONE) {
            return this;
         }

         this.lastContext = transformType;
         this.itemTransforms.getTransform(transformType).apply(applyLeftHandTransform, poseStack);
         return this;
      }

      public ItemTransforms getTransforms() {
         return this.itemTransforms;
      }

      public ModelData getModelData(BlockAndTintGetter level, BlockPos pos, BlockState state, ModelData modelData) {
         return level.getBlockEntity(pos, ModBlocks.BACKPACK_TILE_TYPE.get()).map(backpackBlockEntity -> {
            net.neoforged.neoforge.client.model.data.ModelData.Builder modelDataBuilder = modelData.derive();
            RenderInfo renderInfo = backpackBlockEntity.getBackpackWrapper().getRenderInfo();
            Map<TankPosition, TankRenderInfo> tankRenderInfos = renderInfo.getTankRenderInfos();
            tankRenderInfos.forEach((tankPos, tankInfo) -> {
               if (tankPos == TankPosition.LEFT) {
                  modelDataBuilder.with(HAS_TANK_LEFT, true);
                  modelDataBuilder.with(LEFT_TANK_RENDER_INFO, tankInfo);
               } else {
                  modelDataBuilder.with(HAS_TANK_RIGHT, true);
                  modelDataBuilder.with(RIGHT_TANK_RENDER_INFO, tankInfo);
               }
            });
            renderInfo.getBatteryRenderInfo().ifPresent(batteryInfo -> {
               modelDataBuilder.with(HAS_BATTERY, true);
               modelDataBuilder.with(BATTERY_RENDER_INFO, batteryInfo);
            });
            return modelDataBuilder.build();
         }).orElse(modelData);
      }

      private record FluidCacheKey(Fluid fluid, PatchedDataComponentMap components, int step) {
      }

      private record SliceSpec(Axis axis, double cut, boolean keepGreaterOrEqual) {
      }

      private class TankBakedModel implements IDynamicBakedModel {
         public List<BakedQuad> getQuads(
            @Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData extraData, @Nullable RenderType renderType
         ) {
            if (side != null) {
               return Collections.emptyList();
            }

            List<BakedQuad> ret = new ArrayList<>();
            boolean left = state == null ? BackpackBakedModel.this.tankLeft : (Boolean)state.getValue(BackpackBlock.LEFT_TANK);
            boolean right = state == null ? BackpackBakedModel.this.tankRight : (Boolean)state.getValue(BackpackBlock.RIGHT_TANK);
            TankRenderInfo leftTankRenderInfo = BackpackBakedModel.this.leftTankRenderInfo;
            TankRenderInfo rightTankRenderInfo = BackpackBakedModel.this.rightTankRenderInfo;
            if (left && leftTankRenderInfo != null && leftTankRenderInfo.getFillRatio() != 0.0F) {
               leftTankRenderInfo.getFluid()
                  .ifPresent(
                     fluid -> {
                        if (BackpackBakedModel.this.cachedLeftTankSteps != -1) {
                           BackpackDynamicModel.BackpackBakedModel.FluidCacheKey cacheKey = this.getFluidCacheKey(
                              fluid, BackpackBakedModel.this.cachedLeftTankSteps, leftTankRenderInfo.getFillRatio()
                           );
                           if (BackpackBakedModel.this.leftTankFluidCache.containsKey(cacheKey)) {
                              ret.addAll(BackpackBakedModel.this.leftTankFluidCache.get(cacheKey));
                              return;
                           }
                        }

                        List<BakedQuad> fluidQuads = BackpackBakedModel.this.getTankFluidFromModel(
                           state, rand, extraData, renderType, BackpackDynamicModel.ModelPart.LEFT_TANK_FLUID, fluid, leftTankRenderInfo.getFillRatio(), true
                        );
                        BackpackBakedModel.this.leftTankFluidCache
                           .put(this.getFluidCacheKey(fluid, BackpackBakedModel.this.cachedLeftTankSteps, leftTankRenderInfo.getFillRatio()), fluidQuads);
                        ret.addAll(fluidQuads);
                     }
                  );
            }

            if (right && rightTankRenderInfo != null && rightTankRenderInfo.getFillRatio() != 0.0F) {
               rightTankRenderInfo.getFluid()
                  .ifPresent(
                     fluid -> {
                        if (BackpackBakedModel.this.cachedRightTankSteps != -1) {
                           BackpackDynamicModel.BackpackBakedModel.FluidCacheKey cacheKey = this.getFluidCacheKey(
                              fluid, BackpackBakedModel.this.cachedRightTankSteps, rightTankRenderInfo.getFillRatio()
                           );
                           if (BackpackBakedModel.this.rightTankFluidCache.containsKey(cacheKey)) {
                              ret.addAll(BackpackBakedModel.this.rightTankFluidCache.get(cacheKey));
                              return;
                           }
                        }

                        List<BakedQuad> fluidQuads = BackpackBakedModel.this.getTankFluidFromModel(
                           state,
                           rand,
                           extraData,
                           renderType,
                           BackpackDynamicModel.ModelPart.RIGHT_TANK_FLUID,
                           fluid,
                           rightTankRenderInfo.getFillRatio(),
                           false
                        );
                        BackpackBakedModel.this.rightTankFluidCache
                           .put(this.getFluidCacheKey(fluid, BackpackBakedModel.this.cachedRightTankSteps, rightTankRenderInfo.getFillRatio()), fluidQuads);
                        ret.addAll(fluidQuads);
                     }
                  );
            }

            return ret;
         }

         private BackpackDynamicModel.BackpackBakedModel.FluidCacheKey getFluidCacheKey(FluidStack fluid, int cachedSteps, float fillRatio) {
            int step = cachedSteps > 0 ? BackpackDynamicModel.BackpackBakedModel.ratioToStep(fillRatio, cachedSteps) : 0;
            return new BackpackDynamicModel.BackpackBakedModel.FluidCacheKey(fluid.getFluid(), fluid.getComponents(), step);
         }

         public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
            return List.of(RenderType.translucent());
         }

         public boolean useAmbientOcclusion() {
            return BackpackBakedModel.this.useAmbientOcclusion();
         }

         public boolean isGui3d() {
            return BackpackBakedModel.this.isGui3d();
         }

         public boolean usesBlockLight() {
            return BackpackBakedModel.this.usesBlockLight();
         }

         public boolean isCustomRenderer() {
            return BackpackBakedModel.this.isCustomRenderer();
         }

         public TextureAtlasSprite getParticleIcon() {
            return BackpackBakedModel.this.getParticleIcon();
         }

         public ItemOverrides getOverrides() {
            return BackpackBakedModel.this.getOverrides();
         }
      }

      private record Vert(float x, float y, float z, float u, float v) {
      }
   }

   private static class BackpackItemOverrideList extends ItemOverrides {
      private final BackpackDynamicModel.BackpackBakedModel backpackModel;

      public BackpackItemOverrideList(BackpackDynamicModel.BackpackBakedModel backpackModel) {
         this.backpackModel = backpackModel;
      }

      @Nullable
      public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity livingEntity, int seed) {
         this.backpackModel.tankRight = false;
         this.backpackModel.tankLeft = false;
         this.backpackModel.rightTankRenderInfo = null;
         this.backpackModel.leftTankRenderInfo = null;
         this.backpackModel.battery = false;
         this.backpackModel.batteryRenderInfo = null;
         IBackpackWrapper backpackWrapper = BackpackWrapper.fromStack(stack);
         RenderInfo renderInfo = backpackWrapper.getRenderInfo();
         Map<TankPosition, TankRenderInfo> tankRenderInfos = renderInfo.getTankRenderInfos();
         tankRenderInfos.forEach((pos, info) -> {
            if (pos == TankPosition.LEFT) {
               this.backpackModel.tankLeft = true;
               this.backpackModel.leftTankRenderInfo = info;
            } else {
               this.backpackModel.tankRight = true;
               this.backpackModel.rightTankRenderInfo = info;
            }
         });
         renderInfo.getBatteryRenderInfo().ifPresent(batteryRenderInfo -> {
            this.backpackModel.battery = true;
            this.backpackModel.batteryRenderInfo = batteryRenderInfo;
         });
         return this.backpackModel;
      }
   }

   public static final class Loader implements IGeometryLoader<BackpackDynamicModel> {
      public static final BackpackDynamicModel.Loader INSTANCE = new BackpackDynamicModel.Loader();

      public BackpackDynamicModel read(JsonObject modelContents, JsonDeserializationContext deserializationContext) {
         Builder<BackpackDynamicModel.ModelPart, ResourceLocation> builder = ImmutableMap.builder();

         for (BackpackDynamicModel.ModelPart part : BackpackDynamicModel.ModelPart.values()) {
            builder.put(part, SophisticatedBackpacks.getRL("block/backpack_" + part.name().toLowerCase(Locale.ENGLISH)));
         }

         return new BackpackDynamicModel(builder.build());
      }
   }

   private enum ModelPart {
      BASE,
      BATTERY,
      FRONT_POUCH,
      LEFT_POUCH,
      LEFT_TANK,
      RIGHT_POUCH,
      RIGHT_TANK,
      STRAPS,
      LEFT_TANK_FLUID,
      RIGHT_TANK_FLUID,
      BATTERY_CHARGE,
      DISPLAY_ITEM;
   }
}
