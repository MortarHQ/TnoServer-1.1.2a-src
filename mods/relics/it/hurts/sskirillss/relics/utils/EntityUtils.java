package it.hurts.sskirillss.relics.utils;

import com.google.common.collect.Lists;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

public class EntityUtils {
   public static void moveTowardsPosition(Entity entity, Vec3 targetPos, double speed) {
      Vec3 motion = targetPos.subtract(entity.position()).normalize().scale(speed);
      entity.setDeltaMovement(motion.x, motion.y, motion.z);
   }

   public static int getSlotWithItem(Player player, Item item) {
      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         if (player.getInventory().getItem(i).getItem() == item) {
            return i;
         }
      }

      return -1;
   }

   public static List<Integer> getSlotsWithItem(Player player, Item item) {
      List<Integer> list = Lists.newArrayList();

      for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
         if (player.getInventory().getItem(i).getItem() == item) {
            list.add(i);
            if (i == player.getInventory().getContainerSize()) {
               return list;
            }
         }
      }

      return list;
   }

   public static void addItem(Player player, ItemStack stack) {
      if (!player.addItem(stack)) {
         Level level = player.getCommandSenderWorld();
         RandomSource random = level.getRandom();
         ItemEntity drop = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), stack);
         drop.setDeltaMovement(MathUtils.randomFloat(random) * 0.15F, 0.1F + random.nextFloat() * 0.2F, MathUtils.randomFloat(random) * 0.15F);
         drop.setPickUpDelay(20);
         level.addFreshEntity(drop);
      }
   }

   public static EntityHitResult rayTraceEntity(Entity shooter, Predicate<? super Entity> filter, double distance) {
      Level world = shooter.level();
      Vec3 startVec = shooter.getEyePosition(1.0F);
      Vec3 endVec = shooter.getEyePosition(1.0F).add(shooter.getViewVector(1.0F).scale(distance));
      double d0 = distance * distance;
      Entity entity = null;
      Vec3 vector3d = null;

      for (Entity entity1 : world.getEntities(
         shooter, shooter.getBoundingBox().expandTowards(shooter.getViewVector(1.0F).scale(distance * distance)).inflate(1.0), filter
      )) {
         AABB axisalignedbb = entity1.getBoundingBox().inflate(entity1.getPickRadius());
         Optional<Vec3> optional = axisalignedbb.clip(startVec, endVec);
         if (axisalignedbb.contains(startVec)) {
            if (d0 >= 0.0) {
               entity = entity1;
               vector3d = optional.orElse(startVec);
               d0 = 0.0;
            }
         } else if (optional.isPresent()) {
            Vec3 vector3d1 = optional.get();
            double d1 = startVec.distanceToSqr(vector3d1);
            if (d1 < d0 || d0 == 0.0) {
               if (entity1.getRootVehicle() != shooter.getRootVehicle() || entity1.canRiderInteract()) {
                  entity = entity1;
                  vector3d = vector3d1;
                  d0 = d1;
               } else if (d0 == 0.0) {
                  entity = entity1;
                  vector3d = vector3d1;
               }
            }
         }
      }

      return entity == null ? null : new EntityHitResult(entity, vector3d);
   }

   private static ResourceLocation getAttributeId(ItemStack stack, Attribute attribute) {
      return ResourceLocation.fromNamespaceAndPath(
         "relics", BuiltInRegistries.ITEM.getKey(stack.getItem()).getPath() + "_" + BuiltInRegistries.ATTRIBUTE.getKey(attribute).getPath()
      );
   }

   public static void applyAttribute(LivingEntity entity, ItemStack stack, Holder<Attribute> attributeHolder, float value, Operation operation) {
      Attribute attribute = (Attribute)attributeHolder.value();
      ResourceLocation id = getAttributeId(stack, attribute);
      AttributeInstance instance = entity.getAttribute(attributeHolder);
      if (instance != null && !instance.hasModifier(id)) {
         instance.addTransientModifier(new AttributeModifier(id, value, operation));
      }
   }

   public static void removeAttribute(LivingEntity entity, ItemStack stack, Holder<Attribute> attributeHolder, Operation operation) {
      Attribute attribute = (Attribute)attributeHolder.value();
      ResourceLocation id = getAttributeId(stack, attribute);
      AttributeInstance instance = entity.getAttribute(attributeHolder);
      if (instance != null) {
         if (instance.hasModifier(id)) {
            instance.removeModifier(new AttributeModifier(id, instance.getValue(), operation));
         }
      }
   }

   public static void resetAttribute(LivingEntity entity, ItemStack stack, Holder<Attribute> attributeHolder, float value, Operation operation) {
      removeAttribute(entity, stack, attributeHolder, operation);
      applyAttribute(entity, stack, attributeHolder, value, operation);
   }

   public static ItemStack findEquippedCurio(Entity entity, Item item) {
      if (entity instanceof Player player) {
         Optional<ImmutableTriple<String, Integer, ItemStack>> optional = CuriosApi.getCuriosHelper().findEquippedCurio(item, player);
         return optional.isEmpty() ? ItemStack.EMPTY : (ItemStack)optional.get().getRight();
      } else {
         return ItemStack.EMPTY;
      }
   }

   public static List<ItemStack> findEquippedCurios(Entity entity, Item item) {
      return entity instanceof Player player
         ? CuriosApi.getCuriosInventory(player)
            .map(inventory -> inventory.findCurios(item).stream().<ItemStack>map(SlotResult::stack).toList())
            .orElse(List.of())
         : List.of();
   }

   public static long getExperienceForLevel(int level) {
      return level >= 30 ? 112L + (level - 30) * 9L : (level >= 15 ? 37 + (level - 15) * 5 : 7L + level * 2L);
   }

   public static long getTotalExperienceForLevel(int level) {
      long result = 0L;

      for (int i = 0; i < level; i++) {
         result += getExperienceForLevel(i);
      }

      return result;
   }

   public static long getPlayerTotalExperience(Player player) {
      long totalExperience = 0L;

      for (int level = 0; level < player.experienceLevel; level++) {
         totalExperience += getExperienceForLevel(level);
      }

      return totalExperience + Math.round(player.experienceProgress * (float)getExperienceForLevel(player.experienceLevel));
   }

   public static double getLevelFromTotalExperience(long totalXP) {
      if (totalXP <= 0L) {
         return 0.0;
      } else if (totalXP < 315L) {
         double calculatedLevel = (-6.0 + Math.sqrt(36L + 4L * totalXP)) / 2.0;
         int floorLevel = (int)Math.floor(calculatedLevel);
         double xpAtLevel = floorLevel * floorLevel + 6 * floorLevel;
         double xpNeededForNextLevel = 7 + 2 * floorLevel;
         double progressWithinLevel = (totalXP - xpAtLevel) / xpNeededForNextLevel;
         return MathUtils.round(floorLevel + progressWithinLevel, 1);
      } else if (totalXP < 1395L) {
         double xpOffset = totalXP - 315L;
         double discriminant = 1190.25 + 10.0 * xpOffset;
         double levelOffset = (-34.5 + Math.sqrt(discriminant)) / 5.0;
         double calculatedLevel = 15.0 + levelOffset;
         int floorLevel = (int)Math.floor(calculatedLevel);
         int offsetInt = floorLevel - 15;
         long xpAtLevel = 315L + 37L * offsetInt + (int)(2.5 * offsetInt * (offsetInt - 1));
         long xpNeededForNextLevel = 37L + 5L * offsetInt;
         double progressWithinLevel = (double)(totalXP - xpAtLevel) / xpNeededForNextLevel;
         return MathUtils.round(floorLevel + progressWithinLevel, 1);
      } else {
         double xpOffset = totalXP - 1395L;
         double levelOffset = (-107.5 + Math.sqrt(11556.25 + 18.0 * xpOffset)) / 9.0;
         double calculatedLevel = 30.0 + levelOffset;
         int floorLevel = (int)Math.floor(calculatedLevel);
         int offsetInt = floorLevel - 30;
         long xpAtLevel = 1395L + 112L * offsetInt + (long)(4.5 * offsetInt * (offsetInt - 1));
         long xpNeededForNextLevel = 112L + 9L * offsetInt;
         double progressWithinLevel = (double)(totalXP - xpAtLevel) / xpNeededForNextLevel;
         return MathUtils.round(floorLevel + progressWithinLevel, 1);
      }
   }

   public static double calculateExperienceLevelLoss(Player player, long experience) {
      long totalExperience = getPlayerTotalExperience(player);
      long targetTotalExperience = Math.max(0L, totalExperience - experience);
      double currentLevel = getLevelFromTotalExperience(totalExperience);
      double newLevel = getLevelFromTotalExperience(targetTotalExperience);
      return MathUtils.round(currentLevel - newLevel, 1);
   }

   public static boolean isAlliedTo(@Nullable Entity source, @Nullable Entity target) {
      return source == null
         || target == null
         || source.isAlliedTo(target)
         || target.isAlliedTo(source)
         || target.getUUID().equals(source.getUUID())
         || target instanceof OwnableEntity ownableTarget && ownableTarget.getOwnerUUID() != null && ownableTarget.getOwnerUUID().equals(source.getUUID())
         || source instanceof OwnableEntity ownableSource && ownableSource.getOwnerUUID() != null && ownableSource.getOwnerUUID().equals(target.getUUID());
   }

   public static boolean hurt(LivingEntity entity, DamageSource source, float amount) {
      return source.getEntity() instanceof LivingEntity sourceEntity && isAlliedTo(sourceEntity, entity) ? false : entity.hurt(source, amount);
   }

   public static List<ItemStack> getEquippedRelics(LivingEntity entity) {
      List<ItemStack> items = new ArrayList<>();
      if (entity instanceof Player player) {
         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack.getItem() instanceof IRelicItem) {
               items.add(stack);
            }
         }

         CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
            for (int ix = 0; ix < handler.getEquippedCurios().getSlots(); ix++) {
               ItemStack stackx = handler.getEquippedCurios().getStackInSlot(ix);
               if (stackx.getItem() instanceof IRelicItem) {
                  items.add(stackx);
               }
            }
         });
         return items;
      } else {
         return items;
      }
   }

   public static <T extends LivingEntity> Stream<T> gatherPotentialTargets(Entity seeker, Class<T> type, double radius) {
      return seeker.getCommandSenderWorld()
         .getEntitiesOfClass(type, seeker.getBoundingBox().inflate(radius))
         .stream()
         .filter(
            entity -> !(entity instanceof ArmorStand)
               && !entity.isDeadOrDying()
               && entity.hasLineOfSight(seeker)
               && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test((T)entity)
         )
         .sorted(
            Comparator.<T>comparingInt(entity -> !(entity instanceof Player) && !(entity instanceof Monster) ? (entity instanceof NeutralMob ? 1 : 2) : 0)
               .thenComparing(entity -> seeker.position().distanceTo(entity.position()))
         );
   }
}
