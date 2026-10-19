package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.PawType;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.google.common.collect.UnmodifiableIterator;
import java.util.List;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ItemElokosaPaw extends Item {
   private final PawType pawType;

   public ItemElokosaPaw(PawType pawType, Properties properties) {
      super(properties.stacksTo(1).durability(3));
      this.pawType = pawType;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
      ItemStack itemstack = player.getItemInHand(usedHand);
      player.playSound((SoundEvent)MMSounds.ENTITY_ELOKOSA_PAW.get(), 1.0F, 1.15F - 0.06F * this.pawType.ordinal());
      int cooldown = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ELOKOSA_PAW.cooldown.getAsInt();
      if (!player.hasInfiniteMaterials()) {
         UnmodifiableIterator radius = ItemHandler.ELOKOSA_PAWS.iterator();

         while (radius.hasNext()) {
            DeferredHolder<Item, ItemElokosaPaw> item = (DeferredHolder<Item, ItemElokosaPaw>)radius.next();
            player.getCooldowns().addCooldown((Item)item.get(), cooldown);
         }
      }

      itemstack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(usedHand));
      if (level.isClientSide()) {
         float ringScale = 100.0F;
         AdvancedParticleBase.spawnParticle(
            level,
            ParticleHandler.BURST_MESSY,
            player.getX(),
            player.getY() + player.getBbHeight() / 2.0F,
            player.getZ(),
            0.0,
            0.0,
            0.0,
            false,
            0.0,
            Math.PI / 2,
            0.0,
            0.0,
            5.0,
            0.7,
            0.55,
            0.99,
            1.0,
            1.0,
            20.0,
            true,
            false,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.6) * ringScale * 1.3F),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.Expression(t -> 1.0F - (float)Math.pow(t.floatValue(), 0.5)),
                  false
               )
            }
         );
         AdvancedParticleBase.spawnParticle(
            level,
            ParticleHandler.RING_BIG,
            player.getX(),
            player.getY() + player.getBbHeight() / 2.0F,
            player.getZ(),
            0.0,
            0.0,
            0.0,
            false,
            0.0,
            Math.PI / 2,
            0.0,
            0.0,
            5.0,
            0.85,
            0.65,
            0.99,
            1.0,
            1.0,
            15.0,
            true,
            false,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.6) * ringScale),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.Expression(t -> 1.0F - (float)Math.pow(t.floatValue(), 0.5)),
                  false
               )
            }
         );
         float glowScale = 130.0F;
         AdvancedParticleBase.spawnParticle(
            level,
            ParticleHandler.GLOW,
            player.getX(),
            player.getY() + player.getBbHeight() / 2.0F,
            player.getZ(),
            0.0,
            0.0,
            0.0,
            true,
            0.0,
            0.0,
            0.0,
            0.0,
            5.0,
            0.65,
            0.45,
            0.95,
            1.0,
            1.0,
            20.0,
            true,
            false,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.6) * glowScale),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.Expression(t -> 1.0F - (float)Math.pow(t.floatValue(), 0.5)),
                  false
               )
            }
         );
         Vec3 moonPos;
         if (Minecraft.getInstance().player == player && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON) {
            boolean usingMainHand = usedHand == InteractionHand.MAIN_HAND;
            boolean isRightHanded = player.getMainArm() == HumanoidArm.RIGHT;
            boolean usedRightHand = usingMainHand == isRightHanded;
            float sidewaysOffset = 0.76F;
            Vec3 playerLeftAngle = player.getUpVector(0.0F).cross(player.getLookAngle());
            moonPos = player.getEyePosition()
               .add(player.getLookAngle())
               .add(player.getUpVector(0.0F).scale(0.2F))
               .add(playerLeftAngle.scale(usedRightHand ? -sidewaysOffset : sidewaysOffset));
            float moonSize = 4.3F;
            AdvancedParticleBase.spawnParticle(
               level,
               this.pawType.getParticleType(),
               moonPos.x,
               moonPos.y,
               moonPos.z,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               1.0,
               1.0,
               1.0,
               1.0,
               1.0,
               25.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 1.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.05F, 0.15F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(
                        t -> ((1.0F - (float)Math.pow(t.floatValue(), 0.6)) * (float)Math.pow(t.floatValue(), 0.5) * 7.0F + 1.0F) * moonSize
                     ),
                     false
                  ),
                  new ParticleComponent.ScreenSpace(usedRightHand ? sidewaysOffset : -sidewaysOffset, 1.2F, -0.5F)
               }
            );
            AdvancedParticleBase.spawnParticle(
               level,
               ParticleHandler.GLOW,
               moonPos.x,
               moonPos.y,
               moonPos.z,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               0.4F,
               0.576,
               1.0,
               1.0,
               1.0,
               25.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 0.3F, 0.3F, 0.0F}, new float[]{0.0F, 0.05F, 0.15F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(
                        t -> ((1.0F - (float)Math.pow(t.floatValue(), 0.8)) * (float)Math.pow(t.floatValue(), 0.5) * 7.0F + 1.0F) * moonSize * 0.8F
                     ),
                     false
                  ),
                  new ParticleComponent.ScreenSpace(usedRightHand ? sidewaysOffset : -sidewaysOffset, 1.2F, -0.5F)
               }
            );
         } else {
            float moonSize = 5.0F;
            moonPos = new Vec3(player.getX(), player.getY() + player.getBbHeight() + 0.65F, player.getZ());
            AdvancedParticleBase.spawnParticle(
               level,
               this.pawType.getParticleType(),
               moonPos.x,
               moonPos.y,
               moonPos.z,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               1.0,
               1.0,
               1.0,
               1.0,
               1.0,
               25.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 1.0F, 1.0F, 0.0F}, new float[]{0.0F, 0.05F, 0.15F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(
                        t -> ((1.0F - (float)Math.pow(t.floatValue(), 0.6)) * (float)Math.pow(t.floatValue(), 0.5) * 7.0F + 1.0F) * moonSize
                     ),
                     false
                  )
               }
            );
            AdvancedParticleBase.spawnParticle(
               level,
               ParticleHandler.GLOW,
               moonPos.x,
               moonPos.y,
               moonPos.z,
               0.0,
               0.0,
               0.0,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               0.4F,
               0.576,
               1.0,
               1.0,
               1.0,
               25.0,
               true,
               false,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{0.0F, 0.0F, 0.3F, 0.3F, 0.0F}, new float[]{0.0F, 0.05F, 0.15F, 0.5F, 1.0F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(
                        t -> ((1.0F - (float)Math.pow(t.floatValue(), 0.8)) * (float)Math.pow(t.floatValue(), 0.5) * 7.0F + 1.0F) * moonSize * 0.8F
                     ),
                     false
                  )
               }
            );
         }

         for (int i = 0; i < 60; i++) {
            Vec3 vel = new Vec3(0.15 + player.getRandom().nextFloat() * 0.4F, 0.0, 0.0)
               .yRot(player.getRandom().nextFloat() * (float) (Math.PI * 2))
               .xRot(player.getRandom().nextFloat() * (float) (Math.PI * 2));
            AdvancedParticleBase.spawnParticle(
               level,
               ParticleHandler.PIXEL,
               moonPos.x + vel.x,
               moonPos.y + vel.y,
               moonPos.z + vel.z,
               vel.x,
               vel.y,
               vel.z,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               0.85,
               0.65,
               0.99,
               1.0,
               0.9,
               10 + player.getRandom().nextInt(10),
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(t -> (1.0F - (float)Math.pow(t.floatValue(), 2.0)) * 2.5F),
                     false
                  ),
                  new ParticleComponent.ForceOverTime(new Vec3(0.0, 0.003, 0.0)),
                  new ParticleComponent.Vortex(
                     new Vec3(0.0, 1.0, 0.0),
                     new Vec3(player.getX(), player.getY() + player.getBbHeight() / 2.0F, player.getZ()),
                     ParticleComponent.KeyTrack.startAndEnd(player.getRandom().nextFloat() * 0.15F, 0.0F)
                  ),
                  new ParticleComponent.CurlNoise(0.025F, 4.0F)
               }
            );
         }
      }

      float radius = 10.0F;
      List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(
         LivingEntity.class, player.getBoundingBox().inflate(radius, radius, radius), e -> e.distanceToSqr(player) <= radius * radius && e != player
      );
      int effectDuration = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ELOKOSA_PAW.effectDuration.getAsInt();

      for (LivingEntity entity : entitiesNearby) {
         entity.addEffect(new MobEffectInstance(this.pawType.getPotion(), effectDuration));

         for (int i = 0; i < 30; i++) {
            Vec3 vel = new Vec3(0.05 + entity.getRandom().nextFloat() * 0.1F, 0.0, 0.0)
               .yRot(entity.getRandom().nextFloat() * (float) (Math.PI * 2))
               .xRot(entity.getRandom().nextFloat() * (float) (Math.PI * 2));
            AdvancedParticleBase.spawnParticle(
               level,
               ParticleHandler.PIXEL,
               entity.getX(),
               entity.getY() + entity.getBbHeight() / 2.0F,
               entity.getZ(),
               vel.x,
               vel.y,
               vel.z,
               true,
               0.0,
               0.0,
               0.0,
               0.0,
               5.0,
               0.85,
               0.65,
               0.99,
               1.0,
               0.9,
               10 + player.getRandom().nextInt(10),
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     new ParticleComponent.Expression(t -> (1.0F - (float)Math.pow(t.floatValue(), 2.0)) * 2.5F),
                     false
                  ),
                  new ParticleComponent.ForceOverTime(new Vec3(0.0, 0.003, 0.0)),
                  new ParticleComponent.Vortex(
                     new Vec3(0.0, 1.0, 0.0),
                     new Vec3(entity.getX(), entity.getY() + entity.getBbHeight() / 2.0F, entity.getZ()),
                     ParticleComponent.KeyTrack.startAndEnd(entity.getRandom().nextFloat() * 0.1F, 0.0F)
                  ),
                  new ParticleComponent.CurlNoise(0.025F, 4.0F)
               }
            );
         }

         AdvancedParticleBase.spawnParticle(
            level,
            ParticleHandler.BURST_OUT,
            entity.getX(),
            entity.getY() + entity.getBbHeight() / 2.0F,
            entity.getZ(),
            0.0,
            0.0,
            0.0,
            true,
            0.0,
            0.0,
            0.0,
            0.0,
            4.0,
            0.65,
            0.45,
            0.95,
            1.0,
            1.0,
            18.0,
            true,
            false,
            new ParticleComponent[]{
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                  new ParticleComponent.Expression(t -> (float)Math.pow(t.floatValue(), 0.6) * 14.0F),
                  false
               ),
               new ParticleComponent.PropertyControl(
                  ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                  new ParticleComponent.Expression(t -> 1.0F - (float)Math.pow(t.floatValue(), 0.5)),
                  false
               )
            }
         );
      }

      return InteractionResultHolder.consume(itemstack);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      if (this.pawType == PawType.CRESCENT || this.pawType == PawType.NEW) {
         tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
      }

      if (this.pawType == PawType.NEW) {
         tooltip.add(Component.translatable(this.getDescriptionId() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
      }
   }
}
