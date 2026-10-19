package cn.leolezury.eternalstarlight.common.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;

public class ESEntityUtil {
   public static ESEntityUtil.RaytraceResult raytrace(LevelAccessor level, CollisionContext context, Vec3 from, Vec3 to) {
      BlockHitResult hitResult = level.clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, context));
      ESEntityUtil.RaytraceResult result = new ESEntityUtil.RaytraceResult(new ArrayList<>(), hitResult.getType() == Type.BLOCK ? hitResult : null);

      for (Entity entity : level.getEntitiesOfClass(Entity.class, new AABB(from, to).inflate(1.0))) {
         AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius() + 0.5F);
         if (aabb.contains(from)) {
            result.entities().add(entity);
         } else if (aabb.clip(from, to).isPresent()) {
            result.entities().add(entity);
         }
      }

      result.entities().sort((o1, o2) -> (int)Math.signum(o1.position().distanceTo(from) - o2.position().distanceTo(from)));
      return result;
   }

   public static boolean shouldHarm(Entity attacker, Entity victim) {
      if (attacker == null || victim == null) {
         return true;
      } else if (attacker == victim) {
         return false;
      } else if (!attacker.isAlliedTo(victim) && !victim.isAlliedTo(attacker)) {
         return attacker instanceof Player p1 && victim instanceof Player p2 && !p1.canHarmPlayer(p2) ? false : victim.isAttackable();
      } else {
         return false;
      }
   }

   public static void instantLook(LivingEntity entity, Vec3 pos) {
      if (entity instanceof Mob mob) {
         mob.getLookControl().setLookAt(pos.x, pos.y, pos.z, 360.0F, 360.0F);
      }

      entity.lookAt(Anchor.EYES, pos);
      entity.yBodyRot = entity.getYRot();
      entity.yBodyRotO = entity.getYRot();
      entity.yHeadRot = entity.getYRot();
      entity.yHeadRotO = entity.getYRot();
   }

   public static ItemListing simpleTrade(Item cost, int costCount, Item result, int resultCount, int maxUses) {
      return simpleTrade(new ItemStack(cost, costCount), new ItemStack(result, resultCount), maxUses);
   }

   public static ItemListing simpleTrade(ItemStack cost, ItemStack result, int maxUses) {
      return (entity, random) -> new MerchantOffer(new ItemCost(cost.getItem(), cost.getCount()), result, maxUses, 0, 0.0F);
   }

   public static void givePlayerItem(Player player, ItemStack stack) {
      int maxStackSize = stack.getMaxStackSize();
      int remaining = stack.getCount();

      while (remaining > 0) {
         int size = Math.min(maxStackSize, remaining);
         remaining -= size;
         ItemStack copyToDrop = stack.copyWithCount(size);
         boolean added = player.getInventory().add(copyToDrop);
         if (added && copyToDrop.isEmpty()) {
            player.level()
               .playSound(
                  null,
                  player.getX(),
                  player.getY(),
                  player.getZ(),
                  SoundEvents.ITEM_PICKUP,
                  SoundSource.PLAYERS,
                  0.2F,
                  ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
               );
            player.containerMenu.broadcastChanges();
         } else {
            ItemEntity drop = player.drop(copyToDrop, false);
            if (drop != null) {
               drop.setNoPickUpDelay();
               drop.setTarget(player.getUUID());
            }
         }
      }
   }

   public record RaytraceResult(List<Entity> entities, BlockHitResult blockHitResult) {
   }
}
