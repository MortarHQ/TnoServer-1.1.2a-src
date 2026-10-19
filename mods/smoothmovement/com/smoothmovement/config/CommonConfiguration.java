package com.smoothmovement.config;

import com.cupboard.config.CupboardConfig;
import com.cupboard.config.ICommonConfig;
import com.google.gson.JsonObject;

public class CommonConfiguration implements ICommonConfig {
   private static final String ENABLE_CLIENT = "enableClient";
   private static final String ENABLE_SERVER = "enableServer";
   private static final String DESC_CLIENT = "descClient:";
   private static final String DESC_SEVER = "descServer";
   public static final String ITEM_MOVEMENT = "itemMovement";
   public static final String ENTITY_MOVEMENT = "entityMovement";
   public static final String MINECART_MOVEMENT = "minecartMovement";
   public static final String EXPERIENCE_ORB_MOVEMENT = "experienceOrbMovement";
   public static final String FALLINGBLOCKMOVEMENT = "fallingblockmovement";
   public static final String PROJECTILE_MOVEMENT = "projectileMovement";
   public static final String SKY_MOVEMENT = "skyMovement";
   public static final String PLAYER_MOVEMENT = "playerMovement";
   public static CupboardConfig<CommonConfiguration> config = new CupboardConfig("smoothmovement", new CommonConfiguration());
   public boolean enableItemSmoothing = true;
   public boolean enableItemLagAdjustedMovement = true;
   public boolean enableLivingEntitySmoothing = true;
   public boolean enableLivingEntityLagAdjustedMovement = true;
   public boolean enableMinecartSmoothing = true;
   public boolean enableMinecartLagAdjustedMovement = true;
   public boolean enableExpOrbLagAdjustedMovement = true;
   public boolean enableFallingBlockLagAdjustedMovement = true;
   public boolean enableProjectileLagAdjustedMovement = true;
   public boolean enableRubberbandingLagAdjustedMovement = true;
   public boolean enableSkySmoothing = true;
   public boolean enableSkyLagAdjustedMovement = true;

   public JsonObject serialize() {
      JsonObject root = new JsonObject();
      JsonObject entry = new JsonObject();
      entry.addProperty("descClient:", "Smooths item movement on the client during server or network lag. Default:true");
      entry.addProperty("enableClient", this.enableItemSmoothing);
      entry.addProperty("descServer", "Compensates item movement on the server during low TPS to preserve vanilla behaviour. Default: true");
      entry.addProperty("enableServer", this.enableItemLagAdjustedMovement);
      root.add("itemMovement", entry);
      JsonObject entry2 = new JsonObject();
      entry2.addProperty("descClient:", "Smooths living entity movement on the client during server or network lag. Default: true");
      entry2.addProperty("enableClient", this.enableLivingEntitySmoothing);
      entry2.addProperty("descServer", "Compensates living entity movement on the server during low TPS to preserve vanilla behavior. Default: true");
      entry2.addProperty("enableServer", this.enableLivingEntityLagAdjustedMovement);
      root.add("entityMovement", entry2);
      JsonObject entry3 = new JsonObject();
      entry3.addProperty("descClient:", "Smooths minecart movement on the client during server or network lag. Default: true");
      entry3.addProperty("enableClient", this.enableMinecartSmoothing);
      entry3.addProperty("descServer", "Compensates minecart movement on the server during low TPS to preserve vanilla physics behavior. Default: true");
      entry3.addProperty("enableServer", this.enableMinecartLagAdjustedMovement);
      root.add("minecartMovement", entry3);
      JsonObject entry4 = new JsonObject();
      entry4.addProperty(
         "descServer", "Compensates experience orb movement on the server during low TPS to preserve vanilla collection behavior. Default: true"
      );
      entry4.addProperty("enableServer", this.enableExpOrbLagAdjustedMovement);
      root.add("experienceOrbMovement", entry4);
      JsonObject entry5 = new JsonObject();
      entry5.addProperty("descServer", "Compensates falling block movement on the server during low TPS to preserve vanilla physics behavior. Default: true");
      entry5.addProperty("enableServer", this.enableFallingBlockLagAdjustedMovement);
      root.add("fallingblockmovement", entry5);
      JsonObject entry6 = new JsonObject();
      entry6.addProperty(
         "descServer", "Compensates projectile movement on the server during low TPS to preserve vanilla travel distance and timing. Default: true"
      );
      entry6.addProperty("enableServer", this.enableProjectileLagAdjustedMovement);
      root.add("projectileMovement", entry6);
      JsonObject entry8 = new JsonObject();
      entry8.addProperty("descServer", "Compensates player movement calculations during low TPS to reduce rubber-banding issues on the client. default:true");
      entry8.addProperty("enableServer", this.enableRubberbandingLagAdjustedMovement);
      root.add("playerMovement", entry8);
      JsonObject entry7 = new JsonObject();
      entry7.addProperty("descClient:", "Smooths sky/day movement on the client during server or network lag. Default: true");
      entry7.addProperty("enableClient", this.enableSkySmoothing);
      entry7.addProperty("descServer", "Compensates daytime progression on the server during low TPS to preserve vanilla day length. Default: true");
      entry7.addProperty("enableServer", this.enableSkyLagAdjustedMovement);
      root.add("skyMovement", entry7);
      return root;
   }

   public void deserialize(JsonObject data) {
      this.enableItemSmoothing = data.get("itemMovement").getAsJsonObject().get("enableClient").getAsBoolean();
      this.enableItemLagAdjustedMovement = data.get("itemMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableLivingEntitySmoothing = data.get("entityMovement").getAsJsonObject().get("enableClient").getAsBoolean();
      this.enableLivingEntityLagAdjustedMovement = data.get("entityMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableMinecartSmoothing = data.get("minecartMovement").getAsJsonObject().get("enableClient").getAsBoolean();
      this.enableMinecartLagAdjustedMovement = data.get("minecartMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableExpOrbLagAdjustedMovement = data.get("experienceOrbMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableFallingBlockLagAdjustedMovement = data.get("fallingblockmovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableProjectileLagAdjustedMovement = data.get("projectileMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableRubberbandingLagAdjustedMovement = data.get("playerMovement").getAsJsonObject().get("enableServer").getAsBoolean();
      this.enableSkySmoothing = data.get("skyMovement").getAsJsonObject().get("enableClient").getAsBoolean();
      this.enableSkyLagAdjustedMovement = data.get("skyMovement").getAsJsonObject().get("enableServer").getAsBoolean();
   }
}
