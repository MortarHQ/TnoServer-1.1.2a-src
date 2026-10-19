package com.teamresourceful.resourcefullib.common.fluid.neoforge;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamresourceful.resourcefullib.client.fluid.data.ClientFluidProperties;
import com.teamresourceful.resourcefullib.client.fluid.registry.ResourcefulClientFluidRegistry;
import com.teamresourceful.resourcefullib.common.fluid.data.FluidProperties;
import java.util.function.Consumer;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.textures.FluidSpriteCache;
import net.neoforged.neoforge.common.SoundAction;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidType.Properties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class ResourcefulFluidType extends FluidType {
   private final ResourceLocation id;

   public ResourcefulFluidType(ResourceLocation id, FluidProperties props) {
      super((Properties)Util.make(Properties.create(), properties -> {
         properties.descriptionId(Util.makeDescriptionId("fluid_type", id));
         properties.adjacentPathType(props.adjacentPathType());
         properties.canConvertToSource(props.canConvertToSource());
         properties.canDrown(props.canDrown());
         properties.canExtinguish(props.canExtinguish());
         properties.canHydrate(props.canHydrate());
         properties.canPushEntity(props.canPushEntity());
         properties.canSwim(props.canSwim());
         properties.density(props.density());
         properties.fallDistanceModifier(props.fallDistanceModifier());
         properties.lightLevel(props.lightLevel());
         properties.motionScale(props.motionScale());
         properties.pathType(props.pathType());
         properties.rarity(props.rarity());
         properties.temperature(props.temperature());
         properties.viscosity(props.viscosity());
         props.sounds().sounds().forEach((name, sound) -> properties.sound(SoundAction.get(name), sound));
      }));
      this.id = id;
   }

   public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
      final ResourcefulFluidType type = this;
      consumer.accept(
         new IClientFluidTypeExtensions() {
            private ClientFluidProperties properties = null;

            private ClientFluidProperties properties() {
               if (this.properties == null) {
                  this.properties = ResourcefulClientFluidRegistry.get(type.id);
               }

               return this.properties;
            }

            @NotNull
            public ResourceLocation getStillTexture() {
               return this.properties().still(null, null, null);
            }

            @NotNull
            public ResourceLocation getStillTexture(@NotNull FluidStack stack) {
               return this.properties().still(null, null, stack.getFluid().defaultFluidState());
            }

            @NotNull
            public ResourceLocation getStillTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
               return this.properties().still(getter, pos, state);
            }

            @NotNull
            public ResourceLocation getFlowingTexture() {
               return this.properties().flowing(null, null, null);
            }

            @NotNull
            public ResourceLocation getFlowingTexture(@NotNull FluidStack stack) {
               return this.properties().flowing(null, null, stack.getFluid().defaultFluidState());
            }

            @NotNull
            public ResourceLocation getFlowingTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
               return this.properties().flowing(getter, pos, state);
            }

            @Nullable
            public ResourceLocation getOverlayTexture() {
               return this.properties().overlay(null, null, null);
            }

            @NotNull
            public ResourceLocation getOverlayTexture(@NotNull FluidStack stack) {
               return this.properties().overlay(null, null, stack.getFluid().defaultFluidState());
            }

            @NotNull
            public ResourceLocation getOverlayTexture(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
               return this.properties().overlay(getter, pos, state);
            }

            public void renderOverlay(@NotNull Minecraft mc, @NotNull PoseStack poseStack) {
               this.properties().renderOverlay(mc, poseStack);
            }

            public int getTintColor() {
               return this.properties().tintColor(null, null, null);
            }

            public int getTintColor(@NotNull FluidStack stack) {
               return this.properties().tintColor(null, null, stack.getFluid().defaultFluidState());
            }

            public int getTintColor(@NotNull FluidState state, @NotNull BlockAndTintGetter getter, @NotNull BlockPos pos) {
               return this.properties().tintColor(getter, pos, state);
            }

            public boolean renderFluid(
               @NotNull FluidState fluidState,
               @NotNull BlockAndTintGetter getter,
               @NotNull BlockPos pos,
               @NotNull VertexConsumer vertexConsumer,
               @NotNull BlockState blockState
            ) {
               return this.properties().renderFluid(pos, getter, vertexConsumer, blockState, fluidState, FluidSpriteCache::getSprite)
                  ? true
                  : super.renderFluid(fluidState, getter, pos, vertexConsumer, blockState);
            }

            @NotNull
            public Vector3f modifyFogColor(
               @NotNull Camera camera,
               float partialTick,
               @NotNull ClientLevel level,
               int renderDistance,
               float darkenWorldAmount,
               @NotNull Vector3f fluidFogColor
            ) {
               return this.properties().modifyFogColor(camera, partialTick, level, renderDistance, darkenWorldAmount, fluidFogColor);
            }

            public void modifyFogRender(
               @NotNull Camera camera,
               @NotNull FogMode mode,
               float renderDistance,
               float partialTick,
               float nearDistance,
               float farDistance,
               @NotNull FogShape shape
            ) {
               this.properties().modifyFogRender(camera, mode, renderDistance, partialTick, nearDistance, farDistance, shape);
            }
         }
      );
   }
}
