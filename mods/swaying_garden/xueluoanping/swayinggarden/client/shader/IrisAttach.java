package xueluoanping.swayinggarden.client.shader;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.ResourceLocationException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.commands.arguments.blocks.BlockStateParser.BlockResult;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import xueluoanping.swayinggarden.config.General;
import xueluoanping.swayinggarden.util.TagUtil;

public class IrisAttach {
   private static Set<BlockState> blackList = null;

   public static void reload(@Nullable Object2IntMap<BlockState> blockStateIds, @Nullable String shaderpack) {
      if (shaderpack == null) {
         shaderpack = "";
      }

      if (blockStateIds != null) {
         blackList = new HashSet<>(getBlocks((List<? extends String>)General.blackList.get()));
         copyCustom(blockStateIds, shaderpack);
         copy(
            blockStateIds,
            shaderpack,
            (List<? extends String>)General.doubleBlockPlantsTops.get(),
            (BlockState)Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER)
         );
         copy(
            blockStateIds,
            shaderpack,
            (List<? extends String>)General.doubleBlockPlantsBottoms.get(),
            (BlockState)Blocks.TALL_GRASS.defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER)
         );
         copy(blockStateIds, shaderpack, (List<? extends String>)General.vineLike.get(), Blocks.VINE.defaultBlockState());
         copy(blockStateIds, shaderpack, (List<? extends String>)General.leavesLike.get(), Blocks.OAK_LEAVES.defaultBlockState());
         copy(blockStateIds, shaderpack, (List<? extends String>)General.padLike.get(), Blocks.LILY_PAD.defaultBlockState());
         copy(blockStateIds, shaderpack, (List<? extends String>)General.wheatLike.get(), Blocks.WHEAT.defaultBlockState());
         copy(blockStateIds, shaderpack, (List<? extends String>)General.grassLike.get(), Blocks.SHORT_GRASS.defaultBlockState());
         copy(blockStateIds, shaderpack, (List<? extends String>)General.saplingLike.get(), Blocks.OAK_SAPLING.defaultBlockState());
      }

      blackList = null;
   }

   public static void copyCustom(@NotNull Object2IntMap<BlockState> blockStateIds, @NotNull String shaderpack) {
      for (String string : (List)General.customLike.get()) {
         String[] split = string.split("@");
         if (split.length == 2) {
            BlockState blockState = parseBlock(split[0]);
            if (blockState != null) {
               copy(blockStateIds, shaderpack, List.of(split[1]), blockState);
            }
         }
      }
   }

   public static List<BlockState> getBlocks(List<? extends String> strings) {
      List<BlockState> blocks = new ArrayList<>();

      for (String string : strings) {
         try {
            List<BlockState> blocks2 = new ArrayList<>();
            boolean checkState = string.contains("%");
            String[] split = string.split("%");
            if (checkState) {
               string = split[0];
            }

            if (string.startsWith("!")) {
               String classStringSplit = string.split("!")[1];

               try {
                  Class<?> aClass = Class.forName(classStringSplit);

                  for (Entry<ResourceKey<Block>, Block> blockEntry : BuiltInRegistries.BLOCK.entrySet()) {
                     if (blockEntry.getValue().getClass().equals(aClass)) {
                        blocks2.addAll(blockEntry.getValue().getStateDefinition().getPossibleStates());
                     }
                  }
               } catch (Exception var17) {
               }
            } else if (string.startsWith("~")) {
               String classStringSplit = string.split("~")[1];

               try {
                  Class<?> aClass = Class.forName(classStringSplit);

                  for (Entry<ResourceKey<Block>, Block> blockEntry : BuiltInRegistries.BLOCK.entrySet()) {
                     if (aClass.isAssignableFrom(blockEntry.getValue().getClass())) {
                        blocks2.addAll(blockEntry.getValue().getStateDefinition().getPossibleStates());
                     }
                  }
               } catch (Exception var16) {
               }
            } else if (string.startsWith("#")) {
               string = string.split("#")[1];
               TagKey<Block> blockTagKey = TagUtil.create(string);
               Optional<Named<Block>> tag = BuiltInRegistries.BLOCK.getTag(blockTagKey);
               tag.ifPresent(c -> c.stream().forEach(d -> blocks2.addAll(((Block)d.value()).getStateDefinition().getPossibleStates())));
            } else {
               Pattern pattern = Pattern.compile(string);

               for (Entry<ResourceKey<Block>, Block> blockEntry : BuiltInRegistries.BLOCK.entrySet()) {
                  if (pattern.matcher(blockEntry.getKey().location().toString()).matches()) {
                     blocks2.addAll(blockEntry.getValue().getStateDefinition().getPossibleStates());
                  }
               }
            }

            if (checkState) {
               String[] split1 = split[1].split(":");
               List<Pair<Pattern, Pattern>> pairs = new ArrayList<>();
               if (split1.length % 2 == 0) {
                  for (int i = 0; i < split1.length / 2; i++) {
                     try {
                        pairs.add(Pair.of(Pattern.compile(split1[i * 2]), Pattern.compile(split1[i * 2 + 1])));
                     } catch (PatternSyntaxException var15) {
                     }
                  }
               }

               for (Pair<Pattern, Pattern> pair : pairs) {
                  for (int i = 0; i < blocks2.size(); i++) {
                     BlockState blockState = blocks2.get(i);

                     for (Property<?> property : blockState.getValues().keySet()) {
                        if (((Pattern)pair.first()).matcher(property.getName()).matches()
                           && !((Pattern)pair.second()).matcher(blockState.getValue(property).toString()).matches()) {
                           blocks2.remove(i);
                           i--;
                        }
                     }
                  }
               }
            }

            blocks.addAll(blocks2);
         } catch (PatternSyntaxException | ResourceLocationException e) {
         }
      }

      return blocks;
   }

   public static BlockState parseBlock(String s) {
      try {
         BlockResult blockstateparser$blockresult = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), s, true);
         return blockstateparser$blockresult.blockState();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public static void copyWheat(@Nonnull Object2IntMap<BlockState> blockStateIds, BlockState base, @Nonnull String shader) {
      BlockState blockState = Blocks.WHEAT.defaultBlockState();

      for (BlockState block1 : getBlocks((List<? extends String>)General.wheatLike.get())) {
         copy(blockStateIds, blockState, block1);
      }
   }

   private static void copy(Object2IntMap<BlockState> blockStateIds, String shaderpack, List<? extends String> strings, BlockState blockState) {
      for (BlockState block1 : getBlocks(strings)) {
         copy(blockStateIds, blockState, block1);
      }
   }

   public static void copy(@Nonnull Object2IntMap<BlockState> blockStateIds, BlockState state, Block block) {
      int idsInt = blockStateIds.getInt(state);
      if (idsInt != blockStateIds.defaultReturnValue()) {
         block.getStateDefinition().getPossibleStates().forEach(b -> {
            if (!blackList.contains(state)) {
               blockStateIds.putIfAbsent(b, idsInt);
            }
         });
      }
   }

   public static void copy(@Nonnull Object2IntMap<BlockState> blockStateIds, BlockState state, BlockState aim) {
      int idsInt = blockStateIds.getInt(state);
      if (idsInt != blockStateIds.defaultReturnValue() && !blackList.contains(state)) {
         blockStateIds.putIfAbsent(aim, idsInt);
      }
   }
}
