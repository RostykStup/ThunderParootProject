package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.*;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationForSearchResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationsWithRandomCategoryResponse;
import rostyk.stupnytskiy.zepka.service.AddressService;
import rostyk.stupnytskiy.zepka.service.PublicationService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public void save(@RequestBody PublicationRequest request) throws IOException {
        publicationService.save(request);
    }

    @PutMapping
    public void update(Long id, @RequestBody PublicationRequest request) throws IOException {
        publicationService.update(request,id);
    }

    @GetMapping
    public List<PublicationResponse> findAll(){
        return publicationService.findAll();
    }

    @GetMapping("find")
    public PublicationResponse findOne(Long id){
        return publicationService.findOne(id);
    }

    @DeleteMapping
    public void delete(Long id){
        publicationService.delete(id);
    }

    @GetMapping("/random")
    public PublicationsWithRandomCategoryResponse getWithRandomCategory(PaginationRequest request){
        return publicationService.findWithRandomCategory(request);
    }

    @GetMapping("/user")
    public PageResponse<PublicationResponse> findAll(Long id, PaginationRequest request) {
        return publicationService.findByUserId(id, request);
    }


    @GetMapping("/filter")
    public PageResponse<PublicationForSearchResponse> findAll(@Valid PublicationSearchRequest request) {
        return publicationService.findAll(request);
    }

}
