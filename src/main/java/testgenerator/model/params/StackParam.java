package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackParam {

    private String name;

    private List<Long> Topics;
}
