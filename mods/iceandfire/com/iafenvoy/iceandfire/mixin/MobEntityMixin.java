package com.iafenvoy.iceandfire.mixin;

import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends Entity {
   public MobEntityMixin(EntityType<?> type, Level world) {
      super(type, world);
   }

   @Unique
   private static boolean iceandfire$isSkeleton(Entity entity) {
      return WitherSkeleton.class.isAssignableFrom(entity.getClass());
   }

   @Inject(method = "dropFromLootTable(Lnet/minecraft/world/damagesource/DamageSource;Z)V", at = @At("HEAD"))
   public void dropHandler(DamageSource damageSource, boolean causedByPlayer, CallbackInfo ci) {
      if (causedByPlayer && damageSource.getDirectEntity() instanceof Player player && iceandfire$isSkeleton(this)) {
         this.spawnAtLocation(new ItemStack((ItemLike)IafItems.WITHERBONE.get(), this.random.nextInt(2)));
      }
   }
}
