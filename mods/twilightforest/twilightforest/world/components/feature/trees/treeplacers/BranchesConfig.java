package twilightforest.world.components.feature.trees.treeplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record BranchesConfig(
   BlockStateProvider branchProvider, int branchCount, int randomAddBranches, double length, double randomAddLength, double spacingYaw, double downwardsPitch
) {
   public static final Codec<BranchesConfig> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("branch_provider").forGetter(p -> p.branchProvider),
            Codec.intRange(0, 16).fieldOf("count_minimum").forGetter(o -> o.branchCount),
            Codec.intRange(0, 16).fieldOf("random_add_count").orElse(0).forGetter(o -> o.randomAddBranches),
            Codec.doubleRange(1.0, 24.0).fieldOf("length").forGetter(o -> o.length),
            Codec.doubleRange(0.0, 16.0).fieldOf("random_add_length").orElse(0.0).forGetter(o -> o.randomAddLength),
            Codec.doubleRange(0.0, 0.5).fieldOf("spacing_yaw").orElse(0.3).forGetter(o -> o.spacingYaw),
            Codec.doubleRange(0.0, 1.0).fieldOf("downwards_pitch").orElse(0.2).forGetter(o -> o.downwardsPitch)
         )
         .apply(instance, BranchesConfig::new)
   );
}
