package team.creative.creativecore.client.render.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.creative.creativecore.client.render.box.QuadGeneratorContext;
import team.creative.creativecore.client.render.box.RenderBox;
import team.creative.creativecore.common.util.math.base.Facing;

public class CreativeBakedBoxModel extends CreativeBakedModel {
   public static Minecraft mc = Minecraft.getInstance();
   private static final ThreadLocal<QuadGeneratorContext> QUAD_CONTEXT = ThreadLocal.withInitial(QuadGeneratorContext::new);
   private final List<BakedModel> both;
   private final CreativeBakedBoxModelTranslucent pairModel;
   public CreativeBlockModel block;
   public ItemOverrides customOverride = new ItemOverrides() {
      public BakedModel resolve(BakedModel original, ItemStack stack, ClientLevel level, LivingEntity entity, int p_173469_) {
         CreativeBakedBoxModel.this.renderedStack = stack;
         return super.resolve(original, stack, level, entity, p_173469_);
      }
   };

   public static List<BakedQuad> compileBoxes(
      List<? extends RenderBox> boxes, Facing side, RenderType layer, RandomSource rand, boolean item, List<BakedQuad> baked
   ) {
      if (side == null) {
         return Collections.EMPTY_LIST;
      }

      for (int i = 0; i < boxes.size(); i++) {
         RenderBox box = boxes.get(i);
         if (box.shouldRenderFace(side)) {
            BlockState state = Blocks.AIR.defaultBlockState();
            if (box.state != null) {
               state = box.state;
            }

            BakedModel blockModel = mc.getBlockRenderer().getBlockModel(state);
            int defaultColor = -1;
            if (item) {
               defaultColor = mc.getItemColors().getColor(new ItemStack(state.getBlock()), defaultColor);
            }

            QuadGeneratorContext context = QUAD_CONTEXT.get();
            baked.addAll(box.getBakedQuad(context, null, null, box.getOffset(), state, blockModel, ModelData.EMPTY, side, layer, rand, true, defaultColor));
            context.clear();
         }
      }

      for (BakedQuad quad : baked) {
         if (quad instanceof CreativeBakedQuad c) {
            c.updateAlpha();
         }
      }

      return baked;
   }

   public CreativeBakedBoxModel(ModelResourceLocation location, CreativeItemBoxModel item, CreativeBlockModel block) {
      super(location, item);
      this.block = block;
      this.pairModel = new CreativeBakedBoxModelTranslucent(location, item, block);
      this.both = Arrays.asList(this, this.pairModel);
   }

   CreativeBakedBoxModel(ModelResourceLocation location, CreativeItemBoxModel item, CreativeBlockModel block, boolean outside) {
      super(location, item);
      this.block = block;
      if (outside) {
         this.pairModel = new CreativeBakedBoxModelTranslucent(location, item, block);
         this.both = Arrays.asList(this, this.pairModel);
      } else {
         this.pairModel = null;
         this.both = null;
      }
   }

   public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
      if (((CreativeItemBoxModel)this.item).hasTranslucentLayer(itemStack)) {
         this.pairModel.renderedStack = this.renderedStack;
         return this.both;
      } else {
         return super.getRenderPasses(itemStack, fabulous);
      }
   }

   @NotNull
   public ModelData getModelData(@NotNull BlockAndTintGetter level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull ModelData modelData) {
      return this.block != null ? this.block.getModelData(level, pos, state, modelData) : modelData;
   }

   @NotNull
   @Override
   public List<BakedQuad> getQuads(
      @Nullable BlockState state, @Nullable Direction direction, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType layer
   ) {
      Facing facing = Facing.get(direction);
      if (state != null) {
         return this.block != null
            ? compileBoxes(this.block.getBoxes(state, extraData, rand), facing, layer, rand, false, new ArrayList<>())
            : Collections.EMPTY_LIST;
      }

      if (this.renderedStack != null && !this.renderedStack.isEmpty() && direction == null) {
         try {
            List<BakedQuad> cached = ((CreativeItemBoxModel)this.item).getCachedModel(this.translucent(), this.renderedStack, false);
            if (cached != null) {
               return cached;
            }

            List<? extends RenderBox> boxes = ((CreativeItemBoxModel)this.item).getBoxes(this.renderedStack, this.translucent());
            if (boxes == null) {
               return Collections.EMPTY_LIST;
            }

            cached = new ArrayList<>();

            for (int i = 0; i < Facing.VALUES.length; i++) {
               compileBoxes(boxes, Facing.VALUES[i], layer, rand, true, cached);
            }

            ((CreativeItemBoxModel)this.item).saveCachedModel(this.translucent(), cached, this.renderedStack, false);
            return cached;
         } finally {
            this.renderedStack = null;
         }
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public boolean translucent() {
      return false;
   }

   @Deprecated
   @Override
   public List<BakedQuad> getQuads(BlockState state, Direction direction, RandomSource rand) {
      return this.getQuads(state, direction, rand, ModelData.EMPTY, Sheets.cutoutBlockSheet());
   }
}
