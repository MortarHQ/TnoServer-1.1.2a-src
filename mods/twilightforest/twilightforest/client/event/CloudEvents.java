package twilightforest.client.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome.Precipitation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.apache.commons.lang3.tuple.Pair;
import twilightforest.block.CloudBlock;
import twilightforest.client.renderer.TFWeatherRenderer;
import twilightforest.config.TFConfig;
import twilightforest.util.Vec2i;

public class CloudEvents {
   private static final List<CloudEvents.PrecipitationRenderHelper> RENDER_HELPER = new ArrayList<>();

   protected static void tickWeatherEffects(Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (!mc.isPaused() && mc.level != null && TFConfig.getClientCloudBlockPrecipitationDistance() > 0) {
         Vec3 vec3 = mc.gameRenderer.getMainCamera().getPosition();
         if (mc.level.getGameTime() % 10L == 0L) {
            RENDER_HELPER.clear();
            double camX = vec3.x();
            double camY = vec3.y();
            double camZ = vec3.z();
            int floorX = Mth.floor(camX);
            int floorY = Mth.floor(camY);
            int floorZ = Mth.floor(camZ);
            int renderDistance = Minecraft.useFancyGraphics() ? 10 : 5;
            int precipitationDistance = TFConfig.getClientCloudBlockPrecipitationDistance();
            MutableBlockPos pos = new MutableBlockPos();

            for (int roofZ = floorZ - renderDistance; roofZ <= floorZ + renderDistance; roofZ++) {
               for (int roofX = floorX - renderDistance; roofX <= floorX + renderDistance; roofX++) {
                  int lastBadYLevel = Integer.MIN_VALUE;

                  for (int roofY = floorY - renderDistance; roofY < floorY + precipitationDistance + renderDistance; roofY++) {
                     boolean skipLoop = roofY == lastBadYLevel + 1;
                     pos.set(roofX, roofY, roofZ);
                     if (Types.MOTION_BLOCKING.isOpaque().test(mc.level.getBlockState(pos))) {
                        lastBadYLevel = roofY;
                     }

                     if (!skipLoop && mc.level.getBlockState(pos).getBlock() instanceof CloudBlock cloudBlock) {
                        Pair<Precipitation, Float> precipitationRainLevelPair = cloudBlock.getCurrentPrecipitation(pos, mc.level, mc.level.getRainLevel(1.0F));
                        if (precipitationRainLevelPair.getLeft() != Precipitation.NONE) {
                           int highestRainyBlock = roofY;
                           int y = roofY - 1;

                           while (y > roofY - precipitationDistance && !Types.MOTION_BLOCKING.isOpaque().test(mc.level.getBlockState(pos.atY(y)))) {
                              highestRainyBlock = y--;
                           }

                           if (highestRainyBlock != roofY) {
                              RENDER_HELPER.add(
                                 new CloudEvents.PrecipitationRenderHelper(
                                    pos.immutable(),
                                    (Precipitation)precipitationRainLevelPair.getLeft(),
                                    (Float)precipitationRainLevelPair.getRight(),
                                    highestRainyBlock
                                 )
                              );
                           }
                        }
                     }
                  }
               }
            }
         }

         if (!RENDER_HELPER.isEmpty()) {
            RandomSource randomsource = RandomSource.create(mc.levelRenderer.getTicks() * 312987231L);
            BlockPos particlePos = null;
            int particleCount = 100 / (mc.options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);
            boolean yetToMakeASound = true;
            BlockPos camPos = BlockPos.containing(vec3);
            List<Vec2i> particleChecks = new ArrayList<>();

            for (int i = 0; i < particleCount; i++) {
               particleChecks.add(new Vec2i(randomsource.nextInt(21) - 10 + camPos.getX(), randomsource.nextInt(21) - 10 + camPos.getZ()));
            }

            for (CloudEvents.PrecipitationRenderHelper helper : RENDER_HELPER) {
               if (helper.precipitation() == Precipitation.RAIN) {
                  for (Vec2i vec2 : particleChecks) {
                     if (vec2.x == helper.cloudPos().getX() && vec2.z == helper.cloudPos().getZ()) {
                        BlockPos highestRainyPos = helper.cloudPos().atY(helper.rainOnY());
                        if (Types.MOTION_BLOCKING.isOpaque().test(mc.level.getBlockState(highestRainyPos.below()))) {
                           if (yetToMakeASound && particlePos != null && randomsource.nextInt(3) < mc.levelRenderer.rainSoundTime++) {
                              mc.levelRenderer.rainSoundTime = 0;
                              if (particlePos.getY() > camPos.getY() + 1
                                 && mc.level.getHeightmapPos(Types.MOTION_BLOCKING, camPos).getY() > Mth.floor(camPos.getY())) {
                                 mc.level.playLocalSound(particlePos, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                              } else {
                                 mc.level.playLocalSound(particlePos, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                              }

                              yetToMakeASound = false;
                           }

                           if (highestRainyPos.getY() > mc.level.getMinBuildHeight()
                              && highestRainyPos.getY() <= camPos.getY() + 10
                              && highestRainyPos.getY() >= camPos.getY() - 10) {
                              particlePos = highestRainyPos.below();
                              if (mc.options.particles().get() == ParticleStatus.MINIMAL) {
                                 break;
                              }

                              double particleX = randomsource.nextDouble();
                              double particleZ = randomsource.nextDouble();
                              BlockState blockstate = mc.level.getBlockState(particlePos);
                              FluidState fluidstate = mc.level.getFluidState(particlePos);
                              VoxelShape voxelshape = blockstate.getCollisionShape(mc.level, particlePos);
                              double voxelMax = voxelshape.max(Axis.Y, particleX, particleZ);
                              double fluidMax = fluidstate.getHeight(mc.level, particlePos);
                              double particleY = Math.max(voxelMax, fluidMax);
                              ParticleOptions particleoptions = !fluidstate.is(FluidTags.LAVA)
                                    && !blockstate.is(Blocks.MAGMA_BLOCK)
                                    && !CampfireBlock.isLitCampfire(blockstate)
                                 ? ParticleTypes.RAIN
                                 : ParticleTypes.SMOKE;
                              mc.level
                                 .addParticle(
                                    particleoptions,
                                    particlePos.getX() + particleX,
                                    particlePos.getY() + particleY,
                                    particlePos.getZ() + particleZ,
                                    0.0,
                                    0.0,
                                    0.0
                                 );
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   protected static void renderPrecipitation(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_WEATHER && TFConfig.getClientCloudBlockPrecipitationDistance() > 0 && !RENDER_HELPER.isEmpty()) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level == null) {
            return;
         }

         float partialTick = minecraft.getTimer().getGameTimeDeltaPartialTick(false);
         LightTexture lightTexture = minecraft.gameRenderer.lightTexture();
         int ticks = minecraft.levelRenderer.getTicks();
         lightTexture.turnOnLightLayer();
         Vec3 vec3 = event.getCamera().getPosition();
         double camX = vec3.x();
         double camY = vec3.y();
         double camZ = vec3.z();
         int floorX = Mth.floor(camX);
         int floorY = Mth.floor(camY);
         int floorZ = Mth.floor(camZ);
         Tesselator tesselator = Tesselator.getInstance();
         BufferBuilder bufferbuilder = null;
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(Minecraft.useShaderTransparency());
         int renderDistance = Minecraft.useFancyGraphics() ? 10 : 5;
         int tesselatorCheck = -1;
         float fullTick = ticks + partialTick;
         RenderSystem.setShader(GameRenderer::getParticleShader);
         MutableBlockPos mutableBlockPos = new MutableBlockPos();

         for (CloudEvents.PrecipitationRenderHelper helper : RENDER_HELPER) {
            BlockPos pos = helper.cloudPos();
            int roofX = pos.getX();
            int roofZ = pos.getZ();
            int botY = Math.max(helper.rainOnY(), floorY - renderDistance);
            int topY = Math.min(pos.getY(), floorY + renderDistance);
            if (topY - botY > 0) {
               int rainS = Mth.clamp((roofZ - floorZ + 16) * 32 + roofX - floorX + 16, 0, 1023);
               double rainX = TFWeatherRenderer.rainxs[rainS] * 0.5;
               double rainZ = TFWeatherRenderer.rainzs[rainS] * 0.5;
               mutableBlockPos.set(roofX, camY, roofZ);
               RandomSource random = RandomSource.create((long)roofX * roofX * 3121L + roofX * 45238971L ^ (long)roofZ * roofZ * 418711L + roofZ * 13761L);
               if (helper.precipitation() == Precipitation.RAIN) {
                  if (tesselatorCheck != 0) {
                     if (tesselatorCheck >= 0) {
                        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                     }

                     tesselatorCheck = 0;
                     RenderSystem.setShaderTexture(0, TFWeatherRenderer.RAIN_TEXTURES);
                     bufferbuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
                  }

                  int offset = ticks + roofX * roofX * 3121 + roofX * 45238971 + roofZ * roofZ * 418711 + roofZ * 13761 & 31;
                  float uvOffset = -(offset + partialTick) / 32.0F * (3.0F + random.nextFloat());
                  double xDiff = roofX + 0.5 - camX;
                  double zDiff = roofZ + 0.5 - camZ;
                  float distance = (float)Math.sqrt(xDiff * xDiff + zDiff * zDiff) / renderDistance;
                  float alpha = ((1.0F - distance * distance) * 0.5F + 0.5F) * helper.precipitationLevel();
                  mutableBlockPos.set(roofX, Math.max(helper.rainOnY(), floorY), roofZ);
                  int lightColor = LevelRenderer.getLightColor(minecraft.level, mutableBlockPos);
                  bufferbuilder.addVertex((float)(roofX - camX - rainX + 0.5), (float)(topY - camY), (float)(roofZ - camZ - rainZ + 0.5))
                     .setUv(0.0F, botY * 0.25F + uvOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setLight(lightColor);
                  bufferbuilder.addVertex((float)(roofX - camX + rainX + 0.5), (float)(topY - camY), (float)(roofZ - camZ + rainZ + 0.5))
                     .setUv(1.0F, botY * 0.25F + uvOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setLight(lightColor);
                  bufferbuilder.addVertex((float)(roofX - camX + rainX + 0.5), (float)(botY - camY), (float)(roofZ - camZ + rainZ + 0.5))
                     .setUv(1.0F, topY * 0.25F + uvOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setLight(lightColor);
                  bufferbuilder.addVertex((float)(roofX - camX - rainX + 0.5), (float)(botY - camY), (float)(roofZ - camZ - rainZ + 0.5))
                     .setUv(0.0F, topY * 0.25F + uvOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setLight(lightColor);
               } else if (helper.precipitation() == Precipitation.SNOW) {
                  if (tesselatorCheck != 1) {
                     if (tesselatorCheck == 0) {
                        BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
                     }

                     tesselatorCheck = 1;
                     RenderSystem.setShaderTexture(0, TFWeatherRenderer.SNOW_TEXTURES);
                     bufferbuilder = tesselator.begin(Mode.QUADS, DefaultVertexFormat.PARTICLE);
                  }

                  float offset = -((ticks & 511) + partialTick) / 512.0F;
                  float uOffset = (float)(random.nextDouble() + fullTick * 0.01 * (float)random.nextGaussian());
                  float vOffset = (float)(random.nextDouble() + fullTick * (float)random.nextGaussian() * 0.001);
                  double xDiff = roofX + 0.5 - camX;
                  double zDiff = roofZ + 0.5 - camZ;
                  float distance = (float)Math.sqrt(xDiff * xDiff + zDiff * zDiff) / renderDistance;
                  float alpha = ((1.0F - distance * distance) * 0.3F + 0.5F) * helper.precipitationLevel();
                  mutableBlockPos.set(roofX, Math.max(helper.rainOnY(), floorY), roofZ);
                  int lightColor = LevelRenderer.getLightColor(minecraft.level, mutableBlockPos);
                  int v = lightColor >> 16 & 65535;
                  int u = lightColor & 65535;
                  v = (v * 3 + 240) / 4;
                  u = (u * 3 + 240) / 4;
                  bufferbuilder.addVertex((float)(roofX - camX - rainX + 0.5), (float)(topY - camY), (float)(roofZ - camZ - rainZ + 0.5))
                     .setUv(0.0F + uOffset, botY * 0.25F + offset + vOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setUv2(u, v);
                  bufferbuilder.addVertex((float)(roofX - camX + rainX + 0.5), (float)(topY - camY), (float)(roofZ - camZ + rainZ + 0.5))
                     .setUv(1.0F + uOffset, botY * 0.25F + offset + vOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setUv2(u, v);
                  bufferbuilder.addVertex((float)(roofX - camX + rainX + 0.5), (float)(botY - camY), (float)(roofZ - camZ + rainZ + 0.5))
                     .setUv(1.0F + uOffset, topY * 0.25F + offset + vOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setUv2(u, v);
                  bufferbuilder.addVertex((float)(roofX - camX - rainX + 0.5), (float)(botY - camY), (float)(roofZ - camZ - rainZ + 0.5))
                     .setUv(0.0F + uOffset, topY * 0.25F + offset + vOffset)
                     .setColor(1.0F, 1.0F, 1.0F, alpha)
                     .setUv2(u, v);
               }
            }
         }

         if (tesselatorCheck >= 0) {
            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         lightTexture.turnOffLightLayer();
      }
   }

   record PrecipitationRenderHelper(BlockPos cloudPos, Precipitation precipitation, float precipitationLevel, int rainOnY) {
   }
}
