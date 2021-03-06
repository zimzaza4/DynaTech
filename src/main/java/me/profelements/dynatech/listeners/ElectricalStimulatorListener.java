package me.profelements.dynatech.listeners;

import javax.annotation.Nonnull;

import me.mrCookieSlime.Slimefun.api.Slimefun;
import me.profelements.dynatech.DynaTech;
import me.profelements.dynatech.items.tools.ElectricalStimulator;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

public class ElectricalStimulatorListener implements Listener {

    private final DynaTech plugin;
    private final ElectricalStimulator electricalStimulator;

    public ElectricalStimulatorListener(@Nonnull DynaTech plugin, @Nonnull ElectricalStimulator electricalStimulator) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        
        this.plugin = plugin;
        this.electricalStimulator = electricalStimulator;
    }

    @EventHandler
    public void onHungerLoss(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getFoodLevel() < p.getFoodLevel()) {
                feedPlayer((Player) e.getEntity()); 
            }
        }
    }

    @EventHandler
    public void onHungerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            feedPlayer((Player) e.getEntity());
            
        }
    }

    private void feedPlayer(Player p) {
        if (electricalStimulator == null || electricalStimulator.isDisabled()) {
            return;
        }

        for (ItemStack item : p.getInventory().getContents()) {
            if (electricalStimulator.isItem(item)) {
                if (Slimefun.hasUnlocked(p, item, true)) {
                    DynaTech.runSync(()->givePlayerFood(p));
                    electricalStimulator.removeItemCharge(item,  electricalStimulator.getEnergyComsumption());
                } else {
                    return;
                }
            }
        }
    }

    private void givePlayerFood(Player p) {
        p.setFoodLevel(20);
        p.setSaturation(20f);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_BURP, 1F , 1F);
    }
    
}
