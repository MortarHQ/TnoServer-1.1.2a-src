package com.blamejared.crafttweaker.api.block;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.CommandStringDisplayable;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.Many;
import com.blamejared.crafttweaker.natives.block.ExpandBlock;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.block.BlockIngredient")
@Document("vanilla/api/block/BlockIngredient")
public abstract class CTBlockIngredient implements CommandStringDisplayable {
   @Override
   public abstract String getCommandString();

   public abstract boolean matches(Block var1);

   public abstract boolean matches(Block var1, int var2);

   public abstract boolean matches(BlockState var1);

   public abstract boolean matches(BlockState var1, int var2);

   public abstract boolean matches(TagKey<Block> var1);

   public abstract boolean matches(TagKey<Block> var1, int var2);

   public abstract <T> T mapTo(Function<Block, T> var1, Function<BlockState, T> var2, BiFunction<TagKey<Block>, Integer, T> var3, Function<Stream<T>, T> var4);

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   public CTBlockIngredient asCompound(CTBlockIngredient other) {
      List<CTBlockIngredient> ingredients = new ArrayList<>();
      if (other instanceof CTBlockIngredient.CompoundBlockIngredient) {
         ingredients.addAll(((CTBlockIngredient.CompoundBlockIngredient)other).elements);
      } else {
         ingredients.add(other);
      }

      if (this instanceof CTBlockIngredient.CompoundBlockIngredient) {
         ((CTBlockIngredient.CompoundBlockIngredient)this).elements.addAll(ingredients);
         return this;
      } else {
         ingredients.add(this);
         return new CTBlockIngredient.CompoundBlockIngredient(ingredients);
      }
   }

   public static final class BlockIngredient extends CTBlockIngredient {
      final Block block;

      public BlockIngredient(Block block) {
         this.block = block;
      }

      @Override
      public String getCommandString() {
         return ExpandBlock.getCommandString(this.block);
      }

      @Override
      public boolean matches(Block block) {
         return this.block == block;
      }

      @Override
      public boolean matches(Block block, int amount) {
         return this.matches(block);
      }

      @Override
      public boolean matches(BlockState blockState) {
         return this.block.defaultBlockState() == blockState;
      }

      @Override
      public boolean matches(BlockState blockState, int amount) {
         return this.matches(blockState);
      }

      @Override
      public boolean matches(TagKey<Block> tag) {
         return this.block.builtInRegistryHolder().is(tag);
      }

      @Override
      public boolean matches(TagKey<Block> tag, int amount) {
         return this.matches(tag);
      }

      @Override
      public <T> T mapTo(
         Function<Block, T> blockMapper,
         Function<BlockState, T> blockStateMapper,
         BiFunction<TagKey<Block>, Integer, T> tagMapper,
         Function<Stream<T>, T> compoundMapper
      ) {
         return blockMapper.apply(this.block);
      }
   }

   public static final class BlockStateIngredient extends CTBlockIngredient {
      final BlockState blockState;

      public BlockStateIngredient(BlockState blockState) {
         this.blockState = blockState;
      }

      @Override
      public String getCommandString() {
         return ExpandBlockState.getCommandString(this.blockState);
      }

      @Override
      public boolean matches(Block block) {
         return this.blockState.getBlock() == block;
      }

      @Override
      public boolean matches(Block block, int amount) {
         return this.matches(block);
      }

      @Override
      public boolean matches(BlockState blockState) {
         return this.blockState == blockState;
      }

      @Override
      public boolean matches(BlockState blockState, int amount) {
         return this.matches(blockState);
      }

      @Override
      public boolean matches(TagKey<Block> tag) {
         return this.blockState.is(tag);
      }

      @Override
      public boolean matches(TagKey<Block> tag, int amount) {
         return this.matches(tag);
      }

      @Override
      public <T> T mapTo(
         Function<Block, T> blockMapper,
         Function<BlockState, T> blockStateMapper,
         BiFunction<TagKey<Block>, Integer, T> tagMapper,
         Function<Stream<T>, T> compoundMapper
      ) {
         return blockStateMapper.apply(this.blockState);
      }
   }

   public static final class BlockTagWithAmountIngredient extends CTBlockIngredient {
      final Many<KnownTag<Block>> tag;

      public BlockTagWithAmountIngredient(Many<KnownTag<Block>> tag) {
         this.tag = tag;
      }

      @Override
      public String getCommandString() {
         return this.tag.getCommandString();
      }

      @Override
      public boolean matches(Block block) {
         return this.tag.getData().contains(block);
      }

      @Override
      public boolean matches(Block block, int amount) {
         return this.tag.getData().contains(block) && this.tag.getAmount() <= amount;
      }

      @Override
      public boolean matches(BlockState blockState) {
         return blockState.is(this.tag.getData().getTagKey());
      }

      @Override
      public boolean matches(BlockState blockState, int amount) {
         return blockState.is(this.tag.getData().getTagKey()) && this.tag.getAmount() <= amount;
      }

      @Override
      public boolean matches(TagKey<Block> tag) {
         return this.tag.getData().getTagKey().equals(tag);
      }

      @Override
      public boolean matches(TagKey<Block> tag, int amount) {
         return this.tag.getData().getTagKey().equals(tag) && this.tag.getAmount() <= amount;
      }

      @Override
      public <T> T mapTo(
         Function<Block, T> blockMapper,
         Function<BlockState, T> blockStateMapper,
         BiFunction<TagKey<Block>, Integer, T> tagMapper,
         Function<Stream<T>, T> compoundMapper
      ) {
         return tagMapper.apply(this.tag.getData().getTagKey(), this.tag.getAmount());
      }
   }

   public static final class CompoundBlockIngredient extends CTBlockIngredient {
      final List<CTBlockIngredient> elements;

      public CompoundBlockIngredient(List<CTBlockIngredient> elements) {
         this.elements = elements;
      }

      @Override
      public String getCommandString() {
         return this.elements.stream().map(CTBlockIngredient::getCommandString).collect(Collectors.joining(" | "));
      }

      @Override
      public boolean matches(Block block) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(block));
      }

      @Override
      public boolean matches(Block block, int amount) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(block, amount));
      }

      @Override
      public boolean matches(BlockState blockState) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(blockState));
      }

      @Override
      public boolean matches(BlockState blockState, int amount) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(blockState, amount));
      }

      @Override
      public boolean matches(TagKey<Block> tag) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(tag));
      }

      @Override
      public boolean matches(TagKey<Block> tag, int amount) {
         return this.elements.stream().anyMatch(ingredient -> ingredient.matches(tag, amount));
      }

      @Override
      public <T> T mapTo(
         Function<Block, T> blockMapper,
         Function<BlockState, T> blockStateMapper,
         BiFunction<TagKey<Block>, Integer, T> tagMapper,
         Function<Stream<T>, T> compoundMapper
      ) {
         Stream<T> stream = this.elements.stream().map(element -> element.mapTo(blockMapper, blockStateMapper, tagMapper, compoundMapper));
         return compoundMapper.apply(stream);
      }
   }
}
