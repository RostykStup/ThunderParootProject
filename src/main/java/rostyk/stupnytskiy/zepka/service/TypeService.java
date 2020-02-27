package rostyk.stupnytskiy.zepka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.TypeRequest;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.TypeResponse;
import rostyk.stupnytskiy.zepka.entity.Type;
import rostyk.stupnytskiy.zepka.repository.TypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public void save(TypeRequest request){
        typeRepository.save(typeRequestToType(request,null));
    }

    public void update(TypeRequest request, Long id){
        typeRepository.save(typeRequestToType(request,findById(id)));
    }

    public List<TypeResponse> findAll(){
        return typeRepository.findAll()
                .stream()
                .map(TypeResponse::new)
                .collect(Collectors.toList());
    }

    public void delete(Long id){
        typeRepository.delete(findById(id));
    }

    public Type findById(Long id) {
        return typeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Type with id " + id + " not exist"));
    }

    public PageResponse<TypeResponse> findAll(PaginationRequest paginationRequest){
        final Page<Type> page = typeRepository.findAll(paginationRequest.mapToPageable());
        return new PageResponse<>(page.getContent().stream().map(TypeResponse::new).collect(Collectors.toList()),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    private Type typeRequestToType(TypeRequest request,Type type){
        if(type == null){
            type = new Type();
        }
        type.setName(request.getName());
        return type;
    }
}
