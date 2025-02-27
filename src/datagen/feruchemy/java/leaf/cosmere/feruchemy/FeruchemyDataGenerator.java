/*
 * File updated ~ 8 - 10 - 2022 ~ Leaf
 */

package leaf.cosmere.feruchemy;

import leaf.cosmere.feruchemy.advancements.FeruchemyAdvancementGen;
import leaf.cosmere.feruchemy.common.Feruchemy;
import leaf.cosmere.feruchemy.items.FeruchemyItemModelsGen;
import leaf.cosmere.feruchemy.items.FeruchemyItemTagsGen;
import leaf.cosmere.feruchemy.patchouli.FeruchemyPatchouliGen;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Feruchemy.MODID, bus = Bus.MOD)
public class FeruchemyDataGenerator
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		generator.addProvider(true, new FeruchemyEngLangGen(generator));
		generator.addProvider(true, new FeruchemyItemModelsGen(generator, existingFileHelper));
		generator.addProvider(true, new FeruchemyItemTagsGen(generator, new BlockTagsProvider(generator, Feruchemy.MODID, existingFileHelper), existingFileHelper));
		generator.addProvider(true, new FeruchemyRecipeGen(generator));
		generator.addProvider(true, new FeruchemyPatchouliGen(generator));
		generator.addProvider(true, new FeruchemyAdvancementGen(generator));
	}

}