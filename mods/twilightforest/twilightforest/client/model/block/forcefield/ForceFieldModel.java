package twilightforest.client.model.block.forcefield;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.StringRepresentable.EnumCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.ForceFieldBlock;

public class ForceFieldModel implements IDynamicBakedModel {
   private static final ModelProperty<ForceFieldModel.ForceFieldData> DATA = new ModelProperty();
   private static final FaceBakery FACE_BAKERY = new FaceBakery();
   private final Map<BlockElement, ForceFieldModelLoader.Condition> parts;
   private final Function<Material, TextureAtlasSprite> spriteFunction;
   private final IGeometryBakingContext context;
   private final TextureAtlasSprite particle;
   private final ItemOverrides overrides;
   @Nullable
   private final ChunkRenderTypeSet blockRenderTypes;
   @Nullable
   private final List<RenderType> itemRenderTypes;
   @Nullable
   private final List<RenderType> fabulousItemRenderTypes;

   public ForceFieldModel(
      Map<BlockElement, ForceFieldModelLoader.Condition> parts,
      Function<Material, TextureAtlasSprite> spriteFunction,
      IGeometryBakingContext context,
      ItemOverrides overrides
   ) {
      this.parts = parts;
      this.spriteFunction = spriteFunction;
      this.context = context;
      this.particle = spriteFunction.apply(context.getMaterial("particle"));
      this.overrides = overrides;
      ResourceLocation renderTypeHint = context.getRenderTypeHint();
      RenderTypeGroup group = renderTypeHint != null ? context.getRenderType(renderTypeHint) : RenderTypeGroup.EMPTY;
      this.blockRenderTypes = !group.isEmpty() ? ChunkRenderTypeSet.of(new RenderType[]{group.block()}) : null;
      this.itemRenderTypes = !group.isEmpty() ? List.of(group.entity()) : null;
      this.fabulousItemRenderTypes = !group.isEmpty() ? List.of(group.entityFabulous()) : null;
   }

   @NotNull
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction cullFace, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType renderType
   ) {
      List<BakedQuad> quads = new ArrayList<>();
      ForceFieldModel.ForceFieldData data = (ForceFieldModel.ForceFieldData)extraData.get(DATA);
      if (data != null) {
         if (cullFace != null) {
            return this.getQuads(quads, cullFace, data, true);
         }

         for (Direction direction : Direction.values()) {
            quads = this.getQuads(quads, direction, data, false);
         }
      }

      return quads;
   }

   @NotNull
   public List<BakedQuad> getQuads(List<BakedQuad> quads, Direction side, ForceFieldModel.ForceFieldData data, boolean cull) {
      for (Entry<BlockElement, ForceFieldModelLoader.Condition> entry : this.parts.entrySet()) {
         BlockElementFace blockelementface = (BlockElementFace)entry.getKey().faces.get(side);
         if (blockelementface != null
            && blockelementface.cullForDirection() != null == cull
            && !skipRender(data.directions(), entry.getValue().direction(), entry.getValue().b(), entry.getValue().parents(), side)) {
            TextureAtlasSprite sprite = this.spriteFunction.apply(this.context.getMaterial(blockelementface.texture()));
            quads.add(FACE_BAKERY.bakeQuad(entry.getKey().from, entry.getKey().to, blockelementface, sprite, side, BlockModelRotation.X0_Y0, null, false));
         }
      }

      return quads;
   }

   protected static boolean skipRender(
      Map<ForceFieldModel.ExtraDirection, List<Direction>> directions,
      @Nullable ForceFieldModel.ExtraDirection direction,
      boolean supposedToBe,
      List<ForceFieldModel.ExtraDirection> parents,
      Direction side
   ) {
      if (direction == null) {
         return false;
      }

      for (ForceFieldModel.ExtraDirection parent : parents) {
         if (!directions.containsKey(parent)) {
            return true;
         }
      }

      boolean hasKey = directions.containsKey(direction);
      if (hasKey != supposedToBe) {
         return true;
      } else {
         return hasKey ? directions.get(direction).contains(side) : false;
      }
   }

   @NotNull
   public ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
      if (modelData == ModelData.EMPTY) {
         Map<ForceFieldModel.ExtraDirection, List<Direction>> map = new HashMap<>();

         for (ForceFieldModel.ExtraDirection extraDirection : getExtraDirections(state, level, pos)) {
            List<Direction> directionList = new ArrayList<>();

            for (Direction dir : Direction.values()) {
               ForceFieldModel.ExtraDirection mirrored = extraDirection.mirrored(dir.getAxis());
               if (mirrored != extraDirection) {
                  BlockState other = level.getBlockState(pos.relative(dir));
                  if (other.getBlock() instanceof ForceFieldBlock && getExtraDirections(other, level, pos.relative(dir)).contains(mirrored)) {
                     directionList.add(dir);
                  }
               }
            }

            map.put(extraDirection, directionList);
         }

         modelData = ModelData.builder().with(DATA, new ForceFieldModel.ForceFieldData(map)).build();
      }

      return modelData;
   }

   public static List<ForceFieldModel.ExtraDirection> getExtraDirections(BlockState state, BlockGetter level, BlockPos pos) {
      List<ForceFieldModel.ExtraDirection> directions = new ArrayList<>();
      boolean down = (Boolean)state.getValue(ForceFieldBlock.DOWN);
      boolean up = (Boolean)state.getValue(ForceFieldBlock.UP);
      boolean north = (Boolean)state.getValue(ForceFieldBlock.NORTH);
      boolean south = (Boolean)state.getValue(ForceFieldBlock.SOUTH);
      boolean west = (Boolean)state.getValue(ForceFieldBlock.WEST);
      boolean east = (Boolean)state.getValue(ForceFieldBlock.EAST);
      if (down) {
         directions.add(ForceFieldModel.ExtraDirection.DOWN);
         if (north && ForceFieldBlock.cornerConnects(level, pos, Direction.DOWN, Direction.NORTH)) {
            directions.add(ForceFieldModel.ExtraDirection.DOWN_NORTH);
         }

         if (south && ForceFieldBlock.cornerConnects(level, pos, Direction.DOWN, Direction.SOUTH)) {
            directions.add(ForceFieldModel.ExtraDirection.DOWN_SOUTH);
         }

         if (west && ForceFieldBlock.cornerConnects(level, pos, Direction.DOWN, Direction.WEST)) {
            directions.add(ForceFieldModel.ExtraDirection.DOWN_WEST);
         }

         if (east && ForceFieldBlock.cornerConnects(level, pos, Direction.DOWN, Direction.EAST)) {
            directions.add(ForceFieldModel.ExtraDirection.DOWN_EAST);
         }
      }

      if (up) {
         directions.add(ForceFieldModel.ExtraDirection.UP);
         if (north && ForceFieldBlock.cornerConnects(level, pos, Direction.UP, Direction.NORTH)) {
            directions.add(ForceFieldModel.ExtraDirection.UP_NORTH);
         }

         if (south && ForceFieldBlock.cornerConnects(level, pos, Direction.UP, Direction.SOUTH)) {
            directions.add(ForceFieldModel.ExtraDirection.UP_SOUTH);
         }

         if (west && ForceFieldBlock.cornerConnects(level, pos, Direction.UP, Direction.WEST)) {
            directions.add(ForceFieldModel.ExtraDirection.UP_WEST);
         }

         if (east && ForceFieldBlock.cornerConnects(level, pos, Direction.UP, Direction.EAST)) {
            directions.add(ForceFieldModel.ExtraDirection.UP_EAST);
         }
      }

      if (north) {
         directions.add(ForceFieldModel.ExtraDirection.NORTH);
         if (west && ForceFieldBlock.cornerConnects(level, pos, Direction.NORTH, Direction.WEST)) {
            directions.add(ForceFieldModel.ExtraDirection.NORTH_WEST);
         }

         if (east && ForceFieldBlock.cornerConnects(level, pos, Direction.NORTH, Direction.EAST)) {
            directions.add(ForceFieldModel.ExtraDirection.NORTH_EAST);
         }
      }

      if (south) {
         directions.add(ForceFieldModel.ExtraDirection.SOUTH);
         if (west && ForceFieldBlock.cornerConnects(level, pos, Direction.SOUTH, Direction.WEST)) {
            directions.add(ForceFieldModel.ExtraDirection.SOUTH_WEST);
         }

         if (east && ForceFieldBlock.cornerConnects(level, pos, Direction.SOUTH, Direction.EAST)) {
            directions.add(ForceFieldModel.ExtraDirection.SOUTH_EAST);
         }
      }

      if (west) {
         directions.add(ForceFieldModel.ExtraDirection.WEST);
      }

      if (east) {
         directions.add(ForceFieldModel.ExtraDirection.EAST);
      }

      return directions;
   }

   public boolean useAmbientOcclusion() {
      return this.context.useAmbientOcclusion();
   }

   public boolean isGui3d() {
      return this.context.isGui3d();
   }

   public boolean usesBlockLight() {
      return this.context.useBlockLight();
   }

   public boolean isCustomRenderer() {
      return false;
   }

   public TextureAtlasSprite getParticleIcon() {
      return this.particle;
   }

   public ItemOverrides getOverrides() {
      return this.overrides;
   }

   @NotNull
   public ItemTransforms getTransforms() {
      return this.context.getTransforms();
   }

   @NotNull
   public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
      return this.blockRenderTypes != null ? this.blockRenderTypes : super.getRenderTypes(state, rand, data);
   }

   @NotNull
   public List<RenderType> getRenderTypes(@NotNull ItemStack stack, boolean fabulous) {
      if (!fabulous) {
         if (this.itemRenderTypes != null) {
            return this.itemRenderTypes;
         }
      } else if (this.fabulousItemRenderTypes != null) {
         return this.fabulousItemRenderTypes;
      }

      return super.getRenderTypes(stack, fabulous);
   }

   public enum ExtraDirection implements StringRepresentable {
      DOWN("down", 0, 1, 0),
      UP("up", 1, 0, 1),
      NORTH("north", 2, 2, 3),
      SOUTH("south", 3, 3, 2),
      WEST("west", 5, 4, 4),
      EAST("east", 4, 5, 5),
      DOWN_NORTH("down_north", 6, 10, 7),
      DOWN_SOUTH("down_south", 7, 11, 6),
      DOWN_WEST("down_west", 9, 12, 8),
      DOWN_EAST("down_east", 8, 13, 9),
      UP_NORTH("up_north", 10, 6, 11),
      UP_SOUTH("up_south", 11, 7, 10),
      UP_WEST("up_west", 13, 8, 12),
      UP_EAST("up_east", 12, 9, 13),
      NORTH_WEST("north_west", 15, 14, 16),
      NORTH_EAST("north_east", 14, 15, 17),
      SOUTH_WEST("south_west", 17, 16, 14),
      SOUTH_EAST("south_east", 16, 17, 15);

      public static final EnumCodec<ForceFieldModel.ExtraDirection> CODEC = StringRepresentable.fromEnum(ForceFieldModel.ExtraDirection::values);
      private final String name;
      private final int xAxisMirror;
      private final int yAxisMirror;
      private final int zAxisMirror;

      ExtraDirection(String name, int xAxisMirror, int yAxisMirror, int zAxisMirror) {
         this.name = name;
         this.xAxisMirror = xAxisMirror;
         this.yAxisMirror = yAxisMirror;
         this.zAxisMirror = zAxisMirror;
      }

      public String getSerializedName() {
         return this.name;
      }

      public ForceFieldModel.ExtraDirection mirrored(Axis axis) {
         return switch (axis) {
            case X -> values()[this.xAxisMirror];
            case Y -> values()[this.yAxisMirror];
            case Z -> values()[this.zAxisMirror];
            default -> throw new MatchException(null, null);
         };
      }

      @Nullable
      public static ForceFieldModel.ExtraDirection byName(@Nullable String name) {
         return (ForceFieldModel.ExtraDirection)CODEC.byName(name);
      }
   }

   public record ForceFieldData(Map<ForceFieldModel.ExtraDirection, List<Direction>> directions) {
   }
}
