package rostyk.stupnytskiy.zepka.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> data;
    private Long totalElements;
    private Integer totalPages;

}
