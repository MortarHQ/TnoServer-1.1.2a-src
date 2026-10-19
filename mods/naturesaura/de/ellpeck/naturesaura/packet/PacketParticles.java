package de.ellpeck.naturesaura.packet;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.entities.EntityStructureFinder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketParticles(float posX, float posY, float posZ, int particleType, List<Integer> data) implements CustomPacketPayload {
   public static final net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<PacketParticles> TYPE = new net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type(
      ResourceLocation.fromNamespaceAndPath("naturesaura", "particles")
   );
   public static final StreamCodec<RegistryFriendlyByteBuf, PacketParticles> CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT,
      PacketParticles::posX,
      ByteBufCodecs.FLOAT,
      PacketParticles::posY,
      ByteBufCodecs.FLOAT,
      PacketParticles::posZ,
      ByteBufCodecs.INT,
      PacketParticles::particleType,
      ByteBufCodecs.collection(ArrayList::new, ByteBufCodecs.INT),
      PacketParticles::data,
      PacketParticles::new
   );

   public PacketParticles(float posX, float posY, float posZ, PacketParticles.Type particleType, int... data) {
      this(posX, posY, posZ, particleType.ordinal(), Arrays.stream(data).boxed().toList());
   }

   public net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }

   @OnlyIn(Dist.CLIENT)
   public static void onMessage(PacketParticles message, IPayloadContext ctx) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         PacketParticles.Type.values()[message.particleType].action.accept(message, level);
      }
   }

   public enum Type {
      TR_GOLD_POWDER(
         (message, level) -> {
            BlockPos pos = BlockPos.containing(message.posX, message.posY, message.posZ);
            Multiblocks.TREE_RITUAL
               .forEach(
                  pos,
                  'G',
                  (dustPos, matcher) -> {
                     BlockState state = level.getBlockState(dustPos);
                     AABB box = state.getShape(level, dustPos).bounds();
                     NaturesAuraAPI.instance()
                        .spawnMagicParticle(
                           dustPos.getX() + box.minX + (box.maxX - box.minX) * level.random.nextFloat(),
                           dustPos.getY() + 0.1F,
                           dustPos.getZ() + box.minZ + (box.maxZ - box.minZ) * level.random.nextFloat(),
                           (float)level.random.nextGaussian() * 0.02F,
                           level.random.nextFloat() * 0.01F + 0.02F,
                           (float)level.random.nextGaussian() * 0.02F,
                           16042818,
                           2.0F,
                           50,
                           0.0F,
                           false,
                           true
                        );
                     return true;
                  }
               );
         }
      ),
      TR_CONSUME_ITEM(
         (message, level) -> {
            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.5F,
                     message.posY + 0.9F,
                     message.posZ + 0.5F,
                     (float)level.random.nextGaussian() * 0.04F,
                     level.random.nextFloat() * 0.04F,
                     (float)level.random.nextGaussian() * 0.04F,
                     9030711,
                     1.5F,
                     25,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      TR_DISAPPEAR(
         (message, level) -> {
            for (int i = level.random.nextInt(5) + 3; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat(),
                     message.posY + level.random.nextFloat(),
                     message.posZ + level.random.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     3407667,
                     1.0F,
                     50,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      TR_SPAWN_RESULT(
         (message, level) -> {
            for (int i = level.random.nextInt(10) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY,
                     message.posZ,
                     level.random.nextGaussian() * 0.1F,
                     level.random.nextGaussian() * 0.1F,
                     level.random.nextGaussian() * 0.1F,
                     9030711,
                     2.0F,
                     100,
                     0.0F,
                     true,
                     true
                  );
            }
         }
      ),
      ALTAR_CONVERSION(
         (message, level) -> {
            Integer color = message.data.getFirst();

            for (int i = level.random.nextInt(5) + 2; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 0.9F + 0.25F * level.random.nextFloat(),
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextFloat() * 0.02F,
                     level.random.nextGaussian() * 0.02F,
                     color,
                     level.random.nextFloat() * 1.5F + 0.75F,
                     20,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      POTION_GEN(
         (message, level) -> {
            Integer color = message.data.getFirst();
            boolean releaseAura = message.data.get(1) > 0;

            for (int i = level.random.nextInt(5) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat(),
                     message.posY + 1.1F,
                     message.posZ + level.random.nextFloat(),
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.1F,
                     level.random.nextGaussian() * 0.01F,
                     color,
                     2.0F + level.random.nextFloat(),
                     40,
                     0.0F,
                     true,
                     true
                  );
               if (releaseAura) {
                  for (int x = -1; x <= 1; x += 2) {
                     for (int z = -1; z <= 1; z += 2) {
                        NaturesAuraAPI.instance()
                           .spawnMagicParticle(
                              message.posX + x * 3 + 0.5F,
                              message.posY + 2.5,
                              message.posZ + z * 3 + 0.5F,
                              level.random.nextGaussian() * 0.02F,
                              level.random.nextFloat() * 0.04F,
                              level.random.nextGaussian() * 0.02F,
                              14038028,
                              1.0F + level.random.nextFloat() * 2.0F,
                              75,
                              0.0F,
                              true,
                              true
                           );
                     }
                  }
               }
            }
         }
      ),
      PLANT_BOOST(
         (message, level) -> {
            for (int i = level.random.nextInt(20) + 15; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat(),
                     message.posY + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posZ + level.random.nextFloat(),
                     0.0,
                     level.random.nextFloat() * 0.02F,
                     0.0,
                     6081584,
                     1.0F + level.random.nextFloat() * 2.0F,
                     50,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      FLOWER_GEN_CONSUME(
         (message, level) -> {
            Integer color = message.data.getFirst();

            for (int i = level.random.nextInt(10) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextGaussian() * 0.02F,
                     color,
                     level.random.nextFloat() * 2.0F + 1.0F,
                     25,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      FLOWER_GEN_AURA_CREATION(
         (message, level) -> {
            for (int i = level.random.nextInt(10) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 1.01F,
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     6081584,
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      PLACER_PLACING(
         (message, level) -> {
            for (int i = level.random.nextInt(20) + 20; i >= 0; i--) {
               boolean side = level.random.nextBoolean();
               float x = side ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.1F : -0.1F);
               float z = !side ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.1F : -0.1F);
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + x,
                     message.posY + 0.1F + level.random.nextFloat() * 0.98F,
                     message.posZ + z,
                     0.0,
                     0.0,
                     0.0,
                     11369015,
                     level.random.nextFloat() + 1.0F,
                     50,
                     0.0F,
                     true,
                     true
                  );
            }
         }
      ),
      HOPPER_UPGRADE(
         (message, level) -> {
            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY + 0.45F,
                     message.posZ,
                     level.random.nextGaussian() * 0.015F,
                     level.random.nextGaussian() * 0.015F,
                     level.random.nextGaussian() * 0.015F,
                     14542847,
                     level.random.nextFloat() + 1.0F,
                     30,
                     -0.06F,
                     true,
                     true
                  );
            }
         }
      ),
      SHOCKWAVE_CREATOR(
         (message, level) -> {
            for (int i = 0; i < 360; i += 2) {
               double rad = Math.toRadians(i);
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY + 0.01F,
                     message.posZ,
                     (float)Math.sin(rad) * 0.65F,
                     0.0,
                     (float)Math.cos(rad) * 0.65F,
                     9509639,
                     3.0F,
                     10,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      OAK_GENERATOR(
         (message, level) -> {
            Integer sapX = message.data.getFirst();
            Integer sapY = message.data.get(1);
            Integer sapZ = message.data.get(2);
            boolean releaseAura = message.data.get(3) > 0;

            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnParticleStream(
                     sapX.intValue() + 0.5F + (float)level.random.nextGaussian() * 3.0F,
                     sapY.intValue() + 0.5F + level.random.nextFloat() * 4.0F,
                     sapZ.intValue() + 0.5F + (float)level.random.nextGaussian() * 3.0F,
                     message.posX + 0.5F,
                     message.posY + 0.5F,
                     message.posZ + 0.5F,
                     0.6F,
                     BiomeColors.getAverageGrassColor(level, new BlockPos(sapX, sapY, sapZ)),
                     1.5F
                  );
            }

            if (releaseAura) {
               for (int i = level.random.nextInt(10) + 10; i >= 0; i--) {
                  NaturesAuraAPI.instance()
                     .spawnMagicParticle(
                        message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                        message.posY + 1.01F,
                        message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                        level.random.nextGaussian() * 0.03F,
                        level.random.nextFloat() * 0.04F + 0.04F,
                        level.random.nextGaussian() * 0.03F,
                        6081584,
                        1.0F + level.random.nextFloat() * 1.5F,
                        60,
                        0.0F,
                        false,
                        true
                     );
               }
            }
         }
      ),
      OFFERING_TABLE(
         (message, level) -> {
            Integer genX = message.data.getFirst();
            Integer genY = message.data.get(1);
            Integer genZ = message.data.get(2);

            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY + 0.5F,
                     message.posZ,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextFloat() * 0.25F,
                     level.random.nextGaussian() * 0.02F,
                     16756221,
                     1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }

            for (int i = level.random.nextInt(50) + 30; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     genX.intValue() + 0.5F + level.random.nextGaussian() * 2.5,
                     genY.intValue() + 0.1F,
                     genZ.intValue() + 0.5F + level.random.nextGaussian() * 2.5,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.01F,
                     level.random.nextGaussian() * 0.01F,
                     13886719,
                     1.5F,
                     150,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      PICKUP_STOPPER(
         (message, level) -> NaturesAuraAPI.instance()
            .spawnMagicParticle(
               message.posX,
               message.posY + 0.4F,
               message.posZ,
               level.random.nextGaussian() * 0.005F,
               level.random.nextFloat() * 0.005F,
               level.random.nextGaussian() * 0.005F,
               13381910,
               1.5F,
               40,
               0.0F,
               false,
               true
            )
      ),
      SPAWN_LAMP(
         (message, level) -> {
            for (int i = level.random.nextInt(5) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.3F + level.random.nextFloat() * 0.4F,
                     message.posY + 0.15F + level.random.nextFloat() * 0.5F,
                     message.posZ + 0.3F + level.random.nextFloat() * 0.4F,
                     0.0,
                     0.0,
                     0.0,
                     16032066,
                     1.0F,
                     30,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      ANIMAL_GEN_CREATE(
         (message, level) -> {
            for (int i = level.random.nextInt(5) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 1.01F,
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     13710088,
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      ANIMAL_GEN_CONSUME(
         (message, level) -> {
            boolean child = message.data.getFirst() > 0;
            float height = message.data.get(1).intValue() / 10.0F;
            Integer genX = message.data.get(2);
            Integer genY = message.data.get(3);
            Integer genZ = message.data.get(4);

            for (int i = child ? level.random.nextInt(10) + 10 : level.random.nextInt(20) + 20; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextGaussian() * 0.25,
                     message.posY + height * 0.75F + level.random.nextGaussian() * 0.25,
                     message.posZ + level.random.nextGaussian() * 0.25,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.01F,
                     level.random.nextGaussian() * 0.01F,
                     4388040,
                     level.random.nextFloat() * (child ? 0.5F : 2.0F) + 1.0F,
                     level.random.nextInt(30) + 40,
                     0.0F,
                     true,
                     true
                  );
            }

            NaturesAuraAPI.instance()
               .spawnParticleStream(
                  message.posX,
                  message.posY + height * 0.75F,
                  message.posZ,
                  genX.intValue() + 0.5F,
                  genY.intValue() + 0.5F,
                  genZ.intValue() + 0.5F,
                  0.15F,
                  4310260,
                  child ? 1.5F : 3.0F
               );
         }
      ),
      END_FLOWER_DECAY(
         (message, level) -> {
            Integer color = message.data.getFirst();

            for (int i = level.random.nextInt(10) + 20; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat(),
                     message.posY + level.random.nextFloat(),
                     message.posZ + level.random.nextFloat(),
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.01F,
                     level.random.nextGaussian() * 0.01F,
                     color,
                     1.5F,
                     80,
                     0.0F,
                     true,
                     true
                  );
            }
         }
      ),
      ANIMAL_SPAWNER(
         (message, level) -> {
            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY + 0.5F,
                     message.posZ,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextFloat() * 0.02F,
                     level.random.nextGaussian() * 0.02F,
                     1488818,
                     1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      RF_CONVERTER(
         (message, level) -> {
            for (int i = level.random.nextInt(5) + 2; i >= 0; i--) {
               Multiblocks.RF_CONVERTER
                  .forEach(
                     BlockPos.containing(message.posX, message.posY, message.posZ),
                     'R',
                     (blockPos, matcher) -> {
                        if (level.random.nextFloat() < 0.35F) {
                           NaturesAuraAPI.instance()
                              .spawnParticleStream(
                                 blockPos.getX() + level.random.nextFloat(),
                                 blockPos.getY() + level.random.nextFloat(),
                                 blockPos.getZ() + level.random.nextFloat(),
                                 message.posX + level.random.nextFloat(),
                                 message.posY + level.random.nextFloat(),
                                 message.posZ + level.random.nextFloat(),
                                 0.05F,
                                 16718341,
                                 1.5F
                              );
                        }

                        return true;
                     }
                  );
            }
         }
      ),
      END_FLOWER_CONSUME(
         (message, level) -> {
            Integer color = message.data.getFirst();

            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX,
                     message.posY + 0.5F,
                     message.posZ,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.01F,
                     level.random.nextGaussian() * 0.01F,
                     color,
                     1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      MOVER_CART(
         (message, level) -> {
            float motionX = message.data.getFirst().intValue() / 100.0F;
            float motionY = message.data.get(1).intValue() / 100.0F;
            float motionZ = message.data.get(2).intValue() / 100.0F;

            for (int i = level.random.nextInt(60) + 30; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextGaussian() * 10.0,
                     message.posY + level.random.nextGaussian() * 10.0,
                     message.posZ + level.random.nextGaussian() * 10.0,
                     motionX * 0.2F,
                     motionY * 0.2F,
                     motionZ * 0.2F,
                     IAuraType.forLevel(level).getColor(),
                     2.0F,
                     30,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      MOSS_GENERATOR(
         (message, level) -> {
            for (int i = level.random.nextInt(30) + 30; i >= 0; i--) {
               int side = level.random.nextInt(3);
               float x = side != 0 ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.1F : -0.1F);
               float y = side != 1 ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.1F : -0.1F);
               float z = side != 2 ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.1F : -0.1F);
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + x, message.posY + y, message.posZ + z, 0.0, 0.0, 0.0, 1592333, level.random.nextFloat() + 1.0F, 30, 0.0F, true, true
                  );
            }

            for (int i = level.random.nextInt(20) + 10; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat(),
                     message.posY + 1.0F,
                     message.posZ + level.random.nextFloat(),
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     6081584,
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     true,
                     true
                  );
            }
         }
      ),
      FIREWORK_GEN(
         (message, level) -> {
            Integer goalX = message.data.getFirst();
            Integer goalY = message.data.get(1);
            Integer goalZ = message.data.get(2);
            NaturesAuraAPI.instance().setParticleSpawnRange(64);

            for (int i = level.random.nextInt(30) + 30; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnParticleStream(
                     message.posX + (float)level.random.nextGaussian(),
                     message.posY + (float)level.random.nextGaussian(),
                     message.posZ + (float)level.random.nextGaussian(),
                     goalX.intValue() + 0.25F + level.random.nextFloat() * 0.5F,
                     goalY.intValue() + 0.25F + level.random.nextFloat() * 0.5F,
                     goalZ.intValue() + 0.25F + level.random.nextFloat() * 0.5F,
                     0.65F,
                     message.data.get(3 + level.random.nextInt(message.data.size() - 3)),
                     1.0F
                  );
            }

            NaturesAuraAPI.instance().setParticleSpawnRange(32);
         }
      ),
      DIMENSION_RAIL(
         (message, level) -> {
            float width = message.data.getFirst().intValue() / 100.0F;
            float height = message.data.get(1).intValue() / 100.0F;
            float depth = message.data.get(2).intValue() / 100.0F;

            for (int i = level.random.nextInt(100) + 50; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextFloat() * width,
                     message.posY + level.random.nextFloat() * height,
                     message.posZ + level.random.nextFloat() * depth,
                     0.0,
                     0.0,
                     0.0,
                     14028031,
                     1.0F + level.random.nextFloat(),
                     60,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      PROJECTILE_GEN(
         (message, level) -> {
            Integer x = message.data.getFirst();
            Integer y = message.data.get(1);
            Integer z = message.data.get(2);

            for (int i = level.random.nextInt(10) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     x.intValue() + 0.25F + level.random.nextFloat() * 0.5F,
                     y.intValue() + 1.01F,
                     z.intValue() + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     6081584,
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }

            for (int i = level.random.nextInt(10) + 10; i >= 0; i--) {
               level.addParticle(
                  ParticleTypes.FIREWORK,
                  message.posX,
                  message.posY,
                  message.posZ,
                  level.random.nextGaussian() * 0.03F,
                  level.random.nextGaussian() * 0.03F,
                  level.random.nextGaussian() * 0.03F
               );
            }
         }
      ),
      BLAST_FURNACE_BOOSTER(
         (message, level) -> {
            boolean worked = message.data.getFirst() > 0;

            for (int i = level.random.nextInt(10) + 5; i >= 0; i--) {
               level.addParticle(
                  ParticleTypes.CAMPFIRE_COSY_SMOKE,
                  message.posX + 0.3125F + level.random.nextInt(6) / 16.0F,
                  message.posY + 0.6F,
                  message.posZ + 0.3125F + level.random.nextInt(6) / 16.0F,
                  level.random.nextGaussian() * 0.005F,
                  level.random.nextFloat() * 0.02F + 0.01F,
                  level.random.nextGaussian() * 0.005F
               );
            }

            if (worked) {
               BlockPos pos = BlockPos.containing(message.posX, message.posY, message.posZ);
               int color = IAuraChunk.getAuraChunk(level, pos).getType().getColor();

               for (int i = level.random.nextInt(10) + 10; i >= 0; i--) {
                  NaturesAuraAPI.instance()
                     .spawnParticleStream(
                        message.posX + (float)level.random.nextGaussian() * 5.0F,
                        message.posY + level.random.nextFloat() * 5.0F,
                        message.posZ + (float)level.random.nextGaussian() * 5.0F,
                        message.posX + 0.5F,
                        message.posY + 0.5F,
                        message.posZ + 0.5F,
                        0.25F,
                        color,
                        0.5F + level.random.nextFloat()
                     );
               }
            }
         }
      ),
      ANIMAL_CONTAINER(
         (message, level) -> {
            for (int i = level.random.nextInt(2) + 1; i > 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + level.random.nextGaussian() * 0.15F,
                     message.posY - level.random.nextFloat() * 0.5F,
                     message.posZ + level.random.nextGaussian() * 0.15F,
                     0.0,
                     0.0,
                     0.0,
                     4385269,
                     1.0F + level.random.nextFloat() * 2.0F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      SNOW_CREATOR(
         (message, level) -> {
            BlockPos pos = BlockPos.containing(message.posX, message.posY, message.posZ);
            int color = IAuraChunk.getAuraChunk(level, pos).getType().getColor();

            for (int i = level.random.nextInt(3) + 1; i > 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnParticleStream(
                     message.posX + (float)level.random.nextGaussian() * 5.0F,
                     message.posY + level.random.nextFloat() * 5.0F,
                     message.posZ + (float)level.random.nextGaussian() * 5.0F,
                     message.posX + 0.5F,
                     message.posY + 0.5F,
                     message.posZ + 0.5F,
                     0.25F,
                     color,
                     0.5F + level.random.nextFloat()
                  );
            }
         }
      ),
      CHORUS_GENERATOR(
         (message, level) -> {
            Integer chorusX = message.data.getFirst();
            Integer chorusY = message.data.get(1);
            Integer chorusZ = message.data.get(2);

            for (int i = level.random.nextInt(5) + 3; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     chorusX.intValue() + level.random.nextFloat(),
                     chorusY.intValue() + level.random.nextFloat(),
                     chorusZ.intValue() + level.random.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     12258275,
                     1.0F + level.random.nextFloat(),
                     50,
                     0.0F,
                     false,
                     true
                  );
            }

            for (int i = level.random.nextInt(5) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 1.01F,
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     IAuraType.forLevel(level).getColor(),
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      TIMER_RESET(
         (message, level) -> {
            Integer color = message.data.getFirst();

            for (int i = level.random.nextInt(10) + 15; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.3125F + level.random.nextFloat() * 6.0F / 16.0F,
                     message.posY + 0.125F + level.random.nextFloat() * 8.0F / 16.0F,
                     message.posZ + 0.3125F + level.random.nextFloat() * 6.0F / 16.0F,
                     0.0,
                     0.0,
                     0.0,
                     color,
                     2.0F,
                     40 + level.random.nextInt(20),
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      STRUCTURE_FINDER(
         (message, level) -> {
            EntityStructureFinder entity = (EntityStructureFinder)level.getEntity(message.data.getFirst());
            LevelRenderer renderer = Minecraft.getInstance().levelRenderer;
            double d0 = message.posX + 0.5;
            double d13 = message.posY;
            double d18 = message.posZ + 0.5;

            for (int j2 = 0; j2 < 8; j2++) {
               renderer.addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, entity.getItem()),
                  false,
                  d0,
                  d13,
                  d18,
                  level.random.nextGaussian() * 0.15,
                  level.random.nextDouble() * 0.2,
                  level.random.nextGaussian() * 0.15
               );
            }

            int color = (Integer)entity.getEntityData().get(EntityStructureFinder.COLOR);

            for (double d24 = 0.0; d24 < Math.PI * 2; d24 += Math.PI / 20) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     d0 + Math.cos(d24) * 5.0,
                     d13 - 0.4,
                     d18 + Math.sin(d24) * 5.0,
                     Math.cos(d24) * -2.0,
                     0.0,
                     Math.sin(d24) * -2.0,
                     color,
                     2.0F,
                     60,
                     0.0F,
                     false,
                     true
                  );
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     d0 + Math.cos(d24) * 5.0,
                     d13 - 0.4,
                     d18 + Math.sin(d24) * 5.0,
                     Math.cos(d24) * -2.5,
                     0.0,
                     Math.sin(d24) * -2.5,
                     color,
                     2.0F,
                     60,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      SLIME_SPLIT_GEN_CREATE(
         (message, level) -> {
            for (int i = level.random.nextInt(5) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + 0.25F + level.random.nextFloat() * 0.5F,
                     message.posY + 1.01F,
                     message.posZ + 0.25F + level.random.nextFloat() * 0.5F,
                     level.random.nextGaussian() * 0.01F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.01F,
                     message.data.getFirst(),
                     1.0F + level.random.nextFloat() * 1.5F,
                     40,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      SLIME_SPLIT_GEN_START(
         (message, level) -> {
            Integer x = message.data.getFirst();
            Integer y = message.data.get(1);
            Integer z = message.data.get(2);
            Integer color = message.data.get(3);

            for (int i = level.random.nextInt(10) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + (float)level.random.nextGaussian() * 0.5F,
                     message.posY + (float)level.random.nextGaussian() * 0.5F,
                     message.posZ + (float)level.random.nextGaussian() * 0.5F,
                     level.random.nextGaussian() * 0.02F,
                     level.random.nextFloat() * 0.04F + 0.02F,
                     level.random.nextGaussian() * 0.02F,
                     color,
                     level.random.nextFloat() + 1.0F,
                     level.random.nextInt(20) + 20,
                     0.0F,
                     false,
                     true
                  );
            }

            for (int i = level.random.nextInt(10) + 5; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnParticleStream(
                     message.posX + (float)level.random.nextGaussian() * 0.5F,
                     message.posY + (float)level.random.nextGaussian() * 0.5F,
                     message.posZ + (float)level.random.nextGaussian() * 0.5F,
                     x.intValue() + 0.5F,
                     y.intValue() + 0.5F,
                     z.intValue() + 0.5F,
                     0.2F,
                     color,
                     level.random.nextFloat() + 1.0F
                  );
            }
         }
      ),
      PET_REVIVER(
         (message, level) -> {
            for (int i = level.random.nextInt(50) + 150; i >= 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + (float)level.random.nextGaussian() * 0.4F,
                     message.posY + (float)level.random.nextGaussian() * 0.4F,
                     message.posZ + (float)level.random.nextGaussian() * 0.4F,
                     level.random.nextGaussian() * 0.002F,
                     level.random.nextFloat() * 0.001F + 0.002F,
                     level.random.nextGaussian() * 0.002F,
                     message.data.getFirst(),
                     level.random.nextFloat() * 2.0F + 1.0F,
                     level.random.nextInt(50) + 50,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      ),
      CANNOT_GENERATE(
         (message, level) -> {
            for (int i = level.random.nextInt(100) + 50; i >= 0; i--) {
               boolean side = level.random.nextBoolean();
               float x = side ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.05F : -0.05F);
               float z = !side ? level.random.nextFloat() : (level.random.nextBoolean() ? 1.05F : -0.05F);
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     message.posX + x,
                     message.posY + 0.1F + level.random.nextFloat() * 0.98F,
                     message.posZ + z,
                     level.random.nextGaussian() * 0.001F,
                     level.random.nextGaussian() * 0.001F,
                     level.random.nextGaussian() * 0.001F,
                     14682895,
                     level.random.nextFloat() + 0.25F,
                     50,
                     0.0F,
                     true,
                     true
                  );
            }
         }
      );

      public final BiConsumer<PacketParticles, Level> action;

      Type(BiConsumer<PacketParticles, Level> action) {
         this.action = action;
      }
   }
}
