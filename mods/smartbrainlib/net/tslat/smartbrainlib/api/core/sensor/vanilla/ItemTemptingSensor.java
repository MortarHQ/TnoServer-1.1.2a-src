package net.tslat.smartbrainlib.api.core.sensor.vanilla;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.object.SquareRadius;
import net.tslat.smartbrainlib.registry.SBLSensors;
import net.tslat.smartbrainlib.util.BrainUtils;
import net.tslat.smartbrainlib.util.EntityRetrievalUtil;

public class ItemTemptingSensor<E extends LivingEntity> extends PredicateSensor<Player, E> {
   private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(new MemoryModuleType[]{MemoryModuleType.TEMPTING_PLAYER});
   protected BiPredicate<E, ItemStack> temptPredicate = (entity, stack) -> false;
   protected SquareRadius radius = new SquareRadius(10.0, 10.0);

   public ItemTemptingSensor() {
      this.setPredicate(
         (target, entity) -> !target.isSpectator() && target.isAlive()
            ? this.temptPredicate.test(entity, target.getMainHandItem()) || this.temptPredicate.test(entity, target.getOffhandItem())
            : false
      );
   }

   @Override
   public List<MemoryModuleType<?>> memoriesUsed() {
      return MEMORIES;
   }

   @Override
   public SensorType<? extends ExtendedSensor<?>> type() {
      return SBLSensors.ITEM_TEMPTING.get();
   }

   @Deprecated(forRemoval = true)
   public ItemTemptingSensor<E> setTemptingItems(Ingredient temptingItems) {
      return this.temptedWith((entity, stack) -> temptingItems.test(stack));
   }

   public ItemTemptingSensor<E> temptedWith(BiPredicate<E, ItemStack> predicate) {
      this.temptPredicate = predicate;
      return this;
   }

   public ItemTemptingSensor<E> setRadius(double radius) {
      return this.setRadius(radius, radius);
   }

   public ItemTemptingSensor<E> setRadius(double xz, double y) {
      this.radius = new SquareRadius(xz, y);
      return this;
   }

   @Override
   protected void doTick(ServerLevel level, E entity) {
      List<Player> nearbyPlayers = BrainUtils.getMemory(entity, MemoryModuleType.NEAREST_PLAYERS);
      Player player;
      if (nearbyPlayers != null) {
         player = nearbyPlayers.stream().filter(pl -> this.predicate().test(pl, entity)).min(Comparator.comparing(pl -> pl.distanceToSqr(entity))).orElse(null);
      } else {
         player = EntityRetrievalUtil.getNearestPlayer(
            entity, this.radius.xzRadius(), this.radius.yRadius(), this.radius.xzRadius(), target -> this.predicate().test(target, entity)
         );
      }

      if (player == null) {
         BrainUtils.clearMemory(entity, MemoryModuleType.TEMPTING_PLAYER);
      } else {
         BrainUtils.setMemory(entity, MemoryModuleType.TEMPTING_PLAYER, player);
      }
   }
}
