package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.PaginationRequest;
import rostyk.stupnytskiy.zepka.dto.request.TypeRequest;
import rostyk.stupnytskiy.zepka.dto.response.PageResponse;
import rostyk.stupnytskiy.zepka.dto.response.TypeResponse;
import rostyk.stupnytskiy.zepka.service.TypeService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @PostMapping()
    public void save(@RequestBody TypeRequest request){
        typeService.save(request);
    }

    @PutMapping()
    public void update(Long id, @RequestBody TypeRequest request){
        typeService.update(request,id);
    }

    @GetMapping()
    public List<TypeResponse> findAll(){
        return typeService.findAll();
    }

    @DeleteMapping
    public void delete(Long id){
        typeService.delete(id);
    }

    @GetMapping("/")
    public PageResponse<TypeResponse> findAll(@Valid PaginationRequest request){
        return typeService.findAll(request);
    }
}
