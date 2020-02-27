package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.SubcategoryRequest;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.SubcategoryResponse;
import rostyk.stupnytskiy.zepka.entity.Subcategory;
import rostyk.stupnytskiy.zepka.repository.SubcategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubcategoryService {

    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryService categoryService;

    public void save(SubcategoryRequest request){
        subcategoryRepository.save(subcategoryRequestToSubcategory(request,null));
    }

    public void update(SubcategoryRequest request, Long id){
        subcategoryRepository.save(subcategoryRequestToSubcategory(request,findById(id)));
    }

    public List<SubcategoryResponse> findAll(){
        return subcategoryRepository.findAll()
                .stream()
                .map(SubcategoryResponse::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        subcategoryRepository.delete(findById(id));
    }

    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Subcategory with id " + id + " not exist"));
    }

    public PageResponse<SubcategoryResponse> findAll(PaginationRequest paginationRequest){
        final Page<Subcategory> page = subcategoryRepository.findAll(paginationRequest.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(SubcategoryResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private Subcategory subcategoryRequestToSubcategory(SubcategoryRequest request, Subcategory subcategory){
        if(subcategory == null){
            subcategory = new Subcategory();
        }
        subcategory.setName(request.getName());
        subcategory.setCategory(categoryService.findById(request.getCategoryId()));
        return subcategory;
    }

    public List<SubcategoryResponse> findAllWithCategory(Long id) {
        return subcategoryRepository.findAllByCategoryId(id)
                .stream()
                .map(SubcategoryResponse::new)
                .collect(Collectors.toList());
    }
}
