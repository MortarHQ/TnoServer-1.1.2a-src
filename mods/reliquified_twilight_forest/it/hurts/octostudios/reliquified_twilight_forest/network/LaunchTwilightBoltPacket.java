package it.hurts.octostudios.reliquified_twilight_forest.network;

import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.LichCrownItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import twilightforest.entity.projectile.TwilightWandBolt;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFSounds;

public record LaunchTwilightBoltPacket() implements CustomPacketPayload {
   public static final Type<LaunchTwilightBoltPacket> TYPE = new Type(
      ResourceLocation.fromNamespaceAndPath("reliquified_twilight_forest", "launch_twilight_bolt")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, LaunchTwilightBoltPacket> STREAM_CODEC = CustomPacketPayload.codec(
      LaunchTwilightBoltPacket::write, LaunchTwilightBoltPacket::new
   );

   public LaunchTwilightBoltPacket(RegistryFriendlyByteBuf buf) {
      this();
   }

   public void write(RegistryFriendlyByteBuf buf) {
   }

   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   public static void handle(LaunchTwilightBoltPacket packet, IPayloadContext ctx) {
      Player entity = ctx.player();
      switch (ctx.flow()) {
         case SERVERBOUND:
            ctx.enqueueWork(
               () -> {
                  final ItemStack stack = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.LICH_CROWN.get());
                  if (stack.getItem() instanceof LichCrownItem relic
                     && relic.getAbilityLevel(stack, "twilight") > 0
                     && (Integer)stack.getOrDefault(DataComponentRegistry.TWILIGHT_TIME, 0) <= 0) {
                     TwilightWandBolt bolt = new TwilightWandBolt(entity.level(), entity) {
                        int age = 200;

                        public void tick() {
                           super.tick();
                           if (!this.level().isClientSide()) {
                              if (this.getAge() < 0) {
                                 this.discard();
                              }

                              this.setAge(this.getAge() - 1);
                           }
                        }

                        public void addAdditionalSaveData(CompoundTag compound) {
                           super.addAdditionalSaveData(compound);
                           compound.putInt("Age", this.getAge());
                        }

                        public void readAdditionalSaveData(CompoundTag compound) {
                           super.readAdditionalSaveData(compound);
                           this.setAge(compound.getInt("Age"));
                        }

                        protected void onHitEntity(EntityHitResult result) {
                           if (!this.level().isClientSide()) {
                              result.getEntity()
                                 .hurt(
                                    TFDamageTypes.getIndirectEntityDamageSource(
                                       this.level(), TFDamageTypes.TWILIGHT_SCEPTER, this, this.getOwner(), new EntityType[0]
                                    ),
                                    (float)relic.getStatValue(stack, "twilight", "damage")
                                 );
                              this.level()
                                 .playSound(
                                    null,
                                    result.getEntity().blockPosition(),
                                    (SoundEvent)TFSounds.TWILIGHT_SCEPTER_HIT.get(),
                                    this.getOwner() != null ? this.getOwner().getSoundSource() : SoundSource.PLAYERS
                                 );
                              this.level().broadcastEntityEvent(this, (byte)3);
                              this.discard();
                           }
                        }

                        public void setAge(int age) {
                           this.age = age;
                        }

                        public int getAge() {
                           return this.age;
                        }
                     };
                     bolt.getPersistentData().putBoolean("reliquified_twilight_forest:isCustom", true);
                     bolt.setNoGravity(true);
                     bolt.setDeltaMovement(entity.getViewVector(1.0F).scale(relic.getStatValue(stack, "twilight", "speed_scale")));
                     entity.level().addFreshEntity(bolt);
                     stack.set(DataComponentRegistry.TWILIGHT_TIME, 50);
                     PacketDistributor.sendToPlayer((ServerPlayer)entity, new LaunchTwilightBoltPacket(), new CustomPacketPayload[0]);
                  }
               }
            );
            break;
         case CLIENTBOUND:
            ctx.enqueueWork(
               () -> entity.playSound(
                  (SoundEvent)TFSounds.TWILIGHT_SCEPTER_USE.get(), 1.0F, (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1.0F
               )
            );
      }
   }
}
