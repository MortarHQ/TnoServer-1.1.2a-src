package twilightforest.client.renderer.block;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.CullStateShard;
import net.minecraft.client.renderer.RenderStateShard.DepthTestStateShard;
import net.minecraft.client.renderer.RenderStateShard.LightmapStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import twilightforest.TwilightForestMod;
import twilightforest.block.entity.RedThreadBlockEntity;
import twilightforest.client.TFShaders;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.RedThreadModel;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFDataAttachments;
import twilightforest.init.custom.TravellersModifiersManager;

public class RedThreadRenderer<T extends RedThreadBlockEntity> implements BlockEntityRenderer<T> {
   private static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("red_thread.png");
   private final RedThreadModel redThreadModel;
   private static final RenderType GLOW = RenderType.create(
      "twilightforest:glow",
      DefaultVertexFormat.BLOCK,
      Mode.QUADS,
      2097152,
      true,
      false,
      CompositeState.builder()
         .setLightmapState(new LightmapStateShard(true))
         .setCullState(new CullStateShard(true))
         .setDepthTestState(new DepthTestStateShard("always", 519))
         .setShaderState(new ShaderStateShard(() -> TFShaders.RED_THREAD))
         .setTextureState(new TextureStateShard(TEXTURE, false, true))
         .createCompositeState(true)
   );

   public RedThreadRenderer(Context context) {
      this.redThreadModel = new RedThreadModel(context.bakeLayer(TFModelLayers.RED_THREAD));
   }

   public void render(T entity, float ticks, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
      BlockState state = entity.getBlockState();
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         boolean wearsActivatedTravellersGoggles = (Boolean)player.getData(TFDataAttachments.TRAVELLERS_GOGGLES_RED_THREAD_VISION)
            && TravellersModifiersManager.isModifierActive(player, TravellersModifiersManager.RED_THREAD_VISION_MODIFIER);
         boolean glow = player.isHolding(((Block)TFBlocks.RED_THREAD.get()).asItem()) || wearsActivatedTravellersGoggles;

         for (Direction face : Direction.values()) {
            if ((Boolean)state.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(face))) {
               stack.pushPose();
               Vec3 xyz = getXYZ(face);
               stack.translate(xyz.x, xyz.y, xyz.z);
               stack.mulPose(Axis.ZP.rotationDegrees(getZPDegrees(face)));
               stack.mulPose(Axis.XP.rotationDegrees(getXPDegrees(face)));
               stack.pushPose();
               stack.translate(0.5, 0.0, 0.5);
               this.render(entity, face, stack, glow ? buffer.getBuffer(GLOW) : buffer.getBuffer(RenderType.entityCutout(TEXTURE)), glow ? 15728880 : light);
               stack.popPose();
               stack.popPose();
            }
         }
      }
   }

   private void render(T entity, Direction face, PoseStack stack, VertexConsumer consumer, int light) {
      Level level = entity.getLevel();
      BlockPos pos = entity.getBlockPos();
      this.redThreadModel.renderCenterPiece(stack, consumer, light);

      for (Direction direction : Direction.values()) {
         if (!direction.getAxis().equals(face.getAxis())) {
            boolean flag = (Boolean)entity.getBlockState().getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction));
            if (!flag && level != null) {
               BlockState state = level.getBlockState(pos.relative(direction));
               flag = state.getBlock().equals(TFBlocks.RED_THREAD.get()) && (Boolean)state.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(face));
               if (!flag) {
                  state = level.getBlockState(pos.relative(direction).relative(face));
                  boolean threadBlocked = level.getBlockState(pos.relative(direction)).isFaceSturdy(level, pos, direction.getOpposite());
                  flag = state.is((Block)TFBlocks.RED_THREAD.get())
                     && !threadBlocked
                     && (Boolean)state.getValue((Property)PipeBlock.PROPERTY_BY_DIRECTION.get(direction.getOpposite()));
               }
            }

            if (flag) {
               this.redThreadModel.getPart(face, direction).render(stack, consumer, light, OverlayTexture.NO_OVERLAY);
            }
         }
      }
   }

   private static Vec3 getXYZ(Direction face) {
      return new Vec3(
         face != Direction.EAST && face != Direction.UP ? 0.0 : 1.0,
         face != Direction.WEST && face != Direction.UP && face != Direction.NORTH ? 0.0 : 1.0,
         face == Direction.SOUTH ? 1.0 : 0.0
      );
   }

   private static float getZPDegrees(Direction face) {
      return switch (face) {
         case EAST -> 90.0F;
         case UP -> 180.0F;
         case WEST -> 270.0F;
         default -> 0.0F;
      };
   }

   private static float getXPDegrees(Direction face) {
      return switch (face) {
         case NORTH -> 90.0F;
         case SOUTH -> 270.0F;
         default -> 0.0F;
      };
   }
}
