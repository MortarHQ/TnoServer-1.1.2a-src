package noobanidus.mods.lootr.common.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.PotDecorations;

public record PotDecorationsAdapter(ItemStack back, ItemStack left, ItemStack right, ItemStack front) {
   public static final PotDecorationsAdapter EMPTY = new PotDecorationsAdapter(PotDecorations.EMPTY);
   public static final Codec<PotDecorationsAdapter> CODEC = ItemStack.CODEC
      .sizeLimitedListOf(4)
      .xmap(PotDecorationsAdapter::new, PotDecorationsAdapter::ordered);

   public PotDecorationsAdapter(PotDecorationsAdapter decorations) {
      this(decorations.back().copy(), decorations.left().copy(), decorations.right().copy(), decorations.front().copy());
   }

   public PotDecorationsAdapter(PotDecorations decorations) {
      this(
         decorations.back().<ItemStack>map(ItemStack::new).orElse(ItemStack.EMPTY),
         decorations.left().<ItemStack>map(ItemStack::new).orElse(ItemStack.EMPTY),
         decorations.right().<ItemStack>map(ItemStack::new).orElse(ItemStack.EMPTY),
         decorations.front().<ItemStack>map(ItemStack::new).orElse(ItemStack.EMPTY)
      );
   }

   public PotDecorationsAdapter(List<ItemStack> itemStacks) {
      this(itemStacks.get(0), itemStacks.get(1), itemStacks.get(2), itemStacks.get(3));
   }

   public List<ItemStack> ordered() {
      return Stream.of(this.back, this.left, this.right, this.front).map(o -> o.isEmpty() ? new ItemStack(Items.BRICK) : o).toList();
   }

   public PotDecorationsAdapter load(CompoundTag tag) {
      return !tag.contains("decorations")
         ? EMPTY
         : (PotDecorationsAdapter)((Pair)CODEC.decode(NbtOps.INSTANCE, tag.get("decorations")).getOrThrow()).getFirst();
   }

   public CompoundTag save(CompoundTag tag) {
      if (this != EMPTY) {
         tag.put("decorations", (Tag)CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
      }

      return tag;
   }
}
