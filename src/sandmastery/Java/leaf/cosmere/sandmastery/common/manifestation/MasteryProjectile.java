/*
 * File updated ~ 26 - 5 - 2023 ~ Leaf
 */

package leaf.cosmere.sandmastery.common.manifestation;

import leaf.cosmere.api.Manifestations;
import leaf.cosmere.api.Taldain;
import leaf.cosmere.api.spiritweb.ISpiritweb;
import leaf.cosmere.common.cap.entity.SpiritwebCapability;
import leaf.cosmere.sandmastery.common.capabilities.SandmasterySpiritwebSubmodule;
import leaf.cosmere.sandmastery.common.config.SandmasteryConfigs;
import leaf.cosmere.sandmastery.common.config.SandmasteryServerConfig;
import leaf.cosmere.sandmastery.common.registries.SandmasteryItems;
import leaf.cosmere.sandmastery.common.utils.MiscHelper;
import leaf.cosmere.sandmastery.common.utils.SandmasteryConstants;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class MasteryProjectile extends SandmasteryManifestation
{
	public MasteryProjectile(Taldain.Mastery mastery)
	{
		super(mastery);
	}

	@Override
	public boolean tick(ISpiritweb data)
	{
		SpiritwebCapability playerSpiritweb = (SpiritwebCapability) data;
		SandmasterySpiritwebSubmodule submodule = (SandmasterySpiritwebSubmodule) playerSpiritweb.getSubmodule(Manifestations.ManifestationTypes.SANDMASTERY);
		submodule.tickProjectileCooldown();
		if (!submodule.projectileReady())
		{
			return false;
		}
		boolean enabledViaHotkey = MiscHelper.enabledViaHotkey(data, SandmasteryConstants.PROJECTILE_HOTKEY_FLAG);
		if (getMode(data) > 0 && enabledViaHotkey)
		{
			submodule.setProjectileCooldown(SandmasteryConfigs.SERVER.PROJECTILE_COOLDOWN.get());
			return performEffectServer(data);
		}
		return false;
	}

	protected boolean performEffectServer(ISpiritweb data)
	{
		SpiritwebCapability playerSpiritweb = (SpiritwebCapability) data;
		ServerPlayer player = (ServerPlayer) data.getLiving();
		SandmasterySpiritwebSubmodule submodule = (SandmasterySpiritwebSubmodule) playerSpiritweb.getSubmodule(Manifestations.ManifestationTypes.SANDMASTERY);
		if (!submodule.adjustHydration(-SandmasteryConfigs.SERVER.PROJECTILE_HYDRATION_COST.get(), false))
		{
			return false;
		}
		if (notEnoughChargedSand(data))
		{
			return false;
		}
		for (int i = 0; i < player.getInventory().getContainerSize(); i++)
		{
			ItemStack pouch = player.getInventory().getItem(i);
			if (!pouch.isEmpty() && pouch.is(SandmasteryItems.SAND_POUCH_ITEM.get()))
			{
				SandmasteryItems.SAND_POUCH_ITEM.get().shoot(pouch, player);
				break;
			}
		}

		CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(handler -> {
			ICurioStacksHandler stacksHandler = handler.getCurios().get(SlotTypePreset.BELT.getIdentifier());
			if (stacksHandler != null)
			{
				for (int i = 0; i < stacksHandler.getSlots(); i++)
				{
					ItemStack pouch = stacksHandler.getStacks().getStackInSlot(i);
					if (!pouch.isEmpty() && pouch.is(SandmasteryItems.SAND_POUCH_ITEM.get()))
					{
						SandmasteryItems.SAND_POUCH_ITEM.get().shoot(pouch, player);
						break;
					}
				}
			}
		});

		submodule.adjustHydration(-SandmasteryConfigs.SERVER.PROJECTILE_HYDRATION_COST.get(), true);
		useChargedSand(data);
		return true;
	}

}
