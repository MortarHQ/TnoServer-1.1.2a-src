package com.trbeyond.tensura_better_subs;

import com.google.common.base.Preconditions;
import com.trbeyond.tensura_better_subs.config.ExpandedSkillConfig;
import com.trbeyond.tensura_better_subs.entity.ExpandedSkillStorage;
import com.trbeyond.tensura_better_subs.entity.IExpandedSkillAcquisition;
import com.trbeyond.tensura_better_subs.network.c2s.RequestSubordinateAbilityModeChangePacket;
import com.trbeyond.tensura_better_subs.network.c2s.SubordinateSkillGrantingPacket;
import com.trbeyond.tensura_better_subs.network.s2c.OpenHumanoidSkillMenuPayload;
import com.trbeyond.tensura_better_subs.network.s2c.OpenSkillGrantMenuPayload;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.EntityEvent.LivingHurt;
import dev.architectury.event.events.common.InteractionEvent.InteractEntity;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.NetworkUtils;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingTickEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.AttributeBaseValueChangedEvent;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.MonsterEntityTypes;
import io.github.manasmods.tensura.registry.skill.CommonSkills;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BetterSubs {
   public static final String MOD_ID = "tensura_better_subs";
   public static final Logger LOGGER = LoggerFactory.getLogger("tensura_better_subs");

   public static void init() {
      LOGGER.info("Initializing BetterSubs");
      ConfigRegistry.registerConfig(new ExpandedSkillConfig());
      initEntityEvents();
      initNetworking();
      ExpandedSkillStorage.init();
   }

   public static void initEntityEvents() {
      TensuraEntityEvents.ATTRIBUTE_BASE_CHANGE_EVENT.register((AttributeBaseValueChangedEvent)(entity, instance, oldValue, newValue) -> {
         List<Holder<Attribute>> EPAttributes = List.of(TensuraAttributes.MAX_MAGICULE, TensuraAttributes.MAX_AURA);
         if (EPAttributes.contains(instance.getAttribute()) && newValue > oldValue && SubordinateHelper.getSubordinateOwnerUUID(entity) != null) {
            IExpandedSkillAcquisition storage = getSkillExpansionFrom(entity);
            if (storage != null) {
               storage.markUpdateExistence();
               storage.entityEPUpdate(entity);
            }
         }

         return EventResult.pass();
      });
      EntityEvent.LIVING_DEATH
         .register(
            (LivingDeath)(entity, source) -> {
               Entity sourceEntity;
               if (source.getEntity() instanceof Projectile) {
                  sourceEntity = ((Projectile)source.getEntity()).getOwner();
               } else {
                  sourceEntity = source.getEntity();
               }

               if (sourceEntity == null) {
                  return EventResult.pass();
               }

               if (entity.getType() == MonsterEntityTypes.ORC_DISASTER.get()) {
                  AABB boundingBox = entity.getBoundingBox().inflate(30.0);
                  List<LivingEntity> nearbySubordinates = entity.level()
                     .getEntitiesOfClass(
                        LivingEntity.class, boundingBox, subordinate -> subordinate.isAlliedTo(source.getEntity()) && isSubordinateEntity(subordinate)
                     );
                  if (sourceEntity instanceof Player) {
                     nearbySubordinates = entity.level()
                        .getEntitiesOfClass(
                           LivingEntity.class,
                           boundingBox,
                           subordinate -> SubordinateHelper.getSubordinateOwner(subordinate) == sourceEntity
                              && subordinate.getLastHurtMob() == entity
                              && getSkillExpansionFrom(subordinate) != null
                        );
                  } else if (isSubordinateEntity(sourceEntity)) {
                     nearbySubordinates = entity.level()
                        .getEntitiesOfClass(
                           LivingEntity.class,
                           boundingBox,
                           subordinate -> Objects.equals(
                                 SubordinateHelper.getSubordinateOwnerUUID((LivingEntity)sourceEntity), SubordinateHelper.getSubordinateOwnerUUID(subordinate)
                              )
                              && subordinate.getLastHurtMob() == entity
                              && getSkillExpansionFrom(subordinate) != null
                        );
                  } else {
                     nearbySubordinates = List.of();
                  }

                  for (LivingEntity subordinateEntity : nearbySubordinates) {
                     IExpandedSkillAcquisition.grantSkill(subordinateEntity, (ManasSkill)CommonSkills.CORROSION.get(), true);
                  }
               }

               if (sourceEntity instanceof LivingEntity livingEntity && SubordinateHelper.getSubordinateOwnerUUID(livingEntity) != null) {
                  IExpandedSkillAcquisition storage = getSkillExpansionFrom(livingEntity);
                  if (storage != null) {
                     storage.attemptTallyKill(entity, livingEntity);
                  }
               }

               return EventResult.pass();
            }
         );
      EntityEvent.LIVING_HURT.register((LivingHurt)(entity, source, amount) -> {
         if (SubordinateHelper.getSubordinateOwnerUUID(entity) != null) {
            IExpandedSkillAcquisition storage = getSkillExpansionFrom(entity);
            if (storage != null) {
               storage.attemptTallyDamage(entity, amount);
            }
         }

         return EventResult.pass();
      });
      EntityEvent.LIVING_HURT.register((LivingHurt)(entity, source, amount) -> {
         if (source.getEntity() instanceof Projectile projectile && projectile.getOwner() instanceof LivingEntity owner) {
            owner.setLastHurtMob(entity);
         }

         return EventResult.pass();
      });
      InteractionEvent.INTERACT_ENTITY.register((InteractEntity)(player, entity, interactionHand) -> {
         if (player.level().isClientSide) {
            return EventResult.pass();
         }

         boolean f = shouldConsumePlayerInteract(player, entity, interactionHand);
         return f ? EventResult.interruptTrue() : EventResult.pass();
      });
      EntityEvents.LIVING_POST_TICK.register((LivingTickEvent)entity -> {
         if (!entity.level().isClientSide()) {
            if (entity.level().getServer().getTickCount() % 20 == 0) {
               if (SubordinateHelper.getSubordinateOwnerUUID(entity) != null) {
                  IExpandedSkillAcquisition storage = getSkillExpansionFrom(entity);
                  if (storage != null) {
                     storage.tickStorage(entity);
                  }
               }
            }
         }
      });
   }

   private static boolean isSubordinateEntity(Entity entity) {
      return entity instanceof LivingEntity livingEntity && SubordinateHelper.getSubordinateOwnerUUID(livingEntity) != null;
   }

   public static void initNetworking() {
      NetworkUtils.registerS2CPayload(OpenHumanoidSkillMenuPayload.TYPE, OpenHumanoidSkillMenuPayload.STREAM_CODEC, OpenHumanoidSkillMenuPayload::handle);
      NetworkUtils.registerS2CPayload(OpenSkillGrantMenuPayload.TYPE, OpenSkillGrantMenuPayload.STREAM_CODEC, OpenSkillGrantMenuPayload::handle);
      NetworkUtils.registerC2SPayload(
         RequestSubordinateAbilityModeChangePacket.TYPE,
         RequestSubordinateAbilityModeChangePacket.STREAM_CODEC,
         RequestSubordinateAbilityModeChangePacket::handle
      );
      NetworkUtils.registerC2SPayload(SubordinateSkillGrantingPacket.TYPE, SubordinateSkillGrantingPacket.STREAM_CODEC, SubordinateSkillGrantingPacket::handle);
   }

   private static boolean shouldConsumePlayerInteract(Player player, Entity entity, InteractionHand interactionHand) {
      if (entity instanceof LivingEntity livingEntity && SubordinateHelper.isSubordinate(player, livingEntity) && player.isCrouching()) {
         ItemStack itemStack = player.getItemInHand(interactionHand);
         IExpandedSkillAcquisition storage = getSkillExpansionFrom(livingEntity);
         boolean consumedItem = storage != null && storage.usedSkillItem(itemStack.getItem(), livingEntity);
         if (consumedItem && !player.isCreative()) {
            itemStack.shrink(1);
         }

         return consumedItem;
      } else {
         return false;
      }
   }

   public static IExpandedSkillAcquisition getSkillExpansionFrom(@NotNull LivingEntity entity) {
      return (IExpandedSkillAcquisition)entity.manasCore$getStorage(ExpandedSkillStorage.getKey());
   }

   @NotNull
   public static ResourceLocation rl(String str) {
      Preconditions.checkNotNull(str);
      return !str.isBlank()
         ? ResourceLocation.fromNamespaceAndPath("tensura_better_subs", str)
         : ResourceLocation.fromNamespaceAndPath("tensura_better_subs", "blank");
   }
}
