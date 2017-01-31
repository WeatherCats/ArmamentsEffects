package kipperorigin.armamentseffects;

import org.bukkit.configuration.serialization.ConfigurationSerialization;

public class SerializationRegistration
{
    public static void init() {
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.DamageOtherEntityHookCancelEvent.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.InteractHookCancelEvent.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.InteractHookParticlePlayer.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.InteractHookPlayerLocation.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.InteractHookTargetLocation.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.ParticleEffect.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.ParticleEffectComponent.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.ParticleEffectTimelineEntry.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.PiercingEffect.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.SoundEffect.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.modifier.CoordinateModifierMove.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.modifier.CoordinateModifierRotate.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.modifier.CoordinateModifierScale.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.modifier.CoordinateModifierScale2d.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.coordinate.CircleCoordinateSource.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.coordinate.ConstantCoordinateSource.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.coordinate.RandomCoordinateSource.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.value.ConstantValueSource.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.value.LinearValueSource.class);
	ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.managers.sources.value.SinewaveValueSource.class);
        ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.DamageOtherEntityHookEventAndDamager.class);
        ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.ProjectileLaunchHookParticlePlayer.class);
        ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.hooks.ProjectileLaunchHookPlayerLocation.class);
        ConfigurationSerialization.registerClass(kipperorigin.armamentseffects.registry.RegistryHook.class);
    }
}
