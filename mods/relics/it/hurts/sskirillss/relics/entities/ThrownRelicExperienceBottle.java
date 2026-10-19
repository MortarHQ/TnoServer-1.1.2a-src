package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownRelicExperienceBottle extends ThrowableItemProjectile {
   public ThrownRelicExperienceBottle(EntityType<? extends ThrownRelicExperienceBottle> type, Level level) {
      super(type, level);
   }

   protected Item getDefaultItem() {
      return (Item)ItemRegistry.RELIC_EXPERIENCE_BOTTLE.get();
   }

   protected double getDefaultGravity() {
      return 0.07;
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (this.level() instanceof ServerLevel) {
         this.level().levelEvent(2002, this.blockPosition(), PotionContents.getColor(Potions.LUCK));
         int steps = 10 + this.random.nextInt(10);

         for (int i = 0; i < steps; i++) {
            RelicExperienceOrbEntity orb = new RelicExperienceOrbEntity(
               (EntityType<? extends RelicExperienceOrbEntity>)EntityRegistry.RELIC_EXPERIENCE_ORB.get(), this.level()
            );
            orb.setExperience(1 + this.random.nextInt(3));
            orb.setPos(Vec3.atCenterOf(this.blockPosition()));
            orb.setDeltaMovement(
               (-1.0F + 2.0F * this.random.nextFloat()) * 0.15F, 0.1F + this.random.nextFloat() * 0.2F, (-1.0F + 2.0F * this.random.nextFloat()) * 0.15F
            );
            this.level().addFreshEntity(orb);
         }

         this.discard();
      }
   }
}
