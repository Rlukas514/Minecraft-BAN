package tech.itexpress;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Bansystem extends JavaPlugin implements Listener {


    private Inventory targetSelector;
    private Inventory warningWindow;
    private Inventory banWindow;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        createTargetSelector();
        createWarningWindow();
        createBanWindow();
    }

    private void createTargetSelector() {
        targetSelector = Bukkit.createInventory(null, 9, "Wähle einen Spieler");

        for (Player player : Bukkit.getOnlinePlayers()) {
            ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
            ItemMeta playerItemMeta = playerItem.getItemMeta();
            playerItemMeta.setDisplayName(player.getName());
            playerItem.setItemMeta(playerItemMeta);
            targetSelector.addItem(playerItem);
        }
    }

    private void createWarningWindow() {
        warningWindow = Bukkit.createInventory(null, 9, "Verwarnung");

        ItemStack warningItem = new ItemStack(Material.RED_WOOL, 1);
        ItemMeta warningItemMeta = warningItem.getItemMeta();
        warningItemMeta.setDisplayName("Verwarnung");
        warningItem.setItemMeta(warningItemMeta);
        warningWindow.setItem(4, warningItem);
    }

    private void createBanWindow() {
        banWindow = Bukkit.createInventory(null, 9, "Ban");

        ItemStack banItem = new ItemStack(Material.BARRIER, 1);
        ItemMeta banItemMeta = banItem.getItemMeta();
        banItemMeta.setDisplayName("Ban");
        banItem.setItemMeta(banItemMeta);
        banWindow.setItem(4, banItem);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        Inventory inventory = event.getInventory();

        if (clickedItem == null) {
            return;
        }

        if (clickedItem != null) {
            ItemMeta itemMeta = clickedItem.getItemMeta();
            if (itemMeta != null) {
                switch (itemMeta.getDisplayName()) {
                    case "Ban":
                        player.closeInventory();
                        Inventory targetInventory = Bukkit.createInventory(null, 9, "Ban-Ziel");
                        player.openInventory(targetInventory);
                        break;
                    case "Unban":
                        player.closeInventory();
                        targetInventory = Bukkit.createInventory(null, 9, "Unban-Ziel");
                        player.openInventory(targetInventory);
                        break;
                    case "Kick":
                        player.closeInventory();
                        targetInventory = Bukkit.createInventory(null, 9, "Kick-Ziel");
                        player.openInventory(targetInventory);
                        break;
                    case "Mute":
                        player.closeInventory();
                        targetInventory = Bukkit.createInventory(null, 9, "Mute-Ziel");
                        player.openInventory(targetInventory);
                        break;
                }
            }
        }
    }
}
