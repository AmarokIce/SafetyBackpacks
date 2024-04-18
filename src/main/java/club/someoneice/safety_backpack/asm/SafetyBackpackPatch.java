package club.someoneice.safety_backpack.asm;

import net.tclproject.mysteriumlib.PlaceholderCoremod;

public class SafetyBackpackPatch extends PlaceholderCoremod {
    @Override
    public void registerFixes() {
        registerClassWithFixes(PlayerASM.class.getName());
    }
}
