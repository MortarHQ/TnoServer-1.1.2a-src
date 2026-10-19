package com.notunanancyowen.spears;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.notunanancyowen.spears.components.AttackRange;
import com.notunanancyowen.spears.components.KineticWeapon;
import com.notunanancyowen.spears.components.PiercingWeapon;
import com.notunanancyowen.spears.components.SwingAnimation;
import com.notunanancyowen.spears.components.UseEffects;
import com.notunanancyowen.spears.criteria.SpearedMobs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentType.Builder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Spears {
   public static final String MOD_ID = "spears";
   public static final Logger LOGGER = LoggerFactory.getLogger("spears");
   public static DataComponentType<UseEffects> USE_EFFECTS;
   public static DataComponentType<AttackRange> ATTACK_RANGE;
   public static DataComponentType<KineticWeapon> KINETIC_WEAPON;
   public static DataComponentType<SwingAnimation> SWING_ANIMATION;
   public static DataComponentType<PiercingWeapon> PIERCING_WEAPON;
   public static DataComponentType<Float> MINIMUM_ATTACK_CHARGE;
   public static SoundEvent SPEAR_ATTACK;
   public static SoundEvent SPEAR_HIT;
   public static SoundEvent SPEAR_USE;
   public static SoundEvent SPEAR_WOOD_ATTACK;
   public static SoundEvent SPEAR_WOOD_HIT;
   public static SoundEvent SPEAR_WOOD_USE;
   public static SoundEvent SPEAR_LUNGE;
   public static final TagKey<Item> SPEARS = TagKey.create(Registries.ITEM, ResourceLocation.withDefaultNamespace("spears"));
   public static final TagKey<Item> CAN_LUNGE_WHILE_GLIDING = TagKey.create(
      Registries.ITEM, ResourceLocation.fromNamespaceAndPath("spears", "can_lunge_while_gliding")
   );
   public static Item WOODEN_SPEAR;
   public static Item STONE_SPEAR;
   public static Item GOLDEN_SPEAR;
   public static Item IRON_SPEAR;
   public static Item DIAMOND_SPEAR;
   public static Item NETHERITE_SPEAR;
   public static Item COPPER_SPEAR = null;
   public static Item ELECTRUM_SPEAR = null;
   public static Item DRAGON_SPEAR = null;
   public static Item ENDERITE_SPEAR = null;
   public static DataComponentType<List<ConditionalEffect<EnchantmentEntityEffect>>> POST_PIERCING_ATTACK;
   public static SpearedMobs SPEARED_MOBS;
   public static HashMap<String, Boolean> config;
   public static boolean hasBetterCombat = false;

   public static Collection<EntityHitResult> collectPiercingCollisions(
      LivingEntity entity, float minReach, float maxReach, float hitboxMargin, Predicate<Entity> hitPredicate
   ) {
      Vec3 vec3d = entity.calculateViewVector(entity.getXRot(), entity.getYHeadRot());
      Vec3 vec3d2 = entity.getEyePosition();
      Vec3 vec3d3 = vec3d2.add(vec3d.scale(minReach));
      double d = entity.getKnownMovement().dot(vec3d);
      Vec3 vec3d4 = vec3d2.add(vec3d.scale(maxReach + Math.max(0.0, d)));
      return (Collection<EntityHitResult>)collectPiercingCollisions(entity, vec3d2, vec3d3, hitPredicate, vec3d4, hitboxMargin, Block.COLLIDER)
         .map(hitResult -> List.of(), hitResults -> hitResults);
   }

   private static Either<BlockHitResult, Collection<EntityHitResult>> collectPiercingCollisions(
      Entity entity, Vec3 pos, Vec3 minReach, Predicate<Entity> hitPredicate, Vec3 maxReach, float hitboxMargin, Block shapeType
   ) {
      Level world = entity.getCommandSenderWorld();
      BlockHitResult blockHitResult = getCollisionsIncludingWorldBorder(world, new ClipContext(pos, maxReach, shapeType, Fluid.NONE, entity));
      if (blockHitResult.getType() != Type.MISS) {
         maxReach = blockHitResult.getLocation();
         if (pos.distanceToSqr(maxReach) < pos.distanceToSqr(minReach)) {
            return Either.left(blockHitResult);
         }
      }

      AABB box = AABB.ofSize(minReach, hitboxMargin, hitboxMargin, hitboxMargin).expandTowards(maxReach.subtract(minReach)).inflate(1.0);
      Collection<EntityHitResult> collection = collectPiercingCollisions(world, entity, minReach, maxReach, box, hitPredicate, hitboxMargin, shapeType, true);
      return !collection.isEmpty() ? Either.right(collection) : Either.left(blockHitResult);
   }

   public static Collection<EntityHitResult> collectPiercingCollisions(
      Level world, Entity entity, Vec3 from, Vec3 to, AABB box, Predicate<Entity> hitPredicate, float hitboxMargin, Block shapeType, boolean bl
   ) {
      List<EntityHitResult> list = new ArrayList<>();

      for (Entity entity2 : world.getEntities(entity, box, hitPredicate)) {
         AABB box2 = entity2.getBoundingBox();
         if (bl && box2.contains(from)) {
            list.add(new EntityHitResult(entity2, from));
         } else {
            Optional<Vec3> optional = box2.clip(from, to);
            if (optional.isPresent()) {
               list.add(new EntityHitResult(entity2, optional.get()));
            } else if (!(hitboxMargin <= 0.0)) {
               Optional<Vec3> optional2 = box2.inflate(hitboxMargin).clip(from, to);
               if (optional2.isPresent()) {
                  Vec3 vec3d = optional2.get();
                  Vec3 vec3d2 = box2.getCenter();
                  BlockHitResult blockHitResult = getCollisionsIncludingWorldBorder(world, new ClipContext(vec3d, vec3d2, shapeType, Fluid.NONE, entity));
                  if (blockHitResult.getType() != Type.MISS) {
                     vec3d2 = blockHitResult.getLocation();
                  }

                  Optional<Vec3> optional3 = entity2.getBoundingBox().clip(vec3d, vec3d2);
                  optional3.ifPresent(d -> list.add(new EntityHitResult(entity2, d)));
               }
            }
         }
      }

      return list;
   }

   private static BlockHitResult getCollisionsIncludingWorldBorder(Level world, ClipContext context) {
      BlockHitResult blockHitResult = world.clip(context);
      WorldBorder worldBorder = world.getWorldBorder();
      if (worldBorder.isWithinBounds(context.getFrom()) && !worldBorder.isWithinBounds(blockHitResult.getLocation())) {
         Vec3 vec3d = blockHitResult.getLocation().subtract(context.getFrom());
         Direction direction = Direction.getNearest(vec3d.x, vec3d.y, vec3d.z);
         Vec3 vec3d2 = blockHitResult.getLocation();
         vec3d2 = new Vec3(
            Mth.clamp(vec3d2.x, worldBorder.getMinX(), worldBorder.getMaxX() - 1.0E-5F),
            vec3d2.y,
            Mth.clamp(vec3d2.z, worldBorder.getMinZ(), worldBorder.getMaxZ() - 1.0E-5F)
         );
         return new BlockHitResult(vec3d2, direction, BlockPos.containing(vec3d2), false);
      } else {
         return blockHitResult;
      }
   }

   public static Codec<Float> rangedInclusiveFloat(float minInclusive, float maxInclusive, Function<Float, String> messageFactory) {
      return Codec.FLOAT
         .validate(
            value -> value.compareTo(minInclusive) >= 0 && value.compareTo(maxInclusive) <= 0
               ? DataResult.success(value)
               : DataResult.error(() -> messageFactory.apply(value))
         );
   }

   public static Codec<Float> rangedInclusiveFloat(float minInclusive, float maxInclusive) {
      return rangedInclusiveFloat(minInclusive, maxInclusive, value -> "Value must be within range [" + minInclusive + ";" + maxInclusive + "]: " + value);
   }

   public static <T> DataComponentType<T> registerComponent(String id, UnaryOperator<Builder<T>> builderOperator) {
      return (DataComponentType<T>)Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, builderOperator.apply(DataComponentType.builder()).build());
   }

   public static Holder<SoundEvent> registerSound(String id) {
      ResourceLocation identifier = ResourceLocation.withDefaultNamespace(id);
      return Registry.registerForHolder(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
   }

   public static Item tryLoadCopperSpear(boolean shouldLoad, boolean neoForge) {
      if (shouldLoad && neoForge) {
         return COPPER_SPEAR = registerSpearRaw(new Tier() {
            public int getUses() {
               return 190;
            }

            public float getSpeed() {
               return 5.0F;
            }

            public float getAttackDamageBonus() {
               return 1.0F;
            }

            public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
               return BlockTags.INCORRECT_FOR_STONE_TOOL;
            }

            public int getEnchantmentValue() {
               return 13;
            }

            public Ingredient getRepairIngredient() {
               return Ingredient.of(new ItemLike[]{Items.COPPER_INGOT});
            }
         }, 0.85F, 0.82F, 0.65F, 4.0F, 9.0F, 5.0F, 5.1F, 12.5F, 4.6F);
      }

      if (shouldLoad) {
         return COPPER_SPEAR = registerSpear("copper_spear", new Tier() {
            public int getUses() {
               return 190;
            }

            public float getSpeed() {
               return 5.0F;
            }

            public float getAttackDamageBonus() {
               return 1.0F;
            }

            public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
               return BlockTags.INCORRECT_FOR_STONE_TOOL;
            }

            public int getEnchantmentValue() {
               return 13;
            }

            public Ingredient getRepairIngredient() {
               return Ingredient.of(new ItemLike[]{Items.COPPER_INGOT});
            }
         }, 0.85F, 0.82F, 0.65F, 4.0F, 9.0F, 5.0F, 5.1F, 12.5F, 4.6F);
      }

      COPPER_SPEAR = null;
      return null;
   }

   public static Item tryLoadElectrumSpear(boolean shouldLoad, boolean neoForge) {
      if (shouldLoad && neoForge) {
         return ELECTRUM_SPEAR = registerSpearRaw(
            new Tier() {
               public int getUses() {
                  return 1561;
               }

               public float getSpeed() {
                  return 8.0F;
               }

               public float getAttackDamageBonus() {
                  return 3.0F;
               }

               public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
                  return BlockTags.INCORRECT_FOR_IRON_TOOL;
               }

               public int getEnchantmentValue() {
                  return 14;
               }

               public Ingredient getRepairIngredient() {
                  return Ingredient.of(
                     new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_ingot"))}
                  );
               }
            },
            1.05F,
            1.075F,
            0.5F,
            3.0F,
            7.5F,
            4.0F,
            5.1F,
            10.0F,
            4.6F
         );
      }

      if (shouldLoad) {
         return ELECTRUM_SPEAR = registerSpear(
            ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_spear"),
            new Tier() {
               public int getUses() {
                  return 1561;
               }

               public float getSpeed() {
                  return 8.0F;
               }

               public float getAttackDamageBonus() {
                  return 3.0F;
               }

               public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
                  return BlockTags.INCORRECT_FOR_IRON_TOOL;
               }

               public int getEnchantmentValue() {
                  return 14;
               }

               public Ingredient getRepairIngredient() {
                  return Ingredient.of(
                     new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_ingot"))}
                  );
               }
            },
            1.05F,
            1.075F,
            0.5F,
            3.0F,
            7.5F,
            4.0F,
            5.1F,
            10.0F,
            4.6F
         );
      }

      ELECTRUM_SPEAR = null;
      return null;
   }

   public static Item tryLoadDragonSpear(boolean shouldLoad, boolean neoForge, String id) {
      if (shouldLoad && neoForge) {
         return DRAGON_SPEAR = registerSpearRaw(new Tier() {
            public int getUses() {
               return 2785;
            }

            public float getSpeed() {
               return 11.0F;
            }

            public float getAttackDamageBonus() {
               return 5.0F;
            }

            public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
               return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
               return 20;
            }

            public Ingredient getRepairIngredient() {
               return Ingredient.of(new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(id, "dragon_scale"))});
            }
         }, 1.2F, 1.25F, 0.3F, 2.0F, 6.5F, 3.0F, 5.1F, 8.75F, 4.6F);
      }

      if (shouldLoad) {
         return DRAGON_SPEAR = registerSpear(ResourceLocation.fromNamespaceAndPath(id, "dragon_spear"), new Tier() {
            public int getUses() {
               return 2785;
            }

            public float getSpeed() {
               return 11.0F;
            }

            public float getAttackDamageBonus() {
               return 5.0F;
            }

            public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
               return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
            }

            public int getEnchantmentValue() {
               return 20;
            }

            public Ingredient getRepairIngredient() {
               return Ingredient.of(new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(id, "dragon_scale"))});
            }
         }, 1.2F, 1.25F, 0.3F, 2.0F, 6.5F, 3.0F, 5.1F, 8.75F, 4.6F);
      }

      DRAGON_SPEAR = null;
      return null;
   }

   public static Item tryLoadEnderiteSpear(boolean shouldLoad, boolean neoForge) {
      if (shouldLoad && neoForge) {
         return ENDERITE_SPEAR = registerSpearRaw(
            new Tier() {
               public int getUses() {
                  return 4096;
               }

               public float getSpeed() {
                  return 15.0F;
               }

               public float getAttackDamageBonus() {
                  return 5.0F;
               }

               public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
                  return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("enderitemod", "incorrect_for_enderite_tool"));
               }

               public int getEnchantmentValue() {
                  return 17;
               }

               public Ingredient getRepairIngredient() {
                  return Ingredient.of(
                     new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_ingot"))}
                  );
               }
            },
            1.2F,
            1.25F,
            0.3F,
            2.0F,
            6.5F,
            3.0F,
            5.1F,
            8.75F,
            4.6F
         );
      }

      if (shouldLoad) {
         return ENDERITE_SPEAR = registerSpear(
            ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_spear"),
            new Tier() {
               public int getUses() {
                  return 4096;
               }

               public float getSpeed() {
                  return 15.0F;
               }

               public float getAttackDamageBonus() {
                  return 5.0F;
               }

               public TagKey<net.minecraft.world.level.block.Block> getIncorrectBlocksForDrops() {
                  return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("enderitemod", "incorrect_for_enderite_tool"));
               }

               public int getEnchantmentValue() {
                  return 17;
               }

               public Ingredient getRepairIngredient() {
                  return Ingredient.of(
                     new ItemLike[]{(ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_ingot"))}
                  );
               }
            },
            1.2F,
            1.25F,
            0.3F,
            2.0F,
            6.5F,
            3.0F,
            5.1F,
            8.75F,
            4.6F
         );
      }

      ENDERITE_SPEAR = null;
      return null;
   }

   public static Item registerSpear(
      String id,
      Tier material,
      float swingAnimationSeconds,
      float chargeDamageMultiplier,
      float chargeDelaySeconds,
      float maxDurationForDismountSeconds,
      float minSpeedForDismount,
      float maxDurationForChargeKnockbackInSeconds,
      float minSpeedForChargeKnockback,
      float maxDurationForChargeDamageInSeconds,
      float minRelativeSpeedForChargeDamage
   ) {
      return (Item)Registry.register(
         BuiltInRegistries.ITEM,
         id,
         registerSpearRaw(
            material,
            swingAnimationSeconds,
            chargeDamageMultiplier,
            chargeDelaySeconds,
            maxDurationForDismountSeconds,
            minSpeedForDismount,
            maxDurationForChargeKnockbackInSeconds,
            minSpeedForChargeKnockback,
            maxDurationForChargeDamageInSeconds,
            minRelativeSpeedForChargeDamage
         )
      );
   }

   public static Item registerSpear(
      ResourceLocation id,
      Tier material,
      float swingAnimationSeconds,
      float chargeDamageMultiplier,
      float chargeDelaySeconds,
      float maxDurationForDismountSeconds,
      float minSpeedForDismount,
      float maxDurationForChargeKnockbackInSeconds,
      float minSpeedForChargeKnockback,
      float maxDurationForChargeDamageInSeconds,
      float minRelativeSpeedForChargeDamage
   ) {
      return (Item)Registry.register(
         BuiltInRegistries.ITEM,
         id,
         registerSpearRaw(
            material,
            swingAnimationSeconds,
            chargeDamageMultiplier,
            chargeDelaySeconds,
            maxDurationForDismountSeconds,
            minSpeedForDismount,
            maxDurationForChargeKnockbackInSeconds,
            minSpeedForChargeKnockback,
            maxDurationForChargeDamageInSeconds,
            minRelativeSpeedForChargeDamage
         )
      );
   }

   public static Item registerSpearRaw(
      Tier material,
      float swingAnimationSeconds,
      float chargeDamageMultiplier,
      float chargeDelaySeconds,
      float maxDurationForDismountSeconds,
      float minSpeedForDismount,
      float maxDurationForChargeKnockbackInSeconds,
      float minSpeedForChargeKnockback,
      float maxDurationForChargeDamageInSeconds,
      float minRelativeSpeedForChargeDamage
   ) {
      boolean wood = material == Tiers.WOOD;
      net.minecraft.world.item.component.ItemAttributeModifiers.Builder attributes = ItemAttributeModifiers.builder()
         .add(
            Attributes.ATTACK_DAMAGE,
            new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, material.getAttackDamageBonus(), Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         )
         .add(
            Attributes.ATTACK_SPEED,
            new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, 1.0F / swingAnimationSeconds - 4.0, Operation.ADD_VALUE),
            EquipmentSlotGroup.MAINHAND
         );
      if (material.getUses() == 1561) {
         try {
            if (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("oreganized", "electrum_ingot")) instanceof Item item
               && material.getRepairIngredient().test(item.getDefaultInstance())) {
               attributes.add(
                  BuiltInRegistries.ATTRIBUTE
                     .wrapAsHolder((Attribute)BuiltInRegistries.ATTRIBUTE.get(ResourceLocation.fromNamespaceAndPath("oreganized", "kinetic_damage"))),
                  new AttributeModifier(ResourceLocation.fromNamespaceAndPath("oreganized", "kinetic_damage"), 2.0, Operation.ADD_VALUE),
                  EquipmentSlotGroup.MAINHAND
               );
            }
         } catch (Throwable var17) {
         }
      }

      Properties components = new Properties()
         .component(USE_EFFECTS, new UseEffects(1.0F, true, false))
         .attributes(attributes.build())
         .component(MINIMUM_ATTACK_CHARGE, 1.0F)
         .component(ATTACK_RANGE, new AttackRange(2.0F, 4.5F));
      if (material.getUses() == 2785) {
         try {
            if (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("dragonloot", "dragon_scale")) instanceof Item item
               && material.getRepairIngredient().test(item.getDefaultInstance())) {
               components.fireResistant();
            }

            if (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("ender_dragon_loot", "dragon_scale")) instanceof Item item
               && material.getRepairIngredient().test(item.getDefaultInstance())) {
               components.fireResistant();
            }
         } catch (Throwable var16) {
         }
      } else if (material.getUses() == 4096) {
         try {
            if (BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("enderitemod", "enderite_ingot")) instanceof Item item
               && material.getRepairIngredient().test(item.getDefaultInstance())) {
               components.fireResistant();
            }
         } catch (Throwable var15) {
         }
      }

      HashMap<String, Boolean> config = makeConfig();
      boolean chargeAttacksEnabled = config.getOrDefault("spear_charge_attacks", true);
      if (config.getOrDefault("spear_stabbing_animation", true)) {
         components.component(SWING_ANIMATION, new SwingAnimation((int)(swingAnimationSeconds * 20.0F), "stab"))
            .component(
               PIERCING_WEAPON,
               new PiercingWeapon(
                  0.25F,
                  true,
                  false,
                  Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(wood ? SPEAR_WOOD_ATTACK : SPEAR_ATTACK)),
                  Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(wood ? SPEAR_WOOD_HIT : SPEAR_HIT))
               )
            );
      }

      if (chargeAttacksEnabled) {
         components.component(
            KINETIC_WEAPON,
            new KineticWeapon(
               0.125F,
               10,
               (int)(chargeDelaySeconds * 20.0F),
               KineticWeapon.Condition.ofMinSpeed((int)(maxDurationForDismountSeconds * 20.0F), minSpeedForDismount),
               KineticWeapon.Condition.ofMinSpeed((int)(maxDurationForChargeKnockbackInSeconds * 20.0F), minSpeedForChargeKnockback),
               KineticWeapon.Condition.ofMinRelativeSpeed((int)(maxDurationForChargeDamageInSeconds * 20.0F), minRelativeSpeedForChargeDamage),
               0.38F,
               chargeDamageMultiplier,
               Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(wood ? SPEAR_WOOD_USE : SPEAR_USE)),
               Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(wood ? SPEAR_WOOD_HIT : SPEAR_HIT))
            )
         );
      }

      if (material == Tiers.NETHERITE) {
         components.fireResistant();
      }

      return chargeAttacksEnabled ? new TieredItem(material, components) {
         public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
            if (selected) {
               stack.setEntityRepresentation(entity);
            } else {
               stack.setEntityRepresentation(null);
            }

            super.inventoryTick(stack, world, entity, slot, selected);
         }

         public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
            return false;
         }

         public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            return true;
         }

         public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
         }

         public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(user.getItemInHand(hand));
         }

         public int getUseDuration(ItemStack stack, LivingEntity user) {
            return 72000;
         }
      } : new TieredItem(material, components) {
         public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
            if (selected) {
               stack.setEntityRepresentation(entity);
            } else {
               stack.setEntityRepresentation(null);
            }

            super.inventoryTick(stack, world, entity, slot, selected);
         }

         public boolean canAttackBlock(BlockState state, Level world, BlockPos pos, Player miner) {
            return false;
         }

         public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            return true;
         }

         public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
            stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
         }
      };
   }

   public static <T> DataComponentType<T> registerEffect(String id, UnaryOperator<Builder<T>> builderOperator) {
      return (DataComponentType<T>)Registry.register(
         BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, id, builderOperator.apply(DataComponentType.builder()).build()
      );
   }

   public static <T extends CriterionTrigger<?>> T registerCriterion(String id, T criterion) {
      return (T)Registry.register(BuiltInRegistries.TRIGGER_TYPES, id, criterion);
   }

   public static HashMap<String, Boolean> makeConfig() {
      if (config == null) {
         config = new HashMap<>();
      }

      return config;
   }

   public static void init() {
      LOGGER.info("Backported spears!");
   }
}
