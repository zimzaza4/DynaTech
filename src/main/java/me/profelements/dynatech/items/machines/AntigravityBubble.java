package me.profelements.dynatech.items.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;


public class AntigravityBubble extends AMachine {

    private List<UUID> enabledPlayers = new ArrayList<>();

    private static int[] BORDER     = new int[] {1,2,6,7,9,10,11,15,16,17,19,20,24,25};
    private static int[] BORDER_IN  = new int[] {3,4,5,12,14,21,22,23};
    private static int[] BORDER_OUT = new int[] {0,8,18,26};

    public AntigravityBubble(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }


    @Override
    public void tick(Block b) {
        doFlightIfAvailable(b);
    }

    protected void doFlightIfAvailable(Block block) {
        if (getCharge(block.getLocation()) < getEnergyConsumption()) {
        return;
        }

        for (Player p : block.getWorld().getPlayers()) {
            double distance = block.getLocation().distance(p.getLocation());
            List<UUID> plrsToRemove = new ArrayList<>();

            if (!enabledPlayers.contains(p.getUniqueId()) && distance < 22.5 && !p.getAllowFlight()) {
                    p.setAllowFlight(true);
                    enabledPlayers.add(p.getUniqueId());
                    removeCharge(block.getLocation(), getEnergyConsumption());
                } 
            
            for (UUID playerUUID : enabledPlayers) {
                Player plr = Bukkit.getPlayer(playerUUID);
                double distance2 = block.getLocation().distance(plr.getLocation());
                if (distance2 >= 22.5) {
                    plrsToRemove.add(plr.getUniqueId());
                }
            }

            for(UUID playerToRemove : plrsToRemove) {
                Player plyr = Bukkit.getPlayer(playerToRemove);
                plyr.setFlying(false);
                plyr.setAllowFlight(false);
                plyr.setFallDistance(0.0f);
                enabledPlayers.remove(playerToRemove);
            }
            plrsToRemove.clear();
        }        
    };

    @Override
    public boolean isGraphical() {
        return false;
    }
    
    @Override
    public String getMachineIdentifier() {
        return "ANTIGRAVITY_BUBBLE";
    }


    @Override
    public List<int[]> getBorders() {
        List<int[]> borders = new ArrayList<>();
        borders.add(BORDER);
        borders.add(BORDER_IN);
        borders.add(BORDER_OUT);
        
        return borders;
    }
    
    @Override
    public int[] getInputSlots() {
        return new int[] {13};
    }
    @Override
    public int[] getOutputSlots() {
        return new int[] {13};
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.DRAGON_EGG);
    }
    
    @Override
    public int getProgressBarSlot() {
        return 4;
    }
    
}
