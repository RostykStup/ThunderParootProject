package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.CategoryRequest;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.response.CategoryResponse;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.entity.Category;
import rostyk.stupnytskiy.zepka.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void save(CategoryRequest request){
        categoryRepository.save(categoryRequestToCategory(request,null));
    }

    public void update(CategoryRequest request,Long id){
        categoryRepository.save(categoryRequestToCategory(request,findById(id)));
    }

    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        categoryRepository.delete(findById(id));
    }

    public PageResponse<CategoryResponse> findAll(PaginationRequest paginationRequest){
        final Page<Category> page = categoryRepository.findAll(paginationRequest.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(CategoryResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " not exist"));
    }

    public Long getRandomCategoryId(){
        List<Category> categories = categoryRepository.findAll();
        Random random = new Random();
        return categories.get(random.nextInt(categories.size())).getId();
    }

    private Category categoryRequestToCategory(CategoryRequest request, Category category){
        if(category == null){
            category = new Category();
        }
        category.setName(request.getName());
        return category;
    }
}
