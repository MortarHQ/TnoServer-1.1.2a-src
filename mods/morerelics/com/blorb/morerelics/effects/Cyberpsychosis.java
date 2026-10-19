package com.blorb.morerelics.effects;

import com.blorb.morerelics.Config;
import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.mixin.WalkAnimationStateAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.RenderLivingEvent.Pre;

public class Cyberpsychosis extends MobEffect {
   public Cyberpsychosis() {
      super(MobEffectCategory.BENEFICIAL, 2142890);
   }

   @EventBusSubscriber(modid = "morerelics", value = Dist.CLIENT)
   public static class ClientEvents {
      private static final Map<LivingEntity, LivingEntity> DISGUISE_LOOKUP = new WeakHashMap<>();
      private static final Map<String, LivingEntity> DUMMY_CACHE = new HashMap<>();

      @SubscribeEvent
      public static void onRenderLiving(Pre<?, ?> event) {
         Minecraft mc = Minecraft.getInstance();
         if ((Boolean)Config.CYBERPSYCHOSIS_DISGUISE_EFFECT.get() && mc.player != null && mc.player.hasEffect(MoreRelics.CYBERPSYCHOSIS)) {
            LivingEntity target = event.getEntity();
            ClientLevel level = mc.level;
            LivingEntity disguise = DISGUISE_LOOKUP.computeIfAbsent(target, entity -> {
               EntityType<? extends LivingEntity> type = null;
               if (entity != mc.player && entity instanceof Player) {
                  type = EntityType.CREEPER;
               } else if (entity instanceof Cow || entity instanceof Pig || entity instanceof Sheep) {
                  type = EntityType.SPIDER;
               } else if (entity instanceof Villager) {
                  type = EntityType.ZOMBIE_VILLAGER;
               } else if (entity instanceof Horse) {
                  type = EntityType.ZOMBIE_HORSE;
               } else if (entity instanceof Wolf || entity instanceof Cat) {
                  type = EntityType.ZOMBIE;
               } else if (entity instanceof Parrot) {
                  type = EntityType.VEX;
               } else if (entity instanceof Axolotl) {
                  type = EntityType.DROWNED;
               }

               if (type == null) {
                  return null;
               }

               boolean isBaby = entity instanceof Wolf || entity instanceof Cat || entity instanceof Axolotl || entity.isBaby();
               return getDummy(type, level, isBaby);
            });
            if (disguise != null) {
               renderDisguise(event, target, disguise);
               event.setCanceled(true);
            }
         }
      }

      private static void renderDisguise(Pre<?, ?> event, LivingEntity original, LivingEntity disguise) {
         EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
         syncEntityData(original, disguise);
         dispatcher.render(
            disguise,
            0.0,
            0.0,
            0.0,
            original.getViewYRot(event.getPartialTick()),
            event.getPartialTick(),
            event.getPoseStack(),
            event.getMultiBufferSource(),
            event.getPackedLight()
         );
      }

      private static void syncEntityData(LivingEntity source, LivingEntity dest) {
         dest.setPos(source.getX(), source.getY(), source.getZ());
         dest.xo = source.xo;
         dest.yo = source.yo;
         dest.zo = source.zo;
         dest.yBodyRot = source.yBodyRot;
         dest.yBodyRotO = source.yBodyRotO;
         dest.yHeadRot = source.yHeadRot;
         dest.yHeadRotO = source.yHeadRotO;
         dest.setXRot(source.getXRot());
         dest.xRotO = source.xRotO;
         dest.walkDist = source.walkDist;
         dest.walkDistO = source.walkDistO;
         dest.walkAnimation.setSpeed(source.walkAnimation.speed());
         WalkAnimationStateAccessor sAcc = (WalkAnimationStateAccessor)source.walkAnimation;
         WalkAnimationStateAccessor dAcc = (WalkAnimationStateAccessor)dest.walkAnimation;
         dAcc.setSpeedOld(sAcc.getSpeedOld());
         dAcc.setPosition(sAcc.getPosition());
         dest.tickCount = source.tickCount;
         dest.swingTime = source.swingTime;
         dest.swinging = source.swinging;
         dest.hurtTime = source.hurtTime;
         dest.hurtDuration = source.hurtDuration;
         dest.deathTime = source.deathTime;
      }

      private static LivingEntity getDummy(EntityType<? extends LivingEntity> type, ClientLevel level, boolean isBaby) {
         String key = type.getDescriptionId() + (isBaby ? "_baby" : "_adult");
         return DUMMY_CACHE.computeIfAbsent(key, k -> {
            LivingEntity e = (LivingEntity)type.create(level);
            if (e instanceof Zombie z) {
               z.setBaby(isBaby);
            } else if (e instanceof ZombieHorse zh) {
               zh.setBaby(isBaby);
            } else if (e instanceof ZombieVillager zv) {
               zv.setBaby(isBaby);
            } else if (e instanceof AgeableMob am) {
               am.setBaby(isBaby);
            }

            return e;
         });
      }

      @SubscribeEvent
      public static void onClientLogout(LoggingOut event) {
         DISGUISE_LOOKUP.clear();
         DUMMY_CACHE.clear();
      }
   }
}
