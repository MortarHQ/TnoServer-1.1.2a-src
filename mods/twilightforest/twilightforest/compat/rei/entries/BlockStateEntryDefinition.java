package twilightforest.compat.rei.entries;

import java.util.Objects;
import java.util.stream.Stream;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.compat.RecipeViewerConstants;

public class BlockStateEntryDefinition implements EntryDefinition<BlockState>, EntrySerializer<BlockState> {
   public static EntryType<BlockState> BLOCKSTATE = EntryType.deferred(TwilightForestMod.prefix("blockstate"));
   private final EntryRenderer<BlockState> renderer = new BlockStateEntryDefinition.FlatBlockRenderer();

   public Class<BlockState> getValueType() {
      return BlockState.class;
   }

   public EntryType<BlockState> getType() {
      return BLOCKSTATE;
   }

   public EntryRenderer<BlockState> getRenderer() {
      return this.renderer;
   }

   @Nullable
   public ResourceLocation getIdentifier(EntryStack<BlockState> entry, BlockState value) {
      return BuiltInRegistries.BLOCK.getKey(value.getBlock());
   }

   public boolean isEmpty(EntryStack<BlockState> entry, BlockState value) {
      return value.isEmpty();
   }

   public BlockState copy(EntryStack<BlockState> entry, BlockState value) {
      return value;
   }

   public BlockState normalize(EntryStack<BlockState> entry, BlockState value) {
      return value;
   }

   public BlockState wildcard(EntryStack<BlockState> entry, BlockState value) {
      return value.getBlock().defaultBlockState();
   }

   @Nullable
   public ItemStack cheatsAs(EntryStack<BlockState> entry, BlockState value) {
      return new ItemStack(value.getBlock().asItem());
   }

   @Nullable
   public BlockState add(BlockState o1, BlockState o2) {
      return o1;
   }

   public long hash(EntryStack<BlockState> entry, BlockState value, ComparisonContext context) {
      return Objects.hash(value.getBlock(), value.getProperties());
   }

   public boolean equals(BlockState o1, BlockState o2, ComparisonContext context) {
      return o1.getBlock() != o2.getBlock() ? false : o1.getProperties().equals(o2.getProperties());
   }

   @Nullable
   public EntrySerializer<BlockState> getSerializer() {
      return this;
   }

   public Component asFormattedText(EntryStack<BlockState> entry, BlockState value) {
      return Component.literal(value.toString());
   }

   public Stream<? extends TagKey<?>> getTagsFor(EntryStack<BlockState> entry, BlockState value) {
      return value.getTags();
   }

   public boolean supportSaving() {
      return true;
   }

   public boolean supportReading() {
      return true;
   }

   public boolean acceptsNull() {
      return false;
   }

   public CompoundTag save(EntryStack<BlockState> entry, BlockState value) {
      return NbtUtils.writeBlockState(value);
   }

   public BlockState read(CompoundTag tag) {
      return NbtUtils.readBlockState(BasicDisplay.registryAccess().registryOrThrow(Registries.BLOCK).asLookup(), tag);
   }

   public static class FlatBlockRenderer implements EntryRenderer<BlockState> {
      public void render(EntryStack<BlockState> entry, GuiGraphics graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
         RecipeViewerConstants.renderFlatBlock(graphics.pose(), (BlockState)entry.getValue(), new Vec3(bounds.getX(), bounds.getY(), 201.0), 20.0F);
      }

      @Nullable
      public Tooltip getTooltip(EntryStack<BlockState> entry, TooltipContext context) {
         return Tooltip.create(new ItemStack(((BlockState)entry.getValue()).getBlock()).getTooltipLines(context.vanillaContext(), null, context.getFlag()));
      }
   }
}
