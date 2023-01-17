package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StackAddUpdateParam {

    private String name;

    private List<Long> Topics;
}
