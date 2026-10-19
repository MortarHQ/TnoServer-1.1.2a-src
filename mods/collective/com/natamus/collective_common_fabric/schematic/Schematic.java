package com.natamus.collective_common_fabric.schematic;

import com.mojang.brigadier.StringReader;
import com.mojang.datafixers.util.Pair;
import com.natamus.collective_common_fabric.data.Constants;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2259;
import net.minecraft.class_2338;
import net.minecraft.class_2378;
import net.minecraft.class_2487;
import net.minecraft.class_2499;
import net.minecraft.class_2680;
import net.minecraft.class_7923;
import net.minecraft.class_7924;

public class Schematic {
   private int size;
   private short width;
   private short height;
   private short length;
   private int offsetX;
   private int offsetY;
   private int offsetZ;
   private boolean oldVersion;
   private HashMap<Integer, String> palette;
   private SchematicBlockObject[] blockObjects;
   private List<class_2487> blockEntities;
   private List<Pair<class_2338, class_2487>> entities;
   private boolean parsedCorrectly = false;

   public Schematic(InputStream inputStream) {
      new Schematic(inputStream, null);
   }

   public Schematic(InputStream inputStream, class_1937 level) {
      class_2378<class_2248> blockRegistry = class_7923.field_41175;
      if (level != null) {
         blockRegistry = level.method_30349().method_30530(class_7924.field_41254);
      }

      String type = "";

      try {
         class_2487 nbtdata = SchematicFunctions.readCompressed(inputStream);
         inputStream.close();
         if (nbtdata.method_10545("Schematic")) {
            nbtdata = nbtdata.method_10562("Schematic");
         }

         if (nbtdata.method_10545("Length")) {
            this.width = nbtdata.method_10568("Width");
            this.height = nbtdata.method_10568("Height");
            this.length = nbtdata.method_10568("Length");
         } else {
            class_2499 sizeList = nbtdata.method_10554("size", 3);
            this.width = (short)sizeList.method_10600(0);
            this.height = (short)sizeList.method_10600(1);
            this.length = (short)sizeList.method_10600(2);
         }

         this.size = this.width * this.height * this.length;
         if (nbtdata.method_10545("entities")) {
            type = "nbt";
         } else if (nbtdata.method_10545("DataVersion")) {
            type = "schem";
         } else {
            type = "schematic";
         }

         this.blockObjects = new SchematicBlockObject[this.size];
         this.entities = new ArrayList<>();
         switch (type) {
            case "schem":
               int[] blockData = new int[this.width * this.height * this.length];
               byte[] blockDataRaw;
               class_2487 palette;
               class_2499 tileentitynbtlist;
               if (nbtdata.method_10550("Version") == 2) {
                  blockDataRaw = nbtdata.method_10547("BlockData");
                  palette = nbtdata.method_10562("Palette");
                  tileentitynbtlist = nbtdata.method_10554("BlockEntities", 10);
                  class_2487 offsetCompoundTag = nbtdata.method_10562("Metadata");
                  this.offsetX = offsetCompoundTag.method_10550("WEOffsetX");
                  this.offsetY = offsetCompoundTag.method_10550("WEOffsetY");
                  this.offsetZ = offsetCompoundTag.method_10550("WEOffsetZ");
               } else {
                  class_2487 blocksCompoundTag = nbtdata.method_10562("Blocks");
                  blockDataRaw = blocksCompoundTag.method_10547("Data");
                  palette = blocksCompoundTag.method_10562("Palette");
                  tileentitynbtlist = blocksCompoundTag.method_10554("BlockEntities", 10);
                  int[] offsetArray = nbtdata.method_10561("Offset");
                  this.offsetX = offsetArray[0];
                  this.offsetY = offsetArray[1];
                  this.offsetZ = offsetArray[2];
               }

               int index = 0;

               for (int i = 0; i < blockDataRaw.length; index++) {
                  int value = 0;
                  int varintLength = 0;

                  while (true) {
                     value |= (blockDataRaw[i] & 127) << varintLength++ * 7;
                     if (varintLength <= 5) {
                        if ((blockDataRaw[i] & 128) != 128) {
                           i++;
                           blockData[index] = value;
                           break;
                        }

                        i++;
                        continue;
                     }
                  }
               }

               this.palette = new HashMap<>();

               for (String k : palette.method_10541()) {
                  this.palette.put(palette.method_10550(k), k);
               }

               int counter = 0;

               for (int y = 0; y < this.height; y++) {
                  for (int z = 0; z < this.length; z++) {
                     for (int x = 0; x < this.width; x++) {
                        class_2338 pos = new class_2338(x, y, z);
                        int id = blockData[counter];
                        if (id < 0) {
                           id *= -1;
                        }

                        class_2680 state = this.getStateFromID(blockRegistry, id);
                        this.blockObjects[counter] = new SchematicBlockObject(pos, state);
                        counter++;
                     }
                  }
               }

               this.blockEntities = new ArrayList<>();

               for (int t = 0; t < tileentitynbtlist.size(); t++) {
                  this.blockEntities.add(tileentitynbtlist.method_10602(t));
               }

               this.parsedCorrectly = true;
               return;
            case "schematic":
               byte[] blockIDs_byte = nbtdata.method_10547("Blocks");
               int[] blockIDs = new int[this.size];

               for (int x = 0; x < blockIDs_byte.length; x++) {
                  blockIDs[x] = Byte.toUnsignedInt(blockIDs_byte[x]);
               }

               byte[] metadata = nbtdata.method_10547("Data");
               int counter = 0;

               for (int y = 0; y < this.height; y++) {
                  for (int z = 0; z < this.length; z++) {
                     for (int x = 0; x < this.width; x++) {
                        class_2338 pos = new class_2338(x, y, z);
                        class_2680 state = this.getStateFromOldIds(blockIDs[counter], metadata[counter]);
                        this.blockObjects[counter] = new SchematicBlockObject(pos, state);
                        counter++;
                     }
                  }
               }

               class_2499 tileentitynbtlist = nbtdata.method_10554("TileEntities", 10);
               this.blockEntities = new ArrayList<>();

               for (int i = 0; i < tileentitynbtlist.size(); i++) {
                  class_2487 compound = tileentitynbtlist.method_10602(i);
                  int i0 = compound.method_10550("x");
                  int i1 = compound.method_10550("y");
                  int i2 = compound.method_10550("z");
                  compound.method_10539("Pos", new int[]{i0, i1, i2});
                  this.blockEntities.add(compound);
               }

               this.offsetX = nbtdata.method_10550("WEOffsetX");
               this.offsetY = nbtdata.method_10550("WEOffsetY");
               this.offsetZ = nbtdata.method_10550("WEOffsetZ");
               this.parsedCorrectly = true;
               return;
            case "nbt":
               class_2499 paletteNBTList = nbtdata.method_10554("palette", 10);
               this.palette = new HashMap<>();

               for (int i = 0; i < paletteNBTList.size(); i++) {
                  class_2487 compound = paletteNBTList.method_10602(i);
                  String value = compound.method_10558("Name");
                  if (compound.method_10545("Properties")) {
                     StringBuilder metaData = new StringBuilder("[");
                     class_2487 propertyCompound = compound.method_10562("Properties");

                     for (String propertyKey : propertyCompound.method_10541()) {
                        if (!metaData.toString().equals("[")) {
                           metaData.append(",");
                        }

                        metaData.append(propertyKey).append("=").append(propertyCompound.method_10580(propertyKey));
                     }

                     metaData.append("]");
                     value = value + metaData;
                  }

                  this.palette.put(i, value);
               }

               this.blockEntities = new ArrayList<>();
               class_2499 blocksNBTList = nbtdata.method_10554("blocks", 10);

               for (int i = 0; i < blocksNBTList.size(); i++) {
                  class_2487 compound = blocksNBTList.method_10602(i);
                  class_2499 posList = compound.method_10554("pos", 3);
                  int i0 = posList.method_10600(0);
                  int i1 = posList.method_10600(1);
                  int i2 = posList.method_10600(2);
                  class_2338 pos = new class_2338(i0, i1, i2);
                  class_2680 state = this.getStateFromID(blockRegistry, compound.method_10550("state"));
                  this.blockObjects[i] = new SchematicBlockObject(pos, state);
                  if (compound.method_10545("nbt")) {
                     class_2487 blockEntityCompound = compound.method_10562("nbt");
                     blockEntityCompound.method_10539("Pos", new int[]{i0, i1, i2});
                     blockEntityCompound.method_10582("Id", blockEntityCompound.method_10558("id"));
                     blockEntityCompound.method_10551("id");
                     this.blockEntities.add(blockEntityCompound);
                  }
               }

               class_2499 entitiesNBTList = nbtdata.method_10554("entities", 10);

               for (int i = 0; i < entitiesNBTList.size(); i++) {
                  class_2487 compound = entitiesNBTList.method_10602(i);
                  class_2487 entityCompound = compound.method_10562("nbt");
                  class_2499 posList = compound.method_10554("blockPos", 3);
                  int i0 = posList.method_10600(0);
                  int i1 = posList.method_10600(1);
                  int i2 = posList.method_10600(2);
                  this.entities.add(new Pair(new class_2338(i0, i1, i2), entityCompound));
               }

               this.offsetX = 0;
               this.offsetY = 0;
               this.offsetZ = 0;
               this.parsedCorrectly = true;
               return;
         }
      } catch (Exception ignored) {
         Constants.LOG.warn("[Collective] Something went wrong while parsing the schematic.");
      }

      Constants.LOG.warn("Can't load {} Schematic file.", type);
      this.width = 0;
      this.height = 0;
      this.length = 0;
      this.offsetX = 0;
      this.offsetY = 0;
      this.offsetZ = 0;
      this.size = 0;
      this.blockObjects = null;
      this.palette = null;
      this.blockEntities = null;
   }

   public boolean isOldVersion() {
      return this.oldVersion;
   }

   private class_2680 getStateFromOldIds(int blockID, byte meta) {
      return class_2248.method_9531(blockID);
   }

   public class_2680 getBlockState(class_2338 pos) {
      for (SchematicBlockObject schematicBlockObject : this.blockObjects) {
         if (schematicBlockObject.getPosition().equals(pos)) {
            return schematicBlockObject.getState();
         }
      }

      return class_2246.field_10124.method_9564();
   }

   public int getSize() {
      return this.size;
   }

   public SchematicBlockObject[] getBlocks() {
      return this.blockObjects;
   }

   public class_2680 getStateFromID(class_2378<class_2248> blockRegistry, int id) {
      String iblockstateS = this.palette.get(id);

      try {
         return class_2259.method_41955(blockRegistry.method_46771(), new StringReader(iblockstateS), false).comp_622();
      } catch (Exception ex) {
         return class_2246.field_10124.method_9564();
      }
   }

   public List<class_2487> getBlockEntities() {
      return this.blockEntities;
   }

   public List<Pair<class_2338, class_2487>> getEntityRelativePosPairs() {
      return this.entities;
   }

   public class_2487 getTileEntity(class_2338 pos) {
      for (class_2487 compound : this.blockEntities) {
         int[] pos1 = compound.method_10561("Pos");
         if (pos1[0] == pos.method_10263() && pos1[1] == pos.method_10264() && pos1[2] == pos.method_10260()) {
            return compound;
         }
      }

      return null;
   }

   public class_2338 getBlockPosFromCompoundTag(class_2487 compoundTag) {
      int[] pos = compoundTag.method_10561("Pos");
      return new class_2338(pos[0], pos[1], pos[2]);
   }

   public short getWidth() {
      return this.width;
   }

   public short getHeight() {
      return this.height;
   }

   public short getLength() {
      return this.length;
   }

   public int getOffsetX() {
      return this.offsetX;
   }

   public int getOffsetY() {
      return this.offsetY;
   }

   public int getOffsetZ() {
      return this.offsetZ;
   }

   public boolean wasParsedCorrectly() {
      return this.parsedCorrectly;
   }
}
