package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.util.ModCodecs;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public record FluidCollision(Value<Boolean> enabled, Optional<TagKey<Fluid>> tag, EntityCondition condition) implements TickingAbility {
   public static Codec<FluidCollision> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ValueTypes.enabledField().forGetter(FluidCollision::enabled),
            TagKey.codec(Registries.FLUID).optionalFieldOf("tag").forGetter(FluidCollision::tag),
            EntityCondition.CODEC.optionalFieldOf("condition", EntityCondition.ALWAYS).forGetter(FluidCollision::condition)
         )
         .apply(instance, FluidCollision::new)
   );
   public static StreamCodec<ByteBuf, FluidCollision> STREAM_CODEC = StreamCodec.composite(
      ValueTypes.BOOLEAN.streamCodec(),
      FluidCollision::enabled,
      ByteBufCodecs.optional(ModCodecs.tagKeyStreamCodec(Registries.FLUID)),
      FluidCollision::tag,
      EntityCondition.STREAM_CODEC,
      FluidCollision::condition,
      FluidCollision::new
   );

   @Override
   public void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isDisabled) {
      FluidState fluidState = entity.getBlockStateOn().getFluidState();
      if (fluidState.is(FluidTags.LAVA) && !entity.fireImmune() && this.condition.test(entity)) {
         entity.hurt(entity.damageSources().hotFloor(), 1.0F);
      }
   }

   @Override
   public boolean isNonCosmetic() {
      return this.enabled().get();
   }

   public boolean matchesFluid(FluidState fluidState) {
      return this.tag().isEmpty() || fluidState.is(this.tag().get());
   }

   @Override
   public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
      if (this.condition == EntityCondition.SNEAKING && this.tag().isPresent() && this.tag().get().equals(FluidTags.LAVA)) {
         writer.add("sneaking.lava");
      } else if (this.condition == EntityCondition.SPRINTING && this.tag().isEmpty()) {
         writer.add("sprinting");
      }
   }
}
