package com.chronodawn.items.boats;

import com.chronodawn.entities.boats.ChronoDawnBoat;
import com.chronodawn.entities.boats.ChronoDawnBoatType;
import com.chronodawn.entities.boats.ChronoDawnChestBoat;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class ChronoDawnBoatItem extends Item {
   private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
   private final ChronoDawnBoatType boatType;
   private final boolean hasChest;

   public ChronoDawnBoatItem(ChronoDawnBoatType boatType, boolean hasChest, Properties properties) {
      super(properties.stacksTo(1));
      this.boatType = boatType;
      this.hasChest = hasChest;
   }

   public static Properties createProperties() {
      return new Properties().stacksTo(1);
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      HitResult hitResult = getPlayerPOVHitResult(level, player, Fluid.ANY);
      if (hitResult.getType() == Type.MISS) {
         return InteractionResultHolder.pass(itemStack);
      }

      Vec3 viewVector = player.getViewVector(1.0F);
      double range = 5.0;
      List<Entity> entities = level.getEntities(player, player.getBoundingBox().expandTowards(viewVector.scale(range)).inflate(1.0), ENTITY_PREDICATE);
      if (!entities.isEmpty()) {
         Vec3 eyePosition = player.getEyePosition();

         for (Entity entity : entities) {
            AABB boundingBox = entity.getBoundingBox().inflate(entity.getPickRadius());
            if (boundingBox.contains(eyePosition)) {
               return InteractionResultHolder.pass(itemStack);
            }
         }
      }

      if (hitResult.getType() == Type.BLOCK) {
         Boat boat = this.createBoat(level, hitResult);
         boat.setYRot(player.getYRot());
         if (!level.noCollision(boat, boat.getBoundingBox())) {
            return InteractionResultHolder.fail(itemStack);
         }

         if (!level.isClientSide()) {
            level.addFreshEntity(boat);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, hitResult.getLocation());
            if (!player.getAbilities().instabuild) {
               itemStack.shrink(1);
            }
         }

         player.awardStat(Stats.ITEM_USED.get(this));
         return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
      } else {
         return InteractionResultHolder.pass(itemStack);
      }
   }

   private Boat createBoat(Level level, HitResult hitResult) {
      Vec3 location = hitResult.getLocation();
      if (this.hasChest) {
         ChronoDawnChestBoat chestBoat = new ChronoDawnChestBoat(level, location.x, location.y, location.z);
         chestBoat.setChronoDawnBoatType(this.boatType);
         return chestBoat;
      } else {
         ChronoDawnBoat boat = new ChronoDawnBoat(level, location.x, location.y, location.z);
         boat.setChronoDawnBoatType(this.boatType);
         return boat;
      }
   }

   public ChronoDawnBoatType getBoatType() {
      return this.boatType;
   }

   public boolean hasChest() {
      return this.hasChest;
   }
}
