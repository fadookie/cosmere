/*
 * File created ~ 24 - 4 - 2021 ~ Leaf
 */

package leaf.cosmere.effects.feruchemy;

import leaf.cosmere.constants.Metals;
import leaf.cosmere.items.IHasMetalType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FeruchemyEffectBase extends MobEffect implements IHasMetalType
{
	protected final Metals.MetalType metalType;
	final double bonusPerLevel = 1;

	public FeruchemyEffectBase(Metals.MetalType type, MobEffectCategory effectType)
	{
		super(effectType, type.getColorValue());
		metalType = type;
	}

	@Override
	public Metals.MetalType getMetalType()
	{
		return this.metalType;
	}


	@Override
	public void applyEffectTick(LivingEntity entityLivingBaseIn, int amplifier)
	{
		if (entityLivingBaseIn.level.isClientSide)
		{
			//client side only?
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier)
	{
		//assume we can apply the effect regardless
		return true;
	}
}
