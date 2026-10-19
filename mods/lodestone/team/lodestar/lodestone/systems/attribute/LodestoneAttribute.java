package team.lodestar.lodestone.systems.attribute;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LodestoneAttribute extends Attribute {
   private final ResourceLocation baseId;
   private final boolean forcePercentage;

   public static LodestoneAttributeBuilder create(ResourceLocation id, double defaultValue) {
      return new LodestoneAttributeBuilder(id, defaultValue);
   }

   protected LodestoneAttribute(ResourceLocation id, ResourceLocation baseId, double defaultValue, boolean forcePercentage) {
      super("attribute.name." + id.getNamespace() + "." + id.getPath(), defaultValue);
      this.baseId = baseId;
      this.forcePercentage = forcePercentage;
   }

   @Nullable
   public ResourceLocation getBaseId() {
      return this.baseId;
   }

   @NotNull
   public MutableComponent toValueComponent(@Nullable Operation op, double value, TooltipFlag flag) {
      return this.forcePercentage
         ? Component.translatable("neoforge.value.percent", new Object[]{FORMAT.format(value * 100.0)})
         : Component.translatable("neoforge.value.flat", new Object[]{FORMAT.format(value)});
   }
}
