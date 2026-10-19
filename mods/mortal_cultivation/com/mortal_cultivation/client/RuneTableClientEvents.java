package com.mortal_cultivation.client;

import com.mortal_cultivation.client.renderer.RuneTableRenderer;
import com.mortal_cultivation.init.BlockEntityRegistry;
import com.mortal_cultivation.registry.BlockRegistry;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
public final class RuneTableClientEvents {
   private static final IClientBlockExtensions PARTICLE_EXTENSIONS = new IClientBlockExtensions() {
      public boolean addDestroyEffects(BlockState state, Level level, BlockPos pos, ParticleEngine manager) {
         manager.destroy(pos, Blocks.ANDESITE.defaultBlockState());
         return true;
      }
   };

   private RuneTableClientEvents() {
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerBlock(PARTICLE_EXTENSIONS, new Block[]{(Block)BlockRegistry.RUNE_TABLE.get()});
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer((BlockEntityType)BlockEntityRegistry.RUNE_TABLE.get(), RuneTableRenderer::new);
   }
}
