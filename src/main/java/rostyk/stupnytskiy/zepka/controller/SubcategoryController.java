package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.SubcategoryRequest;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.SubcategoryResponse;
import rostyk.stupnytskiy.zepka.service.SubcategoryService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {

    @Autowired
    private SubcategoryService subcategoryService;

    @PostMapping()
    public void save(@RequestBody SubcategoryRequest request){
        subcategoryService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody SubcategoryRequest request){
        subcategoryService.update(request,id);
    }

    @GetMapping()
    public List<SubcategoryResponse> findAll(){
        return subcategoryService.findAll();
    }

    @GetMapping("/category")
    public List<SubcategoryResponse> findAllWithCategory(Long id){
        return subcategoryService.findAllWithCategory(id);
    }

    @DeleteMapping
    public void delete(Long id){
        subcategoryService.delete(id);
    }

    @GetMapping("/")
    public PageResponse<SubcategoryResponse> findAll(@Valid PaginationRequest request){
        return subcategoryService.findAll(request);
    }
}
