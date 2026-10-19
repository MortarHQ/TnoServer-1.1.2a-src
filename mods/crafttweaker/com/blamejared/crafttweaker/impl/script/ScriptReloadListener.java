package com.blamejared.crafttweaker.impl.script;

import com.blamejared.crafttweaker.CraftTweakerCommon;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.ingredient.IngredientCacheBuster;
import com.blamejared.crafttweaker.api.tag.CraftTweakerTagRegistry;
import com.blamejared.crafttweaker.api.util.sequence.SequenceManager;
import com.blamejared.crafttweaker.api.villager.CTVillagerTrades;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRun;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptDiscoveryConfiguration;
import com.blamejared.crafttweaker.api.zencode.scriptrun.ScriptRunConfiguration;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.blamejared.crafttweaker.mixin.common.access.tag.AccessTagManager;
import com.blamejared.crafttweaker.platform.helper.IAccessibleServerElementsProvider;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

public final class ScriptReloadListener extends SimplePreparableReloadListener<Void> {
   private static final MutableComponent MSG_RELOAD_STARTING = Component.translatable("crafttweaker.reload.start");
   private static final MutableComponent MSG_RELOAD_COMPLETE = Component.translatable("crafttweaker.reload.complete");
   private static final Random RANDOM = ThreadLocalRandom.current();
   private final ReloadableServerResources resources;
   private final Consumer<MutableComponent> feedbackConsumer;

   public ScriptReloadListener(ReloadableServerResources managerSupplier, Consumer<MutableComponent> feedbackConsumer) {
      this.resources = managerSupplier;
      this.feedbackConsumer = feedbackConsumer;
   }

   protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
      return null;
   }

   protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profiler) {
      IngredientCacheBuster.claim();
      SequenceManager.clearSequences();
      CTVillagerTrades.clear();
      IAccessibleServerElementsProvider asep = CraftTweakerAPI.getAccessibleElementsProvider().server();
      asep.resources(this.resources);
      TagManager tagmanager = asep.accessibleResources().crafttweaker$getTagManager();
      asep.registryAccess(((AccessTagManager)tagmanager).crafttweaker$getRegistryAccess());
      CraftTweakerTagRegistry.INSTANCE.bind(tagmanager);
      RecipeManager manager = this.resources.getRecipeManager();
      this.feedbackConsumer.accept(MSG_RELOAD_STARTING);
      this.fixRecipeManager(manager);
      ScriptReloadListener.RunPreparation runPreparation = this.prepareRun();

      label40: {
         try {
            runPreparation.run().execute();
            break label40;
         } catch (Throwable e) {
            CraftTweakerCommon.logger().error("Unable to execute script run", e);
         } finally {
            IngredientCacheBuster.release();
         }

         return;
      }

      this.storeScriptsInRecipes(manager, runPreparation.root(), runPreparation.scripts());
      this.feedbackConsumer.accept(MSG_RELOAD_COMPLETE);
      if (!runPreparation.scripts().isEmpty() && runPreparation.run().specificRunInfo().displayBranding()) {
         this.displayPatreonBranding();
      }
   }

   private void fixRecipeManager(RecipeManager manager) {
      AccessRecipeManager accessRecipeManager = (AccessRecipeManager)manager;
      accessRecipeManager.crafttweaker$setByType(HashMultimap.create(accessRecipeManager.crafttweaker$getByType()));
      accessRecipeManager.crafttweaker$setByName(new HashMap<>(accessRecipeManager.crafttweaker$getByName()));
      CraftTweakerAPI.getAccessibleElementsProvider().recipeManager(manager);
   }

   private ScriptReloadListener.RunPreparation prepareRun() {
      class Retainer implements ScriptDiscoveryConfiguration.DiscoveryRetainer {
         Path root;
         List<Path> scripts;

         @Override
         public void retain(Path root, List<Path> discoveryResults) {
            this.root = root;
            this.scripts = discoveryResults;
         }
      }

      Retainer retainer = new Retainer();
      ScriptDiscoveryConfiguration discoveryConfiguration = new ScriptDiscoveryConfiguration(
         ScriptDiscoveryConfiguration.SuspiciousNamesBehavior.WARN, retainer
      );
      ScriptRunConfiguration runConfiguration = new ScriptRunConfiguration(
         "crafttweaker", CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID, ScriptRunConfiguration.RunKind.EXECUTE
      );
      IScriptRun run = CraftTweakerAPI.getScriptRunManager().createScriptRun(CraftTweakerAPI.getScriptsDirectory(), discoveryConfiguration, runConfiguration);
      return new ScriptReloadListener.RunPreparation(run, retainer.root, retainer.scripts);
   }

   private void storeScriptsInRecipes(RecipeManager manager, Path root, List<Path> scripts) {
      Multimap<RecipeType<?>, RecipeHolder<?>> recipes = ((AccessRecipeManager)manager).crafttweaker$getByType();
      scripts.stream().map(it -> this.buildScriptRecipe(it, root)).forEach(it -> recipes.put(it.getType(), new RecipeHolder(it.getId(), it)));
   }

   private ScriptRecipe buildScriptRecipe(Path file, Path root) {
      String fileName = root.relativize(file).toString().replace('\\', '/');
      return new ScriptRecipe(fileName, this.readContents(file));
   }

   private String readContents(Path file) {
      try {
         return String.join("\n", Files.readAllLines(file));
      } catch (IOException e) {
         CraftTweakerCommon.logger().info("Unable to read script file " + file, e);
         return "";
      }
   }

   private void displayPatreonBranding() {
      Collection<String> patronList = CraftTweakerCommon.getPatronList();
      if (!patronList.isEmpty()) {
         patronList.stream()
            .skip(RANDOM.nextInt(patronList.size()))
            .findFirst()
            .ifPresent(
               name -> CraftTweakerCommon.logger()
                  .info("This reload was made possible by {} and more! Become a patron at https://patreon.com/jaredlll08?s=crtmod", name)
            );
      }
   }

   private record RunPreparation(IScriptRun run, Path root, List<Path> scripts) {
   }
}
