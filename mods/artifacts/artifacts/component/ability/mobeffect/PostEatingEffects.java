package artifacts.component.ability.mobeffect;

import artifacts.component.ability.CompositeAbility;
import artifacts.component.ability.EntityCondition;
import artifacts.component.ability.EquipmentAbility;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;

public record PostEatingEffects(List<PostEatingEffects.Entry> entries) implements CompositeAbility<PostEatingEffects.Entry> {
   public static final Codec<PostEatingEffects> CODEC = CompositeAbility.codec(
      PostEatingEffects.Entry.CODEC, PostEatingEffects::new, PostEatingEffects::entries
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, PostEatingEffects> STREAM_CODEC = CompositeAbility.streamCodec(
      PostEatingEffects.Entry.STREAM_CODEC, PostEatingEffects::new, PostEatingEffects::entries
   );

   public static void applyEffects(LivingEntity entity, FoodProperties properties) {
      int foodPointsMissing = entity instanceof Player player ? 20 - player.getFoodData().getFoodLevel() : 20;
      int foodPointsRestored = Math.min(properties.nutrition(), foodPointsMissing);
      applyEffects(entity, foodPointsRestored);
   }

   public static void applyEffects(LivingEntity entity, int foodPointsRestored) {
      if (foodPointsRestored > 0) {
         EquipmentHelper.iterateAbilities(ModDataComponents.POST_EATING_EFFECTS.get(), entity, true, true, (ability, stack) -> {
            for (PostEatingEffects.Entry entry : ability.entries) {
               entity.addEffect(entry.provider().createEffect(foodPointsRestored));
            }
         });
      }
   }

   public record Entry(MobEffectProvider provider) implements EquipmentAbility {
      public static final Codec<PostEatingEffects.Entry> CODEC = MobEffectProvider.codec(false)
         .xmap(PostEatingEffects.Entry::new, PostEatingEffects.Entry::provider);
      public static final StreamCodec<RegistryFriendlyByteBuf, PostEatingEffects.Entry> STREAM_CODEC = MobEffectProvider.STREAM_CODEC
         .map(PostEatingEffects.Entry::new, PostEatingEffects.Entry::provider);

      @Override
      public boolean isNonCosmetic() {
         return this.provider().isNonCosmetic();
      }

      @Override
      public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
         if (this.provider.mobEffect().equals(MobEffects.DIG_SPEED) && this.provider.condition() == EntityCondition.ALWAYS) {
            writer.add("haste");
         }
      }
   }
}
