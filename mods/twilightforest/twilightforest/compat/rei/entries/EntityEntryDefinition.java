package twilightforest.compat.rei.entries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.util.entities.EntityRenderingUtil;

public class EntityEntryDefinition implements EntryDefinition<Entity>, EntrySerializer<Entity> {
   public static EntryType<Entity> ENTITY_TYPE = EntryType.deferred(TwilightForestMod.prefix("entity"));
   private final EntryRenderer<Entity> renderer = new EntityEntryDefinition.EntityRenderer();

   public Class<Entity> getValueType() {
      return Entity.class;
   }

   public EntryType<Entity> getType() {
      return ENTITY_TYPE;
   }

   public EntryRenderer<Entity> getRenderer() {
      return this.renderer;
   }

   @Nullable
   public ResourceLocation getIdentifier(EntryStack<Entity> entry, Entity value) {
      return BuiltInRegistries.ENTITY_TYPE.getKey(value.getType());
   }

   public boolean isEmpty(EntryStack<Entity> entry, Entity value) {
      return false;
   }

   public Entity copy(EntryStack<Entity> entry, Entity value) {
      CompoundTag tag = new CompoundTag();
      String string = value.getEncodeId();
      if (string != null) {
         tag.putString("id", string);
         value.saveWithoutId(tag);
      }

      Entity entity = value.getType().create(Minecraft.getInstance().level);
      entity.load(tag);
      return entity;
   }

   public Entity normalize(EntryStack<Entity> entry, Entity value) {
      return this.copy(entry, value);
   }

   public Entity wildcard(EntryStack<Entity> entry, Entity value) {
      return value.getType().create(Minecraft.getInstance().level);
   }

   @Nullable
   public ItemStack cheatsAs(EntryStack<Entity> entry, Entity value) {
      SpawnEggItem egg = DeferredSpawnEggItem.byId(value.getType());
      return egg != null ? new ItemStack(egg) : super.cheatsAs(entry, value);
   }

   @Nullable
   public Entity add(Entity o1, Entity o2) {
      CompoundTag tag = new CompoundTag();
      o1.save(tag);
      o2.save(tag);
      Entity copy = o1.getType().create(Minecraft.getInstance().level);
      copy.load(tag);
      return copy;
   }

   public long hash(EntryStack<Entity> entry, Entity value, ComparisonContext context) {
      int code = 1;
      code = 31 * code + System.identityHashCode(value);
      code = 31 * code + Long.hashCode(EntityComparatorImpl.INSTANCE.hashOf(context, value));
      return code;
   }

   public boolean equals(Entity o1, Entity o2, ComparisonContext context) {
      return o1 != o2 ? false : EntityComparatorImpl.INSTANCE.hashOf(context, o1) == EntityComparatorImpl.INSTANCE.hashOf(context, o2);
   }

   @Nullable
   public EntrySerializer<Entity> getSerializer() {
      return null;
   }

   public boolean supportSaving() {
      return true;
   }

   public boolean supportReading() {
      return true;
   }

   public CompoundTag save(EntryStack<Entity> entry, Entity value) {
      CompoundTag tag = new CompoundTag();
      String string = value.getEncodeId();
      if (string != null) {
         tag.putString("id", string);
         value.saveWithoutId(tag);
      }

      return tag;
   }

   public Entity read(CompoundTag tag) {
      return (Entity)EntityType.create(tag, Minecraft.getInstance().level).get();
   }

   public Component asFormattedText(EntryStack<Entity> entry, Entity value) {
      return this.asFormattedText(entry, value, TooltipContext.of(net.minecraft.world.item.Item.TooltipContext.EMPTY));
   }

   public Component asFormattedText(EntryStack<Entity> entry, Entity value, TooltipContext context) {
      return value.getType().getDescription();
   }

   public Stream<? extends TagKey<?>> getTagsFor(EntryStack<Entity> entry, Entity value) {
      return value.getType().builtInRegistryHolder().tags();
   }

   public static class EntityRenderer implements EntryRenderer<Entity> {
      public void render(EntryStack<Entity> entry, GuiGraphics graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
         if (!entry.isEmpty()) {
            graphics.pose().pushPose();
            graphics.pose().translate(bounds.getX(), bounds.getY(), -100.0);
            EntityRenderingUtil.renderEntity(graphics, ((Entity)entry.getValue()).getType(), 32);
            graphics.pose().popPose();
         }
      }

      @Nullable
      public Tooltip getTooltip(EntryStack<Entity> entry, TooltipContext context) {
         if (entry.isEmpty()) {
            return null;
         }

         Tooltip tooltip = Tooltip.create(new Component[0]);
         EntityRenderingUtil.getMobTooltip(((Entity)entry.getValue()).getType()).forEach(tooltip::add);
         return tooltip;
      }
   }

   public static class ItemEntityRenderer implements EntryRenderer<Entity> {
      private final float bobOffs = RandomSource.create().nextFloat() * (float) Math.PI * 2.0F;

      public void render(EntryStack<Entity> entry, GuiGraphics graphics, Rectangle bounds, int mouseX, int mouseY, float delta) {
         ItemStack item = ((ItemEntity)entry.getValue()).getItem();
         if (!entry.isEmpty()) {
            Level level = Minecraft.getInstance().level;
            graphics.pose().pushPose();
            graphics.pose().translate(bounds.getX(), bounds.getY(), 0.0F);
            if (level != null) {
               try {
                  EntityRenderingUtil.renderItemEntity(graphics, item, level, this.bobOffs);
               } catch (Exception e) {
                  TwilightForestMod.LOGGER.error("Error drawing item in REI!", e);
               }
            }

            graphics.pose().popPose();
         }
      }

      @Nullable
      public Tooltip getTooltip(EntryStack<Entity> entry, TooltipContext context) {
         ItemStack item = ((ItemEntity)entry.getValue()).getItem();
         List<Component> tooltip = new ArrayList<>();
         tooltip.add(item.getItem().getDescription());
         if (context.getFlag().isAdvanced()) {
            tooltip.add(Component.literal(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.getItem())).toString()).withStyle(ChatFormatting.DARK_GRAY));
         }

         return Tooltip.create(context.getPoint(), tooltip);
      }
   }
}
