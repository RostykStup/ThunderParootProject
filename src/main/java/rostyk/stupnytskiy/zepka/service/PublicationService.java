package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.*;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationForSearchResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationResponse;
import rostyk.stupnytskiy.zepka.dto.response.PublicationsWithRandomCategoryResponse;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.repository.PublicationRepository;
import rostyk.stupnytskiy.zepka.specification.PublicationSpecification;
import rostyk.stupnytskiy.zepka.specification.SearchSpecification;
import rostyk.stupnytskiy.zepka.tools.FileTool;
import rostyk.stupnytskiy.zepka.tools.MatchFinderTool;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private FileTool fileTool;

    @Autowired
    private MatchFinderTool matchFinder;

    public void save(PublicationRequest request) throws IOException {
        publicationRepository.save(publicationRequestToPublication(request, null));
    }

    public void update(PublicationRequest request, Long id) throws IOException {
        publicationRepository.save(publicationRequestToPublication(request, findById(id)));
    }

    public List<PublicationResponse> findAll() {
        return publicationRepository.findAll()
                .stream()
                .filter(p -> p.getCount() > 0)
                .map(PublicationResponse::new)
                .collect(Collectors.toList());
    }

    public void saveFromObject(Publication publication) {
        publicationRepository.save(publication);
    }

    public PageResponse<PublicationForSearchResponse> findAll(PublicationSearchRequest request) {
        final Page<Publication> page = publicationRepository.findAll(new PublicationSpecification(request), request.getPaginationRequest().mapToPageable());
        return new PageResponse<>(page.get()
                .map(e -> new PublicationForSearchResponse(new PublicationResponse(e), matchFinder.findMatchPointsFromPublication(request.getName(), e)))
                .filter(e -> e.getMatchPoints() >= 100)
                .filter(e -> e.getResponse().getNumber() > 0)
                .collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages());
    }

    public PublicationsWithRandomCategoryResponse findWithRandomCategory(PaginationRequest paginationRequest) {
        Long categoryId = categoryService.getRandomCategoryId();
        final List<PublicationResponse> publicationResponses = publicationRepository.findAllBySubcategoryCategoryId(categoryId, paginationRequest.mapToPageable())
                .stream()
                .map(PublicationResponse::new)
                .collect(Collectors.toList());
        return new PublicationsWithRandomCategoryResponse(publicationResponses, categoryService.findById(categoryId).getName());
    }

    public void delete(Long id) {
        if (findById(id).getUser().getLogin().equals((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            publicationRepository.delete(findById(id));
        }
    }

    public Publication findById(Long id) {
        return publicationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Publication with id " + id + " not exist"));
    }

    public PublicationResponse findOne(Long id) {
        return publicationRepository.findById(id).map(PublicationResponse::new).orElseThrow(() -> new IllegalArgumentException("Publication with id " + id + " not exist"));
    }


    private Publication publicationRequestToPublication(PublicationRequest request, Publication publication) throws IOException {
        if (publication == null) {
            publication = new Publication();
            publication.setCreationDate(LocalDateTime.now());
        }
        List<String> imagesToSave = publication.getImages();
        publication.setName(request.getName());
        publication.setPrice(request.getPrice());
        publication.setDescription(request.getDescription());
        publication.setSubcategory(subcategoryService.findById(request.getSubcategoryId()));
        publication.setType(typeService.findById(request.getTypeId()));
        publication.setUser(userService.findByLogin((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        publication.setDelivery(deliveryService.findById(request.getDeliveryId()));
        publication.setCount(request.getCount());

        if (request.getMainImage() != null) {
            final String file = fileTool.saveFile(request.getMainImage());
            publication.setMainImageName(file);
        }
        if (request.getImages() != null) {
            for (String image : request.getImages()) {
                imagesToSave.add(fileTool.saveFile(image));
                System.out.println(imagesToSave.toString());
            }
            publication.setImages(imagesToSave);
        }
        return publication;
    }

    public Page<Publication> findByLogin(String login, PaginationRequest request) {
        return publicationRepository.findAllByUserLogin(login, request.mapToPageable());
    }

    public Boolean reducePublicationCount(Integer value, Long id) {
        Publication publication = findById(id);
        if (publication.getCount() >= value) {
            publication.setCount(publication.getCount() - value);
            saveFromObject(publication);
            return true;
        }
        return false;
    }

    public void updateImage(Long id, String imageName) {
        Publication p = findById(id);
        p.setMainImageName(imageName);
        publicationRepository.save(p);
    }

    public void addImage(Long id, AddImageRequest request) throws IOException {
        Publication p = findById(id);
        if (p.getImages().size() < 5) {
            p.getImages().add(fileTool.saveFile(request.getImage()));
            publicationRepository.save(p);
        }
    }

    public void deleteImage(Long id, String imageName) {
        Publication p = findById(id);
        p.getImages().remove(imageName);
        publicationRepository.save(p);
    }

    public PageResponse<PublicationResponse> findByUserId(Long id, PaginationRequest request) {
        final Page<Publication> page = publicationRepository.findAllByUserId(id,request.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(PublicationResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }


}
