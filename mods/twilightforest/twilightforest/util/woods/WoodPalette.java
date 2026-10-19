package twilightforest.util.woods;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import twilightforest.util.features.FeaturePlacers;

public class WoodPalette {
   public static final Codec<WoodPalette> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("planks").forGetter(p -> p.planks),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("stairs").forGetter(p -> p.stairs),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("slab").forGetter(p -> p.slab),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("button").forGetter(p -> p.button),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("fence").forGetter(p -> p.fence),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("gate").forGetter(p -> p.gate),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("plate").forGetter(p -> p.plate),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("banister").forGetter(p -> p.banister)
         )
         .apply(instance, WoodPalette::new)
   );
   private final Set<Block> blocks;
   private final Block planks;
   private final Block stairs;
   private final Block slab;
   private final Block button;
   private final Block fence;
   private final Block gate;
   private final Block plate;
   private final Block banister;

   public WoodPalette(
      Holder<Block> planks,
      Holder<Block> stairs,
      Holder<Block> slab,
      Holder<Block> button,
      Holder<Block> fence,
      Holder<Block> gate,
      Holder<Block> plate,
      Holder<Block> banister
   ) {
      this(
         (Block)planks.value(),
         (Block)stairs.value(),
         (Block)slab.value(),
         (Block)button.value(),
         (Block)fence.value(),
         (Block)gate.value(),
         (Block)plate.value(),
         (Block)banister.value()
      );
   }

   public WoodPalette(Block planks, Block stairs, Block slab, Block button, Block fence, Block gate, Block plate, Block banister) {
      this.planks = planks;
      this.stairs = stairs;
      this.slab = slab;
      this.button = button;
      this.fence = fence;
      this.gate = gate;
      this.plate = plate;
      this.banister = banister;
      this.blocks = new HashSet<>(List.of(this.planks, this.stairs, this.slab, this.button, this.fence, this.gate, this.plate, this.banister));
   }

   public boolean contains(Block block) {
      return this.blocks.contains(block);
   }

   public StructureBlockInfo modifyBlockWithType(WoodPalette targetPalette, StructureBlockInfo state) {
      BlockState newState = state.state();
      return targetPalette.contains(newState.getBlock())
         ? new StructureBlockInfo(state.pos(), this.modifyBlockWithType(targetPalette, newState), state.nbt())
         : state;
   }

   public BlockState modifyBlockWithType(WoodPalette targetPalette, BlockState state) {
      return switch (targetPalette.getWoodShapeFromBlock(state.getBlock())) {
         case BLOCK -> this.planks.defaultBlockState();
         case STAIRS -> FeaturePlacers.transferAllStateKeys(state, this.stairs);
         case SLAB -> FeaturePlacers.transferAllStateKeys(state, this.slab);
         case BUTTON -> FeaturePlacers.transferAllStateKeys(state, this.button);
         case FENCE -> FeaturePlacers.transferAllStateKeys(state, this.fence);
         case GATE -> FeaturePlacers.transferAllStateKeys(state, this.gate);
         case PLATE -> FeaturePlacers.transferAllStateKeys(state, this.plate);
         case BANISTER -> FeaturePlacers.transferAllStateKeys(state, this.banister);
         default -> state;
      };
   }

   public WoodPalette.WoodShapes getWoodShapeFromBlock(Block b) {
      if (b == this.planks) {
         return WoodPalette.WoodShapes.BLOCK;
      } else if (b == this.stairs) {
         return WoodPalette.WoodShapes.STAIRS;
      } else if (b == this.slab) {
         return WoodPalette.WoodShapes.SLAB;
      } else if (b == this.button) {
         return WoodPalette.WoodShapes.BUTTON;
      } else if (b == this.fence) {
         return WoodPalette.WoodShapes.FENCE;
      } else if (b == this.gate) {
         return WoodPalette.WoodShapes.GATE;
      } else if (b == this.plate) {
         return WoodPalette.WoodShapes.PLATE;
      } else {
         return b == this.banister ? WoodPalette.WoodShapes.BANISTER : WoodPalette.WoodShapes.INVALID;
      }
   }

   public enum WoodShapes {
      BLOCK,
      STAIRS,
      SLAB,
      BUTTON,
      FENCE,
      GATE,
      PLATE,
      BANISTER,
      INVALID;
   }
}
