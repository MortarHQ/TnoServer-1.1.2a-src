package com.bobmowzie.mowziesmobs.server.capability;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.ParticleDecal;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.client.render.entity.player.GeckoPlayer;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.item.ItemEarthrendGauntlet;
import com.bobmowzie.mowziesmobs.server.item.ItemElokosaPaw;
import com.bobmowzie.mowziesmobs.server.item.ItemHandler;
import com.bobmowzie.mowziesmobs.server.message.MessageAddInProgressCooldown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageLeftMouseUp;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseDown;
import com.bobmowzie.mowziesmobs.server.message.mouse.MessageRightMouseUp;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import com.bobmowzie.mowziesmobs.server.power.Power;
import com.google.common.collect.UnmodifiableIterator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemCooldowns.CooldownInstance;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

public class PlayerData implements INBTSerializable<CompoundTag> {
   public boolean verticalSwing = false;
   public int untilSunstrike = 0;
   public int untilAxeSwing = 0;
   private int prevTime;
   private int time;
   public boolean mouseRightDown = false;
   public boolean mouseLeftDown = false;
   public boolean prevSneaking;
   private float prevCooledAttackStrength;
   public int packCircleTick;
   public List<EntityUmvuthanaFollowerToPlayer> umvuthanaPack = new ArrayList<>();
   public int packRadius = 3;
   private int footstepCounter;
   private int footstepDelay;
   private int pawCooldownRemainingToSave;
   private int pawCooldownRemainingToLoad;
   private GeckoPlayer.GeckoPlayerThirdPerson geckoPlayer;
   private EntitySculptor testingSculptor;
   private boolean usingSolarBeam;
   public boolean axeCanAttack;
   public Vec3 prevMotion;
   public Power[] powers = new Power[0];

   public boolean isVerticalSwing() {
      return this.verticalSwing;
   }

   public void setVerticalSwing(boolean verticalSwing) {
      this.verticalSwing = verticalSwing;
   }

   public int getUntilSunstrike() {
      return this.untilSunstrike;
   }

   public void setUntilSunstrike(int untilSunstrike) {
      this.untilSunstrike = untilSunstrike;
   }

   public int getUntilAxeSwing() {
      return this.untilAxeSwing;
   }

   public void setUntilAxeSwing(int untilAxeSwing) {
      this.untilAxeSwing = untilAxeSwing;
   }

   public void setAxeCanAttack(boolean axeCanAttack) {
      this.axeCanAttack = axeCanAttack;
   }

   public boolean getAxeCanAttack() {
      return this.axeCanAttack;
   }

   public boolean isMouseRightDown() {
      return this.mouseRightDown;
   }

   public void setMouseRightDown(boolean mouseRightDown) {
      this.mouseRightDown = mouseRightDown;
   }

   public boolean isMouseLeftDown() {
      return this.mouseLeftDown;
   }

   public void setMouseLeftDown(boolean mouseLeftDown) {
      this.mouseLeftDown = mouseLeftDown;
   }

   public boolean isPrevSneaking() {
      return this.prevSneaking;
   }

   public void setPrevSneaking(boolean prevSneaking) {
      this.prevSneaking = prevSneaking;
   }

   public int getPackCircleTick() {
      return this.packCircleTick;
   }

   public void setPackCircleTick(int packCircleTick) {
      this.packCircleTick = packCircleTick;
   }

   public List<EntityUmvuthanaFollowerToPlayer> getUmvuthanaPack() {
      return this.umvuthanaPack;
   }

   public void setUmvuthanaPack(List<EntityUmvuthanaFollowerToPlayer> umvuthanaPack) {
      this.umvuthanaPack = umvuthanaPack;
   }

   public int getPackRadius() {
      return this.packRadius;
   }

   public void setPackRadius(int packRadius) {
      this.packRadius = packRadius;
   }

   public Vec3 getPrevMotion() {
      return this.prevMotion;
   }

   public void setUsingSolarBeam(boolean b) {
      this.usingSolarBeam = b;
   }

   public boolean getUsingSolarBeam() {
      return this.usingSolarBeam;
   }

   public float getPrevCooledAttackStrength() {
      return this.prevCooledAttackStrength;
   }

   public void setPrevCooledAttackStrength(float cooledAttackStrength) {
      this.prevCooledAttackStrength = cooledAttackStrength;
   }

   public GeckoPlayer.GeckoPlayerThirdPerson getGeckoPlayer() {
      return this.geckoPlayer;
   }

   public void addedToWorld(EntityJoinLevelEvent event) {
      if (event.getLevel().isClientSide()) {
         Player player = (Player)event.getEntity();
         this.geckoPlayer = new GeckoPlayer.GeckoPlayerThirdPerson(player);
         if (event.getEntity() == MMCommon.PROXY.getLocalPlayer()) {
            new GeckoPlayer.GeckoPlayerFirstPerson(player);
         }
      }
   }

   public void pressedAttackKey(Player player) {
      if (!this.mouseLeftDown) {
         this.mouseLeftDown = true;
         PacketDistributor.sendToServer(new MessageLeftMouseDown(), new CustomPacketPayload[0]);

         for (Power power : this.powers) {
            power.onLeftMouseDown(player);
         }

         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);

         for (Ability<?> ability : abilityData.getAbilities()) {
            if (ability instanceof PlayerAbility) {
               ((PlayerAbility)ability).onLeftMouseDown(player);
            }
         }
      }
   }

   public void pressedUseKey(Player player) {
      if (!this.mouseRightDown) {
         this.mouseRightDown = true;
         PacketDistributor.sendToServer(new MessageRightMouseDown(), new CustomPacketPayload[0]);

         for (Power power : this.powers) {
            power.onLeftMouseDown(player);
         }

         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);

         for (Ability<?> ability : abilityData.getAbilities()) {
            if (ability instanceof PlayerAbility) {
               ((PlayerAbility)ability).onRightMouseDown(player);
            }
         }
      }
   }

   public void tick(PlayerTickEvent event) {
      final Player player = event.getEntity();
      this.packCircleTick++;
      this.prevMotion = player.position().subtract(new Vec3(player.xo, player.yo, player.zo));
      this.prevTime = this.time;
      if (this.untilSunstrike > 0) {
         this.untilSunstrike--;
      }

      if (this.untilAxeSwing > 0) {
         this.untilAxeSwing--;
      }

      if (!player.level().isClientSide()) {
         if (player.getMainHandItem().getItem() instanceof ItemEarthrendGauntlet || player.getOffhandItem().getItem() instanceof ItemEarthrendGauntlet) {
            player.addEffect(new MobEffectInstance(EffectHandler.GEOMANCY, 20, 0, false, false));
         }

         List<EntityUmvuthanaFollowerToPlayer> pack = this.umvuthanaPack;
         float theta = (float) (Math.PI * 2) / pack.size();

         for (int i = 0; i < pack.size(); i++) {
            EntityUmvuthanaFollowerToPlayer barakoan = pack.get(i);
            barakoan.index = i;
            float distanceToPlayer = player.distanceTo(barakoan);
            if (barakoan.getTarget() == null && barakoan.getActiveAbility() == null) {
               if (distanceToPlayer > 4.0F) {
                  barakoan.getNavigation()
                     .moveTo(player.getX() + this.packRadius * Mth.cos(theta * i), player.getY(), player.getZ() + this.packRadius * Mth.sin(theta * i), 0.45);
               } else {
                  barakoan.getNavigation().stop();
               }

               if (distanceToPlayer > 20.0F && player.onGround()) {
                  this.tryTeleportUmvuthanaFollower(player, barakoan);
               }
            }
         }
      }

      Ability<?> iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
      if (iceBreathAbility != null && !iceBreathAbility.isUsing()) {
         for (ItemStack stack : player.getInventory().items) {
            this.restoreIceCrystalStack(player, stack);
         }

         for (ItemStack stack : player.getInventory().offhand) {
            this.restoreIceCrystalStack(player, stack);
         }
      }

      this.useIceCrystalStack(player);
      if (player.level().isClientSide()) {
         if (!Minecraft.getInstance().options.keyAttack.isDown() && this.mouseLeftDown) {
            this.mouseLeftDown = false;
            PacketDistributor.sendToServer(new MessageLeftMouseUp(), new CustomPacketPayload[0]);

            for (int i = 0; i < this.powers.length; i++) {
               this.powers[i].onLeftMouseUp(player);
            }

            AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
            if (abilityData != null) {
               for (Ability<?> ability : abilityData.getAbilities()) {
                  if (ability instanceof PlayerAbility) {
                     ((PlayerAbility)ability).onLeftMouseUp(player);
                  }
               }
            }
         }

         if (!Minecraft.getInstance().options.keyUse.isDown() && this.mouseRightDown) {
            this.mouseRightDown = false;
            PacketDistributor.sendToServer(new MessageRightMouseUp(), new CustomPacketPayload[0]);

            for (int i = 0; i < this.powers.length; i++) {
               this.powers[i].onRightMouseUp(player);
            }

            AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
            if (abilityData != null) {
               for (Ability<?> ability : abilityData.getAbilities()) {
                  if (ability instanceof PlayerAbility) {
                     ((PlayerAbility)ability).onRightMouseUp(player);
                  }
               }
            }
         }
      }

      if (player.isShiftKeyDown() && !this.prevSneaking) {
         for (int i = 0; i < this.powers.length; i++) {
            this.powers[i].onSneakDown(player);
         }

         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
         if (abilityData != null) {
            for (Ability<?> ability : abilityData.getAbilities()) {
               if (ability instanceof PlayerAbility) {
                  ((PlayerAbility)ability).onSneakDown(player);
               }
            }
         }
      } else if (!player.isShiftKeyDown() && this.prevSneaking) {
         for (int i = 0; i < this.powers.length; i++) {
            this.powers[i].onSneakUp(player);
         }

         AbilityData abilityData = DataHandler.getData(player, DataHandler.ABILITY_DATA);
         if (abilityData != null) {
            for (Ability<?> ability : abilityData.getAbilities()) {
               if (ability instanceof PlayerAbility) {
                  ((PlayerAbility)ability).onSneakUp(player);
               }
            }
         }
      }

      this.prevSneaking = player.isShiftKeyDown();
      if (player.level().isClientSide()
         && player.hasEffect(EffectHandler.SUNS_BLESSING)
         && (Boolean)ConfigHandler.CLIENT.umvuthanaFootprints.get()
         && player.onGround()
         && player.getDeltaMovement().lengthSqr() > 0.01) {
         this.footstepDelay--;
         if (this.footstepDelay <= 0) {
            this.footstepDelay = 6;
            this.footstepCounter++;
            double rotation = Math.toRadians(player.yBodyRot + 180.0F);
            Vec3 offset = new Vec3(0.0, 0.0, this.footstepCounter % 2 == 0 ? 0.15 : -0.15).yRot((float)(-rotation) + 90.0F);
            ParticleDecal.spawnDecal(
               player.level(),
               ParticleHandler.PLAYER_FOOTPRINT,
               player.getX() + offset.x(),
               player.getY() + 0.01,
               player.getZ() + offset.z(),
               0.0,
               0.0,
               0.0,
               rotation,
               0.5,
               1.0,
               0.95,
               0.1,
               1.0,
               1.0,
               100.0,
               true,
               8,
               32,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.RED,
                     new ParticleComponent.KeyTrack(new float[]{0.995F, 0.05F}, new float[]{0.0F, 0.5F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.GREEN,
                     new ParticleComponent.KeyTrack(new float[]{0.95F, 0.05F}, new float[]{0.0F, 0.5F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.BLUE,
                     new ParticleComponent.KeyTrack(new float[]{0.1F, 0.05F}, new float[]{0.0F, 0.5F}),
                     false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA,
                     new ParticleComponent.KeyTrack(new float[]{1.0F, 0.8F}, new float[]{0.0F, 0.5F}),
                     false
                  ),
                  new ParticleComponent() {
                     @Override
                     public void postUpdate(AdvancedParticleBase particle) {
                        super.postUpdate(particle);
                        if (particle.getAge() < 60.0F && player.getRandom().nextFloat() < 0.3F) {
                           int amount = 1;

                           while (amount-- > 0) {
                              float theta = player.getRandom().nextFloat() * (float) (Math.PI * 2);
                              float r = player.getRandom().nextFloat() * 0.2F;
                              float x = r * Mth.cos(theta);
                              float z = r * Mth.sin(theta);
                              player.level()
                                 .addParticle(ParticleTypes.SMOKE, particle.getPosX() + x, particle.getPosY() + 0.05, particle.getPosZ() + z, 0.0, 0.0, 0.0);
                           }
                        }
                     }
                  }
               }
            );
         }
      }

      if (this.pawCooldownRemainingToLoad > 0) {
         this.loadPawCooldownsFromNBT(event.getEntity());
         this.pawCooldownRemainingToLoad = 0;
      }

      this.setPawCooldownsForNBT(event.getEntity());
   }

   private void restoreIceCrystalStack(Player entity, ItemStack stack) {
      if (stack.getItem() == ItemHandler.ICE_CRYSTAL.get() && !(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
         stack.setDamageValue(Math.max(stack.getDamageValue() - 1, 0));
      }
   }

   private void useIceCrystalStack(Player player) {
      ItemStack stack = player.getUseItem();
      if (stack.getItem() == ItemHandler.ICE_CRYSTAL.get()) {
         Ability<?> iceBreathAbility = AbilityHandler.INSTANCE.getAbility(player, AbilityHandler.ICE_BREATH_ABILITY);
         if (iceBreathAbility != null && iceBreathAbility.isUsing()) {
            InteractionHand handIn = player.getUsedItemHand();
            if (stack.getDamageValue() + 5 < stack.getMaxDamage()) {
               stack.hurtAndBreak(5, player, LivingEntity.getSlotForHand(handIn));
            } else {
               if ((Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
                  stack.hurtAndBreak(5, player, LivingEntity.getSlotForHand(handIn));
               }

               iceBreathAbility.end();
            }
         }
      }
   }

   private void tryTeleportUmvuthanaFollower(Player player, EntityUmvuthanaFollowerToPlayer umvuthana) {
      int x = Mth.floor(player.getX()) - 2;
      int z = Mth.floor(player.getZ()) - 2;
      int y = Mth.floor(player.getBoundingBox().minY);

      for (int l = 0; l <= 4; l++) {
         for (int i1 = 0; i1 <= 4; i1++) {
            if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && umvuthana.isTeleportFriendlyBlock(x, z, y, l, i1)) {
               umvuthana.moveTo(x + l + 0.5F, y, z + i1 + 0.5F, umvuthana.getYRot(), umvuthana.getXRot());
               umvuthana.getNavigation().stop();
               return;
            }
         }
      }
   }

   public int getTick() {
      return this.time;
   }

   public void decrementTime() {
      this.time--;
   }

   public int getPackSize() {
      this.umvuthanaPack.removeIf(Entity::isRemoved);
      return this.umvuthanaPack.size();
   }

   public void removePackMember(EntityUmvuthanaFollowerToPlayer followerToPlayer) {
      this.umvuthanaPack.remove(followerToPlayer);
   }

   public void addPackMember(EntityUmvuthanaFollowerToPlayer followerToPlayer) {
      this.umvuthanaPack.add(followerToPlayer);
   }

   public Power[] getPowers() {
      return this.powers;
   }

   public EntitySculptor getTestingSculptor() {
      return this.testingSculptor;
   }

   public void setTestingSculptor(EntitySculptor sculptor) {
      this.testingSculptor = sculptor;
   }

   public void setPawCooldownsForNBT(Player player) {
      CooldownInstance cooldownInstance = (CooldownInstance)player.getCooldowns().cooldowns.get(ItemHandler.ELOKOSA_PAW_FULL.get());
      if (cooldownInstance != null) {
         this.pawCooldownRemainingToSave = cooldownInstance.endTime - player.tickCount;
      }
   }

   public void loadPawCooldownsFromNBT(Player player) {
      int cooldown = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ELOKOSA_PAW.cooldown.getAsInt();
      if (this.pawCooldownRemainingToLoad > 0) {
         UnmodifiableIterator var3 = ItemHandler.ELOKOSA_PAWS.iterator();

         while (var3.hasNext()) {
            DeferredHolder<Item, ItemElokosaPaw> item = (DeferredHolder<Item, ItemElokosaPaw>)var3.next();
            int startTime = -(cooldown - this.pawCooldownRemainingToLoad);
            int endTime = this.pawCooldownRemainingToLoad;
            player.getCooldowns().cooldowns.put((Item)item.get(), new CooldownInstance(startTime, endTime));
            if (player instanceof ServerPlayer serverPlayer) {
               serverPlayer.connection.send(new MessageAddInProgressCooldown((Item)item.get(), startTime, endTime));
            }
         }
      }
   }

   public CompoundTag serializeNBT(@NotNull Provider lookup) {
      CompoundTag compound = new CompoundTag();
      compound.putInt("untilSunstrike", this.untilSunstrike);
      compound.putInt("untilAxeSwing", this.untilAxeSwing);
      compound.putInt("prevTime", this.prevTime);
      compound.putInt("time", this.time);
      compound.putInt("pawCooldownRemaining", this.pawCooldownRemainingToSave);
      return compound;
   }

   public void deserializeNBT(@NotNull Provider lookup, CompoundTag compound) {
      this.untilSunstrike = compound.getInt("untilSunstrike");
      this.untilAxeSwing = compound.getInt("untilAxeSwing");
      this.prevTime = compound.getInt("prevTime");
      this.time = compound.getInt("time");
      this.pawCooldownRemainingToLoad = compound.getInt("pawCooldownRemaining");
   }
}
