package com.blamejared.crafttweaker.natives.event.entity.living;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.event.ZenEvent;
import com.blamejared.crafttweaker.api.event.bus.IEventBus;
import com.blamejared.crafttweaker.api.event.bus.NeoForgeEventBusWire;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.damagesource.DamageSource;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.damagesource.DamageContainer.Reduction;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("neoforge/api/event/entity/living/LivingDamageEvent")
@NativeTypeRegistration(value = LivingDamageEvent.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingDamageEvent")
public class ExpandLivingDamageEvent {
   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/entity/living/LivingPostDamageEvent")
   @NativeTypeRegistration(value = Post.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingPostDamageEvent")
   public static class ExpandLivingPostDamageEvent {
      @ZenEvent.Bus
      public static final IEventBus<Post> BUS = IEventBus.direct(Post.class, NeoForgeEventBusWire.of());

      @ZenCodeType.Method
      public static float getReduction(Post internal, Reduction reduction) {
         return internal.getReduction(reduction);
      }

      @ZenCodeType.Getter("postAttackInvulnerabilityTicks")
      public static int getPostAttackInvulnerabilityTicks(Post internal) {
         return internal.getPostAttackInvulnerabilityTicks();
      }

      @ZenCodeType.Getter("shieldDamage")
      public static float getShieldDamage(Post internal) {
         return internal.getShieldDamage();
      }

      @ZenCodeType.Getter("blockedDamage")
      public static float getBlockedDamage(Post internal) {
         return internal.getBlockedDamage();
      }

      @ZenCodeType.Getter("newDamage")
      public static float getNewDamage(Post internal) {
         return internal.getNewDamage();
      }

      @ZenCodeType.Getter("source")
      public static DamageSource getSource(Post internal) {
         return internal.getSource();
      }

      @ZenCodeType.Getter("originalDamage")
      public static float getOriginalDamage(Post internal) {
         return internal.getOriginalDamage();
      }
   }

   @ZenRegister
   @ZenEvent
   @Document("neoforge/api/event/entity/living/LivingPreDamageEvent")
   @NativeTypeRegistration(value = Pre.class, zenCodeName = "crafttweaker.neoforge.api.event.entity.living.LivingPreDamageEvent")
   public static class ExpandLivingPreDamageEvent {
      @ZenEvent.Bus
      public static final IEventBus<Pre> BUS = IEventBus.direct(Pre.class, NeoForgeEventBusWire.of());

      @ZenCodeType.Getter("container")
      public static DamageContainer getContainer(Pre internal) {
         return internal.getContainer();
      }
   }
}
