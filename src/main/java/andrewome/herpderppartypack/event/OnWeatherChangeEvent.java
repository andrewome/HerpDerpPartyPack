package andrewome.herpderppartypack.event;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OnWeatherChangeEvent implements Listener {
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (e.toWeatherState()) { // true if raining
            e.setCancelled(true);
            Bukkit.broadcastMessage("Cancelling weather change.");
        }


    }
}
