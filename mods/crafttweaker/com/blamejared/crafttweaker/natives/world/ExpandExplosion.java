package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.level.AccessExplosion;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeConstructor;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.phys.Vec3;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/Explosion")
@NativeTypeRegistration(
   value = Explosion.class,
   zenCodeName = "crafttweaker.api.world.Explosion",
   constructors = @NativeConstructor(
      {
            @NativeConstructor.ConstructorParameter(
               name = "level",
               type = Level.class,
               description = "The level to spawn the explosion in",
               examples = "level"
            ),
            @NativeConstructor.ConstructorParameter(
               name = "entity",
               type = Entity.class,
               description = "The entity that spawned the explosion",
               examples = "player"
            ),
            @NativeConstructor.ConstructorParameter(name = "x", type = double.class, description = "The x position.", examples = "0"),
            @NativeConstructor.ConstructorParameter(name = "y", type = double.class, description = "The y position.", examples = "0"),
            @NativeConstructor.ConstructorParameter(name = "z", type = double.class, description = "The z position.", examples = "0"),
            @NativeConstructor.ConstructorParameter(name = "size", type = float.class, description = "How big is the explosion", examples = "1.0"),
            @NativeConstructor.ConstructorParameter(
               name = "causesFire",
               type = boolean.class,
               description = "Does the explosion cause fire",
               examples = "true"
            ),
            @NativeConstructor.ConstructorParameter(
               name = "mode",
               type = BlockInteraction.class,
               description = "How should the explosion interact with blocks",
               examples = "<constant:minecraft:explosion/blockinteraction:destroy>"
            )
      }
   )
)
public class ExpandExplosion {
   @ZenCodeType.Method
   public static void explode(Explosion internal) {
      internal.explode();
   }

   @ZenCodeType.Method
   public static void finalizeExplosion(Explosion internal, boolean spawnParticles) {
      internal.finalizeExplosion(spawnParticles);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("damageSource")
   public static DamageSource getDamageSource(Explosion internal) {
      return ((AccessExplosion)internal).crafttweaker$getDamageSource();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("playerKnockbackMap")
   public static Map<Player, Vec3> getHitPlayers(Explosion internal) {
      return internal.getHitPlayers();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("indirectSourceEntity")
   public static @ZenCodeType.Nullable LivingEntity getIndirectSourceEntity(Explosion internal) {
      return internal.getIndirectSourceEntity();
   }

   @ZenCodeType.Method
   public static void clearToBlow(Explosion internal) {
      internal.clearToBlow();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("toBlow")
   public static List<BlockPos> getToBlow(Explosion internal) {
      return internal.getToBlow();
   }
}
