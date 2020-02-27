package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.CategoryRequest;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.CategoryResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.entity.Category;
import rostyk.stupnytskiy.zepka.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public void save(@RequestBody CategoryRequest request){
        categoryService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody CategoryRequest request){
        categoryService.update(request,id);
    }

    @GetMapping()
    public List<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @DeleteMapping
    public void delete(Long id){
        categoryService.delete(id);
    }

    @GetMapping("/")
    public PageResponse<CategoryResponse> findAll(@Valid PaginationRequest request){
        return categoryService.findAll(request);
    }
}
