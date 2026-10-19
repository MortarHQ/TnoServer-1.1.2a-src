package net.jadenxgamer.netherexp.registry;

import java.util.function.Supplier;
import net.jadenxgamer.netherexp.core.block.entity.BrazierChestBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.CiergeOfTreacheryBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.DiscernmentGlassBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.DriftingSoulsBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.JNECampfireBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.PetrifiedSwirlsBlockEntity;
import net.jadenxgamer.netherexp.core.block.entity.SuspiciousSoulSandBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class JNEBlockEntityType {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "netherexp");
   public static final Supplier<BlockEntityType<SuspiciousSoulSandBlockEntity>> SUSPICIOUS_SOUL_SAND = BLOCK_ENTITY_TYPES.register(
      "suspicious_soul_sand", () -> Builder.of(SuspiciousSoulSandBlockEntity::new, new Block[]{JNEBlocks.SUSPICIOUS_SOUL_SAND.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<DiscernmentGlassBlockEntity>> DISCERNMENT_GLASS = BLOCK_ENTITY_TYPES.register(
      "discernment_glass", () -> Builder.of(DiscernmentGlassBlockEntity::new, new Block[]{JNEBlocks.DISCERNMENT_GLASS.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<JNECampfireBlockEntity>> JNE_CAMPFIRE = BLOCK_ENTITY_TYPES.register(
      "jne_campfire", () -> Builder.of(JNECampfireBlockEntity::new, new Block[]{JNEBlocks.TREACHEROUS_CAMPFIRE.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<DriftingSoulsBlockEntity>> DRIFTING_SOULS = BLOCK_ENTITY_TYPES.register(
      "drifting_souls", () -> Builder.of(DriftingSoulsBlockEntity::new, new Block[]{JNEBlocks.DRIFTING_SOULS.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<PetrifiedSwirlsBlockEntity>> PETRIFIED_SWIRLS = BLOCK_ENTITY_TYPES.register(
      "petrified_swirls", () -> Builder.of(PetrifiedSwirlsBlockEntity::new, new Block[]{JNEBlocks.PETRIFIED_SWIRLS.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<CiergeOfTreacheryBlockEntity>> CIERGE_OF_TREACHERY = BLOCK_ENTITY_TYPES.register(
      "cierge_of_treachery", () -> Builder.of(CiergeOfTreacheryBlockEntity::new, new Block[]{JNEBlocks.CIERGE_OF_TREACHERY.get()}).build(null)
   );
   public static final Supplier<BlockEntityType<BrazierChestBlockEntity>> BRAZIER_CHEST = BLOCK_ENTITY_TYPES.register(
      "brazier_chest", () -> Builder.of(BrazierChestBlockEntity::new, new Block[]{JNEBlocks.BRAZIER_CHEST.get()}).build(null)
   );

   public static void init(IEventBus eventBus) {
      BLOCK_ENTITY_TYPES.register(eventBus);
   }
}
