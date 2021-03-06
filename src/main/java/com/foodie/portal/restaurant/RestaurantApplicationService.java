package com.foodie.portal.restaurant;

import com.foodie.portal.city.CityApplicationService;
import com.foodie.portal.commons.Pagination;
import com.foodie.portal.restaurant.command.CreateRestaurantCommand;
import com.foodie.portal.restaurant.command.UpdateRestaurantCommand;
import com.foodie.portal.restaurant.model.Restaurant;
import lombok.var;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantApplicationService {
    @Autowired
    private CityApplicationService cityApplicationService;
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant create(CreateRestaurantCommand command) {
        var city = cityApplicationService.retrieve(command.getCityId());
        var restaurant = Restaurant.create(command.getTitle(), command.getSubTitle(), command.getImages(),
                command.getContent(), city, command.getArea(), command.getAddress(), command.getType(),
                command.getSetMeals());

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    public Restaurant findById(String id) {
        return restaurantRepository.byId(id);
    }

    public Pagination<Restaurant> list(int page, int size, String cityId) {
        if (Strings.isBlank(cityId)) {
            return restaurantRepository.findAll(page - 1, size);
        }
        return restaurantRepository.findAll(cityId, page - 1, size);
    }

    public void deleteById(String id) {
        restaurantRepository.deleteById(id);
    }

    public void update(String id, UpdateRestaurantCommand command) {
        var city = cityApplicationService.retrieve(command.getCityId());
        var restaurant = restaurantRepository.byId(id);
        restaurant.update(command.getTitle(), command.getSubTitle(), command.getImages(),
                command.getContent(), city, command.getArea(), command.getAddress(), command.getType(),
                command.getSetMeals());
        restaurantRepository.save(restaurant);
    }

    public void top(String id) {
        var restaurant = restaurantRepository.byId(id);
        restaurant.top();
        restaurantRepository.save(restaurant);
    }
}
