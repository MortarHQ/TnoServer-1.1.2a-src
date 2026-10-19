package twilightforest.util.entities;

import com.google.common.collect.Lists;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.DoubleUnaryOperator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;
import twilightforest.TwilightForestMod;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.init.TFSounds;

public class EntityUtil {
   private static final Lookup LOOKUP = MethodHandles.lookup();
   private static final Method LivingEntity_getDeathSound = ObfuscationReflectionHelper.findMethod(LivingEntity.class, "getDeathSound", new Class[0]);
   private static final MethodHandle handle_LivingEntity_getDeathSound;
   private static final Method HangingEntity_setDirection = ObfuscationReflectionHelper.findMethod(
      HangingEntity.class, "setDirection", new Class[]{Direction.class}
   );
   private static final MethodHandle handle_HangingEntity_setDirection;

   public static <T extends Mob & EnforcedHomePoint> BlockPos bossChestLocation(T boss) {
      return !boss.isRestrictionPointValid(boss.level().dimension()) ? boss.blockPosition() : boss.getRestrictionPoint().pos().below();
   }

   public static boolean canDestroyBlock(Level world, BlockPos pos, Entity entity) {
      return canDestroyBlock(world, pos, world.getBlockState(pos), entity);
   }

   public static boolean canDestroyBlock(Level world, BlockPos pos, BlockState state, Entity entity) {
      float hardness = state.getDestroySpeed(world, pos);
      return hardness >= 0.0F
         && hardness < 50.0F
         && !state.isAir()
         && !(world.getBlockEntity(pos) instanceof Container)
         && state.getBlock().canEntityDestroy(state, world, pos, entity)
         && (!(entity instanceof LivingEntity) || EventHooks.onEntityDestroyBlock((LivingEntity)entity, pos, state));
   }

   public static BlockHitResult rayTrace(Entity entity, double range) {
      Vec3 position = entity.getEyePosition(1.0F);
      Vec3 look = entity.getViewVector(1.0F);
      Vec3 dest = position.add(look.x * range, look.y * range, look.z * range);
      return entity.level().clip(new ClipContext(position, dest, Block.OUTLINE, Fluid.NONE, entity));
   }

   public static BlockHitResult rayTrace(Player player) {
      return rayTrace(player, null);
   }

   public static BlockHitResult rayTrace(Player player, @Nullable DoubleUnaryOperator modifier) {
      double range = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).getValue();
      return rayTrace(player, modifier == null ? range : modifier.applyAsDouble(range));
   }

   @Nullable
   public static SoundEvent getDeathSound(LivingEntity living) {
      SoundEvent sound = null;
      if (handle_LivingEntity_getDeathSound != null) {
         try {
            sound = (SoundEvent)handle_LivingEntity_getDeathSound.invokeExact((LivingEntity)living);
         } catch (Throwable var3) {
         }
      }

      return sound;
   }

   public static void killLavaAround(Entity entity) {
      AABB bounds = entity.getBoundingBox().inflate(9.0);

      for (double x = bounds.minX; x < bounds.maxX; x++) {
         for (double z = bounds.minZ; z < bounds.maxZ; z++) {
            for (double y = bounds.minY; y < bounds.maxY; y++) {
               BlockPos pos = BlockPos.containing(x, y, z);
               BlockState state = entity.level().getBlockState(pos);
               if (state.is(Blocks.LAVA)) {
                  entity.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
               }
            }
         }
      }
   }

   public static boolean properlyApplyCustomDamageSource(Mob entity, Entity victim, DamageSource source, @Nullable SoundEvent flingSound) {
      float f = (float)entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
      if (entity.level() instanceof ServerLevel serverlevel) {
         f = EnchantmentHelper.modifyDamage(serverlevel, entity.getWeaponItem(), entity, source, f);
      }

      boolean flag = victim.hurt(source, f);
      if (flag) {
         float f1 = getKnockback(entity, victim, source);
         if (f1 > 0.0F && victim instanceof LivingEntity livingentity) {
            if (flingSound != null) {
               entity.playSound(flingSound, 1.0F, 1.0F);
            }

            livingentity.knockback(f1 * 0.5F, Mth.sin(entity.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(entity.getYRot() * (float) (Math.PI / 180.0)));
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.6, 1.0, 0.6));
         }

         if (entity.level() instanceof ServerLevel level) {
            EnchantmentHelper.doPostAttackEffects(level, victim, source);
         }

         entity.setLastHurtMob(entity);
      }

      return flag;
   }

   protected static float getKnockback(Mob entity, Entity victim, DamageSource source) {
      float f = (float)entity.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
      return entity.level() instanceof ServerLevel serverlevel ? EnchantmentHelper.modifyKnockback(serverlevel, entity.getWeaponItem(), victim, source, f) : f;
   }

   @Nullable
   private static <T extends Entity> T createEntityIgnoreException(EntityType<T> type, ServerLevelAccessor levelAccessor) {
      try {
         return (T)type.create(levelAccessor.getLevel());
      } catch (Exception exception) {
         return null;
      }
   }

   public static boolean tryHangPainting(WorldGenLevel world, BlockPos pos, Direction direction, @Nullable Holder<PaintingVariant> chosenPainting) {
      if (chosenPainting == null) {
         return false;
      }

      Painting painting = createEntityIgnoreException(EntityType.PAINTING, world);
      painting.setPos(pos.getX(), pos.getY(), pos.getZ());

      try {
         handle_HangingEntity_setDirection.invoke((Painting)painting, (Direction)direction);
      } catch (Throwable throwable) {
         throwable.printStackTrace();
         return false;
      }

      painting.setVariant(chosenPainting);
      if (checkValidPaintingPosition(world, painting)) {
         world.addFreshEntity(painting);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public static Holder<PaintingVariant> getPaintingOfSize(WorldGenLevel level, RandomSource rand, int minSize) {
      return getPaintingOfSize(level, rand, minSize, minSize, false);
   }

   @Nullable
   public static Holder<PaintingVariant> getPaintingOfSize(WorldGenLevel level, RandomSource rand, int width, int height, boolean exactMeasurements) {
      List<Holder<PaintingVariant>> valid = new ArrayList<>();

      for (Holder<PaintingVariant> art : level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).holders().toList()) {
         if (exactMeasurements) {
            if (((PaintingVariant)art.value()).width() == width && ((PaintingVariant)art.value()).height() == height) {
               valid.add(art);
            }
         } else if (((PaintingVariant)art.value()).width() >= width || ((PaintingVariant)art.value()).height() >= height) {
            valid.add(art);
         }
      }

      return !valid.isEmpty() ? valid.get(rand.nextInt(valid.size())) : null;
   }

   public static List<Holder<PaintingVariant>> getPaintingsOfSizeOrSmaller(
      WorldGenLevel level, TagKey<PaintingVariant> lichTowerPaintings, int width, int height
   ) {
      List<Holder<PaintingVariant>> valid = new ArrayList<>();

      for (Holder<PaintingVariant> art : level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT).getTagOrEmpty(lichTowerPaintings)) {
         if (((PaintingVariant)art.value()).width() <= width && ((PaintingVariant)art.value()).height() <= height) {
            valid.add(art);
         }
      }

      return valid;
   }

   public static boolean checkValidPaintingPosition(WorldGenLevel world, @Nullable Painting painting) {
      if (painting == null) {
         return false;
      }

      AABB largerBox = painting.getBoundingBox();
      if (!world.noCollision(painting, largerBox)) {
         return false;
      }

      for (Entity entityOnList : getEntitiesInAABB(world, largerBox)) {
         if (entityOnList instanceof HangingEntity) {
            return false;
         }
      }

      return true;
   }

   public static List<Entity> getEntitiesInAABB(WorldGenLevel world, AABB boundingBox) {
      List<Entity> list = Lists.newArrayList();
      int i = Mth.floor((boundingBox.minX - 2.0) / 16.0);
      int j = Mth.floor((boundingBox.maxX + 2.0) / 16.0);
      int k = Mth.floor((boundingBox.minZ - 2.0) / 16.0);
      int l = Mth.floor((boundingBox.maxZ + 2.0) / 16.0);

      for (int i1 = i; i1 <= j; i1++) {
         for (int j1 = k; j1 <= l; j1++) {
            if (world.getChunk(i1, j1, ChunkStatus.STRUCTURE_STARTS) instanceof ProtoChunk proto) {
               proto.getEntities().forEach(nbt -> {
                  Entity entity = EntityType.loadEntityRecursive(nbt, world.getLevel(), e -> e);
                  if (entity != null && boundingBox.intersects(entity.getBoundingBox())) {
                     list.add(entity);
                  }
               });
            }
         }
      }

      return list;
   }

   public static boolean convertEntity(LivingEntity oldEntity, EntityType<?> newType) {
      if (!(oldEntity.level() instanceof ServerLevel level)) {
         return false;
      } else {
         Entity newEntity = newType.create(level);
         if (newEntity == null) {
            return false;
         } else if (newEntity instanceof LivingEntity living && !EventHooks.canLivingConvert(oldEntity, living.getType(), timer -> {})) {
            return false;
         } else {
            List<Entity> passengerSave = oldEntity.getPassengers();
            if (oldEntity instanceof Mob mob && newEntity instanceof Mob newMob) {
               newEntity = mob.convertTo(newMob.getType(), true);
            } else {
               newEntity.copyPosition(oldEntity);
               if (newEntity instanceof Mob mob) {
                  if (oldEntity instanceof Mob oldMob) {
                     for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                        ItemStack itemstack = oldEntity.getItemBySlot(equipmentslot).copyAndClear();
                        if (!itemstack.isEmpty()) {
                           mob.setItemSlot(equipmentslot, itemstack.copyAndClear());
                           mob.setDropChance(equipmentslot, oldMob.getEquipmentDropChance(equipmentslot));
                        }
                     }
                  }

                  EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(oldEntity.blockPosition()), MobSpawnType.CONVERSION, null);
               }

               oldEntity.level().addFreshEntity(newEntity);
               oldEntity.discard();
            }

            try {
               UUID uuid = newEntity.getUUID();
               newEntity.load(oldEntity.saveWithoutId(newEntity.saveWithoutId(new CompoundTag())));
               newEntity.setUUID(uuid);
               if (newEntity instanceof LivingEntity living) {
                  living.setHealth(living.getMaxHealth());
               }
            } catch (Exception e) {
               TwilightForestMod.LOGGER.warn("Couldn't transform entity NBT data", e);
            }

            if (oldEntity instanceof Saddleable saddleable && saddleable.isSaddled() && !(newEntity instanceof Saddleable)) {
               newEntity.spawnAtLocation(Items.SADDLE);
            }

            if (newEntity instanceof Mob mob) {
               mob.spawnAnim();
               mob.spawnAnim();

               for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                  ItemStack itemstack = mob.getItemBySlot(equipmentslot).copyAndClear();
                  mob.spawnAtLocation(itemstack);
               }
            }

            if (!passengerSave.isEmpty()) {
               for (Entity entity : passengerSave) {
                  entity.startRiding(newEntity, true);
               }
            }

            if (newEntity instanceof LivingEntity living) {
               EventHooks.onLivingConvert(oldEntity, living);
            }

            level.playSound(null, newEntity.blockPosition(), (SoundEvent)TFSounds.POWDER_USE.get(), newEntity.getSoundSource());
            return true;
         }
      }
   }

   @Nullable
   public static <T extends Entity> T createEntityIgnoreException(ServerLevelAccessor level, EntityType<T> type) {
      try {
         return (T)type.create(level.getLevel());
      } catch (Exception exception) {
         return null;
      }
   }

   static {
      MethodHandle tmp_handle_LivingEntity_getDeathSound = null;
      MethodHandle tmp_handle_HangingEntity_setDirection = null;

      try {
         tmp_handle_LivingEntity_getDeathSound = LOOKUP.unreflect(LivingEntity_getDeathSound);
         tmp_handle_HangingEntity_setDirection = LOOKUP.unreflect(HangingEntity_setDirection);
      } catch (IllegalAccessException e) {
         e.printStackTrace();
      }

      handle_LivingEntity_getDeathSound = tmp_handle_LivingEntity_getDeathSound;
      handle_HangingEntity_setDirection = tmp_handle_HangingEntity_setDirection;
   }
}
