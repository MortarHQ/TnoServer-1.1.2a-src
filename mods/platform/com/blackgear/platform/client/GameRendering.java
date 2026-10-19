package com.blackgear.platform.client;

import com.blackgear.platform.client.neoforge.GameRenderingImpl;
import com.blackgear.platform.client.v2.render.HandHeldItemRenderer;
import com.blackgear.platform.client.v2.render.ItemRendererRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class GameRendering {
   public static final Map<ResourceLocation, ResourceLocation> MODEL_OVERRIDES = new ConcurrentHashMap<>();

   @ExpectPlatform
   @Transformed
   public static void registerBlockColors(Consumer<GameRendering.BlockColorEvent> listener) {
      GameRenderingImpl.registerBlockColors(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerItemColors(Consumer<GameRendering.ItemColorEvent> listener) {
      GameRenderingImpl.registerItemColors(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerBlockRenderers(Consumer<GameRendering.BlockRendererEvent> listener) {
      GameRenderingImpl.registerBlockRenderers(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerEntityRenderers(Consumer<GameRendering.EntityRendererEvent> listener) {
      GameRenderingImpl.registerEntityRenderers(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerBlockEntityRenderers(Consumer<GameRendering.BlockEntityRendererEvent> listener) {
      GameRenderingImpl.registerBlockEntityRenderers(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerModelLayers(Consumer<GameRendering.ModelLayerEvent> listener) {
      GameRenderingImpl.registerModelLayers(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerSpecialModels(Consumer<GameRendering.SpecialModelEvent> listener) {
      GameRenderingImpl.registerSpecialModels(listener);
   }

   public static void registerHandHeldModels(Consumer<GameRendering.HandHeldModelEvent> listener) {
      GameRendering.HandHeldModelEvent event = (item, original, handHeld, perspectives) -> ((ItemRendererRegistry)ItemRendererRegistry.INSTANCE.get())
         .register(item, new HandHeldItemRenderer(original, handHeld));
      listener.accept(event);
   }

   @ExpectPlatform
   @Transformed
   public static void registerModelOverrides(Consumer<GameRendering.ModelOverrideEvent> listener) {
      GameRenderingImpl.registerModelOverrides(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerSkullRenderers(Consumer<GameRendering.SkullRendererEvent> listener) {
      GameRenderingImpl.registerSkullRenderers(listener);
   }

   @ExpectPlatform
   @Transformed
   public static void registerParticleFactories(Consumer<GameRendering.ParticleFactoryEvent> listener) {
      GameRenderingImpl.registerParticleFactories(listener);
   }

   public interface BlockColorEvent {
      void register(BlockColor var1, Block... var2);

      int getColor(BlockState var1, BlockAndTintGetter var2, BlockPos var3, int var4);
   }

   public interface BlockEntityRendererEvent {
      <E extends BlockEntity> void register(BlockEntityType<? extends E> var1, BlockEntityRendererProvider<E> var2);
   }

   public interface BlockRendererEvent {
      void register(RenderType var1, Block... var2);

      void register(RenderType var1, Fluid... var2);
   }

   public interface EntityRendererEvent {
      <E extends Entity> void register(EntityType<? extends E> var1, EntityRendererProvider<E> var2);
   }

   public interface HandHeldModelEvent {
      default void register(Item item, ResourceLocation handHeld) {
         this.register(item, BuiltInRegistries.ITEM.getKey(item), handHeld);
      }

      default void register(Item item, ModelResourceLocation handHeld) {
         this.register(item, ModelResourceLocation.inventory(BuiltInRegistries.ITEM.getKey(item)), handHeld);
      }

      void register(Item var1, ModelResourceLocation var2, ModelResourceLocation var3, Set<ItemDisplayContext> var4);

      default void register(Item item, ResourceLocation original, ResourceLocation handheld, Set<ItemDisplayContext> perspectives) {
         this.register(item, ModelResourceLocation.inventory(original), ModelResourceLocation.inventory(handheld), perspectives);
      }

      default void register(Item item, ModelResourceLocation original, ModelResourceLocation handheld) {
         this.register(item, original, handheld, Set.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED));
      }

      default void register(Item item, ResourceLocation original, ResourceLocation handheld) {
         this.register(item, ModelResourceLocation.inventory(original), ModelResourceLocation.inventory(handheld));
      }

      record Models(ModelResourceLocation original, ModelResourceLocation handheld, Set<ItemDisplayContext> perspectives) {
      }
   }

   public interface ItemColorEvent {
      void register(ItemColor var1, ItemLike... var2);

      int getColor(ItemStack var1, int var2);
   }

   public interface ModelLayerEvent {
      void register(ModelLayerLocation var1, Supplier<LayerDefinition> var2);
   }

   public interface ModelOverrideEvent {
      default void register(ResourceLocation original, ResourceLocation override, boolean condition) {
         if (condition) {
            GameRendering.MODEL_OVERRIDES.put(original, override);
         } else {
            GameRendering.MODEL_OVERRIDES.remove(original);
         }
      }

      default void register(ResourceLocation original, ResourceLocation override) {
         this.register(original, override, true);
      }
   }

   public interface ParticleFactoryEvent {
      <T extends ParticleOptions, P extends ParticleType<T>> void register(Supplier<P> var1, ParticleProvider<T> var2);

      <T extends ParticleOptions, P extends ParticleType<T>> void register(Supplier<P> var1, GameRendering.ParticleFactoryEvent.Factory<T> var2);

      @FunctionalInterface
      interface Factory<T extends ParticleOptions> {
         @NotNull
         ParticleProvider<T> create(SpriteSet var1);
      }
   }

   public interface SkullRendererEvent {
      void registerSkullModel(Type var1, Function<ModelPart, SkullModelBase> var2, ModelLayerLocation var3);

      void registerSkullTexture(Type var1, ResourceLocation var2);
   }

   public interface SpecialModelEvent {
      void register(ResourceLocation var1);

      void register(ResourceLocation... var1);

      @Deprecated
      default void register(ModelResourceLocation model) {
         this.register(model.id());
      }

      @Deprecated
      default void register(ModelResourceLocation... models) {
         for (ModelResourceLocation model : models) {
            this.register(model.id());
         }
      }
   }
}
