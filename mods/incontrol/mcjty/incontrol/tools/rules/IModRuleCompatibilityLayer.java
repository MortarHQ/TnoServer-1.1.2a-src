package mcjty.incontrol.tools.rules;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public interface IModRuleCompatibilityLayer {
   boolean hasBaubles();

   int[] getAmuletSlots();

   int[] getBeltSlots();

   int[] getBodySlots();

   int[] getCharmSlots();

   int[] getHeadSlots();

   int[] getRingSlots();

   int[] getTrinketSlots();

   ItemStack getBaubleStack(Player var1, int var2);

   boolean hasGameStages();

   boolean hasGameStage(Player var1, String var2);

   void addGameStage(Player var1, String var2);

   void removeGameStage(Player var1, String var2);

   boolean hasLostCities();

   <T> boolean isCity(IEventQuery<T> var1, T var2);

   <T> boolean isStreet(IEventQuery<T> var1, T var2);

   <T> boolean inSphere(IEventQuery<T> var1, T var2);

   <T> boolean isBuilding(IEventQuery<T> var1, T var2);

   <T> boolean isMultiBuilding(IEventQuery<T> var1, T var2);

   <T> String getBuilding(IEventQuery<T> var1, T var2);

   <T> String getMultiBuilding(IEventQuery<T> var1, T var2);

   boolean hasSereneSeasons();

   boolean isSpring(Level var1);

   boolean isSummer(Level var1);

   boolean isWinter(Level var1);

   boolean isAutumn(Level var1);

   boolean hasEnigmaScript();

   void setPlayerState(Player var1, String var2, String var3);

   String getPlayerState(Player var1, String var2);

   void setState(LevelAccessor var1, String var2, String var3);

   String getState(LevelAccessor var1, String var2);
}
