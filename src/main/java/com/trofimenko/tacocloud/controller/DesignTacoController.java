package com.trofimenko.tacocloud.controller;

import com.trofimenko.tacocloud.entity.Ingredient;
import com.trofimenko.tacocloud.entity.Taco;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {

    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );

        //собираем массив из всех ингридиентов
        Ingredient.Type[] types = Ingredient.Type.values();

        //перебираем ингридиенты
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());
        return "design";
    }

//    @PostMapping
//    public String processDesign(Design design) {
//
//        log.info("Processing design: " + design);
//        return "redirect:/orders/current";
//    }

    /*
    @Valid аннотация говорит Spring MVC, чтобы выполнить проверку на представленном объекте Taco после того,
    как он привязан к представленным данным формы и перед вызовом метода processDesign(). Если есть какие-либо ошибки проверки,
    сведения об этих ошибках будут записаны в объект Errors, который передается в processDesign(). Первые несколько строк processDesign()
    проверяют объект Errors, через его метод hasErrors (), есть ли какие-либо ошибки проверки. Если есть,
    метод завершается без обработки Taco и возвращает имя представления "design" так, чтобы форма была перерисована.
     */
    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }

        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Процесс создания: " + design);
       // model.addAttribute ("taco", design);

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

}
