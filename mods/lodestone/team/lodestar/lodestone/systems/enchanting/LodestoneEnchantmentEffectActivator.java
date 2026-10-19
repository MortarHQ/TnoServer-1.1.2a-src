package team.lodestar.lodestone.systems.enchanting;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.TargetedConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableFloat;

public class LodestoneEnchantmentEffectActivator<T> {
   private final Either<DataComponentType<List<ConditionalEffect<T>>>, DataComponentType<List<TargetedConditionalEffect<T>>>> componentType;
   private final ServerLevel level;
   private LodestoneEnchantmentEffectActivator.ContextSupplier contextSupplier;

   public static <T> LodestoneEnchantmentEffectActivator<T> createEffectActivator(
      DataComponentType<List<ConditionalEffect<T>>> componentType, ServerLevel level
   ) {
      return new LodestoneEnchantmentEffectActivator<>(Either.left(componentType), level);
   }

   public static <T> LodestoneEnchantmentEffectActivator<T> createTargetedEffectActivator(
      DataComponentType<List<TargetedConditionalEffect<T>>> componentType, ServerLevel level
   ) {
      return new LodestoneEnchantmentEffectActivator<>(Either.right(componentType), level);
   }

   private LodestoneEnchantmentEffectActivator(
      Either<DataComponentType<List<ConditionalEffect<T>>>, DataComponentType<List<TargetedConditionalEffect<T>>>> componentType, ServerLevel level
   ) {
      this.componentType = componentType;
      this.level = level;
   }

   public LodestoneEnchantmentEffectActivator<T> setItemContext(ItemStack enchantedItem) {
      return this.setContext(enchantmentLevel -> Enchantment.itemContext(this.level, enchantmentLevel, enchantedItem));
   }

   public LodestoneEnchantmentEffectActivator<T> setDamageContext(
      ServerLevel level, Entity attackedEntity, DamageSource damageSource, @Nullable ItemStack enchantedItem
   ) {
      return this.setContext(enchantmentLevel -> damageContext(level, enchantmentLevel, attackedEntity, damageSource, enchantedItem));
   }

   public LodestoneEnchantmentEffectActivator<T> setEntityContext(ServerLevel level, Entity entity, @Nullable ItemStack enchantedItem) {
      return this.setEntityContext(level, entity, entity.position(), enchantedItem);
   }

   public LodestoneEnchantmentEffectActivator<T> setEntityContext(ServerLevel level, Entity entity, Vec3 origin, @Nullable ItemStack enchantedItem) {
      return this.setContext(enchantmentLevel -> entityContext(level, enchantmentLevel, entity, origin, enchantedItem));
   }

   public LodestoneEnchantmentEffectActivator<T> setContext(LodestoneEnchantmentEffectActivator.ContextSupplier contextSupplier) {
      this.contextSupplier = contextSupplier;
      return this;
   }

   public void triggerEntityEffects(ItemStack enchantedItem, LivingEntity target) {
      this.triggerEntityEffects(enchantedItem, target, target);
   }

   public void triggerEntityEffects(ItemStack enchantedItem, LivingEntity enchanted, Entity target) {
      this.triggerEntityEffects(new EnchantedItemInUse(enchantedItem, null, enchanted, item -> {}), target);
   }

   public void triggerEntityEffects(EnchantedItemInUse enchantedItem, Entity target) {
      this.applyEffects((effect, entity, enchantmentLevel) -> {
         if (effect instanceof EnchantmentEntityEffect entityEffect) {
            entityEffect.apply(this.level, enchantmentLevel, enchantedItem, entity, entity.position());
         }
      }, enchantedItem.itemStack(), target);
   }

   public float countValue(ItemStack enchantedItem, Entity target) {
      return this.modifyValue(enchantedItem, target, 0.0F);
   }

   public float modifyValue(ItemStack enchantedItem, Entity target, float baseValue) {
      MutableFloat value = new MutableFloat(baseValue);
      this.applyEffects((effect, entity, enchantmentLevel) -> {
         if (effect instanceof EnchantmentValueEffect valueEffect) {
            value.setValue(valueEffect.process(enchantmentLevel, this.level.getRandom(), value.getValue()));
         }
      }, enchantedItem, target);
      return value.getValue();
   }

   public float countValue(LivingEntity target) {
      return this.modifyValue(target, 0.0F);
   }

   public float modifyValue(LivingEntity target, float baseValue) {
      MutableFloat value = new MutableFloat(baseValue);
      this.applyEffects((effect, entity, enchantmentLevel) -> {
         if (effect instanceof EnchantmentValueEffect valueEffect) {
            value.setValue(valueEffect.process(enchantmentLevel, this.level.getRandom(), value.getValue()));
         }
      }, target);
      return value.getValue();
   }

   public boolean hasEffect(ItemStack enchantedItem, Entity target) {
      AtomicBoolean hasEffect = new AtomicBoolean(false);
      this.applyEffects((effect, entity, enchantmentLevel) -> hasEffect.set(true), enchantedItem, target);
      return hasEffect.get();
   }

   public void applyEffects(LodestoneEnchantmentEffectActivator.EnchantmentEffectAcceptor<T> acceptor, ItemStack enchantedItem, Entity target) {
      if (this.contextSupplier == null) {
         throw new IllegalStateException("Context is not set");
      }

      EnchantmentHelper.runIterationOnItem(enchantedItem, (enchantment, enchantmentLevel) -> {
         LootContext context = this.contextSupplier.getContext(enchantmentLevel);
         this.componentType.ifLeft(componentType -> {
            for (ConditionalEffect<T> effect : ((Enchantment)enchantment.value()).getEffects(componentType)) {
               if (effect.matches(context)) {
                  acceptor.apply((T)effect.effect(), target, enchantmentLevel);
               }
            }
         });
         this.componentType.ifRight(componentType -> {
            for (TargetedConditionalEffect<T> effect : ((Enchantment)enchantment.value()).getEffects(componentType)) {
               if (effect.matches(context)) {
                  DamageSource source = (DamageSource)context.getParam(LootContextParams.DAMAGE_SOURCE);

                  Entity entity = switch (effect.affected()) {
                     case ATTACKER -> source.getEntity();
                     case DAMAGING_ENTITY -> source.getDirectEntity();
                     case VICTIM -> target;
                     default -> throw new MatchException(null, null);
                  };
                  if (entity != null) {
                     acceptor.apply((T)effect.effect(), entity, enchantmentLevel);
                  }
               }
            }
         });
      });
   }

   public void applyEffects(LodestoneEnchantmentEffectActivator.EnchantmentEffectAcceptor<T> acceptor, LivingEntity target) {
      if (this.contextSupplier == null) {
         throw new IllegalStateException("Context is not set");
      }

      EnchantmentHelper.runIterationOnEquipment(target, (enchantment, enchantmentLevel, enchantedItem) -> {
         LootContext context = this.contextSupplier.getContext(enchantmentLevel);
         this.componentType.ifLeft(componentType -> {
            for (ConditionalEffect<T> effect : ((Enchantment)enchantment.value()).getEffects(componentType)) {
               if (effect.matches(context)) {
                  acceptor.apply((T)effect.effect(), target, enchantmentLevel);
               }
            }
         });
         this.componentType.ifRight(componentType -> {
            for (TargetedConditionalEffect<T> effect : ((Enchantment)enchantment.value()).getEffects(componentType)) {
               if (effect.matches(context)) {
                  DamageSource source = (DamageSource)context.getParam(LootContextParams.DAMAGE_SOURCE);

                  Entity entity = (Entity)(switch (effect.affected()) {
                     case ATTACKER -> source.getEntity();
                     case DAMAGING_ENTITY -> source.getDirectEntity();
                     case VICTIM -> target;
                     default -> throw new MatchException(null, null);
                  });
                  if (entity != null) {
                     acceptor.apply((T)effect.effect(), entity, enchantmentLevel);
                  }
               }
            }
         });
      });
   }

   public static LootContext entityContext(ServerLevel level, int enchantmentLevel, Entity entity, Vec3 origin, @Nullable ItemStack tool) {
      LootParams lootparams = new Builder(level)
         .withParameter(LootContextParams.THIS_ENTITY, entity)
         .withParameter(LootContextParams.ENCHANTMENT_LEVEL, enchantmentLevel)
         .withParameter(LootContextParams.ORIGIN, origin)
         .withOptionalParameter(LootContextParams.TOOL, tool)
         .create(LootContextParamSets.ENCHANTED_ENTITY);
      return new net.minecraft.world.level.storage.loot.LootContext.Builder(lootparams).create(Optional.empty());
   }

   public static LootContext damageContext(ServerLevel level, int enchantmentLevel, Entity entity, DamageSource damageSource, @Nullable ItemStack tool) {
      LootParams lootparams = new Builder(level)
         .withParameter(LootContextParams.THIS_ENTITY, entity)
         .withParameter(LootContextParams.ENCHANTMENT_LEVEL, enchantmentLevel)
         .withParameter(LootContextParams.ORIGIN, entity.position())
         .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
         .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
         .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity())
         .withOptionalParameter(LootContextParams.TOOL, tool)
         .create(LootContextParamSets.ENCHANTED_DAMAGE);
      return new net.minecraft.world.level.storage.loot.LootContext.Builder(lootparams).create(Optional.empty());
   }

   public interface ContextSupplier {
      LootContext getContext(int var1);
   }

   public interface EnchantmentEffectAcceptor<T> {
      default void apply(T effect, int enchantmentLevel) {
         this.apply(effect, null, enchantmentLevel);
      }

      void apply(T var1, Entity var2, int var3);
   }
}
