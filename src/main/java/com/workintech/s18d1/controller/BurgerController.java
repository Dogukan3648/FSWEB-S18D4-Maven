
package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class BurgerController {

    private final BurgerDao burgerDao;

    public BurgerController(BurgerDao burgerDao) {
        this.burgerDao = burgerDao;
    }

    @GetMapping({"/workintech/burgers", "/burger"})
    public List<Burger> getBurgers() {
        return burgerDao.findAll();
    }

    @GetMapping({"/workintech/burgers/{id}", "/burger/{id}"})
    public Burger getBurger(@PathVariable Long id) {
        return burgerDao.findById(id);
    }

    @PostMapping({"/workintech/burgers", "/burger"})
    public Burger addBurger(@RequestBody Burger burger) {
        BurgerValidation.validateForCreate(burger);
        return burgerDao.save(burger);
    }

    @PutMapping("/workintech/burgers/{id}")
    public Burger updateBurger(@PathVariable Long id, @RequestBody Burger burger) {
        burger.setId(id);
        return updateSafely(burger);
    }

    @PutMapping("/burger")
    public Burger updateBurger(@RequestBody Burger burger) {
        return updateSafely(burger);
    }

    @DeleteMapping({"/workintech/burgers/{id}", "/burger/{id}"})
    public Burger deleteBurger(@PathVariable Long id) {
        return burgerDao.remove(id);
    }

    @GetMapping("/workintech/burgers/findByPrice")
    public List<Burger> getFindByPrice(@RequestBody int price) {
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/burger/price/{price}")
    public List<Burger> getFindByPriceByPath(@PathVariable int price) {
        return burgerDao.findByPrice(price);
    }

    @GetMapping("/workintech/burgers/findByBreadType")
    public List<Burger> getFindByBreadType(@RequestBody BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/burger/breadType/{breadType}")
    public List<Burger> getFindByBreadTypeByPath(@PathVariable BreadType breadType) {
        return burgerDao.findByBreadType(breadType);
    }

    @GetMapping("/workintech/burgers/findByContent")
    public List<Burger> getFindByContent(@RequestBody String content) {
        return burgerDao.findByContent(content);
    }

    @GetMapping("/burger/content/{content}")
    public List<Burger> getFindByContentByPath(@PathVariable String content) {
        return burgerDao.findByContent(content);
    }

    private Burger updateSafely(Burger burger) {
        BurgerValidation.validateForUpdate(burger);

        Burger existing = burgerDao.findById(burger.getId());

        if (existing != null) {
            if (burger.getName() == null) {
                burger.setName(existing.getName());
            }

            if (burger.getPrice() == null) {
                burger.setPrice(existing.getPrice());
            }

            if (burger.getIsVegan() == null) {
                burger.setIsVegan(existing.getIsVegan());
            }

            if (burger.getBreadType() == null) {
                burger.setBreadType(existing.getBreadType());
            }

            if (burger.getContents() == null) {
                burger.setContents(existing.getContents());
            }
        }

        return burgerDao.update(burger);
    }
}
