package rostyk.stupnytskiy.zepka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin
@Controller
public class HtmlPagesController {

    @RequestMapping("country-admin")
    public String country(){
        return "country-admin.html";
    }
    @RequestMapping("type-admin")
    public String type(){
        return "type-admin.html";
    }
    @RequestMapping("delivery-admin")
    public String delivery(){
        return "delivery-admin.html";
    }
    @RequestMapping("currency-admin")
    public String currency(){
        return "currency-admin.html";
    }
    @RequestMapping("category-admin")
    public String category(){
        return "category-admin.html";
    }

    @RequestMapping("subcategory-admin")
    public String subcategory(){
        return "index_subcategory.html";
    }

    @RequestMapping("publication-page")
    public String catalogDark(){
        return "publication.html";
    }

    @RequestMapping("searching")
    public String searchingPage(){
        return "filter-page.html";
    }

    @RequestMapping("main")
    public String mainPage(){
        return "main-page.html";
    }

    @RequestMapping("login")
    public String loginPage(){
        return "login.html";
    }
    @RequestMapping("registration")
    public String registrationPage(){
        return "registration.html";
    }

    @RequestMapping("cart")
    public String cartPage(){
        return "cart-page.html";
    }

//    @RequestMapping("publication-page")
//    public String catalogDark(){
//        return "publication.html";
//    }
}
