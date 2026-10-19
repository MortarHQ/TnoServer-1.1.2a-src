package artifacts.component.ability;

import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;

public interface EquipmentAbility {
   boolean isNonCosmetic();

   default void addToTooltip(EquipmentAbility.TooltipWriter writer) {
      writer.addDefaultTooltipKey();
   }

   class TooltipWriter {
      private final Consumer<Component> tooltip;
      private final String namespace;
      private final String path;
      private final TooltipContext context;
      private final ItemStack stack;

      public TooltipWriter(DataComponentType<? extends EquipmentAbility> type, Consumer<Component> tooltip, TooltipContext context, ItemStack stack) {
         this.tooltip = tooltip;
         ResourceLocation id = Objects.requireNonNull(BuiltInRegistries.DATA_COMPONENT_TYPE.getKey(type));
         this.namespace = id.getNamespace();
         this.path = id.getPath();
         this.context = context;
         this.stack = stack;
      }

      public TooltipContext context() {
         return this.context;
      }

      public ItemStack stack() {
         return this.stack;
      }

      public EquipmentAbility.TooltipWriter add(String identifier, Object... args) {
         return this.addRaw(
            Component.translatable("%s.tooltip.ability.%s.%s".formatted(this.namespace, this.path, identifier), args).withStyle(ChatFormatting.GRAY)
         );
      }

      public EquipmentAbility.TooltipWriter addDefaultTooltipKey(Object... args) {
         return this.addRaw(Component.translatable("%s.tooltip.ability.%s".formatted(this.namespace, this.path), args).withStyle(ChatFormatting.GRAY));
      }

      public EquipmentAbility.TooltipWriter addRaw(Component component) {
         this.tooltip.accept(component);
         return this;
      }
   }
}
