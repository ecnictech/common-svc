package ecnic.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListPaging<T> {

    private Integer totalPage = 0;
    private Long totalData = 0L;
    private List<T>  data;
}
