package team.lodestar.lodestone.systems.attribute;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

public class LodestoneRangedAttribute extends RangedAttribute {
   private final ResourceLocation baseId;
   private final boolean forcePercentage;

   public static LodestoneAttributeBuilder create(ResourceLocation id, double defaultValue, double min, double max) {
      return new LodestoneAttributeBuilder(id, defaultValue, min, max);
   }

   protected LodestoneRangedAttribute(ResourceLocation id, ResourceLocation baseId, double defaultValue, double min, double max, boolean forcePercentage) {
      super("attribute.name." + id.getNamespace() + "." + id.getPath(), defaultValue, min, max);
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
         : super.toValueComponent(op, value, flag);
   }
}
