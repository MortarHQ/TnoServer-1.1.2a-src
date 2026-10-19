package com.cerbon.bosses_of_mass_destruction.block;

import com.cerbon.bosses_of_mass_destruction.block.custom.LevitationBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.MobWardBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.MonolithBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidBlossomSummonBlockEntity;
import com.cerbon.bosses_of_mass_destruction.block.custom.VoidLilyBlockEntity;
import com.cerbon.bosses_of_mass_destruction.client.render.BMDBlockEntityRenderer;
import com.cerbon.bosses_of_mass_destruction.entity.GeoModel;
import com.cerbon.cerbons_api.api.registry.RegistryEntry;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistries;
import com.cerbon.cerbons_api.api.registry.ResourcefulRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BMDBlockEntities {
   public static final ResourcefulRegistry<BlockEntityType<?>> BLOCKS_ENTITIES = ResourcefulRegistries.create(
      BuiltInRegistries.BLOCK_ENTITY_TYPE, "bosses_of_mass_destruction"
   );
   public static final RegistryEntry<BlockEntityType<MobWardBlockEntity>> MOB_WARD = BLOCKS_ENTITIES.register(
      "mob_ward", () -> Builder.of(MobWardBlockEntity::new, new Block[]{(Block)BMDBlocks.MOB_WARD.get()}).build(null)
   );
   public static final RegistryEntry<BlockEntityType<MonolithBlockEntity>> MONOLITH_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "monolith_block", () -> Builder.of(MonolithBlockEntity::new, new Block[]{(Block)BMDBlocks.MONOLITH_BLOCK.get()}).build(null)
   );
   public static final RegistryEntry<BlockEntityType<LevitationBlockEntity>> LEVITATION_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "levitation_block", () -> Builder.of(LevitationBlockEntity::new, new Block[]{(Block)BMDBlocks.LEVITATION_BLOCK.get()}).build(null)
   );
   public static final RegistryEntry<BlockEntityType<VoidBlossomSummonBlockEntity>> VOID_BLOSSOM_SUMMON_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "void_blossom_block", () -> Builder.of(VoidBlossomSummonBlockEntity::new, new Block[]{(Block)BMDBlocks.VOID_BLOSSOM_SUMMON_BLOCK.get()}).build(null)
   );
   public static final RegistryEntry<BlockEntityType<VoidLilyBlockEntity>> VOID_LILY_BLOCK_ENTITY = BLOCKS_ENTITIES.register(
      "void_lily", () -> Builder.of(VoidLilyBlockEntity::new, new Block[]{(Block)BMDBlocks.VOID_LILY_BLOCK.get()}).build(null)
   );

   @OnlyIn(Dist.CLIENT)
   public static void initClient() {
      BlockEntityRenderers.register(
         (BlockEntityType)LEVITATION_BLOCK_ENTITY.get(),
         context -> new BMDBlockEntityRenderer(
            new GeoModel(
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "geo/levitation_block.geo.json"),
               entity -> ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/block/levitation_block.png"),
               ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "animations/levitation_block.animation.json"),
               (animatable, data, geoModel) -> {},
               RenderType::entityCutout
            ),
            (bone, packedLight) -> 15728880
         )
      );
   }

   public static void register() {
      BLOCKS_ENTITIES.register();
   }
}
