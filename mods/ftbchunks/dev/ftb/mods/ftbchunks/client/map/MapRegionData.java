package dev.ftb.mods.ftbchunks.client.map;

import dev.ftb.mods.ftbchunks.FTBChunks;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftblibrary.math.XZ;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.imageio.ImageIO;

public class MapRegionData {
   public final MapRegion region;
   public final short[] height = new short[262144];
   public final short[] waterLightAndBiome = new short[262144];
   public final int[] foliage = new int[262144];
   public final int[] grass = new int[262144];
   public final int[] water = new int[262144];

   public MapRegionData(MapRegion r) {
      this.region = r;
      Arrays.fill(this.height, (short)-32767);
      FTBChunks.LOGGER.debug("constructing map region data for {} / {}", this.region, this.region.pos);
   }

   public int getBlockIndex(int index) {
      int f = this.foliage[index] >> 24 & 0xFF;
      int g = this.grass[index] >> 24 & 0xFF;
      int w = this.water[index] >> 24 & 0xFF;
      return f << 16 | g << 8 | w;
   }

   public void setBlockIndex(int index, int block) {
      this.foliage[index] = this.foliage[index] & 16777215 | (block >> 16 & 0xFF) << 24;
      this.grass[index] = this.grass[index] & 16777215 | (block >> 8 & 0xFF) << 24;
      this.water[index] = this.water[index] & 16777215 | (block & 0xFF) << 24;
   }

   public void read() throws IOException {
      if (Files.notExists(this.region.dimension.directory)) {
         Files.createDirectories(this.region.dimension.directory);
      }

      Path file = this.region.dimension.directory.resolve(this.region.pos.toRegionString() + ".zip");
      if (Files.exists(file) && Files.isReadable(file)) {
         FTBChunks.LOGGER.debug("reading data from {} for {} / {}", file.getFileName(), this.region, this.region.pos);

         try (
            BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file));
            ZipInputStream zis = new ZipInputStream(bufferedInputStream);
         ) {
            BufferedImage dataImage = null;
            BufferedImage foliageImage = null;
            BufferedImage grassImage = null;
            BufferedImage waterImage = null;
            BufferedImage blocksImage = null;
            int version = 0;

            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
               switch (ze.getName()) {
                  case "chunks.dat":
                     DataInputStream stream = new DataInputStream(zis);
                     stream.readByte();
                     version = stream.readByte();
                     int nMapChunks = stream.readShort();

                     for (int i = 0; i < nMapChunks; i++) {
                        int chunkVersion = version >= 2 ? stream.readByte() : 0;
                        int x = stream.readByte();
                        int z = stream.readByte();
                        long lastModified = stream.readLong();
                        MapChunk mapChunk = Objects.requireNonNullElse(this.region.getMapChunk(XZ.of(x, z)), new MapChunk(this.region, XZ.of(x, z)));
                        mapChunk.setVersion(chunkVersion);
                        mapChunk.setModified(lastModified);
                        synchronized (this.region.dimension.getManager().lock) {
                           this.region.addMapChunk(mapChunk);
                        }
                     }
                     break;
                  case "data.png":
                     dataImage = ImageIO.read(zis);
                     break;
                  case "foliage.png":
                     foliageImage = ImageIO.read(zis);
                     break;
                  case "grass.png":
                     grassImage = ImageIO.read(zis);
                     break;
                  case "water.png":
                     waterImage = ImageIO.read(zis);
                     break;
                  case "blocks.png":
                     blocksImage = ImageIO.read(zis);
                     break;
                  default:
                     FTBChunks.LOGGER.warn("Unknown file " + ze.getName() + " in " + file.toAbsolutePath());
               }
            }

            for (int y = 0; y < 512; y++) {
               for (int x = 0; x < 512; x++) {
                  int index = x + y * 512;
                  this.height[index] = (short)(dataImage.getRGB(x, y) >> 16 & 65535);
                  if (version < 3 && this.height[index] == 0) {
                     this.height[index] = -32767;
                     this.region.markDirty();
                  }

                  this.waterLightAndBiome[index] = (short)(dataImage.getRGB(x, y) & 65535);
                  this.foliage[index] = foliageImage.getRGB(x, y);
                  this.grass[index] = grassImage.getRGB(x, y);
                  this.water[index] = waterImage.getRGB(x, y);
                  this.setBlockIndex(index, blocksImage.getRGB(x, y));
               }
            }
         }
      }
   }

   public void write() throws IOException {
      Collection<MapChunk> chunkList;
      synchronized (this.region.dimension.getManager().lock) {
         chunkList = this.region.getModifiedChunks();
      }

      if (!chunkList.isEmpty()) {
         BufferedImage dataImage = new BufferedImage(512, 512, 2);
         BufferedImage foliageImage = new BufferedImage(512, 512, 1);
         BufferedImage grassImage = new BufferedImage(512, 512, 1);
         BufferedImage waterImage = new BufferedImage(512, 512, 1);
         BufferedImage blocksImage = new BufferedImage(512, 512, 1);

         for (int y = 0; y < 512; y++) {
            for (int x = 0; x < 512; x++) {
               int index = x + y * 512;
               dataImage.setRGB(x, y, (this.height[index] & '\uffff') << 16 | this.waterLightAndBiome[index] & 65535);
               foliageImage.setRGB(x, y, 0xFF000000 | this.foliage[index] & 16777215);
               grassImage.setRGB(x, y, 0xFF000000 | this.grass[index] & 16777215);
               waterImage.setRGB(x, y, 0xFF000000 | this.water[index] & 16777215);
               blocksImage.setRGB(x, y, 0xFF000000 | this.getBlockIndex(index));
            }
         }

         FTBChunksClient.MAP_EXECUTOR.execute(() -> {
            try {
               this.writeData(chunkList, dataImage, foliageImage, grassImage, waterImage, blocksImage);
            } catch (Exception ex) {
               FTBChunks.LOGGER.error("Failed to write map region " + this.region.dimension + ":" + this.region + ":");
               ex.printStackTrace();
            }
         });
      }
   }

   private void writeData(
      Collection<MapChunk> chunkList,
      BufferedImage dataImage,
      BufferedImage foliageImage,
      BufferedImage grassImage,
      BufferedImage waterImage,
      BufferedImage blocksImage
   ) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(baos);

      try (ZipOutputStream out = new ZipOutputStream(bufferedOutputStream)) {
         DataOutputStream stream = new DataOutputStream(out);
         out.putNextEntry(new ZipEntry("chunks.dat"));
         stream.writeByte(0);
         stream.writeByte(3);
         stream.writeShort(chunkList.size());

         for (MapChunk chunk : chunkList) {
            chunk.writeData(stream);
         }

         out.closeEntry();
         out.putNextEntry(new ZipEntry("data.png"));
         ImageIO.write(dataImage, "PNG", out);
         out.closeEntry();
         out.putNextEntry(new ZipEntry("foliage.png"));
         ImageIO.write(foliageImage, "PNG", out);
         out.closeEntry();
         out.putNextEntry(new ZipEntry("grass.png"));
         ImageIO.write(grassImage, "PNG", out);
         out.closeEntry();
         out.putNextEntry(new ZipEntry("water.png"));
         ImageIO.write(waterImage, "PNG", out);
         out.closeEntry();
         out.putNextEntry(new ZipEntry("blocks.png"));
         ImageIO.write(blocksImage, "PNG", out);
         out.closeEntry();
      } catch (Throwable var16) {
         try {
            bufferedOutputStream.close();
         } catch (Throwable var13) {
            var16.addSuppressed(var13);
         }

         throw var16;
      }

      bufferedOutputStream.close();
      if (Files.notExists(this.region.dimension.directory)) {
         Files.createDirectories(this.region.dimension.directory);
      }

      FTBChunks.LOGGER.debug("writing data to {} for {} / {}", this.region.pos.toRegionString() + ".zip", this.region, this.region.pos);
      Files.write(this.region.dimension.directory.resolve(this.region.pos.toRegionString() + ".zip"), baos.toByteArray());
   }

   public MapChunk getChunk(XZ pos) {
      XZ effectivePos = pos.x() == (pos.x() & 31) && pos.z() == (pos.z() & 31) ? pos : XZ.of(pos.x() & 31, pos.z() & 31);
      synchronized (this.region.dimension.getManager().lock) {
         return this.region.getOrCreateMapChunk(effectivePos);
      }
   }
}
